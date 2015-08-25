package com.jcraft.jsch.jce;

import javax.crypto.spec.SecretKeySpec;

public class ARCFOUR128
  implements com.jcraft.jsch.Cipher
{
  private static final int bsize = 16;
  private static final int ivsize = 8;
  private static final int skip = 1536;
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
    int i = 1;
    if (paramArrayOfByte1.length > 16)
    {
      byte[] arrayOfByte2 = new byte[16];
      System.arraycopy(paramArrayOfByte1, 0, arrayOfByte2, 0, arrayOfByte2.length);
      paramArrayOfByte1 = arrayOfByte2;
    }
    try
    {
      this.cipher = javax.crypto.Cipher.getInstance("RC4");
      SecretKeySpec localSecretKeySpec = new SecretKeySpec(paramArrayOfByte1, "RC4");
      try
      {
        javax.crypto.Cipher localCipher = this.cipher;
        if (paramInt == 0);
        while (true)
        {
          localCipher.init(i, localSecretKeySpec);
          byte[] arrayOfByte1 = new byte[1];
          for (int j = 0; j < 1536; j++)
            this.cipher.update(arrayOfByte1, 0, 1, arrayOfByte1, 0);
          i = 2;
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
 * Qualified Name:     com.jcraft.jsch.jce.ARCFOUR128
 * JD-Core Version:    0.6.2
 */