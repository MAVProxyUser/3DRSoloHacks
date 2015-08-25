package com.google.android.gms.internal;

import android.content.res.Configuration;
import android.content.res.Resources;

public final class zzhw
{
  public static boolean zzb(Resources paramResources)
  {
    if (paramResources == null);
    while (true)
    {
      return false;
      if ((0xF & paramResources.getConfiguration().screenLayout) > 3);
      for (int i = 1; ((zzic.zzne()) && (i != 0)) || (zzc(paramResources)); i = 0)
        return true;
    }
  }

  private static boolean zzc(Resources paramResources)
  {
    Configuration localConfiguration = paramResources.getConfiguration();
    boolean bool1 = zzic.zzng();
    boolean bool2 = false;
    if (bool1)
    {
      int i = 0xF & localConfiguration.screenLayout;
      bool2 = false;
      if (i <= 3)
      {
        int j = localConfiguration.smallestScreenWidthDp;
        bool2 = false;
        if (j >= 600)
          bool2 = true;
      }
    }
    return bool2;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhw
 * JD-Core Version:    0.6.2
 */