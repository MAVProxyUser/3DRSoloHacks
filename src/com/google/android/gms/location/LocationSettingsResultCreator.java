package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class LocationSettingsResultCreator
  implements Parcelable.Creator<LocationSettingsResult>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(LocationSettingsResult paramLocationSettingsResult, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramLocationSettingsResult.getStatus(), paramInt, false);
    zzb.zzc(paramParcel, 1000, paramLocationSettingsResult.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationSettingsResult.getLocationSettingsStates(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public LocationSettingsResult createFromParcel(Parcel paramParcel)
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
      case 1000:
      case 2:
      }
      while (true)
      {
        j = m;
        localObject2 = localObject4;
        localObject1 = localObject3;
        break;
        Status localStatus = (Status)zza.zza(paramParcel, k, Status.CREATOR);
        m = j;
        localObject3 = localObject1;
        localObject4 = localStatus;
        continue;
        int n = zza.zzg(paramParcel, k);
        Object localObject5 = localObject1;
        localObject4 = localObject2;
        m = n;
        localObject3 = localObject5;
        continue;
        localObject3 = (LocationSettingsStates)zza.zza(paramParcel, k, LocationSettingsStates.CREATOR);
        localObject4 = localObject2;
        m = j;
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LocationSettingsResult(j, localObject2, localObject1);
  }

  public LocationSettingsResult[] newArray(int paramInt)
  {
    return new LocationSettingsResult[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationSettingsResultCreator
 * JD-Core Version:    0.6.2
 */