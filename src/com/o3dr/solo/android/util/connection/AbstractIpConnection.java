package com.o3dr.solo.android.util.connection;

import android.util.Log;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractIpConnection
{
  public static final int CONNECTION_TIMEOUT = 15000;
  private static final int DEFAULT_READ_BUFFER_SIZE = 4096;
  public static final int STATE_CONNECTED = 2;
  public static final int STATE_CONNECTING = 1;
  public static final int STATE_DISCONNECTED;
  private static final String TAG = AbstractIpConnection.class.getSimpleName();
  private final AtomicInteger connectionStatus = new AtomicInteger(0);
  private IpConnectionListener ipConnectionListener;
  private final boolean isReadingDisabled;
  private final boolean isSendingDisabled;
  private final Runnable managerTask = new Runnable()
  {
    // ERROR //
    public void run()
    {
      // Byte code:
      //   0: bipush 252
      //   2: invokestatic 28	android/os/Process:setThreadPriority	(I)V
      //   5: aconst_null
      //   6: astore_1
      //   7: aload_0
      //   8: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   11: invokevirtual 31	com/o3dr/solo/android/util/connection/AbstractIpConnection:open	()V
      //   14: aload_0
      //   15: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   18: invokestatic 35	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$000	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/util/concurrent/atomic/AtomicInteger;
      //   21: iconst_2
      //   22: invokevirtual 40	java/util/concurrent/atomic/AtomicInteger:set	(I)V
      //   25: aload_0
      //   26: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   29: invokestatic 44	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$100	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Lcom/o3dr/solo/android/util/connection/IpConnectionListener;
      //   32: astore 7
      //   34: aconst_null
      //   35: astore_1
      //   36: aload 7
      //   38: ifnull +15 -> 53
      //   41: aload_0
      //   42: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   45: invokestatic 44	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$100	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Lcom/o3dr/solo/android/util/connection/IpConnectionListener;
      //   48: invokeinterface 49 1 0
      //   53: aload_0
      //   54: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   57: invokestatic 53	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$300	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Z
      //   60: istore 8
      //   62: aconst_null
      //   63: astore_1
      //   64: iload 8
      //   66: ifne +29 -> 95
      //   69: new 55	java/lang/Thread
      //   72: dup
      //   73: aload_0
      //   74: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   77: invokestatic 59	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$400	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/lang/Runnable;
      //   80: ldc 61
      //   82: invokespecial 64	java/lang/Thread:<init>	(Ljava/lang/Runnable;Ljava/lang/String;)V
      //   85: astore 9
      //   87: aload 9
      //   89: invokevirtual 67	java/lang/Thread:start	()V
      //   92: aload 9
      //   94: astore_1
      //   95: aload_0
      //   96: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   99: invokestatic 70	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$500	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Z
      //   102: istore 10
      //   104: iload 10
      //   106: ifne +195 -> 301
      //   109: aload_0
      //   110: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   113: invokestatic 35	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$000	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/util/concurrent/atomic/AtomicInteger;
      //   116: invokevirtual 74	java/util/concurrent/atomic/AtomicInteger:get	()I
      //   119: iconst_2
      //   120: if_icmpne +104 -> 224
      //   123: aload_0
      //   124: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   127: invokestatic 78	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$600	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/nio/ByteBuffer;
      //   130: invokevirtual 84	java/nio/ByteBuffer:clear	()Ljava/nio/Buffer;
      //   133: pop
      //   134: aload_0
      //   135: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   138: aload_0
      //   139: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   142: invokestatic 78	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$600	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/nio/ByteBuffer;
      //   145: invokevirtual 88	com/o3dr/solo/android/util/connection/AbstractIpConnection:read	(Ljava/nio/ByteBuffer;)I
      //   148: istore 17
      //   150: iload 17
      //   152: ifle -43 -> 109
      //   155: aload_0
      //   156: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   159: invokestatic 78	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$600	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/nio/ByteBuffer;
      //   162: iload 17
      //   164: invokevirtual 92	java/nio/ByteBuffer:limit	(I)Ljava/nio/Buffer;
      //   167: pop
      //   168: aload_0
      //   169: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   172: invokestatic 44	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$100	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Lcom/o3dr/solo/android/util/connection/IpConnectionListener;
      //   175: ifnull -66 -> 109
      //   178: aload_0
      //   179: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   182: invokestatic 78	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$600	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/nio/ByteBuffer;
      //   185: invokevirtual 95	java/nio/ByteBuffer:rewind	()Ljava/nio/Buffer;
      //   188: pop
      //   189: aload_0
      //   190: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   193: invokestatic 44	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$100	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Lcom/o3dr/solo/android/util/connection/IpConnectionListener;
      //   196: aload_0
      //   197: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   200: invokestatic 78	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$600	(Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;)Ljava/nio/ByteBuffer;
      //   203: invokeinterface 99 2 0
      //   208: goto -99 -> 109
      //   211: astore 14
      //   213: invokestatic 103	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$200	()Ljava/lang/String;
      //   216: ldc 105
      //   218: aload 14
      //   220: invokestatic 111	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   223: pop
      //   224: aload_1
      //   225: ifnull +14 -> 239
      //   228: aload_1
      //   229: invokevirtual 115	java/lang/Thread:isAlive	()Z
      //   232: ifeq +7 -> 239
      //   235: aload_1
      //   236: invokevirtual 118	java/lang/Thread:interrupt	()V
      //   239: aload_0
      //   240: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   243: invokevirtual 121	com/o3dr/solo/android/util/connection/AbstractIpConnection:disconnect	()V
      //   246: invokestatic 103	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$200	()Ljava/lang/String;
      //   249: ldc 123
      //   251: invokestatic 127	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
      //   254: pop
      //   255: return
      //   256: astore 4
      //   258: invokestatic 103	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$200	()Ljava/lang/String;
      //   261: ldc 129
      //   263: aload 4
      //   265: invokestatic 111	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   268: pop
      //   269: iconst_0
      //   270: ifeq +14 -> 284
      //   273: aconst_null
      //   274: invokevirtual 115	java/lang/Thread:isAlive	()Z
      //   277: ifeq +7 -> 284
      //   280: aconst_null
      //   281: invokevirtual 118	java/lang/Thread:interrupt	()V
      //   284: aload_0
      //   285: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   288: invokevirtual 121	com/o3dr/solo/android/util/connection/AbstractIpConnection:disconnect	()V
      //   291: invokestatic 103	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$200	()Ljava/lang/String;
      //   294: ldc 123
      //   296: invokestatic 127	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
      //   299: pop
      //   300: return
      //   301: aload_1
      //   302: ifnull -78 -> 224
      //   305: aload_1
      //   306: invokevirtual 132	java/lang/Thread:join	()V
      //   309: goto -85 -> 224
      //   312: astore 11
      //   314: invokestatic 103	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$200	()Ljava/lang/String;
      //   317: ldc 134
      //   319: aload 11
      //   321: invokestatic 111	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
      //   324: pop
      //   325: goto -101 -> 224
      //   328: astore_2
      //   329: aload_1
      //   330: ifnull +14 -> 344
      //   333: aload_1
      //   334: invokevirtual 115	java/lang/Thread:isAlive	()Z
      //   337: ifeq +7 -> 344
      //   340: aload_1
      //   341: invokevirtual 118	java/lang/Thread:interrupt	()V
      //   344: aload_0
      //   345: getfield 14	com/o3dr/solo/android/util/connection/AbstractIpConnection$1:this$0	Lcom/o3dr/solo/android/util/connection/AbstractIpConnection;
      //   348: invokevirtual 121	com/o3dr/solo/android/util/connection/AbstractIpConnection:disconnect	()V
      //   351: invokestatic 103	com/o3dr/solo/android/util/connection/AbstractIpConnection:access$200	()Ljava/lang/String;
      //   354: ldc 123
      //   356: invokestatic 127	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
      //   359: pop
      //   360: aload_2
      //   361: athrow
      //   362: astore_2
      //   363: aload 9
      //   365: astore_1
      //   366: goto -37 -> 329
      //
      // Exception table:
      //   from	to	target	type
      //   109	150	211	java/io/IOException
      //   155	208	211	java/io/IOException
      //   7	34	256	java/io/IOException
      //   41	53	256	java/io/IOException
      //   305	309	312	java/lang/InterruptedException
      //   7	34	328	finally
      //   41	53	328	finally
      //   53	62	328	finally
      //   69	87	328	finally
      //   95	104	328	finally
      //   109	150	328	finally
      //   155	208	328	finally
      //   213	224	328	finally
      //   258	269	328	finally
      //   305	309	328	finally
      //   314	325	328	finally
      //   87	92	362	finally
    }
  };
  private Thread managerThread;
  private final LinkedBlockingQueue<PacketData> packetsToSend = new LinkedBlockingQueue();
  private final ByteBuffer readBuffer;
  private final Runnable sendingTask = new Runnable()
  {
    public void run()
    {
      try
      {
        while (AbstractIpConnection.this.connectionStatus.get() == 2)
        {
          AbstractIpConnection.PacketData localPacketData = (AbstractIpConnection.PacketData)AbstractIpConnection.this.packetsToSend.take();
          try
          {
            AbstractIpConnection.this.send(localPacketData);
          }
          catch (IOException localIOException)
          {
            Log.e(AbstractIpConnection.TAG, "Error occurred while sending packet.", localIOException);
          }
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        Log.e(AbstractIpConnection.TAG, "Dispatching thread was interrupted.", localInterruptedException);
        return;
        return;
      }
      finally
      {
        AbstractIpConnection.this.disconnect();
        Log.i(AbstractIpConnection.TAG, "Exiting packet dispatcher thread.");
      }
    }
  };

  public AbstractIpConnection()
  {
    this(false, false);
  }

  public AbstractIpConnection(int paramInt)
  {
    this(paramInt, false, false);
  }

  public AbstractIpConnection(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.readBuffer = ByteBuffer.allocate(paramInt);
    this.isReadingDisabled = paramBoolean2;
    this.isSendingDisabled = paramBoolean1;
  }

  public AbstractIpConnection(boolean paramBoolean1, boolean paramBoolean2)
  {
    this(4096, paramBoolean1, paramBoolean2);
  }

  protected abstract void close()
    throws IOException;

  public void connect()
  {
    if (this.connectionStatus.compareAndSet(0, 1))
    {
      Log.i(TAG, "Starting manager thread.");
      this.managerThread = new Thread(this.managerTask, "IP Connection-Manager Thread");
      this.managerThread.setPriority(10);
      this.managerThread.start();
    }
  }

  public void disconnect()
  {
    if ((this.connectionStatus.get() == 0) || (this.managerThread == null));
    while (true)
    {
      return;
      this.connectionStatus.set(0);
      if ((this.managerThread != null) && (this.managerThread.isAlive()) && (!this.managerThread.isInterrupted()))
        this.managerThread.interrupt();
      try
      {
        close();
        if (this.ipConnectionListener == null)
          continue;
        this.ipConnectionListener.onIpDisconnected();
        return;
      }
      catch (IOException localIOException)
      {
        while (true)
          Log.e(TAG, "Error occurred while closing ip connection.", localIOException);
      }
    }
  }

  public int getConnectionStatus()
  {
    return this.connectionStatus.get();
  }

  protected abstract void open()
    throws IOException;

  protected abstract int read(ByteBuffer paramByteBuffer)
    throws IOException;

  protected abstract void send(PacketData paramPacketData)
    throws IOException;

  public void sendPacket(byte[] paramArrayOfByte, int paramInt)
  {
    if ((paramArrayOfByte == null) || (paramInt <= 0))
      return;
    this.packetsToSend.offer(new PacketData(paramInt, paramArrayOfByte));
  }

  public void setIpConnectionListener(IpConnectionListener paramIpConnectionListener)
  {
    this.ipConnectionListener = paramIpConnectionListener;
  }

  protected static final class PacketData
  {
    public final byte[] data;
    public final int dataLength;

    public PacketData(int paramInt, byte[] paramArrayOfByte)
    {
      this.dataLength = paramInt;
      this.data = paramArrayOfByte;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.connection.AbstractIpConnection
 * JD-Core Version:    0.6.2
 */