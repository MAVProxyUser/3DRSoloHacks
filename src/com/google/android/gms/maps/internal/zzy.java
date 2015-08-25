package com.google.android.gms.maps.internal;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class zzy
{
  private static Context zzauP;
  private static zzc zzauQ;

  private static Context getRemoteContext(Context paramContext)
  {
    if (zzauP == null)
      if (!zztN())
        break label23;
    label23: for (zzauP = paramContext.getApplicationContext(); ; zzauP = GooglePlayServicesUtil.getRemoteContext(paramContext))
      return zzauP;
  }

  private static <T> T zza(ClassLoader paramClassLoader, String paramString)
  {
    try
    {
      Object localObject = zzc(((ClassLoader)zzv.zzr(paramClassLoader)).loadClass(paramString));
      return localObject;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    throw new IllegalStateException("Unable to find dynamic class " + paramString);
  }

  public static zzc zzah(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    zzv.zzr(paramContext);
    if (zzauQ != null)
      return zzauQ;
    zzai(paramContext);
    zzauQ = zzaj(paramContext);
    try
    {
      zzauQ.zzb(zze.zzt(getRemoteContext(paramContext).getResources()), 7327000);
      return zzauQ;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  private static void zzai(Context paramContext)
    throws GooglePlayServicesNotAvailableException
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
    switch (i)
    {
    default:
      throw new GooglePlayServicesNotAvailableException(i);
    case 0:
    }
  }

  private static zzc zzaj(Context paramContext)
  {
    if (zztN())
    {
      Log.i(zzy.class.getSimpleName(), "Making Creator statically");
      return (zzc)zzc(zztO());
    }
    Log.i(zzy.class.getSimpleName(), "Making Creator dynamically");
    return zzc.zza.zzbq((IBinder)zza(getRemoteContext(paramContext).getClassLoader(), "com.google.android.gms.maps.internal.CreatorImpl"));
  }

  private static <T> T zzc(Class<?> paramClass)
  {
    try
    {
      Object localObject = paramClass.newInstance();
      return localObject;
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new IllegalStateException("Unable to instantiate the dynamic class " + paramClass.getName());
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
    }
    throw new IllegalStateException("Unable to call the default constructor of " + paramClass.getName());
  }

  public static boolean zztN()
  {
    return false;
  }

  private static Class<?> zztO()
  {
    try
    {
      if (Build.VERSION.SDK_INT < 15)
        return Class.forName("com.google.android.gms.maps.internal.CreatorImplGmm6");
      Class localClass = Class.forName("com.google.android.gms.maps.internal.CreatorImpl");
      return localClass;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new RuntimeException(localClassNotFoundException);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.zzy
 * JD-Core Version:    0.6.2
 */