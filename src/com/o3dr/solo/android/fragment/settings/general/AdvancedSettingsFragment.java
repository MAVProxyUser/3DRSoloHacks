package com.o3dr.solo.android.fragment.settings.general;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.appstate.DatabaseState;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.dialogs.OkDialog;
import com.o3dr.solo.android.fragment.dialogs.ProgressDialogFragment;
import com.o3dr.solo.android.fragment.dialogs.YesNoDialog;
import com.o3dr.solo.android.fragment.dialogs.YesNoDialog.Listener;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.fragment.settings.solo.SoloPresetButtonFragment;
import com.o3dr.solo.android.fragment.settings.solo.SoloPresetButtonFragment.PresetButton;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloButtonSetting;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.maps.mapbox.offline.MapDownloader;

public class AdvancedSettingsFragment extends SettingsDetailFragment
{
  private static final String ADVANCED_FLIGHT_MODE = "Advanced Flight Mode";
  private static final String TAG = SettingsDetailFragment.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED");
  private SwitchCompat advancedFlightModesToggle;
  private AppPreferences appPrefs;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      switch (str.hashCode())
      {
      default:
      case -825409525:
      }
      label28: for (int i = -1; ; i = 0)
        switch (i)
        {
        default:
          return;
          if (!str.equals("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED"))
            break label28;
        case 0:
        }
      int j = paramAnonymousIntent.getIntExtra("extra_preset_button_type", -1);
      AdvancedSettingsFragment.this.updateAdvancedToggle(j);
    }
  };

  private void updateAdvancedToggle(int paramInt)
  {
    if ((paramInt != 4) && (paramInt != 5));
    SoloPresetButtonFragment.PresetButton localPresetButton;
    do
    {
      SoloButtonSetting localSoloButtonSetting;
      do
      {
        SoloLinkManager localSoloLinkManager;
        do
        {
          return;
          localSoloLinkManager = getSoloLinkManager();
        }
        while (localSoloLinkManager == null);
        localSoloButtonSetting = localSoloLinkManager.getLoadedPresetButton(paramInt);
      }
      while (localSoloButtonSetting == null);
      localPresetButton = SoloPresetButtonFragment.PresetButton.getPresetButton(localSoloButtonSetting.getShotType(), localSoloButtonSetting.getFlightMode());
    }
    while ((localPresetButton == null) || (!localPresetButton.isAdvanced()));
    this.advancedFlightModesToggle.setChecked(true);
    this.appPrefs.setAdvancedFlightModes(true);
  }

  public int getSettingDetailTitle()
  {
    return 2131099842;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903095, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    updateAdvancedToggle(4);
    updateAdvancedToggle(5);
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onDroneDetached()
  {
    super.onDroneDetached();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.appPrefs = new AppPreferences(getContext());
    final AppAnalytics localAppAnalytics = getApplication().getAppAnalytics();
    final Context localContext = getContext();
    final FragmentManager localFragmentManager = getChildFragmentManager();
    this.advancedFlightModesToggle = ((SwitchCompat)paramView.findViewById(2131493125));
    this.advancedFlightModesToggle.setChecked(this.appPrefs.areAdvancedFlightModesEnabled());
    paramView.findViewById(2131493124).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AdvancedSettingsFragment.this.advancedFlightModesToggle.toggle();
        boolean bool = AdvancedSettingsFragment.this.advancedFlightModesToggle.isChecked();
        AdvancedSettingsFragment.this.appPrefs.setAdvancedFlightModes(bool);
        localAppAnalytics.track("Settings", "Advanced Flight Mode", Boolean.valueOf(bool));
        String str = AdvancedSettingsFragment.this.getString(2131099983);
        if (!bool)
        {
          SoloPresetButtonFragment.disableSololinkAdvancedFlightModes(AdvancedSettingsFragment.this.getSoloLinkManager());
          return;
        }
        OkDialog.newInstance(localContext, str, AdvancedSettingsFragment.this.getString(2131099982)).show(localFragmentManager, str);
      }
    });
    paramView.findViewById(2131493126).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        YesNoDialog.newInstance(localContext, AdvancedSettingsFragment.this.getString(2131100037), AdvancedSettingsFragment.this.getString(2131099967), new YesNoDialog.Listener()
        {
          public void onNo()
          {
          }

          public void onYes()
          {
            final ProgressDialogFragment localProgressDialogFragment = ProgressDialogFragment.newInstance("Clearing map data...");
            localProgressDialogFragment.setIndeterminate(true);
            new AsyncTask()
            {
              protected Void doInBackground(Void[] paramAnonymous3ArrayOfVoid)
              {
                if (AdvancedSettingsFragment.this.isResumed())
                {
                  SoloApp localSoloApp = AdvancedSettingsFragment.this.getApplication();
                  if (localSoloApp != null)
                    localSoloApp.getMapDownloader().cancelDownload();
                  DatabaseState.deleteDatabase(AdvancedSettingsFragment.this.getContext(), AdvancedSettingsFragment.this.getString(2131099972));
                }
                return null;
              }

              protected void onPostExecute(Void paramAnonymous3Void)
              {
                if (AdvancedSettingsFragment.this.isResumed())
                {
                  if (localProgressDialogFragment.isShowing())
                    localProgressDialogFragment.dismiss();
                  Toast.makeText(AdvancedSettingsFragment.3.this.val$context, 2131099968, 0).show();
                }
              }

              protected void onPreExecute()
              {
                if (AdvancedSettingsFragment.this.isResumed())
                  localProgressDialogFragment.show(AdvancedSettingsFragment.this.getChildFragmentManager(), "Map cache deletion progress dialog");
              }
            }
            .execute(new Void[0]);
          }
        }).show(localFragmentManager, "Delete map cache");
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.general.AdvancedSettingsFragment
 * JD-Core Version:    0.6.2
 */