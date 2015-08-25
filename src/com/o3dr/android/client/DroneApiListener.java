package com.o3dr.android.client;

import android.os.RemoteException;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;
import com.o3dr.services.android.lib.model.IApiListener.Stub;

public class DroneApiListener extends IApiListener.Stub
{
  private final Drone drone;

  public DroneApiListener(Drone paramDrone)
  {
    this.drone = paramDrone;
  }

  public int getApiVersionCode()
  {
    return 20214;
  }

  public int getClientVersionCode()
    throws RemoteException
  {
    return 20329;
  }

  public void onConnectionFailed(ConnectionResult paramConnectionResult)
    throws RemoteException
  {
    this.drone.notifyDroneConnectionFailed(paramConnectionResult);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.DroneApiListener
 * JD-Core Version:    0.6.2
 */