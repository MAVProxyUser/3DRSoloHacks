package com.o3dr.services.android.lib.model;

import android.os.IInterface;
import android.os.RemoteException;
import com.o3dr.services.android.lib.mavlink.MavlinkMessageWrapper;

public abstract interface IMavlinkObserver extends IInterface
{
  public abstract void onMavlinkMessageReceived(MavlinkMessageWrapper paramMavlinkMessageWrapper)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.IMavlinkObserver
 * JD-Core Version:    0.6.2
 */