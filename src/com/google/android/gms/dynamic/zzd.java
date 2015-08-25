package com.google.android.gms.dynamic;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzd extends IInterface
{
  public static abstract class zza extends Binder
    implements zzd
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.dynamic.IObjectWrapper");
    }

    public static zzd zzat(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.dynamic.IObjectWrapper");
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
      }
      paramParcel2.writeString("com.google.android.gms.dynamic.IObjectWrapper");
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
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.dynamic.zzd
 * JD-Core Version:    0.6.2
 */