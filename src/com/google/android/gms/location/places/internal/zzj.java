package com.google.android.gms.location.places.internal;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;

public class zzj
  implements Parcelable.Creator<PlaceImpl>
{
  static void zza(PlaceImpl paramPlaceImpl, Parcel paramParcel, int paramInt)
  {
    int i = zzb.zzM(paramParcel);
    zzb.zza(paramParcel, 1, paramPlaceImpl.getId(), false);
    zzb.zza(paramParcel, 2, paramPlaceImpl.zztd(), false);
    zzb.zza(paramParcel, 3, paramPlaceImpl.zztf(), paramInt, false);
    zzb.zza(paramParcel, 4, paramPlaceImpl.getLatLng(), paramInt, false);
    zzb.zza(paramParcel, 5, paramPlaceImpl.zzsZ());
    zzb.zza(paramParcel, 6, paramPlaceImpl.getViewport(), paramInt, false);
    zzb.zza(paramParcel, 7, paramPlaceImpl.zzte(), false);
    zzb.zza(paramParcel, 8, paramPlaceImpl.getWebsiteUri(), paramInt, false);
    zzb.zza(paramParcel, 9, paramPlaceImpl.zzsT());
    zzb.zza(paramParcel, 10, paramPlaceImpl.getRating());
    zzb.zzc(paramParcel, 11, paramPlaceImpl.getPriceLevel());
    zzb.zza(paramParcel, 12, paramPlaceImpl.zztc());
    zzb.zza(paramParcel, 13, paramPlaceImpl.zzsY(), false);
    zzb.zza(paramParcel, 14, paramPlaceImpl.getAddress(), false);
    zzb.zza(paramParcel, 15, paramPlaceImpl.getPhoneNumber(), false);
    zzb.zzb(paramParcel, 17, paramPlaceImpl.zztb(), false);
    zzb.zza(paramParcel, 16, paramPlaceImpl.zzta(), false);
    zzb.zzc(paramParcel, 1000, paramPlaceImpl.zzzH);
    zzb.zza(paramParcel, 19, paramPlaceImpl.getName(), false);
    zzb.zza(paramParcel, 18, paramPlaceImpl.zzasJ);
    zzb.zza(paramParcel, 20, paramPlaceImpl.getPlaceTypes(), false);
    zzb.zzH(paramParcel, i);
  }

  public PlaceImpl zzdR(Parcel paramParcel)
  {
    int i = zza.zzL(paramParcel);
    int j = 0;
    String str1 = null;
    ArrayList localArrayList1 = null;
    ArrayList localArrayList2 = null;
    Bundle localBundle = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    ArrayList localArrayList3 = null;
    LatLng localLatLng = null;
    float f1 = 0.0F;
    LatLngBounds localLatLngBounds = null;
    String str6 = null;
    Uri localUri = null;
    boolean bool1 = false;
    float f2 = 0.0F;
    int k = 0;
    long l = 0L;
    boolean bool2 = false;
    PlaceLocalization localPlaceLocalization = null;
    while (paramParcel.dataPosition() < i)
    {
      int m = zza.zzK(paramParcel);
      switch (zza.zzaV(m))
      {
      default:
        zza.zzb(paramParcel, m);
        break;
      case 1:
        str1 = zza.zzo(paramParcel, m);
        break;
      case 2:
        localBundle = zza.zzq(paramParcel, m);
        break;
      case 3:
        localPlaceLocalization = (PlaceLocalization)zza.zza(paramParcel, m, PlaceLocalization.CREATOR);
        break;
      case 4:
        localLatLng = (LatLng)zza.zza(paramParcel, m, LatLng.CREATOR);
        break;
      case 5:
        f1 = zza.zzl(paramParcel, m);
        break;
      case 6:
        localLatLngBounds = (LatLngBounds)zza.zza(paramParcel, m, LatLngBounds.CREATOR);
        break;
      case 7:
        str6 = zza.zzo(paramParcel, m);
        break;
      case 8:
        localUri = (Uri)zza.zza(paramParcel, m, Uri.CREATOR);
        break;
      case 9:
        bool1 = zza.zzc(paramParcel, m);
        break;
      case 10:
        f2 = zza.zzl(paramParcel, m);
        break;
      case 11:
        k = zza.zzg(paramParcel, m);
        break;
      case 12:
        l = zza.zzi(paramParcel, m);
        break;
      case 13:
        localArrayList2 = zza.zzB(paramParcel, m);
        break;
      case 14:
        str3 = zza.zzo(paramParcel, m);
        break;
      case 15:
        str4 = zza.zzo(paramParcel, m);
        break;
      case 17:
        localArrayList3 = zza.zzC(paramParcel, m);
        break;
      case 16:
        str5 = zza.zzo(paramParcel, m);
        break;
      case 1000:
        j = zza.zzg(paramParcel, m);
        break;
      case 19:
        str2 = zza.zzo(paramParcel, m);
        break;
      case 18:
        bool2 = zza.zzc(paramParcel, m);
        break;
      case 20:
        localArrayList1 = zza.zzB(paramParcel, m);
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    return new PlaceImpl(j, str1, localArrayList1, localArrayList2, localBundle, str2, str3, str4, str5, localArrayList3, localLatLng, f1, localLatLngBounds, str6, localUri, bool1, f2, k, l, bool2, localPlaceLocalization);
  }

  public PlaceImpl[] zzfW(int paramInt)
  {
    return new PlaceImpl[paramInt];
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzj
 * JD-Core Version:    0.6.2
 */