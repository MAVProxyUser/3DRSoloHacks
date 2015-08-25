package io.fabric.sdk.android.services.network;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Flushable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.Proxy.Type;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.PrivilegedAction;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class HttpRequest
{
  private static final String BOUNDARY = "00content0boundary00";
  public static final String CHARSET_UTF8 = "UTF-8";
  private static ConnectionFactory CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
  public static final String CONTENT_TYPE_FORM = "application/x-www-form-urlencoded";
  public static final String CONTENT_TYPE_JSON = "application/json";
  private static final String CONTENT_TYPE_MULTIPART = "multipart/form-data; boundary=00content0boundary00";
  private static final String CRLF = "\r\n";
  private static final String[] EMPTY_STRINGS = new String[0];
  public static final String ENCODING_GZIP = "gzip";
  public static final String HEADER_ACCEPT = "Accept";
  public static final String HEADER_ACCEPT_CHARSET = "Accept-Charset";
  public static final String HEADER_ACCEPT_ENCODING = "Accept-Encoding";
  public static final String HEADER_AUTHORIZATION = "Authorization";
  public static final String HEADER_CACHE_CONTROL = "Cache-Control";
  public static final String HEADER_CONTENT_ENCODING = "Content-Encoding";
  public static final String HEADER_CONTENT_LENGTH = "Content-Length";
  public static final String HEADER_CONTENT_TYPE = "Content-Type";
  public static final String HEADER_DATE = "Date";
  public static final String HEADER_ETAG = "ETag";
  public static final String HEADER_EXPIRES = "Expires";
  public static final String HEADER_IF_NONE_MATCH = "If-None-Match";
  public static final String HEADER_LAST_MODIFIED = "Last-Modified";
  public static final String HEADER_LOCATION = "Location";
  public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";
  public static final String HEADER_REFERER = "Referer";
  public static final String HEADER_SERVER = "Server";
  public static final String HEADER_USER_AGENT = "User-Agent";
  public static final String METHOD_DELETE = "DELETE";
  public static final String METHOD_GET = "GET";
  public static final String METHOD_HEAD = "HEAD";
  public static final String METHOD_OPTIONS = "OPTIONS";
  public static final String METHOD_POST = "POST";
  public static final String METHOD_PUT = "PUT";
  public static final String METHOD_TRACE = "TRACE";
  public static final String PARAM_CHARSET = "charset";
  private static SSLSocketFactory TRUSTED_FACTORY;
  private static HostnameVerifier TRUSTED_VERIFIER;
  private int bufferSize = 8192;
  private HttpURLConnection connection = null;
  private boolean form;
  private String httpProxyHost;
  private int httpProxyPort;
  private boolean ignoreCloseExceptions = true;
  private boolean multipart;
  private RequestOutputStream output;
  private final String requestMethod;
  private boolean uncompress = false;
  public final URL url;

  public HttpRequest(CharSequence paramCharSequence, String paramString)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      this.url = new URL(paramCharSequence.toString());
      this.requestMethod = paramString;
      return;
    }
    catch (MalformedURLException localMalformedURLException)
    {
      throw new HttpRequestException(localMalformedURLException);
    }
  }

  public HttpRequest(URL paramURL, String paramString)
    throws HttpRequest.HttpRequestException
  {
    this.url = paramURL;
    this.requestMethod = paramString;
  }

  private static StringBuilder addParamPrefix(String paramString, StringBuilder paramStringBuilder)
  {
    int i = paramString.indexOf('?');
    int j = -1 + paramStringBuilder.length();
    if (i == -1)
      paramStringBuilder.append('?');
    while ((i >= j) || (paramString.charAt(j) == '&'))
      return paramStringBuilder;
    paramStringBuilder.append('&');
    return paramStringBuilder;
  }

  private static StringBuilder addPathSeparator(String paramString, StringBuilder paramStringBuilder)
  {
    if (2 + paramString.indexOf(':') == paramString.lastIndexOf('/'))
      paramStringBuilder.append('/');
    return paramStringBuilder;
  }

  public static String append(CharSequence paramCharSequence, Map<?, ?> paramMap)
  {
    String str = paramCharSequence.toString();
    if ((paramMap == null) || (paramMap.isEmpty()))
      return str;
    StringBuilder localStringBuilder = new StringBuilder(str);
    addPathSeparator(str, localStringBuilder);
    addParamPrefix(str, localStringBuilder);
    Iterator localIterator = paramMap.entrySet().iterator();
    Map.Entry localEntry1 = (Map.Entry)localIterator.next();
    localStringBuilder.append(localEntry1.getKey().toString());
    localStringBuilder.append('=');
    Object localObject1 = localEntry1.getValue();
    if (localObject1 != null)
      localStringBuilder.append(localObject1);
    while (localIterator.hasNext())
    {
      localStringBuilder.append('&');
      Map.Entry localEntry2 = (Map.Entry)localIterator.next();
      localStringBuilder.append(localEntry2.getKey().toString());
      localStringBuilder.append('=');
      Object localObject2 = localEntry2.getValue();
      if (localObject2 != null)
        localStringBuilder.append(localObject2);
    }
    return localStringBuilder.toString();
  }

  public static String append(CharSequence paramCharSequence, Object[] paramArrayOfObject)
  {
    String str = paramCharSequence.toString();
    if ((paramArrayOfObject == null) || (paramArrayOfObject.length == 0))
      return str;
    if (paramArrayOfObject.length % 2 != 0)
      throw new IllegalArgumentException("Must specify an even number of parameter names/values");
    StringBuilder localStringBuilder = new StringBuilder(str);
    addPathSeparator(str, localStringBuilder);
    addParamPrefix(str, localStringBuilder);
    localStringBuilder.append(paramArrayOfObject[0]);
    localStringBuilder.append('=');
    Object localObject1 = paramArrayOfObject[1];
    if (localObject1 != null)
      localStringBuilder.append(localObject1);
    for (int i = 2; i < paramArrayOfObject.length; i += 2)
    {
      localStringBuilder.append('&');
      localStringBuilder.append(paramArrayOfObject[i]);
      localStringBuilder.append('=');
      Object localObject2 = paramArrayOfObject[(i + 1)];
      if (localObject2 != null)
        localStringBuilder.append(localObject2);
    }
    return localStringBuilder.toString();
  }

  private HttpURLConnection createConnection()
  {
    try
    {
      if (this.httpProxyHost != null);
      HttpURLConnection localHttpURLConnection;
      for (Object localObject = CONNECTION_FACTORY.create(this.url, createProxy()); ; localObject = localHttpURLConnection)
      {
        ((HttpURLConnection)localObject).setRequestMethod(this.requestMethod);
        return localObject;
        localHttpURLConnection = CONNECTION_FACTORY.create(this.url);
      }
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  private Proxy createProxy()
  {
    return new Proxy(Proxy.Type.HTTP, new InetSocketAddress(this.httpProxyHost, this.httpProxyPort));
  }

  public static HttpRequest delete(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "DELETE");
  }

  public static HttpRequest delete(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    String str = append(paramCharSequence, paramMap);
    if (paramBoolean)
      str = encode(str);
    return delete(str);
  }

  public static HttpRequest delete(CharSequence paramCharSequence, boolean paramBoolean, Object[] paramArrayOfObject)
  {
    String str = append(paramCharSequence, paramArrayOfObject);
    if (paramBoolean)
      str = encode(str);
    return delete(str);
  }

  public static HttpRequest delete(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "DELETE");
  }

  // ERROR //
  public static String encode(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    // Byte code:
    //   0: new 159	java/net/URL
    //   3: dup
    //   4: aload_0
    //   5: invokeinterface 165 1 0
    //   10: invokespecial 168	java/net/URL:<init>	(Ljava/lang/String;)V
    //   13: astore_1
    //   14: aload_1
    //   15: invokevirtual 324	java/net/URL:getHost	()Ljava/lang/String;
    //   18: astore_2
    //   19: aload_1
    //   20: invokevirtual 327	java/net/URL:getPort	()I
    //   23: istore_3
    //   24: iload_3
    //   25: iconst_m1
    //   26: if_icmpeq +30 -> 56
    //   29: new 191	java/lang/StringBuilder
    //   32: dup
    //   33: invokespecial 328	java/lang/StringBuilder:<init>	()V
    //   36: aload_2
    //   37: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   40: bipush 58
    //   42: invokevirtual 199	java/lang/StringBuilder:append	(C)Ljava/lang/StringBuilder;
    //   45: iload_3
    //   46: invokestatic 333	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   49: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   52: invokevirtual 254	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: astore_2
    //   56: new 335	java/net/URI
    //   59: dup
    //   60: aload_1
    //   61: invokevirtual 338	java/net/URL:getProtocol	()Ljava/lang/String;
    //   64: aload_2
    //   65: aload_1
    //   66: invokevirtual 341	java/net/URL:getPath	()Ljava/lang/String;
    //   69: aload_1
    //   70: invokevirtual 344	java/net/URL:getQuery	()Ljava/lang/String;
    //   73: aconst_null
    //   74: invokespecial 347	java/net/URI:<init>	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   77: invokevirtual 350	java/net/URI:toASCIIString	()Ljava/lang/String;
    //   80: astore 7
    //   82: aload 7
    //   84: bipush 63
    //   86: invokevirtual 189	java/lang/String:indexOf	(I)I
    //   89: istore 8
    //   91: iload 8
    //   93: ifle +65 -> 158
    //   96: iload 8
    //   98: iconst_1
    //   99: iadd
    //   100: aload 7
    //   102: invokevirtual 351	java/lang/String:length	()I
    //   105: if_icmpge +53 -> 158
    //   108: new 191	java/lang/StringBuilder
    //   111: dup
    //   112: invokespecial 328	java/lang/StringBuilder:<init>	()V
    //   115: aload 7
    //   117: iconst_0
    //   118: iload 8
    //   120: iconst_1
    //   121: iadd
    //   122: invokevirtual 355	java/lang/String:substring	(II)Ljava/lang/String;
    //   125: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   128: aload 7
    //   130: iload 8
    //   132: iconst_1
    //   133: iadd
    //   134: invokevirtual 357	java/lang/String:substring	(I)Ljava/lang/String;
    //   137: ldc_w 359
    //   140: ldc_w 361
    //   143: invokevirtual 365	java/lang/String:replace	(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Ljava/lang/String;
    //   146: invokevirtual 244	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   149: invokevirtual 254	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   152: astore 9
    //   154: aload 9
    //   156: astore 7
    //   158: aload 7
    //   160: areturn
    //   161: astore 10
    //   163: new 145	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   166: dup
    //   167: aload 10
    //   169: invokespecial 175	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   172: athrow
    //   173: astore 4
    //   175: new 264	java/io/IOException
    //   178: dup
    //   179: ldc_w 367
    //   182: invokespecial 368	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   185: astore 5
    //   187: aload 5
    //   189: aload 4
    //   191: invokevirtual 372	java/io/IOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   194: pop
    //   195: new 145	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   198: dup
    //   199: aload 5
    //   201: invokespecial 175	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   204: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   0	14	161	java/io/IOException
    //   56	91	173	java/net/URISyntaxException
    //   96	154	173	java/net/URISyntaxException
  }

  public static HttpRequest get(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "GET");
  }

  public static HttpRequest get(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    String str = append(paramCharSequence, paramMap);
    if (paramBoolean)
      str = encode(str);
    return get(str);
  }

  public static HttpRequest get(CharSequence paramCharSequence, boolean paramBoolean, Object[] paramArrayOfObject)
  {
    String str = append(paramCharSequence, paramArrayOfObject);
    if (paramBoolean)
      str = encode(str);
    return get(str);
  }

  public static HttpRequest get(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "GET");
  }

  private static SSLSocketFactory getTrustedFactory()
    throws HttpRequest.HttpRequestException
  {
    TrustManager[] arrayOfTrustManager;
    if (TRUSTED_FACTORY == null)
    {
      arrayOfTrustManager = new TrustManager[1];
      arrayOfTrustManager[0] = new X509TrustManager()
      {
        public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        {
        }

        public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
        {
        }

        public X509Certificate[] getAcceptedIssuers()
        {
          return new X509Certificate[0];
        }
      };
    }
    try
    {
      SSLContext localSSLContext = SSLContext.getInstance("TLS");
      localSSLContext.init(null, arrayOfTrustManager, new SecureRandom());
      TRUSTED_FACTORY = localSSLContext.getSocketFactory();
      return TRUSTED_FACTORY;
    }
    catch (GeneralSecurityException localGeneralSecurityException)
    {
      IOException localIOException = new IOException("Security exception configuring SSL context");
      localIOException.initCause(localGeneralSecurityException);
      throw new HttpRequestException(localIOException);
    }
  }

  private static HostnameVerifier getTrustedVerifier()
  {
    if (TRUSTED_VERIFIER == null)
      TRUSTED_VERIFIER = new HostnameVerifier()
      {
        public boolean verify(String paramAnonymousString, SSLSession paramAnonymousSSLSession)
        {
          return true;
        }
      };
    return TRUSTED_VERIFIER;
  }

  private static String getValidCharset(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0))
      return paramString;
    return "UTF-8";
  }

  public static HttpRequest head(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "HEAD");
  }

  public static HttpRequest head(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    String str = append(paramCharSequence, paramMap);
    if (paramBoolean)
      str = encode(str);
    return head(str);
  }

  public static HttpRequest head(CharSequence paramCharSequence, boolean paramBoolean, Object[] paramArrayOfObject)
  {
    String str = append(paramCharSequence, paramArrayOfObject);
    if (paramBoolean)
      str = encode(str);
    return head(str);
  }

  public static HttpRequest head(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "HEAD");
  }

  public static void keepAlive(boolean paramBoolean)
  {
    setProperty("http.keepAlive", Boolean.toString(paramBoolean));
  }

  public static void nonProxyHosts(String[] paramArrayOfString)
  {
    if ((paramArrayOfString != null) && (paramArrayOfString.length > 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      int i = -1 + paramArrayOfString.length;
      for (int j = 0; j < i; j++)
        localStringBuilder.append(paramArrayOfString[j]).append('|');
      localStringBuilder.append(paramArrayOfString[i]);
      setProperty("http.nonProxyHosts", localStringBuilder.toString());
      return;
    }
    setProperty("http.nonProxyHosts", null);
  }

  public static HttpRequest options(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "OPTIONS");
  }

  public static HttpRequest options(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "OPTIONS");
  }

  public static HttpRequest post(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "POST");
  }

  public static HttpRequest post(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    String str = append(paramCharSequence, paramMap);
    if (paramBoolean)
      str = encode(str);
    return post(str);
  }

  public static HttpRequest post(CharSequence paramCharSequence, boolean paramBoolean, Object[] paramArrayOfObject)
  {
    String str = append(paramCharSequence, paramArrayOfObject);
    if (paramBoolean)
      str = encode(str);
    return post(str);
  }

  public static HttpRequest post(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "POST");
  }

  public static void proxyHost(String paramString)
  {
    setProperty("http.proxyHost", paramString);
    setProperty("https.proxyHost", paramString);
  }

  public static void proxyPort(int paramInt)
  {
    String str = Integer.toString(paramInt);
    setProperty("http.proxyPort", str);
    setProperty("https.proxyPort", str);
  }

  public static HttpRequest put(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "PUT");
  }

  public static HttpRequest put(CharSequence paramCharSequence, Map<?, ?> paramMap, boolean paramBoolean)
  {
    String str = append(paramCharSequence, paramMap);
    if (paramBoolean)
      str = encode(str);
    return put(str);
  }

  public static HttpRequest put(CharSequence paramCharSequence, boolean paramBoolean, Object[] paramArrayOfObject)
  {
    String str = append(paramCharSequence, paramArrayOfObject);
    if (paramBoolean)
      str = encode(str);
    return put(str);
  }

  public static HttpRequest put(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "PUT");
  }

  public static void setConnectionFactory(ConnectionFactory paramConnectionFactory)
  {
    if (paramConnectionFactory == null)
    {
      CONNECTION_FACTORY = ConnectionFactory.DEFAULT;
      return;
    }
    CONNECTION_FACTORY = paramConnectionFactory;
  }

  private static String setProperty(String paramString1, final String paramString2)
  {
    if (paramString2 != null);
    for (Object localObject = new PrivilegedAction()
    {
      public String run()
      {
        return System.setProperty(this.val$name, paramString2);
      }
    }
    ; ; localObject = new PrivilegedAction()
    {
      public String run()
      {
        return System.clearProperty(this.val$name);
      }
    })
      return (String)AccessController.doPrivileged((PrivilegedAction)localObject);
  }

  public static HttpRequest trace(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramCharSequence, "TRACE");
  }

  public static HttpRequest trace(URL paramURL)
    throws HttpRequest.HttpRequestException
  {
    return new HttpRequest(paramURL, "TRACE");
  }

  public HttpRequest accept(String paramString)
  {
    return header("Accept", paramString);
  }

  public HttpRequest acceptCharset(String paramString)
  {
    return header("Accept-Charset", paramString);
  }

  public HttpRequest acceptEncoding(String paramString)
  {
    return header("Accept-Encoding", paramString);
  }

  public HttpRequest acceptGzipEncoding()
  {
    return acceptEncoding("gzip");
  }

  public HttpRequest acceptJson()
  {
    return accept("application/json");
  }

  public HttpRequest authorization(String paramString)
  {
    return header("Authorization", paramString);
  }

  public boolean badRequest()
    throws HttpRequest.HttpRequestException
  {
    return 400 == code();
  }

  public HttpRequest basic(String paramString1, String paramString2)
  {
    return authorization("Basic " + Base64.encode(new StringBuilder().append(paramString1).append(':').append(paramString2).toString()));
  }

  public HttpRequest body(AtomicReference<String> paramAtomicReference)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicReference.set(body());
    return this;
  }

  public HttpRequest body(AtomicReference<String> paramAtomicReference, String paramString)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicReference.set(body(paramString));
    return this;
  }

  public String body()
    throws HttpRequest.HttpRequestException
  {
    return body(charset());
  }

  public String body(String paramString)
    throws HttpRequest.HttpRequestException
  {
    ByteArrayOutputStream localByteArrayOutputStream = byteStream();
    try
    {
      copy(buffer(), localByteArrayOutputStream);
      String str = localByteArrayOutputStream.toString(getValidCharset(paramString));
      return str;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public BufferedInputStream buffer()
    throws HttpRequest.HttpRequestException
  {
    return new BufferedInputStream(stream(), this.bufferSize);
  }

  public int bufferSize()
  {
    return this.bufferSize;
  }

  public HttpRequest bufferSize(int paramInt)
  {
    if (paramInt < 1)
      throw new IllegalArgumentException("Size must be greater than zero");
    this.bufferSize = paramInt;
    return this;
  }

  public BufferedReader bufferedReader()
    throws HttpRequest.HttpRequestException
  {
    return bufferedReader(charset());
  }

  public BufferedReader bufferedReader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return new BufferedReader(reader(paramString), this.bufferSize);
  }

  protected ByteArrayOutputStream byteStream()
  {
    int i = contentLength();
    if (i > 0)
      return new ByteArrayOutputStream(i);
    return new ByteArrayOutputStream();
  }

  public byte[] bytes()
    throws HttpRequest.HttpRequestException
  {
    ByteArrayOutputStream localByteArrayOutputStream = byteStream();
    try
    {
      copy(buffer(), localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public String cacheControl()
  {
    return header("Cache-Control");
  }

  public String charset()
  {
    return parameter("Content-Type", "charset");
  }

  public HttpRequest chunk(int paramInt)
  {
    getConnection().setChunkedStreamingMode(paramInt);
    return this;
  }

  protected HttpRequest closeOutput()
    throws IOException
  {
    if (this.output == null)
      return this;
    if (this.multipart)
      this.output.write("\r\n--00content0boundary00--\r\n");
    if (this.ignoreCloseExceptions);
    try
    {
      this.output.close();
      while (true)
      {
        label41: this.output = null;
        return this;
        this.output.close();
      }
    }
    catch (IOException localIOException)
    {
      break label41;
    }
  }

  protected HttpRequest closeOutputQuietly()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      HttpRequest localHttpRequest = closeOutput();
      return localHttpRequest;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public int code()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      closeOutput();
      int i = getConnection().getResponseCode();
      return i;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public HttpRequest code(AtomicInteger paramAtomicInteger)
    throws HttpRequest.HttpRequestException
  {
    paramAtomicInteger.set(code());
    return this;
  }

  public HttpRequest connectTimeout(int paramInt)
  {
    getConnection().setConnectTimeout(paramInt);
    return this;
  }

  public String contentEncoding()
  {
    return header("Content-Encoding");
  }

  public int contentLength()
  {
    return intHeader("Content-Length");
  }

  public HttpRequest contentLength(int paramInt)
  {
    getConnection().setFixedLengthStreamingMode(paramInt);
    return this;
  }

  public HttpRequest contentLength(String paramString)
  {
    return contentLength(Integer.parseInt(paramString));
  }

  public HttpRequest contentType(String paramString)
  {
    return contentType(paramString, null);
  }

  public HttpRequest contentType(String paramString1, String paramString2)
  {
    if ((paramString2 != null) && (paramString2.length() > 0))
      return header("Content-Type", paramString1 + "; charset=" + paramString2);
    return header("Content-Type", paramString1);
  }

  public String contentType()
  {
    return header("Content-Type");
  }

  protected HttpRequest copy(final InputStream paramInputStream, final OutputStream paramOutputStream)
    throws IOException
  {
    return (HttpRequest)new CloseOperation(paramInputStream, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        byte[] arrayOfByte = new byte[HttpRequest.this.bufferSize];
        while (true)
        {
          int i = paramInputStream.read(arrayOfByte);
          if (i == -1)
            break;
          paramOutputStream.write(arrayOfByte, 0, i);
        }
        return HttpRequest.this;
      }
    }
    .call();
  }

  protected HttpRequest copy(final Reader paramReader, final Writer paramWriter)
    throws IOException
  {
    return (HttpRequest)new CloseOperation(paramReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        char[] arrayOfChar = new char[HttpRequest.this.bufferSize];
        while (true)
        {
          int i = paramReader.read(arrayOfChar);
          if (i == -1)
            break;
          paramWriter.write(arrayOfChar, 0, i);
        }
        return HttpRequest.this;
      }
    }
    .call();
  }

  public boolean created()
    throws HttpRequest.HttpRequestException
  {
    return 201 == code();
  }

  public long date()
  {
    return dateHeader("Date");
  }

  public long dateHeader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return dateHeader(paramString, -1L);
  }

  public long dateHeader(String paramString, long paramLong)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFieldDate(paramString, paramLong);
  }

  public HttpRequest disconnect()
  {
    getConnection().disconnect();
    return this;
  }

  public String eTag()
  {
    return header("ETag");
  }

  public long expires()
  {
    return dateHeader("Expires");
  }

  public HttpRequest followRedirects(boolean paramBoolean)
  {
    getConnection().setInstanceFollowRedirects(paramBoolean);
    return this;
  }

  public HttpRequest form(Object paramObject1, Object paramObject2)
    throws HttpRequest.HttpRequestException
  {
    return form(paramObject1, paramObject2, "UTF-8");
  }

  public HttpRequest form(Object paramObject1, Object paramObject2, String paramString)
    throws HttpRequest.HttpRequestException
  {
    int i;
    if (!this.form)
      i = 1;
    while (true)
    {
      if (i != 0)
      {
        contentType("application/x-www-form-urlencoded", paramString);
        this.form = true;
      }
      String str = getValidCharset(paramString);
      try
      {
        openOutput();
        if (i == 0)
          this.output.write(38);
        this.output.write(URLEncoder.encode(paramObject1.toString(), str));
        this.output.write(61);
        if (paramObject2 != null)
          this.output.write(URLEncoder.encode(paramObject2.toString(), str));
        return this;
        i = 0;
      }
      catch (IOException localIOException)
      {
        throw new HttpRequestException(localIOException);
      }
    }
  }

  public HttpRequest form(Map.Entry<?, ?> paramEntry)
    throws HttpRequest.HttpRequestException
  {
    return form(paramEntry, "UTF-8");
  }

  public HttpRequest form(Map.Entry<?, ?> paramEntry, String paramString)
    throws HttpRequest.HttpRequestException
  {
    return form(paramEntry.getKey(), paramEntry.getValue(), paramString);
  }

  public HttpRequest form(Map<?, ?> paramMap)
    throws HttpRequest.HttpRequestException
  {
    return form(paramMap, "UTF-8");
  }

  public HttpRequest form(Map<?, ?> paramMap, String paramString)
    throws HttpRequest.HttpRequestException
  {
    if (!paramMap.isEmpty())
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
        form((Map.Entry)localIterator.next(), paramString);
    }
    return this;
  }

  public HttpURLConnection getConnection()
  {
    if (this.connection == null)
      this.connection = createConnection();
    return this.connection;
  }

  protected String getParam(String paramString1, String paramString2)
  {
    String str;
    if ((paramString1 == null) || (paramString1.length() == 0))
    {
      str = null;
      return str;
    }
    int i = paramString1.length();
    int j = 1 + paramString1.indexOf(';');
    if ((j == 0) || (j == i))
      return null;
    int k = paramString1.indexOf(';', j);
    if (k == -1)
      k = i;
    while (true)
    {
      if (j >= k)
        break label206;
      int m = paramString1.indexOf('=', j);
      if ((m != -1) && (m < k) && (paramString2.equals(paramString1.substring(j, m).trim())))
      {
        str = paramString1.substring(m + 1, k).trim();
        int n = str.length();
        if (n != 0)
        {
          if ((n <= 2) || ('"' != str.charAt(0)) || ('"' != str.charAt(n - 1)))
            break;
          return str.substring(1, n - 1);
        }
      }
      j = k + 1;
      k = paramString1.indexOf(';', j);
      if (k == -1)
        k = i;
    }
    label206: return null;
  }

  protected Map<String, String> getParams(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0))
    {
      localObject = Collections.emptyMap();
      return localObject;
    }
    int i = paramString.length();
    int j = 1 + paramString.indexOf(';');
    if ((j == 0) || (j == i))
      return Collections.emptyMap();
    int k = paramString.indexOf(';', j);
    if (k == -1)
      k = i;
    Object localObject = new LinkedHashMap();
    label74: String str1;
    String str2;
    if (j < k)
    {
      int m = paramString.indexOf('=', j);
      if ((m != -1) && (m < k))
      {
        str1 = paramString.substring(j, m).trim();
        if (str1.length() > 0)
        {
          str2 = paramString.substring(m + 1, k).trim();
          int n = str2.length();
          if (n != 0)
          {
            if ((n <= 2) || ('"' != str2.charAt(0)) || ('"' != str2.charAt(n - 1)))
              break label230;
            ((Map)localObject).put(str1, str2.substring(1, n - 1));
          }
        }
      }
    }
    while (true)
    {
      j = k + 1;
      k = paramString.indexOf(';', j);
      if (k != -1)
        break label74;
      k = i;
      break label74;
      break;
      label230: ((Map)localObject).put(str1, str2);
    }
  }

  public HttpRequest header(String paramString, Number paramNumber)
  {
    if (paramNumber != null);
    for (String str = paramNumber.toString(); ; str = null)
      return header(paramString, str);
  }

  public HttpRequest header(String paramString1, String paramString2)
  {
    getConnection().setRequestProperty(paramString1, paramString2);
    return this;
  }

  public HttpRequest header(Map.Entry<String, String> paramEntry)
  {
    return header((String)paramEntry.getKey(), (String)paramEntry.getValue());
  }

  public String header(String paramString)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderField(paramString);
  }

  public HttpRequest headers(Map<String, String> paramMap)
  {
    if (!paramMap.isEmpty())
    {
      Iterator localIterator = paramMap.entrySet().iterator();
      while (localIterator.hasNext())
        header((Map.Entry)localIterator.next());
    }
    return this;
  }

  public Map<String, List<String>> headers()
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFields();
  }

  public String[] headers(String paramString)
  {
    Map localMap = headers();
    if ((localMap == null) || (localMap.isEmpty()))
      return EMPTY_STRINGS;
    List localList = (List)localMap.get(paramString);
    if ((localList != null) && (!localList.isEmpty()))
      return (String[])localList.toArray(new String[localList.size()]);
    return EMPTY_STRINGS;
  }

  public HttpRequest ifModifiedSince(long paramLong)
  {
    getConnection().setIfModifiedSince(paramLong);
    return this;
  }

  public HttpRequest ifNoneMatch(String paramString)
  {
    return header("If-None-Match", paramString);
  }

  public HttpRequest ignoreCloseExceptions(boolean paramBoolean)
  {
    this.ignoreCloseExceptions = paramBoolean;
    return this;
  }

  public boolean ignoreCloseExceptions()
  {
    return this.ignoreCloseExceptions;
  }

  public int intHeader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    return intHeader(paramString, -1);
  }

  public int intHeader(String paramString, int paramInt)
    throws HttpRequest.HttpRequestException
  {
    closeOutputQuietly();
    return getConnection().getHeaderFieldInt(paramString, paramInt);
  }

  public boolean isBodyEmpty()
    throws HttpRequest.HttpRequestException
  {
    return contentLength() == 0;
  }

  public long lastModified()
  {
    return dateHeader("Last-Modified");
  }

  public String location()
  {
    return header("Location");
  }

  public String message()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      closeOutput();
      String str = getConnection().getResponseMessage();
      return str;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public String method()
  {
    return getConnection().getRequestMethod();
  }

  public boolean notFound()
    throws HttpRequest.HttpRequestException
  {
    return 404 == code();
  }

  public boolean notModified()
    throws HttpRequest.HttpRequestException
  {
    return 304 == code();
  }

  public boolean ok()
    throws HttpRequest.HttpRequestException
  {
    return 200 == code();
  }

  protected HttpRequest openOutput()
    throws IOException
  {
    if (this.output != null)
      return this;
    getConnection().setDoOutput(true);
    String str = getParam(getConnection().getRequestProperty("Content-Type"), "charset");
    this.output = new RequestOutputStream(getConnection().getOutputStream(), str, this.bufferSize);
    return this;
  }

  public String parameter(String paramString1, String paramString2)
  {
    return getParam(header(paramString1), paramString2);
  }

  public Map<String, String> parameters(String paramString)
  {
    return getParams(header(paramString));
  }

  public HttpRequest part(String paramString, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, paramFile);
  }

  public HttpRequest part(String paramString, InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, null, paramInputStream);
  }

  public HttpRequest part(String paramString, Number paramNumber)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString, null, paramNumber);
  }

  public HttpRequest part(String paramString1, String paramString2)
  {
    return part(paramString1, null, paramString2);
  }

  public HttpRequest part(String paramString1, String paramString2, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString1, paramString2, null, paramFile);
  }

  public HttpRequest part(String paramString1, String paramString2, Number paramNumber)
    throws HttpRequest.HttpRequestException
  {
    if (paramNumber != null);
    for (String str = paramNumber.toString(); ; str = null)
      return part(paramString1, paramString2, str);
  }

  public HttpRequest part(String paramString1, String paramString2, String paramString3)
    throws HttpRequest.HttpRequestException
  {
    return part(paramString1, paramString2, null, paramString3);
  }

  // ERROR //
  public HttpRequest part(String paramString1, String paramString2, String paramString3, File paramFile)
    throws HttpRequest.HttpRequestException
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: new 530	java/io/BufferedInputStream
    //   6: dup
    //   7: new 817	java/io/FileInputStream
    //   10: dup
    //   11: aload 4
    //   13: invokespecial 820	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   16: invokespecial 823	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   19: astore 6
    //   21: aload_0
    //   22: aload_1
    //   23: aload_2
    //   24: aload_3
    //   25: aload 6
    //   27: invokevirtual 803	io/fabric/sdk/android/services/network/HttpRequest:part	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/InputStream;)Lio/fabric/sdk/android/services/network/HttpRequest;
    //   30: astore 10
    //   32: aload 6
    //   34: ifnull +8 -> 42
    //   37: aload 6
    //   39: invokevirtual 826	java/io/InputStream:close	()V
    //   42: aload 10
    //   44: areturn
    //   45: astore 7
    //   47: new 145	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
    //   50: dup
    //   51: aload 7
    //   53: invokespecial 175	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
    //   56: athrow
    //   57: astore 8
    //   59: aload 5
    //   61: ifnull +8 -> 69
    //   64: aload 5
    //   66: invokevirtual 826	java/io/InputStream:close	()V
    //   69: aload 8
    //   71: athrow
    //   72: astore 11
    //   74: aload 10
    //   76: areturn
    //   77: astore 9
    //   79: goto -10 -> 69
    //   82: astore 8
    //   84: aload 6
    //   86: astore 5
    //   88: goto -29 -> 59
    //   91: astore 7
    //   93: aload 6
    //   95: astore 5
    //   97: goto -50 -> 47
    //
    // Exception table:
    //   from	to	target	type
    //   3	21	45	java/io/IOException
    //   3	21	57	finally
    //   47	57	57	finally
    //   37	42	72	java/io/IOException
    //   64	69	77	java/io/IOException
    //   21	32	82	finally
    //   21	32	91	java/io/IOException
  }

  public HttpRequest part(String paramString1, String paramString2, String paramString3, InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      startPart();
      writePartHeader(paramString1, paramString2, paramString3);
      copy(paramInputStream, this.output);
      return this;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public HttpRequest part(String paramString1, String paramString2, String paramString3, String paramString4)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      startPart();
      writePartHeader(paramString1, paramString2, paramString3);
      this.output.write(paramString4);
      return this;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public HttpRequest partHeader(String paramString1, String paramString2)
    throws HttpRequest.HttpRequestException
  {
    return send(paramString1).send(": ").send(paramString2).send("\r\n");
  }

  public HttpRequest proxyAuthorization(String paramString)
  {
    return header("Proxy-Authorization", paramString);
  }

  public HttpRequest proxyBasic(String paramString1, String paramString2)
  {
    return proxyAuthorization("Basic " + Base64.encode(new StringBuilder().append(paramString1).append(':').append(paramString2).toString()));
  }

  public HttpRequest readTimeout(int paramInt)
  {
    getConnection().setReadTimeout(paramInt);
    return this;
  }

  public InputStreamReader reader()
    throws HttpRequest.HttpRequestException
  {
    return reader(charset());
  }

  public InputStreamReader reader(String paramString)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      InputStreamReader localInputStreamReader = new InputStreamReader(stream(), getValidCharset(paramString));
      return localInputStreamReader;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      throw new HttpRequestException(localUnsupportedEncodingException);
    }
  }

  public HttpRequest receive(File paramFile)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      final BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(paramFile), this.bufferSize);
      return (HttpRequest)new CloseOperation(localBufferedOutputStream, this.ignoreCloseExceptions)
      {
        protected HttpRequest run()
          throws HttpRequest.HttpRequestException, IOException
        {
          return HttpRequest.this.receive(localBufferedOutputStream);
        }
      }
      .call();
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      throw new HttpRequestException(localFileNotFoundException);
    }
  }

  public HttpRequest receive(OutputStream paramOutputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      HttpRequest localHttpRequest = copy(buffer(), paramOutputStream);
      return localHttpRequest;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public HttpRequest receive(PrintStream paramPrintStream)
    throws HttpRequest.HttpRequestException
  {
    return receive(paramPrintStream);
  }

  public HttpRequest receive(final Writer paramWriter)
    throws HttpRequest.HttpRequestException
  {
    final BufferedReader localBufferedReader = bufferedReader();
    return (HttpRequest)new CloseOperation(localBufferedReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        return HttpRequest.this.copy(localBufferedReader, paramWriter);
      }
    }
    .call();
  }

  public HttpRequest receive(final Appendable paramAppendable)
    throws HttpRequest.HttpRequestException
  {
    final BufferedReader localBufferedReader = bufferedReader();
    return (HttpRequest)new CloseOperation(localBufferedReader, this.ignoreCloseExceptions)
    {
      public HttpRequest run()
        throws IOException
      {
        CharBuffer localCharBuffer = CharBuffer.allocate(HttpRequest.this.bufferSize);
        while (true)
        {
          int i = localBufferedReader.read(localCharBuffer);
          if (i == -1)
            break;
          localCharBuffer.rewind();
          paramAppendable.append(localCharBuffer, 0, i);
          localCharBuffer.rewind();
        }
        return HttpRequest.this;
      }
    }
    .call();
  }

  public HttpRequest referer(String paramString)
  {
    return header("Referer", paramString);
  }

  public HttpRequest send(File paramFile)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream(paramFile));
      return send(localBufferedInputStream);
    }
    catch (FileNotFoundException localFileNotFoundException)
    {
      throw new HttpRequestException(localFileNotFoundException);
    }
  }

  public HttpRequest send(InputStream paramInputStream)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      copy(paramInputStream, this.output);
      return this;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public HttpRequest send(final Reader paramReader)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      final OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(this.output, this.output.encoder.charset());
      return (HttpRequest)new FlushOperation(localOutputStreamWriter)
      {
        protected HttpRequest run()
          throws IOException
        {
          return HttpRequest.this.copy(paramReader, localOutputStreamWriter);
        }
      }
      .call();
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public HttpRequest send(CharSequence paramCharSequence)
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      this.output.write(paramCharSequence.toString());
      return this;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public HttpRequest send(byte[] paramArrayOfByte)
    throws HttpRequest.HttpRequestException
  {
    return send(new ByteArrayInputStream(paramArrayOfByte));
  }

  public String server()
  {
    return header("Server");
  }

  public boolean serverError()
    throws HttpRequest.HttpRequestException
  {
    return 500 == code();
  }

  protected HttpRequest startPart()
    throws IOException
  {
    if (!this.multipart)
    {
      this.multipart = true;
      contentType("multipart/form-data; boundary=00content0boundary00").openOutput();
      this.output.write("--00content0boundary00\r\n");
      return this;
    }
    this.output.write("\r\n--00content0boundary00\r\n");
    return this;
  }

  public InputStream stream()
    throws HttpRequest.HttpRequestException
  {
    if (code() < 400);
    Object localObject;
    while (true)
    {
      try
      {
        InputStream localInputStream2 = getConnection().getInputStream();
        localObject = localInputStream2;
        if ((this.uncompress) && ("gzip".equals(contentEncoding())))
          break;
        return localObject;
      }
      catch (IOException localIOException3)
      {
        throw new HttpRequestException(localIOException3);
      }
      localObject = getConnection().getErrorStream();
      if (localObject == null)
        try
        {
          InputStream localInputStream1 = getConnection().getInputStream();
          localObject = localInputStream1;
        }
        catch (IOException localIOException1)
        {
          throw new HttpRequestException(localIOException1);
        }
    }
    try
    {
      GZIPInputStream localGZIPInputStream = new GZIPInputStream((InputStream)localObject);
      return localGZIPInputStream;
    }
    catch (IOException localIOException2)
    {
      throw new HttpRequestException(localIOException2);
    }
  }

  public String toString()
  {
    return method() + ' ' + url();
  }

  public HttpRequest trustAllCerts()
    throws HttpRequest.HttpRequestException
  {
    HttpURLConnection localHttpURLConnection = getConnection();
    if ((localHttpURLConnection instanceof HttpsURLConnection))
      ((HttpsURLConnection)localHttpURLConnection).setSSLSocketFactory(getTrustedFactory());
    return this;
  }

  public HttpRequest trustAllHosts()
  {
    HttpURLConnection localHttpURLConnection = getConnection();
    if ((localHttpURLConnection instanceof HttpsURLConnection))
      ((HttpsURLConnection)localHttpURLConnection).setHostnameVerifier(getTrustedVerifier());
    return this;
  }

  public HttpRequest uncompress(boolean paramBoolean)
  {
    this.uncompress = paramBoolean;
    return this;
  }

  public URL url()
  {
    return getConnection().getURL();
  }

  public HttpRequest useCaches(boolean paramBoolean)
  {
    getConnection().setUseCaches(paramBoolean);
    return this;
  }

  public HttpRequest useProxy(String paramString, int paramInt)
  {
    if (this.connection != null)
      throw new IllegalStateException("The connection has already been created. This method must be called before reading or writing to the request.");
    this.httpProxyHost = paramString;
    this.httpProxyPort = paramInt;
    return this;
  }

  public HttpRequest userAgent(String paramString)
  {
    return header("User-Agent", paramString);
  }

  protected HttpRequest writePartHeader(String paramString1, String paramString2)
    throws IOException
  {
    return writePartHeader(paramString1, paramString2, null);
  }

  protected HttpRequest writePartHeader(String paramString1, String paramString2, String paramString3)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("form-data; name=\"").append(paramString1);
    if (paramString2 != null)
      localStringBuilder.append("\"; filename=\"").append(paramString2);
    localStringBuilder.append('"');
    partHeader("Content-Disposition", localStringBuilder.toString());
    if (paramString3 != null)
      partHeader("Content-Type", paramString3);
    return send("\r\n");
  }

  public OutputStreamWriter writer()
    throws HttpRequest.HttpRequestException
  {
    try
    {
      openOutput();
      OutputStreamWriter localOutputStreamWriter = new OutputStreamWriter(this.output, this.output.encoder.charset());
      return localOutputStreamWriter;
    }
    catch (IOException localIOException)
    {
      throw new HttpRequestException(localIOException);
    }
  }

  public static class Base64
  {
    private static final byte EQUALS_SIGN = 61;
    private static final String PREFERRED_ENCODING = "US-ASCII";
    private static final byte[] _STANDARD_ALPHABET = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

    public static String encode(String paramString)
    {
      try
      {
        byte[] arrayOfByte2 = paramString.getBytes("US-ASCII");
        arrayOfByte1 = arrayOfByte2;
        return encodeBytes(arrayOfByte1);
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        while (true)
          byte[] arrayOfByte1 = paramString.getBytes();
      }
    }

    private static byte[] encode3to4(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    {
      byte[] arrayOfByte = _STANDARD_ALPHABET;
      int i;
      if (paramInt2 > 0)
      {
        i = paramArrayOfByte1[paramInt1] << 24 >>> 8;
        label20: if (paramInt2 <= 1)
          break label108;
      }
      int n;
      label108: for (int j = paramArrayOfByte1[(paramInt1 + 1)] << 24 >>> 16; ; j = 0)
      {
        int k = j | i;
        int m = 0;
        if (paramInt2 > 2)
          m = paramArrayOfByte1[(paramInt1 + 2)] << 24 >>> 24;
        n = k | m;
        switch (paramInt2)
        {
        default:
          return paramArrayOfByte2;
          i = 0;
          break label20;
        case 3:
        case 2:
        case 1:
        }
      }
      paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
      paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
      paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(0x3F & n >>> 6)];
      paramArrayOfByte2[(paramInt3 + 3)] = arrayOfByte[(n & 0x3F)];
      return paramArrayOfByte2;
      paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
      paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
      paramArrayOfByte2[(paramInt3 + 2)] = arrayOfByte[(0x3F & n >>> 6)];
      paramArrayOfByte2[(paramInt3 + 3)] = 61;
      return paramArrayOfByte2;
      paramArrayOfByte2[paramInt3] = arrayOfByte[(n >>> 18)];
      paramArrayOfByte2[(paramInt3 + 1)] = arrayOfByte[(0x3F & n >>> 12)];
      paramArrayOfByte2[(paramInt3 + 2)] = 61;
      paramArrayOfByte2[(paramInt3 + 3)] = 61;
      return paramArrayOfByte2;
    }

    public static String encodeBytes(byte[] paramArrayOfByte)
    {
      return encodeBytes(paramArrayOfByte, 0, paramArrayOfByte.length);
    }

    public static String encodeBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      byte[] arrayOfByte = encodeBytesToBytes(paramArrayOfByte, paramInt1, paramInt2);
      try
      {
        String str = new String(arrayOfByte, "US-ASCII");
        return str;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
      return new String(arrayOfByte);
    }

    public static byte[] encodeBytesToBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    {
      if (paramArrayOfByte == null)
        throw new NullPointerException("Cannot serialize a null array.");
      if (paramInt1 < 0)
        throw new IllegalArgumentException("Cannot have negative offset: " + paramInt1);
      if (paramInt2 < 0)
        throw new IllegalArgumentException("Cannot have length offset: " + paramInt2);
      if (paramInt1 + paramInt2 > paramArrayOfByte.length)
      {
        Object[] arrayOfObject = new Object[3];
        arrayOfObject[0] = Integer.valueOf(paramInt1);
        arrayOfObject[1] = Integer.valueOf(paramInt2);
        arrayOfObject[2] = Integer.valueOf(paramArrayOfByte.length);
        throw new IllegalArgumentException(String.format("Cannot have offset of %d and length of %d with array of length %d", arrayOfObject));
      }
      int i = 4 * (paramInt2 / 3);
      if (paramInt2 % 3 > 0);
      byte[] arrayOfByte1;
      int k;
      int m;
      for (int j = 4; ; j = 0)
      {
        arrayOfByte1 = new byte[i + j];
        k = 0;
        m = 0;
        int n = paramInt2 - 2;
        while (k < n)
        {
          encode3to4(paramArrayOfByte, k + paramInt1, 3, arrayOfByte1, m);
          k += 3;
          m += 4;
        }
      }
      if (k < paramInt2)
      {
        encode3to4(paramArrayOfByte, k + paramInt1, paramInt2 - k, arrayOfByte1, m);
        m += 4;
      }
      if (m <= -1 + arrayOfByte1.length)
      {
        byte[] arrayOfByte2 = new byte[m];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, m);
        return arrayOfByte2;
      }
      return arrayOfByte1;
    }
  }

  protected static abstract class CloseOperation<V> extends HttpRequest.Operation<V>
  {
    private final Closeable closeable;
    private final boolean ignoreCloseExceptions;

    protected CloseOperation(Closeable paramCloseable, boolean paramBoolean)
    {
      this.closeable = paramCloseable;
      this.ignoreCloseExceptions = paramBoolean;
    }

    protected void done()
      throws IOException
    {
      if ((this.closeable instanceof Flushable))
        ((Flushable)this.closeable).flush();
      if (this.ignoreCloseExceptions);
      try
      {
        this.closeable.close();
        return;
        this.closeable.close();
        return;
      }
      catch (IOException localIOException)
      {
      }
    }
  }

  public static abstract interface ConnectionFactory
  {
    public static final ConnectionFactory DEFAULT = new ConnectionFactory()
    {
      public HttpURLConnection create(URL paramAnonymousURL)
        throws IOException
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection();
      }

      public HttpURLConnection create(URL paramAnonymousURL, Proxy paramAnonymousProxy)
        throws IOException
      {
        return (HttpURLConnection)paramAnonymousURL.openConnection(paramAnonymousProxy);
      }
    };

    public abstract HttpURLConnection create(URL paramURL)
      throws IOException;

    public abstract HttpURLConnection create(URL paramURL, Proxy paramProxy)
      throws IOException;
  }

  protected static abstract class FlushOperation<V> extends HttpRequest.Operation<V>
  {
    private final Flushable flushable;

    protected FlushOperation(Flushable paramFlushable)
    {
      this.flushable = paramFlushable;
    }

    protected void done()
      throws IOException
    {
      this.flushable.flush();
    }
  }

  public static class HttpRequestException extends RuntimeException
  {
    private static final long serialVersionUID = -1170466989781746231L;

    protected HttpRequestException(IOException paramIOException)
    {
      super();
    }

    public IOException getCause()
    {
      return (IOException)super.getCause();
    }
  }

  protected static abstract class Operation<V>
    implements Callable<V>
  {
    // ERROR //
    public V call()
      throws HttpRequest.HttpRequestException
    {
      // Byte code:
      //   0: iconst_0
      //   1: istore_1
      //   2: aload_0
      //   3: invokevirtual 20	io/fabric/sdk/android/services/network/HttpRequest$Operation:run	()Ljava/lang/Object;
      //   6: astore 6
      //   8: aload_0
      //   9: invokevirtual 23	io/fabric/sdk/android/services/network/HttpRequest$Operation:done	()V
      //   12: aload 6
      //   14: areturn
      //   15: astore 7
      //   17: iconst_0
      //   18: ifne -6 -> 12
      //   21: new 15	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   24: dup
      //   25: aload 7
      //   27: invokespecial 26	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   30: athrow
      //   31: astore 5
      //   33: iconst_1
      //   34: istore_1
      //   35: aload 5
      //   37: athrow
      //   38: astore_3
      //   39: aload_0
      //   40: invokevirtual 23	io/fabric/sdk/android/services/network/HttpRequest$Operation:done	()V
      //   43: aload_3
      //   44: athrow
      //   45: astore_2
      //   46: iconst_1
      //   47: istore_1
      //   48: new 15	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   51: dup
      //   52: aload_2
      //   53: invokespecial 26	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   56: athrow
      //   57: astore 4
      //   59: iload_1
      //   60: ifne -17 -> 43
      //   63: new 15	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   66: dup
      //   67: aload 4
      //   69: invokespecial 26	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException:<init>	(Ljava/io/IOException;)V
      //   72: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   8	12	15	java/io/IOException
      //   2	8	31	io/fabric/sdk/android/services/network/HttpRequest$HttpRequestException
      //   2	8	38	finally
      //   35	38	38	finally
      //   48	57	38	finally
      //   2	8	45	java/io/IOException
      //   39	43	57	java/io/IOException
    }

    protected abstract void done()
      throws IOException;

    protected abstract V run()
      throws HttpRequest.HttpRequestException, IOException;
  }

  public static class RequestOutputStream extends BufferedOutputStream
  {
    private final CharsetEncoder encoder;

    public RequestOutputStream(OutputStream paramOutputStream, String paramString, int paramInt)
    {
      super(paramInt);
      this.encoder = Charset.forName(HttpRequest.getValidCharset(paramString)).newEncoder();
    }

    public RequestOutputStream write(String paramString)
      throws IOException
    {
      ByteBuffer localByteBuffer = this.encoder.encode(CharBuffer.wrap(paramString));
      super.write(localByteBuffer.array(), 0, localByteBuffer.limit());
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.network.HttpRequest
 * JD-Core Version:    0.6.2
 */