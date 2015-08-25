package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.HttpMethod;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public final class Request
{
  private final RequestBody body;
  private volatile CacheControl cacheControl;
  private final Headers headers;
  private final String method;
  private final Object tag;
  private volatile URI uri;
  private volatile URL url;
  private final String urlString;

  private Request(Builder paramBuilder)
  {
    this.urlString = paramBuilder.urlString;
    this.method = paramBuilder.method;
    this.headers = paramBuilder.headers.build();
    this.body = paramBuilder.body;
    if (paramBuilder.tag != null);
    for (Object localObject = paramBuilder.tag; ; localObject = this)
    {
      this.tag = localObject;
      this.url = paramBuilder.url;
      return;
    }
  }

  public RequestBody body()
  {
    return this.body;
  }

  public CacheControl cacheControl()
  {
    CacheControl localCacheControl1 = this.cacheControl;
    if (localCacheControl1 != null)
      return localCacheControl1;
    CacheControl localCacheControl2 = CacheControl.parse(this.headers);
    this.cacheControl = localCacheControl2;
    return localCacheControl2;
  }

  public String header(String paramString)
  {
    return this.headers.get(paramString);
  }

  public Headers headers()
  {
    return this.headers;
  }

  public List<String> headers(String paramString)
  {
    return this.headers.values(paramString);
  }

  public boolean isHttps()
  {
    return url().getProtocol().equals("https");
  }

  public String method()
  {
    return this.method;
  }

  public Builder newBuilder()
  {
    return new Builder(this, null);
  }

  public Object tag()
  {
    return this.tag;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append("Request{method=").append(this.method).append(", url=").append(this.urlString).append(", tag=");
    if (this.tag != this);
    for (Object localObject = this.tag; ; localObject = null)
      return localObject + '}';
  }

  public URI uri()
    throws IOException
  {
    try
    {
      URI localURI1 = this.uri;
      if (localURI1 != null)
        return localURI1;
      URI localURI2 = Platform.get().toUriLenient(url());
      this.uri = localURI2;
      return localURI2;
    }
    catch (URISyntaxException localURISyntaxException)
    {
      throw new IOException(localURISyntaxException.getMessage());
    }
  }

  public URL url()
  {
    try
    {
      URL localURL1 = this.url;
      if (localURL1 != null)
        return localURL1;
      URL localURL2 = new URL(this.urlString);
      this.url = localURL2;
      return localURL2;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new RuntimeException("Malformed URL: " + this.urlString, localMalformedURLException);
    }
  }

  public String urlString()
  {
    return this.urlString;
  }

  public static class Builder
  {
    private RequestBody body;
    private Headers.Builder headers;
    private String method;
    private Object tag;
    private URL url;
    private String urlString;

    public Builder()
    {
      this.method = "GET";
      this.headers = new Headers.Builder();
    }

    private Builder(Request paramRequest)
    {
      this.urlString = paramRequest.urlString;
      this.url = paramRequest.url;
      this.method = paramRequest.method;
      this.body = paramRequest.body;
      this.tag = paramRequest.tag;
      this.headers = paramRequest.headers.newBuilder();
    }

    public Builder addHeader(String paramString1, String paramString2)
    {
      this.headers.add(paramString1, paramString2);
      return this;
    }

    public Request build()
    {
      if (this.urlString == null)
        throw new IllegalStateException("url == null");
      return new Request(this, null);
    }

    public Builder cacheControl(CacheControl paramCacheControl)
    {
      String str = paramCacheControl.toString();
      if (str.isEmpty())
        return removeHeader("Cache-Control");
      return header("Cache-Control", str);
    }

    public Builder delete()
    {
      return method("DELETE", null);
    }

    public Builder delete(RequestBody paramRequestBody)
    {
      return method("DELETE", paramRequestBody);
    }

    public Builder get()
    {
      return method("GET", null);
    }

    public Builder head()
    {
      return method("HEAD", null);
    }

    public Builder header(String paramString1, String paramString2)
    {
      this.headers.set(paramString1, paramString2);
      return this;
    }

    public Builder headers(Headers paramHeaders)
    {
      this.headers = paramHeaders.newBuilder();
      return this;
    }

    public Builder method(String paramString, RequestBody paramRequestBody)
    {
      if ((paramString == null) || (paramString.length() == 0))
        throw new IllegalArgumentException("method == null || method.length() == 0");
      if ((paramRequestBody != null) && (!HttpMethod.permitsRequestBody(paramString)))
        throw new IllegalArgumentException("method " + paramString + " must not have a request body.");
      if ((paramRequestBody == null) && (HttpMethod.permitsRequestBody(paramString)))
        paramRequestBody = RequestBody.create(null, Util.EMPTY_BYTE_ARRAY);
      this.method = paramString;
      this.body = paramRequestBody;
      return this;
    }

    public Builder patch(RequestBody paramRequestBody)
    {
      return method("PATCH", paramRequestBody);
    }

    public Builder post(RequestBody paramRequestBody)
    {
      return method("POST", paramRequestBody);
    }

    public Builder put(RequestBody paramRequestBody)
    {
      return method("PUT", paramRequestBody);
    }

    public Builder removeHeader(String paramString)
    {
      this.headers.removeAll(paramString);
      return this;
    }

    public Builder tag(Object paramObject)
    {
      this.tag = paramObject;
      return this;
    }

    public Builder url(String paramString)
    {
      if (paramString == null)
        throw new IllegalArgumentException("url == null");
      this.urlString = paramString;
      this.url = null;
      return this;
    }

    public Builder url(URL paramURL)
    {
      if (paramURL == null)
        throw new IllegalArgumentException("url == null");
      this.url = paramURL;
      this.urlString = paramURL.toString();
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Request
 * JD-Core Version:    0.6.2
 */