package com.o3dr.services.android.lib.drone.calibration.magnetometer;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class MagnetometerCalibrationResult
  implements Parcelable
{
  public static final Parcelable.Creator<MagnetometerCalibrationResult> CREATOR = new Parcelable.Creator()
  {
    public MagnetometerCalibrationResult createFromParcel(Parcel paramAnonymousParcel)
    {
      return new MagnetometerCalibrationResult(paramAnonymousParcel, null);
    }

    public MagnetometerCalibrationResult[] newArray(int paramAnonymousInt)
    {
      return new MagnetometerCalibrationResult[paramAnonymousInt];
    }
  };
  private boolean autoSaved;
  private boolean calibrationSuccessful;
  private byte compassId;
  private float fitness;
  private float xDiag;
  private float xOffDiag;
  private float xOffset;
  private float yDiag;
  private float yOffDiag;
  private float yOffset;
  private float zDiag;
  private float zOffDiag;
  private float zOffset;

  public MagnetometerCalibrationResult()
  {
  }

  public MagnetometerCalibrationResult(byte paramByte, boolean paramBoolean1, boolean paramBoolean2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
  {
    this.compassId = paramByte;
    this.calibrationSuccessful = paramBoolean1;
    this.autoSaved = paramBoolean2;
    this.fitness = paramFloat1;
    this.xDiag = paramFloat5;
    this.xOffDiag = paramFloat8;
    this.xOffset = paramFloat2;
    this.yDiag = paramFloat6;
    this.yOffDiag = paramFloat9;
    this.yOffset = paramFloat3;
    this.zDiag = paramFloat7;
    this.zOffDiag = paramFloat10;
    this.zOffset = paramFloat4;
  }

  private MagnetometerCalibrationResult(Parcel paramParcel)
  {
    this.compassId = paramParcel.readByte();
    this.fitness = paramParcel.readFloat();
    this.xOffset = paramParcel.readFloat();
    this.yOffset = paramParcel.readFloat();
    this.zOffset = paramParcel.readFloat();
    this.xDiag = paramParcel.readFloat();
    this.yDiag = paramParcel.readFloat();
    this.zDiag = paramParcel.readFloat();
    this.xOffDiag = paramParcel.readFloat();
    this.yOffDiag = paramParcel.readFloat();
    this.zOffDiag = paramParcel.readFloat();
    boolean bool2;
    if (paramParcel.readByte() != 0)
    {
      bool2 = bool1;
      this.autoSaved = bool2;
      if (paramParcel.readByte() == 0)
        break label126;
    }
    while (true)
    {
      this.calibrationSuccessful = bool1;
      return;
      bool2 = false;
      break;
      label126: bool1 = false;
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public byte getCompassId()
  {
    return this.compassId;
  }

  public float getFitness()
  {
    return this.fitness;
  }

  public float getxDiag()
  {
    return this.xDiag;
  }

  public float getxOffDiag()
  {
    return this.xOffDiag;
  }

  public float getxOffset()
  {
    return this.xOffset;
  }

  public float getyDiag()
  {
    return this.yDiag;
  }

  public float getyOffDiag()
  {
    return this.yOffDiag;
  }

  public float getyOffset()
  {
    return this.yOffset;
  }

  public float getzDiag()
  {
    return this.zDiag;
  }

  public float getzOffDiag()
  {
    return this.zOffDiag;
  }

  public float getzOffset()
  {
    return this.zOffset;
  }

  public boolean isAutoSaved()
  {
    return this.autoSaved;
  }

  public boolean isCalibrationSuccessful()
  {
    return this.calibrationSuccessful;
  }

  public void setAutoSaved(boolean paramBoolean)
  {
    this.autoSaved = paramBoolean;
  }

  public void setCalibrationSuccessful(boolean paramBoolean)
  {
    this.calibrationSuccessful = paramBoolean;
  }

  public void setCompassId(byte paramByte)
  {
    this.compassId = paramByte;
  }

  public void setFitness(float paramFloat)
  {
    this.fitness = paramFloat;
  }

  public void setxDiag(float paramFloat)
  {
    this.xDiag = paramFloat;
  }

  public void setxOffDiag(float paramFloat)
  {
    this.xOffDiag = paramFloat;
  }

  public void setxOffset(float paramFloat)
  {
    this.xOffset = paramFloat;
  }

  public void setyDiag(float paramFloat)
  {
    this.yDiag = paramFloat;
  }

  public void setyOffDiag(float paramFloat)
  {
    this.yOffDiag = paramFloat;
  }

  public void setyOffset(float paramFloat)
  {
    this.yOffset = paramFloat;
  }

  public void setzDiag(float paramFloat)
  {
    this.zDiag = paramFloat;
  }

  public void setzOffDiag(float paramFloat)
  {
    this.zOffDiag = paramFloat;
  }

  public void setzOffset(float paramFloat)
  {
    this.zOffset = paramFloat;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    byte b1 = 1;
    paramParcel.writeByte(this.compassId);
    paramParcel.writeFloat(this.fitness);
    paramParcel.writeFloat(this.xOffset);
    paramParcel.writeFloat(this.yOffset);
    paramParcel.writeFloat(this.zOffset);
    paramParcel.writeFloat(this.xDiag);
    paramParcel.writeFloat(this.yDiag);
    paramParcel.writeFloat(this.zDiag);
    paramParcel.writeFloat(this.xOffDiag);
    paramParcel.writeFloat(this.yOffDiag);
    paramParcel.writeFloat(this.zOffDiag);
    byte b2;
    if (this.autoSaved)
    {
      b2 = b1;
      paramParcel.writeByte(b2);
      if (!this.calibrationSuccessful)
        break label125;
    }
    while (true)
    {
      paramParcel.writeByte(b1);
      return;
      b2 = 0;
      break;
      label125: b1 = 0;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.calibration.magnetometer.MagnetometerCalibrationResult
 * JD-Core Version:    0.6.2
 */