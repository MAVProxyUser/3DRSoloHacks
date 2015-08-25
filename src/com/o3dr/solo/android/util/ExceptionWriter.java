package com.o3dr.solo.android.util;

import android.content.Context;
import java.io.File;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ExceptionWriter
{
  private final Context context;
  private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);

  public ExceptionWriter(Context paramContext)
  {
    this.context = paramContext;
  }

  public void saveStackTraceToSD(Throwable paramThrowable)
  {
    if (paramThrowable == null);
    while (true)
    {
      return;
      try
      {
        File localFile1 = this.context.getExternalFilesDir(null);
        if (localFile1 != null)
        {
          File localFile2 = new File(localFile1, "/log_cat/");
          if ((localFile2.exists()) || (localFile2.mkdirs()))
          {
            PrintStream localPrintStream = new PrintStream(new File(localFile2, this.sdf.format(new Date(System.currentTimeMillis())) + ".log"));
            paramThrowable.printStackTrace(localPrintStream);
            localPrintStream.close();
            return;
          }
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.ExceptionWriter
 * JD-Core Version:    0.6.2
 */