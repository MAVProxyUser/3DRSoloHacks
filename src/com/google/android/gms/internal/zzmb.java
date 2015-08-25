package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.signin.internal.zzg;
import com.google.android.gms.signin.internal.zzh;
import java.util.concurrent.Executors;

public final class zzmb
{
  public static final Api<zzme> API = new Api("SignIn.API", zzKi, zzKh, new Scope[0]);
  public static final Api.zzc<zzh> zzKh = new Api.zzc();
  public static final Api.zzb<zzh, zzme> zzKi = new Api.zzb()
  {
    public int getPriority()
    {
      return 2147483647;
    }

    public zzh zza(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, zzme paramAnonymouszzme, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
    {
      if (paramAnonymouszzme == null);
      for (zzme localzzme = zzme.zzaBD; ; localzzme = paramAnonymouszzme)
        return new zzh(paramAnonymousContext, paramAnonymousLooper, paramAnonymouszze, localzzme, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener, Executors.newSingleThreadExecutor());
    }
  };
  public static final zzmc zzaBC = new zzg();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzmb
 * JD-Core Version:    0.6.2
 */