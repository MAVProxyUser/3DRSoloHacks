package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzw
  implements Parcelable.Creator<ResolveAccountRequest>
{
  static void zza(ResolveAccountRequest paramResolveAccountRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramResolveAccountRequest.zzzH);
    zzb.zza(paramParcel, 2, paramResolveAccountRequest.getAccount(), paramInt, false);
    zzb.zzc(paramParcel, 3, paramResolveAccountRequest.getSessionId());
    zzb.zzH(paramParcel, i);
  }

  public ResolveAccountRequest zzH(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
    Object localObject1 = null;
    int k = 0;
    if (paramParcel.dataPosition() < j)
    {
      int m = zza.zzK(paramParcel);
      int n;
      Object localObject2;
      int i1;
      switch (zza.zzaV(m))
      {
      default:
        zza.zzb(paramParcel, m);
        n = i;
        localObject2 = localObject1;
        i1 = k;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        k = i1;
        localObject1 = localObject2;
        i = n;
        break;
        int i2 = zza.zzg(paramParcel, m);
        int i3 = i;
        localObject2 = localObject1;
        i1 = i2;
        n = i3;
        continue;
        Account localAccount = (Account)zza.zza(paramParcel, m, Account.CREATOR);
        i1 = k;
        n = i;
        localObject2 = localAccount;
        continue;
        n = zza.zzg(paramParcel, m);
        localObject2 = localObject1;
        i1 = k;
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new ResolveAccountRequest(k, localObject1, i);
  }

  public ResolveAccountRequest[] zzaS(int paramInt)
  {
    return new ResolveAccountRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzw
 * JD-Core Version:    0.6.2
 */