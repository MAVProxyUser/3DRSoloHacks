package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

public abstract interface zzs extends IInterface
{
  public abstract void onStreetViewPanoramaChange(StreetViewPanoramaLocation paramStreetViewPanoramaLocation)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzs
 * JD-Core Version:    0.6.2
 */