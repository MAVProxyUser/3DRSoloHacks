package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GestureRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.zzd;
import java.util.List;

public abstract interface zzg extends IInterface
{
  public abstract void zzX(boolean paramBoolean)
    throws RemoteException;

  public abstract Status zza(GestureRequest paramGestureRequest, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(long paramLong, boolean paramBoolean, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(PendingIntent paramPendingIntent, zzf paramzzf, String paramString)
    throws RemoteException;

  public abstract void zza(Location paramLocation, int paramInt)
    throws RemoteException;

  public abstract void zza(GeofencingRequest paramGeofencingRequest, PendingIntent paramPendingIntent, zzf paramzzf)
    throws RemoteException;

  public abstract void zza(LocationRequest paramLocationRequest, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(LocationRequest paramLocationRequest, zzd paramzzd)
    throws RemoteException;

  public abstract void zza(LocationRequest paramLocationRequest, zzd paramzzd, String paramString)
    throws RemoteException;

  public abstract void zza(LocationSettingsRequest paramLocationSettingsRequest, zzh paramzzh, String paramString)
    throws RemoteException;

  public abstract void zza(LocationRequestInternal paramLocationRequestInternal, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(LocationRequestInternal paramLocationRequestInternal, zzd paramzzd)
    throws RemoteException;

  public abstract void zza(LocationRequestUpdateData paramLocationRequestUpdateData)
    throws RemoteException;

  public abstract void zza(zzf paramzzf, String paramString)
    throws RemoteException;

  public abstract void zza(zzd paramzzd)
    throws RemoteException;

  public abstract void zza(List<ParcelableGeofence> paramList, PendingIntent paramPendingIntent, zzf paramzzf, String paramString)
    throws RemoteException;

  public abstract void zza(String[] paramArrayOfString, zzf paramzzf, String paramString)
    throws RemoteException;

  public abstract Status zzb(PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zzb(Location paramLocation)
    throws RemoteException;

  public abstract void zzc(PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract ActivityRecognitionResult zzcE(String paramString)
    throws RemoteException;

  public abstract Location zzcF(String paramString)
    throws RemoteException;

  public abstract LocationAvailability zzcG(String paramString)
    throws RemoteException;

  public abstract Location zzsG()
    throws RemoteException;

  public abstract IBinder zzsH()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.internal.zzg
 * JD-Core Version:    0.6.2
 */