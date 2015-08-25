package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<CameraPosition>
{
  static void zza(CameraPosition paramCameraPosition, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramCameraPosition.getVersionCode());
    zzb.zza(paramParcel, 2, paramCameraPosition.target, paramInt, false);
    zzb.zza(paramParcel, 3, paramCameraPosition.zoom);
    zzb.zza(paramParcel, 4, paramCameraPosition.tilt);
    zzb.zza(paramParcel, 5, paramCameraPosition.bearing);
    zzb.zzH(paramParcel, i);
  }

  public CameraPosition zzee(Parcel paramParcel)
  {
    float f1 = 0.0F;
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    int j = 0;
    LatLng localLatLng = null;
    float f2 = 0.0F;
    float f3 = 0.0F;
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
        localLatLng = (LatLng)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 3:
        f3 = com.google.android.gms.common.internal.safeparcel.zza.zzl(paramParcel, k);
        break;
      case 4:
        f2 = com.google.android.gms.common.internal.safeparcel.zza.zzl(paramParcel, k);
        break;
      case 5:
        f1 = com.google.android.gms.common.internal.safeparcel.zza.zzl(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new CameraPosition(j, localLatLng, f3, f2, f1);
  }

  public CameraPosition[] zzgj(int paramInt)
  {
    return new CameraPosition[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.zza
 * JD-Core Version:    0.6.2
 */