package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class YawCondition extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<YawCondition> CREATOR = new Parcelable.Creator()
  {
    public YawCondition createFromParcel(Parcel paramAnonymousParcel)
    {
      return new YawCondition(paramAnonymousParcel, null);
    }

    public YawCondition[] newArray(int paramAnonymousInt)
    {
      return new YawCondition[paramAnonymousInt];
    }
  };
  private double angle;
  private double angularSpeed;
  private boolean isRelative;

  public YawCondition()
  {
    super(MissionItemType.YAW_CONDITION);
  }

  private YawCondition(Parcel paramParcel)
  {
    super(paramParcel);
    this.angle = paramParcel.readDouble();
    this.angularSpeed = paramParcel.readDouble();
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.isRelative = bool;
      return;
    }
  }

  public YawCondition(YawCondition paramYawCondition)
  {
    this();
    this.angle = paramYawCondition.angle;
    this.angularSpeed = paramYawCondition.angularSpeed;
    this.isRelative = paramYawCondition.isRelative;
  }

  public MissionItem clone()
  {
    return new YawCondition(this);
  }

  public double getAngle()
  {
    return this.angle;
  }

  public double getAngularSpeed()
  {
    return this.angularSpeed;
  }

  public boolean isRelative()
  {
    return this.isRelative;
  }

  public void setAngle(double paramDouble)
  {
    this.angle = paramDouble;
  }

  public void setAngularSpeed(double paramDouble)
  {
    this.angularSpeed = paramDouble;
  }

  public void setRelative(boolean paramBoolean)
  {
    this.isRelative = paramBoolean;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.angle);
    paramParcel.writeDouble(this.angularSpeed);
    if (this.isRelative);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.YawCondition
 * JD-Core Version:    0.6.2
 */