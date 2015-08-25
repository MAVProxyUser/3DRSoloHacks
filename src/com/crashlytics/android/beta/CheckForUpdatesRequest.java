package com.crashlytics.android.beta;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CheckForUpdatesRequest extends AbstractSpiCall
{
  static final String BETA_SOURCE = "3";
  static final String BUILD_VERSION = "build_version";
  static final String DISPLAY_VERSION = "display_version";
  static final String INSTANCE = "instance";
  static final String SOURCE = "source";
  private final CheckForUpdatesResponseTransform responseTransform;

  public CheckForUpdatesRequest(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, CheckForUpdatesResponseTransform paramCheckForUpdatesResponseTransform)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, HttpMethod.GET);
    this.responseTransform = paramCheckForUpdatesResponseTransform;
  }

  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, String paramString1, String paramString2)
  {
    return paramHttpRequest.header("Accept", "application/json").header("User-Agent", "Crashlytics Android SDK/" + this.kit.getVersion()).header("X-CRASHLYTICS-DEVELOPER-TOKEN", "bca6990fc3c15a8105800c0673517a4b579634a1").header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).header("X-CRASHLYTICS-API-KEY", paramString1).header("X-CRASHLYTICS-D", paramString2);
  }

  private Map<String, String> getQueryParamsFor(BuildProperties paramBuildProperties)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("build_version", paramBuildProperties.versionCode);
    localHashMap.put("display_version", paramBuildProperties.versionName);
    localHashMap.put("instance", paramBuildProperties.buildId);
    localHashMap.put("source", "3");
    return localHashMap;
  }

  public CheckForUpdatesResponse invoke(String paramString1, String paramString2, BuildProperties paramBuildProperties)
  {
    HttpRequest localHttpRequest = null;
    try
    {
      Map localMap = getQueryParamsFor(paramBuildProperties);
      localHttpRequest = getHttpRequest(localMap);
      localHttpRequest = applyHeadersTo(localHttpRequest, paramString1, paramString2);
      Fabric.getLogger().d("Beta", "Checking for updates from " + getUrl());
      Fabric.getLogger().d("Beta", "Checking for updates query params are: " + localMap);
      if (localHttpRequest.ok())
      {
        Fabric.getLogger().d("Beta", "Checking for updates was successful");
        JSONObject localJSONObject = new JSONObject(localHttpRequest.body());
        CheckForUpdatesResponse localCheckForUpdatesResponse = this.responseTransform.fromJson(localJSONObject);
        String str4;
        return localCheckForUpdatesResponse;
      }
      Fabric.getLogger().e("Beta", "Checking for updates failed. Response code: " + localHttpRequest.code());
      String str3;
      return null;
    }
    catch (Exception localException)
    {
      while (true)
      {
        Fabric.getLogger().e("Beta", "Error while checking for updates from " + getUrl(), localException);
        if (localHttpRequest != null)
        {
          String str2 = localHttpRequest.header("X-REQUEST-ID");
          Fabric.getLogger().d("Fabric", "Checking for updates request ID: " + str2);
        }
      }
    }
    finally
    {
      if (localHttpRequest != null)
      {
        String str1 = localHttpRequest.header("X-REQUEST-ID");
        Fabric.getLogger().d("Fabric", "Checking for updates request ID: " + str1);
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.beta.CheckForUpdatesRequest
 * JD-Core Version:    0.6.2
 */