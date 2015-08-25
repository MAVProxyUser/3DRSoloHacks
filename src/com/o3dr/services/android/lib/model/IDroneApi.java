package com.o3dr.services.android.lib.model;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.o3dr.services.android.lib.model.action.Action;

public abstract interface IDroneApi extends IInterface
{
  public abstract void addAttributesObserver(IObserver paramIObserver)
    throws RemoteException;

  public abstract void addMavlinkObserver(IMavlinkObserver paramIMavlinkObserver)
    throws RemoteException;

  public abstract Bundle getAttribute(String paramString)
    throws RemoteException;

  public abstract void performAction(Action paramAction)
    throws RemoteException;

  public abstract void performAsyncAction(Action paramAction)
    throws RemoteException;

  public abstract void removeAttributesObserver(IObserver paramIObserver)
    throws RemoteException;

  public abstract void removeMavlinkObserver(IMavlinkObserver paramIMavlinkObserver)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IDroneApi
  {
    private static final String DESCRIPTOR = "com.o3dr.services.android.lib.model.IDroneApi";
    static final int TRANSACTION_addAttributesObserver = 4;
    static final int TRANSACTION_addMavlinkObserver = 6;
    static final int TRANSACTION_getAttribute = 1;
    static final int TRANSACTION_performAction = 2;
    static final int TRANSACTION_performAsyncAction = 3;
    static final int TRANSACTION_removeAttributesObserver = 5;
    static final int TRANSACTION_removeMavlinkObserver = 7;

    public Stub()
    {
      attachInterface(this, "com.o3dr.services.android.lib.model.IDroneApi");
    }

    public static IDroneApi asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.o3dr.services.android.lib.model.IDroneApi");
      if ((localIInterface != null) && ((localIInterface instanceof IDroneApi)))
        return (IDroneApi)localIInterface;
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
        paramParcel2.writeString("com.o3dr.services.android.lib.model.IDroneApi");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroneApi");
        Bundle localBundle = getAttribute(paramParcel1.readString());
        paramParcel2.writeNoException();
        if (localBundle != null)
        {
          paramParcel2.writeInt(1);
          localBundle.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroneApi");
        if (paramParcel1.readInt() != 0);
        for (Action localAction2 = (Action)Action.CREATOR.createFromParcel(paramParcel1); ; localAction2 = null)
        {
          performAction(localAction2);
          paramParcel2.writeNoException();
          if (localAction2 == null)
            break;
          paramParcel2.writeInt(1);
          localAction2.writeToParcel(paramParcel2, 1);
          return true;
        }
        paramParcel2.writeInt(0);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroneApi");
        if (paramParcel1.readInt() != 0);
        for (Action localAction1 = (Action)Action.CREATOR.createFromParcel(paramParcel1); ; localAction1 = null)
        {
          performAsyncAction(localAction1);
          return true;
        }
      case 4:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroneApi");
        addAttributesObserver(IObserver.Stub.asInterface(paramParcel1.readStrongBinder()));
        return true;
      case 5:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroneApi");
        removeAttributesObserver(IObserver.Stub.asInterface(paramParcel1.readStrongBinder()));
        return true;
      case 6:
        paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroneApi");
        addMavlinkObserver(IMavlinkObserver.Stub.asInterface(paramParcel1.readStrongBinder()));
        return true;
      case 7:
      }
      paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IDroneApi");
      removeMavlinkObserver(IMavlinkObserver.Stub.asInterface(paramParcel1.readStrongBinder()));
      return true;
    }

    private static class Proxy
      implements IDroneApi
    {
      private IBinder mRemote;

      Proxy(IBinder paramIBinder)
      {
        this.mRemote = paramIBinder;
      }

      public void addAttributesObserver(IObserver paramIObserver)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroneApi");
          IBinder localIBinder = null;
          if (paramIObserver != null)
            localIBinder = paramIObserver.asBinder();
          localParcel.writeStrongBinder(localIBinder);
          this.mRemote.transact(4, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void addMavlinkObserver(IMavlinkObserver paramIMavlinkObserver)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroneApi");
          IBinder localIBinder = null;
          if (paramIMavlinkObserver != null)
            localIBinder = paramIMavlinkObserver.asBinder();
          localParcel.writeStrongBinder(localIBinder);
          this.mRemote.transact(6, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public IBinder asBinder()
      {
        return this.mRemote;
      }

      public Bundle getAttribute(String paramString)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroneApi");
          localParcel1.writeString(paramString);
          this.mRemote.transact(1, localParcel1, localParcel2, 0);
          localParcel2.readException();
          if (localParcel2.readInt() != 0)
          {
            localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
            return localBundle;
          }
          Bundle localBundle = null;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public String getInterfaceDescriptor()
      {
        return "com.o3dr.services.android.lib.model.IDroneApi";
      }

      public void performAction(Action paramAction)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroneApi");
          if (paramAction != null)
          {
            localParcel1.writeInt(1);
            paramAction.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.mRemote.transact(2, localParcel1, localParcel2, 0);
            localParcel2.readException();
            if (localParcel2.readInt() != 0)
              paramAction.readFromParcel(localParcel2);
            return;
            localParcel1.writeInt(0);
          }
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void performAsyncAction(Action paramAction)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroneApi");
          if (paramAction != null)
          {
            localParcel.writeInt(1);
            paramAction.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.mRemote.transact(3, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void removeAttributesObserver(IObserver paramIObserver)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroneApi");
          IBinder localIBinder = null;
          if (paramIObserver != null)
            localIBinder = paramIObserver.asBinder();
          localParcel.writeStrongBinder(localIBinder);
          this.mRemote.transact(5, localParcel, null, 1);
          return;
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void removeMavlinkObserver(IMavlinkObserver paramIMavlinkObserver)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.o3dr.services.android.lib.model.IDroneApi");
          IBinder localIBinder = null;
          if (paramIMavlinkObserver != null)
            localIBinder = paramIMavlinkObserver.asBinder();
          localParcel.writeStrongBinder(localIBinder);
          this.mRemote.transact(7, localParcel, null, 1);
          return;
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
 * Qualified Name:     com.o3dr.services.android.lib.model.IDroneApi
 * JD-Core Version:    0.6.2
 */