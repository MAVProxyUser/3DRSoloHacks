package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zza
  implements Parcelable.Creator<AutocompletePredictionEntity>
{
  static void zza(AutocompletePredictionEntity paramAutocompletePredictionEntity, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramAutocompletePredictionEntity.zzadH, false);
    zzb.zzc(paramParcel, 1000, paramAutocompletePredictionEntity.zzzH);
    zzb.zza(paramParcel, 2, paramAutocompletePredictionEntity.zzarP, false);
    zzb.zza(paramParcel, 3, paramAutocompletePredictionEntity.zzarx, false);
    zzb.zzc(paramParcel, 4, paramAutocompletePredictionEntity.zzasj, false);
    zzb.zzc(paramParcel, 5, paramAutocompletePredictionEntity.zzask);
    zzb.zzH(paramParcel, i);
  }

  public AutocompletePredictionEntity zzdQ(Parcel paramParcel)
  {
    int i = 0;
    ArrayList localArrayList1 = null;
    int j = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    ArrayList localArrayList2 = null;
    String str1 = null;
    String str2 = null;
    int k = 0;
    while (paramParcel.dataPosition() < j)
    {
      int m = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(m))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, m);
        break;
      case 1:
        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, m);
        break;
      case 1000:
        k = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        break;
      case 2:
        str1 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, m);
        break;
      case 3:
        localArrayList2 = com.google.android.gms.common.internal.safeparcel.zza.zzB(paramParcel, m);
        break;
      case 4:
        localArrayList1 = com.google.android.gms.common.internal.safeparcel.zza.zzc(paramParcel, m, AutocompletePredictionEntity.SubstringEntity.CREATOR);
        break;
      case 5:
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new AutocompletePredictionEntity(k, str2, str1, localArrayList2, localArrayList1, i);
  }

  public AutocompletePredictionEntity[] zzfU(int paramInt)
  {
    return new AutocompletePredictionEntity[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zza
 * JD-Core Version:    0.6.2
 */