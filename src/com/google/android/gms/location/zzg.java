package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzg
  implements Parcelable.Creator<LocationSettingsStates>
{
  static void zza(LocationSettingsStates paramLocationSettingsStates, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramLocationSettingsStates.isGpsUsable());
    zzb.zzc(paramParcel, 1000, paramLocationSettingsStates.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationSettingsStates.isNetworkLocationUsable());
    zzb.zza(paramParcel, 3, paramLocationSettingsStates.isBleUsable());
    zzb.zza(paramParcel, 4, paramLocationSettingsStates.isGpsPresent());
    zzb.zza(paramParcel, 5, paramLocationSettingsStates.isNetworkLocationPresent());
    zzb.zza(paramParcel, 6, paramLocationSettingsStates.isBlePresent());
    zzb.zza(paramParcel, 7, paramLocationSettingsStates.zzsE());
    zzb.zzH(paramParcel, i);
  }

  public LocationSettingsStates zzdE(Parcel paramParcel)
  {
    boolean bool1 = false;
    int i = zza.zzL(paramParcel);
    boolean bool2 = false;
    boolean bool3 = false;
    boolean bool4 = false;
    boolean bool5 = false;
    boolean bool6 = false;
    boolean bool7 = false;
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
        bool7 = zza.zzc(paramParcel, k);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        bool6 = zza.zzc(paramParcel, k);
        break;
      case 3:
        bool5 = zza.zzc(paramParcel, k);
        break;
      case 4:
        bool4 = zza.zzc(paramParcel, k);
        break;
      case 5:
        bool3 = zza.zzc(paramParcel, k);
        break;
      case 6:
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 7:
        bool1 = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LocationSettingsStates(j, bool7, bool6, bool5, bool4, bool3, bool2, bool1);
  }

  public LocationSettingsStates[] zzfD(int paramInt)
  {
    return new LocationSettingsStates[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.zzg
 * JD-Core Version:    0.6.2
 */