package com.jcraft.jsch.jce;

public class HMACMD596 extends HMACMD5
{
  private final byte[] _buf16 = new byte[16];

  public HMACMD596()
  {
    this.name = "hmac-md5-96";
  }

  public void doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    super.doFinal(this._buf16, 0);
    System.arraycopy(this._buf16, 0, paramArrayOfByte, paramInt, 12);
  }

  public int getBlockSize()
  {
    return 12;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.HMACMD596
 * JD-Core Version:    0.6.2
 */