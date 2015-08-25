package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLPeerUnverifiedException;
import okio.ByteString;

public final class CertificatePinner
{
  public static final CertificatePinner DEFAULT = new Builder().build();
  private final Map<String, List<ByteString>> hostnameToPins;

  private CertificatePinner(Builder paramBuilder)
  {
    this.hostnameToPins = Util.immutableMap(paramBuilder.hostnameToPins);
  }

  public static String pin(Certificate paramCertificate)
  {
    if (!(paramCertificate instanceof X509Certificate))
      throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    return "sha1/" + sha1((X509Certificate)paramCertificate).base64();
  }

  private static ByteString sha1(X509Certificate paramX509Certificate)
  {
    return Util.sha1(ByteString.of(paramX509Certificate.getPublicKey().getEncoded()));
  }

  public void check(String paramString, List<Certificate> paramList)
    throws SSLPeerUnverifiedException
  {
    List localList = (List)this.hostnameToPins.get(paramString);
    if (localList == null)
      return;
    int i = 0;
    int j = paramList.size();
    while (true)
    {
      if (i >= j)
        break label66;
      if (localList.contains(sha1((X509Certificate)paramList.get(i))))
        break;
      i++;
    }
    label66: StringBuilder localStringBuilder = new StringBuilder().append("Certificate pinning failure!").append("\n  Peer certificate chain:");
    int k = 0;
    int m = paramList.size();
    while (k < m)
    {
      X509Certificate localX509Certificate = (X509Certificate)paramList.get(k);
      localStringBuilder.append("\n    ").append(pin(localX509Certificate)).append(": ").append(localX509Certificate.getSubjectDN().getName());
      k++;
    }
    localStringBuilder.append("\n  Pinned certificates for ").append(paramString).append(":");
    int n = 0;
    int i1 = localList.size();
    while (n < i1)
    {
      ByteString localByteString = (ByteString)localList.get(n);
      localStringBuilder.append("\n    sha1/").append(localByteString.base64());
      n++;
    }
    throw new SSLPeerUnverifiedException(localStringBuilder.toString());
  }

  public void check(String paramString, Certificate[] paramArrayOfCertificate)
    throws SSLPeerUnverifiedException
  {
    check(paramString, Arrays.asList(paramArrayOfCertificate));
  }

  public static final class Builder
  {
    private final Map<String, List<ByteString>> hostnameToPins = new LinkedHashMap();

    public Builder add(String paramString, String[] paramArrayOfString)
    {
      if (paramString == null)
        throw new IllegalArgumentException("hostname == null");
      ArrayList localArrayList = new ArrayList();
      List localList = (List)this.hostnameToPins.put(paramString, Collections.unmodifiableList(localArrayList));
      if (localList != null)
        localArrayList.addAll(localList);
      int i = paramArrayOfString.length;
      for (int j = 0; j < i; j++)
      {
        String str = paramArrayOfString[j];
        if (!str.startsWith("sha1/"))
          throw new IllegalArgumentException("pins must start with 'sha1/': " + str);
        ByteString localByteString = ByteString.decodeBase64(str.substring("sha1/".length()));
        if (localByteString == null)
          throw new IllegalArgumentException("pins must be base64: " + str);
        localArrayList.add(localByteString);
      }
      return this;
    }

    public CertificatePinner build()
    {
      return new CertificatePinner(this, null);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.CertificatePinner
 * JD-Core Version:    0.6.2
 */