package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager;
import java.util.regex.Pattern;

public final class zzhs
{
  private static Pattern zzVB = null;

  public static boolean zzV(Context paramContext)
  {
    return paramContext.getPackageManager().hasSystemFeature("android.hardware.type.watch");
  }

  public static int zzbh(int paramInt)
  {
    return paramInt / 1000;
  }

  public static int zzbi(int paramInt)
  {
    return paramInt % 1000 / 100;
  }

  public static boolean zzbj(int paramInt)
  {
    return zzbi(paramInt) == 3;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhs
 * JD-Core Version:    0.6.2
 */