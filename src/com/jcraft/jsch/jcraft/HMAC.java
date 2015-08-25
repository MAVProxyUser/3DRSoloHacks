package com.jcraft.jsch.jcraft;

import java.security.MessageDigest;

class HMAC
{
  private static final int B = 64;
  private int bsize = 0;
  private byte[] k_ipad = null;
  private byte[] k_opad = null;
  private MessageDigest md = null;
  private final byte[] tmp = new byte[4];

  public void doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = this.md.digest();
    this.md.update(this.k_opad, 0, 64);
    this.md.update(arrayOfByte, 0, this.bsize);
    try
    {
      this.md.digest(paramArrayOfByte, paramInt, this.bsize);
      label49: this.md.update(this.k_ipad, 0, 64);
      return;
    }
    catch (Exception localException)
    {
      break label49;
    }
  }

  public int getBlockSize()
  {
    return this.bsize;
  }

  public void init(byte[] paramArrayOfByte)
    throws Exception
  {
    this.md.reset();
    if (paramArrayOfByte.length > this.bsize)
    {
      byte[] arrayOfByte3 = new byte[this.bsize];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte3, 0, this.bsize);
      paramArrayOfByte = arrayOfByte3;
    }
    if (paramArrayOfByte.length > 64)
    {
      this.md.update(paramArrayOfByte, 0, paramArrayOfByte.length);
      paramArrayOfByte = this.md.digest();
    }
    this.k_ipad = new byte[64];
    System.arraycopy(paramArrayOfByte, 0, this.k_ipad, 0, paramArrayOfByte.length);
    this.k_opad = new byte[64];
    System.arraycopy(paramArrayOfByte, 0, this.k_opad, 0, paramArrayOfByte.length);
    for (int i = 0; i < 64; i++)
    {
      byte[] arrayOfByte1 = this.k_ipad;
      arrayOfByte1[i] = ((byte)(0x36 ^ arrayOfByte1[i]));
      byte[] arrayOfByte2 = this.k_opad;
      arrayOfByte2[i] = ((byte)(0x5C ^ arrayOfByte2[i]));
    }
    this.md.update(this.k_ipad, 0, 64);
  }

  protected void setH(MessageDigest paramMessageDigest)
  {
    this.md = paramMessageDigest;
    this.bsize = paramMessageDigest.getDigestLength();
  }

  public void update(int paramInt)
  {
    this.tmp[0] = ((byte)(paramInt >>> 24));
    this.tmp[1] = ((byte)(paramInt >>> 16));
    this.tmp[2] = ((byte)(paramInt >>> 8));
    this.tmp[3] = ((byte)paramInt);
    update(this.tmp, 0, 4);
  }

  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.md.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jcraft.HMAC
 * JD-Core Version:    0.6.2
 */