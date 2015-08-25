package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;

public abstract interface ForwardedTCPIPDaemon extends Runnable
{
  public abstract void setArg(Object[] paramArrayOfObject);

  public abstract void setChannel(ChannelForwardedTCPIP paramChannelForwardedTCPIP, InputStream paramInputStream, OutputStream paramOutputStream);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ForwardedTCPIPDaemon
 * JD-Core Version:    0.6.2
 */