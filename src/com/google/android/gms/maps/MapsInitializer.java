package com.google.android.gms.maps;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public final class MapsInitializer
{
  public static int initialize(Context paramContext)
  {
    zzv.zzr(paramContext);
    try
    {
      zzc localzzc = zzy.zzah(paramContext);
      zza(localzzc);
      return 0;
    }
    catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
    {
      return localGooglePlayServicesNotAvailableException.errorCode;
    }
  }

  public static void zza(zzc paramzzc)
  {
    try
    {
      CameraUpdateFactory.zza(paramzzc.zztL());
      BitmapDescriptorFactory.zza(paramzzc.zztM());
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.MapsInitializer
 * JD-Core Version:    0.6.2
 */