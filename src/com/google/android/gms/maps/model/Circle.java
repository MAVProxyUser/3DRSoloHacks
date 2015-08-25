package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.model.internal.zze;

public final class Circle
{
  private final zze zzauX;

  public Circle(zze paramzze)
  {
    this.zzauX = ((zze)zzv.zzr(paramzze));
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Circle))
      return false;
    try
    {
      boolean bool = this.zzauX.zza(((Circle)paramObject).zzauX);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public LatLng getCenter()
  {
    try
    {
      LatLng localLatLng = this.zzauX.getCenter();
      return localLatLng;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public int getFillColor()
  {
    try
    {
      int i = this.zzauX.getFillColor();
      return i;
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
      String str = this.zzauX.getId();
      return str;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public double getRadius()
  {
    try
    {
      double d = this.zzauX.getRadius();
      return d;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public int getStrokeColor()
  {
    try
    {
      int i = this.zzauX.getStrokeColor();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getStrokeWidth()
  {
    try
    {
      float f = this.zzauX.getStrokeWidth();
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
      float f = this.zzauX.getZIndex();
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
      int i = this.zzauX.hashCodeRemote();
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
      boolean bool = this.zzauX.isVisible();
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
      this.zzauX.remove();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setCenter(LatLng paramLatLng)
  {
    try
    {
      this.zzauX.setCenter(paramLatLng);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setFillColor(int paramInt)
  {
    try
    {
      this.zzauX.setFillColor(paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setRadius(double paramDouble)
  {
    try
    {
      this.zzauX.setRadius(paramDouble);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setStrokeColor(int paramInt)
  {
    try
    {
      this.zzauX.setStrokeColor(paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setStrokeWidth(float paramFloat)
  {
    try
    {
      this.zzauX.setStrokeWidth(paramFloat);
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
      this.zzauX.setVisible(paramBoolean);
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
      this.zzauX.setZIndex(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.Circle
 * JD-Core Version:    0.6.2
 */