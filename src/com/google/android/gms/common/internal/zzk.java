package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;

public abstract class zzk
{
  private static final Object zzTK = new Object();
  private static zzk zzTL;

  public static zzk zzU(Context paramContext)
  {
    synchronized (zzTK)
    {
      if (zzTL == null)
        zzTL = new zzl(paramContext.getApplicationContext());
      return zzTL;
    }
  }

  public abstract boolean zza(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString);

  public abstract boolean zza(String paramString1, ServiceConnection paramServiceConnection, String paramString2);

  public abstract void zzb(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString);

  public abstract void zzb(String paramString1, ServiceConnection paramServiceConnection, String paramString2);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzk
 * JD-Core Version:    0.6.2
 */