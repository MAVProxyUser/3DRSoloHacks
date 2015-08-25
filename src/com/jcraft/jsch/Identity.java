package com.jcraft.jsch;

public abstract interface Identity
{
  public abstract void clear();

  public abstract boolean decrypt();

  public abstract String getAlgName();

  public abstract String getName();

  public abstract byte[] getPublicKeyBlob();

  public abstract byte[] getSignature(byte[] paramArrayOfByte);

  public abstract boolean isEncrypted();

  public abstract boolean setPassphrase(byte[] paramArrayOfByte)
    throws JSchException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.Identity
 * JD-Core Version:    0.6.2
 */