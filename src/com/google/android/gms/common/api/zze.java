package com.google.android.gms.common.api;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.internal.zzr.zza;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme;
import com.google.android.gms.signin.internal.AuthAccountResult;
import com.google.android.gms.signin.internal.zzb;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class zze
  implements zzh
{
  private final Context mContext;
  private final Api.zzb<? extends zzmd, zzme> zzPK;
  private final zzg zzPQ;
  private final Lock zzPR;
  private ConnectionResult zzPS;
  private int zzPT;
  private int zzPU = 0;
  private boolean zzPV = false;
  private int zzPW;
  private final Bundle zzPX = new Bundle();
  private final Set<Api.zzc> zzPY = new HashSet();
  private zzmd zzPZ;
  private int zzQa;
  private boolean zzQb;
  private boolean zzQc;
  private zzo zzQd;
  private boolean zzQe;
  private boolean zzQf;
  private final com.google.android.gms.common.internal.zze zzQg;
  private final Map<Api<?>, Integer> zzQh;

  public zze(zzg paramzzg, com.google.android.gms.common.internal.zze paramzze, Map<Api<?>, Integer> paramMap, Api.zzb<? extends zzmd, zzme> paramzzb, Lock paramLock, Context paramContext)
  {
    this.zzPQ = paramzzg;
    this.zzQg = paramzze;
    this.zzQh = paramMap;
    this.zzPK = paramzzb;
    this.zzPR = paramLock;
    this.mContext = paramContext;
  }

  private void zzO(boolean paramBoolean)
  {
    if (this.zzPZ != null)
    {
      if (this.zzPZ.isConnected())
      {
        if (paramBoolean)
          this.zzPZ.zzwe();
        this.zzPZ.disconnect();
      }
      this.zzQd = null;
    }
  }

  private void zza(ResolveAccountResponse paramResolveAccountResponse)
  {
    ConnectionResult localConnectionResult = paramResolveAccountResponse.zzmn();
    this.zzPR.lock();
    while (true)
    {
      try
      {
        boolean bool = zzat(0);
        if (!bool)
          return;
        if (localConnectionResult.isSuccess())
        {
          this.zzQd = paramResolveAccountResponse.zzmm();
          this.zzQc = true;
          this.zzQe = paramResolveAccountResponse.zzmo();
          this.zzQf = paramResolveAccountResponse.zzmp();
          zzkR();
          return;
        }
        if (zze(localConnectionResult))
        {
          zzkV();
          if (this.zzPW != 0)
            continue;
          zzkT();
          continue;
        }
      }
      finally
      {
        this.zzPR.unlock();
      }
      zzf(localConnectionResult);
    }
  }

  private boolean zza(int paramInt1, int paramInt2, ConnectionResult paramConnectionResult)
  {
    if ((paramInt2 == 1) && (!zzd(paramConnectionResult)));
    while ((this.zzPS != null) && (paramInt1 >= this.zzPT))
      return false;
    return true;
  }

  private boolean zzat(int paramInt)
  {
    if (this.zzPU != paramInt)
    {
      Log.wtf("GoogleApiClientConnecting", "GoogleApiClient connecting is in step " + zzau(this.zzPU) + " but received callback for step " + zzau(paramInt));
      zzf(new ConnectionResult(8, null));
      return false;
    }
    return true;
  }

  private String zzau(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return "UNKNOWN";
    case 0:
      return "STEP_GETTING_SERVICE_BINDINGS";
    case 1:
      return "STEP_VALIDATING_ACCOUNT";
    case 2:
      return "STEP_AUTHENTICATING";
    case 3:
    }
    return "STEP_GETTING_REMOTE_SERVICE";
  }

  private void zzb(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
    if (paramInt != 2)
    {
      int i = paramApi.zzkC().getPriority();
      if (zza(i, paramInt, paramConnectionResult))
      {
        this.zzPS = paramConnectionResult;
        this.zzPT = i;
      }
    }
    this.zzPQ.zzQA.put(paramApi.zzkF(), paramConnectionResult);
  }

  private void zzc(ConnectionResult paramConnectionResult)
  {
    this.zzPR.lock();
    while (true)
    {
      try
      {
        boolean bool = zzat(2);
        if (!bool)
          return;
        if (paramConnectionResult.isSuccess())
        {
          zzkT();
          return;
        }
        if (zze(paramConnectionResult))
        {
          zzkV();
          zzkT();
          continue;
        }
      }
      finally
      {
        this.zzPR.unlock();
      }
      zzf(paramConnectionResult);
    }
  }

  private static boolean zzd(ConnectionResult paramConnectionResult)
  {
    if (paramConnectionResult.hasResolution());
    while (GooglePlayServicesUtil.zzar(paramConnectionResult.getErrorCode()) != null)
      return true;
    return false;
  }

  private boolean zze(ConnectionResult paramConnectionResult)
  {
    return (this.zzQa == 2) || ((this.zzQa == 1) && (!paramConnectionResult.hasResolution()));
  }

  private void zzf(ConnectionResult paramConnectionResult)
  {
    this.zzPV = false;
    this.zzPQ.zzQB.clear();
    boolean bool1 = paramConnectionResult.hasResolution();
    boolean bool2 = false;
    if (!bool1)
      bool2 = true;
    zzO(bool2);
    zzas(3);
    if ((!this.zzPQ.zzlb()) || (!GooglePlayServicesUtil.zze(this.mContext, paramConnectionResult.getErrorCode())))
    {
      this.zzPQ.zzld();
      this.zzPQ.zzQs.zzj(paramConnectionResult);
    }
    this.zzPQ.zzQs.zzmf();
  }

  private boolean zzkP()
  {
    this.zzPW = (-1 + this.zzPW);
    if (this.zzPW > 0)
      return false;
    if (this.zzPS != null)
    {
      zzf(this.zzPS);
      return false;
    }
    return true;
  }

  private void zzkQ()
  {
    if (this.zzQb)
    {
      zzkR();
      return;
    }
    zzkT();
  }

  private void zzkR()
  {
    if ((this.zzQc) && (this.zzPW == 0))
    {
      this.zzPU = 1;
      this.zzPW = this.zzPQ.zzQz.size();
      Iterator localIterator = this.zzPQ.zzQz.keySet().iterator();
      while (localIterator.hasNext())
      {
        Api.zzc localzzc = (Api.zzc)localIterator.next();
        if (this.zzPQ.zzQA.containsKey(localzzc))
        {
          if (zzkP())
            zzkS();
        }
        else
          ((Api.zza)this.zzPQ.zzQz.get(localzzc)).zza(this.zzQd);
      }
    }
  }

  private void zzkS()
  {
    this.zzPU = 2;
    this.zzPQ.zzQB = zzkW();
    this.zzPZ.zza(this.zzQd, this.zzPQ.zzQB, new zza(this));
  }

  private void zzkT()
  {
    Set localSet1 = this.zzPQ.zzQB;
    if (localSet1.isEmpty());
    for (Set localSet2 = zzkW(); ; localSet2 = localSet1)
    {
      this.zzPU = 3;
      this.zzPW = this.zzPQ.zzQz.size();
      Iterator localIterator = this.zzPQ.zzQz.keySet().iterator();
      while (localIterator.hasNext())
      {
        Api.zzc localzzc = (Api.zzc)localIterator.next();
        if (this.zzPQ.zzQA.containsKey(localzzc))
        {
          if (zzkP())
            zzkU();
        }
        else
          ((Api.zza)this.zzPQ.zzQz.get(localzzc)).zza(this.zzQd, localSet2);
      }
      return;
    }
  }

  private void zzkU()
  {
    this.zzPQ.zzla();
    if (this.zzPZ != null)
    {
      if (this.zzQe)
        this.zzPZ.zza(this.zzQd, this.zzQf);
      zzO(false);
    }
    Iterator localIterator = this.zzPQ.zzQA.keySet().iterator();
    while (localIterator.hasNext())
    {
      Api.zzc localzzc = (Api.zzc)localIterator.next();
      ((Api.zza)this.zzPQ.zzQz.get(localzzc)).disconnect();
    }
    if (this.zzPV)
    {
      this.zzPV = false;
      zzas(-1);
      return;
    }
    if (this.zzPX.isEmpty());
    for (Bundle localBundle = null; ; localBundle = this.zzPX)
    {
      this.zzPQ.zzQs.zzg(localBundle);
      return;
    }
  }

  private void zzkV()
  {
    this.zzQb = false;
    this.zzPQ.zzQB.clear();
    Iterator localIterator = this.zzPY.iterator();
    while (localIterator.hasNext())
    {
      Api.zzc localzzc = (Api.zzc)localIterator.next();
      if (!this.zzPQ.zzQA.containsKey(localzzc))
        this.zzPQ.zzQA.put(localzzc, new ConnectionResult(17, null));
    }
  }

  private Set<Scope> zzkW()
  {
    HashSet localHashSet = new HashSet(this.zzQg.zzlG());
    Map localMap = this.zzQg.zzlI();
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Api localApi = (Api)localIterator.next();
      if (!this.zzPQ.zzQA.containsKey(localApi.zzkF()))
        localHashSet.addAll(((com.google.android.gms.common.internal.zze.zza)localMap.get(localApi)).zzPP);
    }
    return localHashSet;
  }

  public void begin()
  {
    this.zzPQ.zzQs.zzmg();
    this.zzPQ.zzQA.clear();
    this.zzPV = false;
    this.zzQb = false;
    this.zzPS = null;
    this.zzPU = 0;
    this.zzQa = 2;
    this.zzQc = false;
    this.zzQe = false;
    Iterator localIterator1 = this.zzQh.keySet().iterator();
    int i = 0;
    if (localIterator1.hasNext())
    {
      Api localApi = (Api)localIterator1.next();
      Api.zza localzza = (Api.zza)this.zzPQ.zzQz.get(localApi.zzkF());
      int j = ((Integer)this.zzQh.get(localApi)).intValue();
      localzza.zza(new zzc(this, localApi, j));
      if (localApi.zzkC().getPriority() == 1);
      for (int k = 1; ; k = 0)
      {
        int m = k | i;
        if (localzza.zzjM())
        {
          this.zzQb = true;
          if (j < this.zzQa)
            this.zzQa = j;
          if (j != 0)
            this.zzPY.add(localApi.zzkF());
        }
        i = m;
        break;
      }
    }
    if (i != 0)
      this.zzQb = false;
    if (this.zzQb)
    {
      this.zzQg.zza(Integer.valueOf(this.zzPQ.getSessionId()));
      zzd localzzd = new zzd(null);
      this.zzPZ = ((zzmd)this.zzPK.zza(this.mContext, this.zzPQ.getLooper(), this.zzQg, this.zzQg.zzlM(), localzzd, localzzd));
      this.zzPZ.connect();
    }
    this.zzPW = this.zzPQ.zzQz.size();
    Iterator localIterator2 = this.zzPQ.zzQz.values().iterator();
    while (localIterator2.hasNext())
      ((Api.zza)localIterator2.next()).connect();
  }

  public void connect()
  {
    this.zzPV = false;
  }

  public String getName()
  {
    return "CONNECTING";
  }

  public void onConnected(Bundle paramBundle)
  {
    if (!zzat(3));
    do
    {
      return;
      if (paramBundle != null)
        this.zzPX.putAll(paramBundle);
    }
    while (!zzkP());
    zzkU();
  }

  public <A extends Api.zza, R extends Result, T extends zza.zza<R, A>> T zza(T paramT)
  {
    this.zzPQ.zzQt.add(paramT);
    return paramT;
  }

  public void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt)
  {
    if (!zzat(3));
    do
    {
      return;
      zzb(paramConnectionResult, paramApi, paramInt);
    }
    while (!zzkP());
    zzkU();
  }

  public void zzas(int paramInt)
  {
    if (paramInt == -1)
    {
      Iterator localIterator = this.zzPQ.zzQt.iterator();
      while (localIterator.hasNext())
      {
        zzg.zze localzze = (zzg.zze)localIterator.next();
        if (localzze.zzkI() != 1)
        {
          localzze.cancel();
          localIterator.remove();
        }
      }
      this.zzPQ.zzkX();
      if ((this.zzPS == null) && (!this.zzPQ.zzQt.isEmpty()))
      {
        this.zzPV = true;
        return;
      }
      this.zzPQ.zzQA.clear();
      this.zzPS = null;
      zzO(true);
    }
    this.zzPQ.zzg(this.zzPS);
  }

  public <A extends Api.zza, T extends zza.zza<? extends Result, A>> T zzb(T paramT)
  {
    throw new IllegalStateException("GoogleApiClient is not connected yet.");
  }

  private static class zza extends zzb
  {
    private final WeakReference<zze> zzQi;

    zza(zze paramzze)
    {
      this.zzQi = new WeakReference(paramzze);
    }

    public void zza(final ConnectionResult paramConnectionResult, AuthAccountResult paramAuthAccountResult)
    {
      final zze localzze = (zze)this.zzQi.get();
      if (localzze == null)
        return;
      zze.zza(localzze).zzQx.post(new Runnable()
      {
        public void run()
        {
          zze.zzc(localzze, paramConnectionResult);
        }
      });
    }
  }

  private static class zzb extends zzr.zza
  {
    private final WeakReference<zze> zzQi;

    zzb(zze paramzze)
    {
      this.zzQi = new WeakReference(paramzze);
    }

    public void zzb(final ResolveAccountResponse paramResolveAccountResponse)
    {
      final zze localzze = (zze)this.zzQi.get();
      if (localzze == null)
        return;
      zze.zza(localzze).zzQx.post(new Runnable()
      {
        public void run()
        {
          zze.zza(localzze, paramResolveAccountResponse);
        }
      });
    }
  }

  private static class zzc
    implements GoogleApiClient.zza
  {
    private final WeakReference<zze> zzQi;
    private final Api<?> zzQo;
    private final int zzQp;

    public zzc(zze paramzze, Api<?> paramApi, int paramInt)
    {
      this.zzQi = new WeakReference(paramzze);
      this.zzQo = paramApi;
      this.zzQp = paramInt;
    }

    public void zza(ConnectionResult paramConnectionResult)
    {
      zze localzze = (zze)this.zzQi.get();
      if (localzze == null)
        return;
      Looper localLooper1 = Looper.myLooper();
      Looper localLooper2 = zze.zza(localzze).getLooper();
      boolean bool1 = false;
      if (localLooper1 == localLooper2)
        bool1 = true;
      zzv.zza(bool1, "onReportServiceBinding must be called on the GoogleApiClient handler thread");
      zze.zzc(localzze).lock();
      try
      {
        boolean bool2 = zze.zza(localzze, 0);
        if (!bool2)
          return;
        if (!paramConnectionResult.isSuccess())
          zze.zza(localzze, paramConnectionResult, this.zzQo, this.zzQp);
        if (zze.zzf(localzze))
          zze.zzg(localzze);
        return;
      }
      finally
      {
        zze.zzc(localzze).unlock();
      }
    }

    public void zzb(ConnectionResult paramConnectionResult)
    {
      boolean bool1 = true;
      zze localzze = (zze)this.zzQi.get();
      if (localzze == null)
        return;
      if (Looper.myLooper() == zze.zza(localzze).getLooper());
      while (true)
      {
        zzv.zza(bool1, "onReportAccountValidation must be called on the GoogleApiClient handler thread");
        zze.zzc(localzze).lock();
        try
        {
          boolean bool2 = zze.zza(localzze, 1);
          if (!bool2)
          {
            return;
            bool1 = false;
            continue;
          }
          if (!paramConnectionResult.isSuccess())
            zze.zza(localzze, paramConnectionResult, this.zzQo, this.zzQp);
          if (zze.zzf(localzze))
            zze.zzh(localzze);
          return;
        }
        finally
        {
          zze.zzc(localzze).unlock();
        }
      }
    }
  }

  private class zzd
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
  {
    private zzd()
    {
    }

    public void onConnected(Bundle paramBundle)
    {
      zze.zzb(zze.this).zza(new zze.zzb(zze.this));
    }

    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      zze.zzc(zze.this).lock();
      try
      {
        if (zze.zza(zze.this, paramConnectionResult))
        {
          zze.zzd(zze.this);
          zze.zze(zze.this);
        }
        while (true)
        {
          return;
          zze.zzb(zze.this, paramConnectionResult);
        }
      }
      finally
      {
        zze.zzc(zze.this).unlock();
      }
    }

    public void onConnectionSuspended(int paramInt)
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zze
 * JD-Core Version:    0.6.2
 */