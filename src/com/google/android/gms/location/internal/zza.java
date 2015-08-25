package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognition.zza;
import com.google.android.gms.location.ActivityRecognitionApi;

public class zza
  implements ActivityRecognitionApi
{
  public PendingResult<Status> removeActivityUpdates(GoogleApiClient paramGoogleApiClient, final PendingIntent paramPendingIntent)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(paramPendingIntent);
        setResult(Status.zzQU);
      }
    });
  }

  public PendingResult<Status> requestActivityUpdates(GoogleApiClient paramGoogleApiClient, final long paramLong, PendingIntent paramPendingIntent)
  {
    return paramGoogleApiClient.zzb(new zza(paramGoogleApiClient)
    {
      protected void zza(zzj paramAnonymouszzj)
        throws RemoteException
      {
        paramAnonymouszzj.zza(paramLong, this.zzaqD);
        setResult(Status.zzQU);
      }
    });
  }

  private static abstract class zza extends ActivityRecognition.zza<Status>
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
 * Qualified Name:     com.google.android.gms.location.internal.zza
 * JD-Core Version:    0.6.2
 */