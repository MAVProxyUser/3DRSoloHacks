package com.squareup.okhttp.internal.spdy;

import java.io.EOFException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.ArrayList;
import java.util.List;
import okio.AsyncTimeout;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;
import okio.Source;
import okio.Timeout;

public final class SpdyStream
{
  long bytesLeftInWriteWindow;
  private final SpdyConnection connection;
  private ErrorCode errorCode = null;
  private final int id;
  private final SpdyTimeout readTimeout = new SpdyTimeout();
  private final List<Header> requestHeaders;
  private List<Header> responseHeaders;
  final SpdyDataSink sink;
  private final SpdyDataSource source;
  long unacknowledgedBytesRead = 0L;
  private final SpdyTimeout writeTimeout = new SpdyTimeout();

  static
  {
    if (!SpdyStream.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }

  SpdyStream(int paramInt, SpdyConnection paramSpdyConnection, boolean paramBoolean1, boolean paramBoolean2, List<Header> paramList)
  {
    if (paramSpdyConnection == null)
      throw new NullPointerException("connection == null");
    if (paramList == null)
      throw new NullPointerException("requestHeaders == null");
    this.id = paramInt;
    this.connection = paramSpdyConnection;
    this.bytesLeftInWriteWindow = paramSpdyConnection.peerSettings.getInitialWindowSize(65536);
    this.source = new SpdyDataSource(paramSpdyConnection.okHttpSettings.getInitialWindowSize(65536), null);
    this.sink = new SpdyDataSink();
    SpdyDataSource.access$102(this.source, paramBoolean2);
    SpdyDataSink.access$202(this.sink, paramBoolean1);
    this.requestHeaders = paramList;
  }

  private void cancelStreamIfNecessary()
    throws IOException
  {
    assert (!Thread.holdsLock(this));
    while (true)
    {
      boolean bool;
      int i;
      try
      {
        if ((!this.source.finished) && (this.source.closed))
        {
          if (this.sink.finished)
            break label112;
          if (this.sink.closed)
          {
            break label112;
            bool = isOpen();
            if (i == 0)
              break label95;
            close(ErrorCode.CANCEL);
            return;
          }
        }
        i = 0;
        continue;
      }
      finally
      {
      }
      label95: if (!bool)
      {
        this.connection.removeStream(this.id);
        return;
        label112: i = 1;
      }
    }
  }

  private void checkOutNotClosed()
    throws IOException
  {
    if (this.sink.closed)
      throw new IOException("stream closed");
    if (this.sink.finished)
      throw new IOException("stream finished");
    if (this.errorCode != null)
      throw new IOException("stream was reset: " + this.errorCode);
  }

  private boolean closeInternal(ErrorCode paramErrorCode)
  {
    assert (!Thread.holdsLock(this));
    try
    {
      if (this.errorCode != null)
        return false;
      if ((this.source.finished) && (this.sink.finished))
        return false;
    }
    finally
    {
    }
    this.errorCode = paramErrorCode;
    notifyAll();
    this.connection.removeStream(this.id);
    return true;
  }

  private void waitForIo()
    throws InterruptedIOException
  {
    try
    {
      wait();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
    }
    throw new InterruptedIOException();
  }

  void addBytesToWriteWindow(long paramLong)
  {
    this.bytesLeftInWriteWindow = (paramLong + this.bytesLeftInWriteWindow);
    if (paramLong > 0L)
      notifyAll();
  }

  public void close(ErrorCode paramErrorCode)
    throws IOException
  {
    if (!closeInternal(paramErrorCode))
      return;
    this.connection.writeSynReset(this.id, paramErrorCode);
  }

  public void closeLater(ErrorCode paramErrorCode)
  {
    if (!closeInternal(paramErrorCode))
      return;
    this.connection.writeSynResetLater(this.id, paramErrorCode);
  }

  public SpdyConnection getConnection()
  {
    return this.connection;
  }

  public ErrorCode getErrorCode()
  {
    try
    {
      ErrorCode localErrorCode = this.errorCode;
      return localErrorCode;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int getId()
  {
    return this.id;
  }

  public List<Header> getRequestHeaders()
  {
    return this.requestHeaders;
  }

  public List<Header> getResponseHeaders()
    throws IOException
  {
    try
    {
      this.readTimeout.enter();
    }
    finally
    {
      try
      {
        if ((this.responseHeaders != null) || (this.errorCode != null))
          break label45;
        waitForIo();
      }
      finally
      {
        this.readTimeout.exitAndThrowIfTimedOut();
      }
    }
    label45: this.readTimeout.exitAndThrowIfTimedOut();
    if (this.responseHeaders != null)
    {
      List localList = this.responseHeaders;
      return localList;
    }
    throw new IOException("stream was reset: " + this.errorCode);
  }

  public Sink getSink()
  {
    try
    {
      if ((this.responseHeaders == null) && (!isLocallyInitiated()))
        throw new IllegalStateException("reply before requesting the sink");
    }
    finally
    {
    }
    return this.sink;
  }

  public Source getSource()
  {
    return this.source;
  }

  public boolean isLocallyInitiated()
  {
    if ((0x1 & this.id) == 1);
    for (int i = 1; this.connection.client == i; i = 0)
      return true;
    return false;
  }

  public boolean isOpen()
  {
    try
    {
      ErrorCode localErrorCode = this.errorCode;
      boolean bool = false;
      if (localErrorCode != null);
      while (true)
      {
        return bool;
        if (((this.source.finished) || (this.source.closed)) && ((this.sink.finished) || (this.sink.closed)))
        {
          List localList = this.responseHeaders;
          bool = false;
          if (localList != null);
        }
        else
        {
          bool = true;
        }
      }
    }
    finally
    {
    }
  }

  public Timeout readTimeout()
  {
    return this.readTimeout;
  }

  void receiveData(BufferedSource paramBufferedSource, int paramInt)
    throws IOException
  {
    assert (!Thread.holdsLock(this));
    this.source.receive(paramBufferedSource, paramInt);
  }

  void receiveFin()
  {
    assert (!Thread.holdsLock(this));
    try
    {
      SpdyDataSource.access$102(this.source, true);
      boolean bool = isOpen();
      notifyAll();
      if (!bool)
        this.connection.removeStream(this.id);
      return;
    }
    finally
    {
    }
  }

  void receiveHeaders(List<Header> paramList, HeadersMode paramHeadersMode)
  {
    assert (!Thread.holdsLock(this));
    boolean bool = true;
    do
      while (true)
      {
        ErrorCode localErrorCode;
        try
        {
          if (this.responseHeaders == null)
          {
            if (paramHeadersMode.failIfHeadersAbsent())
            {
              localErrorCode = ErrorCode.PROTOCOL_ERROR;
              if (localErrorCode == null)
                break;
              closeLater(localErrorCode);
              return;
            }
            this.responseHeaders = paramList;
            bool = isOpen();
            notifyAll();
            localErrorCode = null;
            continue;
          }
        }
        finally
        {
        }
        if (paramHeadersMode.failIfHeadersPresent())
        {
          localErrorCode = ErrorCode.STREAM_IN_USE;
        }
        else
        {
          ArrayList localArrayList = new ArrayList();
          localArrayList.addAll(this.responseHeaders);
          localArrayList.addAll(paramList);
          this.responseHeaders = localArrayList;
          localErrorCode = null;
        }
      }
    while (bool);
    this.connection.removeStream(this.id);
  }

  void receiveRstStream(ErrorCode paramErrorCode)
  {
    try
    {
      if (this.errorCode == null)
      {
        this.errorCode = paramErrorCode;
        notifyAll();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void reply(List<Header> paramList, boolean paramBoolean)
    throws IOException
  {
    assert (!Thread.holdsLock(this));
    if (paramList == null)
      try
      {
        throw new NullPointerException("responseHeaders == null");
      }
      finally
      {
      }
    if (this.responseHeaders != null)
      throw new IllegalStateException("reply already sent");
    this.responseHeaders = paramList;
    boolean bool = false;
    if (!paramBoolean)
    {
      SpdyDataSink.access$202(this.sink, true);
      bool = true;
    }
    this.connection.writeSynReply(this.id, bool, paramList);
    if (bool)
      this.connection.flush();
  }

  public Timeout writeTimeout()
  {
    return this.writeTimeout;
  }

  final class SpdyDataSink
    implements Sink
  {
    private static final long EMIT_BUFFER_SIZE = 16384L;
    private boolean closed;
    private boolean finished;
    private final Buffer sendBuffer = new Buffer();

    static
    {
      if (!SpdyStream.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    SpdyDataSink()
    {
    }

    private void emitDataFrame(boolean paramBoolean)
      throws IOException
    {
      synchronized (SpdyStream.this)
      {
        SpdyStream.this.writeTimeout.enter();
      }
      SpdyStream.this.writeTimeout.exitAndThrowIfTimedOut();
      SpdyStream.this.checkOutNotClosed();
      long l = Math.min(SpdyStream.this.bytesLeftInWriteWindow, this.sendBuffer.size());
      SpdyStream localSpdyStream2 = SpdyStream.this;
      localSpdyStream2.bytesLeftInWriteWindow -= l;
      SpdyConnection localSpdyConnection = SpdyStream.this.connection;
      int i = SpdyStream.this.id;
      if ((paramBoolean) && (l == this.sendBuffer.size()));
      for (boolean bool = true; ; bool = false)
      {
        localSpdyConnection.writeData(i, bool, this.sendBuffer, l);
        return;
      }
    }

    public void close()
      throws IOException
    {
      assert (!Thread.holdsLock(SpdyStream.this));
      synchronized (SpdyStream.this)
      {
        if (this.closed)
          return;
        if (SpdyStream.this.sink.finished)
          break label113;
        if (this.sendBuffer.size() > 0L)
        {
          if (this.sendBuffer.size() <= 0L)
            break label113;
          emitDataFrame(true);
        }
      }
      SpdyStream.this.connection.writeData(SpdyStream.this.id, true, null, 0L);
      label113: synchronized (SpdyStream.this)
      {
        this.closed = true;
        SpdyStream.this.connection.flush();
        SpdyStream.this.cancelStreamIfNecessary();
        return;
      }
    }

    public void flush()
      throws IOException
    {
      assert (!Thread.holdsLock(SpdyStream.this));
      synchronized (SpdyStream.this)
      {
        SpdyStream.this.checkOutNotClosed();
        if (this.sendBuffer.size() > 0L)
          emitDataFrame(false);
      }
      SpdyStream.this.connection.flush();
    }

    public Timeout timeout()
    {
      return SpdyStream.this.writeTimeout;
    }

    public void write(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      assert (!Thread.holdsLock(SpdyStream.this));
      this.sendBuffer.write(paramBuffer, paramLong);
      while (this.sendBuffer.size() >= 16384L)
        emitDataFrame(false);
    }
  }

  private final class SpdyDataSource
    implements Source
  {
    private boolean closed;
    private boolean finished;
    private final long maxByteCount;
    private final Buffer readBuffer = new Buffer();
    private final Buffer receiveBuffer = new Buffer();

    static
    {
      if (!SpdyStream.class.desiredAssertionStatus());
      for (boolean bool = true; ; bool = false)
      {
        $assertionsDisabled = bool;
        return;
      }
    }

    private SpdyDataSource(long arg2)
    {
      Object localObject;
      this.maxByteCount = localObject;
    }

    private void checkNotClosed()
      throws IOException
    {
      if (this.closed)
        throw new IOException("stream closed");
      if (SpdyStream.this.errorCode != null)
        throw new IOException("stream was reset: " + SpdyStream.this.errorCode);
    }

    private void waitUntilReadable()
      throws IOException
    {
      SpdyStream.this.readTimeout.enter();
      try
      {
        if (this.readBuffer.size() == 0L)
          if ((!this.finished) && (!this.closed) && (SpdyStream.this.errorCode == null))
            SpdyStream.this.waitForIo();
      }
      finally
      {
        SpdyStream.this.readTimeout.exitAndThrowIfTimedOut();
      }
    }

    public void close()
      throws IOException
    {
      synchronized (SpdyStream.this)
      {
        this.closed = true;
        this.readBuffer.clear();
        SpdyStream.this.notifyAll();
        SpdyStream.this.cancelStreamIfNecessary();
        return;
      }
    }

    public long read(Buffer paramBuffer, long paramLong)
      throws IOException
    {
      if (paramLong < 0L)
        throw new IllegalArgumentException("byteCount < 0: " + paramLong);
      synchronized (SpdyStream.this)
      {
        waitUntilReadable();
        checkNotClosed();
        if (this.readBuffer.size() == 0L)
          return -1L;
        long l = this.readBuffer.read(paramBuffer, Math.min(paramLong, this.readBuffer.size()));
        SpdyStream localSpdyStream2 = SpdyStream.this;
        localSpdyStream2.unacknowledgedBytesRead = (l + localSpdyStream2.unacknowledgedBytesRead);
        if (SpdyStream.this.unacknowledgedBytesRead >= SpdyStream.this.connection.okHttpSettings.getInitialWindowSize(65536) / 2)
        {
          SpdyStream.this.connection.writeWindowUpdateLater(SpdyStream.this.id, SpdyStream.this.unacknowledgedBytesRead);
          SpdyStream.this.unacknowledgedBytesRead = 0L;
        }
        synchronized (SpdyStream.this.connection)
        {
          SpdyConnection localSpdyConnection2 = SpdyStream.this.connection;
          localSpdyConnection2.unacknowledgedBytesRead = (l + localSpdyConnection2.unacknowledgedBytesRead);
          if (SpdyStream.this.connection.unacknowledgedBytesRead >= SpdyStream.this.connection.okHttpSettings.getInitialWindowSize(65536) / 2)
          {
            SpdyStream.this.connection.writeWindowUpdateLater(0, SpdyStream.this.connection.unacknowledgedBytesRead);
            SpdyStream.this.connection.unacknowledgedBytesRead = 0L;
          }
          return l;
        }
      }
    }

    void receive(BufferedSource paramBufferedSource, long paramLong)
      throws IOException
    {
      if ((!$assertionsDisabled) && (Thread.holdsLock(SpdyStream.this)))
        throw new AssertionError();
      while (true)
      {
        Object localObject2;
        paramLong -= localObject2;
        synchronized (SpdyStream.this)
        {
          if (this.readBuffer.size() == 0L)
          {
            j = 1;
            this.readBuffer.writeAll(this.receiveBuffer);
            if (j != 0)
              SpdyStream.this.notifyAll();
            if (paramLong > 0L);
            boolean bool;
            synchronized (SpdyStream.this)
            {
              bool = this.finished;
              int i;
              if (paramLong + this.readBuffer.size() > this.maxByteCount)
              {
                i = 1;
                if (i != 0)
                {
                  paramBufferedSource.skip(paramLong);
                  SpdyStream.this.closeLater(ErrorCode.FLOW_CONTROL_ERROR);
                }
              }
              else
              {
                i = 0;
              }
            }
            if (bool)
            {
              paramBufferedSource.skip(paramLong);
              return;
            }
            long l = paramBufferedSource.read(this.receiveBuffer, paramLong);
            if (l != -1L)
              continue;
            throw new EOFException();
          }
          int j = 0;
        }
      }
    }

    public Timeout timeout()
    {
      return SpdyStream.this.readTimeout;
    }
  }

  class SpdyTimeout extends AsyncTimeout
  {
    SpdyTimeout()
    {
    }

    public void exitAndThrowIfTimedOut()
      throws InterruptedIOException
    {
      if (exit())
        throw new InterruptedIOException("timeout");
    }

    protected void timedOut()
    {
      SpdyStream.this.closeLater(ErrorCode.CANCEL);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.SpdyStream
 * JD-Core Version:    0.6.2
 */