package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzl
  implements Parcelable.Creator<LocationRequestUpdateData>
{
  static void zza(LocationRequestUpdateData paramLocationRequestUpdateData, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramLocationRequestUpdateData.zzaro);
    zzb.zzc(paramParcel, 1000, paramLocationRequestUpdateData.getVersionCode());
    zzb.zza(paramParcel, 2, paramLocationRequestUpdateData.zzarp, paramInt, false);
    zzb.zza(paramParcel, 3, paramLocationRequestUpdateData.zzsK(), false);
    zzb.zza(paramParcel, 4, paramLocationRequestUpdateData.mPendingIntent, paramInt, false);
    zzb.zza(paramParcel, 5, paramLocationRequestUpdateData.zzsL(), false);
    zzb.zzH(paramParcel, i);
  }

  public LocationRequestUpdateData zzdI(Parcel paramParcel)
  {
    IBinder localIBinder1 = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    int k = 1;
    PendingIntent localPendingIntent = null;
    IBinder localIBinder2 = null;
    LocationRequestInternal localLocationRequestInternal = null;
    while (paramParcel.dataPosition() < i)
    {
      int m = zza.zzK(paramParcel);
      switch (zza.zzaV(m))
      {
      default:
        zza.zzb(paramParcel, m);
        break;
      case 1:
        k = zza.zzg(paramParcel, m);
        break;
      case 1000:
        j = zza.zzg(paramParcel, m);
        break;
      case 2:
        localLocationRequestInternal = (LocationRequestInternal)zza.zza(paramParcel, m, LocationRequestInternal.CREATOR);
        break;
      case 3:
        localIBinder2 = zza.zzp(paramParcel, m);
        break;
      case 4:
        localPendingIntent = (PendingIntent)zza.zza(paramParcel, m, PendingIntent.CREATOR);
        break;
      case 5:
        localIBinder1 = zza.zzp(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LocationRequestUpdateData(j, k, localLocationRequestInternal, localIBinder2, localPendingIntent, localIBinder1);
  }

  public LocationRequestUpdateData[] zzfJ(int paramInt)
  {
    return new LocationRequestUpdateData[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzl
 * JD-Core Version:    0.6.2
 */