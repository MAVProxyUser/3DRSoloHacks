package com.crashlytics.android;

import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.AbstractSpiCall;
import io.fabric.sdk.android.services.common.ResponseParser;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class DefaultCreateReportSpiCall extends AbstractSpiCall
  implements CreateReportSpiCall
{
  static final String FILE_CONTENT_TYPE = "application/octet-stream";
  static final String FILE_PARAM = "report[file]";
  static final String IDENTIFIER_PARAM = "report[identifier]";

  public DefaultCreateReportSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, HttpMethod.POST);
  }

  DefaultCreateReportSpiCall(Kit paramKit, String paramString1, String paramString2, HttpRequestFactory paramHttpRequestFactory, HttpMethod paramHttpMethod)
  {
    super(paramKit, paramString1, paramString2, paramHttpRequestFactory, paramHttpMethod);
  }

  private HttpRequest applyHeadersTo(HttpRequest paramHttpRequest, CreateReportRequest paramCreateReportRequest)
  {
    HttpRequest localHttpRequest = paramHttpRequest.header("X-CRASHLYTICS-API-KEY", paramCreateReportRequest.apiKey).header("X-CRASHLYTICS-API-CLIENT-TYPE", "android").header("X-CRASHLYTICS-API-CLIENT-VERSION", Crashlytics.getInstance().getVersion());
    Iterator localIterator = paramCreateReportRequest.report.getCustomHeaders().entrySet().iterator();
    while (localIterator.hasNext())
      localHttpRequest = localHttpRequest.header((Map.Entry)localIterator.next());
    return localHttpRequest;
  }

  private HttpRequest applyMultipartDataTo(HttpRequest paramHttpRequest, CreateReportRequest paramCreateReportRequest)
  {
    Report localReport = paramCreateReportRequest.report;
    return paramHttpRequest.part("report[file]", localReport.getFileName(), "application/octet-stream", localReport.getFile()).part("report[identifier]", localReport.getIdentifier());
  }

  public boolean invoke(CreateReportRequest paramCreateReportRequest)
  {
    HttpRequest localHttpRequest = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), paramCreateReportRequest), paramCreateReportRequest);
    Fabric.getLogger().d("Fabric", "Sending report to: " + getUrl());
    int i = localHttpRequest.code();
    Fabric.getLogger().d("Fabric", "Create report request ID: " + localHttpRequest.header("X-REQUEST-ID"));
    Fabric.getLogger().d("Fabric", "Result was: " + i);
    return ResponseParser.parse(i) == 0;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.DefaultCreateReportSpiCall
 * JD-Core Version:    0.6.2
 */