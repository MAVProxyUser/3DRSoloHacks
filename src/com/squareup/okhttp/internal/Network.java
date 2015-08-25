package com.squareup.okhttp.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract interface Network
{
  public static final Network DEFAULT = new Network()
  {
    public InetAddress[] resolveInetAddresses(String paramAnonymousString)
      throws UnknownHostException
    {
      if (paramAnonymousString == null)
        throw new UnknownHostException("host == null");
      return InetAddress.getAllByName(paramAnonymousString);
    }
  };

  public abstract InetAddress[] resolveInetAddresses(String paramString)
    throws UnknownHostException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.Network
 * JD-Core Version:    0.6.2
 */