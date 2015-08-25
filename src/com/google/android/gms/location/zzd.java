package com.google.android.gms.location;

import android.location.Location;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

public abstract interface zzd extends IInterface
{
  public abstract void onLocationChanged(Location paramLocation)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzd
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.location.ILocationListener");
    }

    public static zzd zzbe(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.location.ILocationListener");
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
        paramParcel2.writeString("com.google.android.gms.location.ILocationListener");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.google.android.gms.location.ILocationListener");
      if (paramParcel1.readInt() != 0);
      for (Location localLocation = (Location)Location.CREATOR.createFromParcel(paramParcel1); ; localLocation = null)
      {
        onLocationChanged(localLocation);
        return true;
      }
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

      public void onLocationChanged(Location paramLocation)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.ILocationListener");
          if (paramLocation != null)
          {
            localParcel.writeInt(1);
            paramLocation.writeToParcel(localParcel, 0);
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
 * Qualified Name:     com.google.android.gms.location.zzd
 * JD-Core Version:    0.6.2
 */