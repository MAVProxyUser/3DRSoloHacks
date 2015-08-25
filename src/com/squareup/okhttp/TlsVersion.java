package com.squareup.okhttp;

public enum TlsVersion
{
  final String javaName;

  static
  {
    TLS_1_1 = new TlsVersion("TLS_1_1", 1, "TLSv1.1");
    TLS_1_0 = new TlsVersion("TLS_1_0", 2, "TLSv1");
    SSL_3_0 = new TlsVersion("SSL_3_0", 3, "SSLv3");
    TlsVersion[] arrayOfTlsVersion = new TlsVersion[4];
    arrayOfTlsVersion[0] = TLS_1_2;
    arrayOfTlsVersion[1] = TLS_1_1;
    arrayOfTlsVersion[2] = TLS_1_0;
    arrayOfTlsVersion[3] = SSL_3_0;
  }

  private TlsVersion(String paramString)
  {
    this.javaName = paramString;
  }

  public static TlsVersion forJavaName(String paramString)
  {
    int i = -1;
    switch (paramString.hashCode())
    {
    default:
    case -503070502:
    case -503070503:
    case 79923350:
    case 79201641:
    }
    while (true)
      switch (i)
      {
      default:
        throw new IllegalArgumentException("Unexpected TLS version: " + paramString);
        if (paramString.equals("TLSv1.2"))
        {
          i = 0;
          continue;
          if (paramString.equals("TLSv1.1"))
          {
            i = 1;
            continue;
            if (paramString.equals("TLSv1"))
            {
              i = 2;
              continue;
              if (paramString.equals("SSLv3"))
                i = 3;
            }
          }
        }
        break;
      case 0:
      case 1:
      case 2:
      case 3:
      }
    return TLS_1_2;
    return TLS_1_1;
    return TLS_1_0;
    return SSL_3_0;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.TlsVersion
 * JD-Core Version:    0.6.2
 */