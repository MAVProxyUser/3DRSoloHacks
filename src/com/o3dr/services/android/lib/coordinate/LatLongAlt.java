package com.o3dr.services.android.lib.coordinate;

import android.os.Parcel;
import android.os.Parcelable.Creator;

public class LatLongAlt extends LatLong
{
  public static final Parcelable.Creator<LatLongAlt> CREATOR = new Parcelable.Creator()
  {
    public LatLongAlt createFromParcel(Parcel paramAnonymousParcel)
    {
      return (LatLongAlt)paramAnonymousParcel.readSerializable();
    }

    public LatLongAlt[] newArray(int paramAnonymousInt)
    {
      return new LatLongAlt[paramAnonymousInt];
    }
  };
  private static final long serialVersionUID = -4771550293045623743L;
  private double mAltitude;

  public LatLongAlt(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    super(paramDouble1, paramDouble2);
    this.mAltitude = paramDouble3;
  }

  public LatLongAlt(LatLong paramLatLong, double paramDouble)
  {
    super(paramLatLong);
    this.mAltitude = paramDouble;
  }

  public LatLongAlt(LatLongAlt paramLatLongAlt)
  {
    this(paramLatLongAlt.getLatitude(), paramLatLongAlt.getLongitude(), paramLatLongAlt.getAltitude());
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    do
    {
      return true;
      if (!(paramObject instanceof LatLongAlt))
        return false;
      if (!super.equals(paramObject))
        return false;
    }
    while (Double.compare(((LatLongAlt)paramObject).mAltitude, this.mAltitude) == 0);
    return false;
  }

  public double getAltitude()
  {
    return this.mAltitude;
  }

  public int hashCode()
  {
    int i = super.hashCode();
    long l = Double.doubleToLongBits(this.mAltitude);
    return i * 31 + (int)(l ^ l >>> 32);
  }

  public void set(LatLongAlt paramLatLongAlt)
  {
    super.set(paramLatLongAlt);
    this.mAltitude = paramLatLongAlt.mAltitude;
  }

  public void setAltitude(double paramDouble)
  {
    this.mAltitude = paramDouble;
  }

  public String toString()
  {
    String str = super.toString();
    return "LatLongAlt{" + str + ", mAltitude=" + this.mAltitude + '}';
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.coordinate.LatLongAlt
 * JD-Core Version:    0.6.2
 */