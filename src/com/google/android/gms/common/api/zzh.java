package com.google.android.gms.common.api;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

public abstract interface zzh
{
  public abstract void begin();

  public abstract void connect();

  public abstract String getName();

  public abstract void onConnected(Bundle paramBundle);

  public abstract <A extends Api.zza, R extends Result, T extends zza.zza<R, A>> T zza(T paramT);

  public abstract void zza(ConnectionResult paramConnectionResult, Api<?> paramApi, int paramInt);

  public abstract void zzas(int paramInt);

  public abstract <A extends Api.zza, T extends zza.zza<? extends Result, A>> T zzb(T paramT);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzh
 * JD-Core Version:    0.6.2
 */