package com.squareup.okhttp.internal.http;

import com.squareup.okhttp.Connection;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.Headers.Builder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.Response.Builder;
import com.squareup.okhttp.ResponseBody;
import com.squareup.okhttp.internal.Util;
import com.squareup.okhttp.internal.spdy.ErrorCode;
import com.squareup.okhttp.internal.spdy.Header;
import com.squareup.okhttp.internal.spdy.SpdyConnection;
import com.squareup.okhttp.internal.spdy.SpdyStream;
import java.io.IOException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import okio.ByteString;
import okio.Okio;
import okio.Sink;
import okio.Timeout;

public final class SpdyTransport
  implements Transport
{
  private static final List<ByteString> HTTP_2_PROHIBITED_HEADERS = Util.immutableList(arrayOfByteString2);
  private static final List<ByteString> SPDY_3_PROHIBITED_HEADERS;
  private final HttpEngine httpEngine;
  private final SpdyConnection spdyConnection;
  private SpdyStream stream;

  static
  {
    ByteString[] arrayOfByteString1 = new ByteString[5];
    arrayOfByteString1[0] = ByteString.encodeUtf8("connection");
    arrayOfByteString1[1] = ByteString.encodeUtf8("host");
    arrayOfByteString1[2] = ByteString.encodeUtf8("keep-alive");
    arrayOfByteString1[3] = ByteString.encodeUtf8("proxy-connection");
    arrayOfByteString1[4] = ByteString.encodeUtf8("transfer-encoding");
    SPDY_3_PROHIBITED_HEADERS = Util.immutableList(arrayOfByteString1);
    ByteString[] arrayOfByteString2 = new ByteString[8];
    arrayOfByteString2[0] = ByteString.encodeUtf8("connection");
    arrayOfByteString2[1] = ByteString.encodeUtf8("host");
    arrayOfByteString2[2] = ByteString.encodeUtf8("keep-alive");
    arrayOfByteString2[3] = ByteString.encodeUtf8("proxy-connection");
    arrayOfByteString2[4] = ByteString.encodeUtf8("te");
    arrayOfByteString2[5] = ByteString.encodeUtf8("transfer-encoding");
    arrayOfByteString2[6] = ByteString.encodeUtf8("encoding");
    arrayOfByteString2[7] = ByteString.encodeUtf8("upgrade");
  }

  public SpdyTransport(HttpEngine paramHttpEngine, SpdyConnection paramSpdyConnection)
  {
    this.httpEngine = paramHttpEngine;
    this.spdyConnection = paramSpdyConnection;
  }

  private static boolean isProhibitedHeader(Protocol paramProtocol, ByteString paramByteString)
  {
    if (paramProtocol == Protocol.SPDY_3)
      return SPDY_3_PROHIBITED_HEADERS.contains(paramByteString);
    if (paramProtocol == Protocol.HTTP_2)
      return HTTP_2_PROHIBITED_HEADERS.contains(paramByteString);
    throw new AssertionError(paramProtocol);
  }

  private static String joinOnNull(String paramString1, String paramString2)
  {
    return paramString1 + '\000' + paramString2;
  }

  public static Response.Builder readNameValueBlock(List<Header> paramList, Protocol paramProtocol)
    throws IOException
  {
    Object localObject1 = null;
    Object localObject2 = "HTTP/1.1";
    Headers.Builder localBuilder = new Headers.Builder();
    localBuilder.set(OkHeaders.SELECTED_PROTOCOL, paramProtocol.toString());
    int i = 0;
    int j = paramList.size();
    while (i < j)
    {
      ByteString localByteString = ((Header)paramList.get(i)).name;
      String str1 = ((Header)paramList.get(i)).value.utf8();
      int k = 0;
      if (k < str1.length())
      {
        int m = str1.indexOf(0, k);
        if (m == -1)
          m = str1.length();
        String str2 = str1.substring(k, m);
        if (localByteString.equals(Header.RESPONSE_STATUS))
          localObject1 = str2;
        while (true)
        {
          k = m + 1;
          break;
          if (localByteString.equals(Header.VERSION))
            localObject2 = str2;
          else if (!isProhibitedHeader(paramProtocol, localByteString))
            localBuilder.add(localByteString.utf8(), str2);
        }
      }
      i++;
    }
    if (localObject1 == null)
      throw new ProtocolException("Expected ':status' header not present");
    StatusLine localStatusLine = StatusLine.parse((String)localObject2 + " " + localObject1);
    return new Response.Builder().protocol(paramProtocol).code(localStatusLine.code).message(localStatusLine.message).headers(localBuilder.build());
  }

  public static List<Header> writeNameValueBlock(Request paramRequest, Protocol paramProtocol, String paramString)
  {
    Headers localHeaders = paramRequest.headers();
    ArrayList localArrayList = new ArrayList(10 + localHeaders.size());
    localArrayList.add(new Header(Header.TARGET_METHOD, paramRequest.method()));
    localArrayList.add(new Header(Header.TARGET_PATH, RequestLine.requestPath(paramRequest.url())));
    String str1 = HttpEngine.hostHeader(paramRequest.url());
    LinkedHashSet localLinkedHashSet;
    int i;
    label166: ByteString localByteString;
    String str2;
    if (Protocol.SPDY_3 == paramProtocol)
    {
      localArrayList.add(new Header(Header.VERSION, paramString));
      localArrayList.add(new Header(Header.TARGET_HOST, str1));
      localArrayList.add(new Header(Header.TARGET_SCHEME, paramRequest.url().getProtocol()));
      localLinkedHashSet = new LinkedHashSet();
      i = 0;
      int j = localHeaders.size();
      if (i >= j)
        break label440;
      localByteString = ByteString.encodeUtf8(localHeaders.name(i).toLowerCase(Locale.US));
      str2 = localHeaders.value(i);
      if (!isProhibitedHeader(paramProtocol, localByteString))
        break label251;
    }
    label438: 
    while (true)
    {
      i++;
      break label166;
      if (Protocol.HTTP_2 == paramProtocol)
      {
        localArrayList.add(new Header(Header.TARGET_AUTHORITY, str1));
        break;
      }
      throw new AssertionError();
      label251: if ((!localByteString.equals(Header.TARGET_METHOD)) && (!localByteString.equals(Header.TARGET_PATH)) && (!localByteString.equals(Header.TARGET_SCHEME)) && (!localByteString.equals(Header.TARGET_AUTHORITY)) && (!localByteString.equals(Header.TARGET_HOST)) && (!localByteString.equals(Header.VERSION)))
        if (localLinkedHashSet.add(localByteString))
          localArrayList.add(new Header(localByteString, str2));
        else
          for (int k = 0; ; k++)
          {
            if (k >= localArrayList.size())
              break label438;
            if (((Header)localArrayList.get(k)).name.equals(localByteString))
            {
              localArrayList.set(k, new Header(localByteString, joinOnNull(((Header)localArrayList.get(k)).value.utf8(), str2)));
              break;
            }
          }
    }
    label440: return localArrayList;
  }

  public boolean canReuseConnection()
  {
    return true;
  }

  public Sink createRequestBody(Request paramRequest, long paramLong)
    throws IOException
  {
    return this.stream.getSink();
  }

  public void disconnect(HttpEngine paramHttpEngine)
    throws IOException
  {
    if (this.stream != null)
      this.stream.close(ErrorCode.CANCEL);
  }

  public void finishRequest()
    throws IOException
  {
    this.stream.getSink().close();
  }

  public ResponseBody openResponseBody(Response paramResponse)
    throws IOException
  {
    return new RealResponseBody(paramResponse.headers(), Okio.buffer(this.stream.getSource()));
  }

  public Response.Builder readResponseHeaders()
    throws IOException
  {
    return readNameValueBlock(this.stream.getResponseHeaders(), this.spdyConnection.getProtocol());
  }

  public void releaseConnectionOnIdle()
  {
  }

  public void writeRequestBody(RetryableSink paramRetryableSink)
    throws IOException
  {
    paramRetryableSink.writeToSocket(this.stream.getSink());
  }

  public void writeRequestHeaders(Request paramRequest)
    throws IOException
  {
    if (this.stream != null)
      return;
    this.httpEngine.writingRequestHeaders();
    boolean bool = this.httpEngine.permitsRequestBody();
    String str = RequestLine.version(this.httpEngine.getConnection().getProtocol());
    this.stream = this.spdyConnection.newStream(writeNameValueBlock(paramRequest, this.spdyConnection.getProtocol(), str), bool, true);
    this.stream.readTimeout().timeout(this.httpEngine.client.getReadTimeout(), TimeUnit.MILLISECONDS);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.http.SpdyTransport
 * JD-Core Version:    0.6.2
 */