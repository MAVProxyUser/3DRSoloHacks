package com.jcraft.jsch.jce;

import java.security.SecureRandom;

public class Random
  implements com.jcraft.jsch.Random
{
  private SecureRandom random = null;
  private byte[] tmp = new byte[16];

  public void fill(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt2 > this.tmp.length)
      this.tmp = new byte[paramInt2];
    this.random.nextBytes(this.tmp);
    System.arraycopy(this.tmp, 0, paramArrayOfByte, paramInt1, paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.Random
 * JD-Core Version:    0.6.2
 */