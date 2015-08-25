package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceReportCreator;
import com.google.android.gms.location.places.PlaceRequest;
import com.google.android.gms.location.places.UserDataType;
import com.google.android.gms.location.places.personalized.PlaceAlias;
import com.google.android.gms.location.places.zzb;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.ArrayList;
import java.util.List;

public abstract interface zzf extends IInterface
{
  public abstract void zza(AddPlaceRequest paramAddPlaceRequest, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams)
    throws RemoteException;

  public abstract void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(UserDataType paramUserDataType, LatLngBounds paramLatLngBounds, List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zza(PlaceAlias paramPlaceAlias, String paramString1, String paramString2, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(LatLng paramLatLng, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(LatLngBounds paramLatLngBounds, int paramInt, String paramString, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(String paramString, int paramInt, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zza(List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
    throws RemoteException;

  public abstract void zzb(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public abstract void zzb(List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
    throws RemoteException;

  public static abstract class zza extends Binder
    implements zzf
  {
    public static zzf zzbl(IBinder paramIBinder)
    {
      if (paramIBinder == null)
        return null;
      IInterface localIInterface = paramIBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
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
        paramParcel2.writeString("com.google.android.gms.location.places.internal.IGooglePlacesService");
        return true;
      case 2:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        LatLngBounds localLatLngBounds3;
        int i6;
        String str7;
        if (paramParcel1.readInt() != 0)
        {
          localLatLngBounds3 = LatLngBounds.CREATOR.zzeh(paramParcel1);
          i6 = paramParcel1.readInt();
          str7 = paramParcel1.readString();
          if (paramParcel1.readInt() == 0)
            break label276;
        }
        for (PlaceFilter localPlaceFilter3 = PlaceFilter.CREATOR.zzdN(paramParcel1); ; localPlaceFilter3 = null)
        {
          int i7 = paramParcel1.readInt();
          PlacesParams localPlacesParams17 = null;
          if (i7 != 0)
            localPlacesParams17 = PlacesParams.CREATOR.zzdU(paramParcel1);
          zza(localLatLngBounds3, i6, str7, localPlaceFilter3, localPlacesParams17, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          return true;
          localLatLngBounds3 = null;
          break;
        }
      case 3:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        String str6 = paramParcel1.readString();
        int i5 = paramParcel1.readInt();
        PlacesParams localPlacesParams16 = null;
        if (i5 != 0)
          localPlacesParams16 = PlacesParams.CREATOR.zzdU(paramParcel1);
        zza(str6, localPlacesParams16, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
        return true;
      case 4:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        LatLng localLatLng;
        if (paramParcel1.readInt() != 0)
        {
          localLatLng = LatLng.CREATOR.zzei(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label420;
        }
        for (PlaceFilter localPlaceFilter2 = PlaceFilter.CREATOR.zzdN(paramParcel1); ; localPlaceFilter2 = null)
        {
          int i4 = paramParcel1.readInt();
          PlacesParams localPlacesParams15 = null;
          if (i4 != 0)
            localPlacesParams15 = PlacesParams.CREATOR.zzdU(paramParcel1);
          zza(localLatLng, localPlaceFilter2, localPlacesParams15, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          return true;
          localLatLng = null;
          break;
        }
      case 5:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0);
        for (PlaceFilter localPlaceFilter1 = PlaceFilter.CREATOR.zzdN(paramParcel1); ; localPlaceFilter1 = null)
        {
          int i3 = paramParcel1.readInt();
          PlacesParams localPlacesParams14 = null;
          if (i3 != 0)
            localPlacesParams14 = PlacesParams.CREATOR.zzdU(paramParcel1);
          zzb(localPlaceFilter1, localPlacesParams14, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          return true;
        }
      case 6:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        String str5 = paramParcel1.readString();
        int i2 = paramParcel1.readInt();
        PlacesParams localPlacesParams13 = null;
        if (i2 != 0)
          localPlacesParams13 = PlacesParams.CREATOR.zzdU(paramParcel1);
        zzb(str5, localPlacesParams13, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
        return true;
      case 7:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        ArrayList localArrayList3 = paramParcel1.createStringArrayList();
        int i1 = paramParcel1.readInt();
        PlacesParams localPlacesParams12 = null;
        if (i1 != 0)
          localPlacesParams12 = PlacesParams.CREATOR.zzdU(paramParcel1);
        zza(localArrayList3, localPlacesParams12, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
        return true;
      case 17:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        ArrayList localArrayList2 = paramParcel1.createStringArrayList();
        int n = paramParcel1.readInt();
        PlacesParams localPlacesParams11 = null;
        if (n != 0)
          localPlacesParams11 = PlacesParams.CREATOR.zzdU(paramParcel1);
        zzb(localArrayList2, localPlacesParams11, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
        return true;
      case 8:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        UserDataType localUserDataType;
        LatLngBounds localLatLngBounds2;
        ArrayList localArrayList1;
        if (paramParcel1.readInt() != 0)
        {
          localUserDataType = UserDataType.CREATOR.zzdP(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label737;
          localLatLngBounds2 = LatLngBounds.CREATOR.zzeh(paramParcel1);
          localArrayList1 = paramParcel1.createStringArrayList();
          if (paramParcel1.readInt() == 0)
            break label743;
        }
        for (PlacesParams localPlacesParams10 = PlacesParams.CREATOR.zzdU(paramParcel1); ; localPlacesParams10 = null)
        {
          zza(localUserDataType, localLatLngBounds2, localArrayList1, localPlacesParams10, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          return true;
          localUserDataType = null;
          break;
          localLatLngBounds2 = null;
          break label688;
        }
      case 9:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        PlaceRequest localPlaceRequest;
        PlacesParams localPlacesParams9;
        if (paramParcel1.readInt() != 0)
        {
          localPlaceRequest = PlaceRequest.CREATOR.zzdO(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label826;
          localPlacesParams9 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label832;
        }
        for (PendingIntent localPendingIntent4 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent4 = null)
        {
          zza(localPlaceRequest, localPlacesParams9, localPendingIntent4);
          return true;
          localPlaceRequest = null;
          break;
          localPlacesParams9 = null;
          break label787;
        }
      case 10:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        PlacesParams localPlacesParams8;
        if (paramParcel1.readInt() != 0)
        {
          localPlacesParams8 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label897;
        }
        for (PendingIntent localPendingIntent3 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent3 = null)
        {
          zza(localPlacesParams8, localPendingIntent3);
          return true;
          localPlacesParams8 = null;
          break;
        }
      case 11:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        NearbyAlertRequest localNearbyAlertRequest;
        PlacesParams localPlacesParams7;
        if (paramParcel1.readInt() != 0)
        {
          localNearbyAlertRequest = NearbyAlertRequest.CREATOR.zzdM(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label980;
          localPlacesParams7 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label986;
        }
        for (PendingIntent localPendingIntent2 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent2 = null)
        {
          zza(localNearbyAlertRequest, localPlacesParams7, localPendingIntent2);
          return true;
          localNearbyAlertRequest = null;
          break;
          localPlacesParams7 = null;
          break label941;
        }
      case 12:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        PlacesParams localPlacesParams6;
        if (paramParcel1.readInt() != 0)
        {
          localPlacesParams6 = PlacesParams.CREATOR.zzdU(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label1051;
        }
        for (PendingIntent localPendingIntent1 = (PendingIntent)PendingIntent.CREATOR.createFromParcel(paramParcel1); ; localPendingIntent1 = null)
        {
          zzb(localPlacesParams6, localPendingIntent1);
          return true;
          localPlacesParams6 = null;
          break;
        }
      case 13:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        String str4 = paramParcel1.readString();
        LatLngBounds localLatLngBounds1;
        AutocompleteFilter localAutocompleteFilter;
        if (paramParcel1.readInt() != 0)
        {
          localLatLngBounds1 = LatLngBounds.CREATOR.zzeh(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label1144;
          localAutocompleteFilter = AutocompleteFilter.CREATOR.zzdL(paramParcel1);
          if (paramParcel1.readInt() == 0)
            break label1150;
        }
        for (PlacesParams localPlacesParams5 = PlacesParams.CREATOR.zzdU(paramParcel1); ; localPlacesParams5 = null)
        {
          zza(str4, localLatLngBounds1, localAutocompleteFilter, localPlacesParams5, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          return true;
          localLatLngBounds1 = null;
          break;
          localAutocompleteFilter = null;
          break label1101;
        }
      case 14:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0);
        for (AddPlaceRequest localAddPlaceRequest = (AddPlaceRequest)AddPlaceRequest.CREATOR.createFromParcel(paramParcel1); ; localAddPlaceRequest = null)
        {
          int m = paramParcel1.readInt();
          PlacesParams localPlacesParams4 = null;
          if (m != 0)
            localPlacesParams4 = PlacesParams.CREATOR.zzdU(paramParcel1);
          zza(localAddPlaceRequest, localPlacesParams4, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          return true;
        }
      case 15:
        paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        if (paramParcel1.readInt() != 0);
        for (PlaceReport localPlaceReport = PlaceReport.CREATOR.createFromParcel(paramParcel1); ; localPlaceReport = null)
        {
          int k = paramParcel1.readInt();
          PlacesParams localPlacesParams3 = null;
          if (k != 0)
            localPlacesParams3 = PlacesParams.CREATOR.zzdU(paramParcel1);
          zza(localPlaceReport, localPlacesParams3);
          return true;
        }
      case 16:
        label276: label420: label688: label737: label743: label1144: label1150: paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
        label787: label826: label832: label980: label986: PlaceAlias localPlaceAlias;
        label897: label941: label1101: String str2;
        label1051: String str3;
        if (paramParcel1.readInt() != 0)
        {
          localPlaceAlias = PlaceAlias.CREATOR.zzdY(paramParcel1);
          str2 = paramParcel1.readString();
          str3 = paramParcel1.readString();
          if (paramParcel1.readInt() == 0)
            break label1367;
        }
        label1367: for (PlacesParams localPlacesParams2 = PlacesParams.CREATOR.zzdU(paramParcel1); ; localPlacesParams2 = null)
        {
          zza(localPlaceAlias, str2, str3, localPlacesParams2, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
          return true;
          localPlaceAlias = null;
          break;
        }
      case 18:
      }
      paramParcel1.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
      String str1 = paramParcel1.readString();
      int i = paramParcel1.readInt();
      int j = paramParcel1.readInt();
      PlacesParams localPlacesParams1 = null;
      if (j != 0)
        localPlacesParams1 = PlacesParams.CREATOR.zzdU(paramParcel1);
      zza(str1, i, localPlacesParams1, zzg.zza.zzbm(paramParcel1.readStrongBinder()));
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

      public void zza(AddPlaceRequest paramAddPlaceRequest, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramAddPlaceRequest != null)
            {
              localParcel.writeInt(1);
              paramAddPlaceRequest.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                IBinder localIBinder = null;
                if (paramzzg != null)
                  localIBinder = paramzzg.asBinder();
                localParcel.writeStrongBinder(localIBinder);
                this.zzlW.transact(14, localParcel, null, 1);
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

      public void zza(NearbyAlertRequest paramNearbyAlertRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramNearbyAlertRequest != null)
            {
              localParcel.writeInt(1);
              paramNearbyAlertRequest.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                if (paramPendingIntent == null)
                  break label113;
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zzlW.transact(11, localParcel, null, 1);
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
          continue;
          label113: localParcel.writeInt(0);
        }
      }

      public void zza(PlaceReport paramPlaceReport, PlacesParams paramPlacesParams)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceReport != null)
            {
              localParcel.writeInt(1);
              paramPlaceReport.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                this.zzlW.transact(15, localParcel, null, 1);
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

      public void zza(PlaceRequest paramPlaceRequest, PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceRequest != null)
            {
              localParcel.writeInt(1);
              paramPlaceRequest.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                if (paramPendingIntent == null)
                  break label113;
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zzlW.transact(9, localParcel, null, 1);
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
          continue;
          label113: localParcel.writeInt(0);
        }
      }

      public void zza(UserDataType paramUserDataType, LatLngBounds paramLatLngBounds, List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramUserDataType != null)
            {
              localParcel.writeInt(1);
              paramUserDataType.writeToParcel(localParcel, 0);
              if (paramLatLngBounds != null)
              {
                localParcel.writeInt(1);
                paramLatLngBounds.writeToParcel(localParcel, 0);
                localParcel.writeStringList(paramList);
                if (paramPlacesParams == null)
                  break label145;
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                IBinder localIBinder = null;
                if (paramzzg != null)
                  localIBinder = paramzzg.asBinder();
                localParcel.writeStrongBinder(localIBinder);
                this.zzlW.transact(8, localParcel, null, 1);
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
          continue;
          label145: localParcel.writeInt(0);
        }
      }

      public void zza(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlacesParams != null)
            {
              localParcel.writeInt(1);
              paramPlacesParams.writeToParcel(localParcel, 0);
              if (paramPendingIntent != null)
              {
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zzlW.transact(10, localParcel, null, 1);
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

      public void zza(PlaceAlias paramPlaceAlias, String paramString1, String paramString2, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceAlias != null)
            {
              localParcel.writeInt(1);
              paramPlaceAlias.writeToParcel(localParcel, 0);
              localParcel.writeString(paramString1);
              localParcel.writeString(paramString2);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                IBinder localIBinder = null;
                if (paramzzg != null)
                  localIBinder = paramzzg.asBinder();
                localParcel.writeStrongBinder(localIBinder);
                this.zzlW.transact(16, localParcel, null, 1);
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

      public void zza(LatLng paramLatLng, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramLatLng != null)
            {
              localParcel.writeInt(1);
              paramLatLng.writeToParcel(localParcel, 0);
              if (paramPlaceFilter != null)
              {
                localParcel.writeInt(1);
                paramPlaceFilter.writeToParcel(localParcel, 0);
                if (paramPlacesParams == null)
                  break label136;
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                IBinder localIBinder = null;
                if (paramzzg != null)
                  localIBinder = paramzzg.asBinder();
                localParcel.writeStrongBinder(localIBinder);
                this.zzlW.transact(4, localParcel, null, 1);
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
          continue;
          label136: localParcel.writeInt(0);
        }
      }

      public void zza(LatLngBounds paramLatLngBounds, int paramInt, String paramString, PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramLatLngBounds != null)
            {
              localParcel.writeInt(1);
              paramLatLngBounds.writeToParcel(localParcel, 0);
              localParcel.writeInt(paramInt);
              localParcel.writeString(paramString);
              if (paramPlaceFilter != null)
              {
                localParcel.writeInt(1);
                paramPlaceFilter.writeToParcel(localParcel, 0);
                if (paramPlacesParams == null)
                  break label152;
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                IBinder localIBinder = null;
                if (paramzzg != null)
                  localIBinder = paramzzg.asBinder();
                localParcel.writeStrongBinder(localIBinder);
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
          continue;
          label152: localParcel.writeInt(0);
        }
      }

      public void zza(String paramString, int paramInt, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
          localParcel.writeString(paramString);
          localParcel.writeInt(paramInt);
          if (paramPlacesParams != null)
          {
            localParcel.writeInt(1);
            paramPlacesParams.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            IBinder localIBinder = null;
            if (paramzzg != null)
              localIBinder = paramzzg.asBinder();
            localParcel.writeStrongBinder(localIBinder);
            this.zzlW.transact(18, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void zza(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
          localParcel.writeString(paramString);
          if (paramPlacesParams != null)
          {
            localParcel.writeInt(1);
            paramPlacesParams.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            IBinder localIBinder = null;
            if (paramzzg != null)
              localIBinder = paramzzg.asBinder();
            localParcel.writeStrongBinder(localIBinder);
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

      public void zza(String paramString, LatLngBounds paramLatLngBounds, AutocompleteFilter paramAutocompleteFilter, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            localParcel.writeString(paramString);
            if (paramLatLngBounds != null)
            {
              localParcel.writeInt(1);
              paramLatLngBounds.writeToParcel(localParcel, 0);
              if (paramAutocompleteFilter != null)
              {
                localParcel.writeInt(1);
                paramAutocompleteFilter.writeToParcel(localParcel, 0);
                if (paramPlacesParams == null)
                  break label145;
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                IBinder localIBinder = null;
                if (paramzzg != null)
                  localIBinder = paramzzg.asBinder();
                localParcel.writeStrongBinder(localIBinder);
                this.zzlW.transact(13, localParcel, null, 1);
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
          continue;
          label145: localParcel.writeInt(0);
        }
      }

      public void zza(List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
          localParcel.writeStringList(paramList);
          if (paramPlacesParams != null)
          {
            localParcel.writeInt(1);
            paramPlacesParams.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            IBinder localIBinder = null;
            if (paramzzg != null)
              localIBinder = paramzzg.asBinder();
            localParcel.writeStrongBinder(localIBinder);
            this.zzlW.transact(7, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void zzb(PlaceFilter paramPlaceFilter, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlaceFilter != null)
            {
              localParcel.writeInt(1);
              paramPlaceFilter.writeToParcel(localParcel, 0);
              if (paramPlacesParams != null)
              {
                localParcel.writeInt(1);
                paramPlacesParams.writeToParcel(localParcel, 0);
                IBinder localIBinder = null;
                if (paramzzg != null)
                  localIBinder = paramzzg.asBinder();
                localParcel.writeStrongBinder(localIBinder);
                this.zzlW.transact(5, localParcel, null, 1);
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

      public void zzb(PlacesParams paramPlacesParams, PendingIntent paramPendingIntent)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        while (true)
        {
          try
          {
            localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
            if (paramPlacesParams != null)
            {
              localParcel.writeInt(1);
              paramPlacesParams.writeToParcel(localParcel, 0);
              if (paramPendingIntent != null)
              {
                localParcel.writeInt(1);
                paramPendingIntent.writeToParcel(localParcel, 0);
                this.zzlW.transact(12, localParcel, null, 1);
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

      public void zzb(String paramString, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
          localParcel.writeString(paramString);
          if (paramPlacesParams != null)
          {
            localParcel.writeInt(1);
            paramPlacesParams.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            IBinder localIBinder = null;
            if (paramzzg != null)
              localIBinder = paramzzg.asBinder();
            localParcel.writeStrongBinder(localIBinder);
            this.zzlW.transact(6, localParcel, null, 1);
            return;
            localParcel.writeInt(0);
          }
        }
        finally
        {
          localParcel.recycle();
        }
      }

      public void zzb(List<String> paramList, PlacesParams paramPlacesParams, zzg paramzzg)
        throws RemoteException
      {
        Parcel localParcel = Parcel.obtain();
        try
        {
          localParcel.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
          localParcel.writeStringList(paramList);
          if (paramPlacesParams != null)
          {
            localParcel.writeInt(1);
            paramPlacesParams.writeToParcel(localParcel, 0);
          }
          while (true)
          {
            IBinder localIBinder = null;
            if (paramzzg != null)
              localIBinder = paramzzg.asBinder();
            localParcel.writeStrongBinder(localIBinder);
            this.zzlW.transact(17, localParcel, null, 1);
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
 * Qualified Name:     com.google.android.gms.location.places.internal.zzf
 * JD-Core Version:    0.6.2
 */