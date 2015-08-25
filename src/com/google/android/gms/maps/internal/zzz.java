package com.google.android.gms.maps.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzz
  implements Parcelable.Creator<Point>
{
  static void zza(Point paramPoint, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramPoint.getVersionCode());
    zzb.zza(paramParcel, 2, paramPoint.zztP(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public Point zzed(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    android.graphics.Point localPoint = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        break;
      case 1:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        localPoint = (android.graphics.Point)zza.zza(paramParcel, k, android.graphics.Point.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new Point(j, localPoint);
  }

  public Point[] zzgi(int paramInt)
  {
    return new Point[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzz
 * JD-Core Version:    0.6.2
 */