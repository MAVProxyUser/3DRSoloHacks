package com.o3dr.solo.android.fragment.shots.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;
import com.o3dr.solo.android.service.sololink.tlv.SoloShotOptions;
import com.o3dr.solo.android.util.AppPreferences;

public class OrbitSettings extends BaseFragment
{
  private static final String TAG = OrbitSettings.class.getSimpleName();
  private AppPreferences appPrefs;
  private SeekBar cruiseSpeedBar;
  private ShotState<SoloShotOptions> shotState;
  private int shotType;

  private int getCruiseSpeed()
  {
    switch (this.shotType)
    {
    default:
      return this.appPrefs.getOrbitCruiseSpeed();
    case 5:
    }
    return this.appPrefs.getFollowCruiseSpeed();
  }

  private void setCruiseSpeed(int paramInt)
  {
    switch (this.shotType)
    {
    default:
      this.appPrefs.setOrbitCruiseSpeed(paramInt);
    case 5:
    }
    while (true)
    {
      if (this.shotState != null)
        this.shotState.setAbsoluteCruiseSpeed(paramInt);
      return;
      this.appPrefs.setFollowCruiseSpeed(paramInt);
    }
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903093, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    this.shotState = getSoloLinkManager().getShotManager().getCurrentShotState();
  }

  public void onResume()
  {
    super.onResume();
    this.cruiseSpeedBar.setProgress(-1 + getCruiseSpeed());
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle == null)
      throw new IllegalArgumentException("Missing required shot type argument.");
    this.appPrefs = new AppPreferences(getContext());
    this.shotType = localBundle.getInt("extra_shot_type");
    this.cruiseSpeedBar = ((SeekBar)paramView.findViewById(2131493094));
    this.cruiseSpeedBar.setMax(9);
    this.cruiseSpeedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        OrbitSettings.this.setCruiseSpeed(paramAnonymousInt + 1);
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
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.settings.OrbitSettings
 * JD-Core Version:    0.6.2
 */