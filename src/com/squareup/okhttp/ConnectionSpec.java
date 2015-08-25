package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;

public final class ConnectionSpec
{
  public static final ConnectionSpec CLEARTEXT = new Builder(false).build();
  public static final ConnectionSpec COMPATIBLE_TLS;
  public static final ConnectionSpec MODERN_TLS;
  private final String[] cipherSuites;
  final boolean supportsTlsExtensions;
  final boolean tls;
  private final String[] tlsVersions;

  static
  {
    Builder localBuilder1 = new Builder(true);
    CipherSuite[] arrayOfCipherSuite = new CipherSuite[14];
    arrayOfCipherSuite[0] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[1] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[2] = CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[3] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[4] = CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[5] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[6] = CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[7] = CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[8] = CipherSuite.TLS_DHE_DSS_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[9] = CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[10] = CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256;
    arrayOfCipherSuite[11] = CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA;
    arrayOfCipherSuite[12] = CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA;
    arrayOfCipherSuite[13] = CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA;
    Builder localBuilder2 = localBuilder1.cipherSuites(arrayOfCipherSuite);
    TlsVersion[] arrayOfTlsVersion1 = new TlsVersion[3];
    arrayOfTlsVersion1[0] = TlsVersion.TLS_1_2;
    arrayOfTlsVersion1[1] = TlsVersion.TLS_1_1;
    arrayOfTlsVersion1[2] = TlsVersion.TLS_1_0;
    MODERN_TLS = localBuilder2.tlsVersions(arrayOfTlsVersion1).supportsTlsExtensions(true).build();
    Builder localBuilder3 = new Builder(MODERN_TLS);
    TlsVersion[] arrayOfTlsVersion2 = new TlsVersion[1];
    arrayOfTlsVersion2[0] = TlsVersion.TLS_1_0;
    COMPATIBLE_TLS = localBuilder3.tlsVersions(arrayOfTlsVersion2).supportsTlsExtensions(true).build();
  }

  private ConnectionSpec(Builder paramBuilder)
  {
    this.tls = paramBuilder.tls;
    this.cipherSuites = paramBuilder.cipherSuites;
    this.tlsVersions = paramBuilder.tlsVersions;
    this.supportsTlsExtensions = paramBuilder.supportsTlsExtensions;
  }

  private ConnectionSpec supportedSpec(SSLSocket paramSSLSocket)
  {
    String[] arrayOfString1 = this.cipherSuites;
    String[] arrayOfString2 = null;
    if (arrayOfString1 != null)
    {
      String[] arrayOfString5 = paramSSLSocket.getEnabledCipherSuites();
      arrayOfString2 = (String[])Util.intersect(String.class, this.cipherSuites, arrayOfString5);
    }
    String[] arrayOfString3 = paramSSLSocket.getEnabledProtocols();
    String[] arrayOfString4 = (String[])Util.intersect(String.class, this.tlsVersions, arrayOfString3);
    return new Builder(this).cipherSuites(arrayOfString2).tlsVersions(arrayOfString4).build();
  }

  void apply(SSLSocket paramSSLSocket, Route paramRoute)
  {
    ConnectionSpec localConnectionSpec = supportedSpec(paramSSLSocket);
    paramSSLSocket.setEnabledProtocols(localConnectionSpec.tlsVersions);
    Object localObject1 = localConnectionSpec.cipherSuites;
    if ((paramRoute.shouldSendTlsFallbackIndicator) && (Arrays.asList(paramSSLSocket.getSupportedCipherSuites()).contains("TLS_FALLBACK_SCSV")))
      if (localObject1 == null)
        break label133;
    label133: for (Object localObject2 = localObject1; ; localObject2 = paramSSLSocket.getEnabledCipherSuites())
    {
      String[] arrayOfString = new String[1 + localObject2.length];
      System.arraycopy(localObject2, 0, arrayOfString, 0, localObject2.length);
      arrayOfString[(-1 + arrayOfString.length)] = "TLS_FALLBACK_SCSV";
      localObject1 = arrayOfString;
      if (localObject1 != null)
        paramSSLSocket.setEnabledCipherSuites((String[])localObject1);
      Platform localPlatform = Platform.get();
      if (localConnectionSpec.supportsTlsExtensions)
        localPlatform.configureTlsExtensions(paramSSLSocket, paramRoute.address.uriHost, paramRoute.address.protocols);
      return;
    }
  }

  public List<CipherSuite> cipherSuites()
  {
    if (this.cipherSuites == null)
      return null;
    CipherSuite[] arrayOfCipherSuite = new CipherSuite[this.cipherSuites.length];
    for (int i = 0; i < this.cipherSuites.length; i++)
      arrayOfCipherSuite[i] = CipherSuite.forJavaName(this.cipherSuites[i]);
    return Util.immutableList(arrayOfCipherSuite);
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ConnectionSpec));
    ConnectionSpec localConnectionSpec;
    do
    {
      return false;
      if (paramObject == this)
        return true;
      localConnectionSpec = (ConnectionSpec)paramObject;
    }
    while ((this.tls != localConnectionSpec.tls) || ((this.tls) && ((!Arrays.equals(this.cipherSuites, localConnectionSpec.cipherSuites)) || (!Arrays.equals(this.tlsVersions, localConnectionSpec.tlsVersions)) || (this.supportsTlsExtensions != localConnectionSpec.supportsTlsExtensions))));
    return true;
  }

  public int hashCode()
  {
    int i = 17;
    int j;
    if (this.tls)
    {
      j = 31 * (31 * (527 + Arrays.hashCode(this.cipherSuites)) + Arrays.hashCode(this.tlsVersions));
      if (!this.supportsTlsExtensions)
        break label51;
    }
    label51: for (int k = 0; ; k = 1)
    {
      i = j + k;
      return i;
    }
  }

  public boolean isTls()
  {
    return this.tls;
  }

  public boolean supportsTlsExtensions()
  {
    return this.supportsTlsExtensions;
  }

  public List<TlsVersion> tlsVersions()
  {
    TlsVersion[] arrayOfTlsVersion = new TlsVersion[this.tlsVersions.length];
    for (int i = 0; i < this.tlsVersions.length; i++)
      arrayOfTlsVersion[i] = TlsVersion.forJavaName(this.tlsVersions[i]);
    return Util.immutableList(arrayOfTlsVersion);
  }

  public String toString()
  {
    if (this.tls)
    {
      List localList = cipherSuites();
      if (localList == null);
      for (String str = "[use default]"; ; str = localList.toString())
        return "ConnectionSpec(cipherSuites=" + str + ", tlsVersions=" + tlsVersions() + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")";
    }
    return "ConnectionSpec()";
  }

  public static final class Builder
  {
    private String[] cipherSuites;
    private boolean supportsTlsExtensions;
    private boolean tls;
    private String[] tlsVersions;

    public Builder(ConnectionSpec paramConnectionSpec)
    {
      this.tls = paramConnectionSpec.tls;
      this.cipherSuites = paramConnectionSpec.cipherSuites;
      this.tlsVersions = paramConnectionSpec.tlsVersions;
      this.supportsTlsExtensions = paramConnectionSpec.supportsTlsExtensions;
    }

    Builder(boolean paramBoolean)
    {
      this.tls = paramBoolean;
    }

    public ConnectionSpec build()
    {
      return new ConnectionSpec(this, null);
    }

    public Builder cipherSuites(CipherSuite[] paramArrayOfCipherSuite)
    {
      if (!this.tls)
        throw new IllegalStateException("no cipher suites for cleartext connections");
      String[] arrayOfString = new String[paramArrayOfCipherSuite.length];
      for (int i = 0; i < paramArrayOfCipherSuite.length; i++)
        arrayOfString[i] = paramArrayOfCipherSuite[i].javaName;
      this.cipherSuites = arrayOfString;
      return this;
    }

    public Builder cipherSuites(String[] paramArrayOfString)
    {
      if (!this.tls)
        throw new IllegalStateException("no cipher suites for cleartext connections");
      if (paramArrayOfString == null)
      {
        this.cipherSuites = null;
        return this;
      }
      this.cipherSuites = ((String[])paramArrayOfString.clone());
      return this;
    }

    public Builder supportsTlsExtensions(boolean paramBoolean)
    {
      if (!this.tls)
        throw new IllegalStateException("no TLS extensions for cleartext connections");
      this.supportsTlsExtensions = paramBoolean;
      return this;
    }

    public Builder tlsVersions(TlsVersion[] paramArrayOfTlsVersion)
    {
      if (!this.tls)
        throw new IllegalStateException("no TLS versions for cleartext connections");
      String[] arrayOfString = new String[paramArrayOfTlsVersion.length];
      for (int i = 0; i < paramArrayOfTlsVersion.length; i++)
        arrayOfString[i] = paramArrayOfTlsVersion[i].javaName;
      this.tlsVersions = arrayOfString;
      return this;
    }

    public Builder tlsVersions(String[] paramArrayOfString)
    {
      if (!this.tls)
        throw new IllegalStateException("no TLS versions for cleartext connections");
      if (paramArrayOfString == null)
      {
        this.tlsVersions = null;
        return this;
      }
      this.tlsVersions = ((String[])paramArrayOfString.clone());
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.ConnectionSpec
 * JD-Core Version:    0.6.2
 */