package com.google.android.gms.location.places;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzu.zza;
import java.util.concurrent.TimeUnit;

public final class PlaceRequest
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  static final long zzarR = TimeUnit.HOURS.toMillis(1L);
  private final int mPriority;
  private final long zzapJ;
  private final long zzaqe;
  private final PlaceFilter zzarS;
  final int zzzH;

  public PlaceRequest(int paramInt1, PlaceFilter paramPlaceFilter, long paramLong1, int paramInt2, long paramLong2)
  {
    this.zzzH = paramInt1;
    this.zzarS = paramPlaceFilter;
    this.zzaqe = paramLong1;
    this.mPriority = paramInt2;
    this.zzapJ = paramLong2;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    PlaceRequest localPlaceRequest;
    do
    {
      return true;
      if (!(paramObject instanceof PlaceRequest))
        return false;
      localPlaceRequest = (PlaceRequest)paramObject;
    }
    while ((zzu.equal(this.zzarS, localPlaceRequest.zzarS)) && (this.zzaqe == localPlaceRequest.zzaqe) && (this.mPriority == localPlaceRequest.mPriority) && (this.zzapJ == localPlaceRequest.zzapJ));
    return false;
  }

  public long getExpirationTime()
  {
    return this.zzapJ;
  }

  public long getInterval()
  {
    return this.zzaqe;
  }

  public int getPriority()
  {
    return this.mPriority;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = this.zzarS;
    arrayOfObject[1] = Long.valueOf(this.zzaqe);
    arrayOfObject[2] = Integer.valueOf(this.mPriority);
    arrayOfObject[3] = Long.valueOf(this.zzapJ);
    return zzu.hashCode(arrayOfObject);
  }

  public String toString()
  {
    return zzu.zzq(this).zzg("filter", this.zzarS).zzg("interval", Long.valueOf(this.zzaqe)).zzg("priority", Integer.valueOf(this.mPriority)).zzg("expireAt", Long.valueOf(this.zzapJ)).toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze.zza(this, paramParcel, paramInt);
  }

  public PlaceFilter zzsS()
  {
    return this.zzarS;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlaceRequest
 * JD-Core Version:    0.6.2
 */