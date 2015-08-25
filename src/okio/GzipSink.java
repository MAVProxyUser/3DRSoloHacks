package okio;

import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

public final class GzipSink
  implements Sink
{
  private boolean closed;
  private final CRC32 crc = new CRC32();
  private final Deflater deflater;
  private final DeflaterSink deflaterSink;
  private final BufferedSink sink;

  public GzipSink(Sink paramSink)
  {
    if (paramSink == null)
      throw new IllegalArgumentException("sink == null");
    this.deflater = new Deflater(-1, true);
    this.sink = Okio.buffer(paramSink);
    this.deflaterSink = new DeflaterSink(this.sink, this.deflater);
    writeHeader();
  }

  private void updateCrc(Buffer paramBuffer, long paramLong)
  {
    for (Segment localSegment = paramBuffer.head; paramLong > 0L; localSegment = localSegment.next)
    {
      int i = (int)Math.min(paramLong, localSegment.limit - localSegment.pos);
      this.crc.update(localSegment.data, localSegment.pos, i);
      paramLong -= i;
    }
  }

  private void writeFooter()
    throws IOException
  {
    this.sink.writeIntLe((int)this.crc.getValue());
    this.sink.writeIntLe(this.deflater.getTotalIn());
  }

  private void writeHeader()
  {
    Buffer localBuffer = this.sink.buffer();
    localBuffer.writeShort(8075);
    localBuffer.writeByte(8);
    localBuffer.writeByte(0);
    localBuffer.writeInt(0);
    localBuffer.writeByte(0);
    localBuffer.writeByte(0);
  }

  public void close()
    throws IOException
  {
    if (this.closed);
    while (true)
    {
      return;
      Object localObject = null;
      try
      {
        this.deflaterSink.finishDeflate();
        writeFooter();
      }
      catch (Throwable localThrowable2)
      {
        try
        {
          this.deflater.end();
        }
        catch (Throwable localThrowable2)
        {
          try
          {
            while (true)
            {
              this.sink.close();
              this.closed = true;
              if (localObject == null)
                break;
              Util.sneakyRethrow((Throwable)localObject);
              return;
              localThrowable1 = localThrowable1;
              localObject = localThrowable1;
              continue;
              localThrowable2 = localThrowable2;
              if (localObject == null)
                localObject = localThrowable2;
            }
          }
          catch (Throwable localThrowable3)
          {
            while (true)
              if (localObject == null)
                localObject = localThrowable3;
          }
        }
      }
    }
  }

  public void flush()
    throws IOException
  {
    this.deflaterSink.flush();
  }

  public Timeout timeout()
  {
    return this.sink.timeout();
  }

  public void write(Buffer paramBuffer, long paramLong)
    throws IOException
  {
    if (paramLong < 0L)
      throw new IllegalArgumentException("byteCount < 0: " + paramLong);
    if (paramLong == 0L)
      return;
    updateCrc(paramBuffer, paramLong);
    this.deflaterSink.write(paramBuffer, paramLong);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     okio.GzipSink
 * JD-Core Version:    0.6.2
 */