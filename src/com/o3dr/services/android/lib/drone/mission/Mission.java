package com.o3dr.services.android.lib.drone.mission;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Mission
  implements Parcelable
{
  public static final Parcelable.Creator<Mission> CREATOR = new Parcelable.Creator()
  {
    public Mission createFromParcel(Parcel paramAnonymousParcel)
    {
      return new Mission(paramAnonymousParcel, null);
    }

    public Mission[] newArray(int paramAnonymousInt)
    {
      return new Mission[paramAnonymousInt];
    }
  };
  private int currentMissionItem;
  private final List<MissionItem> missionItemsList = new ArrayList();

  public Mission()
  {
  }

  private Mission(Parcel paramParcel)
  {
    this.currentMissionItem = paramParcel.readInt();
    ArrayList localArrayList = new ArrayList();
    paramParcel.readTypedList(localArrayList, Bundle.CREATOR);
    if (!localArrayList.isEmpty())
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        Bundle localBundle = (Bundle)localIterator.next();
        this.missionItemsList.add(MissionItemType.restoreMissionItemFromBundle(localBundle));
      }
    }
  }

  public void addMissionItem(int paramInt, MissionItem paramMissionItem)
  {
    this.missionItemsList.add(paramInt, paramMissionItem);
  }

  public void addMissionItem(MissionItem paramMissionItem)
  {
    this.missionItemsList.add(paramMissionItem);
  }

  public void clear()
  {
    this.missionItemsList.clear();
  }

  public int describeContents()
  {
    return 0;
  }

  public int getCurrentMissionItem()
  {
    return this.currentMissionItem;
  }

  public MissionItem getMissionItem(int paramInt)
  {
    return (MissionItem)this.missionItemsList.get(paramInt);
  }

  public List<MissionItem> getMissionItems()
  {
    return this.missionItemsList;
  }

  public void removeMissionItem(int paramInt)
  {
    this.missionItemsList.remove(paramInt);
  }

  public void removeMissionItem(MissionItem paramMissionItem)
  {
    this.missionItemsList.remove(paramMissionItem);
  }

  public void setCurrentMissionItem(int paramInt)
  {
    this.currentMissionItem = paramInt;
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    paramParcel.writeInt(this.currentMissionItem);
    ArrayList localArrayList = new ArrayList(this.missionItemsList.size());
    if (!this.missionItemsList.isEmpty())
    {
      Iterator localIterator = this.missionItemsList.iterator();
      while (localIterator.hasNext())
      {
        MissionItem localMissionItem = (MissionItem)localIterator.next();
        localArrayList.add(localMissionItem.getType().storeMissionItem(localMissionItem));
      }
    }
    paramParcel.writeTypedList(localArrayList);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.Mission
 * JD-Core Version:    0.6.2
 */