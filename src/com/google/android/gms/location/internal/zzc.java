package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzc
  implements Parcelable.Creator<ClientIdentity>
{
  static void zza(ClientIdentity paramClientIdentity, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramClientIdentity.uid);
    zzb.zzc(paramParcel, 1000, paramClientIdentity.getVersionCode());
    zzb.zza(paramParcel, 2, paramClientIdentity.packageName, false);
    zzb.zzH(paramParcel, i);
  }

  public ClientIdentity zzdG(Parcel paramParcel)
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
        i = zza.zzg(paramParcel, m);
        break;
      case 1000:
        k = zza.zzg(paramParcel, m);
        break;
      case 2:
        str = zza.zzo(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new ClientIdentity(k, i, str);
  }

  public ClientIdentity[] zzfH(int paramInt)
  {
    return new ClientIdentity[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzc
 * JD-Core Version:    0.6.2
 */