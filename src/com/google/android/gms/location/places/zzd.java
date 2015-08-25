package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import java.util.ArrayList;

public class zzd
  implements Parcelable.Creator<PlaceFilter>
{
  static void zza(PlaceFilter paramPlaceFilter, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceFilter.zzarB, false);
    zzb.zzc(paramParcel, 1000, paramPlaceFilter.zzzH);
    zzb.zza(paramParcel, 3, paramPlaceFilter.zzarG);
    zzb.zzc(paramParcel, 4, paramPlaceFilter.zzarH, false);
    zzb.zzb(paramParcel, 6, paramPlaceFilter.zzarI, false);
    zzb.zzH(paramParcel, i);
  }

  public PlaceFilter zzdN(Parcel paramParcel)
  {
    boolean bool = false;
    ArrayList localArrayList1 = null;
    int i = zza.zzL(paramParcel);
    ArrayList localArrayList2 = null;
    ArrayList localArrayList3 = null;
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
        localArrayList3 = zza.zzB(paramParcel, k);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
        break;
      case 3:
        bool = zza.zzc(paramParcel, k);
        break;
      case 4:
        localArrayList1 = zza.zzc(paramParcel, k, UserDataType.CREATOR);
        break;
      case 6:
        localArrayList2 = zza.zzC(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new PlaceFilter(j, localArrayList3, bool, localArrayList2, localArrayList1);
  }

  public PlaceFilter[] zzfQ(int paramInt)
  {
    return new PlaceFilter[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.zzd
 * JD-Core Version:    0.6.2
 */