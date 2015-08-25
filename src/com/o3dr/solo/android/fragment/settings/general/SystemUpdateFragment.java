package com.o3dr.solo.android.fragment.settings.general;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;

public class SystemUpdateFragment extends SettingsDetailFragment
{
  private static final IntentFilter intentFilter = new IntentFilter();
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str.hashCode())
      {
      default:
      case 822378966:
      case -1038879041:
      case -1285212681:
      case -967688227:
      case -846676997:
      }
      while (true)
        switch (i)
        {
        default:
          return;
          if (str.equals("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE"))
          {
            i = 0;
            continue;
            if (str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE"))
            {
              i = 1;
              continue;
              if (str.equals("com.o3dr.solo.android.action.VEHICLE_UPDATE_STARTED"))
              {
                i = 2;
                continue;
                if (str.equals("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_STARTED"))
                {
                  i = 3;
                  continue;
                  if (str.equals("com.o3dr.solo.android.action.ACTION_VEHICLE_WAIT_FOR_CONNECTION"))
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
      SystemUpdateFragment.this.updateScreen();
    }
  };
  private LocalBroadcastManager lbm;
  private SoloApp soloApp;

  static
  {
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE");
    intentFilter.addAction("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_STARTED");
    intentFilter.addAction("com.o3dr.solo.android.action.VEHICLE_UPDATE_STARTED");
    intentFilter.addAction("com.o3dr.solo.android.action.ACTION_VEHICLE_WAIT_FOR_CONNECTION");
  }

  private void updateScreen()
  {
    if (getActivity() == null);
    while (true)
    {
      return;
      FragmentManager localFragmentManager = getChildFragmentManager();
      Object localObject = localFragmentManager.findFragmentById(2131493178);
      int i;
      if (this.soloApp == null)
      {
        boolean bool4 = localObject instanceof TransferVehicleUpdateFragment;
        i = 0;
        if (!bool4)
        {
          localObject = new TransferVehicleUpdateFragment();
          i = 1;
        }
      }
      while (i != 0)
      {
        localFragmentManager.beginTransaction().add(2131493178, (Fragment)localObject).commitAllowingStateLoss();
        return;
        UpdateState localUpdateState = this.soloApp.getUpdateState();
        if (localUpdateState.isVehicleUpdating())
        {
          boolean bool3 = localObject instanceof TransferVehicleUpdateFragment;
          i = 0;
          if (!bool3)
          {
            localObject = new TransferVehicleUpdateFragment();
            i = 1;
          }
        }
        else if ((localUpdateState.isServerUpdateAvailable()) || (localUpdateState.isGettingUpdatesFromServer()))
        {
          boolean bool1 = localObject instanceof DownloadVehicleUpdateFragment;
          i = 0;
          if (!bool1)
          {
            localObject = new DownloadVehicleUpdateFragment();
            i = 1;
          }
        }
        else
        {
          boolean bool2 = localObject instanceof TransferVehicleUpdateFragment;
          i = 0;
          if (!bool2)
          {
            localObject = new TransferVehicleUpdateFragment();
            i = 1;
          }
        }
      }
    }
  }

  public int getSettingDetailTitle()
  {
    return 2131099918;
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance(true);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903100, paramViewGroup, false);
  }

  public void onStart()
  {
    super.onStart();
    updateScreen();
    this.lbm.registerReceiver(this.broadcastReceiver, intentFilter);
  }

  public void onStop()
  {
    super.onStop();
    this.lbm.unregisterReceiver(this.broadcastReceiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.lbm = LocalBroadcastManager.getInstance(getContext());
    this.soloApp = getApplication();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.general.SystemUpdateFragment
 * JD-Core Version:    0.6.2
 */