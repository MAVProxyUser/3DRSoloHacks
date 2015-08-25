package com.o3dr.services.android.lib.drone.mission;

import android.os.Bundle;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.util.ParcelableUtils;

public enum MissionItemType
{
  private static final String EXTRA_MISSION_ITEM = "extra_mission_item";
  private static final String EXTRA_MISSION_ITEM_TYPE = "extra_mission_item_type";
  private final String label;

  static
  {
    SPLINE_WAYPOINT = new MissionItemType.2("SPLINE_WAYPOINT", 1, "Spline Waypoint");
    TAKEOFF = new MissionItemType.3("TAKEOFF", 2, "Takeoff");
    CHANGE_SPEED = new MissionItemType.4("CHANGE_SPEED", 3, "Change Speed");
    CAMERA_TRIGGER = new MissionItemType.5("CAMERA_TRIGGER", 4, "Camera Trigger");
    EPM_GRIPPER = new MissionItemType.6("EPM_GRIPPER", 5, "EPM Gripper");
    RETURN_TO_LAUNCH = new MissionItemType.7("RETURN_TO_LAUNCH", 6, "Return to Launch");
    LAND = new MissionItemType.8("LAND", 7, "Land");
    CIRCLE = new MissionItemType.9("CIRCLE", 8, "Circle");
    REGION_OF_INTEREST = new MissionItemType.10("REGION_OF_INTEREST", 9, "Region of Interest");
    SURVEY = new MissionItemType.11("SURVEY", 10, "Survey");
    STRUCTURE_SCANNER = new MissionItemType.12("STRUCTURE_SCANNER", 11, "Structure Scanner");
    SET_SERVO = new MissionItemType.13("SET_SERVO", 12, "Set Servo");
    YAW_CONDITION = new MissionItemType.14("YAW_CONDITION", 13, "Set Yaw");
    SET_RELAY = new MissionItemType.15("SET_RELAY", 14, "Set Relay");
    MissionItemType[] arrayOfMissionItemType = new MissionItemType[15];
    arrayOfMissionItemType[0] = WAYPOINT;
    arrayOfMissionItemType[1] = SPLINE_WAYPOINT;
    arrayOfMissionItemType[2] = TAKEOFF;
    arrayOfMissionItemType[3] = CHANGE_SPEED;
    arrayOfMissionItemType[4] = CAMERA_TRIGGER;
    arrayOfMissionItemType[5] = EPM_GRIPPER;
    arrayOfMissionItemType[6] = RETURN_TO_LAUNCH;
    arrayOfMissionItemType[7] = LAND;
    arrayOfMissionItemType[8] = CIRCLE;
    arrayOfMissionItemType[9] = REGION_OF_INTEREST;
    arrayOfMissionItemType[10] = SURVEY;
    arrayOfMissionItemType[11] = STRUCTURE_SCANNER;
    arrayOfMissionItemType[12] = SET_SERVO;
    arrayOfMissionItemType[13] = YAW_CONDITION;
    arrayOfMissionItemType[14] = SET_RELAY;
  }

  private MissionItemType(String paramString)
  {
    this.label = paramString;
  }

  public static <T extends MissionItem> T restoreMissionItemFromBundle(Bundle paramBundle)
  {
    if (paramBundle == null);
    byte[] arrayOfByte;
    MissionItemType localMissionItemType;
    do
    {
      String str;
      do
      {
        return null;
        str = paramBundle.getString("extra_mission_item_type");
        arrayOfByte = paramBundle.getByteArray("extra_mission_item");
      }
      while ((str == null) || (arrayOfByte == null));
      localMissionItemType = valueOf(str);
    }
    while (localMissionItemType == null);
    return (MissionItem)ParcelableUtils.unmarshall(arrayOfByte, localMissionItemType.getMissionItemCreator());
  }

  public String getLabel()
  {
    return this.label;
  }

  protected abstract <T extends MissionItem> Parcelable.Creator<T> getMissionItemCreator();

  public abstract MissionItem getNewItem();

  public final Bundle storeMissionItem(MissionItem paramMissionItem)
  {
    Bundle localBundle = new Bundle(2);
    storeMissionItem(paramMissionItem, localBundle);
    return localBundle;
  }

  public void storeMissionItem(MissionItem paramMissionItem, Bundle paramBundle)
  {
    paramBundle.putString("extra_mission_item_type", name());
    paramBundle.putByteArray("extra_mission_item", ParcelableUtils.marshall(paramMissionItem));
  }

  public String toString()
  {
    return getLabel();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.drone.mission.MissionItemType
 * JD-Core Version:    0.6.2
 */