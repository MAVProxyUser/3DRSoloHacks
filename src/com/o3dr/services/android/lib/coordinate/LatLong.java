package com.o3dr.services.android.lib.coordinate;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class LatLong
  implements Parcelable, Serializable
{
  public static final Parcelable.Creator<LatLong> CREATOR = new Parcelable.Creator()
  {
    public LatLong createFromParcel(Parcel paramAnonymousParcel)
    {
      return (LatLong)paramAnonymousParcel.readSerializable();
    }

    public LatLong[] newArray(int paramAnonymousInt)
    {
      return new LatLong[paramAnonymousInt];
    }
  };
  private static final long serialVersionUID = -5809863197722412339L;
  private double latitude;
  private double longitude;

  public LatLong(double paramDouble1, double paramDouble2)
  {
    this.latitude = paramDouble1;
    this.longitude = paramDouble2;
  }

  public LatLong(LatLong paramLatLong)
  {
    this(paramLatLong.getLatitude(), paramLatLong.getLongitude());
  }

  public static LatLong sum(LatLong[] paramArrayOfLatLong)
  {
    double d1 = 0.0D;
    double d2 = 0.0D;
    int i = paramArrayOfLatLong.length;
    for (int j = 0; j < i; j++)
    {
      LatLong localLatLong = paramArrayOfLatLong[j];
      d1 += localLatLong.latitude;
      d2 += localLatLong.longitude;
    }
    return new LatLong(d1, d2);
  }

  public int describeContents()
  {
    return 0;
  }

  public LatLong dot(double paramDouble)
  {
    return new LatLong(paramDouble * this.latitude, paramDouble * this.longitude);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    LatLong localLatLong;
    do
    {
      return true;
      if (!(paramObject instanceof LatLong))
        return false;
      localLatLong = (LatLong)paramObject;
      if (Double.compare(localLatLong.latitude, this.latitude) != 0)
        return false;
    }
    while (Double.compare(localLatLong.longitude, this.longitude) == 0);
    return false;
  }

  public double getLatitude()
  {
    return this.latitude;
  }

  public double getLongitude()
  {
    return this.longitude;
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.latitude);
    int i = (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.longitude);
    return i * 31 + (int)(l2 ^ l2 >>> 32);
  }

  public LatLong negate()
  {
    return new LatLong(-1.0D * this.latitude, -1.0D * this.longitude);
  }

  public void set(LatLong paramLatLong)
  {
    this.latitude = paramLatLong.latitude;
    this.longitude = paramLatLong.longitude;
  }

  public void setLatitude(double paramDouble)
  {
    this.latitude = paramDouble;
  }

  public void setLongitude(double paramDouble)
  {
    this.longitude = paramDouble;
  }

  public LatLong subtract(LatLong paramLatLong)
  {
    return new LatLong(this.latitude - paramLatLong.latitude, this.longitude - paramLatLong.longitude);
  }

  public LatLong sum(LatLong paramLatLong)
  {
    return new LatLong(this.latitude + paramLatLong.latitude, this.longitude + paramLatLong.longitude);
  }

  public String toString()
  {
    return "LatLong{latitude=" + this.latitude + ", longitude=" + this.longitude + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeSerializable(this);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.coordinate.LatLong
 * JD-Core Version:    0.6.2
 */