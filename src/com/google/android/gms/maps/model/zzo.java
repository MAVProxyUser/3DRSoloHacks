package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzo
  implements Parcelable.Creator<VisibleRegion>
{
  static void zza(VisibleRegion paramVisibleRegion, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramVisibleRegion.getVersionCode());
    zzb.zza(paramParcel, 2, paramVisibleRegion.nearLeft, paramInt, false);
    zzb.zza(paramParcel, 3, paramVisibleRegion.nearRight, paramInt, false);
    zzb.zza(paramParcel, 4, paramVisibleRegion.farLeft, paramInt, false);
    zzb.zza(paramParcel, 5, paramVisibleRegion.farRight, paramInt, false);
    zzb.zza(paramParcel, 6, paramVisibleRegion.latLngBounds, paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public VisibleRegion zzes(Parcel paramParcel)
  {
    LatLngBounds localLatLngBounds = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    LatLng localLatLng1 = null;
    LatLng localLatLng2 = null;
    LatLng localLatLng3 = null;
    LatLng localLatLng4 = null;
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
        localLatLng4 = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 3:
        localLatLng3 = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 4:
        localLatLng2 = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 5:
        localLatLng1 = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 6:
        localLatLngBounds = (LatLngBounds)zza.zza(paramParcel, k, LatLngBounds.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new VisibleRegion(j, localLatLng4, localLatLng3, localLatLng2, localLatLng1, localLatLngBounds);
  }

  public VisibleRegion[] zzgx(int paramInt)
  {
    return new VisibleRegion[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.zzo
 * JD-Core Version:    0.6.2
 */