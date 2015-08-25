package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsResponse;

public abstract interface zzr extends IInterface
{
  public abstract Bundle zza(Account paramAccount, String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle zza(String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle zza(String paramString1, String paramString2, Bundle paramBundle)
    throws RemoteException;

  public abstract AccountChangeEventsResponse zza(AccountChangeEventsRequest paramAccountChangeEventsRequest)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzr
 * JD-Core Version:    0.6.2
 */