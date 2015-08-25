package com.o3dr.services.android.lib.model;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.o3dr.services.android.lib.drone.connection.ConnectionResult;

public abstract interface IApiListener extends IInterface
{
  public abstract int getApiVersionCode()
    throws RemoteException;

  public abstract int getClientVersionCode()
    throws RemoteException;

  public abstract void onConnectionFailed(ConnectionResult paramConnectionResult)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IApiListener
  {
    private static final String DESCRIPTOR = "com.o3dr.services.android.lib.model.IApiListener";
    static final int TRANSACTION_getApiVersionCode = 1;
    static final int TRANSACTION_getClientVersionCode = 3;
    static final int TRANSACTION_onConnectionFailed = 2;

    public Stub()
    {
      attachInterface(this, "com.o3dr.services.android.lib.model.IApiListener");
    }

    public static IApiListener asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.o3dr.services.android.lib.model.IApiListener");
      if ((localIInterface != null) && ((localIInterface instanceof IApiListener)))
        return (IApiListener)localIInterface;
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
        paramParcel2.writeString("com.o3dr.services.android.lib.model.IApiListener");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IApiListener");
        int j = getApiVersionCode();
        paramParcel2.writeNoException();
        paramParcel2.writeInt(j);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IApiListener");
        if (paramParcel1.readInt() != 0);
        for (ConnectionResult localConnectionResult = (ConnectionResult)ConnectionResult.CREATOR.createFromParcel(paramParcel1); ; localConnectionResult = null)
        {
          onConnectionFailed(localConnectionResult);
          return true;
        }
      case 3:
      }
      paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IApiListener");
      int i = getClientVersionCode();
      paramParcel2.writeNoException();
      paramParcel2.writeInt(i);
      return true;
    }

    private static class Proxy
      implements IApiListener
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
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IApiListener");
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

      public int getClientVersionCode()
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IApiListener");
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

      public String getInterfaceDescriptor()
      {
        return "com.o3dr.services.android.lib.model.IApiListener";
      }

      public void onConnectionFailed(ConnectionResult paramConnectionResult)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.o3dr.services.android.lib.model.IApiListener");
          if (paramConnectionResult != null)
          {
            localParcel.writeInt(1);
            paramConnectionResult.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.mRemote.transact(2, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.services.android.lib.model.IApiListener
 * JD-Core Version:    0.6.2
 */