package com.nostra13.universalimageloader.cache.disc.naming;

import com.nostra13.universalimageloader.utils.L;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5FileNameGenerator
  implements FileNameGenerator
{
  private static final String HASH_ALGORITHM = "MD5";
  private static final int RADIX = 36;

  private byte[] getMD5(byte[] paramArrayOfByte)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramArrayOfByte);
      byte[] arrayOfByte = localMessageDigest.digest();
      return arrayOfByte;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      L.e(localNoSuchAlgorithmException);
    }
    return null;
  }

  public String generate(String paramString)
  {
    return new BigInteger(getMD5(paramString.getBytes())).abs().toString(36);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator
 * JD-Core Version:    0.6.2
 */