package com.google.android.gms.location;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class DetectedActivityCreator
  implements Parcelable.Creator<DetectedActivity>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(DetectedActivity paramDetectedActivity, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramDetectedActivity.zzapG);
    zzb.zzc(paramParcel, 1000, paramDetectedActivity.getVersionCode());
    zzb.zzc(paramParcel, 2, paramDetectedActivity.zzapH);
    zzb.zzH(paramParcel, i);
  }

  public DetectedActivity createFromParcel(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
    int k = 0;
    int m = 0;
    while (paramParcel.dataPosition() < j)
    {
      int n = zza.zzK(paramParcel);
      switch (zza.zzaV(n))
      {
      default:
        zza.zzb(paramParcel, n);
        break;
      case 1:
        k = zza.zzg(paramParcel, n);
        break;
      case 1000:
        m = zza.zzg(paramParcel, n);
        break;
      case 2:
        i = zza.zzg(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new DetectedActivity(m, k, i);
  }

  public DetectedActivity[] newArray(int paramInt)
  {
    return new DetectedActivity[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.DetectedActivityCreator
 * JD-Core Version:    0.6.2
 */