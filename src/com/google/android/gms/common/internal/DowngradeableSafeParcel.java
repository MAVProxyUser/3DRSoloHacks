package com.google.android.gms.common.internal;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import java.lang.reflect.Field;

public abstract class DowngradeableSafeParcel
  implements SafeParcelable
{
  private static final Object zzTd = new Object();
  private static ClassLoader zzTe = null;
  private static Integer zzTf = null;
  private boolean zzTg = false;

  private static boolean zza(Class<?> paramClass)
  {
    try
    {
      boolean bool = "SAFE_PARCELABLE_NULL_STRING".equals(paramClass.getField("NULL").get(null));
      return bool;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      return false;
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
    }
    return false;
  }

  protected static boolean zzbK(String paramString)
  {
    ClassLoader localClassLoader = zzlO();
    if (localClassLoader == null)
      return true;
    try
    {
      boolean bool = zza(localClassLoader.loadClass(paramString));
      return bool;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  protected static ClassLoader zzlO()
  {
    synchronized (zzTd)
    {
      ClassLoader localClassLoader = zzTe;
      return localClassLoader;
    }
  }

  protected static Integer zzlP()
  {
    synchronized (zzTd)
    {
      Integer localInteger = zzTf;
      return localInteger;
    }
  }

  protected boolean zzlQ()
  {
    return this.zzTg;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.DowngradeableSafeParcel
 * JD-Core Version:    0.6.2
 */