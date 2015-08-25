package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Address;
import com.squareup.okhttp.ConnectionSpec;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Route;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Network;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.ProxySelector;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;

public final class RouteSelector
{
  private final Address address;
  private final OkHttpClient client;
  private List<ConnectionSpec> connectionSpecs = Collections.emptyList();
  private List<InetSocketAddress> inetSocketAddresses = Collections.emptyList();
  private InetSocketAddress lastInetSocketAddress;
  private Proxy lastProxy;
  private ConnectionSpec lastSpec;
  private final Network network;
  private int nextInetSocketAddressIndex;
  private int nextProxyIndex;
  private int nextSpecIndex;
  private final List<Route> postponedRoutes = new ArrayList();
  private List<Proxy> proxies = Collections.emptyList();
  private final Request request;
  private final RouteDatabase routeDatabase;
  private final URI uri;

  private RouteSelector(Address paramAddress, URI paramURI, OkHttpClient paramOkHttpClient, Request paramRequest)
  {
    this.address = paramAddress;
    this.uri = paramURI;
    this.client = paramOkHttpClient;
    this.routeDatabase = Internal.instance.routeDatabase(paramOkHttpClient);
    this.network = Internal.instance.network(paramOkHttpClient);
    this.request = paramRequest;
    resetNextProxy(paramURI, paramAddress.getProxy());
  }

  public static RouteSelector get(Address paramAddress, Request paramRequest, OkHttpClient paramOkHttpClient)
    throws IOException
  {
    return new RouteSelector(paramAddress, paramRequest.uri(), paramOkHttpClient, paramRequest);
  }

  static String getHostString(InetSocketAddress paramInetSocketAddress)
  {
    InetAddress localInetAddress = paramInetSocketAddress.getAddress();
    if (localInetAddress == null)
      return paramInetSocketAddress.getHostName();
    return localInetAddress.getHostAddress();
  }

  private boolean hasNextConnectionSpec()
  {
    return this.nextSpecIndex < this.connectionSpecs.size();
  }

  private boolean hasNextInetSocketAddress()
  {
    return this.nextInetSocketAddressIndex < this.inetSocketAddresses.size();
  }

  private boolean hasNextPostponed()
  {
    return !this.postponedRoutes.isEmpty();
  }

  private boolean hasNextProxy()
  {
    return this.nextProxyIndex < this.proxies.size();
  }

  private ConnectionSpec nextConnectionSpec()
    throws IOException
  {
    if (this.connectionSpecs.isEmpty())
    {
      StringBuilder localStringBuilder2 = new StringBuilder().append("No route to ");
      if (this.uri.getScheme() != null);
      for (String str2 = this.uri.getScheme() + "://"; ; str2 = "//")
        throw new UnknownServiceException(str2 + this.address.getUriHost() + "; no connection specs");
    }
    if (!hasNextConnectionSpec())
    {
      StringBuilder localStringBuilder1 = new StringBuilder().append("No route to ");
      if (this.uri.getScheme() != null);
      for (String str1 = this.uri.getScheme() + "://"; ; str1 = "//")
        throw new SocketException(str1 + this.address.getUriHost() + "; exhausted connection specs: " + this.connectionSpecs);
    }
    List localList = this.connectionSpecs;
    int i = this.nextSpecIndex;
    this.nextSpecIndex = (i + 1);
    return (ConnectionSpec)localList.get(i);
  }

  private InetSocketAddress nextInetSocketAddress()
    throws IOException
  {
    if (!hasNextInetSocketAddress())
      throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted inet socket addresses: " + this.inetSocketAddresses);
    List localList = this.inetSocketAddresses;
    int i = this.nextInetSocketAddressIndex;
    this.nextInetSocketAddressIndex = (i + 1);
    InetSocketAddress localInetSocketAddress = (InetSocketAddress)localList.get(i);
    resetConnectionSpecs();
    return localInetSocketAddress;
  }

  private Route nextPostponed()
  {
    return (Route)this.postponedRoutes.remove(0);
  }

  private Proxy nextProxy()
    throws IOException
  {
    if (!hasNextProxy())
      throw new SocketException("No route to " + this.address.getUriHost() + "; exhausted proxy configurations: " + this.proxies);
    List localList = this.proxies;
    int i = this.nextProxyIndex;
    this.nextProxyIndex = (i + 1);
    Proxy localProxy = (Proxy)localList.get(i);
    resetNextInetSocketAddress(localProxy);
    return localProxy;
  }

  private void resetConnectionSpecs()
  {
    this.connectionSpecs = new ArrayList();
    List localList = this.address.getConnectionSpecs();
    int i = 0;
    int j = localList.size();
    while (i < j)
    {
      ConnectionSpec localConnectionSpec = (ConnectionSpec)localList.get(i);
      if (this.request.isHttps() == localConnectionSpec.isTls())
        this.connectionSpecs.add(localConnectionSpec);
      i++;
    }
    this.nextSpecIndex = 0;
  }

  private void resetNextInetSocketAddress(Proxy paramProxy)
    throws IOException
  {
    this.inetSocketAddresses = new ArrayList();
    String str;
    if ((paramProxy.type() == Proxy.Type.DIRECT) || (paramProxy.type() == Proxy.Type.SOCKS))
      str = this.address.getUriHost();
    InetSocketAddress localInetSocketAddress;
    for (int i = Util.getEffectivePort(this.uri); (i < 1) || (i > 65535); i = localInetSocketAddress.getPort())
    {
      throw new SocketException("No route to " + str + ":" + i + "; port is out of range");
      SocketAddress localSocketAddress = paramProxy.address();
      if (!(localSocketAddress instanceof InetSocketAddress))
        throw new IllegalArgumentException("Proxy.address() is not an InetSocketAddress: " + localSocketAddress.getClass());
      localInetSocketAddress = (InetSocketAddress)localSocketAddress;
      str = getHostString(localInetSocketAddress);
    }
    for (InetAddress localInetAddress : this.network.resolveInetAddresses(str))
      this.inetSocketAddresses.add(new InetSocketAddress(localInetAddress, i));
    this.nextInetSocketAddressIndex = 0;
  }

  private void resetNextProxy(URI paramURI, Proxy paramProxy)
  {
    if (paramProxy != null)
      this.proxies = Collections.singletonList(paramProxy);
    while (true)
    {
      this.nextProxyIndex = 0;
      return;
      this.proxies = new ArrayList();
      List localList = this.client.getProxySelector().select(paramURI);
      if (localList != null)
        this.proxies.addAll(localList);
      this.proxies.removeAll(Collections.singleton(Proxy.NO_PROXY));
      this.proxies.add(Proxy.NO_PROXY);
    }
  }

  private boolean shouldSendTlsFallbackIndicator(ConnectionSpec paramConnectionSpec)
  {
    Object localObject = this.connectionSpecs.get(0);
    boolean bool1 = false;
    if (paramConnectionSpec != localObject)
    {
      boolean bool2 = paramConnectionSpec.isTls();
      bool1 = false;
      if (bool2)
        bool1 = true;
    }
    return bool1;
  }

  public void connectFailed(Route paramRoute, IOException paramIOException)
  {
    if ((paramRoute.getProxy().type() != Proxy.Type.DIRECT) && (this.address.getProxySelector() != null))
      this.address.getProxySelector().connectFailed(this.uri, paramRoute.getProxy().address(), paramIOException);
    this.routeDatabase.failed(paramRoute);
    if ((!(paramIOException instanceof SSLHandshakeException)) && (!(paramIOException instanceof SSLProtocolException)))
      while (this.nextSpecIndex < this.connectionSpecs.size())
      {
        List localList = this.connectionSpecs;
        int i = this.nextSpecIndex;
        this.nextSpecIndex = (i + 1);
        ConnectionSpec localConnectionSpec = (ConnectionSpec)localList.get(i);
        boolean bool = shouldSendTlsFallbackIndicator(localConnectionSpec);
        Route localRoute = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, localConnectionSpec, bool);
        this.routeDatabase.failed(localRoute);
      }
  }

  public boolean hasNext()
  {
    return (hasNextConnectionSpec()) || (hasNextInetSocketAddress()) || (hasNextProxy()) || (hasNextPostponed());
  }

  public Route next()
    throws IOException
  {
    Route localRoute;
    if (!hasNextConnectionSpec())
      if (!hasNextInetSocketAddress())
        if (!hasNextProxy())
        {
          if (!hasNextPostponed())
            throw new NoSuchElementException();
          localRoute = nextPostponed();
        }
    do
    {
      return localRoute;
      this.lastProxy = nextProxy();
      this.lastInetSocketAddress = nextInetSocketAddress();
      this.lastSpec = nextConnectionSpec();
      boolean bool = shouldSendTlsFallbackIndicator(this.lastSpec);
      localRoute = new Route(this.address, this.lastProxy, this.lastInetSocketAddress, this.lastSpec, bool);
    }
    while (!this.routeDatabase.shouldPostpone(localRoute));
    this.postponedRoutes.add(localRoute);
    return next();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.RouteSelector
 * JD-Core Version:    0.6.2
 */