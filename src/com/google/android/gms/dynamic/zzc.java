package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;

public abstract interface zzc extends IInterface
{
  public abstract Bundle getArguments()
    throws RemoteException;

  public abstract int getId()
    throws RemoteException;

  public abstract boolean getRetainInstance()
    throws RemoteException;

  public abstract String getTag()
    throws RemoteException;

  public abstract int getTargetRequestCode()
    throws RemoteException;

  public abstract boolean getUserVisibleHint()
    throws RemoteException;

  public abstract zzd getView()
    throws RemoteException;

  public abstract boolean isAdded()
    throws RemoteException;

  public abstract boolean isDetached()
    throws RemoteException;

  public abstract boolean isHidden()
    throws RemoteException;

  public abstract boolean isInLayout()
    throws RemoteException;

  public abstract boolean isRemoving()
    throws RemoteException;

  public abstract boolean isResumed()
    throws RemoteException;

  public abstract boolean isVisible()
    throws RemoteException;

  public abstract void setHasOptionsMenu(boolean paramBoolean)
    throws RemoteException;

  public abstract void setMenuVisibility(boolean paramBoolean)
    throws RemoteException;

  public abstract void setRetainInstance(boolean paramBoolean)
    throws RemoteException;

  public abstract void setUserVisibleHint(boolean paramBoolean)
    throws RemoteException;

  public abstract void startActivity(Intent paramIntent)
    throws RemoteException;

  public abstract void startActivityForResult(Intent paramIntent, int paramInt)
    throws RemoteException;

  public abstract void zze(zzd paramzzd)
    throws RemoteException;

  public abstract void zzf(zzd paramzzd)
    throws RemoteException;

  public abstract zzd zzov()
    throws RemoteException;

  public abstract zzc zzow()
    throws RemoteException;

  public abstract zzd zzox()
    throws RemoteException;

  public abstract zzc zzoy()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.dynamic.zzc
 * JD-Core Version:    0.6.2
 */