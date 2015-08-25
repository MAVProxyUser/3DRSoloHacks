package com.o3dr.solo.android.fragment.shots;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.CableCamState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;

public class CableCamControlHandler extends ShotControlHandler
{
  private static final long TRANSITION_TIMEOUT = 1500L;
  final Runnable showSecondPositionInstructions = new Runnable()
  {
    public void run()
    {
      ShotControlFragment localShotControlFragment = CableCamControlHandler.this.getFragment();
      if (localShotControlFragment != null)
        localShotControlFragment.displayInstructions(2131099712, 2131099711, 2131427385, 2131034113);
    }
  };

  CableCamControlHandler(ShotControlFragment paramShotControlFragment)
  {
    super(paramShotControlFragment);
  }

  boolean onReceive(Context paramContext, Intent paramIntent)
  {
    ShotControlFragment localShotControlFragment = getFragment();
    if (localShotControlFragment == null)
      return false;
    String str = paramIntent.getAction();
    int i = -1;
    switch (str.hashCode())
    {
    default:
    case 566338349:
    case -966973459:
    case -1578907242:
    case -1002472721:
    case -189477859:
    case 1781897487:
    }
    while (true)
      switch (i)
      {
      default:
        return false;
        if (str.equals("com.o3dr.services.android.lib.attribute.event.ALTITUDE_UPDATED"))
        {
          i = 0;
          continue;
          if (str.equals("com.o3dr.services.android.lib.attribute.event.GPS_POSITION"))
          {
            i = 1;
            continue;
            if (str.equals("com.o3dr.solo.android.action.CABLE_CAM_STARTED"))
            {
              i = 2;
              continue;
              if (str.equals("com.o3dr.solo.android.action.CABLE_CAM_WAYPOINT_SET"))
              {
                i = 3;
                continue;
                if (str.equals("com.o3dr.solo.android.action.CABLE_CAM_STATUS"))
                {
                  i = 4;
                  continue;
                  if (str.equals("com.o3dr.solo.android.action.CABLE_CAM_ENDED"))
                    i = 5;
                }
              }
            }
          }
        }
        break;
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      }
    SoloLinkManager localSoloLinkManager = localShotControlFragment.getSoloLinkManager();
    if (localSoloLinkManager == null)
      return false;
    ShotState localShotState = localSoloLinkManager.getShotManager().getCurrentShotState();
    if (!(localShotState instanceof CableCamState))
      return false;
    if (!localShotState.isActive())
      return false;
    Drone localDrone = localShotControlFragment.getDrone();
    if ((localDrone == null) || (!localShotControlFragment.isDroneConnected()))
      return false;
    Gps localGps = (Gps)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    Altitude localAltitude = (Altitude)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.ALTITUDE");
    if (!localGps.isValid())
      return false;
    LatLongAlt[] arrayOfLatLongAlt = localShotState.getWaypoints();
    LatLongAlt localLatLongAlt1 = arrayOfLatLongAlt[1];
    LatLongAlt localLatLongAlt2 = arrayOfLatLongAlt[0];
    if ((localLatLongAlt1 == null) || (localLatLongAlt2 == null))
      return false;
    localShotControlFragment.updateVehicleProgress(computeVehicleProgress(new LatLongAlt(localGps.getPosition(), localAltitude.getAltitude()), localLatLongAlt1, localLatLongAlt2));
    return true;
    localShotControlFragment.displayInstructions(2131099722, 2131099710, 2131427385, 2131034113);
    localShotControlFragment.notifyShotStarted(2);
    return true;
    switch (paramIntent.getIntExtra("extra_cable_cam_waypoint_index", -1))
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return true;
      localShotControlFragment.displayInstructions(2131099709, 0, 0, 2131034129);
      localShotControlFragment.handler.postDelayed(this.showSecondPositionInstructions, 1500L);
      localShotControlFragment.notifyShotSetupCompleted(2);
      continue;
      localShotControlFragment.handler.removeCallbacks(this.showSecondPositionInstructions);
      localShotControlFragment.displayInstructions(2131099713, 0, 0, 2131034129);
      localShotControlFragment.notifyShotSetupCompleted(2);
      Toast.makeText(paramContext, 2131099835, 1).show();
    }
    localShotControlFragment.showControlView(paramIntent.getIntExtra("extra_cable_cam_status", -1));
    return true;
    localShotControlFragment.resetViews();
    localShotControlFragment.notifyShotEnded(2);
    return true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.CableCamControlHandler
 * JD-Core Version:    0.6.2
 */