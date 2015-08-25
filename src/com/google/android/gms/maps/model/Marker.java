package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.model.internal.zzi;

public final class Marker
{
  private final zzi zzavu;

  public Marker(zzi paramzzi)
  {
    this.zzavu = ((zzi)zzv.zzr(paramzzi));
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Marker))
      return false;
    try
    {
      boolean bool = this.zzavu.zzh(((Marker)paramObject).zzavu);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getAlpha()
  {
    try
    {
      float f = this.zzavu.getAlpha();
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
      String str = this.zzavu.getId();
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
      LatLng localLatLng = this.zzavu.getPosition();
      return localLatLng;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public float getRotation()
  {
    try
    {
      float f = this.zzavu.getRotation();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public String getSnippet()
  {
    try
    {
      String str = this.zzavu.getSnippet();
      return str;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public String getTitle()
  {
    try
    {
      String str = this.zzavu.getTitle();
      return str;
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
      int i = this.zzavu.hashCodeRemote();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void hideInfoWindow()
  {
    try
    {
      this.zzavu.hideInfoWindow();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isDraggable()
  {
    try
    {
      boolean bool = this.zzavu.isDraggable();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isFlat()
  {
    try
    {
      boolean bool = this.zzavu.isFlat();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isInfoWindowShown()
  {
    try
    {
      boolean bool = this.zzavu.isInfoWindowShown();
      return bool;
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
      boolean bool = this.zzavu.isVisible();
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
      this.zzavu.remove();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setAlpha(float paramFloat)
  {
    try
    {
      this.zzavu.setAlpha(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setAnchor(float paramFloat1, float paramFloat2)
  {
    try
    {
      this.zzavu.setAnchor(paramFloat1, paramFloat2);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setDraggable(boolean paramBoolean)
  {
    try
    {
      this.zzavu.setDraggable(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setFlat(boolean paramBoolean)
  {
    try
    {
      this.zzavu.setFlat(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setIcon(BitmapDescriptor paramBitmapDescriptor)
  {
    try
    {
      this.zzavu.zzo(paramBitmapDescriptor.zztp());
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setInfoWindowAnchor(float paramFloat1, float paramFloat2)
  {
    try
    {
      this.zzavu.setInfoWindowAnchor(paramFloat1, paramFloat2);
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
      this.zzavu.setPosition(paramLatLng);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setRotation(float paramFloat)
  {
    try
    {
      this.zzavu.setRotation(paramFloat);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setSnippet(String paramString)
  {
    try
    {
      this.zzavu.setSnippet(paramString);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setTitle(String paramString)
  {
    try
    {
      this.zzavu.setTitle(paramString);
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
      this.zzavu.setVisible(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void showInfoWindow()
  {
    try
    {
      this.zzavu.showInfoWindow();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.Marker
 * JD-Core Version:    0.6.2
 */