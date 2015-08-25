package com.o3dr.services.android.lib.drone.mission.item.spatial;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;

public class RegionOfInterest extends BaseSpatialItem
  implements Parcelable
{
  public static final Parcelable.Creator<RegionOfInterest> CREATOR = new Parcelable.Creator()
  {
    public RegionOfInterest createFromParcel(Parcel paramAnonymousParcel)
    {
      return new RegionOfInterest(paramAnonymousParcel, null);
    }

    public RegionOfInterest[] newArray(int paramAnonymousInt)
    {
      return new RegionOfInterest[paramAnonymousInt];
    }
  };

  public RegionOfInterest()
  {
    super(MissionItemType.REGION_OF_INTEREST);
  }

  private RegionOfInterest(Parcel paramParcel)
  {
    super(paramParcel);
  }

  public RegionOfInterest(RegionOfInterest paramRegionOfInterest)
  {
    super(paramRegionOfInterest);
  }

  public MissionItem clone()
  {
    return new RegionOfInterest(this);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.item.spatial.RegionOfInterest
 * JD-Core Version:    0.6.2
 */