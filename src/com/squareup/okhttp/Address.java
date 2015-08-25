package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.net.Proxy;
import java.net.ProxySelector;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

public final class Address
{
  final Authenticator authenticator;
  final CertificatePinner certificatePinner;
  final List<ConnectionSpec> connectionSpecs;
  final HostnameVerifier hostnameVerifier;
  final List<Protocol> protocols;
  final Proxy proxy;
  final ProxySelector proxySelector;
  final SocketFactory socketFactory;
  final SSLSocketFactory sslSocketFactory;
  final String uriHost;
  final int uriPort;

  public Address(String paramString, int paramInt, SocketFactory paramSocketFactory, SSLSocketFactory paramSSLSocketFactory, HostnameVerifier paramHostnameVerifier, CertificatePinner paramCertificatePinner, Authenticator paramAuthenticator, Proxy paramProxy, List<Protocol> paramList, List<ConnectionSpec> paramList1, ProxySelector paramProxySelector)
  {
    if (paramString == null)
      throw new NullPointerException("uriHost == null");
    if (paramInt <= 0)
      throw new IllegalArgumentException("uriPort <= 0: " + paramInt);
    if (paramAuthenticator == null)
      throw new IllegalArgumentException("authenticator == null");
    if (paramList == null)
      throw new IllegalArgumentException("protocols == null");
    if (paramProxySelector == null)
      throw new IllegalArgumentException("proxySelector == null");
    this.proxy = paramProxy;
    this.uriHost = paramString;
    this.uriPort = paramInt;
    this.socketFactory = paramSocketFactory;
    this.sslSocketFactory = paramSSLSocketFactory;
    this.hostnameVerifier = paramHostnameVerifier;
    this.certificatePinner = paramCertificatePinner;
    this.authenticator = paramAuthenticator;
    this.protocols = Util.immutableList(paramList);
    this.connectionSpecs = Util.immutableList(paramList1);
    this.proxySelector = paramProxySelector;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Address;
    boolean bool2 = false;
    if (bool1)
    {
      Address localAddress = (Address)paramObject;
      boolean bool3 = Util.equal(this.proxy, localAddress.proxy);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.uriHost.equals(localAddress.uriHost);
        bool2 = false;
        if (bool4)
        {
          int i = this.uriPort;
          int j = localAddress.uriPort;
          bool2 = false;
          if (i == j)
          {
            boolean bool5 = Util.equal(this.sslSocketFactory, localAddress.sslSocketFactory);
            bool2 = false;
            if (bool5)
            {
              boolean bool6 = Util.equal(this.hostnameVerifier, localAddress.hostnameVerifier);
              bool2 = false;
              if (bool6)
              {
                boolean bool7 = Util.equal(this.certificatePinner, localAddress.certificatePinner);
                bool2 = false;
                if (bool7)
                {
                  boolean bool8 = Util.equal(this.authenticator, localAddress.authenticator);
                  bool2 = false;
                  if (bool8)
                  {
                    boolean bool9 = Util.equal(this.protocols, localAddress.protocols);
                    bool2 = false;
                    if (bool9)
                    {
                      boolean bool10 = Util.equal(this.connectionSpecs, localAddress.connectionSpecs);
                      bool2 = false;
                      if (bool10)
                      {
                        boolean bool11 = Util.equal(this.proxySelector, localAddress.proxySelector);
                        bool2 = false;
                        if (bool11)
                          bool2 = true;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    return bool2;
  }

  public Authenticator getAuthenticator()
  {
    return this.authenticator;
  }

  public List<ConnectionSpec> getConnectionSpecs()
  {
    return this.connectionSpecs;
  }

  public HostnameVerifier getHostnameVerifier()
  {
    return this.hostnameVerifier;
  }

  public List<Protocol> getProtocols()
  {
    return this.protocols;
  }

  public Proxy getProxy()
  {
    return this.proxy;
  }

  public ProxySelector getProxySelector()
  {
    return this.proxySelector;
  }

  public SocketFactory getSocketFactory()
  {
    return this.socketFactory;
  }

  public SSLSocketFactory getSslSocketFactory()
  {
    return this.sslSocketFactory;
  }

  public String getUriHost()
  {
    return this.uriHost;
  }

  public int getUriPort()
  {
    return this.uriPort;
  }

  public int hashCode()
  {
    int i;
    int k;
    label58: int m;
    if (this.proxy != null)
    {
      i = this.proxy.hashCode();
      int j = 31 * (31 * (31 * (i + 527) + this.uriHost.hashCode()) + this.uriPort);
      if (this.sslSocketFactory == null)
        break label174;
      k = this.sslSocketFactory.hashCode();
      m = 31 * (j + k);
      if (this.hostnameVerifier == null)
        break label179;
    }
    label174: label179: for (int n = this.hostnameVerifier.hashCode(); ; n = 0)
    {
      int i1 = 31 * (m + n);
      CertificatePinner localCertificatePinner = this.certificatePinner;
      int i2 = 0;
      if (localCertificatePinner != null)
        i2 = this.certificatePinner.hashCode();
      return 31 * (31 * (31 * (31 * (i1 + i2) + this.authenticator.hashCode()) + this.protocols.hashCode()) + this.connectionSpecs.hashCode()) + this.proxySelector.hashCode();
      i = 0;
      break;
      k = 0;
      break label58;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Address
 * JD-Core Version:    0.6.2
 */