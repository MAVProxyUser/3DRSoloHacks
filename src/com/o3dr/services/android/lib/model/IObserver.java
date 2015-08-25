package com.o3dr.services.android.lib.model;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;

public abstract interface IObserver extends IInterface
{
  public abstract void onAttributeUpdated(String paramString, Bundle paramBundle)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.IObserver
 * JD-Core Version:    0.6.2
 */