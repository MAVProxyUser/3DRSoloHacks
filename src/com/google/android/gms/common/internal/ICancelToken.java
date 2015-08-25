package com.google.android.gms.common.internal;

import android.os.IInterface;
import android.os.RemoteException;

public abstract interface ICancelToken extends IInterface
{
  public abstract void cancel()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.ICancelToken
 * JD-Core Version:    0.6.2
 */