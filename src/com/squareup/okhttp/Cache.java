package com.squareup.okhttp;

import com.squareup.okhttp.internal.DiskLruCache;
import com.squareup.okhttp.internal.DiskLruCache.Editor;
import com.squareup.okhttp.internal.DiskLruCache.Snapshot;
import com.squareup.okhttp.internal.InternalCache;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.http.CacheRequest;
import com.squareup.okhttp.internal.http.CacheStrategy;
import com.squareup.okhttp.internal.http.HttpMethod;
import com.squareup.okhttp.internal.http.OkHeaders;
import com.squareup.okhttp.internal.http.StatusLine;
import com.squareup.okhttp.internal.io.FileSystem;
import java.io.File;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class Cache
{
  private static final int ENTRY_BODY = 1;
  private static final int ENTRY_COUNT = 2;
  private static final int ENTRY_METADATA = 0;
  private static final int VERSION = 201105;
  private final DiskLruCache cache;
  private int hitCount;
  final InternalCache internalCache = new InternalCache()
  {
    public Response get(Request paramAnonymousRequest)
      throws IOException
    {
      return Cache.this.get(paramAnonymousRequest);
    }

    public CacheRequest put(Response paramAnonymousResponse)
      throws IOException
    {
      return Cache.this.put(paramAnonymousResponse);
    }

    public void remove(Request paramAnonymousRequest)
      throws IOException
    {
      Cache.this.remove(paramAnonymousRequest);
    }

    public void trackConditionalCacheHit()
    {
      Cache.this.trackConditionalCacheHit();
    }

    public void trackResponse(CacheStrategy paramAnonymousCacheStrategy)
    {
      Cache.this.trackResponse(paramAnonymousCacheStrategy);
    }

    public void update(Response paramAnonymousResponse1, Response paramAnonymousResponse2)
      throws IOException
    {
      Cache.this.update(paramAnonymousResponse1, paramAnonymousResponse2);
    }
  };
  private int networkCount;
  private int requestCount;
  private int writeAbortCount;
  private int writeSuccessCount;

  public Cache(File paramFile, long paramLong)
  {
    this.cache = DiskLruCache.create(FileSystem.SYSTEM, paramFile, 201105, 2, paramLong);
  }

  private void abortQuietly(DiskLruCache.Editor paramEditor)
  {
    if (paramEditor != null);
    try
    {
      paramEditor.abort();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  private CacheRequest put(Response paramResponse)
    throws IOException
  {
    String str = paramResponse.request().method();
    if (HttpMethod.invalidatesCache(paramResponse.request().method()));
    try
    {
      remove(paramResponse.request());
      while (true)
      {
        return null;
        if ((str.equals("GET")) && (!OkHeaders.hasVaryAll(paramResponse)))
        {
          Entry localEntry = new Entry(paramResponse);
          DiskLruCache.Editor localEditor = null;
          try
          {
            localEditor = this.cache.edit(urlToKey(paramResponse.request()));
            if (localEditor != null)
            {
              localEntry.writeTo(localEditor);
              CacheRequestImpl localCacheRequestImpl = new CacheRequestImpl(localEditor);
              return localCacheRequestImpl;
            }
          }
          catch (IOException localIOException1)
          {
            abortQuietly(localEditor);
            return null;
          }
        }
      }
    }
    catch (IOException localIOException2)
    {
    }
    return null;
  }

  private static int readInt(BufferedSource paramBufferedSource)
    throws IOException
  {
    long l;
    try
    {
      l = paramBufferedSource.readDecimalLong();
      String str = paramBufferedSource.readUtf8LineStrict();
      if ((l < 0L) || (l > 2147483647L) || (!str.isEmpty()))
        throw new IOException("expected an int but was \"" + l + str + "\"");
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new IOException(localNumberFormatException.getMessage());
    }
    return (int)l;
  }

  private void remove(Request paramRequest)
    throws IOException
  {
    this.cache.remove(urlToKey(paramRequest));
  }

  private void trackConditionalCacheHit()
  {
    try
    {
      this.hitCount = (1 + this.hitCount);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void trackResponse(CacheStrategy paramCacheStrategy)
  {
    try
    {
      this.requestCount = (1 + this.requestCount);
      if (paramCacheStrategy.networkRequest != null)
        this.networkCount = (1 + this.networkCount);
      while (true)
      {
        return;
        if (paramCacheStrategy.cacheResponse != null)
          this.hitCount = (1 + this.hitCount);
      }
    }
    finally
    {
    }
  }

  private void update(Response paramResponse1, Response paramResponse2)
  {
    Entry localEntry = new Entry(paramResponse2);
    DiskLruCache.Snapshot localSnapshot = ((CacheResponseBody)paramResponse1.body()).snapshot;
    DiskLruCache.Editor localEditor = null;
    try
    {
      localEditor = localSnapshot.edit();
      if (localEditor != null)
      {
        localEntry.writeTo(localEditor);
        localEditor.commit();
      }
      return;
    }
    catch (IOException localIOException)
    {
      abortQuietly(localEditor);
    }
  }

  private static String urlToKey(Request paramRequest)
  {
    return Util.md5Hex(paramRequest.urlString());
  }

  public void close()
    throws IOException
  {
    this.cache.close();
  }

  public void delete()
    throws IOException
  {
    this.cache.delete();
  }

  public void evictAll()
    throws IOException
  {
    this.cache.evictAll();
  }

  public void flush()
    throws IOException
  {
    this.cache.flush();
  }

  Response get(Request paramRequest)
  {
    String str = urlToKey(paramRequest);
    while (true)
    {
      DiskLruCache.Snapshot localSnapshot;
      Response localResponse;
      try
      {
        localSnapshot = this.cache.get(str);
        if (localSnapshot == null)
        {
          localResponse = null;
          return localResponse;
        }
      }
      catch (IOException localIOException1)
      {
        return null;
      }
      try
      {
        Entry localEntry = new Entry(localSnapshot.getSource(0));
        localResponse = localEntry.response(paramRequest, localSnapshot);
        if (!localEntry.matches(paramRequest, localResponse))
        {
          Util.closeQuietly(localResponse.body());
          return null;
        }
      }
      catch (IOException localIOException2)
      {
        Util.closeQuietly(localSnapshot);
      }
    }
    return null;
  }

  public File getDirectory()
  {
    return this.cache.getDirectory();
  }

  public int getHitCount()
  {
    try
    {
      int i = this.hitCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public long getMaxSize()
  {
    return this.cache.getMaxSize();
  }

  public int getNetworkCount()
  {
    try
    {
      int i = this.networkCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int getRequestCount()
  {
    try
    {
      int i = this.requestCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public long getSize()
    throws IOException
  {
    return this.cache.size();
  }

  public int getWriteAbortCount()
  {
    try
    {
      int i = this.writeAbortCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int getWriteSuccessCount()
  {
    try
    {
      int i = this.writeSuccessCount;
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean isClosed()
  {
    return this.cache.isClosed();
  }

  public Iterator<String> urls()
    throws IOException
  {
    return new Iterator()
    {
      boolean canRemove;
      final Iterator<DiskLruCache.Snapshot> delegate = Cache.this.cache.snapshots();
      String nextUrl;

      // ERROR //
      public boolean hasNext()
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 45	com/squareup/okhttp/Cache$2:nextUrl	Ljava/lang/String;
        //   4: ifnull +5 -> 9
        //   7: iconst_1
        //   8: ireturn
        //   9: aload_0
        //   10: iconst_0
        //   11: putfield 47	com/squareup/okhttp/Cache$2:canRemove	Z
        //   14: aload_0
        //   15: getfield 41	com/squareup/okhttp/Cache$2:delegate	Ljava/util/Iterator;
        //   18: invokeinterface 49 1 0
        //   23: ifeq +54 -> 77
        //   26: aload_0
        //   27: getfield 41	com/squareup/okhttp/Cache$2:delegate	Ljava/util/Iterator;
        //   30: invokeinterface 53 1 0
        //   35: checkcast 55	com/squareup/okhttp/internal/DiskLruCache$Snapshot
        //   38: astore_1
        //   39: aload_0
        //   40: aload_1
        //   41: iconst_0
        //   42: invokevirtual 59	com/squareup/okhttp/internal/DiskLruCache$Snapshot:getSource	(I)Lokio/Source;
        //   45: invokestatic 65	okio/Okio:buffer	(Lokio/Source;)Lokio/BufferedSource;
        //   48: invokeinterface 71 1 0
        //   53: putfield 45	com/squareup/okhttp/Cache$2:nextUrl	Ljava/lang/String;
        //   56: aload_1
        //   57: invokevirtual 74	com/squareup/okhttp/internal/DiskLruCache$Snapshot:close	()V
        //   60: iconst_1
        //   61: ireturn
        //   62: astore_3
        //   63: aload_1
        //   64: invokevirtual 74	com/squareup/okhttp/internal/DiskLruCache$Snapshot:close	()V
        //   67: goto -53 -> 14
        //   70: astore_2
        //   71: aload_1
        //   72: invokevirtual 74	com/squareup/okhttp/internal/DiskLruCache$Snapshot:close	()V
        //   75: aload_2
        //   76: athrow
        //   77: iconst_0
        //   78: ireturn
        //
        // Exception table:
        //   from	to	target	type
        //   39	56	62	java/io/IOException
        //   39	56	70	finally
      }

      public String next()
      {
        if (!hasNext())
          throw new NoSuchElementException();
        String str = this.nextUrl;
        this.nextUrl = null;
        this.canRemove = true;
        return str;
      }

      public void remove()
      {
        if (!this.canRemove)
          throw new IllegalStateException("remove() before next()");
        this.delegate.remove();
      }
    };
  }

  private final class CacheRequestImpl
    implements CacheRequest
  {
    private Sink body;
    private Sink cacheOut;
    private boolean done;
    private final DiskLruCache.Editor editor;

    public CacheRequestImpl(DiskLruCache.Editor arg2)
      throws IOException
    {
      final DiskLruCache.Editor localEditor;
      this.editor = localEditor;
      this.cacheOut = localEditor.newSink(1);
      this.body = new ForwardingSink(this.cacheOut)
      {
        public void close()
          throws IOException
        {
          synchronized (Cache.this)
          {
            if (Cache.CacheRequestImpl.this.done)
              return;
            Cache.CacheRequestImpl.access$702(Cache.CacheRequestImpl.this, true);
            Cache.access$808(Cache.this);
            super.close();
            localEditor.commit();
            return;
          }
        }
      };
    }

    public void abort()
    {
      synchronized (Cache.this)
      {
        if (this.done)
          return;
        this.done = true;
        Cache.access$908(Cache.this);
        Util.closeQuietly(this.cacheOut);
        try
        {
          this.editor.abort();
          return;
        }
        catch (IOException localIOException)
        {
        }
      }
    }

    public Sink body()
    {
      return this.body;
    }
  }

  private static class CacheResponseBody extends ResponseBody
  {
    private final BufferedSource bodySource;
    private final String contentLength;
    private final String contentType;
    private final DiskLruCache.Snapshot snapshot;

    public CacheResponseBody(final DiskLruCache.Snapshot paramSnapshot, String paramString1, String paramString2)
    {
      this.snapshot = paramSnapshot;
      this.contentType = paramString1;
      this.contentLength = paramString2;
      this.bodySource = Okio.buffer(new ForwardingSource(paramSnapshot.getSource(1))
      {
        public void close()
          throws IOException
        {
          paramSnapshot.close();
          super.close();
        }
      });
    }

    public long contentLength()
    {
      long l1 = -1L;
      try
      {
        if (this.contentLength != null)
        {
          long l2 = Long.parseLong(this.contentLength);
          l1 = l2;
        }
        return l1;
      }
      catch (NumberFormatException localNumberFormatException)
      {
      }
      return l1;
    }

    public MediaType contentType()
    {
      if (this.contentType != null)
        return MediaType.parse(this.contentType);
      return null;
    }

    public BufferedSource source()
    {
      return this.bodySource;
    }
  }

  private static final class Entry
  {
    private final int code;
    private final Handshake handshake;
    private final String message;
    private final Protocol protocol;
    private final String requestMethod;
    private final Headers responseHeaders;
    private final String url;
    private final Headers varyHeaders;

    public Entry(Response paramResponse)
    {
      this.url = paramResponse.request().urlString();
      this.varyHeaders = OkHeaders.varyHeaders(paramResponse);
      this.requestMethod = paramResponse.request().method();
      this.protocol = paramResponse.protocol();
      this.code = paramResponse.code();
      this.message = paramResponse.message();
      this.responseHeaders = paramResponse.headers();
      this.handshake = paramResponse.handshake();
    }

    public Entry(Source paramSource)
      throws IOException
    {
      BufferedSource localBufferedSource;
      try
      {
        localBufferedSource = Okio.buffer(paramSource);
        this.url = localBufferedSource.readUtf8LineStrict();
        this.requestMethod = localBufferedSource.readUtf8LineStrict();
        Headers.Builder localBuilder1 = new Headers.Builder();
        int i = Cache.readInt(localBufferedSource);
        for (int j = 0; j < i; j++)
          localBuilder1.addLenient(localBufferedSource.readUtf8LineStrict());
        this.varyHeaders = localBuilder1.build();
        StatusLine localStatusLine = StatusLine.parse(localBufferedSource.readUtf8LineStrict());
        this.protocol = localStatusLine.protocol;
        this.code = localStatusLine.code;
        this.message = localStatusLine.message;
        Headers.Builder localBuilder2 = new Headers.Builder();
        int k = Cache.readInt(localBufferedSource);
        for (int m = 0; m < k; m++)
          localBuilder2.addLenient(localBufferedSource.readUtf8LineStrict());
        this.responseHeaders = localBuilder2.build();
        if (isHttps())
        {
          String str = localBufferedSource.readUtf8LineStrict();
          if (str.length() > 0)
            throw new IOException("expected \"\" but was \"" + str + "\"");
        }
      }
      finally
      {
        paramSource.close();
      }
      for (this.handshake = Handshake.get(localBufferedSource.readUtf8LineStrict(), readCertificateList(localBufferedSource), readCertificateList(localBufferedSource)); ; this.handshake = null)
      {
        paramSource.close();
        return;
      }
    }

    private boolean isHttps()
    {
      return this.url.startsWith("https://");
    }

    private List<Certificate> readCertificateList(BufferedSource paramBufferedSource)
      throws IOException
    {
      int i = Cache.readInt(paramBufferedSource);
      Object localObject;
      if (i == -1)
        localObject = Collections.emptyList();
      while (true)
      {
        return localObject;
        try
        {
          CertificateFactory localCertificateFactory = CertificateFactory.getInstance("X.509");
          localObject = new ArrayList(i);
          for (int j = 0; j < i; j++)
          {
            String str = paramBufferedSource.readUtf8LineStrict();
            Buffer localBuffer = new Buffer();
            localBuffer.write(ByteString.decodeBase64(str));
            ((List)localObject).add(localCertificateFactory.generateCertificate(localBuffer.inputStream()));
          }
        }
        catch (CertificateException localCertificateException)
        {
          throw new IOException(localCertificateException.getMessage());
        }
      }
    }

    private void writeCertList(BufferedSink paramBufferedSink, List<Certificate> paramList)
      throws IOException
    {
      try
      {
        paramBufferedSink.writeDecimalLong(paramList.size());
        paramBufferedSink.writeByte(10);
        int i = 0;
        int j = paramList.size();
        while (i < j)
        {
          paramBufferedSink.writeUtf8(ByteString.of(((Certificate)paramList.get(i)).getEncoded()).base64());
          paramBufferedSink.writeByte(10);
          i++;
        }
      }
      catch (CertificateEncodingException localCertificateEncodingException)
      {
        throw new IOException(localCertificateEncodingException.getMessage());
      }
    }

    public boolean matches(Request paramRequest, Response paramResponse)
    {
      return (this.url.equals(paramRequest.urlString())) && (this.requestMethod.equals(paramRequest.method())) && (OkHeaders.varyMatches(paramResponse, this.varyHeaders, paramRequest));
    }

    public Response response(Request paramRequest, DiskLruCache.Snapshot paramSnapshot)
    {
      String str1 = this.responseHeaders.get("Content-Type");
      String str2 = this.responseHeaders.get("Content-Length");
      Request localRequest = new Request.Builder().url(this.url).method(this.requestMethod, null).headers(this.varyHeaders).build();
      return new Response.Builder().request(localRequest).protocol(this.protocol).code(this.code).message(this.message).headers(this.responseHeaders).body(new Cache.CacheResponseBody(paramSnapshot, str1, str2)).handshake(this.handshake).build();
    }

    public void writeTo(DiskLruCache.Editor paramEditor)
      throws IOException
    {
      BufferedSink localBufferedSink = Okio.buffer(paramEditor.newSink(0));
      localBufferedSink.writeUtf8(this.url);
      localBufferedSink.writeByte(10);
      localBufferedSink.writeUtf8(this.requestMethod);
      localBufferedSink.writeByte(10);
      localBufferedSink.writeDecimalLong(this.varyHeaders.size());
      localBufferedSink.writeByte(10);
      int i = 0;
      int j = this.varyHeaders.size();
      while (i < j)
      {
        localBufferedSink.writeUtf8(this.varyHeaders.name(i));
        localBufferedSink.writeUtf8(": ");
        localBufferedSink.writeUtf8(this.varyHeaders.value(i));
        localBufferedSink.writeByte(10);
        i++;
      }
      localBufferedSink.writeUtf8(new StatusLine(this.protocol, this.code, this.message).toString());
      localBufferedSink.writeByte(10);
      localBufferedSink.writeDecimalLong(this.responseHeaders.size());
      localBufferedSink.writeByte(10);
      int k = 0;
      int m = this.responseHeaders.size();
      while (k < m)
      {
        localBufferedSink.writeUtf8(this.responseHeaders.name(k));
        localBufferedSink.writeUtf8(": ");
        localBufferedSink.writeUtf8(this.responseHeaders.value(k));
        localBufferedSink.writeByte(10);
        k++;
      }
      if (isHttps())
      {
        localBufferedSink.writeByte(10);
        localBufferedSink.writeUtf8(this.handshake.cipherSuite());
        localBufferedSink.writeByte(10);
        writeCertList(localBufferedSink, this.handshake.peerCertificates());
        writeCertList(localBufferedSink, this.handshake.localCertificates());
      }
      localBufferedSink.close();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Cache
 * JD-Core Version:    0.6.2
 */