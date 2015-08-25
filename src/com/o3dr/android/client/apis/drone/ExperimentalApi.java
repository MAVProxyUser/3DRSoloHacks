package com.o3dr.android.client.apis.drone;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.mavlink.MavlinkMessageWrapper;
import com.o3dr.services.android.lib.model.action.Action;

public class ExperimentalApi
{
  public static void epmCommand(Drone paramDrone, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("com.o3dr.services.androidextra_epm_release", paramBoolean);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.EPM_COMMAND", localBundle));
  }

  public static void sendMavlinkMessage(Drone paramDrone, MavlinkMessageWrapper paramMavlinkMessageWrapper)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("extra_mavlink_message", paramMavlinkMessageWrapper);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SEND_MAVLINK_MESSAGE", localBundle));
  }

  public static void setRelay(Drone paramDrone, int paramInt, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle(2);
    localBundle.putInt("extra_relay_number", paramInt);
    localBundle.putBoolean("extra_is_relay_on", paramBoolean);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SET_RELAY", localBundle));
  }

  public static void setServo(Drone paramDrone, int paramInt1, int paramInt2)
  {
    Bundle localBundle = new Bundle(2);
    localBundle.putInt("extra_servo_channel", paramInt1);
    localBundle.putInt("extra_servo_PWM", paramInt2);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SET_SERVO", localBundle));
  }

  public static void triggerCamera(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.TRIGGER_CAMERA"));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.drone.ExperimentalApi
 * JD-Core Version:    0.6.2
 */