package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class ReturnToLaunch extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<ReturnToLaunch> CREATOR = new Parcelable.Creator()
  {
    public ReturnToLaunch createFromParcel(Parcel paramAnonymousParcel)
    {
      return new ReturnToLaunch(paramAnonymousParcel, null);
    }

    public ReturnToLaunch[] newArray(int paramAnonymousInt)
    {
      return new ReturnToLaunch[paramAnonymousInt];
    }
  };
  private double returnAltitude;

  public ReturnToLaunch()
  {
    super(MissionItemType.RETURN_TO_LAUNCH);
  }

  private ReturnToLaunch(Parcel paramParcel)
  {
    super(paramParcel);
    this.returnAltitude = paramParcel.readDouble();
  }

  public ReturnToLaunch(ReturnToLaunch paramReturnToLaunch)
  {
    this();
    this.returnAltitude = paramReturnToLaunch.returnAltitude;
  }

  public MissionItem clone()
  {
    return new ReturnToLaunch(this);
  }

  public double getReturnAltitude()
  {
    return this.returnAltitude;
  }

  public void setReturnAltitude(double paramDouble)
  {
    this.returnAltitude = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.returnAltitude);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.ReturnToLaunch
 * JD-Core Version:    0.6.2
 */