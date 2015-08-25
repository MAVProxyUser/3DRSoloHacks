package com.google.android.gms.maps.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;
import com.google.android.gms.maps.model.zzk;

public abstract interface zzs extends IInterface
{
  public abstract void onStreetViewPanoramaChange(StreetViewPanoramaLocation paramStreetViewPanoramaLocation)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzs
  {
    public zza()
    {
      attachInterface(this, "com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
    }

    public static zzs zzbK(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
      if ((localIInterface != null) && ((localIInterface instanceof zzs)))
        return (zzs)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
        return true;
      case 1:
      }
      paramParcel1.enforceInterface("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
      if (paramParcel1.readInt() != 0);
      for (StreetViewPanoramaLocation localStreetViewPanoramaLocation = StreetViewPanoramaLocation.CREATOR.zzeo(paramParcel1); ; localStreetViewPanoramaLocation = null)
      {
        onStreetViewPanoramaChange(localStreetViewPanoramaLocation);
        paramParcel2.writeNoException();
        return true;
      }
    }

    private static class zza
      implements zzs
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

      public void onStreetViewPanoramaChange(StreetViewPanoramaLocation paramStreetViewPanoramaLocation)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        try
        {
          localParcel1.writeInterfaceToken("com.google.android.gms.maps.internal.IOnStreetViewPanoramaChangeListener");
          if (paramStreetViewPanoramaLocation != null)
          {
            localParcel1.writeInt(1);
            paramStreetViewPanoramaLocation.writeToParcel(localParcel1, 0);
          }
          while (true)
          {
            this.zzlW.transact(1, localParcel1, localParcel2, 0);
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
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzs
 * JD-Core Version:    0.6.2
 */