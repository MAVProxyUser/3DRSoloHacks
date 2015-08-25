package com.google.android.gms.maps.model;

import android.graphics.Bitmap;
import android.os.RemoteException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.model.internal.zzd;

public final class BitmapDescriptorFactory
{
  public static final float HUE_AZURE = 210.0F;
  public static final float HUE_BLUE = 240.0F;
  public static final float HUE_CYAN = 180.0F;
  public static final float HUE_GREEN = 120.0F;
  public static final float HUE_MAGENTA = 300.0F;
  public static final float HUE_ORANGE = 30.0F;
  public static final float HUE_RED = 0.0F;
  public static final float HUE_ROSE = 330.0F;
  public static final float HUE_VIOLET = 270.0F;
  public static final float HUE_YELLOW = 60.0F;
  private static zzd zzauS;

  public static BitmapDescriptor defaultMarker()
  {
    try
    {
      BitmapDescriptor localBitmapDescriptor = new BitmapDescriptor(zztQ().zztX());
      return localBitmapDescriptor;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static BitmapDescriptor defaultMarker(float paramFloat)
  {
    try
    {
      BitmapDescriptor localBitmapDescriptor = new BitmapDescriptor(zztQ().zzh(paramFloat));
      return localBitmapDescriptor;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static BitmapDescriptor fromAsset(String paramString)
  {
    try
    {
      BitmapDescriptor localBitmapDescriptor = new BitmapDescriptor(zztQ().zzcO(paramString));
      return localBitmapDescriptor;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static BitmapDescriptor fromBitmap(Bitmap paramBitmap)
  {
    try
    {
      BitmapDescriptor localBitmapDescriptor = new BitmapDescriptor(zztQ().zzb(paramBitmap));
      return localBitmapDescriptor;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static BitmapDescriptor fromFile(String paramString)
  {
    try
    {
      BitmapDescriptor localBitmapDescriptor = new BitmapDescriptor(zztQ().zzcP(paramString));
      return localBitmapDescriptor;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static BitmapDescriptor fromPath(String paramString)
  {
    try
    {
      BitmapDescriptor localBitmapDescriptor = new BitmapDescriptor(zztQ().zzcQ(paramString));
      return localBitmapDescriptor;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static BitmapDescriptor fromResource(int paramInt)
  {
    try
    {
      BitmapDescriptor localBitmapDescriptor = new BitmapDescriptor(zztQ().zzgB(paramInt));
      return localBitmapDescriptor;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public static void zza(zzd paramzzd)
  {
    if (zzauS != null)
      return;
    zzauS = (zzd)zzv.zzr(paramzzd);
  }

  private static zzd zztQ()
  {
    return (zzd)zzv.zzb(zzauS, "IBitmapDescriptorFactory is not initialized");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.model.BitmapDescriptorFactory
 * JD-Core Version:    0.6.2
 */