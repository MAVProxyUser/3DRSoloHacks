package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.coordinate.LatLong;

public class Gps
  implements Parcelable
{
  public static final Parcelable.Creator<Gps> CREATOR = new Parcelable.Creator()
  {
    public Gps createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Gps(paramAnonymousParcel, null);
    }

    public Gps[] newArray(int paramAnonymousInt)
    {
      return new Gps[paramAnonymousInt];
    }
  };
  public static final String LOCK_2D = "2D";
  private static final int LOCK_2D_TYPE = 2;
  public static final String LOCK_3D = "3D";
  private static final int LOCK_3D_TYPE = 3;
  public static final String NO_FIX = "NoFix";
  private int mFixType;
  private double mGpsEph;
  private LatLong mPosition;
  private int mSatCount;

  public Gps()
  {
  }

  public Gps(double paramDouble1, double paramDouble2, double paramDouble3, int paramInt1, int paramInt2)
  {
    this(new LatLong(paramDouble1, paramDouble2), paramDouble3, paramInt1, paramInt2);
  }

  private Gps(Parcel paramParcel)
  {
    this.mGpsEph = paramParcel.readDouble();
    this.mSatCount = paramParcel.readInt();
    this.mFixType = paramParcel.readInt();
    this.mPosition = ((LatLong)paramParcel.readParcelable(LatLong.class.getClassLoader()));
  }

  public Gps(LatLong paramLatLong, double paramDouble, int paramInt1, int paramInt2)
  {
    this.mPosition = paramLatLong;
    this.mGpsEph = paramDouble;
    this.mSatCount = paramInt1;
    this.mFixType = paramInt2;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    Gps localGps;
    do
    {
      return true;
      if (!(paramObject instanceof Gps))
        return false;
      localGps = (Gps)paramObject;
      if (this.mFixType != localGps.mFixType)
        return false;
      if (Double.compare(localGps.mGpsEph, this.mGpsEph) != 0)
        return false;
      if (this.mSatCount != localGps.mSatCount)
        return false;
      if (this.mPosition == null)
        break;
    }
    while (this.mPosition.equals(localGps.mPosition));
    while (true)
    {
      return false;
      if (localGps.mPosition == null)
        break;
    }
  }

  public String getFixStatus()
  {
    switch (this.mFixType)
    {
    default:
      return "NoFix";
    case 2:
      return "2D";
    case 3:
    }
    return "3D";
  }

  public int getFixType()
  {
    return this.mFixType;
  }

  public double getGpsEph()
  {
    return this.mGpsEph;
  }

  public LatLong getPosition()
  {
    return this.mPosition;
  }

  public int getSatellitesCount()
  {
    return this.mSatCount;
  }

  public int hashCode()
  {
    long l = Double.doubleToLongBits(this.mGpsEph);
    int i = 31 * (31 * (31 * (int)(l ^ l >>> 32) + this.mSatCount) + this.mFixType);
    if (this.mPosition != null);
    for (int j = this.mPosition.hashCode(); ; j = 0)
      return i + j;
  }

  public boolean isValid()
  {
    return this.mPosition != null;
  }

  public void setFixType(int paramInt)
  {
    this.mFixType = paramInt;
  }

  public void setGpsEph(double paramDouble)
  {
    this.mGpsEph = paramDouble;
  }

  public void setPosition(LatLong paramLatLong)
  {
    this.mPosition = paramLatLong;
  }

  public void setSatCount(int paramInt)
  {
    this.mSatCount = paramInt;
  }

  public String toString()
  {
    return "Gps{mGpsEph=" + this.mGpsEph + ", mSatCount=" + this.mSatCount + ", mFixType=" + this.mFixType + ", mPosition=" + this.mPosition + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeDouble(this.mGpsEph);
    paramParcel.writeInt(this.mSatCount);
    paramParcel.writeInt(this.mFixType);
    paramParcel.writeParcelable(this.mPosition, 0);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.Gps
 * JD-Core Version:    0.6.2
 */