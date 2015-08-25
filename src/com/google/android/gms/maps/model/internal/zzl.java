package com.google.android.gms.maps.model.internal;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.Tile;

public abstract interface zzl extends IInterface
{
  public abstract Tile getTile(int paramInt1, int paramInt2, int paramInt3)
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzl
 * JD-Core Version:    0.6.2
 */