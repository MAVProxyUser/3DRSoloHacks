package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Altitude
  implements Parcelable
{
  public static final Parcelable.Creator<Altitude> CREATOR = new Parcelable.Creator()
  {
    public Altitude createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Altitude(paramAnonymousParcel, null);
    }

    public Altitude[] newArray(int paramAnonymousInt)
    {
      return new Altitude[paramAnonymousInt];
    }
  };
  private double altitude;
  private double targetAltitude;

  public Altitude()
  {
  }

  public Altitude(double paramDouble1, double paramDouble2)
  {
    this.altitude = paramDouble1;
    this.targetAltitude = paramDouble2;
  }

  private Altitude(Parcel paramParcel)
  {
    this.altitude = paramParcel.readDouble();
    this.targetAltitude = paramParcel.readDouble();
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Altitude localAltitude;
    do
    {
      return true;
      if (!(paramObject instanceof Altitude))
        return false;
      localAltitude = (Altitude)paramObject;
      if (Double.compare(localAltitude.altitude, this.altitude) != 0)
        return false;
    }
    while (Double.compare(localAltitude.targetAltitude, this.targetAltitude) == 0);
    return false;
  }

  public double getAltitude()
  {
    return this.altitude;
  }

  public double getTargetAltitude()
  {
    return this.targetAltitude;
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.altitude);
    int i = (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.targetAltitude);
    return i * 31 + (int)(l2 ^ l2 >>> 32);
  }

  public void setAltitude(double paramDouble)
  {
    this.altitude = paramDouble;
  }

  public void setTargetAltitude(double paramDouble)
  {
    this.targetAltitude = paramDouble;
  }

  public String toString()
  {
    return "Altitude{altitude=" + this.altitude + ", targetAltitude=" + this.targetAltitude + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.altitude);
    paramParcel.writeDouble(this.targetAltitude);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Altitude
 * JD-Core Version:    0.6.2
 */