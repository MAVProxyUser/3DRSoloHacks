package io.fabric.sdk.android.services.network;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public final class NetworkUtils
{
  public static final SSLSocketFactory getSSLSocketFactory(PinningInfoProvider paramPinningInfoProvider)
    throws KeyManagementException, NoSuchAlgorithmException
  {
    SSLContext localSSLContext = SSLContext.getInstance("TLS");
    localSSLContext.init(null, new TrustManager[] { new PinningTrustManager(new SystemKeyStore(paramPinningInfoProvider.getKeyStoreStream(), paramPinningInfoProvider.getKeyStorePassword()), paramPinningInfoProvider) }, null);
    return localSSLContext.getSocketFactory();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.NetworkUtils
 * JD-Core Version:    0.6.2
 */