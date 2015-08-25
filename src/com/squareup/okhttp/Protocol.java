package com.squareup.okhttp;

import java.io.IOException;

public enum Protocol
{
  private final String protocol;

  static
  {
    HTTP_2 = new Protocol("HTTP_2", 3, "h2");
    Protocol[] arrayOfProtocol = new Protocol[4];
    arrayOfProtocol[0] = HTTP_1_0;
    arrayOfProtocol[1] = HTTP_1_1;
    arrayOfProtocol[2] = SPDY_3;
    arrayOfProtocol[3] = HTTP_2;
  }

  private Protocol(String paramString)
  {
    this.protocol = paramString;
  }

  public static Protocol get(String paramString)
    throws IOException
  {
    if (paramString.equals(HTTP_1_0.protocol))
      return HTTP_1_0;
    if (paramString.equals(HTTP_1_1.protocol))
      return HTTP_1_1;
    if (paramString.equals(HTTP_2.protocol))
      return HTTP_2;
    if (paramString.equals(SPDY_3.protocol))
      return SPDY_3;
    throw new IOException("Unexpected protocol: " + paramString);
  }

  public String toString()
  {
    return this.protocol;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Protocol
 * JD-Core Version:    0.6.2
 */