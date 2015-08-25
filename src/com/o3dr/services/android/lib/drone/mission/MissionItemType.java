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
import com.o3dr.services.android.lib.drone.mission.item.spatial.DoLandStart;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Land;
import com.o3dr.services.android.lib.drone.mission.item.spatial.RegionOfInterest;
import com.o3dr.services.android.lib.drone.mission.item.spatial.SplineWaypoint;
import com.o3dr.services.android.lib.drone.mission.item.spatial.Waypoint;
import com.o3dr.services.android.lib.drone.property.Type;
import com.o3dr.services.android.lib.util.ParcelableUtils;

public enum MissionItemType
{
  private static final String EXTRA_MISSION_ITEM = "extra_mission_item";
  private static final String EXTRA_MISSION_ITEM_TYPE = "extra_mission_item_type";
  private final String label;

  static
  {
    // Byte code:
    //   0: new 36	com/o3dr/services/android/lib/drone/mission/MissionItemType$1
    //   3: dup
    //   4: ldc 37
    //   6: iconst_0
    //   7: ldc 39
    //   9: invokespecial 43	com/o3dr/services/android/lib/drone/mission/MissionItemType$1:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   12: putstatic 45	com/o3dr/services/android/lib/drone/mission/MissionItemType:WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   15: new 47	com/o3dr/services/android/lib/drone/mission/MissionItemType$2
    //   18: dup
    //   19: ldc 48
    //   21: iconst_1
    //   22: ldc 50
    //   24: invokespecial 51	com/o3dr/services/android/lib/drone/mission/MissionItemType$2:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   27: putstatic 53	com/o3dr/services/android/lib/drone/mission/MissionItemType:SPLINE_WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   30: new 55	com/o3dr/services/android/lib/drone/mission/MissionItemType$3
    //   33: dup
    //   34: ldc 56
    //   36: iconst_2
    //   37: ldc 58
    //   39: invokespecial 59	com/o3dr/services/android/lib/drone/mission/MissionItemType$3:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   42: putstatic 61	com/o3dr/services/android/lib/drone/mission/MissionItemType:TAKEOFF	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   45: new 63	com/o3dr/services/android/lib/drone/mission/MissionItemType$4
    //   48: dup
    //   49: ldc 64
    //   51: iconst_3
    //   52: ldc 66
    //   54: invokespecial 67	com/o3dr/services/android/lib/drone/mission/MissionItemType$4:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   57: putstatic 69	com/o3dr/services/android/lib/drone/mission/MissionItemType:CHANGE_SPEED	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   60: new 71	com/o3dr/services/android/lib/drone/mission/MissionItemType$5
    //   63: dup
    //   64: ldc 72
    //   66: iconst_4
    //   67: ldc 74
    //   69: invokespecial 75	com/o3dr/services/android/lib/drone/mission/MissionItemType$5:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   72: putstatic 77	com/o3dr/services/android/lib/drone/mission/MissionItemType:CAMERA_TRIGGER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   75: new 79	com/o3dr/services/android/lib/drone/mission/MissionItemType$6
    //   78: dup
    //   79: ldc 80
    //   81: iconst_5
    //   82: ldc 82
    //   84: invokespecial 83	com/o3dr/services/android/lib/drone/mission/MissionItemType$6:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   87: putstatic 85	com/o3dr/services/android/lib/drone/mission/MissionItemType:EPM_GRIPPER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   90: new 87	com/o3dr/services/android/lib/drone/mission/MissionItemType$7
    //   93: dup
    //   94: ldc 88
    //   96: bipush 6
    //   98: ldc 90
    //   100: invokespecial 91	com/o3dr/services/android/lib/drone/mission/MissionItemType$7:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   103: putstatic 93	com/o3dr/services/android/lib/drone/mission/MissionItemType:RETURN_TO_LAUNCH	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   106: new 95	com/o3dr/services/android/lib/drone/mission/MissionItemType$8
    //   109: dup
    //   110: ldc 96
    //   112: bipush 7
    //   114: ldc 98
    //   116: invokespecial 99	com/o3dr/services/android/lib/drone/mission/MissionItemType$8:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   119: putstatic 101	com/o3dr/services/android/lib/drone/mission/MissionItemType:LAND	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   122: new 103	com/o3dr/services/android/lib/drone/mission/MissionItemType$9
    //   125: dup
    //   126: ldc 104
    //   128: bipush 8
    //   130: ldc 106
    //   132: invokespecial 107	com/o3dr/services/android/lib/drone/mission/MissionItemType$9:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   135: putstatic 109	com/o3dr/services/android/lib/drone/mission/MissionItemType:CIRCLE	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   138: new 111	com/o3dr/services/android/lib/drone/mission/MissionItemType$10
    //   141: dup
    //   142: ldc 112
    //   144: bipush 9
    //   146: ldc 114
    //   148: invokespecial 115	com/o3dr/services/android/lib/drone/mission/MissionItemType$10:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   151: putstatic 117	com/o3dr/services/android/lib/drone/mission/MissionItemType:REGION_OF_INTEREST	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   154: new 119	com/o3dr/services/android/lib/drone/mission/MissionItemType$11
    //   157: dup
    //   158: ldc 120
    //   160: bipush 10
    //   162: ldc 122
    //   164: invokespecial 123	com/o3dr/services/android/lib/drone/mission/MissionItemType$11:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   167: putstatic 125	com/o3dr/services/android/lib/drone/mission/MissionItemType:SURVEY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   170: new 127	com/o3dr/services/android/lib/drone/mission/MissionItemType$12
    //   173: dup
    //   174: ldc 128
    //   176: bipush 11
    //   178: ldc 130
    //   180: invokespecial 131	com/o3dr/services/android/lib/drone/mission/MissionItemType$12:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   183: putstatic 133	com/o3dr/services/android/lib/drone/mission/MissionItemType:STRUCTURE_SCANNER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   186: new 135	com/o3dr/services/android/lib/drone/mission/MissionItemType$13
    //   189: dup
    //   190: ldc 136
    //   192: bipush 12
    //   194: ldc 138
    //   196: invokespecial 139	com/o3dr/services/android/lib/drone/mission/MissionItemType$13:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   199: putstatic 141	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_SERVO	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   202: new 143	com/o3dr/services/android/lib/drone/mission/MissionItemType$14
    //   205: dup
    //   206: ldc 144
    //   208: bipush 13
    //   210: ldc 146
    //   212: invokespecial 147	com/o3dr/services/android/lib/drone/mission/MissionItemType$14:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   215: putstatic 149	com/o3dr/services/android/lib/drone/mission/MissionItemType:YAW_CONDITION	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   218: new 151	com/o3dr/services/android/lib/drone/mission/MissionItemType$15
    //   221: dup
    //   222: ldc 152
    //   224: bipush 14
    //   226: ldc 154
    //   228: invokespecial 155	com/o3dr/services/android/lib/drone/mission/MissionItemType$15:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   231: putstatic 157	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_RELAY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   234: new 159	com/o3dr/services/android/lib/drone/mission/MissionItemType$16
    //   237: dup
    //   238: ldc 160
    //   240: bipush 15
    //   242: ldc 162
    //   244: invokespecial 163	com/o3dr/services/android/lib/drone/mission/MissionItemType$16:<init>	(Ljava/lang/String;ILjava/lang/String;)V
    //   247: putstatic 165	com/o3dr/services/android/lib/drone/mission/MissionItemType:DO_LAND_START	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   250: bipush 16
    //   252: anewarray 2	com/o3dr/services/android/lib/drone/mission/MissionItemType
    //   255: astore_0
    //   256: aload_0
    //   257: iconst_0
    //   258: getstatic 45	com/o3dr/services/android/lib/drone/mission/MissionItemType:WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   261: aastore
    //   262: aload_0
    //   263: iconst_1
    //   264: getstatic 53	com/o3dr/services/android/lib/drone/mission/MissionItemType:SPLINE_WAYPOINT	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   267: aastore
    //   268: aload_0
    //   269: iconst_2
    //   270: getstatic 61	com/o3dr/services/android/lib/drone/mission/MissionItemType:TAKEOFF	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   273: aastore
    //   274: aload_0
    //   275: iconst_3
    //   276: getstatic 69	com/o3dr/services/android/lib/drone/mission/MissionItemType:CHANGE_SPEED	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   279: aastore
    //   280: aload_0
    //   281: iconst_4
    //   282: getstatic 77	com/o3dr/services/android/lib/drone/mission/MissionItemType:CAMERA_TRIGGER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   285: aastore
    //   286: aload_0
    //   287: iconst_5
    //   288: getstatic 85	com/o3dr/services/android/lib/drone/mission/MissionItemType:EPM_GRIPPER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   291: aastore
    //   292: aload_0
    //   293: bipush 6
    //   295: getstatic 93	com/o3dr/services/android/lib/drone/mission/MissionItemType:RETURN_TO_LAUNCH	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   298: aastore
    //   299: aload_0
    //   300: bipush 7
    //   302: getstatic 101	com/o3dr/services/android/lib/drone/mission/MissionItemType:LAND	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   305: aastore
    //   306: aload_0
    //   307: bipush 8
    //   309: getstatic 109	com/o3dr/services/android/lib/drone/mission/MissionItemType:CIRCLE	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   312: aastore
    //   313: aload_0
    //   314: bipush 9
    //   316: getstatic 117	com/o3dr/services/android/lib/drone/mission/MissionItemType:REGION_OF_INTEREST	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   319: aastore
    //   320: aload_0
    //   321: bipush 10
    //   323: getstatic 125	com/o3dr/services/android/lib/drone/mission/MissionItemType:SURVEY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   326: aastore
    //   327: aload_0
    //   328: bipush 11
    //   330: getstatic 133	com/o3dr/services/android/lib/drone/mission/MissionItemType:STRUCTURE_SCANNER	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   333: aastore
    //   334: aload_0
    //   335: bipush 12
    //   337: getstatic 141	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_SERVO	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   340: aastore
    //   341: aload_0
    //   342: bipush 13
    //   344: getstatic 149	com/o3dr/services/android/lib/drone/mission/MissionItemType:YAW_CONDITION	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   347: aastore
    //   348: aload_0
    //   349: bipush 14
    //   351: getstatic 157	com/o3dr/services/android/lib/drone/mission/MissionItemType:SET_RELAY	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   354: aastore
    //   355: aload_0
    //   356: bipush 15
    //   358: getstatic 165	com/o3dr/services/android/lib/drone/mission/MissionItemType:DO_LAND_START	Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   361: aastore
    //   362: aload_0
    //   363: putstatic 167	com/o3dr/services/android/lib/drone/mission/MissionItemType:$VALUES	[Lcom/o3dr/services/android/lib/drone/mission/MissionItemType;
    //   366: return
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

  public boolean isTypeSupported(Type paramType)
  {
    return paramType != null;
  }

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