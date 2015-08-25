package com.squareup.okhttp;

import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.AuthenticatorAdapter;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.CookieHandler;
import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

public class OkHttpClient
  implements Cloneable
{
  private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS;
  private static final List<Protocol> DEFAULT_PROTOCOLS;
  private static SSLSocketFactory defaultSslSocketFactory;
  private Authenticator authenticator;
  private Cache cache;
  private CertificatePinner certificatePinner;
  private int connectTimeout;
  private ConnectionPool connectionPool;
  private List<ConnectionSpec> connectionSpecs;
  private CookieHandler cookieHandler;
  private Dispatcher dispatcher;
  private boolean followRedirects = true;
  private boolean followSslRedirects = true;
  private HostnameVerifier hostnameVerifier;
  private final List<Interceptor> interceptors = new ArrayList();
  private InternalCache internalCache;
  private Network network;
  private final List<Interceptor> networkInterceptors = new ArrayList();
  private List<Protocol> protocols;
  private Proxy proxy;
  private ProxySelector proxySelector;
  private int readTimeout;
  private boolean retryOnConnectionFailure = true;
  private final RouteDatabase routeDatabase;
  private SocketFactory socketFactory;
  private SSLSocketFactory sslSocketFactory;
  private int writeTimeout;

  static
  {
    Protocol[] arrayOfProtocol = new Protocol[3];
    arrayOfProtocol[0] = Protocol.HTTP_2;
    arrayOfProtocol[1] = Protocol.SPDY_3;
    arrayOfProtocol[2] = Protocol.HTTP_1_1;
    DEFAULT_PROTOCOLS = Util.immutableList(arrayOfProtocol);
    ConnectionSpec[] arrayOfConnectionSpec = new ConnectionSpec[3];
    arrayOfConnectionSpec[0] = ConnectionSpec.MODERN_TLS;
    arrayOfConnectionSpec[1] = ConnectionSpec.COMPATIBLE_TLS;
    arrayOfConnectionSpec[2] = ConnectionSpec.CLEARTEXT;
    DEFAULT_CONNECTION_SPECS = Util.immutableList(arrayOfConnectionSpec);
    Internal.instance = new Internal()
    {
      public void addLenient(Headers.Builder paramAnonymousBuilder, String paramAnonymousString)
      {
        paramAnonymousBuilder.addLenient(paramAnonymousString);
      }

      public Connection callEngineGetConnection(Call paramAnonymousCall)
      {
        return paramAnonymousCall.engine.getConnection();
      }

      public void callEngineReleaseConnection(Call paramAnonymousCall)
        throws IOException
      {
        paramAnonymousCall.engine.releaseConnection();
      }

      public void callEnqueue(Call paramAnonymousCall, Callback paramAnonymousCallback, boolean paramAnonymousBoolean)
      {
        paramAnonymousCall.enqueue(paramAnonymousCallback, paramAnonymousBoolean);
      }

      public boolean clearOwner(Connection paramAnonymousConnection)
      {
        return paramAnonymousConnection.clearOwner();
      }

      public void closeIfOwnedBy(Connection paramAnonymousConnection, Object paramAnonymousObject)
        throws IOException
      {
        paramAnonymousConnection.closeIfOwnedBy(paramAnonymousObject);
      }

      public void connectAndSetOwner(OkHttpClient paramAnonymousOkHttpClient, Connection paramAnonymousConnection, HttpEngine paramAnonymousHttpEngine, Request paramAnonymousRequest)
        throws IOException
      {
        paramAnonymousConnection.connectAndSetOwner(paramAnonymousOkHttpClient, paramAnonymousHttpEngine, paramAnonymousRequest);
      }

      public void connectionSetOwner(Connection paramAnonymousConnection, Object paramAnonymousObject)
      {
        paramAnonymousConnection.setOwner(paramAnonymousObject);
      }

      public InternalCache internalCache(OkHttpClient paramAnonymousOkHttpClient)
      {
        return paramAnonymousOkHttpClient.internalCache();
      }

      public boolean isReadable(Connection paramAnonymousConnection)
      {
        return paramAnonymousConnection.isReadable();
      }

      public Network network(OkHttpClient paramAnonymousOkHttpClient)
      {
        return paramAnonymousOkHttpClient.network;
      }

      public Transport newTransport(Connection paramAnonymousConnection, HttpEngine paramAnonymousHttpEngine)
        throws IOException
      {
        return paramAnonymousConnection.newTransport(paramAnonymousHttpEngine);
      }

      public void recycle(ConnectionPool paramAnonymousConnectionPool, Connection paramAnonymousConnection)
      {
        paramAnonymousConnectionPool.recycle(paramAnonymousConnection);
      }

      public int recycleCount(Connection paramAnonymousConnection)
      {
        return paramAnonymousConnection.recycleCount();
      }

      public RouteDatabase routeDatabase(OkHttpClient paramAnonymousOkHttpClient)
      {
        return paramAnonymousOkHttpClient.routeDatabase();
      }

      public void setCache(OkHttpClient paramAnonymousOkHttpClient, InternalCache paramAnonymousInternalCache)
      {
        paramAnonymousOkHttpClient.setInternalCache(paramAnonymousInternalCache);
      }

      public void setNetwork(OkHttpClient paramAnonymousOkHttpClient, Network paramAnonymousNetwork)
      {
        OkHttpClient.access$002(paramAnonymousOkHttpClient, paramAnonymousNetwork);
      }

      public void setOwner(Connection paramAnonymousConnection, HttpEngine paramAnonymousHttpEngine)
      {
        paramAnonymousConnection.setOwner(paramAnonymousHttpEngine);
      }

      public void setProtocol(Connection paramAnonymousConnection, Protocol paramAnonymousProtocol)
      {
        paramAnonymousConnection.setProtocol(paramAnonymousProtocol);
      }
    };
  }

  public OkHttpClient()
  {
    this.routeDatabase = new RouteDatabase();
    this.dispatcher = new Dispatcher();
  }

  private OkHttpClient(OkHttpClient paramOkHttpClient)
  {
    this.routeDatabase = paramOkHttpClient.routeDatabase;
    this.dispatcher = paramOkHttpClient.dispatcher;
    this.proxy = paramOkHttpClient.proxy;
    this.protocols = paramOkHttpClient.protocols;
    this.connectionSpecs = paramOkHttpClient.connectionSpecs;
    this.interceptors.addAll(paramOkHttpClient.interceptors);
    this.networkInterceptors.addAll(paramOkHttpClient.networkInterceptors);
    this.proxySelector = paramOkHttpClient.proxySelector;
    this.cookieHandler = paramOkHttpClient.cookieHandler;
    this.cache = paramOkHttpClient.cache;
    if (this.cache != null);
    for (InternalCache localInternalCache = this.cache.internalCache; ; localInternalCache = paramOkHttpClient.internalCache)
    {
      this.internalCache = localInternalCache;
      this.socketFactory = paramOkHttpClient.socketFactory;
      this.sslSocketFactory = paramOkHttpClient.sslSocketFactory;
      this.hostnameVerifier = paramOkHttpClient.hostnameVerifier;
      this.certificatePinner = paramOkHttpClient.certificatePinner;
      this.authenticator = paramOkHttpClient.authenticator;
      this.connectionPool = paramOkHttpClient.connectionPool;
      this.network = paramOkHttpClient.network;
      this.followSslRedirects = paramOkHttpClient.followSslRedirects;
      this.followRedirects = paramOkHttpClient.followRedirects;
      this.retryOnConnectionFailure = paramOkHttpClient.retryOnConnectionFailure;
      this.connectTimeout = paramOkHttpClient.connectTimeout;
      this.readTimeout = paramOkHttpClient.readTimeout;
      this.writeTimeout = paramOkHttpClient.writeTimeout;
      return;
    }
  }

  private SSLSocketFactory getDefaultSSLSocketFactory()
  {
    try
    {
      SSLSocketFactory localSSLSocketFactory1 = defaultSslSocketFactory;
      if (localSSLSocketFactory1 == null);
      try
      {
        SSLContext localSSLContext = SSLContext.getInstance("TLS");
        localSSLContext.init(null, null, null);
        defaultSslSocketFactory = localSSLContext.getSocketFactory();
        SSLSocketFactory localSSLSocketFactory2 = defaultSslSocketFactory;
        return localSSLSocketFactory2;
      }
      catch (GeneralSecurityException localGeneralSecurityException)
      {
        throw new AssertionError();
      }
    }
    finally
    {
    }
  }

  public OkHttpClient cancel(Object paramObject)
  {
    getDispatcher().cancel(paramObject);
    return this;
  }

  public final OkHttpClient clone()
  {
    try
    {
      OkHttpClient localOkHttpClient = (OkHttpClient)super.clone();
      return localOkHttpClient;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
    }
    throw new AssertionError();
  }

  final OkHttpClient copyWithDefaults()
  {
    OkHttpClient localOkHttpClient = new OkHttpClient(this);
    if (localOkHttpClient.proxySelector == null)
      localOkHttpClient.proxySelector = ProxySelector.getDefault();
    if (localOkHttpClient.cookieHandler == null)
      localOkHttpClient.cookieHandler = CookieHandler.getDefault();
    if (localOkHttpClient.socketFactory == null)
      localOkHttpClient.socketFactory = SocketFactory.getDefault();
    if (localOkHttpClient.sslSocketFactory == null)
      localOkHttpClient.sslSocketFactory = getDefaultSSLSocketFactory();
    if (localOkHttpClient.hostnameVerifier == null)
      localOkHttpClient.hostnameVerifier = OkHostnameVerifier.INSTANCE;
    if (localOkHttpClient.certificatePinner == null)
      localOkHttpClient.certificatePinner = CertificatePinner.DEFAULT;
    if (localOkHttpClient.authenticator == null)
      localOkHttpClient.authenticator = AuthenticatorAdapter.INSTANCE;
    if (localOkHttpClient.connectionPool == null)
      localOkHttpClient.connectionPool = ConnectionPool.getDefault();
    if (localOkHttpClient.protocols == null)
      localOkHttpClient.protocols = DEFAULT_PROTOCOLS;
    if (localOkHttpClient.connectionSpecs == null)
      localOkHttpClient.connectionSpecs = DEFAULT_CONNECTION_SPECS;
    if (localOkHttpClient.network == null)
      localOkHttpClient.network = Network.DEFAULT;
    return localOkHttpClient;
  }

  public final Authenticator getAuthenticator()
  {
    return this.authenticator;
  }

  public final Cache getCache()
  {
    return this.cache;
  }

  public final CertificatePinner getCertificatePinner()
  {
    return this.certificatePinner;
  }

  public final int getConnectTimeout()
  {
    return this.connectTimeout;
  }

  public final ConnectionPool getConnectionPool()
  {
    return this.connectionPool;
  }

  public final List<ConnectionSpec> getConnectionSpecs()
  {
    return this.connectionSpecs;
  }

  public final CookieHandler getCookieHandler()
  {
    return this.cookieHandler;
  }

  public final Dispatcher getDispatcher()
  {
    return this.dispatcher;
  }

  public final boolean getFollowRedirects()
  {
    return this.followRedirects;
  }

  public final boolean getFollowSslRedirects()
  {
    return this.followSslRedirects;
  }

  public final HostnameVerifier getHostnameVerifier()
  {
    return this.hostnameVerifier;
  }

  public final List<Protocol> getProtocols()
  {
    return this.protocols;
  }

  public final Proxy getProxy()
  {
    return this.proxy;
  }

  public final ProxySelector getProxySelector()
  {
    return this.proxySelector;
  }

  public final int getReadTimeout()
  {
    return this.readTimeout;
  }

  public final boolean getRetryOnConnectionFailure()
  {
    return this.retryOnConnectionFailure;
  }

  public final SocketFactory getSocketFactory()
  {
    return this.socketFactory;
  }

  public final SSLSocketFactory getSslSocketFactory()
  {
    return this.sslSocketFactory;
  }

  public final int getWriteTimeout()
  {
    return this.writeTimeout;
  }

  public List<Interceptor> interceptors()
  {
    return this.interceptors;
  }

  final InternalCache internalCache()
  {
    return this.internalCache;
  }

  public List<Interceptor> networkInterceptors()
  {
    return this.networkInterceptors;
  }

  public Call newCall(Request paramRequest)
  {
    return new Call(this, paramRequest);
  }

  final RouteDatabase routeDatabase()
  {
    return this.routeDatabase;
  }

  public final OkHttpClient setAuthenticator(Authenticator paramAuthenticator)
  {
    this.authenticator = paramAuthenticator;
    return this;
  }

  public final OkHttpClient setCache(Cache paramCache)
  {
    this.cache = paramCache;
    this.internalCache = null;
    return this;
  }

  public final OkHttpClient setCertificatePinner(CertificatePinner paramCertificatePinner)
  {
    this.certificatePinner = paramCertificatePinner;
    return this;
  }

  public final void setConnectTimeout(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("timeout < 0");
    if (paramTimeUnit == null)
      throw new IllegalArgumentException("unit == null");
    long l = paramTimeUnit.toMillis(paramLong);
    if (l > 2147483647L)
      throw new IllegalArgumentException("Timeout too large.");
    if ((l == 0L) && (paramLong > 0L))
      throw new IllegalArgumentException("Timeout too small.");
    this.connectTimeout = ((int)l);
  }

  public final OkHttpClient setConnectionPool(ConnectionPool paramConnectionPool)
  {
    this.connectionPool = paramConnectionPool;
    return this;
  }

  public final OkHttpClient setConnectionSpecs(List<ConnectionSpec> paramList)
  {
    this.connectionSpecs = Util.immutableList(paramList);
    return this;
  }

  public final OkHttpClient setCookieHandler(CookieHandler paramCookieHandler)
  {
    this.cookieHandler = paramCookieHandler;
    return this;
  }

  public final OkHttpClient setDispatcher(Dispatcher paramDispatcher)
  {
    if (paramDispatcher == null)
      throw new IllegalArgumentException("dispatcher == null");
    this.dispatcher = paramDispatcher;
    return this;
  }

  public final void setFollowRedirects(boolean paramBoolean)
  {
    this.followRedirects = paramBoolean;
  }

  public final OkHttpClient setFollowSslRedirects(boolean paramBoolean)
  {
    this.followSslRedirects = paramBoolean;
    return this;
  }

  public final OkHttpClient setHostnameVerifier(HostnameVerifier paramHostnameVerifier)
  {
    this.hostnameVerifier = paramHostnameVerifier;
    return this;
  }

  final void setInternalCache(InternalCache paramInternalCache)
  {
    this.internalCache = paramInternalCache;
    this.cache = null;
  }

  public final OkHttpClient setProtocols(List<Protocol> paramList)
  {
    List localList = Util.immutableList(paramList);
    if (!localList.contains(Protocol.HTTP_1_1))
      throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + localList);
    if (localList.contains(Protocol.HTTP_1_0))
      throw new IllegalArgumentException("protocols must not contain http/1.0: " + localList);
    if (localList.contains(null))
      throw new IllegalArgumentException("protocols must not contain null");
    this.protocols = Util.immutableList(localList);
    return this;
  }

  public final OkHttpClient setProxy(Proxy paramProxy)
  {
    this.proxy = paramProxy;
    return this;
  }

  public final OkHttpClient setProxySelector(ProxySelector paramProxySelector)
  {
    this.proxySelector = paramProxySelector;
    return this;
  }

  public final void setReadTimeout(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("timeout < 0");
    if (paramTimeUnit == null)
      throw new IllegalArgumentException("unit == null");
    long l = paramTimeUnit.toMillis(paramLong);
    if (l > 2147483647L)
      throw new IllegalArgumentException("Timeout too large.");
    if ((l == 0L) && (paramLong > 0L))
      throw new IllegalArgumentException("Timeout too small.");
    this.readTimeout = ((int)l);
  }

  public final void setRetryOnConnectionFailure(boolean paramBoolean)
  {
    this.retryOnConnectionFailure = paramBoolean;
  }

  public final OkHttpClient setSocketFactory(SocketFactory paramSocketFactory)
  {
    this.socketFactory = paramSocketFactory;
    return this;
  }

  public final OkHttpClient setSslSocketFactory(SSLSocketFactory paramSSLSocketFactory)
  {
    this.sslSocketFactory = paramSSLSocketFactory;
    return this;
  }

  public final void setWriteTimeout(long paramLong, TimeUnit paramTimeUnit)
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("timeout < 0");
    if (paramTimeUnit == null)
      throw new IllegalArgumentException("unit == null");
    long l = paramTimeUnit.toMillis(paramLong);
    if (l > 2147483647L)
      throw new IllegalArgumentException("Timeout too large.");
    if ((l == 0L) && (paramLong > 0L))
      throw new IllegalArgumentException("Timeout too small.");
    this.writeTimeout = ((int)l);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.OkHttpClient
 * JD-Core Version:    0.6.2
 */