package com.google.android.gms.location.places.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import java.util.List;

@Deprecated
public final class PlaceLocalization
  implements SafeParcelable
{
  public static final zzm CREATOR = new zzm();
  public final String name;
  public final int versionCode;
  public final String zzasR;
  public final String zzasS;
  public final String zzasT;
  public final List<String> zzasU;

  public PlaceLocalization(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4, List<String> paramList)
  {
    this.versionCode = paramInt;
    this.name = paramString1;
    this.zzasR = paramString2;
    this.zzasS = paramString3;
    this.zzasT = paramString4;
    this.zzasU = paramList;
  }

  public static PlaceLocalization zza(String paramString1, String paramString2, String paramString3, String paramString4, List<String> paramList)
  {
    return new PlaceLocalization(0, paramString1, paramString2, paramString3, paramString4, paramList);
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlaceLocalization localPlaceLocalization;
    do
    {
      return true;
      if (!(paramObject instanceof PlaceLocalization))
        return false;
      localPlaceLocalization = (PlaceLocalization)paramObject;
    }
    while ((zzu.equal(this.name, localPlaceLocalization.name)) && (zzu.equal(this.zzasR, localPlaceLocalization.zzasR)) && (zzu.equal(this.zzasS, localPlaceLocalization.zzasS)) && (zzu.equal(this.zzasT, localPlaceLocalization.zzasT)) && (zzu.equal(this.zzasU, localPlaceLocalization.zzasU)));
    return false;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.name;
    arrayOfObject[1] = this.zzasR;
    arrayOfObject[2] = this.zzasS;
    arrayOfObject[3] = this.zzasT;
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("name", this.name).zzg("address", this.zzasR).zzg("internationalPhoneNumber", this.zzasS).zzg("regularOpenHours", this.zzasT).zzg("attributions", this.zzasU).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzm.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.PlaceLocalization
 * JD-Core Version:    0.6.2
 */