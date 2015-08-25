package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzr
  implements Parcelable.Creator<AutocompletePredictionEntity.SubstringEntity>
{
  static void zza(AutocompletePredictionEntity.SubstringEntity paramSubstringEntity, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramSubstringEntity.mOffset);
    zzb.zzc(paramParcel, 1000, paramSubstringEntity.zzzH);
    zzb.zzc(paramParcel, 2, paramSubstringEntity.mLength);
    zzb.zzH(paramParcel, i);
  }

  public AutocompletePredictionEntity.SubstringEntity zzdV(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
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
        k = zza.zzg(paramParcel, n);
        break;
      case 1000:
        m = zza.zzg(paramParcel, n);
        break;
      case 2:
        i = zza.zzg(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new AutocompletePredictionEntity.SubstringEntity(m, k, i);
  }

  public AutocompletePredictionEntity.SubstringEntity[] zzga(int paramInt)
  {
    return new AutocompletePredictionEntity.SubstringEntity[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzr
 * JD-Core Version:    0.6.2
 */