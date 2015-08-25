package com.squareup.okhttp;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Proxy.Type;

public final class Route
{
  final Address address;
  final ConnectionSpec connectionSpec;
  final InetSocketAddress inetSocketAddress;
  final Proxy proxy;
  final boolean shouldSendTlsFallbackIndicator;

  public Route(Address paramAddress, Proxy paramProxy, InetSocketAddress paramInetSocketAddress, ConnectionSpec paramConnectionSpec)
  {
    this(paramAddress, paramProxy, paramInetSocketAddress, paramConnectionSpec, false);
  }

  public Route(Address paramAddress, Proxy paramProxy, InetSocketAddress paramInetSocketAddress, ConnectionSpec paramConnectionSpec, boolean paramBoolean)
  {
    if (paramAddress == null)
      throw new NullPointerException("address == null");
    if (paramProxy == null)
      throw new NullPointerException("proxy == null");
    if (paramInetSocketAddress == null)
      throw new NullPointerException("inetSocketAddress == null");
    if (paramConnectionSpec == null)
      throw new NullPointerException("connectionConfiguration == null");
    this.address = paramAddress;
    this.proxy = paramProxy;
    this.inetSocketAddress = paramInetSocketAddress;
    this.connectionSpec = paramConnectionSpec;
    this.shouldSendTlsFallbackIndicator = paramBoolean;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Route;
    boolean bool2 = false;
    if (bool1)
    {
      Route localRoute = (Route)paramObject;
      boolean bool3 = this.address.equals(localRoute.address);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.proxy.equals(localRoute.proxy);
        bool2 = false;
        if (bool4)
        {
          boolean bool5 = this.inetSocketAddress.equals(localRoute.inetSocketAddress);
          bool2 = false;
          if (bool5)
          {
            boolean bool6 = this.connectionSpec.equals(localRoute.connectionSpec);
            bool2 = false;
            if (bool6)
            {
              boolean bool7 = this.shouldSendTlsFallbackIndicator;
              boolean bool8 = localRoute.shouldSendTlsFallbackIndicator;
              bool2 = false;
              if (bool7 == bool8)
                bool2 = true;
            }
          }
        }
      }
    }
    return bool2;
  }

  public Address getAddress()
  {
    return this.address;
  }

  public ConnectionSpec getConnectionSpec()
  {
    return this.connectionSpec;
  }

  public Proxy getProxy()
  {
    return this.proxy;
  }

  public boolean getShouldSendTlsFallbackIndicator()
  {
    return this.shouldSendTlsFallbackIndicator;
  }

  public InetSocketAddress getSocketAddress()
  {
    return this.inetSocketAddress;
  }

  public int hashCode()
  {
    int i = 31 * (31 * (31 * (31 * (527 + this.address.hashCode()) + this.proxy.hashCode()) + this.inetSocketAddress.hashCode()) + this.connectionSpec.hashCode());
    if (this.shouldSendTlsFallbackIndicator);
    for (int j = 1; ; j = 0)
      return i + j;
  }

  public boolean requiresTunnel()
  {
    return (this.address.sslSocketFactory != null) && (this.proxy.type() == Proxy.Type.HTTP);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Route
 * JD-Core Version:    0.6.2
 */