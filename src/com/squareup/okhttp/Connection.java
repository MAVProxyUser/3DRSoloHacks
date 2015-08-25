package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.RouteDatabase;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpConnection;
import com.squareup.okhttp.internal.http.HttpEngine;
import com.squareup.okhttp.internal.http.HttpTransport;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.SpdyTransport;
import com.squareup.okhttp.internal.http.Transport;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyConnection.Builder;
import com.squareup.okhttp.internal.tls.OkHostnameVerifier;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.Socket;
import java.net.URL;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import okio.Source;

public final class Connection
{
  private boolean connected = false;
  private Handshake handshake;
  private HttpConnection httpConnection;
  private long idleStartTimeNs;
  private Object owner;
  private final ConnectionPool pool;
  private Protocol protocol = Protocol.HTTP_1_1;
  private int recycleCount;
  private final Route route;
  private Socket socket;
  private SpdyConnection spdyConnection;

  public Connection(ConnectionPool paramConnectionPool, Route paramRoute)
  {
    this.pool = paramConnectionPool;
    this.route = paramRoute;
  }

  private void makeTunnel(Request paramRequest, int paramInt1, int paramInt2)
    throws IOException
  {
    HttpConnection localHttpConnection = new HttpConnection(this.pool, this, this.socket);
    localHttpConnection.setTimeouts(paramInt1, paramInt2);
    URL localURL = paramRequest.url();
    String str = "CONNECT " + localURL.getHost() + ":" + localURL.getPort() + " HTTP/1.1";
    do
    {
      localHttpConnection.writeRequest(paramRequest.headers(), str);
      localHttpConnection.flush();
      Response localResponse = localHttpConnection.readResponse().request(paramRequest).build();
      long l = OkHeaders.contentLength(localResponse);
      if (l == -1L)
        l = 0L;
      Source localSource = localHttpConnection.newFixedLengthSource(l);
      Util.skipAll(localSource, 2147483647, TimeUnit.MILLISECONDS);
      localSource.close();
      switch (localResponse.code())
      {
      default:
        throw new IOException("Unexpected response code for CONNECT: " + localResponse.code());
      case 200:
        if (localHttpConnection.bufferSize() <= 0L)
          break;
        throw new IOException("TLS tunnel buffered too many bytes!");
      case 407:
        paramRequest = OkHeaders.processAuthHeader(this.route.address.authenticator, localResponse, this.route.proxy);
      }
    }
    while (paramRequest != null);
    throw new IOException("Failed to authenticate with proxy");
  }

  private Request tunnelRequest(Request paramRequest)
    throws IOException
  {
    if (!this.route.requiresTunnel())
      return null;
    String str1 = paramRequest.url().getHost();
    int i = Util.getEffectivePort(paramRequest.url());
    if (i == Util.getDefaultPort("https"));
    for (String str2 = str1; ; str2 = str1 + ":" + i)
    {
      Request.Builder localBuilder = new Request.Builder().url(new URL("https", str1, i, "/")).header("Host", str2).header("Proxy-Connection", "Keep-Alive");
      String str3 = paramRequest.header("User-Agent");
      if (str3 != null)
        localBuilder.header("User-Agent", str3);
      String str4 = paramRequest.header("Proxy-Authorization");
      if (str4 != null)
        localBuilder.header("Proxy-Authorization", str4);
      return localBuilder.build();
    }
  }

  private void upgradeToTls(Request paramRequest, int paramInt1, int paramInt2)
    throws IOException
  {
    Platform localPlatform = Platform.get();
    if (paramRequest != null)
      makeTunnel(paramRequest, paramInt1, paramInt2);
    this.socket = this.route.address.sslSocketFactory.createSocket(this.socket, this.route.address.uriHost, this.route.address.uriPort, true);
    SSLSocket localSSLSocket = (SSLSocket)this.socket;
    this.route.connectionSpec.apply(localSSLSocket, this.route);
    try
    {
      localSSLSocket.startHandshake();
      if (this.route.connectionSpec.supportsTlsExtensions())
      {
        String str = localPlatform.getSelectedProtocol(localSSLSocket);
        if (str != null)
          this.protocol = Protocol.get(str);
      }
      localPlatform.afterHandshake(localSSLSocket);
      this.handshake = Handshake.get(localSSLSocket.getSession());
      if (!this.route.address.hostnameVerifier.verify(this.route.address.uriHost, localSSLSocket.getSession()))
      {
        X509Certificate localX509Certificate = (X509Certificate)localSSLSocket.getSession().getPeerCertificates()[0];
        throw new SSLPeerUnverifiedException("Hostname " + this.route.address.uriHost + " not verified:" + "\n    certificate: " + CertificatePinner.pin(localX509Certificate) + "\n    DN: " + localX509Certificate.getSubjectDN().getName() + "\n    subjectAltNames: " + OkHostnameVerifier.allSubjectAltNames(localX509Certificate));
      }
    }
    finally
    {
      localPlatform.afterHandshake(localSSLSocket);
    }
    this.route.address.certificatePinner.check(this.route.address.uriHost, this.handshake.peerCertificates());
    if ((this.protocol == Protocol.SPDY_3) || (this.protocol == Protocol.HTTP_2))
    {
      localSSLSocket.setSoTimeout(0);
      this.spdyConnection = new SpdyConnection.Builder(this.route.address.getUriHost(), true, this.socket).protocol(this.protocol).build();
      this.spdyConnection.sendConnectionPreface();
      return;
    }
    this.httpConnection = new HttpConnection(this.pool, this, this.socket);
  }

  boolean clearOwner()
  {
    synchronized (this.pool)
    {
      if (this.owner == null)
        return false;
      this.owner = null;
      return true;
    }
  }

  void closeIfOwnedBy(Object paramObject)
    throws IOException
  {
    if (isSpdy())
      throw new IllegalStateException();
    synchronized (this.pool)
    {
      if (this.owner != paramObject)
        return;
      this.owner = null;
      this.socket.close();
      return;
    }
  }

  void connect(int paramInt1, int paramInt2, int paramInt3, Request paramRequest)
    throws IOException
  {
    if (this.connected)
      throw new IllegalStateException("already connected");
    if ((this.route.proxy.type() == Proxy.Type.DIRECT) || (this.route.proxy.type() == Proxy.Type.HTTP))
    {
      this.socket = this.route.address.socketFactory.createSocket();
      this.socket.setSoTimeout(paramInt2);
      Platform.get().connectSocket(this.socket, this.route.inetSocketAddress, paramInt1);
      if (this.route.address.sslSocketFactory == null)
        break label141;
      upgradeToTls(paramRequest, paramInt2, paramInt3);
    }
    while (true)
    {
      this.connected = true;
      return;
      this.socket = new Socket(this.route.proxy);
      break;
      label141: this.httpConnection = new HttpConnection(this.pool, this, this.socket);
    }
  }

  void connectAndSetOwner(OkHttpClient paramOkHttpClient, Object paramObject, Request paramRequest)
    throws IOException
  {
    setOwner(paramObject);
    if (!isConnected())
    {
      Request localRequest = tunnelRequest(paramRequest);
      connect(paramOkHttpClient.getConnectTimeout(), paramOkHttpClient.getReadTimeout(), paramOkHttpClient.getWriteTimeout(), localRequest);
      if (isSpdy())
        paramOkHttpClient.getConnectionPool().share(this);
      paramOkHttpClient.routeDatabase().connected(getRoute());
    }
    setTimeouts(paramOkHttpClient.getReadTimeout(), paramOkHttpClient.getWriteTimeout());
  }

  public Handshake getHandshake()
  {
    return this.handshake;
  }

  long getIdleStartTimeNs()
  {
    if (this.spdyConnection == null)
      return this.idleStartTimeNs;
    return this.spdyConnection.getIdleStartTimeNs();
  }

  Object getOwner()
  {
    synchronized (this.pool)
    {
      Object localObject2 = this.owner;
      return localObject2;
    }
  }

  public Protocol getProtocol()
  {
    return this.protocol;
  }

  public Route getRoute()
  {
    return this.route;
  }

  public Socket getSocket()
  {
    return this.socket;
  }

  void incrementRecycleCount()
  {
    this.recycleCount = (1 + this.recycleCount);
  }

  boolean isAlive()
  {
    return (!this.socket.isClosed()) && (!this.socket.isInputShutdown()) && (!this.socket.isOutputShutdown());
  }

  boolean isConnected()
  {
    return this.connected;
  }

  boolean isIdle()
  {
    return (this.spdyConnection == null) || (this.spdyConnection.isIdle());
  }

  boolean isReadable()
  {
    if (this.httpConnection != null)
      return this.httpConnection.isReadable();
    return true;
  }

  boolean isSpdy()
  {
    return this.spdyConnection != null;
  }

  Transport newTransport(HttpEngine paramHttpEngine)
    throws IOException
  {
    if (this.spdyConnection != null)
      return new SpdyTransport(paramHttpEngine, this.spdyConnection);
    return new HttpTransport(paramHttpEngine, this.httpConnection);
  }

  int recycleCount()
  {
    return this.recycleCount;
  }

  void resetIdleStartTime()
  {
    if (this.spdyConnection != null)
      throw new IllegalStateException("spdyConnection != null");
    this.idleStartTimeNs = System.nanoTime();
  }

  void setOwner(Object paramObject)
  {
    if (isSpdy())
      return;
    synchronized (this.pool)
    {
      if (this.owner != null)
        throw new IllegalStateException("Connection already has an owner!");
    }
    this.owner = paramObject;
  }

  void setProtocol(Protocol paramProtocol)
  {
    if (paramProtocol == null)
      throw new IllegalArgumentException("protocol == null");
    this.protocol = paramProtocol;
  }

  void setTimeouts(int paramInt1, int paramInt2)
    throws IOException
  {
    if (!this.connected)
      throw new IllegalStateException("setTimeouts - not connected");
    if (this.httpConnection != null)
    {
      this.socket.setSoTimeout(paramInt1);
      this.httpConnection.setTimeouts(paramInt1, paramInt2);
    }
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Connection{").append(this.route.address.uriHost).append(":").append(this.route.address.uriPort).append(", proxy=").append(this.route.proxy).append(" hostAddress=").append(this.route.inetSocketAddress.getAddress().getHostAddress()).append(" cipherSuite=");
    if (this.handshake != null);
    for (String str = this.handshake.cipherSuite(); ; str = "none")
      return str + " protocol=" + this.protocol + '}';
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Connection
 * JD-Core Version:    0.6.2
 */