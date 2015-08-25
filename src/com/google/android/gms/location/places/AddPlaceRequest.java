package com.google.android.gms.location.places;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.model.LatLng;
import java.util.ArrayList;
import java.util.List;

public class AddPlaceRequest
  implements SafeParcelable
{
  public static final Parcelable.Creator<AddPlaceRequest> CREATOR = new zza();
  private final String mName;
  private final String zzacM;
  private final LatLng zzarw;
  private final List<Integer> zzarx;
  private final String zzary;
  private final Uri zzarz;
  final int zzzH;

  AddPlaceRequest(int paramInt, String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, String paramString3, Uri paramUri)
  {
    this.zzzH = paramInt;
    this.mName = zzv.zzbS(paramString1);
    this.zzarw = ((LatLng)zzv.zzr(paramLatLng));
    this.zzacM = paramString2;
    this.zzarx = new ArrayList(paramList);
    if ((!TextUtils.isEmpty(paramString3)) || (paramUri != null));
    for (boolean bool = true; ; bool = false)
    {
      zzv.zzb(bool, "One of phone number or URI should be provided.");
      this.zzary = paramString3;
      this.zzarz = paramUri;
      return;
    }
  }

  public AddPlaceRequest(String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, Uri paramUri)
  {
    this(paramString1, paramLatLng, paramString2, paramList, null, (Uri)zzv.zzr(paramUri));
  }

  public AddPlaceRequest(String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, String paramString3)
  {
    this(paramString1, paramLatLng, paramString2, paramList, zzv.zzbS(paramString3), null);
  }

  public AddPlaceRequest(String paramString1, LatLng paramLatLng, String paramString2, List<Integer> paramList, String paramString3, Uri paramUri)
  {
    this(0, paramString1, paramLatLng, paramString2, paramList, paramString3, paramUri);
  }

  public int describeContents()
  {
    return 0;
  }

  public String getAddress()
  {
    return this.zzacM;
  }

  public LatLng getLatLng()
  {
    return this.zzarw;
  }

  public String getName()
  {
    return this.mName;
  }

  public String getPhoneNumber()
  {
    return this.zzary;
  }

  public List<Integer> getPlaceTypes()
  {
    return this.zzarx;
  }

  public Uri getWebsiteUri()
  {
    return this.zzarz;
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("name", this.mName).zzg("latLng", this.zzarw).zzg("address", this.zzacM).zzg("placeTypes", this.zzarx).zzg("phoneNumer", this.zzary).zzg("websiteUri", this.zzarz).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zza.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.AddPlaceRequest
 * JD-Core Version:    0.6.2
 */