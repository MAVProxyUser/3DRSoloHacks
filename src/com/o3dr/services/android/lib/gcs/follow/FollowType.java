package com.o3dr.services.android.lib.gcs.follow;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.List;

public enum FollowType
  implements Parcelable
{
  public static final Parcelable.Creator<FollowType> CREATOR = new Parcelable.Creator()
  {
    public FollowType createFromParcel(Parcel paramAnonymousParcel)
    {
      return FollowType.valueOf(paramAnonymousParcel.readString());
    }

    public FollowType[] newArray(int paramAnonymousInt)
    {
      return new FollowType[paramAnonymousInt];
    }
  };
  public static final String EXTRA_FOLLOW_RADIUS = "extra_follow_radius";
  public static final String EXTRA_FOLLOW_ROI_TARGET = "extra_follow_roi_target";
  private final String typeLabel;

  static
  {
    LEAD = new FollowType("LEAD", 1, "Lead");
    RIGHT = new FollowType("RIGHT", 2, "Right");
    LEFT = new FollowType("LEFT", 3, "Left");
    CIRCLE = new FollowType("CIRCLE", 4, "Circle");
    ABOVE = new FollowType.1("ABOVE", 5, "Above");
    SPLINE_LEASH = new FollowType("SPLINE_LEASH", 6, "Vector Leash");
    SPLINE_ABOVE = new FollowType.2("SPLINE_ABOVE", 7, "Vector Above");
    GUIDED_SCAN = new FollowType.3("GUIDED_SCAN", 8, "Guided Scan");
    LOOK_AT_ME = new FollowType.4("LOOK_AT_ME", 9, "Look At Me");
    FollowType[] arrayOfFollowType = new FollowType[10];
    arrayOfFollowType[0] = LEASH;
    arrayOfFollowType[1] = LEAD;
    arrayOfFollowType[2] = RIGHT;
    arrayOfFollowType[3] = LEFT;
    arrayOfFollowType[4] = CIRCLE;
    arrayOfFollowType[5] = ABOVE;
    arrayOfFollowType[6] = SPLINE_LEASH;
    arrayOfFollowType[7] = SPLINE_ABOVE;
    arrayOfFollowType[8] = GUIDED_SCAN;
    arrayOfFollowType[9] = LOOK_AT_ME;
  }

  private FollowType(String paramString)
  {
    this.typeLabel = paramString;
  }

  public static List<FollowType> getFollowTypes(boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(LEASH);
    localArrayList.add(LEAD);
    localArrayList.add(RIGHT);
    localArrayList.add(LEFT);
    localArrayList.add(CIRCLE);
    localArrayList.add(ABOVE);
    localArrayList.add(GUIDED_SCAN);
    localArrayList.add(LOOK_AT_ME);
    if (paramBoolean)
    {
      localArrayList.add(SPLINE_LEASH);
      localArrayList.add(SPLINE_ABOVE);
    }
    return localArrayList;
  }

  public int describeContents()
  {
    return 0;
  }

  public String getTypeLabel()
  {
    return this.typeLabel;
  }

  public boolean hasParam(String paramString)
  {
    int i = 1;
    int j = -1;
    switch (paramString.hashCode())
    {
    default:
    case 1619123953:
    case -1924169149:
    }
    while (true)
      switch (j)
      {
      default:
        i = 0;
      case 0:
        return i;
        if (paramString.equals("extra_follow_radius"))
        {
          j = 0;
          continue;
          if (paramString.equals("extra_follow_roi_target"))
            j = i;
        }
        break;
      case 1:
      }
    return false;
  }

  public String toString()
  {
    return getTypeLabel();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeString(name());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.gcs.follow.FollowType
 * JD-Core Version:    0.6.2
 */