package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Logger;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public final class ExecutorUtils
{
  private static final long DEFAULT_TERMINATION_TIMEOUT = 2L;

  private static final void addDelayedShutdownHook(String paramString, ExecutorService paramExecutorService)
  {
    addDelayedShutdownHook(paramString, paramExecutorService, 2L, TimeUnit.SECONDS);
  }

  public static final void addDelayedShutdownHook(String paramString, final ExecutorService paramExecutorService, final long paramLong, TimeUnit paramTimeUnit)
  {
    Runtime.getRuntime().addShutdownHook(new Thread(new BackgroundPriorityRunnable()
    {
      public void onRun()
      {
        try
        {
          Fabric.getLogger().d("Fabric", "Executing shutdown hook for " + this.val$serviceName);
          paramExecutorService.shutdown();
          if (!paramExecutorService.awaitTermination(paramLong, this.val$timeUnit))
          {
            Fabric.getLogger().d("Fabric", this.val$serviceName + " did not shut down in the" + " allocated time. Requesting immediate shutdown.");
            paramExecutorService.shutdownNow();
          }
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          Logger localLogger = Fabric.getLogger();
          Locale localLocale = Locale.US;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = this.val$serviceName;
          localLogger.d("Fabric", String.format(localLocale, "Interrupted while waiting for %s to shut down. Requesting immediate shutdown.", arrayOfObject));
          paramExecutorService.shutdownNow();
        }
      }
    }
    , "Crashlytics Shutdown Hook for " + paramString));
  }

  public static ExecutorService buildSingleThreadExecutorService(String paramString)
  {
    ExecutorService localExecutorService = Executors.newSingleThreadExecutor(getNamedThreadFactory(paramString));
    addDelayedShutdownHook(paramString, localExecutorService);
    return localExecutorService;
  }

  public static ScheduledExecutorService buildSingleThreadScheduledExecutorService(String paramString)
  {
    ScheduledExecutorService localScheduledExecutorService = Executors.newSingleThreadScheduledExecutor(getNamedThreadFactory(paramString));
    addDelayedShutdownHook(paramString, localScheduledExecutorService);
    return localScheduledExecutorService;
  }

  public static final ThreadFactory getNamedThreadFactory(String paramString)
  {
    return new ThreadFactory()
    {
      public Thread newThread(final Runnable paramAnonymousRunnable)
      {
        Thread localThread = Executors.defaultThreadFactory().newThread(new BackgroundPriorityRunnable()
        {
          public void onRun()
          {
            paramAnonymousRunnable.run();
          }
        });
        localThread.setName(this.val$threadNameTemplate + this.val$count.getAndIncrement());
        return localThread;
      }
    };
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.ExecutorUtils
 * JD-Core Version:    0.6.2
 */