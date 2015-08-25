package com.google.android.gms.common.internal;

import android.os.Looper;
import android.text.TextUtils;

public final class zzv
{
  public static void zzP(boolean paramBoolean)
  {
    if (!paramBoolean)
      throw new IllegalStateException();
  }

  public static void zzQ(boolean paramBoolean)
  {
    if (!paramBoolean)
      throw new IllegalArgumentException();
  }

  public static int zza(int paramInt, Object paramObject)
  {
    if (paramInt == 0)
      throw new IllegalArgumentException(String.valueOf(paramObject));
    return paramInt;
  }

  public static void zza(boolean paramBoolean, Object paramObject)
  {
    if (!paramBoolean)
      throw new IllegalStateException(String.valueOf(paramObject));
  }

  public static void zza(boolean paramBoolean, String paramString, Object[] paramArrayOfObject)
  {
    if (!paramBoolean)
      throw new IllegalStateException(String.format(paramString, paramArrayOfObject));
  }

  public static int zzaR(int paramInt)
  {
    if (paramInt == 0)
      throw new IllegalArgumentException("Given Integer is zero");
    return paramInt;
  }

  public static <T> T zzb(T paramT, Object paramObject)
  {
    if (paramT == null)
      throw new NullPointerException(String.valueOf(paramObject));
    return paramT;
  }

  public static void zzb(boolean paramBoolean, Object paramObject)
  {
    if (!paramBoolean)
      throw new IllegalArgumentException(String.valueOf(paramObject));
  }

  public static void zzb(boolean paramBoolean, String paramString, Object[] paramArrayOfObject)
  {
    if (!paramBoolean)
      throw new IllegalArgumentException(String.format(paramString, paramArrayOfObject));
  }

  public static void zzbI(String paramString)
  {
    if (Looper.myLooper() != Looper.getMainLooper())
      throw new IllegalStateException(paramString);
  }

  public static void zzbJ(String paramString)
  {
    if (Looper.myLooper() == Looper.getMainLooper())
      throw new IllegalStateException(paramString);
  }

  public static String zzbS(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      throw new IllegalArgumentException("Given String is empty or null");
    return paramString;
  }

  public static String zzh(String paramString, Object paramObject)
  {
    if (TextUtils.isEmpty(paramString))
      throw new IllegalArgumentException(String.valueOf(paramObject));
    return paramString;
  }

  public static <T> T zzr(T paramT)
  {
    if (paramT == null)
      throw new NullPointerException("null reference");
    return paramT;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzv
 * JD-Core Version:    0.6.2
 */