package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
  implements Parcelable.Creator<SafeParcelResponse>
{
  static void zza(SafeParcelResponse paramSafeParcelResponse, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramSafeParcelResponse.getVersionCode());
    zzb.zza(paramParcel, 2, paramSafeParcelResponse.zzmQ(), false);
    zzb.zza(paramParcel, 3, paramSafeParcelResponse.zzmR(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public SafeParcelResponse zzV(Parcel paramParcel)
  {
    FieldMappingDictionary localFieldMappingDictionary = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    Parcel localParcel = null;
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
        localParcel = zza.zzD(paramParcel, k);
        break;
      case 3:
        localFieldMappingDictionary = (FieldMappingDictionary)zza.zza(paramParcel, k, FieldMappingDictionary.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new SafeParcelResponse(j, localParcel, localFieldMappingDictionary);
  }

  public SafeParcelResponse[] zzbf(int paramInt)
  {
    return new SafeParcelResponse[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.response.zze
 * JD-Core Version:    0.6.2
 */