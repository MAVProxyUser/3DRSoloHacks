package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build.VERSION;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.cache.MemoryValueCache;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeviceIdentifierProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.BetaSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Beta extends Kit<Boolean>
  implements DeviceIdentifierProvider
{
  private static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  private static final String CRASHLYTICS_BUILD_PROPERTIES = "crashlytics-build.properties";
  static final String NO_DEVICE_TOKEN = "";
  public static final String TAG = "Beta";
  private final MemoryValueCache<String> deviceTokenCache = new MemoryValueCache();
  private final DeviceTokenLoader deviceTokenLoader = new DeviceTokenLoader();

  private void checkForUpdates(Context paramContext, IdManager paramIdManager, BetaSettingsData paramBetaSettingsData, BuildProperties paramBuildProperties)
  {
    new CheckForUpdatesController(paramContext, this, paramIdManager, paramBetaSettingsData, paramBuildProperties, new PreferenceStoreImpl(Fabric.getKit(Beta.class)), new SystemCurrentTimeProvider(), new DefaultHttpRequestFactory(Fabric.getLogger())).checkForUpdates();
  }

  private String getBetaDeviceToken(Context paramContext, String paramString)
  {
    if (isAppPossiblyInstalledByBeta(paramString, Build.VERSION.SDK_INT))
    {
      Fabric.getLogger().d("Beta", "App was installed by Beta. Getting device token");
      try
      {
        String str = (String)this.deviceTokenCache.get(paramContext, this.deviceTokenLoader);
        boolean bool = "".equals(str);
        if (bool)
          return null;
        return str;
      }
      catch (Exception localException)
      {
        Fabric.getLogger().e("Beta", "Failed to load the Beta device token", localException);
        return null;
      }
    }
    Fabric.getLogger().d("Beta", "App was not installed by Beta. Skipping device token");
    return null;
  }

  private BetaSettingsData getBetaSettingsData()
  {
    SettingsData localSettingsData = Settings.getInstance().awaitSettingsData();
    if (localSettingsData != null)
      return localSettingsData.betaSettingsData;
    return null;
  }

  public static Beta getInstance()
  {
    return (Beta)Fabric.getKit(Beta.class);
  }

  private BuildProperties loadBuildProperties(Context paramContext)
  {
    InputStream localInputStream = null;
    BuildProperties localBuildProperties = null;
    try
    {
      localInputStream = paramContext.getAssets().open("crashlytics-build.properties");
      localBuildProperties = null;
      if (localInputStream != null)
      {
        localBuildProperties = BuildProperties.fromPropertiesStream(localInputStream);
        Fabric.getLogger().d("Beta", localBuildProperties.packageName + " build properties: " + localBuildProperties.versionName + " (" + localBuildProperties.versionCode + ")" + " - " + localBuildProperties.buildId);
      }
      if (localInputStream != null);
      try
      {
        localInputStream.close();
        return localBuildProperties;
      }
      catch (IOException localIOException3)
      {
        Fabric.getLogger().e("Beta", "Error closing Beta build properties asset", localIOException3);
        return localBuildProperties;
      }
    }
    catch (Exception localException)
    {
      do
        Fabric.getLogger().e("Beta", "Error reading Beta build properties", localException);
      while (localInputStream == null);
      try
      {
        localInputStream.close();
        return localBuildProperties;
      }
      catch (IOException localIOException2)
      {
        Fabric.getLogger().e("Beta", "Error closing Beta build properties asset", localIOException2);
        return localBuildProperties;
      }
    }
    finally
    {
      if (localInputStream == null);
    }
    try
    {
      localInputStream.close();
      throw localObject;
    }
    catch (IOException localIOException1)
    {
      while (true)
        Fabric.getLogger().e("Beta", "Error closing Beta build properties asset", localIOException1);
    }
  }

  boolean canCheckForUpdates(BetaSettingsData paramBetaSettingsData, BuildProperties paramBuildProperties)
  {
    return (paramBetaSettingsData != null) && (!TextUtils.isEmpty(paramBetaSettingsData.updateUrl)) && (paramBuildProperties != null);
  }

  protected Boolean doInBackground()
  {
    Fabric.getLogger().d("Beta", "Beta kit initializing...");
    Context localContext = getContext();
    IdManager localIdManager = getIdManager();
    if (TextUtils.isEmpty(getBetaDeviceToken(localContext, localIdManager.getInstallerPackageName())))
    {
      Fabric.getLogger().d("Beta", "A Beta device token was not found for this app");
      return Boolean.valueOf(false);
    }
    Fabric.getLogger().d("Beta", "Beta device token is present, checking for app updates.");
    BetaSettingsData localBetaSettingsData = getBetaSettingsData();
    BuildProperties localBuildProperties = loadBuildProperties(localContext);
    if (canCheckForUpdates(localBetaSettingsData, localBuildProperties))
      checkForUpdates(localContext, localIdManager, localBetaSettingsData, localBuildProperties);
    return Boolean.valueOf(true);
  }

  public Map<IdManager.DeviceIdentifierType, String> getDeviceIdentifiers()
  {
    String str1 = getIdManager().getInstallerPackageName();
    String str2 = getBetaDeviceToken(getContext(), str1);
    HashMap localHashMap = new HashMap();
    if (!TextUtils.isEmpty(str2))
      localHashMap.put(IdManager.DeviceIdentifierType.FONT_TOKEN, str2);
    return localHashMap;
  }

  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android:beta";
  }

  String getOverridenSpiEndpoint()
  {
    return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
  }

  public String getVersion()
  {
    return "1.1.1.32";
  }

  @TargetApi(11)
  boolean isAppPossiblyInstalledByBeta(String paramString, int paramInt)
  {
    if (paramInt < 11)
      return paramString == null;
    return "io.crash.air".equals(paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.beta.Beta
 * JD-Core Version:    0.6.2
 */