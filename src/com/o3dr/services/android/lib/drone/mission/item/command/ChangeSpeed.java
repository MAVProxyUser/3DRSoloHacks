package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class ChangeSpeed extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<ChangeSpeed> CREATOR = new Parcelable.Creator()
  {
    public ChangeSpeed createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ChangeSpeed(paramAnonymousParcel, null);
    }

    public ChangeSpeed[] newArray(int paramAnonymousInt)
    {
      return new ChangeSpeed[paramAnonymousInt];
    }
  };
  private double speed;

  public ChangeSpeed()
  {
    super(MissionItemType.CHANGE_SPEED);
  }

  private ChangeSpeed(Parcel paramParcel)
  {
    super(paramParcel);
    this.speed = paramParcel.readDouble();
  }

  public ChangeSpeed(ChangeSpeed paramChangeSpeed)
  {
    super(MissionItemType.CHANGE_SPEED);
    this.speed = paramChangeSpeed.speed;
  }

  public MissionItem clone()
  {
    return new ChangeSpeed(this);
  }

  public double getSpeed()
  {
    return this.speed;
  }

  public void setSpeed(double paramDouble)
  {
    this.speed = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.speed);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.ChangeSpeed
 * JD-Core Version:    0.6.2
 */