package com.o3dr.android.client.apis.drone;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.connection.ConnectionParameter;
import com.o3dr.services.android.lib.model.action.Action;

public class ConnectApi
{
  public static boolean connect(Drone paramDrone, ConnectionParameter paramConnectionParameter)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("extra_connect_parameter", paramConnectionParameter);
    return paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.CONNECT", localBundle));
  }

  public static boolean disconnect(Drone paramDrone)
  {
    return paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.DISCONNECT"));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.drone.ConnectApi
 * JD-Core Version:    0.6.2
 */