package com.squareup.okhttp.internal.huc;

import com.squareup.okhttp.Handshake;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.security.Permission;
import java.security.Principal;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Map;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSocketFactory;

abstract class DelegatingHttpsURLConnection extends HttpsURLConnection
{
  private final HttpURLConnection delegate;

  public DelegatingHttpsURLConnection(HttpURLConnection paramHttpURLConnection)
  {
    super(paramHttpURLConnection.getURL());
    this.delegate = paramHttpURLConnection;
  }

  public void addRequestProperty(String paramString1, String paramString2)
  {
    this.delegate.addRequestProperty(paramString1, paramString2);
  }

  public void connect()
    throws IOException
  {
    this.connected = true;
    this.delegate.connect();
  }

  public void disconnect()
  {
    this.delegate.disconnect();
  }

  public boolean getAllowUserInteraction()
  {
    return this.delegate.getAllowUserInteraction();
  }

  public String getCipherSuite()
  {
    Handshake localHandshake = handshake();
    if (localHandshake != null)
      return localHandshake.cipherSuite();
    return null;
  }

  public int getConnectTimeout()
  {
    return this.delegate.getConnectTimeout();
  }

  public Object getContent()
    throws IOException
  {
    return this.delegate.getContent();
  }

  public Object getContent(Class[] paramArrayOfClass)
    throws IOException
  {
    return this.delegate.getContent(paramArrayOfClass);
  }

  public String getContentEncoding()
  {
    return this.delegate.getContentEncoding();
  }

  public int getContentLength()
  {
    return this.delegate.getContentLength();
  }

  public String getContentType()
  {
    return this.delegate.getContentType();
  }

  public long getDate()
  {
    return this.delegate.getDate();
  }

  public boolean getDefaultUseCaches()
  {
    return this.delegate.getDefaultUseCaches();
  }

  public boolean getDoInput()
  {
    return this.delegate.getDoInput();
  }

  public boolean getDoOutput()
  {
    return this.delegate.getDoOutput();
  }

  public InputStream getErrorStream()
  {
    return this.delegate.getErrorStream();
  }

  public long getExpiration()
  {
    return this.delegate.getExpiration();
  }

  public String getHeaderField(int paramInt)
  {
    return this.delegate.getHeaderField(paramInt);
  }

  public String getHeaderField(String paramString)
  {
    return this.delegate.getHeaderField(paramString);
  }

  public long getHeaderFieldDate(String paramString, long paramLong)
  {
    return this.delegate.getHeaderFieldDate(paramString, paramLong);
  }

  public int getHeaderFieldInt(String paramString, int paramInt)
  {
    return this.delegate.getHeaderFieldInt(paramString, paramInt);
  }

  public String getHeaderFieldKey(int paramInt)
  {
    return this.delegate.getHeaderFieldKey(paramInt);
  }

  public Map<String, List<String>> getHeaderFields()
  {
    return this.delegate.getHeaderFields();
  }

  public abstract HostnameVerifier getHostnameVerifier();

  public long getIfModifiedSince()
  {
    return this.delegate.getIfModifiedSince();
  }

  public InputStream getInputStream()
    throws IOException
  {
    return this.delegate.getInputStream();
  }

  public boolean getInstanceFollowRedirects()
  {
    return this.delegate.getInstanceFollowRedirects();
  }

  public long getLastModified()
  {
    return this.delegate.getLastModified();
  }

  public Certificate[] getLocalCertificates()
  {
    Handshake localHandshake = handshake();
    if (localHandshake == null);
    List localList;
    do
    {
      return null;
      localList = localHandshake.localCertificates();
    }
    while (localList.isEmpty());
    return (Certificate[])localList.toArray(new Certificate[localList.size()]);
  }

  public Principal getLocalPrincipal()
  {
    Handshake localHandshake = handshake();
    if (localHandshake != null)
      return localHandshake.localPrincipal();
    return null;
  }

  public OutputStream getOutputStream()
    throws IOException
  {
    return this.delegate.getOutputStream();
  }

  public Principal getPeerPrincipal()
    throws SSLPeerUnverifiedException
  {
    Handshake localHandshake = handshake();
    if (localHandshake != null)
      return localHandshake.peerPrincipal();
    return null;
  }

  public Permission getPermission()
    throws IOException
  {
    return this.delegate.getPermission();
  }

  public int getReadTimeout()
  {
    return this.delegate.getReadTimeout();
  }

  public String getRequestMethod()
  {
    return this.delegate.getRequestMethod();
  }

  public Map<String, List<String>> getRequestProperties()
  {
    return this.delegate.getRequestProperties();
  }

  public String getRequestProperty(String paramString)
  {
    return this.delegate.getRequestProperty(paramString);
  }

  public int getResponseCode()
    throws IOException
  {
    return this.delegate.getResponseCode();
  }

  public String getResponseMessage()
    throws IOException
  {
    return this.delegate.getResponseMessage();
  }

  public abstract SSLSocketFactory getSSLSocketFactory();

  public Certificate[] getServerCertificates()
    throws SSLPeerUnverifiedException
  {
    Handshake localHandshake = handshake();
    if (localHandshake == null);
    List localList;
    do
    {
      return null;
      localList = localHandshake.peerCertificates();
    }
    while (localList.isEmpty());
    return (Certificate[])localList.toArray(new Certificate[localList.size()]);
  }

  public URL getURL()
  {
    return this.delegate.getURL();
  }

  public boolean getUseCaches()
  {
    return this.delegate.getUseCaches();
  }

  protected abstract Handshake handshake();

  public void setAllowUserInteraction(boolean paramBoolean)
  {
    this.delegate.setAllowUserInteraction(paramBoolean);
  }

  public void setChunkedStreamingMode(int paramInt)
  {
    this.delegate.setChunkedStreamingMode(paramInt);
  }

  public void setConnectTimeout(int paramInt)
  {
    this.delegate.setConnectTimeout(paramInt);
  }

  public void setDefaultUseCaches(boolean paramBoolean)
  {
    this.delegate.setDefaultUseCaches(paramBoolean);
  }

  public void setDoInput(boolean paramBoolean)
  {
    this.delegate.setDoInput(paramBoolean);
  }

  public void setDoOutput(boolean paramBoolean)
  {
    this.delegate.setDoOutput(paramBoolean);
  }

  public void setFixedLengthStreamingMode(int paramInt)
  {
    this.delegate.setFixedLengthStreamingMode(paramInt);
  }

  public abstract void setHostnameVerifier(HostnameVerifier paramHostnameVerifier);

  public void setIfModifiedSince(long paramLong)
  {
    this.delegate.setIfModifiedSince(paramLong);
  }

  public void setInstanceFollowRedirects(boolean paramBoolean)
  {
    this.delegate.setInstanceFollowRedirects(paramBoolean);
  }

  public void setReadTimeout(int paramInt)
  {
    this.delegate.setReadTimeout(paramInt);
  }

  public void setRequestMethod(String paramString)
    throws ProtocolException
  {
    this.delegate.setRequestMethod(paramString);
  }

  public void setRequestProperty(String paramString1, String paramString2)
  {
    this.delegate.setRequestProperty(paramString1, paramString2);
  }

  public abstract void setSSLSocketFactory(SSLSocketFactory paramSSLSocketFactory);

  public void setUseCaches(boolean paramBoolean)
  {
    this.delegate.setUseCaches(paramBoolean);
  }

  public String toString()
  {
    return this.delegate.toString();
  }

  public boolean usingProxy()
  {
    return this.delegate.usingProxy();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.huc.DelegatingHttpsURLConnection
 * JD-Core Version:    0.6.2
 */