package com.o3dr.solo.android.fragment.shots.follow;

import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.o3dr.services.android.lib.util.googleApi.GoogleApiClientManager;
import com.o3dr.services.android.lib.util.googleApi.GoogleApiClientManager.GoogleApiClientTask;
import com.o3dr.services.android.lib.util.googleApi.GoogleApiClientManager.ManagerListener;

public class FusedLocation extends LocationCallback
  implements GoogleApiClientManager.ManagerListener
{
  private static final float JUMP_FACTOR = 4.0F;
  public static final int LOCATION_ACCURACY_PRIORITY = 100;
  private static final float LOCATION_ACCURACY_THRESHOLD = 15.0F;
  public static final float LOCATION_REQUEST_DISTANCE_M = 0.0F;
  public static final long LOCATION_REQUEST_FASTEST_INTERVAL = 16L;
  public static final long LOCATION_REQUEST_INTERVAL = 16L;
  private static final String TAG = FusedLocation.class.getSimpleName();
  private static final Api<? extends Api.ApiOptions.NotRequiredOptions>[] apisList = arrayOfApi;
  private final Context context;
  private final GoogleApiClientManager gApiMgr;
  private Location mLastLocation;
  private long mSpeedReadings;
  private float mTotalSpeed;
  private LocationReceiver receiver;
  private final GoogleApiClientManager.GoogleApiClientTask removeLocationUpdate = new GoogleApiClientManager.GoogleApiClientTask()
  {
    protected void doRun()
    {
      LocationServices.FusedLocationApi.removeLocationUpdates(getGoogleApiClient(), FusedLocation.this);
    }
  };
  private final GoogleApiClientManager.GoogleApiClientTask requestLocationUpdate;

  static
  {
    Api[] arrayOfApi = new Api[1];
    arrayOfApi[0] = LocationServices.API;
  }

  public FusedLocation(Context paramContext, final Handler paramHandler)
  {
    this.context = paramContext;
    this.gApiMgr = new GoogleApiClientManager(paramContext, paramHandler, apisList);
    this.gApiMgr.setManagerListener(this);
    this.requestLocationUpdate = new GoogleApiClientManager.GoogleApiClientTask()
    {
      protected void doRun()
      {
        LocationRequest localLocationRequest = LocationRequest.create();
        localLocationRequest.setPriority(100);
        localLocationRequest.setInterval(16L);
        localLocationRequest.setFastestInterval(16L);
        localLocationRequest.setSmallestDisplacement(0.0F);
        LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), localLocationRequest, FusedLocation.this, paramHandler.getLooper());
      }
    };
  }

  private boolean isLocationAccurate(float paramFloat1, float paramFloat2)
  {
    if (paramFloat1 >= 15.0F)
    {
      Log.d(TAG, "High accuracy: " + paramFloat1);
      return false;
    }
    this.mTotalSpeed = (paramFloat2 + this.mTotalSpeed);
    float f1 = this.mTotalSpeed;
    long l = 1L + this.mSpeedReadings;
    this.mSpeedReadings = l;
    float f2 = f1 / (float)l;
    if ((paramFloat2 > 0.0F) && (f2 >= 1.0D) && (paramFloat2 >= 4.0F * f2))
    {
      Log.d(TAG, "High current speed: " + paramFloat2);
      return false;
    }
    return true;
  }

  public void disableLocationUpdates()
  {
    this.gApiMgr.addTask(this.removeLocationUpdate);
    this.gApiMgr.stopSafely();
  }

  public void enableLocationUpdates()
  {
    this.gApiMgr.start();
    this.mSpeedReadings = 0L;
    this.mTotalSpeed = 0.0F;
    this.mLastLocation = null;
  }

  public void onGoogleApiConnectionError(ConnectionResult paramConnectionResult)
  {
    GooglePlayServicesUtil.showErrorNotification(paramConnectionResult.getErrorCode(), this.context);
  }

  public void onLocationAvailability(LocationAvailability paramLocationAvailability)
  {
    super.onLocationAvailability(paramLocationAvailability);
    if ((!paramLocationAvailability.isLocationAvailable()) && (this.receiver != null))
      this.receiver.onLocationUnavailable();
  }

  public void onLocationResult(LocationResult paramLocationResult)
  {
    super.onLocationResult(paramLocationResult);
    if (this.receiver != null)
    {
      Location localLocation = paramLocationResult.getLastLocation();
      if (localLocation != null)
      {
        float f1 = -1.0F;
        long l1 = -1L;
        long l2 = localLocation.getTime();
        if (this.mLastLocation != null)
        {
          f1 = localLocation.distanceTo(this.mLastLocation);
          l1 = (l2 - this.mLastLocation.getTime()) / 1000L;
        }
        boolean bool1 = f1 < 0.0F;
        float f2 = 0.0F;
        if (bool1)
        {
          boolean bool3 = l1 < 0L;
          f2 = 0.0F;
          if (bool3)
            f2 = f1 / (float)l1;
        }
        boolean bool2 = isLocationAccurate(localLocation.getAccuracy(), f2);
        this.mLastLocation = localLocation;
        this.receiver.onLocationUpdate(localLocation, bool2);
      }
    }
  }

  public void onManagerStarted()
  {
    this.gApiMgr.addTask(this.requestLocationUpdate);
  }

  public void onManagerStopped()
  {
  }

  public void onUnavailableGooglePlayServices(int paramInt)
  {
    GooglePlayServicesUtil.showErrorNotification(paramInt, this.context);
  }

  public void setLocationReceiver(LocationReceiver paramLocationReceiver)
  {
    this.receiver = paramLocationReceiver;
  }

  public static abstract interface LocationReceiver
  {
    public abstract void onLocationUnavailable();

    public abstract void onLocationUpdate(Location paramLocation, boolean paramBoolean);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.follow.FusedLocation
 * JD-Core Version:    0.6.2
 */