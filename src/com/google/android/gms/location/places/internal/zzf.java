package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceRequest;
import com.google.android.gms.location.places.UserDataType;
import com.google.android.gms.location.places.personalized.PlaceAlias;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;

public abstract interface zzf extends IInterface
{
  public abstract void zza(AddPlaceRequest paramAddPlaceRequest, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams)
    throws RemoteException;

  public abstract void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(UserDataType paramUserDataType, LatLngBounds paramLatLngBounds, List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(PlaceAlias paramPlaceAlias, String paramString1, String paramString2, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(LatLng paramLatLng, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(LatLngBounds paramLatLngBounds, int paramInt, String paramString, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(String paramString, int paramInt, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zzb(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zzf
 * JD-Core Version:    0.6.2
 */