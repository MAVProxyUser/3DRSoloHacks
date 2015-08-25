package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;
import java.util.HashMap;
import javax.security.auth.x500.X500Principal;

class SystemKeyStore
{
  private final HashMap<Principal, X509Certificate> trustRoots;
  final KeyStore trustStore;

  public SystemKeyStore(InputStream paramInputStream, String paramString)
  {
    KeyStore localKeyStore = getTrustStore(paramInputStream, paramString);
    this.trustRoots = initializeTrustedRoots(localKeyStore);
    this.trustStore = localKeyStore;
  }

  private KeyStore getTrustStore(InputStream paramInputStream, String paramString)
  {
    try
    {
      KeyStore localKeyStore = KeyStore.getInstance("BKS");
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(paramInputStream);
      try
      {
        localKeyStore.load(localBufferedInputStream, paramString.toCharArray());
        return localKeyStore;
      }
      finally
      {
        localBufferedInputStream.close();
      }
    }
    catch (KeyStoreException localKeyStoreException)
    {
      throw new AssertionError(localKeyStoreException);
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError(localNoSuchAlgorithmException);
    }
    catch (CertificateException localCertificateException)
    {
      throw new AssertionError(localCertificateException);
    }
    catch (IOException localIOException)
    {
      throw new AssertionError(localIOException);
    }
  }

  private HashMap<Principal, X509Certificate> initializeTrustedRoots(KeyStore paramKeyStore)
  {
    HashMap localHashMap;
    try
    {
      localHashMap = new HashMap();
      Enumeration localEnumeration = paramKeyStore.aliases();
      while (localEnumeration.hasMoreElements())
      {
        X509Certificate localX509Certificate = (X509Certificate)paramKeyStore.getCertificate((String)localEnumeration.nextElement());
        if (localX509Certificate != null)
          localHashMap.put(localX509Certificate.getSubjectX500Principal(), localX509Certificate);
      }
    }
    catch (KeyStoreException localKeyStoreException)
    {
      throw new AssertionError(localKeyStoreException);
    }
    return localHashMap;
  }

  public X509Certificate getTrustRootFor(X509Certificate paramX509Certificate)
  {
    X509Certificate localX509Certificate = (X509Certificate)this.trustRoots.get(paramX509Certificate.getIssuerX500Principal());
    if (localX509Certificate == null)
      return null;
    if (localX509Certificate.getSubjectX500Principal().equals(paramX509Certificate.getSubjectX500Principal()))
      return null;
    try
    {
      paramX509Certificate.verify(localX509Certificate.getPublicKey());
      return localX509Certificate;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
    }
    return null;
  }

  public boolean isTrustRoot(X509Certificate paramX509Certificate)
  {
    X509Certificate localX509Certificate = (X509Certificate)this.trustRoots.get(paramX509Certificate.getSubjectX500Principal());
    return (localX509Certificate != null) && (localX509Certificate.getPublicKey().equals(paramX509Certificate.getPublicKey()));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.SystemKeyStore
 * JD-Core Version:    0.6.2
 */