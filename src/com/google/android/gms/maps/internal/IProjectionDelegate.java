package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;

public abstract interface IProjectionDelegate extends IInterface
{
  public abstract LatLng fromScreenLocation(zzd paramzzd)
    throws RemoteException;

  public abstract LatLng fromScreenLocation2(Point paramPoint)
    throws RemoteException;

  public abstract VisibleRegion getVisibleRegion()
    throws RemoteException;

  public abstract zzd toScreenLocation(LatLng paramLatLng)
    throws RemoteException;

  public abstract Point toScreenLocation2(LatLng paramLatLng)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.IProjectionDelegate
 * JD-Core Version:    0.6.2
 */