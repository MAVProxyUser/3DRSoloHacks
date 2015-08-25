package com.google.android.gms.location.places;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;

public abstract interface PlaceDetectionApi
{
  public abstract PendingResult<PlaceLikelihoodBuffer> getCurrentPlace(GoogleApiClient paramGoogleApiClient, PlaceFilter paramPlaceFilter);

  public abstract PendingResult<Status> reportDeviceAtPlace(GoogleApiClient paramGoogleApiClient, PlaceReport paramPlaceReport);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.PlaceDetectionApi
 * JD-Core Version:    0.6.2
 */