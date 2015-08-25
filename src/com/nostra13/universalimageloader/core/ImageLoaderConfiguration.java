package com.nostra13.universalimageloader.core;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.FuzzyKeyMemoryCache;
import com.nostra13.universalimageloader.core.assist.FlushedInputStream;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.ImageDecoder;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.core.process.BitmapProcessor;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;

public final class ImageLoaderConfiguration
{
  final boolean customExecutor;
  final boolean customExecutorForCachedImages;
  final ImageDecoder decoder;
  final DisplayImageOptions defaultDisplayImageOptions;
  final DiskCache diskCache;
  final ImageDownloader downloader;
  final int maxImageHeightForDiskCache;
  final int maxImageHeightForMemoryCache;
  final int maxImageWidthForDiskCache;
  final int maxImageWidthForMemoryCache;
  final MemoryCache memoryCache;
  final ImageDownloader networkDeniedDownloader;
  final BitmapProcessor processorForDiskCache;
  final Resources resources;
  final ImageDownloader slowNetworkDownloader;
  final Executor taskExecutor;
  final Executor taskExecutorForCachedImages;
  final QueueProcessingType tasksProcessingType;
  final int threadPoolSize;
  final int threadPriority;

  private ImageLoaderConfiguration(Builder paramBuilder)
  {
    this.resources = paramBuilder.context.getResources();
    this.maxImageWidthForMemoryCache = paramBuilder.maxImageWidthForMemoryCache;
    this.maxImageHeightForMemoryCache = paramBuilder.maxImageHeightForMemoryCache;
    this.maxImageWidthForDiskCache = paramBuilder.maxImageWidthForDiskCache;
    this.maxImageHeightForDiskCache = paramBuilder.maxImageHeightForDiskCache;
    this.processorForDiskCache = paramBuilder.processorForDiskCache;
    this.taskExecutor = paramBuilder.taskExecutor;
    this.taskExecutorForCachedImages = paramBuilder.taskExecutorForCachedImages;
    this.threadPoolSize = paramBuilder.threadPoolSize;
    this.threadPriority = paramBuilder.threadPriority;
    this.tasksProcessingType = paramBuilder.tasksProcessingType;
    this.diskCache = paramBuilder.diskCache;
    this.memoryCache = paramBuilder.memoryCache;
    this.defaultDisplayImageOptions = paramBuilder.defaultDisplayImageOptions;
    this.downloader = paramBuilder.downloader;
    this.decoder = paramBuilder.decoder;
    this.customExecutor = paramBuilder.customExecutor;
    this.customExecutorForCachedImages = paramBuilder.customExecutorForCachedImages;
    this.networkDeniedDownloader = new NetworkDeniedImageDownloader(this.downloader);
    this.slowNetworkDownloader = new SlowNetworkImageDownloader(this.downloader);
    L.writeDebugLogs(paramBuilder.writeLogs);
  }

  public static ImageLoaderConfiguration createDefault(Context paramContext)
  {
    return new Builder(paramContext).build();
  }

  ImageSize getMaxImageSize()
  {
    DisplayMetrics localDisplayMetrics = this.resources.getDisplayMetrics();
    int i = this.maxImageWidthForMemoryCache;
    if (i <= 0)
      i = localDisplayMetrics.widthPixels;
    int j = this.maxImageHeightForMemoryCache;
    if (j <= 0)
      j = localDisplayMetrics.heightPixels;
    return new ImageSize(i, j);
  }

  public static class Builder
  {
    public static final QueueProcessingType DEFAULT_TASK_PROCESSING_TYPE = QueueProcessingType.FIFO;
    public static final int DEFAULT_THREAD_POOL_SIZE = 3;
    public static final int DEFAULT_THREAD_PRIORITY = 4;
    private static final String WARNING_OVERLAP_DISK_CACHE_NAME_GENERATOR = "diskCache() and diskCacheFileNameGenerator() calls overlap each other";
    private static final String WARNING_OVERLAP_DISK_CACHE_PARAMS = "diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other";
    private static final String WARNING_OVERLAP_EXECUTOR = "threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.";
    private static final String WARNING_OVERLAP_MEMORY_CACHE = "memoryCache() and memoryCacheSize() calls overlap each other";
    private Context context;
    private boolean customExecutor = false;
    private boolean customExecutorForCachedImages = false;
    private ImageDecoder decoder;
    private DisplayImageOptions defaultDisplayImageOptions = null;
    private boolean denyCacheImageMultipleSizesInMemory = false;
    private DiskCache diskCache = null;
    private int diskCacheFileCount = 0;
    private FileNameGenerator diskCacheFileNameGenerator = null;
    private long diskCacheSize = 0L;
    private ImageDownloader downloader = null;
    private int maxImageHeightForDiskCache = 0;
    private int maxImageHeightForMemoryCache = 0;
    private int maxImageWidthForDiskCache = 0;
    private int maxImageWidthForMemoryCache = 0;
    private MemoryCache memoryCache = null;
    private int memoryCacheSize = 0;
    private BitmapProcessor processorForDiskCache = null;
    private Executor taskExecutor = null;
    private Executor taskExecutorForCachedImages = null;
    private QueueProcessingType tasksProcessingType = DEFAULT_TASK_PROCESSING_TYPE;
    private int threadPoolSize = 3;
    private int threadPriority = 4;
    private boolean writeLogs = false;

    public Builder(Context paramContext)
    {
      this.context = paramContext.getApplicationContext();
    }

    private void initEmptyFieldsWithDefaultValues()
    {
      if (this.taskExecutor == null)
      {
        this.taskExecutor = DefaultConfigurationFactory.createExecutor(this.threadPoolSize, this.threadPriority, this.tasksProcessingType);
        if (this.taskExecutorForCachedImages != null)
          break label198;
        this.taskExecutorForCachedImages = DefaultConfigurationFactory.createExecutor(this.threadPoolSize, this.threadPriority, this.tasksProcessingType);
      }
      while (true)
      {
        if (this.diskCache == null)
        {
          if (this.diskCacheFileNameGenerator == null)
            this.diskCacheFileNameGenerator = DefaultConfigurationFactory.createFileNameGenerator();
          this.diskCache = DefaultConfigurationFactory.createDiskCache(this.context, this.diskCacheFileNameGenerator, this.diskCacheSize, this.diskCacheFileCount);
        }
        if (this.memoryCache == null)
          this.memoryCache = DefaultConfigurationFactory.createMemoryCache(this.memoryCacheSize);
        if (this.denyCacheImageMultipleSizesInMemory)
          this.memoryCache = new FuzzyKeyMemoryCache(this.memoryCache, MemoryCacheUtils.createFuzzyKeyComparator());
        if (this.downloader == null)
          this.downloader = DefaultConfigurationFactory.createImageDownloader(this.context);
        if (this.decoder == null)
          this.decoder = DefaultConfigurationFactory.createImageDecoder(this.writeLogs);
        if (this.defaultDisplayImageOptions == null)
          this.defaultDisplayImageOptions = DisplayImageOptions.createSimple();
        return;
        this.customExecutor = true;
        break;
        label198: this.customExecutorForCachedImages = true;
      }
    }

    public ImageLoaderConfiguration build()
    {
      initEmptyFieldsWithDefaultValues();
      return new ImageLoaderConfiguration(this, null);
    }

    public Builder defaultDisplayImageOptions(DisplayImageOptions paramDisplayImageOptions)
    {
      this.defaultDisplayImageOptions = paramDisplayImageOptions;
      return this;
    }

    public Builder denyCacheImageMultipleSizesInMemory()
    {
      this.denyCacheImageMultipleSizesInMemory = true;
      return this;
    }

    @Deprecated
    public Builder discCache(DiskCache paramDiskCache)
    {
      return diskCache(paramDiskCache);
    }

    @Deprecated
    public Builder discCacheExtraOptions(int paramInt1, int paramInt2, BitmapProcessor paramBitmapProcessor)
    {
      return diskCacheExtraOptions(paramInt1, paramInt2, paramBitmapProcessor);
    }

    @Deprecated
    public Builder discCacheFileCount(int paramInt)
    {
      return diskCacheFileCount(paramInt);
    }

    @Deprecated
    public Builder discCacheFileNameGenerator(FileNameGenerator paramFileNameGenerator)
    {
      return diskCacheFileNameGenerator(paramFileNameGenerator);
    }

    @Deprecated
    public Builder discCacheSize(int paramInt)
    {
      return diskCacheSize(paramInt);
    }

    public Builder diskCache(DiskCache paramDiskCache)
    {
      if ((this.diskCacheSize > 0L) || (this.diskCacheFileCount > 0))
        L.w("diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other", new Object[0]);
      if (this.diskCacheFileNameGenerator != null)
        L.w("diskCache() and diskCacheFileNameGenerator() calls overlap each other", new Object[0]);
      this.diskCache = paramDiskCache;
      return this;
    }

    public Builder diskCacheExtraOptions(int paramInt1, int paramInt2, BitmapProcessor paramBitmapProcessor)
    {
      this.maxImageWidthForDiskCache = paramInt1;
      this.maxImageHeightForDiskCache = paramInt2;
      this.processorForDiskCache = paramBitmapProcessor;
      return this;
    }

    public Builder diskCacheFileCount(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("maxFileCount must be a positive number");
      if (this.diskCache != null)
        L.w("diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other", new Object[0]);
      this.diskCacheFileCount = paramInt;
      return this;
    }

    public Builder diskCacheFileNameGenerator(FileNameGenerator paramFileNameGenerator)
    {
      if (this.diskCache != null)
        L.w("diskCache() and diskCacheFileNameGenerator() calls overlap each other", new Object[0]);
      this.diskCacheFileNameGenerator = paramFileNameGenerator;
      return this;
    }

    public Builder diskCacheSize(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("maxCacheSize must be a positive number");
      if (this.diskCache != null)
        L.w("diskCache(), diskCacheSize() and diskCacheFileCount calls overlap each other", new Object[0]);
      this.diskCacheSize = paramInt;
      return this;
    }

    public Builder imageDecoder(ImageDecoder paramImageDecoder)
    {
      this.decoder = paramImageDecoder;
      return this;
    }

    public Builder imageDownloader(ImageDownloader paramImageDownloader)
    {
      this.downloader = paramImageDownloader;
      return this;
    }

    public Builder memoryCache(MemoryCache paramMemoryCache)
    {
      if (this.memoryCacheSize != 0)
        L.w("memoryCache() and memoryCacheSize() calls overlap each other", new Object[0]);
      this.memoryCache = paramMemoryCache;
      return this;
    }

    public Builder memoryCacheExtraOptions(int paramInt1, int paramInt2)
    {
      this.maxImageWidthForMemoryCache = paramInt1;
      this.maxImageHeightForMemoryCache = paramInt2;
      return this;
    }

    public Builder memoryCacheSize(int paramInt)
    {
      if (paramInt <= 0)
        throw new IllegalArgumentException("memoryCacheSize must be a positive number");
      if (this.memoryCache != null)
        L.w("memoryCache() and memoryCacheSize() calls overlap each other", new Object[0]);
      this.memoryCacheSize = paramInt;
      return this;
    }

    public Builder memoryCacheSizePercentage(int paramInt)
    {
      if ((paramInt <= 0) || (paramInt >= 100))
        throw new IllegalArgumentException("availableMemoryPercent must be in range (0 < % < 100)");
      if (this.memoryCache != null)
        L.w("memoryCache() and memoryCacheSize() calls overlap each other", new Object[0]);
      this.memoryCacheSize = ((int)((float)Runtime.getRuntime().maxMemory() * (paramInt / 100.0F)));
      return this;
    }

    public Builder taskExecutor(Executor paramExecutor)
    {
      if ((this.threadPoolSize != 3) || (this.threadPriority != 4) || (this.tasksProcessingType != DEFAULT_TASK_PROCESSING_TYPE))
        L.w("threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.", new Object[0]);
      this.taskExecutor = paramExecutor;
      return this;
    }

    public Builder taskExecutorForCachedImages(Executor paramExecutor)
    {
      if ((this.threadPoolSize != 3) || (this.threadPriority != 4) || (this.tasksProcessingType != DEFAULT_TASK_PROCESSING_TYPE))
        L.w("threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.", new Object[0]);
      this.taskExecutorForCachedImages = paramExecutor;
      return this;
    }

    public Builder tasksProcessingOrder(QueueProcessingType paramQueueProcessingType)
    {
      if ((this.taskExecutor != null) || (this.taskExecutorForCachedImages != null))
        L.w("threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.", new Object[0]);
      this.tasksProcessingType = paramQueueProcessingType;
      return this;
    }

    public Builder threadPoolSize(int paramInt)
    {
      if ((this.taskExecutor != null) || (this.taskExecutorForCachedImages != null))
        L.w("threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.", new Object[0]);
      this.threadPoolSize = paramInt;
      return this;
    }

    public Builder threadPriority(int paramInt)
    {
      if ((this.taskExecutor != null) || (this.taskExecutorForCachedImages != null))
        L.w("threadPoolSize(), threadPriority() and tasksProcessingOrder() calls can overlap taskExecutor() and taskExecutorForCachedImages() calls.", new Object[0]);
      if (paramInt < 1)
      {
        this.threadPriority = 1;
        return this;
      }
      if (paramInt > 10)
      {
        this.threadPriority = 10;
        return this;
      }
      this.threadPriority = paramInt;
      return this;
    }

    public Builder writeDebugLogs()
    {
      this.writeLogs = true;
      return this;
    }
  }

  private static class NetworkDeniedImageDownloader
    implements ImageDownloader
  {
    private final ImageDownloader wrappedDownloader;

    public NetworkDeniedImageDownloader(ImageDownloader paramImageDownloader)
    {
      this.wrappedDownloader = paramImageDownloader;
    }

    public InputStream getStream(String paramString, Object paramObject)
      throws IOException
    {
      switch (ImageLoaderConfiguration.1.$SwitchMap$com$nostra13$universalimageloader$core$download$ImageDownloader$Scheme[com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme.ofUri(paramString).ordinal()])
      {
      default:
        return this.wrappedDownloader.getStream(paramString, paramObject);
      case 1:
      case 2:
      }
      throw new IllegalStateException();
    }
  }

  private static class SlowNetworkImageDownloader
    implements ImageDownloader
  {
    private final ImageDownloader wrappedDownloader;

    public SlowNetworkImageDownloader(ImageDownloader paramImageDownloader)
    {
      this.wrappedDownloader = paramImageDownloader;
    }

    public InputStream getStream(String paramString, Object paramObject)
      throws IOException
    {
      InputStream localInputStream = this.wrappedDownloader.getStream(paramString, paramObject);
      switch (ImageLoaderConfiguration.1.$SwitchMap$com$nostra13$universalimageloader$core$download$ImageDownloader$Scheme[com.nostra13.universalimageloader.core.download.ImageDownloader.Scheme.ofUri(paramString).ordinal()])
      {
      default:
        return localInputStream;
      case 1:
      case 2:
      }
      return new FlushedInputStream(localInputStream);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.ImageLoaderConfiguration
 * JD-Core Version:    0.6.2
 */