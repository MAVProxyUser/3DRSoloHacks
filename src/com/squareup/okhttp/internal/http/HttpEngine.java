package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.CertificatePinner;
import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Interceptor.Chain;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.Version;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.CookieHandler;
import java.net.ProtocolException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.GzipSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpEngine
{
  private static final ResponseBody EMPTY_BODY = new ResponseBody()
  {
    public long contentLength()
    {
      return 0L;
    }

    public MediaType contentType()
    {
      return null;
    }

    public BufferedSource source()
    {
      return new Buffer();
    }
  };
  public static final int MAX_FOLLOW_UPS = 20;
  private Address address;
  public final boolean bufferRequestBody;
  private BufferedSink bufferedRequestBody;
  private Response cacheResponse;
  private CacheStrategy cacheStrategy;
  private final boolean callerWritesRequestBody;
  final OkHttpClient client;
  private Connection connection;
  private final boolean forWebSocket;
  private Request networkRequest;
  private final Response priorResponse;
  private Sink requestBodyOut;
  private Route route;
  private RouteSelector routeSelector;
  long sentRequestMillis = -1L;
  private CacheRequest storeRequest;
  private boolean transparentGzip;
  private Transport transport;
  private final Request userRequest;
  private Response userResponse;

  public HttpEngine(OkHttpClient paramOkHttpClient, Request paramRequest, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, Connection paramConnection, RouteSelector paramRouteSelector, RetryableSink paramRetryableSink, Response paramResponse)
  {
    this.client = paramOkHttpClient;
    this.userRequest = paramRequest;
    this.bufferRequestBody = paramBoolean1;
    this.callerWritesRequestBody = paramBoolean2;
    this.forWebSocket = paramBoolean3;
    this.connection = paramConnection;
    this.routeSelector = paramRouteSelector;
    this.requestBodyOut = paramRetryableSink;
    this.priorResponse = paramResponse;
    if (paramConnection != null)
    {
      Internal.instance.setOwner(paramConnection, this);
      this.route = paramConnection.getRoute();
      return;
    }
    this.route = null;
  }

  private Response cacheWritingResponse(final CacheRequest paramCacheRequest, Response paramResponse)
    throws IOException
  {
    if (paramCacheRequest == null);
    Sink localSink;
    do
    {
      return paramResponse;
      localSink = paramCacheRequest.body();
    }
    while (localSink == null);
    Source local2 = new Source()
    {
      boolean cacheRequestClosed;

      public void close()
        throws IOException
      {
        if ((!this.cacheRequestClosed) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS)))
        {
          this.cacheRequestClosed = true;
          paramCacheRequest.abort();
        }
        this.val$source.close();
      }

      public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        long l;
        try
        {
          l = this.val$source.read(paramAnonymousBuffer, paramAnonymousLong);
          if (l == -1L)
          {
            if (!this.cacheRequestClosed)
            {
              this.cacheRequestClosed = true;
              this.val$cacheBody.close();
            }
            return -1L;
          }
        }
        catch (IOException localIOException)
        {
          if (!this.cacheRequestClosed)
          {
            this.cacheRequestClosed = true;
            paramCacheRequest.abort();
          }
          throw localIOException;
        }
        paramAnonymousBuffer.copyTo(this.val$cacheBody.buffer(), paramAnonymousBuffer.size() - l, l);
        this.val$cacheBody.emitCompleteSegments();
        return l;
      }

      public Timeout timeout()
      {
        return this.val$source.timeout();
      }
    };
    return paramResponse.newBuilder().body(new RealResponseBody(paramResponse.headers(), Okio.buffer(local2))).build();
  }

  private static Headers combine(Headers paramHeaders1, Headers paramHeaders2)
    throws IOException
  {
    Headers.Builder localBuilder = new Headers.Builder();
    int i = 0;
    int j = paramHeaders1.size();
    if (i < j)
    {
      String str2 = paramHeaders1.name(i);
      String str3 = paramHeaders1.value(i);
      if (("Warning".equalsIgnoreCase(str2)) && (str3.startsWith("1")));
      while (true)
      {
        i++;
        break;
        if ((!OkHeaders.isEndToEnd(str2)) || (paramHeaders2.get(str2) == null))
          localBuilder.add(str2, str3);
      }
    }
    int k = 0;
    int m = paramHeaders2.size();
    if (k < m)
    {
      String str1 = paramHeaders2.name(k);
      if ("Content-Length".equalsIgnoreCase(str1));
      while (true)
      {
        k++;
        break;
        if (OkHeaders.isEndToEnd(str1))
          localBuilder.add(str1, paramHeaders2.value(k));
      }
    }
    return localBuilder.build();
  }

  private void connect()
    throws IOException
  {
    if (this.connection != null)
      throw new IllegalStateException();
    if (this.routeSelector == null)
    {
      this.address = createAddress(this.client, this.networkRequest);
      this.routeSelector = RouteSelector.get(this.address, this.networkRequest, this.client);
    }
    this.connection = nextConnection();
    this.route = this.connection.getRoute();
  }

  private void connectFailed(RouteSelector paramRouteSelector, IOException paramIOException)
  {
    if (Internal.instance.recycleCount(this.connection) > 0)
      return;
    paramRouteSelector.connectFailed(this.connection.getRoute(), paramIOException);
  }

  private static Address createAddress(OkHttpClient paramOkHttpClient, Request paramRequest)
    throws UnknownHostException
  {
    String str = paramRequest.url().getHost();
    if ((str == null) || (str.length() == 0))
      throw new UnknownHostException(paramRequest.url().toString());
    boolean bool = paramRequest.isHttps();
    SSLSocketFactory localSSLSocketFactory = null;
    HostnameVerifier localHostnameVerifier = null;
    CertificatePinner localCertificatePinner = null;
    if (bool)
    {
      localSSLSocketFactory = paramOkHttpClient.getSslSocketFactory();
      localHostnameVerifier = paramOkHttpClient.getHostnameVerifier();
      localCertificatePinner = paramOkHttpClient.getCertificatePinner();
    }
    return new Address(str, Util.getEffectivePort(paramRequest.url()), paramOkHttpClient.getSocketFactory(), localSSLSocketFactory, localHostnameVerifier, localCertificatePinner, paramOkHttpClient.getAuthenticator(), paramOkHttpClient.getProxy(), paramOkHttpClient.getProtocols(), paramOkHttpClient.getConnectionSpecs(), paramOkHttpClient.getProxySelector());
  }

  private Connection createNextConnection()
    throws IOException
  {
    ConnectionPool localConnectionPool = this.client.getConnectionPool();
    while (true)
    {
      Connection localConnection = localConnectionPool.get(this.address);
      if (localConnection == null)
        break;
      if ((this.networkRequest.method().equals("GET")) || (Internal.instance.isReadable(localConnection)))
        return localConnection;
      localConnection.getSocket().close();
    }
    return new Connection(localConnectionPool, this.routeSelector.next());
  }

  public static boolean hasBody(Response paramResponse)
  {
    if (paramResponse.request().method().equals("HEAD"));
    do
    {
      return false;
      int i = paramResponse.code();
      if (((i < 100) || (i >= 200)) && (i != 204) && (i != 304))
        return true;
    }
    while ((OkHeaders.contentLength(paramResponse) == -1L) && (!"chunked".equalsIgnoreCase(paramResponse.header("Transfer-Encoding"))));
    return true;
  }

  public static String hostHeader(URL paramURL)
  {
    if (Util.getEffectivePort(paramURL) != Util.getDefaultPort(paramURL.getProtocol()))
      return paramURL.getHost() + ":" + paramURL.getPort();
    return paramURL.getHost();
  }

  private boolean isRecoverable(IOException paramIOException)
  {
    if (!this.client.getRetryOnConnectionFailure());
    while (((paramIOException instanceof SSLPeerUnverifiedException)) || (((paramIOException instanceof SSLHandshakeException)) && ((paramIOException.getCause() instanceof CertificateException))) || ((paramIOException instanceof ProtocolException)) || ((paramIOException instanceof InterruptedIOException)))
      return false;
    return true;
  }

  private void maybeCache()
    throws IOException
  {
    InternalCache localInternalCache = Internal.instance.internalCache(this.client);
    if (localInternalCache == null);
    do
    {
      return;
      if (CacheStrategy.isCacheable(this.userResponse, this.networkRequest))
        break;
    }
    while (!HttpMethod.invalidatesCache(this.networkRequest.method()));
    try
    {
      localInternalCache.remove(this.networkRequest);
      return;
    }
    catch (IOException localIOException)
    {
      return;
    }
    this.storeRequest = localInternalCache.put(stripBody(this.userResponse));
  }

  private Request networkRequest(Request paramRequest)
    throws IOException
  {
    Request.Builder localBuilder = paramRequest.newBuilder();
    if (paramRequest.header("Host") == null)
      localBuilder.header("Host", hostHeader(paramRequest.url()));
    if (((this.connection == null) || (this.connection.getProtocol() != Protocol.HTTP_1_0)) && (paramRequest.header("Connection") == null))
      localBuilder.header("Connection", "Keep-Alive");
    if (paramRequest.header("Accept-Encoding") == null)
    {
      this.transparentGzip = true;
      localBuilder.header("Accept-Encoding", "gzip");
    }
    CookieHandler localCookieHandler = this.client.getCookieHandler();
    if (localCookieHandler != null)
    {
      Map localMap = OkHeaders.toMultimap(localBuilder.build().headers(), null);
      OkHeaders.addCookies(localBuilder, localCookieHandler.get(paramRequest.uri(), localMap));
    }
    if (paramRequest.header("User-Agent") == null)
      localBuilder.header("User-Agent", Version.userAgent());
    return localBuilder.build();
  }

  private Connection nextConnection()
    throws IOException
  {
    Connection localConnection = createNextConnection();
    Internal.instance.connectAndSetOwner(this.client, localConnection, this, this.networkRequest);
    return localConnection;
  }

  private Response readNetworkResponse()
    throws IOException
  {
    this.transport.finishRequest();
    Response localResponse = this.transport.readResponseHeaders().request(this.networkRequest).handshake(this.connection.getHandshake()).header(OkHeaders.SENT_MILLIS, Long.toString(this.sentRequestMillis)).header(OkHeaders.RECEIVED_MILLIS, Long.toString(System.currentTimeMillis())).build();
    if (!this.forWebSocket)
      localResponse = localResponse.newBuilder().body(this.transport.openResponseBody(localResponse)).build();
    Internal.instance.setProtocol(this.connection, localResponse.protocol());
    return localResponse;
  }

  private static Response stripBody(Response paramResponse)
  {
    if ((paramResponse != null) && (paramResponse.body() != null))
      paramResponse = paramResponse.newBuilder().body(null).build();
    return paramResponse;
  }

  private Response unzip(Response paramResponse)
    throws IOException
  {
    if ((!this.transparentGzip) || (!"gzip".equalsIgnoreCase(this.userResponse.header("Content-Encoding"))));
    while (paramResponse.body() == null)
      return paramResponse;
    GzipSource localGzipSource = new GzipSource(paramResponse.body().source());
    Headers localHeaders = paramResponse.headers().newBuilder().removeAll("Content-Encoding").removeAll("Content-Length").build();
    return paramResponse.newBuilder().headers(localHeaders).body(new RealResponseBody(localHeaders, Okio.buffer(localGzipSource))).build();
  }

  private static boolean validate(Response paramResponse1, Response paramResponse2)
  {
    if (paramResponse2.code() == 304);
    Date localDate1;
    Date localDate2;
    do
    {
      return true;
      localDate1 = paramResponse1.headers().getDate("Last-Modified");
      if (localDate1 == null)
        break;
      localDate2 = paramResponse2.headers().getDate("Last-Modified");
    }
    while ((localDate2 != null) && (localDate2.getTime() < localDate1.getTime()));
    return false;
  }

  public Connection close()
  {
    if (this.bufferedRequestBody != null)
      Util.closeQuietly(this.bufferedRequestBody);
    while (this.userResponse == null)
    {
      if (this.connection != null)
        Util.closeQuietly(this.connection.getSocket());
      this.connection = null;
      return null;
      if (this.requestBodyOut != null)
        Util.closeQuietly(this.requestBodyOut);
    }
    Util.closeQuietly(this.userResponse.body());
    if ((this.transport != null) && (this.connection != null) && (!this.transport.canReuseConnection()))
    {
      Util.closeQuietly(this.connection.getSocket());
      this.connection = null;
      return null;
    }
    if ((this.connection != null) && (!Internal.instance.clearOwner(this.connection)))
      this.connection = null;
    Connection localConnection = this.connection;
    this.connection = null;
    return localConnection;
  }

  public void disconnect()
  {
    if (this.transport != null);
    try
    {
      this.transport.disconnect(this);
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public Request followUpRequest()
    throws IOException
  {
    if (this.userResponse == null)
      throw new IllegalStateException();
    Proxy localProxy;
    if (getRoute() != null)
    {
      localProxy = getRoute().getProxy();
      switch (this.userResponse.code())
      {
      default:
      case 407:
      case 401:
      case 307:
      case 308:
      case 300:
      case 301:
      case 302:
      case 303:
      }
    }
    URL localURL;
    do
    {
      String str;
      do
      {
        do
        {
          return null;
          localProxy = this.client.getProxy();
          break;
          if (localProxy.type() != Proxy.Type.HTTP)
            throw new ProtocolException("Received HTTP_PROXY_AUTH (407) code while not using proxy");
          return OkHeaders.processAuthHeader(this.client.getAuthenticator(), this.userResponse, localProxy);
        }
        while (((!this.userRequest.method().equals("GET")) && (!this.userRequest.method().equals("HEAD"))) || (!this.client.getFollowRedirects()));
        str = this.userResponse.header("Location");
      }
      while (str == null);
      localURL = new URL(this.userRequest.url(), str);
    }
    while (((!localURL.getProtocol().equals("https")) && (!localURL.getProtocol().equals("http"))) || ((!localURL.getProtocol().equals(this.userRequest.url().getProtocol())) && (!this.client.getFollowSslRedirects())));
    Request.Builder localBuilder = this.userRequest.newBuilder();
    if (HttpMethod.permitsRequestBody(this.userRequest.method()))
    {
      localBuilder.method("GET", null);
      localBuilder.removeHeader("Transfer-Encoding");
      localBuilder.removeHeader("Content-Length");
      localBuilder.removeHeader("Content-Type");
    }
    if (!sameConnection(localURL))
      localBuilder.removeHeader("Authorization");
    return localBuilder.url(localURL).build();
  }

  public BufferedSink getBufferedRequestBody()
  {
    BufferedSink localBufferedSink1 = this.bufferedRequestBody;
    if (localBufferedSink1 != null)
      return localBufferedSink1;
    Sink localSink = getRequestBody();
    BufferedSink localBufferedSink2;
    if (localSink != null)
    {
      localBufferedSink2 = Okio.buffer(localSink);
      this.bufferedRequestBody = localBufferedSink2;
    }
    while (true)
    {
      return localBufferedSink2;
      localBufferedSink2 = null;
    }
  }

  public Connection getConnection()
  {
    return this.connection;
  }

  public Request getRequest()
  {
    return this.userRequest;
  }

  public Sink getRequestBody()
  {
    if (this.cacheStrategy == null)
      throw new IllegalStateException();
    return this.requestBodyOut;
  }

  public Response getResponse()
  {
    if (this.userResponse == null)
      throw new IllegalStateException();
    return this.userResponse;
  }

  public Route getRoute()
  {
    return this.route;
  }

  public boolean hasResponse()
  {
    return this.userResponse != null;
  }

  boolean permitsRequestBody()
  {
    return HttpMethod.permitsRequestBody(this.userRequest.method());
  }

  public void readResponse()
    throws IOException
  {
    if (this.userResponse != null);
    label418: label430: label440: 
    do
    {
      do
      {
        return;
        if ((this.networkRequest == null) && (this.cacheResponse == null))
          throw new IllegalStateException("call sendRequest() first!");
      }
      while (this.networkRequest == null);
      if (this.forWebSocket)
        this.transport.writeRequestHeaders(this.networkRequest);
      for (Response localResponse = readNetworkResponse(); ; localResponse = new NetworkInterceptorChain(0, this.networkRequest).proceed(this.networkRequest))
      {
        receiveHeaders(localResponse.headers());
        if (this.cacheResponse == null)
          break label440;
        if (!validate(this.cacheResponse, localResponse))
          break label430;
        this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).headers(combine(this.cacheResponse.headers(), localResponse.headers())).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(localResponse)).build();
        localResponse.body().close();
        releaseConnection();
        InternalCache localInternalCache = Internal.instance.internalCache(this.client);
        localInternalCache.trackConditionalCacheHit();
        localInternalCache.update(this.cacheResponse, stripBody(this.userResponse));
        this.userResponse = unzip(this.userResponse);
        return;
        if (this.callerWritesRequestBody)
          break;
      }
      if ((this.bufferedRequestBody != null) && (this.bufferedRequestBody.buffer().size() > 0L))
        this.bufferedRequestBody.emit();
      if (this.sentRequestMillis == -1L)
      {
        if ((OkHeaders.contentLength(this.networkRequest) == -1L) && ((this.requestBodyOut instanceof RetryableSink)))
        {
          long l = ((RetryableSink)this.requestBodyOut).contentLength();
          this.networkRequest = this.networkRequest.newBuilder().header("Content-Length", Long.toString(l)).build();
        }
        this.transport.writeRequestHeaders(this.networkRequest);
      }
      if (this.requestBodyOut != null)
      {
        if (this.bufferedRequestBody == null)
          break label418;
        this.bufferedRequestBody.close();
      }
      while (true)
      {
        if ((this.requestBodyOut instanceof RetryableSink))
          this.transport.writeRequestBody((RetryableSink)this.requestBodyOut);
        localResponse = readNetworkResponse();
        break;
        this.requestBodyOut.close();
      }
      Util.closeQuietly(this.cacheResponse.body());
      this.userResponse = localResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).networkResponse(stripBody(localResponse)).build();
    }
    while (!hasBody(this.userResponse));
    maybeCache();
    this.userResponse = unzip(cacheWritingResponse(this.storeRequest, this.userResponse));
  }

  public void receiveHeaders(Headers paramHeaders)
    throws IOException
  {
    CookieHandler localCookieHandler = this.client.getCookieHandler();
    if (localCookieHandler != null)
      localCookieHandler.put(this.userRequest.uri(), OkHeaders.toMultimap(paramHeaders, null));
  }

  public HttpEngine recover(IOException paramIOException)
  {
    return recover(paramIOException, this.requestBodyOut);
  }

  public HttpEngine recover(IOException paramIOException, Sink paramSink)
  {
    if ((this.routeSelector != null) && (this.connection != null))
      connectFailed(this.routeSelector, paramIOException);
    if ((paramSink == null) || ((paramSink instanceof RetryableSink)));
    for (int i = 1; ((this.routeSelector == null) && (this.connection == null)) || ((this.routeSelector != null) && (!this.routeSelector.hasNext())) || (!isRecoverable(paramIOException)) || (i == 0); i = 0)
      return null;
    Connection localConnection = close();
    return new HttpEngine(this.client, this.userRequest, this.bufferRequestBody, this.callerWritesRequestBody, this.forWebSocket, localConnection, this.routeSelector, (RetryableSink)paramSink, this.priorResponse);
  }

  public void releaseConnection()
    throws IOException
  {
    if ((this.transport != null) && (this.connection != null))
      this.transport.releaseConnectionOnIdle();
    this.connection = null;
  }

  public boolean sameConnection(URL paramURL)
  {
    URL localURL = this.userRequest.url();
    return (localURL.getHost().equals(paramURL.getHost())) && (Util.getEffectivePort(localURL) == Util.getEffectivePort(paramURL)) && (localURL.getProtocol().equals(paramURL.getProtocol()));
  }

  public void sendRequest()
    throws IOException
  {
    if (this.cacheStrategy != null)
      return;
    if (this.transport != null)
      throw new IllegalStateException();
    Request localRequest = networkRequest(this.userRequest);
    InternalCache localInternalCache = Internal.instance.internalCache(this.client);
    if (localInternalCache != null);
    long l;
    for (Response localResponse = localInternalCache.get(localRequest); ; localResponse = null)
    {
      this.cacheStrategy = new CacheStrategy.Factory(System.currentTimeMillis(), localRequest, localResponse).get();
      this.networkRequest = this.cacheStrategy.networkRequest;
      this.cacheResponse = this.cacheStrategy.cacheResponse;
      if (localInternalCache != null)
        localInternalCache.trackResponse(this.cacheStrategy);
      if ((localResponse != null) && (this.cacheResponse == null))
        Util.closeQuietly(localResponse.body());
      if (this.networkRequest == null)
        break label302;
      if (this.connection == null)
        connect();
      this.transport = Internal.instance.newTransport(this.connection, this);
      if ((!this.callerWritesRequestBody) || (!permitsRequestBody()) || (this.requestBodyOut != null))
        break;
      l = OkHeaders.contentLength(localRequest);
      if (!this.bufferRequestBody)
        break label269;
      if (l <= 2147483647L)
        break label220;
      throw new IllegalStateException("Use setFixedLengthStreamingMode() or setChunkedStreamingMode() for requests larger than 2 GiB.");
    }
    label220: if (l != -1L)
    {
      this.transport.writeRequestHeaders(this.networkRequest);
      this.requestBodyOut = new RetryableSink((int)l);
      return;
    }
    this.requestBodyOut = new RetryableSink();
    return;
    label269: this.transport.writeRequestHeaders(this.networkRequest);
    this.requestBodyOut = this.transport.createRequestBody(this.networkRequest, l);
    return;
    label302: if (this.connection != null)
    {
      Internal.instance.recycle(this.client.getConnectionPool(), this.connection);
      this.connection = null;
    }
    if (this.cacheResponse != null);
    for (this.userResponse = this.cacheResponse.newBuilder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).cacheResponse(stripBody(this.cacheResponse)).build(); ; this.userResponse = new Response.Builder().request(this.userRequest).priorResponse(stripBody(this.priorResponse)).protocol(Protocol.HTTP_1_1).code(504).message("Unsatisfiable Request (only-if-cached)").body(EMPTY_BODY).build())
    {
      this.userResponse = unzip(this.userResponse);
      return;
    }
  }

  public void writingRequestHeaders()
  {
    if (this.sentRequestMillis != -1L)
      throw new IllegalStateException();
    this.sentRequestMillis = System.currentTimeMillis();
  }

  class NetworkInterceptorChain
    implements Interceptor.Chain
  {
    private int calls;
    private final int index;
    private final Request request;

    NetworkInterceptorChain(int paramRequest, Request arg3)
    {
      this.index = paramRequest;
      Object localObject;
      this.request = localObject;
    }

    public Connection connection()
    {
      return HttpEngine.this.connection;
    }

    public Response proceed(Request paramRequest)
      throws IOException
    {
      this.calls = (1 + this.calls);
      if (this.index > 0)
      {
        Interceptor localInterceptor2 = (Interceptor)HttpEngine.this.client.networkInterceptors().get(-1 + this.index);
        Address localAddress = connection().getRoute().getAddress();
        if ((!paramRequest.url().getHost().equals(localAddress.getUriHost())) || (Util.getEffectivePort(paramRequest.url()) != localAddress.getUriPort()))
          throw new IllegalStateException("network interceptor " + localInterceptor2 + " must retain the same host and port");
        if (this.calls > 1)
          throw new IllegalStateException("network interceptor " + localInterceptor2 + " must call proceed() exactly once");
      }
      Response localResponse;
      if (this.index < HttpEngine.this.client.networkInterceptors().size())
      {
        NetworkInterceptorChain localNetworkInterceptorChain = new NetworkInterceptorChain(HttpEngine.this, 1 + this.index, paramRequest);
        Interceptor localInterceptor1 = (Interceptor)HttpEngine.this.client.networkInterceptors().get(this.index);
        localResponse = localInterceptor1.intercept(localNetworkInterceptorChain);
        if (localNetworkInterceptorChain.calls != 1)
          throw new IllegalStateException("network interceptor " + localInterceptor1 + " must call proceed() exactly once");
      }
      else
      {
        HttpEngine.this.transport.writeRequestHeaders(paramRequest);
        if ((HttpEngine.this.permitsRequestBody()) && (paramRequest.body() != null))
        {
          BufferedSink localBufferedSink = Okio.buffer(HttpEngine.this.transport.createRequestBody(paramRequest, paramRequest.body().contentLength()));
          paramRequest.body().writeTo(localBufferedSink);
          localBufferedSink.close();
        }
        localResponse = HttpEngine.this.readNetworkResponse();
      }
      return localResponse;
    }

    public Request request()
    {
      return this.request;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.HttpEngine
 * JD-Core Version:    0.6.2
 */