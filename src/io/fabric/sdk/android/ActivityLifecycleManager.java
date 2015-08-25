package io.fabric.sdk.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ActivityLifecycleManager
{
  private final Application application;
  private ActivityLifecycleCallbacksWrapper callbacksWrapper;

  public ActivityLifecycleManager(Context paramContext)
  {
    this.application = ((Application)paramContext.getApplicationContext());
    if (Build.VERSION.SDK_INT >= 14)
      this.callbacksWrapper = new ActivityLifecycleCallbacksWrapper(this.application);
  }

  public boolean registerCallbacks(Callbacks paramCallbacks)
  {
    return (this.callbacksWrapper != null) && (this.callbacksWrapper.registerLifecycleCallbacks(paramCallbacks));
  }

  public void resetCallbacks()
  {
    if (this.callbacksWrapper != null)
      this.callbacksWrapper.clearCallbacks();
  }

  private static class ActivityLifecycleCallbacksWrapper
  {
    private final Application application;
    private final Set<Application.ActivityLifecycleCallbacks> registeredCallbacks = new HashSet();

    ActivityLifecycleCallbacksWrapper(Application paramApplication)
    {
      this.application = paramApplication;
    }

    @TargetApi(14)
    private void clearCallbacks()
    {
      Iterator localIterator = this.registeredCallbacks.iterator();
      while (localIterator.hasNext())
      {
        Application.ActivityLifecycleCallbacks localActivityLifecycleCallbacks = (Application.ActivityLifecycleCallbacks)localIterator.next();
        this.application.unregisterActivityLifecycleCallbacks(localActivityLifecycleCallbacks);
      }
    }

    @TargetApi(14)
    private boolean registerLifecycleCallbacks(final ActivityLifecycleManager.Callbacks paramCallbacks)
    {
      if (this.application != null)
      {
        Application.ActivityLifecycleCallbacks local1 = new Application.ActivityLifecycleCallbacks()
        {
          public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
          {
            paramCallbacks.onActivityCreated(paramAnonymousActivity, paramAnonymousBundle);
          }

          public void onActivityDestroyed(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityDestroyed(paramAnonymousActivity);
          }

          public void onActivityPaused(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityPaused(paramAnonymousActivity);
          }

          public void onActivityResumed(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityResumed(paramAnonymousActivity);
          }

          public void onActivitySaveInstanceState(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
          {
            paramCallbacks.onActivitySaveInstanceState(paramAnonymousActivity, paramAnonymousBundle);
          }

          public void onActivityStarted(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityStarted(paramAnonymousActivity);
          }

          public void onActivityStopped(Activity paramAnonymousActivity)
          {
            paramCallbacks.onActivityStopped(paramAnonymousActivity);
          }
        };
        this.application.registerActivityLifecycleCallbacks(local1);
        this.registeredCallbacks.add(local1);
        return true;
      }
      return false;
    }
  }

  public static abstract class Callbacks
  {
    public void onActivityCreated(Activity paramActivity, Bundle paramBundle)
    {
    }

    public void onActivityDestroyed(Activity paramActivity)
    {
    }

    public void onActivityPaused(Activity paramActivity)
    {
    }

    public void onActivityResumed(Activity paramActivity)
    {
    }

    public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle)
    {
    }

    public void onActivityStarted(Activity paramActivity)
    {
    }

    public void onActivityStopped(Activity paramActivity)
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.ActivityLifecycleManager
 * JD-Core Version:    0.6.2
 */