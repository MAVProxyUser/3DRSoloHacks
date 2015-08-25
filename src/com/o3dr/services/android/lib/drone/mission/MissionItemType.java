package com.o3dr.services.android.lib.drone.mission;

import android.os.Bundle;
import android.os.Parcelable.Creator;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.command.CameraTrigger;
import com.o3dr.services.android.lib.drone.mission.item.command.ChangeSpeed;
import com.o3dr.services.android.lib.drone.mission.item.command.EpmGripper;
import com.o3dr.services.android.lib.drone.mission.item.command.ReturnToLaunch;
import com.o3dr.services.android.lib.drone.mission.item.command.SetRelay;
import com.o3dr.services.android.lib.drone.mission.item.command.SetServo;
import com.o3dr.services.android.lib.drone.mission.item.command.Takeoff;
import com.o3dr.services.android.lib.drone.mission.item.command.YawCondition;
import com.o3dr.services.android.lib.drone.mission.item.complex.StructureScanner;
import com.o3dr.services.android.lib.drone.mission.item.complex.Survey;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Circle;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Land;
import com.o3dr.services.android.lib.drone.mission.item.spatial.RegionOfInterest;
import com.o3dr.services.android.lib.drone.mission.item.spatial.SplineWaypoint;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Waypoint;
import com.o3dr.services.android.lib.util.ParcelableUtils;

public enum MissionItemType
{
  private static final String EXTRA_MISSION_ITEM = "extra_mission_item";
  private static final String EXTRA_MISSION_ITEM_TYPE = "extra_mission_item_type";
  private final String label;

  static
  {
    // Byte code:
    //   0: new 35	com/o3dr/services/android/lib/drone/mission/MissionItemType$1
    //   3: dup
    //   4: ldc 36
    //   6: iconst_0
    //   7: ldc 38
    //   9: invokespecial 42	com/o3dr/services/android/lib/drone/mission/MissionItemType$1:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   12: putstatic 44	com/o3dr/services/android/lib/drone/mission/MissionItemType:WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   15: new 46	com/o3dr/services/android/lib/drone/mission/MissionItemType$2
    //   18: dup
    //   19: ldc 47
    //   21: iconst_1
    //   22: ldc 49
    //   24: invokespecial 50	com/o3dr/services/android/lib/drone/mission/MissionItemType$2:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   27: putstatic 52	com/o3dr/services/android/lib/drone/mission/MissionItemType:SPLINE_WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   30: new 54	com/o3dr/services/android/lib/drone/mission/MissionItemType$3
    //   33: dup
    //   34: ldc 55
    //   36: iconst_2
    //   37: ldc 57
    //   39: invokespecial 58	com/o3dr/services/android/lib/drone/mission/MissionItemType$3:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   42: putstatic 60	com/o3dr/services/android/lib/drone/mission/MissionItemType:TAKEOFF	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   45: new 62	com/o3dr/services/android/lib/drone/mission/MissionItemType$4
    //   48: dup
    //   49: ldc 63
    //   51: iconst_3
    //   52: ldc 65
    //   54: invokespecial 66	com/o3dr/services/android/lib/drone/mission/MissionItemType$4:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   57: putstatic 68	com/o3dr/services/android/lib/drone/mission/MissionItemType:CHANGE_SPEED	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   60: new 70	com/o3dr/services/android/lib/drone/mission/MissionItemType$5
    //   63: dup
    //   64: ldc 71
    //   66: iconst_4
    //   67: ldc 73
    //   69: invokespecial 74	com/o3dr/services/android/lib/drone/mission/MissionItemType$5:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   72: putstatic 76	com/o3dr/services/android/lib/drone/mission/MissionItemType:CAMERA_TRIGGER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   75: new 78	com/o3dr/services/android/lib/drone/mission/MissionItemType$6
    //   78: dup
    //   79: ldc 79
    //   81: iconst_5
    //   82: ldc 81
    //   84: invokespecial 82	com/o3dr/services/android/lib/drone/mission/MissionItemType$6:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   87: putstatic 84	com/o3dr/services/android/lib/drone/mission/MissionItemType:EPM_GRIPPER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   90: new 86	com/o3dr/services/android/lib/drone/mission/MissionItemType$7
    //   93: dup
    //   94: ldc 87
    //   96: bipush 6
    //   98: ldc 89
    //   100: invokespecial 90	com/o3dr/services/android/lib/drone/mission/MissionItemType$7:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   103: putstatic 92	com/o3dr/services/android/lib/drone/mission/MissionItemType:RETURN_TO_LAUNCH	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   106: new 94	com/o3dr/services/android/lib/drone/mission/MissionItemType$8
    //   109: dup
    //   110: ldc 95
    //   112: bipush 7
    //   114: ldc 97
    //   116: invokespecial 98	com/o3dr/services/android/lib/drone/mission/MissionItemType$8:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   119: putstatic 100	com/o3dr/services/android/lib/drone/mission/MissionItemType:LAND	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   122: new 102	com/o3dr/services/android/lib/drone/mission/MissionItemType$9
    //   125: dup
    //   126: ldc 103
    //   128: bipush 8
    //   130: ldc 105
    //   132: invokespecial 106	com/o3dr/services/android/lib/drone/mission/MissionItemType$9:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   135: putstatic 108	com/o3dr/services/android/lib/drone/mission/MissionItemType:CIRCLE	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   138: new 110	com/o3dr/services/android/lib/drone/mission/MissionItemType$10
    //   141: dup
    //   142: ldc 111
    //   144: bipush 9
    //   146: ldc 113
    //   148: invokespecial 114	com/o3dr/services/android/lib/drone/mission/MissionItemType$10:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   151: putstatic 116	com/o3dr/services/android/lib/drone/mission/MissionItemType:REGION_OF_INTEREST	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   154: new 118	com/o3dr/services/android/lib/drone/mission/MissionItemType$11
    //   157: dup
    //   158: ldc 119
    //   160: bipush 10
    //   162: ldc 121
    //   164: invokespecial 122	com/o3dr/services/android/lib/drone/mission/MissionItemType$11:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   167: putstatic 124	com/o3dr/services/android/lib/drone/mission/MissionItemType:SURVEY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   170: new 126	com/o3dr/services/android/lib/drone/mission/MissionItemType$12
    //   173: dup
    //   174: ldc 127
    //   176: bipush 11
    //   178: ldc 129
    //   180: invokespecial 130	com/o3dr/services/android/lib/drone/mission/MissionItemType$12:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   183: putstatic 132	com/o3dr/services/android/lib/drone/mission/MissionItemType:STRUCTURE_SCANNER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   186: new 134	com/o3dr/services/android/lib/drone/mission/MissionItemType$13
    //   189: dup
    //   190: ldc 135
    //   192: bipush 12
    //   194: ldc 137
    //   196: invokespecial 138	com/o3dr/services/android/lib/drone/mission/MissionItemType$13:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   199: putstatic 140	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_SERVO	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   202: new 142	com/o3dr/services/android/lib/drone/mission/MissionItemType$14
    //   205: dup
    //   206: ldc 143
    //   208: bipush 13
    //   210: ldc 145
    //   212: invokespecial 146	com/o3dr/services/android/lib/drone/mission/MissionItemType$14:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   215: putstatic 148	com/o3dr/services/android/lib/drone/mission/MissionItemType:YAW_CONDITION	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   218: new 150	com/o3dr/services/android/lib/drone/mission/MissionItemType$15
    //   221: dup
    //   222: ldc 151
    //   224: bipush 14
    //   226: ldc 153
    //   228: invokespecial 154	com/o3dr/services/android/lib/drone/mission/MissionItemType$15:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   231: putstatic 156	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_RELAY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   234: bipush 15
    //   236: anewarray 2	com/o3dr/services/android/lib/drone/mission/MissionItemType
    //   239: astore_0
    //   240: aload_0
    //   241: iconst_0
    //   242: getstatic 44	com/o3dr/services/android/lib/drone/mission/MissionItemType:WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   245: aastore
    //   246: aload_0
    //   247: iconst_1
    //   248: getstatic 52	com/o3dr/services/android/lib/drone/mission/MissionItemType:SPLINE_WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   251: aastore
    //   252: aload_0
    //   253: iconst_2
    //   254: getstatic 60	com/o3dr/services/android/lib/drone/mission/MissionItemType:TAKEOFF	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   257: aastore
    //   258: aload_0
    //   259: iconst_3
    //   260: getstatic 68	com/o3dr/services/android/lib/drone/mission/MissionItemType:CHANGE_SPEED	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   263: aastore
    //   264: aload_0
    //   265: iconst_4
    //   266: getstatic 76	com/o3dr/services/android/lib/drone/mission/MissionItemType:CAMERA_TRIGGER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   269: aastore
    //   270: aload_0
    //   271: iconst_5
    //   272: getstatic 84	com/o3dr/services/android/lib/drone/mission/MissionItemType:EPM_GRIPPER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   275: aastore
    //   276: aload_0
    //   277: bipush 6
    //   279: getstatic 92	com/o3dr/services/android/lib/drone/mission/MissionItemType:RETURN_TO_LAUNCH	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   282: aastore
    //   283: aload_0
    //   284: bipush 7
    //   286: getstatic 100	com/o3dr/services/android/lib/drone/mission/MissionItemType:LAND	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   289: aastore
    //   290: aload_0
    //   291: bipush 8
    //   293: getstatic 108	com/o3dr/services/android/lib/drone/mission/MissionItemType:CIRCLE	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   296: aastore
    //   297: aload_0
    //   298: bipush 9
    //   300: getstatic 116	com/o3dr/services/android/lib/drone/mission/MissionItemType:REGION_OF_INTEREST	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   303: aastore
    //   304: aload_0
    //   305: bipush 10
    //   307: getstatic 124	com/o3dr/services/android/lib/drone/mission/MissionItemType:SURVEY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   310: aastore
    //   311: aload_0
    //   312: bipush 11
    //   314: getstatic 132	com/o3dr/services/android/lib/drone/mission/MissionItemType:STRUCTURE_SCANNER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   317: aastore
    //   318: aload_0
    //   319: bipush 12
    //   321: getstatic 140	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_SERVO	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   324: aastore
    //   325: aload_0
    //   326: bipush 13
    //   328: getstatic 148	com/o3dr/services/android/lib/drone/mission/MissionItemType:YAW_CONDITION	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   331: aastore
    //   332: aload_0
    //   333: bipush 14
    //   335: getstatic 156	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_RELAY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   338: aastore
    //   339: aload_0
    //   340: putstatic 158	com/o3dr/services/android/lib/drone/mission/MissionItemType:$VALUES	[Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   343: return
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