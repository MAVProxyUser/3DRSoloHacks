package com.google.android.gms.common.server;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zza
  implements Parcelable.Creator<FavaDiagnosticsEntity>
{
  static void zza(FavaDiagnosticsEntity paramFavaDiagnosticsEntity, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zzc(paramParcel, 1, paramFavaDiagnosticsEntity.zzzH);
    zzb.zza(paramParcel, 2, paramFavaDiagnosticsEntity.zzUq, false);
    zzb.zzc(paramParcel, 3, paramFavaDiagnosticsEntity.zzUr);
    zzb.zzH(paramParcel, i);
  }

  public FavaDiagnosticsEntity zzN(Parcel paramParcel)
  {
    int i = 0;
    int j = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    String str = null;
    int k = 0;
    while (paramParcel.dataPosition() < j)
    {
      int m = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(m))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, m);
        break;
      case 1:
        k = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
        break;
      case 2:
        str = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, m);
        break;
      case 3:
        i = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != j)
      throw new zza.zza("Overread allowed size end=" + j, paramParcel);
    return new FavaDiagnosticsEntity(k, str, i);
  }

  public FavaDiagnosticsEntity[] zzaX(int paramInt)
  {
    return new FavaDiagnosticsEntity[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.zza
 * JD-Core Version:    0.6.2
 */