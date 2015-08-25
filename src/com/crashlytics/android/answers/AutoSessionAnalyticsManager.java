package com.crashlytics.android.answers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.concurrent.ScheduledExecutorService;

@TargetApi(14)
class AutoSessionAnalyticsManager extends SessionAnalyticsManager
{
  private final Application.ActivityLifecycleCallbacks activityLifecycleCallbacks = new Application.ActivityLifecycleCallbacks()
  {
    public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
    {
      AutoSessionAnalyticsManager.this.onCreate(paramAnonymousActivity);
    }

    public void onActivityDestroyed(Activity paramAnonymousActivity)
    {
      AutoSessionAnalyticsManager.this.onDestroy(paramAnonymousActivity);
    }

    public void onActivityPaused(Activity paramAnonymousActivity)
    {
      AutoSessionAnalyticsManager.this.onPause(paramAnonymousActivity);
    }

    public void onActivityResumed(Activity paramAnonymousActivity)
    {
      AutoSessionAnalyticsManager.this.onResume(paramAnonymousActivity);
    }

    public void onActivitySaveInstanceState(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
    {
      AutoSessionAnalyticsManager.this.onSaveInstanceState(paramAnonymousActivity);
    }

    public void onActivityStarted(Activity paramAnonymousActivity)
    {
      AutoSessionAnalyticsManager.this.onStart(paramAnonymousActivity);
    }

    public void onActivityStopped(Activity paramAnonymousActivity)
    {
      AutoSessionAnalyticsManager.this.onStop(paramAnonymousActivity);
    }
  };
  private final Application application;

  public AutoSessionAnalyticsManager(Application paramApplication, SessionEventMetadata paramSessionEventMetadata, SessionAnalyticsFilesManager paramSessionAnalyticsFilesManager, HttpRequestFactory paramHttpRequestFactory)
  {
    this(paramApplication, paramSessionEventMetadata, paramSessionAnalyticsFilesManager, ExecutorUtils.buildSingleThreadScheduledExecutorService("Crashlytics Trace Manager"), paramHttpRequestFactory);
  }

  AutoSessionAnalyticsManager(Application paramApplication, SessionEventMetadata paramSessionEventMetadata, SessionAnalyticsFilesManager paramSessionAnalyticsFilesManager, ScheduledExecutorService paramScheduledExecutorService, HttpRequestFactory paramHttpRequestFactory)
  {
    super(paramApplication, paramSessionEventMetadata, paramSessionAnalyticsFilesManager, paramScheduledExecutorService, paramHttpRequestFactory);
    this.application = paramApplication;
    CommonUtils.logControlled(Answers.getInstance().getContext(), "Registering activity lifecycle callbacks for session analytics.");
    paramApplication.registerActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
  }

  public void disable()
  {
    CommonUtils.logControlled(Answers.getInstance().getContext(), "Unregistering activity lifecycle callbacks for session analytics");
    this.application.unregisterActivityLifecycleCallbacks(this.activityLifecycleCallbacks);
    super.disable();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.AutoSessionAnalyticsManager
 * JD-Core Version:    0.6.2
 */