package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesUtil;

public class zza extends zzo.zza
{
  private Context mContext;
  private Account zzJc;
  int zzSR;

  public static Account zzb(zzo paramzzo)
  {
    Object localObject1 = null;
    long l;
    if (paramzzo != null)
      l = Binder.clearCallingIdentity();
    try
    {
      Account localAccount = paramzzo.getAccount();
      localObject1 = localAccount;
      return localObject1;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("AccountAccessor", "Remote account accessor probably died");
      return null;
    }
    finally
    {
      Binder.restoreCallingIdentity(l);
    }
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof zza))
      return false;
    return this.zzJc.equals(((zza)paramObject).zzJc);
  }

  public Account getAccount()
  {
    int i = Binder.getCallingUid();
    if (i == this.zzSR)
      return this.zzJc;
    if (GooglePlayServicesUtil.zzd(this.mContext, i))
    {
      this.zzSR = i;
      return this.zzJc;
    }
    throw new SecurityException("Caller is not GooglePlayServices");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zza
 * JD-Core Version:    0.6.2
 */