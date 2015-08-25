package com.o3dr.services.android.lib.drone.mission.item.complex;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CameraDetail
  implements Parcelable
{
  public static final Parcelable.Creator<CameraDetail> CREATOR = new Parcelable.Creator()
  {
    public CameraDetail createFromParcel(Parcel paramAnonymousParcel)
    {
      return new CameraDetail(paramAnonymousParcel, null);
    }

    public CameraDetail[] newArray(int paramAnonymousInt)
    {
      return new CameraDetail[paramAnonymousInt];
    }
  };
  private final double focalLength;
  private final boolean isInLandscapeOrientation;
  private final String name;
  private final double overlap;
  private final double sensorHeight;
  private final double sensorResolution;
  private final double sensorWidth;
  private final double sidelap;

  public CameraDetail()
  {
    this.name = "Canon SX260";
    this.sensorWidth = 6.12D;
    this.sensorHeight = 4.22D;
    this.sensorResolution = 12.1D;
    this.focalLength = 5.0D;
    this.overlap = 50.0D;
    this.sidelap = 60.0D;
    this.isInLandscapeOrientation = true;
  }

  private CameraDetail(Parcel paramParcel)
  {
    this.name = paramParcel.readString();
    this.sensorWidth = paramParcel.readDouble();
    this.sensorHeight = paramParcel.readDouble();
    this.sensorResolution = paramParcel.readDouble();
    this.focalLength = paramParcel.readDouble();
    this.overlap = paramParcel.readDouble();
    this.sidelap = paramParcel.readDouble();
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.isInLandscapeOrientation = bool;
      return;
    }
  }

  public CameraDetail(CameraDetail paramCameraDetail)
  {
    this(paramCameraDetail.name, paramCameraDetail.sensorWidth, paramCameraDetail.sensorHeight, paramCameraDetail.sensorResolution, paramCameraDetail.focalLength, paramCameraDetail.overlap, paramCameraDetail.sidelap, paramCameraDetail.isInLandscapeOrientation);
  }

  public CameraDetail(String paramString, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, double paramDouble5, double paramDouble6, boolean paramBoolean)
  {
    this.name = paramString;
    this.sensorWidth = paramDouble1;
    this.sensorHeight = paramDouble2;
    this.sensorResolution = paramDouble3;
    this.focalLength = paramDouble4;
    this.overlap = paramDouble5;
    this.sidelap = paramDouble6;
    this.isInLandscapeOrientation = paramBoolean;
  }

  public int describeContents()
  {
    return 0;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    CameraDetail localCameraDetail;
    do
    {
      return true;
      if (!(paramObject instanceof CameraDetail))
        return false;
      localCameraDetail = (CameraDetail)paramObject;
      if (Double.compare(localCameraDetail.focalLength, this.focalLength) != 0)
        return false;
      if (this.isInLandscapeOrientation != localCameraDetail.isInLandscapeOrientation)
        return false;
      if (Double.compare(localCameraDetail.overlap, this.overlap) != 0)
        return false;
      if (Double.compare(localCameraDetail.sensorHeight, this.sensorHeight) != 0)
        return false;
      if (Double.compare(localCameraDetail.sensorResolution, this.sensorResolution) != 0)
        return false;
      if (Double.compare(localCameraDetail.sensorWidth, this.sensorWidth) != 0)
        return false;
      if (Double.compare(localCameraDetail.sidelap, this.sidelap) != 0)
        return false;
      if (this.name == null)
        break;
    }
    while (this.name.equals(localCameraDetail.name));
    while (true)
    {
      return false;
      if (localCameraDetail.name == null)
        break;
    }
  }

  public double getFocalLength()
  {
    return this.focalLength;
  }

  public String getName()
  {
    return this.name;
  }

  public double getOverlap()
  {
    return this.overlap;
  }

  public double getSensorHeight()
  {
    return this.sensorHeight;
  }

  public Double getSensorLateralSize()
  {
    if (this.isInLandscapeOrientation)
      return Double.valueOf(this.sensorWidth);
    return Double.valueOf(this.sensorHeight);
  }

  public Double getSensorLongitudinalSize()
  {
    if (this.isInLandscapeOrientation)
      return Double.valueOf(this.sensorHeight);
    return Double.valueOf(this.sensorWidth);
  }

  public double getSensorResolution()
  {
    return this.sensorResolution;
  }

  public double getSensorWidth()
  {
    return this.sensorWidth;
  }

  public double getSidelap()
  {
    return this.sidelap;
  }

  public int hashCode()
  {
    if (this.name != null);
    for (int i = this.name.hashCode(); ; i = 0)
    {
      long l1 = Double.doubleToLongBits(this.sensorWidth);
      int j = i * 31 + (int)(l1 ^ l1 >>> 32);
      long l2 = Double.doubleToLongBits(this.sensorHeight);
      int k = j * 31 + (int)(l2 ^ l2 >>> 32);
      long l3 = Double.doubleToLongBits(this.sensorResolution);
      int m = k * 31 + (int)(l3 ^ l3 >>> 32);
      long l4 = Double.doubleToLongBits(this.focalLength);
      int n = m * 31 + (int)(l4 ^ l4 >>> 32);
      long l5 = Double.doubleToLongBits(this.overlap);
      int i1 = n * 31 + (int)(l5 ^ l5 >>> 32);
      long l6 = Double.doubleToLongBits(this.sidelap);
      int i2 = 31 * (i1 * 31 + (int)(l6 ^ l6 >>> 32));
      boolean bool = this.isInLandscapeOrientation;
      int i3 = 0;
      if (bool)
        i3 = 1;
      return i2 + i3;
    }
  }

  public boolean isInLandscapeOrientation()
  {
    return this.isInLandscapeOrientation;
  }

  public String toString()
  {
    return "CameraDetail{name='" + this.name + '\'' + ", sensorWidth=" + this.sensorWidth + ", sensorHeight=" + this.sensorHeight + ", sensorResolution=" + this.sensorResolution + ", focalLength=" + this.focalLength + ", overlap=" + this.overlap + ", sidelap=" + this.sidelap + ", isInLandscapeOrientation=" + this.isInLandscapeOrientation + '}';
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(this.name);
    paramParcel.writeDouble(this.sensorWidth);
    paramParcel.writeDouble(this.sensorHeight);
    paramParcel.writeDouble(this.sensorResolution);
    paramParcel.writeDouble(this.focalLength);
    paramParcel.writeDouble(this.overlap);
    paramParcel.writeDouble(this.sidelap);
    if (this.isInLandscapeOrientation);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.complex.CameraDetail
 * JD-Core Version:    0.6.2
 */