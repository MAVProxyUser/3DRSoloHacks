package com.google.android.gms.signin.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.Scope;
import java.util.List;

public abstract interface zzd extends IInterface
{
  public abstract void zza(String paramString1, String paramString2, zzf paramzzf)
    throws RemoteException;

  public abstract void zza(String paramString, List<Scope> paramList, zzf paramzzf)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzd
 * JD-Core Version:    0.6.2
 */