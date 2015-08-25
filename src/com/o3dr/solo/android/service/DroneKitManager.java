package com.o3dr.solo.android.service;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.o3dr.android.client.ControlTower;
import com.o3dr.android.client.Drone;
import com.o3dr.android.client.interfaces.DroneListener;
import com.o3dr.android.client.interfaces.TowerListener;
import com.o3dr.services.android.lib.drone.connection.ConnectionParameter;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.solo.android.util.AppPreferences;
import java.util.concurrent.atomic.AtomicBoolean;

public class DroneKitManager
  implements DroneListener, TowerListener
{
  public static final String ACTION_DRONE_KIT_STATE = "com.o3dr.solo.android.action.DRONE_KIT_STATE";
  public static final String EXTRA_IS_DRONE_KIT_CONNECTED = "extra_is_drone_kit_connected";
  public static final int MAVLINK_UDP_PORT = 14550;
  private static final String TAG = DroneKitManager.class.getSimpleName();
  private final AppPreferences appPrefs;
  private final ControlTower controlTower;
  private final Drone drone;
  private ConnectionParameter droneConnParams;
  private final Handler handler = new Handler();
  private final AtomicBoolean isStarted = new AtomicBoolean(false);
  private final LocalBroadcastManager lbm;
  private AbstractLinkManager.LinkListener linkListener;

  DroneKitManager(Context paramContext)
  {
    loadDroneConnectionParameter();
    this.appPrefs = new AppPreferences(paramContext);
    this.lbm = LocalBroadcastManager.getInstance(paramContext);
    this.drone = new Drone(paramContext);
    this.controlTower = new ControlTower(paramContext);
    this.controlTower.connect(this);
  }

  private void loadDroneConnectionParameter()
  {
    if (this.droneConnParams == null)
    {
      Bundle localBundle = new Bundle(1);
      localBundle.putInt("extra_udp_server_port", 14550);
      this.droneConnParams = new ConnectionParameter(1, localBundle, null);
    }
  }

  Drone getDrone()
  {
    return this.drone;
  }

  public boolean isDroneConnected()
  {
    State localState = (State)this.drone.getAttribute("com.o3dr.services.android.lib.attribute.STATE");
    return (localState != null) && (localState.isTelemetryLive());
  }

  public boolean isTowerConnected()
  {
    return this.controlTower.isTowerConnected();
  }

  public void onDroneConnectionFailed(ConnectionResult paramConnectionResult)
  {
  }

  public void onDroneEvent(String paramString, Bundle paramBundle)
  {
    Intent localIntent = new Intent(paramString);
    if (paramBundle != null)
      localIntent.putExtras(paramBundle);
    this.lbm.sendBroadcast(localIntent);
    int i = -1;
    switch (paramString.hashCode())
    {
    default:
      switch (i)
      {
      default:
      case 0:
      case 1:
      case 2:
      case 3:
      }
      break;
    case 1256617868:
    case -1116774648:
    case 1962523320:
    case 600585103:
    }
    do
    {
      do
      {
        return;
        if (!paramString.equals("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED"))
          break;
        i = 0;
        break;
        if (!paramString.equals("com.o3dr.services.android.lib.attribute.event.HEARTBEAT_RESTORED"))
          break;
        i = 1;
        break;
        if (!paramString.equals("com.o3dr.services.android.lib.attribute.event.STATE_DISCONNECTED"))
          break;
        i = 2;
        break;
        if (!paramString.equals("com.o3dr.services.android.lib.attribute.event.HEARTBEAT_TIMEOUT"))
          break;
        i = 3;
        break;
        Log.d(TAG, paramString);
      }
      while (this.linkListener == null);
      this.linkListener.onLinkConnected();
      return;
      Log.d(TAG, paramString);
    }
    while (this.linkListener == null);
    this.linkListener.onLinkDisconnected();
  }

  public void onDroneServiceInterrupted(String paramString)
  {
    this.controlTower.unregisterDrone(this.drone);
    if (!TextUtils.isEmpty(paramString))
      Log.e(TAG, paramString);
  }

  public void onTowerConnected()
  {
    Log.d(TAG, "Connected to 3DR Services.");
    if (!this.drone.isStarted())
    {
      this.controlTower.registerDrone(this.drone, this.handler);
      this.drone.registerDroneListener(this);
    }
    if (this.isStarted.get())
      start(this.linkListener);
  }

  public void onTowerDisconnected()
  {
    Log.d(TAG, "Lost connection to 3DR Services.");
    this.controlTower.unregisterDrone(this.drone);
  }

  void start(AbstractLinkManager.LinkListener paramLinkListener)
  {
    this.linkListener = paramLinkListener;
    this.isStarted.set(true);
    if (!this.controlTower.isTowerConnected())
    {
      Log.d(TAG, "Connecting to the control tower");
      this.controlTower.connect(this);
    }
    do
    {
      return;
      if (!this.drone.isConnected())
      {
        Log.d(TAG, "Connecting drone instance");
        this.drone.connect(this.droneConnParams);
        return;
      }
    }
    while ((!isDroneConnected()) || (paramLinkListener == null));
    paramLinkListener.onLinkConnected();
  }

  public void stop()
  {
    this.isStarted.set(false);
    if (this.drone.isConnected())
    {
      Log.d(TAG, "Disconnecting drone instance");
      this.drone.disconnect();
    }
    Log.d(TAG, "Disconnecting from the control tower.");
    this.controlTower.unregisterDrone(this.drone);
    if (this.controlTower.isTowerConnected())
      this.controlTower.disconnect();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.DroneKitManager
 * JD-Core Version:    0.6.2
 */