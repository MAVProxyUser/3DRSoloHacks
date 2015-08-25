package com.nostra13.universalimageloader.core;

import android.content.Context;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.assist.deque.LIFOLinkedBlockingDeque;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DefaultConfigurationFactory
{
  public static BitmapDisplayer createBitmapDisplayer()
  {
    return new SimpleBitmapDisplayer();
  }

  public static DiskCache createDiskCache(Context paramContext, FileNameGenerator paramFileNameGenerator, long paramLong, int paramInt)
  {
    File localFile1 = createReserveDiskCacheDir(paramContext);
    if ((paramLong > 0L) || (paramInt > 0))
    {
      File localFile2 = StorageUtils.getIndividualCacheDirectory(paramContext);
      try
      {
        LruDiscCache localLruDiscCache = new LruDiscCache(localFile2, localFile1, paramFileNameGenerator, paramLong, paramInt);
        return localLruDiscCache;
      }
      catch (IOException localIOException)
      {
        L.e(localIOException);
      }
    }
    return new UnlimitedDiscCache(StorageUtils.getCacheDirectory(paramContext), localFile1, paramFileNameGenerator);
  }

  public static Executor createExecutor(int paramInt1, int paramInt2, QueueProcessingType paramQueueProcessingType)
  {
    int i;
    if (paramQueueProcessingType == QueueProcessingType.LIFO)
    {
      i = 1;
      if (i == 0)
        break label56;
    }
    label56: for (Object localObject = new LIFOLinkedBlockingDeque(); ; localObject = new LinkedBlockingQueue())
    {
      BlockingQueue localBlockingQueue = (BlockingQueue)localObject;
      return new ThreadPoolExecutor(paramInt1, paramInt1, 0L, TimeUnit.MILLISECONDS, localBlockingQueue, createThreadFactory(paramInt2, "uil-pool-"));
      i = 0;
      break;
    }
  }

  public static FileNameGenerator createFileNameGenerator()
  {
    return new HashCodeFileNameGenerator();
  }

  public static ImageDecoder createImageDecoder(boolean paramBoolean)
  {
    return new BaseImageDecoder(paramBoolean);
  }

  public static ImageDownloader createImageDownloader(Context paramContext)
  {
    return new BaseImageDownloader(paramContext);
  }

  public static MemoryCache createMemoryCache(int paramInt)
  {
    if (paramInt == 0)
      paramInt = (int)(Runtime.getRuntime().maxMemory() / 8L);
    return new LruMemoryCache(paramInt);
  }

  private static File createReserveDiskCacheDir(Context paramContext)
  {
    Object localObject = StorageUtils.getCacheDirectory(paramContext, false);
    File localFile = new File((File)localObject, "uil-images");
    if ((localFile.exists()) || (localFile.mkdir()))
      localObject = localFile;
    return localObject;
  }

  public static Executor createTaskDistributor()
  {
    return Executors.newCachedThreadPool(createThreadFactory(5, "uil-pool-d-"));
  }

  private static ThreadFactory createThreadFactory(int paramInt, String paramString)
  {
    return new DefaultThreadFactory(paramInt, paramString);
  }

  private static class DefaultThreadFactory
    implements ThreadFactory
  {
    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final String namePrefix;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final int threadPriority;

    DefaultThreadFactory(int paramInt, String paramString)
    {
      this.threadPriority = paramInt;
      this.group = Thread.currentThread().getThreadGroup();
      this.namePrefix = (paramString + poolNumber.getAndIncrement() + "-thread-");
    }

    public Thread newThread(Runnable paramRunnable)
    {
      Thread localThread = new Thread(this.group, paramRunnable, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
      if (localThread.isDaemon())
        localThread.setDaemon(false);
      localThread.setPriority(this.threadPriority);
      return localThread;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.DefaultConfigurationFactory
 * JD-Core Version:    0.6.2
 */