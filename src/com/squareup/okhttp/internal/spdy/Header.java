package com.squareup.okhttp.internal.spdy;

import okio.ByteString;

public final class Header
{
  public static final ByteString RESPONSE_STATUS = ByteString.encodeUtf8(":status");
  public static final ByteString TARGET_AUTHORITY = ByteString.encodeUtf8(":authority");
  public static final ByteString TARGET_HOST = ByteString.encodeUtf8(":host");
  public static final ByteString TARGET_METHOD = ByteString.encodeUtf8(":method");
  public static final ByteString TARGET_PATH = ByteString.encodeUtf8(":path");
  public static final ByteString TARGET_SCHEME = ByteString.encodeUtf8(":scheme");
  public static final ByteString VERSION = ByteString.encodeUtf8(":version");
  final int hpackSize;
  public final ByteString name;
  public final ByteString value;

  public Header(String paramString1, String paramString2)
  {
    this(ByteString.encodeUtf8(paramString1), ByteString.encodeUtf8(paramString2));
  }

  public Header(ByteString paramByteString, String paramString)
  {
    this(paramByteString, ByteString.encodeUtf8(paramString));
  }

  public Header(ByteString paramByteString1, ByteString paramByteString2)
  {
    this.name = paramByteString1;
    this.value = paramByteString2;
    this.hpackSize = (32 + paramByteString1.size() + paramByteString2.size());
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Header;
    boolean bool2 = false;
    if (bool1)
    {
      Header localHeader = (Header)paramObject;
      boolean bool3 = this.name.equals(localHeader.name);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.value.equals(localHeader.value);
        bool2 = false;
        if (bool4)
          bool2 = true;
      }
    }
    return bool2;
  }

  public int hashCode()
  {
    return 31 * (527 + this.name.hashCode()) + this.value.hashCode();
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = this.name.utf8();
    arrayOfObject[1] = this.value.utf8();
    return String.format("%s: %s", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.spdy.Header
 * JD-Core Version:    0.6.2
 */