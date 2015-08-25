package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Battery
  implements Parcelable
{
  public static final Parcelable.Creator<Battery> CREATOR = new Parcelable.Creator()
  {
    public Battery createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Battery(paramAnonymousParcel, null);
    }

    public Battery[] newArray(int paramAnonymousInt)
    {
      return new Battery[paramAnonymousInt];
    }
  };
  private double batteryCurrent;
  private Double batteryDischarge;
  private double batteryRemain;
  private double batteryVoltage;

  public Battery()
  {
  }

  public Battery(double paramDouble1, double paramDouble2, double paramDouble3, Double paramDouble)
  {
    this.batteryVoltage = paramDouble1;
    this.batteryRemain = paramDouble2;
    this.batteryCurrent = paramDouble3;
    this.batteryDischarge = paramDouble;
  }

  private Battery(Parcel paramParcel)
  {
    this.batteryVoltage = paramParcel.readDouble();
    this.batteryRemain = paramParcel.readDouble();
    this.batteryCurrent = paramParcel.readDouble();
    this.batteryDischarge = ((Double)paramParcel.readValue(Double.class.getClassLoader()));
  }

  public int describeContents()
  {
    return 0;
  }

  public double getBatteryCurrent()
  {
    return this.batteryCurrent;
  }

  public Double getBatteryDischarge()
  {
    return this.batteryDischarge;
  }

  public double getBatteryRemain()
  {
    return this.batteryRemain;
  }

  public double getBatteryVoltage()
  {
    return this.batteryVoltage;
  }

  public void setBatteryCurrent(double paramDouble)
  {
    this.batteryCurrent = paramDouble;
  }

  public void setBatteryDischarge(Double paramDouble)
  {
    this.batteryDischarge = paramDouble;
  }

  public void setBatteryRemain(double paramDouble)
  {
    this.batteryRemain = paramDouble;
  }

  public void setBatteryVoltage(double paramDouble)
  {
    this.batteryVoltage = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.batteryVoltage);
    paramParcel.writeDouble(this.batteryRemain);
    paramParcel.writeDouble(this.batteryCurrent);
    paramParcel.writeValue(this.batteryDischarge);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Battery
 * JD-Core Version:    0.6.2
 */