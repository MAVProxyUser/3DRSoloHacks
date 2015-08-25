package com.segment.analytics.internal;

import android.app.Activity;
import android.os.Bundle;
import com.segment.analytics.Analytics;
import com.segment.analytics.ValueMap;
import com.segment.analytics.internal.model.payloads.AliasPayload;
import com.segment.analytics.internal.model.payloads.GroupPayload;
import com.segment.analytics.internal.model.payloads.IdentifyPayload;
import com.segment.analytics.internal.model.payloads.ScreenPayload;
import com.segment.analytics.internal.model.payloads.TrackPayload;

public abstract class AbstractIntegration<T>
{
  public static final String ADDED_PRODUCT = "Added Product";
  public static final String COMPLETED_ORDER = "Completed Order";
  public static final String VIEWED_EVENT_FORMAT = "Viewed %s Screen";
  public static final String VIEWED_PRODUCT = "Viewed Product";
  public static final String VIEWED_PRODUCT_CATEGORY = "Viewed Product Category";

  public void alias(AliasPayload paramAliasPayload)
  {
  }

  public void flush()
  {
  }

  public T getUnderlyingInstance()
  {
    return null;
  }

  public void group(GroupPayload paramGroupPayload)
  {
  }

  public void identify(IdentifyPayload paramIdentifyPayload)
  {
  }

  public abstract void initialize(Analytics paramAnalytics, ValueMap paramValueMap)
    throws IllegalStateException;

  public abstract String key();

  public void onActivityCreated(Activity paramActivity, Bundle paramBundle)
  {
  }

  public void onActivityDestroyed(Activity paramActivity)
  {
  }

  public void onActivityPaused(Activity paramActivity)
  {
  }

  public void onActivityResumed(Activity paramActivity)
  {
  }

  public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle)
  {
  }

  public void onActivityStarted(Activity paramActivity)
  {
  }

  public void onActivityStopped(Activity paramActivity)
  {
  }

  public void reset()
  {
  }

  public void screen(ScreenPayload paramScreenPayload)
  {
  }

  public void track(TrackPayload paramTrackPayload)
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.AbstractIntegration
 * JD-Core Version:    0.6.2
 */