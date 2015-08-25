package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.api.Api.zzd;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

public class zzaa<T extends IInterface> extends zzi<T>
{
  private final Api.zzd<T> zzUd;

  public zzaa(Context paramContext, Looper paramLooper, int paramInt, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, zze paramzze, Api.zzd paramzzd)
  {
    super(paramContext, paramLooper, paramInt, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    this.zzUd = paramzzd;
  }

  protected T zzD(IBinder paramIBinder)
  {
    return this.zzUd.zzD(paramIBinder);
  }

  protected String zzeq()
  {
    return this.zzUd.zzeq();
  }

  protected String zzer()
  {
    return this.zzUd.zzer();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzaa
 * JD-Core Version:    0.6.2
 */