package com.google.android.gms.common.api;

import android.os.Looper;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends AbstractPendingResult<BatchResult>
{
  private int zzPr;
  private boolean zzPs;
  private boolean zzPt;
  private final PendingResult<?>[] zzPu;
  private final Object zzoe = new Object();

  private Batch(List<PendingResult<?>> paramList, Looper paramLooper)
  {
    super(new AbstractPendingResult.CallbackHandler(paramLooper));
    this.zzPr = paramList.size();
    this.zzPu = new PendingResult[this.zzPr];
    for (int i = 0; i < paramList.size(); i++)
    {
      PendingResult localPendingResult = (PendingResult)paramList.get(i);
      this.zzPu[i] = localPendingResult;
      localPendingResult.addBatchCallback(new PendingResult.BatchCallback()
      {
        public void zzl(Status paramAnonymousStatus)
        {
          while (true)
          {
            synchronized (Batch.zza(Batch.this))
            {
              if (Batch.this.isCanceled())
                return;
              if (paramAnonymousStatus.isCanceled())
              {
                Batch.zza(Batch.this, true);
                Batch.zzb(Batch.this);
                if (Batch.zzc(Batch.this) == 0)
                {
                  if (!Batch.zzd(Batch.this))
                    break;
                  Batch.zze(Batch.this);
                }
                return;
              }
            }
            if (!paramAnonymousStatus.isSuccess())
              Batch.zzb(Batch.this, true);
          }
          if (Batch.zzf(Batch.this));
          for (Status localStatus = new Status(13); ; localStatus = Status.zzQU)
          {
            Batch.this.setResult(new BatchResult(localStatus, Batch.zzg(Batch.this)));
            break;
          }
        }
      });
    }
  }

  public void cancel()
  {
    super.cancel();
    PendingResult[] arrayOfPendingResult = this.zzPu;
    int i = arrayOfPendingResult.length;
    for (int j = 0; j < i; j++)
      arrayOfPendingResult[j].cancel();
  }

  public BatchResult createFailedResult(Status paramStatus)
  {
    return new BatchResult(paramStatus, this.zzPu);
  }

  public static final class Builder
  {
    private List<PendingResult<?>> zzPw = new ArrayList();
    private Looper zzPx;

    public Builder(GoogleApiClient paramGoogleApiClient)
    {
      this.zzPx = paramGoogleApiClient.getLooper();
    }

    public <R extends Result> BatchResultToken<R> add(PendingResult<R> paramPendingResult)
    {
      BatchResultToken localBatchResultToken = new BatchResultToken(this.zzPw.size());
      this.zzPw.add(paramPendingResult);
      return localBatchResultToken;
    }

    public Batch build()
    {
      return new Batch(this.zzPw, this.zzPx, null);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.Batch
 * JD-Core Version:    0.6.2
 */