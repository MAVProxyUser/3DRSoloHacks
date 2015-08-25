package com.jcraft.jsch.jce;

import javax.crypto.spec.SecretKeySpec;

public class ARCFOUR
  implements com.jcraft.jsch.Cipher
{
  private static final int bsize = 16;
  private static final int ivsize = 8;
  private javax.crypto.Cipher cipher;

  public int getBlockSize()
  {
    return 16;
  }

  public int getIVSize()
  {
    return 8;
  }

  public void init(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    if (paramArrayOfByte1.length > 16)
    {
      byte[] arrayOfByte = new byte[16];
      System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, arrayOfByte.length);
      paramArrayOfByte1 = arrayOfByte;
    }
    while (true)
    {
      try
      {
        this.cipher = javax.crypto.Cipher.getInstance("RC4");
        SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1, "RC4");
        try
        {
          javax.crypto.Cipher localCipher = this.cipher;
          if (paramInt == 0)
          {
            i = 1;
            localCipher.init(i, localSecretKeySpec);
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
    return false;
  }

  public void update(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws Exception
  {
    this.cipher.update(paramArrayOfByte1, paramInt1, paramInt2, paramArrayOfByte2, paramInt3);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.ARCFOUR
 * JD-Core Version:    0.6.2
 */