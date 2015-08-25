package com.google.android.gms.maps.model;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzk
  implements Parcelable.Creator<StreetViewPanoramaLocation>
{
  static void zza(StreetViewPanoramaLocation paramStreetViewPanoramaLocation, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramStreetViewPanoramaLocation.getVersionCode());
    zzb.zza(paramParcel, 2, paramStreetViewPanoramaLocation.links, paramInt, false);
    zzb.zza(paramParcel, 3, paramStreetViewPanoramaLocation.position, paramInt, false);
    zzb.zza(paramParcel, 4, paramStreetViewPanoramaLocation.panoId, false);
    zzb.zzH(paramParcel, i);
  }

  public StreetViewPanoramaLocation zzeo(Parcel paramParcel)
  {
    Object localObject1 = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    Object localObject2 = null;
    Object localObject3 = null;
    if (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      Object localObject4;
      Object localObject5;
      Object localObject6;
      int m;
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        localObject4 = localObject1;
        localObject5 = localObject2;
        localObject6 = localObject3;
        m = j;
      case 1:
      case 2:
      case 3:
      case 4:
      }
      while (true)
      {
        j = m;
        localObject3 = localObject6;
        localObject2 = localObject5;
        localObject1 = localObject4;
        break;
        int n = zza.zzg(paramParcel, k);
        Object localObject9 = localObject1;
        localObject5 = localObject2;
        localObject6 = localObject3;
        m = n;
        localObject4 = localObject9;
        continue;
        StreetViewPanoramaLink[] arrayOfStreetViewPanoramaLink = (StreetViewPanoramaLink[])zza.zzb(paramParcel, k, StreetViewPanoramaLink.CREATOR);
        m = j;
        Object localObject8 = localObject2;
        localObject6 = arrayOfStreetViewPanoramaLink;
        localObject4 = localObject1;
        localObject5 = localObject8;
        continue;
        LatLng localLatLng = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        localObject6 = localObject3;
        m = j;
        Object localObject7 = localObject1;
        localObject5 = localLatLng;
        localObject4 = localObject7;
        continue;
        localObject4 = zza.zzo(paramParcel, k);
        localObject5 = localObject2;
        localObject6 = localObject3;
        m = j;
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new StreetViewPanoramaLocation(j, localObject3, localObject2, localObject1);
  }

  public StreetViewPanoramaLocation[] zzgt(int paramInt)
  {
    return new StreetViewPanoramaLocation[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.zzk
 * JD-Core Version:    0.6.2
 */