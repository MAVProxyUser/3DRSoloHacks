package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzi;

public class zzhm extends zzi<zzho>
{
  public zzhm(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    super(paramContext, paramLooper, 39, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }

  protected zzho zzX(IBinder paramIBinder)
  {
    return zzho.zza.zzZ(paramIBinder);
  }

  public String zzeq()
  {
    return "com.google.android.gms.common.service.START";
  }

  protected String zzer()
  {
    return "com.google.android.gms.common.internal.service.ICommonService";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhm
 * JD-Core Version:    0.6.2
 */