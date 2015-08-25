package com.o3dr.solo.android.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.update.UpdateService;
import com.o3dr.solo.android.util.AppPreferences;
import com.o3dr.solo.android.util.NetworkUtils;
import java.lang.ref.SoftReference;

public class AppService extends Service
  implements WifiConnectionHandler.WifiConnectionListener
{
  public static final String ACTION_ARTOO_LINK_CONNECTED = "com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED";
  public static final String ACTION_ARTOO_LINK_DISCONNECTED = "com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED";
  public static final String ACTION_CONNECT_TO_WIFI = "com.o3dr.solo.android.action.CONNECT_TO_WIFI";
  public static final String ACTION_ENABLE_DRONE_CONNECTION = "com.o3dr.solo.android.action.ENABLE_DRONE_CONNECTION";
  public static final String ACTION_VEHICLE_LINK_CONNECTED = "com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED";
  public static final String ACTION_VEHICLE_LINK_DISCONNECTED = "com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED";
  public static final String EXTRA_WIFI_SCAN_RESULT = "extra_wifi_scan_result";
  private static final String TAG = AppService.class.getSimpleName();
  private AppManager appManager;
  private AppPreferences appPrefs;
  private ArtooLinkManager artooLinkMgr;
  private DroneKitManager droneKitMgr;
  private final Handler handler = new Handler();
  private LocalBroadcastManager lbm;
  private final AbstractLinkManager.LinkListener linkListener = new AbstractLinkManager.LinkListener()
  {
    public void onLinkConnected()
    {
      Context localContext = AppService.this.getApplicationContext();
      if (AppService.this.isSoloConnected())
        AppService.this.artooLinkMgr.startVideoManager();
      if (AppService.this.isLinkConnected())
      {
        Log.d(AppService.TAG, "Link established!");
        AppService.this.appPrefs.setUserVehicleLink(NetworkUtils.getCurrentWifiLink(localContext));
        AppService.this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_LINK_CONNECTED"));
      }
      do
      {
        return;
        if (!AppService.this.artooLinkMgr.isLinkConnected())
          AppService.this.artooLinkMgr.start(this);
        if (!AppService.this.droneKitMgr.isDroneConnected())
          AppService.this.droneKitMgr.start(this);
      }
      while (AppService.this.soloLinkMgr.isLinkConnected());
      AppService.this.soloLinkMgr.start(this);
    }

    public void onLinkDisconnected()
    {
      if (!AppService.this.isLinkConnected())
      {
        Log.d(AppService.TAG, "Link broken!");
        AppService.this.appPrefs.setUserVehicleLink(null);
        AppService.this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_LINK_DISCONNECTED"));
        AppService.this.artooLinkMgr.stopVideoManager();
        if (!AppService.this.droneKitMgr.isDroneConnected())
          AppService.this.soloLinkMgr.stop();
      }
    }
  };
  private SoloLinkManager soloLinkMgr;
  private SoundManager soundManager;
  private WifiConnectionHandler wifiConnHandler;

  private boolean isSoloConnected()
  {
    return (this.artooLinkMgr.isLinkConnected()) && (this.soloLinkMgr.isLinkConnected());
  }

  private void startTasks()
  {
    if (!NetworkUtils.isOnSololinkNetwork(getApplicationContext()))
      return;
    this.droneKitMgr.start(this.linkListener);
    this.artooLinkMgr.start(this.linkListener);
    this.soloLinkMgr.start(this.linkListener);
  }

  private void stopTasks()
  {
    this.artooLinkMgr.stop();
    this.droneKitMgr.stop();
    this.soloLinkMgr.stop();
  }

  public boolean isLinkConnected()
  {
    return (this.artooLinkMgr.isLinkConnected()) && (this.droneKitMgr.isDroneConnected()) && (this.soloLinkMgr.isLinkConnected());
  }

  public IBinder onBind(Intent paramIntent)
  {
    return this.appManager;
  }

  public void onCreate()
  {
    super.onCreate();
    Context localContext = getApplicationContext();
    this.lbm = LocalBroadcastManager.getInstance(localContext);
    this.appManager = new AppManager(this);
    this.appPrefs = new AppPreferences(localContext);
    this.soundManager = new SoundManager(localContext);
    this.soundManager.start();
    this.wifiConnHandler = new WifiConnectionHandler((SoloApp)getApplication(), this.handler);
    this.wifiConnHandler.setWifiConnectionListener(this);
    this.wifiConnHandler.start();
    this.artooLinkMgr = new ArtooLinkManager(localContext);
    this.droneKitMgr = new DroneKitManager(localContext);
    this.soloLinkMgr = new SoloLinkManager(localContext, this.droneKitMgr);
    startService(new Intent(localContext, UpdateService.class).setAction("com.o3dr.solo.android.action.CHECK_FOR_UPDATE"));
    startTasks();
  }

  public void onDestroy()
  {
    super.onDestroy();
    stopTasks();
    this.wifiConnHandler.stop();
    this.soundManager.stop();
    stopService(new Intent(getApplicationContext(), UpdateService.class));
  }

  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    String str;
    int i;
    if (paramIntent != null)
    {
      str = paramIntent.getAction();
      i = -1;
      switch (str.hashCode())
      {
      default:
        switch (i)
        {
        default:
        case 0:
        case 1:
        }
        break;
      case -1785710744:
      case -1489691509:
      }
    }
    while (true)
    {
      stopSelf(paramInt2);
      return 2;
      if (!str.equals("com.o3dr.solo.android.action.ENABLE_DRONE_CONNECTION"))
        break;
      i = 0;
      break;
      if (!str.equals("com.o3dr.solo.android.action.CONNECT_TO_WIFI"))
        break;
      i = 1;
      break;
      Log.d(TAG, "Received drone connection request.");
      if (!this.droneKitMgr.isTowerConnected())
        this.droneKitMgr.start(this.linkListener);
      this.wifiConnHandler.connectToVehicle(this.artooLinkMgr.isLinkConnected());
      continue;
      ScanResult localScanResult = (ScanResult)paramIntent.getParcelableExtra("extra_wifi_scan_result");
      this.wifiConnHandler.connectToWifi(localScanResult);
    }
  }

  public void onWifiConnected()
  {
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED"));
    startTasks();
  }

  public void onWifiDisconnected()
  {
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED"));
    stopTasks();
  }

  public static class AppManager extends Binder
  {
    private final SoftReference<AppService> serviceRef;

    AppManager(AppService paramAppService)
    {
      this.serviceRef = new SoftReference(paramAppService);
    }

    public ArtooLinkManager getArtooLinkManager()
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      if (localAppService == null)
        return null;
      return localAppService.artooLinkMgr;
    }

    public Drone getDrone()
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      if (localAppService == null)
        return null;
      return localAppService.droneKitMgr.getDrone();
    }

    public SoloLinkManager getSoloLinkManager()
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      if (localAppService == null)
        return null;
      return localAppService.soloLinkMgr;
    }

    public boolean isArtooLinkConnected()
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      return (localAppService != null) && (localAppService.artooLinkMgr.isLinkConnected());
    }

    public boolean isDroneConnected()
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      return (localAppService != null) && (localAppService.droneKitMgr.isDroneConnected());
    }

    public boolean isLinkConnected()
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      return (localAppService != null) && (localAppService.isLinkConnected());
    }

    public boolean isSoloLinkConnected()
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      return (localAppService != null) && (localAppService.soloLinkMgr.isLinkConnected());
    }

    public boolean play(int paramInt)
    {
      AppService localAppService = (AppService)this.serviceRef.get();
      return (localAppService != null) && (localAppService.soundManager.play(paramInt));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.AppService
 * JD-Core Version:    0.6.2
 */