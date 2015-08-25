package com.google.android.gms.maps.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.CameraPosition;

public abstract interface zzf extends IInterface
{
  public abstract void onCameraChange(CameraPosition paramCameraPosition)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzf
 * JD-Core Version:    0.6.2
 */