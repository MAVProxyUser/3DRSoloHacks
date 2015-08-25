package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import java.util.ArrayList;

public class zzb
  implements Parcelable.Creator<AutocompleteFilter>
{
  static void zza(AutocompleteFilter paramAutocompleteFilter, Parcel paramParcel, int paramInt)
  {
    int i = com.google.android.gms.common.internal.safeparcel.zzb.zzM(paramParcel);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 1, paramAutocompleteFilter.zzsQ());
    com.google.android.gms.common.internal.safeparcel.zzb.zzc(paramParcel, 1000, paramAutocompleteFilter.zzzH);
    com.google.android.gms.common.internal.safeparcel.zzb.zza(paramParcel, 2, paramAutocompleteFilter.zzarB, false);
    com.google.android.gms.common.internal.safeparcel.zzb.zzH(paramParcel, i);
  }

  public AutocompleteFilter zzdL(Parcel paramParcel)
  {
    boolean bool = false;
    int i = zza.zzL(paramParcel);
    ArrayList localArrayList = null;
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
        bool = zza.zzc(paramParcel, k);
        break;
      case 1000:
        j = zza.zzg(paramParcel, k);
        break;
      case 2:
        localArrayList = zza.zzB(paramParcel, k);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new AutocompleteFilter(j, bool, localArrayList);
  }

  public AutocompleteFilter[] zzfO(int paramInt)
  {
    return new AutocompleteFilter[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.zzb
 * JD-Core Version:    0.6.2
 */