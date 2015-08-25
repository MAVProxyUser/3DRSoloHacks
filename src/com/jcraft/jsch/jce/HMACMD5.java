package com.jcraft.jsch.jce;

public class HMACMD5 extends HMAC
{
  public HMACMD5()
  {
    this.name = "hmac-md5";
    this.bsize = 16;
    this.algorithm = "HmacMD5";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.HMACMD5
 * JD-Core Version:    0.6.2
 */