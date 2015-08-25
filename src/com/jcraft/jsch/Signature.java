package com.jcraft.jsch;

public abstract interface Signature
{
  public abstract void init()
    throws Exception;

  public abstract byte[] sign()
    throws Exception;

  public abstract void update(byte[] paramArrayOfByte)
    throws Exception;

  public abstract boolean verify(byte[] paramArrayOfByte)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Signature
 * JD-Core Version:    0.6.2
 */