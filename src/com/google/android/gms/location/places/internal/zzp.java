package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzp
  implements Parcelable.Creator<PlacesParams>
{
  static void zza(PlacesParams paramPlacesParams, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramPlacesParams.zzatc, false);
    zzb.zzc(paramParcel, 1000, paramPlacesParams.versionCode);
    zzb.zza(paramParcel, 2, paramPlacesParams.zzatd, false);
    zzb.zza(paramParcel, 3, paramPlacesParams.zzate, false);
    zzb.zza(paramParcel, 4, paramPlacesParams.zzasc, false);
    zzb.zza(paramParcel, 5, paramPlacesParams.zzatf, false);
    zzb.zzc(paramParcel, 6, paramPlacesParams.zzatg);
    zzb.zzH(paramParcel, i);
  }

  public PlacesParams zzdU(Parcel paramParcel)
  {
    int i = 0;
    String str1 = null;
    int j = zza.zzL(paramParcel);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
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
        str5 = zza.zzo(paramParcel, m);
        break;
      case 1000:
        k = zza.zzg(paramParcel, m);
        break;
      case 2:
        str4 = zza.zzo(paramParcel, m);
        break;
      case 3:
        str3 = zza.zzo(paramParcel, m);
        break;
      case 4:
        str2 = zza.zzo(paramParcel, m);
        break;
      case 5:
        str1 = zza.zzo(paramParcel, m);
        break;
      case 6:
        i = zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new PlacesParams(k, str5, str4, str3, str2, str1, i);
  }

  public PlacesParams[] zzfZ(int paramInt)
  {
    return new PlacesParams[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzp
 * JD-Core Version:    0.6.2
 */