package com.jcraft.jsch;

public abstract interface KeyPairGenDSA
{
  public abstract byte[] getG();

  public abstract byte[] getP();

  public abstract byte[] getQ();

  public abstract byte[] getX();

  public abstract byte[] getY();

  public abstract void init(int paramInt)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.KeyPairGenDSA
 * JD-Core Version:    0.6.2
 */