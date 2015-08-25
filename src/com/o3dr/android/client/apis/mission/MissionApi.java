package com.o3dr.android.client.apis.mission;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.mission.Mission;
import com.o3dr.services.android.lib.drone.mission.MissionItemType;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.ComplexItem;
import com.o3dr.services.android.lib.model.action.Action;

public class MissionApi
{
  private static Action buildComplexMissionItem(Drone paramDrone, Bundle paramBundle)
  {
    Action localAction = new Action("com.o3dr.services.android.action.BUILD_COMPLEX_MISSION_ITEM", paramBundle);
    if (paramDrone.performAction(localAction))
      return localAction;
    return null;
  }

  public static <T extends MissionItem> T buildMissionItem(Drone paramDrone, MissionItem.ComplexItem<T> paramComplexItem)
  {
    MissionItem localMissionItem = (MissionItem)paramComplexItem;
    Bundle localBundle = localMissionItem.getType().storeMissionItem(localMissionItem);
    if (localBundle == null)
      return null;
    Action localAction = buildComplexMissionItem(paramDrone, localBundle);
    if (localAction != null)
    {
      paramComplexItem.copy(MissionItemType.restoreMissionItemFromBundle(localAction.getData()));
      return (MissionItem)paramComplexItem;
    }
    return null;
  }

  public static void generateDronie(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.GENERATE_DRONIE"));
  }

  public static void loadWaypoints(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.LOAD_WAYPOINTS"));
  }

  public static void setMission(Drone paramDrone, Mission paramMission, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("extra_mission", paramMission);
    localBundle.putBoolean("extra_push_to_drone", paramBoolean);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SET_MISSION", localBundle));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.mission.MissionApi
 * JD-Core Version:    0.6.2
 */