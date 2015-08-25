package com.jcraft.jsch;

public abstract interface KeyPairGenRSA
{
  public abstract byte[] getC();

  public abstract byte[] getD();

  public abstract byte[] getE();

  public abstract byte[] getEP();

  public abstract byte[] getEQ();

  public abstract byte[] getN();

  public abstract byte[] getP();

  public abstract byte[] getQ();

  public abstract void init(int paramInt)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.KeyPairGenRSA
 * JD-Core Version:    0.6.2
 */