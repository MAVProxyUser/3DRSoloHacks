package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzx
  implements Parcelable.Creator<ResolveAccountResponse>
{
  static void zza(ResolveAccountResponse paramResolveAccountResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramResolveAccountResponse.zzzH);
    zzb.zza(paramParcel, 2, paramResolveAccountResponse.zzSS, false);
    zzb.zza(paramParcel, 3, paramResolveAccountResponse.zzmn(), paramInt, false);
    zzb.zza(paramParcel, 4, paramResolveAccountResponse.zzmo());
    zzb.zza(paramParcel, 5, paramResolveAccountResponse.zzmp());
    zzb.zzH(paramParcel, i);
  }

  public ResolveAccountResponse zzI(Parcel paramParcel)
  {
    ConnectionResult localConnectionResult = null;
    boolean bool1 = false;
    int i = zza.zzL(paramParcel);
    boolean bool2 = false;
    IBinder localIBinder = null;
    int j = 0;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        break;
      case 1:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        localIBinder = zza.zzp(paramParcel, k);
        break;
      case 3:
        localConnectionResult = (ConnectionResult)zza.zza(paramParcel, k, ConnectionResult.CREATOR);
        break;
      case 4:
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 5:
        bool1 = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new ResolveAccountResponse(j, localIBinder, localConnectionResult, bool2, bool1);
  }

  public ResolveAccountResponse[] zzaT(int paramInt)
  {
    return new ResolveAccountResponse[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzx
 * JD-Core Version:    0.6.2
 */