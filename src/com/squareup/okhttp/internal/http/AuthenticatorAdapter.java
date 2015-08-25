package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Challenge;
import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.Response;
import java.io.IOException;
import java.net.Authenticator.RequestorType;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.util.List;

public final class AuthenticatorAdapter
  implements com.squareup.okhttp.Authenticator
{
  public static final com.squareup.okhttp.Authenticator INSTANCE = new AuthenticatorAdapter();

  private InetAddress getConnectToInetAddress(Proxy paramProxy, URL paramURL)
    throws IOException
  {
    if ((paramProxy != null) && (paramProxy.type() != Proxy.Type.DIRECT))
      return ((InetSocketAddress)paramProxy.address()).getAddress();
    return InetAddress.getByName(paramURL.getHost());
  }

  public Request authenticate(Proxy paramProxy, Response paramResponse)
    throws IOException
  {
    List localList = paramResponse.challenges();
    Request localRequest = paramResponse.request();
    URL localURL = localRequest.url();
    int i = 0;
    int j = localList.size();
    if (i < j)
    {
      Challenge localChallenge = (Challenge)localList.get(i);
      if (!"Basic".equalsIgnoreCase(localChallenge.getScheme()));
      PasswordAuthentication localPasswordAuthentication;
      do
      {
        i++;
        break;
        localPasswordAuthentication = java.net.Authenticator.requestPasswordAuthentication(localURL.getHost(), getConnectToInetAddress(paramProxy, localURL), localURL.getPort(), localURL.getProtocol(), localChallenge.getRealm(), localChallenge.getScheme(), localURL, Authenticator.RequestorType.SERVER);
      }
      while (localPasswordAuthentication == null);
      String str = Credentials.basic(localPasswordAuthentication.getUserName(), new String(localPasswordAuthentication.getPassword()));
      return localRequest.newBuilder().header("Authorization", str).build();
    }
    return null;
  }

  public Request authenticateProxy(Proxy paramProxy, Response paramResponse)
    throws IOException
  {
    List localList = paramResponse.challenges();
    Request localRequest = paramResponse.request();
    URL localURL = localRequest.url();
    int i = 0;
    int j = localList.size();
    if (i < j)
    {
      Challenge localChallenge = (Challenge)localList.get(i);
      if (!"Basic".equalsIgnoreCase(localChallenge.getScheme()));
      PasswordAuthentication localPasswordAuthentication;
      do
      {
        i++;
        break;
        InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramProxy.address();
        localPasswordAuthentication = java.net.Authenticator.requestPasswordAuthentication(localInetSocketAddress.getHostName(), getConnectToInetAddress(paramProxy, localURL), localInetSocketAddress.getPort(), localURL.getProtocol(), localChallenge.getRealm(), localChallenge.getScheme(), localURL, Authenticator.RequestorType.PROXY);
      }
      while (localPasswordAuthentication == null);
      String str = Credentials.basic(localPasswordAuthentication.getUserName(), new String(localPasswordAuthentication.getPassword()));
      return localRequest.newBuilder().header("Proxy-Authorization", str).build();
    }
    return null;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.AuthenticatorAdapter
 * JD-Core Version:    0.6.2
 */