package io.fabric.sdk.android.services.settings;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.InputStream;
import java.util.Collection;
import java.util.Iterator;

abstract class AbstractAppSpiCall extends AbstractSpiCall
  implements AppSpiCall
{
  public static final String APP_BUILD_VERSION_PARAM = "app[build_version]";
  public static final String APP_BUILT_SDK_VERSION_PARAM = "app[built_sdk_version]";
  public static final String APP_DISPLAY_VERSION_PARAM = "app[display_version]";
  public static final String APP_ICON_DATA_PARAM = "app[icon][data]";
  public static final String APP_ICON_HASH_PARAM = "app[icon][hash]";
  public static final String APP_ICON_HEIGHT_PARAM = "app[icon][height]";
  public static final String APP_ICON_PRERENDERED_PARAM = "app[icon][prerendered]";
  public static final String APP_ICON_WIDTH_PARAM = "app[icon][width]";
  public static final String APP_IDENTIFIER_PARAM = "app[identifier]";
  public static final String APP_INSTANCE_IDENTIFIER_PARAM = "app[instance_identifier]";
  public static final String APP_MIN_SDK_VERSION_PARAM = "app[minimum_sdk_version]";
  public static final String APP_NAME_PARAM = "app[name]";
  public static final String APP_SDK_MODULES_PARAM_PREFIX = "app[build][libraries]";
  public static final String APP_SOURCE_PARAM = "app[source]";
  static final String ICON_CONTENT_TYPE = "application/octet-stream";
  static final String ICON_FILE_NAME = "icon.png";

  public AbstractAppSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
  }

  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, AppRequestData paramAppRequestData)
  {
    return paramHttpRequest.header("X-CRASHLYTICS-API-KEY", paramAppRequestData.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
  }

  private HttpRequest applyMultipartDataTo(HttpRequest paramHttpRequest, AppRequestData paramAppRequestData)
  {
    HttpRequest localHttpRequest = paramHttpRequest.part("app[identifier]", paramAppRequestData.appId).part("app[name]", paramAppRequestData.name).part("app[display_version]", paramAppRequestData.displayVersion).part("app[build_version]", paramAppRequestData.buildVersion).part("app[source]", Integer.valueOf(paramAppRequestData.source)).part("app[minimum_sdk_version]", paramAppRequestData.minSdkVersion).part("app[built_sdk_version]", paramAppRequestData.builtSdkVersion);
    if (!CommonUtils.isNullOrEmpty(paramAppRequestData.instanceIdentifier))
      localHttpRequest.part("app[instance_identifier]", paramAppRequestData.instanceIdentifier);
    InputStream localInputStream;
    if (paramAppRequestData.icon != null)
      localInputStream = null;
    while (true)
    {
      Kit localKit;
      try
      {
        localInputStream = this.kit.getContext().getResources().openRawResource(paramAppRequestData.icon.iconResourceId);
        localHttpRequest.part("app[icon][hash]", paramAppRequestData.icon.hash).part("app[icon][data]", "icon.png", "application/octet-stream", localInputStream).part("app[icon][width]", Integer.valueOf(paramAppRequestData.icon.width)).part("app[icon][height]", Integer.valueOf(paramAppRequestData.icon.height));
        CommonUtils.closeOrLog(localInputStream, "Failed to close app icon InputStream.");
        if (paramAppRequestData.sdkKits == null)
          break;
        Iterator localIterator = paramAppRequestData.sdkKits.iterator();
        if (!localIterator.hasNext())
          break;
        localKit = (Kit)localIterator.next();
        if (localKit.getVersion() == null)
        {
          str = "";
          localHttpRequest.part(sdkKitParamName(localKit), str);
          continue;
        }
      }
      catch (Resources.NotFoundException localNotFoundException)
      {
        Fabric.getLogger().e("Fabric", "Failed to find app icon with resource ID: " + paramAppRequestData.icon.iconResourceId, localNotFoundException);
        CommonUtils.closeOrLog(localInputStream, "Failed to close app icon InputStream.");
        continue;
      }
      finally
      {
        CommonUtils.closeOrLog(localInputStream, "Failed to close app icon InputStream.");
      }
      String str = localKit.getVersion();
    }
    return localHttpRequest;
  }

  public boolean invoke(AppRequestData paramAppRequestData)
  {
    HttpRequest localHttpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), paramAppRequestData), paramAppRequestData);
    Fabric.getLogger().d("Fabric", "Sending app info to " + getUrl());
    if (paramAppRequestData.icon != null)
    {
      Fabric.getLogger().d("Fabric", "App icon hash is " + paramAppRequestData.icon.hash);
      Fabric.getLogger().d("Fabric", "App icon size is " + paramAppRequestData.icon.width + "x" + paramAppRequestData.icon.height);
    }
    int i = localHttpRequest.code();
    if ("POST".equals(localHttpRequest.method()));
    for (String str = "Create"; ; str = "Update")
    {
      Fabric.getLogger().d("Fabric", str + " app request ID: " + localHttpRequest.header("X-REQUEST-ID"));
      Fabric.getLogger().d("Fabric", "Result was " + i);
      if (ResponseParser.parse(i) != 0)
        break;
      return true;
    }
    return false;
  }

  String sdkKitParamName(Kit paramKit)
  {
    if (paramKit.getIdentifier() == null);
    for (String str = ""; ; str = paramKit.getIdentifier())
      return "app[build][libraries][" + str + "]";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.settings.AbstractAppSpiCall
 * JD-Core Version:    0.6.2
 */