package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<NearbyAlertRequest>
{
  static void zza(NearbyAlertRequest paramNearbyAlertRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramNearbyAlertRequest.zzsO());
    zzb.zzc(paramParcel, 1000, paramNearbyAlertRequest.getVersionCode());
    zzb.zzc(paramParcel, 2, paramNearbyAlertRequest.zzsR());
    zzb.zza(paramParcel, 3, paramNearbyAlertRequest.zzsS(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public NearbyAlertRequest zzdM(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
    int k = -1;
    PlaceFilter localPlaceFilter = null;
    int m = 0;
    while (paramParcel.dataPosition() < j)
    {
      int n = zza.zzK(paramParcel);
      switch (zza.zzaV(n))
      {
      default:
        zza.zzb(paramParcel, n);
        break;
      case 1:
        i = zza.zzg(paramParcel, n);
        break;
      case 1000:
        m = zza.zzg(paramParcel, n);
        break;
      case 2:
        k = zza.zzg(paramParcel, n);
        break;
      case 3:
        localPlaceFilter = (PlaceFilter)zza.zza(paramParcel, n, PlaceFilter.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new NearbyAlertRequest(m, i, k, localPlaceFilter);
  }

  public NearbyAlertRequest[] zzfP(int paramInt)
  {
    return new NearbyAlertRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.zzc
 * JD-Core Version:    0.6.2
 */