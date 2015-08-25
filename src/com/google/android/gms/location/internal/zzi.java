package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class zzi
{
  private final Context mContext;
  private Map<LocationListener, zzi.zzc> zzadz = new HashMap();
  private ContentProviderClient zzaqU = null;
  private boolean zzaqV = false;
  private Map<LocationCallback, zzi.zza> zzaqW = new HashMap();
  private final zzn<zzg> zzaqz;

  public zzi(Context paramContext, zzn<zzg> paramzzn)
  {
    this.mContext = paramContext;
    this.zzaqz = paramzzn;
  }

  private zzi.zza zza(LocationCallback paramLocationCallback, Looper paramLooper)
  {
    synchronized (this.zzadz)
    {
      zzi.zza localzza = (zzi.zza)this.zzaqW.get(paramLocationCallback);
      if (localzza == null)
        localzza = new zzi.zza(paramLocationCallback, paramLooper);
      this.zzaqW.put(paramLocationCallback, localzza);
      return localzza;
    }
  }

  private zzi.zzc zza(LocationListener paramLocationListener, Looper paramLooper)
  {
    synchronized (this.zzadz)
    {
      zzi.zzc localzzc = (zzi.zzc)this.zzadz.get(paramLocationListener);
      if (localzzc == null)
        localzzc = new zzi.zzc(paramLocationListener, paramLooper);
      this.zzadz.put(paramLocationListener, localzzc);
      return localzzc;
    }
  }

  public Location getLastLocation()
  {
    this.zzaqz.zzlW();
    try
    {
      Location localLocation = ((zzg)this.zzaqz.zzlX()).zzcF(this.mContext.getPackageName());
      return localLocation;
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
  }

  public void removeAllListeners()
  {
    try
    {
      synchronized (this.zzadz)
      {
        Iterator localIterator1 = this.zzadz.values().iterator();
        while (localIterator1.hasNext())
        {
          zzi.zzc localzzc = (zzi.zzc)localIterator1.next();
          if (localzzc != null)
            ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zzb(localzzc));
        }
      }
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
    this.zzadz.clear();
    Iterator localIterator2 = this.zzaqW.values().iterator();
    while (localIterator2.hasNext())
    {
      zzi.zza localzza = (zzi.zza)localIterator2.next();
      if (localzza != null)
        ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zza(localzza));
    }
    this.zzaqW.clear();
  }

  public void zzX(boolean paramBoolean)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    ((zzg)this.zzaqz.zzlX()).zzX(paramBoolean);
    this.zzaqV = paramBoolean;
  }

  public void zza(LocationCallback paramLocationCallback)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    zzv.zzb(paramLocationCallback, "Invalid null callback");
    synchronized (this.zzaqW)
    {
      zzi.zza localzza = (zzi.zza)this.zzaqW.remove(paramLocationCallback);
      if (localzza != null)
      {
        localzza.release();
        ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zza(localzza));
      }
      return;
    }
  }

  public void zza(LocationListener paramLocationListener)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    zzv.zzb(paramLocationListener, "Invalid null listener");
    synchronized (this.zzadz)
    {
      zzi.zzc localzzc = (zzi.zzc)this.zzadz.remove(paramLocationListener);
      if ((this.zzaqU != null) && (this.zzadz.isEmpty()))
      {
        this.zzaqU.release();
        this.zzaqU = null;
      }
      if (localzzc != null)
      {
        localzzc.release();
        ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zzb(localzzc));
      }
      return;
    }
  }

  public void zza(LocationRequest paramLocationRequest, LocationListener paramLocationListener, Looper paramLooper)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    zzi.zzc localzzc = zza(paramLocationListener, paramLooper);
    ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zzb(LocationRequestInternal.zzb(paramLocationRequest), localzzc));
  }

  public void zza(LocationRequestInternal paramLocationRequestInternal, LocationCallback paramLocationCallback, Looper paramLooper)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    zzi.zza localzza = zza(paramLocationCallback, paramLooper);
    ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zza(paramLocationRequestInternal, localzza));
  }

  public void zzb(Location paramLocation)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    ((zzg)this.zzaqz.zzlX()).zzb(paramLocation);
  }

  public void zzb(LocationRequest paramLocationRequest, PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zzb(LocationRequestInternal.zzb(paramLocationRequest), paramPendingIntent));
  }

  public void zzd(PendingIntent paramPendingIntent)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zze(paramPendingIntent));
  }

  public LocationAvailability zzsI()
  {
    this.zzaqz.zzlW();
    try
    {
      LocationAvailability localLocationAvailability = ((zzg)this.zzaqz.zzlX()).zzcG(this.mContext.getPackageName());
      return localLocationAvailability;
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
  }

  public void zzsJ()
  {
    if (this.zzaqV);
    try
    {
      zzX(false);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new IllegalStateException(localRemoteException);
    }
  }

  private static class zzb extends Handler
  {
    private final LocationListener zzaqZ;

    public zzb(LocationListener paramLocationListener)
    {
      this.zzaqZ = paramLocationListener;
    }

    public zzb(LocationListener paramLocationListener, Looper paramLooper)
    {
      super();
      this.zzaqZ = paramLocationListener;
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
        return;
      case 1:
      }
      Location localLocation = new Location((Location)paramMessage.obj);
      this.zzaqZ.onLocationChanged(localLocation);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzi
 * JD-Core Version:    0.6.2
 */