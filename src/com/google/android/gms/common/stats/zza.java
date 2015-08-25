package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<ConnectionEvent>
{
  static void zza(ConnectionEvent paramConnectionEvent, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramConnectionEvent.zzzH);
    zzb.zza(paramParcel, 2, paramConnectionEvent.getTimeMillis());
    zzb.zza(paramParcel, 3, paramConnectionEvent.zzmS(), false);
    zzb.zza(paramParcel, 4, paramConnectionEvent.zzmT(), false);
    zzb.zza(paramParcel, 5, paramConnectionEvent.zzmU(), false);
    zzb.zza(paramParcel, 6, paramConnectionEvent.zzmV(), false);
    zzb.zza(paramParcel, 7, paramConnectionEvent.zzmW(), false);
    zzb.zza(paramParcel, 8, paramConnectionEvent.zzmX(), false);
    zzb.zza(paramParcel, 9, paramConnectionEvent.zzmY());
    zzb.zza(paramParcel, 10, paramConnectionEvent.zzna());
    zzb.zza(paramParcel, 11, paramConnectionEvent.zzmZ());
    zzb.zzH(paramParcel, i);
  }

  public ConnectionEvent zzW(Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    int j = 0;
    long l1 = 0L;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    long l2 = 0L;
    long l3 = 0L;
    long l4 = 0L;
    while (paramParcel.dataPosition() < i)
    {
      int k = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(k))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, k);
        break;
      case 1:
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, k);
        break;
      case 2:
        l1 = com.google.android.gms.common.internal.safeparcel.zza.zzi(paramParcel, k);
        break;
      case 3:
        str1 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 4:
        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 5:
        str3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 6:
        str4 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 7:
        str5 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 8:
        str6 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 9:
        l2 = com.google.android.gms.common.internal.safeparcel.zza.zzi(paramParcel, k);
        break;
      case 10:
        l3 = com.google.android.gms.common.internal.safeparcel.zza.zzi(paramParcel, k);
        break;
      case 11:
        l4 = com.google.android.gms.common.internal.safeparcel.zza.zzi(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new ConnectionEvent(j, l1, str1, str2, str3, str4, str5, str6, l2, l3, l4);
  }

  public ConnectionEvent[] zzbg(int paramInt)
  {
    return new ConnectionEvent[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zza
 * JD-Core Version:    0.6.2
 */