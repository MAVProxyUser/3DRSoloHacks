package com.jcraft.jsch;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;

public abstract interface ServerSocketFactory
{
  public abstract ServerSocket createServerSocket(int paramInt1, int paramInt2, InetAddress paramInetAddress)
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ServerSocketFactory
 * JD-Core Version:    0.6.2
 */