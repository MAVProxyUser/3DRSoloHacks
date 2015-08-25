package com.jcraft.jsch.jce;

public class HMACSHA512 extends HMAC
{
  public HMACSHA512()
  {
    this.name = "hmac-sha2-512";
    this.bsize = 64;
    this.algorithm = "HmacSHA512";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.HMACSHA512
 * JD-Core Version:    0.6.2
 */