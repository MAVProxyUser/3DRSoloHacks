package com.o3dr.android.client.apis.drone.camera;

import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.model.action.Action;

public class CameraApi
{
  public static boolean startVideoRecording(Drone paramDrone)
  {
    return (paramDrone != null) && (paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.camera.START_VIDEO_RECORDING")));
  }

  public static boolean stopVideoRecording(Drone paramDrone)
  {
    return (paramDrone != null) && (paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.camera.STOP_VIDEO_RECORDING")));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.drone.camera.CameraApi
 * JD-Core Version:    0.6.2
 */