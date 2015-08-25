package com.google.android.gms.common;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class ConnectionResultCreator
  implements Parcelable.Creator<ConnectionResult>
{
  public static final int CONTENT_DESCRIPTION;

  static void zza(ConnectionResult paramConnectionResult, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramConnectionResult.zzzH);
    zzb.zzc(paramParcel, 2, paramConnectionResult.getErrorCode());
    zzb.zza(paramParcel, 3, paramConnectionResult.getResolution(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public ConnectionResult createFromParcel(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
    PendingIntent localPendingIntent = null;
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
        localPendingIntent = (PendingIntent)zza.zza(paramParcel, m, PendingIntent.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new ConnectionResult(k, i, localPendingIntent);
  }

  public ConnectionResult[] newArray(int paramInt)
  {
    return new ConnectionResult[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.ConnectionResultCreator
 * JD-Core Version:    0.6.2
 */