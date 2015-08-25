package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<GroundOverlayOptions>
{
  static void zza(GroundOverlayOptions paramGroundOverlayOptions, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramGroundOverlayOptions.getVersionCode());
    zzb.zza(paramParcel, 2, paramGroundOverlayOptions.zztR(), false);
    zzb.zza(paramParcel, 3, paramGroundOverlayOptions.getLocation(), paramInt, false);
    zzb.zza(paramParcel, 4, paramGroundOverlayOptions.getWidth());
    zzb.zza(paramParcel, 5, paramGroundOverlayOptions.getHeight());
    zzb.zza(paramParcel, 6, paramGroundOverlayOptions.getBounds(), paramInt, false);
    zzb.zza(paramParcel, 7, paramGroundOverlayOptions.getBearing());
    zzb.zza(paramParcel, 8, paramGroundOverlayOptions.getZIndex());
    zzb.zza(paramParcel, 9, paramGroundOverlayOptions.isVisible());
    zzb.zza(paramParcel, 10, paramGroundOverlayOptions.getTransparency());
    zzb.zza(paramParcel, 11, paramGroundOverlayOptions.getAnchorU());
    zzb.zza(paramParcel, 12, paramGroundOverlayOptions.getAnchorV());
    zzb.zzH(paramParcel, i);
  }

  public GroundOverlayOptions zzeg(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    IBinder localIBinder = null;
    LatLng localLatLng = null;
    float f1 = 0.0F;
    float f2 = 0.0F;
    LatLngBounds localLatLngBounds = null;
    float f3 = 0.0F;
    float f4 = 0.0F;
    boolean bool = false;
    float f5 = 0.0F;
    float f6 = 0.0F;
    float f7 = 0.0F;
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
        localLatLng = (LatLng)zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 4:
        f1 = zza.zzl(paramParcel, k);
        break;
      case 5:
        f2 = zza.zzl(paramParcel, k);
        break;
      case 6:
        localLatLngBounds = (LatLngBounds)zza.zza(paramParcel, k, LatLngBounds.CREATOR);
        break;
      case 7:
        f3 = zza.zzl(paramParcel, k);
        break;
      case 8:
        f4 = zza.zzl(paramParcel, k);
        break;
      case 9:
        bool = zza.zzc(paramParcel, k);
        break;
      case 10:
        f5 = zza.zzl(paramParcel, k);
        break;
      case 11:
        f6 = zza.zzl(paramParcel, k);
        break;
      case 12:
        f7 = zza.zzl(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new GroundOverlayOptions(j, localIBinder, localLatLng, f1, f2, localLatLngBounds, f3, f4, bool, f5, f6, f7);
  }

  public GroundOverlayOptions[] zzgl(int paramInt)
  {
    return new GroundOverlayOptions[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.zzc
 * JD-Core Version:    0.6.2
 */