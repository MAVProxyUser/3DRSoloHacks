package com.jcraft.jsch.jce;

public class HMACSHA196 extends HMACSHA1
{
  private final byte[] _buf20 = new byte[20];

  public HMACSHA196()
  {
    this.name = "hmac-sha1-96";
  }

  public void doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    super.doFinal(this._buf20, 0);
    System.arraycopy(this._buf20, 0, paramArrayOfByte, paramInt, 12);
  }

  public int getBlockSize()
  {
    return 12;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.HMACSHA196
 * JD-Core Version:    0.6.2
 */