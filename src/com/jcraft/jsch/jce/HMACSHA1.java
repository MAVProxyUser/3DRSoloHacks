package com.jcraft.jsch.jce;

public class HMACSHA1 extends HMAC
{
  public HMACSHA1()
  {
    this.name = "hmac-sha1";
    this.bsize = 20;
    this.algorithm = "HmacSHA1";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.HMACSHA1
 * JD-Core Version:    0.6.2
 */