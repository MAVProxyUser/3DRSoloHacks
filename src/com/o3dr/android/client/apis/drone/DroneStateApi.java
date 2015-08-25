package com.o3dr.android.client.apis.drone;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import com.o3dr.services.android.lib.model.action.Action;

public class DroneStateApi
{
  public static void arm(Drone paramDrone, boolean paramBoolean)
  {
    arm(paramDrone, paramBoolean, false);
  }

  public static void arm(Drone paramDrone, boolean paramBoolean1, boolean paramBoolean2)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("extra_arm", paramBoolean1);
    localBundle.putBoolean("extra_emergency_disarm", paramBoolean2);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.ARM", localBundle));
  }

  public static void setVehicleMode(Drone paramDrone, VehicleMode paramVehicleMode)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("extra_vehicle_mode", paramVehicleMode);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SET_VEHICLE_MODE", localBundle));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.drone.DroneStateApi
 * JD-Core Version:    0.6.2
 */