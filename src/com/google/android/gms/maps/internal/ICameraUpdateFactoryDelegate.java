package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

public abstract interface ICameraUpdateFactoryDelegate extends IInterface
{
  public abstract zzd newCameraPosition(CameraPosition paramCameraPosition)
    throws RemoteException;

  public abstract zzd newLatLng(LatLng paramLatLng)
    throws RemoteException;

  public abstract zzd newLatLngBounds(LatLngBounds paramLatLngBounds, int paramInt)
    throws RemoteException;

  public abstract zzd newLatLngBoundsWithSize(LatLngBounds paramLatLngBounds, int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;

  public abstract zzd newLatLngZoom(LatLng paramLatLng, float paramFloat)
    throws RemoteException;

  public abstract zzd scrollBy(float paramFloat1, float paramFloat2)
    throws RemoteException;

  public abstract zzd zoomBy(float paramFloat)
    throws RemoteException;

  public abstract zzd zoomByWithFocus(float paramFloat, int paramInt1, int paramInt2)
    throws RemoteException;

  public abstract zzd zoomIn()
    throws RemoteException;

  public abstract zzd zoomOut()
    throws RemoteException;

  public abstract zzd zoomTo(float paramFloat)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate
 * JD-Core Version:    0.6.2
 */