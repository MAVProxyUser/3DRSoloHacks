package com.o3dr.solo.android.fragment.shots;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v4.content.LocalBroadcastManager;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.util.MathUtils;
import com.o3dr.solo.android.fragment.shots.follow.FusedLocation;
import com.o3dr.solo.android.fragment.shots.follow.FusedLocation.LocationReceiver;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.FollowShotState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;
import java.util.concurrent.atomic.AtomicBoolean;

public class FollowControlHandler extends ShotControlHandler
  implements FusedLocation.LocationReceiver
{
  public static final String ACTION_FOLLOW_LOCATION_ACCURACY = "com.o3dr.solo.android.action.FOLLOW_LOCATION_ACCURACY";
  public static final String ACTION_FOLLOW_LOCATION_UNAVAILABLE = "com.o3dr.solo.android.action.FOLLOW_LOCATION_UNAVAILABLE";
  public static final String EXTRA_IS_LOCATION_ACCURATE = "extra_is_location_accurate";
  private static final String TAG = FollowControlHandler.class.getSimpleName();
  private FollowShotState followState;
  private final FusedLocation fusedLocation;
  private final LocalBroadcastManager lbm;
  private final AtomicBoolean reportLocation = new AtomicBoolean(false);

  FollowControlHandler(ShotControlFragment paramShotControlFragment)
  {
    super(paramShotControlFragment);
    Context localContext = paramShotControlFragment.getContext();
    this.lbm = LocalBroadcastManager.getInstance(localContext);
    this.fusedLocation = new FusedLocation(localContext, paramShotControlFragment.handler);
    this.fusedLocation.setLocationReceiver(this);
  }

  private void disableLocationReporting()
  {
    this.reportLocation.set(false);
  }

  public void enableLocationReporting()
  {
    this.reportLocation.set(true);
  }

  public void onLocationUnavailable()
  {
    ShotControlFragment localShotControlFragment = getFragment();
    if (localShotControlFragment == null);
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
    while (localShotManager.getCurrentShot() != 5);
    localShotManager.setCurrentShot(-1);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.FOLLOW_LOCATION_UNAVAILABLE"));
  }

  public void onLocationUpdate(Location paramLocation, boolean paramBoolean)
  {
    if ((this.followState != null) && (this.reportLocation.get()))
    {
      if (paramBoolean)
        this.followState.setFollowTarget(new LatLongAlt(paramLocation.getLatitude(), paramLocation.getLongitude(), paramLocation.getAltitude()));
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.FOLLOW_LOCATION_ACCURACY").putExtra("extra_is_location_accurate", paramBoolean));
    }
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
    case -966973459:
    case -818937780:
    case 2051794599:
    case 191628613:
    case 1428056510:
    }
    while (true)
      switch (i)
      {
      default:
        return false;
        if (str.equals("com.o3dr.services.android.lib.attribute.event.GPS_POSITION"))
        {
          i = 0;
          continue;
          if (str.equals("com.o3dr.solo.android.action.FOLLOW_STARTED"))
          {
            i = 1;
            continue;
            if (str.equals("com.o3dr.solo.android.action.FOLLOW_STATUS"))
            {
              i = 2;
              continue;
              if (str.equals("com.o3dr.solo.android.action.FOLLOW_ENDED"))
              {
                i = 3;
                continue;
                if (str.equals("com.o3dr.solo.android.action.FOLLOW_LOOK_AT"))
                  i = 4;
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
      }
    SoloLinkManager localSoloLinkManager3 = localShotControlFragment.getSoloLinkManager();
    if (localSoloLinkManager3 == null)
      return false;
    ShotState localShotState2 = localSoloLinkManager3.getShotManager().getCurrentShotState();
    if ((!(localShotState2 instanceof FollowShotState)) || (!localShotState2.isActive()))
      return false;
    FollowShotState localFollowShotState = (FollowShotState)localShotState2;
    if (localFollowShotState.isLookAtEnabled())
      return true;
    Drone localDrone = localShotControlFragment.getDrone();
    if ((localDrone == null) || (!localShotControlFragment.isDroneConnected()))
      return false;
    Gps localGps = (Gps)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    if (!localGps.isValid())
      return false;
    LatLongAlt localLatLongAlt = localFollowShotState.getWaypoints()[0];
    if (localLatLongAlt == null)
      return false;
    localShotControlFragment.updateOrbitRadius(MathUtils.getDistance2D(localLatLongAlt, localGps.getPosition()));
    return true;
    SoloLinkManager localSoloLinkManager2 = localShotControlFragment.getSoloLinkManager();
    if (localSoloLinkManager2 == null)
      return false;
    ShotManager localShotManager = localSoloLinkManager2.getShotManager();
    if (localShotManager == null)
      return false;
    this.followState = ((FollowShotState)localShotManager.getCurrentShotState());
    localShotControlFragment.displayInstructions(2131099825, 0, 0, 2131034113);
    this.fusedLocation.enableLocationUpdates();
    localShotControlFragment.notifyShotStarted(5);
    return true;
    localShotControlFragment.showOrbitControl(paramIntent.getIntExtra("extra_orbit_status", 0));
    return true;
    localShotControlFragment.resetViews();
    disableLocationReporting();
    this.fusedLocation.disableLocationUpdates();
    localShotControlFragment.notifyShotEnded(5);
    this.followState = null;
    return true;
    if (paramIntent.getBooleanExtra("extra_look_at_enabled", false))
    {
      localShotControlFragment.notifyShotPaused(5);
      localShotControlFragment.displayInstructions(2131099824, 0, 0, 2131034113);
    }
    while (true)
    {
      return true;
      localShotControlFragment.notifyShotResumed(5);
      SoloLinkManager localSoloLinkManager1 = localShotControlFragment.getSoloLinkManager();
      if (localSoloLinkManager1 == null)
        return false;
      ShotState localShotState1 = localSoloLinkManager1.getShotManager().getCurrentShotState();
      if ((!(localShotState1 instanceof FollowShotState)) || (!localShotState1.isActive()))
        return false;
      localShotControlFragment.showOrbitControl(localShotState1.getState());
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.FollowControlHandler
 * JD-Core Version:    0.6.2
 */