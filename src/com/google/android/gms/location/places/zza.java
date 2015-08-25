package com.google.android.gms.location.places;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;

public class zza
  implements Parcelable.Creator<AddPlaceRequest>
{
  static void zza(AddPlaceRequest paramAddPlaceRequest, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramAddPlaceRequest.getName(), false);
    zzb.zzc(paramParcel, 1000, paramAddPlaceRequest.zzzH);
    zzb.zza(paramParcel, 2, paramAddPlaceRequest.getLatLng(), paramInt, false);
    zzb.zza(paramParcel, 3, paramAddPlaceRequest.getAddress(), false);
    zzb.zza(paramParcel, 4, paramAddPlaceRequest.getPlaceTypes(), false);
    zzb.zza(paramParcel, 5, paramAddPlaceRequest.getPhoneNumber(), false);
    zzb.zza(paramParcel, 6, paramAddPlaceRequest.getWebsiteUri(), paramInt, false);
    zzb.zzH(paramParcel, i);
  }

  public AddPlaceRequest zzdK(Parcel paramParcel)
  {
    Uri localUri = null;
    int i = com.google.android.gms.common.internal.safeparcel.zza.zzL(paramParcel);
    int j = 0;
    String str1 = null;
    ArrayList localArrayList = null;
    String str2 = null;
    LatLng localLatLng = null;
    String str3 = null;
    while (paramParcel.dataPosition() < i)
    {
      int k = com.google.android.gms.common.internal.safeparcel.zza.zzK(paramParcel);
      switch (com.google.android.gms.common.internal.safeparcel.zza.zzaV(k))
      {
      default:
        com.google.android.gms.common.internal.safeparcel.zza.zzb(paramParcel, k);
        break;
      case 1:
        str3 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 1000:
        j = com.google.android.gms.common.internal.safeparcel.zza.zzg(paramParcel, k);
        break;
      case 2:
        localLatLng = (LatLng)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, k, LatLng.CREATOR);
        break;
      case 3:
        str2 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 4:
        localArrayList = com.google.android.gms.common.internal.safeparcel.zza.zzB(paramParcel, k);
        break;
      case 5:
        str1 = com.google.android.gms.common.internal.safeparcel.zza.zzo(paramParcel, k);
        break;
      case 6:
        localUri = (Uri)com.google.android.gms.common.internal.safeparcel.zza.zza(paramParcel, k, Uri.CREATOR);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new AddPlaceRequest(j, str3, localLatLng, str2, localArrayList, str1, localUri);
  }

  public AddPlaceRequest[] zzfN(int paramInt)
  {
    return new AddPlaceRequest[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.zza
 * JD-Core Version:    0.6.2
 */