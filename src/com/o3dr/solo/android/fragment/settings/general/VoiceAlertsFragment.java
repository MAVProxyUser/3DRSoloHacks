package com.o3dr.solo.android.fragment.settings.general;

import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;

public class VoiceAlertsFragment extends SettingsDetailFragment
{
  private static final int DISCRETE_VOLUME_STEP = 10;
  private static final String VOICE_ENABLED = "Voice Enabled";
  private static final String VOLUME_CHANGED = "Volume Changed";
  private static final int VOLUME_MAX = 100;
  private static final int VOLUME_MIN;

  public int getSettingDetailTitle()
  {
    return 2131099922;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903103, paramViewGroup, false);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    final AppPreferences localAppPreferences = new AppPreferences(getContext());
    final AppAnalytics localAppAnalytics = getApplication().getAppAnalytics();
    final SeekBar localSeekBar = (SeekBar)paramView.findViewById(2131493213);
    localSeekBar.setMax(100);
    localSeekBar.setProgress(localAppPreferences.getVoiceAlertsVolume());
    localSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
    {
      public void onProgressChanged(SeekBar paramAnonymousSeekBar, int paramAnonymousInt, boolean paramAnonymousBoolean)
      {
        localAppAnalytics.track("Settings", "Volume Changed", Integer.valueOf(paramAnonymousInt));
        localAppPreferences.setVoiceAlertsVolume(paramAnonymousInt);
      }

      public void onStartTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }

      public void onStopTrackingTouch(SeekBar paramAnonymousSeekBar)
      {
      }
    });
    ImageView localImageView1 = (ImageView)paramView.findViewById(2131493211);
    localImageView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        int i = localSeekBar.getProgress();
        int j = Math.max(i - 10, 0);
        if (i != j)
          localSeekBar.setProgress(j);
      }
    });
    localImageView1.setOnLongClickListener(new View.OnLongClickListener()
    {
      public boolean onLongClick(View paramAnonymousView)
      {
        localSeekBar.setProgress(0);
        return true;
      }
    });
    ImageView localImageView2 = (ImageView)paramView.findViewById(2131493212);
    localImageView2.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        int i = localSeekBar.getProgress();
        int j = Math.min(i + 10, 100);
        if (i != j)
          localSeekBar.setProgress(j);
      }
    });
    localImageView2.setOnLongClickListener(new View.OnLongClickListener()
    {
      public boolean onLongClick(View paramAnonymousView)
      {
        localSeekBar.setProgress(100);
        return true;
      }
    });
    final SwitchCompat localSwitchCompat = (SwitchCompat)paramView.findViewById(2131493210);
    localSwitchCompat.setChecked(localAppPreferences.isVoiceAlertsEnabled());
    paramView.findViewById(2131493209).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localSwitchCompat.toggle();
        boolean bool = localSwitchCompat.isChecked();
        localAppPreferences.enableVoiceAlerts(bool);
        localAppAnalytics.track("Settings", "Voice Enabled", Boolean.valueOf(bool));
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.general.VoiceAlertsFragment
 * JD-Core Version:    0.6.2
 */