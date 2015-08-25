package com.o3dr.services.android.lib.drone.calibration.magnetometer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MagnetometerCalibrationProgress
  implements Parcelable
{
  public static final Parcelable.Creator<MagnetometerCalibrationProgress> CREATOR = new Parcelable.Creator()
  {
    public MagnetometerCalibrationProgress createFromParcel(Parcel paramAnonymousParcel)
    {
      return new MagnetometerCalibrationProgress(paramAnonymousParcel, null);
    }

    public MagnetometerCalibrationProgress[] newArray(int paramAnonymousInt)
    {
      return new MagnetometerCalibrationProgress[paramAnonymousInt];
    }
  };
  private byte compassId;
  private byte completionPercentage;
  private float directionX;
  private float directionY;
  private float directionZ;

  public MagnetometerCalibrationProgress()
  {
  }

  public MagnetometerCalibrationProgress(byte paramByte1, byte paramByte2, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.compassId = paramByte1;
    this.completionPercentage = paramByte2;
    this.directionX = paramFloat1;
    this.directionY = paramFloat2;
    this.directionZ = paramFloat3;
  }

  private MagnetometerCalibrationProgress(Parcel paramParcel)
  {
    this.compassId = paramParcel.readByte();
    this.completionPercentage = paramParcel.readByte();
    this.directionX = paramParcel.readFloat();
    this.directionY = paramParcel.readFloat();
    this.directionZ = paramParcel.readFloat();
  }

  public int describeContents()
  {
    return 0;
  }

  public byte getCompassId()
  {
    return this.compassId;
  }

  public byte getCompletionPercentage()
  {
    return this.completionPercentage;
  }

  public float getDirectionX()
  {
    return this.directionX;
  }

  public float getDirectionY()
  {
    return this.directionY;
  }

  public float getDirectionZ()
  {
    return this.directionZ;
  }

  public void setCompassId(byte paramByte)
  {
    this.compassId = paramByte;
  }

  public void setCompletionPercentage(byte paramByte)
  {
    this.completionPercentage = paramByte;
  }

  public void setDirectionX(float paramFloat)
  {
    this.directionX = paramFloat;
  }

  public void setDirectionY(float paramFloat)
  {
    this.directionY = paramFloat;
  }

  public void setDirectionZ(float paramFloat)
  {
    this.directionZ = paramFloat;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeByte(this.compassId);
    paramParcel.writeByte(this.completionPercentage);
    paramParcel.writeFloat(this.directionX);
    paramParcel.writeFloat(this.directionY);
    paramParcel.writeFloat(this.directionZ);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.calibration.magnetometer.MagnetometerCalibrationProgress
 * JD-Core Version:    0.6.2
 */