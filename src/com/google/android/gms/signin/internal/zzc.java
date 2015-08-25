package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzc
  implements Parcelable.Creator<CheckServerAuthResult>
{
  static void zza(CheckServerAuthResult paramCheckServerAuthResult, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramCheckServerAuthResult.zzzH);
    zzb.zza(paramParcel, 2, paramCheckServerAuthResult.zzaBL);
    zzb.zzc(paramParcel, 3, paramCheckServerAuthResult.zzaBM, false);
    zzb.zzH(paramParcel, i);
  }

  public CheckServerAuthResult zzfh(Parcel paramParcel)
  {
    boolean bool = false;
    int i = zza.zzL(paramParcel);
    ArrayList localArrayList = null;
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
        bool = zza.zzc(paramParcel, k);
        break;
      case 3:
        localArrayList = zza.zzc(paramParcel, k, Scope.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new CheckServerAuthResult(j, bool, localArrayList);
  }

  public CheckServerAuthResult[] zzhA(int paramInt)
  {
    return new CheckServerAuthResult[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzc
 * JD-Core Version:    0.6.2
 */