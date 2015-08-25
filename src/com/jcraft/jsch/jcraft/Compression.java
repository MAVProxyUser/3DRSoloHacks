package com.jcraft.jsch.jcraft;

import com.jcraft.jzlib.ZStream;
import java.io.PrintStream;

public class Compression
  implements com.jcraft.jsch.Compression
{
  private static final int BUF_SIZE = 4096;
  private final int buffer_margin = 52;
  private byte[] inflated_buf;
  private ZStream stream = new ZStream();
  private byte[] tmpbuf = new byte[4096];
  private int type;

  public byte[] compress(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
  {
    this.stream.next_in = paramArrayOfByte;
    this.stream.next_in_index = paramInt;
    this.stream.avail_in = (paramArrayOfInt[0] - paramInt);
    int i = paramInt;
    Object localObject = paramArrayOfByte;
    this.stream.next_out = this.tmpbuf;
    this.stream.next_out_index = 0;
    this.stream.avail_out = 4096;
    int j = this.stream.deflate(1);
    switch (j)
    {
    default:
      System.err.println("compress: deflate returnd " + j);
    case 0:
    }
    while (this.stream.avail_out != 0)
    {
      paramArrayOfInt[0] = i;
      return localObject;
      int k = 4096 - this.stream.avail_out;
      if (localObject.length < 52 + (i + k))
      {
        byte[] arrayOfByte = new byte[2 * (52 + (i + k))];
        System.arraycopy(localObject, 0, arrayOfByte, 0, localObject.length);
        localObject = arrayOfByte;
      }
      System.arraycopy(this.tmpbuf, 0, localObject, i, k);
      i += k;
    }
  }

  public void init(int paramInt1, int paramInt2)
  {
    if (paramInt1 == 1)
    {
      this.stream.deflateInit(paramInt2);
      this.type = 1;
    }
    while (paramInt1 != 0)
      return;
    this.stream.inflateInit();
    this.inflated_buf = new byte[4096];
    this.type = 0;
  }

  public byte[] uncompress(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt)
  {
    int i = 0;
    this.stream.next_in = paramArrayOfByte;
    this.stream.next_in_index = paramInt;
    this.stream.avail_in = paramArrayOfInt[0];
    while (true)
    {
      this.stream.next_out = this.tmpbuf;
      this.stream.next_out_index = 0;
      this.stream.avail_out = 4096;
      int j = this.stream.inflate(1);
      switch (j)
      {
      default:
        System.err.println("uncompress: inflate returnd " + j);
        return null;
      case 0:
        if (this.inflated_buf.length < i + 4096 - this.stream.avail_out)
        {
          int k = 2 * this.inflated_buf.length;
          if (k < i + 4096 - this.stream.avail_out)
            k = i + 4096 - this.stream.avail_out;
          byte[] arrayOfByte2 = new byte[k];
          System.arraycopy(this.inflated_buf, 0, arrayOfByte2, 0, i);
          this.inflated_buf = arrayOfByte2;
        }
        System.arraycopy(this.tmpbuf, 0, this.inflated_buf, i, 4096 - this.stream.avail_out);
        i += 4096 - this.stream.avail_out;
        paramArrayOfInt[0] = i;
      case -5:
      }
    }
    if (i > paramArrayOfByte.length - paramInt)
    {
      byte[] arrayOfByte1 = new byte[i + paramInt];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, paramInt);
      System.arraycopy(this.inflated_buf, 0, arrayOfByte1, paramInt, i);
      paramArrayOfByte = arrayOfByte1;
    }
    while (true)
    {
      paramArrayOfInt[0] = i;
      return paramArrayOfByte;
      System.arraycopy(this.inflated_buf, 0, paramArrayOfByte, paramInt, i);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jcraft.Compression
 * JD-Core Version:    0.6.2
 */