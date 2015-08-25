package com.jcraft.jsch.jce;

import com.jcraft.jsch.MAC;
import java.io.PrintStream;
import javax.crypto.Mac;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.SecretKeySpec;

abstract class HMAC
  implements MAC
{
  protected String algorithm;
  protected int bsize;
  private Mac mac;
  protected String name;
  private final byte[] tmp = new byte[4];

  public void doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    try
    {
      this.mac.doFinal(paramArrayOfByte, paramInt);
      return;
    }
    catch (ShortBufferException localShortBufferException)
    {
      System.err.println(localShortBufferException);
    }
  }

  public int getBlockSize()
  {
    return this.bsize;
  }

  public String getName()
  {
    return this.name;
  }

  public void init(byte[] paramArrayOfByte)
    throws Exception
  {
    if (paramArrayOfByte.length > this.bsize)
    {
      byte[] arrayOfByte = new byte[this.bsize];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, this.bsize);
      paramArrayOfByte = arrayOfByte;
    }
    SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte, this.algorithm);
    this.mac = Mac.getInstance(this.algorithm);
    this.mac.init(localSecretKeySpec);
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
    this.mac.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.HMAC
 * JD-Core Version:    0.6.2
 */