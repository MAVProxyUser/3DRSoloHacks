package com.o3dr.android.client.apis.gcs;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.model.action.Action;

public class CalibrationApi
{
  public static void acceptMagnetometerCalibration(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.ACCEPT_MAGNETOMETER_CALIBRATION"));
  }

  public static void cancelMagnetometerCalibration(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.CANCEL_MAGNETOMETER_CALIBRATION"));
  }

  public static void sendIMUAck(Drone paramDrone, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putInt("extra_step", paramInt);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SEND_IMU_CALIBRATION_ACK", localBundle));
  }

  public static void startIMUCalibration(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.START_IMU_CALIBRATION"));
  }

  public static void startMagnetometerCalibration(Drone paramDrone)
  {
    startMagnetometerCalibration(paramDrone, false, true, 0);
  }

  public static void startMagnetometerCalibration(Drone paramDrone, boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("extra_retry_on_failure", paramBoolean1);
    localBundle.putBoolean("extra_save_automatically", paramBoolean2);
    localBundle.putInt("extra_start_delay", paramInt);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.START_MAGNETOMETER_CALIBRATION", localBundle));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.gcs.CalibrationApi
 * JD-Core Version:    0.6.2
 */