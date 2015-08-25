package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public abstract interface Proxy
{
  public abstract void close();

  public abstract void connect(SocketFactory paramSocketFactory, String paramString, int paramInt1, int paramInt2)
    throws Exception;

  public abstract InputStream getInputStream();

  public abstract OutputStream getOutputStream();

  public abstract Socket getSocket();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Proxy
 * JD-Core Version:    0.6.2
 */