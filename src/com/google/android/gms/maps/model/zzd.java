package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzd
  implements Parcelable.Creator<LatLngBounds>
{
  static void zza(LatLngBounds paramLatLngBounds, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramLatLngBounds.getVersionCode());
    zzb.zza(paramParcel, 2, paramLatLngBounds.southwest, paramInt, false);
    zzb.zza(paramParcel, 3, paramLatLngBounds.northeast, paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public LatLngBounds zzeh(Parcel paramParcel)
  {
    Object localObject1 = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    Object localObject2 = null;
    if (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      Object localObject3;
      Object localObject4;
      int m;
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        localObject3 = localObject1;
        localObject4 = localObject2;
        m = j;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        j = m;
        localObject2 = localObject4;
        localObject1 = localObject3;
        break;
        int n = zza.zzg(paramParcel, k);
        Object localObject5 = localObject1;
        localObject4 = localObject2;
        m = n;
        localObject3 = localObject5;
        continue;
        LatLng localLatLng = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        m = j;
        localObject3 = localObject1;
        localObject4 = localLatLng;
        continue;
        localObject3 = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        localObject4 = localObject2;
        m = j;
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LatLngBounds(j, localObject2, localObject1);
  }

  public LatLngBounds[] zzgm(int paramInt)
  {
    return new LatLngBounds[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.zzd
 * JD-Core Version:    0.6.2
 */