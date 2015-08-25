package com.o3dr.solo.android.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import java.util.Locale;

public class AppPreferences
{
  private static final boolean DEFAULT_ADVANCED_FLIGHT_MODES = false;
  private static final boolean DEFAULT_ANALYTICS_ENABLED = true;
  private static final boolean DEFAULT_ARE_VIDEOS_SAVED_LOCALLY = true;
  private static final boolean DEFAULT_CABLE_CAM_INTERPOLATION = true;
  private static final int DEFAULT_CONTROLLER_MODE = 1;
  private static final int DEFAULT_CRUISE_SPEED = 4;
  private static final boolean DEFAULT_RUN_WIZARD = true;
  private static final int DEFAULT_UNIT_SYSTEM = 0;
  private static final boolean DEFAULT_VOICE_ALERTS_ENABLED = true;
  private static final int DEFAULT_VOICE_ALERTS_VOLUME = 50;
  public static final String PACKAGE_NAME = "com.o3dr.solo.android";
  public static final String PREF_ADVANCED_FLIGHT_MODES = "pref_advanced_flight_modes";
  public static final String PREF_ANALYTICS_ENABLED = "pref_analytics_enabled";
  private static final String PREF_CABLE_CAM_CRUISE_SPEED = "pref_cable_cam_cruise_speed";
  private static final String PREF_CABLE_CAM_INTERPOLATION = "pref_cable_cam_interpolation";
  public static final String PREF_CONTROLLER_MODE = "pref_controller_mode";
  private static final String PREF_FOLLOW_CRUISE_SPEED = "pref_follow_cruise_speed";
  private static final String PREF_ORBIT_CRUISE_SPEED = "pref_orbit_cruise_speed";
  public static final String PREF_RUN_WIZARD = "pref_run_wizard";
  private static final String PREF_SELFIE_ALTITUDE_UP = "pref_selfie_altitude_up";
  private static final String PREF_SELFIE_CRUISE_SPEED = "pref_selfie_cruise_speed";
  private static final String PREF_SELFIE_DISTANCE_OUT = "pref_selfie_distance_out";
  public static final String PREF_UNIT_SYSTEM = "pref_unit_system";
  public static final String PREF_USER_VEHICLE_LINK = "pref_user_vehicle_link";
  public static final String PREF_UUID = "pref_uuid";
  public static final String PREF_VIDEOS_SAVED_LOCALLY = "pref_videos_saved_locally";
  public static final String PREF_VOICE_ALERTS_ENABLED = "pref_voice_alerts_enabled";
  public static final String PREF_VOICE_ALERTS_VOLUME = "pref_voice_alerts_volume";
  private final LocalBroadcastManager lbm;
  private final SharedPreferences prefs;

  static
  {
    Locale localLocale = Locale.getDefault();
    if (Locale.US.equals(localLocale))
    {
      DEFAULT_UNIT_SYSTEM = 2;
      return;
    }
  }

  public AppPreferences(Context paramContext)
  {
    this.lbm = LocalBroadcastManager.getInstance(paramContext);
    this.prefs = PreferenceManager.getDefaultSharedPreferences(paramContext);
  }

  public boolean areAdvancedFlightModesEnabled()
  {
    return this.prefs.getBoolean("pref_advanced_flight_modes", false);
  }

  public boolean areAnalyticsEnabled()
  {
    return this.prefs.getBoolean("pref_analytics_enabled", true);
  }

  public boolean areVideosSavedLocally()
  {
    return this.prefs.getBoolean("pref_videos_saved_locally", true);
  }

  public void enableAnalytics(boolean paramBoolean)
  {
    this.prefs.edit().putBoolean("pref_analytics_enabled", paramBoolean).apply();
    this.lbm.sendBroadcast(new Intent("pref_analytics_enabled"));
  }

  public void enableVoiceAlerts(boolean paramBoolean)
  {
    this.prefs.edit().putBoolean("pref_voice_alerts_enabled", paramBoolean).apply();
    this.lbm.sendBroadcast(new Intent("pref_voice_alerts_enabled"));
  }

  public int getCableCamCruiseSpeed()
  {
    return this.prefs.getInt("pref_cable_cam_cruise_speed", 4);
  }

  public int getControllerStyle()
  {
    return this.prefs.getInt("pref_controller_mode", 1);
  }

  public int getFollowCruiseSpeed()
  {
    return this.prefs.getInt("pref_follow_cruise_speed", 4);
  }

  public int getOrbitCruiseSpeed()
  {
    return this.prefs.getInt("pref_orbit_cruise_speed", 4);
  }

  public String getPrefUuid()
  {
    return this.prefs.getString("pref_uuid", "");
  }

  public SharedPreferences getPrefs()
  {
    return this.prefs;
  }

  public int getSelfieAltitudeUp()
  {
    return this.prefs.getInt("pref_selfie_altitude_up", 25);
  }

  public int getSelfieCruiseSpeed()
  {
    return this.prefs.getInt("pref_selfie_cruise_speed", 4);
  }

  public int getSelfieDistanceOut()
  {
    return this.prefs.getInt("pref_selfie_distance_out", 50);
  }

  public int getUnitSystemType()
  {
    return this.prefs.getInt("pref_unit_system", DEFAULT_UNIT_SYSTEM);
  }

  public String getUserVehicleLink()
  {
    return this.prefs.getString("pref_user_vehicle_link", null);
  }

  public int getVoiceAlertsVolume()
  {
    return this.prefs.getInt("pref_voice_alerts_volume", 50);
  }

  public boolean isCableCamInterpolationOn()
  {
    return this.prefs.getBoolean("pref_cable_cam_interpolation", true);
  }

  public boolean isVoiceAlertsEnabled()
  {
    return this.prefs.getBoolean("pref_voice_alerts_enabled", true);
  }

  public boolean runWizard()
  {
    return this.prefs.getBoolean("pref_run_wizard", true);
  }

  public void saveVideosLocally(boolean paramBoolean)
  {
    this.prefs.edit().putBoolean("pref_videos_saved_locally", paramBoolean).apply();
    this.lbm.sendBroadcast(new Intent("pref_videos_saved_locally"));
  }

  public void setAdvancedFlightModes(boolean paramBoolean)
  {
    this.prefs.edit().putBoolean("pref_advanced_flight_modes", paramBoolean).apply();
    this.lbm.sendBroadcast(new Intent("pref_advanced_flight_modes"));
  }

  public void setCableCamCruiseSpeed(int paramInt)
  {
    this.prefs.edit().putInt("pref_cable_cam_cruise_speed", paramInt).apply();
  }

  public void setCableCamInterpolation(boolean paramBoolean)
  {
    this.prefs.edit().putBoolean("pref_cable_cam_interpolation", paramBoolean).apply();
  }

  public void setControllerStyle(int paramInt)
  {
    this.prefs.edit().putInt("pref_controller_mode", paramInt).apply();
    this.lbm.sendBroadcast(new Intent("pref_controller_mode"));
  }

  public void setFollowCruiseSpeed(int paramInt)
  {
    this.prefs.edit().putInt("pref_follow_cruise_speed", paramInt).apply();
  }

  public void setOrbitCruiseSpeed(int paramInt)
  {
    this.prefs.edit().putInt("pref_orbit_cruise_speed", paramInt).apply();
  }

  public void setPrefUuid(String paramString)
  {
    this.prefs.edit().putString("pref_uuid", paramString).apply();
  }

  public void setRunWizard(boolean paramBoolean)
  {
    this.prefs.edit().putBoolean("pref_run_wizard", paramBoolean).apply();
    this.lbm.sendBroadcast(new Intent("pref_run_wizard"));
  }

  public void setSelfieAltitudeUp(int paramInt)
  {
    this.prefs.edit().putInt("pref_selfie_altitude_up", paramInt).apply();
  }

  public void setSelfieCruiseSpeed(int paramInt)
  {
    this.prefs.edit().putInt("pref_selfie_cruise_speed", paramInt).apply();
  }

  public void setSelfieDistanceOut(int paramInt)
  {
    this.prefs.edit().putInt("pref_selfie_distance_out", paramInt).apply();
  }

  public void setUnitSystemType(int paramInt)
  {
    this.prefs.edit().putInt("pref_unit_system", paramInt).apply();
    this.lbm.sendBroadcast(new Intent("pref_unit_system"));
  }

  public void setUserVehicleLink(String paramString)
  {
    this.prefs.edit().putString("pref_user_vehicle_link", paramString).apply();
    this.lbm.sendBroadcast(new Intent("pref_user_vehicle_link"));
  }

  public void setVoiceAlertsVolume(int paramInt)
  {
    this.prefs.edit().putInt("pref_voice_alerts_volume", paramInt).apply();
    this.lbm.sendBroadcast(new Intent("pref_voice_alerts_volume"));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.AppPreferences
 * JD-Core Version:    0.6.2
 */