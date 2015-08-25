package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
  implements Parcelable.Creator<PlaceRequest>
{
  static void zza(PlaceRequest paramPlaceRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1000, paramPlaceRequest.zzzH);
    zzb.zza(paramParcel, 2, paramPlaceRequest.zzsS(), paramInt, false);
    zzb.zza(paramParcel, 3, paramPlaceRequest.getInterval());
    zzb.zzc(paramParcel, 4, paramPlaceRequest.getPriority());
    zzb.zza(paramParcel, 5, paramPlaceRequest.getExpirationTime());
    zzb.zzH(paramParcel, i);
  }

  public PlaceRequest zzdO(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    PlaceFilter localPlaceFilter = null;
    long l1 = PlaceRequest.zzarR;
    int k = 102;
    long l2 = 9223372036854775807L;
    while (paramParcel.dataPosition() < i)
    {
      int m = zza.zzK(paramParcel);
      switch (zza.zzaV(m))
      {
      default:
        zza.zzb(paramParcel, m);
        break;
      case 1000:
        j = zza.zzg(paramParcel, m);
        break;
      case 2:
        localPlaceFilter = (PlaceFilter)zza.zza(paramParcel, m, PlaceFilter.CREATOR);
        break;
      case 3:
        l1 = zza.zzi(paramParcel, m);
        break;
      case 4:
        k = zza.zzg(paramParcel, m);
        break;
      case 5:
        l2 = zza.zzi(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new PlaceRequest(j, localPlaceFilter, l1, k, l2);
  }

  public PlaceRequest[] zzfS(int paramInt)
  {
    return new PlaceRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.zze
 * JD-Core Version:    0.6.2
 */