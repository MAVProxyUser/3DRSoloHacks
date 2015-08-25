package com.crashlytics.android.beta;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.ApiKey;
import io.fabric.sdk.android.services.common.CurrentTimeProvider;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.settings.BetaSettingsData;

class CheckForUpdatesController
{
  static final long LAST_UPDATE_CHECK_DEFAULT = 0L;
  static final String LAST_UPDATE_CHECK_KEY = "last_update_check";
  private final Beta beta;
  private final BetaSettingsData betaSettings;
  private final BuildProperties buildProps;
  private final Context context;
  private final CurrentTimeProvider currentTimeProvider;
  private final HttpRequestFactory httpRequestFactory;
  private final IdManager idManager;
  private final PreferenceStore preferenceStore;

  public CheckForUpdatesController(Context paramContext, Beta paramBeta, IdManager paramIdManager, BetaSettingsData paramBetaSettingsData, BuildProperties paramBuildProperties, PreferenceStore paramPreferenceStore, CurrentTimeProvider paramCurrentTimeProvider, HttpRequestFactory paramHttpRequestFactory)
  {
    this.context = paramContext;
    this.beta = paramBeta;
    this.idManager = paramIdManager;
    this.betaSettings = paramBetaSettingsData;
    this.buildProps = paramBuildProperties;
    this.preferenceStore = paramPreferenceStore;
    this.currentTimeProvider = paramCurrentTimeProvider;
    this.httpRequestFactory = paramHttpRequestFactory;
  }

  public void checkForUpdates()
  {
    long l1 = this.currentTimeProvider.getCurrentTimeMillis();
    long l2 = 1000 * this.betaSettings.updateSuspendDurationSeconds;
    Fabric.getLogger().d("Beta", "Check for updates delay: " + l2);
    long l3 = this.preferenceStore.get().getLong("last_update_check", 0L);
    Fabric.getLogger().d("Beta", "Check for updates last check time: " + l3);
    long l4 = l3 + l2;
    Fabric.getLogger().d("Beta", "Check for updates current time: " + l1 + ", next check time: " + l4);
    if (l1 >= l4)
      try
      {
        Fabric.getLogger().d("Beta", "Performing update check");
        String str1 = ApiKey.getApiKey(this.context);
        String str2 = this.idManager.createIdHeaderValue(str1, this.buildProps.packageName);
        new CheckForUpdatesRequest(this.beta, this.beta.getOverridenSpiEndpoint(), this.betaSettings.updateUrl, this.httpRequestFactory, new CheckForUpdatesResponseTransform()).invoke(str1, str2, this.buildProps);
        return;
      }
      finally
      {
        this.preferenceStore.edit().putLong("last_update_check", l1).commit();
      }
    Fabric.getLogger().d("Beta", "Check for updates next check time was not passed");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.beta.CheckForUpdatesController
 * JD-Core Version:    0.6.2
 */