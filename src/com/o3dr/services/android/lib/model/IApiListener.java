package com.o3dr.services.android.lib.model;

import android.os.IInterface;
import android.os.RemoteException;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;

public abstract interface IApiListener extends IInterface
{
  public abstract int getApiVersionCode()
    throws RemoteException;

  public abstract int getClientVersionCode()
    throws RemoteException;

  public abstract void onConnectionFailed(ConnectionResult paramConnectionResult)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.IApiListener
 * JD-Core Version:    0.6.2
 */