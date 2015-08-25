package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public abstract class EnabledEventsStrategy<T>
  implements EventsStrategy<T>
{
  protected static final int UNDEFINED_ROLLOVER_INTERVAL_SECONDS = -1;
  protected final Context context;
  protected final ScheduledExecutorService executorService;
  protected final EventsFilesManager<T> filesManager;
  protected volatile int rolloverIntervalSeconds = -1;
  protected final AtomicReference<ScheduledFuture<?>> scheduledRolloverFutureRef;

  public EnabledEventsStrategy(Context paramContext, ScheduledExecutorService paramScheduledExecutorService, EventsFilesManager<T> paramEventsFilesManager)
  {
    this.context = paramContext;
    this.executorService = paramScheduledExecutorService;
    this.filesManager = paramEventsFilesManager;
    this.scheduledRolloverFutureRef = new AtomicReference();
  }

  public void cancelTimeBasedFileRollOver()
  {
    if (this.scheduledRolloverFutureRef.get() != null)
    {
      CommonUtils.logControlled(this.context, "Cancelling time-based rollover because no events are currently being generated.");
      ((ScheduledFuture)this.scheduledRolloverFutureRef.get()).cancel(false);
      this.scheduledRolloverFutureRef.set(null);
    }
  }

  protected void configureRollover(int paramInt)
  {
    this.rolloverIntervalSeconds = paramInt;
    scheduleTimeBasedFileRollOver(0, this.rolloverIntervalSeconds);
  }

  public void deleteAllEvents()
  {
    this.filesManager.deleteAllEventsFiles();
  }

  public void recordEvent(T paramT)
  {
    CommonUtils.logControlled(this.context, paramT.toString());
    try
    {
      this.filesManager.writeEvent(paramT);
      scheduleTimeBasedRollOverIfNeeded();
      return;
    }
    catch (IOException localIOException)
    {
      while (true)
        CommonUtils.logControlledError(this.context, "Failed to write event.", localIOException);
    }
  }

  public boolean rollFileOver()
  {
    try
    {
      boolean bool = this.filesManager.rollFileOver();
      return bool;
    }
    catch (IOException localIOException)
    {
      CommonUtils.logControlledError(this.context, "Failed to roll file over.", localIOException);
    }
    return false;
  }

  protected void scheduleTimeBasedFileRollOver(int paramInt1, int paramInt2)
  {
    try
    {
      TimeBasedFileRollOverRunnable localTimeBasedFileRollOverRunnable = new TimeBasedFileRollOverRunnable(this.context, this);
      CommonUtils.logControlled(this.context, "Scheduling time based file roll over every " + paramInt2 + " seconds");
      this.scheduledRolloverFutureRef.set(this.executorService.scheduleAtFixedRate(localTimeBasedFileRollOverRunnable, paramInt1, paramInt2, TimeUnit.SECONDS));
      return;
    }
    catch (RejectedExecutionException localRejectedExecutionException)
    {
      CommonUtils.logControlledError(this.context, "Failed to schedule time based file roll over", localRejectedExecutionException);
    }
  }

  public void scheduleTimeBasedRollOverIfNeeded()
  {
    int i;
    if (this.rolloverIntervalSeconds != -1)
    {
      i = 1;
      if (this.scheduledRolloverFutureRef.get() != null)
        break label48;
    }
    label48: for (int j = 1; ; j = 0)
    {
      if ((i != 0) && (j != 0))
        scheduleTimeBasedFileRollOver(this.rolloverIntervalSeconds, this.rolloverIntervalSeconds);
      return;
      i = 0;
      break;
    }
  }

  void sendAndCleanUpIfSuccess()
  {
    FilesSender localFilesSender = getFilesSender();
    if (localFilesSender == null)
      CommonUtils.logControlled(this.context, "skipping files send because we don't yet know the target endpoint");
    while (true)
    {
      return;
      CommonUtils.logControlled(this.context, "Sending all files");
      int i = 0;
      Object localObject = this.filesManager.getBatchOfFilesToSend();
      try
      {
        Context localContext = this.context;
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(((List)localObject).size());
        CommonUtils.logControlled(localContext, String.format(localLocale, "attempt to send batch of %d files", arrayOfObject));
        while (true)
        {
          if (((List)localObject).size() > 0)
          {
            boolean bool = localFilesSender.send((List)localObject);
            if (bool)
            {
              i += ((List)localObject).size();
              this.filesManager.deleteSentFiles((List)localObject);
            }
            if (bool);
          }
          else
          {
            if (i != 0)
              break;
            this.filesManager.deleteOldestInRollOverIfOverMax();
            return;
          }
          List localList = this.filesManager.getBatchOfFilesToSend();
          localObject = localList;
        }
      }
      catch (Exception localException)
      {
        while (true)
          CommonUtils.logControlledError(this.context, "Failed to send batch of analytics files to server: " + localException.getMessage(), localException);
      }
    }
  }

  public void sendEvents()
  {
    sendAndCleanUpIfSuccess();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.EnabledEventsStrategy
 * JD-Core Version:    0.6.2
 */