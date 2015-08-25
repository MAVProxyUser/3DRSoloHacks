package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi.zzc;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.location.copresence.internal.CopresenceApiOptions;
import com.google.android.gms.location.zze.zza;
import com.google.android.gms.location.zze.zzb;
import java.util.List;

public class zzj extends zzb
{
  private final zzi zzara = new zzi(paramContext, this.zzaqz);
  private final com.google.android.gms.location.copresence.internal.zzb zzarb;

  public zzj(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString)
  {
    this(paramContext, paramLooper, paramConnectionCallbacks, paramOnConnectionFailedListener, paramString, new GoogleApiClient.Builder(paramContext).zzkK());
  }

  public zzj(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString, zze paramzze)
  {
    this(paramContext, paramLooper, paramConnectionCallbacks, paramOnConnectionFailedListener, paramString, paramzze, CopresenceApiOptions.zzaqx);
  }

  public zzj(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString, zze paramzze, CopresenceApiOptions paramCopresenceApiOptions)
  {
    super(paramContext, paramLooper, paramConnectionCallbacks, paramOnConnectionFailedListener, paramString, paramzze);
    this.zzarb = com.google.android.gms.location.copresence.internal.zzb.zza(paramContext, paramzze.getAccountName(), paramzze.zzlJ(), this.zzaqz, paramCopresenceApiOptions);
  }

  public void disconnect()
  {
    synchronized (this.zzara)
    {
      boolean bool = isConnected();
      if (bool);
      try
      {
        this.zzara.removeAllListeners();
        this.zzara.zzsJ();
        super.disconnect();
        return;
      }
      catch (Exception localException)
      {
        while (true)
          Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", localException);
      }
    }
  }

  public Location getLastLocation()
  {
    return this.zzara.getLastLocation();
  }

  public void zzX(boolean paramBoolean)
    throws RemoteException
  {
    this.zzara.zzX(paramBoolean);
  }

  public void zza(long paramLong, PendingIntent paramPendingIntent)
    throws RemoteException
  {
    zzlW();
    zzv.zzr(paramPendingIntent);
    if (paramLong >= 0L);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zzb(bool, "detectionIntervalMillis must be >= 0");
      ((zzg)zzlX()).zza(paramLong, true, paramPendingIntent);
      return;
    }
  }

  public void zza(PendingIntent paramPendingIntent)
    throws RemoteException
  {
    zzlW();
    zzv.zzr(paramPendingIntent);
    ((zzg)zzlX()).zza(paramPendingIntent);
  }

  public void zza(PendingIntent paramPendingIntent, zze.zzb paramzzb)
    throws RemoteException
  {
    zzlW();
    zzv.zzb(paramPendingIntent, "PendingIntent must be specified.");
    zzv.zzb(paramzzb, "OnRemoveGeofencesResultListener not provided.");
    if (paramzzb == null);
    for (Object localObject = null; ; localObject = new zzb(paramzzb, this))
    {
      ((zzg)zzlX()).zza(paramPendingIntent, (zzf)localObject, getContext().getPackageName());
      return;
    }
  }

  public void zza(GeofencingRequest paramGeofencingRequest, PendingIntent paramPendingIntent, zze.zza paramzza)
    throws RemoteException
  {
    zzlW();
    zzv.zzb(paramGeofencingRequest, "geofencingRequest can't be null.");
    zzv.zzb(paramPendingIntent, "PendingIntent must be specified.");
    zzv.zzb(paramzza, "OnAddGeofencesResultListener not provided.");
    if (paramzza == null);
    for (Object localObject = null; ; localObject = new zzb(paramzza, this))
    {
      ((zzg)zzlX()).zza(paramGeofencingRequest, paramPendingIntent, (zzf)localObject);
      return;
    }
  }

  public void zza(LocationCallback paramLocationCallback)
    throws RemoteException
  {
    this.zzara.zza(paramLocationCallback);
  }

  public void zza(LocationListener paramLocationListener)
    throws RemoteException
  {
    this.zzara.zza(paramLocationListener);
  }

  public void zza(LocationRequest paramLocationRequest, LocationListener paramLocationListener, Looper paramLooper)
    throws RemoteException
  {
    synchronized (this.zzara)
    {
      this.zzara.zza(paramLocationRequest, paramLocationListener, paramLooper);
      return;
    }
  }

  public void zza(LocationSettingsRequest paramLocationSettingsRequest, zza.zzb<LocationSettingsResult> paramzzb, String paramString)
    throws RemoteException
  {
    boolean bool1 = true;
    zzlW();
    boolean bool2;
    if (paramLocationSettingsRequest != null)
    {
      bool2 = bool1;
      zzv.zzb(bool2, "locationSettingsRequest can't be null nor empty.");
      if (paramzzb == null)
        break label66;
    }
    while (true)
    {
      zzv.zzb(bool1, "listener can't be null.");
      zzd localzzd = new zzd(paramzzb);
      ((zzg)zzlX()).zza(paramLocationSettingsRequest, localzzd, paramString);
      return;
      bool2 = false;
      break;
      label66: bool1 = false;
    }
  }

  public void zza(LocationRequestInternal paramLocationRequestInternal, LocationCallback paramLocationCallback, Looper paramLooper)
    throws RemoteException
  {
    synchronized (this.zzara)
    {
      this.zzara.zza(paramLocationRequestInternal, paramLocationCallback, paramLooper);
      return;
    }
  }

  public void zza(List<String> paramList, zze.zzb paramzzb)
    throws RemoteException
  {
    zzlW();
    boolean bool;
    String[] arrayOfString;
    if ((paramList != null) && (paramList.size() > 0))
    {
      bool = true;
      zzv.zzb(bool, "geofenceRequestIds can't be null nor empty.");
      zzv.zzb(paramzzb, "OnRemoveGeofencesResultListener not provided.");
      arrayOfString = (String[])paramList.toArray(new String[0]);
      if (paramzzb != null)
        break label83;
    }
    label83: for (Object localObject = null; ; localObject = new zzb(paramzzb, this))
    {
      ((zzg)zzlX()).zza(arrayOfString, (zzf)localObject, getContext().getPackageName());
      return;
      bool = false;
      break;
    }
  }

  public void zzb(Location paramLocation)
    throws RemoteException
  {
    this.zzara.zzb(paramLocation);
  }

  public void zzb(LocationRequest paramLocationRequest, PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzara.zzb(paramLocationRequest, paramPendingIntent);
  }

  public void zzd(PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzara.zzd(paramPendingIntent);
  }

  public boolean zzlZ()
  {
    return true;
  }

  public LocationAvailability zzsI()
  {
    return this.zzara.zzsI();
  }

  private final class zza extends com.google.android.gms.common.internal.zzi<zzg>.zzc<zze.zza>
  {
    private final int zzOJ;
    private final String[] zzarc;

    public zza(zze.zza paramInt, int paramArrayOfString, String[] arg4)
    {
      super(paramInt);
      this.zzOJ = LocationStatusCodes.zzfE(paramArrayOfString);
      Object localObject;
      this.zzarc = localObject;
    }

    protected void zza(zze.zza paramzza)
    {
      if (paramzza != null)
        paramzza.zza(this.zzOJ, this.zzarc);
    }

    protected void zzmb()
    {
    }
  }

  private static final class zzb extends zzf.zza
  {
    private zze.zza zzare;
    private zze.zzb zzarf;
    private zzj zzarg;

    public zzb(zze.zza paramzza, zzj paramzzj)
    {
      this.zzare = paramzza;
      this.zzarf = null;
      this.zzarg = paramzzj;
    }

    public zzb(zze.zzb paramzzb, zzj paramzzj)
    {
      this.zzarf = paramzzb;
      this.zzare = null;
      this.zzarg = paramzzj;
    }

    public void zza(int paramInt, PendingIntent paramPendingIntent)
    {
      if (this.zzarg == null)
      {
        Log.wtf("LocationClientImpl", "onRemoveGeofencesByPendingIntentResult called multiple times");
        return;
      }
      zzj localzzj1 = this.zzarg;
      zzj localzzj2 = this.zzarg;
      localzzj2.getClass();
      localzzj1.zza(new zzj.zzc(localzzj2, 1, this.zzarf, paramInt, paramPendingIntent));
      this.zzarg = null;
      this.zzare = null;
      this.zzarf = null;
    }

    public void zza(int paramInt, String[] paramArrayOfString)
      throws RemoteException
    {
      if (this.zzarg == null)
      {
        Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
        return;
      }
      zzj localzzj1 = this.zzarg;
      zzj localzzj2 = this.zzarg;
      localzzj2.getClass();
      localzzj1.zza(new zzj.zza(localzzj2, this.zzare, paramInt, paramArrayOfString));
      this.zzarg = null;
      this.zzare = null;
      this.zzarf = null;
    }

    public void zzb(int paramInt, String[] paramArrayOfString)
    {
      if (this.zzarg == null)
      {
        Log.wtf("LocationClientImpl", "onRemoveGeofencesByRequestIdsResult called multiple times");
        return;
      }
      zzj localzzj1 = this.zzarg;
      zzj localzzj2 = this.zzarg;
      localzzj2.getClass();
      localzzj1.zza(new zzj.zzc(localzzj2, 2, this.zzarf, paramInt, paramArrayOfString));
      this.zzarg = null;
      this.zzare = null;
      this.zzarf = null;
    }
  }

  private final class zzc extends com.google.android.gms.common.internal.zzi<zzg>.zzc<zze.zzb>
  {
    private final PendingIntent mPendingIntent;
    private final int zzOJ;
    private final String[] zzarc;
    private final int zzarh;

    public zzc(int paramzzb, zze.zzb paramInt1, int paramPendingIntent, PendingIntent arg5)
    {
      super(paramInt1);
      if (paramzzb == localzzb);
      while (true)
      {
        com.google.android.gms.common.internal.zzb.zzP(localzzb);
        this.zzarh = paramzzb;
        this.zzOJ = LocationStatusCodes.zzfE(paramPendingIntent);
        Object localObject;
        this.mPendingIntent = localObject;
        this.zzarc = null;
        return;
        int i = 0;
      }
    }

    public zzc(int paramzzb, zze.zzb paramInt1, int paramArrayOfString, String[] arg5)
    {
      super(paramInt1);
      if (paramzzb == 2);
      for (boolean bool = true; ; bool = false)
      {
        com.google.android.gms.common.internal.zzb.zzP(bool);
        this.zzarh = paramzzb;
        this.zzOJ = LocationStatusCodes.zzfE(paramArrayOfString);
        Object localObject;
        this.zzarc = localObject;
        this.mPendingIntent = null;
        return;
      }
    }

    protected void zza(zze.zzb paramzzb)
    {
      if (paramzzb != null);
      switch (this.zzarh)
      {
      default:
        Log.wtf("LocationClientImpl", "Unsupported action: " + this.zzarh);
        return;
      case 1:
        paramzzb.zza(this.zzOJ, this.mPendingIntent);
        return;
      case 2:
      }
      paramzzb.zzb(this.zzOJ, this.zzarc);
    }

    protected void zzmb()
    {
    }
  }

  private static final class zzd extends zzh.zza
  {
    private zza.zzb<LocationSettingsResult> zzari;

    public zzd(zza.zzb<LocationSettingsResult> paramzzb)
    {
      if (paramzzb != null);
      for (boolean bool = true; ; bool = false)
      {
        zzv.zzb(bool, "listener can't be null.");
        this.zzari = paramzzb;
        return;
      }
    }

    public void zza(LocationSettingsResult paramLocationSettingsResult)
      throws RemoteException
    {
      this.zzari.zzj(paramLocationSettingsResult);
      this.zzari = null;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzj
 * JD-Core Version:    0.6.2
 */