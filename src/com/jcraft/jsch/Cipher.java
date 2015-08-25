package com.jcraft.jsch;

public abstract interface Cipher
{
  public static final int DECRYPT_MODE = 1;
  public static final int ENCRYPT_MODE;

  public abstract int getBlockSize();

  public abstract int getIVSize();

  public abstract void init(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception;

  public abstract boolean isCBC();

  public abstract void update(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Cipher
 * JD-Core Version:    0.6.2
 */