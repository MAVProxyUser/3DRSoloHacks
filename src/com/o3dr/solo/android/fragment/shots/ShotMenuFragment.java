package com.o3dr.solo.android.fragment.shots;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RelativeLayout;
import com.google.android.gms.location.LocationRequest;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.solo.android.activity.FlightActivity;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.ShotTutorialFragment;
import com.o3dr.solo.android.fragment.base.BaseDialogFragment;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.location.CheckLocationSettings;

public class ShotMenuFragment extends BaseDialogFragment
  implements View.OnClickListener
{
  private static final String DIALOG_TAG = "Shot tutorial";
  private static final long DISMISSAL_TIMEOUT = 200L;
  public static final int FOLLOW_SETTINGS_UPDATE = 147;
  private static final String SHOT_TYPE = "Shot type";
  private static final IntentFilter intentFilter = new IntentFilter();
  private AppAnalytics appAnalytics;
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 1138777058:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE"))
            i = 0;
          break;
        case 0:
        }
      ShotMenuFragment.this.updateSelectedShot();
    }
  };
  private RelativeLayout cableCamView;
  private Context context;
  private final Runnable dismissDialog = new Runnable()
  {
    public void run()
    {
      ShotMenuFragment.this.dismiss();
    }
  };
  private State droneState;
  private RelativeLayout followView;
  private RelativeLayout freeFlightView;
  private final Handler handler = new Handler();
  private RelativeLayout orbitView;
  private RelativeLayout selfieView;

  static
  {
    intentFilter.addAction("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE");
  }

  private void cancelDismissal()
  {
    this.handler.removeCallbacks(this.dismissDialog);
  }

  public static void checkGpsLocationSettings(FlightActivity paramFlightActivity)
  {
    new CheckLocationSettings(paramFlightActivity, LocationRequest.create().setPriority(100).setFastestInterval(16L).setInterval(16L).setSmallestDisplacement(0.0F), 147, new Runnable()
    {
      public void run()
      {
        SoloLinkManager localSoloLinkManager = this.val$flightActivity.getSoloLinkManager();
        if (localSoloLinkManager != null)
          localSoloLinkManager.getShotManager().setCurrentShot(5);
      }
    }).check();
  }

  private void resetShotView()
  {
    if (this.freeFlightView != null)
      this.freeFlightView.setActivated(false);
    if (this.selfieView != null)
      this.selfieView.setActivated(false);
    if (this.cableCamView != null)
      this.cableCamView.setActivated(false);
    if (this.orbitView != null)
      this.orbitView.setActivated(false);
    if (this.followView != null)
      this.followView.setActivated(false);
  }

  private void triggerDismissal()
  {
    this.handler.postDelayed(this.dismissDialog, 200L);
  }

  private void updateSelectedShot()
  {
    SoloLinkManager localSoloLinkManager = getSoloLinkManager();
    if (localSoloLinkManager == null);
    for (int i = -1; ; i = localSoloLinkManager.getShotManager().getCurrentShot())
    {
      resetShotView();
      switch (i)
      {
      case 3:
      case 4:
      default:
        return;
      case -1:
      case 2:
      case 0:
      case 1:
      case 5:
      }
    }
    this.freeFlightView.setActivated(true);
    return;
    this.cableCamView.setActivated(true);
    return;
    this.selfieView.setActivated(true);
    return;
    this.orbitView.setActivated(true);
    return;
    this.followView.setActivated(true);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    if (!(paramActivity instanceof FlightActivity))
      throw new IllegalStateException("Parent activity must be an instance of " + FlightActivity.class.getName());
  }

  public void onClick(View paramView)
  {
    cancelDismissal();
    resetShotView();
    int i;
    SoloLinkManager localSoloLinkManager;
    switch (paramView.getId())
    {
    case 2131493239:
    case 2131493241:
    case 2131493243:
    default:
      i = -1;
      this.appAnalytics.track("Shot Selected", "Shot type", SoloMessageShot.getShotLabel(this.context, i));
      paramView.setActivated(true);
      if ((!isSoloLinkConnected()) || (!this.droneState.isFlying()))
        break label174;
      localSoloLinkManager = getSoloLinkManager();
      if ((localSoloLinkManager != null) && (i != localSoloLinkManager.getShotManager().getCurrentShot()))
      {
        if (i != 5)
          break label162;
        checkGpsLocationSettings((FlightActivity)getActivity());
      }
      break;
    case 2131493240:
    case 2131493238:
    case 2131493242:
    case 2131493244:
    }
    while (true)
    {
      triggerDismissal();
      return;
      i = 2;
      break;
      i = 0;
      break;
      i = 1;
      break;
      i = 5;
      break;
      label162: localSoloLinkManager.getShotManager().setCurrentShot(i);
    }
    label174: Bundle localBundle = new Bundle();
    localBundle.putInt("extra_shot_tutorial_type", i);
    ShotTutorialFragment localShotTutorialFragment = new ShotTutorialFragment();
    localShotTutorialFragment.setArguments(localBundle);
    localShotTutorialFragment.show(getChildFragmentManager(), "Shot tutorial");
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903108, paramViewGroup, false);
  }

  protected void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    this.droneState = ((State)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.STATE"));
  }

  public void onStart()
  {
    super.onStart();
    updateSelectedShot();
    getBroadcastManager().registerReceiver(this.broadcastReceiver, intentFilter);
    Dialog localDialog = getDialog();
    if (localDialog != null)
      localDialog.getWindow().setLayout((int)getResources().getDimension(2131165200), -2);
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.broadcastReceiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.freeFlightView = ((RelativeLayout)paramView.findViewById(2131493236));
    this.freeFlightView.setOnClickListener(this);
    this.cableCamView = ((RelativeLayout)paramView.findViewById(2131493240));
    this.cableCamView.setOnClickListener(this);
    this.selfieView = ((RelativeLayout)paramView.findViewById(2131493238));
    this.selfieView.setOnClickListener(this);
    this.orbitView = ((RelativeLayout)paramView.findViewById(2131493242));
    this.orbitView.setOnClickListener(this);
    this.followView = ((RelativeLayout)paramView.findViewById(2131493244));
    this.followView.setOnClickListener(this);
    this.context = getContext();
    this.appAnalytics = getApplication().getAppAnalytics();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.ShotMenuFragment
 * JD-Core Version:    0.6.2
 */