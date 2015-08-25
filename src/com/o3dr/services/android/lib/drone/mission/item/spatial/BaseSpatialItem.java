package com.o3dr.services.android.lib.drone.mission.item.spatial;

import android.os.Parcel;
import android.os.Parcelable;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.SpatialItem;

public abstract class BaseSpatialItem extends MissionItem
  implements MissionItem.SpatialItem, Parcelable
{
  private LatLongAlt coordinate;

  protected BaseSpatialItem(Parcel paramParcel)
  {
    super(paramParcel);
    this.coordinate = ((LatLongAlt)paramParcel.readParcelable(LatLongAlt.class.getClassLoader()));
  }

  protected BaseSpatialItem(MissionItemType paramMissionItemType)
  {
    super(paramMissionItemType);
  }

  protected BaseSpatialItem(BaseSpatialItem paramBaseSpatialItem)
  {
    this(paramBaseSpatialItem.getType());
    if (paramBaseSpatialItem.coordinate == null);
    for (LatLongAlt localLatLongAlt = null; ; localLatLongAlt = new LatLongAlt(paramBaseSpatialItem.coordinate))
    {
      this.coordinate = localLatLongAlt;
      return;
    }
  }

  public LatLongAlt getCoordinate()
  {
    return this.coordinate;
  }

  public void setCoordinate(LatLongAlt paramLatLongAlt)
  {
    this.coordinate = paramLatLongAlt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    super.writeToParcel(paramParcel, paramInt);
    paramParcel.writeParcelable(this.coordinate, paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.spatial.BaseSpatialItem
 * JD-Core Version:    0.6.2
 */