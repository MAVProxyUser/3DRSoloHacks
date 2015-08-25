package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzb extends IInterface
{
  public abstract void onCancel()
    throws RemoteException;

  public abstract void onFinish()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzb
 * JD-Core Version:    0.6.2
 */