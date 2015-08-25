package com.google.android.gms.playlog.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

public abstract interface zza extends IInterface
{
  public abstract void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
    throws RemoteException;

  public abstract void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, List<LogEvent> paramList)
    throws RemoteException;

  public abstract void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, byte[] paramArrayOfByte)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zza
  {
    public static zza zzct(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.playlog.internal.IPlayLogService");
      if ((localIInterface != null) && ((localIInterface instanceof zza)))
        return (zza)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.playlog.internal.IPlayLogService");
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.playlog.internal.IPlayLogService");
        String str3 = paramParcel1.readString();
        if (paramParcel1.readInt() != 0);
        for (PlayLoggerContext localPlayLoggerContext3 = PlayLoggerContext.CREATOR.zzeP(paramParcel1); ; localPlayLoggerContext3 = null)
        {
          int k = paramParcel1.readInt();
          LogEvent localLogEvent = null;
          if (k != 0)
            localLogEvent = LogEvent.CREATOR.zzeO(paramParcel1);
          zza(str3, localPlayLoggerContext3, localLogEvent);
          return true;
        }
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.playlog.internal.IPlayLogService");
        String str2 = paramParcel1.readString();
        int j = paramParcel1.readInt();
        PlayLoggerContext localPlayLoggerContext2 = null;
        if (j != 0)
          localPlayLoggerContext2 = PlayLoggerContext.CREATOR.zzeP(paramParcel1);
        zza(str2, localPlayLoggerContext2, paramParcel1.createTypedArrayList(LogEvent.CREATOR));
        return true;
      case 4:
      }
      paramParcel1.enforceInterface("com.google.android.gms.playlog.internal.IPlayLogService");
      String str1 = paramParcel1.readString();
      int i = paramParcel1.readInt();
      PlayLoggerContext localPlayLoggerContext1 = null;
      if (i != 0)
        localPlayLoggerContext1 = PlayLoggerContext.CREATOR.zzeP(paramParcel1);
      zza(str1, localPlayLoggerContext1, paramParcel1.createByteArray());
      return true;
    }

    private static class zza
      implements zza
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

      public void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, LogEvent paramLogEvent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.playlog.internal.IPlayLogService");
            localParcel.writeString(paramString);
            if (paramPlayLoggerContext != null)
            {
              localParcel.writeInt(1);
              paramPlayLoggerContext.writeToParcel(localParcel, 0);
              if (paramLogEvent != null)
              {
                localParcel.writeInt(1);
                paramLogEvent.writeToParcel(localParcel, 0);
                this.zzlW.transact(2, localParcel, null, 1);
              }
            }
            else
            {
              localParcel.writeInt(0);
              continue;
            }
          }
          finally
          {
            localParcel.recycle();
          }
          localParcel.writeInt(0);
        }
      }

      public void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, List<LogEvent> paramList)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.playlog.internal.IPlayLogService");
          localParcel.writeString(paramString);
          if (paramPlayLoggerContext != null)
          {
            localParcel.writeInt(1);
            paramPlayLoggerContext.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            localParcel.writeTypedList(paramList);
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

      public void zza(String paramString, PlayLoggerContext paramPlayLoggerContext, byte[] paramArrayOfByte)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.playlog.internal.IPlayLogService");
          localParcel.writeString(paramString);
          if (paramPlayLoggerContext != null)
          {
            localParcel.writeInt(1);
            paramPlayLoggerContext.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            localParcel.writeByteArray(paramArrayOfByte);
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
 * Qualified Name:     com.google.android.gms.playlog.internal.zza
 * JD-Core Version:    0.6.2
 */