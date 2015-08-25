package com.jcraft.jsch;

import java.io.InputStream;
import java.io.OutputStream;

public class ChannelDirectTCPIP extends Channel
{
  private static final int LOCAL_MAXIMUM_PACKET_SIZE = 16384;
  private static final int LOCAL_WINDOW_SIZE_MAX = 131072;
  private static final byte[] _type = Util.str2byte("direct-tcpip");
  String host;
  String originator_IP_address = "127.0.0.1";
  int originator_port = 0;
  int port;

  ChannelDirectTCPIP()
  {
    this.type = _type;
    setLocalWindowSizeMax(131072);
    setLocalWindowSize(131072);
    setLocalPacketSize(16384);
  }

  public void connect(int paramInt)
    throws JSchException
  {
    this.connectTimeout = paramInt;
    try
    {
      localSession = getSession();
      if (!localSession.isConnected())
        throw new JSchException("session is down");
    }
    catch (Exception localException)
    {
      Session localSession;
      this.io.close();
      this.io = null;
      Channel.del(this);
      if ((localException instanceof JSchException))
      {
        throw ((JSchException)localException);
        if (this.io.in != null)
        {
          this.thread = new Thread(this);
          this.thread.setName("DirectTCPIP thread " + localSession.getHost());
          if (localSession.daemon_thread)
            this.thread.setDaemon(localSession.daemon_thread);
          this.thread.start();
          return;
        }
        sendChannelOpen();
      }
    }
  }

  protected Packet genChannelOpenPacket()
  {
    Buffer localBuffer = new Buffer(84 + (50 + this.host.length() + this.originator_IP_address.length()));
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)90);
    localBuffer.putString(this.type);
    localBuffer.putInt(this.id);
    localBuffer.putInt(this.lwsize);
    localBuffer.putInt(this.lmpsize);
    localBuffer.putString(Util.str2byte(this.host));
    localBuffer.putInt(this.port);
    localBuffer.putString(Util.str2byte(this.originator_IP_address));
    localBuffer.putInt(this.originator_port);
    return localPacket;
  }

  void init()
  {
    this.io = new IO();
  }

  public void run()
  {
    while (true)
    {
      Packet localPacket;
      Session localSession;
      int i;
      try
      {
        sendChannelOpen();
        Buffer localBuffer = new Buffer(this.rmpsize);
        localPacket = new Packet(localBuffer);
        localSession = getSession();
        if ((isConnected()) && (this.thread != null) && (this.io != null) && (this.io.in != null))
        {
          i = this.io.in.read(localBuffer.buffer, 14, -84 + (-14 + localBuffer.buffer.length));
          if (i <= 0)
            eof();
        }
        else
        {
          eof();
          disconnect();
          return;
        }
        localPacket.reset();
        localBuffer.putByte((byte)94);
        localBuffer.putInt(this.recipient);
        localBuffer.putInt(i);
        localBuffer.skip(i);
        try
        {
          if (this.close)
            continue;
        }
        finally
        {
        }
      }
      catch (Exception localException)
      {
        if (!this.connected)
          this.connected = true;
        disconnect();
        return;
      }
      localSession.write(localPacket, this, i);
    }
  }

  public void setHost(String paramString)
  {
    this.host = paramString;
  }

  public void setInputStream(InputStream paramInputStream)
  {
    this.io.setInputStream(paramInputStream);
  }

  public void setOrgIPAddress(String paramString)
  {
    this.originator_IP_address = paramString;
  }

  public void setOrgPort(int paramInt)
  {
    this.originator_port = paramInt;
  }

  public void setOutputStream(OutputStream paramOutputStream)
  {
    this.io.setOutputStream(paramOutputStream);
  }

  public void setPort(int paramInt)
  {
    this.port = paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelDirectTCPIP
 * JD-Core Version:    0.6.2
 */