package com.jcraft.jsch;

public abstract interface Compression
{
  public static final int DEFLATER = 1;
  public static final int INFLATER;

  public abstract byte[] compress(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt);

  public abstract void init(int paramInt1, int paramInt2);

  public abstract byte[] uncompress(byte[] paramArrayOfByte, int paramInt, int[] paramArrayOfInt);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Compression
 * JD-Core Version:    0.6.2
 */