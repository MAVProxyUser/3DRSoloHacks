package com.google.android.gms.common.data;

import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zze
  implements Parcelable.Creator<DataHolder>
{
  static void zza(DataHolder paramDataHolder, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramDataHolder.zzlr(), false);
    zzb.zzc(paramParcel, 1000, paramDataHolder.getVersionCode());
    zzb.zza(paramParcel, 2, paramDataHolder.zzls(), paramInt, false);
    zzb.zzc(paramParcel, 3, paramDataHolder.getStatusCode());
    zzb.zza(paramParcel, 4, paramDataHolder.zzlm(), false);
    zzb.zzH(paramParcel, i);
  }

  public DataHolder zzC(Parcel paramParcel)
  {
    int i = 0;
    Bundle localBundle = null;
    int j = zza.zzL(paramParcel);
    CursorWindow[] arrayOfCursorWindow = null;
    String[] arrayOfString = null;
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
        arrayOfString = zza.zzA(paramParcel, m);
        break;
      case 1000:
        k = zza.zzg(paramParcel, m);
        break;
      case 2:
        arrayOfCursorWindow = (CursorWindow[])zza.zzb(paramParcel, m, CursorWindow.CREATOR);
        break;
      case 3:
        i = zza.zzg(paramParcel, m);
        break;
      case 4:
        localBundle = zza.zzq(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    DataHolder localDataHolder = new DataHolder(k, arrayOfString, arrayOfCursorWindow, i, localBundle);
    localDataHolder.zzlq();
    return localDataHolder;
  }

  public DataHolder[] zzaF(int paramInt)
  {
    return new DataHolder[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zze
 * JD-Core Version:    0.6.2
 */