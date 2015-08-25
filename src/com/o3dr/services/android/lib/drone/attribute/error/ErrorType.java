package com.o3dr.services.android.lib.drone.attribute.error;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.o3dr.android.client.R.string;

public enum ErrorType
  implements Parcelable
{
  public static final Parcelable.Creator<ErrorType> CREATOR = new Parcelable.Creator()
  {
    public ErrorType createFromParcel(Parcel paramAnonymousParcel)
    {
      return ErrorType.valueOf(paramAnonymousParcel.readString());
    }

    public ErrorType[] newArray(int paramAnonymousInt)
    {
      return new ErrorType[paramAnonymousInt];
    }
  };
  private final int labelResId;

  static
  {
    ARM_THROTTLE_BELOW_FAILSAFE = new ErrorType("ARM_THROTTLE_BELOW_FAILSAFE", 1, R.string.error_throttle_below_failsafe);
    ARM_GYRO_CALIBRATION_FAILED = new ErrorType("ARM_GYRO_CALIBRATION_FAILED", 2, R.string.error_gyro_calibration_failed);
    ARM_MODE_NOT_ARMABLE = new ErrorType("ARM_MODE_NOT_ARMABLE", 3, R.string.error_mode_not_armable);
    ARM_ROTOR_NOT_SPINNING = new ErrorType("ARM_ROTOR_NOT_SPINNING", 4, R.string.error_rotor_not_spinning);
    ARM_LEANING = new ErrorType("ARM_LEANING", 5, R.string.error_vehicle_leaning);
    ARM_THROTTLE_TOO_HIGH = new ErrorType("ARM_THROTTLE_TOO_HIGH", 6, R.string.error_throttle_too_high);
    ARM_SAFETY_SWITCH = new ErrorType("ARM_SAFETY_SWITCH", 7, R.string.error_safety_switch);
    ARM_COMPASS_CALIBRATION_RUNNING = new ErrorType("ARM_COMPASS_CALIBRATION_RUNNING", 8, R.string.error_compass_calibration_running);
    PRE_ARM_RC_NOT_CALIBRATED = new ErrorType("PRE_ARM_RC_NOT_CALIBRATED", 9, R.string.error_rc_not_calibrated);
    PRE_ARM_BAROMETER_NOT_HEALTHY = new ErrorType("PRE_ARM_BAROMETER_NOT_HEALTHY", 10, R.string.error_barometer_not_healthy);
    PRE_ARM_COMPASS_NOT_HEALTHY = new ErrorType("PRE_ARM_COMPASS_NOT_HEALTHY", 11, R.string.error_compass_not_healthy);
    PRE_ARM_COMPASS_NOT_CALIBRATED = new ErrorType("PRE_ARM_COMPASS_NOT_CALIBRATED", 12, R.string.error_compass_not_calibrated);
    PRE_ARM_COMPASS_OFFSETS_TOO_HIGH = new ErrorType("PRE_ARM_COMPASS_OFFSETS_TOO_HIGH", 13, R.string.error_compass_offsets_too_high);
    PRE_ARM_CHECK_MAGNETIC_FIELD = new ErrorType("PRE_ARM_CHECK_MAGNETIC_FIELD", 14, R.string.error_check_magnetic_field);
    PRE_ARM_INCONSISTENT_COMPASSES = new ErrorType("PRE_ARM_INCONSISTENT_COMPASSES", 15, R.string.error_inconsistent_compass);
    PRE_ARM_CHECK_FENCE = new ErrorType("PRE_ARM_CHECK_FENCE", 16, R.string.error_check_geo_fence);
    PRE_ARM_INS_NOT_CALIBRATED = new ErrorType("PRE_ARM_INS_NOT_CALIBRATED", 17, R.string.error_ins_not_calibrated);
    PRE_ARM_ACCELEROMETERS_NOT_HEALTHY = new ErrorType("PRE_ARM_ACCELEROMETERS_NOT_HEALTHY", 18, R.string.error_accelerometers_not_healthy);
    PRE_ARM_INCONSISTENT_ACCELEROMETERS = new ErrorType("PRE_ARM_INCONSISTENT_ACCELEROMETERS", 19, R.string.error_inconsistent_accelerometers);
    PRE_ARM_GYROS_NOT_HEALTHY = new ErrorType("PRE_ARM_GYROS_NOT_HEALTHY", 20, R.string.error_gyros_not_healthy);
    PRE_ARM_INCONSISTENT_GYROS = new ErrorType("PRE_ARM_INCONSISTENT_GYROS", 21, R.string.error_inconsistent_gyros);
    PRE_ARM_CHECK_BOARD_VOLTAGE = new ErrorType("PRE_ARM_CHECK_BOARD_VOLTAGE", 22, R.string.error_check_board_voltage);
    PRE_ARM_DUPLICATE_AUX_SWITCH_OPTIONS = new ErrorType("PRE_ARM_DUPLICATE_AUX_SWITCH_OPTIONS", 23, R.string.error_duplicate_aux_switch_options);
    PRE_ARM_CHECK_FAILSAFE_THRESHOLD_VALUE = new ErrorType("PRE_ARM_CHECK_FAILSAFE_THRESHOLD_VALUE", 24, R.string.error_check_failsafe_threshold);
    PRE_ARM_CHECK_ANGLE_MAX = new ErrorType("PRE_ARM_CHECK_ANGLE_MAX", 25, R.string.error_check_angle_max);
    PRE_ARM_ACRO_BAL_ROLL_PITCH = new ErrorType("PRE_ARM_ACRO_BAL_ROLL_PITCH", 26, R.string.error_acro_bal_roll_pitch);
    PRE_ARM_NEED_GPS_LOCK = new ErrorType("PRE_ARM_NEED_GPS_LOCK", 27, R.string.error_need_gps_lock);
    PRE_ARM_EKF_HOME_VARIANCE = new ErrorType("PRE_ARM_EKF_HOME_VARIANCE", 28, R.string.error_ekf_home_variance);
    PRE_ARM_HIGH_GPS_HDOP = new ErrorType("PRE_ARM_HIGH_GPS_HDOP", 29, R.string.error_high_gps_hdop);
    PRE_ARM_GPS_GLITCH = new ErrorType("PRE_ARM_GPS_GLITCH", 30, R.string.error_gps_glitch);
    WAITING_FOR_NAVIGATION_ALIGNMENT = new ErrorType("WAITING_FOR_NAVIGATION_ALIGNMENT", 31, R.string.error_waiting_for_navigation_alignment);
    ALTITUDE_DISPARITY = new ErrorType("ALTITUDE_DISPARITY", 32, R.string.error_altitude_disparity);
    LOW_BATTERY = new ErrorType("LOW_BATTERY", 33, R.string.error_low_battery);
    AUTO_TUNE_FAILED = new ErrorType("AUTO_TUNE_FAILED", 34, R.string.error_auto_tune_failed);
    CRASH_DISARMING = new ErrorType("CRASH_DISARMING", 35, R.string.error_crash);
    PARACHUTE_TOO_LOW = new ErrorType("PARACHUTE_TOO_LOW", 36, R.string.error_parachute_too_low);
    EKF_VARIANCE = new ErrorType("EKF_VARIANCE", 37, R.string.error_ekf_variance);
    NO_DATAFLASH_INSERTED = new ErrorType("NO_DATAFLASH_INSERTED", 38, R.string.error_no_dataflash);
    RC_FAILSAFE = new ErrorType("RC_FAILSAFE", 39, R.string.error_rc_failsafe);
    ErrorType[] arrayOfErrorType = new ErrorType[40];
    arrayOfErrorType[0] = NO_ERROR;
    arrayOfErrorType[1] = ARM_THROTTLE_BELOW_FAILSAFE;
    arrayOfErrorType[2] = ARM_GYRO_CALIBRATION_FAILED;
    arrayOfErrorType[3] = ARM_MODE_NOT_ARMABLE;
    arrayOfErrorType[4] = ARM_ROTOR_NOT_SPINNING;
    arrayOfErrorType[5] = ARM_LEANING;
    arrayOfErrorType[6] = ARM_THROTTLE_TOO_HIGH;
    arrayOfErrorType[7] = ARM_SAFETY_SWITCH;
    arrayOfErrorType[8] = ARM_COMPASS_CALIBRATION_RUNNING;
    arrayOfErrorType[9] = PRE_ARM_RC_NOT_CALIBRATED;
    arrayOfErrorType[10] = PRE_ARM_BAROMETER_NOT_HEALTHY;
    arrayOfErrorType[11] = PRE_ARM_COMPASS_NOT_HEALTHY;
    arrayOfErrorType[12] = PRE_ARM_COMPASS_NOT_CALIBRATED;
    arrayOfErrorType[13] = PRE_ARM_COMPASS_OFFSETS_TOO_HIGH;
    arrayOfErrorType[14] = PRE_ARM_CHECK_MAGNETIC_FIELD;
    arrayOfErrorType[15] = PRE_ARM_INCONSISTENT_COMPASSES;
    arrayOfErrorType[16] = PRE_ARM_CHECK_FENCE;
    arrayOfErrorType[17] = PRE_ARM_INS_NOT_CALIBRATED;
    arrayOfErrorType[18] = PRE_ARM_ACCELEROMETERS_NOT_HEALTHY;
    arrayOfErrorType[19] = PRE_ARM_INCONSISTENT_ACCELEROMETERS;
    arrayOfErrorType[20] = PRE_ARM_GYROS_NOT_HEALTHY;
    arrayOfErrorType[21] = PRE_ARM_INCONSISTENT_GYROS;
    arrayOfErrorType[22] = PRE_ARM_CHECK_BOARD_VOLTAGE;
    arrayOfErrorType[23] = PRE_ARM_DUPLICATE_AUX_SWITCH_OPTIONS;
    arrayOfErrorType[24] = PRE_ARM_CHECK_FAILSAFE_THRESHOLD_VALUE;
    arrayOfErrorType[25] = PRE_ARM_CHECK_ANGLE_MAX;
    arrayOfErrorType[26] = PRE_ARM_ACRO_BAL_ROLL_PITCH;
    arrayOfErrorType[27] = PRE_ARM_NEED_GPS_LOCK;
    arrayOfErrorType[28] = PRE_ARM_EKF_HOME_VARIANCE;
    arrayOfErrorType[29] = PRE_ARM_HIGH_GPS_HDOP;
    arrayOfErrorType[30] = PRE_ARM_GPS_GLITCH;
    arrayOfErrorType[31] = WAITING_FOR_NAVIGATION_ALIGNMENT;
    arrayOfErrorType[32] = ALTITUDE_DISPARITY;
    arrayOfErrorType[33] = LOW_BATTERY;
    arrayOfErrorType[34] = AUTO_TUNE_FAILED;
    arrayOfErrorType[35] = CRASH_DISARMING;
    arrayOfErrorType[36] = PARACHUTE_TOO_LOW;
    arrayOfErrorType[37] = EKF_VARIANCE;
    arrayOfErrorType[38] = NO_DATAFLASH_INSERTED;
    arrayOfErrorType[39] = RC_FAILSAFE;
  }

  private ErrorType(int paramInt)
  {
    this.labelResId = paramInt;
  }

  public static ErrorType getErrorById(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    return valueOf(paramString);
  }

  public int describeContents()
  {
    return 0;
  }

  public CharSequence getLabel(Context paramContext)
  {
    return paramContext.getText(this.labelResId);
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(name());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.attribute.error.ErrorType
 * JD-Core Version:    0.6.2
 */