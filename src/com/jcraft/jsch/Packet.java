package com.jcraft.jsch;

public class Packet
{
  private static Random random = null;
  byte[] ba4 = new byte[4];
  Buffer buffer;

  public Packet(Buffer paramBuffer)
  {
    this.buffer = paramBuffer;
  }

  static void setRandom(Random paramRandom)
  {
    random = paramRandom;
  }

  Buffer getBuffer()
  {
    return this.buffer;
  }

  void padding(int paramInt)
  {
    int i = this.buffer.index;
    int j = -i & paramInt - 1;
    if (j < paramInt)
      j += paramInt;
    int k = -4 + (i + j);
    this.ba4[0] = ((byte)(k >>> 24));
    this.ba4[1] = ((byte)(k >>> 16));
    this.ba4[2] = ((byte)(k >>> 8));
    this.ba4[3] = ((byte)k);
    System.arraycopy(this.ba4, 0, this.buffer.buffer, 0, 4);
    this.buffer.buffer[4] = ((byte)j);
    synchronized (random)
    {
      random.fill(this.buffer.buffer, this.buffer.index, j);
      this.buffer.skip(j);
      return;
    }
  }

  public void reset()
  {
    this.buffer.index = 5;
  }

  int shift(int paramInt1, int paramInt2, int paramInt3)
  {
    int i = 9 + (paramInt1 + 5);
    int j = -i & paramInt2 - 1;
    if (j < paramInt2)
      j += paramInt2;
    int k = 32 + (paramInt3 + (i + j));
    if (this.buffer.buffer.length < -9 + (-5 + (k + this.buffer.index)) - paramInt1)
    {
      byte[] arrayOfByte = new byte[-9 + (-5 + (k + this.buffer.index)) - paramInt1];
      System.arraycopy(this.buffer.buffer, 0, arrayOfByte, 0, this.buffer.buffer.length);
      this.buffer.buffer = arrayOfByte;
    }
    System.arraycopy(this.buffer.buffer, 9 + (paramInt1 + 5), this.buffer.buffer, k, -9 + (-5 + this.buffer.index) - paramInt1);
    this.buffer.index = 10;
    this.buffer.putInt(paramInt1);
    this.buffer.index = (9 + (paramInt1 + 5));
    return k;
  }

  void unshift(byte paramByte, int paramInt1, int paramInt2, int paramInt3)
  {
    System.arraycopy(this.buffer.buffer, paramInt2, this.buffer.buffer, 14, paramInt3);
    this.buffer.buffer[5] = paramByte;
    this.buffer.index = 6;
    this.buffer.putInt(paramInt1);
    this.buffer.putInt(paramInt3);
    this.buffer.index = (9 + (paramInt3 + 5));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Packet
 * JD-Core Version:    0.6.2
 */