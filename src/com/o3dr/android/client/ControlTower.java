package com.o3dr.android.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import android.util.Log;
import com.o3dr.android.client.interfaces.TowerListener;
import com.o3dr.android.client.utils.InstallServiceDialog;
import com.o3dr.android.client.utils.UpdateServiceDialog;
import com.o3dr.services.android.lib.drone.connection.ConnectionParameter;
import com.o3dr.services.android.lib.model.IDroidPlannerServices;
import com.o3dr.services.android.lib.model.IDroidPlannerServices.Stub;
import java.util.concurrent.atomic.AtomicBoolean;

public class ControlTower
{
  private static final String TAG = ControlTower.class.getSimpleName();
  private final IBinder.DeathRecipient binderDeathRecipient = new IBinder.DeathRecipient()
  {
    public void binderDied()
    {
      ControlTower.this.notifyTowerDisconnected();
    }
  };
  private final Context context;
  private final AtomicBoolean isServiceConnecting = new AtomicBoolean(false);
  private IDroidPlannerServices o3drServices;
  private final ServiceConnection o3drServicesConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      ControlTower.this.isServiceConnecting.set(false);
      ControlTower.access$102(ControlTower.this, IDroidPlannerServices.Stub.asInterface(paramAnonymousIBinder));
      try
      {
        if (ControlTower.this.o3drServices.getApiVersionCode() < 20214)
        {
          ControlTower.access$102(ControlTower.this, null);
          ControlTower.this.promptFor3DRServicesUpdate();
          ControlTower.this.context.unbindService(ControlTower.this.o3drServicesConnection);
          return;
        }
        ControlTower.this.o3drServices.asBinder().linkToDeath(ControlTower.this.binderDeathRecipient, 0);
        ControlTower.this.notifyTowerConnected();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        ControlTower.this.notifyTowerDisconnected();
      }
    }

    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      ControlTower.this.isServiceConnecting.set(false);
      ControlTower.this.notifyTowerDisconnected();
    }
  };
  private final Intent serviceIntent = new Intent(IDroidPlannerServices.class.getName());
  private TowerListener towerListener;

  public ControlTower(Context paramContext)
  {
    this.context = paramContext;
  }

  private boolean is3DRServicesInstalled()
  {
    ResolveInfo localResolveInfo = this.context.getPackageManager().resolveService(this.serviceIntent, 0);
    if (localResolveInfo == null)
      return false;
    this.serviceIntent.setClassName(localResolveInfo.serviceInfo.packageName, localResolveInfo.serviceInfo.name);
    return true;
  }

  private void promptFor3DRServicesInstall()
  {
    this.context.startActivity(new Intent(this.context, InstallServiceDialog.class).addFlags(268435456));
  }

  private void promptFor3DRServicesUpdate()
  {
    this.context.startActivity(new Intent(this.context, UpdateServiceDialog.class).addFlags(268435456));
  }

  public void connect(TowerListener paramTowerListener)
  {
    if ((this.towerListener != null) && ((this.isServiceConnecting.get()) || (isTowerConnected())));
    do
    {
      return;
      if (paramTowerListener == null)
        throw new IllegalArgumentException("ServiceListener argument cannot be null.");
      this.towerListener = paramTowerListener;
    }
    while ((isTowerConnected()) || (this.isServiceConnecting.get()));
    if (is3DRServicesInstalled())
    {
      this.isServiceConnecting.set(this.context.bindService(this.serviceIntent, this.o3drServicesConnection, 1));
      return;
    }
    promptFor3DRServicesInstall();
  }

  public void disconnect()
  {
    if (this.o3drServices != null)
    {
      this.o3drServices.asBinder().unlinkToDeath(this.binderDeathRecipient, 0);
      this.o3drServices = null;
    }
    this.towerListener = null;
    try
    {
      this.context.unbindService(this.o3drServicesConnection);
      return;
    }
    catch (Exception localException)
    {
      Log.e(TAG, "Error occurred while unbinding from 3DR Services.", localException);
    }
  }

  IDroidPlannerServices get3drServices()
  {
    return this.o3drServices;
  }

  String getApplicationId()
  {
    return this.context.getPackageName();
  }

  public Bundle[] getConnectedApps()
  {
    Bundle[] arrayOfBundle1 = new Bundle[0];
    if (isTowerConnected())
      try
      {
        arrayOfBundle1 = this.o3drServices.getConnectedApps(getApplicationId());
        if (arrayOfBundle1 != null)
        {
          Bundle[] arrayOfBundle2 = arrayOfBundle1;
          int i = arrayOfBundle2.length;
          for (int j = 0; j < i; j++)
            arrayOfBundle2[j].setClassLoader(ConnectionParameter.class.getClassLoader());
        }
      }
      catch (RemoteException localRemoteException)
      {
        Log.e(TAG, localRemoteException.getMessage(), localRemoteException);
      }
    return arrayOfBundle1;
  }

  public boolean isTowerConnected()
  {
    return (this.o3drServices != null) && (this.o3drServices.asBinder().pingBinder());
  }

  void notifyTowerConnected()
  {
    if (this.towerListener == null)
      return;
    this.towerListener.onTowerConnected();
  }

  void notifyTowerDisconnected()
  {
    if (this.towerListener == null)
      return;
    this.towerListener.onTowerDisconnected();
  }

  public void registerDrone(Drone paramDrone, Handler paramHandler)
  {
    if (paramDrone == null)
      return;
    if (!isTowerConnected())
      throw new IllegalStateException("Control Tower must be connected.");
    paramDrone.init(this, paramHandler);
    paramDrone.start();
  }

  public void unregisterDrone(Drone paramDrone)
  {
    if (paramDrone != null)
      paramDrone.destroy();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.ControlTower
 * JD-Core Version:    0.6.2
 */