package com.google.android.gms.maps.model.internal;

import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzk extends IInterface
{
  public abstract void clearTileCache()
    throws RemoteException;

  public abstract boolean getFadeIn()
    throws RemoteException;

  public abstract String getId()
    throws RemoteException;

  public abstract float getZIndex()
    throws RemoteException;

  public abstract int hashCodeRemote()
    throws RemoteException;

  public abstract boolean isVisible()
    throws RemoteException;

  public abstract void remove()
    throws RemoteException;

  public abstract void setFadeIn(boolean paramBoolean)
    throws RemoteException;

  public abstract void setVisible(boolean paramBoolean)
    throws RemoteException;

  public abstract void setZIndex(float paramFloat)
    throws RemoteException;

  public abstract boolean zza(zzk paramzzk)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzk
 * JD-Core Version:    0.6.2
 */