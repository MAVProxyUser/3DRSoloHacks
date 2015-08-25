package com.o3dr.services.android.lib.drone.mission.item.spatial;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;

public class SplineWaypoint extends BaseSpatialItem
  implements Parcelable
{
  public static final Parcelable.Creator<SplineWaypoint> CREATOR = new Parcelable.Creator()
  {
    public SplineWaypoint createFromParcel(Parcel paramAnonymousParcel)
    {
      return new SplineWaypoint(paramAnonymousParcel, null);
    }

    public SplineWaypoint[] newArray(int paramAnonymousInt)
    {
      return new SplineWaypoint[paramAnonymousInt];
    }
  };
  private double delay;

  public SplineWaypoint()
  {
    super(MissionItemType.SPLINE_WAYPOINT);
  }

  private SplineWaypoint(Parcel paramParcel)
  {
    super(paramParcel);
    this.delay = paramParcel.readDouble();
  }

  public SplineWaypoint(SplineWaypoint paramSplineWaypoint)
  {
    super(paramSplineWaypoint);
    this.delay = paramSplineWaypoint.delay;
  }

  public MissionItem clone()
  {
    return new SplineWaypoint(this);
  }

  public double getDelay()
  {
    return this.delay;
  }

  public void setDelay(double paramDouble)
  {
    this.delay = paramDouble;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeDouble(this.delay);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.spatial.SplineWaypoint
 * JD-Core Version:    0.6.2
 */