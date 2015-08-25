package com.segment.analytics.internal.model.payloads;

import com.segment.analytics.AnalyticsContext;
import com.segment.analytics.Options;
import com.segment.analytics.Traits;
import com.segment.analytics.ValueMap;
import com.segment.analytics.internal.Utils;
import java.util.Date;
import java.util.UUID;

public abstract class BasePayload extends ValueMap
{
  private static final String ANONYMOUS_ID_KEY = "anonymousId";
  private static final String CHANNEL_KEY = "channel";
  private static final String CONTEXT_KEY = "context";
  private static final String INTEGRATIONS_KEY = "integrations";
  private static final String MESSAGE_ID = "messageId";
  private static final String TIMESTAMP_KEY = "timestamp";
  private static final String TYPE_KEY = "type";
  protected static final String USER_ID_KEY = "userId";

  public BasePayload(Type paramType, AnalyticsContext paramAnalyticsContext, Options paramOptions)
  {
    AnalyticsContext localAnalyticsContext = paramAnalyticsContext.unmodifiableCopy();
    put("messageId", UUID.randomUUID().toString());
    put("type", paramType);
    put("channel", Channel.mobile);
    put("context", localAnalyticsContext);
    put("anonymousId", localAnalyticsContext.traits().anonymousId());
    String str = localAnalyticsContext.traits().userId();
    if (!Utils.isNullOrEmpty(str))
      put("userId", str);
    put("timestamp", Utils.toISO8601Date(new Date()));
    put("integrations", paramOptions.integrations());
  }

  public String anonymousId()
  {
    return getString("anonymousId");
  }

  public AnalyticsContext context()
  {
    return (AnalyticsContext)getValueMap("context", AnalyticsContext.class);
  }

  public ValueMap integrations()
  {
    return getValueMap("integrations");
  }

  public String messageId()
  {
    return getString("messageId");
  }

  public BasePayload putValue(String paramString, Object paramObject)
  {
    super.putValue(paramString, paramObject);
    return this;
  }

  public Type type()
  {
    return (Type)getEnum(Type.class, "type");
  }

  public String userId()
  {
    return getString("userId");
  }

  public static enum Channel
  {
    static
    {
      Channel[] arrayOfChannel = new Channel[3];
      arrayOfChannel[0] = browser;
      arrayOfChannel[1] = mobile;
      arrayOfChannel[2] = server;
    }
  }

  public static enum Type
  {
    static
    {
      Type[] arrayOfType = new Type[5];
      arrayOfType[0] = alias;
      arrayOfType[1] = group;
      arrayOfType[2] = identify;
      arrayOfType[3] = screen;
      arrayOfType[4] = track;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.model.payloads.BasePayload
 * JD-Core Version:    0.6.2
 */