package com.jcraft.jsch;

public abstract interface GSSContext
{
  public abstract void create(String paramString1, String paramString2)
    throws JSchException;

  public abstract void dispose();

  public abstract byte[] getMIC(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public abstract byte[] init(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws JSchException;

  public abstract boolean isEstablished();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.GSSContext
 * JD-Core Version:    0.6.2
 */