package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class EpmGripper extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<EpmGripper> CREATOR = new Parcelable.Creator()
  {
    public EpmGripper createFromParcel(Parcel paramAnonymousParcel)
    {
      return new EpmGripper(paramAnonymousParcel, null);
    }

    public EpmGripper[] newArray(int paramAnonymousInt)
    {
      return new EpmGripper[paramAnonymousInt];
    }
  };
  private boolean release;

  public EpmGripper()
  {
    super(MissionItemType.EPM_GRIPPER);
  }

  private EpmGripper(Parcel paramParcel)
  {
    super(paramParcel);
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.release = bool;
      return;
    }
  }

  public EpmGripper(EpmGripper paramEpmGripper)
  {
    this();
    this.release = paramEpmGripper.release;
  }

  public MissionItem clone()
  {
    return new EpmGripper(this);
  }

  public boolean isRelease()
  {
    return this.release;
  }

  public void setRelease(boolean paramBoolean)
  {
    this.release = paramBoolean;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    if (this.release);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.EpmGripper
 * JD-Core Version:    0.6.2
 */