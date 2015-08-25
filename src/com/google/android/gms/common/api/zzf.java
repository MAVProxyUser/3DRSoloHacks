package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;

public class zzf
  implements zzh
{
  private final zzg zzPQ;

  public zzf(zzg paramzzg)
  {
    this.zzPQ = paramzzg;
  }

  public void begin()
  {
    this.zzPQ.zzkY();
  }

  public void connect()
  {
    this.zzPQ.zzkZ();
  }

  public String getName()
  {
    return "DISCONNECTED";
  }

  public void onConnected(Bundle paramBundle)
  {
  }

  public <A extends Api.zza, R extends Result, T extends zza.zza<R, A>> T zza(T paramT)
  {
    this.zzPQ.zzQt.add(paramT);
    return paramT;
  }

  public void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
  }

  public void zzas(int paramInt)
  {
    int i;
    if (paramInt == -1)
      i = 1;
    while (i != 0)
    {
      Iterator localIterator = this.zzPQ.zzQt.iterator();
      while (true)
        if (localIterator.hasNext())
        {
          ((zzg.zze)localIterator.next()).cancel();
          continue;
          i = 0;
          break;
        }
      this.zzPQ.zzQt.clear();
      this.zzPQ.zzkX();
      this.zzPQ.zzQA.clear();
    }
  }

  public <A extends Api.zza, T extends zza.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new IllegalStateException("GoogleApiClient is not connected yet.");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzf
 * JD-Core Version:    0.6.2
 */