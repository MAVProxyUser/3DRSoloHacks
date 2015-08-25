package com.o3dr.services.android.lib.drone.mission.item.spatial;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;

public class Land extends BaseSpatialItem
  implements Parcelable
{
  public static final Parcelable.Creator<Land> CREATOR = new Parcelable.Creator()
  {
    public Land createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Land(paramAnonymousParcel, null);
    }

    public Land[] newArray(int paramAnonymousInt)
    {
      return new Land[paramAnonymousInt];
    }
  };

  public Land()
  {
    super(MissionItemType.LAND);
  }

  private Land(Parcel paramParcel)
  {
    super(paramParcel);
  }

  public Land(Land paramLand)
  {
    super(paramLand);
  }

  public MissionItem clone()
  {
    return new Land(this);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.spatial.Land
 * JD-Core Version:    0.6.2
 */