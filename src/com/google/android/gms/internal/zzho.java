package com.google.android.gms.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public abstract interface zzho extends IInterface
{
  public abstract void zza(zzhn paramzzhn)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzho
  {
    public static zzho zzZ(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.common.internal.service.ICommonService");
      if ((localIInterface != null) && ((localIInterface instanceof zzho)))
        return (zzho)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.common.internal.service.ICommonService");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.google.android.gms.common.internal.service.ICommonService");
      zza(zzhn.zza.zzY(paramParcel1.readStrongBinder()));
      return true;
    }

    private static class zza
      implements zzho
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

      public void zza(zzhn paramzzhn)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.common.internal.service.ICommonService");
          IBinder localIBinder = null;
          if (paramzzhn != null)
            localIBinder = paramzzhn.asBinder();
          localParcel.writeStrongBinder(localIBinder);
          this.zzlW.transact(1, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.internal.zzho
 * JD-Core Version:    0.6.2
 */