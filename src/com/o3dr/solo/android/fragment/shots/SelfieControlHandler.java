package com.o3dr.solo.android.fragment.shots;

import android.content.Context;
import android.content.Intent;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Attitude;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.util.MathUtils;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.SelfieState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;
import com.o3dr.solo.android.util.AppPreferences;

public class SelfieControlHandler extends ShotControlHandler
{
  SelfieControlHandler(ShotControlFragment paramShotControlFragment)
  {
    super(paramShotControlFragment);
  }

  private void generateSelfieWaypoints()
  {
    ShotControlFragment localShotControlFragment = getFragment();
    if (localShotControlFragment == null);
    SelfieState localSelfieState;
    Drone localDrone;
    Gps localGps;
    do
    {
      do
      {
        ShotState localShotState;
        do
        {
          ShotManager localShotManager;
          do
          {
            SoloLinkManager localSoloLinkManager;
            do
            {
              return;
              localSoloLinkManager = localShotControlFragment.getSoloLinkManager();
            }
            while (localSoloLinkManager == null);
            localShotManager = localSoloLinkManager.getShotManager();
          }
          while (localShotManager == null);
          localShotState = localShotManager.getCurrentShotState();
        }
        while ((localShotState == null) || (!(localShotState instanceof SelfieState)));
        localSelfieState = (SelfieState)localShotState;
        localDrone = localShotControlFragment.getDrone();
      }
      while (localDrone == null);
      localGps = (Gps)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    }
    while (!localGps.isValid());
    double d1 = ((Altitude)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.ALTITUDE")).getAltitude();
    LatLong localLatLong1 = localGps.getPosition();
    LatLongAlt localLatLongAlt1 = new LatLongAlt(localLatLong1.getLatitude(), localLatLong1.getLongitude(), d1);
    double d2 = 180.0D + ((Attitude)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.ATTITUDE")).getYaw();
    AppPreferences localAppPreferences = localShotControlFragment.appPrefs;
    LatLong localLatLong2 = MathUtils.newCoordFromBearingAndDistance(localLatLongAlt1, d2, localAppPreferences.getSelfieDistanceOut());
    LatLongAlt localLatLongAlt2 = new LatLongAlt(localLatLong2.getLatitude(), localLatLong2.getLongitude(), d1 + localAppPreferences.getSelfieAltitudeUp());
    LatLong localLatLong3 = MathUtils.newCoordFromBearingAndDistance(localLatLongAlt1, d2, -8.0D);
    localSelfieState.setSelfieWaypoints(localLatLongAlt1, localLatLongAlt2, new LatLongAlt(localLatLong3.getLatitude(), localLatLong3.getLongitude(), 1.0D));
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
      switch (i)
      {
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      }
      break;
    case 566338349:
    case -966973459:
    case 1383094019:
    case 441792541:
    case -42951236:
    case -925213424:
    }
    while (true)
    {
      return false;
      if (!str.equals("com.o3dr.services.android.lib.attribute.event.ALTITUDE_UPDATED"))
        break;
      i = 0;
      break;
      if (!str.equals("com.o3dr.services.android.lib.attribute.event.GPS_POSITION"))
        break;
      i = 1;
      break;
      if (!str.equals("com.o3dr.solo.android.action.SELFIE_STARTED"))
        break;
      i = 2;
      break;
      if (!str.equals("com.o3dr.solo.android.action.SELFIE_SETTINGS_UPDATED"))
        break;
      i = 3;
      break;
      if (!str.equals("com.o3dr.solo.android.action.SELFIE_ENDED"))
        break;
      i = 4;
      break;
      if (!str.equals("com.o3dr.solo.android.action.SELFIE_STATUS"))
        break;
      i = 5;
      break;
      SoloLinkManager localSoloLinkManager = localShotControlFragment.getSoloLinkManager();
      if (localSoloLinkManager == null)
        return false;
      ShotManager localShotManager = localSoloLinkManager.getShotManager();
      if (localShotManager == null)
        return false;
      ShotState localShotState = localShotManager.getCurrentShotState();
      if ((localShotState == null) || (!(localShotState instanceof SelfieState)))
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
      LatLongAlt localLatLongAlt1 = arrayOfLatLongAlt[0];
      LatLongAlt localLatLongAlt2 = arrayOfLatLongAlt[1];
      if ((localLatLongAlt1 == null) || (localLatLongAlt2 == null))
        return false;
      localShotControlFragment.updateVehicleProgress(computeVehicleProgress(new LatLongAlt(localGps.getPosition(), localAltitude.getAltitude()), localLatLongAlt1, localLatLongAlt2));
      return true;
      localShotControlFragment.notifyShotStarted(0);
      localShotControlFragment.playSound(2131034129);
      generateSelfieWaypoints();
      return true;
      localShotControlFragment.resetViews();
      localShotControlFragment.notifyShotEnded(0);
      return true;
      localShotControlFragment.showControlView(paramIntent.getIntExtra("extra_selfie_status", -1));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.SelfieControlHandler
 * JD-Core Version:    0.6.2
 */