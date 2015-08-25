package com.google.android.gms.location.places.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.PlacesOptions.Builder;
import com.google.android.gms.location.places.zzf;
import java.util.Locale;

public class zzi extends com.google.android.gms.common.internal.zzi<zze>
{
  private final PlacesParams zzasq;
  private final Locale zzasr = Locale.getDefault();

  public zzi(Context paramContext, Looper paramLooper, com.google.android.gms.common.internal.zze paramzze, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString1, String paramString2, PlacesOptions paramPlacesOptions)
  {
    super(paramContext, paramLooper, 67, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    Account localAccount = paramzze.getAccount();
    String str = null;
    if (localAccount != null)
      str = paramzze.getAccount().name;
    this.zzasq = new PlacesParams(paramString1, this.zzasr, str, paramPlacesOptions.zzasc, paramString2);
  }

  public void zza(zzf paramzzf, PlaceFilter paramPlaceFilter)
    throws RemoteException
  {
    if (paramPlaceFilter == null)
      paramPlaceFilter = PlaceFilter.zzsU();
    ((zze)zzlX()).zza(paramPlaceFilter, this.zzasq, paramzzf);
  }

  public void zza(zzf paramzzf, PlaceReport paramPlaceReport)
    throws RemoteException
  {
    zzv.zzr(paramPlaceReport);
    ((zze)zzlX()).zza(paramPlaceReport, this.zzasq, paramzzf);
  }

  protected zze zzbn(IBinder paramIBinder)
  {
    return zze.zza.zzbk(paramIBinder);
  }

  protected String zzeq()
  {
    return "com.google.android.gms.location.places.PlaceDetectionApi";
  }

  protected String zzer()
  {
    return "com.google.android.gms.location.places.internal.IGooglePlaceDetectionService";
  }

  public static class zza
    implements Api.zzb<zzi, PlacesOptions>
  {
    private final String zzass;
    private final String zzast;

    public zza(String paramString1, String paramString2)
    {
      this.zzass = paramString1;
      this.zzast = paramString2;
    }

    public int getPriority()
    {
      return 2147483647;
    }

    public zzi zzb(Context paramContext, Looper paramLooper, com.google.android.gms.common.internal.zze paramzze, PlacesOptions paramPlacesOptions, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      String str1;
      String str2;
      if (this.zzass != null)
      {
        str1 = this.zzass;
        if (this.zzast == null)
          break label73;
        str2 = this.zzast;
        label26: if (paramPlacesOptions != null)
          break label82;
      }
      label73: label82: for (PlacesOptions localPlacesOptions = new PlacesOptions.Builder().build(); ; localPlacesOptions = paramPlacesOptions)
      {
        return new zzi(paramContext, paramLooper, paramzze, paramConnectionCallbacks, paramOnConnectionFailedListener, str1, str2, localPlacesOptions);
        str1 = paramContext.getPackageName();
        break;
        str2 = paramContext.getPackageName();
        break label26;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzi
 * JD-Core Version:    0.6.2
 */