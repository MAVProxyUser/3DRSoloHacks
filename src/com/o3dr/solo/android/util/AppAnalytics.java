package com.o3dr.solo.android.util;

import android.content.Context;
import com.segment.analytics.Analytics;
import com.segment.analytics.Analytics.Builder;
import com.segment.analytics.Properties;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class AppAnalytics
{
  public static final String ALERT_DISMISSED = "Alert Dismissed";
  public static final String ALERT_SHOWN = "Alert Shown";
  public static final String CALIBRATION = "Calibration";
  public static final String DOWNLOAD_UPDATE_COMPLETED = "Download Update Completed";
  public static final String FLIGHT_SCREEN = "Flight Screen";
  public static final String SETTINGS = "Settings";
  public static final String SHOT_COMPLETED = "Shot Completed";
  public static final String SHOT_SELECTED = "Shot Selected";
  public static final String SHOT_STARTED = "Shot Started";
  public static final String SHOT_TUTORIAL_COMPLETED = "Shot Tutorial Completed";
  public static final String SHOT_TUTORIAL_STARTED = "Shot Tutorial Started";
  public static final String UPDATE_APPLIED = "Update Applied";
  public static final String UPDATE_AVAILABLE = "Update Available";
  public static final String UPDATE_DOWNLOADED = "Update Downloaded";
  public static final String UPDATE_TRANSFERED = "Update Transferred";
  public static final String UPDATE_VEHICLE_UP_TO_DATE = "Vehicle up to date";
  private AppPreferences appPreferences;
  private Context context;

  public AppAnalytics(Context paramContext, String paramString)
  {
    this.context = paramContext;
    this.appPreferences = new AppPreferences(paramContext);
    Analytics.setSingletonInstance(new Analytics.Builder(paramContext, paramContext.getString(2131099748)).build());
    setUniqueID(paramString);
  }

  private void setUniqueID(String paramString)
  {
    Analytics.with(this.context).identify(paramString);
  }

  private void track(String paramString, Properties paramProperties)
  {
    if (this.appPreferences.areAnalyticsEnabled())
      Analytics.with(this.context).track(paramString, paramProperties);
  }

  public void track(String paramString)
  {
    track(paramString, new Properties());
  }

  public void track(String paramString1, String paramString2, Object paramObject)
  {
    track(paramString1, new Properties().putValue(paramString2, paramObject));
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface Category
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.AppAnalytics
 * JD-Core Version:    0.6.2
 */