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
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzc.zza;
import com.google.android.gms.location.zzd.zza;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class zzi
{
  private final Context mContext;
  private Map<LocationListener, zzc> zzadz = new HashMap();
  private ContentProviderClient zzaqU = null;
  private boolean zzaqV = false;
  private Map<LocationCallback, zza> zzaqW = new HashMap();
  private final zzn<zzg> zzaqz;

  public zzi(Context paramContext, zzn<zzg> paramzzn)
  {
    this.mContext = paramContext;
    this.zzaqz = paramzzn;
  }

  private zza zza(LocationCallback paramLocationCallback, Looper paramLooper)
  {
    synchronized (this.zzadz)
    {
      zza localzza = (zza)this.zzaqW.get(paramLocationCallback);
      if (localzza == null)
        localzza = new zza(paramLocationCallback, paramLooper);
      this.zzaqW.put(paramLocationCallback, localzza);
      return localzza;
    }
  }

  private zzc zza(LocationListener paramLocationListener, Looper paramLooper)
  {
    synchronized (this.zzadz)
    {
      zzc localzzc = (zzc)this.zzadz.get(paramLocationListener);
      if (localzzc == null)
        localzzc = new zzc(paramLocationListener, paramLooper);
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
          zzc localzzc = (zzc)localIterator1.next();
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
      zza localzza = (zza)localIterator2.next();
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
      zza localzza = (zza)this.zzaqW.remove(paramLocationCallback);
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
      zzc localzzc = (zzc)this.zzadz.remove(paramLocationListener);
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
    zzc localzzc = zza(paramLocationListener, paramLooper);
    ((zzg)this.zzaqz.zzlX()).zza(LocationRequestUpdateData.zzb(LocationRequestInternal.zzb(paramLocationRequest), localzzc));
  }

  public void zza(LocationRequestInternal paramLocationRequestInternal, LocationCallback paramLocationCallback, Looper paramLooper)
    throws RemoteException
  {
    this.zzaqz.zzlW();
    zza localzza = zza(paramLocationCallback, paramLooper);
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

  private static class zza extends zzc.zza
  {
    private Handler zzaqX;

    zza(final LocationCallback paramLocationCallback, Looper paramLooper)
    {
      if (paramLooper == null)
      {
        paramLooper = Looper.myLooper();
        if (paramLooper == null)
          break label39;
      }
      label39: for (boolean bool = true; ; bool = false)
      {
        zzv.zza(bool, "Can't create handler inside thread that has not called Looper.prepare()");
        this.zzaqX = new Handler(paramLooper)
        {
          public void handleMessage(Message paramAnonymousMessage)
          {
            switch (paramAnonymousMessage.what)
            {
            default:
              return;
            case 0:
              paramLocationCallback.onLocationResult((LocationResult)paramAnonymousMessage.obj);
              return;
            case 1:
            }
            paramLocationCallback.onLocationAvailability((LocationAvailability)paramAnonymousMessage.obj);
          }
        };
        return;
      }
    }

    private void zzb(int paramInt, Object paramObject)
    {
      if (this.zzaqX == null)
      {
        Log.e("LocationClientHelper", "Received a data in client after calling removeLocationUpdates.");
        return;
      }
      Message localMessage = Message.obtain();
      localMessage.what = paramInt;
      localMessage.obj = paramObject;
      this.zzaqX.sendMessage(localMessage);
    }

    public void onLocationAvailability(LocationAvailability paramLocationAvailability)
    {
      zzb(1, paramLocationAvailability);
    }

    public void onLocationResult(LocationResult paramLocationResult)
    {
      zzb(0, paramLocationResult);
    }

    public void release()
    {
      this.zzaqX = null;
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

  private static class zzc extends zzd.zza
  {
    private Handler zzaqX;

    zzc(LocationListener paramLocationListener, Looper paramLooper)
    {
      boolean bool;
      if (paramLooper == null)
      {
        if (Looper.myLooper() != null)
        {
          bool = true;
          zzv.zza(bool, "Can't create handler inside thread that has not called Looper.prepare()");
        }
      }
      else
        if (paramLooper != null)
          break label49;
      label49: for (zzi.zzb localzzb = new zzi.zzb(paramLocationListener); ; localzzb = new zzi.zzb(paramLocationListener, paramLooper))
      {
        this.zzaqX = localzzb;
        return;
        bool = false;
        break;
      }
    }

    public void onLocationChanged(Location paramLocation)
    {
      if (this.zzaqX == null)
      {
        Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
        return;
      }
      Message localMessage = Message.obtain();
      localMessage.what = 1;
      localMessage.obj = paramLocation;
      this.zzaqX.sendMessage(localMessage);
    }

    public void release()
    {
      this.zzaqX = null;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzi
 * JD-Core Version:    0.6.2
 */