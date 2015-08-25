package com.jcraft.jsch;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;

class ChannelSession extends Channel
{
  private static byte[] _session = Util.str2byte("session");
  protected boolean agent_forwarding = false;
  protected Hashtable env = null;
  protected boolean pty = false;
  protected int tcol = 80;
  protected byte[] terminal_mode = null;
  protected int thp = 480;
  protected int trow = 24;
  protected String ttype = "vt100";
  protected int twp = 640;
  protected boolean xforwading = false;

  ChannelSession()
  {
    this.type = _session;
    this.io = new IO();
  }

  private Hashtable getEnv()
  {
    if (this.env == null)
      this.env = new Hashtable();
    return this.env;
  }

  private byte[] toByteArray(Object paramObject)
  {
    if ((paramObject instanceof String))
      return Util.str2byte((String)paramObject);
    return (byte[])paramObject;
  }

  public void run()
  {
    Buffer localBuffer = new Buffer(this.rmpsize);
    Packet localPacket = new Packet(localBuffer);
    try
    {
      while ((isConnected()) && (this.thread != null) && (this.io != null) && (this.io.in != null))
      {
        i = this.io.in.read(localBuffer.buffer, 14, -84 + (-14 + localBuffer.buffer.length));
        if (i != 0)
        {
          if (i != -1)
            break label124;
          eof();
        }
      }
      localThread = this.thread;
      if (localThread == null);
    }
    catch (Exception localException)
    {
      Thread localThread;
      try
      {
        while (true)
        {
          int i;
          localThread.notifyAll();
          this.thread = null;
          return;
          label124: if (!this.close)
          {
            localPacket.reset();
            localBuffer.putByte((byte)94);
            localBuffer.putInt(this.recipient);
            localBuffer.putInt(i);
            localBuffer.skip(i);
            getSession().write(localPacket, this, i);
            continue;
            localException = localException;
          }
        }
      }
      finally
      {
      }
    }
  }

  protected void sendRequests()
    throws Exception
  {
    Session localSession = getSession();
    if (this.agent_forwarding)
      new RequestAgentForwarding().request(localSession, this);
    if (this.xforwading)
      new RequestX11().request(localSession, this);
    if (this.pty)
    {
      RequestPtyReq localRequestPtyReq = new RequestPtyReq();
      ((RequestPtyReq)localRequestPtyReq).setTType(this.ttype);
      ((RequestPtyReq)localRequestPtyReq).setTSize(this.tcol, this.trow, this.twp, this.thp);
      if (this.terminal_mode != null)
        ((RequestPtyReq)localRequestPtyReq).setTerminalMode(this.terminal_mode);
      localRequestPtyReq.request(localSession, this);
    }
    if (this.env != null)
    {
      Enumeration localEnumeration = this.env.keys();
      while (localEnumeration.hasMoreElements())
      {
        Object localObject1 = localEnumeration.nextElement();
        Object localObject2 = this.env.get(localObject1);
        RequestEnv localRequestEnv = new RequestEnv();
        ((RequestEnv)localRequestEnv).setEnv(toByteArray(localObject1), toByteArray(localObject2));
        localRequestEnv.request(localSession, this);
      }
    }
  }

  public void setAgentForwarding(boolean paramBoolean)
  {
    this.agent_forwarding = paramBoolean;
  }

  public void setEnv(String paramString1, String paramString2)
  {
    setEnv(Util.str2byte(paramString1), Util.str2byte(paramString2));
  }

  public void setEnv(Hashtable paramHashtable)
  {
    try
    {
      this.env = paramHashtable;
      return;
    }
    finally
    {
    }
  }

  public void setEnv(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    try
    {
      getEnv().put(paramArrayOfByte1, paramArrayOfByte2);
      return;
    }
    finally
    {
    }
  }

  public void setPty(boolean paramBoolean)
  {
    this.pty = paramBoolean;
  }

  public void setPtySize(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setPtyType(this.ttype, paramInt1, paramInt2, paramInt3, paramInt4);
    if ((!this.pty) || (!isConnected()))
      return;
    try
    {
      RequestWindowChange localRequestWindowChange = new RequestWindowChange();
      localRequestWindowChange.setSize(paramInt1, paramInt2, paramInt3, paramInt4);
      localRequestWindowChange.request(getSession(), this);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void setPtyType(String paramString)
  {
    setPtyType(paramString, 80, 24, 640, 480);
  }

  public void setPtyType(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.ttype = paramString;
    this.tcol = paramInt1;
    this.trow = paramInt2;
    this.twp = paramInt3;
    this.thp = paramInt4;
  }

  public void setTerminalMode(byte[] paramArrayOfByte)
  {
    this.terminal_mode = paramArrayOfByte;
  }

  public void setXForwarding(boolean paramBoolean)
  {
    this.xforwading = paramBoolean;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.ChannelSession
 * JD-Core Version:    0.6.2
 */