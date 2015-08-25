package com.o3dr.solo.android.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import com.o3dr.solo.android.util.maps.mapbox.MapboxUtils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

public class NetworkUtils
{
  public static String getCurrentWifiLink(Context paramContext)
  {
    WifiInfo localWifiInfo = ((WifiManager)paramContext.getSystemService("wifi")).getConnectionInfo();
    if (localWifiInfo == null)
      return null;
    return localWifiInfo.getSSID().replace("\"", "");
  }

  public static DefaultHttpClient getHttpClient()
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    localSchemeRegistry.register(new Scheme("https", org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory(), 443));
    return new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
  }

  public static HttpURLConnection getHttpURLConnection(URL paramURL)
  {
    return getHttpURLConnection(paramURL, null, null);
  }

  public static HttpURLConnection getHttpURLConnection(URL paramURL, Cache paramCache)
  {
    return getHttpURLConnection(paramURL, paramCache, null);
  }

  public static HttpURLConnection getHttpURLConnection(URL paramURL, Cache paramCache, javax.net.ssl.SSLSocketFactory paramSSLSocketFactory)
  {
    OkHttpClient localOkHttpClient = new OkHttpClient();
    if (paramCache != null)
      localOkHttpClient.setCache(paramCache);
    if (paramSSLSocketFactory != null)
      localOkHttpClient.setSslSocketFactory(paramSSLSocketFactory);
    HttpURLConnection localHttpURLConnection = new OkUrlFactory(localOkHttpClient).open(paramURL);
    localHttpURLConnection.setRequestProperty("User-Agent", MapboxUtils.getUserAgent());
    return localHttpURLConnection;
  }

  public static boolean isNetworkAvailable(Context paramContext)
  {
    if (isOnSololinkNetwork(paramContext));
    NetworkInfo localNetworkInfo;
    do
    {
      return false;
      localNetworkInfo = ((ConnectivityManager)paramContext.getSystemService("connectivity")).getActiveNetworkInfo();
    }
    while ((localNetworkInfo == null) || (!localNetworkInfo.isConnected()));
    return true;
  }

  public static boolean isOnSololinkNetwork(Context paramContext)
  {
    String str = getCurrentWifiLink(paramContext);
    return (str != null) && (str.startsWith("SoloLink_"));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.NetworkUtils
 * JD-Core Version:    0.6.2
 */