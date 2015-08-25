package com.crashlytics.android.answers;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.events.EventsHandler;
import io.fabric.sdk.android.services.events.EventsStrategy;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.settings.AnalyticsSettingsData;
import java.util.HashMap;
import java.util.concurrent.ScheduledExecutorService;

class SessionAnalyticsManager extends EventsHandler<SessionEvent>
{
  private final SessionEventMetadata metadata;

  public SessionAnalyticsManager(Context paramContext, SessionEventMetadata paramSessionEventMetadata, SessionAnalyticsFilesManager paramSessionAnalyticsFilesManager, HttpRequestFactory paramHttpRequestFactory)
  {
    this(paramContext, paramSessionEventMetadata, paramSessionAnalyticsFilesManager, ExecutorUtils.buildSingleThreadScheduledExecutorService("Crashlytics SAM"), paramHttpRequestFactory);
  }

  SessionAnalyticsManager(Context paramContext, SessionEventMetadata paramSessionEventMetadata, SessionAnalyticsFilesManager paramSessionAnalyticsFilesManager, ScheduledExecutorService paramScheduledExecutorService, HttpRequestFactory paramHttpRequestFactory)
  {
    super(paramContext, new EnabledSessionAnalyticsManagerStrategy(paramContext, paramScheduledExecutorService, paramSessionAnalyticsFilesManager, paramHttpRequestFactory), paramSessionAnalyticsFilesManager, paramScheduledExecutorService);
    this.metadata = paramSessionEventMetadata;
  }

  private void recordEventAsync(SessionEvent.Type paramType, Activity paramActivity, boolean paramBoolean)
  {
    recordEventAsync(SessionEvent.buildActivityLifecycleEvent(this.metadata, paramType, paramActivity), paramBoolean);
  }

  protected EventsStrategy<SessionEvent> getDisabledEventsStrategy()
  {
    return new DisabledSessionAnalyticsManagerStrategy();
  }

  public void onCrash(final String paramString)
  {
    if (Looper.myLooper() == Looper.getMainLooper())
      throw new IllegalStateException("onCrash called from main thread!!!");
    executeSync(new Runnable()
    {
      public void run()
      {
        try
        {
          SessionAnalyticsManager.this.strategy.recordEvent(SessionEvent.buildCrashEvent(SessionAnalyticsManager.this.metadata, paramString));
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(Answers.getInstance().getContext(), "Crashlytics failed to record crash event", localException);
        }
      }
    });
  }

  public void onCreate(Activity paramActivity)
  {
    recordEventAsync(SessionEvent.Type.CREATE, paramActivity, false);
  }

  public void onDestroy(Activity paramActivity)
  {
    recordEventAsync(SessionEvent.Type.DESTROY, paramActivity, false);
  }

  public void onError(String paramString)
  {
    recordEventAsync(SessionEvent.buildErrorEvent(this.metadata, paramString), false);
  }

  public void onInstall()
  {
    recordEventAsync(SessionEvent.buildEvent(this.metadata, SessionEvent.Type.INSTALL, new HashMap()), true);
  }

  public void onPause(Activity paramActivity)
  {
    recordEventAsync(SessionEvent.Type.PAUSE, paramActivity, false);
  }

  public void onResume(Activity paramActivity)
  {
    recordEventAsync(SessionEvent.Type.RESUME, paramActivity, false);
  }

  public void onSaveInstanceState(Activity paramActivity)
  {
    recordEventAsync(SessionEvent.Type.SAVE_INSTANCE_STATE, paramActivity, false);
  }

  public void onStart(Activity paramActivity)
  {
    recordEventAsync(SessionEvent.Type.START, paramActivity, false);
  }

  public void onStop(Activity paramActivity)
  {
    recordEventAsync(SessionEvent.Type.STOP, paramActivity, false);
  }

  void setAnalyticsSettingsData(final AnalyticsSettingsData paramAnalyticsSettingsData, final String paramString)
  {
    executeAsync(new Runnable()
    {
      public void run()
      {
        try
        {
          ((SessionAnalyticsManagerStrategy)SessionAnalyticsManager.this.strategy).setAnalyticsSettingsData(paramAnalyticsSettingsData, paramString);
          return;
        }
        catch (Exception localException)
        {
          CommonUtils.logControlledError(Answers.getInstance().getContext(), "Crashlytics failed to set analytics settings data.", localException);
        }
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.SessionAnalyticsManager
 * JD-Core Version:    0.6.2
 */