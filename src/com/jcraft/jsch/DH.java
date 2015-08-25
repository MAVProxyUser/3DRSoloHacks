package com.jcraft.jsch;

public abstract interface DH
{
  public abstract byte[] getE()
    throws Exception;

  public abstract byte[] getK()
    throws Exception;

  public abstract void init()
    throws Exception;

  public abstract void setF(byte[] paramArrayOfByte);

  public abstract void setG(byte[] paramArrayOfByte);

  public abstract void setP(byte[] paramArrayOfByte);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.DH
 * JD-Core Version:    0.6.2
 */