package okio;

import java.io.IOException;
import java.io.InterruptedIOException;

public class AsyncTimeout extends Timeout
{
  private static AsyncTimeout head;
  private boolean inQueue;
  private AsyncTimeout next;
  private long timeoutAt;

  private static AsyncTimeout awaitTimeout()
    throws InterruptedException
  {
    try
    {
      AsyncTimeout localAsyncTimeout = head.next;
      if (localAsyncTimeout == null)
      {
        AsyncTimeout.class.wait();
        localAsyncTimeout = null;
      }
      while (true)
      {
        return localAsyncTimeout;
        long l1 = localAsyncTimeout.remainingNanos(System.nanoTime());
        if (l1 > 0L)
        {
          long l2 = l1 / 1000000L;
          AsyncTimeout.class.wait(l2, (int)(l1 - l2 * 1000000L));
          localAsyncTimeout = null;
        }
        else
        {
          head.next = localAsyncTimeout.next;
          localAsyncTimeout.next = null;
        }
      }
    }
    finally
    {
    }
  }

  private static boolean cancelScheduledTimeout(AsyncTimeout paramAsyncTimeout)
  {
    try
    {
      AsyncTimeout localAsyncTimeout = head;
      if (localAsyncTimeout != null)
        if (localAsyncTimeout.next == paramAsyncTimeout)
        {
          localAsyncTimeout.next = paramAsyncTimeout.next;
          paramAsyncTimeout.next = null;
        }
      for (boolean bool = false; ; bool = true)
      {
        return bool;
        localAsyncTimeout = localAsyncTimeout.next;
        break;
      }
    }
    finally
    {
    }
  }

  private long remainingNanos(long paramLong)
  {
    return this.timeoutAt - paramLong;
  }

  private static void scheduleTimeout(AsyncTimeout paramAsyncTimeout, long paramLong, boolean paramBoolean)
  {
    while (true)
    {
      AsyncTimeout localAsyncTimeout;
      try
      {
        if (head == null)
        {
          head = new AsyncTimeout();
          new Watchdog().start();
        }
        long l1 = System.nanoTime();
        if ((paramLong != 0L) && (paramBoolean))
        {
          paramAsyncTimeout.timeoutAt = (l1 + Math.min(paramLong, paramAsyncTimeout.deadlineNanoTime() - l1));
          long l2 = paramAsyncTimeout.remainingNanos(l1);
          localAsyncTimeout = head;
          if ((localAsyncTimeout.next != null) && (l2 >= localAsyncTimeout.next.remainingNanos(l1)))
            break label183;
          paramAsyncTimeout.next = localAsyncTimeout.next;
          localAsyncTimeout.next = paramAsyncTimeout;
          if (localAsyncTimeout == head)
            AsyncTimeout.class.notify();
          return;
        }
        if (paramLong != 0L)
        {
          long l3 = l1 + paramLong;
          paramAsyncTimeout.timeoutAt = l3;
          continue;
        }
      }
      finally
      {
      }
      if (paramBoolean)
      {
        paramAsyncTimeout.timeoutAt = paramAsyncTimeout.deadlineNanoTime();
      }
      else
      {
        throw new AssertionError();
        label183: localAsyncTimeout = localAsyncTimeout.next;
      }
    }
  }

  public final void enter()
  {
    if (this.inQueue)
      throw new IllegalStateException("Unbalanced enter/exit");
    long l = timeoutNanos();
    boolean bool = hasDeadline();
    if ((l == 0L) && (!bool))
      return;
    this.inQueue = true;
    scheduleTimeout(this, l, bool);
  }

  final IOException exit(IOException paramIOException)
    throws IOException
  {
    if (!exit())
      return paramIOException;
    InterruptedIOException localInterruptedIOException = new InterruptedIOException("timeout");
    localInterruptedIOException.initCause(paramIOException);
    return localInterruptedIOException;
  }

  final void exit(boolean paramBoolean)
    throws IOException
  {
    if ((exit()) && (paramBoolean))
      throw new InterruptedIOException("timeout");
  }

  public final boolean exit()
  {
    if (!this.inQueue)
      return false;
    this.inQueue = false;
    return cancelScheduledTimeout(this);
  }

  public final Sink sink(final Sink paramSink)
  {
    return new Sink()
    {
      public void close()
        throws IOException
      {
        AsyncTimeout.this.enter();
        try
        {
          paramSink.close();
          AsyncTimeout.this.exit(true);
          return;
        }
        catch (IOException localIOException)
        {
          throw AsyncTimeout.this.exit(localIOException);
        }
        finally
        {
          AsyncTimeout.this.exit(false);
        }
      }

      public void flush()
        throws IOException
      {
        AsyncTimeout.this.enter();
        try
        {
          paramSink.flush();
          AsyncTimeout.this.exit(true);
          return;
        }
        catch (IOException localIOException)
        {
          throw AsyncTimeout.this.exit(localIOException);
        }
        finally
        {
          AsyncTimeout.this.exit(false);
        }
      }

      public Timeout timeout()
      {
        return AsyncTimeout.this;
      }

      public String toString()
      {
        return "AsyncTimeout.sink(" + paramSink + ")";
      }

      public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        AsyncTimeout.this.enter();
        try
        {
          paramSink.write(paramAnonymousBuffer, paramAnonymousLong);
          AsyncTimeout.this.exit(true);
          return;
        }
        catch (IOException localIOException)
        {
          throw AsyncTimeout.this.exit(localIOException);
        }
        finally
        {
          AsyncTimeout.this.exit(false);
        }
      }
    };
  }

  public final Source source(final Source paramSource)
  {
    return new Source()
    {
      public void close()
        throws IOException
      {
        try
        {
          paramSource.close();
          AsyncTimeout.this.exit(true);
          return;
        }
        catch (IOException localIOException)
        {
          throw AsyncTimeout.this.exit(localIOException);
        }
        finally
        {
          AsyncTimeout.this.exit(false);
        }
      }

      public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        AsyncTimeout.this.enter();
        try
        {
          long l = paramSource.read(paramAnonymousBuffer, paramAnonymousLong);
          AsyncTimeout.this.exit(true);
          return l;
        }
        catch (IOException localIOException)
        {
          throw AsyncTimeout.this.exit(localIOException);
        }
        finally
        {
          AsyncTimeout.this.exit(false);
        }
      }

      public Timeout timeout()
      {
        return AsyncTimeout.this;
      }

      public String toString()
      {
        return "AsyncTimeout.source(" + paramSource + ")";
      }
    };
  }

  protected void timedOut()
  {
  }

  private static final class Watchdog extends Thread
  {
    public Watchdog()
    {
      super();
      setDaemon(true);
    }

    public void run()
    {
      while (true)
        try
        {
          AsyncTimeout localAsyncTimeout = AsyncTimeout.access$000();
          if (localAsyncTimeout != null)
            localAsyncTimeout.timedOut();
        }
        catch (InterruptedException localInterruptedException)
        {
        }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     okio.AsyncTimeout
 * JD-Core Version:    0.6.2
 */