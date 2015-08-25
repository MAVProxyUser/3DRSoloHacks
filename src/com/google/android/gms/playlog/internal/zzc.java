package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<LogEvent>
{
  static void zza(LogEvent paramLogEvent, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramLogEvent.versionCode);
    zzb.zza(paramParcel, 2, paramLogEvent.zzayr);
    zzb.zza(paramParcel, 3, paramLogEvent.tag, false);
    zzb.zza(paramParcel, 4, paramLogEvent.zzays, false);
    zzb.zza(paramParcel, 5, paramLogEvent.zzayt, false);
    zzb.zzH(paramParcel, i);
  }

  public LogEvent zzeO(Parcel paramParcel)
  {
    Bundle localBundle = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    long l = 0L;
    byte[] arrayOfByte = null;
    String str = null;
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
        l = zza.zzi(paramParcel, k);
        break;
      case 3:
        str = zza.zzo(paramParcel, k);
        break;
      case 4:
        arrayOfByte = zza.zzr(paramParcel, k);
        break;
      case 5:
        localBundle = zza.zzq(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new LogEvent(j, l, str, arrayOfByte, localBundle);
  }

  public LogEvent[] zzhg(int paramInt)
  {
    return new LogEvent[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.zzc
 * JD-Core Version:    0.6.2
 */