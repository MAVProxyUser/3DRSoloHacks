package com.crashlytics.android.answers;

import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.events.FilesSender;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.io.File;
import java.util.Iterator;
import java.util.List;

class DefaultSessionAnalyticsFilesSender extends AbstractSpiCall
  implements FilesSender
{
  static final String FILE_CONTENT_TYPE = "application/vnd.crashlytics.android.events";
  static final String FILE_PARAM_NAME = "session_analytics_file_";
  private final String apiKey;

  public DefaultSessionAnalyticsFilesSender(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, String paramString3)
  {
    this(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramString3, HttpMethod.POST);
  }

  DefaultSessionAnalyticsFilesSender(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, String paramString3, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
    this.apiKey = paramString3;
  }

  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, String paramString)
  {
    return paramHttpRequest.header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", Answers.getInstance().getVersion()).header("X-CRASHLYTICS-API-KEY", paramString);
  }

  private HttpRequest applyMultipartDataTo(HttpRequest paramHttpRequest, List<File> paramList)
  {
    int i = 0;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      File localFile = (File)localIterator.next();
      CommonUtils.logControlled(Answers.getInstance().getContext(), "Adding analytics session file " + localFile.getName() + " to multipart POST");
      paramHttpRequest.part("session_analytics_file_" + i, localFile.getName(), "application/vnd.crashlytics.android.events", localFile);
      i++;
    }
    return paramHttpRequest;
  }

  public boolean send(List<File> paramList)
  {
    HttpRequest localHttpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), this.apiKey), paramList);
    CommonUtils.logControlled(Answers.getInstance().getContext(), "Sending " + paramList.size() + " analytics files to " + getUrl());
    int i = localHttpRequest.code();
    CommonUtils.logControlled(Answers.getInstance().getContext(), "Response code for analytics file send is " + i);
    return ResponseParser.parse(i) == 0;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.answers.DefaultSessionAnalyticsFilesSender
 * JD-Core Version:    0.6.2
 */