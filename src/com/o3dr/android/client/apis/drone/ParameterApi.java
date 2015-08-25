package com.o3dr.android.client.apis.drone;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.Parameters;
import com.o3dr.services.android.lib.model.action.Action;

public class ParameterApi
{
  public static void refreshParameters(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.REFRESH_PARAMETERS"));
  }

  public static void writeParameters(Drone paramDrone, Parameters paramParameters)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("extra_parameters", paramParameters);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.WRITE_PARAMETERS", localBundle));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.drone.ParameterApi
 * JD-Core Version:    0.6.2
 */