package io.fabric.sdk.android.services.settings;

import android.content.Context;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public class Settings
{
  public static final String SETTINGS_CACHE_FILENAME = "com.crashlytics.settings.json";
  private static final String SETTINGS_URL_FORMAT = "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings";
  private boolean initialized = false;
  private SettingsController settingsController;
  private final AtomicReference<SettingsData> settingsData = new AtomicReference();
  private final CountDownLatch settingsDataLatch = new CountDownLatch(1);

  public static Settings getInstance()
  {
    return LazyHolder.INSTANCE;
  }

  private void setSettingsData(SettingsData paramSettingsData)
  {
    this.settingsData.set(paramSettingsData);
    this.settingsDataLatch.countDown();
  }

  public SettingsData awaitSettingsData()
  {
    try
    {
      this.settingsDataLatch.await();
      SettingsData localSettingsData = (SettingsData)this.settingsData.get();
      return localSettingsData;
    }
    catch (InterruptedException localInterruptedException)
    {
      Fabric.getLogger().e("Fabric", "Interrupted while waiting for settings data.");
    }
    return null;
  }

  public void clearSettings()
  {
    this.settingsData.set(null);
  }

  public Settings initialize(Kit paramKit, IdManager paramIdManager, HttpRequestFactory paramHttpRequestFactory, String paramString1, String paramString2, String paramString3)
  {
    try
    {
      boolean bool = this.initialized;
      if (bool);
      for (Settings localSettings = this; ; localSettings = this)
      {
        return localSettings;
        if (this.settingsController == null)
        {
          Context localContext = paramKit.getContext();
          String str1 = paramIdManager.getAppIdentifier();
          String str2 = ApiKey.getApiKey(localContext, false);
          String str3 = paramIdManager.getInstallerPackageName();
          SystemCurrentTimeProvider localSystemCurrentTimeProvider = new SystemCurrentTimeProvider();
          DefaultSettingsJsonTransform localDefaultSettingsJsonTransform = new DefaultSettingsJsonTransform();
          DefaultCachedSettingsIo localDefaultCachedSettingsIo = new DefaultCachedSettingsIo(paramKit);
          String str4 = CommonUtils.getAppIconHashOrNull(localContext);
          String str5 = String.format(Locale.US, "https://settings.crashlytics.com/spi/v2/platforms/android/apps/%s/settings", new Object[] { str1 });
          DefaultSettingsSpiCall localDefaultSettingsSpiCall = new DefaultSettingsSpiCall(paramKit, paramString3, str5, paramHttpRequestFactory);
          String str6 = paramIdManager.createIdHeaderValue(str2, str1);
          String[] arrayOfString = new String[1];
          arrayOfString[0] = CommonUtils.resolveBuildId(localContext);
          this.settingsController = new DefaultSettingsController(paramKit, new SettingsRequest(str2, str6, CommonUtils.createInstanceIdFrom(arrayOfString), paramString2, paramString1, DeliveryMechanism.determineFrom(str3).getId(), str4), localSystemCurrentTimeProvider, localDefaultSettingsJsonTransform, localDefaultCachedSettingsIo, localDefaultSettingsSpiCall);
        }
        this.initialized = true;
      }
    }
    finally
    {
    }
  }

  public boolean loadSettingsData()
  {
    try
    {
      SettingsData localSettingsData = this.settingsController.loadSettingsData();
      setSettingsData(localSettingsData);
      if (localSettingsData != null)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
    finally
    {
    }
  }

  public boolean loadSettingsSkippingCache()
  {
    try
    {
      SettingsData localSettingsData = this.settingsController.loadSettingsData(SettingsCacheBehavior.SKIP_CACHE_LOOKUP);
      setSettingsData(localSettingsData);
      if (localSettingsData == null)
        Fabric.getLogger().e("Fabric", "Failed to force reload of settings from Crashlytics.", null);
      if (localSettingsData != null);
      for (boolean bool = true; ; bool = false)
        return bool;
    }
    finally
    {
    }
  }

  public void setSettingsController(SettingsController paramSettingsController)
  {
    this.settingsController = paramSettingsController;
  }

  public <T> T withSettings(SettingsAccess<T> paramSettingsAccess, T paramT)
  {
    SettingsData localSettingsData = (SettingsData)this.settingsData.get();
    if (localSettingsData == null)
      return paramT;
    return paramSettingsAccess.usingSettings(localSettingsData);
  }

  static class LazyHolder
  {
    private static final Settings INSTANCE = new Settings(null);
  }

  public static abstract interface SettingsAccess<T>
  {
    public abstract T usingSettings(SettingsData paramSettingsData);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.Settings
 * JD-Core Version:    0.6.2
 */