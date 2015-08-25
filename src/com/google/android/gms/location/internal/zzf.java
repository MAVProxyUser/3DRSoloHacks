package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzf extends IInterface
{
  public abstract void zza(int paramInt, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(int paramInt, String[] paramArrayOfString)
    throws RemoteException;

  public abstract void zzb(int paramInt, String[] paramArrayOfString)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzf
 * JD-Core Version:    0.6.2
 */