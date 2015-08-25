package com.google.android.gms.maps.model.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.LatLng;

public abstract interface zzi extends IInterface
{
  public abstract float getAlpha()
    throws RemoteException;

  public abstract String getId()
    throws RemoteException;

  public abstract LatLng getPosition()
    throws RemoteException;

  public abstract float getRotation()
    throws RemoteException;

  public abstract String getSnippet()
    throws RemoteException;

  public abstract String getTitle()
    throws RemoteException;

  public abstract int hashCodeRemote()
    throws RemoteException;

  public abstract void hideInfoWindow()
    throws RemoteException;

  public abstract boolean isDraggable()
    throws RemoteException;

  public abstract boolean isFlat()
    throws RemoteException;

  public abstract boolean isInfoWindowShown()
    throws RemoteException;

  public abstract boolean isVisible()
    throws RemoteException;

  public abstract void remove()
    throws RemoteException;

  public abstract void setAlpha(float paramFloat)
    throws RemoteException;

  public abstract void setAnchor(float paramFloat1, float paramFloat2)
    throws RemoteException;

  public abstract void setDraggable(boolean paramBoolean)
    throws RemoteException;

  public abstract void setFlat(boolean paramBoolean)
    throws RemoteException;

  public abstract void setInfoWindowAnchor(float paramFloat1, float paramFloat2)
    throws RemoteException;

  public abstract void setPosition(LatLng paramLatLng)
    throws RemoteException;

  public abstract void setRotation(float paramFloat)
    throws RemoteException;

  public abstract void setSnippet(String paramString)
    throws RemoteException;

  public abstract void setTitle(String paramString)
    throws RemoteException;

  public abstract void setVisible(boolean paramBoolean)
    throws RemoteException;

  public abstract void showInfoWindow()
    throws RemoteException;

  public abstract void zzb(BitmapDescriptorParcelable paramBitmapDescriptorParcelable)
    throws RemoteException;

  public abstract boolean zzh(zzi paramzzi)
    throws RemoteException;

  public abstract void zzo(zzd paramzzd)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzi
 * JD-Core Version:    0.6.2
 */