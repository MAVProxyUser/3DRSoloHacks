package com.google.android.gms.location;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzu;

public final class LocationAvailability
  implements SafeParcelable
{
  public static final LocationAvailabilityCreator CREATOR = new LocationAvailabilityCreator();
  int zzaqa;
  int zzaqb;
  long zzaqc;
  int zzaqd;
  private final int zzzH;

  LocationAvailability(int paramInt1, int paramInt2, int paramInt3, int paramInt4, long paramLong)
  {
    this.zzzH = paramInt1;
    this.zzaqd = paramInt2;
    this.zzaqa = paramInt3;
    this.zzaqb = paramInt4;
    this.zzaqc = paramLong;
  }

  public static LocationAvailability extractLocationAvailability(Intent paramIntent)
  {
    if (!hasLocationAvailability(paramIntent))
      return null;
    return (LocationAvailability)paramIntent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
  }

  public static boolean hasLocationAvailability(Intent paramIntent)
  {
    if (paramIntent == null)
      return false;
    return paramIntent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_AVAILABILITY");
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LocationAvailability));
    LocationAvailability localLocationAvailability;
    do
    {
      return false;
      localLocationAvailability = (LocationAvailability)paramObject;
    }
    while ((this.zzaqd != localLocationAvailability.zzaqd) || (this.zzaqa != localLocationAvailability.zzaqa) || (this.zzaqb != localLocationAvailability.zzaqb) || (this.zzaqc != localLocationAvailability.zzaqc));
    return true;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Integer.valueOf(this.zzaqd);
    arrayOfObject[1] = Integer.valueOf(this.zzaqa);
    arrayOfObject[2] = Integer.valueOf(this.zzaqb);
    arrayOfObject[3] = Long.valueOf(this.zzaqc);
    return zzu.hashCode(arrayOfObject);
  }

  public boolean isLocationAvailable()
  {
    return this.zzaqd < 1000;
  }

  public String toString()
  {
    return "LocationAvailability[isLocationAvailable: " + isLocationAvailable() + "]";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LocationAvailabilityCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationAvailability
 * JD-Core Version:    0.6.2
 */