package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSource;

public abstract class ResponseBody
  implements Closeable
{
  private Reader reader;

  private Charset charset()
  {
    MediaType localMediaType = contentType();
    if (localMediaType != null)
      return localMediaType.charset(Util.UTF_8);
    return Util.UTF_8;
  }

  public static ResponseBody create(MediaType paramMediaType, final long paramLong, BufferedSource paramBufferedSource)
  {
    if (paramBufferedSource == null)
      throw new NullPointerException("source == null");
    return new ResponseBody()
    {
      public long contentLength()
      {
        return paramLong;
      }

      public MediaType contentType()
      {
        return this.val$contentType;
      }

      public BufferedSource source()
      {
        return this.val$content;
      }
    };
  }

  public static ResponseBody create(MediaType paramMediaType, String paramString)
  {
    Charset localCharset = Util.UTF_8;
    if (paramMediaType != null)
    {
      localCharset = paramMediaType.charset();
      if (localCharset == null)
      {
        localCharset = Util.UTF_8;
        paramMediaType = MediaType.parse(paramMediaType + "; charset=utf-8");
      }
    }
    Buffer localBuffer = new Buffer().writeString(paramString, localCharset);
    return create(paramMediaType, localBuffer.size(), localBuffer);
  }

  public static ResponseBody create(MediaType paramMediaType, byte[] paramArrayOfByte)
  {
    Buffer localBuffer = new Buffer().write(paramArrayOfByte);
    return create(paramMediaType, paramArrayOfByte.length, localBuffer);
  }

  public final InputStream byteStream()
    throws IOException
  {
    return source().inputStream();
  }

  public final byte[] bytes()
    throws IOException
  {
    long l = contentLength();
    if (l > 2147483647L)
      throw new IOException("Cannot buffer entire body for content length: " + l);
    BufferedSource localBufferedSource = source();
    byte[] arrayOfByte;
    try
    {
      arrayOfByte = localBufferedSource.readByteArray();
      Util.closeQuietly(localBufferedSource);
      if ((l != -1L) && (l != arrayOfByte.length))
        throw new IOException("Content-Length and stream length disagree");
    }
    finally
    {
      Util.closeQuietly(localBufferedSource);
    }
    return arrayOfByte;
  }

  public final Reader charStream()
    throws IOException
  {
    Reader localReader = this.reader;
    if (localReader != null)
      return localReader;
    InputStreamReader localInputStreamReader = new InputStreamReader(byteStream(), charset());
    this.reader = localInputStreamReader;
    return localInputStreamReader;
  }

  public void close()
    throws IOException
  {
    source().close();
  }

  public abstract long contentLength()
    throws IOException;

  public abstract MediaType contentType();

  public abstract BufferedSource source()
    throws IOException;

  public final String string()
    throws IOException
  {
    return new String(bytes(), charset().name());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.ResponseBody
 * JD-Core Version:    0.6.2
 */