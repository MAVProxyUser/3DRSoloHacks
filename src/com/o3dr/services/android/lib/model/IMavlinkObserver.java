package com.o3dr.services.android.lib.model;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.o3dr.services.android.lib.mavlink.MavlinkMessageWrapper;

public abstract interface IMavlinkObserver extends IInterface
{
  public abstract void onMavlinkMessageReceived(MavlinkMessageWrapper paramMavlinkMessageWrapper)
    throws RemoteException;

  public static abstract class Stub extends Binder
    implements IMavlinkObserver
  {
    private static final String DESCRIPTOR = "com.o3dr.services.android.lib.model.IMavlinkObserver";
    static final int TRANSACTION_onMavlinkMessageReceived = 1;

    public Stub()
    {
      attachInterface(this, "com.o3dr.services.android.lib.model.IMavlinkObserver");
    }

    public static IMavlinkObserver asInterface(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.o3dr.services.android.lib.model.IMavlinkObserver");
      if ((localIInterface != null) && ((localIInterface instanceof IMavlinkObserver)))
        return (IMavlinkObserver)localIInterface;
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
        paramParcel2.writeString("com.o3dr.services.android.lib.model.IMavlinkObserver");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.o3dr.services.android.lib.model.IMavlinkObserver");
      if (paramParcel1.readInt() != 0);
      for (MavlinkMessageWrapper localMavlinkMessageWrapper = (MavlinkMessageWrapper)MavlinkMessageWrapper.CREATOR.createFromParcel(paramParcel1); ; localMavlinkMessageWrapper = null)
      {
        onMavlinkMessageReceived(localMavlinkMessageWrapper);
        return true;
      }
    }

    private static class Proxy
      implements IMavlinkObserver
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

      public String getInterfaceDescriptor()
      {
        return "com.o3dr.services.android.lib.model.IMavlinkObserver";
      }

      public void onMavlinkMessageReceived(MavlinkMessageWrapper paramMavlinkMessageWrapper)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.o3dr.services.android.lib.model.IMavlinkObserver");
          if (paramMavlinkMessageWrapper != null)
          {
            localParcel.writeInt(1);
            paramMavlinkMessageWrapper.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.mRemote.transact(1, localParcel, null, 1);
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
 * Qualified Name:     com.o3dr.services.android.lib.model.IMavlinkObserver
 * JD-Core Version:    0.6.2
 */