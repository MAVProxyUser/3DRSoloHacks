package com.nostra13.universalimageloader.core;

import android.graphics.Bitmap;
import android.os.Handler;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.FailReason.FailType;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.assist.ViewScaleType;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.decode.ImageDecodingInfo;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.IoUtils.CopyListener;
import com.nostra13.universalimageloader.utils.L;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

final class LoadAndDisplayImageTask
  implements Runnable, IoUtils.CopyListener
{
  private static final String ERROR_POST_PROCESSOR_NULL = "Post-processor returned null [%s]";
  private static final String ERROR_PRE_PROCESSOR_NULL = "Pre-processor returned null [%s]";
  private static final String ERROR_PROCESSOR_FOR_DISK_CACHE_NULL = "Bitmap processor for disk cache returned null [%s]";
  private static final String LOG_CACHE_IMAGE_IN_MEMORY = "Cache image in memory [%s]";
  private static final String LOG_CACHE_IMAGE_ON_DISK = "Cache image on disk [%s]";
  private static final String LOG_DELAY_BEFORE_LOADING = "Delay %d ms before loading...  [%s]";
  private static final String LOG_GET_IMAGE_FROM_MEMORY_CACHE_AFTER_WAITING = "...Get cached bitmap from memory after waiting. [%s]";
  private static final String LOG_LOAD_IMAGE_FROM_DISK_CACHE = "Load image from disk cache [%s]";
  private static final String LOG_LOAD_IMAGE_FROM_NETWORK = "Load image from network [%s]";
  private static final String LOG_POSTPROCESS_IMAGE = "PostProcess image before displaying [%s]";
  private static final String LOG_PREPROCESS_IMAGE = "PreProcess image before caching in memory [%s]";
  private static final String LOG_PROCESS_IMAGE_BEFORE_CACHE_ON_DISK = "Process image before cache on disk [%s]";
  private static final String LOG_RESIZE_CACHED_IMAGE_FILE = "Resize image in disk cache [%s]";
  private static final String LOG_RESUME_AFTER_PAUSE = ".. Resume loading [%s]";
  private static final String LOG_START_DISPLAY_IMAGE_TASK = "Start display image task [%s]";
  private static final String LOG_TASK_CANCELLED_IMAGEAWARE_COLLECTED = "ImageAware was collected by GC. Task is cancelled. [%s]";
  private static final String LOG_TASK_CANCELLED_IMAGEAWARE_REUSED = "ImageAware is reused for another image. Task is cancelled. [%s]";
  private static final String LOG_TASK_INTERRUPTED = "Task was interrupted [%s]";
  private static final String LOG_WAITING_FOR_IMAGE_LOADED = "Image already is loading. Waiting... [%s]";
  private static final String LOG_WAITING_FOR_RESUME = "ImageLoader is paused. Waiting...  [%s]";
  private final ImageLoaderConfiguration configuration;
  private final ImageDecoder decoder;
  private final ImageDownloader downloader;
  private final ImageLoaderEngine engine;
  private final Handler handler;
  final ImageAware imageAware;
  private final ImageLoadingInfo imageLoadingInfo;
  final ImageLoadingListener listener;
  private LoadedFrom loadedFrom = LoadedFrom.NETWORK;
  private final String memoryCacheKey;
  private final ImageDownloader networkDeniedDownloader;
  final DisplayImageOptions options;
  final ImageLoadingProgressListener progressListener;
  private final ImageDownloader slowNetworkDownloader;
  private final boolean syncLoading;
  private final ImageSize targetSize;
  final String uri;

  public LoadAndDisplayImageTask(ImageLoaderEngine paramImageLoaderEngine, ImageLoadingInfo paramImageLoadingInfo, Handler paramHandler)
  {
    this.engine = paramImageLoaderEngine;
    this.imageLoadingInfo = paramImageLoadingInfo;
    this.handler = paramHandler;
    this.configuration = paramImageLoaderEngine.configuration;
    this.downloader = this.configuration.downloader;
    this.networkDeniedDownloader = this.configuration.networkDeniedDownloader;
    this.slowNetworkDownloader = this.configuration.slowNetworkDownloader;
    this.decoder = this.configuration.decoder;
    this.uri = paramImageLoadingInfo.uri;
    this.memoryCacheKey = paramImageLoadingInfo.memoryCacheKey;
    this.imageAware = paramImageLoadingInfo.imageAware;
    this.targetSize = paramImageLoadingInfo.targetSize;
    this.options = paramImageLoadingInfo.options;
    this.listener = paramImageLoadingInfo.listener;
    this.progressListener = paramImageLoadingInfo.progressListener;
    this.syncLoading = this.options.isSyncLoading();
  }

  private void checkTaskInterrupted()
    throws LoadAndDisplayImageTask.TaskCancelledException
  {
    if (isTaskInterrupted())
      throw new TaskCancelledException();
  }

  private void checkTaskNotActual()
    throws LoadAndDisplayImageTask.TaskCancelledException
  {
    checkViewCollected();
    checkViewReused();
  }

  private void checkViewCollected()
    throws LoadAndDisplayImageTask.TaskCancelledException
  {
    if (isViewCollected())
      throw new TaskCancelledException();
  }

  private void checkViewReused()
    throws LoadAndDisplayImageTask.TaskCancelledException
  {
    if (isViewReused())
      throw new TaskCancelledException();
  }

  private Bitmap decodeImage(String paramString)
    throws IOException
  {
    ViewScaleType localViewScaleType = this.imageAware.getScaleType();
    ImageDecodingInfo localImageDecodingInfo = new ImageDecodingInfo(this.memoryCacheKey, paramString, this.uri, this.targetSize, localViewScaleType, getDownloader(), this.options);
    return this.decoder.decode(localImageDecodingInfo);
  }

  private boolean delayIfNeed()
  {
    if (this.options.shouldDelayBeforeLoading())
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = Integer.valueOf(this.options.getDelayBeforeLoading());
      arrayOfObject1[1] = this.memoryCacheKey;
      L.d("Delay %d ms before loading...  [%s]", arrayOfObject1);
      try
      {
        Thread.sleep(this.options.getDelayBeforeLoading());
        return isTaskNotActual();
      }
      catch (InterruptedException localInterruptedException)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.memoryCacheKey;
        L.e("Task was interrupted [%s]", arrayOfObject2);
        return true;
      }
    }
    return false;
  }

  private boolean downloadImage()
    throws IOException
  {
    InputStream localInputStream = getDownloader().getStream(this.uri, this.options.getExtraForDownloader());
    return this.configuration.diskCache.save(this.uri, localInputStream, this);
  }

  private void fireCancelEvent()
  {
    if ((this.syncLoading) || (isTaskInterrupted()))
      return;
    runTask(new Runnable()
    {
      public void run()
      {
        LoadAndDisplayImageTask.this.listener.onLoadingCancelled(LoadAndDisplayImageTask.this.uri, LoadAndDisplayImageTask.this.imageAware.getWrappedView());
      }
    }
    , false, this.handler, this.engine);
  }

  private void fireFailEvent(final FailReason.FailType paramFailType, final Throwable paramThrowable)
  {
    if ((this.syncLoading) || (isTaskInterrupted()) || (isTaskNotActual()))
      return;
    runTask(new Runnable()
    {
      public void run()
      {
        if (LoadAndDisplayImageTask.this.options.shouldShowImageOnFail())
          LoadAndDisplayImageTask.this.imageAware.setImageDrawable(LoadAndDisplayImageTask.this.options.getImageOnFail(LoadAndDisplayImageTask.this.configuration.resources));
        LoadAndDisplayImageTask.this.listener.onLoadingFailed(LoadAndDisplayImageTask.this.uri, LoadAndDisplayImageTask.this.imageAware.getWrappedView(), new FailReason(paramFailType, paramThrowable));
      }
    }
    , false, this.handler, this.engine);
  }

  private boolean fireProgressEvent(final int paramInt1, final int paramInt2)
  {
    if ((isTaskInterrupted()) || (isTaskNotActual()))
      return false;
    if (this.progressListener != null)
      runTask(new Runnable()
      {
        public void run()
        {
          LoadAndDisplayImageTask.this.progressListener.onProgressUpdate(LoadAndDisplayImageTask.this.uri, LoadAndDisplayImageTask.this.imageAware.getWrappedView(), paramInt1, paramInt2);
        }
      }
      , false, this.handler, this.engine);
    return true;
  }

  private ImageDownloader getDownloader()
  {
    if (this.engine.isNetworkDenied())
      return this.networkDeniedDownloader;
    if (this.engine.isSlowNetwork())
      return this.slowNetworkDownloader;
    return this.downloader;
  }

  private boolean isTaskInterrupted()
  {
    if (Thread.interrupted())
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.memoryCacheKey;
      L.d("Task was interrupted [%s]", arrayOfObject);
      return true;
    }
    return false;
  }

  private boolean isTaskNotActual()
  {
    return (isViewCollected()) || (isViewReused());
  }

  private boolean isViewCollected()
  {
    if (this.imageAware.isCollected())
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.memoryCacheKey;
      L.d("ImageAware was collected by GC. Task is cancelled. [%s]", arrayOfObject);
      return true;
    }
    return false;
  }

  private boolean isViewReused()
  {
    String str = this.engine.getLoadingUriForView(this.imageAware);
    if (!this.memoryCacheKey.equals(str));
    for (int i = 1; i != 0; i = 0)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.memoryCacheKey;
      L.d("ImageAware is reused for another image. Task is cancelled. [%s]", arrayOfObject);
      return true;
    }
    return false;
  }

  private boolean resizeAndSaveImage(int paramInt1, int paramInt2)
    throws IOException
  {
    File localFile = this.configuration.diskCache.get(this.uri);
    boolean bool1 = false;
    if (localFile != null)
    {
      boolean bool2 = localFile.exists();
      bool1 = false;
      if (bool2)
      {
        ImageSize localImageSize = new ImageSize(paramInt1, paramInt2);
        DisplayImageOptions localDisplayImageOptions = new DisplayImageOptions.Builder().cloneFrom(this.options).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
        ImageDecodingInfo localImageDecodingInfo = new ImageDecodingInfo(this.memoryCacheKey, ImageDownloader.Scheme.FILE.wrap(localFile.getAbsolutePath()), this.uri, localImageSize, ViewScaleType.FIT_INSIDE, getDownloader(), localDisplayImageOptions);
        Bitmap localBitmap = this.decoder.decode(localImageDecodingInfo);
        if ((localBitmap != null) && (this.configuration.processorForDiskCache != null))
        {
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = this.memoryCacheKey;
          L.d("Process image before cache on disk [%s]", arrayOfObject1);
          localBitmap = this.configuration.processorForDiskCache.process(localBitmap);
          if (localBitmap == null)
          {
            Object[] arrayOfObject2 = new Object[1];
            arrayOfObject2[0] = this.memoryCacheKey;
            L.e("Bitmap processor for disk cache returned null [%s]", arrayOfObject2);
          }
        }
        bool1 = false;
        if (localBitmap != null)
        {
          bool1 = this.configuration.diskCache.save(this.uri, localBitmap);
          localBitmap.recycle();
        }
      }
    }
    return bool1;
  }

  static void runTask(Runnable paramRunnable, boolean paramBoolean, Handler paramHandler, ImageLoaderEngine paramImageLoaderEngine)
  {
    if (paramBoolean)
    {
      paramRunnable.run();
      return;
    }
    if (paramHandler == null)
    {
      paramImageLoaderEngine.fireCallback(paramRunnable);
      return;
    }
    paramHandler.post(paramRunnable);
  }

  private boolean tryCacheImageOnDisk()
    throws LoadAndDisplayImageTask.TaskCancelledException
  {
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = this.memoryCacheKey;
    L.d("Cache image on disk [%s]", arrayOfObject1);
    try
    {
      boolean bool = downloadImage();
      if (bool)
      {
        int i = this.configuration.maxImageWidthForDiskCache;
        int j = this.configuration.maxImageHeightForDiskCache;
        if ((i > 0) || (j > 0))
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = this.memoryCacheKey;
          L.d("Resize image in disk cache [%s]", arrayOfObject2);
          resizeAndSaveImage(i, j);
        }
      }
      return bool;
    }
    catch (IOException localIOException)
    {
      L.e(localIOException);
    }
    return false;
  }

  private Bitmap tryLoadBitmap()
    throws LoadAndDisplayImageTask.TaskCancelledException
  {
    Bitmap localBitmap = null;
    try
    {
      File localFile1 = this.configuration.diskCache.get(this.uri);
      localBitmap = null;
      if (localFile1 != null)
      {
        boolean bool = localFile1.exists();
        localBitmap = null;
        if (bool)
        {
          Object[] arrayOfObject1 = new Object[1];
          arrayOfObject1[0] = this.memoryCacheKey;
          L.d("Load image from disk cache [%s]", arrayOfObject1);
          this.loadedFrom = LoadedFrom.DISC_CACHE;
          checkTaskNotActual();
          localBitmap = decodeImage(ImageDownloader.Scheme.FILE.wrap(localFile1.getAbsolutePath()));
        }
      }
      if ((localBitmap == null) || (localBitmap.getWidth() <= 0) || (localBitmap.getHeight() <= 0))
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.memoryCacheKey;
        L.d("Load image from network [%s]", arrayOfObject2);
        this.loadedFrom = LoadedFrom.NETWORK;
        String str = this.uri;
        if ((this.options.isCacheOnDisk()) && (tryCacheImageOnDisk()))
        {
          File localFile2 = this.configuration.diskCache.get(this.uri);
          if (localFile2 != null)
            str = ImageDownloader.Scheme.FILE.wrap(localFile2.getAbsolutePath());
        }
        checkTaskNotActual();
        localBitmap = decodeImage(str);
        if ((localBitmap == null) || (localBitmap.getWidth() <= 0) || (localBitmap.getHeight() <= 0))
          fireFailEvent(FailReason.FailType.DECODING_ERROR, null);
      }
      return localBitmap;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      fireFailEvent(FailReason.FailType.NETWORK_DENIED, null);
      return localBitmap;
    }
    catch (TaskCancelledException localTaskCancelledException)
    {
      throw localTaskCancelledException;
    }
    catch (IOException localIOException)
    {
      L.e(localIOException);
      fireFailEvent(FailReason.FailType.IO_ERROR, localIOException);
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      L.e(localOutOfMemoryError);
      fireFailEvent(FailReason.FailType.OUT_OF_MEMORY, localOutOfMemoryError);
      return localBitmap;
    }
    catch (Throwable localThrowable)
    {
      L.e(localThrowable);
      fireFailEvent(FailReason.FailType.UNKNOWN, localThrowable);
    }
    return localBitmap;
  }

  private boolean waitIfPaused()
  {
    AtomicBoolean localAtomicBoolean = this.engine.getPause();
    if (localAtomicBoolean.get());
    synchronized (this.engine.getPauseLock())
    {
      if (localAtomicBoolean.get())
      {
        Object[] arrayOfObject1 = new Object[1];
        arrayOfObject1[0] = this.memoryCacheKey;
        L.d("ImageLoader is paused. Waiting...  [%s]", arrayOfObject1);
      }
      try
      {
        this.engine.getPauseLock().wait();
        Object[] arrayOfObject3 = new Object[1];
        arrayOfObject3[0] = this.memoryCacheKey;
        L.d(".. Resume loading [%s]", arrayOfObject3);
        return isTaskNotActual();
      }
      catch (InterruptedException localInterruptedException)
      {
        Object[] arrayOfObject2 = new Object[1];
        arrayOfObject2[0] = this.memoryCacheKey;
        L.e("Task was interrupted [%s]", arrayOfObject2);
        return true;
      }
    }
  }

  String getLoadingUri()
  {
    return this.uri;
  }

  public boolean onBytesCopied(int paramInt1, int paramInt2)
  {
    return (this.syncLoading) || (fireProgressEvent(paramInt1, paramInt2));
  }

  public void run()
  {
    if (waitIfPaused());
    while (delayIfNeed())
      return;
    ReentrantLock localReentrantLock = this.imageLoadingInfo.loadFromUriLock;
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = this.memoryCacheKey;
    L.d("Start display image task [%s]", arrayOfObject1);
    if (localReentrantLock.isLocked())
    {
      Object[] arrayOfObject8 = new Object[1];
      arrayOfObject8[0] = this.memoryCacheKey;
      L.d("Image already is loading. Waiting... [%s]", arrayOfObject8);
    }
    localReentrantLock.lock();
    try
    {
      checkTaskNotActual();
      Object localObject2 = (Bitmap)this.configuration.memoryCache.get(this.memoryCacheKey);
      if ((localObject2 == null) || (((Bitmap)localObject2).isRecycled()))
      {
        Bitmap localBitmap = tryLoadBitmap();
        localObject2 = localBitmap;
        if (localObject2 == null)
          return;
        checkTaskNotActual();
        checkTaskInterrupted();
        if (this.options.shouldPreProcess())
        {
          Object[] arrayOfObject5 = new Object[1];
          arrayOfObject5[0] = this.memoryCacheKey;
          L.d("PreProcess image before caching in memory [%s]", arrayOfObject5);
          localObject2 = this.options.getPreProcessor().process((Bitmap)localObject2);
          if (localObject2 == null)
          {
            Object[] arrayOfObject6 = new Object[1];
            arrayOfObject6[0] = this.memoryCacheKey;
            L.e("Pre-processor returned null [%s]", arrayOfObject6);
          }
        }
        if ((localObject2 != null) && (this.options.isCacheInMemory()))
        {
          Object[] arrayOfObject4 = new Object[1];
          arrayOfObject4[0] = this.memoryCacheKey;
          L.d("Cache image in memory [%s]", arrayOfObject4);
          this.configuration.memoryCache.put(this.memoryCacheKey, localObject2);
        }
      }
      while (true)
      {
        if ((localObject2 != null) && (this.options.shouldPostProcess()))
        {
          Object[] arrayOfObject2 = new Object[1];
          arrayOfObject2[0] = this.memoryCacheKey;
          L.d("PostProcess image before displaying [%s]", arrayOfObject2);
          localObject2 = this.options.getPostProcessor().process((Bitmap)localObject2);
          if (localObject2 == null)
          {
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = this.memoryCacheKey;
            L.e("Post-processor returned null [%s]", arrayOfObject3);
          }
        }
        checkTaskNotActual();
        checkTaskInterrupted();
        localReentrantLock.unlock();
        runTask(new DisplayBitmapTask((Bitmap)localObject2, this.imageLoadingInfo, this.engine, this.loadedFrom), this.syncLoading, this.handler, this.engine);
        return;
        this.loadedFrom = LoadedFrom.MEMORY_CACHE;
        Object[] arrayOfObject7 = new Object[1];
        arrayOfObject7[0] = this.memoryCacheKey;
        L.d("...Get cached bitmap from memory after waiting. [%s]", arrayOfObject7);
      }
    }
    catch (TaskCancelledException localTaskCancelledException)
    {
      fireCancelEvent();
      return;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  class TaskCancelledException extends Exception
  {
    TaskCancelledException()
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.LoadAndDisplayImageTask
 * JD-Core Version:    0.6.2
 */