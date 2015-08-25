package com.o3dr.solo.android.fragment.settings;

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
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.util.AppPreferences;
import java.util.Locale;

public class GlobalSettingsMasterFragment extends SettingsMasterFragment
{
  private static final IntentFilter intentFilter = new IntentFilter();
  private View advancedSettingsPane;
  private View gimbalPane;
  private View goproPane;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 98729262:
      case -1219801886:
      case 822378966:
      case -1038879041:
      case -1542478246:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("pref_unit_system"))
          {
            i = 0;
            continue;
            if (str.equals("pref_voice_alerts_enabled"))
            {
              i = 1;
              continue;
              if (str.equals("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE"))
              {
                i = 2;
                continue;
                if (str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE"))
                {
                  i = 3;
                  continue;
                  if (str.equals("com.o3dr.solo.android.action.VEHICLE_UP_TO_DATE"))
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
      GlobalSettingsMasterFragment.this.updateUnitsSummary();
      return;
      GlobalSettingsMasterFragment.this.updateVoiceAlertsSummary();
      return;
      GlobalSettingsMasterFragment.this.updateUpdatesAlertIcon();
    }
  };
  private View soloPane;
  private View systemInfoPane;
  private View unitsPane;
  private TextView unitsSummary;
  private ImageView updatesAlertIcon;
  private View updatesPane;
  private View voiceAlertsPane;
  private TextView voiceAlertsSummary;

  static
  {
    intentFilter.addAction("pref_unit_system");
    intentFilter.addAction("pref_voice_alerts_enabled");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UP_TO_DATE");
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE");
  }

  private boolean isUpdateAvailable()
  {
    SoloApp localSoloApp = getApplication();
    if (localSoloApp == null);
    UpdateState localUpdateState;
    do
    {
      return false;
      localUpdateState = localSoloApp.getUpdateState();
    }
    while ((localUpdateState == null) || ((!localUpdateState.isServerUpdateAvailable()) && (!localUpdateState.isVehicleUpdateAvailable()) && (!localUpdateState.isVehicleUpdating()) && (!localUpdateState.isGettingUpdatesFromServer())));
    return true;
  }

  private void updateUnitsSummary()
  {
    if (getContext() == null)
      return;
    int i;
    switch (this.appPrefs.getUnitSystemType())
    {
    default:
      Locale localLocale = Locale.getDefault();
      if (Locale.US == localLocale)
        i = 2131099893;
      break;
    case 2:
    case 1:
    }
    while (true)
    {
      this.unitsSummary.setText(i);
      return;
      i = 2131099893;
      continue;
      i = 2131099897;
      continue;
      i = 2131099897;
    }
  }

  private void updateUpdatesAlertIcon()
  {
    boolean bool = isUpdateAvailable();
    ImageView localImageView = this.updatesAlertIcon;
    if (bool);
    for (int i = 0; ; i = 8)
    {
      localImageView.setVisibility(i);
      return;
    }
  }

  private void updateVoiceAlertsSummary()
  {
    if (getContext() == null)
      return;
    if (this.appPrefs.isVoiceAlertsEnabled())
    {
      this.voiceAlertsSummary.setText(2131099878);
      return;
    }
    this.voiceAlertsSummary.setText(2131099870);
  }

  protected View getViewForPaneId(int paramInt)
  {
    switch (paramInt)
    {
    case 2131493144:
    case 2131493145:
    case 2131493147:
    case 2131493148:
    case 2131493150:
    case 2131493151:
    default:
      return null;
    case 2131493140:
      return this.soloPane;
    case 2131493141:
      return this.gimbalPane;
    case 2131493142:
      return this.goproPane;
    case 2131493143:
      return this.updatesPane;
    case 2131493146:
      return this.unitsPane;
    case 2131493149:
      return this.voiceAlertsPane;
    case 2131493152:
      return this.systemInfoPane;
    case 2131493153:
    }
    return this.advancedSettingsPane;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance(true);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903097, paramViewGroup, false);
  }

  public void onStart()
  {
    super.onStart();
    updateUnitsSummary();
    updateVoiceAlertsSummary();
    updateUpdatesAlertIcon();
    getBroadcastManager().registerReceiver(this.receiver, intentFilter);
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    final ScrollView localScrollView = (ScrollView)paramView.findViewById(2131493139);
    this.soloPane = paramView.findViewById(2131493140);
    this.soloPane.setOnClickListener(this.settingsPaneClickListener);
    this.gimbalPane = paramView.findViewById(2131493141);
    this.gimbalPane.setOnClickListener(this.settingsPaneClickListener);
    this.goproPane = paramView.findViewById(2131493142);
    this.goproPane.setOnClickListener(this.settingsPaneClickListener);
    this.updatesPane = paramView.findViewById(2131493143);
    this.updatesPane.setOnClickListener(this.settingsPaneClickListener);
    this.updatesAlertIcon = ((ImageView)paramView.findViewById(2131493145));
    this.unitsSummary = ((TextView)paramView.findViewById(2131493148));
    this.unitsPane = paramView.findViewById(2131493146);
    this.unitsPane.setOnClickListener(this.settingsPaneClickListener);
    this.voiceAlertsSummary = ((TextView)paramView.findViewById(2131493151));
    this.voiceAlertsPane = paramView.findViewById(2131493149);
    this.voiceAlertsPane.setOnClickListener(this.settingsPaneClickListener);
    this.systemInfoPane = paramView.findViewById(2131493152);
    this.systemInfoPane.setOnClickListener(this.settingsPaneClickListener);
    this.advancedSettingsPane = paramView.findViewById(2131493153);
    this.advancedSettingsPane.setOnClickListener(this.settingsPaneClickListener);
    final SwitchCompat localSwitchCompat1 = (SwitchCompat)paramView.findViewById(2131493155);
    localSwitchCompat1.setChecked(this.appPrefs.areVideosSavedLocally());
    paramView.findViewById(2131493154).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localSwitchCompat1.toggle();
        GlobalSettingsMasterFragment.this.appPrefs.saveVideosLocally(localSwitchCompat1.isChecked());
      }
    });
    final SwitchCompat localSwitchCompat2 = (SwitchCompat)paramView.findViewById(2131493157);
    getApplication().getAppAnalytics();
    localSwitchCompat2.setChecked(this.appPrefs.areAnalyticsEnabled());
    paramView.findViewById(2131493156).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        localSwitchCompat2.toggle();
        GlobalSettingsMasterFragment.this.appPrefs.enableAnalytics(localSwitchCompat2.isChecked());
      }
    });
    Bundle localBundle = getArguments();
    if (localBundle != null)
      selectPane(localBundle.getInt("extra_settings_selected_pane_id", -1));
    if (this.selectedPane != null)
      localScrollView.post(new Runnable()
      {
        public void run()
        {
          localScrollView.scrollTo(GlobalSettingsMasterFragment.this.selectedPane.getLeft(), GlobalSettingsMasterFragment.this.selectedPane.getTop());
        }
      });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.GlobalSettingsMasterFragment
 * JD-Core Version:    0.6.2
 */