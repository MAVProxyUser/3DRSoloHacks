package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceReportCreator;
import com.google.android.gms.location.places.PlaceRequest;
import com.google.android.gms.location.places.zzc;
import com.google.android.gms.location.places.zzd;

public abstract interface zze extends IInterface
{
  public abstract void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zze
  {
    public static zze zzbk(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
      if ((localIInterface != null) && ((localIInterface instanceof zze)))
        return (zze)localIInterface;
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
        paramParcel2.writeString("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
        PlaceRequest localPlaceRequest;
        PlacesParams localPlacesParams6;
        if (paramParcel1.readInt() != 0)
        {
          localPlaceRequest = PlaceRequest.CREATOR.zzdO(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label174;
          localPlacesParams6 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label180;
        }
        for (PendingIntent localPendingIntent4 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent4 = null)
        {
          zza(localPlaceRequest, localPlacesParams6, localPendingIntent4, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
          localPlaceRequest = null;
          break;
          localPlacesParams6 = null;
          break label124;
        }
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
        PlacesParams localPlacesParams5;
        if (paramParcel1.readInt() != 0)
        {
          localPlacesParams5 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label256;
        }
        for (PendingIntent localPendingIntent3 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent3 = null)
        {
          zza(localPlacesParams5, localPendingIntent3, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
          localPlacesParams5 = null;
          break;
        }
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
        NearbyAlertRequest localNearbyAlertRequest;
        PlacesParams localPlacesParams4;
        if (paramParcel1.readInt() != 0)
        {
          localNearbyAlertRequest = NearbyAlertRequest.CREATOR.zzdM(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label350;
          localPlacesParams4 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label356;
        }
        for (PendingIntent localPendingIntent2 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent2 = null)
        {
          zza(localNearbyAlertRequest, localPlacesParams4, localPendingIntent2, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
          localNearbyAlertRequest = null;
          break;
          localPlacesParams4 = null;
          break label300;
        }
      case 5:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
        PlacesParams localPlacesParams3;
        if (paramParcel1.readInt() != 0)
        {
          localPlacesParams3 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label432;
        }
        for (PendingIntent localPendingIntent1 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent1 = null)
        {
          zzb(localPlacesParams3, localPendingIntent1, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
          localPlacesParams3 = null;
          break;
        }
      case 6:
        label124: paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
        label174: label180: label350: label356: if (paramParcel1.readInt() != 0);
        label256: label300: label432: for (PlaceFilter localPlaceFilter = PlaceFilter.CREATOR.zzdN(paramParcel1); ; localPlaceFilter = null)
        {
          int j = paramParcel1.readInt();
          PlacesParams localPlacesParams2 = null;
          if (j != 0)
            localPlacesParams2 = PlacesParams.CREATOR.zzdU(paramParcel1);
          zza(localPlaceFilter, localPlacesParams2, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          paramParcel2.writeNoException();
          return true;
        }
      case 7:
      }
      paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
      if (paramParcel1.readInt() != 0);
      for (PlaceReport localPlaceReport = PlaceReport.CREATOR.createFromParcel(paramParcel1); ; localPlaceReport = null)
      {
        int i = paramParcel1.readInt();
        PlacesParams localPlacesParams1 = null;
        if (i != 0)
          localPlacesParams1 = PlacesParams.CREATOR.zzdU(paramParcel1);
        zza(localPlaceReport, localPlacesParams1, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
        paramParcel2.writeNoException();
        return true;
      }
    }

    private static class zza
      implements zze
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

      public void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
            if (paramNearbyAlertRequest != null)
            {
              localParcel1.writeInt(1);
              paramNearbyAlertRequest.writeToParcel(localParcel1, 0);
              if (paramPlacesParams != null)
              {
                localParcel1.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel1, 0);
                if (paramPendingIntent == null)
                  break label154;
                localParcel1.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel1, 0);
                if (paramzzg == null)
                  break label163;
                localIBinder = paramzzg.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzlW.transact(4, localParcel1, localParcel2, 0);
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
          localParcel1.writeInt(0);
          continue;
          label154: localParcel1.writeInt(0);
          continue;
          label163: IBinder localIBinder = null;
        }
      }

      public void zza(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
            if (paramPlaceFilter != null)
            {
              localParcel1.writeInt(1);
              paramPlaceFilter.writeToParcel(localParcel1, 0);
              if (paramPlacesParams != null)
              {
                localParcel1.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel1, 0);
                if (paramzzg == null)
                  break label136;
                localIBinder = paramzzg.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzlW.transact(6, localParcel1, localParcel2, 0);
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
          localParcel1.writeInt(0);
          continue;
          label136: IBinder localIBinder = null;
        }
      }

      public void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
            if (paramPlaceReport != null)
            {
              localParcel1.writeInt(1);
              paramPlaceReport.writeToParcel(localParcel1, 0);
              if (paramPlacesParams != null)
              {
                localParcel1.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel1, 0);
                if (paramzzg == null)
                  break label136;
                localIBinder = paramzzg.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzlW.transact(7, localParcel1, localParcel2, 0);
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
          localParcel1.writeInt(0);
          continue;
          label136: IBinder localIBinder = null;
        }
      }

      public void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
            if (paramPlaceRequest != null)
            {
              localParcel1.writeInt(1);
              paramPlaceRequest.writeToParcel(localParcel1, 0);
              if (paramPlacesParams != null)
              {
                localParcel1.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel1, 0);
                if (paramPendingIntent == null)
                  break label154;
                localParcel1.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel1, 0);
                if (paramzzg == null)
                  break label163;
                localIBinder = paramzzg.asBinder();
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
          localParcel1.writeInt(0);
          continue;
          label154: localParcel1.writeInt(0);
          continue;
          label163: IBinder localIBinder = null;
        }
      }

      public void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
            if (paramPlacesParams != null)
            {
              localParcel1.writeInt(1);
              paramPlacesParams.writeToParcel(localParcel1, 0);
              if (paramPendingIntent != null)
              {
                localParcel1.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel1, 0);
                if (paramzzg == null)
                  break label135;
                localIBinder = paramzzg.asBinder();
                localParcel1.writeStrongBinder(localIBinder);
                this.zzlW.transact(3, localParcel1, localParcel2, 0);
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
          localParcel1.writeInt(0);
          continue;
          label135: IBinder localIBinder = null;
        }
      }

      public void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel1 = Parcel.obtain();
        Parcel localParcel2 = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel1.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlaceDetectionService");
            if (paramPlacesParams != null)
            {
              localParcel1.writeInt(1);
              paramPlacesParams.writeToParcel(localParcel1, 0);
              if (paramPendingIntent != null)
              {
                localParcel1.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel1, 0);
                if (paramzzg == null)
                  break label135;
                localIBinder = paramzzg.asBinder();
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
          localParcel1.writeInt(0);
          continue;
          label135: IBinder localIBinder = null;
        }
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.places.internal.zze
 * JD-Core Version:    0.6.2
 */