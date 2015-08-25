package com.segment.analytics;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class Stats
{
  private static final String STATS_THREAD_NAME = "SegmentAnalytics-Stats";
  long flushCount;
  long flushEventCount;
  final StatsHandler handler;
  long integrationOperationCount;
  long integrationOperationDuration;
  Map<String, Long> integrationOperationDurationByIntegration = new HashMap();
  final HandlerThread statsThread = new HandlerThread("SegmentAnalytics-Stats", 10);

  Stats()
  {
    this.statsThread.start();
    this.handler = new StatsHandler(this.statsThread.getLooper(), this);
  }

  StatsSnapshot createSnapshot()
  {
    return new StatsSnapshot(System.currentTimeMillis(), this.flushCount, this.flushEventCount, this.integrationOperationCount, this.integrationOperationDuration, Collections.unmodifiableMap(this.integrationOperationDurationByIntegration));
  }

  void dispatchFlush(int paramInt)
  {
    this.handler.sendMessage(this.handler.obtainMessage(1, paramInt, 0));
  }

  void dispatchIntegrationOperation(String paramString, long paramLong)
  {
    this.handler.sendMessage(this.handler.obtainMessage(2, new Pair(paramString, Long.valueOf(paramLong))));
  }

  void performFlush(int paramInt)
  {
    this.flushCount = (1L + this.flushCount);
    this.flushEventCount += paramInt;
  }

  void performIntegrationOperation(Pair<String, Long> paramPair)
  {
    this.integrationOperationCount = (1L + this.integrationOperationCount);
    this.integrationOperationDuration += ((Long)paramPair.second).longValue();
    Long localLong = (Long)this.integrationOperationDurationByIntegration.get(paramPair.first);
    if (localLong == null)
    {
      this.integrationOperationDurationByIntegration.put(paramPair.first, paramPair.second);
      return;
    }
    this.integrationOperationDurationByIntegration.put(paramPair.first, Long.valueOf(localLong.longValue() + ((Long)paramPair.second).longValue()));
  }

  void shutdown()
  {
    this.statsThread.quit();
  }

  private static class StatsHandler extends Handler
  {
    private static final int TRACK_FLUSH = 1;
    private static final int TRACK_INTEGRATION_OPERATION = 2;
    private final Stats stats;

    StatsHandler(Looper paramLooper, Stats paramStats)
    {
      super();
      this.stats = paramStats;
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        throw new AssertionError("Unknown Stats handler message: " + paramMessage);
      case 1:
        this.stats.performFlush(paramMessage.arg1);
        return;
      case 2:
      }
      this.stats.performIntegrationOperation((Pair)paramMessage.obj);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.Stats
 * JD-Core Version:    0.6.2
 */