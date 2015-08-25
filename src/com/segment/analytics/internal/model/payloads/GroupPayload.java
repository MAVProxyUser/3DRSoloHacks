package com.segment.analytics.internal.model.payloads;

import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.Options;
import com.segment.analytics.Traits;

public class GroupPayload extends BasePayload
{
  private static final String GROUP_ID_KEY = "groupId";
  private static final String TRAITS_KEY = "traits";

  public GroupPayload(AnalyticsContext paramAnalyticsContext, Options paramOptions, String paramString, Traits paramTraits)
  {
    super(BasePayload.Type.group, paramAnalyticsContext, paramOptions);
    put("groupId", paramString);
    put("traits", paramTraits.unmodifiableCopy());
  }

  public String groupId()
  {
    return getString("groupId");
  }

  public String toString()
  {
    return "GroupPayload{\"groupId=\"" + groupId() + '}';
  }

  public Traits traits()
  {
    return (Traits)getValueMap("traits", Traits.class);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.model.payloads.GroupPayload
 * JD-Core Version:    0.6.2
 */