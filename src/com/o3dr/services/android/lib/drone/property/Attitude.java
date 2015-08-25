package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Attitude
  implements Parcelable
{
  public static final Parcelable.Creator<Attitude> CREATOR = new Parcelable.Creator()
  {
    public Attitude createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Attitude(paramAnonymousParcel, null);
    }

    public Attitude[] newArray(int paramAnonymousInt)
    {
      return new Attitude[paramAnonymousInt];
    }
  };
  private double pitch;
  private double roll;
  private double yaw;

  public Attitude()
  {
  }

  public Attitude(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    this.roll = paramDouble1;
    this.pitch = paramDouble2;
    this.yaw = paramDouble3;
  }

  private Attitude(Parcel paramParcel)
  {
    this.roll = paramParcel.readDouble();
    this.pitch = paramParcel.readDouble();
    this.yaw = paramParcel.readDouble();
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Attitude localAttitude;
    do
    {
      return true;
      if (!(paramObject instanceof Attitude))
        return false;
      localAttitude = (Attitude)paramObject;
      if (Double.compare(localAttitude.pitch, this.pitch) != 0)
        return false;
      if (Double.compare(localAttitude.roll, this.roll) != 0)
        return false;
    }
    while (Double.compare(localAttitude.yaw, this.yaw) == 0);
    return false;
  }

  public double getPitch()
  {
    return this.pitch;
  }

  public double getRoll()
  {
    return this.roll;
  }

  public double getYaw()
  {
    return this.yaw;
  }

  public int hashCode()
  {
    long l1 = Double.doubleToLongBits(this.roll);
    int i = (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.pitch);
    int j = i * 31 + (int)(l2 ^ l2 >>> 32);
    long l3 = Double.doubleToLongBits(this.yaw);
    return j * 31 + (int)(l3 ^ l3 >>> 32);
  }

  public void setPitch(double paramDouble)
  {
    this.pitch = paramDouble;
  }

  public void setRoll(double paramDouble)
  {
    this.roll = paramDouble;
  }

  public void setYaw(double paramDouble)
  {
    this.yaw = paramDouble;
  }

  public String toString()
  {
    return "Attitude{roll=" + this.roll + ", pitch=" + this.pitch + ", yaw=" + this.yaw + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.roll);
    paramParcel.writeDouble(this.pitch);
    paramParcel.writeDouble(this.yaw);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Attitude
 * JD-Core Version:    0.6.2
 */