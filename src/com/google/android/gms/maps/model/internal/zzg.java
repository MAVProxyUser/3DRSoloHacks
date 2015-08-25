package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
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

  public static abstract class zza extends Binder
    implements zzg
  {
    public static zzg zzbX(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
      if ((localIInterface != null) && ((localIInterface instanceof zzg)))
        return (zzg)localIInterface;
      return new zza(paramIBinder);
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default:
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
        paramParcel2.writeString("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        int n = getActiveLevelIndex();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(n);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        int m = getDefaultLevelIndex();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(m);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        List localList = getLevels();
        paramParcel2.writeNoException();
        paramParcel2.writeBinderList(localList);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        boolean bool2 = isUnderground();
        paramParcel2.writeNoException();
        int k = 0;
        if (bool2)
          k = 1;
        paramParcel2.writeInt(k);
        return true;
      case 5:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
        boolean bool1 = zzb(zzbX(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        int j = 0;
        if (bool1)
          j = 1;
        paramParcel2.writeInt(j);
        return true;
      case 6:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
      int i = hashCodeRemote();
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }

    private static class zza
      implements zzg
    {
      private IBinder zzlW;

      zza(IBinder paramIBinder)
      {
        this.zzlW = paramIBinder;
      }

      public IBinder asBinder()
      {
        return this.zzlW;
      }

      public int getActiveLevelIndex()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
          this.zzlW.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int getDefaultLevelIndex()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
          this.zzlW.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public List<IBinder> getLevels()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
          this.zzlW.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ArrayList localArrayList = localParcel2.createBinderArrayList();
          return localArrayList;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public int hashCodeRemote()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
          this.zzlW.transact(6, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          return i;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean isUnderground()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
          this.zzlW.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          int i = localParcel2.readInt();
          boolean bool = false;
          if (i != 0)
            bool = true;
          return bool;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public boolean zzb(zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorBuildingDelegate");
          if (paramzzg != null);
          for (IBinder localIBinder = paramzzg.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.zzlW.transact(5, localParcel1, localParcel2, 0);
            localParcel2.readException();
            int i = localParcel2.readInt();
            boolean bool = false;
            if (i != 0)
              bool = true;
            return bool;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzg
 * JD-Core Version:    0.6.2
 */