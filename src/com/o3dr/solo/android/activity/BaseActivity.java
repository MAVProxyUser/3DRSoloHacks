package com.o3dr.solo.android.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import com.crashlytics.android.Crashlytics;
import com.o3dr.android.client.Drone;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.service.AppService;
import com.o3dr.solo.android.service.AppService.AppManager;
import com.o3dr.solo.android.service.artoo.ArtooLinkManager;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;

public abstract class BaseActivity extends AppCompatActivity
  implements ServiceConnection
{
  protected AppService.AppManager appManager;
  protected LocalBroadcastManager lbm;
  protected SoloApp soloApp;

  private void enableScreenOn()
  {
    ((ViewGroup)findViewById(16908290)).getChildAt(0).setKeepScreenOn(true);
  }

  public ArtooLinkManager getArtooLinkManager()
  {
    if (this.appManager == null)
      return null;
    return this.appManager.getArtooLinkManager();
  }

  public Drone getDrone()
  {
    if (this.appManager != null)
      return this.appManager.getDrone();
    return null;
  }

  public SoloLinkManager getSoloLinkManager()
  {
    if (this.appManager == null)
      return null;
    return this.appManager.getSoloLinkManager();
  }

  public boolean isArtooLinkConnected()
  {
    return (this.appManager != null) && (this.appManager.isArtooLinkConnected());
  }

  public boolean isDroneConnected()
  {
    return (this.appManager != null) && (this.appManager.isDroneConnected());
  }

  public boolean isLinkConnected()
  {
    return (this.appManager != null) && (this.appManager.isLinkConnected());
  }

  public boolean isSoloLinkConnected()
  {
    return (this.appManager != null) && (this.appManager.isSoloLinkConnected());
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.soloApp = ((SoloApp)getApplication());
    Context localContext = getApplicationContext();
    Kit[] arrayOfKit = new Kit[1];
    arrayOfKit[0] = new Crashlytics();
    Fabric.with(localContext, arrayOfKit);
    this.lbm = LocalBroadcastManager.getInstance(localContext);
    bindService(new Intent(localContext, AppService.class), this, 1);
  }

  public void onDestroy()
  {
    super.onDestroy();
    unbindService(this);
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    this.appManager = ((AppService.AppManager)paramIBinder);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.DRONE_KIT_STATE").putExtra("extra_is_drone_kit_connected", true));
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.DRONE_KIT_STATE").putExtra("extra_is_drone_kit_connected", false));
    this.appManager = null;
  }

  public void onStart()
  {
    super.onStart();
    enableScreenOn();
  }

  public boolean playSound(int paramInt)
  {
    return (this.appManager != null) && (this.appManager.play(paramInt));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.activity.BaseActivity
 * JD-Core Version:    0.6.2
 */