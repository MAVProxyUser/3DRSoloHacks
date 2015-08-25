package com.nostra13.universalimageloader.core.assist;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FlushedInputStream extends FilterInputStream
{
  public FlushedInputStream(InputStream paramInputStream)
  {
    super(paramInputStream);
  }

  public long skip(long paramLong)
    throws IOException
  {
    long l2;
    for (long l1 = 0L; ; l1 += l2)
    {
      if (l1 < paramLong)
      {
        l2 = this.in.skip(paramLong - l1);
        if (l2 != 0L)
          continue;
        if (read() >= 0);
      }
      else
      {
        return l1;
      }
      l2 = 1L;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.assist.FlushedInputStream
 * JD-Core Version:    0.6.2
 */