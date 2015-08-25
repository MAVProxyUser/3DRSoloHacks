package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzm
  implements Parcelable.Creator<ParcelableGeofence>
{
  static void zza(ParcelableGeofence paramParcelableGeofence, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramParcelableGeofence.getRequestId(), false);
    zzb.zzc(paramParcel, 1000, paramParcelableGeofence.getVersionCode());
    zzb.zza(paramParcel, 2, paramParcelableGeofence.getExpirationTime());
    zzb.zza(paramParcel, 3, paramParcelableGeofence.zzsM());
    zzb.zza(paramParcel, 4, paramParcelableGeofence.getLatitude());
    zzb.zza(paramParcel, 5, paramParcelableGeofence.getLongitude());
    zzb.zza(paramParcel, 6, paramParcelableGeofence.zzsN());
    zzb.zzc(paramParcel, 7, paramParcelableGeofence.zzsO());
    zzb.zzc(paramParcel, 8, paramParcelableGeofence.getNotificationResponsiveness());
    zzb.zzc(paramParcel, 9, paramParcelableGeofence.zzsP());
    zzb.zzH(paramParcel, i);
  }

  public ParcelableGeofence zzdJ(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    String str = null;
    int k = 0;
    short s = 0;
    double d1 = 0.0D;
    double d2 = 0.0D;
    float f = 0.0F;
    long l = 0L;
    int m = 0;
    int n = -1;
    while (paramParcel.dataPosition() < i)
    {
      int i1 = zza.zzK(paramParcel);
      switch (zza.zzaV(i1))
      {
      default:
        zza.zzb(paramParcel, i1);
        break;
      case 1:
        str = zza.zzo(paramParcel, i1);
        break;
      case 1000:
        j = zza.zzg(paramParcel, i1);
        break;
      case 2:
        l = zza.zzi(paramParcel, i1);
        break;
      case 3:
        s = zza.zzf(paramParcel, i1);
        break;
      case 4:
        d1 = zza.zzm(paramParcel, i1);
        break;
      case 5:
        d2 = zza.zzm(paramParcel, i1);
        break;
      case 6:
        f = zza.zzl(paramParcel, i1);
        break;
      case 7:
        k = zza.zzg(paramParcel, i1);
        break;
      case 8:
        m = zza.zzg(paramParcel, i1);
        break;
      case 9:
        n = zza.zzg(paramParcel, i1);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new ParcelableGeofence(j, str, k, s, d1, d2, f, l, m, n);
  }

  public ParcelableGeofence[] zzfM(int paramInt)
  {
    return new ParcelableGeofence[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzm
 * JD-Core Version:    0.6.2
 */