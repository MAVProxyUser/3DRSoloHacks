package com.google.android.gms.common.api;

import android.os.Bundle;
import android.os.DeadObjectException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zzj;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class zzd
  implements zzh
{
  private final zzg zzPQ;

  public zzd(zzg paramzzg)
  {
    this.zzPQ = paramzzg;
  }

  private <A extends Api.zza> void zza(zzg.zze<A> paramzze)
    throws DeadObjectException
  {
    this.zzPQ.zzb(paramzze);
    Api.zza localzza = this.zzPQ.zza(paramzze.zzkF());
    if ((!localzza.isConnected()) && (this.zzPQ.zzQA.containsKey(paramzze.zzkF())))
    {
      paramzze.zzk(new Status(17));
      return;
    }
    paramzze.zzb(localzza);
  }

  public void begin()
  {
    while (!this.zzPQ.zzQt.isEmpty())
      try
      {
        zza((zzg.zze)this.zzPQ.zzQt.remove());
      }
      catch (DeadObjectException localDeadObjectException)
      {
        Log.w("GoogleApiClientConnected", "Service died while flushing queue", localDeadObjectException);
      }
  }

  public void connect()
  {
  }

  public String getName()
  {
    return "CONNECTED";
  }

  public void onConnected(Bundle paramBundle)
  {
  }

  public <A extends Api.zza, R extends Result, T extends zza.zza<R, A>> T zza(T paramT)
  {
    return zzb(paramT);
  }

  public void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
  }

  public void zzas(int paramInt)
  {
    int i;
    if (paramInt == -1)
    {
      i = 1;
      if (i == 0)
        break label69;
      this.zzPQ.zzkX();
      this.zzPQ.zzQA.clear();
    }
    while (true)
    {
      this.zzPQ.zzg(null);
      if (i == 0)
        this.zzPQ.zzQs.zzaP(paramInt);
      this.zzPQ.zzQs.zzmf();
      return;
      i = 0;
      break;
      label69: Iterator localIterator = this.zzPQ.zzQF.iterator();
      while (localIterator.hasNext())
        ((zzg.zze)localIterator.next()).forceFailureUnlessReady(new Status(8, "The connection to Google Play services was lost"));
    }
  }

  public <A extends Api.zza, T extends zza.zza<? extends Result, A>> T zzb(T paramT)
  {
    try
    {
      zza(paramT);
      return paramT;
    }
    catch (DeadObjectException localDeadObjectException)
    {
      zzas(1);
    }
    return paramT;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzd
 * JD-Core Version:    0.6.2
 */