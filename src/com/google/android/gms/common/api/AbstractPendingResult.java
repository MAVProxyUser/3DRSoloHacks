package com.google.android.gms.common.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.zzv;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPendingResult<R extends Result>
  implements PendingResult<R>
{
  protected final CallbackHandler<R> mHandler;
  private final Object zzPd = new Object();
  private final ArrayList<PendingResult.BatchCallback> zzPe = new ArrayList();
  private ResultCallback<R> zzPf;
  private volatile R zzPg;
  private volatile boolean zzPh;
  private boolean zzPi;
  private boolean zzPj;
  private ICancelToken zzPk;
  private final CountDownLatch zzns = new CountDownLatch(1);

  protected AbstractPendingResult(Looper paramLooper)
  {
    this.mHandler = new CallbackHandler(paramLooper);
  }

  protected AbstractPendingResult(CallbackHandler<R> paramCallbackHandler)
  {
    this.mHandler = paramCallbackHandler;
  }

  private void zza(R paramR)
  {
    this.zzPg = paramR;
    this.zzPk = null;
    this.zzns.countDown();
    Status localStatus = this.zzPg.getStatus();
    if (this.zzPf != null)
    {
      this.mHandler.removeTimeoutMessages();
      if (!this.zzPi)
        this.mHandler.sendResultCallback(this.zzPf, zzkB());
    }
    Iterator localIterator = this.zzPe.iterator();
    while (localIterator.hasNext())
      ((PendingResult.BatchCallback)localIterator.next()).zzl(localStatus);
    this.zzPe.clear();
  }

  static void zzb(Result paramResult)
  {
    if ((paramResult instanceof Releasable));
    try
    {
      ((Releasable)paramResult).release();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      Log.w("AbstractPendingResult", "Unable to release " + paramResult, localRuntimeException);
    }
  }

  private R zzkB()
  {
    boolean bool = true;
    synchronized (this.zzPd)
    {
      if (!this.zzPh)
      {
        zzv.zza(bool, "Result has already been consumed.");
        zzv.zza(isReady(), "Result is not ready.");
        Result localResult = this.zzPg;
        this.zzPg = null;
        this.zzPf = null;
        this.zzPh = true;
        onResultConsumed();
        return localResult;
      }
      bool = false;
    }
  }

  public final void addBatchCallback(PendingResult.BatchCallback paramBatchCallback)
  {
    if (!this.zzPh);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "Result has already been consumed.");
      synchronized (this.zzPd)
      {
        if (isReady())
        {
          paramBatchCallback.zzl(this.zzPg.getStatus());
          return;
        }
        this.zzPe.add(paramBatchCallback);
      }
    }
  }

  public final R await()
  {
    boolean bool1 = true;
    boolean bool2;
    if (Looper.myLooper() != Looper.getMainLooper())
      bool2 = bool1;
    while (true)
    {
      zzv.zza(bool2, "await must not be called on the UI thread");
      if (!this.zzPh)
        zzv.zza(bool1, "Result has already been consumed");
      try
      {
        this.zzns.await();
        zzv.zza(isReady(), "Result is not ready.");
        return zzkB();
        bool2 = false;
        continue;
        bool1 = false;
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          forceFailureUnlessReady(Status.zzQV);
      }
    }
  }

  public final R await(long paramLong, TimeUnit paramTimeUnit)
  {
    boolean bool1 = true;
    boolean bool2;
    if ((paramLong <= 0L) || (Looper.myLooper() != Looper.getMainLooper()))
      bool2 = bool1;
    while (true)
    {
      zzv.zza(bool2, "await must not be called on the UI thread when time is greater than zero.");
      if (!this.zzPh)
        zzv.zza(bool1, "Result has already been consumed.");
      try
      {
        if (!this.zzns.await(paramLong, paramTimeUnit))
          forceFailureUnlessReady(Status.zzQX);
        zzv.zza(isReady(), "Result is not ready.");
        return zzkB();
        bool2 = false;
        continue;
        bool1 = false;
      }
      catch (InterruptedException localInterruptedException)
      {
        while (true)
          forceFailureUnlessReady(Status.zzQV);
      }
    }
  }

  public void cancel()
  {
    synchronized (this.zzPd)
    {
      if ((this.zzPi) || (this.zzPh))
        return;
      ICancelToken localICancelToken = this.zzPk;
      if (localICancelToken == null);
    }
    try
    {
      this.zzPk.cancel();
      label42: zzb(this.zzPg);
      this.zzPf = null;
      this.zzPi = true;
      zza(createFailedResult(Status.zzQY));
      return;
      localObject2 = finally;
      throw localObject2;
    }
    catch (RemoteException localRemoteException)
    {
      break label42;
    }
  }

  protected abstract R createFailedResult(Status paramStatus);

  public final void forceFailureUnlessReady(Status paramStatus)
  {
    synchronized (this.zzPd)
    {
      if (!isReady())
      {
        setResult(createFailedResult(paramStatus));
        this.zzPj = true;
      }
      return;
    }
  }

  public boolean isCanceled()
  {
    synchronized (this.zzPd)
    {
      boolean bool = this.zzPi;
      return bool;
    }
  }

  public final boolean isReady()
  {
    return this.zzns.getCount() == 0L;
  }

  protected void onResultConsumed()
  {
  }

  protected final void setCancelToken(ICancelToken paramICancelToken)
  {
    synchronized (this.zzPd)
    {
      this.zzPk = paramICancelToken;
      return;
    }
  }

  public final void setResult(R paramR)
  {
    boolean bool1 = true;
    while (true)
    {
      synchronized (this.zzPd)
      {
        if ((this.zzPj) || (this.zzPi))
        {
          zzb(paramR);
          return;
        }
        if (!isReady())
        {
          bool2 = bool1;
          zzv.zza(bool2, "Results have already been set");
          if (this.zzPh)
            break label81;
          zzv.zza(bool1, "Result has already been consumed");
          zza(paramR);
          return;
        }
      }
      boolean bool2 = false;
      continue;
      label81: bool1 = false;
    }
  }

  public final void setResultCallback(ResultCallback<R> paramResultCallback)
  {
    if (!this.zzPh);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "Result has already been consumed.");
      while (true)
      {
        synchronized (this.zzPd)
        {
          if (isCanceled())
            return;
          if (isReady())
          {
            this.mHandler.sendResultCallback(paramResultCallback, zzkB());
            return;
          }
        }
        this.zzPf = paramResultCallback;
      }
    }
  }

  public final void setResultCallback(ResultCallback<R> paramResultCallback, long paramLong, TimeUnit paramTimeUnit)
  {
    boolean bool1 = true;
    boolean bool2;
    if (!this.zzPh)
    {
      bool2 = bool1;
      zzv.zza(bool2, "Result has already been consumed.");
      if (this.mHandler == null)
        break label114;
    }
    while (true)
    {
      zzv.zza(bool1, "CallbackHandler has not been set before calling setResultCallback.");
      while (true)
      {
        synchronized (this.zzPd)
        {
          if (isCanceled())
            return;
          if (isReady())
          {
            this.mHandler.sendResultCallback(paramResultCallback, zzkB());
            return;
          }
        }
        this.zzPf = paramResultCallback;
        this.mHandler.sendTimeoutResultCallback(this, paramTimeUnit.toMillis(paramLong));
      }
      bool2 = false;
      break;
      label114: bool1 = false;
    }
  }

  public static class CallbackHandler<R extends Result> extends Handler
  {
    public static final int CALLBACK_ON_COMPLETE = 1;
    public static final int CALLBACK_ON_TIMEOUT = 2;

    public CallbackHandler()
    {
      this(Looper.getMainLooper());
    }

    public CallbackHandler(Looper paramLooper)
    {
      super();
    }

    protected void deliverResultCallback(ResultCallback<R> paramResultCallback, R paramR)
    {
      try
      {
        paramResultCallback.onResult(paramR);
        return;
      }
      catch (RuntimeException localRuntimeException)
      {
        AbstractPendingResult.zzb(paramR);
        throw localRuntimeException;
      }
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        Log.wtf("AbstractPendingResult", "Don't know how to handle this message.");
        return;
      case 1:
        Pair localPair = (Pair)paramMessage.obj;
        deliverResultCallback((ResultCallback)localPair.first, (Result)localPair.second);
        return;
      case 2:
      }
      ((AbstractPendingResult)paramMessage.obj).forceFailureUnlessReady(Status.zzQX);
    }

    public void removeTimeoutMessages()
    {
      removeMessages(2);
    }

    public void sendResultCallback(ResultCallback<R> paramResultCallback, R paramR)
    {
      sendMessage(obtainMessage(1, new Pair(paramResultCallback, paramR)));
    }

    public void sendTimeoutResultCallback(AbstractPendingResult<R> paramAbstractPendingResult, long paramLong)
    {
      sendMessageDelayed(obtainMessage(2, paramAbstractPendingResult), paramLong);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.AbstractPendingResult
 * JD-Core Version:    0.6.2
 */