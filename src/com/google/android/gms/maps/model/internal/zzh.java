package com.google.android.gms.maps.model.internal;

import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzh extends IInterface
{
  public abstract void activate()
    throws RemoteException;

  public abstract String getName()
    throws RemoteException;

  public abstract String getShortName()
    throws RemoteException;

  public abstract int hashCodeRemote()
    throws RemoteException;

  public abstract boolean zza(zzh paramzzh)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzh
 * JD-Core Version:    0.6.2
 */