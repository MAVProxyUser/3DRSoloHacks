package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;

public final class NearbyAlertRequest
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  private final int zzapI;
  private final int zzarD;
  private final PlaceFilter zzarE;
  private final int zzzH;

  NearbyAlertRequest(int paramInt1, int paramInt2, int paramInt3, PlaceFilter paramPlaceFilter)
  {
    this.zzzH = paramInt1;
    this.zzapI = paramInt2;
    this.zzarD = paramInt3;
    this.zzarE = paramPlaceFilter;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    NearbyAlertRequest localNearbyAlertRequest;
    do
    {
      return true;
      if (!(paramObject instanceof NearbyAlertRequest))
        return false;
      localNearbyAlertRequest = (NearbyAlertRequest)paramObject;
    }
    while ((this.zzapI == localNearbyAlertRequest.zzapI) && (this.zzarD == localNearbyAlertRequest.zzarD) && (this.zzarE.equals(localNearbyAlertRequest.zzarE)));
    return false;
  }

  public int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(this.zzapI);
    arrayOfObject[1] = Integer.valueOf(this.zzarD);
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("transitionTypes", Integer.valueOf(this.zzapI)).zzg("loiteringTimeMillis", Integer.valueOf(this.zzarD)).zzg("placeFilter", this.zzarE).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }

  public int zzsO()
  {
    return this.zzapI;
  }

  public int zzsR()
  {
    return this.zzarD;
  }

  public PlaceFilter zzsS()
  {
    return this.zzarE;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.NearbyAlertRequest
 * JD-Core Version:    0.6.2
 */