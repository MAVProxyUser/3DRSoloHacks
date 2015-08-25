package com.google.android.gms.common.stats;

import android.os.SystemClock;
import android.support.v4.util.SimpleArrayMap;
import android.util.Log;

public class zze
{
  private final SimpleArrayMap<Long, Long> zzVA;
  private final long zzVy;
  private final int zzVz;

  public zze()
  {
    this.zzVy = 60000L;
    this.zzVz = 10;
    this.zzVA = new SimpleArrayMap(10);
  }

  public zze(int paramInt, long paramLong)
  {
    this.zzVy = paramLong;
    this.zzVz = paramInt;
    this.zzVA = new SimpleArrayMap();
  }

  private void zzc(long paramLong1, long paramLong2)
  {
    for (int i = -1 + this.zzVA.size(); i >= 0; i--)
      if (paramLong2 - ((Long)this.zzVA.valueAt(i)).longValue() > paramLong1)
        this.zzVA.removeAt(i);
  }

  public boolean zzA(long paramLong)
  {
    while (true)
    {
      try
      {
        if (this.zzVA.remove(Long.valueOf(paramLong)) != null)
        {
          bool = true;
          return bool;
        }
      }
      finally
      {
      }
      boolean bool = false;
    }
  }

  public Long zza(Long paramLong)
  {
    long l1 = SystemClock.elapsedRealtime();
    long l2 = this.zzVy;
    try
    {
      while (this.zzVA.size() >= this.zzVz)
      {
        zzc(l2, l1);
        l2 /= 2L;
        Log.w("PassiveTimedConnectionMap", "The max capacity " + this.zzVz + " is not enough. Current durationThreshold is: " + l2);
      }
    }
    finally
    {
    }
    Long localLong = (Long)this.zzVA.put(paramLong, Long.valueOf(l1));
    return localLong;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zze
 * JD-Core Version:    0.6.2
 */