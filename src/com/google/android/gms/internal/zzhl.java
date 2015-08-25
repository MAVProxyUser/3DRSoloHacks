package com.google.android.gms.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zza;

abstract class zzhl<R extends Result> extends zza.zza<R, zzhm>
{
  public zzhl(GoogleApiClient paramGoogleApiClient)
  {
    super(zzhi.zzKh, paramGoogleApiClient);
  }

  static abstract class zza extends zzhl<Status>
  {
    public zza(GoogleApiClient paramGoogleApiClient)
    {
      super();
    }

    public Status zzb(Status paramStatus)
    {
      return paramStatus;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhl
 * JD-Core Version:    0.6.2
 */