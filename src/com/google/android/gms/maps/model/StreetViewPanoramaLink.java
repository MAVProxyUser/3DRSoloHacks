package com.google.android.gms.maps.model;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;

public class StreetViewPanoramaLink
  implements SafeParcelable
{
  public static final zzj CREATOR = new zzj();
  public final float bearing;
  public final String panoId;
  private final int zzzH;

  StreetViewPanoramaLink(int paramInt, String paramString, float paramFloat)
  {
    this.zzzH = paramInt;
    this.panoId = paramString;
    if (paramFloat <= 0.0D)
      paramFloat = 360.0F + paramFloat % 360.0F;
    this.bearing = (paramFloat % 360.0F);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    StreetViewPanoramaLink localStreetViewPanoramaLink;
    do
    {
      return true;
      if (!(paramObject instanceof StreetViewPanoramaLink))
        return false;
      localStreetViewPanoramaLink = (StreetViewPanoramaLink)paramObject;
    }
    while ((this.panoId.equals(localStreetViewPanoramaLink.panoId)) && (Float.floatToIntBits(this.bearing) == Float.floatToIntBits(localStreetViewPanoramaLink.bearing)));
    return false;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.panoId;
    arrayOfObject[1] = Float.valueOf(this.bearing);
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("panoId", this.panoId).zzg("bearing", Float.valueOf(this.bearing)).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzj.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.StreetViewPanoramaLink
 * JD-Core Version:    0.6.2
 */