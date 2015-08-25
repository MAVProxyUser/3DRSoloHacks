package com.squareup.okhttp.internal;

import java.io.Closeable;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import okio.Buffer;
import okio.ByteString;
import okio.Source;
import okio.Timeout;

public final class Util
{
  public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
  public static final String[] EMPTY_STRING_ARRAY = new String[0];
  public static final Charset UTF_8 = Charset.forName("UTF-8");

  public static void checkOffsetAndCount(long paramLong1, long paramLong2, long paramLong3)
  {
    if (((paramLong2 | paramLong3) < 0L) || (paramLong2 > paramLong1) || (paramLong1 - paramLong2 < paramLong3))
      throw new ArrayIndexOutOfBoundsException();
  }

  public static void closeAll(Closeable paramCloseable1, Closeable paramCloseable2)
    throws IOException
  {
    Object localObject = null;
    try
    {
      paramCloseable1.close();
    }
    catch (Throwable localThrowable1)
    {
      try
      {
        while (true)
        {
          paramCloseable2.close();
          if (localObject != null)
            break;
          return;
          localThrowable1 = localThrowable1;
          localObject = localThrowable1;
        }
      }
      catch (Throwable localThrowable2)
      {
        while (true)
          if (localObject == null)
            localObject = localThrowable2;
        if ((localObject instanceof IOException))
          throw ((IOException)localObject);
        if ((localObject instanceof RuntimeException))
          throw ((RuntimeException)localObject);
        if ((localObject instanceof Error))
          throw ((Error)localObject);
      }
    }
    throw new AssertionError(localObject);
  }

  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable != null);
    try
    {
      paramCloseable.close();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException)
    {
    }
  }

  public static void closeQuietly(ServerSocket paramServerSocket)
  {
    if (paramServerSocket != null);
    try
    {
      paramServerSocket.close();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException)
    {
    }
  }

  public static void closeQuietly(Socket paramSocket)
  {
    if (paramSocket != null);
    try
    {
      paramSocket.close();
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (Exception localException)
    {
    }
  }

  public static boolean discard(Source paramSource, int paramInt, TimeUnit paramTimeUnit)
  {
    try
    {
      boolean bool = skipAll(paramSource, paramInt, paramTimeUnit);
      return bool;
    }
    catch (IOException localIOException)
    {
    }
    return false;
  }

  public static boolean equal(Object paramObject1, Object paramObject2)
  {
    return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
  }

  public static int getDefaultPort(String paramString)
  {
    if ("http".equals(paramString))
      return 80;
    if ("https".equals(paramString))
      return 443;
    return -1;
  }

  private static int getEffectivePort(String paramString, int paramInt)
  {
    if (paramInt != -1)
      return paramInt;
    return getDefaultPort(paramString);
  }

  public static int getEffectivePort(URI paramURI)
  {
    return getEffectivePort(paramURI.getScheme(), paramURI.getPort());
  }

  public static int getEffectivePort(URL paramURL)
  {
    return getEffectivePort(paramURL.getProtocol(), paramURL.getPort());
  }

  public static <T> List<T> immutableList(List<T> paramList)
  {
    return Collections.unmodifiableList(new ArrayList(paramList));
  }

  public static <T> List<T> immutableList(T[] paramArrayOfT)
  {
    return Collections.unmodifiableList(Arrays.asList((Object[])paramArrayOfT.clone()));
  }

  public static <K, V> Map<K, V> immutableMap(Map<K, V> paramMap)
  {
    return Collections.unmodifiableMap(new LinkedHashMap(paramMap));
  }

  private static <T> List<T> intersect(T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    ArrayList localArrayList = new ArrayList();
    int i = paramArrayOfT1.length;
    int j = 0;
    if (j < i)
    {
      T ? = paramArrayOfT1[j];
      int k = paramArrayOfT2.length;
      for (int m = 0; ; m++)
        if (m < k)
        {
          T ? = paramArrayOfT2[m];
          if (?.equals(?))
            localArrayList.add(?);
        }
        else
        {
          j++;
          break;
        }
    }
    return localArrayList;
  }

  public static <T> T[] intersect(Class<T> paramClass, T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    List localList = intersect(paramArrayOfT1, paramArrayOfT2);
    return localList.toArray((Object[])Array.newInstance(paramClass, localList.size()));
  }

  public static String md5Hex(String paramString)
  {
    try
    {
      String str = ByteString.of(MessageDigest.getInstance("MD5").digest(paramString.getBytes("UTF-8"))).hex();
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError(localNoSuchAlgorithmException);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      label24: break label24;
    }
  }

  public static ByteString sha1(ByteString paramByteString)
  {
    try
    {
      ByteString localByteString = ByteString.of(MessageDigest.getInstance("SHA-1").digest(paramByteString.toByteArray()));
      return localByteString;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError(localNoSuchAlgorithmException);
    }
  }

  public static String shaBase64(String paramString)
  {
    try
    {
      String str = ByteString.of(MessageDigest.getInstance("SHA-1").digest(paramString.getBytes("UTF-8"))).base64();
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new AssertionError(localNoSuchAlgorithmException);
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      label24: break label24;
    }
  }

  public static boolean skipAll(Source paramSource, int paramInt, TimeUnit paramTimeUnit)
    throws IOException
  {
    long l1 = System.nanoTime();
    long l2;
    if (paramSource.timeout().hasDeadline())
      l2 = paramSource.timeout().deadlineNanoTime() - l1;
    while (true)
    {
      paramSource.timeout().deadlineNanoTime(l1 + Math.min(l2, paramTimeUnit.toNanos(paramInt)));
      try
      {
        Buffer localBuffer = new Buffer();
        while (paramSource.read(localBuffer, 2048L) != -1L)
          localBuffer.clear();
      }
      catch (InterruptedIOException localInterruptedIOException)
      {
        if (l2 == 9223372036854775807L)
          paramSource.timeout().clearDeadline();
        while (true)
        {
          return false;
          l2 = 9223372036854775807L;
          break;
          if (l2 == 9223372036854775807L)
            paramSource.timeout().clearDeadline();
          while (true)
          {
            return true;
            paramSource.timeout().deadlineNanoTime(l1 + l2);
          }
          paramSource.timeout().deadlineNanoTime(l1 + l2);
        }
      }
      finally
      {
        if (l2 != 9223372036854775807L)
          break label197;
      }
    }
    paramSource.timeout().clearDeadline();
    while (true)
    {
      throw localObject;
      label197: paramSource.timeout().deadlineNanoTime(l1 + l2);
    }
  }

  public static ThreadFactory threadFactory(String paramString, final boolean paramBoolean)
  {
    return new ThreadFactory()
    {
      public Thread newThread(Runnable paramAnonymousRunnable)
      {
        Thread localThread = new Thread(paramAnonymousRunnable, this.val$name);
        localThread.setDaemon(paramBoolean);
        return localThread;
      }
    };
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.Util
 * JD-Core Version:    0.6.2
 */