package com.o3dr.android.client.apis.drone;

import android.os.Bundle;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.Drone.AttributeRetrievedListener;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.model.action.Action;

public class GuidedApi
{
  public static void pauseAtCurrentLocation(Drone paramDrone)
  {
    paramDrone.getAttributeAsync("com.o3dr.services.android.lib.attribute.GPS", new Drone.AttributeRetrievedListener()
    {
      public void onRetrievalSucceed(Gps paramAnonymousGps)
      {
        GuidedApi.sendGuidedPoint(this.val$drone, paramAnonymousGps.getPosition(), true);
      }
    });
  }

  public static void sendGuidedPoint(Drone paramDrone, LatLong paramLatLong, boolean paramBoolean)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("extra_force_guided_point", paramBoolean);
    localBundle.putParcelable("extra_guided_point", paramLatLong);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SEND_GUIDED_POINT", localBundle));
  }

  public static void setGuidedAltitude(Drone paramDrone, double paramDouble)
  {
    Bundle localBundle = new Bundle();
    localBundle.putDouble("extra_altitude", paramDouble);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.SET_GUIDED_ALTITUDE", localBundle));
  }

  public static void takeoff(Drone paramDrone, double paramDouble)
  {
    Bundle localBundle = new Bundle();
    localBundle.putDouble("extra_altitude", paramDouble);
    paramDrone.performAsyncAction(new Action("com.o3dr.services.android.action.DO_GUIDED_TAKEOFF", localBundle));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.apis.drone.GuidedApi
 * JD-Core Version:    0.6.2
 */