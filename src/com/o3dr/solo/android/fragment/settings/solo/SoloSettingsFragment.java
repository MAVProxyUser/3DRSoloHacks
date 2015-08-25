package com.o3dr.solo.android.fragment.settings.solo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.fragment.settings.SubSettingsFragment;

public class SoloSettingsFragment extends SubSettingsFragment
{
  private static final IntentFilter filter = new IntentFilter();
  private final BroadcastReceiver receiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 551871472:
      case 662782932:
      case 1256617868:
      case 1962523320:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"))
          {
            i = 0;
            continue;
            if (str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"))
            {
              i = 1;
              continue;
              if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED"))
              {
                i = 2;
                continue;
                if (str.equals("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED"))
                  i = 3;
              }
            }
          }
          break;
        case 0:
        case 1:
        case 2:
        case 3:
        }
      SoloSettingsFragment.this.updateSoloSettingsAccess();
    }
  };

  static
  {
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED");
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED");
    filter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED");
  }

  private void updateSoloSettingsAccess()
  {
    boolean bool = isLinkConnected();
    enableSettingsPane(2131493269, bool);
    enableSettingsPane(2131493270, bool);
    enableSettingsPane(2131493273, bool);
    enableSettingsPane(2131493276, bool);
    enableSettingsPane(2131493263, bool);
    enableSettingsPane(2131493280, bool);
    enableSettingsPane(2131493281, bool);
  }

  protected SettingsDetailFragment getDetailFragmentForPaneId(int paramInt)
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
    case 2131493266:
      return new SoloControllerStyleFragment();
    case 2131493273:
      Bundle localBundle2 = new Bundle(1);
      localBundle2.putInt("extra_type_preset", 4);
      SoloPresetButtonFragment localSoloPresetButtonFragment2 = new SoloPresetButtonFragment();
      localSoloPresetButtonFragment2.setArguments(localBundle2);
      return localSoloPresetButtonFragment2;
    case 2131493276:
      Bundle localBundle1 = new Bundle(1);
      localBundle1.putInt("extra_type_preset", 5);
      SoloPresetButtonFragment localSoloPresetButtonFragment1 = new SoloPresetButtonFragment();
      localSoloPresetButtonFragment1.setArguments(localBundle1);
      return localSoloPresetButtonFragment1;
    case 2131493269:
      return new SoloPerformanceFragment();
    case 2131493263:
      return new SoloWifiFragment();
    case 2131493270:
      return new SoloAltitudeLimitFragment();
    case 2131493279:
      return new SoloArmLightsFragment();
    case 2131493280:
      return new SoloLevelCalibrationFragment();
    case 2131493281:
    }
    return new SoloCompassCalibrationFragment();
  }

  public int getSettingDetailTitle()
  {
    return 2131099919;
  }

  protected int getSubDetailFragmentId()
  {
    return 2131493160;
  }

  protected int getSubMasterFragmentId()
  {
    return 2131493159;
  }

  protected int getSubSlidePaneId()
  {
    return 2131493158;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903098, paramViewGroup, false);
  }

  protected void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    updateSoloSettingsAccess();
  }

  public void onStart()
  {
    super.onStart();
    getBroadcastManager().registerReceiver(this.receiver, filter);
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.solo.SoloSettingsFragment
 * JD-Core Version:    0.6.2
 */