package com.google.android.gms.maps;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.CameraPosition;

public class zza
  implements Parcelable.Creator<GoogleMapOptions>
{
  static void zza(GoogleMapOptions paramGoogleMapOptions, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramGoogleMapOptions.getVersionCode());
    zzb.zza(paramParcel, 2, paramGoogleMapOptions.zzts());
    zzb.zza(paramParcel, 3, paramGoogleMapOptions.zztt());
    zzb.zzc(paramParcel, 4, paramGoogleMapOptions.getMapType());
    zzb.zza(paramParcel, 5, paramGoogleMapOptions.getCamera(), paramInt, false);
    zzb.zza(paramParcel, 6, paramGoogleMapOptions.zztu());
    zzb.zza(paramParcel, 7, paramGoogleMapOptions.zztv());
    zzb.zza(paramParcel, 8, paramGoogleMapOptions.zztw());
    zzb.zza(paramParcel, 9, paramGoogleMapOptions.zztx());
    zzb.zza(paramParcel, 10, paramGoogleMapOptions.zzty());
    zzb.zza(paramParcel, 11, paramGoogleMapOptions.zztz());
    zzb.zza(paramParcel, 12, paramGoogleMapOptions.zztA());
    zzb.zza(paramParcel, 14, paramGoogleMapOptions.zztB());
    zzb.zzH(paramParcel, i);
  }

  public GoogleMapOptions zzeb(Parcel paramParcel)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    int j = 0;
    byte b1 = -1;
    byte b2 = -1;
    int k = 0;
    CameraPosition localCameraPosition = null;
    byte b3 = -1;
    byte b4 = -1;
    byte b5 = -1;
    byte b6 = -1;
    byte b7 = -1;
    byte b8 = -1;
    byte b9 = -1;
    byte b10 = -1;
    while (paramParcel.dataPosition() < i)
    {
      int m = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(m))
      {
      case 13:
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, m);
        break;
      case 1:
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        break;
      case 2:
        b1 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 3:
        b2 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 4:
        k = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        break;
      case 5:
        localCameraPosition = (CameraPosition)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, m, CameraPosition.CREATOR);
        break;
      case 6:
        b3 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 7:
        b4 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 8:
        b5 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 9:
        b6 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 10:
        b7 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 11:
        b8 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 12:
        b9 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
        break;
      case 14:
        b10 = com.google.android.gms.common.internal.safeparcel.zza.zze(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new GoogleMapOptions(j, b1, b2, k, localCameraPosition, b3, b4, b5, b6, b7, b8, b9, b10);
  }

  public GoogleMapOptions[] zzgg(int paramInt)
  {
    return new GoogleMapOptions[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.zza
 * JD-Core Version:    0.6.2
 */