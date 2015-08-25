package com.google.android.gms.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Binder;
import java.util.Iterator;
import java.util.List;

public class zzid
{
  public static String zzW(Context paramContext)
  {
    return zzk(paramContext, Binder.getCallingPid());
  }

  private static String zza(StackTraceElement[] paramArrayOfStackTraceElement, int paramInt)
  {
    if (paramInt + 4 >= paramArrayOfStackTraceElement.length)
      return "<bottom of call stack>";
    StackTraceElement localStackTraceElement = paramArrayOfStackTraceElement[(paramInt + 4)];
    return localStackTraceElement.getClassName() + "." + localStackTraceElement.getMethodName() + ":" + localStackTraceElement.getLineNumber();
  }

  public static String zzj(int paramInt1, int paramInt2)
  {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = paramInt2 + paramInt1;
    while (paramInt1 < i)
    {
      localStringBuffer.append(zza(arrayOfStackTraceElement, paramInt1)).append(" ");
      paramInt1++;
    }
    return localStringBuffer.toString();
  }

  public static String zzk(Context paramContext, int paramInt)
  {
    List localList = ((ActivityManager)paramContext.getSystemService("activity")).getRunningAppProcesses();
    if (localList != null)
    {
      Iterator localIterator = localList.iterator();
      while (localIterator.hasNext())
      {
        ActivityManager.RunningAppProcessInfo localRunningAppProcessInfo = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (localRunningAppProcessInfo.pid == paramInt)
          return localRunningAppProcessInfo.processName;
      }
    }
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzid
 * JD-Core Version:    0.6.2
 */