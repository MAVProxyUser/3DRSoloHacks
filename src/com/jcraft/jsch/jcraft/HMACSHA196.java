package com.jcraft.jsch.jcraft;

public class HMACSHA196 extends HMACSHA1
{
  private static final int BSIZE = 12;
  private static final String name = "hmac-sha1-96";
  private final byte[] _buf16 = new byte[20];

  public void doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    super.doFinal(this._buf16, 0);
    System.arraycopy(this._buf16, 0, paramArrayOfByte, paramInt, 12);
  }

  public int getBlockSize()
  {
    return 12;
  }

  public String getName()
  {
    return "hmac-sha1-96";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jcraft.HMACSHA196
 * JD-Core Version:    0.6.2
 */