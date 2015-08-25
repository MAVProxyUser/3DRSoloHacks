package com.google.android.gms.maps.internal;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

public class Point
  implements SafeParcelable
{
  public static final zzz CREATOR = new zzz();
  private final int versionCode;
  private final android.graphics.Point zzauR;

  public Point(int paramInt, android.graphics.Point paramPoint)
  {
    this.versionCode = paramInt;
    this.zzauR = paramPoint;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject)
      return true;
    if (!(paramObject instanceof Point))
      return false;
    Point localPoint = (Point)paramObject;
    return this.zzauR.equals(localPoint.zzauR);
  }

  int getVersionCode()
  {
    return this.versionCode;
  }

  public int hashCode()
  {
    return this.zzauR.hashCode();
  }

  public String toString()
  {
    return this.zzauR.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzz.zza(this, paramParcel, paramInt);
  }

  public android.graphics.Point zztP()
  {
    return this.zzauR;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.Point
 * JD-Core Version:    0.6.2
 */