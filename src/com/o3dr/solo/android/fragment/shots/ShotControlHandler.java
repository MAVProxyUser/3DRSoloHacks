package com.o3dr.solo.android.fragment.shots;

import android.content.Context;
import android.content.Intent;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.util.MathUtils;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;
import java.lang.ref.WeakReference;

public abstract class ShotControlHandler
{
  private WeakReference<ShotControlFragment> fragmentRef;

  ShotControlHandler(ShotControlFragment paramShotControlFragment)
  {
    this.fragmentRef = new WeakReference(paramShotControlFragment);
  }

  protected int computeVehicleProgress(LatLongAlt paramLatLongAlt1, LatLongAlt paramLatLongAlt2, LatLongAlt paramLatLongAlt3)
  {
    double d1 = MathUtils.getDistance3D(paramLatLongAlt2, paramLatLongAlt3);
    double d2 = MathUtils.getDistance3D(paramLatLongAlt2, paramLatLongAlt1);
    double d3 = MathUtils.getDistance3D(paramLatLongAlt1, paramLatLongAlt3);
    if (((d2 <= d1) && (d3 <= d1)) || (d2 == d3))
      return (int)(100.0D * d2 / d1);
    if (d2 < d3)
      return 0;
    return 100;
  }

  protected ShotState getCurrentShotState()
  {
    ShotControlFragment localShotControlFragment = getFragment();
    if (localShotControlFragment == null);
    SoloLinkManager localSoloLinkManager;
    do
    {
      return null;
      localSoloLinkManager = localShotControlFragment.getSoloLinkManager();
    }
    while (localSoloLinkManager == null);
    return localSoloLinkManager.getShotManager().getCurrentShotState();
  }

  protected ShotControlFragment getFragment()
  {
    return (ShotControlFragment)this.fragmentRef.get();
  }

  protected abstract int getShotType();

  abstract boolean onReceive(Context paramContext, Intent paramIntent);

  protected void reset()
  {
    ShotControlFragment localShotControlFragment = getFragment();
    if (localShotControlFragment != null)
    {
      localShotControlFragment.resetViews();
      localShotControlFragment.notifyShotEnded(getShotType());
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.ShotControlHandler
 * JD-Core Version:    0.6.2
 */