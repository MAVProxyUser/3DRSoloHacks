package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzn
  implements Parcelable.Creator<TileOverlayOptions>
{
  static void zza(TileOverlayOptions paramTileOverlayOptions, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramTileOverlayOptions.getVersionCode());
    zzb.zza(paramParcel, 2, paramTileOverlayOptions.zztU(), false);
    zzb.zza(paramParcel, 3, paramTileOverlayOptions.isVisible());
    zzb.zza(paramParcel, 4, paramTileOverlayOptions.getZIndex());
    zzb.zza(paramParcel, 5, paramTileOverlayOptions.getFadeIn());
    zzb.zzH(paramParcel, i);
  }

  public TileOverlayOptions zzer(Parcel paramParcel)
  {
    boolean bool1 = false;
    int i = zza.zzL(paramParcel);
    IBinder localIBinder = null;
    float f = 0.0F;
    boolean bool2 = true;
    int j = 0;
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
        localIBinder = zza.zzp(paramParcel, k);
        break;
      case 3:
        bool1 = zza.zzc(paramParcel, k);
        break;
      case 4:
        f = zza.zzl(paramParcel, k);
        break;
      case 5:
        bool2 = zza.zzc(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new TileOverlayOptions(j, localIBinder, bool1, f, bool2);
  }

  public TileOverlayOptions[] zzgw(int paramInt)
  {
    return new TileOverlayOptions[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.zzn
 * JD-Core Version:    0.6.2
 */