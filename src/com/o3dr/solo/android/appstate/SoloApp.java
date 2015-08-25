package com.o3dr.solo.android.appstate;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.o3dr.solo.android.util.AppAnalytics;
import com.o3dr.solo.android.util.ExceptionWriter;
import com.o3dr.solo.android.util.IdUtils;
import com.o3dr.solo.android.util.database.SoloDb;
import com.o3dr.solo.android.util.maps.mapbox.offline.MapDownloader;

public class SoloApp extends Application
{
  public static final boolean SITL_DEBUG;
  private AppAnalytics appAnalytics;
  private SoloDb appDB;
  private MapDownloader mapDownloader;
  private UpdateState updateState;
  private VersionsInfo versionsInfo;

  public static void initImageLoader(Context paramContext)
  {
    ImageLoaderConfiguration.Builder localBuilder = new ImageLoaderConfiguration.Builder(paramContext);
    localBuilder.threadPriority(3);
    localBuilder.denyCacheImageMultipleSizesInMemory();
    localBuilder.tasksProcessingOrder(QueueProcessingType.LIFO);
    ImageLoader.getInstance().init(localBuilder.build());
  }

  public AppAnalytics getAppAnalytics()
  {
    return this.appAnalytics;
  }

  public SoloDb getAppDB()
  {
    return this.appDB;
  }

  public MapDownloader getMapDownloader()
  {
    return this.mapDownloader;
  }

  public UpdateState getUpdateState()
  {
    return this.updateState;
  }

  public VersionsInfo getVersionsInfo()
  {
    return this.versionsInfo;
  }

  public void onCreate()
  {
    super.onCreate();
    Context localContext = getApplicationContext();
    this.updateState = new UpdateState(localContext);
    this.versionsInfo = new VersionsInfo(localContext);
    this.mapDownloader = new MapDownloader(localContext);
    this.appDB = new SoloDb(localContext);
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
    {
      public void uncaughtException(Thread paramAnonymousThread, Throwable paramAnonymousThrowable)
      {
        this.val$exceptionWriter.saveStackTraceToSD(paramAnonymousThrowable);
        this.val$defaultHandler.uncaughtException(paramAnonymousThread, paramAnonymousThrowable);
      }
    });
    initImageLoader(localContext);
    this.appAnalytics = new AppAnalytics(localContext, IdUtils.getID(localContext));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.appstate.SoloApp
 * JD-Core Version:    0.6.2
 */