package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;

public class zzb
  implements Parcelable.Creator<FieldMappingDictionary.FieldMapPair>
{
  static void zza(FieldMappingDictionary.FieldMapPair paramFieldMapPair, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzM(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramFieldMapPair.versionCode);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 2, paramFieldMapPair.zzgk, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 3, paramFieldMapPair.zzUM, paramInt, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, i);
  }

  public FieldMappingDictionary.FieldMapPair zzS(Parcel paramParcel)
  {
    FastJsonResponse.Field localField = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    String str = null;
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
        str = zza.zzo(paramParcel, k);
        break;
      case 3:
        localField = (FastJsonResponse.Field)zza.zza(paramParcel, k, FastJsonResponse.Field.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new FieldMappingDictionary.FieldMapPair(j, str, localField);
  }

  public FieldMappingDictionary.FieldMapPair[] zzbc(int paramInt)
  {
    return new FieldMappingDictionary.FieldMapPair[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.response.zzb
 * JD-Core Version:    0.6.2
 */