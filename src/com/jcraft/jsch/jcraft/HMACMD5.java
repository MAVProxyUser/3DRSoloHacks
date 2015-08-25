package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import java.io.PrintStream;
import java.security.MessageDigest;

public class HMACMD5 extends HMAC
  implements MAC
{
  private static final String name = "hmac-md5";

  public HMACMD5()
  {
    try
    {
      MessageDigest localMessageDigest2 = MessageDigest.getInstance("MD5");
      localMessageDigest1 = localMessageDigest2;
      setH(localMessageDigest1);
      return;
    }
    catch (Exception localException)
    {
      while (true)
      {
        System.err.println(localException);
        MessageDigest localMessageDigest1 = null;
      }
    }
  }

  public String getName()
  {
    return "hmac-md5";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jcraft.HMACMD5
 * JD-Core Version:    0.6.2
 */