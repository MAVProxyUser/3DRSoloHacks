package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

public abstract interface IStreetViewPanoramaViewDelegate extends IInterface
{
  public abstract IStreetViewPanoramaDelegate getStreetViewPanorama()
    throws RemoteException;

  public abstract void getStreetViewPanoramaAsync(zzv paramzzv)
    throws RemoteException;

  public abstract zzd getView()
    throws RemoteException;

  public abstract void onCreate(Bundle paramBundle)
    throws RemoteException;

  public abstract void onDestroy()
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
 * Qualified Name:     com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate
 * JD-Core Version:    0.6.2
 */