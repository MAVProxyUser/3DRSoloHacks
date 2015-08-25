package io.fabric.sdk.android;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.DeliveryMechanism;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AppRequestData;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.CreateAppSpiCall;
import io.fabric.sdk.android.services.settings.IconRequest;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import io.fabric.sdk.android.services.settings.UpdateAppSpiCall;
import java.util.Collection;

class Onboarding extends Kit<Boolean>
{
  static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  private String applicationLabel;
  private String installerPackageName;
  private final Collection<Kit> kits;
  private PackageInfo packageInfo;
  private PackageManager packageManager;
  private String packageName;
  private final HttpRequestFactory requestFactory;
  private String targetAndroidSdkVersion;
  private String versionCode;
  private String versionName;

  public Onboarding(Collection<Kit> paramCollection)
  {
    this.kits = paramCollection;
    this.requestFactory = new DefaultHttpRequestFactory();
  }

  private AppRequestData buildAppRequest(IconRequest paramIconRequest, Collection<Kit> paramCollection)
  {
    Context localContext = getContext();
    String str1 = ApiKey.getApiKey(localContext, Fabric.isDebuggable());
    String str2 = CommonUtils.createInstanceIdFrom(new String[] { CommonUtils.resolveBuildId(localContext) });
    int i = DeliveryMechanism.determineFrom(this.installerPackageName).getId();
    return new AppRequestData(str1, getIdManager().getAppIdentifier(), this.versionName, this.versionCode, str2, this.applicationLabel, i, this.targetAndroidSdkVersion, "0", paramIconRequest, paramCollection);
  }

  private boolean performAutoConfigure(String paramString, AppSettingsData paramAppSettingsData, Collection<Kit> paramCollection)
  {
    boolean bool = true;
    if ("new".equals(paramAppSettingsData.status))
      if (performCreateApp(paramString, paramAppSettingsData, paramCollection))
        bool = Settings.getInstance().loadSettingsSkippingCache();
    do
    {
      return bool;
      Fabric.getLogger().e("Fabric", "Failed to create app with Crashlytics service.", null);
      return false;
      if ("configured".equals(paramAppSettingsData.status))
        return Settings.getInstance().loadSettingsSkippingCache();
    }
    while (!paramAppSettingsData.updateRequired);
    Fabric.getLogger().d("Fabric", "Server says an update is required - forcing a full App update.");
    performUpdateApp(paramString, paramAppSettingsData, paramCollection);
    return bool;
  }

  private boolean performCreateApp(String paramString, AppSettingsData paramAppSettingsData, Collection<Kit> paramCollection)
  {
    AppRequestData localAppRequestData = buildAppRequest(IconRequest.build(getContext(), paramString), paramCollection);
    return new CreateAppSpiCall(this, getOverridenSpiEndpoint(), paramAppSettingsData.url, this.requestFactory).invoke(localAppRequestData);
  }

  private boolean performUpdateApp(AppSettingsData paramAppSettingsData, IconRequest paramIconRequest, Collection<Kit> paramCollection)
  {
    AppRequestData localAppRequestData = buildAppRequest(paramIconRequest, paramCollection);
    return new UpdateAppSpiCall(this, getOverridenSpiEndpoint(), paramAppSettingsData.url, this.requestFactory).invoke(localAppRequestData);
  }

  private boolean performUpdateApp(String paramString, AppSettingsData paramAppSettingsData, Collection<Kit> paramCollection)
  {
    return performUpdateApp(paramAppSettingsData, IconRequest.build(getContext(), paramString), paramCollection);
  }

  protected Boolean doInBackground()
  {
    String str = CommonUtils.getAppIconHashOrNull(getContext());
    try
    {
      Settings.getInstance().initialize(this, this.idManager, this.requestFactory, this.versionCode, this.versionName, getOverridenSpiEndpoint()).loadSettingsData();
      SettingsData localSettingsData2 = Settings.getInstance().awaitSettingsData();
      localSettingsData1 = localSettingsData2;
      bool1 = false;
      if (localSettingsData1 == null);
    }
    catch (Exception localException1)
    {
      try
      {
        boolean bool2 = performAutoConfigure(str, localSettingsData1.appData, this.kits);
        bool1 = bool2;
        return Boolean.valueOf(bool1);
        localException1 = localException1;
        Fabric.getLogger().e("Fabric", "Error dealing with settings", localException1);
        SettingsData localSettingsData1 = null;
      }
      catch (Exception localException2)
      {
        while (true)
        {
          Fabric.getLogger().e("Fabric", "Error performing auto configuration.", localException2);
          boolean bool1 = false;
        }
      }
    }
  }

  public String getIdentifier()
  {
    return "io.fabric.sdk.android:fabric";
  }

  String getOverridenSpiEndpoint()
  {
    return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
  }

  public String getVersion()
  {
    return "1.1.1.32";
  }

  protected boolean onPreExecute()
  {
    try
    {
      this.installerPackageName = getIdManager().getInstallerPackageName();
      this.packageManager = getContext().getPackageManager();
      this.packageName = getContext().getPackageName();
      this.packageInfo = this.packageManager.getPackageInfo(this.packageName, 0);
      this.versionCode = Integer.toString(this.packageInfo.versionCode);
      if (this.packageInfo.versionName == null);
      for (String str = "0.0"; ; str = this.packageInfo.versionName)
      {
        this.versionName = str;
        this.applicationLabel = this.packageManager.getApplicationLabel(getContext().getApplicationInfo()).toString();
        this.targetAndroidSdkVersion = Integer.toString(getContext().getApplicationInfo().targetSdkVersion);
        return true;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      Fabric.getLogger().e("Fabric", "Failed init", localNameNotFoundException);
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.Onboarding
 * JD-Core Version:    0.6.2
 */