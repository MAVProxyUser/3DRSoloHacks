package com.google.android.gms.location.places.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.PlacesOptions;
import com.google.android.gms.location.places.PlacesOptions.Builder;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;
import java.util.Locale;

public class zzd extends zzi<zzf>
{
  private final PlacesParams zzasq;
  private final Locale zzasr = Locale.getDefault();

  public zzd(Context paramContext, Looper paramLooper, zze paramzze, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, String paramString1, String paramString2, PlacesOptions paramPlacesOptions)
  {
    super(paramContext, paramLooper, 65, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    Account localAccount = paramzze.getAccount();
    String str = null;
    if (localAccount != null)
      str = paramzze.getAccount().name;
    this.zzasq = new PlacesParams(paramString1, this.zzasr, str, paramPlacesOptions.zzasc, paramString2);
  }

  public void zza(com.google.android.gms.location.places.zzf paramzzf, AddPlaceRequest paramAddPlaceRequest)
    throws RemoteException
  {
    zzv.zzb(paramAddPlaceRequest, "userAddedPlace == null");
    ((zzf)zzlX()).zza(paramAddPlaceRequest, this.zzasq, paramzzf);
  }

  public void zza(com.google.android.gms.location.places.zzf paramzzf, String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter)
    throws RemoteException
  {
    zzv.zzb(paramString, "query == null");
    zzv.zzb(paramLatLngBounds, "bounds == null");
    zzv.zzb(paramzzf, "callback == null");
    if (paramAutocompleteFilter == null);
    for (AutocompleteFilter localAutocompleteFilter = AutocompleteFilter.create(null); ; localAutocompleteFilter = paramAutocompleteFilter)
    {
      ((zzf)zzlX()).zza(paramString, paramLatLngBounds, localAutocompleteFilter, this.zzasq, paramzzf);
      return;
    }
  }

  public void zza(com.google.android.gms.location.places.zzf paramzzf, List<String> paramList)
    throws RemoteException
  {
    ((zzf)zzlX()).zzb(paramList, this.zzasq, paramzzf);
  }

  protected zzf zzbj(IBinder paramIBinder)
  {
    return zzf.zza.zzbl(paramIBinder);
  }

  protected String zzeq()
  {
    return "com.google.android.gms.location.places.GeoDataApi";
  }

  protected String zzer()
  {
    return "com.google.android.gms.location.places.internal.IGooglePlacesService";
  }

  public static class zza
    implements Api.zzb<zzd, PlacesOptions>
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

    public zzd zza(Context paramContext, Looper paramLooper, zze paramzze, PlacesOptions paramPlacesOptions, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
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
        return new zzd(paramContext, paramLooper, paramzze, paramConnectionCallbacks, paramOnConnectionFailedListener, str1, str2, localPlacesOptions);
        str1 = paramContext.getPackageName();
        break;
        str2 = paramContext.getPackageName();
        break label26;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzd
 * JD-Core Version:    0.6.2
 */