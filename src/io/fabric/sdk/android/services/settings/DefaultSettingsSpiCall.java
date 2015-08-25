package io.fabric.sdk.android.services.settings;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class DefaultSettingsSpiCall extends AbstractSpiCall
  implements SettingsSpiCall
{
  static final String BUILD_VERSION_PARAM = "build_version";
  static final String DISPLAY_VERSION_PARAM = "display_version";
  static final String ICON_HASH = "icon_hash";
  static final String INSTANCE_PARAM = "instance";
  static final String SOURCE_PARAM = "source";

  public DefaultSettingsSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory)
  {
    this(paramKit, paramString1, paramString2, paramHttpRequestFactory, HttpMethod.GET);
  }

  DefaultSettingsSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
  }

  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, SettingsRequest paramSettingsRequest)
  {
    return paramHttpRequest.header("X-CRASHLYTICS-API-KEY", paramSettingsRequest.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-D", paramSettingsRequest.deviceId).header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("Accept", "application/json");
  }

  private Map<String, String> getQueryParamsFor(SettingsRequest paramSettingsRequest)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("build_version", paramSettingsRequest.buildVersion);
    localHashMap.put("display_version", paramSettingsRequest.displayVersion);
    localHashMap.put("source", Integer.toString(paramSettingsRequest.source));
    if (paramSettingsRequest.iconHash != null)
      localHashMap.put("icon_hash", paramSettingsRequest.iconHash);
    String str = paramSettingsRequest.instanceId;
    if (!CommonUtils.isNullOrEmpty(str))
      localHashMap.put("instance", str);
    return localHashMap;
  }

  public JSONObject invoke(SettingsRequest paramSettingsRequest)
  {
    HttpRequest localHttpRequest = null;
    String str = null;
    JSONObject localJSONObject2;
    try
    {
      Map localMap = getQueryParamsFor(paramSettingsRequest);
      localHttpRequest = getHttpRequest(localMap);
      localHttpRequest = applyHeadersTo(localHttpRequest, paramSettingsRequest);
      Fabric.getLogger().d("Fabric", "Requesting settings from " + getUrl());
      Fabric.getLogger().d("Fabric", "Settings query params were: " + localMap);
      int i = localHttpRequest.code();
      Fabric.getLogger().d("Fabric", "Settings result was: " + i);
      str = localHttpRequest.body();
      localJSONObject2 = new JSONObject(str);
      if (localHttpRequest != null)
      {
        Fabric.getLogger().d("Fabric", "Settings request ID: " + localHttpRequest.header("X-REQUEST-ID"));
        localJSONObject1 = localJSONObject2;
        return localJSONObject1;
      }
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Failed to retrieve settings from " + getUrl(), localException);
      Fabric.getLogger().d("Fabric", "Settings response " + str);
      JSONObject localJSONObject1 = null;
      return null;
    }
    finally
    {
      if (localHttpRequest != null)
        Fabric.getLogger().d("Fabric", "Settings request ID: " + localHttpRequest.header("X-REQUEST-ID"));
    }
    return localJSONObject2;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.DefaultSettingsSpiCall
 * JD-Core Version:    0.6.2
 */