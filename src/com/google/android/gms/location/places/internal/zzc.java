package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.zzf;
import com.google.android.gms.location.places.zzf.zza;
import com.google.android.gms.location.places.zzf.zzc;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Arrays;
import java.util.List;

public class zzc
  implements GeoDataApi
{
  public PendingResult<PlaceBuffer> addPlace(GoogleApiClient paramGoogleApiClient, final AddPlaceRequest paramAddPlaceRequest)
  {
    return paramGoogleApiClient.zzb(new zzf.zzc(Places.zzarV, paramGoogleApiClient)
    {
      protected void zza(zzd paramAnonymouszzd)
        throws RemoteException
      {
        paramAnonymouszzd.zza(new zzf(this, paramAnonymouszzd.getContext()), paramAddPlaceRequest);
      }
    });
  }

  public PendingResult<AutocompletePredictionBuffer> getAutocompletePredictions(GoogleApiClient paramGoogleApiClient, final String paramString, final LatLngBounds paramLatLngBounds, final AutocompleteFilter paramAutocompleteFilter)
  {
    return paramGoogleApiClient.zza(new zzf.zza(Places.zzarV, paramGoogleApiClient)
    {
      protected void zza(zzd paramAnonymouszzd)
        throws RemoteException
      {
        paramAnonymouszzd.zza(new zzf(this), paramString, paramLatLngBounds, paramAutocompleteFilter);
      }
    });
  }

  public PendingResult<PlaceBuffer> getPlaceById(GoogleApiClient paramGoogleApiClient, final String[] paramArrayOfString)
  {
    int i = 1;
    if ((paramArrayOfString != null) && (paramArrayOfString.length >= i));
    while (true)
    {
      zzv.zzQ(i);
      return paramGoogleApiClient.zza(new zzf.zzc(Places.zzarV, paramGoogleApiClient)
      {
        protected void zza(zzd paramAnonymouszzd)
          throws RemoteException
        {
          List localList = Arrays.asList(paramArrayOfString);
          paramAnonymouszzd.zza(new zzf(this, paramAnonymouszzd.getContext()), localList);
        }
      });
      int j = 0;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzc
 * JD-Core Version:    0.6.2
 */