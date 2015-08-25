package com.google.android.gms.common.api;

import java.util.concurrent.TimeUnit;

public abstract interface PendingResult<R extends Result>
{
  public abstract void addBatchCallback(BatchCallback paramBatchCallback);

  public abstract R await();

  public abstract R await(long paramLong, TimeUnit paramTimeUnit);

  public abstract void cancel();

  public abstract boolean isCanceled();

  public abstract void setResultCallback(ResultCallback<R> paramResultCallback);

  public abstract void setResultCallback(ResultCallback<R> paramResultCallback, long paramLong, TimeUnit paramTimeUnit);

  public static abstract interface BatchCallback
  {
    public abstract void zzl(Status paramStatus);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.PendingResult
 * JD-Core Version:    0.6.2
 */