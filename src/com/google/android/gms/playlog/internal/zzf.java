package com.google.android.gms.playlog.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzny;
import java.util.ArrayList;
import java.util.Iterator;

public class zzf extends zzi<zza>
{
  private final String zzJd;
  private final zzd zzayI;
  private final zzb zzayJ;
  private boolean zzayK;
  private final Object zzoe;

  public zzf(Context paramContext, Looper paramLooper, zzd paramzzd, zze paramzze)
  {
    super(paramContext, paramLooper, 24, paramzzd, paramzzd, paramzze);
    this.zzJd = paramContext.getPackageName();
    this.zzayI = ((zzd)zzv.zzr(paramzzd));
    this.zzayI.zza(this);
    this.zzayJ = new zzb();
    this.zzoe = new Object();
    this.zzayK = true;
  }

  private void zzc(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    this.zzayJ.zza(paramPlayLoggerContext, paramLogEvent);
  }

  private void zzd(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    try
    {
      zzvt();
      ((zza)zzlX()).zza(this.zzJd, paramPlayLoggerContext, paramLogEvent);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e("PlayLoggerImpl", "Couldn't send log event.  Will try caching.");
      zzc(paramPlayLoggerContext, paramLogEvent);
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e("PlayLoggerImpl", "Service was disconnected.  Will try caching.");
      zzc(paramPlayLoggerContext, paramLogEvent);
    }
  }

  private void zzvt()
  {
    boolean bool;
    if (!this.zzayK)
    {
      bool = true;
      com.google.android.gms.common.internal.zzb.zzP(bool);
      if (this.zzayJ.isEmpty());
    }
    Object localObject2;
    for (Object localObject1 = null; ; localObject1 = localObject2)
    {
      ArrayList localArrayList;
      zzb.zza localzza;
      try
      {
        localArrayList = new ArrayList();
        Iterator localIterator = this.zzayJ.zzvr().iterator();
        while (true)
        {
          if (!localIterator.hasNext())
            break label204;
          localzza = (zzb.zza)localIterator.next();
          if (localzza.zzayy == null)
            break;
          ((zza)zzlX()).zza(this.zzJd, localzza.zzayw, zzny.zzf(localzza.zzayy));
        }
      }
      catch (RemoteException localRemoteException)
      {
        Log.e("PlayLoggerImpl", "Couldn't send cached log events to AndroidLog service.  Retaining in memory cache.");
      }
      return;
      bool = false;
      break;
      if (localzza.zzayw.equals(localObject1))
      {
        localArrayList.add(localzza.zzayx);
        localObject2 = localObject1;
      }
      else
      {
        if (!localArrayList.isEmpty())
        {
          ((zza)zzlX()).zza(this.zzJd, localObject1, localArrayList);
          localArrayList.clear();
        }
        PlayLoggerContext localPlayLoggerContext = localzza.zzayw;
        localArrayList.add(localzza.zzayx);
        localObject2 = localPlayLoggerContext;
        continue;
        label204: if (!localArrayList.isEmpty())
          ((zza)zzlX()).zza(this.zzJd, localObject1, localArrayList);
        this.zzayJ.clear();
        return;
      }
    }
  }

  public void start()
  {
    synchronized (this.zzoe)
    {
      if ((isConnecting()) || (isConnected()))
        return;
      this.zzayI.zzae(true);
      connect();
      return;
    }
  }

  public void stop()
  {
    synchronized (this.zzoe)
    {
      this.zzayI.zzae(false);
      disconnect();
      return;
    }
  }

  void zzaf(boolean paramBoolean)
  {
    synchronized (this.zzoe)
    {
      boolean bool = this.zzayK;
      this.zzayK = paramBoolean;
      if ((bool) && (!this.zzayK))
        zzvt();
      return;
    }
  }

  public void zzb(PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
  {
    synchronized (this.zzoe)
    {
      if (this.zzayK)
      {
        zzc(paramPlayLoggerContext, paramLogEvent);
        return;
      }
      zzd(paramPlayLoggerContext, paramLogEvent);
    }
  }

  protected zza zzcu(IBinder paramIBinder)
  {
    return zza.zza.zzct(paramIBinder);
  }

  protected String zzeq()
  {
    return "com.google.android.gms.playlog.service.START";
  }

  protected String zzer()
  {
    return "com.google.android.gms.playlog.internal.IPlayLogService";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.zzf
 * JD-Core Version:    0.6.2
 */