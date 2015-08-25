package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.internal.zzr;

public abstract interface zzf extends IInterface
{
  public abstract void zza(int paramInt, Account paramAccount, zze paramzze)
    throws RemoteException;

  public abstract void zza(AuthAccountRequest paramAuthAccountRequest, zze paramzze)
    throws RemoteException;

  public abstract void zza(ResolveAccountRequest paramResolveAccountRequest, zzr paramzzr)
    throws RemoteException;

  public abstract void zza(zzo paramzzo, int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract void zza(CheckServerAuthResult paramCheckServerAuthResult)
    throws RemoteException;

  public abstract void zzag(boolean paramBoolean)
    throws RemoteException;

  public abstract void zzhB(int paramInt)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzf
 * JD-Core Version:    0.6.2
 */