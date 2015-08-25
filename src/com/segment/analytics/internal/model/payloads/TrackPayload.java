package com.segment.analytics.internal.model.payloads;

import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.Options;
import com.segment.analytics.Properties;

public class TrackPayload extends BasePayload
{
  private static final String EVENT_KEY = "event";
  private static final String PROPERTIES_KEY = "properties";

  public TrackPayload(AnalyticsContext paramAnalyticsContext, Options paramOptions, String paramString, Properties paramProperties)
  {
    super(BasePayload.Type.track, paramAnalyticsContext, paramOptions);
    put("event", paramString);
    put("properties", paramProperties);
  }

  public String event()
  {
    return getString("event");
  }

  public Properties properties()
  {
    return (Properties)getValueMap("properties", Properties.class);
  }

  public String toString()
  {
    return "TrackPayload{\"" + event() + '}';
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.model.payloads.TrackPayload
 * JD-Core Version:    0.6.2
 */