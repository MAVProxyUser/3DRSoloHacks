package com.o3dr.solo.android.fragment.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.activity.BaseActivity;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.util.unit.UnitManager;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;
import com.o3dr.solo.android.util.unit.providers.speed.SpeedUnitProvider;
import com.o3dr.solo.android.util.unit.systems.UnitSystem;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class BaseFragment extends Fragment
{
  private static final String TAG = BaseFragment.class.getSimpleName();
  private static final IntentFilter droneAttachedFilter = new IntentFilter("com.o3dr.solo.android.action.DRONE_KIT_STATE");
  private final BroadcastReceiver droneAttachedReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (BaseFragment.this.parent == null);
      label119: 
      do
      {
        return;
        String str = paramAnonymousIntent.getAction();
        int i = -1;
        switch (str.hashCode())
        {
        default:
        case 1142324680:
        }
        while (true)
        {
          switch (i)
          {
          default:
            return;
          case 0:
          }
          if (!paramAnonymousIntent.getBooleanExtra("extra_is_drone_kit_connected", false))
            break label119;
          if (!BaseFragment.this.isDroneAttached.compareAndSet(false, true))
            break;
          BaseFragment.this.onDroneAttached(BaseFragment.this.parent.getDrone());
          return;
          if (str.equals("com.o3dr.solo.android.action.DRONE_KIT_STATE"))
            i = 0;
        }
      }
      while (!BaseFragment.this.isDroneAttached.compareAndSet(true, false));
      BaseFragment.this.onDroneDetached();
    }
  };
  private final AtomicBoolean isDroneAttached = new AtomicBoolean(false);
  private LocalBroadcastManager lbm;
  private BaseActivity parent;

  protected SoloApp getApplication()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity == null)
      return null;
    return (SoloApp)localFragmentActivity.getApplication();
  }

  public ArtooLinkManager getArtooLinkManager()
  {
    if (this.parent == null)
      return null;
    return this.parent.getArtooLinkManager();
  }

  protected final LocalBroadcastManager getBroadcastManager()
  {
    return this.lbm;
  }

  public Context getContext()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity == null)
      return null;
    return localFragmentActivity.getApplicationContext();
  }

  public Drone getDrone()
  {
    if (!this.isDroneAttached.get())
      return null;
    return this.parent.getDrone();
  }

  public LengthUnitProvider getLengthUnitProvider()
  {
    Context localContext = getContext();
    if (localContext == null)
      return null;
    return UnitManager.getUnitSystem(localContext).getLengthUnitProvider();
  }

  public SoloLinkManager getSoloLinkManager()
  {
    if (this.parent == null)
      return null;
    return this.parent.getSoloLinkManager();
  }

  protected SpeedUnitProvider getSpeedUnitProvider()
  {
    Context localContext = getContext();
    if (localContext == null)
      return null;
    return UnitManager.getUnitSystem(localContext).getSpeedUnitProvider();
  }

  protected boolean isArtooLinkConnected()
  {
    return (this.parent != null) && (this.parent.isArtooLinkConnected());
  }

  protected final boolean isDroneAttached()
  {
    return this.isDroneAttached.get();
  }

  public boolean isDroneConnected()
  {
    return (this.parent != null) && (this.parent.isDroneConnected());
  }

  protected boolean isLinkConnected()
  {
    return (this.parent != null) && (this.parent.isLinkConnected());
  }

  protected boolean isSoloLinkConnected()
  {
    return (this.parent != null) && (this.parent.isSoloLinkConnected());
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof BaseActivity))
      throw new IllegalStateException("Parent activity must be an instance of " + BaseActivity.class.getName());
    this.parent = ((BaseActivity)paramActivity);
    this.lbm = LocalBroadcastManager.getInstance(paramActivity.getApplicationContext());
  }

  public void onDetach()
  {
    super.onDetach();
    this.parent = null;
    this.lbm = null;
  }

  protected void onDroneAttached(Drone paramDrone)
  {
  }

  protected void onDroneDetached()
  {
  }

  public void onStart()
  {
    super.onStart();
    Drone localDrone = this.parent.getDrone();
    if ((localDrone != null) && (this.isDroneAttached.compareAndSet(false, true)))
      onDroneAttached(localDrone);
    this.lbm.registerReceiver(this.droneAttachedReceiver, droneAttachedFilter);
  }

  public void onStop()
  {
    super.onStop();
    this.lbm.unregisterReceiver(this.droneAttachedReceiver);
    if (this.isDroneAttached.compareAndSet(true, false))
      onDroneDetached();
  }

  public boolean playSound(int paramInt)
  {
    return (this.parent != null) && (this.parent.playSound(paramInt));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.base.BaseFragment
 * JD-Core Version:    0.6.2
 */