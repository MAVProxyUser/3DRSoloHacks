package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.playlog.internal.LogEvent;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import com.google.android.gms.playlog.internal.zzd;
import com.google.android.gms.playlog.internal.zzf;

public class zzlk
{
  private final zzf zzayp;
  private PlayLoggerContext zzayq;

  public zzlk(Context paramContext, int paramInt, String paramString1, String paramString2, zza paramzza, boolean paramBoolean, String paramString3)
  {
    String str = paramContext.getPackageName();
    try
    {
      i = paramContext.getPackageManager().getPackageInfo(str, 0).versionCode;
      this.zzayq = new PlayLoggerContext(str, i, paramInt, paramString1, paramString2, paramBoolean);
      this.zzayp = new zzf(paramContext, paramContext.getMainLooper(), new zzd(paramzza), new zze(null, null, null, 49, null, str, paramString3, null));
      return;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
      {
        Log.wtf("PlayLogger", "This can't happen.");
        int i = 0;
      }
    }
  }

  public void start()
  {
    this.zzayp.start();
  }

  public void stop()
  {
    this.zzayp.stop();
  }

  public void zza(long paramLong, String paramString, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    this.zzayp.zzb(this.zzayq, new LogEvent(paramLong, paramString, paramArrayOfByte, paramArrayOfString));
  }

  public void zzb(String paramString, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    zza(System.currentTimeMillis(), paramString, paramArrayOfByte, paramArrayOfString);
  }

  public static abstract interface zza
  {
    public abstract void zzf(PendingIntent paramPendingIntent);

    public abstract void zzvp();

    public abstract void zzvq();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzlk
 * JD-Core Version:    0.6.2
 */