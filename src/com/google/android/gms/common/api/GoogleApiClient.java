package com.google.android.gms.common.api;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zze.zza;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzmb;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme;
import com.google.android.gms.internal.zzme.zza;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public abstract interface GoogleApiClient
{
  public abstract ConnectionResult blockingConnect();

  public abstract ConnectionResult blockingConnect(long paramLong, TimeUnit paramTimeUnit);

  public abstract PendingResult<Status> clearDefaultAccountAndReconnect();

  public abstract void connect();

  public abstract void disconnect();

  public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);

  public abstract ConnectionResult getConnectionResult(Api<?> paramApi);

  public abstract Context getContext();

  public abstract Looper getLooper();

  public abstract int getSessionId();

  public abstract boolean hasConnectedApi(Api<?> paramApi);

  public abstract boolean isConnected();

  public abstract boolean isConnecting();

  public abstract boolean isConnectionCallbacksRegistered(ConnectionCallbacks paramConnectionCallbacks);

  public abstract boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener paramOnConnectionFailedListener);

  public abstract void reconnect();

  public abstract void registerConnectionCallbacks(ConnectionCallbacks paramConnectionCallbacks);

  public abstract void registerConnectionFailedListener(OnConnectionFailedListener paramOnConnectionFailedListener);

  public abstract void stopAutoManage(FragmentActivity paramFragmentActivity);

  public abstract void unregisterConnectionCallbacks(ConnectionCallbacks paramConnectionCallbacks);

  public abstract void unregisterConnectionFailedListener(OnConnectionFailedListener paramOnConnectionFailedListener);

  public abstract <C extends Api.zza> C zza(Api.zzc<C> paramzzc);

  public abstract <A extends Api.zza, R extends Result, T extends zza.zza<R, A>> T zza(T paramT);

  public abstract boolean zza(Api<?> paramApi);

  public abstract boolean zza(Scope paramScope);

  public abstract <A extends Api.zza, T extends zza.zza<? extends Result, A>> T zzb(T paramT);

  public abstract <L> zzi<L> zzl(L paramL);

  public static final class Builder
  {
    private final Context mContext;
    private Account zzJc;
    private int zzPA;
    private View zzPB;
    private String zzPC;
    private String zzPD;
    private final Map<Api<?>, zze.zza> zzPE = new HashMap();
    private final Map<Api<?>, Api.ApiOptions> zzPF = new HashMap();
    private FragmentActivity zzPG;
    private int zzPH = -1;
    private int zzPI = -1;
    private GoogleApiClient.OnConnectionFailedListener zzPJ;
    private Api.zzb<? extends zzmd, zzme> zzPK;
    private final Set<GoogleApiClient.ConnectionCallbacks> zzPL = new HashSet();
    private final Set<GoogleApiClient.OnConnectionFailedListener> zzPM = new HashSet();
    private zzme.zza zzPN = new zzme.zza();
    private Looper zzPx;
    private final Set<Scope> zzPz = new HashSet();

    public Builder(Context paramContext)
    {
      this.mContext = paramContext;
      this.zzPx = paramContext.getMainLooper();
      this.zzPC = paramContext.getPackageName();
      this.zzPD = paramContext.getClass().getName();
      this.zzPK = zzmb.zzKi;
    }

    public Builder(Context paramContext, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this(paramContext);
      zzv.zzb(paramConnectionCallbacks, "Must provide a connected listener");
      this.zzPL.add(paramConnectionCallbacks);
      zzv.zzb(paramOnConnectionFailedListener, "Must provide a connection failed listener");
      this.zzPM.add(paramOnConnectionFailedListener);
    }

    private void zza(Api<?> paramApi, int paramInt, Scope[] paramArrayOfScope)
    {
      int i = 1;
      int j = 0;
      if (paramInt == i);
      HashSet localHashSet;
      while (true)
      {
        localHashSet = new HashSet(paramApi.zzkE());
        int k = paramArrayOfScope.length;
        while (j < k)
        {
          localHashSet.add(paramArrayOfScope[j]);
          j++;
        }
        if (paramInt != 2)
          break;
        i = 0;
      }
      throw new IllegalArgumentException("Invalid resolution mode: '" + paramInt + "', use a constant from GoogleApiClient.ResolutionMode");
      this.zzPE.put(paramApi, new zze.zza(localHashSet, i));
    }

    private GoogleApiClient zzkL()
    {
      zzl localzzl = zzl.zza(this.zzPG);
      zzg localzzg = new zzg(this.mContext.getApplicationContext(), this.zzPx, zzkK(), this.zzPK, this.zzPF, this.zzPL, this.zzPM, this.zzPH, -1);
      localzzl.zza(this.zzPH, localzzg, this.zzPJ);
      return localzzg;
    }

    private GoogleApiClient zzkM()
    {
      zzm localzzm = zzm.zzb(this.zzPG);
      Object localObject = localzzm.zzay(this.zzPI);
      if (localObject == null)
        localObject = new zzg(this.mContext.getApplicationContext(), this.zzPx, zzkK(), this.zzPK, this.zzPF, this.zzPL, this.zzPM, -1, this.zzPI);
      localzzm.zza(this.zzPI, (GoogleApiClient)localObject, this.zzPJ);
      return localObject;
    }

    public Builder addApi(Api<? extends Api.ApiOptions.NotRequiredOptions> paramApi)
    {
      this.zzPF.put(paramApi, null);
      this.zzPz.addAll(paramApi.zzkE());
      return this;
    }

    public <O extends Api.ApiOptions.HasOptions> Builder addApi(Api<O> paramApi, O paramO)
    {
      zzv.zzb(paramO, "Null options are not permitted for this Api");
      this.zzPF.put(paramApi, paramO);
      this.zzPz.addAll(paramApi.zzkE());
      return this;
    }

    public <O extends Api.ApiOptions.HasOptions> Builder addApiIfAvailable(Api<O> paramApi, O paramO, Scope[] paramArrayOfScope)
    {
      zzv.zzb(paramO, "Null options are not permitted for this Api");
      this.zzPF.put(paramApi, paramO);
      zza(paramApi, 1, paramArrayOfScope);
      return this;
    }

    public Builder addApiIfAvailable(Api<? extends Api.ApiOptions.NotRequiredOptions> paramApi, Scope[] paramArrayOfScope)
    {
      this.zzPF.put(paramApi, null);
      zza(paramApi, 1, paramArrayOfScope);
      return this;
    }

    public Builder addConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
    {
      this.zzPL.add(paramConnectionCallbacks);
      return this;
    }

    public Builder addOnConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this.zzPM.add(paramOnConnectionFailedListener);
      return this;
    }

    public Builder addScope(Scope paramScope)
    {
      this.zzPz.add(paramScope);
      return this;
    }

    public GoogleApiClient build()
    {
      if (!this.zzPF.isEmpty());
      for (boolean bool = true; ; bool = false)
      {
        zzv.zzb(bool, "must call addApi() to add at least one API");
        if (this.zzPH < 0)
          break;
        return zzkL();
      }
      if (this.zzPI >= 0)
        return zzkM();
      return new zzg(this.mContext, this.zzPx, zzkK(), this.zzPK, this.zzPF, this.zzPL, this.zzPM, -1, -1);
    }

    public Builder enableAutoManage(FragmentActivity paramFragmentActivity, int paramInt, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      if (paramInt >= 0);
      for (boolean bool = true; ; bool = false)
      {
        zzv.zzb(bool, "clientId must be non-negative");
        this.zzPH = paramInt;
        this.zzPG = ((FragmentActivity)zzv.zzb(paramFragmentActivity, "Null activity is not permitted."));
        this.zzPJ = paramOnConnectionFailedListener;
        return this;
      }
    }

    public Builder requestServerAuthCode(String paramString, GoogleApiClient.ServerAuthCodeCallbacks paramServerAuthCodeCallbacks)
    {
      this.zzPN.zza(paramString, paramServerAuthCodeCallbacks);
      return this;
    }

    public Builder setAccountName(String paramString)
    {
      if (paramString == null);
      for (Account localAccount = null; ; localAccount = new Account(paramString, "com.google"))
      {
        this.zzJc = localAccount;
        return this;
      }
    }

    public Builder setGravityForPopups(int paramInt)
    {
      this.zzPA = paramInt;
      return this;
    }

    public Builder setHandler(Handler paramHandler)
    {
      zzv.zzb(paramHandler, "Handler must not be null");
      this.zzPx = paramHandler.getLooper();
      return this;
    }

    public Builder setViewForPopups(View paramView)
    {
      this.zzPB = paramView;
      return this;
    }

    public Builder useDefaultAccount()
    {
      return setAccountName("<<default account>>");
    }

    public zze zzkK()
    {
      return new zze(this.zzJc, this.zzPz, this.zzPE, this.zzPA, this.zzPB, this.zzPC, this.zzPD, this.zzPN.zzwi());
    }
  }

  public static abstract interface ConnectionCallbacks
  {
    public static final int CAUSE_NETWORK_LOST = 2;
    public static final int CAUSE_SERVICE_DISCONNECTED = 1;

    public abstract void onConnected(Bundle paramBundle);

    public abstract void onConnectionSuspended(int paramInt);
  }

  public static abstract interface OnConnectionFailedListener
  {
    public abstract void onConnectionFailed(ConnectionResult paramConnectionResult);
  }

  public static abstract interface ServerAuthCodeCallbacks
  {
    public abstract CheckResult onCheckServerAuthorization(String paramString, Set<Scope> paramSet);

    public abstract boolean onUploadServerAuthCode(String paramString1, String paramString2);

    public static class CheckResult
    {
      private boolean zzPO;
      private Set<Scope> zzPP;

      private CheckResult(boolean paramBoolean, Set<Scope> paramSet)
      {
        this.zzPO = paramBoolean;
        this.zzPP = paramSet;
      }

      public static CheckResult newAuthNotRequiredResult()
      {
        return new CheckResult(false, null);
      }

      public static CheckResult newAuthRequiredResult(Set<Scope> paramSet)
      {
        if ((paramSet != null) && (!paramSet.isEmpty()));
        for (boolean bool = true; ; bool = false)
        {
          zzv.zzb(bool, "A non-empty scope set is required if further auth is needed.");
          return new CheckResult(true, paramSet);
        }
      }

      public boolean zzkN()
      {
        return this.zzPO;
      }

      public Set<Scope> zzkO()
      {
        return this.zzPP;
      }
    }
  }

  public static abstract interface zza
  {
    public abstract void zza(ConnectionResult paramConnectionResult);

    public abstract void zzb(ConnectionResult paramConnectionResult);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.GoogleApiClient
 * JD-Core Version:    0.6.2
 */