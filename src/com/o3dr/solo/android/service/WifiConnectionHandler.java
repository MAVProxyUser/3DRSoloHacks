package com.o3dr.solo.android.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;
import com.o3dr.solo.android.activity.FlightActivity;
import com.o3dr.solo.android.activity.ReturnToSystemUpdate;
import com.o3dr.solo.android.activity.WifiConnectionProgress;
import com.o3dr.solo.android.activity.WifiSelector;
import com.o3dr.solo.android.activity.WifiSettingsAccess;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.appstate.UpdateState;
import com.o3dr.solo.android.service.update.UpdateService;
import com.o3dr.solo.android.util.NetworkUtils;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class WifiConnectionHandler
{
  public static final String ACTION_STARTING_VEHICLE_CONNECTION = "com.o3dr.solo.android.action.STARTING_VEHICLE_CONNECTION";
  public static final String ACTION_VEHICLE_CONNECTION_COMPLETED = "com.o3dr.solo.android.action.VEHICLE_CONNECTION_COMPLETED";
  private static final long CHECK_FOR_UPDATE_DELAY = 1000L;
  public static final String EXTRA_AVAILABLE_SOLO_LINKS = "extra_available_solo_links";
  public static final String EXTRA_SELECTED_SOLO_LINK = "extra_selected_solo_link";
  private static final String TAG = WifiConnectionHandler.class.getSimpleName();
  private static final String[] WIFI_SECURITY_MODES = { "WEP", "PSK", "EAP" };
  private static final IntentFilter intentFilter = new IntentFilter();
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      String str1 = paramAnonymousIntent.getAction();
      int i = -1;
      switch (str1.hashCode())
      {
      default:
        switch (i)
        {
        default:
        case 0:
        case 1:
        }
        break;
      case 1878357501:
      case -343630553:
      case -1875733435:
      }
      label72: 
      do
      {
        do
        {
          return;
          if (!str1.equals("android.net.wifi.SCAN_RESULTS"))
            break;
          i = 0;
          break;
          if (!str1.equals("android.net.wifi.STATE_CHANGE"))
            break;
          i = 1;
          break;
          if (!str1.equals("android.net.wifi.WIFI_STATE_CHANGED"))
            break;
          i = 2;
          break;
        }
        while (!WifiConnectionHandler.this.isScanning.compareAndSet(true, false));
        WifiConnectionHandler.this.showVehicleLinksSelector();
        return;
        NetworkInfo localNetworkInfo = (NetworkInfo)paramAnonymousIntent.getParcelableExtra("networkInfo");
        if (localNetworkInfo == null);
        for (NetworkInfo.State localState = NetworkInfo.State.DISCONNECTED; ; localState = localNetworkInfo.getState())
          switch (WifiConnectionHandler.3.$SwitchMap$android$net$NetworkInfo$State[localState.ordinal()])
          {
          default:
            return;
          case 1:
            String str2 = ((WifiInfo)paramAnonymousIntent.getParcelableExtra("wifiInfo")).getSSID();
            Log.d(WifiConnectionHandler.TAG, "Connected to " + str2);
            WifiConnectionHandler.this.handler.postDelayed(WifiConnectionHandler.this.checkForUpdateTask, 1000L);
            if ((WifiConnectionHandler.this.wifiConnectionListener == null) || (!NetworkUtils.isOnSololinkNetwork(paramAnonymousContext)))
              break label72;
            WifiConnectionHandler.this.wifiConnectionListener.onWifiConnected();
            return;
          case 2:
          case 3:
          }
        Log.d(WifiConnectionHandler.TAG, "Disconnected from wifi network.");
      }
      while (WifiConnectionHandler.this.wifiConnectionListener == null);
      WifiConnectionHandler.this.wifiConnectionListener.onWifiDisconnected();
      return;
      Log.d(WifiConnectionHandler.TAG, "Connecting to wifi network.");
    }
  };
  private final Runnable checkForUpdateTask = new Runnable()
  {
    public void run()
    {
      WifiConnectionHandler.this.handler.removeCallbacks(this);
      WifiConnectionHandler.this.context.startService(new Intent(WifiConnectionHandler.this.context, UpdateService.class).setAction("com.o3dr.solo.android.action.CHECK_FOR_UPDATE"));
      Log.i(WifiConnectionHandler.TAG, "Trigger check for update action.");
    }
  };
  private final Context context;
  private final Handler handler;
  private final AtomicBoolean isScanning = new AtomicBoolean(false);
  private final LocalBroadcastManager lbm;
  private final SoloApp soloApp;
  private String targetSSID;
  private WifiConnectionListener wifiConnectionListener;
  private final WifiManager wifiMgr;

  static
  {
    intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
    intentFilter.addAction("android.net.wifi.STATE_CHANGE");
    intentFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
  }

  public WifiConnectionHandler(SoloApp paramSoloApp, Handler paramHandler)
  {
    this.handler = paramHandler;
    this.soloApp = paramSoloApp;
    this.context = paramSoloApp.getApplicationContext();
    this.lbm = LocalBroadcastManager.getInstance(this.context);
    this.wifiMgr = ((WifiManager)this.context.getSystemService("wifi"));
  }

  private boolean connectToClosedWifi(ScanResult paramScanResult)
  {
    WifiConfiguration localWifiConfiguration = new WifiConfiguration();
    localWifiConfiguration.SSID = ("\"" + paramScanResult.SSID + "\"");
    localWifiConfiguration.preSharedKey = "\"sololink\"";
    localWifiConfiguration.allowedGroupCiphers.set(2);
    localWifiConfiguration.allowedGroupCiphers.set(3);
    localWifiConfiguration.allowedGroupCiphers.set(0);
    localWifiConfiguration.allowedGroupCiphers.set(1);
    localWifiConfiguration.allowedKeyManagement.set(1);
    localWifiConfiguration.allowedPairwiseCiphers.set(1);
    localWifiConfiguration.allowedPairwiseCiphers.set(2);
    localWifiConfiguration.allowedProtocols.set(0);
    localWifiConfiguration.allowedProtocols.set(1);
    if (this.wifiMgr.addNetwork(localWifiConfiguration) == -1)
    {
      Toast.makeText(this.context, "Unable to connect to Wi-Fi " + paramScanResult.SSID, 1).show();
      Log.e(TAG, "Unable to add wifi configuration for " + paramScanResult.SSID);
      return false;
    }
    return true;
  }

  private boolean connectToOpenWifi(ScanResult paramScanResult)
  {
    WifiConfiguration localWifiConfiguration = new WifiConfiguration();
    localWifiConfiguration.SSID = ("\"" + paramScanResult.SSID + "\"");
    localWifiConfiguration.allowedKeyManagement.set(0);
    if (this.wifiMgr.addNetwork(localWifiConfiguration) == -1)
    {
      Toast.makeText(this.context, "Unable to connect to Wi-Fi " + paramScanResult.SSID, 1).show();
      Log.e(TAG, "Unable to add wifi configuration for " + paramScanResult.SSID);
      return false;
    }
    return true;
  }

  private WifiConfiguration getWifiConfigs(String paramString)
  {
    Iterator localIterator = this.wifiMgr.getConfiguredNetworks().iterator();
    while (localIterator.hasNext())
    {
      WifiConfiguration localWifiConfiguration = (WifiConfiguration)localIterator.next();
      if ((localWifiConfiguration.SSID != null) && (localWifiConfiguration.SSID.equals("\"" + paramString + "\"")))
        return localWifiConfiguration;
    }
    return null;
  }

  private boolean isWifiOpen(ScanResult paramScanResult)
  {
    for (String str : WIFI_SECURITY_MODES)
      if (paramScanResult.capabilities.contains(str))
        return false;
    return true;
  }

  private void showVehicleLinksSelector()
  {
    List localList = this.wifiMgr.getScanResults();
    HashMap localHashMap = new HashMap();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
    {
      ScanResult localScanResult = (ScanResult)localIterator.next();
      if (localScanResult.SSID.startsWith("SoloLink_"))
        localHashMap.put(localScanResult.SSID, localScanResult);
    }
    int i = localHashMap.size();
    if (i == 0)
    {
      Toast.makeText(this.context, "No solo vehicle detected!", 1).show();
      return;
    }
    ArrayList localArrayList = new ArrayList(localHashMap.values());
    if (i == 1)
    {
      connectToWifi((ScanResult)localArrayList.get(0));
      return;
    }
    WifiInfo localWifiInfo = this.wifiMgr.getConnectionInfo();
    if (localWifiInfo == null);
    for (String str = null; ; str = localWifiInfo.getSSID().replace("\"", ""))
    {
      this.context.startActivity(new Intent(this.context, WifiSelector.class).addFlags(268435456).putExtra("extra_available_solo_links", localArrayList).putExtra("extra_selected_solo_link", str));
      return;
    }
  }

  public void connectToVehicle(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.context.startActivity(new Intent(this.context, FlightActivity.class).addFlags(268435456));
      return;
    }
    Log.d(TAG, "Prompting user to select vehicle.");
    if (this.soloApp.getUpdateState().isGettingUpdatesFromServer())
    {
      this.context.startActivity(new Intent(this.context, ReturnToSystemUpdate.class).addFlags(268435456));
      return;
    }
    this.context.startActivity(new Intent(this.context, WifiSettingsAccess.class).addFlags(268435456));
  }

  public boolean connectToWifi(ScanResult paramScanResult)
  {
    if (paramScanResult == null);
    do
    {
      return false;
      Log.d(TAG, "Connecting to wifi " + paramScanResult.SSID);
      WifiInfo localWifiInfo = this.wifiMgr.getConnectionInfo();
      if (localWifiInfo.getSSID().equals("\"" + paramScanResult.SSID + "\""))
      {
        Log.d(TAG, "Already connected to " + localWifiInfo.getSSID());
        this.targetSSID = ("\"" + paramScanResult.SSID + "\"");
        if (this.wifiConnectionListener != null)
          this.wifiConnectionListener.onWifiConnected();
        return true;
      }
      if (!isWifiOpen(paramScanResult))
        break;
      Log.d(TAG, "Connecting to open wifi network.");
    }
    while (!connectToOpenWifi(paramScanResult));
    do
    {
      this.wifiMgr.saveConfiguration();
      WifiConfiguration localWifiConfiguration = getWifiConfigs(paramScanResult.SSID);
      if (localWifiConfiguration == null)
        break;
      this.targetSSID = ("\"" + paramScanResult.SSID + "\"");
      this.context.startActivity(new Intent(this.context, WifiConnectionProgress.class).addFlags(268435456).setAction("com.o3dr.solo.android.action.STARTING_VEHICLE_CONNECTION").putExtra("extra_selected_solo_link", this.targetSSID));
      this.wifiMgr.enableNetwork(localWifiConfiguration.networkId, true);
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.STARTING_VEHICLE_CONNECTION"));
      return true;
      Log.d(TAG, "Connecting to closed wifi network.");
    }
    while (connectToClosedWifi(paramScanResult));
    return false;
  }

  public void refreshWifiAPs()
  {
    Log.d(TAG, "Querying wifi access points.");
    if (this.wifiMgr == null);
    do
    {
      return;
      if ((!this.wifiMgr.isWifiEnabled()) && (!this.wifiMgr.setWifiEnabled(true)))
      {
        Toast.makeText(this.context, "Unable to activate Wi-Fi!", 1).show();
        return;
      }
      showVehicleLinksSelector();
      this.isScanning.set(this.wifiMgr.startScan());
    }
    while (this.isScanning.get());
    Toast.makeText(this.context, "Unable to scan for Wi-Fi networks!", 1).show();
  }

  public void setWifiConnectionListener(WifiConnectionListener paramWifiConnectionListener)
  {
    this.wifiConnectionListener = paramWifiConnectionListener;
  }

  public void start()
  {
    this.context.registerReceiver(this.broadcastReceiver, intentFilter);
  }

  public void stop()
  {
    try
    {
      this.context.unregisterReceiver(this.broadcastReceiver);
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      Log.d(TAG, "Receiver was not registered.", localIllegalArgumentException);
    }
  }

  public static abstract interface WifiConnectionListener
  {
    public abstract void onWifiConnected();

    public abstract void onWifiDisconnected();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.WifiConnectionHandler
 * JD-Core Version:    0.6.2
 */