package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzg
  implements Parcelable.Creator<UserDataType>
{
  static void zza(UserDataType paramUserDataType, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramUserDataType.zzAV, false);
    zzb.zzc(paramParcel, 1000, paramUserDataType.zzzH);
    zzb.zzc(paramParcel, 2, paramUserDataType.zzasi);
    zzb.zzH(paramParcel, i);
  }

  public UserDataType zzdP(Parcel paramParcel)
  {
    int i = 0;
    int j = zza.zzL(paramParcel);
    String str = null;
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
        str = zza.zzo(paramParcel, m);
        break;
      case 1000:
        k = zza.zzg(paramParcel, m);
        break;
      case 2:
        i = zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new UserDataType(k, str, i);
  }

  public UserDataType[] zzfT(int paramInt)
  {
    return new UserDataType[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.zzg
 * JD-Core Version:    0.6.2
 */