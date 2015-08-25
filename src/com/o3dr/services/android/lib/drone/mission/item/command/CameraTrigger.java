package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class CameraTrigger extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<CameraTrigger> CREATOR = new Parcelable.Creator()
  {
    public CameraTrigger createFromParcel(Parcel paramAnonymousParcel)
    {
      return new CameraTrigger(paramAnonymousParcel, null);
    }

    public CameraTrigger[] newArray(int paramAnonymousInt)
    {
      return new CameraTrigger[paramAnonymousInt];
    }
  };
  private double triggerDistance;

  public CameraTrigger()
  {
    super(MissionItemType.CAMERA_TRIGGER);
  }

  private CameraTrigger(Parcel paramParcel)
  {
    super(paramParcel);
    this.triggerDistance = paramParcel.readDouble();
  }

  public CameraTrigger(CameraTrigger paramCameraTrigger)
  {
    super(MissionItemType.CAMERA_TRIGGER);
    this.triggerDistance = paramCameraTrigger.triggerDistance;
  }

  public MissionItem clone()
  {
    return new CameraTrigger(this);
  }

  public double getTriggerDistance()
  {
    return this.triggerDistance;
  }

  public void setTriggerDistance(double paramDouble)
  {
    this.triggerDistance = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.triggerDistance);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.CameraTrigger
 * JD-Core Version:    0.6.2
 */