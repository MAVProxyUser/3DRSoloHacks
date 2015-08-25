package com.jcraft.jsch.jce;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PBKDF
  implements com.jcraft.jsch.PBKDF
{
  public byte[] getKey(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
  {
    char[] arrayOfChar = new char[paramArrayOfByte1.length];
    for (int i = 0; i < paramArrayOfByte1.length; i++)
      arrayOfChar[i] = ((char)(0xFF & paramArrayOfByte1[i]));
    try
    {
      PBEKeySpec localPBEKeySpec = new PBEKeySpec(arrayOfChar, paramArrayOfByte2, paramInt1, paramInt2 * 8);
      byte[] arrayOfByte = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1").generateSecret(localPBEKeySpec).getEncoded();
      return arrayOfByte;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      return null;
    }
    catch (InvalidKeySpecException localInvalidKeySpecException)
    {
      label76: break label76;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jce.PBKDF
 * JD-Core Version:    0.6.2
 */