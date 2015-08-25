package com.google.android.gms.common.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzv;

public final class zzi<L>
{
  private volatile L mListener;
  private final zzi<L>.zza zzQQ;

  zzi(Looper paramLooper, L paramL)
  {
    this.zzQQ = new zza(paramLooper);
    this.mListener = zzv.zzb(paramL, "Listener must not be null");
  }

  public void clear()
  {
    this.mListener = null;
  }

  public void zza(zzb<? super L> paramzzb)
  {
    zzv.zzb(paramzzb, "Notifier must not be null");
    Message localMessage = this.zzQQ.obtainMessage(1, paramzzb);
    this.zzQQ.sendMessage(localMessage);
  }

  void zzb(zzb<? super L> paramzzb)
  {
    Object localObject = this.mListener;
    if (localObject == null)
    {
      paramzzb.zzkJ();
      return;
    }
    try
    {
      paramzzb.zzk(localObject);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      paramzzb.zzkJ();
      throw localRuntimeException;
    }
  }

  private final class zza extends Handler
  {
    public zza(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      int i = 1;
      if (paramMessage.what == i);
      while (true)
      {
        zzv.zzQ(i);
        zzi.this.zzb((zzi.zzb)paramMessage.obj);
        return;
        int j = 0;
      }
    }
  }

  public static abstract interface zzb<L>
  {
    public abstract void zzk(L paramL);

    public abstract void zzkJ();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzi
 * JD-Core Version:    0.6.2
 */