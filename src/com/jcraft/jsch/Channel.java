package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Vector;

public abstract class Channel
  implements Runnable
{
  static final int SSH_MSG_CHANNEL_OPEN_CONFIRMATION = 91;
  static final int SSH_MSG_CHANNEL_OPEN_FAILURE = 92;
  static final int SSH_MSG_CHANNEL_WINDOW_ADJUST = 93;
  static final int SSH_OPEN_ADMINISTRATIVELY_PROHIBITED = 1;
  static final int SSH_OPEN_CONNECT_FAILED = 2;
  static final int SSH_OPEN_RESOURCE_SHORTAGE = 4;
  static final int SSH_OPEN_UNKNOWN_CHANNEL_TYPE = 3;
  static int index = 0;
  private static Vector pool = new Vector();
  volatile boolean close = false;
  volatile int connectTimeout = 0;
  volatile boolean connected = false;
  volatile boolean eof_local = false;
  volatile boolean eof_remote = false;
  volatile int exitstatus = -1;
  int id;
  IO io = null;
  volatile int lmpsize = 16384;
  volatile int lwsize = this.lwsize_max;
  volatile int lwsize_max = 1048576;
  int notifyme = 0;
  volatile boolean open_confirmation = false;
  volatile int recipient = -1;
  volatile int reply = 0;
  volatile int rmpsize = 0;
  volatile long rwsize = 0L;
  private Session session;
  Thread thread = null;
  protected byte[] type = Util.str2byte("foo");

  Channel()
  {
    synchronized (pool)
    {
      int i = index;
      index = i + 1;
      this.id = i;
      pool.addElement(this);
      return;
    }
  }

  static void del(Channel paramChannel)
  {
    synchronized (pool)
    {
      pool.removeElement(paramChannel);
      return;
    }
  }

  static void disconnect(Session paramSession)
  {
    Channel[] arrayOfChannel;
    int i;
    int j;
    synchronized (pool)
    {
      arrayOfChannel = new Channel[pool.size()];
      i = 0;
      j = 0;
    }
    try
    {
      int k = pool.size();
      if (i >= k);
    }
    finally
    {
      try
      {
        localChannel = (Channel)pool.elementAt(i);
        Session localSession = localChannel.session;
        if (localSession == paramSession)
          n = j + 1;
      }
      catch (Exception localException1)
      {
        while (true)
        {
          try
          {
            Channel localChannel;
            arrayOfChannel[j] = localChannel;
            i++;
            j = n;
            continue;
            int m = 0;
            if (m < j)
            {
              arrayOfChannel[m].disconnect();
              m++;
              continue;
              localObject1 = finally;
              throw localObject1;
            }
            return;
            localObject2 = finally;
            continue;
            localException1 = localException1;
            n = j;
            continue;
          }
          catch (Exception localException2)
          {
            continue;
          }
          int n = j;
        }
      }
    }
  }

  static Channel getChannel(int paramInt, Session paramSession)
  {
    Vector localVector = pool;
    for (int i = 0; ; i++)
      try
      {
        if (i < pool.size())
        {
          Channel localChannel = (Channel)pool.elementAt(i);
          if ((localChannel.id == paramInt) && (localChannel.session == paramSession))
            return localChannel;
        }
        else
        {
          return null;
        }
      }
      finally
      {
      }
  }

  static Channel getChannel(String paramString)
  {
    if (paramString.equals("session"))
      return new ChannelSession();
    if (paramString.equals("shell"))
      return new ChannelShell();
    if (paramString.equals("exec"))
      return new ChannelExec();
    if (paramString.equals("x11"))
      return new ChannelX11();
    if (paramString.equals("auth-agent@openssh.com"))
      return new ChannelAgentForwarding();
    if (paramString.equals("direct-tcpip"))
      return new ChannelDirectTCPIP();
    if (paramString.equals("forwarded-tcpip"))
      return new ChannelForwardedTCPIP();
    if (paramString.equals("sftp"))
      return new ChannelSftp();
    if (paramString.equals("subsystem"))
      return new ChannelSubsystem();
    return null;
  }

  void addRemoteWindowSize(long paramLong)
  {
    try
    {
      this.rwsize = (paramLong + this.rwsize);
      if (this.notifyme > 0)
        notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void close()
  {
    if (this.close);
    int i;
    do
    {
      return;
      this.close = true;
      this.eof_remote = true;
      this.eof_local = true;
      i = getRecipient();
    }
    while (i == -1);
    try
    {
      Buffer localBuffer = new Buffer(100);
      Packet localPacket = new Packet(localBuffer);
      localPacket.reset();
      localBuffer.putByte((byte)97);
      localBuffer.putInt(i);
      try
      {
        getSession().write(localPacket);
        return;
      }
      finally
      {
      }
    }
    catch (Exception localException)
    {
    }
  }

  public void connect()
    throws JSchException
  {
    connect(0);
  }

  public void connect(int paramInt)
    throws JSchException
  {
    this.connectTimeout = paramInt;
    try
    {
      sendChannelOpen();
      start();
      return;
    }
    catch (Exception localException)
    {
      this.connected = false;
      disconnect();
      if ((localException instanceof JSchException))
        throw ((JSchException)localException);
      throw new JSchException(localException.toString(), localException);
    }
  }

  // ERROR //
  public void disconnect()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 97	com/jcraft/jsch/Channel:connected	Z
    //   6: ifne +10 -> 16
    //   9: aload_0
    //   10: monitorexit
    //   11: aload_0
    //   12: invokestatic 245	com/jcraft/jsch/Channel:del	(Lcom/jcraft/jsch/Channel;)V
    //   15: return
    //   16: aload_0
    //   17: iconst_0
    //   18: putfield 97	com/jcraft/jsch/Channel:connected	Z
    //   21: aload_0
    //   22: monitorexit
    //   23: aload_0
    //   24: invokevirtual 247	com/jcraft/jsch/Channel:close	()V
    //   27: aload_0
    //   28: iconst_1
    //   29: putfield 91	com/jcraft/jsch/Channel:eof_local	Z
    //   32: aload_0
    //   33: iconst_1
    //   34: putfield 93	com/jcraft/jsch/Channel:eof_remote	Z
    //   37: aload_0
    //   38: aconst_null
    //   39: putfield 89	com/jcraft/jsch/Channel:thread	Ljava/lang/Thread;
    //   42: aload_0
    //   43: getfield 87	com/jcraft/jsch/Channel:io	Lcom/jcraft/jsch/IO;
    //   46: ifnull +10 -> 56
    //   49: aload_0
    //   50: getfield 87	com/jcraft/jsch/Channel:io	Lcom/jcraft/jsch/IO;
    //   53: invokevirtual 250	com/jcraft/jsch/IO:close	()V
    //   56: aload_0
    //   57: invokestatic 245	com/jcraft/jsch/Channel:del	(Lcom/jcraft/jsch/Channel;)V
    //   60: return
    //   61: astore_2
    //   62: aload_0
    //   63: monitorexit
    //   64: aload_2
    //   65: athrow
    //   66: astore_1
    //   67: aload_0
    //   68: invokestatic 245	com/jcraft/jsch/Channel:del	(Lcom/jcraft/jsch/Channel;)V
    //   71: aload_1
    //   72: athrow
    //   73: astore_3
    //   74: goto -18 -> 56
    //
    // Exception table:
    //   from	to	target	type
    //   2	11	61	finally
    //   16	23	61	finally
    //   62	64	61	finally
    //   0	2	66	finally
    //   23	42	66	finally
    //   42	56	66	finally
    //   64	66	66	finally
    //   42	56	73	java/lang/Exception
  }

  void eof()
  {
    if (this.eof_local);
    int i;
    do
    {
      return;
      this.eof_local = true;
      i = getRecipient();
    }
    while (i == -1);
    try
    {
      Buffer localBuffer = new Buffer(100);
      Packet localPacket = new Packet(localBuffer);
      localPacket.reset();
      localBuffer.putByte((byte)96);
      localBuffer.putInt(i);
      try
      {
        if (!this.close)
          getSession().write(localPacket);
        return;
      }
      finally
      {
      }
    }
    catch (Exception localException)
    {
    }
  }

  void eof_remote()
  {
    this.eof_remote = true;
    try
    {
      this.io.out_close();
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
    }
  }

  protected Packet genChannelOpenPacket()
  {
    Buffer localBuffer = new Buffer(100);
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)90);
    localBuffer.putString(this.type);
    localBuffer.putInt(this.id);
    localBuffer.putInt(this.lwsize);
    localBuffer.putInt(this.lmpsize);
    return localPacket;
  }

  void getData(Buffer paramBuffer)
  {
    setRecipient(paramBuffer.getInt());
    setRemoteWindowSize(paramBuffer.getUInt());
    setRemotePacketSize(paramBuffer.getInt());
  }

  public int getExitStatus()
  {
    return this.exitstatus;
  }

  public InputStream getExtInputStream()
    throws IOException
  {
    int i = 32768;
    try
    {
      int j = Integer.parseInt(getSession().getConfig("max_input_buffer_size"));
      i = j;
      label22: MyPipedInputStream localMyPipedInputStream = new MyPipedInputStream(32768, i);
      if (32768 < i);
      for (boolean bool = true; ; bool = false)
      {
        this.io.setExtOutputStream(new PassiveOutputStream(localMyPipedInputStream, bool), false);
        return localMyPipedInputStream;
      }
    }
    catch (Exception localException)
    {
      break label22;
    }
  }

  public int getId()
  {
    return this.id;
  }

  public InputStream getInputStream()
    throws IOException
  {
    int i = 32768;
    try
    {
      int j = Integer.parseInt(getSession().getConfig("max_input_buffer_size"));
      i = j;
      label22: MyPipedInputStream localMyPipedInputStream = new MyPipedInputStream(32768, i);
      if (32768 < i);
      for (boolean bool = true; ; bool = false)
      {
        this.io.setOutputStream(new PassiveOutputStream(localMyPipedInputStream, bool), false);
        return localMyPipedInputStream;
      }
    }
    catch (Exception localException)
    {
      break label22;
    }
  }

  public OutputStream getOutputStream()
    throws IOException
  {
    return new OutputStream()
    {
      byte[] b = new byte[1];
      private Buffer buffer = null;
      private boolean closed = false;
      private int dataLen = 0;
      private Packet packet = null;

      private void init()
        throws IOException
      {
        try
        {
          this.buffer = new Buffer(Channel.this.rmpsize);
          this.packet = new Packet(this.buffer);
          if (-84 + (-14 + this.buffer.buffer.length) <= 0)
          {
            this.buffer = null;
            this.packet = null;
            throw new IOException("failed to initialize the channel.");
          }
        }
        finally
        {
        }
      }

      public void close()
        throws IOException
      {
        if (this.packet == null);
        try
        {
          init();
          if (this.closed)
            return;
        }
        catch (IOException localIOException)
        {
          return;
        }
        if (this.dataLen > 0)
          flush();
        jdField_this.eof();
        this.closed = true;
      }

      public void flush()
        throws IOException
      {
        if (this.closed)
          throw new IOException("Already closed");
        if (this.dataLen == 0)
          return;
        this.packet.reset();
        this.buffer.putByte((byte)94);
        this.buffer.putInt(Channel.this.recipient);
        this.buffer.putInt(this.dataLen);
        this.buffer.skip(this.dataLen);
        try
        {
          int i = this.dataLen;
          this.dataLen = 0;
          synchronized (jdField_this)
          {
            if (!jdField_this.close)
              Channel.this.getSession().write(this.packet, jdField_this, i);
            return;
          }
        }
        catch (Exception localException)
        {
          close();
          throw new IOException(localException.toString());
        }
      }

      public void write(int paramAnonymousInt)
        throws IOException
      {
        this.b[0] = ((byte)paramAnonymousInt);
        write(this.b, 0, 1);
      }

      public void write(byte[] paramAnonymousArrayOfByte, int paramAnonymousInt1, int paramAnonymousInt2)
        throws IOException
      {
        if (this.packet == null)
          init();
        if (this.closed)
          throw new IOException("Already closed");
        byte[] arrayOfByte = this.buffer.buffer;
        int i = arrayOfByte.length;
        while (paramAnonymousInt2 > 0)
        {
          int j = paramAnonymousInt2;
          if (paramAnonymousInt2 > -84 + (i - (14 + this.dataLen)))
            j = -84 + (i - (14 + this.dataLen));
          if (j <= 0)
          {
            flush();
          }
          else
          {
            System.arraycopy(paramAnonymousArrayOfByte, paramAnonymousInt1, arrayOfByte, 14 + this.dataLen, j);
            this.dataLen = (j + this.dataLen);
            paramAnonymousInt1 += j;
            paramAnonymousInt2 -= j;
          }
        }
      }
    };
  }

  int getRecipient()
  {
    return this.recipient;
  }

  public Session getSession()
    throws JSchException
  {
    Session localSession = this.session;
    if (localSession == null)
      throw new JSchException("session is not available");
    return localSession;
  }

  void init()
    throws JSchException
  {
  }

  public boolean isClosed()
  {
    return this.close;
  }

  public boolean isConnected()
  {
    Session localSession = this.session;
    boolean bool1 = false;
    if (localSession != null)
    {
      boolean bool2 = localSession.isConnected();
      bool1 = false;
      if (bool2)
      {
        boolean bool3 = this.connected;
        bool1 = false;
        if (bool3)
          bool1 = true;
      }
    }
    return bool1;
  }

  public boolean isEOF()
  {
    return this.eof_remote;
  }

  public void run()
  {
  }

  // ERROR //
  protected void sendChannelOpen()
    throws Exception
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 219	com/jcraft/jsch/Channel:getSession	()Lcom/jcraft/jsch/Session;
    //   4: astore_1
    //   5: aload_1
    //   6: invokevirtual 334	com/jcraft/jsch/Session:isConnected	()Z
    //   9: ifne +14 -> 23
    //   12: new 228	com/jcraft/jsch/JSchException
    //   15: dup
    //   16: ldc_w 340
    //   19: invokespecial 328	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   22: athrow
    //   23: aload_1
    //   24: aload_0
    //   25: invokevirtual 342	com/jcraft/jsch/Channel:genChannelOpenPacket	()Lcom/jcraft/jsch/Packet;
    //   28: invokevirtual 225	com/jcraft/jsch/Session:write	(Lcom/jcraft/jsch/Packet;)V
    //   31: sipush 2000
    //   34: istore_2
    //   35: invokestatic 347	java/lang/System:currentTimeMillis	()J
    //   38: lstore_3
    //   39: aload_0
    //   40: getfield 105	com/jcraft/jsch/Channel:connectTimeout	I
    //   43: i2l
    //   44: lstore 5
    //   46: lload 5
    //   48: lconst_0
    //   49: lcmp
    //   50: ifeq +5 -> 55
    //   53: iconst_1
    //   54: istore_2
    //   55: aload_0
    //   56: monitorenter
    //   57: aload_0
    //   58: invokevirtual 195	com/jcraft/jsch/Channel:getRecipient	()I
    //   61: iconst_m1
    //   62: if_icmpne +99 -> 161
    //   65: aload_1
    //   66: invokevirtual 334	com/jcraft/jsch/Session:isConnected	()Z
    //   69: ifeq +92 -> 161
    //   72: iload_2
    //   73: ifle +88 -> 161
    //   76: lload 5
    //   78: lconst_0
    //   79: lcmp
    //   80: ifle +23 -> 103
    //   83: invokestatic 347	java/lang/System:currentTimeMillis	()J
    //   86: lstore 8
    //   88: lload 8
    //   90: lload_3
    //   91: lsub
    //   92: lload 5
    //   94: lcmp
    //   95: ifle +8 -> 103
    //   98: iconst_0
    //   99: istore_2
    //   100: goto -43 -> 57
    //   103: lload 5
    //   105: lconst_0
    //   106: lcmp
    //   107: ifne +123 -> 230
    //   110: ldc2_w 348
    //   113: lstore 10
    //   115: aload_0
    //   116: iconst_1
    //   117: putfield 107	com/jcraft/jsch/Channel:notifyme	I
    //   120: aload_0
    //   121: lload 10
    //   123: invokevirtual 352	java/lang/Object:wait	(J)V
    //   126: aload_0
    //   127: iconst_0
    //   128: putfield 107	com/jcraft/jsch/Channel:notifyme	I
    //   131: goto +93 -> 224
    //   134: astore 13
    //   136: aload_0
    //   137: iconst_0
    //   138: putfield 107	com/jcraft/jsch/Channel:notifyme	I
    //   141: goto +83 -> 224
    //   144: astore 7
    //   146: aload_0
    //   147: monitorexit
    //   148: aload 7
    //   150: athrow
    //   151: astore 12
    //   153: aload_0
    //   154: iconst_0
    //   155: putfield 107	com/jcraft/jsch/Channel:notifyme	I
    //   158: aload 12
    //   160: athrow
    //   161: aload_0
    //   162: monitorexit
    //   163: aload_1
    //   164: invokevirtual 334	com/jcraft/jsch/Session:isConnected	()Z
    //   167: ifne +14 -> 181
    //   170: new 228	com/jcraft/jsch/JSchException
    //   173: dup
    //   174: ldc_w 340
    //   177: invokespecial 328	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   180: athrow
    //   181: aload_0
    //   182: invokevirtual 195	com/jcraft/jsch/Channel:getRecipient	()I
    //   185: iconst_m1
    //   186: if_icmpne +14 -> 200
    //   189: new 228	com/jcraft/jsch/JSchException
    //   192: dup
    //   193: ldc_w 354
    //   196: invokespecial 328	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   199: athrow
    //   200: aload_0
    //   201: getfield 99	com/jcraft/jsch/Channel:open_confirmation	Z
    //   204: ifne +14 -> 218
    //   207: new 228	com/jcraft/jsch/JSchException
    //   210: dup
    //   211: ldc_w 354
    //   214: invokespecial 328	com/jcraft/jsch/JSchException:<init>	(Ljava/lang/String;)V
    //   217: athrow
    //   218: aload_0
    //   219: iconst_1
    //   220: putfield 97	com/jcraft/jsch/Channel:connected	Z
    //   223: return
    //   224: iinc 2 255
    //   227: goto -170 -> 57
    //   230: lload 5
    //   232: lstore 10
    //   234: goto -119 -> 115
    //
    // Exception table:
    //   from	to	target	type
    //   115	126	134	java/lang/InterruptedException
    //   57	72	144	finally
    //   83	88	144	finally
    //   126	131	144	finally
    //   136	141	144	finally
    //   146	148	144	finally
    //   153	161	144	finally
    //   161	163	144	finally
    //   115	126	151	finally
  }

  protected void sendOpenConfirmation()
    throws Exception
  {
    Buffer localBuffer = new Buffer(100);
    Packet localPacket = new Packet(localBuffer);
    localPacket.reset();
    localBuffer.putByte((byte)91);
    localBuffer.putInt(getRecipient());
    localBuffer.putInt(this.id);
    localBuffer.putInt(this.lwsize);
    localBuffer.putInt(this.lmpsize);
    getSession().write(localPacket);
  }

  protected void sendOpenFailure(int paramInt)
  {
    try
    {
      Buffer localBuffer = new Buffer(100);
      Packet localPacket = new Packet(localBuffer);
      localPacket.reset();
      localBuffer.putByte((byte)92);
      localBuffer.putInt(getRecipient());
      localBuffer.putInt(paramInt);
      localBuffer.putString(Util.str2byte("open failed"));
      localBuffer.putString(Util.empty);
      getSession().write(localPacket);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void sendSignal(String paramString)
    throws Exception
  {
    RequestSignal localRequestSignal = new RequestSignal();
    localRequestSignal.setSignal(paramString);
    localRequestSignal.request(getSession(), this);
  }

  void setExitStatus(int paramInt)
  {
    this.exitstatus = paramInt;
  }

  public void setExtOutputStream(OutputStream paramOutputStream)
  {
    this.io.setExtOutputStream(paramOutputStream, false);
  }

  public void setExtOutputStream(OutputStream paramOutputStream, boolean paramBoolean)
  {
    this.io.setExtOutputStream(paramOutputStream, paramBoolean);
  }

  public void setInputStream(InputStream paramInputStream)
  {
    this.io.setInputStream(paramInputStream, false);
  }

  public void setInputStream(InputStream paramInputStream, boolean paramBoolean)
  {
    this.io.setInputStream(paramInputStream, paramBoolean);
  }

  void setLocalPacketSize(int paramInt)
  {
    this.lmpsize = paramInt;
  }

  void setLocalWindowSize(int paramInt)
  {
    this.lwsize = paramInt;
  }

  void setLocalWindowSizeMax(int paramInt)
  {
    this.lwsize_max = paramInt;
  }

  public void setOutputStream(OutputStream paramOutputStream)
  {
    this.io.setOutputStream(paramOutputStream, false);
  }

  public void setOutputStream(OutputStream paramOutputStream, boolean paramBoolean)
  {
    this.io.setOutputStream(paramOutputStream, paramBoolean);
  }

  void setRecipient(int paramInt)
  {
    try
    {
      this.recipient = paramInt;
      if (this.notifyme > 0)
        notifyAll();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void setRemotePacketSize(int paramInt)
  {
    this.rmpsize = paramInt;
  }

  void setRemoteWindowSize(long paramLong)
  {
    try
    {
      this.rwsize = paramLong;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  void setSession(Session paramSession)
  {
    this.session = paramSession;
  }

  public void setXForwarding(boolean paramBoolean)
  {
  }

  public void start()
    throws JSchException
  {
  }

  void write(byte[] paramArrayOfByte)
    throws IOException
  {
    write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      this.io.put(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
    }
  }

  void write_ext(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    try
    {
      this.io.put_ext(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
    }
  }

  class MyPipedInputStream extends PipedInputStream
  {
    private int BUFFER_SIZE = 1024;
    private int max_buffer_size = this.BUFFER_SIZE;

    MyPipedInputStream()
      throws IOException
    {
    }

    MyPipedInputStream(int arg2)
      throws IOException
    {
      int i;
      this.buffer = new byte[i];
      this.BUFFER_SIZE = i;
      this.max_buffer_size = i;
    }

    MyPipedInputStream(int paramInt1, int arg3)
      throws IOException
    {
      this(paramInt1);
      int i;
      this.max_buffer_size = i;
    }

    MyPipedInputStream(PipedOutputStream arg2)
      throws IOException
    {
      super();
    }

    MyPipedInputStream(PipedOutputStream paramInt, int arg3)
      throws IOException
    {
      super();
      int i;
      this.buffer = new byte[i];
      this.BUFFER_SIZE = i;
    }

    private int freeSpace()
    {
      int k;
      if (this.out < this.in)
        k = this.buffer.length - this.in;
      int i;
      int j;
      do
      {
        return k;
        i = this.in;
        j = this.out;
        k = 0;
      }
      while (i >= j);
      if (this.in == -1)
        return this.buffer.length;
      return this.out - this.in;
    }

    void checkSpace(int paramInt)
      throws IOException
    {
      while (true)
      {
        int i;
        int j;
        int k;
        byte[] arrayOfByte;
        try
        {
          i = freeSpace();
          if (i >= paramInt)
            break label236;
          j = this.buffer.length - i;
          k = this.buffer.length;
          break label284;
          if (k > this.max_buffer_size)
            k = this.max_buffer_size;
          if (k - j < paramInt)
            return;
          arrayOfByte = new byte[k];
          if (this.out < this.in)
          {
            System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.buffer.length);
            this.buffer = arrayOfByte;
            continue;
          }
        }
        finally
        {
        }
        if (this.in < this.out)
        {
          if (this.in != -1)
          {
            System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.in);
            System.arraycopy(this.buffer, this.out, arrayOfByte, arrayOfByte.length - (this.buffer.length - this.out), this.buffer.length - this.out);
            this.out = (arrayOfByte.length - (this.buffer.length - this.out));
          }
        }
        else
          label284: if (this.in == this.out)
          {
            System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.buffer.length);
            this.in = this.buffer.length;
            continue;
            label236: if ((this.buffer.length == i) && (i > this.BUFFER_SIZE))
            {
              int m = i / 2;
              if (m < this.BUFFER_SIZE)
                m = this.BUFFER_SIZE;
              this.buffer = new byte[m];
              continue;
              while (k - j < paramInt)
                k *= 2;
            }
          }
      }
    }

    public void updateReadSide()
      throws IOException
    {
      try
      {
        int i = available();
        if (i != 0);
        while (true)
        {
          return;
          this.in = 0;
          this.out = 0;
          byte[] arrayOfByte = this.buffer;
          int j = this.in;
          this.in = (j + 1);
          arrayOfByte[j] = 0;
          read();
        }
      }
      finally
      {
      }
    }
  }

  class PassiveInputStream extends Channel.MyPipedInputStream
  {
    PipedOutputStream out;

    PassiveInputStream(PipedOutputStream arg2)
      throws IOException
    {
      super(localPipedOutputStream);
      this.out = localPipedOutputStream;
    }

    PassiveInputStream(PipedOutputStream paramInt, int arg3)
      throws IOException
    {
      super(paramInt, i);
      this.out = paramInt;
    }

    public void close()
      throws IOException
    {
      if (this.out != null)
        this.out.close();
      this.out = null;
    }
  }

  class PassiveOutputStream extends PipedOutputStream
  {
    private Channel.MyPipedInputStream _sink = null;

    PassiveOutputStream(PipedInputStream paramBoolean, boolean arg3)
      throws IOException
    {
      super();
      int i;
      if ((i != 0) && ((paramBoolean instanceof Channel.MyPipedInputStream)))
        this._sink = ((Channel.MyPipedInputStream)paramBoolean);
    }

    public void write(int paramInt)
      throws IOException
    {
      if (this._sink != null)
        this._sink.checkSpace(1);
      super.write(paramInt);
    }

    public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      if (this._sink != null)
        this._sink.checkSpace(paramInt2);
      super.write(paramArrayOfByte, paramInt1, paramInt2);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Channel
 * JD-Core Version:    0.6.2
 */