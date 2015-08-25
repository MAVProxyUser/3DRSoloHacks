package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<BitmapDescriptorParcelable>
{
  static void zza(BitmapDescriptorParcelable paramBitmapDescriptorParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramBitmapDescriptorParcelable.getVersionCode());
    zzb.zza(paramParcel, 2, paramBitmapDescriptorParcelable.getType());
    zzb.zza(paramParcel, 3, paramBitmapDescriptorParcelable.zztV(), false);
    zzb.zza(paramParcel, 4, paramBitmapDescriptorParcelable.getBitmap(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public BitmapDescriptorParcelable zzet(Parcel paramParcel)
  {
    Bitmap localBitmap = null;
    byte b = 0;
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    Bundle localBundle = null;
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
        b = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, k);
        break;
      case 3:
        localBundle = com.google.android.gms.common.internal.safeparcel.zza.zzq(paramParcel, k);
        break;
      case 4:
        localBitmap = (Bitmap)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, k, Bitmap.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new BitmapDescriptorParcelable(j, b, localBundle, localBitmap);
  }

  public BitmapDescriptorParcelable[] zzgy(int paramInt)
  {
    return new BitmapDescriptorParcelable[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zza
 * JD-Core Version:    0.6.2
 */