package com.o3dr.services.android.lib.drone.mission.item;

import android.os.Parcel;
import android.os.Parcelable;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;

public abstract class MissionItem
  implements Cloneable, Parcelable
{
  private final MissionItemType type;

  protected MissionItem(Parcel paramParcel)
  {
    this.type = MissionItemType.values()[paramParcel.readInt()];
  }

  protected MissionItem(MissionItemType paramMissionItemType)
  {
    this.type = paramMissionItemType;
  }

  public abstract MissionItem clone();

  public int describeContents()
  {
    return 0;
  }

  public MissionItemType getType()
  {
    return this.type;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.type.ordinal());
  }

  public static abstract interface Command
  {
  }

  public static abstract interface ComplexItem<T extends MissionItem>
  {
    public abstract void copy(T paramT);
  }

  public static abstract interface SpatialItem
  {
    public abstract LatLongAlt getCoordinate();

    public abstract void setCoordinate(LatLongAlt paramLatLongAlt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.MissionItem
 * JD-Core Version:    0.6.2
 */