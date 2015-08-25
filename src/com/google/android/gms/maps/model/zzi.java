package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzi
  implements Parcelable.Creator<StreetViewPanoramaCamera>
{
  static void zza(StreetViewPanoramaCamera paramStreetViewPanoramaCamera, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramStreetViewPanoramaCamera.getVersionCode());
    zzb.zza(paramParcel, 2, paramStreetViewPanoramaCamera.zoom);
    zzb.zza(paramParcel, 3, paramStreetViewPanoramaCamera.tilt);
    zzb.zza(paramParcel, 4, paramStreetViewPanoramaCamera.bearing);
    zzb.zzH(paramParcel, i);
  }

  public StreetViewPanoramaCamera zzem(Parcel paramParcel)
  {
    float f1 = 0.0F;
    int i = zza.zzL(paramParcel);
    float f2 = 0.0F;
    int j = 0;
    float f3 = 0.0F;
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
        f2 = zza.zzl(paramParcel, k);
        break;
      case 3:
        f3 = zza.zzl(paramParcel, k);
        break;
      case 4:
        f1 = zza.zzl(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new StreetViewPanoramaCamera(j, f2, f3, f1);
  }

  public StreetViewPanoramaCamera[] zzgr(int paramInt)
  {
    return new StreetViewPanoramaCamera[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.zzi
 * JD-Core Version:    0.6.2
 */