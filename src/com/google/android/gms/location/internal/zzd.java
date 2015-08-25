package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationServices.zza;

public class zzd
  implements FusedLocationProviderApi
{
  public Location getLastLocation(GoogleApiClient paramGoogleApiClient)
  {
    zzj localzzj = LocationServices.zze(paramGoogleApiClient);
    try
    {
      Location localLocation = localzzj.getLastLocation();
      return localLocation;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public LocationAvailability getLocationAvailability(GoogleApiClient paramGoogleApiClient)
  {
    zzj localzzj = LocationServices.zze(paramGoogleApiClient);
    try
    {
      LocationAvailability localLocationAvailability = localzzj.zzsI();
      return localLocationAvailability;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public PendingResult<Status> removeLocationUpdates(GoogleApiClient paramGoogleApiClient, final PendingIntent paramPendingIntent)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zzd(paramPendingIntent);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> removeLocationUpdates(GoogleApiClient paramGoogleApiClient, final LocationCallback paramLocationCallback)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(paramLocationCallback);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> removeLocationUpdates(GoogleApiClient paramGoogleApiClient, final LocationListener paramLocationListener)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(paramLocationListener);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, final LocationRequest paramLocationRequest, final PendingIntent paramPendingIntent)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zzb(paramLocationRequest, paramPendingIntent);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, final LocationRequest paramLocationRequest, final LocationCallback paramLocationCallback, final Looper paramLooper)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(LocationRequestInternal.zzb(paramLocationRequest), paramLocationCallback, paramLooper);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, final LocationRequest paramLocationRequest, final LocationListener paramLocationListener)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(paramLocationRequest, paramLocationListener, null);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, final LocationRequest paramLocationRequest, final LocationListener paramLocationListener, final Looper paramLooper)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(paramLocationRequest, paramLocationListener, paramLooper);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> setMockLocation(GoogleApiClient paramGoogleApiClient, final Location paramLocation)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zzb(paramLocation);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> setMockMode(GoogleApiClient paramGoogleApiClient, final boolean paramBoolean)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zzX(paramBoolean);
        setResult(Status.zzQU);
      }
    });
  }

  private static abstract class zza extends LocationServices.zza<Status>
  {
    public zza(GoogleApiClient paramGoogleApiClient)
    {
      super();
    }

    public Status zzb(Status paramStatus)
    {
      return paramStatus;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzd
 * JD-Core Version:    0.6.2
 */