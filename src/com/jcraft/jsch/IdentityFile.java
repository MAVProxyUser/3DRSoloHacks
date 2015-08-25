package com.jcraft.jsch;

class IdentityFile
  implements Identity
{
  private String identity;
  private JSch jsch;
  private KeyPair kpair;

  private IdentityFile(JSch paramJSch, String paramString, KeyPair paramKeyPair)
    throws JSchException
  {
    this.jsch = paramJSch;
    this.identity = paramString;
    this.kpair = paramKeyPair;
  }

  static IdentityFile newInstance(String paramString1, String paramString2, JSch paramJSch)
    throws JSchException
  {
    return new IdentityFile(paramJSch, paramString1, KeyPair.load(paramJSch, paramString1, paramString2));
  }

  static IdentityFile newInstance(String paramString, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, JSch paramJSch)
    throws JSchException
  {
    return new IdentityFile(paramJSch, paramString, KeyPair.load(paramJSch, paramArrayOfByte1, paramArrayOfByte2));
  }

  public void clear()
  {
    this.kpair.dispose();
    this.kpair = null;
  }

  public boolean decrypt()
  {
    throw new RuntimeException("not implemented");
  }

  public String getAlgName()
  {
    return new String(this.kpair.getKeyTypeName());
  }

  public KeyPair getKeyPair()
  {
    return this.kpair;
  }

  public String getName()
  {
    return this.identity;
  }

  public byte[] getPublicKeyBlob()
  {
    return this.kpair.getPublicKeyBlob();
  }

  public byte[] getSignature(byte[] paramArrayOfByte)
  {
    return this.kpair.getSignature(paramArrayOfByte);
  }

  public boolean isEncrypted()
  {
    return this.kpair.isEncrypted();
  }

  public boolean setPassphrase(byte[] paramArrayOfByte)
    throws JSchException
  {
    return this.kpair.decrypt(paramArrayOfByte);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.IdentityFile
 * JD-Core Version:    0.6.2
 */