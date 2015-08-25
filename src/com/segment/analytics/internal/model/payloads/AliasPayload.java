package com.segment.analytics.internal.model.payloads;

import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.Options;
import com.segment.analytics.Traits;

public class AliasPayload extends BasePayload
{
  private static final String PREVIOUS_ID_KEY = "previousId";

  public AliasPayload(AnalyticsContext paramAnalyticsContext, Options paramOptions, String paramString)
  {
    super(BasePayload.Type.alias, paramAnalyticsContext, paramOptions);
    put("userId", paramString);
    put("previousId", context().traits().currentId());
  }

  public String previousId()
  {
    return getString("previousId");
  }

  public String toString()
  {
    return "AliasPayload{userId=\"" + userId() + ",previousId=\"" + previousId() + "\"}";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.model.payloads.AliasPayload
 * JD-Core Version:    0.6.2
 */