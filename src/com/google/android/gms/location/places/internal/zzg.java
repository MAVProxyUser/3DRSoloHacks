package com.google.android.gms.location.places.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.StatusCreator;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zze;

public abstract interface zzg extends IInterface
{
  public abstract void zzY(DataHolder paramDataHolder)
    throws RemoteException;

  public abstract void zzZ(DataHolder paramDataHolder)
    throws RemoteException;

  public abstract void zzaa(DataHolder paramDataHolder)
    throws RemoteException;

  public abstract void zzab(DataHolder paramDataHolder)
    throws RemoteException;

  public abstract void zzaz(Status paramStatus)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzg
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.location.places.internal.IPlacesCallbacks");
    }

    public static zzg zzbm(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
      if ((localIInterface != null) && ((localIInterface instanceof zzg)))
        return (zzg)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.location.places.internal.IPlacesCallbacks");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
        int n = paramParcel1.readInt();
        DataHolder localDataHolder4 = null;
        if (n != 0)
          localDataHolder4 = DataHolder.CREATOR.zzC(paramParcel1);
        zzY(localDataHolder4);
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
        int m = paramParcel1.readInt();
        DataHolder localDataHolder3 = null;
        if (m != 0)
          localDataHolder3 = DataHolder.CREATOR.zzC(paramParcel1);
        zzZ(localDataHolder3);
        return true;
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
        int k = paramParcel1.readInt();
        DataHolder localDataHolder2 = null;
        if (k != 0)
          localDataHolder2 = DataHolder.CREATOR.zzC(paramParcel1);
        zzaa(localDataHolder2);
        return true;
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
        int j = paramParcel1.readInt();
        Status localStatus = null;
        if (j != 0)
          localStatus = Status.CREATOR.createFromParcel(paramParcel1);
        zzaz(localStatus);
        return true;
      case 5:
      }
      paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IPlacesCallbacks");
      int i = paramParcel1.readInt();
      DataHolder localDataHolder1 = null;
      if (i != 0)
        localDataHolder1 = DataHolder.CREATOR.zzC(paramParcel1);
      zzab(localDataHolder1);
      return true;
    }

    private static class zza
      implements zzg
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

      public void zzY(DataHolder paramDataHolder)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IPlacesCallbacks");
          if (paramDataHolder != null)
          {
            localParcel.writeInt(1);
            paramDataHolder.writeToParcel(localParcel, 0);
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

      public void zzZ(DataHolder paramDataHolder)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IPlacesCallbacks");
          if (paramDataHolder != null)
          {
            localParcel.writeInt(1);
            paramDataHolder.writeToParcel(localParcel, 0);
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

      public void zzaa(DataHolder paramDataHolder)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IPlacesCallbacks");
          if (paramDataHolder != null)
          {
            localParcel.writeInt(1);
            paramDataHolder.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.zzlW.transact(3, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void zzab(DataHolder paramDataHolder)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IPlacesCallbacks");
          if (paramDataHolder != null)
          {
            localParcel.writeInt(1);
            paramDataHolder.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.zzlW.transact(5, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void zzaz(Status paramStatus)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IPlacesCallbacks");
          if (paramStatus != null)
          {
            localParcel.writeInt(1);
            paramStatus.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            this.zzlW.transact(4, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.location.places.internal.zzg
 * JD-Core Version:    0.6.2
 */