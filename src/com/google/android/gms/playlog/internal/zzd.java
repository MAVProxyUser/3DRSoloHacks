package com.google.android.gms.playlog.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.internal.zzlk.zza;

public class zzd
  implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{
  private boolean zzayA;
  private zzf zzayp;
  private final zzlk.zza zzayz;

  public zzd(zzlk.zza paramzza)
  {
    this.zzayz = paramzza;
    this.zzayp = null;
    this.zzayA = true;
  }

  public void onConnected(Bundle paramBundle)
  {
    this.zzayp.zzaf(false);
    if ((this.zzayA) && (this.zzayz != null))
      this.zzayz.zzvp();
    this.zzayA = false;
  }

  public void onConnectionFailed(ConnectionResult paramConnectionResult)
  {
    this.zzayp.zzaf(true);
    if ((this.zzayA) && (this.zzayz != null))
    {
      if (!paramConnectionResult.hasResolution())
        break label48;
      this.zzayz.zzf(paramConnectionResult.getResolution());
    }
    while (true)
    {
      this.zzayA = false;
      return;
      label48: this.zzayz.zzvq();
    }
  }

  public void onConnectionSuspended(int paramInt)
  {
    this.zzayp.zzaf(true);
  }

  public void zza(zzf paramzzf)
  {
    this.zzayp = paramzzf;
  }

  public void zzae(boolean paramBoolean)
  {
    this.zzayA = paramBoolean;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.playlog.internal.zzd
 * JD-Core Version:    0.6.2
 */