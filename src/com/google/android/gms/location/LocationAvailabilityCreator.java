package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class LocationAvailabilityCreator
  implements Parcelable.Creator<LocationAvailability>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(LocationAvailability paramLocationAvailability, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramLocationAvailability.zzaqa);
    zzb.zzc(paramParcel, 1000, paramLocationAvailability.getVersionCode());
    zzb.zzc(paramParcel, 2, paramLocationAvailability.zzaqb);
    zzb.zza(paramParcel, 3, paramLocationAvailability.zzaqc);
    zzb.zzc(paramParcel, 4, paramLocationAvailability.zzaqd);
    zzb.zzH(paramParcel, i);
  }

  public LocationAvailability createFromParcel(Parcel paramParcel)
  {
    int i = 1;
    int j = zza.zzL(paramParcel);
    int k = 0;
    int m = 1000;
    long l = 0L;
    int n = i;
    while (paramParcel.dataPosition() < j)
    {
      int i1 = zza.zzK(paramParcel);
      switch (zza.zzaV(i1))
      {
      default:
        zza.zzb(paramParcel, i1);
        break;
      case 1:
        n = zza.zzg(paramParcel, i1);
        break;
      case 1000:
        k = zza.zzg(paramParcel, i1);
        break;
      case 2:
        i = zza.zzg(paramParcel, i1);
        break;
      case 3:
        l = zza.zzi(paramParcel, i1);
        break;
      case 4:
        m = zza.zzg(paramParcel, i1);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new LocationAvailability(k, m, n, i, l);
  }

  public LocationAvailability[] newArray(int paramInt)
  {
    return new LocationAvailability[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationAvailabilityCreator
 * JD-Core Version:    0.6.2
 */