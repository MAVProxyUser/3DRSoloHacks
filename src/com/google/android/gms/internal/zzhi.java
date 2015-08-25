package com.google.android.gms.internal;

import android.content.Context;
import android.os.Looper;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zze;

public final class zzhi
{
  public static final Api<Api.ApiOptions.NoOptions> API = new Api("Common.API", zzKi, zzKh, new Scope[0]);
  public static final Api.zzc<zzhm> zzKh = new Api.zzc();
  private static final Api.zzb<zzhm, Api.ApiOptions.NoOptions> zzKi = new Api.zzb()
  {
    public int getPriority()
    {
      return 2147483647;
    }

    public zzhm zzc(Context paramAnonymousContext, Looper paramAnonymousLooper, zze paramAnonymouszze, Api.ApiOptions.NoOptions paramAnonymousNoOptions, GoogleApiClient.ConnectionCallbacks paramAnonymousConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramAnonymousOnConnectionFailedListener)
    {
      return new zzhm(paramAnonymousContext, paramAnonymousLooper, paramAnonymousConnectionCallbacks, paramAnonymousOnConnectionFailedListener);
    }
  };
  public static final zzhj zzUh = new zzhk();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhi
 * JD-Core Version:    0.6.2
 */