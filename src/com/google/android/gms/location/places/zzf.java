package com.google.android.gms.location.places;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zza;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzie;
import com.google.android.gms.location.places.internal.zzg.zza;
import com.google.android.gms.location.places.personalized.zzd;

public class zzf extends zzg.zza
{
  private static final String TAG = zzf.class.getSimpleName();
  private final Context mContext;
  private final zzd zzarX;
  private final zza zzarY;
  private final zze zzarZ;
  private final zzf zzasa;
  private final zzc zzasb;

  public zzf(zza paramzza)
  {
    this.zzarX = null;
    this.zzarY = paramzza;
    this.zzarZ = null;
    this.zzasa = null;
    this.zzasb = null;
    this.mContext = null;
  }

  public zzf(zzc paramzzc, Context paramContext)
  {
    this.zzarX = null;
    this.zzarY = null;
    this.zzarZ = null;
    this.zzasa = null;
    this.zzasb = paramzzc;
    this.mContext = paramContext;
  }

  public zzf(zzd paramzzd, Context paramContext)
  {
    this.zzarX = paramzzd;
    this.zzarY = null;
    this.zzarZ = null;
    this.zzasa = null;
    this.zzasb = null;
    this.mContext = paramContext;
  }

  public zzf(zzf paramzzf)
  {
    this.zzarX = null;
    this.zzarY = null;
    this.zzarZ = null;
    this.zzasa = paramzzf;
    this.zzasb = null;
    this.mContext = null;
  }

  public void zzY(DataHolder paramDataHolder)
    throws RemoteException
  {
    if (this.zzarX != null);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "placeEstimator cannot be null");
      if (paramDataHolder != null)
        break;
      if (Log.isLoggable(TAG, 6))
        Log.e(TAG, "onPlaceEstimated received null DataHolder: " + zzie.zznn());
      this.zzarX.zzk(Status.zzQW);
      return;
    }
    PlaceLikelihoodBuffer localPlaceLikelihoodBuffer = new PlaceLikelihoodBuffer(paramDataHolder, 100, this.mContext);
    this.zzarX.setResult(localPlaceLikelihoodBuffer);
  }

  public void zzZ(DataHolder paramDataHolder)
    throws RemoteException
  {
    if (paramDataHolder == null)
    {
      if (Log.isLoggable(TAG, 6))
        Log.e(TAG, "onAutocompletePrediction received null DataHolder: " + zzie.zznn());
      this.zzarY.zzk(Status.zzQW);
      return;
    }
    this.zzarY.setResult(new AutocompletePredictionBuffer(paramDataHolder));
  }

  public void zzaa(DataHolder paramDataHolder)
    throws RemoteException
  {
    if (paramDataHolder == null)
    {
      if (Log.isLoggable(TAG, 6))
        Log.e(TAG, "onPlaceUserDataFetched received null DataHolder: " + zzie.zznn());
      this.zzarZ.zzk(Status.zzQW);
      return;
    }
    this.zzarZ.setResult(new zzd(paramDataHolder));
  }

  public void zzab(DataHolder paramDataHolder)
    throws RemoteException
  {
    PlaceBuffer localPlaceBuffer = new PlaceBuffer(paramDataHolder, this.mContext);
    this.zzasb.setResult(localPlaceBuffer);
  }

  public void zzaz(Status paramStatus)
    throws RemoteException
  {
    this.zzasa.setResult(paramStatus);
  }

  public static abstract class zza<A extends Api.zza> extends zzf.zzb<AutocompletePredictionBuffer, A>
  {
    public zza(Api.zzc<A> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }

    protected AutocompletePredictionBuffer zzaA(Status paramStatus)
    {
      return new AutocompletePredictionBuffer(DataHolder.zzaE(paramStatus.getStatusCode()));
    }
  }

  public static abstract class zzb<R extends Result, A extends Api.zza> extends zza.zza<R, A>
  {
    public zzb(Api.zzc<A> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }
  }

  public static abstract class zzc<A extends Api.zza> extends zzf.zzb<PlaceBuffer, A>
  {
    public zzc(Api.zzc<A> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }

    protected PlaceBuffer zzaB(Status paramStatus)
    {
      return new PlaceBuffer(DataHolder.zzaE(paramStatus.getStatusCode()), null);
    }
  }

  public static abstract class zzd<A extends Api.zza> extends zzf.zzb<PlaceLikelihoodBuffer, A>
  {
    public zzd(Api.zzc<A> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }

    protected PlaceLikelihoodBuffer zzaC(Status paramStatus)
    {
      return new PlaceLikelihoodBuffer(DataHolder.zzaE(paramStatus.getStatusCode()), 100, null);
    }
  }

  public static abstract class zze<A extends Api.zza> extends zzf.zzb<zzd, A>
  {
    protected zzd zzaD(Status paramStatus)
    {
      return zzd.zzaE(paramStatus);
    }
  }

  public static abstract class zzf<A extends Api.zza> extends zzf.zzb<Status, A>
  {
    public zzf(Api.zzc<A> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super(paramGoogleApiClient);
    }

    protected Status zzb(Status paramStatus)
    {
      return paramStatus;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.zzf
 * JD-Core Version:    0.6.2
 */