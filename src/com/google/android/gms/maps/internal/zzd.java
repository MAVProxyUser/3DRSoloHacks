package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.internal.zzi;
import com.google.android.gms.maps.model.internal.zzi.zza;

public abstract interface zzd extends IInterface
{
  public abstract com.google.android.gms.dynamic.zzd zzf(zzi paramzzi)
    throws RemoteException;

  public abstract com.google.android.gms.dynamic.zzd zzg(zzi paramzzi)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzd
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.maps.internal.IInfoWindowAdapter");
    }

    public static zzd zzbs(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
      if ((localIInterface != null) && ((localIInterface instanceof zzd)))
        return (zzd)localIInterface;
      return new zza(paramIBinder);
    }

    public IBinder asBinder()
    {
      return this;
    }

    public boolean onTransact(int paramInt1, Parcel paramParcel1, Parcel paramParcel2, int paramInt2)
      throws RemoteException
    {
      switch (paramInt1)
      {
      default:
        return super.onTransact(paramInt1, paramParcel1, paramParcel2, paramInt2);
      case 1598968902:
        paramParcel2.writeString("com.google.android.gms.maps.internal.IInfoWindowAdapter");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
        com.google.android.gms.dynamic.zzd localzzd2 = zzf(zzi.zza.zzbZ(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        IBinder localIBinder2 = null;
        if (localzzd2 != null)
          localIBinder2 = localzzd2.asBinder();
        paramParcel2.writeStrongBinder(localIBinder2);
        return true;
      case 2:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IInfoWindowAdapter");
      com.google.android.gms.dynamic.zzd localzzd1 = zzg(zzi.zza.zzbZ(paramParcel1.readStrongBinder()));
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

      public com.google.android.gms.dynamic.zzd zzf(zzi paramzzi)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
          if (paramzzi != null);
          for (IBinder localIBinder = paramzzi.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.zzlW.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
            return localzzd;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.dynamic.zzd zzg(zzi paramzzi)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IInfoWindowAdapter");
          if (paramzzi != null);
          for (IBinder localIBinder = paramzzi.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.zzlW.transact(2, localParcel1, localParcel2, 0);
            localParcel2.readException();
            com.google.android.gms.dynamic.zzd localzzd = com.google.android.gms.dynamic.zzd.zza.zzat(localParcel2.readStrongBinder());
            return localzzd;
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
 * Qualified Name:     com.google.android.gms.maps.internal.zzd
 * JD-Core Version:    0.6.2
 */