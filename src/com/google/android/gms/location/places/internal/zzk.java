package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;

public class zzk
  implements Parcelable.Creator<PlaceLikelihoodEntity>
{
  static void zza(PlaceLikelihoodEntity paramPlaceLikelihoodEntity, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceLikelihoodEntity.zzasP, paramInt, false);
    zzb.zzc(paramParcel, 1000, paramPlaceLikelihoodEntity.zzzH);
    zzb.zza(paramParcel, 2, paramPlaceLikelihoodEntity.zzasQ);
    zzb.zzH(paramParcel, i);
  }

  public PlaceLikelihoodEntity zzdS(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    Object localObject1 = null;
    float f1 = 0.0F;
    if (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      float f2;
      Object localObject2;
      int m;
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        f2 = f1;
        localObject2 = localObject1;
        m = j;
      case 1:
      case 1000:
      case 2:
      }
      while (true)
      {
        j = m;
        localObject1 = localObject2;
        f1 = f2;
        break;
        PlaceImpl localPlaceImpl = (PlaceImpl)zza.zza(paramParcel, k, PlaceImpl.CREATOR);
        m = j;
        f2 = f1;
        localObject2 = localPlaceImpl;
        continue;
        int n = zza.zzg(paramParcel, k);
        float f3 = f1;
        localObject2 = localObject1;
        m = n;
        f2 = f3;
        continue;
        f2 = zza.zzl(paramParcel, k);
        localObject2 = localObject1;
        m = j;
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new PlaceLikelihoodEntity(j, localObject1, f1);
  }

  public PlaceLikelihoodEntity[] zzfX(int paramInt)
  {
    return new PlaceLikelihoodEntity[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzk
 * JD-Core Version:    0.6.2
 */