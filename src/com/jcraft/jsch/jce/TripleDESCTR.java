package com.jcraft.jsch.jce;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class TripleDESCTR
  implements com.jcraft.jsch.Cipher
{
  private static final int bsize = 24;
  private static final int ivsize = 8;
  private javax.crypto.Cipher cipher;

  public int getBlockSize()
  {
    return 24;
  }

  public int getIVSize()
  {
    return 8;
  }

  public void init(int paramInt, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
    throws Exception
  {
    if (paramArrayOfByte2.length > 8)
    {
      byte[] arrayOfByte2 = new byte[8];
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
        this.cipher = javax.crypto.Cipher.getInstance("DESede/CTR/" + "NoPadding");
        DESedeKeySpec localDESedeKeySpec = new DESedeKeySpec(paramArrayOfByte1);
        SecretKey localSecretKey = SecretKeyFactory.getInstance("DESede").generateSecret(localDESedeKeySpec);
        try
        {
          javax.crypto.Cipher localCipher = this.cipher;
          if (paramInt == 0)
          {
            i = 1;
            localCipher.init(i, localSecretKey, new IvParameterSpec(paramArrayOfByte2));
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
 * Qualified Name:     com.jcraft.jsch.jce.TripleDESCTR
 * JD-Core Version:    0.6.2
 */