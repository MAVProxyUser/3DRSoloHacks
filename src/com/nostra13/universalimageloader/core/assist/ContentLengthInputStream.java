package com.nostra13.universalimageloader.core.assist;

import java.io.IOException;
import java.io.InputStream;

public class ContentLengthInputStream extends InputStream
{
  private final int length;
  private final InputStream stream;

  public ContentLengthInputStream(InputStream paramInputStream, int paramInt)
  {
    this.stream = paramInputStream;
    this.length = paramInt;
  }

  public int available()
  {
    return this.length;
  }

  public void close()
    throws IOException
  {
    this.stream.close();
  }

  public void mark(int paramInt)
  {
    this.stream.mark(paramInt);
  }

  public boolean markSupported()
  {
    return this.stream.markSupported();
  }

  public int read()
    throws IOException
  {
    return this.stream.read();
  }

  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return this.stream.read(paramArrayOfByte);
  }

  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    return this.stream.read(paramArrayOfByte, paramInt1, paramInt2);
  }

  public void reset()
    throws IOException
  {
    this.stream.reset();
  }

  public long skip(long paramLong)
    throws IOException
  {
    return this.stream.skip(paramLong);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.assist.ContentLengthInputStream
 * JD-Core Version:    0.6.2
 */