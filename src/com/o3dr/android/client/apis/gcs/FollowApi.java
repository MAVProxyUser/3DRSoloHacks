package com.o3dr.android.client.apis.gcs;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.gcs.follow.FollowType;
import com.o3dr.services.android.lib.model.action.Action;

public class FollowApi
{
  public static void disableFollowMe(Drone paramDrone)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.DISABLE_FOLLOW_ME"));
  }

  public static void enableFollowMe(Drone paramDrone, FollowType paramFollowType)
  {
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("extra_follow_type", paramFollowType);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.ENABLE_FOLLOW_ME", localBundle));
  }

  public static void updateFollowParams(Drone paramDrone, Bundle paramBundle)
  {
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.UPDATE_FOLLOW_PARAMS", paramBundle));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.gcs.FollowApi
 * JD-Core Version:    0.6.2
 */