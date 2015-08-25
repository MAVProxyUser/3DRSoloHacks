package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Signal
  implements Parcelable
{
  public static final Parcelable.Creator<Signal> CREATOR = new Parcelable.Creator()
  {
    public Signal createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Signal(paramAnonymousParcel, null);
    }

    public Signal[] newArray(int paramAnonymousInt)
    {
      return new Signal[paramAnonymousInt];
    }
  };
  public static final int MAX_FADE_MARGIN = 50;
  public static final int MIN_FADE_MARGIN = 6;
  private int fixed;
  private boolean isValid;
  private double noise;
  private double remnoise;
  private double remrssi;
  private double rssi;
  private int rxerrors;
  private int txbuf;

  public Signal()
  {
  }

  private Signal(Parcel paramParcel)
  {
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.isValid = bool;
      this.rxerrors = paramParcel.readInt();
      this.fixed = paramParcel.readInt();
      this.txbuf = paramParcel.readInt();
      this.rssi = paramParcel.readDouble();
      this.remrssi = paramParcel.readDouble();
      this.noise = paramParcel.readDouble();
      this.remnoise = paramParcel.readDouble();
      return;
    }
  }

  public Signal(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4)
  {
    this.isValid = paramBoolean;
    this.rxerrors = paramInt1;
    this.fixed = paramInt2;
    this.txbuf = paramInt3;
    this.rssi = paramDouble1;
    this.remrssi = paramDouble2;
    this.noise = paramDouble3;
    this.remnoise = paramDouble4;
  }

  public int describeContents()
  {
    return 0;
  }

  public double getFadeMargin()
  {
    return this.rssi - this.noise;
  }

  public int getFixed()
  {
    return this.fixed;
  }

  public double getNoise()
  {
    return this.noise;
  }

  public double getRemFadeMargin()
  {
    return this.remrssi - this.remnoise;
  }

  public double getRemnoise()
  {
    return this.remnoise;
  }

  public double getRemrssi()
  {
    return this.remrssi;
  }

  public double getRssi()
  {
    return this.rssi;
  }

  public int getRxerrors()
  {
    return this.rxerrors;
  }

  public int getTxbuf()
  {
    return this.txbuf;
  }

  public boolean isValid()
  {
    return this.isValid;
  }

  public void setFixed(int paramInt)
  {
    this.fixed = paramInt;
  }

  public void setNoise(double paramDouble)
  {
    this.noise = paramDouble;
  }

  public void setRemnoise(double paramDouble)
  {
    this.remnoise = paramDouble;
  }

  public void setRemrssi(double paramDouble)
  {
    this.remrssi = paramDouble;
  }

  public void setRssi(double paramDouble)
  {
    this.rssi = paramDouble;
  }

  public void setRxerrors(int paramInt)
  {
    this.rxerrors = paramInt;
  }

  public void setTxbuf(int paramInt)
  {
    this.txbuf = paramInt;
  }

  public void setValid(boolean paramBoolean)
  {
    this.isValid = paramBoolean;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    if (this.isValid);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      paramParcel.writeInt(this.rxerrors);
      paramParcel.writeInt(this.fixed);
      paramParcel.writeInt(this.txbuf);
      paramParcel.writeDouble(this.rssi);
      paramParcel.writeDouble(this.remrssi);
      paramParcel.writeDouble(this.noise);
      paramParcel.writeDouble(this.remnoise);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Signal
 * JD-Core Version:    0.6.2
 */