package com.google.android.gms.location;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class LocationResult
  implements SafeParcelable
{
  public static final LocationResultCreator CREATOR = new LocationResultCreator();
  static final List<Location> zzaqj = Collections.emptyList();
  private final List<Location> zzaqk;
  private final int zzzH;

  LocationResult(int paramInt, List<Location> paramList)
  {
    this.zzzH = paramInt;
    this.zzaqk = paramList;
  }

  public static LocationResult create(List<Location> paramList)
  {
    if (paramList == null)
      paramList = zzaqj;
    return new LocationResult(2, paramList);
  }

  public static LocationResult extractResult(Intent paramIntent)
  {
    if (!hasResult(paramIntent))
      return null;
    return (LocationResult)paramIntent.getExtras().getParcelable("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
  }

  public static boolean hasResult(Intent paramIntent)
  {
    if (paramIntent == null)
      return false;
    return paramIntent.hasExtra("com.google.android.gms.location.EXTRA_LOCATION_RESULT");
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof LocationResult))
      return false;
    LocationResult localLocationResult = (LocationResult)paramObject;
    if (localLocationResult.zzaqk.size() != this.zzaqk.size())
      return false;
    Iterator localIterator1 = localLocationResult.zzaqk.iterator();
    Iterator localIterator2 = this.zzaqk.iterator();
    while (localIterator1.hasNext())
    {
      Location localLocation1 = (Location)localIterator2.next();
      Location localLocation2 = (Location)localIterator1.next();
      if (localLocation1.getTime() != localLocation2.getTime())
        return false;
    }
    return true;
  }

  public Location getLastLocation()
  {
    int i = this.zzaqk.size();
    if (i == 0)
      return null;
    return (Location)this.zzaqk.get(i - 1);
  }

  public List<Location> getLocations()
  {
    return this.zzaqk;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public int hashCode()
  {
    Iterator localIterator = this.zzaqk.iterator();
    long l;
    for (int i = 17; localIterator.hasNext(); i = (int)(l ^ l >>> 32) + i * 31)
      l = ((Location)localIterator.next()).getTime();
    return i;
  }

  public String toString()
  {
    return "LocationResult[locations: " + this.zzaqk + "]";
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    LocationResultCreator.zza(this, paramParcel, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationResult
 * JD-Core Version:    0.6.2
 */