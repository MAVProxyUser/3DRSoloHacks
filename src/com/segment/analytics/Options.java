package com.segment.analytics;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Options
{
  public static final String ALL_INTEGRATIONS_KEY = "All";
  private final Map<String, Object> integrations = new ConcurrentHashMap();

  public Options()
  {
    this.integrations.put("All", Boolean.valueOf(true));
  }

  public Map<String, Object> integrations()
  {
    return new LinkedHashMap(this.integrations);
  }

  public Options setIntegration(Analytics.BundledIntegration paramBundledIntegration, boolean paramBoolean)
  {
    setIntegration(paramBundledIntegration.key, paramBoolean);
    return this;
  }

  public Options setIntegration(String paramString, boolean paramBoolean)
  {
    if ("Segment.io".equals(paramString))
      throw new IllegalArgumentException("Segment integration cannot be enabled or disabled.");
    this.integrations.put(paramString, Boolean.valueOf(paramBoolean));
    return this;
  }

  public Options setIntegrationOptions(Analytics.BundledIntegration paramBundledIntegration, Map<String, Object> paramMap)
  {
    this.integrations.put(paramBundledIntegration.key, paramMap);
    return this;
  }

  public Options setIntegrationOptions(String paramString, Map<String, Object> paramMap)
  {
    this.integrations.put(paramString, paramMap);
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.Options
 * JD-Core Version:    0.6.2
 */