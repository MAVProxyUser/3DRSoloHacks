package com.google.android.gms.common.api;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.internal.zzv;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public final class Api<O extends ApiOptions>
{
  private final String mName;
  private final zzb<?, O> zzPl;
  private final zze<?, O> zzPm;
  private final zzc<?> zzPn;
  private final zzf<?> zzPo;
  private final ArrayList<Scope> zzPp;

  public <C extends zza> Api(String paramString, zzb<C, O> paramzzb, zzc<C> paramzzc, Scope[] paramArrayOfScope)
  {
    zzv.zzb(paramzzb, "Cannot construct an Api with a null ClientBuilder");
    zzv.zzb(paramzzc, "Cannot construct an Api with a null ClientKey");
    this.mName = paramString;
    this.zzPl = paramzzb;
    this.zzPm = null;
    this.zzPn = paramzzc;
    this.zzPo = null;
    this.zzPp = new ArrayList(Arrays.asList(paramArrayOfScope));
  }

  public String getName()
  {
    return this.mName;
  }

  public zzb<?, O> zzkC()
  {
    if (this.zzPl != null);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "This API was constructed with a SimpleClientBuilder. Use getSimpleClientBuilder");
      return this.zzPl;
    }
  }

  public zze<?, O> zzkD()
  {
    if (this.zzPm != null);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "This API was constructed with a ClientBuilder. Use getClientBuilder");
      return this.zzPm;
    }
  }

  public List<Scope> zzkE()
  {
    return this.zzPp;
  }

  public zzc<?> zzkF()
  {
    if (this.zzPn != null);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "This API was constructed with a SimpleClientKey. Use getSimpleClientKey");
      return this.zzPn;
    }
  }

  public boolean zzkG()
  {
    return this.zzPo != null;
  }

  public static abstract interface ApiOptions
  {
    public static abstract interface HasOptions extends Api.ApiOptions
    {
    }

    public static final class NoOptions
      implements Api.ApiOptions.NotRequiredOptions
    {
    }

    public static abstract interface NotRequiredOptions extends Api.ApiOptions
    {
    }

    public static abstract interface Optional extends Api.ApiOptions.HasOptions, Api.ApiOptions.NotRequiredOptions
    {
    }
  }

  public static abstract interface zza
  {
    public abstract void connect();

    public abstract void disconnect();

    public abstract void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString);

    public abstract boolean isConnected();

    public abstract void zza(GoogleApiClient.zza paramzza);

    public abstract void zza(zzo paramzzo);

    public abstract void zza(zzo paramzzo, Set<Scope> paramSet);

    public abstract boolean zzjM();
  }

  public static abstract interface zzb<T extends Api.zza, O>
  {
    public abstract int getPriority();

    public abstract T zza(Context paramContext, Looper paramLooper, zze paramzze, O paramO, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener);
  }

  public static final class zzc<C extends Api.zza>
  {
  }

  public static abstract interface zzd<T extends IInterface>
  {
    public abstract T zzD(IBinder paramIBinder);

    public abstract String zzeq();

    public abstract String zzer();
  }

  public static abstract interface zze<T extends Api.zzd, O>
  {
    public abstract T zzi(O paramO);

    public abstract int zzkH();
  }

  public static final class zzf<C extends Api.zzd>
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.Api
 * JD-Core Version:    0.6.2
 */