package com.o3dr.solo.android.fragment.shots;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.util.MathUtils;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.OrbitState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;

public class OrbitControlHandler extends ShotControlHandler
{
  private static final String TAG = OrbitControlHandler.class.getSimpleName();

  OrbitControlHandler(ShotControlFragment paramShotControlFragment)
  {
    super(paramShotControlFragment);
  }

  boolean onReceive(Context paramContext, Intent paramIntent)
  {
    ShotControlFragment localShotControlFragment = getFragment();
    if (localShotControlFragment == null)
      return false;
    int i = -1;
    String str = paramIntent.getAction();
    int j = -1;
    switch (str.hashCode())
    {
    default:
    case -260677709:
    case -966973459:
    case 1654160992:
    case 1841369708:
    case -286121488:
    }
    while (true)
      switch (j)
      {
      default:
        return false;
        if (str.equals("com.o3dr.solo.android.action.ORBIT_STARTED"))
        {
          j = 0;
          continue;
          if (str.equals("com.o3dr.services.android.lib.attribute.event.GPS_POSITION"))
          {
            j = 1;
            continue;
            if (str.equals("com.o3dr.solo.android.action.ORBIT_STATUS"))
            {
              j = 2;
              continue;
              if (str.equals("com.o3dr.solo.android.action.ORBIT_ENDED"))
              {
                j = 3;
                continue;
                if (str.equals("com.o3dr.solo.android.action_ORBIT_ROI_SET"))
                  j = 4;
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
    i = 2131034113;
    localShotControlFragment.notifyShotStarted(1);
    SoloLinkManager localSoloLinkManager = localShotControlFragment.getSoloLinkManager();
    if (localSoloLinkManager == null)
      return false;
    ShotManager localShotManager = localSoloLinkManager.getShotManager();
    if (localShotManager == null)
      return false;
    ShotState localShotState = localShotManager.getCurrentShotState();
    if (!(localShotState instanceof OrbitState))
      return false;
    Drone localDrone = localShotControlFragment.getDrone();
    if ((localDrone == null) || (!localShotControlFragment.isDroneConnected()))
      return false;
    Gps localGps = (Gps)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    if (!localGps.isValid())
      return false;
    if (localShotState.isActive())
    {
      localShotControlFragment.updateOrbitRadius(MathUtils.getDistance2D(localShotState.getWaypoints()[0], localGps.getPosition()));
      return true;
    }
    if (localShotState.isStarted())
    {
      LengthUnitProvider localLengthUnitProvider = localShotControlFragment.getLengthUnitProvider();
      if (localLengthUnitProvider == null)
        return false;
      localShotControlFragment.displayInstructions(localShotControlFragment.getString(2131099975, new Object[] { localLengthUnitProvider.boxBaseValueToTarget(MathUtils.getDistance2D(OrbitState.estimateOrbitRoi(localDrone), localGps.getPosition())).toString() }), null, null, i);
      return true;
    }
    return false;
    localShotControlFragment.showOrbitControl(paramIntent.getIntExtra("extra_orbit_status", 0));
    return true;
    localShotControlFragment.resetViews();
    localShotControlFragment.notifyShotEnded(1);
    return true;
    Log.d(TAG, "Orbit roi set.");
    localShotControlFragment.displayInstructions(2131099974, 0, 0, 2131034129);
    localShotControlFragment.notifyShotSetupCompleted(1);
    return true;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.OrbitControlHandler
 * JD-Core Version:    0.6.2
 */