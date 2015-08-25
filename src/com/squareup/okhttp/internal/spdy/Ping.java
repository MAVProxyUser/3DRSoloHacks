package com.squareup.okhttp.internal.spdy;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class Ping
{
  private final CountDownLatch latch = new CountDownLatch(1);
  private long received = -1L;
  private long sent = -1L;

  void cancel()
  {
    if ((this.received != -1L) || (this.sent == -1L))
      throw new IllegalStateException();
    this.received = (this.sent - 1L);
    this.latch.countDown();
  }

  void receive()
  {
    if ((this.received != -1L) || (this.sent == -1L))
      throw new IllegalStateException();
    this.received = System.nanoTime();
    this.latch.countDown();
  }

  public long roundTripTime()
    throws InterruptedException
  {
    this.latch.await();
    return this.received - this.sent;
  }

  public long roundTripTime(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (this.latch.await(paramLong, paramTimeUnit))
      return this.received - this.sent;
    return -2L;
  }

  void send()
  {
    if (this.sent != -1L)
      throw new IllegalStateException();
    this.sent = System.nanoTime();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Ping
 * JD-Core Version:    0.6.2
 */