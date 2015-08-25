package com.google.android.gms.location;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzc extends IInterface
{
  public abstract void onLocationAvailability(LocationAvailability paramLocationAvailability)
    throws RemoteException;

  public abstract void onLocationResult(LocationResult paramLocationResult)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzc
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.location.ILocationCallback");
    }

    public static zzc zzbd(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.location.ILocationCallback");
      if ((localIInterface != null) && ((localIInterface instanceof zzc)))
        return (zzc)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.location.ILocationCallback");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.gms.location.ILocationCallback");
        int j = paramParcel1.readInt();
        LocationResult localLocationResult = null;
        if (j != 0)
          localLocationResult = LocationResult.CREATOR.createFromParcel(paramParcel1);
        onLocationResult(localLocationResult);
        return true;
      case 2:
      }
      paramParcel1.enforceInterface("com.google.android.gms.location.ILocationCallback");
      int i = paramParcel1.readInt();
      LocationAvailability localLocationAvailability = null;
      if (i != 0)
        localLocationAvailability = LocationAvailability.CREATOR.createFromParcel(paramParcel1);
      onLocationAvailability(localLocationAvailability);
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

      public void onLocationAvailability(LocationAvailability paramLocationAvailability)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.ILocationCallback");
          if (paramLocationAvailability != null)
          {
            localParcel.writeInt(1);
            paramLocationAvailability.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.zzlW.transact(2, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void onLocationResult(LocationResult paramLocationResult)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.ILocationCallback");
          if (paramLocationResult != null)
          {
            localParcel.writeInt(1);
            paramLocationResult.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.zzlW.transact(1, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.location.zzc
 * JD-Core Version:    0.6.2
 */