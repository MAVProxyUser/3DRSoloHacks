package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloButtonSetting;
import com.o3dr.solo.android.service.sololink.tlv.SoloButtonSettingSetter;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.AppPreferences;

public class SoloPresetButtonFragment extends SettingsDetailFragment
{
  private static final SoloButtonSettingSetter DEFAULT_BUTTON_A_PRESET;
  private static final SoloButtonSettingSetter DEFAULT_BUTTON_B_PRESET;
  public static final String EXTRA_TYPE_PRESET = "extra_type_preset";
  private static final String OPTION_NAME = "Option Name";
  private static final String TAG = SoloPresetButtonFragment.class.getSimpleName();
  private static final IntentFilter filter;
  private AppAnalytics appAnalytics;
  private AppPreferences appPrefs;
  private SoloButtonSettingSetter presetButtonSetter;
  private int presetType;
  private RadioGroup presetTypes;
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i;
      switch (str.hashCode())
      {
      default:
        i = -1;
        label39: switch (i)
        {
        default:
        case 0:
        case 1:
        }
        break;
      case -825409525:
      case -1042944382:
      }
      do
      {
        return;
        if (!str.equals("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED"))
          break;
        i = 0;
        break label39;
        if (!str.equals("pref_advanced_flight_modes"))
          break;
        i = 1;
        break label39;
      }
      while (paramAnonymousIntent.getIntExtra("extra_preset_button_type", -1) != SoloPresetButtonFragment.this.presetType);
      SoloPresetButtonFragment.this.updatePresetButton();
      return;
      SoloPresetButtonFragment.this.enableAdvancedModes(SoloPresetButtonFragment.this.appPrefs.areAdvancedFlightModesEnabled());
    }
  };

  static
  {
    DEFAULT_BUTTON_A_PRESET = new SoloButtonSettingSetter(4, 0, 2, VehicleMode.UNKNOWN.getMode());
    DEFAULT_BUTTON_B_PRESET = new SoloButtonSettingSetter(5, 0, 1, VehicleMode.UNKNOWN.getMode());
    filter = new IntentFilter();
    filter.addAction("com.o3dr.solo.android.action.PRESET_BUTTON_LOADED");
    filter.addAction("pref_advanced_flight_modes");
  }

  public static void disableSololinkAdvancedFlightModes(SoloLinkManager paramSoloLinkManager)
  {
    if (paramSoloLinkManager == null)
      return;
    setPresetButton(paramSoloLinkManager, DEFAULT_BUTTON_A_PRESET);
    setPresetButton(paramSoloLinkManager, DEFAULT_BUTTON_B_PRESET);
  }

  private void enableAdvancedModes(boolean paramBoolean)
  {
    for (PresetButton localPresetButton : PresetButton.values())
      if (localPresetButton.advanced)
        localPresetButton.enableButton(paramBoolean);
  }

  public static CharSequence getPresetLabel(Context paramContext, int paramInt1, int paramInt2)
  {
    PresetButton localPresetButton = PresetButton.getPresetButton(paramInt1, paramInt2);
    if (localPresetButton == null)
      return null;
    return localPresetButton.getLabel(paramContext);
  }

  private void setPresetButton(int paramInt1, int paramInt2)
  {
    this.presetButtonSetter.setShotTypeFlightMode(paramInt1, paramInt2);
    setPresetButton(getSoloLinkManager(), this.presetButtonSetter);
    Context localContext = getContext();
    if (localContext != null)
      this.appAnalytics.track("Settings", "Option Name", SoloMessageShot.getShotLabel(localContext, paramInt1));
  }

  private static void setPresetButton(SoloLinkManager paramSoloLinkManager, SoloButtonSettingSetter paramSoloButtonSettingSetter)
  {
    if (paramSoloLinkManager == null)
      return;
    paramSoloLinkManager.pushPresetButtonSettings(paramSoloButtonSettingSetter);
  }

  private void updatePresetButton()
  {
    SoloLinkManager localSoloLinkManager = getSoloLinkManager();
    if (localSoloLinkManager == null)
      this.presetTypes.clearCheck();
    PresetButton localPresetButton;
    do
    {
      return;
      SoloButtonSetting localSoloButtonSetting = localSoloLinkManager.getLoadedPresetButton(this.presetType);
      if (localSoloButtonSetting == null)
      {
        this.presetTypes.clearCheck();
        return;
      }
      localPresetButton = PresetButton.getPresetButton(localSoloButtonSetting.getShotType(), localSoloButtonSetting.getFlightMode());
      if (localPresetButton == null)
      {
        this.presetTypes.clearCheck();
        return;
      }
      this.presetTypes.check(localPresetButton.viewId);
    }
    while (!localPresetButton.advanced);
    this.appPrefs.setAdvancedFlightModes(true);
  }

  public int getSettingDetailTitle()
  {
    switch (this.presetType)
    {
    default:
      return 0;
    case 4:
      return 2131099938;
    case 5:
    }
    return 2131099939;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getArguments();
    if (localBundle == null)
      throw new IllegalStateException("The preset type must be specified.");
    this.presetType = localBundle.getInt("extra_type_preset", 4);
    this.presetButtonSetter = new SoloButtonSettingSetter(this.presetType, 0);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903116, paramViewGroup, false);
  }

  public void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    SoloLinkManager localSoloLinkManager = getSoloLinkManager();
    if (localSoloLinkManager != null)
    {
      updatePresetButton();
      localSoloLinkManager.loadPresetButtonSettings();
    }
  }

  public void onStart()
  {
    super.onStart();
    enableAdvancedModes(this.appPrefs.areAdvancedFlightModesEnabled());
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
    Context localContext = getContext();
    this.appPrefs = new AppPreferences(localContext);
    this.appAnalytics = getApplication().getAppAnalytics();
    for (PresetButton localPresetButton : PresetButton.values())
    {
      RadioButton localRadioButton = (RadioButton)paramView.findViewById(localPresetButton.viewId);
      if (localRadioButton != null)
      {
        localRadioButton.setText(localPresetButton.getLabel(localContext));
        localPresetButton.setButtonView(localRadioButton);
      }
      View localView = paramView.findViewById(localPresetButton.dividerId);
      if (localView != null)
        localPresetButton.setButtonDivider(localView);
    }
    this.presetTypes = ((RadioGroup)paramView.findViewById(2131493292));
    this.presetTypes.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      public void onCheckedChanged(RadioGroup paramAnonymousRadioGroup, int paramAnonymousInt)
      {
        SoloPresetButtonFragment.PresetButton localPresetButton = SoloPresetButtonFragment.PresetButton.getPresetButton(paramAnonymousInt);
        if (localPresetButton == null)
          return;
        SoloPresetButtonFragment.this.setPresetButton(localPresetButton.shotType, localPresetButton.vehicleMode.getMode());
      }
    });
  }

  public static enum PresetButton
  {
    final boolean advanced;
    View buttonDivider;
    RadioButton buttonView;

    @IdRes
    final int dividerId;
    final int shotType;
    final VehicleMode vehicleMode;

    @IdRes
    final int viewId;

    static
    {
      FLY = new PresetButton("FLY", 2, 2131493297, 2131493298, VehicleMode.COPTER_LOITER);
      MANUAL = new PresetButton("MANUAL", 3, 2131493299, 2131493300, VehicleMode.COPTER_ALT_HOLD, true);
      STABILIZE = new PresetButton("STABILIZE", 4, 2131493301, 2131493302, VehicleMode.COPTER_STABILIZE, true);
      ACRO = new PresetButton("ACRO", 5, 2131493303, 2131493304, VehicleMode.COPTER_ACRO, true);
      DRIFT = new PresetButton("DRIFT", 6, 2131493305, 2131493306, VehicleMode.COPTER_DRIFT, true);
      SPORT = new PresetButton("SPORT", 7, 2131493307, 2131493308, VehicleMode.COPTER_SPORT, true);
      PresetButton[] arrayOfPresetButton = new PresetButton[8];
      arrayOfPresetButton[0] = CABLE_CAM;
      arrayOfPresetButton[1] = ORBIT;
      arrayOfPresetButton[2] = FLY;
      arrayOfPresetButton[3] = MANUAL;
      arrayOfPresetButton[4] = STABILIZE;
      arrayOfPresetButton[5] = ACRO;
      arrayOfPresetButton[6] = DRIFT;
      arrayOfPresetButton[7] = SPORT;
    }

    private PresetButton(@IdRes int paramInt1, @IdRes int paramInt2, int paramInt3)
    {
      this(paramInt1, paramInt2, paramInt3, VehicleMode.UNKNOWN, false);
    }

    private PresetButton(@IdRes int paramInt1, @IdRes int paramInt2, int paramInt3, VehicleMode paramVehicleMode, boolean paramBoolean)
    {
      this.viewId = paramInt1;
      this.dividerId = paramInt2;
      this.shotType = paramInt3;
      this.advanced = paramBoolean;
      this.vehicleMode = paramVehicleMode;
    }

    private PresetButton(@IdRes int paramInt1, @IdRes int paramInt2, VehicleMode paramVehicleMode)
    {
      this(paramInt1, paramInt2, paramVehicleMode, false);
    }

    private PresetButton(@IdRes int paramInt1, @IdRes int paramInt2, VehicleMode paramVehicleMode, boolean paramBoolean)
    {
      this(paramInt1, paramInt2, -1, paramVehicleMode, paramBoolean);
    }

    static PresetButton getPresetButton(@IdRes int paramInt)
    {
      for (PresetButton localPresetButton : values())
        if (paramInt == localPresetButton.viewId)
          return localPresetButton;
      return null;
    }

    public static PresetButton getPresetButton(int paramInt1, int paramInt2)
    {
      for (PresetButton localPresetButton : values())
        if ((localPresetButton.shotType == paramInt1) && (localPresetButton.vehicleMode.getMode() == paramInt2))
          return localPresetButton;
      return null;
    }

    void enableButton(boolean paramBoolean)
    {
      if (paramBoolean);
      for (int i = 0; ; i = 8)
      {
        if (this.buttonView != null)
          this.buttonView.setVisibility(i);
        if (this.buttonDivider != null)
          this.buttonDivider.setVisibility(i);
        return;
      }
    }

    CharSequence getLabel(Context paramContext)
    {
      if (-1 == this.shotType)
        return SoloMessageShot.getFlightModeLabel(paramContext, this.vehicleMode);
      return SoloMessageShot.getShotLabel(paramContext, this.shotType);
    }

    public boolean isAdvanced()
    {
      return this.advanced;
    }

    void setButtonDivider(View paramView)
    {
      this.buttonDivider = paramView;
    }

    void setButtonView(RadioButton paramRadioButton)
    {
      this.buttonView = paramRadioButton;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloPresetButtonFragment
 * JD-Core Version:    0.6.2
 */