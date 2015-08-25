package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<StringToIntConverter.Entry>
{
  static void zza(StringToIntConverter.Entry paramEntry, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramEntry.versionCode);
    zzb.zza(paramParcel, 2, paramEntry.zzUw, false);
    zzb.zzc(paramParcel, 3, paramEntry.zzUx);
    zzb.zzH(paramParcel, i);
  }

  public StringToIntConverter.Entry zzQ(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
    String str = null;
    int k = 0;
    while (paramParcel.dataPosition() < j)
    {
      int m = zza.zzK(paramParcel);
      switch (zza.zzaV(m))
      {
      default:
        zza.zzb(paramParcel, m);
        break;
      case 1:
        k = zza.zzg(paramParcel, m);
        break;
      case 2:
        str = zza.zzo(paramParcel, m);
        break;
      case 3:
        i = zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new StringToIntConverter.Entry(k, str, i);
  }

  public StringToIntConverter.Entry[] zzba(int paramInt)
  {
    return new StringToIntConverter.Entry[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.converter.zzc
 * JD-Core Version:    0.6.2
 */