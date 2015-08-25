package com.google.android.gms.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.util.Log;

public class zzlj
  implements zzlk.zza
{
  private final zzlk zzayn;
  private boolean zzayo;

  public zzlj(Context paramContext, int paramInt)
  {
    this(paramContext, paramInt, null);
  }

  public zzlj(Context paramContext, int paramInt, String paramString)
  {
    this(paramContext, paramInt, paramString, null, true);
  }

  public zzlj(Context paramContext, int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    if (paramContext != paramContext.getApplicationContext());
    for (String str = paramContext.getClass().getName(); ; str = "OneTimePlayLogger")
    {
      this.zzayn = new zzlk(paramContext, paramInt, paramString1, paramString2, this, paramBoolean, str);
      this.zzayo = true;
      return;
    }
  }

  private void zzvo()
  {
    if (!this.zzayo)
      throw new IllegalStateException("Cannot reuse one-time logger after sending.");
  }

  public void send()
  {
    zzvo();
    this.zzayn.start();
    this.zzayo = false;
  }

  public void zza(String paramString, byte[] paramArrayOfByte, String[] paramArrayOfString)
  {
    zzvo();
    this.zzayn.zzb(paramString, paramArrayOfByte, paramArrayOfString);
  }

  public void zzf(PendingIntent paramPendingIntent)
  {
    Log.w("OneTimePlayLogger", "logger connection failed: " + paramPendingIntent);
  }

  public void zzvp()
  {
    this.zzayn.stop();
  }

  public void zzvq()
  {
    Log.w("OneTimePlayLogger", "logger connection failed");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzlj
 * JD-Core Version:    0.6.2
 */