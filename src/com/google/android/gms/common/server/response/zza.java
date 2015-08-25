package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.server.converter.ConverterWrapper;

public class zza
  implements Parcelable.Creator<FastJsonResponse.Field>
{
  static void zza(FastJsonResponse.Field paramField, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramField.getVersionCode());
    zzb.zzc(paramParcel, 2, paramField.zzmw());
    zzb.zza(paramParcel, 3, paramField.zzmC());
    zzb.zzc(paramParcel, 4, paramField.zzmx());
    zzb.zza(paramParcel, 5, paramField.zzmD());
    zzb.zza(paramParcel, 6, paramField.zzmE(), false);
    zzb.zzc(paramParcel, 7, paramField.zzmF());
    zzb.zza(paramParcel, 8, paramField.zzmH(), false);
    zzb.zza(paramParcel, 9, paramField.zzmJ(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public FastJsonResponse.Field zzR(Parcel paramParcel)
  {
    ConverterWrapper localConverterWrapper = null;
    int i = 0;
    int j = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    String str1 = null;
    String str2 = null;
    boolean bool1 = false;
    int k = 0;
    boolean bool2 = false;
    int m = 0;
    int n = 0;
    while (paramParcel.dataPosition() < j)
    {
      int i1 = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(i1))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, i1);
        break;
      case 1:
        n = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 2:
        m = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 3:
        bool2 = com.google.android.gms.common.internal.safeparcel.zza.zzc(paramParcel, i1);
        break;
      case 4:
        k = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 5:
        bool1 = com.google.android.gms.common.internal.safeparcel.zza.zzc(paramParcel, i1);
        break;
      case 6:
        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, i1);
        break;
      case 7:
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, i1);
        break;
      case 8:
        str1 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, i1);
        break;
      case 9:
        localConverterWrapper = (ConverterWrapper)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, i1, ConverterWrapper.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new FastJsonResponse.Field(n, m, bool2, k, bool1, str2, i, str1, localConverterWrapper);
  }

  public FastJsonResponse.Field[] zzbb(int paramInt)
  {
    return new FastJsonResponse.Field[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.response.zza
 * JD-Core Version:    0.6.2
 */