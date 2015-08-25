package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public abstract interface zzu extends IInterface
{
  public abstract void onStreetViewPanoramaLongClick(StreetViewPanoramaOrientation paramStreetViewPanoramaOrientation)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzu
 * JD-Core Version:    0.6.2
 */