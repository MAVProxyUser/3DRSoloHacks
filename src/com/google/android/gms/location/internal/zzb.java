package com.google.android.gms.location.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;

public class zzb extends zzi<zzg>
{
  private final String zzaqF;
  protected final zzn<zzg> zzaqz = new zzn()
  {
    public void zzlW()
    {
      zzb.zza(zzb.this);
    }

    public zzg zzsF()
      throws DeadObjectException
    {
      return (zzg)zzb.this.zzlX();
    }
  };

  public zzb(Context paramContext, Looper paramLooper, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString, zze paramzze)
  {
    super(paramContext, paramLooper, 23, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    this.zzaqF = paramString;
  }

  protected zzg zzbf(IBinder paramIBinder)
  {
    return zzg.zza.zzbh(paramIBinder);
  }

  protected String zzeq()
  {
    return "com.google.android.location.internal.GoogleLocationManagerService.START";
  }

  protected String zzer()
  {
    return "com.google.android.gms.location.internal.IGoogleLocationManagerService";
  }

  protected Bundle zzka()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("client_name", this.zzaqF);
    return localBundle;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzb
 * JD-Core Version:    0.6.2
 */