package com.o3dr.services.android.lib.drone.mission.item.spatial;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;

public class Waypoint extends BaseSpatialItem
  implements Parcelable
{
  public static final Parcelable.Creator<Waypoint> CREATOR = new Parcelable.Creator()
  {
    public Waypoint createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Waypoint(paramAnonymousParcel, null);
    }

    public Waypoint[] newArray(int paramAnonymousInt)
    {
      return new Waypoint[paramAnonymousInt];
    }
  };
  private double acceptanceRadius;
  private double delay;
  private boolean orbitCCW;
  private double orbitalRadius;
  private double yawAngle;

  public Waypoint()
  {
    super(MissionItemType.WAYPOINT);
  }

  private Waypoint(Parcel paramParcel)
  {
    super(paramParcel);
    this.delay = paramParcel.readDouble();
    this.acceptanceRadius = paramParcel.readDouble();
    this.yawAngle = paramParcel.readDouble();
    this.orbitalRadius = paramParcel.readDouble();
    if (paramParcel.readByte() != 0);
    for (boolean bool = true; ; bool = false)
    {
      this.orbitCCW = bool;
      return;
    }
  }

  public Waypoint(Waypoint paramWaypoint)
  {
    super(paramWaypoint);
    this.delay = paramWaypoint.delay;
    this.acceptanceRadius = paramWaypoint.acceptanceRadius;
    this.yawAngle = paramWaypoint.yawAngle;
    this.orbitalRadius = paramWaypoint.orbitalRadius;
    this.orbitCCW = paramWaypoint.orbitCCW;
  }

  public MissionItem clone()
  {
    return new Waypoint(this);
  }

  public double getAcceptanceRadius()
  {
    return this.acceptanceRadius;
  }

  public double getDelay()
  {
    return this.delay;
  }

  public double getOrbitalRadius()
  {
    return this.orbitalRadius;
  }

  public double getYawAngle()
  {
    return this.yawAngle;
  }

  public boolean isOrbitCCW()
  {
    return this.orbitCCW;
  }

  public void setAcceptanceRadius(double paramDouble)
  {
    this.acceptanceRadius = paramDouble;
  }

  public void setDelay(double paramDouble)
  {
    this.delay = paramDouble;
  }

  public void setOrbitCCW(boolean paramBoolean)
  {
    this.orbitCCW = paramBoolean;
  }

  public void setOrbitalRadius(double paramDouble)
  {
    this.orbitalRadius = paramDouble;
  }

  public void setYawAngle(double paramDouble)
  {
    this.yawAngle = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.delay);
    paramParcel.writeDouble(this.acceptanceRadius);
    paramParcel.writeDouble(this.yawAngle);
    paramParcel.writeDouble(this.orbitalRadius);
    if (this.orbitCCW);
    for (byte b = 1; ; b = 0)
    {
      paramParcel.writeByte(b);
      return;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.spatial.Waypoint
 * JD-Core Version:    0.6.2
 */