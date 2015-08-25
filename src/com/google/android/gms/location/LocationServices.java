package com.google.android.gms.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.internal.zzd;
import com.google.android.gms.location.internal.zze;
import com.google.android.gms.location.internal.zzj;
import com.google.android.gms.location.internal.zzo;

public class LocationServices
{
  public static final Api<Api.ApiOptions.NoOptions> API = new Api("LocationServices.API", zzKi, zzKh, new Scope[0]);
  public static FusedLocationProviderApi FusedLocationApi = new zzd();
  public static GeofencingApi GeofencingApi = new zze();
  public static SettingsApi SettingsApi = new zzo();
  private static final Api.zzc<zzj> zzKh = new Api.zzc();
  private static final Api.zzb<zzj, Api.ApiOptions.NoOptions> zzKi = new LocationServices.1();

  public static zzj zze(GoogleApiClient paramGoogleApiClient)
  {
    boolean bool1 = true;
    boolean bool2;
    zzj localzzj;
    if (paramGoogleApiClient != null)
    {
      bool2 = bool1;
      zzv.zzb(bool2, "GoogleApiClient parameter is required.");
      localzzj = (zzj)paramGoogleApiClient.zza(zzKh);
      if (localzzj == null)
        break label44;
    }
    while (true)
    {
      zzv.zza(bool1, "GoogleApiClient is not configured to use the LocationServices.API Api. Pass thisinto GoogleApiClient.Builder#addApi() to use this feature.");
      return localzzj;
      bool2 = false;
      break;
      label44: bool1 = false;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.LocationServices
 * JD-Core Version:    0.6.2
 */