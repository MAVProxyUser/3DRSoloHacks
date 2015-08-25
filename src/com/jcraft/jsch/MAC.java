package com.jcraft.jsch;

public abstract interface MAC
{
  public abstract void doFinal(byte[] paramArrayOfByte, int paramInt);

  public abstract int getBlockSize();

  public abstract String getName();

  public abstract void init(byte[] paramArrayOfByte)
    throws Exception;

  public abstract void update(int paramInt);

  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.MAC
 * JD-Core Version:    0.6.2
 */