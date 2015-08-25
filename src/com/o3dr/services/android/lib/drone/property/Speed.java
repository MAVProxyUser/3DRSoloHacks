package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Speed
  implements Parcelable
{
  public static final Parcelable.Creator<Speed> CREATOR = new Parcelable.Creator()
  {
    public Speed createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Speed(paramAnonymousParcel, null);
    }

    public Speed[] newArray(int paramAnonymousInt)
    {
      return new Speed[paramAnonymousInt];
    }
  };
  private double airSpeed;
  private double groundSpeed;
  private double verticalSpeed;

  public Speed()
  {
  }

  public Speed(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.verticalSpeed = paramDouble1;
    this.groundSpeed = paramDouble2;
    this.airSpeed = paramDouble3;
  }

  private Speed(Parcel paramParcel)
  {
    this.verticalSpeed = paramParcel.readDouble();
    this.groundSpeed = paramParcel.readDouble();
    this.airSpeed = paramParcel.readDouble();
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Speed localSpeed;
    do
    {
      return true;
      if (!(paramObject instanceof Speed))
        return false;
      localSpeed = (Speed)paramObject;
      if (Double.compare(localSpeed.airSpeed, this.airSpeed) != 0)
        return false;
      if (Double.compare(localSpeed.groundSpeed, this.groundSpeed) != 0)
        return false;
    }
    while (Double.compare(localSpeed.verticalSpeed, this.verticalSpeed) == 0);
    return false;
  }

  public double getAirSpeed()
  {
    return this.airSpeed;
  }

  public double getGroundSpeed()
  {
    return this.groundSpeed;
  }

  public double getVerticalSpeed()
  {
    return this.verticalSpeed;
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.verticalSpeed);
    int i = (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.groundSpeed);
    int j = i * 31 + (int)(l2 ^ l2 >>> 32);
    long l3 = Double.doubleToLongBits(this.airSpeed);
    return j * 31 + (int)(l3 ^ l3 >>> 32);
  }

  public void setAirSpeed(double paramDouble)
  {
    this.airSpeed = paramDouble;
  }

  public void setGroundSpeed(double paramDouble)
  {
    this.groundSpeed = paramDouble;
  }

  public void setVerticalSpeed(double paramDouble)
  {
    this.verticalSpeed = paramDouble;
  }

  public String toString()
  {
    return "Speed{verticalSpeed=" + this.verticalSpeed + ", groundSpeed=" + this.groundSpeed + ", airSpeed=" + this.airSpeed + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.verticalSpeed);
    paramParcel.writeDouble(this.groundSpeed);
    paramParcel.writeDouble(this.airSpeed);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Speed
 * JD-Core Version:    0.6.2
 */