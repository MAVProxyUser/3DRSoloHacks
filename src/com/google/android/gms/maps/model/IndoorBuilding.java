package com.google.android.gms.maps.model;

import android.os.IBinder;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzh.zza;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class IndoorBuilding
{
  private final zzg zzavo;

  public IndoorBuilding(zzg paramzzg)
  {
    this.zzavo = ((zzg)zzv.zzr(paramzzg));
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof IndoorBuilding))
      return false;
    try
    {
      boolean bool = this.zzavo.zzb(((IndoorBuilding)paramObject).zzavo);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public int getActiveLevelIndex()
  {
    try
    {
      int i = this.zzavo.getActiveLevelIndex();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public int getDefaultLevelIndex()
  {
    try
    {
      int i = this.zzavo.getActiveLevelIndex();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public List<IndoorLevel> getLevels()
  {
    ArrayList localArrayList;
    try
    {
      List localList = this.zzavo.getLevels();
      localArrayList = new ArrayList(localList.size());
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
        localArrayList.add(new IndoorLevel(zzh.zza.zzbY((IBinder)localIterator.next())));
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
    return localArrayList;
  }

  public int hashCode()
  {
    try
    {
      int i = this.zzavo.hashCodeRemote();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public boolean isUnderground()
  {
    try
    {
      boolean bool = this.zzavo.isUnderground();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.IndoorBuilding
 * JD-Core Version:    0.6.2
 */