package com.google.android.gms.location.copresence.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<CopresenceApiOptions>
{
  static void zza(CopresenceApiOptions paramCopresenceApiOptions, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramCopresenceApiOptions.zzzH);
    zzb.zza(paramParcel, 2, paramCopresenceApiOptions.zzaqy);
    zzb.zzH(paramParcel, i);
  }

  public CopresenceApiOptions zzdF(Parcel paramParcel)
  {
    boolean bool = false;
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    int j = 0;
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
        bool = com.google.android.gms.common.internal.safeparcel.zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new CopresenceApiOptions(j, bool);
  }

  public CopresenceApiOptions[] zzfG(int paramInt)
  {
    return new CopresenceApiOptions[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.copresence.internal.zza
 * JD-Core Version:    0.6.2
 */