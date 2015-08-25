package com.segment.analytics;

import android.util.Base64;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionFactory
{
  private static final int DEFAULT_CONNECT_TIMEOUT_MILLIS = 15000;
  private static final int DEFAULT_READ_TIMEOUT_MILLIS = 20000;

  private String authorizationHeader(String paramString)
  {
    return "Basic " + Base64.encodeToString(new StringBuilder().append(paramString).append(":").toString().getBytes(), 2);
  }

  protected HttpURLConnection openConnection(String paramString)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
    localHttpURLConnection.setConnectTimeout(15000);
    localHttpURLConnection.setReadTimeout(20000);
    localHttpURLConnection.setDoInput(true);
    return localHttpURLConnection;
  }

  public HttpURLConnection projectSettings(String paramString)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = openConnection("https://cdn.segment.com/v1/projects/" + paramString + "/settings");
    localHttpURLConnection.setRequestProperty("Content-Type", "application/json");
    return localHttpURLConnection;
  }

  public HttpURLConnection upload(String paramString)
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = openConnection("https://api.segment.io/v1/import");
    localHttpURLConnection.setRequestProperty("Content-Type", "application/json");
    localHttpURLConnection.setRequestProperty("Authorization", authorizationHeader(paramString));
    localHttpURLConnection.setDoOutput(true);
    localHttpURLConnection.setChunkedStreamingMode(0);
    return localHttpURLConnection;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.ConnectionFactory
 * JD-Core Version:    0.6.2
 */