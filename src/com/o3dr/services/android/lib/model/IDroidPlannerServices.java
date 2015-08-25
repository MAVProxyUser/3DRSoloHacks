package com.o3dr.services.android.lib.model;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface IDroidPlannerServices extends IInterface
{
  public abstract int getApiVersionCode()
    throws RemoteException;

  public abstract Bundle[] getConnectedApps(String paramString)
    throws RemoteException;

  public abstract int getServiceVersionCode()
    throws RemoteException;

  public abstract IDroneApi registerDroneApi(IApiListener paramIApiListener, String paramString)
    throws RemoteException;

  public abstract void releaseDroneApi(IDroneApi paramIDroneApi)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IDroidPlannerServices
  {
    private static final String DESCRIPTOR = "com.o3dr.services.android.lib.model.IDroidPlannerServices";
    static final int TRANSACTION_getApiVersionCode = 3;
    static final int TRANSACTION_getConnectedApps = 5;
    static final int TRANSACTION_getServiceVersionCode = 1;
    static final int TRANSACTION_registerDroneApi = 4;
    static final int TRANSACTION_releaseDroneApi = 2;

    public Stub()
    {
      attachInterface(this, "com.o3dr.services.android.lib.model.IDroidPlannerServices");
    }

    public static IDroidPlannerServices asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.o3dr.services.android.lib.model.IDroidPlannerServices");
      if ((localIInterface != null) && ((localIInterface instanceof IDroidPlannerServices)))
        return (IDroidPlannerServices)localIInterface;
      return new Proxy(paramIBinder);
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
        paramParcel2.writeString("com.o3dr.services.android.lib.model.IDroidPlannerServices");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroidPlannerServices");
        int j = getServiceVersionCode();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroidPlannerServices");
        releaseDroneApi(IDroneApi.Stub.asInterface(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 3:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroidPlannerServices");
        int i = getApiVersionCode();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(i);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroidPlannerServices");
        IDroneApi localIDroneApi = registerDroneApi(IApiListener.Stub.asInterface(paramParcel1.readStrongBinder()), paramParcel1.readString());
        paramParcel2.writeNoException();
        if (localIDroneApi != null);
        for (IBinder localIBinder = localIDroneApi.asBinder(); ; localIBinder = null)
        {
          paramParcel2.writeStrongBinder(localIBinder);
          return true;
        }
      case 5:
      }
      paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroidPlannerServices");
      Bundle[] arrayOfBundle = getConnectedApps(paramParcel1.readString());
      paramParcel2.writeNoException();
      paramParcel2.writeTypedArray(arrayOfBundle, 1);
      return true;
    }

    private static class Proxy
      implements IDroidPlannerServices
    {
      private IBinder mRemote;

      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }

      public IBinder asBinder()
      {
        return this.mRemote;
      }

      public int getApiVersionCode()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroidPlannerServices");
          this.mRemote.transact(3, localParcel1, localParcel2, 0);
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

      public Bundle[] getConnectedApps(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroidPlannerServices");
          localParcel1.writeString(paramString);
          this.mRemote.transact(5, localParcel1, localParcel2, 0);
          localParcel2.readException();
          Bundle[] arrayOfBundle = (Bundle[])localParcel2.createTypedArray(Bundle.CREATOR);
          return arrayOfBundle;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getInterfaceDescriptor()
      {
        return "com.o3dr.services.android.lib.model.IDroidPlannerServices";
      }

      public int getServiceVersionCode()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroidPlannerServices");
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
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

      public IDroneApi registerDroneApi(IApiListener paramIApiListener, String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroidPlannerServices");
          if (paramIApiListener != null);
          for (IBinder localIBinder = paramIApiListener.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            localParcel1.writeString(paramString);
            this.mRemote.transact(4, localParcel1, localParcel2, 0);
            localParcel2.readException();
            IDroneApi localIDroneApi = IDroneApi.Stub.asInterface(localParcel2.readStrongBinder());
            return localIDroneApi;
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void releaseDroneApi(IDroneApi paramIDroneApi)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroidPlannerServices");
          if (paramIDroneApi != null);
          for (IBinder localIBinder = paramIDroneApi.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            this.mRemote.transact(2, localParcel1, localParcel2, 0);
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
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.IDroidPlannerServices
 * JD-Core Version:    0.6.2
 */