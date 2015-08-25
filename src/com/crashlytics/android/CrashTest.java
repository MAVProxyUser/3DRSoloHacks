package com.crashlytics.android;

import android.os.AsyncTask;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;

public class CrashTest
{
  private void privateMethodThatThrowsException(String paramString)
  {
    throw new RuntimeException(paramString);
  }

  public void crashAsyncTask(final long paramLong)
  {
    AsyncTask local1 = new AsyncTask()
    {
      protected Void doInBackground(Void[] paramAnonymousArrayOfVoid)
      {
        try
        {
          Thread.sleep(paramLong);
          label7: CrashTest.this.throwRuntimeException("Background thread crash");
          return null;
        }
        catch (InterruptedException localInterruptedException)
        {
          break label7;
        }
      }
    };
    Void[] arrayOfVoid = new Void[1];
    arrayOfVoid[0] = ((Void)null);
    local1.execute(arrayOfVoid);
  }

  public void indexOutOfBounds()
  {
    int i = new int[2][10];
    Fabric.getLogger().d("Fabric", "Out of bounds value: " + i);
  }

  public int stackOverflow()
  {
    return stackOverflow() + (int)Math.random();
  }

  public void throwFiveChainedExceptions()
  {
    try
    {
      privateMethodThatThrowsException("1");
      return;
    }
    catch (Exception localException1)
    {
      try
      {
        throw new RuntimeException("2", localException1);
      }
      catch (Exception localException2)
      {
        try
        {
          throw new RuntimeException("3", localException2);
        }
        catch (Exception localException3)
        {
          try
          {
            throw new RuntimeException("4", localException3);
          }
          catch (Exception localException4)
          {
            throw new RuntimeException("5", localException4);
          }
        }
      }
    }
  }

  public void throwRuntimeException(String paramString)
  {
    throw new RuntimeException(paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.CrashTest
 * JD-Core Version:    0.6.2
 */