package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzh
  implements Parcelable.Creator<GetServiceRequest>
{
  static void zza(GetServiceRequest paramGetServiceRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramGetServiceRequest.version);
    zzb.zzc(paramParcel, 2, paramGetServiceRequest.zzTh);
    zzb.zzc(paramParcel, 3, paramGetServiceRequest.zzTi);
    zzb.zza(paramParcel, 4, paramGetServiceRequest.zzTj, false);
    zzb.zza(paramParcel, 5, paramGetServiceRequest.zzTk, false);
    zzb.zza(paramParcel, 6, paramGetServiceRequest.zzTl, paramInt, false);
    zzb.zza(paramParcel, 7, paramGetServiceRequest.zzTm, false);
    zzb.zza(paramParcel, 8, paramGetServiceRequest.zzTn, paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public GetServiceRequest zzG(Parcel paramParcel)
  {
    int i = 0;
    Account localAccount = null;
    int j = zza.zzL(paramParcel);
    Bundle localBundle = null;
    Scope[] arrayOfScope = null;
    IBinder localIBinder = null;
    String str = null;
    int k = 0;
    int m = 0;
    while (paramParcel.dataPosition() < j)
    {
      int n = zza.zzK(paramParcel);
      switch (zza.zzaV(n))
      {
      default:
        zza.zzb(paramParcel, n);
        break;
      case 1:
        m = zza.zzg(paramParcel, n);
        break;
      case 2:
        k = zza.zzg(paramParcel, n);
        break;
      case 3:
        i = zza.zzg(paramParcel, n);
        break;
      case 4:
        str = zza.zzo(paramParcel, n);
        break;
      case 5:
        localIBinder = zza.zzp(paramParcel, n);
        break;
      case 6:
        arrayOfScope = (Scope[])zza.zzb(paramParcel, n, Scope.CREATOR);
        break;
      case 7:
        localBundle = zza.zzq(paramParcel, n);
        break;
      case 8:
        localAccount = (Account)zza.zza(paramParcel, n, Account.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new GetServiceRequest(m, k, i, str, localIBinder, arrayOfScope, localBundle, localAccount);
  }

  public GetServiceRequest[] zzaN(int paramInt)
  {
    return new GetServiceRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzh
 * JD-Core Version:    0.6.2
 */