package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

public final class Okio
{
  private static final Logger logger = Logger.getLogger(Okio.class.getName());

  public static Sink appendingSink(File paramFile)
    throws FileNotFoundException
  {
    if (paramFile == null)
      throw new IllegalArgumentException("file == null");
    return sink(new FileOutputStream(paramFile, true));
  }

  public static BufferedSink buffer(Sink paramSink)
  {
    if (paramSink == null)
      throw new IllegalArgumentException("sink == null");
    return new RealBufferedSink(paramSink);
  }

  public static BufferedSource buffer(Source paramSource)
  {
    if (paramSource == null)
      throw new IllegalArgumentException("source == null");
    return new RealBufferedSource(paramSource);
  }

  public static Sink sink(File paramFile)
    throws FileNotFoundException
  {
    if (paramFile == null)
      throw new IllegalArgumentException("file == null");
    return sink(new FileOutputStream(paramFile));
  }

  public static Sink sink(OutputStream paramOutputStream)
  {
    return sink(paramOutputStream, new Timeout());
  }

  private static Sink sink(final OutputStream paramOutputStream, Timeout paramTimeout)
  {
    if (paramOutputStream == null)
      throw new IllegalArgumentException("out == null");
    if (paramTimeout == null)
      throw new IllegalArgumentException("timeout == null");
    return new Sink()
    {
      public void close()
        throws IOException
      {
        paramOutputStream.close();
      }

      public void flush()
        throws IOException
      {
        paramOutputStream.flush();
      }

      public Timeout timeout()
      {
        return this.val$timeout;
      }

      public String toString()
      {
        return "sink(" + paramOutputStream + ")";
      }

      public void write(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        Util.checkOffsetAndCount(paramAnonymousBuffer.size, 0L, paramAnonymousLong);
        while (paramAnonymousLong > 0L)
        {
          this.val$timeout.throwIfReached();
          Segment localSegment = paramAnonymousBuffer.head;
          int i = (int)Math.min(paramAnonymousLong, localSegment.limit - localSegment.pos);
          paramOutputStream.write(localSegment.data, localSegment.pos, i);
          localSegment.pos = (i + localSegment.pos);
          paramAnonymousLong -= i;
          paramAnonymousBuffer.size -= i;
          if (localSegment.pos == localSegment.limit)
          {
            paramAnonymousBuffer.head = localSegment.pop();
            SegmentPool.recycle(localSegment);
          }
        }
      }
    };
  }

  public static Sink sink(Socket paramSocket)
    throws IOException
  {
    if (paramSocket == null)
      throw new IllegalArgumentException("socket == null");
    AsyncTimeout localAsyncTimeout = timeout(paramSocket);
    return localAsyncTimeout.sink(sink(paramSocket.getOutputStream(), localAsyncTimeout));
  }

  @IgnoreJRERequirement
  public static Sink sink(Path paramPath, OpenOption[] paramArrayOfOpenOption)
    throws IOException
  {
    if (paramPath == null)
      throw new IllegalArgumentException("path == null");
    return sink(Files.newOutputStream(paramPath, paramArrayOfOpenOption));
  }

  public static Source source(File paramFile)
    throws FileNotFoundException
  {
    if (paramFile == null)
      throw new IllegalArgumentException("file == null");
    return source(new FileInputStream(paramFile));
  }

  public static Source source(InputStream paramInputStream)
  {
    return source(paramInputStream, new Timeout());
  }

  private static Source source(final InputStream paramInputStream, Timeout paramTimeout)
  {
    if (paramInputStream == null)
      throw new IllegalArgumentException("in == null");
    if (paramTimeout == null)
      throw new IllegalArgumentException("timeout == null");
    return new Source()
    {
      public void close()
        throws IOException
      {
        paramInputStream.close();
      }

      public long read(Buffer paramAnonymousBuffer, long paramAnonymousLong)
        throws IOException
      {
        if (paramAnonymousLong < 0L)
          throw new IllegalArgumentException("byteCount < 0: " + paramAnonymousLong);
        if (paramAnonymousLong == 0L)
          return 0L;
        this.val$timeout.throwIfReached();
        Segment localSegment = paramAnonymousBuffer.writableSegment(1);
        int i = (int)Math.min(paramAnonymousLong, 2048 - localSegment.limit);
        int j = paramInputStream.read(localSegment.data, localSegment.limit, i);
        if (j == -1)
          return -1L;
        localSegment.limit = (j + localSegment.limit);
        paramAnonymousBuffer.size += j;
        return j;
      }

      public Timeout timeout()
      {
        return this.val$timeout;
      }

      public String toString()
      {
        return "source(" + paramInputStream + ")";
      }
    };
  }

  public static Source source(Socket paramSocket)
    throws IOException
  {
    if (paramSocket == null)
      throw new IllegalArgumentException("socket == null");
    AsyncTimeout localAsyncTimeout = timeout(paramSocket);
    return localAsyncTimeout.source(source(paramSocket.getInputStream(), localAsyncTimeout));
  }

  @IgnoreJRERequirement
  public static Source source(Path paramPath, OpenOption[] paramArrayOfOpenOption)
    throws IOException
  {
    if (paramPath == null)
      throw new IllegalArgumentException("path == null");
    return source(Files.newInputStream(paramPath, paramArrayOfOpenOption));
  }

  private static AsyncTimeout timeout(Socket paramSocket)
  {
    return new AsyncTimeout()
    {
      protected void timedOut()
      {
        try
        {
          this.val$socket.close();
          return;
        }
        catch (Exception localException)
        {
          Okio.logger.log(Level.WARNING, "Failed to close timed out socket " + this.val$socket, localException);
        }
      }
    };
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     okio.Okio
 * JD-Core Version:    0.6.2
 */