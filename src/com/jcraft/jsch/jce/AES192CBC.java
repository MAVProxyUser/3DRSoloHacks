package com.jcraft.jsch.jce;

import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES192CBC
  implements com.jcraft.jsch.Cipher
{
  private static final int bsize = 24;
  private static final int ivsize = 16;
  private javax.crypto.Cipher cipher;

  public int getBlockSize()
  {
    return 24;
  }

  public int getIVSize()
  {
    return 16;
  }

  public void init(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    if (paramArrayOfByte2.length > 16)
    {
      byte[] arrayOfByte2 = new byte[16];
      System.arraycopy(paramArrayOfByte2, 0, arrayOfByte2, 0, arrayOfByte2.length);
      paramArrayOfByte2 = arrayOfByte2;
    }
    if (paramArrayOfByte1.length > 24)
    {
      byte[] arrayOfByte1 = new byte[24];
      System.arraycopy(paramArrayOfByte1, 0, arrayOfByte1, 0, arrayOfByte1.length);
      paramArrayOfByte1 = arrayOfByte1;
    }
    while (true)
    {
      try
      {
        SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1, "AES");
        this.cipher = javax.crypto.Cipher.getInstance("AES/CBC/" + "NoPadding");
        try
        {
          javax.crypto.Cipher localCipher = this.cipher;
          if (paramInt == 0)
          {
            i = 1;
            localCipher.init(i, localSecretKeySpec, new IvParameterSpec(paramArrayOfByte2));
            return;
          }
        }
        finally
        {
        }
      }
      catch (Exception localException)
      {
        this.cipher = null;
        throw localException;
      }
      int i = 2;
    }
  }

  public boolean isCBC()
  {
    return true;
  }

  public void update(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws Exception
  {
    this.cipher.update(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramInt3);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.AES192CBC
 * JD-Core Version:    0.6.2
 */