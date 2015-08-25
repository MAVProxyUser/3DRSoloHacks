package com.o3dr.solo.android.fragment.alerts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import com.o3dr.android.client.apis.drone.DroneStateApi;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import com.o3dr.solo.android.activity.SettingsActivity;
import com.o3dr.solo.android.activity.SupportActivity;
import com.o3dr.solo.android.activity.WifiSettingsAccess;
import com.o3dr.solo.android.fragment.base.BaseFragment;

public enum AlertType
{
  private final boolean autoDismiss;
  private final boolean canDismiss;
  private final int iconResId;
  private final int messageResId;
  private final OnAlertClickListener onClickListener;
  private final int origin;
  private final int priority;
  private final boolean showOnce;
  private final int soundType;
  private final int subMessageResId;

  static
  {
    PREFLIGHT_SOLO_COMPASS_CALIBRATION = new AlertType("PREFLIGHT_SOLO_COMPASS_CALIBRATION", 3, 0, 3, 2131099738, 2131100055, 2130837632, new OnAlertClickListener()
    {
      public void onAlertClick(BaseFragment paramAnonymousBaseFragment)
      {
        AlertType.launchCompassCalibration(paramAnonymousBaseFragment);
      }
    });
    PREFLIGHT_MAG_INTERFERENCE = new AlertType("PREFLIGHT_MAG_INTERFERENCE", 4, 0, 2, 2131099729, 2131099703, 2130837632, null);
    PREFLIGHT_SOLO_LEVEL_CALIBRATION = new AlertType("PREFLIGHT_SOLO_LEVEL_CALIBRATION", 5, 0, 3, 2131099739, 2131100055, 2130837889, new OnAlertClickListener()
    {
      public void onAlertClick(BaseFragment paramAnonymousBaseFragment)
      {
        AlertType.launchAccelCalibration(paramAnonymousBaseFragment);
      }
    });
    PREFLIGHT_SOLO_MUST_BE_LEVEL = new AlertType("PREFLIGHT_SOLO_MUST_BE_LEVEL", 6, 0, 2, 2131099740, 0, 2130837889);
    PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE = new AlertType("PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE", 7, 3, 1, 2131099742, 2131099737, 2130837791, 2131034121, null);
    PREFLIGHT_BATTERY_TOO_LOW = new AlertType("PREFLIGHT_BATTERY_TOO_LOW", 8, 1, 3, 2131099732, 2131099998, 2130837611, 2131034116, true, false, true, null);
    PREFLIGHT_SOLO_NEEDS_SERVICE = new AlertType("PREFLIGHT_SOLO_NEEDS_SERVICE", 9, 0, 3, 2131099741, 2131100056, 2130837899, new OnAlertClickListener()
    {
      public void onAlertClick(BaseFragment paramAnonymousBaseFragment)
      {
        paramAnonymousBaseFragment.startActivity(new Intent(paramAnonymousBaseFragment.getContext(), SupportActivity.class));
      }
    });
    PREFLIGHT_CRASH_DETECTED = new AlertType("PREFLIGHT_CRASH_DETECTED", 10, 0, 3, 2131099733, 2131100056, 2130837899, 2131034120, new OnAlertClickListener()
    {
      public void onAlertClick(BaseFragment paramAnonymousBaseFragment)
      {
        paramAnonymousBaseFragment.startActivity(new Intent(paramAnonymousBaseFragment.getContext(), SupportActivity.class));
      }
    });
    TAKEOFF_FOR_SHOT = new AlertType("TAKEOFF_FOR_SHOT", 11, 3, 2, 2131100054, 0, 2130837930);
    PREFLIGHT_CALIBRATION_FAILED = new AlertType("PREFLIGHT_CALIBRATION_FAILED", 12, 0, 2, 2131099863, 2131099746, 2130837889, 2131034120, new OnAlertClickListener()
    {
      public void onAlertClick(BaseFragment paramAnonymousBaseFragment)
      {
        AlertType.launchCompassCalibration(paramAnonymousBaseFragment);
      }
    });
    PREFLIGHT_CALIBRATING_COMPASS = new AlertType("PREFLIGHT_CALIBRATING_COMPASS", 13, 0, 2, 2131099704, 2131099730, 2130837632);
    PREFLIGHT_THROTTLE_TOO_HIGH = new AlertType("PREFLIGHT_THROTTLE_TOO_HIGH", 14, 0, 2, 2131099748, 0, 2130837930);
    PREFLIGHT_CALIBRATING_SENSORS = new AlertType("PREFLIGHT_CALIBRATING_SENSORS", 15, 0, 2, 2131099705, 0, 2130837791);
    INFLIGHT_FLIGHT_BATTERY_25P = new AlertType("INFLIGHT_FLIGHT_BATTERY_25P", 16, 1, 2, 2131099721, 2131100001, 2130837616, 2131034116, true, true, true, new OnAlertClickListener()
    {
      public void onAlertClick(BaseFragment paramAnonymousBaseFragment)
      {
        if (paramAnonymousBaseFragment.isDroneConnected())
          return;
        DroneStateApi.setVehicleMode(paramAnonymousBaseFragment.getDrone(), VehicleMode.COPTER_RTL);
      }
    });
    INFLIGHT_CONTROL_BATTERY_5P = new AlertType("INFLIGHT_CONTROL_BATTERY_5P", 17, 2, 2, 2131099718, 2131099985, 2130837611, 2131034115, true, false, true, null);
    INFLIGHT_MAX_ALTITUDE = new AlertType("INFLIGHT_MAX_ALTITUDE", 18, 1, 2, 2131099727, 0, 2130837828, 2131034112, true, true, true, null);
    INFLIGHT_FLIGHT_BATTERY_10P = new AlertType("INFLIGHT_FLIGHT_BATTERY_10P", 19, 1, 3, 2131099720, 2131100001, 2130837613, 2131034116, true, false, true, null);
    INFLIGHT_FLIGHT_BATTERY_3P = new AlertType("INFLIGHT_FLIGHT_BATTERY_3P", 20, 1, 3, 2131099722, 2131099752, 2130837611, 2131034116, true, false, true, null);
    INFLIGHT_GPS_LOST = new AlertType("INFLIGHT_GPS_LOST", 21, 0, 3, 2131099725, 2131100053, 2130837791, 2131034121, null);
    INFLIGHT_GPS_LOCK = new AlertType("INFLIGHT_GPS_LOCK", 22, 0, 0, 2131099724, 2131100003, 2130837791, 2131034121, true, true, false, null);
    INFLIGHT_CONTROLLER_SIGNAL_LOST = new AlertType("INFLIGHT_CONTROLLER_SIGNAL_LOST", 23, 0, 3, 2131099719, 2131100002, 2130837987, 2131034128, null);
    INFLIGHT_CONTROLLER_SIGNAL_RECOVERY = new AlertType("INFLIGHT_CONTROLLER_SIGNAL_RECOVERY", 24, 3, 0, 2131099744, 2131099743, 0, true);
    INFLIGHT_CONTROLLER_SIGNAL_LOST_NO_GPS = new AlertType("INFLIGHT_CONTROLLER_SIGNAL_LOST_NO_GPS", 25, 0, 3, 2131099719, 2131099706, 2130837987, 2131034128, null);
    INFLIGHT_CONTROL_BATTERY_0P_RETURNING = new AlertType("INFLIGHT_CONTROL_BATTERY_0P_RETURNING", 26, 2, 3, 2131099715, 2131100002, 2130837611, 2131034128, true, false, true, null);
    INFLIGHT_CONTROL_BATTERY_0P_LANDING = new AlertType("INFLIGHT_CONTROL_BATTERY_0P_LANDING", 27, 2, 3, 2131099715, 2131099752, 2130837611, 2131034120, true, false, true, null);
    FOLLOW_INACCURATE_LOCATION = new AlertType("FOLLOW_INACCURATE_LOCATION", 28, 3, 2, 2131099708, 0, 2130837791, 2131034121, false, false, false, null);
    FOLLOW_UNAVAILABLE_LOCATION = new AlertType("FOLLOW_UNAVAILABLE_LOCATION", 29, 3, 3, 2131099709, 2131099707, 2130837791, 2131034112, true, true, false, null);
    POOR_CONTROLLER_CONNECTION = new AlertType("POOR_CONTROLLER_CONNECTION", 30, 3, 3, 2131099731, 2131099747, 2130837987, new OnAlertClickListener()
    {
      public void onAlertClick(BaseFragment paramAnonymousBaseFragment)
      {
        paramAnonymousBaseFragment.startActivity(new Intent(paramAnonymousBaseFragment.getContext(), WifiSettingsAccess.class).addFlags(268435456));
      }
    });
    POOR_CONNECTION_TO_SOLO = new AlertType("POOR_CONNECTION_TO_SOLO", 31, 3, 2, 2131099728, 0, 2130837987, 2131034112, true, false, true, null);
    AlertType[] arrayOfAlertType = new AlertType[32];
    arrayOfAlertType[0] = PREFLIGHT_READY_TO_ARM;
    arrayOfAlertType[1] = PREFLIGHT_READY_TO_TAKEOFF;
    arrayOfAlertType[2] = PREFLIGHT_WAITING_FOR_GPS;
    arrayOfAlertType[3] = PREFLIGHT_SOLO_COMPASS_CALIBRATION;
    arrayOfAlertType[4] = PREFLIGHT_MAG_INTERFERENCE;
    arrayOfAlertType[5] = PREFLIGHT_SOLO_LEVEL_CALIBRATION;
    arrayOfAlertType[6] = PREFLIGHT_SOLO_MUST_BE_LEVEL;
    arrayOfAlertType[7] = PREFLIGHT_SHOT_AND_RETURN_NOT_AVAILABLE;
    arrayOfAlertType[8] = PREFLIGHT_BATTERY_TOO_LOW;
    arrayOfAlertType[9] = PREFLIGHT_SOLO_NEEDS_SERVICE;
    arrayOfAlertType[10] = PREFLIGHT_CRASH_DETECTED;
    arrayOfAlertType[11] = TAKEOFF_FOR_SHOT;
    arrayOfAlertType[12] = PREFLIGHT_CALIBRATION_FAILED;
    arrayOfAlertType[13] = PREFLIGHT_CALIBRATING_COMPASS;
    arrayOfAlertType[14] = PREFLIGHT_THROTTLE_TOO_HIGH;
    arrayOfAlertType[15] = PREFLIGHT_CALIBRATING_SENSORS;
    arrayOfAlertType[16] = INFLIGHT_FLIGHT_BATTERY_25P;
    arrayOfAlertType[17] = INFLIGHT_CONTROL_BATTERY_5P;
    arrayOfAlertType[18] = INFLIGHT_MAX_ALTITUDE;
    arrayOfAlertType[19] = INFLIGHT_FLIGHT_BATTERY_10P;
    arrayOfAlertType[20] = INFLIGHT_FLIGHT_BATTERY_3P;
    arrayOfAlertType[21] = INFLIGHT_GPS_LOST;
    arrayOfAlertType[22] = INFLIGHT_GPS_LOCK;
    arrayOfAlertType[23] = INFLIGHT_CONTROLLER_SIGNAL_LOST;
    arrayOfAlertType[24] = INFLIGHT_CONTROLLER_SIGNAL_RECOVERY;
    arrayOfAlertType[25] = INFLIGHT_CONTROLLER_SIGNAL_LOST_NO_GPS;
    arrayOfAlertType[26] = INFLIGHT_CONTROL_BATTERY_0P_RETURNING;
    arrayOfAlertType[27] = INFLIGHT_CONTROL_BATTERY_0P_LANDING;
    arrayOfAlertType[28] = FOLLOW_INACCURATE_LOCATION;
    arrayOfAlertType[29] = FOLLOW_UNAVAILABLE_LOCATION;
    arrayOfAlertType[30] = POOR_CONTROLLER_CONNECTION;
    arrayOfAlertType[31] = POOR_CONNECTION_TO_SOLO;
  }

  private AlertType(int paramInt1, int paramInt2, @StringRes int paramInt3, @StringRes int paramInt4, @DrawableRes int paramInt5)
  {
    this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, false);
  }

  private AlertType(int paramInt1, int paramInt2, @StringRes int paramInt3, @StringRes int paramInt4, @DrawableRes int paramInt5, int paramInt6, OnAlertClickListener paramOnAlertClickListener)
  {
    this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, true, false, false, paramOnAlertClickListener);
  }

  private AlertType(int paramInt1, int paramInt2, @StringRes int paramInt3, @StringRes int paramInt4, @DrawableRes int paramInt5, int paramInt6, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, OnAlertClickListener paramOnAlertClickListener)
  {
    this.origin = paramInt1;
    this.priority = paramInt2;
    this.messageResId = paramInt3;
    this.subMessageResId = paramInt4;
    this.iconResId = paramInt5;
    this.soundType = paramInt6;
    this.canDismiss = paramBoolean1;
    this.autoDismiss = paramBoolean2;
    this.showOnce = paramBoolean3;
    this.onClickListener = paramOnAlertClickListener;
  }

  private AlertType(int paramInt1, int paramInt2, @StringRes int paramInt3, @StringRes int paramInt4, @DrawableRes int paramInt5, OnAlertClickListener paramOnAlertClickListener)
  {
    this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 2131034112, true, false, false, paramOnAlertClickListener);
  }

  private AlertType(int paramInt1, int paramInt2, @StringRes int paramInt3, @StringRes int paramInt4, @DrawableRes int paramInt5, boolean paramBoolean)
  {
    this(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, 2131034112, true, paramBoolean, false, null);
  }

  protected static void launchAccelCalibration(BaseFragment paramBaseFragment)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("extra_sub_settings_selected_pane_id", 2131493295);
    localBundle.putBoolean("extra_sub_settings_open_detail_pane", true);
    paramBaseFragment.startActivity(new Intent(paramBaseFragment.getContext(), SettingsActivity.class).putExtra("extra_settings_selected_pane_id", 2131493154).putExtra("extra_settings_open_detail_pane", true).putExtra("extra_settings_arguments", localBundle));
  }

  protected static void launchCompassCalibration(BaseFragment paramBaseFragment)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("extra_sub_settings_selected_pane_id", 2131493296);
    localBundle.putBoolean("extra_sub_settings_open_detail_pane", true);
    paramBaseFragment.startActivity(new Intent(paramBaseFragment.getContext(), SettingsActivity.class).putExtra("extra_settings_selected_pane_id", 2131493154).putExtra("extra_settings_open_detail_pane", true).putExtra("extra_settings_arguments", localBundle));
  }

  public boolean canDismiss()
  {
    return this.canDismiss;
  }

  public int getIconResId()
  {
    return this.iconResId;
  }

  public int getMessageResId()
  {
    return this.messageResId;
  }

  public OnAlertClickListener getOnClickListener()
  {
    return this.onClickListener;
  }

  public int getOrigin()
  {
    return this.origin;
  }

  public int getPriority()
  {
    return this.priority;
  }

  public int getSoundType()
  {
    return this.soundType;
  }

  public int getSubMessageResId()
  {
    return this.subMessageResId;
  }

  public boolean isAutoDismiss()
  {
    return this.autoDismiss;
  }

  public boolean isShownOnce()
  {
    return this.showOnce;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.alerts.AlertType
 * JD-Core Version:    0.6.2
 */