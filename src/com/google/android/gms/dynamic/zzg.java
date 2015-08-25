package com.google.android.gms.dynamic;

import android.content.Context;
import android.os.IBinder;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzv;

public abstract class zzg<T>
{
  private final String zzacr;
  private T zzacs;

  protected zzg(String paramString)
  {
    this.zzacr = paramString;
  }

  protected final T zzX(Context paramContext)
    throws zzg.zza
  {
    ClassLoader localClassLoader;
    if (this.zzacs == null)
    {
      zzv.zzr(paramContext);
      Context localContext = GooglePlayServicesUtil.getRemoteContext(paramContext);
      if (localContext == null)
        throw new zza("Could not get remote context.");
      localClassLoader = localContext.getClassLoader();
    }
    try
    {
      this.zzacs = zzd((IBinder)localClassLoader.loadClass(this.zzacr).newInstance());
      return this.zzacs;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new zza("Could not load creator class.", localClassNotFoundException);
    }
    catch (InstantiationException localInstantiationException)
    {
      throw new zza("Could not instantiate creator.", localInstantiationException);
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new zza("Could not access creator.", localIllegalAccessException);
    }
  }

  protected abstract T zzd(IBinder paramIBinder);

  public static class zza extends Exception
  {
    public zza(String paramString)
    {
      super();
    }

    public zza(String paramString, Throwable paramThrowable)
    {
      super(paramThrowable);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.dynamic.zzg
 * JD-Core Version:    0.6.2
 */