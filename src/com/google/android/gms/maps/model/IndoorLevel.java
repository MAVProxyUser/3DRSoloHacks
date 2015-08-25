package com.google.android.gms.maps.model;

import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.model.internal.zzh;

public final class IndoorLevel
{
  private final zzh zzavp;

  public IndoorLevel(zzh paramzzh)
  {
    this.zzavp = ((zzh)zzv.zzr(paramzzh));
  }

  public void activate()
  {
    try
    {
      this.zzavp.activate();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof IndoorLevel))
      return false;
    try
    {
      boolean bool = this.zzavp.zza(((IndoorLevel)paramObject).zzavp);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public String getName()
  {
    try
    {
      String str = this.zzavp.getName();
      return str;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public String getShortName()
  {
    try
    {
      String str = this.zzavp.getShortName();
      return str;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public int hashCode()
  {
    try
    {
      int i = this.zzavp.hashCodeRemote();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.IndoorLevel
 * JD-Core Version:    0.6.2
 */