package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<ConverterWrapper>
{
  static void zza(ConverterWrapper paramConverterWrapper, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramConverterWrapper.getVersionCode());
    zzb.zza(paramParcel, 2, paramConverterWrapper.zzmt(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public ConverterWrapper zzO(Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    int j = 0;
    StringToIntConverter localStringToIntConverter = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(k))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, k);
        break;
      case 1:
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, k);
        break;
      case 2:
        localStringToIntConverter = (StringToIntConverter)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, k, StringToIntConverter.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new ConverterWrapper(j, localStringToIntConverter);
  }

  public ConverterWrapper[] zzaY(int paramInt)
  {
    return new ConverterWrapper[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.converter.zza
 * JD-Core Version:    0.6.2
 */