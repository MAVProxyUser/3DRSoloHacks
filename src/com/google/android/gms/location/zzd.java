package com.google.android.gms.location;

import android.location.Location;
import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzd extends IInterface
{
  public abstract void onLocationChanged(Location paramLocation)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.zzd
 * JD-Core Version:    0.6.2
 */