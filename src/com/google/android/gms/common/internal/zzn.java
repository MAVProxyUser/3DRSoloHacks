package com.google.android.gms.common.internal;

import android.content.Context;
import android.util.Log;
import com.google.android.gms.internal.zzlj;

public final class zzn
{
  private final String zzTY;

  public zzn(String paramString)
  {
    this.zzTY = ((String)zzv.zzr(paramString));
  }

  public void zza(Context paramContext, String paramString1, String paramString2, Throwable paramThrowable)
  {
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    StringBuilder localStringBuilder = new StringBuilder();
    for (int i = 0; (i < arrayOfStackTraceElement.length) && (i < 2); i++)
    {
      localStringBuilder.append(arrayOfStackTraceElement[i].toString());
      localStringBuilder.append("\n");
    }
    zzlj localzzlj = new zzlj(paramContext, 10);
    String[] arrayOfString = new String[2];
    arrayOfString[0] = "GMS_WTF";
    arrayOfString[1] = localStringBuilder.toString();
    localzzlj.zza("GMS_WTF", null, arrayOfString);
    localzzlj.send();
    if (zzaQ(7))
    {
      Log.e(paramString1, paramString2, paramThrowable);
      Log.wtf(paramString1, paramString2, paramThrowable);
    }
  }

  public void zza(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (zzaQ(4))
      Log.i(paramString1, paramString2, paramThrowable);
  }

  public boolean zzaQ(int paramInt)
  {
    return Log.isLoggable(this.zzTY, paramInt);
  }

  public void zzb(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (zzaQ(5))
      Log.w(paramString1, paramString2, paramThrowable);
  }

  public void zzc(String paramString1, String paramString2, Throwable paramThrowable)
  {
    if (zzaQ(6))
      Log.e(paramString1, paramString2, paramThrowable);
  }

  public void zzs(String paramString1, String paramString2)
  {
    if (zzaQ(3))
      Log.d(paramString1, paramString2);
  }

  public void zzt(String paramString1, String paramString2)
  {
    if (zzaQ(5))
      Log.w(paramString1, paramString2);
  }

  public void zzu(String paramString1, String paramString2)
  {
    if (zzaQ(6))
      Log.e(paramString1, paramString2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzn
 * JD-Core Version:    0.6.2
 */