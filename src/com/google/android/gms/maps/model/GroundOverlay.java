package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.model.internal.zzf;

public final class GroundOverlay
{
  private final zzf zzavf;

  public GroundOverlay(zzf paramzzf)
  {
    this.zzavf = ((zzf)zzv.zzr(paramzzf));
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof GroundOverlay))
      return false;
    try
    {
      boolean bool = this.zzavf.zza(((GroundOverlay)paramObject).zzavf);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getBearing()
  {
    try
    {
      float f = this.zzavf.getBearing();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public LatLngBounds getBounds()
  {
    try
    {
      LatLngBounds localLatLngBounds = this.zzavf.getBounds();
      return localLatLngBounds;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getHeight()
  {
    try
    {
      float f = this.zzavf.getHeight();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public String getId()
  {
    try
    {
      String str = this.zzavf.getId();
      return str;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public LatLng getPosition()
  {
    try
    {
      LatLng localLatLng = this.zzavf.getPosition();
      return localLatLng;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getTransparency()
  {
    try
    {
      float f = this.zzavf.getTransparency();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getWidth()
  {
    try
    {
      float f = this.zzavf.getWidth();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getZIndex()
  {
    try
    {
      float f = this.zzavf.getZIndex();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public int hashCode()
  {
    try
    {
      int i = this.zzavf.hashCodeRemote();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isVisible()
  {
    try
    {
      boolean bool = this.zzavf.isVisible();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void remove()
  {
    try
    {
      this.zzavf.remove();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setBearing(float paramFloat)
  {
    try
    {
      this.zzavf.setBearing(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setDimensions(float paramFloat)
  {
    try
    {
      this.zzavf.setDimensions(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setDimensions(float paramFloat1, float paramFloat2)
  {
    try
    {
      this.zzavf.zza(paramFloat1, paramFloat2);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setImage(BitmapDescriptor paramBitmapDescriptor)
  {
    try
    {
      this.zzavf.zzn(paramBitmapDescriptor.zztp());
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
      this.zzavf.setPosition(paramLatLng);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setPositionFromBounds(LatLngBounds paramLatLngBounds)
  {
    try
    {
      this.zzavf.setPositionFromBounds(paramLatLngBounds);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setTransparency(float paramFloat)
  {
    try
    {
      this.zzavf.setTransparency(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setVisible(boolean paramBoolean)
  {
    try
    {
      this.zzavf.setVisible(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setZIndex(float paramFloat)
  {
    try
    {
      this.zzavf.setZIndex(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.GroundOverlay
 * JD-Core Version:    0.6.2
 */