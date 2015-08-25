package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzf
  implements Parcelable.Creator<LocationSettingsRequest>
{
  static void zza(LocationSettingsRequest paramLocationSettingsRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramLocationSettingsRequest.zzpu(), false);
    zzb.zzc(paramParcel, 1000, paramLocationSettingsRequest.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationSettingsRequest.zzsB());
    zzb.zza(paramParcel, 3, paramLocationSettingsRequest.zzsC());
    zzb.zza(paramParcel, 4, paramLocationSettingsRequest.zzsD());
    zzb.zzH(paramParcel, i);
  }

  public LocationSettingsRequest zzdD(Parcel paramParcel)
  {
    boolean bool1 = false;
    int i = zza.zzL(paramParcel);
    ArrayList localArrayList = null;
    boolean bool2 = false;
    boolean bool3 = false;
    int j = 0;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        break;
      case 1:
        localArrayList = zza.zzc(paramParcel, k, LocationRequest.CREATOR);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        bool3 = zza.zzc(paramParcel, k);
        break;
      case 3:
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 4:
        bool1 = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LocationSettingsRequest(j, localArrayList, bool3, bool2, bool1);
  }

  public LocationSettingsRequest[] zzfC(int paramInt)
  {
    return new LocationSettingsRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.zzf
 * JD-Core Version:    0.6.2
 */