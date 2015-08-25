package com.squareup.okhttp.internal.spdy;

import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.internal.Util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ProtocolException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.zip.Deflater;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.DeflaterSink;
import okio.Okio;

public final class Spdy3
  implements Variant
{
  static final byte[] DICTIONARY;
  static final int FLAG_FIN = 1;
  static final int FLAG_UNIDIRECTIONAL = 2;
  static final int TYPE_DATA = 0;
  static final int TYPE_GOAWAY = 7;
  static final int TYPE_HEADERS = 8;
  static final int TYPE_PING = 6;
  static final int TYPE_RST_STREAM = 3;
  static final int TYPE_SETTINGS = 4;
  static final int TYPE_SYN_REPLY = 2;
  static final int TYPE_SYN_STREAM = 1;
  static final int TYPE_WINDOW_UPDATE = 9;
  static final int VERSION = 3;

  static
  {
    try
    {
      DICTIONARY = "".getBytes(Util.UTF_8.name());
      return;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new AssertionError();
  }

  public Protocol getProtocol()
  {
    return Protocol.SPDY_3;
  }

  public FrameReader newReader(BufferedSource paramBufferedSource, boolean paramBoolean)
  {
    return new Reader(paramBufferedSource, paramBoolean);
  }

  public FrameWriter newWriter(BufferedSink paramBufferedSink, boolean paramBoolean)
  {
    return new Writer(paramBufferedSink, paramBoolean);
  }

  static final class Reader
    implements FrameReader
  {
    private final boolean client;
    private final NameValueBlockReader headerBlockReader;
    private final BufferedSource source;

    Reader(BufferedSource paramBufferedSource, boolean paramBoolean)
    {
      this.source = paramBufferedSource;
      this.headerBlockReader = new NameValueBlockReader(this.source);
      this.client = paramBoolean;
    }

    private static IOException ioException(String paramString, Object[] paramArrayOfObject)
      throws IOException
    {
      throw new IOException(String.format(paramString, paramArrayOfObject));
    }

    private void readGoAway(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramInt2 != 8)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt2);
        throw ioException("TYPE_GOAWAY length: %d != 8", arrayOfObject2);
      }
      int i = 0x7FFFFFFF & this.source.readInt();
      int j = this.source.readInt();
      ErrorCode localErrorCode = ErrorCode.fromSpdyGoAway(j);
      if (localErrorCode == null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(j);
        throw ioException("TYPE_GOAWAY unexpected error code: %d", arrayOfObject1);
      }
      paramHandler.goAway(i, localErrorCode, ByteString.EMPTY);
    }

    private void readHeaders(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      paramHandler.headers(false, false, 0x7FFFFFFF & this.source.readInt(), -1, this.headerBlockReader.readNameValueBlock(paramInt2 - 4), HeadersMode.SPDY_HEADERS);
    }

    private void readPing(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      boolean bool1 = true;
      if (paramInt2 != 4)
      {
        Object[] arrayOfObject = new Object[bool1];
        arrayOfObject[0] = Integer.valueOf(paramInt2);
        throw ioException("TYPE_PING length: %d != 4", arrayOfObject);
      }
      int i = this.source.readInt();
      boolean bool2 = this.client;
      boolean bool3;
      if ((i & 0x1) == bool1)
      {
        bool3 = bool1;
        if (bool2 != bool3)
          break label86;
      }
      while (true)
      {
        paramHandler.ping(bool1, i, 0);
        return;
        bool3 = false;
        break;
        label86: bool1 = false;
      }
    }

    private void readRstStream(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramInt2 != 8)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt2);
        throw ioException("TYPE_RST_STREAM length: %d != 8", arrayOfObject2);
      }
      int i = 0x7FFFFFFF & this.source.readInt();
      int j = this.source.readInt();
      ErrorCode localErrorCode = ErrorCode.fromSpdy3Rst(j);
      if (localErrorCode == null)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Integer.valueOf(j);
        throw ioException("TYPE_RST_STREAM unexpected error code: %d", arrayOfObject1);
      }
      paramHandler.rstStream(i, localErrorCode);
    }

    private void readSettings(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      boolean bool = true;
      int i = this.source.readInt();
      if (paramInt2 != 4 + i * 8)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = Integer.valueOf(paramInt2);
        arrayOfObject[bool] = Integer.valueOf(i);
        throw ioException("TYPE_SETTINGS length: %d != 4 + 8 * %d", arrayOfObject);
      }
      Settings localSettings = new Settings();
      for (int j = 0; j < i; j++)
      {
        int k = this.source.readInt();
        int m = this.source.readInt();
        int n = (0xFF000000 & k) >>> 24;
        localSettings.set(k & 0xFFFFFF, n, m);
      }
      if ((paramInt1 & 0x1) != 0);
      while (true)
      {
        paramHandler.settings(bool, localSettings);
        return;
        bool = false;
      }
    }

    private void readSynReply(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = 0x7FFFFFFF & this.source.readInt();
      List localList = this.headerBlockReader.readNameValueBlock(paramInt2 - 4);
      if ((paramInt1 & 0x1) != 0);
      for (boolean bool = true; ; bool = false)
      {
        paramHandler.headers(false, bool, i, -1, localList, HeadersMode.SPDY_REPLY);
        return;
      }
    }

    private void readSynStream(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      boolean bool1 = true;
      int i = this.source.readInt();
      int j = this.source.readInt();
      int k = i & 0x7FFFFFFF;
      int m = j & 0x7FFFFFFF;
      this.source.readShort();
      List localList = this.headerBlockReader.readNameValueBlock(paramInt2 - 10);
      boolean bool2;
      if ((paramInt1 & 0x1) != 0)
      {
        bool2 = bool1;
        if ((paramInt1 & 0x2) == 0)
          break label104;
      }
      while (true)
      {
        paramHandler.headers(bool1, bool2, k, m, localList, HeadersMode.SPDY_SYN_STREAM);
        return;
        bool2 = false;
        break;
        label104: bool1 = false;
      }
    }

    private void readWindowUpdate(FrameReader.Handler paramHandler, int paramInt1, int paramInt2)
      throws IOException
    {
      if (paramInt2 != 8)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = Integer.valueOf(paramInt2);
        throw ioException("TYPE_WINDOW_UPDATE length: %d != 8", arrayOfObject2);
      }
      int i = this.source.readInt();
      int j = this.source.readInt();
      int k = i & 0x7FFFFFFF;
      long l = j & 0x7FFFFFFF;
      if (l == 0L)
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = Long.valueOf(l);
        throw ioException("windowSizeIncrement was 0", arrayOfObject1);
      }
      paramHandler.windowUpdate(k, l);
    }

    public void close()
      throws IOException
    {
      this.headerBlockReader.close();
    }

    public boolean nextFrame(FrameReader.Handler paramHandler)
      throws IOException
    {
      int i;
      int m;
      int n;
      int i4;
      while (true)
      {
        try
        {
          i = this.source.readInt();
          int j = this.source.readInt();
          if ((0x80000000 & i) != 0)
          {
            k = 1;
            m = (0xFF000000 & j) >>> 24;
            n = j & 0xFFFFFF;
            if (k == 0)
              break label266;
            int i3 = (0x7FFF0000 & i) >>> 16;
            i4 = i & 0xFFFF;
            if (i3 == 3)
              break;
            throw new ProtocolException("version != 3: " + i3);
          }
        }
        catch (IOException localIOException)
        {
          return false;
        }
        int k = 0;
      }
      switch (i4)
      {
      case 5:
      default:
        this.source.skip(n);
        return true;
      case 1:
        readSynStream(paramHandler, m, n);
        return true;
      case 2:
        readSynReply(paramHandler, m, n);
        return true;
      case 3:
        readRstStream(paramHandler, m, n);
        return true;
      case 4:
        readSettings(paramHandler, m, n);
        return true;
      case 6:
        readPing(paramHandler, m, n);
        return true;
      case 7:
        readGoAway(paramHandler, m, n);
        return true;
      case 8:
        readHeaders(paramHandler, m, n);
        return true;
      case 9:
      }
      readWindowUpdate(paramHandler, m, n);
      return true;
      label266: int i1 = i & 0x7FFFFFFF;
      int i2 = m & 0x1;
      boolean bool = false;
      if (i2 != 0)
        bool = true;
      paramHandler.data(bool, i1, this.source, n);
      return true;
    }

    public void readConnectionPreface()
    {
    }
  }

  static final class Writer
    implements FrameWriter
  {
    private final boolean client;
    private boolean closed;
    private final Buffer headerBlockBuffer;
    private final BufferedSink headerBlockOut;
    private final BufferedSink sink;

    Writer(BufferedSink paramBufferedSink, boolean paramBoolean)
    {
      this.sink = paramBufferedSink;
      this.client = paramBoolean;
      Deflater localDeflater = new Deflater();
      localDeflater.setDictionary(Spdy3.DICTIONARY);
      this.headerBlockBuffer = new Buffer();
      this.headerBlockOut = Okio.buffer(new DeflaterSink(this.headerBlockBuffer, localDeflater));
    }

    private void writeNameValueBlockToBuffer(List<Header> paramList)
      throws IOException
    {
      if (this.headerBlockBuffer.size() != 0L)
        throw new IllegalStateException();
      this.headerBlockOut.writeInt(paramList.size());
      int i = 0;
      int j = paramList.size();
      while (i < j)
      {
        ByteString localByteString1 = ((Header)paramList.get(i)).name;
        this.headerBlockOut.writeInt(localByteString1.size());
        this.headerBlockOut.write(localByteString1);
        ByteString localByteString2 = ((Header)paramList.get(i)).value;
        this.headerBlockOut.writeInt(localByteString2.size());
        this.headerBlockOut.write(localByteString2);
        i++;
      }
      this.headerBlockOut.flush();
    }

    public void ackSettings(Settings paramSettings)
    {
    }

    public void close()
      throws IOException
    {
      try
      {
        this.closed = true;
        Util.closeAll(this.sink, this.headerBlockOut);
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }

    public void connectionPreface()
    {
    }

    public void data(boolean paramBoolean, int paramInt1, Buffer paramBuffer, int paramInt2)
      throws IOException
    {
      int i;
      if (paramBoolean)
        i = 1;
      try
      {
        sendDataFrame(paramInt1, i, paramBuffer, paramInt2);
        return;
        i = 0;
      }
      finally
      {
      }
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
      if (paramErrorCode.spdyGoAwayCode == -1)
        throw new IllegalArgumentException("errorCode.spdyGoAwayCode == -1");
      this.sink.writeInt(-2147287033);
      this.sink.writeInt(8);
      this.sink.writeInt(paramInt);
      this.sink.writeInt(paramErrorCode.spdyGoAwayCode);
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
      writeNameValueBlockToBuffer(paramList);
      int i = (int)(4L + this.headerBlockBuffer.size());
      this.sink.writeInt(-2147287032);
      this.sink.writeInt(0x0 | 0xFFFFFF & i);
      this.sink.writeInt(0x7FFFFFFF & paramInt);
      this.sink.writeAll(this.headerBlockBuffer);
    }

    public int maxDataLength()
    {
      return 16383;
    }

    public void ping(boolean paramBoolean, int paramInt1, int paramInt2)
      throws IOException
    {
      boolean bool1 = true;
      try
      {
        if (this.closed)
          throw new IOException("closed");
      }
      finally
      {
      }
      boolean bool2 = this.client;
      boolean bool3;
      if ((paramInt1 & 0x1) == bool1)
        bool3 = bool1;
      while (true)
      {
        if (paramBoolean != bool1)
          throw new IllegalArgumentException("payload != reply");
        this.sink.writeInt(-2147287034);
        this.sink.writeInt(4);
        this.sink.writeInt(paramInt1);
        this.sink.flush();
        return;
        while (true)
        {
          if (bool2 == bool3)
            break label128;
          break;
          bool3 = false;
        }
        label128: bool1 = false;
      }
    }

    public void pushPromise(int paramInt1, int paramInt2, List<Header> paramList)
      throws IOException
    {
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
      this.sink.writeInt(-2147287037);
      this.sink.writeInt(8);
      this.sink.writeInt(0x7FFFFFFF & paramInt);
      this.sink.writeInt(paramErrorCode.spdyRstCode);
      this.sink.flush();
    }

    void sendDataFrame(int paramInt1, int paramInt2, Buffer paramBuffer, int paramInt3)
      throws IOException
    {
      if (this.closed)
        throw new IOException("closed");
      if (paramInt3 > 16777215L)
        throw new IllegalArgumentException("FRAME_TOO_LARGE max size is 16Mib: " + paramInt3);
      this.sink.writeInt(0x7FFFFFFF & paramInt1);
      this.sink.writeInt((paramInt2 & 0xFF) << 24 | 0xFFFFFF & paramInt3);
      if (paramInt3 > 0)
        this.sink.write(paramBuffer, paramInt3);
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
      int i = paramSettings.size();
      int j = 4 + i * 8;
      this.sink.writeInt(-2147287036);
      this.sink.writeInt(0x0 | j & 0xFFFFFF);
      this.sink.writeInt(i);
      for (int k = 0; ; k++)
        if (k <= 10)
        {
          if (paramSettings.isSet(k))
          {
            int m = paramSettings.flags(k);
            this.sink.writeInt((m & 0xFF) << 24 | k & 0xFFFFFF);
            this.sink.writeInt(paramSettings.get(k));
          }
        }
        else
        {
          this.sink.flush();
          return;
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
      writeNameValueBlockToBuffer(paramList);
      if (paramBoolean);
      for (int i = 1; ; i = 0)
      {
        int j = (int)(4L + this.headerBlockBuffer.size());
        this.sink.writeInt(-2147287038);
        this.sink.writeInt((i & 0xFF) << 24 | 0xFFFFFF & j);
        this.sink.writeInt(0x7FFFFFFF & paramInt);
        this.sink.writeAll(this.headerBlockBuffer);
        this.sink.flush();
        return;
      }
    }

    public void synStream(boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, List<Header> paramList)
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
      writeNameValueBlockToBuffer(paramList);
      int i = (int)(10L + this.headerBlockBuffer.size());
      int j;
      if (paramBoolean1)
        j = 1;
      while (true)
      {
        int m = j | k;
        this.sink.writeInt(-2147287039);
        this.sink.writeInt((m & 0xFF) << 24 | 0xFFFFFF & i);
        this.sink.writeInt(0x7FFFFFFF & paramInt1);
        this.sink.writeInt(0x7FFFFFFF & paramInt2);
        this.sink.writeShort(0);
        this.sink.writeAll(this.headerBlockBuffer);
        this.sink.flush();
        return;
        j = 0;
        while (!paramBoolean2)
        {
          k = 0;
          break;
        }
        int k = 2;
      }
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
        throw new IllegalArgumentException("windowSizeIncrement must be between 1 and 0x7fffffff: " + paramLong);
      this.sink.writeInt(-2147287031);
      this.sink.writeInt(8);
      this.sink.writeInt(paramInt);
      this.sink.writeInt((int)paramLong);
      this.sink.flush();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Spdy3
 * JD-Core Version:    0.6.2
 */