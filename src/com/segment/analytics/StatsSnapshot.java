package com.segment.analytics;

import java.util.Map;

public class StatsSnapshot
{
  public final long flushCount;
  public final long flushEventCount;
  public final float integrationOperationAverageDuration;
  public final long integrationOperationCount;
  public final long integrationOperationDuration;
  public final Map<String, Long> integrationOperationDurationByIntegration;
  public final long timestamp;

  public StatsSnapshot(long paramLong1, long paramLong2, long paramLong3, long paramLong4, long paramLong5, Map<String, Long> paramMap)
  {
    this.timestamp = paramLong1;
    this.flushCount = paramLong2;
    this.flushEventCount = paramLong3;
    this.integrationOperationCount = paramLong4;
    this.integrationOperationDuration = paramLong5;
    if (paramLong4 == 0L);
    for (float f = 0.0F; ; f = (float)paramLong5 / (float)paramLong4)
    {
      this.integrationOperationAverageDuration = f;
      this.integrationOperationDurationByIntegration = paramMap;
      return;
    }
  }

  public String toString()
  {
    return "StatsSnapshot{timestamp=" + this.timestamp + ", flushCount=" + this.flushCount + ", flushEventCount=" + this.flushEventCount + ", integrationOperationCount=" + this.integrationOperationCount + ", integrationOperationDuration=" + this.integrationOperationDuration + ", integrationOperationAverageDuration=" + this.integrationOperationAverageDuration + ", integrationOperationDurationByIntegration=" + this.integrationOperationDurationByIntegration + '}';
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.StatsSnapshot
 * JD-Core Version:    0.6.2
 */