package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzo extends IInterface
{
  public abstract Account getAccount()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzo
 * JD-Core Version:    0.6.2
 */