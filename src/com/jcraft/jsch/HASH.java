package com.jcraft.jsch;

public abstract interface HASH
{
  public abstract byte[] digest()
    throws Exception;

  public abstract int getBlockSize();

  public abstract void init()
    throws Exception;

  public abstract void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.HASH
 * JD-Core Version:    0.6.2
 */