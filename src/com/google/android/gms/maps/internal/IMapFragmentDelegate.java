package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.GoogleMapOptions;

public abstract interface IMapFragmentDelegate extends IInterface
{
  public abstract IGoogleMapDelegate getMap()
    throws RemoteException;

  public abstract void getMapAsync(zzm paramzzm)
    throws RemoteException;

  public abstract boolean isReady()
    throws RemoteException;

  public abstract void onCreate(Bundle paramBundle)
    throws RemoteException;

  public abstract zzd onCreateView(zzd paramzzd1, zzd paramzzd2, Bundle paramBundle)
    throws RemoteException;

  public abstract void onDestroy()
    throws RemoteException;

  public abstract void onDestroyView()
    throws RemoteException;

  public abstract void onInflate(zzd paramzzd, GoogleMapOptions paramGoogleMapOptions, Bundle paramBundle)
    throws RemoteException;

  public abstract void onLowMemory()
    throws RemoteException;

  public abstract void onPause()
    throws RemoteException;

  public abstract void onResume()
    throws RemoteException;

  public abstract void onSaveInstanceState(Bundle paramBundle)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.IMapFragmentDelegate
 * JD-Core Version:    0.6.2
 */