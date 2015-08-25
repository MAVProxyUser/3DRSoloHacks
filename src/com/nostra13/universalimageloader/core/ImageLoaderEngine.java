package com.nostra13.universalimageloader.core;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

class ImageLoaderEngine
{
  private final Map<Integer, String> cacheKeysForImageAwares = Collections.synchronizedMap(new HashMap());
  final ImageLoaderConfiguration configuration;
  private final AtomicBoolean networkDenied = new AtomicBoolean(false);
  private final Object pauseLock = new Object();
  private final AtomicBoolean paused = new AtomicBoolean(false);
  private final AtomicBoolean slowNetwork = new AtomicBoolean(false);
  private Executor taskDistributor;
  private Executor taskExecutor;
  private Executor taskExecutorForCachedImages;
  private final Map<String, ReentrantLock> uriLocks = new WeakHashMap();

  ImageLoaderEngine(ImageLoaderConfiguration paramImageLoaderConfiguration)
  {
    this.configuration = paramImageLoaderConfiguration;
    this.taskExecutor = paramImageLoaderConfiguration.taskExecutor;
    this.taskExecutorForCachedImages = paramImageLoaderConfiguration.taskExecutorForCachedImages;
    this.taskDistributor = DefaultConfigurationFactory.createTaskDistributor();
  }

  private Executor createTaskExecutor()
  {
    return DefaultConfigurationFactory.createExecutor(this.configuration.threadPoolSize, this.configuration.threadPriority, this.configuration.tasksProcessingType);
  }

  private void initExecutorsIfNeed()
  {
    if ((!this.configuration.customExecutor) && (((ExecutorService)this.taskExecutor).isShutdown()))
      this.taskExecutor = createTaskExecutor();
    if ((!this.configuration.customExecutorForCachedImages) && (((ExecutorService)this.taskExecutorForCachedImages).isShutdown()))
      this.taskExecutorForCachedImages = createTaskExecutor();
  }

  void cancelDisplayTaskFor(ImageAware paramImageAware)
  {
    this.cacheKeysForImageAwares.remove(Integer.valueOf(paramImageAware.getId()));
  }

  void denyNetworkDownloads(boolean paramBoolean)
  {
    this.networkDenied.set(paramBoolean);
  }

  void fireCallback(Runnable paramRunnable)
  {
    this.taskDistributor.execute(paramRunnable);
  }

  String getLoadingUriForView(ImageAware paramImageAware)
  {
    return (String)this.cacheKeysForImageAwares.get(Integer.valueOf(paramImageAware.getId()));
  }

  ReentrantLock getLockForUri(String paramString)
  {
    ReentrantLock localReentrantLock = (ReentrantLock)this.uriLocks.get(paramString);
    if (localReentrantLock == null)
    {
      localReentrantLock = new ReentrantLock();
      this.uriLocks.put(paramString, localReentrantLock);
    }
    return localReentrantLock;
  }

  AtomicBoolean getPause()
  {
    return this.paused;
  }

  Object getPauseLock()
  {
    return this.pauseLock;
  }

  void handleSlowNetwork(boolean paramBoolean)
  {
    this.slowNetwork.set(paramBoolean);
  }

  boolean isNetworkDenied()
  {
    return this.networkDenied.get();
  }

  boolean isSlowNetwork()
  {
    return this.slowNetwork.get();
  }

  void pause()
  {
    this.paused.set(true);
  }

  void prepareDisplayTaskFor(ImageAware paramImageAware, String paramString)
  {
    this.cacheKeysForImageAwares.put(Integer.valueOf(paramImageAware.getId()), paramString);
  }

  void resume()
  {
    this.paused.set(false);
    synchronized (this.pauseLock)
    {
      this.pauseLock.notifyAll();
      return;
    }
  }

  void stop()
  {
    if (!this.configuration.customExecutor)
      ((ExecutorService)this.taskExecutor).shutdownNow();
    if (!this.configuration.customExecutorForCachedImages)
      ((ExecutorService)this.taskExecutorForCachedImages).shutdownNow();
    this.cacheKeysForImageAwares.clear();
    this.uriLocks.clear();
  }

  void submit(final LoadAndDisplayImageTask paramLoadAndDisplayImageTask)
  {
    this.taskDistributor.execute(new Runnable()
    {
      public void run()
      {
        File localFile = ImageLoaderEngine.this.configuration.diskCache.get(paramLoadAndDisplayImageTask.getLoadingUri());
        if ((localFile != null) && (localFile.exists()));
        for (int i = 1; ; i = 0)
        {
          ImageLoaderEngine.this.initExecutorsIfNeed();
          if (i == 0)
            break;
          ImageLoaderEngine.this.taskExecutorForCachedImages.execute(paramLoadAndDisplayImageTask);
          return;
        }
        ImageLoaderEngine.this.taskExecutor.execute(paramLoadAndDisplayImageTask);
      }
    });
  }

  void submit(ProcessAndDisplayImageTask paramProcessAndDisplayImageTask)
  {
    initExecutorsIfNeed();
    this.taskExecutorForCachedImages.execute(paramProcessAndDisplayImageTask);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.ImageLoaderEngine
 * JD-Core Version:    0.6.2
 */