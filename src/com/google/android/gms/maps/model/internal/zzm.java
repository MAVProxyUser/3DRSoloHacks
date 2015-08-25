package com.google.android.gms.maps.model.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzm
  implements Parcelable.Creator<MarkerOptionsParcelable>
{
  static void zza(MarkerOptionsParcelable paramMarkerOptionsParcelable, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramMarkerOptionsParcelable.getVersionCode());
    zzb.zza(paramParcel, 2, paramMarkerOptionsParcelable.zztY(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public MarkerOptionsParcelable zzew(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    BitmapDescriptorParcelable localBitmapDescriptorParcelable = null;
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
        localBitmapDescriptorParcelable = (BitmapDescriptorParcelable)zza.zza(paramParcel, k, BitmapDescriptorParcelable.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new MarkerOptionsParcelable(j, localBitmapDescriptorParcelable);
  }

  public MarkerOptionsParcelable[] zzgC(int paramInt)
  {
    return new MarkerOptionsParcelable[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzm
 * JD-Core Version:    0.6.2
 */