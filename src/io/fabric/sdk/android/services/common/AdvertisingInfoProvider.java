package io.fabric.sdk.android.services.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;

class AdvertisingInfoProvider
{
  private static final String ADVERTISING_INFO_PREFERENCES = "TwitterAdvertisingInfoPreferences";
  private static final String PREFKEY_ADVERTISING_ID = "advertising_id";
  private static final String PREFKEY_LIMIT_AD_TRACKING = "limit_ad_tracking_enabled";
  private final Context context;
  private final PreferenceStore preferenceStore;

  public AdvertisingInfoProvider(Context paramContext)
  {
    this.context = paramContext.getApplicationContext();
    this.preferenceStore = new PreferenceStoreImpl(paramContext, "TwitterAdvertisingInfoPreferences");
  }

  private AdvertisingInfo getAdvertisingInfoFromStrategies()
  {
    AdvertisingInfo localAdvertisingInfo1 = getReflectionStrategy().getAdvertisingInfo();
    if (!isInfoValid(localAdvertisingInfo1))
    {
      AdvertisingInfo localAdvertisingInfo2 = getServiceStrategy().getAdvertisingInfo();
      if (!isInfoValid(localAdvertisingInfo2))
      {
        Fabric.getLogger().d("Fabric", "AdvertisingInfo not present");
        return localAdvertisingInfo2;
      }
      Fabric.getLogger().d("Fabric", "Using AdvertisingInfo from Service Provider");
      return localAdvertisingInfo2;
    }
    Fabric.getLogger().d("Fabric", "Using AdvertisingInfo from Reflection Provider");
    return localAdvertisingInfo1;
  }

  private boolean isInfoValid(AdvertisingInfo paramAdvertisingInfo)
  {
    return (paramAdvertisingInfo != null) && (!TextUtils.isEmpty(paramAdvertisingInfo.advertisingId));
  }

  private void refreshInfoIfNeededAsync(final AdvertisingInfo paramAdvertisingInfo)
  {
    new Thread(new BackgroundPriorityRunnable()
    {
      public void onRun()
      {
        AdvertisingInfo localAdvertisingInfo = AdvertisingInfoProvider.this.getAdvertisingInfoFromStrategies();
        if (!paramAdvertisingInfo.equals(localAdvertisingInfo))
        {
          Fabric.getLogger().d("Fabric", "Asychronously getting Advertising Info and storing it to preferences");
          AdvertisingInfoProvider.this.storeInfoToPreferences(localAdvertisingInfo);
        }
      }
    }).start();
  }

  @SuppressLint({"CommitPrefEdits"})
  private void storeInfoToPreferences(AdvertisingInfo paramAdvertisingInfo)
  {
    if (isInfoValid(paramAdvertisingInfo))
    {
      this.preferenceStore.save(this.preferenceStore.edit().putString("advertising_id", paramAdvertisingInfo.advertisingId).putBoolean("limit_ad_tracking_enabled", paramAdvertisingInfo.limitAdTrackingEnabled));
      return;
    }
    this.preferenceStore.save(this.preferenceStore.edit().remove("advertising_id").remove("limit_ad_tracking_enabled"));
  }

  public AdvertisingInfo getAdvertisingInfo()
  {
    AdvertisingInfo localAdvertisingInfo1 = getInfoFromPreferences();
    if (isInfoValid(localAdvertisingInfo1))
    {
      Fabric.getLogger().d("Fabric", "Using AdvertisingInfo from Preference Store");
      refreshInfoIfNeededAsync(localAdvertisingInfo1);
      return localAdvertisingInfo1;
    }
    AdvertisingInfo localAdvertisingInfo2 = getAdvertisingInfoFromStrategies();
    storeInfoToPreferences(localAdvertisingInfo2);
    return localAdvertisingInfo2;
  }

  protected AdvertisingInfo getInfoFromPreferences()
  {
    return new AdvertisingInfo(this.preferenceStore.get().getString("advertising_id", ""), this.preferenceStore.get().getBoolean("limit_ad_tracking_enabled", false));
  }

  public AdvertisingInfoStrategy getReflectionStrategy()
  {
    return new AdvertisingInfoReflectionStrategy(this.context);
  }

  public AdvertisingInfoStrategy getServiceStrategy()
  {
    return new AdvertisingInfoServiceStrategy(this.context);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.AdvertisingInfoProvider
 * JD-Core Version:    0.6.2
 */