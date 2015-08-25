package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Status;

public abstract interface zze extends IInterface
{
  public abstract void zza(ConnectionResult paramConnectionResult, AuthAccountResult paramAuthAccountResult)
    throws RemoteException;

  public abstract void zzaN(Status paramStatus)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zze
 * JD-Core Version:    0.6.2
 */