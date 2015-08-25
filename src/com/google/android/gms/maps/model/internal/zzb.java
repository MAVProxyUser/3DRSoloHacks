package com.google.android.gms.maps.model.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;

public class zzb
  implements Parcelable.Creator<CameraUpdateParcelable>
{
  static void zza(CameraUpdateParcelable paramCameraUpdateParcelable, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzM(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1, paramCameraUpdateParcelable.getVersionCode());
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 2, paramCameraUpdateParcelable.getType());
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 3, paramCameraUpdateParcelable.zztV(), false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, i);
  }

  public CameraUpdateParcelable zzeu(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
    Bundle localBundle = null;
    int k = 0;
    while (paramParcel.dataPosition() < j)
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
      case 2:
        i = zza.zzg(paramParcel, m);
        break;
      case 3:
        localBundle = zza.zzq(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new CameraUpdateParcelable(k, i, localBundle);
  }

  public CameraUpdateParcelable[] zzgz(int paramInt)
  {
    return new CameraUpdateParcelable[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzb
 * JD-Core Version:    0.6.2
 */