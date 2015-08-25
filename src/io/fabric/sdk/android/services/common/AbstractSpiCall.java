package io.fabric.sdk.android.services.common;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Collections;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractSpiCall
{
  public static final String ACCEPT_JSON_VALUE = "application/json";
  public static final String ANDROID_CLIENT_TYPE = "android";
  public static final String CLS_ANDROID_SDK_DEVELOPER_TOKEN = "bca6990fc3c15a8105800c0673517a4b579634a1";
  public static final String CRASHLYTICS_USER_AGENT = "Crashlytics Android SDK/";
  public static final int DEFAULT_TIMEOUT = 10000;
  public static final String HEADER_ACCEPT = "Accept";
  public static final String HEADER_API_KEY = "X-CRASHLYTICS-API-KEY";
  public static final String HEADER_CLIENT_TYPE = "X-CRASHLYTICS-API-CLIENT-TYPE";
  public static final String HEADER_CLIENT_VERSION = "X-CRASHLYTICS-API-CLIENT-VERSION";
  public static final String HEADER_D = "X-CRASHLYTICS-D";
  public static final String HEADER_DEVELOPER_TOKEN = "X-CRASHLYTICS-DEVELOPER-TOKEN";
  public static final String HEADER_DEVICE_STATE = "X-CRASHLYTICS-DEVICE-STATE";
  public static final String HEADER_REQUEST_ID = "X-REQUEST-ID";
  public static final String HEADER_USER_AGENT = "User-Agent";
  private static final Pattern PROTOCOL_AND_HOST_PATTERN = Pattern.compile("http(s?)://[^\\/]+", 2);
  protected final Kit kit;
  private final HttpMethod method;
  private final String protocolAndHostOverride;
  private final HttpRequestFactory requestFactory;
  private final String url;

  public AbstractSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    if (paramString2 == null)
      throw new IllegalArgumentException("url must not be null.");
    if (paramHttpRequestFactory == null)
      throw new IllegalArgumentException("requestFactory must not be null.");
    this.kit = paramKit;
    this.protocolAndHostOverride = paramString1;
    this.url = overrideProtocolAndHost(paramString2);
    this.requestFactory = paramHttpRequestFactory;
    this.method = paramHttpMethod;
  }

  private String overrideProtocolAndHost(String paramString)
  {
    String str = paramString;
    if (!CommonUtils.isNullOrEmpty(this.protocolAndHostOverride))
      str = PROTOCOL_AND_HOST_PATTERN.matcher(paramString).replaceFirst(this.protocolAndHostOverride);
    return str;
  }

  protected HttpRequest getHttpRequest()
  {
    return getHttpRequest(Collections.emptyMap());
  }

  protected HttpRequest getHttpRequest(Map<String, String> paramMap)
  {
    return this.requestFactory.buildHttpRequest(this.method, getUrl(), paramMap).useCaches(false).connectTimeout(10000).header("User-Agent", "Crashlytics Android SDK/" + this.kit.getVersion()).header("X-CRASHLYTICS-DEVELOPER-TOKEN", "bca6990fc3c15a8105800c0673517a4b579634a1");
  }

  protected String getUrl()
  {
    return this.url;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.AbstractSpiCall
 * JD-Core Version:    0.6.2
 */