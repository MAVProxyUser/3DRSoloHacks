package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class GetServiceRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<GetServiceRequest> CREATOR = new zzh();
  final int version;
  final int zzTh;
  int zzTi;
  String zzTj;
  IBinder zzTk;
  Scope[] zzTl;
  Bundle zzTm;
  Account zzTn;

  public GetServiceRequest(int paramInt)
  {
    this.version = 2;
    this.zzTi = 7327000;
    this.zzTh = paramInt;
  }

  GetServiceRequest(int paramInt1, int paramInt2, int paramInt3, String paramString, IBinder paramIBinder, Scope[] paramArrayOfScope, Bundle paramBundle, Account paramAccount)
  {
    this.version = paramInt1;
    this.zzTh = paramInt2;
    this.zzTi = paramInt3;
    this.zzTj = paramString;
    if (paramInt1 < 2);
    for (this.zzTn = zzP(paramIBinder); ; this.zzTn = paramAccount)
    {
      this.zzTl = paramArrayOfScope;
      this.zzTm = paramBundle;
      return;
      this.zzTk = paramIBinder;
    }
  }

  private Account zzP(IBinder paramIBinder)
  {
    Account localAccount = null;
    if (paramIBinder != null)
      localAccount = zza.zzb(zzo.zza.zzQ(paramIBinder));
    return localAccount;
  }

  public int describeContents()
  {
    return 0;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzh.zza(this, paramParcel, paramInt);
  }

  public GetServiceRequest zza(Scope[] paramArrayOfScope)
  {
    this.zzTl = paramArrayOfScope;
    return this;
  }

  public GetServiceRequest zzb(Account paramAccount)
  {
    this.zzTn = paramAccount;
    return this;
  }

  public GetServiceRequest zzbL(String paramString)
  {
    this.zzTj = paramString;
    return this;
  }

  public GetServiceRequest zzc(zzo paramzzo)
  {
    if (paramzzo != null)
      this.zzTk = paramzzo.asBinder();
    return this;
  }

  public GetServiceRequest zzf(Bundle paramBundle)
  {
    this.zzTm = paramBundle;
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.GetServiceRequest
 * JD-Core Version:    0.6.2
 */