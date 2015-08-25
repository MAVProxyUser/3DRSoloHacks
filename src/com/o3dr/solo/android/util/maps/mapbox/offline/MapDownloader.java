package com.o3dr.solo.android.util.maps.mapbox.offline;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.VisibleRegion;
import com.o3dr.solo.android.appstate.DatabaseState;
import com.o3dr.solo.android.util.NetworkUtils;
import com.o3dr.solo.android.util.Utils;
import com.o3dr.solo.android.util.maps.mapbox.MapboxUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapDownloader
{
  private static final String TAG = MapDownloader.class.getSimpleName();
  private final Context context;
  private ExecutorService downloadsScheduler;
  private final ArrayList<MapDownloaderListener> listeners = new ArrayList();
  private MBXOfflineMapDownloaderState state;
  private final AtomicInteger totalFilesExpectedToWrite = new AtomicInteger(0);
  private final AtomicInteger totalFilesWritten = new AtomicInteger(0);

  public MapDownloader(Context paramContext)
  {
    this.context = paramContext;
    setupDownloadScheduler();
    this.state = MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateAvailable;
  }

  private void finishUpDownloadProcess()
  {
    if (this.state == MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateRunning)
    {
      Log.i(TAG, "Just finished downloading all materials.  Persist the OfflineMapDatabase, change the state, and call it a day.");
      notifyDelegateOfCompletionWithOfflineMapDatabase();
      this.state = MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateAvailable;
      notifyDelegateOfStateChange();
    }
  }

  private void setupDownloadScheduler()
  {
    if (this.downloadsScheduler != null)
      this.downloadsScheduler.shutdownNow();
    int i = (int)(1.5F * Runtime.getRuntime().availableProcessors());
    Log.v(TAG, "Using " + i + " processors.");
    this.downloadsScheduler = Executors.newFixedThreadPool(i);
  }

  private void startDownloadProcess(final String paramString, final List<String> paramList)
  {
    this.downloadsScheduler.execute(new Runnable()
    {
      public void run()
      {
        if (!MapDownloader.this.sqliteCreateDatabaseUsingMetadata(paramString, paramList))
        {
          Log.e(MapDownloader.TAG, "Map Database wasn't created");
          return;
        }
        MapDownloader.this.startDownloading(paramString);
      }
    });
  }

  public boolean addMapDownloaderListener(MapDownloaderListener paramMapDownloaderListener)
  {
    return this.listeners.add(paramMapDownloaderListener);
  }

  public void beginDownloadingMapID(String paramString1, String paramString2, VisibleRegion paramVisibleRegion, int paramInt1, int paramInt2)
  {
    beginDownloadingMapID(paramString1, paramString2, paramVisibleRegion, paramInt1, paramInt2, true, true);
  }

  public void beginDownloadingMapID(final String paramString1, final String paramString2, VisibleRegion paramVisibleRegion, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.state != MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateAvailable)
    {
      Log.w(TAG, "state doesn't equal MBXOfflineMapDownloaderStateAvailable so return.  state = " + this.state);
      return;
    }
    this.state = MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateRunning;
    final ArrayList localArrayList = new ArrayList();
    if (paramBoolean1)
      localArrayList.add(String.format(Locale.US, "https://a.tiles.mapbox.com/v4/%s.json?secure&access_token=%s", new Object[] { paramString1, paramString2 }));
    if (paramBoolean2)
      localArrayList.add(String.format(Locale.US, "https://a.tiles.mapbox.com/v4/%s/%s?access_token=%s", new Object[] { paramString1, "features.json", paramString2 }));
    double d1 = Math.min(Math.min(paramVisibleRegion.farLeft.latitude, paramVisibleRegion.nearLeft.latitude), Math.min(paramVisibleRegion.farRight.latitude, paramVisibleRegion.nearRight.latitude));
    double d2 = Math.max(Math.max(paramVisibleRegion.farLeft.latitude, paramVisibleRegion.nearLeft.latitude), Math.max(paramVisibleRegion.farRight.latitude, paramVisibleRegion.nearRight.latitude));
    double d3 = Math.min(Math.min(paramVisibleRegion.farLeft.longitude, paramVisibleRegion.nearLeft.longitude), Math.min(paramVisibleRegion.farRight.longitude, paramVisibleRegion.nearRight.longitude));
    double d4 = Math.max(Math.max(paramVisibleRegion.farLeft.longitude, paramVisibleRegion.nearLeft.longitude), Math.max(paramVisibleRegion.farRight.longitude, paramVisibleRegion.nearRight.longitude));
    Log.d(TAG, "Generating urls for tiles from zoom " + paramInt1 + " to zoom " + paramInt2);
    for (int i = paramInt1; i <= paramInt2; i++)
    {
      int j = Double.valueOf(Math.pow(2.0D, i)).intValue();
      int k = Double.valueOf(Math.floor((180.0D + d3) / 360.0D * j)).intValue();
      int m = Double.valueOf(Math.floor((180.0D + d4) / 360.0D * j)).intValue();
      int n = Double.valueOf(Math.floor((1.0D - Math.log(Math.tan(3.141592653589793D * d2 / 180.0D) + 1.0D / Math.cos(3.141592653589793D * d2 / 180.0D)) / 3.141592653589793D) / 2.0D * j)).intValue();
      int i1 = Double.valueOf(Math.floor((1.0D - Math.log(Math.tan(3.141592653589793D * d1 / 180.0D) + 1.0D / Math.cos(3.141592653589793D * d1 / 180.0D)) / 3.141592653589793D) / 2.0D * j)).intValue();
      for (int i2 = k; i2 <= m; i2++)
        for (int i3 = n; i3 <= i1; i3++)
          localArrayList.add(MapboxUtils.getMapTileURL(paramString1, paramString2, i, i2, i3));
    }
    Log.d(TAG, localArrayList.size() + " urls generated.");
    if (paramBoolean2)
    {
      final String str = String.format(Locale.US, "https://a.tiles.mapbox.com/v4/%s/%s?access_token=%s", new Object[] { paramString1, "markers.geojson", paramString2 });
      if (!NetworkUtils.isNetworkAvailable(this.context))
      {
        notifyDelegateOfNetworkConnectivityError(new IllegalStateException("Network is unavailable"));
        Log.e(TAG, "Network is unavailable.");
        return;
      }
      this.downloadsScheduler.execute(new Runnable()
      {
        public void run()
        {
          try
          {
            localHttpURLConnection = NetworkUtils.getHttpURLConnection(new URL(str));
            localHttpURLConnection.setConnectTimeout(60000);
            localHttpURLConnection.connect();
            if (localHttpURLConnection.getResponseCode() != 200)
              throw new IOException();
          }
          catch (IOException localIOException)
          {
            HttpURLConnection localHttpURLConnection;
            localIOException.printStackTrace();
            return;
            String str = Utils.readAll(new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream(), Charset.forName("UTF-8"))));
            HashSet localHashSet = new HashSet();
            localHashSet.addAll(MapDownloader.this.parseMarkerIconURLStringsFromGeojsonData(paramString2, str));
            Log.i(MapDownloader.TAG, "Number of markerIconURLs = " + localHashSet.size());
            if (localHashSet.size() > 0)
              localArrayList.addAll(localHashSet);
            return;
          }
          finally
          {
            MapDownloader.this.startDownloadProcess(paramString1, localArrayList);
          }
        }
      });
      return;
    }
    Log.i(TAG, "No marker icons to worry about, so just start downloading.");
    startDownloadProcess(paramString1, localArrayList);
  }

  public void cancelDownload()
  {
    if (this.state == MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateRunning)
    {
      this.state = MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateCanceling;
      notifyDelegateOfStateChange();
    }
    setupDownloadScheduler();
    if (this.state == MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateCanceling)
    {
      this.state = MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateAvailable;
      notifyDelegateOfStateChange();
    }
  }

  public MBXOfflineMapDownloaderState getState()
  {
    return this.state;
  }

  public void notifyDelegateOfCompletionWithOfflineMapDatabase()
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((MapDownloaderListener)localIterator.next()).completionOfOfflineDatabaseMap();
  }

  public void notifyDelegateOfHTTPStatusError(int paramInt, String paramString)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
    {
      MapDownloaderListener localMapDownloaderListener = (MapDownloaderListener)localIterator.next();
      Locale localLocale = Locale.US;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(paramInt);
      arrayOfObject[1] = paramString;
      localMapDownloaderListener.httpStatusError(new Exception(String.format(localLocale, "HTTP Status Error %d, for url = %s", arrayOfObject)));
    }
  }

  public void notifyDelegateOfInitialCount(int paramInt)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((MapDownloaderListener)localIterator.next()).initialCountOfFiles(paramInt);
  }

  public void notifyDelegateOfNetworkConnectivityError(Throwable paramThrowable)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((MapDownloaderListener)localIterator.next()).networkConnectivityError(paramThrowable);
  }

  public void notifyDelegateOfProgress(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((MapDownloaderListener)localIterator.next()).progressUpdate(paramInt1, paramInt2);
  }

  public void notifyDelegateOfSqliteError(Throwable paramThrowable)
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((MapDownloaderListener)localIterator.next()).sqlLiteError(paramThrowable);
  }

  public void notifyDelegateOfStateChange()
  {
    Iterator localIterator = this.listeners.iterator();
    while (localIterator.hasNext())
      ((MapDownloaderListener)localIterator.next()).stateChanged(this.state);
  }

  public Set<String> parseMarkerIconURLStringsFromGeojsonData(String paramString1, String paramString2)
  {
    HashSet localHashSet = new HashSet();
    try
    {
      JSONArray localJSONArray = new JSONObject(paramString2).getJSONArray("features");
      if ((localJSONArray != null) && (localJSONArray.length() > 0))
        for (int i = 0; i < localJSONArray.length(); i++)
        {
          JSONObject localJSONObject = localJSONArray.optJSONObject(i);
          if ((localJSONObject != null) && ("Point".equals(localJSONObject.getJSONObject("geometry").getString("type"))))
          {
            String str1 = localJSONObject.getJSONObject("properties").getString("marker-size");
            String str2 = localJSONObject.getJSONObject("properties").getString("marker-color");
            String str3 = localJSONObject.getJSONObject("properties").getString("marker-symbol");
            if ((!TextUtils.isEmpty(str1)) && (!TextUtils.isEmpty(str2)) && (!TextUtils.isEmpty(str3)))
            {
              String str4 = MapboxUtils.markerIconURL(paramString1, str1, str3, str2);
              if (!TextUtils.isEmpty(str4))
                localHashSet.add(str4);
            }
          }
        }
    }
    catch (JSONException localJSONException)
    {
      Log.e(TAG, localJSONException.getMessage(), localJSONException);
    }
    return localHashSet;
  }

  public boolean removeMapDownloaderListener(MapDownloaderListener paramMapDownloaderListener)
  {
    return this.listeners.remove(paramMapDownloaderListener);
  }

  public boolean sqliteCreateDatabaseUsingMetadata(String paramString, List<String> paramList)
  {
    if (Utils.runningOnMainThread())
    {
      Log.w(TAG, "sqliteCreateDatabaseUsingMetadata() running on main thread.  Returning.");
      return false;
    }
    SQLiteDatabase localSQLiteDatabase = DatabaseState.getOfflineDatabaseHandlerForMapId(this.context, paramString).getWritableDatabase();
    localSQLiteDatabase.beginTransaction();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("url", str);
      localSQLiteDatabase.insertWithOnConflict("resources", null, localContentValues, 4);
    }
    localSQLiteDatabase.setTransactionSuccessful();
    localSQLiteDatabase.endTransaction();
    return true;
  }

  public ArrayList<String> sqliteReadArrayOfOfflineMapURLsToBeDownloadLimit(String paramString, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    if (Utils.runningOnMainThread())
      Log.w(TAG, "Attempting to run sqliteReadArrayOfOfflineMapURLsToBeDownloadLimit() on main thread.  Returning.");
    Cursor localCursor;
    do
    {
      return localArrayList;
      String str1 = String.format(Locale.US, "SELECT %s FROM %s WHERE %s IS NULL", new Object[] { "url", "resources", "status" });
      if (paramInt > 0)
      {
        StringBuilder localStringBuilder = new StringBuilder().append(str1);
        Locale localLocale = Locale.US;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = Integer.valueOf(paramInt);
        str1 = String.format(localLocale, " LIMIT %d", arrayOfObject);
      }
      String str2 = str1 + ";";
      localCursor = DatabaseState.getOfflineDatabaseHandlerForMapId(this.context, paramString).getReadableDatabase().rawQuery(str2, null);
    }
    while (localCursor == null);
    if (localCursor.moveToFirst())
      do
        localArrayList.add(localCursor.getString(0));
      while (localCursor.moveToNext());
    localCursor.close();
    return localArrayList;
  }

  public void sqliteSaveDownloadedData(String paramString1, byte[] paramArrayOfByte, String paramString2)
  {
    if (Utils.runningOnMainThread())
    {
      Log.w(TAG, "trying to run sqliteSaveDownloadedData() on main thread. Return.");
      return;
    }
    if (this.state != MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateRunning)
    {
      Log.w(TAG, "sqliteSaveDownloadedData() is not in a Running state so bailing.  State = " + this.state);
      return;
    }
    SQLiteDatabase localSQLiteDatabase = DatabaseState.getOfflineDatabaseHandlerForMapId(this.context, paramString1).getWritableDatabase();
    try
    {
      localSQLiteDatabase.beginTransaction();
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("value", paramArrayOfByte);
      localSQLiteDatabase.insert("data", null, localContentValues);
      localSQLiteDatabase.execSQL(String.format(Locale.US, "UPDATE %s SET %s=200, %s=last_insert_rowid() WHERE %s='%s';", new Object[] { "resources", "status", "id", "url", paramString2 }));
      localSQLiteDatabase.setTransactionSuccessful();
      localSQLiteDatabase.endTransaction();
      notifyDelegateOfProgress(this.totalFilesWritten.incrementAndGet(), this.totalFilesExpectedToWrite.get());
      Log.d(TAG, "totalFilesWritten = " + this.totalFilesWritten + "; totalFilesExpectedToWrite = " + this.totalFilesExpectedToWrite.get());
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        Log.e(TAG, "Error while saving downloader data to the database.", localIllegalStateException);
    }
  }

  public void startDownloading(final String paramString)
  {
    ArrayList localArrayList = sqliteReadArrayOfOfflineMapURLsToBeDownloadLimit(paramString, -1);
    this.totalFilesExpectedToWrite.set(localArrayList.size());
    this.totalFilesWritten.set(0);
    notifyDelegateOfInitialCount(this.totalFilesExpectedToWrite.get());
    String str1 = TAG;
    Locale localLocale = Locale.US;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Integer.valueOf(localArrayList.size());
    Log.d(str1, String.format(localLocale, "number of urls to download = %d", arrayOfObject));
    if (this.totalFilesExpectedToWrite.get() == 0)
    {
      finishUpDownloadProcess();
      return;
    }
    if (!NetworkUtils.isNetworkAvailable(this.context))
    {
      Log.e(TAG, "Network is not available.");
      notifyDelegateOfNetworkConnectivityError(new IllegalStateException("Network is not available"));
      return;
    }
    final CountDownLatch localCountDownLatch = new CountDownLatch(this.totalFilesExpectedToWrite.get());
    Iterator localIterator = localArrayList.iterator();
    while (localIterator.hasNext())
    {
      final String str2 = (String)localIterator.next();
      this.downloadsScheduler.execute(new Runnable()
      {
        // ERROR //
        public void run()
        {
          // Byte code:
          //   0: aconst_null
          //   1: astore_1
          //   2: new 36	java/net/URL
          //   5: dup
          //   6: aload_0
          //   7: getfield 24	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:val$url	Ljava/lang/String;
          //   10: invokespecial 38	java/net/URL:<init>	(Ljava/lang/String;)V
          //   13: invokestatic 44	com/o3dr/solo/android/util/NetworkUtils:getHttpURLConnection	(Ljava/net/URL;)Ljava/net/HttpURLConnection;
          //   16: astore_1
          //   17: invokestatic 48	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader:access$000	()Ljava/lang/String;
          //   20: new 50	java/lang/StringBuilder
          //   23: dup
          //   24: invokespecial 51	java/lang/StringBuilder:<init>	()V
          //   27: ldc 53
          //   29: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   32: aload_1
          //   33: invokevirtual 63	java/net/HttpURLConnection:getURL	()Ljava/net/URL;
          //   36: invokevirtual 66	java/net/URL:toString	()Ljava/lang/String;
          //   39: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
          //   42: invokevirtual 67	java/lang/StringBuilder:toString	()Ljava/lang/String;
          //   45: invokestatic 73	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
          //   48: pop
          //   49: aload_1
          //   50: ldc 74
          //   52: invokevirtual 78	java/net/HttpURLConnection:setConnectTimeout	(I)V
          //   55: aload_1
          //   56: invokevirtual 81	java/net/HttpURLConnection:connect	()V
          //   59: aload_1
          //   60: invokevirtual 85	java/net/HttpURLConnection:getResponseCode	()I
          //   63: istore 6
          //   65: iload 6
          //   67: sipush 200
          //   70: if_icmpeq +104 -> 174
          //   73: getstatic 91	java/util/Locale:US	Ljava/util/Locale;
          //   76: astore 7
          //   78: iconst_2
          //   79: anewarray 4	java/lang/Object
          //   82: astore 8
          //   84: aload 8
          //   86: iconst_0
          //   87: iload 6
          //   89: invokestatic 97	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
          //   92: aastore
          //   93: aload 8
          //   95: iconst_1
          //   96: aload_1
          //   97: invokevirtual 63	java/net/HttpURLConnection:getURL	()Ljava/net/URL;
          //   100: invokevirtual 66	java/net/URL:toString	()Ljava/lang/String;
          //   103: aastore
          //   104: aload 7
          //   106: ldc 99
          //   108: aload 8
          //   110: invokestatic 105	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
          //   113: astore 9
          //   115: invokestatic 48	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader:access$000	()Ljava/lang/String;
          //   118: aload 9
          //   120: invokestatic 108	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
          //   123: pop
          //   124: aload_0
          //   125: getfield 22	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:this$0	Lcom/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader;
          //   128: iload 6
          //   130: aload_0
          //   131: getfield 24	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:val$url	Ljava/lang/String;
          //   134: invokevirtual 112	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader:notifyDelegateOfHTTPStatusError	(ILjava/lang/String;)V
          //   137: new 34	java/io/IOException
          //   140: dup
          //   141: aload 9
          //   143: invokespecial 113	java/io/IOException:<init>	(Ljava/lang/String;)V
          //   146: athrow
          //   147: astore_3
          //   148: invokestatic 48	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader:access$000	()Ljava/lang/String;
          //   151: ldc 115
          //   153: aload_3
          //   154: invokestatic 119	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
          //   157: pop
          //   158: aload_0
          //   159: getfield 28	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:val$downloadsTracker	Ljava/util/concurrent/CountDownLatch;
          //   162: invokevirtual 124	java/util/concurrent/CountDownLatch:countDown	()V
          //   165: aload_1
          //   166: ifnull +7 -> 173
          //   169: aload_1
          //   170: invokevirtual 127	java/net/HttpURLConnection:disconnect	()V
          //   173: return
          //   174: new 129	java/io/ByteArrayOutputStream
          //   177: dup
          //   178: invokespecial 130	java/io/ByteArrayOutputStream:<init>	()V
          //   181: astore 11
          //   183: aconst_null
          //   184: astore 12
          //   186: aload_1
          //   187: invokevirtual 134	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
          //   190: astore 12
          //   192: sipush 4096
          //   195: newarray byte
          //   197: astore 19
          //   199: aload 12
          //   201: aload 19
          //   203: invokevirtual 140	java/io/InputStream:read	([B)I
          //   206: istore 20
          //   208: iload 20
          //   210: ifle +124 -> 334
          //   213: aload 11
          //   215: aload 19
          //   217: iconst_0
          //   218: iload 20
          //   220: invokevirtual 144	java/io/ByteArrayOutputStream:write	([BII)V
          //   223: goto -24 -> 199
          //   226: astore 14
          //   228: invokestatic 48	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader:access$000	()Ljava/lang/String;
          //   231: astore 15
          //   233: getstatic 91	java/util/Locale:US	Ljava/util/Locale;
          //   236: astore 16
          //   238: iconst_2
          //   239: anewarray 4	java/lang/Object
          //   242: astore 17
          //   244: aload 17
          //   246: iconst_0
          //   247: aload_1
          //   248: invokevirtual 63	java/net/HttpURLConnection:getURL	()Ljava/net/URL;
          //   251: invokevirtual 66	java/net/URL:toString	()Ljava/lang/String;
          //   254: aastore
          //   255: aload 17
          //   257: iconst_1
          //   258: aload 14
          //   260: invokevirtual 147	java/io/IOException:getMessage	()Ljava/lang/String;
          //   263: aastore
          //   264: aload 15
          //   266: aload 16
          //   268: ldc 149
          //   270: aload 17
          //   272: invokestatic 105	java/lang/String:format	(Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
          //   275: invokestatic 151	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
          //   278: pop
          //   279: aload 14
          //   281: invokevirtual 154	java/io/IOException:printStackTrace	()V
          //   284: aload 12
          //   286: ifnull +8 -> 294
          //   289: aload 12
          //   291: invokevirtual 157	java/io/InputStream:close	()V
          //   294: aload_1
          //   295: invokevirtual 127	java/net/HttpURLConnection:disconnect	()V
          //   298: aload_0
          //   299: getfield 22	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:this$0	Lcom/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader;
          //   302: aload_0
          //   303: getfield 26	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:val$mapId	Ljava/lang/String;
          //   306: aload 11
          //   308: invokevirtual 161	java/io/ByteArrayOutputStream:toByteArray	()[B
          //   311: aload_0
          //   312: getfield 24	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:val$url	Ljava/lang/String;
          //   315: invokevirtual 165	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader:sqliteSaveDownloadedData	(Ljava/lang/String;[BLjava/lang/String;)V
          //   318: aload_0
          //   319: getfield 28	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:val$downloadsTracker	Ljava/util/concurrent/CountDownLatch;
          //   322: invokevirtual 124	java/util/concurrent/CountDownLatch:countDown	()V
          //   325: aload_1
          //   326: ifnull -153 -> 173
          //   329: aload_1
          //   330: invokevirtual 127	java/net/HttpURLConnection:disconnect	()V
          //   333: return
          //   334: aload 12
          //   336: ifnull +8 -> 344
          //   339: aload 12
          //   341: invokevirtual 157	java/io/InputStream:close	()V
          //   344: aload_1
          //   345: invokevirtual 127	java/net/HttpURLConnection:disconnect	()V
          //   348: goto -50 -> 298
          //   351: astore_2
          //   352: aload_0
          //   353: getfield 28	com/o3dr/solo/android/util/maps/mapbox/offline/MapDownloader$1:val$downloadsTracker	Ljava/util/concurrent/CountDownLatch;
          //   356: invokevirtual 124	java/util/concurrent/CountDownLatch:countDown	()V
          //   359: aload_1
          //   360: ifnull +7 -> 367
          //   363: aload_1
          //   364: invokevirtual 127	java/net/HttpURLConnection:disconnect	()V
          //   367: aload_2
          //   368: athrow
          //   369: astore 13
          //   371: aload 12
          //   373: ifnull +8 -> 381
          //   376: aload 12
          //   378: invokevirtual 157	java/io/InputStream:close	()V
          //   381: aload_1
          //   382: invokevirtual 127	java/net/HttpURLConnection:disconnect	()V
          //   385: aload 13
          //   387: athrow
          //
          // Exception table:
          //   from	to	target	type
          //   2	65	147	java/io/IOException
          //   73	147	147	java/io/IOException
          //   174	183	147	java/io/IOException
          //   289	294	147	java/io/IOException
          //   294	298	147	java/io/IOException
          //   298	318	147	java/io/IOException
          //   339	344	147	java/io/IOException
          //   344	348	147	java/io/IOException
          //   376	381	147	java/io/IOException
          //   381	388	147	java/io/IOException
          //   186	199	226	java/io/IOException
          //   199	208	226	java/io/IOException
          //   213	223	226	java/io/IOException
          //   2	65	351	finally
          //   73	147	351	finally
          //   148	158	351	finally
          //   174	183	351	finally
          //   289	294	351	finally
          //   294	298	351	finally
          //   298	318	351	finally
          //   339	344	351	finally
          //   344	348	351	finally
          //   376	381	351	finally
          //   381	388	351	finally
          //   186	199	369	finally
          //   199	208	369	finally
          //   213	223	369	finally
          //   228	284	369	finally
        }
      });
    }
    this.downloadsScheduler.execute(new Runnable()
    {
      public void run()
      {
        try
        {
          localCountDownLatch.await();
          return;
        }
        catch (InterruptedException localInterruptedException)
        {
          Log.e(MapDownloader.TAG, "Error while waiting for downloads to complete.", localInterruptedException);
          return;
        }
        finally
        {
          MapDownloader.this.finishUpDownloadProcess();
        }
      }
    });
  }

  public static enum MBXOfflineMapDownloaderState
  {
    static
    {
      MBXOfflineMapDownloaderStateCanceling = new MBXOfflineMapDownloaderState("MBXOfflineMapDownloaderStateCanceling", 1);
      MBXOfflineMapDownloaderStateAvailable = new MBXOfflineMapDownloaderState("MBXOfflineMapDownloaderStateAvailable", 2);
      MBXOfflineMapDownloaderState[] arrayOfMBXOfflineMapDownloaderState = new MBXOfflineMapDownloaderState[3];
      arrayOfMBXOfflineMapDownloaderState[0] = MBXOfflineMapDownloaderStateRunning;
      arrayOfMBXOfflineMapDownloaderState[1] = MBXOfflineMapDownloaderStateCanceling;
      arrayOfMBXOfflineMapDownloaderState[2] = MBXOfflineMapDownloaderStateAvailable;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.maps.mapbox.offline.MapDownloader
 * JD-Core Version:    0.6.2
 */