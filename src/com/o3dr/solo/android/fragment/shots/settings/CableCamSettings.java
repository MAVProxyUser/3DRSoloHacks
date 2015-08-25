package com.o3dr.solo.android.fragment.shots.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.CableCamState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloCableCamOptions;
import com.o3dr.solo.android.util.AppPreferences;

public class CableCamSettings extends BaseFragment
{
  private static final String TAG = CableCamSettings.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter("com.o3dr.solo.android.action.CABLE_CAM_STATUS");
  private AppPreferences appPrefs;
  private SwitchCompat autoViewLock;
  private CableCamState cableCamState;
  private SeekBar cruiseSpeedBar;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
        switch (i)
        {
        default:
        case 0:
        }
        break;
      case -189477859:
      }
      SoloCableCamOptions localSoloCableCamOptions;
      do
      {
        do
        {
          return;
          if (!str.equals("com.o3dr.solo.android.action.CABLE_CAM_STATUS"))
            break;
          i = 0;
          break;
        }
        while (CableCamSettings.this.cableCamState == null);
        localSoloCableCamOptions = (SoloCableCamOptions)CableCamSettings.this.cableCamState.getOptions();
      }
      while (localSoloCableCamOptions == null);
      CableCamSettings.this.activateYawDirectionToggle(localSoloCableCamOptions.isYawDirectionClockWise());
    }
  };
  private TextView yawDirectionToggle;

  private void activateYawDirectionToggle(boolean paramBoolean)
  {
    this.yawDirectionToggle.setActivated(paramBoolean);
    if (paramBoolean)
      this.yawDirectionToggle.setText(2131099884);
    while (true)
    {
      if (this.cableCamState != null)
        this.cableCamState.updateYawDirection(paramBoolean);
      return;
      this.yawDirectionToggle.setText(2131099885);
    }
  }

  private int getCruiseSpeed()
  {
    return this.appPrefs.getCableCamCruiseSpeed();
  }

  private boolean isCamInterpolationOn()
  {
    return this.appPrefs.isCableCamInterpolationOn();
  }

  private void setCamInterpolation(boolean paramBoolean)
  {
    this.appPrefs.setCableCamInterpolation(paramBoolean);
    if (this.cableCamState != null)
      this.cableCamState.updateCamInterpolation(paramBoolean);
  }

  private void setCruiseSpeed(int paramInt)
  {
    this.appPrefs.setCableCamCruiseSpeed(paramInt);
    if (this.cableCamState != null)
      this.cableCamState.setAbsoluteCruiseSpeed(paramInt);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903089, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    this.cableCamState = ((CableCamState)getSoloLinkManager().getShotManager().getCurrentShotState());
    SoloCableCamOptions localSoloCableCamOptions = (SoloCableCamOptions)this.cableCamState.getOptions();
    if (localSoloCableCamOptions != null)
      activateYawDirectionToggle(localSoloCableCamOptions.isYawDirectionClockWise());
  }

  public void onStart()
  {
    super.onStart();
    this.cruiseSpeedBar.setProgress(-1 + getCruiseSpeed());
    this.autoViewLock.setChecked(isCamInterpolationOn());
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appPrefs = new AppPreferences(getContext());
    this.cruiseSpeedBar = ((SeekBar)paramView.findViewById(2131493094));
    this.cruiseSpeedBar.setMax(9);
    this.cruiseSpeedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        CableCamSettings.this.setCruiseSpeed(paramAnonymousInt + 1);
      }

      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }

      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }
    });
    this.autoViewLock = ((SwitchCompat)paramView.findViewById(2131493097));
    paramView.findViewById(2131493096).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CableCamSettings.this.autoViewLock.toggle();
        CableCamSettings.this.setCamInterpolation(CableCamSettings.this.autoViewLock.isChecked());
      }
    });
    this.yawDirectionToggle = ((TextView)paramView.findViewById(2131493099));
    this.yawDirectionToggle.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CableCamSettings localCableCamSettings = CableCamSettings.this;
        if (!CableCamSettings.this.yawDirectionToggle.isActivated());
        for (boolean bool = true; ; bool = false)
        {
          localCableCamSettings.activateYawDirectionToggle(bool);
          return;
        }
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.settings.CableCamSettings
 * JD-Core Version:    0.6.2
 */