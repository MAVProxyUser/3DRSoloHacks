package com.o3dr.solo.android.util.location;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NotRequiredOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest.Builder;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;
import com.o3dr.services.android.lib.util.googleApi.GoogleApiClientManager;
import com.o3dr.services.android.lib.util.googleApi.GoogleApiClientManager.GoogleApiClientTask;
import com.o3dr.services.android.lib.util.googleApi.GoogleApiClientManager.ManagerListener;
import com.o3dr.solo.android.activity.FlightActivity;
import java.lang.ref.WeakReference;

public class CheckLocationSettings
  implements GoogleApiClientManager.ManagerListener
{
  public static final String ACTION_LOCATION_SETTINGS_UPDATED = "com.o3dr.solo.android.action.LOCATION_SETTINGS_UPDATED";
  public static final String EXTRA_RESULT_CODE = "extra_result_code";
  private static final String TAG = CheckLocationSettings.class.getSimpleName();
  private static final Api<? extends Api.ApiOptions.NotRequiredOptions>[] apisList = arrayOfApi;
  private final WeakReference<FlightActivity> activityRef;
  private final GoogleApiClientManager.GoogleApiClientTask checkLocationSettings = new GoogleApiClientManager.GoogleApiClientTask()
  {
    protected void doRun()
    {
      GoogleApiClient localGoogleApiClient = getGoogleApiClient();
      LocationSettingsRequest.Builder localBuilder = new LocationSettingsRequest.Builder().addLocationRequest(CheckLocationSettings.this.locationReq);
      LocationServices.SettingsApi.checkLocationSettings(localGoogleApiClient, localBuilder.build()).setResultCallback(new ResultCallback()
      {
        public void onResult(LocationSettingsResult paramAnonymous2LocationSettingsResult)
        {
          Status localStatus = paramAnonymous2LocationSettingsResult.getStatus();
          FlightActivity localFlightActivity = (FlightActivity)CheckLocationSettings.this.activityRef.get();
          switch (localStatus.getStatusCode())
          {
          default:
          case 0:
          case 6:
          case 8502:
          }
          while (true)
          {
            CheckLocationSettings.this.gapiMgr.stopSafely();
            return;
            if (CheckLocationSettings.this.onSuccess != null)
            {
              CheckLocationSettings.this.onSuccess.run();
              continue;
              if (localFlightActivity != null)
              {
                try
                {
                  localStatus.startResolutionForResult(localFlightActivity, CheckLocationSettings.this.requestCode);
                }
                catch (IntentSender.SendIntentException localSendIntentException)
                {
                  Log.e(CheckLocationSettings.TAG, localSendIntentException.getMessage(), localSendIntentException);
                }
                continue;
                if (localFlightActivity != null)
                {
                  Log.w(CheckLocationSettings.TAG, "Unable to get accurate user location.");
                  Toast.makeText(localFlightActivity, 2131099839, 1).show();
                }
              }
            }
          }
        }
      });
    }
  };
  private final GoogleApiClientManager gapiMgr;
  private final LocationRequest locationReq;
  private final Runnable onSuccess;
  private final int requestCode;

  static
  {
    Api[] arrayOfApi = new Api[1];
    arrayOfApi[0] = LocationServices.API;
  }

  public CheckLocationSettings(FlightActivity paramFlightActivity, LocationRequest paramLocationRequest, int paramInt, Runnable paramRunnable)
  {
    this.requestCode = paramInt;
    this.activityRef = new WeakReference(paramFlightActivity);
    this.locationReq = paramLocationRequest;
    this.onSuccess = paramRunnable;
    this.gapiMgr = new GoogleApiClientManager(paramFlightActivity.getApplicationContext(), new Handler(), apisList);
    this.gapiMgr.setManagerListener(this);
  }

  public void check()
  {
    this.gapiMgr.start();
  }

  public void onGoogleApiConnectionError(ConnectionResult paramConnectionResult)
  {
    Activity localActivity = (Activity)this.activityRef.get();
    if (localActivity == null)
      return;
    if (paramConnectionResult.hasResolution())
      try
      {
        paramConnectionResult.startResolutionForResult(localActivity, 0);
        return;
      }
      catch (IntentSender.SendIntentException localSendIntentException)
      {
        this.gapiMgr.start();
        return;
      }
    onUnavailableGooglePlayServices(paramConnectionResult.getErrorCode());
  }

  public void onManagerStarted()
  {
    this.gapiMgr.addTask(this.checkLocationSettings);
  }

  public void onManagerStopped()
  {
  }

  public void onReceive(Intent paramIntent)
  {
    String str = paramIntent.getAction();
    int i;
    switch (str.hashCode())
    {
    default:
      i = -1;
      label30: switch (i)
      {
      default:
      case 0:
      }
      break;
    case 1896041392:
    }
    Activity localActivity;
    do
    {
      do
      {
        return;
        if (!str.equals("com.o3dr.solo.android.action.LOCATION_SETTINGS_UPDATED"))
          break;
        i = 0;
        break label30;
        switch (paramIntent.getIntExtra("extra_result_code", -1))
        {
        default:
          return;
        case -1:
        case 0:
        }
      }
      while (this.onSuccess == null);
      this.onSuccess.run();
      return;
      localActivity = (Activity)this.activityRef.get();
    }
    while (localActivity == null);
    Toast.makeText(localActivity, 2131100045, 1).show();
  }

  public void onUnavailableGooglePlayServices(int paramInt)
  {
    final Activity localActivity = (Activity)this.activityRef.get();
    if (localActivity != null)
      GooglePlayServicesUtil.showErrorDialogFragment(paramInt, localActivity, 0, new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          localActivity.finish();
        }
      });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.location.CheckLocationSettings
 * JD-Core Version:    0.6.2
 */