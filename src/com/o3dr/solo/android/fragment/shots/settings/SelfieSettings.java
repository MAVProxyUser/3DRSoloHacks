package com.o3dr.solo.android.fragment.shots.settings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.SelfieState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;

public class SelfieSettings extends BaseFragment
{
  public static final String ACTION_SELFIE_SETTINGS_UPDATED = "com.o3dr.solo.android.action.SELFIE_SETTINGS_UPDATED";
  private static final String TAG = SelfieSettings.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter("com.o3dr.solo.android.action.SELFIE_STATUS");
  private SeekBar altitudeUpBar;
  private TextView altitudeUpValue;
  private AppPreferences appPrefs;
  private SeekBar cruiseSpeedBar;
  private SeekBar distanceOutBar;
  private TextView distanceOutValue;
  private LengthUnitProvider lengthUnit;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case -925213424:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.solo.android.action.SELFIE_STATUS"))
            i = 0;
          break;
        case 0:
        }
      SelfieSettings.this.enableSelfieSettings();
    }
  };
  private SelfieState selfieState;

  private void enableSelfieSettings()
  {
    boolean bool1 = true;
    boolean bool3;
    SeekBar localSeekBar2;
    if (this.selfieState != null)
    {
      boolean bool2 = this.selfieState.isActive();
      SeekBar localSeekBar1 = this.distanceOutBar;
      if (bool2)
        break label52;
      bool3 = bool1;
      localSeekBar1.setEnabled(bool3);
      localSeekBar2 = this.altitudeUpBar;
      if (bool2)
        break label58;
    }
    while (true)
    {
      localSeekBar2.setEnabled(bool1);
      return;
      label52: bool3 = false;
      break;
      label58: bool1 = false;
    }
  }

  private int getAltitudeUp()
  {
    return this.appPrefs.getSelfieAltitudeUp();
  }

  private int getCruiseSpeed()
  {
    return this.appPrefs.getSelfieCruiseSpeed();
  }

  private int getDistanceOut()
  {
    return this.appPrefs.getSelfieDistanceOut();
  }

  private void setAltitudeUp(int paramInt)
  {
    this.appPrefs.setSelfieAltitudeUp(paramInt);
    if ((this.lengthUnit != null) && (this.altitudeUpValue != null))
      this.altitudeUpValue.setText(this.lengthUnit.boxBaseValueToTarget(paramInt).toString());
    getBroadcastManager().sendBroadcast(new Intent("com.o3dr.solo.android.action.SELFIE_SETTINGS_UPDATED"));
  }

  private void setCruiseSpeed(int paramInt)
  {
    this.appPrefs.setSelfieCruiseSpeed(paramInt);
    if (this.selfieState != null)
      this.selfieState.setAbsoluteCruiseSpeed(paramInt);
  }

  private void setDistanceOut(int paramInt)
  {
    this.appPrefs.setSelfieDistanceOut(paramInt);
    if ((this.lengthUnit != null) && (this.distanceOutValue != null))
      this.distanceOutValue.setText(this.lengthUnit.boxBaseValueToTarget(paramInt).toString());
    getBroadcastManager().sendBroadcast(new Intent("com.o3dr.solo.android.action.SELFIE_SETTINGS_UPDATED"));
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903094, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    this.selfieState = ((SelfieState)getSoloLinkManager().getShotManager().getCurrentShotState());
    enableSelfieSettings();
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onDroneDetached()
  {
    super.onDroneDetached();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onResume()
  {
    super.onResume();
    this.lengthUnit = getLengthUnitProvider();
    this.cruiseSpeedBar.setProgress(-1 + getCruiseSpeed());
    int i = getDistanceOut();
    this.distanceOutValue.setText(this.lengthUnit.boxBaseValueToTarget(i).toString());
    this.distanceOutBar.setProgress(i - 20);
    int j = getAltitudeUp();
    this.altitudeUpValue.setText(this.lengthUnit.boxBaseValueToTarget(j).toString());
    this.altitudeUpBar.setProgress(j - 1);
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
        SelfieSettings.this.setCruiseSpeed(paramAnonymousInt + 1);
      }

      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }

      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }
    });
    this.distanceOutValue = ((TextView)paramView.findViewById(2131493114));
    this.distanceOutBar = ((SeekBar)paramView.findViewById(2131493118));
    this.distanceOutBar.setMax(80);
    this.distanceOutBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        SelfieSettings.this.setDistanceOut(paramAnonymousInt + 20);
      }

      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }

      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }
    });
    this.altitudeUpValue = ((TextView)paramView.findViewById(2131493119));
    this.altitudeUpBar = ((SeekBar)paramView.findViewById(2131493123));
    this.altitudeUpBar.setMax(49);
    this.altitudeUpBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        SelfieSettings.this.setAltitudeUp(paramAnonymousInt + 1);
      }

      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }

      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.settings.SelfieSettings
 * JD-Core Version:    0.6.2
 */