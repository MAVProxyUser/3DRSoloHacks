package com.segment.analytics;

import android.content.Context;
import java.util.Collections;
import java.util.Map;

class ProjectSettings extends ValueMap
{
  private static final String INTEGRATIONS_KEY = "integrations";
  private static final String PLAN_KEY = "plan";
  private static final String TIMESTAMP_KEY = "timestamp";
  private static final String TRACKING_PLAN_KEY = "track";

  private ProjectSettings(Map<String, Object> paramMap)
  {
    super(Collections.unmodifiableMap(paramMap));
  }

  static ProjectSettings create(Map<String, Object> paramMap)
  {
    paramMap.put("timestamp", Long.valueOf(System.currentTimeMillis()));
    return new ProjectSettings(paramMap);
  }

  ValueMap integrations()
  {
    return getValueMap("integrations");
  }

  ValueMap plan()
  {
    return getValueMap("plan");
  }

  long timestamp()
  {
    return getLong("timestamp", 0L);
  }

  ValueMap trackingPlan()
  {
    ValueMap localValueMap = plan();
    if (localValueMap == null)
      return null;
    return localValueMap.getValueMap("track");
  }

  static class Cache extends ValueMap.Cache<ProjectSettings>
  {
    private static final String PROJECT_SETTINGS_CACHE_KEY_PREFIX = "project-settings-plan-";

    Cache(Context paramContext, Cartographer paramCartographer, String paramString)
    {
      super(paramCartographer, "project-settings-plan-" + paramString, ProjectSettings.class);
    }

    public ProjectSettings create(Map<String, Object> paramMap)
    {
      return new ProjectSettings(paramMap, null);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.ProjectSettings
 * JD-Core Version:    0.6.2
 */