package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.internal.Internal;
import com.squareup.okhttp.internal.Util;
import java.io.EOFException;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class HttpConnection
{
  private static final int ON_IDLE_CLOSE = 2;
  private static final int ON_IDLE_HOLD = 0;
  private static final int ON_IDLE_POOL = 1;
  private static final int STATE_CLOSED = 6;
  private static final int STATE_IDLE = 0;
  private static final int STATE_OPEN_REQUEST_BODY = 1;
  private static final int STATE_OPEN_RESPONSE_BODY = 4;
  private static final int STATE_READING_RESPONSE_BODY = 5;
  private static final int STATE_READ_RESPONSE_HEADERS = 3;
  private static final int STATE_WRITING_REQUEST_BODY = 2;
  private final Connection connection;
  private int onIdle = 0;
  private final ConnectionPool pool;
  private final BufferedSink sink;
  private final Socket socket;
  private final BufferedSource source;
  private int state = 0;

  public HttpConnection(ConnectionPool paramConnectionPool, Connection paramConnection, Socket paramSocket)
    throws IOException
  {
    this.pool = paramConnectionPool;
    this.connection = paramConnection;
    this.socket = paramSocket;
    this.source = Okio.buffer(Okio.source(paramSocket));
    this.sink = Okio.buffer(Okio.sink(paramSocket));
  }

  public long bufferSize()
  {
    return this.source.buffer().size();
  }

  public void closeIfOwnedBy(Object paramObject)
    throws IOException
  {
    Internal.instance.closeIfOwnedBy(this.connection, paramObject);
  }

  public void closeOnIdle()
    throws IOException
  {
    this.onIdle = 2;
    if (this.state == 0)
    {
      this.state = 6;
      this.connection.getSocket().close();
    }
  }

  public void flush()
    throws IOException
  {
    this.sink.flush();
  }

  public boolean isClosed()
  {
    return this.state == 6;
  }

  public boolean isReadable()
  {
    try
    {
      int i = this.socket.getSoTimeout();
      try
      {
        this.socket.setSoTimeout(1);
        boolean bool = this.source.exhausted();
        return !bool;
      }
      finally
      {
        this.socket.setSoTimeout(i);
      }
    }
    catch (SocketTimeoutException localSocketTimeoutException)
    {
      return true;
    }
    catch (IOException localIOException)
    {
    }
    return false;
  }

  public Sink newChunkedSink()
  {
    if (this.state != 1)
      throw new IllegalStateException("state: " + this.state);
    this.state = 2;
    return new ChunkedSink(null);
  }

  public Source newChunkedSource(HttpEngine paramHttpEngine)
    throws IOException
  {
    if (this.state != 4)
      throw new IllegalStateException("state: " + this.state);
    this.state = 5;
    return new ChunkedSource(paramHttpEngine);
  }

  public Sink newFixedLengthSink(long paramLong)
  {
    if (this.state != 1)
      throw new IllegalStateException("state: " + this.state);
    this.state = 2;
    return new FixedLengthSink(paramLong, null);
  }

  public Source newFixedLengthSource(long paramLong)
    throws IOException
  {
    if (this.state != 4)
      throw new IllegalStateException("state: " + this.state);
    this.state = 5;
    return new FixedLengthSource(paramLong);
  }

  public Source newUnknownLengthSource()
    throws IOException
  {
    if (this.state != 4)
      throw new IllegalStateException("state: " + this.state);
    this.state = 5;
    return new UnknownLengthSource(null);
  }

  public void poolOnIdle()
  {
    this.onIdle = 1;
    if (this.state == 0)
    {
      this.onIdle = 0;
      Internal.instance.recycle(this.pool, this.connection);
    }
  }

  public void readHeaders(Headers.Builder paramBuilder)
    throws IOException
  {
    while (true)
    {
      String str = this.source.readUtf8LineStrict();
      if (str.length() == 0)
        break;
      Internal.instance.addLenient(paramBuilder, str);
    }
  }

  public Response.Builder readResponse()
    throws IOException
  {
    if ((this.state != 1) && (this.state != 3))
      throw new IllegalStateException("state: " + this.state);
    try
    {
      StatusLine localStatusLine;
      Response.Builder localBuilder;
      do
      {
        localStatusLine = StatusLine.parse(this.source.readUtf8LineStrict());
        localBuilder = new Response.Builder().protocol(localStatusLine.protocol).code(localStatusLine.code).message(localStatusLine.message);
        Headers.Builder localBuilder1 = new Headers.Builder();
        readHeaders(localBuilder1);
        localBuilder1.add(OkHeaders.SELECTED_PROTOCOL, localStatusLine.protocol.toString());
        localBuilder.headers(localBuilder1.build());
      }
      while (localStatusLine.code == 100);
      this.state = 4;
      return localBuilder;
    }
    catch (EOFException localEOFException)
    {
      IOException localIOException = new IOException("unexpected end of stream on " + this.connection + " (recycle count=" + Internal.instance.recycleCount(this.connection) + ")");
      localIOException.initCause(localEOFException);
      throw localIOException;
    }
  }

  public void setTimeouts(int paramInt1, int paramInt2)
  {
    if (paramInt1 != 0)
      this.source.timeout().timeout(paramInt1, TimeUnit.MILLISECONDS);
    if (paramInt2 != 0)
      this.sink.timeout().timeout(paramInt2, TimeUnit.MILLISECONDS);
  }

  public void writeRequest(Headers paramHeaders, String paramString)
    throws IOException
  {
    if (this.state != 0)
      throw new IllegalStateException("state: " + this.state);
    this.sink.writeUtf8(paramString).writeUtf8("\r\n");
    int i = 0;
    int j = paramHeaders.size();
    while (i < j)
    {
      this.sink.writeUtf8(paramHeaders.name(i)).writeUtf8(": ").writeUtf8(paramHeaders.value(i)).writeUtf8("\r\n");
      i++;
    }
    this.sink.writeUtf8("\r\n");
    this.state = 1;
  }

  public void writeRequestBody(RetryableSink paramRetryableSink)
    throws IOException
  {
    if (this.state != 1)
      throw new IllegalStateException("state: " + this.state);
    this.state = 3;
    paramRetryableSink.writeToSocket(this.sink);
  }

  private abstract class AbstractSource
    implements Source
  {
    protected boolean closed;

    private AbstractSource()
    {
    }

    protected final void endOfInput(boolean paramBoolean)
      throws IOException
    {
      if (HttpConnection.this.state != 5)
        throw new IllegalStateException("state: " + HttpConnection.this.state);
      HttpConnection.access$402(HttpConnection.this, 0);
      if ((paramBoolean) && (HttpConnection.this.onIdle == 1))
      {
        HttpConnection.access$602(HttpConnection.this, 0);
        Internal.instance.recycle(HttpConnection.this.pool, HttpConnection.this.connection);
      }
      while (HttpConnection.this.onIdle != 2)
        return;
      HttpConnection.access$402(HttpConnection.this, 6);
      HttpConnection.this.connection.getSocket().close();
    }

    public Timeout timeout()
    {
      return HttpConnection.this.source.timeout();
    }

    protected final void unexpectedEndOfInput()
    {
      Util.closeQuietly(HttpConnection.this.connection.getSocket());
      HttpConnection.access$402(HttpConnection.this, 6);
    }
  }

  private final class ChunkedSink
    implements Sink
  {
    private boolean closed;

    private ChunkedSink()
    {
    }

    public void close()
      throws IOException
    {
      try
      {
        boolean bool = this.closed;
        if (bool);
        while (true)
        {
          return;
          this.closed = true;
          HttpConnection.this.sink.writeUtf8("0\r\n\r\n");
          HttpConnection.access$402(HttpConnection.this, 3);
        }
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
        boolean bool = this.closed;
        if (bool);
        while (true)
        {
          return;
          HttpConnection.this.sink.flush();
        }
      }
      finally
      {
      }
    }

    public Timeout timeout()
    {
      return HttpConnection.this.sink.timeout();
    }

    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (this.closed)
        throw new IllegalStateException("closed");
      if (paramLong == 0L)
        return;
      HttpConnection.this.sink.writeHexadecimalUnsignedLong(paramLong);
      HttpConnection.this.sink.writeUtf8("\r\n");
      HttpConnection.this.sink.write(paramBuffer, paramLong);
      HttpConnection.this.sink.writeUtf8("\r\n");
    }
  }

  private class ChunkedSource extends HttpConnection.AbstractSource
  {
    private static final long NO_CHUNK_YET = -1L;
    private long bytesRemainingInChunk = -1L;
    private boolean hasMoreChunks = true;
    private final HttpEngine httpEngine;

    ChunkedSource(HttpEngine arg2)
      throws IOException
    {
      super(null);
      Object localObject;
      this.httpEngine = localObject;
    }

    private void readChunkSize()
      throws IOException
    {
      if (this.bytesRemainingInChunk != -1L)
        HttpConnection.this.source.readUtf8LineStrict();
      try
      {
        this.bytesRemainingInChunk = HttpConnection.this.source.readHexadecimalUnsignedLong();
        String str = HttpConnection.this.source.readUtf8LineStrict().trim();
        if ((this.bytesRemainingInChunk < 0L) || ((!str.isEmpty()) && (!str.startsWith(";"))))
          throw new ProtocolException("expected chunk size and optional extensions but was \"" + this.bytesRemainingInChunk + str + "\"");
      }
      catch (NumberFormatException localNumberFormatException)
      {
        throw new ProtocolException(localNumberFormatException.getMessage());
      }
      if (this.bytesRemainingInChunk == 0L)
      {
        this.hasMoreChunks = false;
        Headers.Builder localBuilder = new Headers.Builder();
        HttpConnection.this.readHeaders(localBuilder);
        this.httpEngine.receiveHeaders(localBuilder.build());
        endOfInput(true);
      }
    }

    public void close()
      throws IOException
    {
      if (this.closed)
        return;
      if ((this.hasMoreChunks) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS)))
        unexpectedEndOfInput();
      this.closed = true;
    }

    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L)
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      if (this.closed)
        throw new IllegalStateException("closed");
      if (!this.hasMoreChunks)
        return -1L;
      if ((this.bytesRemainingInChunk == 0L) || (this.bytesRemainingInChunk == -1L))
      {
        readChunkSize();
        if (!this.hasMoreChunks)
          return -1L;
      }
      long l = HttpConnection.this.source.read(paramBuffer, Math.min(paramLong, this.bytesRemainingInChunk));
      if (l == -1L)
      {
        unexpectedEndOfInput();
        throw new IOException("unexpected end of stream");
      }
      this.bytesRemainingInChunk -= l;
      return l;
    }
  }

  private final class FixedLengthSink
    implements Sink
  {
    private long bytesRemaining;
    private boolean closed;

    private FixedLengthSink(long arg2)
    {
      Object localObject;
      this.bytesRemaining = localObject;
    }

    public void close()
      throws IOException
    {
      if (this.closed)
        return;
      this.closed = true;
      if (this.bytesRemaining > 0L)
        throw new ProtocolException("unexpected end of stream");
      HttpConnection.access$402(HttpConnection.this, 3);
    }

    public void flush()
      throws IOException
    {
      if (this.closed)
        return;
      HttpConnection.this.sink.flush();
    }

    public Timeout timeout()
    {
      return HttpConnection.this.sink.timeout();
    }

    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (this.closed)
        throw new IllegalStateException("closed");
      Util.checkOffsetAndCount(paramBuffer.size(), 0L, paramLong);
      if (paramLong > this.bytesRemaining)
        throw new ProtocolException("expected " + this.bytesRemaining + " bytes but received " + paramLong);
      HttpConnection.this.sink.write(paramBuffer, paramLong);
      this.bytesRemaining -= paramLong;
    }
  }

  private class FixedLengthSource extends HttpConnection.AbstractSource
  {
    private long bytesRemaining;

    public FixedLengthSource(long arg2)
      throws IOException
    {
      super(null);
      Object localObject;
      this.bytesRemaining = localObject;
      if (this.bytesRemaining == 0L)
        endOfInput(true);
    }

    public void close()
      throws IOException
    {
      if (this.closed)
        return;
      if ((this.bytesRemaining != 0L) && (!Util.discard(this, 100, TimeUnit.MILLISECONDS)))
        unexpectedEndOfInput();
      this.closed = true;
    }

    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L)
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      if (this.closed)
        throw new IllegalStateException("closed");
      long l;
      if (this.bytesRemaining == 0L)
        l = -1L;
      do
      {
        return l;
        l = HttpConnection.this.source.read(paramBuffer, Math.min(this.bytesRemaining, paramLong));
        if (l == -1L)
        {
          unexpectedEndOfInput();
          throw new ProtocolException("unexpected end of stream");
        }
        this.bytesRemaining -= l;
      }
      while (this.bytesRemaining != 0L);
      endOfInput(true);
      return l;
    }
  }

  private class UnknownLengthSource extends HttpConnection.AbstractSource
  {
    private boolean inputExhausted;

    private UnknownLengthSource()
    {
      super(null);
    }

    public void close()
      throws IOException
    {
      if (this.closed)
        return;
      if (!this.inputExhausted)
        unexpectedEndOfInput();
      this.closed = true;
    }

    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L)
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      if (this.closed)
        throw new IllegalStateException("closed");
      long l;
      if (this.inputExhausted)
        l = -1L;
      do
      {
        return l;
        l = HttpConnection.this.source.read(paramBuffer, paramLong);
      }
      while (l != -1L);
      this.inputExhausted = true;
      endOfInput(false);
      return -1L;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.HttpConnection
 * JD-Core Version:    0.6.2
 */