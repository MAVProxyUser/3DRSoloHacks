package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import java.util.ArrayList;

public class zzb
  implements Parcelable.Creator<StringToIntConverter>
{
  static void zza(StringToIntConverter paramStringToIntConverter, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzM(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramStringToIntConverter.getVersionCode());
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 2, paramStringToIntConverter.zzmv(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, i);
  }

  public StringToIntConverter zzP(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    ArrayList localArrayList = null;
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
        localArrayList = zza.zzc(paramParcel, k, StringToIntConverter.Entry.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new StringToIntConverter(j, localArrayList);
  }

  public StringToIntConverter[] zzaZ(int paramInt)
  {
    return new StringToIntConverter[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.converter.zzb
 * JD-Core Version:    0.6.2
 */