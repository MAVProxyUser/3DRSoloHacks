package com.o3dr.solo.android.fragment.settings.general;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.appstate.VersionsInfo;
import com.o3dr.solo.android.fragment.settings.SettingsDetailFragment;
import com.o3dr.solo.android.service.update.UpdateService;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.widget.NiceProgressView;

public class SystemInfoFragment extends SettingsDetailFragment
{
  private static final String SYSTEM_INFO = "System Info";
  private static final String TAG = SystemInfoFragment.class.getSimpleName();
  private static final IntentFilter filter = new IntentFilter();
  private TextView artooVersion;
  private NiceProgressView artooVersionProgress;
  private String disconnectedLabel;
  private TextView firmwareServer;
  private TextView pixhawkVersion;
  private NiceProgressView pixhawkVersionProgress;
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
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        }
        break;
      case 662782932:
      case 551871472:
      case 787620458:
      case 1723986696:
      case 1828451976:
      case -1317834585:
      case -1284552981:
      case -2090241934:
      }
      do
      {
        return;
        if (!str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"))
          break;
        i = 0;
        break;
        if (!str.equals("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"))
          break;
        i = 1;
        break;
        if (!str.equals("com.o3dr.solo.android.action.CONTROLLER_VERSION_UPDATED"))
          break;
        i = 2;
        break;
        if (!str.equals("com.o3dr.solo.android.action.PIXHAWK_VERSION_UPDATED"))
          break;
        i = 3;
        break;
        if (!str.equals("com.o3dr.solo.android.action.VEHICLE_VERSION_UPDATED"))
          break;
        i = 4;
        break;
        if (!str.equals("com.o3dr.solo.android.action.STM32_VERSION_UPDATED"))
          break;
        i = 5;
        break;
        if (!str.equals("com.o3dr.solo.android.action.VERSIONS_REFRESH_ENDED"))
          break;
        i = 6;
        break;
        if (!str.equals("com.o3dr.solo.android.action.VERSIONS_REFRESH_STARTED"))
          break;
        i = 7;
        break;
        SystemInfoFragment.this.triggerVersionsRefresh();
        return;
        SystemInfoFragment.this.updateArtooVersion(false);
        return;
        SystemInfoFragment.this.updatePixhawkVersion(false);
        return;
        SystemInfoFragment.this.updateSoloLinkVersion(false);
        return;
        SystemInfoFragment.this.updateStm32Version(false);
        return;
        SystemInfoFragment.this.updateAllVersion();
        return;
      }
      while (SystemInfoFragment.this.soloApp.getVersionsInfo().areVersionsSet());
      SystemInfoFragment.this.setRefreshingFlag();
    }
  };
  private SoloApp soloApp;
  private TextView soloLinkVersion;
  private NiceProgressView soloLinkVersionProgress;
  private TextView stm32Version;
  private NiceProgressView stm32VersionProgress;

  static
  {
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED");
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED");
    filter.addAction("com.o3dr.solo.android.action.VERSIONS_REFRESH_STARTED");
    filter.addAction("com.o3dr.solo.android.action.VERSIONS_REFRESH_ENDED");
    filter.addAction("com.o3dr.solo.android.action.CONTROLLER_VERSION_UPDATED");
    filter.addAction("com.o3dr.solo.android.action.PIXHAWK_VERSION_UPDATED");
    filter.addAction("com.o3dr.solo.android.action.VEHICLE_VERSION_UPDATED");
    filter.addAction("com.o3dr.solo.android.action.STM32_VERSION_UPDATED");
  }

  private void setRefreshingFlag()
  {
    updateArtooVersion(true);
    updatePixhawkVersion(true);
    updateSoloLinkVersion(true);
    updateStm32Version(true);
  }

  private void triggerVersionsRefresh()
  {
    Context localContext = getContext();
    if (localContext == null);
    VersionsInfo localVersionsInfo;
    do
    {
      return;
      localVersionsInfo = this.soloApp.getVersionsInfo();
      if (!localVersionsInfo.areVersionsBeingRefreshed())
        break;
    }
    while (localVersionsInfo.areVersionsSet());
    setRefreshingFlag();
    return;
    localContext.startService(new Intent(localContext, UpdateService.class).setAction("com.o3dr.solo.android.action.UPDATE_COMPONENTS_VERSION"));
  }

  private void updateAllVersion()
  {
    updateArtooVersion(false);
    updatePixhawkVersion(false);
    updateSoloLinkVersion(false);
    updateStm32Version(false);
    updateFirmwareServer();
  }

  private void updateArtooVersion(boolean paramBoolean)
  {
    int i = 8;
    int j = 1;
    String str = this.soloApp.getVersionsInfo().getControllerVersion();
    if (TextUtils.isEmpty(str))
    {
      j = 0;
      str = this.disconnectedLabel;
    }
    int k;
    int m;
    label58: TextView localTextView;
    if ((j == 0) && (paramBoolean))
    {
      k = 1;
      NiceProgressView localNiceProgressView = this.artooVersionProgress;
      if (k == 0)
        break label98;
      m = 0;
      localNiceProgressView.setVisibility(m);
      this.artooVersion.setText(str);
      localTextView = this.artooVersion;
      if (k == 0)
        break label104;
    }
    while (true)
    {
      localTextView.setVisibility(i);
      return;
      k = 0;
      break;
      label98: m = i;
      break label58;
      label104: i = 0;
    }
  }

  private void updateFirmwareServer()
  {
    this.firmwareServer.setText(this.soloApp.getUpdateState().getUpdateInfoUrl());
  }

  private void updatePixhawkVersion(boolean paramBoolean)
  {
    int i = 8;
    int j = 1;
    String str = this.soloApp.getVersionsInfo().getPixhawkVersion();
    if (TextUtils.isEmpty(str))
    {
      j = 0;
      str = this.disconnectedLabel;
    }
    int k;
    int m;
    label58: TextView localTextView;
    if ((j == 0) && (paramBoolean))
    {
      k = 1;
      NiceProgressView localNiceProgressView = this.pixhawkVersionProgress;
      if (k == 0)
        break label98;
      m = 0;
      localNiceProgressView.setVisibility(m);
      this.pixhawkVersion.setText(str);
      localTextView = this.pixhawkVersion;
      if (k == 0)
        break label104;
    }
    while (true)
    {
      localTextView.setVisibility(i);
      return;
      k = 0;
      break;
      label98: m = i;
      break label58;
      label104: i = 0;
    }
  }

  private void updateSoloLinkVersion(boolean paramBoolean)
  {
    int i = 8;
    int j = 1;
    AppAnalytics localAppAnalytics = this.soloApp.getAppAnalytics();
    String str = this.soloApp.getVersionsInfo().getVehicleVersion();
    if (TextUtils.isEmpty(str))
    {
      j = 0;
      str = this.disconnectedLabel;
    }
    if (!paramBoolean)
      localAppAnalytics.track("Settings", "System Info", str);
    int k;
    int m;
    label82: TextView localTextView;
    if ((j == 0) && (paramBoolean))
    {
      k = 1;
      NiceProgressView localNiceProgressView = this.soloLinkVersionProgress;
      if (k == 0)
        break label122;
      m = 0;
      localNiceProgressView.setVisibility(m);
      this.soloLinkVersion.setText(str);
      localTextView = this.soloLinkVersion;
      if (k == 0)
        break label128;
    }
    while (true)
    {
      localTextView.setVisibility(i);
      return;
      k = 0;
      break;
      label122: m = i;
      break label82;
      label128: i = 0;
    }
  }

  private void updateStm32Version(boolean paramBoolean)
  {
    int i = 8;
    int j = 1;
    String str = this.soloApp.getVersionsInfo().getStm32Version();
    if (TextUtils.isEmpty(str))
    {
      j = 0;
      str = this.disconnectedLabel;
    }
    int k;
    int m;
    label58: TextView localTextView;
    if ((j == 0) && (paramBoolean))
    {
      k = 1;
      NiceProgressView localNiceProgressView = this.stm32VersionProgress;
      if (k == 0)
        break label98;
      m = 0;
      localNiceProgressView.setVisibility(m);
      this.stm32Version.setText(str);
      localTextView = this.stm32Version;
      if (k == 0)
        break label104;
    }
    while (true)
    {
      localTextView.setVisibility(i);
      return;
      k = 0;
      break;
      label98: m = i;
      break label58;
      label104: i = 0;
    }
  }

  public int getSettingDetailTitle()
  {
    return 2131099920;
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903099, paramViewGroup, false);
  }

  public void onStart()
  {
    super.onStart();
    updateAllVersion();
    getBroadcastManager().registerReceiver(this.receiver, filter);
    triggerVersionsRefresh();
  }

  public void onStop()
  {
    super.onStop();
    getBroadcastManager().unregisterReceiver(this.receiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.disconnectedLabel = getString(2131099877);
    this.soloApp = getApplication();
    Context localContext = getContext();
    try
    {
      ((TextView)paramView.findViewById(2131493162)).setText(localContext.getPackageManager().getPackageInfo(localContext.getPackageName(), 0).versionName);
      this.soloLinkVersion = ((TextView)paramView.findViewById(2131493165));
      this.soloLinkVersionProgress = ((NiceProgressView)paramView.findViewById(2131493164));
      this.artooVersion = ((TextView)paramView.findViewById(2131493171));
      this.artooVersionProgress = ((NiceProgressView)paramView.findViewById(2131493170));
      this.pixhawkVersion = ((TextView)paramView.findViewById(2131493168));
      this.pixhawkVersionProgress = ((NiceProgressView)paramView.findViewById(2131493167));
      this.stm32Version = ((TextView)paramView.findViewById(2131493174));
      this.stm32VersionProgress = ((NiceProgressView)paramView.findViewById(2131493173));
      this.firmwareServer = ((TextView)paramView.findViewById(2131493177));
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        Log.e(TAG, "Unable to retrieve the version name.", localNameNotFoundException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.settings.general.SystemInfoFragment
 * JD-Core Version:    0.6.2
 */