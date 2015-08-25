package com.google.android.gms.maps.model.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public abstract interface zzj extends IInterface
{
  public abstract int getFillColor()
    throws RemoteException;

  public abstract List getHoles()
    throws RemoteException;

  public abstract String getId()
    throws RemoteException;

  public abstract List<LatLng> getPoints()
    throws RemoteException;

  public abstract int getStrokeColor()
    throws RemoteException;

  public abstract float getStrokeWidth()
    throws RemoteException;

  public abstract float getZIndex()
    throws RemoteException;

  public abstract int hashCodeRemote()
    throws RemoteException;

  public abstract boolean isGeodesic()
    throws RemoteException;

  public abstract boolean isVisible()
    throws RemoteException;

  public abstract void remove()
    throws RemoteException;

  public abstract void setFillColor(int paramInt)
    throws RemoteException;

  public abstract void setGeodesic(boolean paramBoolean)
    throws RemoteException;

  public abstract void setHoles(List paramList)
    throws RemoteException;

  public abstract void setPoints(List<LatLng> paramList)
    throws RemoteException;

  public abstract void setStrokeColor(int paramInt)
    throws RemoteException;

  public abstract void setStrokeWidth(float paramFloat)
    throws RemoteException;

  public abstract void setVisible(boolean paramBoolean)
    throws RemoteException;

  public abstract void setZIndex(float paramFloat)
    throws RemoteException;

  public abstract boolean zza(zzj paramzzj)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzj
 * JD-Core Version:    0.6.2
 */