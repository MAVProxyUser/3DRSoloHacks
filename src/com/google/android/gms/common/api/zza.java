package com.google.android.gms.common.api;

import android.os.DeadObjectException;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import java.util.concurrent.atomic.AtomicReference;

public class zza
{
  public static abstract class zza<R extends Result, A extends Api.zza> extends AbstractPendingResult<R>
    implements zza.zzb<R>, zzg.zze<A>
  {
    private final Api.zzc<A> zzPn;
    private AtomicReference<zzg.zzc> zzPq = new AtomicReference();

    protected zza(Api.zzc<A> paramzzc, GoogleApiClient paramGoogleApiClient)
    {
      super();
      this.zzPn = ((Api.zzc)zzv.zzr(paramzzc));
    }

    private void zza(RemoteException paramRemoteException)
    {
      zzk(new Status(8, paramRemoteException.getLocalizedMessage(), null));
    }

    protected void onResultConsumed()
    {
      zzg.zzc localzzc = (zzg.zzc)this.zzPq.getAndSet(null);
      if (localzzc != null)
        localzzc.zzc(this);
    }

    protected abstract void zza(A paramA)
      throws RemoteException;

    public void zza(zzg.zzc paramzzc)
    {
      this.zzPq.set(paramzzc);
    }

    public final void zzb(A paramA)
      throws DeadObjectException
    {
      try
      {
        zza(paramA);
        return;
      }
      catch (DeadObjectException localDeadObjectException)
      {
        zza(localDeadObjectException);
        throw localDeadObjectException;
      }
      catch (RemoteException localRemoteException)
      {
        zza(localRemoteException);
      }
    }

    public final void zzk(Status paramStatus)
    {
      if (!paramStatus.isSuccess());
      for (boolean bool = true; ; bool = false)
      {
        zzv.zzb(bool, "Failed result must not be success");
        setResult(createFailedResult(paramStatus));
        return;
      }
    }

    public final Api.zzc<A> zzkF()
    {
      return this.zzPn;
    }

    public int zzkI()
    {
      return 0;
    }
  }

  public static abstract interface zzb<R>
  {
    public abstract void zzj(R paramR);

    public abstract void zzk(Status paramStatus);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zza
 * JD-Core Version:    0.6.2
 */