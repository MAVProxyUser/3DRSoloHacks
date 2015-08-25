package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.StreetViewPanoramaCamera;

public class zzb
  implements Parcelable.Creator<StreetViewPanoramaOptions>
{
  static void zza(StreetViewPanoramaOptions paramStreetViewPanoramaOptions, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzM(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramStreetViewPanoramaOptions.getVersionCode());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 2, paramStreetViewPanoramaOptions.getStreetViewPanoramaCamera(), paramInt, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 3, paramStreetViewPanoramaOptions.getPanoramaId(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 4, paramStreetViewPanoramaOptions.getPosition(), paramInt, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 5, paramStreetViewPanoramaOptions.getRadius(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 6, paramStreetViewPanoramaOptions.zztH());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 7, paramStreetViewPanoramaOptions.zztx());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 8, paramStreetViewPanoramaOptions.zztI());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 9, paramStreetViewPanoramaOptions.zztJ());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 10, paramStreetViewPanoramaOptions.zztt());
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, i);
  }

  public StreetViewPanoramaOptions zzec(Parcel paramParcel)
  {
    Integer localInteger = null;
    byte b1 = 0;
    int i = zza.zzL(paramParcel);
    byte b2 = 0;
    byte b3 = 0;
    byte b4 = 0;
    byte b5 = 0;
    LatLng localLatLng = null;
    String str = null;
    StreetViewPanoramaCamera localStreetViewPanoramaCamera = null;
    int j = 0;
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
        localStreetViewPanoramaCamera = (StreetViewPanoramaCamera)zza.zza(paramParcel, k, StreetViewPanoramaCamera.CREATOR);
        break;
      case 3:
        str = zza.zzo(paramParcel, k);
        break;
      case 4:
        localLatLng = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 5:
        localInteger = zza.zzh(paramParcel, k);
        break;
      case 6:
        b5 = zza.zze(paramParcel, k);
        break;
      case 7:
        b4 = zza.zze(paramParcel, k);
        break;
      case 8:
        b3 = zza.zze(paramParcel, k);
        break;
      case 9:
        b2 = zza.zze(paramParcel, k);
        break;
      case 10:
        b1 = zza.zze(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new StreetViewPanoramaOptions(j, localStreetViewPanoramaCamera, str, localLatLng, localInteger, b5, b4, b3, b2, b1);
  }

  public StreetViewPanoramaOptions[] zzgh(int paramInt)
  {
    return new StreetViewPanoramaOptions[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.zzb
 * JD-Core Version:    0.6.2
 */