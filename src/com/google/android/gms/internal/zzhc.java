package com.google.android.gms.internal;

import android.os.Binder;

public abstract class zzhc<T>
{
  private static zza zzRo = null;
  private static int zzRp = 0;
  private static final Object zznu = new Object();
  private T zzHW = null;
  protected final String zzra;
  protected final T zzrb;

  protected zzhc(String paramString, T paramT)
  {
    this.zzra = paramString;
    this.zzrb = paramT;
  }

  public static boolean isInitialized()
  {
    return zzRo != null;
  }

  public static zzhc<Float> zza(String paramString, Float paramFloat)
  {
    return new zzhc.4(paramString, paramFloat);
  }

  public static zzhc<Integer> zza(String paramString, Integer paramInteger)
  {
    return new zzhc.3(paramString, paramInteger);
  }

  public static zzhc<Long> zza(String paramString, Long paramLong)
  {
    return new zzhc.2(paramString, paramLong);
  }

  public static zzhc<Boolean> zzg(String paramString, boolean paramBoolean)
  {
    return new zzhc.1(paramString, Boolean.valueOf(paramBoolean));
  }

  public static int zzlj()
  {
    return zzRp;
  }

  public static zzhc<String> zzr(String paramString1, String paramString2)
  {
    return new zzhc.5(paramString1, paramString2);
  }

  public final T get()
  {
    if (this.zzHW != null)
      return this.zzHW;
    return zzbz(this.zzra);
  }

  protected abstract T zzbz(String paramString);

  public final T zzlk()
  {
    long l = Binder.clearCallingIdentity();
    try
    {
      Object localObject2 = get();
      return localObject2;
    }
    finally
    {
      Binder.restoreCallingIdentity(l);
    }
  }

  private static abstract interface zza
  {
    public abstract Long getLong(String paramString, Long paramLong);

    public abstract String getString(String paramString1, String paramString2);

    public abstract Boolean zzb(String paramString, Boolean paramBoolean);

    public abstract Float zzb(String paramString, Float paramFloat);

    public abstract Integer zzb(String paramString, Integer paramInteger);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhc
 * JD-Core Version:    0.6.2
 */