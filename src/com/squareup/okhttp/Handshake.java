package com.squareup.okhttp;

import com.squareup.okhttp.internal.Util;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;

public final class Handshake
{
  private final String cipherSuite;
  private final List<Certificate> localCertificates;
  private final List<Certificate> peerCertificates;

  private Handshake(String paramString, List<Certificate> paramList1, List<Certificate> paramList2)
  {
    this.cipherSuite = paramString;
    this.peerCertificates = paramList1;
    this.localCertificates = paramList2;
  }

  public static Handshake get(String paramString, List<Certificate> paramList1, List<Certificate> paramList2)
  {
    if (paramString == null)
      throw new IllegalArgumentException("cipherSuite == null");
    return new Handshake(paramString, Util.immutableList(paramList1), Util.immutableList(paramList2));
  }

  public static Handshake get(SSLSession paramSSLSession)
  {
    String str = paramSSLSession.getCipherSuite();
    if (str == null)
      throw new IllegalStateException("cipherSuite == null");
    try
    {
      Certificate[] arrayOfCertificate3 = paramSSLSession.getPeerCertificates();
      arrayOfCertificate1 = arrayOfCertificate3;
      if (arrayOfCertificate1 != null)
      {
        localList1 = Util.immutableList(arrayOfCertificate1);
        Certificate[] arrayOfCertificate2 = paramSSLSession.getLocalCertificates();
        if (arrayOfCertificate2 == null)
          break label89;
        localList2 = Util.immutableList(arrayOfCertificate2);
        return new Handshake(str, localList1, localList2);
      }
    }
    catch (SSLPeerUnverifiedException localSSLPeerUnverifiedException)
    {
      while (true)
      {
        Certificate[] arrayOfCertificate1 = null;
        continue;
        List localList1 = Collections.emptyList();
        continue;
        label89: List localList2 = Collections.emptyList();
      }
    }
  }

  public String cipherSuite()
  {
    return this.cipherSuite;
  }

  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Handshake));
    Handshake localHandshake;
    do
    {
      return false;
      localHandshake = (Handshake)paramObject;
    }
    while ((!this.cipherSuite.equals(localHandshake.cipherSuite)) || (!this.peerCertificates.equals(localHandshake.peerCertificates)) || (!this.localCertificates.equals(localHandshake.localCertificates)));
    return true;
  }

  public int hashCode()
  {
    return 31 * (31 * (527 + this.cipherSuite.hashCode()) + this.peerCertificates.hashCode()) + this.localCertificates.hashCode();
  }

  public List<Certificate> localCertificates()
  {
    return this.localCertificates;
  }

  public Principal localPrincipal()
  {
    if (!this.localCertificates.isEmpty())
      return ((X509Certificate)this.localCertificates.get(0)).getSubjectX500Principal();
    return null;
  }

  public List<Certificate> peerCertificates()
  {
    return this.peerCertificates;
  }

  public Principal peerPrincipal()
  {
    if (!this.peerCertificates.isEmpty())
      return ((X509Certificate)this.peerCertificates.get(0)).getSubjectX500Principal();
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Handshake
 * JD-Core Version:    0.6.2
 */