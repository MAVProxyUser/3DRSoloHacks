package com.jcraft.jsch.jce;

public class HMACSHA256 extends HMAC
{
  public HMACSHA256()
  {
    this.name = "hmac-sha2-256";
    this.bsize = 32;
    this.algorithm = "HmacSHA256";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.HMACSHA256
 * JD-Core Version:    0.6.2
 */