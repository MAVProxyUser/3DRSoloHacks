package com.google.android.gms.location.places.personalized;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.location.places.personalized.internal.TestDataImpl;
import java.util.ArrayList;

public class zze
  implements Parcelable.Creator<PlaceUserData>
{
  static void zza(PlaceUserData paramPlaceUserData, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceUserData.zztk(), false);
    zzb.zzc(paramParcel, 1000, paramPlaceUserData.zzzH);
    zzb.zza(paramParcel, 2, paramPlaceUserData.getPlaceId(), false);
    zzb.zzc(paramParcel, 5, paramPlaceUserData.zztn(), false);
    zzb.zzc(paramParcel, 6, paramPlaceUserData.zztl(), false);
    zzb.zzc(paramParcel, 7, paramPlaceUserData.zztm(), false);
    zzb.zzH(paramParcel, i);
  }

  public PlaceUserData zzdZ(Parcel paramParcel)
  {
    ArrayList localArrayList1 = null;
    int i = zza.zzL(paramParcel);
    int j = 0;
    ArrayList localArrayList2 = null;
    ArrayList localArrayList3 = null;
    String str1 = null;
    String str2 = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      switch (zza.zzaV(k))
      {
      default:
        zza.zzb(paramParcel, k);
        break;
      case 1:
        str2 = zza.zzo(paramParcel, k);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        str1 = zza.zzo(paramParcel, k);
        break;
      case 5:
        localArrayList3 = zza.zzc(paramParcel, k, TestDataImpl.CREATOR);
        break;
      case 6:
        localArrayList2 = zza.zzc(paramParcel, k, PlaceAlias.CREATOR);
        break;
      case 7:
        localArrayList1 = zza.zzc(paramParcel, k, HereContent.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new PlaceUserData(j, str2, str1, localArrayList3, localArrayList2, localArrayList1);
  }

  public PlaceUserData[] zzge(int paramInt)
  {
    return new PlaceUserData[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.personalized.zze
 * JD-Core Version:    0.6.2
 */