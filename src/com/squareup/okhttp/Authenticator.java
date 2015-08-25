package com.squareup.okhttp;

import java.io.IOException;
import java.net.Proxy;

public abstract interface Authenticator
{
  public abstract Request authenticate(Proxy paramProxy, Response paramResponse)
    throws IOException;

  public abstract Request authenticateProxy(Proxy paramProxy, Response paramResponse)
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Authenticator
 * JD-Core Version:    0.6.2
 */