package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzm
  implements Parcelable.Creator<PlaceLocalization>
{
  static void zza(PlaceLocalization paramPlaceLocalization, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceLocalization.name, false);
    zzb.zzc(paramParcel, 1000, paramPlaceLocalization.versionCode);
    zzb.zza(paramParcel, 2, paramPlaceLocalization.zzasR, false);
    zzb.zza(paramParcel, 3, paramPlaceLocalization.zzasS, false);
    zzb.zza(paramParcel, 4, paramPlaceLocalization.zzasT, false);
    zzb.zzb(paramParcel, 5, paramPlaceLocalization.zzasU, false);
    zzb.zzH(paramParcel, i);
  }

  public PlaceLocalization zzdT(Parcel paramParcel)
  {
    ArrayList localArrayList = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        break;
      case 1:
        str4 = zza.zzo(paramParcel, k);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        str3 = zza.zzo(paramParcel, k);
        break;
      case 3:
        str2 = zza.zzo(paramParcel, k);
        break;
      case 4:
        str1 = zza.zzo(paramParcel, k);
        break;
      case 5:
        localArrayList = zza.zzC(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new PlaceLocalization(j, str4, str3, str2, str1, localArrayList);
  }

  public PlaceLocalization[] zzfY(int paramInt)
  {
    return new PlaceLocalization[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzm
 * JD-Core Version:    0.6.2
 */