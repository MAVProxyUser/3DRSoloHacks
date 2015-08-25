package com.o3dr.services.android.lib.model;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;

public abstract interface IDroidPlannerServices extends IInterface
{
  public abstract int getApiVersionCode()
    throws RemoteException;

  public abstract Bundle[] getConnectedApps(String paramString)
    throws RemoteException;

  public abstract int getServiceVersionCode()
    throws RemoteException;

  public abstract IDroneApi registerDroneApi(IApiListener paramIApiListener, String paramString)
    throws RemoteException;

  public abstract void releaseDroneApi(IDroneApi paramIDroneApi)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.IDroidPlannerServices
 * JD-Core Version:    0.6.2
 */