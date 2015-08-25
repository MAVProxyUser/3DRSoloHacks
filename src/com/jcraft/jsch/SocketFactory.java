package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract interface SocketFactory
{
  public abstract Socket createSocket(String paramString, int paramInt)
    throws IOException, UnknownHostException;

  public abstract InputStream getInputStream(Socket paramSocket)
    throws IOException;

  public abstract OutputStream getOutputStream(Socket paramSocket)
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.SocketFactory
 * JD-Core Version:    0.6.2
 */