package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;

public abstract interface ILocationSourceDelegate extends IInterface
{
  public abstract void activate(zzi paramzzi)
    throws RemoteException;

  public abstract void deactivate()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.ILocationSourceDelegate
 * JD-Core Version:    0.6.2
 */