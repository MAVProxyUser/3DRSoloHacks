package com.MAVLink.Messages;

import java.nio.ByteBuffer;

public class MAVLinkPayload
{
  public static final int MAX_PAYLOAD_SIZE = 512;
  public int index;
  public ByteBuffer payload = ByteBuffer.allocate(512);

  public void add(byte paramByte)
  {
    this.payload.put(paramByte);
  }

  public byte getByte()
  {
    byte b = (byte)(0x0 | 0xFF & this.payload.get(0 + this.index));
    this.index = (1 + this.index);
    return b;
  }

  public ByteBuffer getData()
  {
    return this.payload;
  }

  public float getFloat()
  {
    return Float.intBitsToFloat(getInt());
  }

  public int getInt()
  {
    int i = 0x0 | (0xFF & this.payload.get(3 + this.index)) << 24 | (0xFF & this.payload.get(2 + this.index)) << 16 | (0xFF & this.payload.get(1 + this.index)) << 8 | 0xFF & this.payload.get(0 + this.index);
    this.index = (4 + this.index);
    return i;
  }

  public long getLong()
  {
    long l = 0L | (0xFF & this.payload.get(7 + this.index)) << 56 | (0xFF & this.payload.get(6 + this.index)) << 48 | (0xFF & this.payload.get(5 + this.index)) << 40 | (0xFF & this.payload.get(4 + this.index)) << 32 | (0xFF & this.payload.get(3 + this.index)) << 24 | (0xFF & this.payload.get(2 + this.index)) << 16 | (0xFF & this.payload.get(1 + this.index)) << 8 | 0xFF & this.payload.get(0 + this.index);
    this.index = (8 + this.index);
    return l;
  }

  public long getLongReverse()
  {
    long l = 0L | (0xFF & this.payload.get(0 + this.index)) << 56 | (0xFF & this.payload.get(1 + this.index)) << 48 | (0xFF & this.payload.get(2 + this.index)) << 40 | (0xFF & this.payload.get(3 + this.index)) << 32 | (0xFF & this.payload.get(4 + this.index)) << 24 | (0xFF & this.payload.get(5 + this.index)) << 16 | (0xFF & this.payload.get(6 + this.index)) << 8 | 0xFF & this.payload.get(7 + this.index);
    this.index = (8 + this.index);
    return l;
  }

  public short getShort()
  {
    short s = (short)((short)(0x0 | (0xFF & this.payload.get(1 + this.index)) << 8) | 0xFF & this.payload.get(0 + this.index));
    this.index = (2 + this.index);
    return s;
  }

  public void putByte(byte paramByte)
  {
    add(paramByte);
  }

  public void putFloat(float paramFloat)
  {
    putInt(Float.floatToIntBits(paramFloat));
  }

  public void putInt(int paramInt)
  {
    add((byte)(paramInt >> 0));
    add((byte)(paramInt >> 8));
    add((byte)(paramInt >> 16));
    add((byte)(paramInt >> 24));
  }

  public void putLong(long paramLong)
  {
    add((byte)(int)(paramLong >> 0));
    add((byte)(int)(paramLong >> 8));
    add((byte)(int)(paramLong >> 16));
    add((byte)(int)(paramLong >> 24));
    add((byte)(int)(paramLong >> 32));
    add((byte)(int)(paramLong >> 40));
    add((byte)(int)(paramLong >> 48));
    add((byte)(int)(paramLong >> 56));
  }

  public void putShort(short paramShort)
  {
    add((byte)(paramShort >> 0));
    add((byte)(paramShort >> 8));
  }

  public void resetIndex()
  {
    this.index = 0;
  }

  public int size()
  {
    return this.payload.position();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.MAVLink.Messages.MAVLinkPayload
 * JD-Core Version:    0.6.2
 */