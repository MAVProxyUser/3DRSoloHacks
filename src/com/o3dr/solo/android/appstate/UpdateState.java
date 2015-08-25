package com.o3dr.solo.android.appstate;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class UpdateState
{
  public static final String ACTION_SERVER_UPDATE_AVAILABLE = "com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE";
  public static final String ACTION_SERVER_UPDATE_DOWNLOAD_STARTED = "com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_STARTED";
  public static final String ACTION_SERVER_UP_TO_DATE = "com.o3dr.solo.android.action.SERVER_UP_TO_DATE";
  public static final String ACTION_VEHICLE_UPDATE_AVAILABLE = "com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE";
  public static final String ACTION_VEHICLE_UPDATE_STARTED = "com.o3dr.solo.android.action.VEHICLE_UPDATE_STARTED";
  public static final String ACTION_VEHICLE_UP_TO_DATE = "com.o3dr.solo.android.action.VEHICLE_UP_TO_DATE";
  public static final String ACTION_VEHICLE_WAIT_FOR_CONNECTION = "com.o3dr.solo.android.action.ACTION_VEHICLE_WAIT_FOR_CONNECTION";
  public static final String ACTION_VEHICLE_WAIT_FOR_DISCONNECTION = "com.o3dr.solo.androidaction.ACTION_VEHICLE_WAIT_FOR_DISCONNECTION";
  private static int NOTIFICATION_ID = 0;
  private static final String TAG = UpdateState.class.getSimpleName();
  private static final String UPDATE_BETA_CHANNEL_PATH = "?channel=Beta";
  private static final String UPDATE_INFO_URL_PATH = "products/";
  private static final String UPDATE_PRODUCTION_CHANNEL_PATH = "?channel=Production";
  private static final String UPDATE_SERVER_HOST = "https://firmwarehouse.3dr.com/";
  private static final String UPDATE_SERVER_HOST_DEV = "http://firmwarehouse-stage.3dr.com/";
  private final AtomicBoolean checkingForUpdate = new AtomicBoolean(false);
  private final AtomicBoolean isServerUpdateAvailable = new AtomicBoolean(false);
  private final AtomicBoolean isVehicleUpdateAvailable = new AtomicBoolean(false);
  private final AtomicBoolean isVehicleUpdating = new AtomicBoolean(false);
  private final LocalBroadcastManager lbm;
  private final ConcurrentHashMap<Long, String> serverUpdateDownloadIds = new ConcurrentHashMap();
  private final AtomicBoolean waitForConnection = new AtomicBoolean(false);
  private final AtomicBoolean waitForDisconnection = new AtomicBoolean(false);

  UpdateState(Context paramContext)
  {
    this.lbm = LocalBroadcastManager.getInstance(paramContext);
  }

  public void addServerUpdateDownloadId(long paramLong, String paramString)
  {
    if (paramLong != -1L)
    {
      if (paramString == null)
        paramString = "";
      if (this.serverUpdateDownloadIds.put(Long.valueOf(paramLong), paramString) == null)
        this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SERVER_UPDATE_DOWNLOAD_STARTED"));
    }
  }

  public void cancelVehicleUpdate()
  {
    this.isVehicleUpdating.set(false);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ACTION_UPDATE_TRANSFER_CANCELLED"));
  }

  public void clearServerUpdateDownloadIds()
  {
    this.serverUpdateDownloadIds.clear();
  }

  public Map<Long, String> getServerUpdateDownloadInfos()
  {
    return this.serverUpdateDownloadIds;
  }

  public String getUpdateInfoUrl()
  {
    return "https://firmwarehouse.3dr.com/products/?channel=Production";
  }

  public boolean isCheckingForUpdate()
  {
    return this.checkingForUpdate.get();
  }

  public boolean isGettingUpdatesFromServer()
  {
    return !this.serverUpdateDownloadIds.isEmpty();
  }

  public boolean isServerUpdateAvailable()
  {
    return this.isServerUpdateAvailable.get();
  }

  public boolean isVehicleUpdateAvailable()
  {
    return this.isVehicleUpdateAvailable.get();
  }

  public boolean isVehicleUpdating()
  {
    return this.isVehicleUpdating.get();
  }

  public boolean isWaitingForDisconnection()
  {
    return this.waitForDisconnection.get();
  }

  public boolean isWaitingForFinalConnection()
  {
    return this.waitForConnection.get();
  }

  public void removeServerUpdateDownloadId(long paramLong)
  {
    this.serverUpdateDownloadIds.remove(Long.valueOf(paramLong));
  }

  public void setCheckingForUpdate(boolean paramBoolean)
  {
    this.checkingForUpdate.set(paramBoolean);
  }

  public void setServerUpdateAvailable(boolean paramBoolean)
  {
    this.isServerUpdateAvailable.set(paramBoolean);
    if (paramBoolean)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SERVER_UPDATE_AVAILABLE"));
      return;
    }
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SERVER_UP_TO_DATE"));
  }

  public void setVehicleUpdateAvailable(boolean paramBoolean)
  {
    this.isVehicleUpdateAvailable.set(paramBoolean);
    if (paramBoolean)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_UPDATE_AVAILABLE"));
      return;
    }
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_UP_TO_DATE"));
  }

  public void setVehicleUpdating(boolean paramBoolean)
  {
    boolean bool = this.isVehicleUpdating.get();
    this.isVehicleUpdating.set(paramBoolean);
    if ((paramBoolean) && (!bool))
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.VEHICLE_UPDATE_STARTED"));
  }

  public void setWaitForDisconnection(boolean paramBoolean)
  {
    this.waitForDisconnection.set(paramBoolean);
    if (paramBoolean)
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.androidaction.ACTION_VEHICLE_WAIT_FOR_DISCONNECTION"));
  }

  public void setWaitForFinalConnection(boolean paramBoolean)
  {
    this.waitForConnection.set(paramBoolean);
    if (paramBoolean)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ACTION_VEHICLE_WAIT_FOR_CONNECTION"));
      setWaitForDisconnection(false);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.appstate.UpdateState
 * JD-Core Version:    0.6.2
 */