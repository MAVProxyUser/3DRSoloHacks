package com.o3dr.services.android.lib.drone.property;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.BitSet;

public class EkfStatus
  implements Parcelable
{
  public static final Parcelable.Creator<EkfStatus> CREATOR = new Parcelable.Creator()
  {
    public EkfStatus createFromParcel(Parcel paramAnonymousParcel)
    {
      return new EkfStatus(paramAnonymousParcel, null);
    }

    public EkfStatus[] newArray(int paramAnonymousInt)
    {
      return new EkfStatus[paramAnonymousInt];
    }
  };
  private static final int FLAGS_BIT_COUNT = 16;
  private static final String TAG = EkfStatus.class.getSimpleName();
  private float compassVariance;
  private final BitSet flags;
  private float horizontalPositionVariance;
  private float terrainAltitudeVariance;
  private float velocityVariance;
  private float verticalPositionVariance;

  public EkfStatus()
  {
    this.flags = new BitSet(16);
  }

  private EkfStatus(Parcel paramParcel)
  {
    this.velocityVariance = paramParcel.readFloat();
    this.horizontalPositionVariance = paramParcel.readFloat();
    this.verticalPositionVariance = paramParcel.readFloat();
    this.compassVariance = paramParcel.readFloat();
    this.terrainAltitudeVariance = paramParcel.readFloat();
    this.flags = ((BitSet)paramParcel.readSerializable());
  }

  public EkfStatus(short paramShort, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    this();
    this.compassVariance = paramFloat1;
    this.horizontalPositionVariance = paramFloat2;
    this.terrainAltitudeVariance = paramFloat3;
    this.velocityVariance = paramFloat4;
    this.verticalPositionVariance = paramFloat5;
    fromShortToBitSet(paramShort);
  }

  private void fromShortToBitSet(short paramShort)
  {
    EkfFlags[] arrayOfEkfFlags = EkfFlags.values();
    int i = arrayOfEkfFlags.length;
    int j = 0;
    if (j < i)
    {
      BitSet localBitSet = this.flags;
      if ((paramShort & arrayOfEkfFlags[j].value) != 0);
      for (boolean bool = true; ; bool = false)
      {
        localBitSet.set(j, bool);
        j++;
        break;
      }
    }
  }

  public int describeContents()
  {
    return 0;
  }

  public float getCompassVariance()
  {
    return this.compassVariance;
  }

  public float getHorizontalPositionVariance()
  {
    return this.horizontalPositionVariance;
  }

  public float getTerrainAltitudeVariance()
  {
    return this.terrainAltitudeVariance;
  }

  public float getVelocityVariance()
  {
    return this.velocityVariance;
  }

  public float getVerticalPositionVariance()
  {
    return this.verticalPositionVariance;
  }

  public boolean isPositionOk(boolean paramBoolean)
  {
    if (paramBoolean)
      return (this.flags.get(EkfFlags.EKF_POS_HORIZ_ABS.ordinal())) && (!this.flags.get(EkfFlags.EKF_CONST_POS_MODE.ordinal()));
    boolean bool1;
    if (!this.flags.get(EkfFlags.EKF_POS_HORIZ_ABS.ordinal()))
    {
      boolean bool2 = this.flags.get(EkfFlags.EKF_PRED_POS_HORIZ_ABS.ordinal());
      bool1 = false;
      if (!bool2);
    }
    else
    {
      bool1 = true;
    }
    return bool1;
  }

  public void setCompassVariance(float paramFloat)
  {
    this.compassVariance = paramFloat;
  }

  public void setHorizontalPositionVariance(float paramFloat)
  {
    this.horizontalPositionVariance = paramFloat;
  }

  public void setTerrainAltitudeVariance(float paramFloat)
  {
    this.terrainAltitudeVariance = paramFloat;
  }

  public void setVelocityVariance(float paramFloat)
  {
    this.velocityVariance = paramFloat;
  }

  public void setVerticalPositionVariance(float paramFloat)
  {
    this.verticalPositionVariance = paramFloat;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeFloat(this.velocityVariance);
    paramParcel.writeFloat(this.horizontalPositionVariance);
    paramParcel.writeFloat(this.verticalPositionVariance);
    paramParcel.writeFloat(this.compassVariance);
    paramParcel.writeFloat(this.terrainAltitudeVariance);
    paramParcel.writeSerializable(this.flags);
  }

  private static enum EkfFlags
  {
    final int value;

    static
    {
      EKF_POS_HORIZ_REL = new EkfFlags("EKF_POS_HORIZ_REL", 3, 8);
      EKF_POS_HORIZ_ABS = new EkfFlags("EKF_POS_HORIZ_ABS", 4, 16);
      EKF_POS_VERT_ABS = new EkfFlags("EKF_POS_VERT_ABS", 5, 32);
      EKF_POS_VERT_AGL = new EkfFlags("EKF_POS_VERT_AGL", 6, 64);
      EKF_CONST_POS_MODE = new EkfFlags("EKF_CONST_POS_MODE", 7, 128);
      EKF_PRED_POS_HORIZ_REL = new EkfFlags("EKF_PRED_POS_HORIZ_REL", 8, 256);
      EKF_PRED_POS_HORIZ_ABS = new EkfFlags("EKF_PRED_POS_HORIZ_ABS", 9, 512);
      EkfFlags[] arrayOfEkfFlags = new EkfFlags[10];
      arrayOfEkfFlags[0] = EKF_ATTITUDE;
      arrayOfEkfFlags[1] = EKF_VELOCITY_HORIZ;
      arrayOfEkfFlags[2] = EKF_VELOCITY_VERT;
      arrayOfEkfFlags[3] = EKF_POS_HORIZ_REL;
      arrayOfEkfFlags[4] = EKF_POS_HORIZ_ABS;
      arrayOfEkfFlags[5] = EKF_POS_VERT_ABS;
      arrayOfEkfFlags[6] = EKF_POS_VERT_AGL;
      arrayOfEkfFlags[7] = EKF_CONST_POS_MODE;
      arrayOfEkfFlags[8] = EKF_PRED_POS_HORIZ_REL;
      arrayOfEkfFlags[9] = EKF_PRED_POS_HORIZ_ABS;
    }

    private EkfFlags(int paramInt)
    {
      this.value = paramInt;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.property.EkfStatus
 * JD-Core Version:    0.6.2
 */