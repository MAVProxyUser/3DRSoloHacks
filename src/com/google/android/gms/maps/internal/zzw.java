package com.google.android.gms.maps.internal;

import android.graphics.Bitmap;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;

public abstract interface zzw extends IInterface
{
  public abstract void onSnapshotReady(Bitmap paramBitmap)
    throws RemoteException;

  public abstract void zzi(zzd paramzzd)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzw
 * JD-Core Version:    0.6.2
 */