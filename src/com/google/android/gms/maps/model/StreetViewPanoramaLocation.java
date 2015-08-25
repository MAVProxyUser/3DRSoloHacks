package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;

public class StreetViewPanoramaLocation
  implements SafeParcelable
{
  public static final zzk CREATOR = new zzk();
  public final StreetViewPanoramaLink[] links;
  public final String panoId;
  public final LatLng position;
  private final int zzzH;

  StreetViewPanoramaLocation(int paramInt, StreetViewPanoramaLink[] paramArrayOfStreetViewPanoramaLink, LatLng paramLatLng, String paramString)
  {
    this.zzzH = paramInt;
    this.links = paramArrayOfStreetViewPanoramaLink;
    this.position = paramLatLng;
    this.panoId = paramString;
  }

  public StreetViewPanoramaLocation(StreetViewPanoramaLink[] paramArrayOfStreetViewPanoramaLink, LatLng paramLatLng, String paramString)
  {
    this(1, paramArrayOfStreetViewPanoramaLink, paramLatLng, paramString);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    StreetViewPanoramaLocation localStreetViewPanoramaLocation;
    do
    {
      return true;
      if (!(paramObject instanceof StreetViewPanoramaLocation))
        return false;
      localStreetViewPanoramaLocation = (StreetViewPanoramaLocation)paramObject;
    }
    while ((this.panoId.equals(localStreetViewPanoramaLocation.panoId)) && (this.position.equals(localStreetViewPanoramaLocation.position)));
    return false;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.position;
    arrayOfObject[1] = this.panoId;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("panoId", this.panoId).zzg("position", this.position.toString()).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzk.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.StreetViewPanoramaLocation
 * JD-Core Version:    0.6.2
 */