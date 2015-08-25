package com.segment.analytics.internal;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Process;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public final class Utils
{
  public static final int DEFAULT_FLUSH_INTERVAL = 30000;
  public static final int DEFAULT_FLUSH_QUEUE_SIZE = 20;

  @SuppressLint({"SimpleDateFormat"})
  private static final DateFormat ISO_8601_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
  static final String TAG = "Segment";
  public static final String THREAD_PREFIX = "SegmentAnalytics-";

  private Utils()
  {
    throw new AssertionError("No instances");
  }

  public static BufferedReader buffer(InputStream paramInputStream)
  {
    return new BufferedReader(new InputStreamReader(paramInputStream));
  }

  public static void closeQuietly(Closeable paramCloseable)
  {
    if (paramCloseable == null)
      return;
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException localIOException)
    {
    }
  }

  public static void createDirectory(File paramFile)
    throws IOException
  {
    if ((!paramFile.exists()) && (!paramFile.mkdirs()) && (!paramFile.isDirectory()))
      throw new IOException("Could not create directory at " + paramFile);
  }

  public static <T> Map<String, T> createMap()
  {
    return new NullableConcurrentHashMap();
  }

  public static void debug(String paramString, Object[] paramArrayOfObject)
  {
    Log.d("Segment", String.format(paramString, paramArrayOfObject));
  }

  public static void error(Throwable paramThrowable, String paramString, Object[] paramArrayOfObject)
  {
    Log.e("Segment", String.format(paramString, paramArrayOfObject), paramThrowable);
  }

  public static String getDeviceId(Context paramContext)
  {
    String str1 = Settings.Secure.getString(paramContext.getContentResolver(), "android_id");
    if ((!isNullOrEmpty(str1)) && (!"9774d56d682e549c".equals(str1)) && (!"unknown".equals(str1)) && (!"000000000000000".equals(str1)))
      return str1;
    if (!isNullOrEmpty(Build.SERIAL))
      return Build.SERIAL;
    if ((hasPermission(paramContext, "android.permission.READ_PHONE_STATE")) && (hasFeature(paramContext, "android.hardware.telephony")))
    {
      String str2 = ((TelephonyManager)getSystemService(paramContext, "phone")).getDeviceId();
      if (!isNullOrEmpty(str2))
        return str2;
    }
    return UUID.randomUUID().toString();
  }

  private static int getIdentifier(Context paramContext, String paramString1, String paramString2)
  {
    return paramContext.getResources().getIdentifier(paramString2, paramString1, paramContext.getPackageName());
  }

  public static String getResourceString(Context paramContext, String paramString)
  {
    int i = getIdentifier(paramContext, "string", paramString);
    if (i != 0)
      return paramContext.getResources().getString(i);
    return null;
  }

  public static SharedPreferences getSegmentSharedPreferences(Context paramContext)
  {
    return paramContext.getSharedPreferences("analytics-android", 0);
  }

  public static <T> T getSystemService(Context paramContext, String paramString)
  {
    return paramContext.getSystemService(paramString);
  }

  public static boolean hasFeature(Context paramContext, String paramString)
  {
    return paramContext.getPackageManager().hasSystemFeature(paramString);
  }

  public static boolean hasPermission(Context paramContext, String paramString)
  {
    return paramContext.checkCallingOrSelfPermission(paramString) == 0;
  }

  public static boolean isConnected(Context paramContext)
  {
    if (!hasPermission(paramContext, "android.permission.ACCESS_NETWORK_STATE"));
    NetworkInfo localNetworkInfo;
    do
    {
      return true;
      localNetworkInfo = ((ConnectivityManager)getSystemService(paramContext, "connectivity")).getActiveNetworkInfo();
    }
    while ((localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting()));
    return false;
  }

  public static boolean isNullOrEmpty(CharSequence paramCharSequence)
  {
    return (TextUtils.isEmpty(paramCharSequence)) || (TextUtils.getTrimmedLength(paramCharSequence) == 0);
  }

  public static boolean isNullOrEmpty(Collection paramCollection)
  {
    return (paramCollection == null) || (paramCollection.size() == 0);
  }

  public static boolean isNullOrEmpty(Map paramMap)
  {
    return (paramMap == null) || (paramMap.size() == 0);
  }

  public static boolean isOnClassPath(String paramString)
  {
    try
    {
      Class.forName(paramString);
      return true;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
    }
    return false;
  }

  public static <T> Set<T> newSet(T[] paramArrayOfT)
  {
    HashSet localHashSet = new HashSet(paramArrayOfT.length);
    Collections.addAll(localHashSet, paramArrayOfT);
    return localHashSet;
  }

  public static String readFully(BufferedReader paramBufferedReader)
    throws IOException
  {
    StringBuilder localStringBuilder = new StringBuilder();
    while (true)
    {
      String str = paramBufferedReader.readLine();
      if (str == null)
        break;
      localStringBuilder.append(str);
    }
    return localStringBuilder.toString();
  }

  public static String readFully(InputStream paramInputStream)
    throws IOException
  {
    return readFully(buffer(paramInputStream));
  }

  public static String toISO8601Date(Date paramDate)
  {
    return ISO_8601_DATE_FORMAT.format(paramDate);
  }

  public static Date toISO8601Date(String paramString)
    throws ParseException
  {
    return ISO_8601_DATE_FORMAT.parse(paramString);
  }

  public static class AnalyticsExecutorService extends ThreadPoolExecutor
  {
    private static final int DEFAULT_THREAD_COUNT = 1;
    private static final int MAX_THREAD_COUNT = 2;

    public AnalyticsExecutorService()
    {
      super(2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), new Utils.AnalyticsThreadFactory());
    }
  }

  private static class AnalyticsThread extends Thread
  {
    private static final AtomicInteger SEQUENCE_GENERATOR = new AtomicInteger(1);

    public AnalyticsThread(Runnable paramRunnable)
    {
      super("SegmentAnalytics-" + SEQUENCE_GENERATOR.getAndIncrement());
    }

    public void run()
    {
      Process.setThreadPriority(10);
      super.run();
    }
  }

  public static class AnalyticsThreadFactory
    implements ThreadFactory
  {
    public Thread newThread(Runnable paramRunnable)
    {
      return new Utils.AnalyticsThread(paramRunnable);
    }
  }

  public static class NullableConcurrentHashMap<K, V> extends ConcurrentHashMap<K, V>
  {
    public NullableConcurrentHashMap()
    {
    }

    public NullableConcurrentHashMap(Map<? extends K, ? extends V> paramMap)
    {
      super();
    }

    public V put(K paramK, V paramV)
    {
      if ((paramK == null) || (paramV == null))
        return null;
      return super.put(paramK, paramV);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.internal.Utils
 * JD-Core Version:    0.6.2
 */