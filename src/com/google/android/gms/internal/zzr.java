package com.google.android.gms.internal;

import android.accounts.Account;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.auth.AccountChangeEventsRequest;
import com.google.android.gms.auth.AccountChangeEventsRequestCreator;
import com.google.android.gms.auth.AccountChangeEventsResponse;
import com.google.android.gms.auth.AccountChangeEventsResponseCreator;

public abstract interface zzr extends IInterface
{
  public abstract Bundle zza(Account paramAccount, String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle zza(String paramString, Bundle paramBundle)
    throws RemoteException;

  public abstract Bundle zza(String paramString1, String paramString2, Bundle paramBundle)
    throws RemoteException;

  public abstract AccountChangeEventsResponse zza(AccountChangeEventsRequest paramAccountChangeEventsRequest)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzr
  {
    public static zzr zza(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.auth.IAuthManagerService");
      if ((localIInterface != null) && ((localIInterface instanceof zzr)))
        return (zzr)localIInterface;
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
        paramParcel2.writeString("com.google.android.auth.IAuthManagerService");
        return true;
      case 1:
        paramParcel1.enforceInterface("com.google.android.auth.IAuthManagerService");
        String str3 = paramParcel1.readString();
        String str4 = paramParcel1.readString();
        Bundle localBundle5;
        if (paramParcel1.readInt() != 0)
        {
          localBundle5 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          Bundle localBundle6 = zza(str3, str4, localBundle5);
          paramParcel2.writeNoException();
          if (localBundle6 == null)
            break label150;
          paramParcel2.writeInt(1);
          localBundle6.writeToParcel(paramParcel2, 1);
        }
        while (true)
        {
          return true;
          localBundle5 = null;
          break;
          paramParcel2.writeInt(0);
        }
      case 2:
        paramParcel1.enforceInterface("com.google.android.auth.IAuthManagerService");
        String str2 = paramParcel1.readString();
        Bundle localBundle3;
        if (paramParcel1.readInt() != 0)
        {
          localBundle3 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
          Bundle localBundle4 = zza(str2, localBundle3);
          paramParcel2.writeNoException();
          if (localBundle4 == null)
            break label230;
          paramParcel2.writeInt(1);
          localBundle4.writeToParcel(paramParcel2, 1);
        }
        while (true)
        {
          return true;
          localBundle3 = null;
          break;
          paramParcel2.writeInt(0);
        }
      case 3:
        label150: label230: paramParcel1.enforceInterface("com.google.android.auth.IAuthManagerService");
        int i = paramParcel1.readInt();
        AccountChangeEventsRequest localAccountChangeEventsRequest = null;
        if (i != 0)
          localAccountChangeEventsRequest = AccountChangeEventsRequest.CREATOR.createFromParcel(paramParcel1);
        AccountChangeEventsResponse localAccountChangeEventsResponse = zza(localAccountChangeEventsRequest);
        paramParcel2.writeNoException();
        if (localAccountChangeEventsResponse != null)
        {
          paramParcel2.writeInt(1);
          localAccountChangeEventsResponse.writeToParcel(paramParcel2, 1);
        }
        while (true)
        {
          return true;
          paramParcel2.writeInt(0);
        }
      case 5:
      }
      paramParcel1.enforceInterface("com.google.android.auth.IAuthManagerService");
      Account localAccount;
      Bundle localBundle1;
      if (paramParcel1.readInt() != 0)
      {
        localAccount = (Account)Account.CREATOR.createFromParcel(paramParcel1);
        String str1 = paramParcel1.readString();
        if (paramParcel1.readInt() == 0)
          break label401;
        localBundle1 = (Bundle)Bundle.CREATOR.createFromParcel(paramParcel1);
        label360: Bundle localBundle2 = zza(localAccount, str1, localBundle1);
        paramParcel2.writeNoException();
        if (localBundle2 == null)
          break label407;
        paramParcel2.writeInt(1);
        localBundle2.writeToParcel(paramParcel2, 1);
      }
      while (true)
      {
        return true;
        localAccount = null;
        break;
        label401: localBundle1 = null;
        break label360;
        label407: paramParcel2.writeInt(0);
      }
    }

    private static class zza
      implements zzr
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

      public Bundle zza(Account paramAccount, String paramString, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
            if (paramAccount != null)
            {
              localParcel1.writeInt(1);
              paramAccount.writeToParcel(localParcel1, 0);
              localParcel1.writeString(paramString);
              if (paramBundle != null)
              {
                localParcel1.writeInt(1);
                paramBundle.writeToParcel(localParcel1, 0);
                this.zzlW.transact(5, localParcel1, localParcel2, 0);
                localParcel2.readException();
                if (localParcel2.readInt() == 0)
                  break label147;
                localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
                return localBundle;
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
          localParcel1.writeInt(0);
          continue;
          label147: Bundle localBundle = null;
        }
      }

      public Bundle zza(String paramString, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
            localParcel1.writeString(paramString);
            if (paramBundle != null)
            {
              localParcel1.writeInt(1);
              paramBundle.writeToParcel(localParcel1, 0);
              this.zzlW.transact(2, localParcel1, localParcel2, 0);
              localParcel2.readException();
              if (localParcel2.readInt() != 0)
              {
                localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
                return localBundle;
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
          Bundle localBundle = null;
        }
      }

      public Bundle zza(String paramString1, String paramString2, Bundle paramBundle)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
            localParcel1.writeString(paramString1);
            localParcel1.writeString(paramString2);
            if (paramBundle != null)
            {
              localParcel1.writeInt(1);
              paramBundle.writeToParcel(localParcel1, 0);
              this.zzlW.transact(1, localParcel1, localParcel2, 0);
              localParcel2.readException();
              if (localParcel2.readInt() != 0)
              {
                localBundle = (Bundle)Bundle.CREATOR.createFromParcel(localParcel2);
                return localBundle;
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
          Bundle localBundle = null;
        }
      }

      public AccountChangeEventsResponse zza(AccountChangeEventsRequest paramAccountChangeEventsRequest)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.auth.IAuthManagerService");
            if (paramAccountChangeEventsRequest != null)
            {
              localParcel1.writeInt(1);
              paramAccountChangeEventsRequest.writeToParcel(localParcel1, 0);
              this.zzlW.transact(3, localParcel1, localParcel2, 0);
              localParcel2.readException();
              if (localParcel2.readInt() != 0)
              {
                AccountChangeEventsResponse localAccountChangeEventsResponse2 = AccountChangeEventsResponse.CREATOR.createFromParcel(localParcel2);
                localAccountChangeEventsResponse1 = localAccountChangeEventsResponse2;
                return localAccountChangeEventsResponse1;
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
          AccountChangeEventsResponse localAccountChangeEventsResponse1 = null;
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzr
 * JD-Core Version:    0.6.2
 */