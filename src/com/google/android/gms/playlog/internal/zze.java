package com.google.android.gms.playlog.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
  implements Parcelable.Creator<PlayLoggerContext>
{
  static void zza(PlayLoggerContext paramPlayLoggerContext, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramPlayLoggerContext.versionCode);
    zzb.zza(paramParcel, 2, paramPlayLoggerContext.packageName, false);
    zzb.zzc(paramParcel, 3, paramPlayLoggerContext.zzayB);
    zzb.zzc(paramParcel, 4, paramPlayLoggerContext.zzayC);
    zzb.zza(paramParcel, 5, paramPlayLoggerContext.zzayD, false);
    zzb.zza(paramParcel, 6, paramPlayLoggerContext.zzayE, false);
    zzb.zza(paramParcel, 7, paramPlayLoggerContext.zzayF);
    zzb.zza(paramParcel, 8, paramPlayLoggerContext.zzayG, false);
    zzb.zza(paramParcel, 9, paramPlayLoggerContext.zzayH);
    zzb.zzH(paramParcel, i);
  }

  public PlayLoggerContext zzeP(Parcel paramParcel)
  {
    String str1 = null;
    boolean bool1 = false;
    int i = zza.zzL(paramParcel);
    boolean bool2 = true;
    String str2 = null;
    String str3 = null;
    int j = 0;
    int k = 0;
    String str4 = null;
    int m = 0;
    while (paramParcel.dataPosition() < i)
    {
      int n = zza.zzK(paramParcel);
      switch (zza.zzaV(n))
      {
      default:
        zza.zzb(paramParcel, n);
        break;
      case 1:
        m = zza.zzg(paramParcel, n);
        break;
      case 2:
        str4 = zza.zzo(paramParcel, n);
        break;
      case 3:
        k = zza.zzg(paramParcel, n);
        break;
      case 4:
        j = zza.zzg(paramParcel, n);
        break;
      case 5:
        str3 = zza.zzo(paramParcel, n);
        break;
      case 6:
        str2 = zza.zzo(paramParcel, n);
        break;
      case 7:
        bool2 = zza.zzc(paramParcel, n);
        break;
      case 8:
        str1 = zza.zzo(paramParcel, n);
        break;
      case 9:
        bool1 = zza.zzc(paramParcel, n);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new PlayLoggerContext(m, str4, k, j, str3, str2, bool2, str1, bool1);
  }

  public PlayLoggerContext[] zzhh(int paramInt)
  {
    return new PlayLoggerContext[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.zze
 * JD-Core Version:    0.6.2
 */