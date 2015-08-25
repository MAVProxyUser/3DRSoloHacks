package com.o3dr.services.android.lib.drone.mission.item.command;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.Command;

public class Takeoff extends MissionItem
  implements MissionItem.Command, Parcelable
{
  public static final Parcelable.Creator<Takeoff> CREATOR = new Parcelable.Creator()
  {
    public Takeoff createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Takeoff(paramAnonymousParcel, null);
    }

    public Takeoff[] newArray(int paramAnonymousInt)
    {
      return new Takeoff[paramAnonymousInt];
    }
  };
  public static final double DEFAULT_TAKEOFF_ALTITUDE = 10.0D;
  private double takeoffAltitude;

  public Takeoff()
  {
    super(MissionItemType.TAKEOFF);
  }

  private Takeoff(Parcel paramParcel)
  {
    super(paramParcel);
    this.takeoffAltitude = paramParcel.readDouble();
  }

  public Takeoff(Takeoff paramTakeoff)
  {
    this();
    this.takeoffAltitude = paramTakeoff.takeoffAltitude;
  }

  public MissionItem clone()
  {
    return new Takeoff(this);
  }

  public double getTakeoffAltitude()
  {
    return this.takeoffAltitude;
  }

  public void setTakeoffAltitude(double paramDouble)
  {
    this.takeoffAltitude = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.takeoffAltitude);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.command.Takeoff
 * JD-Core Version:    0.6.2
 */