package com.google.android.gms.location;

import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzc extends IInterface
{
  public abstract void onLocationAvailability(LocationAvailability paramLocationAvailability)
    throws RemoteException;

  public abstract void onLocationResult(LocationResult paramLocationResult)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.zzc
 * JD-Core Version:    0.6.2
 */