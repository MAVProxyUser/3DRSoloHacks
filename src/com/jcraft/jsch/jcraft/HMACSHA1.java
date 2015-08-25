package com.jcraft.jsch.jcraft;

import com.jcraft.jsch.MAC;
import java.io.PrintStream;
import java.security.MessageDigest;

public class HMACSHA1 extends HMAC
  implements MAC
{
  private static final String name = "hmac-sha1";

  public HMACSHA1()
  {
    try
    {
      MessageDigest localMessageDigest2 = MessageDigest.getInstance("SHA-1");
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
    return "hmac-sha1";
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.jcraft.HMACSHA1
 * JD-Core Version:    0.6.2
 */