package okio;

import java.io.Closeable;
import java.io.IOException;

public abstract interface Source extends Closeable
{
  public abstract void close()
    throws IOException;

  public abstract long read(Buffer paramBuffer, long paramLong)
    throws IOException;

  public abstract Timeout timeout();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     okio.Source
 * JD-Core Version:    0.6.2
 */