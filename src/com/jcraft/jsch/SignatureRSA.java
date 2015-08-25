package com.jcraft.jsch;

public abstract interface SignatureRSA extends Signature
{
  public abstract void setPrvKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception;

  public abstract void setPubKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.SignatureRSA
 * JD-Core Version:    0.6.2
 */