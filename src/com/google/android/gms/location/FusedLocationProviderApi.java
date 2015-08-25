package com.google.android.gms.location;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

public abstract interface FusedLocationProviderApi
{
  public static final String KEY_LOCATION_CHANGED = "com.google.android.location.LOCATION";
  public static final String KEY_MOCK_LOCATION = "mockLocation";

  public abstract Location getLastLocation(GoogleApiClient paramGoogleApiClient);

  public abstract LocationAvailability getLocationAvailability(GoogleApiClient paramGoogleApiClient);

  public abstract PendingResult<Status> removeLocationUpdates(GoogleApiClient paramGoogleApiClient, PendingIntent paramPendingIntent);

  public abstract PendingResult<Status> removeLocationUpdates(GoogleApiClient paramGoogleApiClient, LocationCallback paramLocationCallback);

  public abstract PendingResult<Status> removeLocationUpdates(GoogleApiClient paramGoogleApiClient, LocationListener paramLocationListener);

  public abstract PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, LocationRequest paramLocationRequest, PendingIntent paramPendingIntent);

  public abstract PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, LocationRequest paramLocationRequest, LocationCallback paramLocationCallback, Looper paramLooper);

  public abstract PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, LocationRequest paramLocationRequest, LocationListener paramLocationListener);

  public abstract PendingResult<Status> requestLocationUpdates(GoogleApiClient paramGoogleApiClient, LocationRequest paramLocationRequest, LocationListener paramLocationListener, Looper paramLooper);

  public abstract PendingResult<Status> setMockLocation(GoogleApiClient paramGoogleApiClient, Location paramLocation);

  public abstract PendingResult<Status> setMockMode(GoogleApiClient paramGoogleApiClient, boolean paramBoolean);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.FusedLocationProviderApi
 * JD-Core Version:    0.6.2
 */