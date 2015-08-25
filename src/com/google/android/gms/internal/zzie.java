package com.google.android.gms.internal;

public final class zzie
{
  private static int zza(StackTraceElement[] paramArrayOfStackTraceElement1, StackTraceElement[] paramArrayOfStackTraceElement2)
  {
    int i = 0;
    int j = paramArrayOfStackTraceElement2.length;
    int k = paramArrayOfStackTraceElement1.length;
    while (true)
    {
      k--;
      if (k < 0)
        break;
      j--;
      if ((j < 0) || (!paramArrayOfStackTraceElement2[j].equals(paramArrayOfStackTraceElement1[k])))
        break;
      i++;
    }
    return i;
  }

  public static String zznn()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Throwable localThrowable1 = new Throwable();
    StackTraceElement[] arrayOfStackTraceElement1 = localThrowable1.getStackTrace();
    localStringBuilder.append("Async stack trace:");
    int i = arrayOfStackTraceElement1.length;
    for (int j = 0; j < i; j++)
    {
      StackTraceElement localStackTraceElement = arrayOfStackTraceElement1[j];
      localStringBuilder.append("\n\tat ").append(localStackTraceElement);
    }
    Throwable localThrowable2 = localThrowable1.getCause();
    Object localObject = arrayOfStackTraceElement1;
    Throwable localThrowable3 = localThrowable2;
    while (localThrowable3 != null)
    {
      localStringBuilder.append("\nCaused by: ");
      localStringBuilder.append(localThrowable3);
      StackTraceElement[] arrayOfStackTraceElement2 = localThrowable3.getStackTrace();
      int k = zza(arrayOfStackTraceElement2, (StackTraceElement[])localObject);
      for (int m = 0; m < arrayOfStackTraceElement2.length - k; m++)
        localStringBuilder.append("\n\tat " + arrayOfStackTraceElement2[m]);
      if (k > 0)
        localStringBuilder.append("\n\t... " + k + " more");
      localThrowable3 = localThrowable3.getCause();
      localObject = arrayOfStackTraceElement2;
    }
    return localStringBuilder.toString();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzie
 * JD-Core Version:    0.6.2
 */