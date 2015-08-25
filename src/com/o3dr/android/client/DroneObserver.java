package com.o3dr.android.client;

import android.os.Bundle;
import android.os.RemoteException;
import com.o3dr.services.android.lib.model.IObserver.Stub;

final class DroneObserver extends IObserver.Stub
{
  private final Drone drone;

  public DroneObserver(Drone paramDrone)
  {
    this.drone = paramDrone;
  }

  public void onAttributeUpdated(String paramString, Bundle paramBundle)
    throws RemoteException
  {
    this.drone.notifyAttributeUpdated(paramString, paramBundle);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.DroneObserver
 * JD-Core Version:    0.6.2
 */