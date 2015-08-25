package com.o3dr.android.client.interfaces;

import android.os.Bundle;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;

public abstract interface DroneListener
{
  public abstract void onDroneConnectionFailed(ConnectionResult paramConnectionResult);

  public abstract void onDroneEvent(String paramString, Bundle paramBundle);

  public abstract void onDroneServiceInterrupted(String paramString);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.interfaces.DroneListener
 * JD-Core Version:    0.6.2
 */