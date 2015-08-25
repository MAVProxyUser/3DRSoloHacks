package com.segment.analytics.internal.model.payloads;

import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.Options;
import com.segment.analytics.Traits;

public class IdentifyPayload extends BasePayload
{
  private static final String TRAITS_KEY = "traits";

  public IdentifyPayload(AnalyticsContext paramAnalyticsContext, Options paramOptions, Traits paramTraits)
  {
    super(BasePayload.Type.identify, paramAnalyticsContext, paramOptions);
    put("traits", paramTraits);
  }

  public String toString()
  {
    return "IdentifyPayload{\"userId=\"" + userId() + '}';
  }

  public Traits traits()
  {
    return (Traits)getValueMap("traits", Traits.class);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.model.payloads.IdentifyPayload
 * JD-Core Version:    0.6.2
 */