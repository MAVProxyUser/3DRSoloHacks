package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.NamedRunnable;
import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;

public final class SpdyConnection
  implements Closeable
{
  private static final int OKHTTP_CLIENT_WINDOW_SIZE = 16777216;
  private static final ExecutorService executor;
  long bytesLeftInWriteWindow;
  final boolean client;
  private final Set<Integer> currentPushRequests = new LinkedHashSet();
  final FrameWriter frameWriter;
  private final IncomingStreamHandler handler;
  private final String hostName;
  private long idleStartTimeNs = System.nanoTime();
  private int lastGoodStreamId;
  private int nextPingId;
  private int nextStreamId;
  final Settings okHttpSettings = new Settings();
  final Settings peerSettings = new Settings();
  private Map<Integer, Ping> pings;
  final Protocol protocol;
  private final ExecutorService pushExecutor;
  private final PushObserver pushObserver;
  final Reader readerRunnable;
  private boolean receivedInitialPeerSettings = false;
  private boolean shutdown;
  final Socket socket;
  private final Map<Integer, SpdyStream> streams = new HashMap();
  long unacknowledgedBytesRead = 0L;
  final Variant variant;

  static
  {
    if (!SpdyConnection.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      executor = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp SpdyConnection", true));
      return;
    }
  }

  private SpdyConnection(Builder paramBuilder)
    throws IOException
  {
    this.protocol = paramBuilder.protocol;
    this.pushObserver = paramBuilder.pushObserver;
    this.client = paramBuilder.client;
    this.handler = paramBuilder.handler;
    int j;
    if (paramBuilder.client)
    {
      j = 1;
      this.nextStreamId = j;
      if ((paramBuilder.client) && (this.protocol == Protocol.HTTP_2))
        this.nextStreamId = (2 + this.nextStreamId);
      if (paramBuilder.client)
        i = 1;
      this.nextPingId = i;
      if (paramBuilder.client)
        this.okHttpSettings.set(7, 0, 16777216);
      this.hostName = paramBuilder.hostName;
      if (this.protocol != Protocol.HTTP_2)
        break label370;
      this.variant = new Http2();
      TimeUnit localTimeUnit = TimeUnit.SECONDS;
      LinkedBlockingQueue localLinkedBlockingQueue = new LinkedBlockingQueue();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.hostName;
      this.pushExecutor = new ThreadPoolExecutor(0, 1, 60L, localTimeUnit, localLinkedBlockingQueue, Util.threadFactory(String.format("OkHttp %s Push Observer", arrayOfObject), true));
      this.peerSettings.set(7, 0, 65535);
      this.peerSettings.set(5, 0, 16384);
    }
    while (true)
    {
      this.bytesLeftInWriteWindow = this.peerSettings.getInitialWindowSize(65536);
      this.socket = paramBuilder.socket;
      this.frameWriter = this.variant.newWriter(Okio.buffer(Okio.sink(paramBuilder.socket)), this.client);
      this.readerRunnable = new Reader(null);
      new Thread(this.readerRunnable).start();
      return;
      j = i;
      break;
      label370: if (this.protocol != Protocol.SPDY_3)
        break label399;
      this.variant = new Spdy3();
      this.pushExecutor = null;
    }
    label399: throw new AssertionError(this.protocol);
  }

  // ERROR //
  private void close(ErrorCode paramErrorCode1, ErrorCode paramErrorCode2)
    throws IOException
  {
    // Byte code:
    //   0: getstatic 62	com/squareup/okhttp/internal/spdy/SpdyConnection:$assertionsDisabled	Z
    //   3: ifne +18 -> 21
    //   6: aload_0
    //   7: invokestatic 331	java/lang/Thread:holdsLock	(Ljava/lang/Object;)Z
    //   10: ifeq +11 -> 21
    //   13: new 242	java/lang/AssertionError
    //   16: dup
    //   17: invokespecial 332	java/lang/AssertionError:<init>	()V
    //   20: athrow
    //   21: aconst_null
    //   22: astore_3
    //   23: aload_0
    //   24: aload_1
    //   25: invokevirtual 335	com/squareup/okhttp/internal/spdy/SpdyConnection:shutdown	(Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
    //   28: aload_0
    //   29: monitorenter
    //   30: aload_0
    //   31: getfield 100	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   34: invokeinterface 340 1 0
    //   39: istore 6
    //   41: aconst_null
    //   42: astore 7
    //   44: iload 6
    //   46: ifne +48 -> 94
    //   49: aload_0
    //   50: getfield 100	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   53: invokeinterface 344 1 0
    //   58: aload_0
    //   59: getfield 100	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   62: invokeinterface 348 1 0
    //   67: anewarray 350	com/squareup/okhttp/internal/spdy/SpdyStream
    //   70: invokeinterface 356 2 0
    //   75: checkcast 358	[Lcom/squareup/okhttp/internal/spdy/SpdyStream;
    //   78: astore 7
    //   80: aload_0
    //   81: getfield 100	com/squareup/okhttp/internal/spdy/SpdyConnection:streams	Ljava/util/Map;
    //   84: invokeinterface 361 1 0
    //   89: aload_0
    //   90: iconst_0
    //   91: invokespecial 365	com/squareup/okhttp/internal/spdy/SpdyConnection:setIdle	(Z)V
    //   94: aload_0
    //   95: getfield 367	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   98: astore 8
    //   100: aconst_null
    //   101: astore 9
    //   103: aload 8
    //   105: ifnull +39 -> 144
    //   108: aload_0
    //   109: getfield 367	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   112: invokeinterface 344 1 0
    //   117: aload_0
    //   118: getfield 367	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   121: invokeinterface 348 1 0
    //   126: anewarray 369	com/squareup/okhttp/internal/spdy/Ping
    //   129: invokeinterface 356 2 0
    //   134: checkcast 371	[Lcom/squareup/okhttp/internal/spdy/Ping;
    //   137: astore 9
    //   139: aload_0
    //   140: aconst_null
    //   141: putfield 367	com/squareup/okhttp/internal/spdy/SpdyConnection:pings	Ljava/util/Map;
    //   144: aload_0
    //   145: monitorexit
    //   146: aload 7
    //   148: ifnull +64 -> 212
    //   151: aload 7
    //   153: arraylength
    //   154: istore 14
    //   156: iconst_0
    //   157: istore 15
    //   159: iload 15
    //   161: iload 14
    //   163: if_icmpge +49 -> 212
    //   166: aload 7
    //   168: iload 15
    //   170: aaload
    //   171: astore 16
    //   173: aload 16
    //   175: aload_2
    //   176: invokevirtual 373	com/squareup/okhttp/internal/spdy/SpdyStream:close	(Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
    //   179: iinc 15 1
    //   182: goto -23 -> 159
    //   185: astore 4
    //   187: aload 4
    //   189: astore_3
    //   190: goto -162 -> 28
    //   193: astore 5
    //   195: aload_0
    //   196: monitorexit
    //   197: aload 5
    //   199: athrow
    //   200: astore 17
    //   202: aload_3
    //   203: ifnull -24 -> 179
    //   206: aload 17
    //   208: astore_3
    //   209: goto -30 -> 179
    //   212: aload 9
    //   214: ifnull +32 -> 246
    //   217: aload 9
    //   219: arraylength
    //   220: istore 12
    //   222: iconst_0
    //   223: istore 13
    //   225: iload 13
    //   227: iload 12
    //   229: if_icmpge +17 -> 246
    //   232: aload 9
    //   234: iload 13
    //   236: aaload
    //   237: invokevirtual 376	com/squareup/okhttp/internal/spdy/Ping:cancel	()V
    //   240: iinc 13 1
    //   243: goto -18 -> 225
    //   246: aload_0
    //   247: getfield 219	com/squareup/okhttp/internal/spdy/SpdyConnection:frameWriter	Lcom/squareup/okhttp/internal/spdy/FrameWriter;
    //   250: invokeinterface 380 1 0
    //   255: aload_0
    //   256: getfield 201	com/squareup/okhttp/internal/spdy/SpdyConnection:socket	Ljava/net/Socket;
    //   259: invokevirtual 383	java/net/Socket:close	()V
    //   262: aload_3
    //   263: ifnull +25 -> 288
    //   266: aload_3
    //   267: athrow
    //   268: astore 10
    //   270: aload_3
    //   271: ifnonnull -16 -> 255
    //   274: aload 10
    //   276: astore_3
    //   277: goto -22 -> 255
    //   280: astore 11
    //   282: aload 11
    //   284: astore_3
    //   285: goto -23 -> 262
    //   288: return
    //
    // Exception table:
    //   from	to	target	type
    //   23	28	185	java/io/IOException
    //   30	41	193	finally
    //   49	94	193	finally
    //   94	100	193	finally
    //   108	144	193	finally
    //   144	146	193	finally
    //   195	197	193	finally
    //   173	179	200	java/io/IOException
    //   246	255	268	java/io/IOException
    //   255	262	280	java/io/IOException
  }

  private SpdyStream newStream(int paramInt, List<Header> paramList, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    boolean bool1 = true;
    boolean bool2;
    if (!paramBoolean1)
    {
      bool2 = bool1;
      if (paramBoolean2)
        break label66;
    }
    while (true)
    {
      synchronized (this.frameWriter)
      {
        try
        {
          if (!this.shutdown)
            break label72;
          throw new IOException("shutdown");
        }
        finally
        {
        }
      }
      bool2 = false;
      break;
      label66: bool1 = false;
    }
    label72: int i = this.nextStreamId;
    this.nextStreamId = (2 + this.nextStreamId);
    SpdyStream localSpdyStream = new SpdyStream(i, this, bool2, bool1, paramList);
    if (localSpdyStream.isOpen())
    {
      this.streams.put(Integer.valueOf(i), localSpdyStream);
      setIdle(false);
    }
    if (paramInt == 0)
      this.frameWriter.synStream(bool2, bool1, i, paramInt, paramList);
    while (true)
    {
      if (!paramBoolean1)
        this.frameWriter.flush();
      return localSpdyStream;
      if (this.client)
        throw new IllegalArgumentException("client streams shouldn't have associated stream IDs");
      this.frameWriter.pushPromise(paramInt, i, paramList);
    }
  }

  private void pushDataLater(final int paramInt1, BufferedSource paramBufferedSource, final int paramInt2, final boolean paramBoolean)
    throws IOException
  {
    final Buffer localBuffer = new Buffer();
    paramBufferedSource.require(paramInt2);
    paramBufferedSource.read(localBuffer, paramInt2);
    if (localBuffer.size() != paramInt2)
      throw new IOException(localBuffer.size() + " != " + paramInt2);
    ExecutorService localExecutorService = this.pushExecutor;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt1);
    localExecutorService.execute(new NamedRunnable("OkHttp %s Push Data[%s]", arrayOfObject)
    {
      public void execute()
      {
        try
        {
          boolean bool = SpdyConnection.this.pushObserver.onData(paramInt1, localBuffer, paramInt2, paramBoolean);
          if (bool)
            SpdyConnection.this.frameWriter.rstStream(paramInt1, ErrorCode.CANCEL);
          if ((bool) || (paramBoolean))
            synchronized (SpdyConnection.this)
            {
              SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(paramInt1));
              return;
            }
        }
        catch (IOException localIOException)
        {
        }
      }
    });
  }

  private void pushHeadersLater(final int paramInt, final List<Header> paramList, final boolean paramBoolean)
  {
    ExecutorService localExecutorService = this.pushExecutor;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    localExecutorService.execute(new NamedRunnable("OkHttp %s Push Headers[%s]", arrayOfObject)
    {
      public void execute()
      {
        boolean bool = SpdyConnection.this.pushObserver.onHeaders(paramInt, paramList, paramBoolean);
        if (bool);
        try
        {
          SpdyConnection.this.frameWriter.rstStream(paramInt, ErrorCode.CANCEL);
          if ((bool) || (paramBoolean))
            synchronized (SpdyConnection.this)
            {
              SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(paramInt));
              return;
            }
        }
        catch (IOException localIOException)
        {
        }
      }
    });
  }

  private void pushRequestLater(final int paramInt, final List<Header> paramList)
  {
    try
    {
      if (this.currentPushRequests.contains(Integer.valueOf(paramInt)))
      {
        writeSynResetLater(paramInt, ErrorCode.PROTOCOL_ERROR);
        return;
      }
      this.currentPushRequests.add(Integer.valueOf(paramInt));
      ExecutorService localExecutorService = this.pushExecutor;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.hostName;
      arrayOfObject[1] = Integer.valueOf(paramInt);
      localExecutorService.execute(new NamedRunnable("OkHttp %s Push Request[%s]", arrayOfObject)
      {
        public void execute()
        {
          if (SpdyConnection.this.pushObserver.onRequest(paramInt, paramList))
            try
            {
              SpdyConnection.this.frameWriter.rstStream(paramInt, ErrorCode.CANCEL);
              synchronized (SpdyConnection.this)
              {
                SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(paramInt));
                return;
              }
            }
            catch (IOException localIOException)
            {
            }
        }
      });
      return;
    }
    finally
    {
    }
  }

  private void pushResetLater(final int paramInt, final ErrorCode paramErrorCode)
  {
    ExecutorService localExecutorService = this.pushExecutor;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    localExecutorService.execute(new NamedRunnable("OkHttp %s Push Reset[%s]", arrayOfObject)
    {
      public void execute()
      {
        SpdyConnection.this.pushObserver.onReset(paramInt, paramErrorCode);
        synchronized (SpdyConnection.this)
        {
          SpdyConnection.this.currentPushRequests.remove(Integer.valueOf(paramInt));
          return;
        }
      }
    });
  }

  private boolean pushedStream(int paramInt)
  {
    return (this.protocol == Protocol.HTTP_2) && (paramInt != 0) && ((paramInt & 0x1) == 0);
  }

  private Ping removePing(int paramInt)
  {
    try
    {
      if (this.pings != null)
      {
        localPing = (Ping)this.pings.remove(Integer.valueOf(paramInt));
        return localPing;
      }
      Ping localPing = null;
    }
    finally
    {
    }
  }

  private void setIdle(boolean paramBoolean)
  {
    if (paramBoolean);
    try
    {
      for (long l = System.nanoTime(); ; l = 9223372036854775807L)
      {
        this.idleStartTimeNs = l;
        return;
      }
    }
    finally
    {
    }
  }

  private void writePing(boolean paramBoolean, int paramInt1, int paramInt2, Ping paramPing)
    throws IOException
  {
    FrameWriter localFrameWriter = this.frameWriter;
    if (paramPing != null);
    try
    {
      paramPing.send();
      this.frameWriter.ping(paramBoolean, paramInt1, paramInt2);
      return;
    }
    finally
    {
    }
  }

  private void writePingLater(final boolean paramBoolean, final int paramInt1, final int paramInt2, final Ping paramPing)
  {
    ExecutorService localExecutorService = executor;
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt1);
    arrayOfObject[2] = Integer.valueOf(paramInt2);
    localExecutorService.execute(new NamedRunnable("OkHttp %s ping %08x%08x", arrayOfObject)
    {
      public void execute()
      {
        try
        {
          SpdyConnection.this.writePing(paramBoolean, paramInt1, paramInt2, paramPing);
          return;
        }
        catch (IOException localIOException)
        {
        }
      }
    });
  }

  void addBytesToWriteWindow(long paramLong)
  {
    this.bytesLeftInWriteWindow = (paramLong + this.bytesLeftInWriteWindow);
    if (paramLong > 0L)
      notifyAll();
  }

  public void close()
    throws IOException
  {
    close(ErrorCode.NO_ERROR, ErrorCode.CANCEL);
  }

  public void flush()
    throws IOException
  {
    this.frameWriter.flush();
  }

  public long getIdleStartTimeNs()
  {
    try
    {
      long l = this.idleStartTimeNs;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Protocol getProtocol()
  {
    return this.protocol;
  }

  SpdyStream getStream(int paramInt)
  {
    try
    {
      SpdyStream localSpdyStream = (SpdyStream)this.streams.get(Integer.valueOf(paramInt));
      return localSpdyStream;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean isIdle()
  {
    try
    {
      long l = this.idleStartTimeNs;
      if (l != 9223372036854775807L)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
    finally
    {
    }
  }

  public SpdyStream newStream(List<Header> paramList, boolean paramBoolean1, boolean paramBoolean2)
    throws IOException
  {
    return newStream(0, paramList, paramBoolean1, paramBoolean2);
  }

  public int openStreamCount()
  {
    try
    {
      int i = this.streams.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Ping ping()
    throws IOException
  {
    Ping localPing = new Ping();
    try
    {
      if (this.shutdown)
        throw new IOException("shutdown");
    }
    finally
    {
    }
    int i = this.nextPingId;
    this.nextPingId = (2 + this.nextPingId);
    if (this.pings == null)
      this.pings = new HashMap();
    this.pings.put(Integer.valueOf(i), localPing);
    writePing(false, i, 1330343787, localPing);
    return localPing;
  }

  public SpdyStream pushStream(int paramInt, List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    if (this.client)
      throw new IllegalStateException("Client cannot push requests.");
    if (this.protocol != Protocol.HTTP_2)
      throw new IllegalStateException("protocol != HTTP_2");
    return newStream(paramInt, paramList, paramBoolean, false);
  }

  SpdyStream removeStream(int paramInt)
  {
    try
    {
      SpdyStream localSpdyStream = (SpdyStream)this.streams.remove(Integer.valueOf(paramInt));
      if ((localSpdyStream != null) && (this.streams.isEmpty()))
        setIdle(true);
      return localSpdyStream;
    }
    finally
    {
    }
  }

  public void sendConnectionPreface()
    throws IOException
  {
    this.frameWriter.connectionPreface();
    this.frameWriter.settings(this.okHttpSettings);
    int i = this.okHttpSettings.getInitialWindowSize(65536);
    if (i != 65536)
      this.frameWriter.windowUpdate(0, i - 65536);
  }

  public void shutdown(ErrorCode paramErrorCode)
    throws IOException
  {
    synchronized (this.frameWriter)
    {
    }
    try
    {
      if (this.shutdown)
        return;
      this.shutdown = true;
      int i = this.lastGoodStreamId;
      this.frameWriter.goAway(i, paramErrorCode, Util.EMPTY_BYTE_ARRAY);
      return;
      localObject1 = finally;
      throw localObject1;
    }
    finally
    {
    }
  }

  public void writeData(int paramInt, boolean paramBoolean, Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (paramLong == 0L)
    {
      this.frameWriter.data(paramBoolean, paramInt, paramBuffer, 0);
      return;
    }
    while (true)
    {
      try
      {
        int i = Math.min((int)Math.min(paramLong, this.bytesLeftInWriteWindow), this.frameWriter.maxDataLength());
        this.bytesLeftInWriteWindow -= i;
        paramLong -= i;
        FrameWriter localFrameWriter = this.frameWriter;
        if ((paramBoolean) && (paramLong == 0L))
        {
          bool = true;
          localFrameWriter.data(bool, paramInt, paramBuffer, i);
          if (paramLong <= 0L)
            break;
          try
          {
            if (this.bytesLeftInWriteWindow > 0L)
              continue;
            wait();
            continue;
          }
          catch (InterruptedException localInterruptedException)
          {
            throw new InterruptedIOException();
          }
        }
      }
      finally
      {
      }
      boolean bool = false;
    }
  }

  void writeSynReply(int paramInt, boolean paramBoolean, List<Header> paramList)
    throws IOException
  {
    this.frameWriter.synReply(paramBoolean, paramInt, paramList);
  }

  void writeSynReset(int paramInt, ErrorCode paramErrorCode)
    throws IOException
  {
    this.frameWriter.rstStream(paramInt, paramErrorCode);
  }

  void writeSynResetLater(final int paramInt, final ErrorCode paramErrorCode)
  {
    ExecutorService localExecutorService = executor;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    localExecutorService.submit(new NamedRunnable("OkHttp %s stream %d", arrayOfObject)
    {
      public void execute()
      {
        try
        {
          SpdyConnection.this.writeSynReset(paramInt, paramErrorCode);
          return;
        }
        catch (IOException localIOException)
        {
        }
      }
    });
  }

  void writeWindowUpdateLater(final int paramInt, final long paramLong)
  {
    ExecutorService localExecutorService = executor;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.hostName;
    arrayOfObject[1] = Integer.valueOf(paramInt);
    localExecutorService.execute(new NamedRunnable("OkHttp Window Update %s stream %d", arrayOfObject)
    {
      public void execute()
      {
        try
        {
          SpdyConnection.this.frameWriter.windowUpdate(paramInt, paramLong);
          return;
        }
        catch (IOException localIOException)
        {
        }
      }
    });
  }

  public static class Builder
  {
    private boolean client;
    private IncomingStreamHandler handler = IncomingStreamHandler.REFUSE_INCOMING_STREAMS;
    private String hostName;
    private Protocol protocol = Protocol.SPDY_3;
    private PushObserver pushObserver = PushObserver.CANCEL;
    private Socket socket;

    public Builder(String paramString, boolean paramBoolean, Socket paramSocket)
      throws IOException
    {
      this.hostName = paramString;
      this.client = paramBoolean;
      this.socket = paramSocket;
    }

    public Builder(boolean paramBoolean, Socket paramSocket)
      throws IOException
    {
      this(((InetSocketAddress)paramSocket.getRemoteSocketAddress()).getHostName(), paramBoolean, paramSocket);
    }

    public SpdyConnection build()
      throws IOException
    {
      return new SpdyConnection(this, null);
    }

    public Builder handler(IncomingStreamHandler paramIncomingStreamHandler)
    {
      this.handler = paramIncomingStreamHandler;
      return this;
    }

    public Builder protocol(Protocol paramProtocol)
    {
      this.protocol = paramProtocol;
      return this;
    }

    public Builder pushObserver(PushObserver paramPushObserver)
    {
      this.pushObserver = paramPushObserver;
      return this;
    }
  }

  class Reader extends NamedRunnable
    implements FrameReader.Handler
  {
    FrameReader frameReader;

    private Reader()
    {
      super(arrayOfObject);
    }

    private void ackSettingsLater(final Settings paramSettings)
    {
      ExecutorService localExecutorService = SpdyConnection.executor;
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = SpdyConnection.this.hostName;
      localExecutorService.execute(new NamedRunnable("OkHttp %s ACK Settings", arrayOfObject)
      {
        public void execute()
        {
          try
          {
            SpdyConnection.this.frameWriter.ackSettings(paramSettings);
            return;
          }
          catch (IOException localIOException)
          {
          }
        }
      });
    }

    public void ackSettings()
    {
    }

    public void alternateService(int paramInt1, String paramString1, ByteString paramByteString, String paramString2, int paramInt2, long paramLong)
    {
    }

    public void data(boolean paramBoolean, int paramInt1, BufferedSource paramBufferedSource, int paramInt2)
      throws IOException
    {
      if (SpdyConnection.this.pushedStream(paramInt1))
        SpdyConnection.this.pushDataLater(paramInt1, paramBufferedSource, paramInt2, paramBoolean);
      SpdyStream localSpdyStream;
      do
      {
        return;
        localSpdyStream = SpdyConnection.this.getStream(paramInt1);
        if (localSpdyStream == null)
        {
          SpdyConnection.this.writeSynResetLater(paramInt1, ErrorCode.INVALID_STREAM);
          paramBufferedSource.skip(paramInt2);
          return;
        }
        localSpdyStream.receiveData(paramBufferedSource, paramInt2);
      }
      while (!paramBoolean);
      localSpdyStream.receiveFin();
    }

    // ERROR //
    protected void execute()
    {
      // Byte code:
      //   0: getstatic 97	com/squareup/okhttp/internal/spdy/ErrorCode:INTERNAL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   3: astore_1
      //   4: getstatic 97	com/squareup/okhttp/internal/spdy/ErrorCode:INTERNAL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   7: astore_2
      //   8: aload_0
      //   9: aload_0
      //   10: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   13: getfield 101	com/squareup/okhttp/internal/spdy/SpdyConnection:variant	Lcom/squareup/okhttp/internal/spdy/Variant;
      //   16: aload_0
      //   17: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   20: getfield 105	com/squareup/okhttp/internal/spdy/SpdyConnection:socket	Ljava/net/Socket;
      //   23: invokestatic 111	okio/Okio:source	(Ljava/net/Socket;)Lokio/Source;
      //   26: invokestatic 115	okio/Okio:buffer	(Lokio/Source;)Lokio/BufferedSource;
      //   29: aload_0
      //   30: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   33: getfield 119	com/squareup/okhttp/internal/spdy/SpdyConnection:client	Z
      //   36: invokeinterface 125 3 0
      //   41: putfield 127	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   44: aload_0
      //   45: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   48: getfield 119	com/squareup/okhttp/internal/spdy/SpdyConnection:client	Z
      //   51: ifne +12 -> 63
      //   54: aload_0
      //   55: getfield 127	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   58: invokeinterface 132 1 0
      //   63: aload_0
      //   64: getfield 127	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   67: aload_0
      //   68: invokeinterface 136 2 0
      //   73: ifne -10 -> 63
      //   76: getstatic 139	com/squareup/okhttp/internal/spdy/ErrorCode:NO_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   79: astore_1
      //   80: getstatic 142	com/squareup/okhttp/internal/spdy/ErrorCode:CANCEL	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   83: astore 8
      //   85: aload_0
      //   86: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   89: aload_1
      //   90: aload 8
      //   92: invokestatic 146	com/squareup/okhttp/internal/spdy/SpdyConnection:access$1000	(Lcom/squareup/okhttp/internal/spdy/SpdyConnection;Lcom/squareup/okhttp/internal/spdy/ErrorCode;Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
      //   95: aload_0
      //   96: getfield 127	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   99: invokestatic 152	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   102: return
      //   103: astore 5
      //   105: getstatic 155	com/squareup/okhttp/internal/spdy/ErrorCode:PROTOCOL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   108: astore_1
      //   109: getstatic 155	com/squareup/okhttp/internal/spdy/ErrorCode:PROTOCOL_ERROR	Lcom/squareup/okhttp/internal/spdy/ErrorCode;
      //   112: astore 6
      //   114: aload_0
      //   115: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   118: aload_1
      //   119: aload 6
      //   121: invokestatic 146	com/squareup/okhttp/internal/spdy/SpdyConnection:access$1000	(Lcom/squareup/okhttp/internal/spdy/SpdyConnection;Lcom/squareup/okhttp/internal/spdy/ErrorCode;Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
      //   124: aload_0
      //   125: getfield 127	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   128: invokestatic 152	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   131: return
      //   132: astore_3
      //   133: aload_0
      //   134: getfield 14	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:this$0	Lcom/squareup/okhttp/internal/spdy/SpdyConnection;
      //   137: aload_1
      //   138: aload_2
      //   139: invokestatic 146	com/squareup/okhttp/internal/spdy/SpdyConnection:access$1000	(Lcom/squareup/okhttp/internal/spdy/SpdyConnection;Lcom/squareup/okhttp/internal/spdy/ErrorCode;Lcom/squareup/okhttp/internal/spdy/ErrorCode;)V
      //   142: aload_0
      //   143: getfield 127	com/squareup/okhttp/internal/spdy/SpdyConnection$Reader:frameReader	Lcom/squareup/okhttp/internal/spdy/FrameReader;
      //   146: invokestatic 152	com/squareup/okhttp/internal/Util:closeQuietly	(Ljava/io/Closeable;)V
      //   149: aload_3
      //   150: athrow
      //   151: astore 4
      //   153: goto -11 -> 142
      //   156: astore 7
      //   158: goto -34 -> 124
      //   161: astore 9
      //   163: goto -68 -> 95
      //
      // Exception table:
      //   from	to	target	type
      //   8	63	103	java/io/IOException
      //   63	85	103	java/io/IOException
      //   8	63	132	finally
      //   63	85	132	finally
      //   105	114	132	finally
      //   133	142	151	java/io/IOException
      //   114	124	156	java/io/IOException
      //   85	95	161	java/io/IOException
    }

    public void goAway(int paramInt, ErrorCode paramErrorCode, ByteString paramByteString)
    {
      if (paramByteString.size() > 0);
      synchronized (SpdyConnection.this)
      {
        SpdyStream[] arrayOfSpdyStream = (SpdyStream[])SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
        SpdyConnection.access$1402(SpdyConnection.this, true);
        int i = arrayOfSpdyStream.length;
        int j = 0;
        if (j < i)
        {
          SpdyStream localSpdyStream = arrayOfSpdyStream[j];
          if ((localSpdyStream.getId() > paramInt) && (localSpdyStream.isLocallyInitiated()))
          {
            localSpdyStream.receiveRstStream(ErrorCode.REFUSED_STREAM);
            SpdyConnection.this.removeStream(localSpdyStream.getId());
          }
          j++;
        }
      }
    }

    public void headers(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, List<Header> paramList, HeadersMode paramHeadersMode)
    {
      if (SpdyConnection.this.pushedStream(paramInt1))
        SpdyConnection.this.pushHeadersLater(paramInt1, paramList, paramBoolean2);
      SpdyStream localSpdyStream1;
      do
      {
        return;
        synchronized (SpdyConnection.this)
        {
          if (SpdyConnection.this.shutdown)
            return;
        }
        localSpdyStream1 = SpdyConnection.this.getStream(paramInt1);
        if (localSpdyStream1 == null)
        {
          if (paramHeadersMode.failIfStreamAbsent())
          {
            SpdyConnection.this.writeSynResetLater(paramInt1, ErrorCode.INVALID_STREAM);
            return;
          }
          if (paramInt1 <= SpdyConnection.this.lastGoodStreamId)
            return;
          if (paramInt1 % 2 == SpdyConnection.this.nextStreamId % 2)
            return;
          final SpdyStream localSpdyStream2 = new SpdyStream(paramInt1, SpdyConnection.this, paramBoolean1, paramBoolean2, paramList);
          SpdyConnection.access$1502(SpdyConnection.this, paramInt1);
          SpdyConnection.this.streams.put(Integer.valueOf(paramInt1), localSpdyStream2);
          ExecutorService localExecutorService = SpdyConnection.executor;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = SpdyConnection.this.hostName;
          arrayOfObject[1] = Integer.valueOf(paramInt1);
          localExecutorService.execute(new NamedRunnable("OkHttp %s stream %d", arrayOfObject)
          {
            public void execute()
            {
              try
              {
                SpdyConnection.this.handler.receive(localSpdyStream2);
                return;
              }
              catch (IOException localIOException)
              {
                throw new RuntimeException(localIOException);
              }
            }
          });
          return;
        }
        if (paramHeadersMode.failIfStreamPresent())
        {
          localSpdyStream1.closeLater(ErrorCode.PROTOCOL_ERROR);
          SpdyConnection.this.removeStream(paramInt1);
          return;
        }
        localSpdyStream1.receiveHeaders(paramList, paramHeadersMode);
      }
      while (!paramBoolean2);
      localSpdyStream1.receiveFin();
    }

    public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
    {
      if (paramBoolean)
      {
        Ping localPing = SpdyConnection.this.removePing(paramInt1);
        if (localPing != null)
          localPing.receive();
        return;
      }
      SpdyConnection.this.writePingLater(true, paramInt1, paramInt2, null);
    }

    public void priority(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
    {
    }

    public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
    {
      SpdyConnection.this.pushRequestLater(paramInt2, paramList);
    }

    public void rstStream(int paramInt, ErrorCode paramErrorCode)
    {
      if (SpdyConnection.this.pushedStream(paramInt))
        SpdyConnection.this.pushResetLater(paramInt, paramErrorCode);
      SpdyStream localSpdyStream;
      do
      {
        return;
        localSpdyStream = SpdyConnection.this.removeStream(paramInt);
      }
      while (localSpdyStream == null);
      localSpdyStream.receiveRstStream(paramErrorCode);
    }

    public void settings(boolean paramBoolean, Settings paramSettings)
    {
      long l = 0L;
      while (true)
      {
        SpdyStream[] arrayOfSpdyStream;
        int m;
        synchronized (SpdyConnection.this)
        {
          int i = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
          if (paramBoolean)
            SpdyConnection.this.peerSettings.clear();
          SpdyConnection.this.peerSettings.merge(paramSettings);
          if (SpdyConnection.this.getProtocol() == Protocol.HTTP_2)
            ackSettingsLater(paramSettings);
          int j = SpdyConnection.this.peerSettings.getInitialWindowSize(65536);
          arrayOfSpdyStream = null;
          if (j != -1)
          {
            arrayOfSpdyStream = null;
            if (j != i)
            {
              l = j - i;
              if (!SpdyConnection.this.receivedInitialPeerSettings)
              {
                SpdyConnection.this.addBytesToWriteWindow(l);
                SpdyConnection.access$2102(SpdyConnection.this, true);
              }
              boolean bool = SpdyConnection.this.streams.isEmpty();
              arrayOfSpdyStream = null;
              if (!bool)
                arrayOfSpdyStream = (SpdyStream[])SpdyConnection.this.streams.values().toArray(new SpdyStream[SpdyConnection.this.streams.size()]);
            }
          }
          if ((arrayOfSpdyStream == null) || (l == 0L))
            break;
          int k = arrayOfSpdyStream.length;
          m = 0;
          if (m >= k)
            break;
        }
        synchronized (arrayOfSpdyStream[m])
        {
          ???.addBytesToWriteWindow(l);
          m++;
          continue;
          localObject1 = finally;
          throw localObject1;
        }
      }
    }

    public void windowUpdate(int paramInt, long paramLong)
    {
      if (paramInt == 0)
        synchronized (SpdyConnection.this)
        {
          SpdyConnection localSpdyConnection2 = SpdyConnection.this;
          localSpdyConnection2.bytesLeftInWriteWindow = (paramLong + localSpdyConnection2.bytesLeftInWriteWindow);
          SpdyConnection.this.notifyAll();
          return;
        }
      SpdyStream localSpdyStream = SpdyConnection.this.getStream(paramInt);
      if (localSpdyStream != null)
        try
        {
          localSpdyStream.addBytesToWriteWindow(paramLong);
          return;
        }
        finally
        {
        }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.SpdyConnection
 * JD-Core Version:    0.6.2
 */