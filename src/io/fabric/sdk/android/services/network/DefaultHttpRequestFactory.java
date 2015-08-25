package io.fabric.sdk.android.services.network;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Logger;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class DefaultHttpRequestFactory
  implements HttpRequestFactory
{
  private static final String HTTPS = "https";
  private boolean attemptedSslInit;
  private final Logger logger;
  private PinningInfoProvider pinningInfo;
  private SSLSocketFactory sslSocketFactory;

  public DefaultHttpRequestFactory()
  {
    this(new DefaultLogger());
  }

  public DefaultHttpRequestFactory(Logger paramLogger)
  {
    this.logger = paramLogger;
  }

  private SSLSocketFactory getSSLSocketFactory()
  {
    try
    {
      if ((this.sslSocketFactory == null) && (!this.attemptedSslInit))
        this.sslSocketFactory = initSSLSocketFactory();
      SSLSocketFactory localSSLSocketFactory = this.sslSocketFactory;
      return localSSLSocketFactory;
    }
    finally
    {
    }
  }

  private SSLSocketFactory initSSLSocketFactory()
  {
    try
    {
      this.attemptedSslInit = true;
      try
      {
        localSSLSocketFactory = NetworkUtils.getSSLSocketFactory(this.pinningInfo);
        this.logger.d("Fabric", "Custom SSL pinning enabled");
        return localSSLSocketFactory;
      }
      catch (Exception localException)
      {
        while (true)
        {
          this.logger.e("Fabric", "Exception while validating pinned certs", localException);
          SSLSocketFactory localSSLSocketFactory = null;
        }
      }
    }
    finally
    {
    }
  }

  private boolean isHttps(String paramString)
  {
    return (paramString != null) && (paramString.toLowerCase(Locale.US).startsWith("https"));
  }

  private void resetSSLSocketFactory()
  {
    try
    {
      this.attemptedSslInit = false;
      this.sslSocketFactory = null;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString)
  {
    return buildHttpRequest(paramHttpMethod, paramString, Collections.emptyMap());
  }

  public HttpRequest buildHttpRequest(HttpMethod paramHttpMethod, String paramString, Map<String, String> paramMap)
  {
    HttpRequest localHttpRequest;
    switch (1.$SwitchMap$io$fabric$sdk$android$services$network$HttpMethod[paramHttpMethod.ordinal()])
    {
    default:
      throw new IllegalArgumentException("Unsupported HTTP method!");
    case 1:
      localHttpRequest = HttpRequest.get(paramString, paramMap, true);
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      if ((isHttps(paramString)) && (this.pinningInfo != null))
      {
        SSLSocketFactory localSSLSocketFactory = getSSLSocketFactory();
        if (localSSLSocketFactory != null)
          ((HttpsURLConnection)localHttpRequest.getConnection()).setSSLSocketFactory(localSSLSocketFactory);
      }
      return localHttpRequest;
      localHttpRequest = HttpRequest.post(paramString, paramMap, true);
      continue;
      localHttpRequest = HttpRequest.put(paramString);
      continue;
      localHttpRequest = HttpRequest.delete(paramString);
    }
  }

  public PinningInfoProvider getPinningInfoProvider()
  {
    return this.pinningInfo;
  }

  public void setPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider)
  {
    if (this.pinningInfo != paramPinningInfoProvider)
    {
      this.pinningInfo = paramPinningInfoProvider;
      resetSSLSocketFactory();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.DefaultHttpRequestFactory
 * JD-Core Version:    0.6.2
 */