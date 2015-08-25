package com.segment.analytics;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.JsonWriter;
import com.segment.analytics.internal.AbstractIntegration;
import com.segment.analytics.internal.Utils;
import com.segment.analytics.internal.Utils.AnalyticsThreadFactory;
import com.segment.analytics.internal.model.payloads.AliasPayload;
import com.segment.analytics.internal.model.payloads.BasePayload;
import com.segment.analytics.internal.model.payloads.GroupPayload;
import com.segment.analytics.internal.model.payloads.IdentifyPayload;
import com.segment.analytics.internal.model.payloads.ScreenPayload;
import com.segment.analytics.internal.model.payloads.TrackPayload;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

class SegmentDispatcher extends AbstractIntegration
{
  private static final int MAX_BATCH_SIZE = 475000;
  static final int MAX_PAYLOAD_SIZE = 15000;
  static final int MAX_QUEUE_SIZE = 1000;
  static final String SEGMENT_KEY = "Segment.io";
  private static final String SEGMENT_THREAD_NAME = "SegmentAnalytics-SegmentDispatcher";
  private static final Charset UTF_8 = Charset.forName("UTF-8");
  private final Map<String, Boolean> bundledIntegrations;
  private final Cartographer cartographer;
  private final Client client;
  private final Context context;
  private final Object flushLock = new Object();
  private final int flushQueueSize;
  private final ScheduledExecutorService flushScheduler;
  private final Handler handler;
  private final Analytics.LogLevel logLevel;
  private final ExecutorService networkExecutor;
  private final QueueFile queueFile;
  private final HandlerThread segmentThread;
  private final Stats stats;

  SegmentDispatcher(Context paramContext, Client paramClient, Cartographer paramCartographer, ExecutorService paramExecutorService, QueueFile paramQueueFile, Stats paramStats, Map<String, Boolean> paramMap, long paramLong, int paramInt, Analytics.LogLevel paramLogLevel)
  {
    this.context = paramContext;
    this.client = paramClient;
    this.networkExecutor = paramExecutorService;
    this.queueFile = paramQueueFile;
    this.stats = paramStats;
    this.logLevel = paramLogLevel;
    this.bundledIntegrations = paramMap;
    this.cartographer = paramCartographer;
    this.flushQueueSize = paramInt;
    this.flushScheduler = Executors.newScheduledThreadPool(1, new Utils.AnalyticsThreadFactory());
    this.segmentThread = new HandlerThread("SegmentAnalytics-SegmentDispatcher", 10);
    this.segmentThread.start();
    this.handler = new SegmentDispatcherHandler(this.segmentThread.getLooper(), this);
    if (paramQueueFile.size() >= paramInt);
    for (long l = 0L; ; l = paramLong)
    {
      this.flushScheduler.scheduleAtFixedRate(new Runnable()
      {
        public void run()
        {
          SegmentDispatcher.this.flush();
        }
      }
      , l, paramLong, TimeUnit.MILLISECONDS);
      return;
    }
  }

  static SegmentDispatcher create(Context paramContext, Client paramClient, Cartographer paramCartographer, ExecutorService paramExecutorService, Stats paramStats, Map<String, Boolean> paramMap, String paramString, long paramLong, int paramInt, Analytics.LogLevel paramLogLevel)
  {
    try
    {
      QueueFile localQueueFile = createQueueFile(paramContext.getDir("segment-disk-queue", 0), paramString);
      SegmentDispatcher localSegmentDispatcher = new SegmentDispatcher(paramContext, paramClient, paramCartographer, paramExecutorService, localQueueFile, paramStats, paramMap, paramLong, paramInt, paramLogLevel);
      return localSegmentDispatcher;
    }
    catch (IOException localIOException)
    {
      throw new IOError(localIOException);
    }
    finally
    {
    }
  }

  private static QueueFile createQueueFile(File paramFile, String paramString)
    throws IOException
  {
    Utils.createDirectory(paramFile);
    File localFile = new File(paramFile, paramString);
    try
    {
      QueueFile localQueueFile = new QueueFile(localFile);
      return localQueueFile;
    }
    catch (IOException localIOException)
    {
      if (localFile.delete())
        return new QueueFile(localFile);
    }
    throw new IOException("Could not create queue file (" + paramString + ") in " + paramFile + ".");
  }

  private void dispatchEnqueue(BasePayload paramBasePayload)
  {
    this.handler.sendMessage(this.handler.obtainMessage(0, paramBasePayload));
  }

  // ERROR //
  private void performFlush()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 230	com/segment/analytics/SegmentDispatcher:shouldFlush	()Z
    //   4: ifne +4 -> 8
    //   7: return
    //   8: aload_0
    //   9: getfield 79	com/segment/analytics/SegmentDispatcher:logLevel	Lcom/segment/analytics/Analytics$LogLevel;
    //   12: invokevirtual 235	com/segment/analytics/Analytics$LogLevel:log	()Z
    //   15: ifeq +12 -> 27
    //   18: ldc 237
    //   20: iconst_0
    //   21: anewarray 64	java/lang/Object
    //   24: invokestatic 241	com/segment/analytics/internal/Utils:debug	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   27: aconst_null
    //   28: astore_1
    //   29: aload_0
    //   30: getfield 71	com/segment/analytics/SegmentDispatcher:client	Lcom/segment/analytics/Client;
    //   33: invokevirtual 247	com/segment/analytics/Client:upload	()Lcom/segment/analytics/Client$Connection;
    //   36: astore_1
    //   37: new 249	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter
    //   40: dup
    //   41: aload_1
    //   42: getfield 255	com/segment/analytics/Client$Connection:os	Ljava/io/OutputStream;
    //   45: invokespecial 258	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter:<init>	(Ljava/io/OutputStream;)V
    //   48: invokevirtual 262	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter:beginObject	()Lcom/segment/analytics/SegmentDispatcher$BatchPayloadWriter;
    //   51: aload_0
    //   52: getfield 81	com/segment/analytics/SegmentDispatcher:bundledIntegrations	Ljava/util/Map;
    //   55: invokevirtual 266	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter:integrations	(Ljava/util/Map;)Lcom/segment/analytics/SegmentDispatcher$BatchPayloadWriter;
    //   58: invokevirtual 269	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter:beginBatchArray	()Lcom/segment/analytics/SegmentDispatcher$BatchPayloadWriter;
    //   61: astore 4
    //   63: new 271	com/segment/analytics/SegmentDispatcher$PayloadWriter
    //   66: dup
    //   67: aload 4
    //   69: invokespecial 274	com/segment/analytics/SegmentDispatcher$PayloadWriter:<init>	(Lcom/segment/analytics/SegmentDispatcher$BatchPayloadWriter;)V
    //   72: astore 5
    //   74: aload_0
    //   75: getfield 75	com/segment/analytics/SegmentDispatcher:queueFile	Lcom/segment/analytics/QueueFile;
    //   78: aload 5
    //   80: invokevirtual 278	com/segment/analytics/QueueFile:forEach	(Lcom/segment/analytics/QueueFile$ElementVisitor;)I
    //   83: pop
    //   84: aload 4
    //   86: invokevirtual 281	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter:endBatchArray	()Lcom/segment/analytics/SegmentDispatcher$BatchPayloadWriter;
    //   89: invokevirtual 284	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter:endObject	()Lcom/segment/analytics/SegmentDispatcher$BatchPayloadWriter;
    //   92: invokevirtual 287	com/segment/analytics/SegmentDispatcher$BatchPayloadWriter:close	()V
    //   95: aload 5
    //   97: getfield 290	com/segment/analytics/SegmentDispatcher$PayloadWriter:payloadCount	I
    //   100: istore 7
    //   102: aload_1
    //   103: invokevirtual 291	com/segment/analytics/Client$Connection:close	()V
    //   106: aload_1
    //   107: invokestatic 295	com/segment/analytics/internal/Utils:closeQuietly	(Ljava/io/Closeable;)V
    //   110: aload_0
    //   111: getfield 75	com/segment/analytics/SegmentDispatcher:queueFile	Lcom/segment/analytics/QueueFile;
    //   114: iload 7
    //   116: invokevirtual 299	com/segment/analytics/QueueFile:remove	(I)V
    //   119: aload_0
    //   120: getfield 79	com/segment/analytics/SegmentDispatcher:logLevel	Lcom/segment/analytics/Analytics$LogLevel;
    //   123: invokevirtual 235	com/segment/analytics/Analytics$LogLevel:log	()Z
    //   126: ifeq +40 -> 166
    //   129: iconst_2
    //   130: anewarray 64	java/lang/Object
    //   133: astore 12
    //   135: aload 12
    //   137: iconst_0
    //   138: iload 7
    //   140: invokestatic 305	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   143: aastore
    //   144: aload 12
    //   146: iconst_1
    //   147: aload_0
    //   148: getfield 75	com/segment/analytics/SegmentDispatcher:queueFile	Lcom/segment/analytics/QueueFile;
    //   151: invokevirtual 123	com/segment/analytics/QueueFile:size	()I
    //   154: invokestatic 305	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   157: aastore
    //   158: ldc_w 307
    //   161: aload 12
    //   163: invokestatic 241	com/segment/analytics/internal/Utils:debug	(Ljava/lang/String;[Ljava/lang/Object;)V
    //   166: aload_0
    //   167: getfield 77	com/segment/analytics/SegmentDispatcher:stats	Lcom/segment/analytics/Stats;
    //   170: iload 7
    //   172: invokevirtual 312	com/segment/analytics/Stats:dispatchFlush	(I)V
    //   175: aload_0
    //   176: getfield 75	com/segment/analytics/SegmentDispatcher:queueFile	Lcom/segment/analytics/QueueFile;
    //   179: invokevirtual 123	com/segment/analytics/QueueFile:size	()I
    //   182: ifle -175 -> 7
    //   185: aload_0
    //   186: invokespecial 146	com/segment/analytics/SegmentDispatcher:performFlush	()V
    //   189: return
    //   190: astore 8
    //   192: aload_0
    //   193: getfield 79	com/segment/analytics/SegmentDispatcher:logLevel	Lcom/segment/analytics/Analytics$LogLevel;
    //   196: invokevirtual 235	com/segment/analytics/Analytics$LogLevel:log	()Z
    //   199: ifeq -93 -> 106
    //   202: aload 8
    //   204: ldc_w 314
    //   207: iconst_0
    //   208: anewarray 64	java/lang/Object
    //   211: invokestatic 318	com/segment/analytics/internal/Utils:error	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   214: goto -108 -> 106
    //   217: astore_2
    //   218: aload_1
    //   219: invokestatic 295	com/segment/analytics/internal/Utils:closeQuietly	(Ljava/io/Closeable;)V
    //   222: aload_2
    //   223: athrow
    //   224: astore_3
    //   225: aload_0
    //   226: getfield 79	com/segment/analytics/SegmentDispatcher:logLevel	Lcom/segment/analytics/Analytics$LogLevel;
    //   229: invokevirtual 235	com/segment/analytics/Analytics$LogLevel:log	()Z
    //   232: ifeq -225 -> 7
    //   235: aload_3
    //   236: ldc_w 320
    //   239: iconst_0
    //   240: anewarray 64	java/lang/Object
    //   243: invokestatic 318	com/segment/analytics/internal/Utils:error	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   246: return
    //   247: astore 11
    //   249: new 168	java/io/IOError
    //   252: dup
    //   253: new 152	java/io/IOException
    //   256: dup
    //   257: new 190	java/lang/StringBuilder
    //   260: dup
    //   261: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   264: ldc_w 322
    //   267: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   270: iload 7
    //   272: invokevirtual 325	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   275: ldc_w 327
    //   278: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   281: aload_0
    //   282: getfield 75	com/segment/analytics/SegmentDispatcher:queueFile	Lcom/segment/analytics/QueueFile;
    //   285: invokevirtual 202	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   288: invokevirtual 208	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   291: aload 11
    //   293: invokespecial 330	java/io/IOException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   296: invokespecial 171	java/io/IOError:<init>	(Ljava/lang/Throwable;)V
    //   299: athrow
    //   300: astore 9
    //   302: iconst_2
    //   303: anewarray 64	java/lang/Object
    //   306: astore 10
    //   308: aload 10
    //   310: iconst_0
    //   311: iload 7
    //   313: invokestatic 305	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   316: aastore
    //   317: aload 10
    //   319: iconst_1
    //   320: aload_0
    //   321: getfield 75	com/segment/analytics/SegmentDispatcher:queueFile	Lcom/segment/analytics/QueueFile;
    //   324: aastore
    //   325: aload 9
    //   327: ldc_w 332
    //   330: aload 10
    //   332: invokestatic 318	com/segment/analytics/internal/Utils:error	(Ljava/lang/Throwable;Ljava/lang/String;[Ljava/lang/Object;)V
    //   335: aload 9
    //   337: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   102	106	190	com/segment/analytics/Client$UploadException
    //   29	102	217	finally
    //   102	106	217	finally
    //   192	214	217	finally
    //   106	110	224	java/io/IOException
    //   218	224	224	java/io/IOException
    //   110	119	247	java/io/IOException
    //   110	119	300	java/lang/ArrayIndexOutOfBoundsException
  }

  private boolean shouldFlush()
  {
    return (this.queueFile.size() > 0) && (Utils.isConnected(this.context));
  }

  public void alias(AliasPayload paramAliasPayload)
  {
    dispatchEnqueue(paramAliasPayload);
  }

  public void flush()
  {
    this.handler.sendMessage(this.handler.obtainMessage(1));
  }

  public void group(GroupPayload paramGroupPayload)
  {
    dispatchEnqueue(paramGroupPayload);
  }

  public void identify(IdentifyPayload paramIdentifyPayload)
  {
    dispatchEnqueue(paramIdentifyPayload);
  }

  public void initialize(Analytics paramAnalytics, ValueMap paramValueMap)
    throws IllegalStateException
  {
  }

  public String key()
  {
    return "Segment.io";
  }

  void performEnqueue(BasePayload paramBasePayload)
  {
    if (this.queueFile.size() >= 1000);
    while (true)
    {
      String str;
      synchronized (this.flushLock)
      {
        if (this.queueFile.size() >= 1000)
          if (this.logLevel.log())
          {
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = Integer.valueOf(this.queueFile.size());
            Utils.debug("Queue is at max capacity (%s), removing oldest payload.", arrayOfObject3);
          }
        try
        {
          this.queueFile.remove();
          try
          {
            str = this.cartographer.toJson(paramBasePayload);
            if ((!Utils.isNullOrEmpty(str)) && (str.length() <= 15000))
              break label257;
            throw new IOException("Could not serialize payload " + paramBasePayload);
          }
          catch (IOException localIOException1)
          {
            if (this.logLevel.log())
            {
              Object[] arrayOfObject2 = new Object[2];
              arrayOfObject2[0] = paramBasePayload;
              arrayOfObject2[1] = this.queueFile;
              Utils.error(localIOException1, "Could not add payload %s to queue: %s.", arrayOfObject2);
            }
          }
          if (this.logLevel.log())
          {
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = paramBasePayload;
            arrayOfObject1[1] = Integer.valueOf(this.queueFile.size());
            Utils.debug("Enqueued %s payload. Queue size is now : %s.", arrayOfObject1);
          }
          if (this.queueFile.size() >= this.flushQueueSize)
            submitFlush();
          return;
        }
        catch (IOException localIOException2)
        {
          throw new IOError(localIOException2);
        }
      }
      label257: this.queueFile.add(str.getBytes(UTF_8));
    }
  }

  public void screen(ScreenPayload paramScreenPayload)
  {
    dispatchEnqueue(paramScreenPayload);
  }

  void shutdown()
  {
    this.flushScheduler.shutdown();
    this.segmentThread.quit();
    Utils.closeQuietly(this.queueFile);
  }

  void submitFlush()
  {
    if (!shouldFlush())
      return;
    this.networkExecutor.submit(new Runnable()
    {
      public void run()
      {
        synchronized (SegmentDispatcher.this.flushLock)
        {
          SegmentDispatcher.this.performFlush();
          return;
        }
      }
    });
  }

  public void track(TrackPayload paramTrackPayload)
  {
    dispatchEnqueue(paramTrackPayload);
  }

  static class BatchPayloadWriter
    implements Closeable
  {
    private final BufferedWriter bufferedWriter;
    private final JsonWriter jsonWriter;
    private boolean needsComma = false;

    BatchPayloadWriter(OutputStream paramOutputStream)
    {
      this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(paramOutputStream));
      this.jsonWriter = new JsonWriter(this.bufferedWriter);
    }

    BatchPayloadWriter beginBatchArray()
      throws IOException
    {
      this.jsonWriter.name("batch").beginArray();
      this.needsComma = false;
      return this;
    }

    BatchPayloadWriter beginObject()
      throws IOException
    {
      this.jsonWriter.beginObject();
      return this;
    }

    public void close()
      throws IOException
    {
      this.jsonWriter.close();
    }

    BatchPayloadWriter emitPayloadObject(String paramString)
      throws IOException
    {
      if (this.needsComma)
        this.bufferedWriter.write(44);
      while (true)
      {
        this.bufferedWriter.write(paramString);
        return this;
        this.needsComma = true;
      }
    }

    BatchPayloadWriter endBatchArray()
      throws IOException
    {
      if (!this.needsComma)
        throw new IOException("At least one payload must be provided.");
      this.jsonWriter.endArray();
      return this;
    }

    BatchPayloadWriter endObject()
      throws IOException
    {
      this.jsonWriter.name("sentAt").value(Utils.toISO8601Date(new Date())).endObject();
      return this;
    }

    BatchPayloadWriter integrations(Map<String, Boolean> paramMap)
      throws IOException
    {
      if (!Utils.isNullOrEmpty(paramMap))
      {
        this.jsonWriter.name("integrations").beginObject();
        Iterator localIterator = paramMap.entrySet().iterator();
        while (localIterator.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          this.jsonWriter.name((String)localEntry.getKey()).value(((Boolean)localEntry.getValue()).booleanValue());
        }
        this.jsonWriter.endObject();
      }
      return this;
    }
  }

  static class PayloadWriter
    implements QueueFile.ElementVisitor
  {
    int payloadCount;
    int size;
    final SegmentDispatcher.BatchPayloadWriter writer;

    PayloadWriter(SegmentDispatcher.BatchPayloadWriter paramBatchPayloadWriter)
    {
      this.writer = paramBatchPayloadWriter;
    }

    public boolean read(InputStream paramInputStream, int paramInt)
      throws IOException
    {
      int i = paramInt + this.size;
      if (i > 475000)
        return false;
      this.size = i;
      byte[] arrayOfByte = new byte[paramInt];
      paramInputStream.read(arrayOfByte, 0, paramInt);
      this.writer.emitPayloadObject(new String(arrayOfByte, SegmentDispatcher.UTF_8));
      this.payloadCount = (1 + this.payloadCount);
      return true;
    }
  }

  static class SegmentDispatcherHandler extends Handler
  {
    private static final int REQUEST_ENQUEUE = 0;
    static final int REQUEST_FLUSH = 1;
    private final SegmentDispatcher segmentDispatcher;

    SegmentDispatcherHandler(Looper paramLooper, SegmentDispatcher paramSegmentDispatcher)
    {
      super();
      this.segmentDispatcher = paramSegmentDispatcher;
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        throw new AssertionError("Unknown dispatcher message: " + paramMessage.what);
      case 0:
        BasePayload localBasePayload = (BasePayload)paramMessage.obj;
        this.segmentDispatcher.performEnqueue(localBasePayload);
        return;
      case 1:
      }
      this.segmentDispatcher.submitFlush();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.SegmentDispatcher
 * JD-Core Version:    0.6.2
 */