package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

public abstract class RequestBody
{
  public static RequestBody create(MediaType paramMediaType, final File paramFile)
  {
    if (paramFile == null)
      throw new NullPointerException("content == null");
    return new RequestBody()
    {
      public long contentLength()
      {
        return paramFile.length();
      }

      public MediaType contentType()
      {
        return this.val$contentType;
      }

      public void writeTo(BufferedSink paramAnonymousBufferedSink)
        throws IOException
      {
        Source localSource = null;
        try
        {
          localSource = Okio.source(paramFile);
          paramAnonymousBufferedSink.writeAll(localSource);
          return;
        }
        finally
        {
          Util.closeQuietly(localSource);
        }
      }
    };
  }

  public static RequestBody create(MediaType paramMediaType, String paramString)
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
    return create(paramMediaType, paramString.getBytes(localCharset));
  }

  public static RequestBody create(MediaType paramMediaType, byte[] paramArrayOfByte)
  {
    return create(paramMediaType, paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static RequestBody create(MediaType paramMediaType, final byte[] paramArrayOfByte, final int paramInt1, final int paramInt2)
  {
    if (paramArrayOfByte == null)
      throw new NullPointerException("content == null");
    Util.checkOffsetAndCount(paramArrayOfByte.length, paramInt1, paramInt2);
    return new RequestBody()
    {
      public long contentLength()
      {
        return paramInt2;
      }

      public MediaType contentType()
      {
        return this.val$contentType;
      }

      public void writeTo(BufferedSink paramAnonymousBufferedSink)
        throws IOException
      {
        paramAnonymousBufferedSink.write(paramArrayOfByte, paramInt1, paramInt2);
      }
    };
  }

  public long contentLength()
    throws IOException
  {
    return -1L;
  }

  public abstract MediaType contentType();

  public abstract void writeTo(BufferedSink paramBufferedSink)
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.RequestBody
 * JD-Core Version:    0.6.2
 */