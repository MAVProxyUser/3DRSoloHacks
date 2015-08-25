package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceRequest;

public abstract interface zze extends IInterface
{
  public abstract void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zze
 * JD-Core Version:    0.6.2
 */