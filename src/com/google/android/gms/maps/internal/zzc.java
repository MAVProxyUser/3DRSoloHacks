package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.StreetViewPanoramaOptions;
import com.google.android.gms.maps.zza;
import com.google.android.gms.maps.zzb;

public abstract interface zzc extends IInterface
{
  public abstract IMapViewDelegate zza(com.google.android.gms.dynamic.zzd paramzzd, GoogleMapOptions paramGoogleMapOptions)
    throws RemoteException;

  public abstract IStreetViewPanoramaViewDelegate zza(com.google.android.gms.dynamic.zzd paramzzd, StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
    throws RemoteException;

  public abstract void zzb(com.google.android.gms.dynamic.zzd paramzzd, int paramInt)
    throws RemoteException;

  public abstract void zzj(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract IMapFragmentDelegate zzk(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract IStreetViewPanoramaFragmentDelegate zzl(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract ICameraUpdateFactoryDelegate zztL()
    throws RemoteException;

  public abstract com.google.android.gms.maps.model.internal.zzd zztM()
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzc
  {
    public static zzc zzbq(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.internal.ICreator");
      if ((localIInterface != null) && ((localIInterface instanceof zzc)))
        return (zzc)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.maps.internal.ICreator");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        zzj(com.google.android.gms.dynamic.zzd.zza.zzat(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        IMapFragmentDelegate localIMapFragmentDelegate = zzk(com.google.android.gms.dynamic.zzd.zza.zzat(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        IBinder localIBinder6 = null;
        if (localIMapFragmentDelegate != null)
          localIBinder6 = localIMapFragmentDelegate.asBinder();
        paramParcel2.writeStrongBinder(localIBinder6);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        com.google.android.gms.dynamic.zzd localzzd2 = com.google.android.gms.dynamic.zzd.zza.zzat(paramParcel1.readStrongBinder());
        if (paramParcel1.readInt() != 0);
        for (GoogleMapOptions localGoogleMapOptions = GoogleMapOptions.CREATOR.zzeb(paramParcel1); ; localGoogleMapOptions = null)
        {
          IMapViewDelegate localIMapViewDelegate = zza(localzzd2, localGoogleMapOptions);
          paramParcel2.writeNoException();
          IBinder localIBinder5 = null;
          if (localIMapViewDelegate != null)
            localIBinder5 = localIMapViewDelegate.asBinder();
          paramParcel2.writeStrongBinder(localIBinder5);
          return true;
        }
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        ICameraUpdateFactoryDelegate localICameraUpdateFactoryDelegate = zztL();
        paramParcel2.writeNoException();
        IBinder localIBinder4 = null;
        if (localICameraUpdateFactoryDelegate != null)
          localIBinder4 = localICameraUpdateFactoryDelegate.asBinder();
        paramParcel2.writeStrongBinder(localIBinder4);
        return true;
      case 5:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        com.google.android.gms.maps.model.internal.zzd localzzd = zztM();
        paramParcel2.writeNoException();
        IBinder localIBinder3 = null;
        if (localzzd != null)
          localIBinder3 = localzzd.asBinder();
        paramParcel2.writeStrongBinder(localIBinder3);
        return true;
      case 6:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        zzb(com.google.android.gms.dynamic.zzd.zza.zzat(paramParcel1.readStrongBinder()), paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 7:
        paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
        com.google.android.gms.dynamic.zzd localzzd1 = com.google.android.gms.dynamic.zzd.zza.zzat(paramParcel1.readStrongBinder());
        if (paramParcel1.readInt() != 0);
        for (StreetViewPanoramaOptions localStreetViewPanoramaOptions = StreetViewPanoramaOptions.CREATOR.zzec(paramParcel1); ; localStreetViewPanoramaOptions = null)
        {
          IStreetViewPanoramaViewDelegate localIStreetViewPanoramaViewDelegate = zza(localzzd1, localStreetViewPanoramaOptions);
          paramParcel2.writeNoException();
          IBinder localIBinder2 = null;
          if (localIStreetViewPanoramaViewDelegate != null)
            localIBinder2 = localIStreetViewPanoramaViewDelegate.asBinder();
          paramParcel2.writeStrongBinder(localIBinder2);
          return true;
        }
      case 8:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.internal.ICreator");
      IStreetViewPanoramaFragmentDelegate localIStreetViewPanoramaFragmentDelegate = zzl(com.google.android.gms.dynamic.zzd.zza.zzat(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      IBinder localIBinder1 = null;
      if (localIStreetViewPanoramaFragmentDelegate != null)
        localIBinder1 = localIStreetViewPanoramaFragmentDelegate.asBinder();
      paramParcel2.writeStrongBinder(localIBinder1);
      return true;
    }

    private static class zza
      implements zzc
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

      public IMapViewDelegate zza(com.google.android.gms.dynamic.zzd paramzzd, GoogleMapOptions paramGoogleMapOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          IBinder localIBinder;
          if (paramzzd != null)
          {
            localIBinder = paramzzd.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            if (paramGoogleMapOptions == null)
              break label96;
            localParcel1.writeInt(1);
            paramGoogleMapOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.zzlW.transact(3, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IMapViewDelegate localIMapViewDelegate = IMapViewDelegate.zza.zzbw(localParcel2.readStrongBinder());
            return localIMapViewDelegate;
            localIBinder = null;
            break;
            label96: localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public IStreetViewPanoramaViewDelegate zza(com.google.android.gms.dynamic.zzd paramzzd, StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          IBinder localIBinder;
          if (paramzzd != null)
          {
            localIBinder = paramzzd.asBinder();
            localParcel1.writeStrongBinder(localIBinder);
            if (paramStreetViewPanoramaOptions == null)
              break label97;
            localParcel1.writeInt(1);
            paramStreetViewPanoramaOptions.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.zzlW.transact(7, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IStreetViewPanoramaViewDelegate localIStreetViewPanoramaViewDelegate = IStreetViewPanoramaViewDelegate.zza.zzbS(localParcel2.readStrongBinder());
            return localIStreetViewPanoramaViewDelegate;
            localIBinder = null;
            break;
            label97: localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void zzb(com.google.android.gms.dynamic.zzd paramzzd, int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          if (paramzzd != null);
          for (IBinder localIBinder = paramzzd.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            localParcel1.writeInt(paramInt);
            this.zzlW.transact(6, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void zzj(com.google.android.gms.dynamic.zzd paramzzd)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          if (paramzzd != null);
          for (IBinder localIBinder = paramzzd.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.zzlW.transact(1, localParcel1, localParcel2, 0);
            localParcel2.readException();
            return;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public IMapFragmentDelegate zzk(com.google.android.gms.dynamic.zzd paramzzd)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          if (paramzzd != null);
          for (IBinder localIBinder = paramzzd.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.zzlW.transact(2, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IMapFragmentDelegate localIMapFragmentDelegate = IMapFragmentDelegate.zza.zzbv(localParcel2.readStrongBinder());
            return localIMapFragmentDelegate;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public IStreetViewPanoramaFragmentDelegate zzl(com.google.android.gms.dynamic.zzd paramzzd)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          if (paramzzd != null);
          for (IBinder localIBinder = paramzzd.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.zzlW.transact(8, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IStreetViewPanoramaFragmentDelegate localIStreetViewPanoramaFragmentDelegate = IStreetViewPanoramaFragmentDelegate.zza.zzbR(localParcel2.readStrongBinder());
            return localIStreetViewPanoramaFragmentDelegate;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public ICameraUpdateFactoryDelegate zztL()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          this.zzlW.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          ICameraUpdateFactoryDelegate localICameraUpdateFactoryDelegate = ICameraUpdateFactoryDelegate.zza.zzbo(localParcel2.readStrongBinder());
          return localICameraUpdateFactoryDelegate;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public com.google.android.gms.maps.model.internal.zzd zztM()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.ICreator");
          this.zzlW.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          com.google.android.gms.maps.model.internal.zzd localzzd = com.google.android.gms.maps.model.internal.zzd.zza.zzbU(localParcel2.readStrongBinder());
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
 * Qualified Name:     com.google.android.gms.maps.internal.zzc
 * JD-Core Version:    0.6.2
 */