package com.google.android.gms.location;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.List;

public class LocationResultCreator
  implements Parcelable.Creator<LocationResult>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(LocationResult paramLocationResult, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramLocationResult.getLocations(), false);
    zzb.zzc(paramParcel, 1000, paramLocationResult.getVersionCode());
    zzb.zzH(paramParcel, i);
  }

  public LocationResult createFromParcel(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    Object localObject = LocationResult.zzaqj;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        break;
      case 1:
        localObject = zza.zzc(paramParcel, k, Location.CREATOR);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LocationResult(j, (List)localObject);
  }

  public LocationResult[] newArray(int paramInt)
  {
    return new LocationResult[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationResultCreator
 * JD-Core Version:    0.6.2
 */