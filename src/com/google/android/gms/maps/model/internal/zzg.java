package com.google.android.gms.maps.model.internal;

import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

public abstract interface zzg extends IInterface
{
  public abstract int getActiveLevelIndex()
    throws RemoteException;

  public abstract int getDefaultLevelIndex()
    throws RemoteException;

  public abstract List<IBinder> getLevels()
    throws RemoteException;

  public abstract int hashCodeRemote()
    throws RemoteException;

  public abstract boolean isUnderground()
    throws RemoteException;

  public abstract boolean zzb(zzg paramzzg)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzg
 * JD-Core Version:    0.6.2
 */