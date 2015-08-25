package com.google.android.gms.signin.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.common.api.Scope;
import java.util.List;

public abstract interface zzd extends IInterface
{
  public abstract void zza(String paramString1, String paramString2, zzf paramzzf)
    throws RemoteException;

  public abstract void zza(String paramString, List<Scope> paramList, zzf paramzzf)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzd
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
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
        paramParcel2.writeString("com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
        zza(paramParcel1.readString(), paramParcel1.createTypedArrayList(Scope.CREATOR), zzf.zza.zzcH(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      case 3:
      }
      paramParcel1.enforceInterface("com.google.android.gms.signin.internal.IOfflineAccessCallbacks");
      zza(paramParcel1.readString(), paramParcel1.readString(), zzf.zza.zzcH(paramParcel1.readStrongBinder()));
      paramParcel2.writeNoException();
      return true;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzd
 * JD-Core Version:    0.6.2
 */