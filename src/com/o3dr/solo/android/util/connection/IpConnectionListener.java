package com.o3dr.solo.android.util.connection;

import java.nio.ByteBuffer;

public abstract interface IpConnectionListener
{
  public abstract void onIpConnected();

  public abstract void onIpDisconnected();

  public abstract void onPacketReceived(ByteBuffer paramByteBuffer);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.connection.IpConnectionListener
 * JD-Core Version:    0.6.2
 */