package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Http2
  implements Variant
{
  private static final ByteString CONNECTION_PREFACE = ByteString.encodeUtf8("PRI * HTTP/2.0\r\n\r\nSM\r\n\r\n");
  static final byte FLAG_ACK = 1;
  static final byte FLAG_COMPRESSED = 32;
  static final byte FLAG_END_HEADERS = 4;
  static final byte FLAG_END_PUSH_PROMISE = 4;
  static final byte FLAG_END_STREAM = 1;
  static final byte FLAG_NONE = 0;
  static final byte FLAG_PADDED = 8;
  static final byte FLAG_PRIORITY = 32;
  static final int INITIAL_MAX_FRAME_SIZE = 16384;
  static final byte TYPE_CONTINUATION = 9;
  static final byte TYPE_DATA = 0;
  static final byte TYPE_GOAWAY = 7;
  static final byte TYPE_HEADERS = 1;
  static final byte TYPE_PING = 6;
  static final byte TYPE_PRIORITY = 2;
  static final byte TYPE_PUSH_PROMISE = 5;
  static final byte TYPE_RST_STREAM = 3;
  static final byte TYPE_SETTINGS = 4;
  static final byte TYPE_WINDOW_UPDATE = 8;
  private static final Logger logger = Logger.getLogger(FrameLogger.class.getName());

  private static IllegalArgumentException illegalArgument(String paramString, Object[] paramArrayOfObject)
  {
    throw new IllegalArgumentException(String.format(paramString, paramArrayOfObject));
  }

  private static IOException ioException(String paramString, Object[] paramArrayOfObject)
    throws IOException
  {
    throw new IOException(String.format(paramString, paramArrayOfObject));
  }

  private static int lengthWithoutPadding(int paramInt, byte paramByte, short paramShort)
    throws IOException
  {
    if ((paramByte & 0x8) != 0)
      paramInt--;
    if (paramShort > paramInt)
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Short.valueOf(paramShort);
      arrayOfObject[1] = Integer.valueOf(paramInt);
      throw ioException("PROTOCOL_ERROR padding %s > remaining length %s", arrayOfObject);
    }
    return (short)(paramInt - paramShort);
  }

  private static int readMedium(BufferedSource paramBufferedSource)
    throws IOException
  {
    return (0xFF & paramBufferedSource.readByte()) << 16 | (0xFF & paramBufferedSource.readByte()) << 8 | 0xFF & paramBufferedSource.readByte();
  }

  private static void writeMedium(BufferedSink paramBufferedSink, int paramInt)
    throws IOException
  {
    paramBufferedSink.writeByte(0xFF & paramInt >>> 16);
    paramBufferedSink.writeByte(0xFF & paramInt >>> 8);
    paramBufferedSink.writeByte(paramInt & 0xFF);
  }

  public Protocol getProtocol()
  {
    return Protocol.HTTP_2;
  }

  public FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean)
  {
    return new Reader(paramBufferedSource, 4096, paramBoolean);
  }

  public FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean)
  {
    return new Writer(paramBufferedSink, paramBoolean);
  }

  static final class ContinuationSource
    implements Source
  {
    byte flags;
    int left;
    int length;
    short padding;
    private final BufferedSource source;
    int streamId;

    public ContinuationSource(BufferedSource paramBufferedSource)
    {
      this.source = paramBufferedSource;
    }

    private void readContinuationHeader()
      throws IOException
    {
      int i = this.streamId;
      int j = Http2.readMedium(this.source);
      this.left = j;
      this.length = j;
      byte b = (byte)(0xFF & this.source.readByte());
      this.flags = ((byte)(0xFF & this.source.readByte()));
      if (Http2.logger.isLoggable(Level.FINE))
        Http2.logger.fine(Http2.FrameLogger.formatHeader(true, this.streamId, this.length, b, this.flags));
      this.streamId = (0x7FFFFFFF & this.source.readInt());
      if (b != 9)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Byte.valueOf(b);
        throw Http2.ioException("%s != TYPE_CONTINUATION", arrayOfObject);
      }
      if (this.streamId != i)
        throw Http2.ioException("TYPE_CONTINUATION streamId changed", new Object[0]);
    }

    public void close()
      throws IOException
    {
    }

    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      while (this.left == 0)
      {
        this.source.skip(this.padding);
        this.padding = 0;
        if ((0x4 & this.flags) != 0)
          return -1L;
        readContinuationHeader();
      }
      long l = this.source.read(paramBuffer, Math.min(paramLong, this.left));
      if (l == -1L)
        return -1L;
      this.left = ((int)(this.left - l));
      return l;
    }

    public Timeout timeout()
    {
      return this.source.timeout();
    }
  }

  static final class FrameLogger
  {
    private static final String[] BINARY;
    private static final String[] FLAGS;
    private static final String[] TYPES = { "DATA", "HEADERS", "PRIORITY", "RST_STREAM", "SETTINGS", "PUSH_PROMISE", "PING", "GOAWAY", "WINDOW_UPDATE", "CONTINUATION" };

    static
    {
      FLAGS = new String[64];
      BINARY = new String[256];
      for (int i = 0; i < BINARY.length; i++)
      {
        String[] arrayOfString = BINARY;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.toBinaryString(i);
        arrayOfString[i] = String.format("%8s", arrayOfObject).replace(' ', '0');
      }
      FLAGS[0] = "";
      FLAGS[1] = "END_STREAM";
      int[] arrayOfInt1 = { 1 };
      FLAGS[8] = "PADDED";
      int j = arrayOfInt1.length;
      for (int k = 0; k < j; k++)
      {
        int i6 = arrayOfInt1[k];
        FLAGS[(i6 | 0x8)] = (FLAGS[i6] + "|PADDED");
      }
      FLAGS[4] = "END_HEADERS";
      FLAGS[32] = "PRIORITY";
      FLAGS[36] = "END_HEADERS|PRIORITY";
      for (int i2 : new int[] { 4, 32, 36 })
      {
        int i3 = arrayOfInt1.length;
        for (int i4 = 0; i4 < i3; i4++)
        {
          int i5 = arrayOfInt1[i4];
          FLAGS[(i5 | i2)] = (FLAGS[i5] + '|' + FLAGS[i2]);
          FLAGS[(0x8 | (i5 | i2))] = (FLAGS[i5] + '|' + FLAGS[i2] + "|PADDED");
        }
      }
      for (int i1 = 0; i1 < FLAGS.length; i1++)
        if (FLAGS[i1] == null)
          FLAGS[i1] = BINARY[i1];
    }

    static String formatFlags(byte paramByte1, byte paramByte2)
    {
      if (paramByte2 == 0)
        return "";
      switch (paramByte1)
      {
      case 5:
      default:
        if (paramByte2 >= FLAGS.length)
          break;
      case 4:
      case 6:
      case 2:
      case 3:
      case 7:
      case 8:
      }
      for (String str = FLAGS[paramByte2]; (paramByte1 == 5) && ((paramByte2 & 0x4) != 0); str = BINARY[paramByte2])
      {
        return str.replace("HEADERS", "PUSH_PROMISE");
        if (paramByte2 == 1)
          return "ACK";
        return BINARY[paramByte2];
        return BINARY[paramByte2];
      }
      if ((paramByte1 == 0) && ((paramByte2 & 0x20) != 0))
        return str.replace("PRIORITY", "COMPRESSED");
      return str;
    }

    static String formatHeader(boolean paramBoolean, int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
    {
      String str1;
      String str2;
      Object[] arrayOfObject2;
      if (paramByte1 < TYPES.length)
      {
        str1 = TYPES[paramByte1];
        str2 = formatFlags(paramByte1, paramByte2);
        arrayOfObject2 = new Object[5];
        if (!paramBoolean)
          break label105;
      }
      label105: for (String str3 = "<<"; ; str3 = ">>")
      {
        arrayOfObject2[0] = str3;
        arrayOfObject2[1] = Integer.valueOf(paramInt1);
        arrayOfObject2[2] = Integer.valueOf(paramInt2);
        arrayOfObject2[3] = str1;
        arrayOfObject2[4] = str2;
        return String.format("%s 0x%08x %5d %-13s %s", arrayOfObject2);
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Byte.valueOf(paramByte1);
        str1 = String.format("0x%02x", arrayOfObject1);
        break;
      }
    }
  }

  static final class Reader
    implements FrameReader
  {
    private final boolean client;
    private final Http2.ContinuationSource continuation;
    final Hpack.Reader hpackReader;
    private final BufferedSource source;

    Reader(BufferedSource paramBufferedSource, int paramInt, boolean paramBoolean)
    {
      this.source = paramBufferedSource;
      this.client = paramBoolean;
      this.continuation = new Http2.ContinuationSource(this.source);
      this.hpackReader = new Hpack.Reader(paramInt, this.continuation);
    }

    private void readData(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      boolean bool1 = true;
      boolean bool2;
      if ((paramByte & 0x1) != 0)
      {
        bool2 = bool1;
        if ((paramByte & 0x20) == 0)
          break label41;
      }
      while (true)
      {
        if (!bool1)
          break label47;
        throw Http2.ioException("PROTOCOL_ERROR: FLAG_COMPRESSED without SETTINGS_COMPRESS_DATA", new Object[0]);
        bool2 = false;
        break;
        label41: bool1 = false;
      }
      label47: int i = paramByte & 0x8;
      short s = 0;
      if (i != 0)
        s = (short)(0xFF & this.source.readByte());
      int j = Http2.lengthWithoutPadding(paramInt1, paramByte, s);
      paramHandler.data(bool2, paramInt2, this.source, j);
      this.source.skip(s);
    }

    private void readGoAway(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 < 8)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt1);
        throw Http2.ioException("TYPE_GOAWAY length < 8: %s", arrayOfObject2);
      }
      if (paramInt2 != 0)
        throw Http2.ioException("TYPE_GOAWAY streamId != 0", new Object[0]);
      int i = this.source.readInt();
      int j = this.source.readInt();
      int k = paramInt1 - 8;
      ErrorCode localErrorCode = ErrorCode.fromHttp2(j);
      if (localErrorCode == null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(j);
        throw Http2.ioException("TYPE_GOAWAY unexpected error code: %d", arrayOfObject1);
      }
      ByteString localByteString = ByteString.EMPTY;
      if (k > 0)
        localByteString = this.source.readByteString(k);
      paramHandler.goAway(i, localErrorCode, localByteString);
    }

    private List<Header> readHeaderBlock(int paramInt1, short paramShort, byte paramByte, int paramInt2)
      throws IOException
    {
      Http2.ContinuationSource localContinuationSource = this.continuation;
      this.continuation.left = paramInt1;
      localContinuationSource.length = paramInt1;
      this.continuation.padding = paramShort;
      this.continuation.flags = paramByte;
      this.continuation.streamId = paramInt2;
      this.hpackReader.readHeaders();
      return this.hpackReader.getAndResetHeaderList();
    }

    private void readHeaders(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt2 == 0)
        throw Http2.ioException("PROTOCOL_ERROR: TYPE_HEADERS streamId == 0", new Object[0]);
      boolean bool;
      if ((paramByte & 0x1) != 0)
      {
        bool = true;
        if ((paramByte & 0x8) == 0)
          break label102;
      }
      label102: for (short s = (short)(0xFF & this.source.readByte()); ; s = 0)
      {
        if ((paramByte & 0x20) != 0)
        {
          readPriority(paramHandler, paramInt2);
          paramInt1 -= 5;
        }
        paramHandler.headers(false, bool, paramInt2, -1, readHeaderBlock(Http2.lengthWithoutPadding(paramInt1, paramByte, s), s, paramByte, paramInt2), HeadersMode.HTTP_20_HEADERS);
        return;
        bool = false;
        break;
      }
    }

    private void readPing(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      boolean bool = true;
      if (paramInt1 != 8)
      {
        Object[] arrayOfObject = new Object[bool];
        arrayOfObject[0] = Integer.valueOf(paramInt1);
        throw Http2.ioException("TYPE_PING length != 8: %s", arrayOfObject);
      }
      if (paramInt2 != 0)
        throw Http2.ioException("TYPE_PING streamId != 0", new Object[0]);
      int i = this.source.readInt();
      int j = this.source.readInt();
      if ((paramByte & 0x1) != 0);
      while (true)
      {
        paramHandler.ping(bool, i, j);
        return;
        bool = false;
      }
    }

    private void readPriority(FrameReader.Handler paramHandler, int paramInt)
      throws IOException
    {
      int i = this.source.readInt();
      if ((0x80000000 & i) != 0);
      for (boolean bool = true; ; bool = false)
      {
        paramHandler.priority(paramInt, i & 0x7FFFFFFF, 1 + (0xFF & this.source.readByte()), bool);
        return;
      }
    }

    private void readPriority(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 != 5)
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt1);
        throw Http2.ioException("TYPE_PRIORITY length: %d != 5", arrayOfObject);
      }
      if (paramInt2 == 0)
        throw Http2.ioException("TYPE_PRIORITY streamId == 0", new Object[0]);
      readPriority(paramHandler, paramInt2);
    }

    private void readPushPromise(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt2 == 0)
        throw Http2.ioException("PROTOCOL_ERROR: TYPE_PUSH_PROMISE streamId == 0", new Object[0]);
      int i = paramByte & 0x8;
      short s = 0;
      if (i != 0)
        s = (short)(0xFF & this.source.readByte());
      paramHandler.pushPromise(paramInt2, 0x7FFFFFFF & this.source.readInt(), readHeaderBlock(Http2.lengthWithoutPadding(paramInt1 - 4, paramByte, s), s, paramByte, paramInt2));
    }

    private void readRstStream(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 != 4)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt1);
        throw Http2.ioException("TYPE_RST_STREAM length: %d != 4", arrayOfObject2);
      }
      if (paramInt2 == 0)
        throw Http2.ioException("TYPE_RST_STREAM streamId == 0", new Object[0]);
      int i = this.source.readInt();
      ErrorCode localErrorCode = ErrorCode.fromHttp2(i);
      if (localErrorCode == null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(i);
        throw Http2.ioException("TYPE_RST_STREAM unexpected error code: %d", arrayOfObject1);
      }
      paramHandler.rstStream(paramInt2, localErrorCode);
    }

    private void readSettings(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt2 != 0)
        throw Http2.ioException("TYPE_SETTINGS streamId != 0", new Object[0]);
      if ((paramByte & 0x1) != 0)
      {
        if (paramInt1 != 0)
          throw Http2.ioException("FRAME_SIZE_ERROR ack frame should be empty!", new Object[0]);
        paramHandler.ackSettings();
      }
      Settings localSettings;
      do
      {
        return;
        if (paramInt1 % 6 != 0)
        {
          Object[] arrayOfObject3 = new Object[1];
          arrayOfObject3[0] = Integer.valueOf(paramInt1);
          throw Http2.ioException("TYPE_SETTINGS length %% 6 != 0: %s", arrayOfObject3);
        }
        localSettings = new Settings();
        int i = 0;
        if (i < paramInt1)
        {
          short s = this.source.readShort();
          int j = this.source.readInt();
          switch (s)
          {
          default:
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = Short.valueOf(s);
            throw Http2.ioException("PROTOCOL_ERROR invalid settings id: %s", arrayOfObject2);
          case 2:
            if ((j != 0) && (j != 1))
              throw Http2.ioException("PROTOCOL_ERROR SETTINGS_ENABLE_PUSH != 0 or 1", new Object[0]);
            break;
          case 3:
            s = 4;
          case 1:
          case 6:
          case 4:
          case 5:
          }
          do
          {
            do
            {
              localSettings.set(s, 0, j);
              i += 6;
              break;
              s = 7;
            }
            while (j >= 0);
            throw Http2.ioException("PROTOCOL_ERROR SETTINGS_INITIAL_WINDOW_SIZE > 2^31 - 1", new Object[0]);
          }
          while ((j >= 16384) && (j <= 16777215));
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = Integer.valueOf(j);
          throw Http2.ioException("PROTOCOL_ERROR SETTINGS_MAX_FRAME_SIZE: %s", arrayOfObject1);
        }
        paramHandler.settings(false, localSettings);
      }
      while (localSettings.getHeaderTableSize() < 0);
      this.hpackReader.headerTableSizeSetting(localSettings.getHeaderTableSize());
    }

    private void readWindowUpdate(FrameReader.Handler paramHandler, int paramInt1, byte paramByte, int paramInt2)
      throws IOException
    {
      if (paramInt1 != 4)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt1);
        throw Http2.ioException("TYPE_WINDOW_UPDATE length !=4: %s", arrayOfObject2);
      }
      long l = 0x7FFFFFFF & this.source.readInt();
      if (l == 0L)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Long.valueOf(l);
        throw Http2.ioException("windowSizeIncrement was 0", arrayOfObject1);
      }
      paramHandler.windowUpdate(paramInt2, l);
    }

    public void close()
      throws IOException
    {
      this.source.close();
    }

    public boolean nextFrame(FrameReader.Handler paramHandler)
      throws IOException
    {
      int i;
      try
      {
        this.source.require(9L);
        i = Http2.readMedium(this.source);
        if ((i < 0) || (i > 16384))
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Integer.valueOf(i);
          throw Http2.ioException("FRAME_SIZE_ERROR: %s", arrayOfObject);
        }
      }
      catch (IOException localIOException)
      {
        return false;
      }
      byte b1 = (byte)(0xFF & this.source.readByte());
      byte b2 = (byte)(0xFF & this.source.readByte());
      int j = 0x7FFFFFFF & this.source.readInt();
      if (Http2.logger.isLoggable(Level.FINE))
        Http2.logger.fine(Http2.FrameLogger.formatHeader(true, j, i, b1, b2));
      switch (b1)
      {
      default:
        this.source.skip(i);
        return true;
      case 0:
        readData(paramHandler, i, b2, j);
        return true;
      case 1:
        readHeaders(paramHandler, i, b2, j);
        return true;
      case 2:
        readPriority(paramHandler, i, b2, j);
        return true;
      case 3:
        readRstStream(paramHandler, i, b2, j);
        return true;
      case 4:
        readSettings(paramHandler, i, b2, j);
        return true;
      case 5:
        readPushPromise(paramHandler, i, b2, j);
        return true;
      case 6:
        readPing(paramHandler, i, b2, j);
        return true;
      case 7:
        readGoAway(paramHandler, i, b2, j);
        return true;
      case 8:
      }
      readWindowUpdate(paramHandler, i, b2, j);
      return true;
    }

    public void readConnectionPreface()
      throws IOException
    {
      if (this.client);
      ByteString localByteString;
      do
      {
        return;
        localByteString = this.source.readByteString(Http2.CONNECTION_PREFACE.size());
        if (Http2.logger.isLoggable(Level.FINE))
        {
          Logger localLogger = Http2.logger;
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = localByteString.hex();
          localLogger.fine(String.format("<< CONNECTION %s", arrayOfObject2));
        }
      }
      while (Http2.CONNECTION_PREFACE.equals(localByteString));
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = localByteString.utf8();
      throw Http2.ioException("Expected a connection header but was %s", arrayOfObject1);
    }
  }

  static final class Writer
    implements FrameWriter
  {
    private final boolean client;
    private boolean closed;
    private final Buffer hpackBuffer;
    private final Hpack.Writer hpackWriter;
    private int maxFrameSize;
    private final BufferedSink sink;

    Writer(BufferedSink paramBufferedSink, boolean paramBoolean)
    {
      this.sink = paramBufferedSink;
      this.client = paramBoolean;
      this.hpackBuffer = new Buffer();
      this.hpackWriter = new Hpack.Writer(this.hpackBuffer);
      this.maxFrameSize = 16384;
    }

    private void writeContinuationFrames(int paramInt, long paramLong)
      throws IOException
    {
      if (paramLong > 0L)
      {
        int i = (int)Math.min(this.maxFrameSize, paramLong);
        paramLong -= i;
        if (paramLong == 0L);
        for (byte b = 4; ; b = 0)
        {
          frameHeader(paramInt, i, (byte)9, b);
          this.sink.write(this.hpackBuffer, i);
          break;
        }
      }
    }

    public void ackSettings(Settings paramSettings)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      this.maxFrameSize = paramSettings.getMaxFrameSize(this.maxFrameSize);
      frameHeader(0, 0, (byte)4, (byte)1);
      this.sink.flush();
    }

    public void close()
      throws IOException
    {
      try
      {
        this.closed = true;
        this.sink.close();
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public void connectionPreface()
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      boolean bool = this.client;
      if (!bool);
      while (true)
      {
        return;
        if (Http2.logger.isLoggable(Level.FINE))
        {
          Logger localLogger = Http2.logger;
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Http2.CONNECTION_PREFACE.hex();
          localLogger.fine(String.format(">> CONNECTION %s", arrayOfObject));
        }
        this.sink.write(Http2.CONNECTION_PREFACE.toByteArray());
        this.sink.flush();
      }
    }

    public void data(boolean paramBoolean, int paramInt1, Buffer paramBuffer, int paramInt2)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      byte b = 0;
      if (paramBoolean)
        b = (byte)1;
      dataFrame(paramInt1, b, paramBuffer, paramInt2);
    }

    void dataFrame(int paramInt1, byte paramByte, Buffer paramBuffer, int paramInt2)
      throws IOException
    {
      frameHeader(paramInt1, paramInt2, (byte)0, paramByte);
      if (paramInt2 > 0)
        this.sink.write(paramBuffer, paramInt2);
    }

    public void flush()
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      this.sink.flush();
    }

    void frameHeader(int paramInt1, int paramInt2, byte paramByte1, byte paramByte2)
      throws IOException
    {
      if (Http2.logger.isLoggable(Level.FINE))
        Http2.logger.fine(Http2.FrameLogger.formatHeader(false, paramInt1, paramInt2, paramByte1, paramByte2));
      if (paramInt2 > this.maxFrameSize)
      {
        Object[] arrayOfObject2 = new Object[2];
        arrayOfObject2[0] = Integer.valueOf(this.maxFrameSize);
        arrayOfObject2[1] = Integer.valueOf(paramInt2);
        throw Http2.illegalArgument("FRAME_SIZE_ERROR length > %d: %d", arrayOfObject2);
      }
      if ((0x80000000 & paramInt1) != 0)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(paramInt1);
        throw Http2.illegalArgument("reserved bit set: %s", arrayOfObject1);
      }
      Http2.writeMedium(this.sink, paramInt2);
      this.sink.writeByte(paramByte1 & 0xFF);
      this.sink.writeByte(paramByte2 & 0xFF);
      this.sink.writeInt(0x7FFFFFFF & paramInt1);
    }

    public void goAway(int paramInt, ErrorCode paramErrorCode, byte[] paramArrayOfByte)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      if (paramErrorCode.httpCode == -1)
        throw Http2.illegalArgument("errorCode.httpCode == -1", new Object[0]);
      frameHeader(0, 8 + paramArrayOfByte.length, (byte)7, (byte)0);
      this.sink.writeInt(paramInt);
      this.sink.writeInt(paramErrorCode.httpCode);
      if (paramArrayOfByte.length > 0)
        this.sink.write(paramArrayOfByte);
      this.sink.flush();
    }

    public void headers(int paramInt, List<Header> paramList)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      headers(false, paramInt, paramList);
    }

    void headers(boolean paramBoolean, int paramInt, List<Header> paramList)
      throws IOException
    {
      if (this.closed)
        throw new IOException("closed");
      if (this.hpackBuffer.size() != 0L)
        throw new IllegalStateException();
      this.hpackWriter.writeHeaders(paramList);
      long l = this.hpackBuffer.size();
      int i = (int)Math.min(this.maxFrameSize, l);
      if (l == i);
      for (byte b = 4; ; b = 0)
      {
        if (paramBoolean)
          b = (byte)(b | 0x1);
        frameHeader(paramInt, i, (byte)1, b);
        this.sink.write(this.hpackBuffer, i);
        if (l > i)
          writeContinuationFrames(paramInt, l - i);
        return;
      }
    }

    public int maxDataLength()
    {
      return this.maxFrameSize;
    }

    public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      if (paramBoolean);
      for (byte b = 1; ; b = 0)
      {
        frameHeader(0, 8, (byte)6, b);
        this.sink.writeInt(paramInt1);
        this.sink.writeInt(paramInt2);
        this.sink.flush();
        return;
      }
    }

    public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      if (this.hpackBuffer.size() != 0L)
        throw new IllegalStateException();
      this.hpackWriter.writeHeaders(paramList);
      long l = this.hpackBuffer.size();
      int i = (int)Math.min(-4 + this.maxFrameSize, l);
      if (l == i);
      for (byte b = 4; ; b = 0)
      {
        frameHeader(paramInt1, i + 4, (byte)5, b);
        this.sink.writeInt(0x7FFFFFFF & paramInt2);
        this.sink.write(this.hpackBuffer, i);
        if (l > i)
          writeContinuationFrames(paramInt1, l - i);
        return;
      }
    }

    public void rstStream(int paramInt, ErrorCode paramErrorCode)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      if (paramErrorCode.spdyRstCode == -1)
        throw new IllegalArgumentException();
      frameHeader(paramInt, 4, (byte)3, (byte)0);
      this.sink.writeInt(paramErrorCode.httpCode);
      this.sink.flush();
    }

    public void settings(Settings paramSettings)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      frameHeader(0, 6 * paramSettings.size(), (byte)4, (byte)0);
      int i = 0;
      if (i < 10)
        if (paramSettings.isSet(i))
          break label105;
      while (true)
      {
        this.sink.writeShort(j);
        this.sink.writeInt(paramSettings.get(i));
        break label99;
        this.sink.flush();
        return;
        label99: i++;
        break;
        label105: int j = i;
        if (j == 4)
          j = 3;
        else if (j == 7)
          j = 4;
      }
    }

    public void synReply(boolean paramBoolean, int paramInt, List<Header> paramList)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      headers(paramBoolean, paramInt, paramList);
    }

    public void synStream(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, List<Header> paramList)
      throws IOException
    {
      if (paramBoolean2)
        try
        {
          throw new UnsupportedOperationException();
        }
        finally
        {
        }
      if (this.closed)
        throw new IOException("closed");
      headers(paramBoolean1, paramInt1, paramList);
    }

    public void windowUpdate(int paramInt, long paramLong)
      throws IOException
    {
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      if ((paramLong == 0L) || (paramLong > 2147483647L))
      {
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Long.valueOf(paramLong);
        throw Http2.illegalArgument("windowSizeIncrement == 0 || windowSizeIncrement > 0x7fffffffL: %s", arrayOfObject);
      }
      frameHeader(paramInt, 4, (byte)8, (byte)0);
      this.sink.writeInt((int)paramLong);
      this.sink.flush();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Http2
 * JD-Core Version:    0.6.2
 */