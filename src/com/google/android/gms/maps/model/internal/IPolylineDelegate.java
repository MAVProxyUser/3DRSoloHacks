package com.google.android.gms.maps.model.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public abstract interface IPolylineDelegate extends IInterface
{
  public abstract boolean equalsRemote(IPolylineDelegate paramIPolylineDelegate)
    throws RemoteException;

  public abstract int getColor()
    throws RemoteException;

  public abstract String getId()
    throws RemoteException;

  public abstract List<LatLng> getPoints()
    throws RemoteException;

  public abstract float getWidth()
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

  public abstract void setColor(int paramInt)
    throws RemoteException;

  public abstract void setGeodesic(boolean paramBoolean)
    throws RemoteException;

  public abstract void setPoints(List<LatLng> paramList)
    throws RemoteException;

  public abstract void setVisible(boolean paramBoolean)
    throws RemoteException;

  public abstract void setWidth(float paramFloat)
    throws RemoteException;

  public abstract void setZIndex(float paramFloat)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.IPolylineDelegate
 * JD-Core Version:    0.6.2
 */