package com.google.android.gms.common.api;

import com.google.android.gms.common.internal.zzv;
import java.util.concurrent.TimeUnit;

public final class BatchResult
  implements Result
{
  private final Status zzKr;
  private final PendingResult<?>[] zzPu;

  BatchResult(Status paramStatus, PendingResult<?>[] paramArrayOfPendingResult)
  {
    this.zzKr = paramStatus;
    this.zzPu = paramArrayOfPendingResult;
  }

  public Status getStatus()
  {
    return this.zzKr;
  }

  public <R extends Result> R take(BatchResultToken<R> paramBatchResultToken)
  {
    if (paramBatchResultToken.mId < this.zzPu.length);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zzb(bool, "The result token does not belong to this batch");
      return this.zzPu[paramBatchResultToken.mId].await(0L, TimeUnit.MILLISECONDS);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.BatchResult
 * JD-Core Version:    0.6.2
 */