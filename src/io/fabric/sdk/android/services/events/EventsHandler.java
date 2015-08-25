package io.fabric.sdk.android.services.events;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

public abstract class EventsHandler<T>
  implements EventsStorageListener
{
  protected final Context context;
  protected final ScheduledExecutorService executor;
  protected EventsStrategy<T> strategy;

  public EventsHandler(Context paramContext, EventsStrategy<T> paramEventsStrategy, EventsFilesManager paramEventsFilesManager, ScheduledExecutorService paramScheduledExecutorService)
  {
    this.context = paramContext.getApplicationContext();
    this.executor = paramScheduledExecutorService;
    this.strategy = paramEventsStrategy;
    paramEventsFilesManager.registerRollOverListener(this);
  }

  public void disable()
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          EventsStrategy localEventsStrategy = EventsHandler.this.strategy;
          EventsHandler.this.strategy = EventsHandler.this.getDisabledEventsStrategy();
          localEventsStrategy.deleteAllEvents();
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(EventsHandler.this.context, "Failed to disable events.", localException);
        }
      }
    });
  }

  protected void executeAsync(Runnable paramRunnable)
  {
    try
    {
      this.executor.submit(paramRunnable);
      return;
    }
    catch (Exception localException)
    {
      CommonUtils.logControlledError(this.context, "Failed to submit events task", localException);
    }
  }

  protected void executeSync(Runnable paramRunnable)
  {
    try
    {
      this.executor.submit(paramRunnable).get();
      return;
    }
    catch (Exception localException)
    {
      CommonUtils.logControlledError(this.context, "Failed to run events task", localException);
    }
  }

  protected abstract EventsStrategy<T> getDisabledEventsStrategy();

  public void onRollOver(String paramString)
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          EventsHandler.this.strategy.sendEvents();
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(EventsHandler.this.context, "Failed to send events files.", localException);
        }
      }
    });
  }

  protected void recordEventAsync(final T paramT, final boolean paramBoolean)
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          EventsHandler.this.strategy.recordEvent(paramT);
          if (paramBoolean)
            EventsHandler.this.strategy.rollFileOver();
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(EventsHandler.this.context, "Failed to record event.", localException);
        }
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.EventsHandler
 * JD-Core Version:    0.6.2
 */