package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.internal.ICameraUpdateFactoryDelegate;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class CameraUpdateFactory
{
  private static ICameraUpdateFactoryDelegate zzatr;

  public static CameraUpdate newCameraPosition(CameraPosition paramCameraPosition)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().newCameraPosition(paramCameraPosition));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate newLatLng(LatLng paramLatLng)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().newLatLng(paramLatLng));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate newLatLngBounds(LatLngBounds paramLatLngBounds, int paramInt)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().newLatLngBounds(paramLatLngBounds, paramInt));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate newLatLngBounds(LatLngBounds paramLatLngBounds, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().newLatLngBoundsWithSize(paramLatLngBounds, paramInt1, paramInt2, paramInt3));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate newLatLngZoom(LatLng paramLatLng, float paramFloat)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().newLatLngZoom(paramLatLng, paramFloat));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate scrollBy(float paramFloat1, float paramFloat2)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().scrollBy(paramFloat1, paramFloat2));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate zoomBy(float paramFloat)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().zoomBy(paramFloat));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate zoomBy(float paramFloat, Point paramPoint)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().zoomByWithFocus(paramFloat, paramPoint.x, paramPoint.y));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate zoomIn()
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().zoomIn());
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate zoomOut()
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().zoomOut());
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static CameraUpdate zoomTo(float paramFloat)
  {
    try
    {
      CameraUpdate localCameraUpdate = new CameraUpdate(zztq().zoomTo(paramFloat));
      return localCameraUpdate;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static void zza(ICameraUpdateFactoryDelegate paramICameraUpdateFactoryDelegate)
  {
    zzatr = (ICameraUpdateFactoryDelegate)zzv.zzr(paramICameraUpdateFactoryDelegate);
  }

  private static ICameraUpdateFactoryDelegate zztq()
  {
    return (ICameraUpdateFactoryDelegate)zzv.zzb(zzatr, "CameraUpdateFactory is not initialized");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.CameraUpdateFactory
 * JD-Core Version:    0.6.2
 */