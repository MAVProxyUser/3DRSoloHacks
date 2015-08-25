package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.internal.zzo.zza;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzr.zza;

public abstract interface zzf extends IInterface
{
  public abstract void zza(int paramInt, Account paramAccount, zze paramzze)
    throws RemoteException;

  public abstract void zza(AuthAccountRequest paramAuthAccountRequest, zze paramzze)
    throws RemoteException;

  public abstract void zza(ResolveAccountRequest paramResolveAccountRequest, zzr paramzzr)
    throws RemoteException;

  public abstract void zza(zzo paramzzo, int paramInt, boolean paramBoolean)
    throws RemoteException;

  public abstract void zza(CheckServerAuthResult paramCheckServerAuthResult)
    throws RemoteException;

  public abstract void zzag(boolean paramBoolean)
    throws RemoteException;

  public abstract void zzhB(int paramInt)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzf
  {
    public static zzf zzcH(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.signin.internal.ISignInService");
      if ((localIInterface != null) && ((localIInterface instanceof zzf)))
        return (zzf)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.signin.internal.ISignInService");
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
        int i2 = paramParcel1.readInt();
        AuthAccountRequest localAuthAccountRequest = null;
        if (i2 != 0)
          localAuthAccountRequest = (AuthAccountRequest)AuthAccountRequest.CREATOR.createFromParcel(paramParcel1);
        zza(localAuthAccountRequest, zze.zza.zzcG(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
        int i1 = paramParcel1.readInt();
        CheckServerAuthResult localCheckServerAuthResult = null;
        if (i1 != 0)
          localCheckServerAuthResult = (CheckServerAuthResult)CheckServerAuthResult.CREATOR.createFromParcel(paramParcel1);
        zza(localCheckServerAuthResult);
        paramParcel2.writeNoException();
        return true;
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
        if (paramParcel1.readInt() != 0);
        for (boolean bool2 = true; ; bool2 = false)
        {
          zzag(bool2);
          paramParcel2.writeNoException();
          return true;
        }
      case 5:
        paramParcel1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
        int n = paramParcel1.readInt();
        ResolveAccountRequest localResolveAccountRequest = null;
        if (n != 0)
          localResolveAccountRequest = (ResolveAccountRequest)ResolveAccountRequest.CREATOR.createFromParcel(paramParcel1);
        zza(localResolveAccountRequest, zzr.zza.zzU(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 7:
        paramParcel1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
        zzhB(paramParcel1.readInt());
        paramParcel2.writeNoException();
        return true;
      case 8:
        paramParcel1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
        int k = paramParcel1.readInt();
        int m = paramParcel1.readInt();
        Account localAccount = null;
        if (m != 0)
          localAccount = (Account)Account.CREATOR.createFromParcel(paramParcel1);
        zza(k, localAccount, zze.zza.zzcG(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 9:
      }
      paramParcel1.enforceInterface("com.google.android.gms.signin.internal.ISignInService");
      zzo localzzo = zzo.zza.zzQ(paramParcel1.readStrongBinder());
      int i = paramParcel1.readInt();
      int j = paramParcel1.readInt();
      boolean bool1 = false;
      if (j != 0)
        bool1 = true;
      zza(localzzo, i, bool1);
      paramParcel2.writeNoException();
      return true;
    }

    private static class zza
      implements zzf
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

      public void zza(int paramInt, Account paramAccount, zze paramzze)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
            localParcel1.writeInt(paramInt);
            if (paramAccount != null)
            {
              localParcel1.writeInt(1);
              paramAccount.writeToParcel(localParcel1, 0);
              if (paramzze != null)
              {
                localIBinder = paramzze.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzlW.transact(8, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          IBinder localIBinder = null;
        }
      }

      public void zza(AuthAccountRequest paramAuthAccountRequest, zze paramzze)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
            if (paramAuthAccountRequest != null)
            {
              localParcel1.writeInt(1);
              paramAuthAccountRequest.writeToParcel(localParcel1, 0);
              if (paramzze != null)
              {
                localIBinder = paramzze.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzlW.transact(2, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          IBinder localIBinder = null;
        }
      }

      public void zza(ResolveAccountRequest paramResolveAccountRequest, zzr paramzzr)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
            if (paramResolveAccountRequest != null)
            {
              localParcel1.writeInt(1);
              paramResolveAccountRequest.writeToParcel(localParcel1, 0);
              if (paramzzr != null)
              {
                localIBinder = paramzzr.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzlW.transact(5, localParcel1, localParcel2, 0);
                localParcel2.readException();
              }
            }
            else
            {
              localParcel1.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel2.recycle();
            localParcel1.recycle();
          }
          IBinder localIBinder = null;
        }
      }

      public void zza(zzo paramzzo, int paramInt, boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
          if (paramzzo != null);
          for (IBinder localIBinder = paramzzo.asBinder(); ; localIBinder = null)
          {
            localParcel1.writeStrongBinder(localIBinder);
            localParcel1.writeInt(paramInt);
            int i = 0;
            if (paramBoolean)
              i = 1;
            localParcel1.writeInt(i);
            this.zzlW.transact(9, localParcel1, localParcel2, 0);
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

      public void zza(CheckServerAuthResult paramCheckServerAuthResult)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
          if (paramCheckServerAuthResult != null)
          {
            localParcel1.writeInt(1);
            paramCheckServerAuthResult.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.zzlW.transact(3, localParcel1, localParcel2, 0);
            localParcel2.readException();
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

      public void zzag(boolean paramBoolean)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
          int i = 0;
          if (paramBoolean)
            i = 1;
          localParcel1.writeInt(i);
          this.zzlW.transact(4, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
        }
        finally
        {
          localParcel2.recycle();
          localParcel1.recycle();
        }
      }

      public void zzhB(int paramInt)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.signin.internal.ISignInService");
          localParcel1.writeInt(paramInt);
          this.zzlW.transact(7, localParcel1, localParcel2, 0);
          localParcel2.readException();
          return;
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
 * Qualified Name:     com.google.android.gms.signin.internal.zzf
 * JD-Core Version:    0.6.2
 */