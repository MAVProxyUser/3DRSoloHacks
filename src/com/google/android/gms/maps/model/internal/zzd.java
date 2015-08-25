package com.google.android.gms.maps.model.internal;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface zzd extends IInterface
{
  public abstract com.google.android.gms.dynamic.zzd zzb(Bitmap paramBitmap)
    throws RemoteException;

  public abstract com.google.android.gms.dynamic.zzd zzcO(String paramString)
    throws RemoteException;

  public abstract com.google.android.gms.dynamic.zzd zzcP(String paramString)
    throws RemoteException;

  public abstract com.google.android.gms.dynamic.zzd zzcQ(String paramString)
    throws RemoteException;

  public abstract com.google.android.gms.dynamic.zzd zzgB(int paramInt)
    throws RemoteException;

  public abstract com.google.android.gms.dynamic.zzd zzh(float paramFloat)
    throws RemoteException;

  public abstract com.google.android.gms.dynamic.zzd zztX()
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzd
  {
    public static zzd zzbU(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
      if ((localIInterface != null) && ((localIInterface instanceof zzd)))
        return (zzd)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        com.google.android.gms.dynamic.zzd localzzd7 = zzgB(paramParcel1.readInt());
        paramParcel2.writeNoException();
        if (localzzd7 != null);
        for (IBinder localIBinder7 = localzzd7.asBinder(); ; localIBinder7 = null)
        {
          paramParcel2.writeStrongBinder(localIBinder7);
          return true;
        }
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        com.google.android.gms.dynamic.zzd localzzd6 = zzcO(paramParcel1.readString());
        paramParcel2.writeNoException();
        IBinder localIBinder6 = null;
        if (localzzd6 != null)
          localIBinder6 = localzzd6.asBinder();
        paramParcel2.writeStrongBinder(localIBinder6);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        com.google.android.gms.dynamic.zzd localzzd5 = zzcP(paramParcel1.readString());
        paramParcel2.writeNoException();
        IBinder localIBinder5 = null;
        if (localzzd5 != null)
          localIBinder5 = localzzd5.asBinder();
        paramParcel2.writeStrongBinder(localIBinder5);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        com.google.android.gms.dynamic.zzd localzzd4 = zztX();
        paramParcel2.writeNoException();
        IBinder localIBinder4 = null;
        if (localzzd4 != null)
          localIBinder4 = localzzd4.asBinder();
        paramParcel2.writeStrongBinder(localIBinder4);
        return true;
      case 5:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        com.google.android.gms.dynamic.zzd localzzd3 = zzh(paramParcel1.readFloat());
        paramParcel2.writeNoException();
        IBinder localIBinder3 = null;
        if (localzzd3 != null)
          localIBinder3 = localzzd3.asBinder();
        paramParcel2.writeStrongBinder(localIBinder3);
        return true;
      case 6:
        paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
        if (paramParcel1.readInt() != 0);
        for (Bitmap localBitmap = (Bitmap)Bitmap.CREATOR.createFromParcel(paramParcel1); ; localBitmap = null)
        {
          com.google.android.gms.dynamic.zzd localzzd2 = zzb(localBitmap);
          paramParcel2.writeNoException();
          IBinder localIBinder2 = null;
          if (localzzd2 != null)
            localIBinder2 = localzzd2.asBinder();
          paramParcel2.writeStrongBinder(localIBinder2);
          return true;
        }
      case 7:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
      com.google.android.gms.dynamic.zzd localzzd1 = zzcQ(paramParcel1.readString());
      paramParcel2.writeNoException();
      IBinder localIBinder1 = null;
      if (localzzd1 != null)
        localIBinder1 = localzzd1.asBinder();
      paramParcel2.writeStrongBinder(localIBinder1);
      return true;
    }

    private static class zza
      implements zzd
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

      public com.google.android.gms.dynamic.zzd zzb(Bitmap paramBitmap)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          if (paramBitmap != null)
          {
            localParcel1.writeInt(1);
            paramBitmap.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.zzlW.transact(6, localParcel1, localParcel2, 0);
            localParcel2.readException();
            com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
            return localzzd;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.dynamic.zzd zzcO(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeString(paramString);
          this.zzlW.transact(2, localParcel1, localParcel2, 0);
          localParcel2.readException();
          com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
          return localzzd;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.dynamic.zzd zzcP(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeString(paramString);
          this.zzlW.transact(3, localParcel1, localParcel2, 0);
          localParcel2.readException();
          com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
          return localzzd;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.dynamic.zzd zzcQ(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeString(paramString);
          this.zzlW.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
          return localzzd;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.dynamic.zzd zzgB(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeInt(paramInt);
          this.zzlW.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
          return localzzd;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.dynamic.zzd zzh(float paramFloat)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          localParcel1.writeFloat(paramFloat);
          this.zzlW.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
          return localzzd;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.dynamic.zzd zztX()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.model.internal.IBitmapDescriptorFactoryDelegate");
          this.zzlW.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
          return localzzd;
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
 * Qualified Name:     com.google.android.gms.maps.model.internal.zzd
 * JD-Core Version:    0.6.2
 */