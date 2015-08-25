package com.jcraft.jsch;

public abstract interface SignatureDSA extends Signature
{
  public abstract void setPrvKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
    throws Exception;

  public abstract void setPubKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.SignatureDSA
 * JD-Core Version:    0.6.2
 */