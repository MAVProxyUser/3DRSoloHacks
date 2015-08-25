package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;

public abstract interface zzc extends IInterface
{
  public abstract IMapViewDelegate zza(com.google.android.gms.dynamic.zzd paramzzd, GoogleMapOptions paramGoogleMapOptions)
    throws RemoteException;

  public abstract IStreetViewPanoramaViewDelegate zza(com.google.android.gms.dynamic.zzd paramzzd, StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
    throws RemoteException;

  public abstract void zzb(com.google.android.gms.dynamic.zzd paramzzd, int paramInt)
    throws RemoteException;

  public abstract void zzj(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract IMapFragmentDelegate zzk(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract IStreetViewPanoramaFragmentDelegate zzl(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract ICameraUpdateFactoryDelegate zztL()
    throws RemoteException;

  public abstract com.google.android.gms.maps.model.internal.zzd zztM()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzc
 * JD-Core Version:    0.6.2
 */