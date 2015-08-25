package com.google.android.gms.internal;

import android.os.SystemClock;

public final class zzhv
  implements zzht
{
  private static zzhv zzVC;

  public static zzht zznd()
  {
    try
    {
      if (zzVC == null)
        zzVC = new zzhv();
      zzhv localzzhv = zzVC;
      return localzzhv;
    }
    finally
    {
    }
  }

  public long currentTimeMillis()
  {
    return System.currentTimeMillis();
  }

  public long elapsedRealtime()
  {
    return SystemClock.elapsedRealtime();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhv
 * JD-Core Version:    0.6.2
 */