package com.jcraft.jsch;

public class Buffer
{
  byte[] buffer;
  int index;
  int s;
  final byte[] tmp = new byte[4];

  public Buffer()
  {
    this(20480);
  }

  public Buffer(int paramInt)
  {
    this.buffer = new byte[paramInt];
    this.index = 0;
    this.s = 0;
  }

  public Buffer(byte[] paramArrayOfByte)
  {
    this.buffer = paramArrayOfByte;
    this.index = 0;
    this.s = 0;
  }

  static Buffer fromBytes(byte[][] paramArrayOfByte)
  {
    int i = 4 * paramArrayOfByte.length;
    for (int j = 0; j < paramArrayOfByte.length; j++)
      i += paramArrayOfByte[j].length;
    Buffer localBuffer = new Buffer(i);
    for (int k = 0; k < paramArrayOfByte.length; k++)
      localBuffer.putString(paramArrayOfByte[k]);
    return localBuffer;
  }

  void checkFreeSize(int paramInt)
  {
    int i = 84 + (paramInt + this.index);
    if (this.buffer.length < i)
    {
      int j = 2 * this.buffer.length;
      if (j < i)
        j = i;
      byte[] arrayOfByte = new byte[j];
      System.arraycopy(this.buffer, 0, arrayOfByte, 0, this.index);
      this.buffer = arrayOfByte;
    }
  }

  public int getByte()
  {
    byte[] arrayOfByte = this.buffer;
    int i = this.s;
    this.s = (i + 1);
    return 0xFF & arrayOfByte[i];
  }

  public int getByte(int paramInt)
  {
    int i = this.s;
    this.s = (paramInt + this.s);
    return i;
  }

  public void getByte(byte[] paramArrayOfByte)
  {
    getByte(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  void getByte(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    System.arraycopy(this.buffer, this.s, paramArrayOfByte, paramInt1, paramInt2);
    this.s = (paramInt2 + this.s);
  }

  byte[][] getBytes(int paramInt, String paramString)
    throws JSchException
  {
    byte[][] arrayOfByte = new byte[paramInt][];
    for (int i = 0; i < paramInt; i++)
    {
      int j = getInt();
      if (getLength() < j)
        throw new JSchException(paramString);
      arrayOfByte[i] = new byte[j];
      getByte(arrayOfByte[i]);
    }
    return arrayOfByte;
  }

  byte getCommand()
  {
    return this.buffer[5];
  }

  public int getInt()
  {
    return 0xFFFF0000 & getShort() << 16 | 0xFFFF & getShort();
  }

  public int getLength()
  {
    return this.index - this.s;
  }

  public long getLong()
  {
    return (0xFFFFFFFF & getInt()) << 32 | 0xFFFFFFFF & getInt();
  }

  public byte[] getMPInt()
  {
    int i = getInt();
    if ((i < 0) || (i > 8192))
      i = 8192;
    byte[] arrayOfByte = new byte[i];
    getByte(arrayOfByte, 0, i);
    return arrayOfByte;
  }

  public byte[] getMPIntBits()
  {
    int i = (7 + getInt()) / 8;
    Object localObject = new byte[i];
    getByte((byte[])localObject, 0, i);
    if ((0x80 & localObject[0]) != 0)
    {
      byte[] arrayOfByte = new byte[1 + localObject.length];
      arrayOfByte[0] = 0;
      System.arraycopy(localObject, 0, arrayOfByte, 1, localObject.length);
      localObject = arrayOfByte;
    }
    return localObject;
  }

  public int getOffSet()
  {
    return this.s;
  }

  int getShort()
  {
    return 0xFF00 & getByte() << 8 | 0xFF & getByte();
  }

  public byte[] getString()
  {
    int i = getInt();
    if ((i < 0) || (i > 262144))
      i = 262144;
    byte[] arrayOfByte = new byte[i];
    getByte(arrayOfByte, 0, i);
    return arrayOfByte;
  }

  byte[] getString(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    int i = getInt();
    paramArrayOfInt1[0] = getByte(i);
    paramArrayOfInt2[0] = i;
    return this.buffer;
  }

  public long getUInt()
  {
    long l1 = 0xFF00 & getByte() << 8 | 0xFF & getByte();
    long l2 = 0xFF00 & getByte() << 8 | 0xFF & getByte();
    return 0xFFFF0000 & l1 << 16 | 0xFFFF & l2;
  }

  public void putByte(byte paramByte)
  {
    byte[] arrayOfByte = this.buffer;
    int i = this.index;
    this.index = (i + 1);
    arrayOfByte[i] = paramByte;
  }

  public void putByte(byte[] paramArrayOfByte)
  {
    putByte(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void putByte(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    System.arraycopy(paramArrayOfByte, paramInt1, this.buffer, this.index, paramInt2);
    this.index = (paramInt2 + this.index);
  }

  public void putInt(int paramInt)
  {
    this.tmp[0] = ((byte)(paramInt >>> 24));
    this.tmp[1] = ((byte)(paramInt >>> 16));
    this.tmp[2] = ((byte)(paramInt >>> 8));
    this.tmp[3] = ((byte)paramInt);
    System.arraycopy(this.tmp, 0, this.buffer, this.index, 4);
    this.index = (4 + this.index);
  }

  public void putLong(long paramLong)
  {
    this.tmp[0] = ((byte)(int)(paramLong >>> 56));
    this.tmp[1] = ((byte)(int)(paramLong >>> 48));
    this.tmp[2] = ((byte)(int)(paramLong >>> 40));
    this.tmp[3] = ((byte)(int)(paramLong >>> 32));
    System.arraycopy(this.tmp, 0, this.buffer, this.index, 4);
    this.tmp[0] = ((byte)(int)(paramLong >>> 24));
    this.tmp[1] = ((byte)(int)(paramLong >>> 16));
    this.tmp[2] = ((byte)(int)(paramLong >>> 8));
    this.tmp[3] = ((byte)(int)paramLong);
    System.arraycopy(this.tmp, 0, this.buffer, 4 + this.index, 4);
    this.index = (8 + this.index);
  }

  public void putMPInt(byte[] paramArrayOfByte)
  {
    int i = paramArrayOfByte.length;
    if ((0x80 & paramArrayOfByte[0]) != 0)
    {
      putInt(i + 1);
      putByte((byte)0);
    }
    while (true)
    {
      putByte(paramArrayOfByte);
      return;
      putInt(i);
    }
  }

  void putPad(int paramInt)
  {
    while (paramInt > 0)
    {
      byte[] arrayOfByte = this.buffer;
      int i = this.index;
      this.index = (i + 1);
      arrayOfByte[i] = 0;
      paramInt--;
    }
  }

  public void putString(byte[] paramArrayOfByte)
  {
    putString(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public void putString(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    putInt(paramInt2);
    putByte(paramArrayOfByte, paramInt1, paramInt2);
  }

  public void reset()
  {
    this.index = 0;
    this.s = 0;
  }

  void rewind()
  {
    this.s = 0;
  }

  public void setOffSet(int paramInt)
  {
    this.s = paramInt;
  }

  public void shift()
  {
    if (this.s == 0)
      return;
    System.arraycopy(this.buffer, this.s, this.buffer, 0, this.index - this.s);
    this.index -= this.s;
    this.s = 0;
  }

  void skip(int paramInt)
  {
    this.index = (paramInt + this.index);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Buffer
 * JD-Core Version:    0.6.2
 */