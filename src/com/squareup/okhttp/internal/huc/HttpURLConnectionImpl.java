package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Handshake;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpDate;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.RetryableSink;
import com.squareup.okhttp.internal.http.StatusLine;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.SocketPermission;
import java.net.URL;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.BufferedSink;
import okio.Sink;

public class HttpURLConnectionImpl extends HttpURLConnection
{
  private static final Set<String> METHODS = new LinkedHashSet(Arrays.asList(new String[] { "OPTIONS", "GET", "HEAD", "POST", "PUT", "DELETE", "TRACE", "PATCH" }));
  final OkHttpClient client;
  private long fixedContentLength = -1L;
  private int followUpCount;
  Handshake handshake;
  protected HttpEngine httpEngine;
  protected IOException httpEngineFailure;
  private Headers.Builder requestHeaders = new Headers.Builder();
  private Headers responseHeaders;
  private Route route;

  public HttpURLConnectionImpl(URL paramURL, OkHttpClient paramOkHttpClient)
  {
    super(paramURL);
    this.client = paramOkHttpClient;
  }

  private String defaultUserAgent()
  {
    String str = System.getProperty("http.agent");
    if (str != null)
      return str;
    return "Java" + System.getProperty("java.version");
  }

  private boolean execute(boolean paramBoolean)
    throws IOException
  {
    try
    {
      this.httpEngine.sendRequest();
      this.route = this.httpEngine.getRoute();
      if (this.httpEngine.getConnection() != null);
      for (Handshake localHandshake = this.httpEngine.getConnection().getHandshake(); ; localHandshake = null)
      {
        this.handshake = localHandshake;
        if (paramBoolean)
          this.httpEngine.readResponse();
        return true;
      }
    }
    catch (IOException localIOException)
    {
      HttpEngine localHttpEngine = this.httpEngine.recover(localIOException);
      if (localHttpEngine != null)
      {
        this.httpEngine = localHttpEngine;
        return false;
      }
      this.httpEngineFailure = localIOException;
      throw localIOException;
    }
  }

  private Headers getHeaders()
    throws IOException
  {
    if (this.responseHeaders == null)
    {
      Response localResponse = getResponse().getResponse();
      this.responseHeaders = localResponse.headers().newBuilder().add(Platform.get().getPrefix() + "-Response-Source", responseSourceHeader(localResponse)).build();
    }
    return this.responseHeaders;
  }

  private HttpEngine getResponse()
    throws IOException
  {
    initHttpEngine();
    if (this.httpEngine.hasResponse())
      return this.httpEngine;
    while (true)
      if (execute(true))
      {
        Response localResponse = this.httpEngine.getResponse();
        Request localRequest = this.httpEngine.followUpRequest();
        if (localRequest == null)
        {
          this.httpEngine.releaseConnection();
          return this.httpEngine;
        }
        int i = 1 + this.followUpCount;
        this.followUpCount = i;
        if (i > 20)
          throw new ProtocolException("Too many follow-up requests: " + this.followUpCount);
        this.url = localRequest.url();
        this.requestHeaders = localRequest.headers().newBuilder();
        Sink localSink = this.httpEngine.getRequestBody();
        if (!localRequest.method().equals(this.method))
          localSink = null;
        if ((localSink != null) && (!(localSink instanceof RetryableSink)))
          throw new HttpRetryException("Cannot retry streamed HTTP body", this.responseCode);
        if (!this.httpEngine.sameConnection(localRequest.url()))
          this.httpEngine.releaseConnection();
        Connection localConnection = this.httpEngine.close();
        this.httpEngine = newHttpEngine(localRequest.method(), localConnection, (RetryableSink)localSink, localResponse);
      }
  }

  private void initHttpEngine()
    throws IOException
  {
    if (this.httpEngineFailure != null)
      throw this.httpEngineFailure;
    if (this.httpEngine != null)
      return;
    this.connected = true;
    do
      try
      {
        if (this.doOutput)
        {
          if (this.method.equals("GET"))
            this.method = "POST";
        }
        else
        {
          this.httpEngine = newHttpEngine(this.method, null, null, null);
          return;
        }
      }
      catch (IOException localIOException)
      {
        this.httpEngineFailure = localIOException;
        throw localIOException;
      }
    while (HttpMethod.permitsRequestBody(this.method));
    throw new ProtocolException(this.method + " does not support writing");
  }

  private HttpEngine newHttpEngine(String paramString, Connection paramConnection, RetryableSink paramRetryableSink, Response paramResponse)
  {
    Request.Builder localBuilder = new Request.Builder().url(getURL()).method(paramString, null);
    Headers localHeaders = this.requestHeaders.build();
    int i = 0;
    int j = localHeaders.size();
    while (i < j)
    {
      localBuilder.addHeader(localHeaders.name(i), localHeaders.value(i));
      i++;
    }
    boolean bool1 = HttpMethod.permitsRequestBody(paramString);
    boolean bool2 = false;
    if (bool1)
    {
      if (this.fixedContentLength == -1L)
        break label226;
      localBuilder.header("Content-Length", Long.toString(this.fixedContentLength));
    }
    while (true)
    {
      if (localHeaders.get("Content-Type") == null)
        localBuilder.header("Content-Type", "application/x-www-form-urlencoded");
      if (localHeaders.get("User-Agent") == null)
        localBuilder.header("User-Agent", defaultUserAgent());
      Request localRequest = localBuilder.build();
      OkHttpClient localOkHttpClient = this.client;
      if ((Internal.instance.internalCache(localOkHttpClient) != null) && (!getUseCaches()))
        localOkHttpClient = this.client.clone().setCache(null);
      return new HttpEngine(localOkHttpClient, localRequest, bool2, true, false, paramConnection, null, paramRetryableSink, paramResponse);
      label226: if (this.chunkLength > 0)
      {
        localBuilder.header("Transfer-Encoding", "chunked");
        bool2 = false;
      }
      else
      {
        bool2 = true;
      }
    }
  }

  private static String responseSourceHeader(Response paramResponse)
  {
    if (paramResponse.networkResponse() == null)
    {
      if (paramResponse.cacheResponse() == null)
        return "NONE";
      return "CACHE " + paramResponse.code();
    }
    if (paramResponse.cacheResponse() == null)
      return "NETWORK " + paramResponse.code();
    return "CONDITIONAL_CACHE " + paramResponse.networkResponse().code();
  }

  private void setProtocols(String paramString, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    if (paramBoolean)
      localArrayList.addAll(this.client.getProtocols());
    String[] arrayOfString = paramString.split(",", -1);
    int i = arrayOfString.length;
    int j = 0;
    while (j < i)
    {
      String str = arrayOfString[j];
      try
      {
        localArrayList.add(Protocol.get(str));
        j++;
      }
      catch (IOException localIOException)
      {
        throw new IllegalStateException(localIOException);
      }
    }
    this.client.setProtocols(localArrayList);
  }

  public final void addRequestProperty(String paramString1, String paramString2)
  {
    if (this.connected)
      throw new IllegalStateException("Cannot add request property after connection is made");
    if (paramString1 == null)
      throw new NullPointerException("field == null");
    if (paramString2 == null)
    {
      Platform.get().logW("Ignoring header " + paramString1 + " because its value was null.");
      return;
    }
    if (("X-Android-Transports".equals(paramString1)) || ("X-Android-Protocols".equals(paramString1)))
    {
      setProtocols(paramString2, true);
      return;
    }
    this.requestHeaders.add(paramString1, paramString2);
  }

  public final void connect()
    throws IOException
  {
    initHttpEngine();
    while (!execute(false));
  }

  public final void disconnect()
  {
    if (this.httpEngine == null)
      return;
    this.httpEngine.disconnect();
  }

  public int getConnectTimeout()
  {
    return this.client.getConnectTimeout();
  }

  public final InputStream getErrorStream()
  {
    try
    {
      HttpEngine localHttpEngine = getResponse();
      boolean bool = HttpEngine.hasBody(localHttpEngine.getResponse());
      Object localObject = null;
      if (bool)
      {
        int i = localHttpEngine.getResponse().code();
        localObject = null;
        if (i >= 400)
        {
          InputStream localInputStream = localHttpEngine.getResponse().body().byteStream();
          localObject = localInputStream;
        }
      }
      return localObject;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  public final String getHeaderField(int paramInt)
  {
    try
    {
      String str = getHeaders().value(paramInt);
      return str;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  public final String getHeaderField(String paramString)
  {
    if (paramString == null);
    try
    {
      return StatusLine.get(getResponse().getResponse()).toString();
      String str = getHeaders().get(paramString);
      return str;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  public final String getHeaderFieldKey(int paramInt)
  {
    try
    {
      String str = getHeaders().name(paramInt);
      return str;
    }
    catch (IOException localIOException)
    {
    }
    return null;
  }

  public final Map<String, List<String>> getHeaderFields()
  {
    try
    {
      Map localMap = OkHeaders.toMultimap(getHeaders(), StatusLine.get(getResponse().getResponse()).toString());
      return localMap;
    }
    catch (IOException localIOException)
    {
    }
    return Collections.emptyMap();
  }

  public final InputStream getInputStream()
    throws IOException
  {
    if (!this.doInput)
      throw new ProtocolException("This protocol does not support input");
    HttpEngine localHttpEngine = getResponse();
    if (getResponseCode() >= 400)
      throw new FileNotFoundException(this.url.toString());
    return localHttpEngine.getResponse().body().byteStream();
  }

  public final OutputStream getOutputStream()
    throws IOException
  {
    connect();
    BufferedSink localBufferedSink = this.httpEngine.getBufferedRequestBody();
    if (localBufferedSink == null)
      throw new ProtocolException("method does not support a request body: " + this.method);
    if (this.httpEngine.hasResponse())
      throw new ProtocolException("cannot write request body after response has been read");
    return localBufferedSink.outputStream();
  }

  public final Permission getPermission()
    throws IOException
  {
    String str = getURL().getHost();
    int i = Util.getEffectivePort(getURL());
    if (usingProxy())
    {
      InetSocketAddress localInetSocketAddress = (InetSocketAddress)this.client.getProxy().address();
      str = localInetSocketAddress.getHostName();
      i = localInetSocketAddress.getPort();
    }
    return new SocketPermission(str + ":" + i, "connect, resolve");
  }

  public int getReadTimeout()
  {
    return this.client.getReadTimeout();
  }

  public final Map<String, List<String>> getRequestProperties()
  {
    if (this.connected)
      throw new IllegalStateException("Cannot access request header fields after connection is set");
    return OkHeaders.toMultimap(this.requestHeaders.build(), null);
  }

  public final String getRequestProperty(String paramString)
  {
    if (paramString == null)
      return null;
    return this.requestHeaders.get(paramString);
  }

  public final int getResponseCode()
    throws IOException
  {
    return getResponse().getResponse().code();
  }

  public String getResponseMessage()
    throws IOException
  {
    return getResponse().getResponse().message();
  }

  public void setConnectTimeout(int paramInt)
  {
    this.client.setConnectTimeout(paramInt, TimeUnit.MILLISECONDS);
  }

  public void setFixedLengthStreamingMode(int paramInt)
  {
    setFixedLengthStreamingMode(paramInt);
  }

  public void setFixedLengthStreamingMode(long paramLong)
  {
    if (this.connected)
      throw new IllegalStateException("Already connected");
    if (this.chunkLength > 0)
      throw new IllegalStateException("Already in chunked mode");
    if (paramLong < 0L)
      throw new IllegalArgumentException("contentLength < 0");
    this.fixedContentLength = paramLong;
    this.fixedContentLength = ((int)Math.min(paramLong, 2147483647L));
  }

  public void setIfModifiedSince(long paramLong)
  {
    super.setIfModifiedSince(paramLong);
    if (this.ifModifiedSince != 0L)
    {
      this.requestHeaders.set("If-Modified-Since", HttpDate.format(new Date(this.ifModifiedSince)));
      return;
    }
    this.requestHeaders.removeAll("If-Modified-Since");
  }

  public void setInstanceFollowRedirects(boolean paramBoolean)
  {
    this.client.setFollowRedirects(paramBoolean);
  }

  public void setReadTimeout(int paramInt)
  {
    this.client.setReadTimeout(paramInt, TimeUnit.MILLISECONDS);
  }

  public void setRequestMethod(String paramString)
    throws ProtocolException
  {
    if (!METHODS.contains(paramString))
      throw new ProtocolException("Expected one of " + METHODS + " but was " + paramString);
    this.method = paramString;
  }

  public final void setRequestProperty(String paramString1, String paramString2)
  {
    if (this.connected)
      throw new IllegalStateException("Cannot set request property after connection is made");
    if (paramString1 == null)
      throw new NullPointerException("field == null");
    if (paramString2 == null)
    {
      Platform.get().logW("Ignoring header " + paramString1 + " because its value was null.");
      return;
    }
    if (("X-Android-Transports".equals(paramString1)) || ("X-Android-Protocols".equals(paramString1)))
    {
      setProtocols(paramString2, false);
      return;
    }
    this.requestHeaders.set(paramString1, paramString2);
  }

  public final boolean usingProxy()
  {
    if (this.route != null);
    for (Proxy localProxy = this.route.getProxy(); (localProxy != null) && (localProxy.type() != Proxy.Type.DIRECT); localProxy = this.client.getProxy())
      return true;
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.huc.HttpURLConnectionImpl
 * JD-Core Version:    0.6.2
 */