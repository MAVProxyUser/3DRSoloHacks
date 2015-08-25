package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.internal.IProjectionDelegate;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.VisibleRegion;

public final class Projection
{
  private final IProjectionDelegate zzaul;

  Projection(IProjectionDelegate paramIProjectionDelegate)
  {
    this.zzaul = paramIProjectionDelegate;
  }

  public LatLng fromScreenLocation(Point paramPoint)
  {
    try
    {
      LatLng localLatLng = this.zzaul.fromScreenLocation(zze.zzt(paramPoint));
      return localLatLng;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public VisibleRegion getVisibleRegion()
  {
    try
    {
      VisibleRegion localVisibleRegion = this.zzaul.getVisibleRegion();
      return localVisibleRegion;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public Point toScreenLocation(LatLng paramLatLng)
  {
    try
    {
      Point localPoint = (Point)zze.zzg(this.zzaul.toScreenLocation(paramLatLng));
      return localPoint;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.Projection
 * JD-Core Version:    0.6.2
 */