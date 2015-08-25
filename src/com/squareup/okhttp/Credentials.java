package com.squareup.okhttp;

import java.io.UnsupportedEncodingException;
import okio.ByteString;

public final class Credentials
{
  public static String basic(String paramString1, String paramString2)
  {
    try
    {
      String str1 = ByteString.of((paramString1 + ":" + paramString2).getBytes("ISO-8859-1")).base64();
      String str2 = "Basic " + str1;
      return str2;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
    }
    throw new AssertionError();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Credentials
 * JD-Core Version:    0.6.2
 */