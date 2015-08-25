package com.jcraft.jsch;

public abstract interface PBKDF
{
  public abstract byte[] getKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.PBKDF
 * JD-Core Version:    0.6.2
 */