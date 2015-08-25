package com.google.android.gms.maps;

import android.graphics.Point;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.zzr.zza;
import com.google.android.gms.maps.internal.zzs.zza;
import com.google.android.gms.maps.internal.zzt.zza;
import com.google.android.gms.maps.internal.zzu.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation;

public class StreetViewPanorama
{
  private final IStreetViewPanoramaDelegate zzaum;

  protected StreetViewPanorama(IStreetViewPanoramaDelegate paramIStreetViewPanoramaDelegate)
  {
    this.zzaum = ((IStreetViewPanoramaDelegate)zzv.zzr(paramIStreetViewPanoramaDelegate));
  }

  public void animateTo(StreetViewPanoramaCamera paramStreetViewPanoramaCamera, long paramLong)
  {
    try
    {
      this.zzaum.animateTo(paramStreetViewPanoramaCamera, paramLong);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public StreetViewPanoramaLocation getLocation()
  {
    try
    {
      StreetViewPanoramaLocation localStreetViewPanoramaLocation = this.zzaum.getStreetViewPanoramaLocation();
      return localStreetViewPanoramaLocation;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public StreetViewPanoramaCamera getPanoramaCamera()
  {
    try
    {
      StreetViewPanoramaCamera localStreetViewPanoramaCamera = this.zzaum.getPanoramaCamera();
      return localStreetViewPanoramaCamera;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isPanningGesturesEnabled()
  {
    try
    {
      boolean bool = this.zzaum.isPanningGesturesEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isStreetNamesEnabled()
  {
    try
    {
      boolean bool = this.zzaum.isStreetNamesEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isUserNavigationEnabled()
  {
    try
    {
      boolean bool = this.zzaum.isUserNavigationEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isZoomGesturesEnabled()
  {
    try
    {
      boolean bool = this.zzaum.isZoomGesturesEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public Point orientationToPoint(StreetViewPanoramaOrientation paramStreetViewPanoramaOrientation)
  {
    try
    {
      zzd localzzd = this.zzaum.orientationToPoint(paramStreetViewPanoramaOrientation);
      if (localzzd == null)
        return null;
      Point localPoint = (Point)zze.zzg(localzzd);
      return localPoint;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public StreetViewPanoramaOrientation pointToOrientation(Point paramPoint)
  {
    try
    {
      StreetViewPanoramaOrientation localStreetViewPanoramaOrientation = this.zzaum.pointToOrientation(zze.zzt(paramPoint));
      return localStreetViewPanoramaOrientation;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnStreetViewPanoramaCameraChangeListener(final OnStreetViewPanoramaCameraChangeListener paramOnStreetViewPanoramaCameraChangeListener)
  {
    if (paramOnStreetViewPanoramaCameraChangeListener == null);
    try
    {
      this.zzaum.setOnStreetViewPanoramaCameraChangeListener(null);
      return;
      this.zzaum.setOnStreetViewPanoramaCameraChangeListener(new zzr.zza()
      {
        public void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera paramAnonymousStreetViewPanoramaCamera)
        {
          paramOnStreetViewPanoramaCameraChangeListener.onStreetViewPanoramaCameraChange(paramAnonymousStreetViewPanoramaCamera);
        }
      });
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnStreetViewPanoramaChangeListener(final OnStreetViewPanoramaChangeListener paramOnStreetViewPanoramaChangeListener)
  {
    if (paramOnStreetViewPanoramaChangeListener == null);
    try
    {
      this.zzaum.setOnStreetViewPanoramaChangeListener(null);
      return;
      this.zzaum.setOnStreetViewPanoramaChangeListener(new zzs.zza()
      {
        public void onStreetViewPanoramaChange(StreetViewPanoramaLocation paramAnonymousStreetViewPanoramaLocation)
        {
          paramOnStreetViewPanoramaChangeListener.onStreetViewPanoramaChange(paramAnonymousStreetViewPanoramaLocation);
        }
      });
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnStreetViewPanoramaClickListener(final OnStreetViewPanoramaClickListener paramOnStreetViewPanoramaClickListener)
  {
    if (paramOnStreetViewPanoramaClickListener == null);
    try
    {
      this.zzaum.setOnStreetViewPanoramaClickListener(null);
      return;
      this.zzaum.setOnStreetViewPanoramaClickListener(new zzt.zza()
      {
        public void onStreetViewPanoramaClick(StreetViewPanoramaOrientation paramAnonymousStreetViewPanoramaOrientation)
        {
          paramOnStreetViewPanoramaClickListener.onStreetViewPanoramaClick(paramAnonymousStreetViewPanoramaOrientation);
        }
      });
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnStreetViewPanoramaLongClickListener(final OnStreetViewPanoramaLongClickListener paramOnStreetViewPanoramaLongClickListener)
  {
    if (paramOnStreetViewPanoramaLongClickListener == null);
    try
    {
      this.zzaum.setOnStreetViewPanoramaLongClickListener(null);
      return;
      this.zzaum.setOnStreetViewPanoramaLongClickListener(new zzu.zza()
      {
        public void onStreetViewPanoramaLongClick(StreetViewPanoramaOrientation paramAnonymousStreetViewPanoramaOrientation)
        {
          paramOnStreetViewPanoramaLongClickListener.onStreetViewPanoramaLongClick(paramAnonymousStreetViewPanoramaOrientation);
        }
      });
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setPanningGesturesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzaum.enablePanning(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setPosition(LatLng paramLatLng)
  {
    try
    {
      this.zzaum.setPosition(paramLatLng);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setPosition(LatLng paramLatLng, int paramInt)
  {
    try
    {
      this.zzaum.setPositionWithRadius(paramLatLng, paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setPosition(String paramString)
  {
    try
    {
      this.zzaum.setPositionWithID(paramString);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setStreetNamesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzaum.enableStreetNames(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setUserNavigationEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzaum.enableUserNavigation(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setZoomGesturesEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzaum.enableZoom(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  IStreetViewPanoramaDelegate zztF()
  {
    return this.zzaum;
  }

  public static abstract interface OnStreetViewPanoramaCameraChangeListener
  {
    public abstract void onStreetViewPanoramaCameraChange(StreetViewPanoramaCamera paramStreetViewPanoramaCamera);
  }

  public static abstract interface OnStreetViewPanoramaChangeListener
  {
    public abstract void onStreetViewPanoramaChange(StreetViewPanoramaLocation paramStreetViewPanoramaLocation);
  }

  public static abstract interface OnStreetViewPanoramaClickListener
  {
    public abstract void onStreetViewPanoramaClick(StreetViewPanoramaOrientation paramStreetViewPanoramaOrientation);
  }

  public static abstract interface OnStreetViewPanoramaLongClickListener
  {
    public abstract void onStreetViewPanoramaLongClick(StreetViewPanoramaOrientation paramStreetViewPanoramaOrientation);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.StreetViewPanorama
 * JD-Core Version:    0.6.2
 */