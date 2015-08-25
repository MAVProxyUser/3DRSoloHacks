package com.segment.analytics;

import android.content.Context;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

class Client
{
  final ConnectionFactory connectionFactory;
  final Context context;
  final String writeKey;

  Client(Context paramContext, String paramString, ConnectionFactory paramConnectionFactory)
  {
    this.context = paramContext;
    this.writeKey = paramString;
    this.connectionFactory = paramConnectionFactory;
  }

  private static Connection createGetConnection(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    return new Connection(paramHttpURLConnection, paramHttpURLConnection.getInputStream(), null)
    {
      public void close()
        throws IOException
      {
        super.close();
        this.is.close();
      }
    };
  }

  private static Connection createPostConnection(HttpURLConnection paramHttpURLConnection)
    throws IOException
  {
    return new Connection(paramHttpURLConnection, null, paramHttpURLConnection.getOutputStream())
    {
      // ERROR //
      public void close()
        throws IOException
      {
        // Byte code:
        //   0: aload_0
        //   1: getfield 21	com/segment/analytics/Client$1:connection	Ljava/net/HttpURLConnection;
        //   4: invokevirtual 27	java/net/HttpURLConnection:getResponseCode	()I
        //   7: istore_2
        //   8: iload_2
        //   9: sipush 300
        //   12: if_icmplt +83 -> 95
        //   15: aload_0
        //   16: getfield 21	com/segment/analytics/Client$1:connection	Ljava/net/HttpURLConnection;
        //   19: invokevirtual 31	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
        //   22: invokestatic 37	com/segment/analytics/internal/Utils:readFully	(Ljava/io/InputStream;)Ljava/lang/String;
        //   25: astore 6
        //   27: aload 6
        //   29: astore 5
        //   31: new 39	com/segment/analytics/Client$UploadException
        //   34: dup
        //   35: iload_2
        //   36: aload_0
        //   37: getfield 21	com/segment/analytics/Client$1:connection	Ljava/net/HttpURLConnection;
        //   40: invokevirtual 43	java/net/HttpURLConnection:getResponseMessage	()Ljava/lang/String;
        //   43: aload 5
        //   45: invokespecial 46	com/segment/analytics/Client$UploadException:<init>	(ILjava/lang/String;Ljava/lang/String;)V
        //   48: athrow
        //   49: astore_1
        //   50: aload_0
        //   51: invokespecial 48	com/segment/analytics/Client$Connection:close	()V
        //   54: aload_0
        //   55: getfield 52	com/segment/analytics/Client$1:os	Ljava/io/OutputStream;
        //   58: invokevirtual 55	java/io/OutputStream:close	()V
        //   61: aload_1
        //   62: athrow
        //   63: astore_3
        //   64: new 57	java/lang/StringBuilder
        //   67: dup
        //   68: invokespecial 59	java/lang/StringBuilder:<init>	()V
        //   71: ldc 61
        //   73: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   76: aload_3
        //   77: invokevirtual 68	java/io/IOException:toString	()Ljava/lang/String;
        //   80: invokevirtual 65	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   83: invokevirtual 69	java/lang/StringBuilder:toString	()Ljava/lang/String;
        //   86: astore 4
        //   88: aload 4
        //   90: astore 5
        //   92: goto -61 -> 31
        //   95: aload_0
        //   96: invokespecial 48	com/segment/analytics/Client$Connection:close	()V
        //   99: aload_0
        //   100: getfield 52	com/segment/analytics/Client$1:os	Ljava/io/OutputStream;
        //   103: invokevirtual 55	java/io/OutputStream:close	()V
        //   106: return
        //
        // Exception table:
        //   from	to	target	type
        //   0	8	49	finally
        //   15	27	49	finally
        //   31	49	49	finally
        //   64	88	49	finally
        //   15	27	63	java/io/IOException
      }
    };
  }

  Connection fetchSettings()
    throws IOException
  {
    HttpURLConnection localHttpURLConnection = this.connectionFactory.projectSettings(this.writeKey);
    int i = localHttpURLConnection.getResponseCode();
    if (i != 200)
    {
      localHttpURLConnection.disconnect();
      throw new IOException("HTTP " + i + ": " + localHttpURLConnection.getResponseMessage());
    }
    return createGetConnection(localHttpURLConnection);
  }

  Connection upload()
    throws IOException
  {
    return createPostConnection(this.connectionFactory.upload(this.writeKey));
  }

  static abstract class Connection
    implements Closeable
  {
    protected final HttpURLConnection connection;
    final InputStream is;
    final OutputStream os;

    Connection(HttpURLConnection paramHttpURLConnection, InputStream paramInputStream, OutputStream paramOutputStream)
    {
      if (paramHttpURLConnection == null)
        throw new IllegalArgumentException("connection == null");
      this.connection = paramHttpURLConnection;
      this.is = paramInputStream;
      this.os = paramOutputStream;
    }

    public void close()
      throws IOException
    {
      this.connection.disconnect();
    }
  }

  static class UploadException extends IOException
  {
    final String responseBody;
    final int responseCode;
    final String responseMessage;

    UploadException(int paramInt, String paramString1, String paramString2)
    {
      super();
      this.responseCode = paramInt;
      this.responseMessage = paramString1;
      this.responseBody = paramString2;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.Client
 * JD-Core Version:    0.6.2
 */