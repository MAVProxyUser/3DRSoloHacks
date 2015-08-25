package com.o3dr.android.client;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.util.Log;
import com.o3dr.android.client.apis.drone.ConnectApi;
import com.o3dr.android.client.apis.drone.DroneStateApi;
import com.o3dr.android.client.apis.drone.ExperimentalApi;
import com.o3dr.android.client.apis.drone.GuidedApi;
import com.o3dr.android.client.apis.drone.ParameterApi;
import com.o3dr.android.client.apis.gcs.CalibrationApi;
import com.o3dr.android.client.apis.gcs.FollowApi;
import com.o3dr.android.client.apis.mission.MissionApi;
import com.o3dr.android.client.interfaces.DroneListener;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.drone.calibration.magnetometer.MagnetometerCalibrationStatus;
import com.o3dr.services.android.lib.drone.camera.GoPro;
import com.o3dr.services.android.lib.drone.connection.ConnectionParameter;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;
import com.o3dr.services.android.lib.drone.mission.Mission;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem;
import com.o3dr.services.android.lib.drone.mission.item.MissionItem.ComplexItem;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Attitude;
import com.o3dr.services.android.lib.drone.property.Battery;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.drone.property.GuidedState;
import com.o3dr.services.android.lib.drone.property.Home;
import com.o3dr.services.android.lib.drone.property.Parameter;
import com.o3dr.services.android.lib.drone.property.Parameters;
import com.o3dr.services.android.lib.drone.property.Signal;
import com.o3dr.services.android.lib.drone.property.Speed;
import com.o3dr.services.android.lib.drone.property.State;
import com.o3dr.services.android.lib.drone.property.Type;
import com.o3dr.services.android.lib.drone.property.VehicleMode;
import com.o3dr.services.android.lib.gcs.follow.FollowState;
import com.o3dr.services.android.lib.gcs.follow.FollowType;
import com.o3dr.services.android.lib.mavlink.MavlinkMessageWrapper;
import com.o3dr.services.android.lib.model.IDroidPlannerServices;
import com.o3dr.services.android.lib.model.IDroneApi;
import com.o3dr.services.android.lib.model.IObserver;
import com.o3dr.services.android.lib.model.action.Action;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Drone
{
  public static final String ACTION_GROUND_COLLISION_IMMINENT = CLAZZ_NAME + ".ACTION_GROUND_COLLISION_IMMINENT";
  private static final String CLAZZ_NAME = Drone.class.getName();
  public static final double COLLISION_DANGEROUS_SPEED_METERS_PER_SECOND = -3.0D;
  public static final double COLLISION_SAFE_ALTITUDE_METERS = 1.0D;
  public static final int COLLISION_SECONDS_BEFORE_COLLISION = 2;
  public static final String EXTRA_IS_GROUND_COLLISION_IMMINENT = "extra_is_ground_collision_imminent";
  private static final String TAG = Drone.class.getSimpleName();
  private DroneApiListener apiListener;
  private ExecutorService asyncScheduler;
  private final IBinder.DeathRecipient binderDeathRecipient = new IBinder.DeathRecipient()
  {
    public void binderDied()
    {
      Drone.this.notifyDroneServiceInterrupted("Lost access to the drone api.");
    }
  };
  private ConnectionParameter connectionParameter;
  private final Context context;
  private IDroneApi droneApi;
  private final ConcurrentLinkedQueue<DroneListener> droneListeners = new ConcurrentLinkedQueue();
  private DroneObserver droneObserver;
  private long elapsedFlightTime = 0L;
  private Handler handler;
  private ControlTower serviceMgr;
  private long startTime = 0L;

  public Drone(Context paramContext)
  {
    this.context = paramContext;
  }

  private void addAttributesObserver(IObserver paramIObserver)
  {
    if (isStarted());
    try
    {
      this.droneApi.addAttributesObserver(paramIObserver);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      handleRemoteException(localRemoteException);
    }
  }

  private void checkForGroundCollision()
  {
    Speed localSpeed = (Speed)getAttribute("com.o3dr.services.android.lib.attribute.SPEED");
    Altitude localAltitude = (Altitude)getAttribute("com.o3dr.services.android.lib.attribute.ALTITUDE");
    if ((localSpeed == null) || (localAltitude == null))
      return;
    double d1 = localSpeed.getVerticalSpeed();
    double d2 = localAltitude.getAltitude();
    if ((d2 + 2.0D * d1 < 0.0D) && (d1 < -3.0D) && (d2 > 1.0D));
    for (boolean bool = true; ; bool = false)
    {
      Bundle localBundle = new Bundle(1);
      localBundle.putBoolean("extra_is_ground_collision_imminent", bool);
      notifyAttributeUpdated(ACTION_GROUND_COLLISION_IMMINENT, localBundle);
      return;
    }
  }

  private <T extends Parcelable> T getAttributeDefaultValue(String paramString)
  {
    if (paramString == null)
      return null;
    int i = -1;
    switch (paramString.hashCode())
    {
    default:
    case -1598946243:
    case 744584719:
    case -1702436682:
    case -1445036859:
    case -1702552468:
    case 1206115909:
    case 1607318522:
    case 1906790642:
    case -987487119:
    case -1245915389:
    case -828014987:
    case 1607685717:
    case -998663554:
    case -1713653526:
    case -835416121:
    case -1711199360:
    }
    while (true)
      switch (i)
      {
      default:
        return null;
      case 0:
        return new Altitude();
        if (paramString.equals("com.o3dr.services.android.lib.attribute.ALTITUDE"))
        {
          i = 0;
          continue;
          if (paramString.equals("com.o3dr.services.android.lib.attribute.GPS"))
          {
            i = 1;
            continue;
            if (paramString.equals("com.o3dr.services.android.lib.attribute.STATE"))
            {
              i = 2;
              continue;
              if (paramString.equals("com.o3dr.services.android.lib.attribute.PARAMETERS"))
              {
                i = 3;
                continue;
                if (paramString.equals("com.o3dr.services.android.lib.attribute.SPEED"))
                {
                  i = 4;
                  continue;
                  if (paramString.equals("com.o3dr.services.android.lib.attribute.ATTITUDE"))
                  {
                    i = 5;
                    continue;
                    if (paramString.equals("com.o3dr.services.android.lib.attribute.HOME"))
                    {
                      i = 6;
                      continue;
                      if (paramString.equals("com.o3dr.services.android.lib.attribute.BATTERY"))
                      {
                        i = 7;
                        continue;
                        if (paramString.equals("com.o3dr.services.android.lib.attribute.MISSION"))
                        {
                          i = 8;
                          continue;
                          if (paramString.equals("com.o3dr.services.android.lib.attribute.SIGNAL"))
                          {
                            i = 9;
                            continue;
                            if (paramString.equals("com.o3dr.services.android.lib.attribute.GUIDED_STATE"))
                            {
                              i = 10;
                              continue;
                              if (paramString.equals("com.o3dr.services.android.lib.attribute.TYPE"))
                              {
                                i = 11;
                                continue;
                                if (paramString.equals("com.o3dr.services.android.lib.attribute.FOLLOW_STATE"))
                                {
                                  i = 12;
                                  continue;
                                  if (paramString.equals("com.o3dr.services.android.lib.attribute.GOPRO"))
                                  {
                                    i = 13;
                                    continue;
                                    if (paramString.equals("com.o3dr.services.android.lib.attribute.MAGNETOMETER_CALIBRATION_STATUS"))
                                    {
                                      i = 14;
                                      continue;
                                      if (paramString.equals("com.o3dr.services.android.lib.attribute.CAMERA"))
                                        i = 15;
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
        break;
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      }
    return new Gps();
    return new State();
    return new Parameters();
    return new Speed();
    return new Attitude();
    return new Home();
    return new Battery();
    return new Mission();
    return new Signal();
    return new GuidedState();
    return new Type();
    return new FollowState();
    return new GoPro();
    return new MagnetometerCalibrationStatus();
  }

  private void handleRemoteException(RemoteException paramRemoteException)
  {
    if ((this.droneApi != null) && (!this.droneApi.asBinder().pingBinder()))
    {
      String str = paramRemoteException.getMessage();
      Log.e(TAG, str, paramRemoteException);
      notifyDroneServiceInterrupted(str);
    }
  }

  private void removeAttributesObserver(IObserver paramIObserver)
  {
    if (isStarted());
    try
    {
      this.droneApi.removeAttributesObserver(paramIObserver);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      handleRemoteException(localRemoteException);
    }
  }

  public void addMavlinkObserver(MavlinkObserver paramMavlinkObserver)
  {
    if (isStarted());
    try
    {
      this.droneApi.addMavlinkObserver(paramMavlinkObserver);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      handleRemoteException(localRemoteException);
    }
  }

  public void arm(boolean paramBoolean)
  {
    DroneStateApi.arm(this, paramBoolean);
  }

  public <T extends MissionItem> void buildMissionItemsAsync(final OnMissionItemsBuiltCallback<T> paramOnMissionItemsBuiltCallback, final MissionItem.ComplexItem<T>[] paramArrayOfComplexItem)
  {
    if (paramOnMissionItemsBuiltCallback == null)
      throw new IllegalArgumentException("Callback must be non-null.");
    if ((paramArrayOfComplexItem == null) || (paramArrayOfComplexItem.length == 0))
      return;
    this.asyncScheduler.execute(new Runnable()
    {
      public void run()
      {
        for (MissionItem.ComplexItem localComplexItem : paramArrayOfComplexItem)
          MissionApi.buildMissionItem(Drone.this, localComplexItem);
        Drone.this.handler.post(new Runnable()
        {
          public void run()
          {
            Drone.3.this.val$callback.onMissionItemsBuilt(Drone.3.this.val$missionItems);
          }
        });
      }
    });
  }

  public void changeVehicleMode(VehicleMode paramVehicleMode)
  {
    DroneStateApi.setVehicleMode(this, paramVehicleMode);
  }

  public void connect(ConnectionParameter paramConnectionParameter)
  {
    if (ConnectApi.connect(this, paramConnectionParameter))
      this.connectionParameter = paramConnectionParameter;
  }

  void destroy()
  {
    removeAttributesObserver(this.droneObserver);
    try
    {
      if (isStarted())
      {
        this.droneApi.asBinder().unlinkToDeath(this.binderDeathRecipient, 0);
        this.serviceMgr.get3drServices().releaseDroneApi(this.droneApi);
      }
      if (this.asyncScheduler != null)
      {
        this.asyncScheduler.shutdownNow();
        this.asyncScheduler = null;
      }
      this.droneApi = null;
      this.droneListeners.clear();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      while (true)
        Log.e(TAG, localRemoteException.getMessage(), localRemoteException);
    }
  }

  public void disableFollowMe()
  {
    FollowApi.disableFollowMe(this);
  }

  public void disconnect()
  {
    if (ConnectApi.disconnect(this))
      this.connectionParameter = null;
  }

  public void doGuidedTakeoff(double paramDouble)
  {
    GuidedApi.takeoff(this, paramDouble);
  }

  public void enableFollowMe(FollowType paramFollowType)
  {
    FollowApi.enableFollowMe(this, paramFollowType);
  }

  public void epmCommand(boolean paramBoolean)
  {
    ExperimentalApi.epmCommand(this, paramBoolean);
  }

  public void generateDronie()
  {
    MissionApi.generateDronie(this);
  }

  public <T extends Parcelable> T getAttribute(String paramString)
  {
    Parcelable localParcelable;
    if ((!isStarted()) || (paramString == null))
      localParcelable = getAttributeDefaultValue(paramString);
    while (true)
    {
      return localParcelable;
      try
      {
        Bundle localBundle2 = this.droneApi.getAttribute(paramString);
        localBundle1 = localBundle2;
        localParcelable = null;
        if (localBundle1 != null)
        {
          ClassLoader localClassLoader = this.context.getClassLoader();
          localParcelable = null;
          if (localClassLoader != null)
          {
            localBundle1.setClassLoader(localClassLoader);
            localParcelable = localBundle1.getParcelable(paramString);
          }
        }
        if (localParcelable != null)
          continue;
        return getAttributeDefaultValue(paramString);
      }
      catch (RemoteException localRemoteException)
      {
        while (true)
        {
          handleRemoteException(localRemoteException);
          Bundle localBundle1 = null;
        }
      }
    }
  }

  public <T extends Parcelable> void getAttributeAsync(final String paramString, final OnAttributeRetrievedCallback<T> paramOnAttributeRetrievedCallback)
  {
    if (paramOnAttributeRetrievedCallback == null)
      throw new IllegalArgumentException("Callback must be non-null.");
    if (!isStarted())
    {
      paramOnAttributeRetrievedCallback.onRetrievalFailed();
      return;
    }
    this.asyncScheduler.execute(new Runnable()
    {
      public void run()
      {
        final Parcelable localParcelable = Drone.this.getAttribute(paramString);
        Drone.this.handler.post(new Runnable()
        {
          public void run()
          {
            if (localParcelable == null)
            {
              Drone.2.this.val$callback.onRetrievalFailed();
              return;
            }
            Drone.2.this.val$callback.onRetrievalSucceed(localParcelable);
          }
        });
      }
    });
  }

  public ConnectionParameter getConnectionParameter()
  {
    return this.connectionParameter;
  }

  public long getFlightTime()
  {
    State localState = (State)getAttribute("com.o3dr.services.android.lib.attribute.STATE");
    if ((localState != null) && (localState.isFlying()))
    {
      this.elapsedFlightTime += SystemClock.elapsedRealtime() - this.startTime;
      this.startTime = SystemClock.elapsedRealtime();
    }
    return this.elapsedFlightTime / 1000L;
  }

  public double getSpeedParameter()
  {
    Parameters localParameters = (Parameters)getAttribute("com.o3dr.services.android.lib.attribute.PARAMETERS");
    if (localParameters != null)
    {
      Parameter localParameter = localParameters.getParameter("WPNAV_SPEED");
      if (localParameter != null)
        return localParameter.getValue();
    }
    return 0.0D;
  }

  void init(ControlTower paramControlTower, Handler paramHandler)
  {
    this.handler = paramHandler;
    this.serviceMgr = paramControlTower;
    this.apiListener = new DroneApiListener(this);
    this.droneObserver = new DroneObserver(this);
  }

  public boolean isConnected()
  {
    State localState = (State)getAttribute("com.o3dr.services.android.lib.attribute.STATE");
    return (isStarted()) && (localState.isConnected());
  }

  public boolean isStarted()
  {
    return (this.droneApi != null) && (this.droneApi.asBinder().pingBinder());
  }

  public void loadWaypoints()
  {
    MissionApi.loadWaypoints(this);
  }

  void notifyAttributeUpdated(final String paramString, final Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(this.context.getClassLoader());
    if ("com.o3dr.services.android.lib.attribute.event.STATE_UPDATED".equals(paramString))
      getAttributeAsync("com.o3dr.services.android.lib.attribute.STATE", new OnAttributeRetrievedCallback()
      {
        public void onRetrievalFailed()
        {
          Drone.this.stopTimer();
        }

        public void onRetrievalSucceed(State paramAnonymousState)
        {
          if (paramAnonymousState.isFlying())
          {
            Drone.this.resetFlightTimer();
            return;
          }
          Drone.this.stopTimer();
        }
      });
    while (this.droneListeners.isEmpty())
    {
      return;
      if ("com.o3dr.services.android.lib.attribute.event.SPEED_UPDATED".equals(paramString))
        checkForGroundCollision();
    }
    this.handler.post(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = Drone.this.droneListeners.iterator();
        while (localIterator.hasNext())
          ((DroneListener)localIterator.next()).onDroneEvent(paramString, paramBundle);
      }
    });
  }

  void notifyDroneConnectionFailed(final ConnectionResult paramConnectionResult)
  {
    if (this.droneListeners.isEmpty())
      return;
    this.handler.post(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = Drone.this.droneListeners.iterator();
        while (localIterator.hasNext())
          ((DroneListener)localIterator.next()).onDroneConnectionFailed(paramConnectionResult);
      }
    });
  }

  void notifyDroneServiceInterrupted(final String paramString)
  {
    if (this.droneListeners.isEmpty())
      return;
    this.handler.post(new Runnable()
    {
      public void run()
      {
        Iterator localIterator = Drone.this.droneListeners.iterator();
        while (localIterator.hasNext())
          ((DroneListener)localIterator.next()).onDroneServiceInterrupted(paramString);
      }
    });
  }

  public void pauseAtCurrentLocation()
  {
    GuidedApi.pauseAtCurrentLocation(this);
  }

  public boolean performAction(Action paramAction)
  {
    if (isStarted())
      try
      {
        this.droneApi.performAction(paramAction);
        return true;
      }
      catch (RemoteException localRemoteException)
      {
        handleRemoteException(localRemoteException);
      }
    return false;
  }

  public boolean performAsyncAction(Action paramAction)
  {
    if (isStarted())
      try
      {
        this.droneApi.performAsyncAction(paramAction);
        return true;
      }
      catch (RemoteException localRemoteException)
      {
        handleRemoteException(localRemoteException);
      }
    return false;
  }

  public void refreshParameters()
  {
    ParameterApi.refreshParameters(this);
  }

  public void registerDroneListener(DroneListener paramDroneListener)
  {
    if (paramDroneListener == null);
    while (this.droneListeners.contains(paramDroneListener))
      return;
    this.droneListeners.add(paramDroneListener);
  }

  public void removeMavlinkObserver(MavlinkObserver paramMavlinkObserver)
  {
    if (isStarted());
    try
    {
      this.droneApi.removeMavlinkObserver(paramMavlinkObserver);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      handleRemoteException(localRemoteException);
    }
  }

  public void resetFlightTimer()
  {
    this.elapsedFlightTime = 0L;
    this.startTime = SystemClock.elapsedRealtime();
  }

  public void sendGuidedPoint(LatLong paramLatLong, boolean paramBoolean)
  {
    GuidedApi.sendGuidedPoint(this, paramLatLong, paramBoolean);
  }

  public void sendIMUCalibrationAck(int paramInt)
  {
    CalibrationApi.sendIMUAck(this, paramInt);
  }

  public void sendMavlinkMessage(MavlinkMessageWrapper paramMavlinkMessageWrapper)
  {
    ExperimentalApi.sendMavlinkMessage(this, paramMavlinkMessageWrapper);
  }

  public void setGuidedAltitude(double paramDouble)
  {
    GuidedApi.setGuidedAltitude(this, paramDouble);
  }

  public void setMission(Mission paramMission, boolean paramBoolean)
  {
    MissionApi.setMission(this, paramMission, paramBoolean);
  }

  void start()
  {
    if (!this.serviceMgr.isTowerConnected())
      throw new IllegalStateException("Service manager must be connected.");
    if (isStarted())
      return;
    try
    {
      this.droneApi = this.serviceMgr.get3drServices().registerDroneApi(this.apiListener, this.serviceMgr.getApplicationId());
      this.droneApi.asBinder().linkToDeath(this.binderDeathRecipient, 0);
      if ((this.asyncScheduler == null) || (this.asyncScheduler.isShutdown()))
        this.asyncScheduler = Executors.newFixedThreadPool(1);
      addAttributesObserver(this.droneObserver);
      resetFlightTimer();
      return;
    }
    catch (RemoteException localRemoteException)
    {
    }
    throw new IllegalStateException("Unable to retrieve a valid drone handle.");
  }

  public void startIMUCalibration()
  {
    CalibrationApi.startIMUCalibration(this);
  }

  public void stopTimer()
  {
    this.elapsedFlightTime += SystemClock.elapsedRealtime() - this.startTime;
    this.startTime = SystemClock.elapsedRealtime();
  }

  public void triggerCamera()
  {
    ExperimentalApi.triggerCamera(this);
  }

  public void unregisterDroneListener(DroneListener paramDroneListener)
  {
    if (paramDroneListener == null)
      return;
    this.droneListeners.remove(paramDroneListener);
  }

  public void writeParameters(Parameters paramParameters)
  {
    ParameterApi.writeParameters(this, paramParameters);
  }

  public static class AttributeRetrievedListener<T extends Parcelable>
    implements Drone.OnAttributeRetrievedCallback<T>
  {
    public void onRetrievalFailed()
    {
    }

    public void onRetrievalSucceed(T paramT)
    {
    }
  }

  public static abstract interface OnAttributeRetrievedCallback<T extends Parcelable>
  {
    public abstract void onRetrievalFailed();

    public abstract void onRetrievalSucceed(T paramT);
  }

  public static abstract interface OnMissionItemsBuiltCallback<T extends MissionItem>
  {
    public abstract void onMissionItemsBuilt(MissionItem.ComplexItem<T>[] paramArrayOfComplexItem);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.android.client.Drone
 * JD-Core Version:    0.6.2
 */