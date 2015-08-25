package com.jcraft.jsch;

import java.io.PrintStream;

public class HostKey
{
  protected static final int GUESS = 0;
  public static final int SSHDSS = 1;
  public static final int SSHRSA = 2;
  static final int UNKNOWN = 3;
  private static final byte[] sshdss = Util.str2byte("ssh-dss");
  private static final byte[] sshrsa = Util.str2byte("ssh-rsa");
  protected String comment;
  protected String host;
  protected byte[] key;
  protected String marker;
  protected int type;

  public HostKey(String paramString, int paramInt, byte[] paramArrayOfByte)
    throws JSchException
  {
    this(paramString, paramInt, paramArrayOfByte, null);
  }

  public HostKey(String paramString1, int paramInt, byte[] paramArrayOfByte, String paramString2)
    throws JSchException
  {
    this("", paramString1, paramInt, paramArrayOfByte, paramString2);
  }

  public HostKey(String paramString1, String paramString2, int paramInt, byte[] paramArrayOfByte, String paramString3)
    throws JSchException
  {
    this.marker = paramString1;
    this.host = paramString2;
    if (paramInt == 0)
      if (paramArrayOfByte[8] == 100)
        this.type = 1;
    while (true)
    {
      this.key = paramArrayOfByte;
      this.comment = paramString3;
      return;
      if (paramArrayOfByte[8] == 114)
      {
        this.type = 2;
      }
      else
      {
        throw new JSchException("invalid key type");
        this.type = paramInt;
      }
    }
  }

  public HostKey(String paramString, byte[] paramArrayOfByte)
    throws JSchException
  {
    this(paramString, 0, paramArrayOfByte);
  }

  private boolean isIncluded(String paramString)
  {
    int i = 0;
    String str = this.host;
    int j = str.length();
    int k = paramString.length();
    while (true)
    {
      int m;
      if (i < j)
      {
        m = str.indexOf(',', i);
        if (m != -1)
          break label62;
        if (k == j - i);
      }
      else
      {
        return false;
      }
      return str.regionMatches(true, i, paramString, 0, k);
      label62: if ((k == m - i) && (str.regionMatches(true, i, paramString, 0, k)))
        return true;
      i = m + 1;
    }
  }

  public String getComment()
  {
    return this.comment;
  }

  public String getFingerPrint(JSch paramJSch)
  {
    try
    {
      localHASH = (HASH)Class.forName(JSch.getConfig("md5")).newInstance();
      return Util.getFingerPrint(localHASH, this.key);
    }
    catch (Exception localException)
    {
      while (true)
      {
        System.err.println("getFingerPrint: " + localException);
        HASH localHASH = null;
      }
    }
  }

  public String getHost()
  {
    return this.host;
  }

  public String getKey()
  {
    return Util.byte2str(Util.toBase64(this.key, 0, this.key.length));
  }

  public String getMarker()
  {
    return this.marker;
  }

  public String getType()
  {
    if (this.type == 1)
      return Util.byte2str(sshdss);
    if (this.type == 2)
      return Util.byte2str(sshrsa);
    return "UNKNOWN";
  }

  boolean isMatched(String paramString)
  {
    return isIncluded(paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.jcraft.jsch.HostKey
 * JD-Core Version:    0.6.2
 */