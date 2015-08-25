package com.o3dr.services.android.lib.model;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.o3dr.services.android.lib.model.action.Action;

public abstract interface IDroneApi extends IInterface
{
  public abstract void addAttributesObserver(IObserver paramIObserver)
    throws RemoteException;

  public abstract void addMavlinkObserver(IMavlinkObserver paramIMavlinkObserver)
    throws RemoteException;

  public abstract Bundle getAttribute(String paramString)
    throws RemoteException;

  public abstract void performAction(Action paramAction)
    throws RemoteException;

  public abstract void performAsyncAction(Action paramAction)
    throws RemoteException;

  public abstract void removeAttributesObserver(IObserver paramIObserver)
    throws RemoteException;

  public abstract void removeMavlinkObserver(IMavlinkObserver paramIMavlinkObserver)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.IDroneApi
 * JD-Core Version:    0.6.2
 */