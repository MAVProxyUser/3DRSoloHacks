package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.internal.zzv;

public final class PendingResults
{
  public static PendingResult<Status> canceledPendingResult()
  {
    zzk localzzk = new zzk(Looper.getMainLooper());
    localzzk.cancel();
    return localzzk;
  }

  public static <R extends Result> PendingResult<R> canceledPendingResult(R paramR)
  {
    zzv.zzb(paramR, "Result must not be null");
    if (paramR.getStatus().getStatusCode() == 16);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zzb(bool, "Status code must be CommonStatusCodes.CANCELED");
      PendingResults.zza localzza = new PendingResults.zza(paramR);
      localzza.cancel();
      return localzza;
    }
  }

  public static <R extends Result> PendingResult<R> immediatePendingResult(R paramR)
  {
    zzv.zzb(paramR, "Result must not be null");
    PendingResults.zzb localzzb = new PendingResults.zzb();
    localzzb.setResult(paramR);
    return localzzb;
  }

  public static PendingResult<Status> immediatePendingResult(Status paramStatus)
  {
    zzv.zzb(paramStatus, "Result must not be null");
    zzk localzzk = new zzk(Looper.getMainLooper());
    localzzk.setResult(paramStatus);
    return localzzk;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.PendingResults
 * JD-Core Version:    0.6.2
 */