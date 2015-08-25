package com.google.android.gms.maps.model.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzh extends IInterface
{
  public abstract void activate()
    throws RemoteException;

  public abstract String getName()
    throws RemoteException;

  public abstract String getShortName()
    throws RemoteException;

  public abstract int hashCodeRemote()
    throws RemoteException;

  public abstract boolean zza(zzh paramzzh)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzh
  {
    public static zzh zzbY(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
      if ((localIInterface != null) && ((localIInterface instanceof zzh)))
        return (zzh)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
        String str2 = getName();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str2);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
        String str1 = getShortName();
        paramParcel2.writeNoException();
        paramParcel2.writeString(str1);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
        activate();
        paramParcel2.writeNoException();
        return true;
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
        boolean bool = zza(zzbY(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        if (bool);
        for (int j = 1; ; j = 0)
        {
          paramParcel2.writeInt(j);
          return true;
        }
      case 5:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
      int i = hashCodeRemote();
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }

    private static class zza
      implements zzh
    {
      private IBinder zzlW;

      zza(IBinder paramIBinder)
      {
        this.zzlW = paramIBinder;
      }

      public void activate()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
          this.zzlW.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public IBinder asBinder()
      {
        return this.zzlW;
      }

      public String getName()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
          this.zzlW.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getShortName()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
          this.zzlW.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          String str = localParcel2.readString();
          return str;
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
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
          this.zzlW.transact(5, localParcel1, localParcel2, 0);
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

      public boolean zza(zzh paramzzh)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IIndoorLevelDelegate");
          if (paramzzh != null);
          for (IBinder localIBinder = paramzzh.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.zzlW.transact(4, localParcel1, localParcel2, 0);
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
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzh
 * JD-Core Version:    0.6.2
 */