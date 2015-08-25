package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.common.IdManager.DeviceIdentifierType;
import io.fabric.sdk.android.services.common.SystemCurrentTimeProvider;
import io.fabric.sdk.android.services.events.GZIPQueueFileEventStorage;
import io.fabric.sdk.android.services.network.DefaultHttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.util.Map;
import java.util.UUID;

public class Answers extends Kit<Boolean>
{
  static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  static final long FIRST_LAUNCH_INTERVAL_IN_MS = 3600000L;
  static final String PREFKEY_ANALYTICS_LAUNCHED = "analytics_launched";
  static final String SESSION_ANALYTICS_FILE_EXTENSION = ".tap";
  static final String SESSION_ANALYTICS_FILE_NAME = "session_analytics.tap";
  private static final String SESSION_ANALYTICS_TO_SEND_DIR = "session_analytics_to_send";
  public static final String TAG = "Answers";
  private long installedAt;
  private PreferenceStore preferenceStore;
  private SessionAnalyticsManager sessionAnalyticsManager;
  private String versionCode;
  private String versionName;

  public static Answers getInstance()
  {
    return (Answers)Fabric.getKit(Answers.class);
  }

  @TargetApi(14)
  private void initializeSessionAnalytics(Context paramContext)
  {
    try
    {
      SessionEventTransform localSessionEventTransform = new SessionEventTransform();
      SystemCurrentTimeProvider localSystemCurrentTimeProvider = new SystemCurrentTimeProvider();
      GZIPQueueFileEventStorage localGZIPQueueFileEventStorage = new GZIPQueueFileEventStorage(getContext(), getSdkDirectory(), "session_analytics.tap", "session_analytics_to_send");
      SessionAnalyticsFilesManager localSessionAnalyticsFilesManager = new SessionAnalyticsFilesManager(paramContext, localSessionEventTransform, localSystemCurrentTimeProvider, localGZIPQueueFileEventStorage);
      IdManager localIdManager = getIdManager();
      Map localMap = localIdManager.getDeviceIdentifiers();
      String str1 = paramContext.getPackageName();
      String str2 = localIdManager.getAppInstallIdentifier();
      String str3 = (String)localMap.get(IdManager.DeviceIdentifierType.ANDROID_ID);
      String str4 = (String)localMap.get(IdManager.DeviceIdentifierType.ANDROID_ADVERTISING_ID);
      String str5 = (String)localMap.get(IdManager.DeviceIdentifierType.FONT_TOKEN);
      String str6 = CommonUtils.resolveBuildId(paramContext);
      String str7 = localIdManager.getOsVersionString();
      String str8 = localIdManager.getModelName();
      SessionEventMetadata localSessionEventMetadata = new SessionEventMetadata(str1, UUID.randomUUID().toString(), str2, str3, str4, str5, str6, str7, str8, this.versionCode, this.versionName);
      Application localApplication = (Application)getContext().getApplicationContext();
      AutoSessionAnalyticsManager localAutoSessionAnalyticsManager;
      if ((localApplication != null) && (Build.VERSION.SDK_INT >= 14))
      {
        DefaultHttpRequestFactory localDefaultHttpRequestFactory1 = new DefaultHttpRequestFactory(Fabric.getLogger());
        localAutoSessionAnalyticsManager = new AutoSessionAnalyticsManager(localApplication, localSessionEventMetadata, localSessionAnalyticsFilesManager, localDefaultHttpRequestFactory1);
      }
      SessionAnalyticsManager localSessionAnalyticsManager;
      for (this.sessionAnalyticsManager = localAutoSessionAnalyticsManager; isFirstLaunch(this.installedAt); this.sessionAnalyticsManager = localSessionAnalyticsManager)
      {
        Fabric.getLogger().d("Answers", "First launch");
        onApplicationInstall();
        return;
        DefaultHttpRequestFactory localDefaultHttpRequestFactory2 = new DefaultHttpRequestFactory(Fabric.getLogger());
        localSessionAnalyticsManager = new SessionAnalyticsManager(paramContext, localSessionEventMetadata, localSessionAnalyticsFilesManager, localDefaultHttpRequestFactory2);
      }
    }
    catch (Exception localException)
    {
      CommonUtils.logControlledError(paramContext, "Crashlytics failed to initialize session analytics.", localException);
    }
  }

  protected Boolean doInBackground()
  {
    Context localContext = getContext();
    initializeSessionAnalytics(localContext);
    try
    {
      SettingsData localSettingsData = Settings.getInstance().awaitSettingsData();
      if (localSettingsData == null)
        return Boolean.valueOf(false);
      if (localSettingsData.featuresData.collectAnalytics)
      {
        this.sessionAnalyticsManager.setAnalyticsSettingsData(localSettingsData.analyticsSettingsData, getOverridenSpiEndpoint());
        return Boolean.valueOf(true);
      }
      CommonUtils.logControlled(localContext, "Disabling analytics collection based on settings flag value.");
      this.sessionAnalyticsManager.disable();
      Boolean localBoolean = Boolean.valueOf(false);
      return localBoolean;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Answers", "Error dealing with settings", localException);
    }
    return Boolean.valueOf(false);
  }

  boolean getAnalyticsLaunched()
  {
    return this.preferenceStore.get().getBoolean("analytics_launched", false);
  }

  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android:answers";
  }

  String getOverridenSpiEndpoint()
  {
    return CommonUtils.getStringsFileValue(getContext(), "com.crashlytics.ApiEndpoint");
  }

  File getSdkDirectory()
  {
    return new FileStoreImpl(this).getFilesDir();
  }

  public String getVersion()
  {
    return "1.1.1.32";
  }

  boolean installedRecently(long paramLong)
  {
    return System.currentTimeMillis() - paramLong < 3600000L;
  }

  boolean isFirstLaunch(long paramLong)
  {
    return (!getAnalyticsLaunched()) && (installedRecently(paramLong));
  }

  @SuppressLint({"CommitPrefEdits"})
  void onApplicationInstall()
  {
    if (this.sessionAnalyticsManager != null)
    {
      this.sessionAnalyticsManager.onInstall();
      this.preferenceStore.save(this.preferenceStore.edit().putBoolean("analytics_launched", true));
    }
  }

  public void onException(Crash.FatalException paramFatalException)
  {
    if (this.sessionAnalyticsManager != null)
      this.sessionAnalyticsManager.onCrash(paramFatalException.getSessionId());
  }

  public void onException(Crash.LoggedException paramLoggedException)
  {
    if (this.sessionAnalyticsManager != null)
      this.sessionAnalyticsManager.onError(paramLoggedException.getSessionId());
  }

  @SuppressLint({"NewApi"})
  protected boolean onPreExecute()
  {
    try
    {
      this.preferenceStore = new PreferenceStoreImpl(Fabric.getKit(Answers.class));
      Context localContext = getContext();
      PackageInfo localPackageInfo = localContext.getPackageManager().getPackageInfo(localContext.getPackageName(), 0);
      this.versionCode = Integer.toString(localPackageInfo.versionCode);
      if (localPackageInfo.versionName == null);
      for (String str = "0.0"; ; str = localPackageInfo.versionName)
      {
        this.versionName = str;
        if (Build.VERSION.SDK_INT < 9)
          break;
        this.installedAt = localPackageInfo.firstInstallTime;
        break label140;
      }
      this.installedAt = new File(localContext.getPackageManager().getApplicationInfo(localContext.getPackageName(), 0).sourceDir).lastModified();
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Answers", "Error setting up app properties", localException);
      return false;
    }
    label140: return true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.Answers
 * JD-Core Version:    0.6.2
 */