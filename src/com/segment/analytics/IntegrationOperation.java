package com.segment.analytics;

import android.app.Activity;
import android.os.Bundle;
import com.segment.analytics.internal.AbstractIntegration;
import com.segment.analytics.internal.Utils;
import com.segment.analytics.internal.model.payloads.AliasPayload;
import com.segment.analytics.internal.model.payloads.GroupPayload;
import com.segment.analytics.internal.model.payloads.IdentifyPayload;
import com.segment.analytics.internal.model.payloads.ScreenPayload;
import com.segment.analytics.internal.model.payloads.TrackPayload;

abstract class IntegrationOperation
{
  static IntegrationOperation alias(AliasPayload paramAliasPayload)
  {
    return new IntegrationOperation(paramAliasPayload)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.alias(this.val$aliasPayload);
      }

      public String toString()
      {
        return this.val$aliasPayload.toString();
      }
    };
  }

  static IntegrationOperation flush()
  {
    // Byte code:
    //   0: new 21	com/segment/analytics/IntegrationOperation$13
    //   3: dup
    //   4: invokespecial 22	com/segment/analytics/IntegrationOperation$13:<init>	()V
    //   7: areturn
  }

  static IntegrationOperation group(GroupPayload paramGroupPayload)
  {
    return new IntegrationOperation(paramGroupPayload)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.group(this.val$groupPayload);
      }

      public String toString()
      {
        return this.val$groupPayload.toString();
      }
    };
  }

  static IntegrationOperation identify(IdentifyPayload paramIdentifyPayload)
  {
    return new IntegrationOperation(paramIdentifyPayload)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.identify(this.val$identifyPayload);
      }

      public String toString()
      {
        return this.val$identifyPayload.toString();
      }
    };
  }

  static boolean isIntegrationEnabled(ValueMap paramValueMap, AbstractIntegration paramAbstractIntegration)
  {
    boolean bool;
    if (Utils.isNullOrEmpty(paramValueMap))
      bool = true;
    do
    {
      return bool;
      if ("Segment.io".equals(paramAbstractIntegration.key()))
        return true;
      bool = true;
      String str = paramAbstractIntegration.key();
      if (paramValueMap.containsKey(str))
        return paramValueMap.getBoolean(str, true);
    }
    while (!paramValueMap.containsKey("All"));
    return paramValueMap.getBoolean("All", true);
  }

  static boolean isIntegrationEnabledInPlan(ValueMap paramValueMap, AbstractIntegration paramAbstractIntegration)
  {
    boolean bool = paramValueMap.getBoolean("enabled", true);
    if (bool)
      bool = isIntegrationEnabled(paramValueMap.getValueMap("integrations"), paramAbstractIntegration);
    return bool;
  }

  static IntegrationOperation onActivityCreated(Activity paramActivity, final Bundle paramBundle)
  {
    return new IntegrationOperation(paramActivity)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.onActivityCreated(this.val$activity, paramBundle);
      }

      public String toString()
      {
        return "Activity Created";
      }
    };
  }

  static IntegrationOperation onActivityDestroyed(Activity paramActivity)
  {
    return new IntegrationOperation(paramActivity)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.onActivityDestroyed(this.val$activity);
      }

      public String toString()
      {
        return "Activity Destroyed";
      }
    };
  }

  static IntegrationOperation onActivityPaused(Activity paramActivity)
  {
    return new IntegrationOperation(paramActivity)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.onActivityPaused(this.val$activity);
      }

      public String toString()
      {
        return "Activity Paused";
      }
    };
  }

  static IntegrationOperation onActivityResumed(Activity paramActivity)
  {
    return new IntegrationOperation(paramActivity)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.onActivityResumed(this.val$activity);
      }

      public String toString()
      {
        return "Activity Resumed";
      }
    };
  }

  static IntegrationOperation onActivitySaveInstanceState(Activity paramActivity, final Bundle paramBundle)
  {
    return new IntegrationOperation(paramActivity)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.onActivitySaveInstanceState(this.val$activity, paramBundle);
      }

      public String toString()
      {
        return "Activity Save Instance";
      }
    };
  }

  static IntegrationOperation onActivityStarted(Activity paramActivity)
  {
    return new IntegrationOperation(paramActivity)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.onActivityStarted(this.val$activity);
      }

      public String toString()
      {
        return "Activity Started";
      }
    };
  }

  static IntegrationOperation onActivityStopped(Activity paramActivity)
  {
    return new IntegrationOperation(paramActivity)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.onActivityStopped(this.val$activity);
      }

      public String toString()
      {
        return "Activity Stopped";
      }
    };
  }

  static IntegrationOperation reset()
  {
    // Byte code:
    //   0: new 117	com/segment/analytics/IntegrationOperation$14
    //   3: dup
    //   4: invokespecial 118	com/segment/analytics/IntegrationOperation$14:<init>	()V
    //   7: areturn
  }

  static IntegrationOperation screen(ScreenPayload paramScreenPayload)
  {
    return new IntegrationOperation(paramScreenPayload)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        paramAnonymousAbstractIntegration.screen(this.val$screenPayload);
      }

      public String toString()
      {
        return this.val$screenPayload.toString();
      }
    };
  }

  static IntegrationOperation track(TrackPayload paramTrackPayload)
  {
    return new IntegrationOperation(paramTrackPayload)
    {
      public void run(AbstractIntegration paramAnonymousAbstractIntegration, ProjectSettings paramAnonymousProjectSettings)
      {
        ValueMap localValueMap = paramAnonymousProjectSettings.trackingPlan();
        boolean bool = true;
        if (!Utils.isNullOrEmpty(localValueMap))
        {
          String str = this.val$trackPayload.event();
          if (localValueMap.containsKey(str))
            bool = isIntegrationEnabledInPlan(localValueMap.getValueMap(str), paramAnonymousAbstractIntegration);
        }
        if (bool)
          paramAnonymousAbstractIntegration.track(this.val$trackPayload);
      }

      public String toString()
      {
        return this.val$trackPayload.toString();
      }
    };
  }

  abstract void run(AbstractIntegration paramAbstractIntegration, ProjectSettings paramProjectSettings);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.IntegrationOperation
 * JD-Core Version:    0.6.2
 */