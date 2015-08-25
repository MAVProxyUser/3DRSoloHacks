package com.segment.analytics.internal.model.payloads;

import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.Options;
import com.segment.analytics.Properties;
import com.segment.analytics.internal.Utils;

public class ScreenPayload extends BasePayload
{
  private static final String CATEGORY_KEY = "category";
  private static final String NAME_KEY = "name";
  private static final String PROPERTIES_KEY = "properties";
  private String event;

  public ScreenPayload(AnalyticsContext paramAnalyticsContext, Options paramOptions, String paramString1, String paramString2, Properties paramProperties)
  {
    super(BasePayload.Type.screen, paramAnalyticsContext, paramOptions);
    put("category", paramString1);
    put("name", paramString2);
    put("properties", paramProperties);
  }

  public String category()
  {
    return getString("category");
  }

  public String event()
  {
    if (Utils.isNullOrEmpty(this.event))
      if (!Utils.isNullOrEmpty(name()))
        break label35;
    label35: for (String str = category(); ; str = name())
    {
      this.event = str;
      return this.event;
    }
  }

  public String name()
  {
    return getString("name");
  }

  public Properties properties()
  {
    return (Properties)get("properties");
  }

  public String toString()
  {
    return "ScreenPayload{\"" + event() + '}';
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.model.payloads.ScreenPayload
 * JD-Core Version:    0.6.2
 */