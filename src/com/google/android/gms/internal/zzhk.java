package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;

public final class zzhk
  implements zzhj
{
  public PendingResult<Status> zzc(GoogleApiClient paramGoogleApiClient)
  {
    return paramGoogleApiClient.zzb(new zzhl.zza(paramGoogleApiClient)
    {
      protected void zza(zzhm paramAnonymouszzhm)
        throws RemoteException
      {
        ((zzho)paramAnonymouszzhm.zzlX()).zza(new zzhk.zza(this));
      }
    });
  }

  private static class zza extends zzhh
  {
    private final zza.zzb<Status> zzKq;

    public zza(zza.zzb<Status> paramzzb)
    {
      this.zzKq = paramzzb;
    }

    public void zzaW(int paramInt)
      throws RemoteException
    {
      this.zzKq.zzj(new Status(paramInt));
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhk
 * JD-Core Version:    0.6.2
 */