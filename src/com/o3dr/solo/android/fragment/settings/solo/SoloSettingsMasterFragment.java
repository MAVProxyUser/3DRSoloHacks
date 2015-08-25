package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import com.o3dr.solo.android.fragment.settings.SettingsMasterFragment;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloButtonSetting;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.Utils;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;

public class SoloSettingsMasterFragment extends SettingsMasterFragment
{
  private static final IntentFilter filter = new IntentFilter();
  private View accelCalibration;
  private TextView altitudeLimitSummary;
  private View altitudeLimits;
  private View armLights;
  private View compassCalibration;
  private TextView controllerModeSummary;
  private View controllerStyle;
  private View performanceSettings;
  private View presetA;
  private TextView presetASummary;
  private View presetB;
  private TextView presetBSummary;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (SoloSettingsMasterFragment.this.getActivity() == null);
      label64: String str2;
      do
      {
        return;
        String str1 = paramAnonymousIntent.getAction();
        int i;
        switch (str1.hashCode())
        {
        default:
          i = -1;
        case 933556871:
        case 993313354:
        case -825409525:
        case 598821639:
        }
        while (true)
          switch (i)
          {
          default:
            return;
          case 0:
            SoloSettingsMasterFragment.this.updateWifiSettingsSummary();
            return;
            if (!str1.equals("com.o3dr.solo.android.action.SOLOLINK_WIFI_INFO_UPDATED"))
              break label64;
            i = 0;
            continue;
            if (!str1.equals("pref_controller_mode"))
              break label64;
            i = 1;
            continue;
            if (!str1.equals("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED"))
              break label64;
            i = 2;
            continue;
            if (!str1.equals("com.o3dr.services.android.lib.attribute.event.PARAMETERS_RECEIVED"))
              break label64;
            i = 3;
          case 1:
          case 2:
          case 3:
          }
        SoloSettingsMasterFragment.this.updateControllerModeSummary();
        return;
        int j = paramAnonymousIntent.getIntExtra("extra_preset_button_type", -1);
        switch (j)
        {
        default:
          return;
        case 4:
          SoloSettingsMasterFragment.this.updatePresetSummary(j);
          return;
        case 5:
        }
        SoloSettingsMasterFragment.this.updatePresetSummary(j);
        return;
        str2 = paramAnonymousIntent.getStringExtra("com.o3dr.services.android.lib.attribute.event.extra.PARAMETER_NAME");
      }
      while (!SoloAltitudeLimitFragment.getAltitudeLimitParameterName().equals(str2));
      double d = paramAnonymousIntent.getDoubleExtra("com.o3dr.services.android.lib.attribute.event.extra.PARAMETER_VALUE", 0.0D);
      SoloSettingsMasterFragment.this.updateAltitudeLimitSummary(d);
    }
  };
  private View wifiSettings;
  private TextView wifiSettingsSummary;

  static
  {
    filter.addAction("pref_controller_mode");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.PARAMETERS_RECEIVED");
    filter.addAction("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED");
    filter.addAction("com.o3dr.solo.android.action.SOLOLINK_WIFI_INFO_UPDATED");
  }

  private void updateAltitudeLimitSummary(double paramDouble)
  {
    this.altitudeLimitSummary.setText(getLengthUnitProvider().boxBaseValueToTarget(paramDouble).toString());
  }

  private void updateControllerModeSummary()
  {
    if (this.appPrefs.getControllerStyle() == 1);
    for (String str = "Mode 1"; ; str = "Mode 2")
    {
      this.controllerModeSummary.setText(str);
      return;
    }
  }

  private void updatePresetSummary(int paramInt)
  {
    Context localContext = getContext();
    SoloLinkManager localSoloLinkManager = getSoloLinkManager();
    if ((localSoloLinkManager == null) || (localContext == null));
    CharSequence localCharSequence;
    do
    {
      SoloButtonSetting localSoloButtonSetting;
      do
      {
        return;
        localSoloButtonSetting = localSoloLinkManager.getLoadedPresetButton(paramInt);
      }
      while (localSoloButtonSetting == null);
      localCharSequence = SoloPresetButtonFragment.getPresetLabel(localContext, localSoloButtonSetting.getShotType(), localSoloButtonSetting.getFlightMode());
    }
    while (TextUtils.isEmpty(localCharSequence));
    switch (paramInt)
    {
    default:
      return;
    case 4:
      this.presetASummary.setText(localCharSequence);
      return;
    case 5:
    }
    this.presetBSummary.setText(localCharSequence);
  }

  private void updateWifiSettingsSummary()
  {
    ArtooLinkManager localArtooLinkManager = getArtooLinkManager();
    if (localArtooLinkManager == null);
    String str;
    do
    {
      Pair localPair;
      do
      {
        return;
        localPair = localArtooLinkManager.getSoloLinkWifiInfo();
      }
      while (localPair == null);
      str = (String)localPair.first;
    }
    while (TextUtils.isEmpty(str));
    this.wifiSettingsSummary.setText(str);
  }

  protected View getViewForPaneId(int paramInt)
  {
    switch (paramInt)
    {
    case 2131493264:
    case 2131493265:
    case 2131493267:
    case 2131493268:
    case 2131493271:
    case 2131493272:
    case 2131493274:
    case 2131493275:
    case 2131493277:
    case 2131493278:
    default:
      return null;
    case 2131493263:
      return this.wifiSettings;
    case 2131493266:
      return this.controllerStyle;
    case 2131493269:
      return this.performanceSettings;
    case 2131493270:
      return this.altitudeLimits;
    case 2131493273:
      return this.presetA;
    case 2131493276:
      return this.presetB;
    case 2131493279:
      return this.armLights;
    case 2131493280:
      return this.accelCalibration;
    case 2131493281:
    }
    return this.compassCalibration;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903114, paramViewGroup, false);
  }

  public void onStart()
  {
    super.onStart();
    updateWifiSettingsSummary();
    updateControllerModeSummary();
    updateAltitudeLimitSummary(Utils.getAltitudeLimit(getDrone()));
    updatePresetSummary(4);
    updatePresetSummary(5);
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
    final ScrollView localScrollView = (ScrollView)paramView.findViewById(2131493262);
    this.wifiSettings = paramView.findViewById(2131493263);
    this.wifiSettings.setOnClickListener(this.settingsPaneClickListener);
    this.wifiSettingsSummary = ((TextView)paramView.findViewById(2131493265));
    this.controllerStyle = paramView.findViewById(2131493266);
    this.controllerModeSummary = ((TextView)paramView.findViewById(2131493268));
    this.performanceSettings = paramView.findViewById(2131493269);
    this.performanceSettings.setOnClickListener(this.settingsPaneClickListener);
    this.altitudeLimits = paramView.findViewById(2131493270);
    this.altitudeLimits.setOnClickListener(this.settingsPaneClickListener);
    this.altitudeLimitSummary = ((TextView)paramView.findViewById(2131493272));
    this.presetA = paramView.findViewById(2131493273);
    this.presetA.setOnClickListener(this.settingsPaneClickListener);
    this.presetASummary = ((TextView)paramView.findViewById(2131493275));
    this.presetB = paramView.findViewById(2131493276);
    this.presetB.setOnClickListener(this.settingsPaneClickListener);
    this.presetBSummary = ((TextView)paramView.findViewById(2131493278));
    this.armLights = paramView.findViewById(2131493279);
    this.armLights.setOnClickListener(this.settingsPaneClickListener);
    this.accelCalibration = paramView.findViewById(2131493280);
    this.accelCalibration.setOnClickListener(this.settingsPaneClickListener);
    this.compassCalibration = paramView.findViewById(2131493281);
    this.compassCalibration.setOnClickListener(this.settingsPaneClickListener);
    Bundle localBundle = getArguments();
    if (localBundle != null)
    {
      selectPane(localBundle.getInt("extra_sub_settings_selected_pane_id", -1));
      if (this.selectedPane != null)
        localScrollView.post(new Runnable()
        {
          public void run()
          {
            localScrollView.scrollTo(SoloSettingsMasterFragment.this.selectedPane.getLeft(), SoloSettingsMasterFragment.this.selectedPane.getBottom());
          }
        });
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloSettingsMasterFragment
 * JD-Core Version:    0.6.2
 */