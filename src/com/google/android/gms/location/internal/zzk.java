package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.LocationRequest;
import java.util.List;

public class zzk
  implements Parcelable.Creator<LocationRequestInternal>
{
  static void zza(LocationRequestInternal paramLocationRequestInternal, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramLocationRequestInternal.zzaft, paramInt, false);
    zzb.zzc(paramParcel, 1000, paramLocationRequestInternal.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationRequestInternal.zzark);
    zzb.zza(paramParcel, 3, paramLocationRequestInternal.zzarl);
    zzb.zza(paramParcel, 4, paramLocationRequestInternal.zzarm);
    zzb.zzc(paramParcel, 5, paramLocationRequestInternal.zzarn, false);
    zzb.zza(paramParcel, 6, paramLocationRequestInternal.mTag, false);
    zzb.zzH(paramParcel, i);
  }

  public LocationRequestInternal zzdH(Parcel paramParcel)
  {
    String str = null;
    boolean bool1 = true;
    boolean bool2 = false;
    int i = zza.zzL(paramParcel);
    Object localObject = LocationRequestInternal.zzarj;
    boolean bool3 = bool1;
    LocationRequest localLocationRequest = null;
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
        localLocationRequest = (LocationRequest)zza.zza(paramParcel, k, LocationRequest.CREATOR);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        bool2 = zza.zzc(paramParcel, k);
        break;
      case 3:
        bool3 = zza.zzc(paramParcel, k);
        break;
      case 4:
        bool1 = zza.zzc(paramParcel, k);
        break;
      case 5:
        localObject = zza.zzc(paramParcel, k, ClientIdentity.CREATOR);
        break;
      case 6:
        str = zza.zzo(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LocationRequestInternal(j, localLocationRequest, bool2, bool3, bool1, (List)localObject, str);
  }

  public LocationRequestInternal[] zzfI(int paramInt)
  {
    return new LocationRequestInternal[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzk
 * JD-Core Version:    0.6.2
 */