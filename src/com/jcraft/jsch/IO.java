package com.jcraft.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;

public class IO
{
  InputStream in;
  private boolean in_dontclose = false;
  OutputStream out;
  private boolean out_dontclose = false;
  OutputStream out_ext;
  private boolean out_ext_dontclose = false;

  public void close()
  {
    try
    {
      if ((this.in != null) && (!this.in_dontclose))
        this.in.close();
      this.in = null;
      label26: out_close();
      try
      {
        if ((this.out_ext != null) && (!this.out_ext_dontclose))
          this.out_ext.close();
        this.out_ext = null;
        return;
      }
      catch (Exception localException2)
      {
      }
    }
    catch (Exception localException1)
    {
      break label26;
    }
  }

  int getByte()
    throws IOException
  {
    return this.in.read();
  }

  void getByte(byte[] paramArrayOfByte)
    throws IOException
  {
    getByte(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  void getByte(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    do
    {
      int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
      if (i < 0)
        throw new IOException("End of IO Stream Read");
      paramInt1 += i;
      paramInt2 -= i;
    }
    while (paramInt2 > 0);
  }

  void out_close()
  {
    try
    {
      if ((this.out != null) && (!this.out_dontclose))
        this.out.close();
      this.out = null;
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void put(Packet paramPacket)
    throws IOException, SocketException
  {
    this.out.write(paramPacket.buffer.buffer, 0, paramPacket.buffer.index);
    this.out.flush();
  }

  void put(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.out.write(paramArrayOfByte, paramInt1, paramInt2);
    this.out.flush();
  }

  void put_ext(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.out_ext.write(paramArrayOfByte, paramInt1, paramInt2);
    this.out_ext.flush();
  }

  void setExtOutputStream(OutputStream paramOutputStream)
  {
    this.out_ext = paramOutputStream;
  }

  void setExtOutputStream(OutputStream paramOutputStream, boolean paramBoolean)
  {
    this.out_ext_dontclose = paramBoolean;
    setExtOutputStream(paramOutputStream);
  }

  void setInputStream(InputStream paramInputStream)
  {
    this.in = paramInputStream;
  }

  void setInputStream(InputStream paramInputStream, boolean paramBoolean)
  {
    this.in_dontclose = paramBoolean;
    setInputStream(paramInputStream);
  }

  void setOutputStream(OutputStream paramOutputStream)
  {
    this.out = paramOutputStream;
  }

  void setOutputStream(OutputStream paramOutputStream, boolean paramBoolean)
  {
    this.out_dontclose = paramBoolean;
    setOutputStream(paramOutputStream);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.IO
 * JD-Core Version:    0.6.2
 */