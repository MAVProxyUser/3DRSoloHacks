package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.zzf;
import com.google.android.gms.location.places.zzf.zzd;
import com.google.android.gms.location.places.zzf.zzf;

public class zzh
  implements PlaceDetectionApi
{
  public PendingResult<PlaceLikelihoodBuffer> getCurrentPlace(GoogleApiClient paramGoogleApiClient, final PlaceFilter paramPlaceFilter)
  {
    return paramGoogleApiClient.zza(new zzf.zzd(Places.zzarW, paramGoogleApiClient)
    {
      protected void zza(zzi paramAnonymouszzi)
        throws RemoteException
      {
        paramAnonymouszzi.zza(new zzf(this, paramAnonymouszzi.getContext()), paramPlaceFilter);
      }
    });
  }

  public PendingResult<Status> reportDeviceAtPlace(GoogleApiClient paramGoogleApiClient, final PlaceReport paramPlaceReport)
  {
    return paramGoogleApiClient.zzb(new zzf.zzf(Places.zzarW, paramGoogleApiClient)
    {
      protected void zza(zzi paramAnonymouszzi)
        throws RemoteException
      {
        paramAnonymouszzi.zza(new zzf(this), paramPlaceReport);
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzh
 * JD-Core Version:    0.6.2
 */