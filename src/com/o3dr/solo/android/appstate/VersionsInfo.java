package com.o3dr.solo.android.appstate;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import com.o3dr.solo.android.util.AppPreferences;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class VersionsInfo
{
  public static final String ACTION_CONTROLLER_VERSION_UPDATED = "com.o3dr.solo.android.action.CONTROLLER_VERSION_UPDATED";
  public static final String ACTION_PIXHAWK_VERSION_UPDATED = "com.o3dr.solo.android.action.PIXHAWK_VERSION_UPDATED";
  public static final String ACTION_STM32_VERSION_UPDATED = "com.o3dr.solo.android.action.STM32_VERSION_UPDATED";
  public static final String ACTION_VEHICLE_VERSION_UPDATED = "com.o3dr.solo.android.action.VEHICLE_VERSION_UPDATED";
  public static final String ACTION_VERSIONS_REFRESH_ENDED = "com.o3dr.solo.android.action.VERSIONS_REFRESH_ENDED";
  public static final String ACTION_VERSIONS_REFRESH_STARTED = "com.o3dr.solo.android.action.VERSIONS_REFRESH_STARTED";
  private static final String PREF_CONTROLLER_VERSION = "pref_controller_version";
  private static final String PREF_PIXHAWK_VERSION = "pref_pixhawk_version";
  private static final String PREF_STM32_VERSION = "pref_stm32_version";
  private static final String PREF_VEHICLE_VERSION = "pref_vehicle_version";
  private final AppPreferences appPrefs;
  private final AtomicReference<String> controllerVersion = new AtomicReference("");
  private final AtomicBoolean isRefreshingVersions = new AtomicBoolean(false);
  private final LocalBroadcastManager lbm;
  private final AtomicReference<String> pixhawkVersion = new AtomicReference("");
  private final AtomicReference<String> stm32Version = new AtomicReference("");
  private final AtomicReference<String> vehicleVersion = new AtomicReference("");

  VersionsInfo(Context paramContext)
  {
    this.lbm = LocalBroadcastManager.getInstance(paramContext);
    this.appPrefs = new AppPreferences(paramContext);
    restoreInstance(this.appPrefs);
  }

  private void restoreInstance(AppPreferences paramAppPreferences)
  {
    SharedPreferences localSharedPreferences = paramAppPreferences.getPrefs();
    setVehicleVersion(localSharedPreferences.getString("pref_vehicle_version", ""));
    setControllerVersion(localSharedPreferences.getString("pref_controller_version", ""));
    setPixhawkVersion(localSharedPreferences.getString("pref_pixhawk_version", ""));
    setStm32Version(localSharedPreferences.getString("pref_stm32_version", ""));
  }

  private void saveInstance(AppPreferences paramAppPreferences)
  {
    paramAppPreferences.getPrefs().edit().putString("pref_vehicle_version", getVehicleVersion()).putString("pref_controller_version", getControllerVersion()).putString("pref_pixhawk_version", getPixhawkVersion()).putString("pref_stm32_version", getStm32Version()).apply();
  }

  public boolean areVersionsBeingRefreshed()
  {
    return this.isRefreshingVersions.get();
  }

  public boolean areVersionsSet()
  {
    return (isVehicleVersionSet()) && (isControllerVersionSet()) && (isStm32VersionSet()) && (isPixhawkVersionSet());
  }

  public String getControllerVersion()
  {
    return (String)this.controllerVersion.get();
  }

  public String getPixhawkVersion()
  {
    return (String)this.pixhawkVersion.get();
  }

  public String getStm32Version()
  {
    return (String)this.stm32Version.get();
  }

  public String getVehicleVersion()
  {
    return (String)this.vehicleVersion.get();
  }

  public boolean isControllerVersionSet()
  {
    return !TextUtils.isEmpty((CharSequence)this.controllerVersion.get());
  }

  public boolean isPixhawkVersionSet()
  {
    return !TextUtils.isEmpty((CharSequence)this.pixhawkVersion.get());
  }

  public boolean isStm32VersionSet()
  {
    return !TextUtils.isEmpty((CharSequence)this.stm32Version.get());
  }

  public boolean isVehicleVersionSet()
  {
    return !TextUtils.isEmpty((CharSequence)this.vehicleVersion.get());
  }

  public void reset()
  {
    this.vehicleVersion.set("");
    this.controllerVersion.set("");
    this.pixhawkVersion.set("");
    this.stm32Version.set("");
  }

  public void restoreInstance()
  {
    restoreInstance(this.appPrefs);
  }

  public void saveInstance()
  {
    saveInstance(this.appPrefs);
  }

  public void setControllerVersion(String paramString)
  {
    this.controllerVersion.set(paramString);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.CONTROLLER_VERSION_UPDATED"));
  }

  public void setPixhawkVersion(String paramString)
  {
    this.pixhawkVersion.set(paramString);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.PIXHAWK_VERSION_UPDATED"));
  }

  public void setRefreshingVersionsFlag(boolean paramBoolean)
  {
    this.isRefreshingVersions.set(paramBoolean);
    if (paramBoolean)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VERSIONS_REFRESH_STARTED"));
      return;
    }
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VERSIONS_REFRESH_ENDED"));
  }

  public void setStm32Version(String paramString)
  {
    this.stm32Version.set(paramString);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.STM32_VERSION_UPDATED"));
  }

  public void setVehicleVersion(String paramString)
  {
    this.vehicleVersion.set(paramString);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_VERSION_UPDATED"));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.appstate.VersionsInfo
 * JD-Core Version:    0.6.2
 */