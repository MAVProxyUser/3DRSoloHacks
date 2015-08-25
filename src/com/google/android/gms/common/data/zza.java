package com.google.android.gms.common.data;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<BitmapTeleporter>
{
  static void zza(BitmapTeleporter paramBitmapTeleporter, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramBitmapTeleporter.zzzH);
    zzb.zza(paramParcel, 2, paramBitmapTeleporter.zzzI, paramInt, false);
    zzb.zzc(paramParcel, 3, paramBitmapTeleporter.zzMG);
    zzb.zzH(paramParcel, i);
  }

  public BitmapTeleporter zzB(Parcel paramParcel)
  {
    int i = 0;
    int j = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    Object localObject1 = null;
    int k = 0;
    if (paramParcel.dataPosition() < j)
    {
      int m = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      int n;
      Object localObject2;
      int i1;
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(m))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, m);
        n = i;
        localObject2 = localObject1;
        i1 = k;
      case 1:
      case 2:
      case 3:
      }
      while (true)
      {
        k = i1;
        localObject1 = localObject2;
        i = n;
        break;
        int i2 = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        int i3 = i;
        localObject2 = localObject1;
        i1 = i2;
        n = i3;
        continue;
        ParcelFileDescriptor localParcelFileDescriptor = (ParcelFileDescriptor)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, m, ParcelFileDescriptor.CREATOR);
        i1 = k;
        n = i;
        localObject2 = localParcelFileDescriptor;
        continue;
        n = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        localObject2 = localObject1;
        i1 = k;
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new BitmapTeleporter(k, localObject1, i);
  }

  public BitmapTeleporter[] zzaA(int paramInt)
  {
    return new BitmapTeleporter[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zza
 * JD-Core Version:    0.6.2
 */