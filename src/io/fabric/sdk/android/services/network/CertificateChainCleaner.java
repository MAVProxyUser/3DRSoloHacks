package io.fabric.sdk.android.services.network;

import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedList;
import javax.security.auth.x500.X500Principal;

final class CertificateChainCleaner
{
  public static X509Certificate[] getCleanChain(X509Certificate[] paramArrayOfX509Certificate, SystemKeyStore paramSystemKeyStore)
    throws CertificateException
  {
    LinkedList localLinkedList = new LinkedList();
    boolean bool = paramSystemKeyStore.isTrustRoot(paramArrayOfX509Certificate[0]);
    int i = 0;
    if (bool)
      i = 1;
    localLinkedList.add(paramArrayOfX509Certificate[0]);
    for (int j = 1; j < paramArrayOfX509Certificate.length; j++)
    {
      if (paramSystemKeyStore.isTrustRoot(paramArrayOfX509Certificate[j]))
        i = 1;
      if (!isValidLink(paramArrayOfX509Certificate[j], paramArrayOfX509Certificate[(j - 1)]))
        break;
      localLinkedList.add(paramArrayOfX509Certificate[j]);
    }
    X509Certificate localX509Certificate = paramSystemKeyStore.getTrustRootFor(paramArrayOfX509Certificate[(j - 1)]);
    if (localX509Certificate != null)
    {
      localLinkedList.add(localX509Certificate);
      i = 1;
    }
    if (i != 0)
      return (X509Certificate[])localLinkedList.toArray(new X509Certificate[localLinkedList.size()]);
    throw new CertificateException("Didn't find a trust anchor in chain cleanup!");
  }

  private static boolean isValidLink(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2)
  {
    if (!paramX509Certificate1.getSubjectX500Principal().equals(paramX509Certificate2.getIssuerX500Principal()))
      return false;
    try
    {
      paramX509Certificate2.verify(paramX509Certificate1.getPublicKey());
      return true;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
    }
    return false;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.CertificateChainCleaner
 * JD-Core Version:    0.6.2
 */