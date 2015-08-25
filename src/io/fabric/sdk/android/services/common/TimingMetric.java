package io.fabric.sdk.android.services.common;

import android.os.SystemClock;
import android.util.Log;

public class TimingMetric
{
  private final boolean disabled;
  private long duration;
  private final String eventName;
  private long start;
  private final String tag;

  public TimingMetric(String paramString1, String paramString2)
  {
    this.eventName = paramString1;
    this.tag = paramString2;
    if (!Log.isLoggable(paramString2, 2));
    for (boolean bool = true; ; bool = false)
    {
      this.disabled = bool;
      return;
    }
  }

  private void reportToLog()
  {
    Log.v(this.tag, this.eventName + ": " + this.duration + "ms");
  }

  public long getDuration()
  {
    return this.duration;
  }

  public void startMeasuring()
  {
    try
    {
      boolean bool = this.disabled;
      if (bool);
      while (true)
      {
        return;
        this.start = SystemClock.elapsedRealtime();
        this.duration = 0L;
      }
    }
    finally
    {
    }
  }

  public void stopMeasuring()
  {
    try
    {
      boolean bool = this.disabled;
      if (bool);
      while (true)
      {
        return;
        if (this.duration == 0L)
        {
          this.duration = (SystemClock.elapsedRealtime() - this.start);
          reportToLog();
        }
      }
    }
    finally
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.TimingMetric
 * JD-Core Version:    0.6.2
 */