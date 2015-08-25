package com.nostra13.universalimageloader.core;

import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;
import java.util.concurrent.locks.ReentrantLock;

final class ImageLoadingInfo
{
  final ImageAware imageAware;
  final ImageLoadingListener listener;
  final ReentrantLock loadFromUriLock;
  final String memoryCacheKey;
  final DisplayImageOptions options;
  final ImageLoadingProgressListener progressListener;
  final ImageSize targetSize;
  final String uri;

  public ImageLoadingInfo(String paramString1, ImageAware paramImageAware, ImageSize paramImageSize, String paramString2, DisplayImageOptions paramDisplayImageOptions, ImageLoadingListener paramImageLoadingListener, ImageLoadingProgressListener paramImageLoadingProgressListener, ReentrantLock paramReentrantLock)
  {
    this.uri = paramString1;
    this.imageAware = paramImageAware;
    this.targetSize = paramImageSize;
    this.options = paramDisplayImageOptions;
    this.listener = paramImageLoadingListener;
    this.progressListener = paramImageLoadingProgressListener;
    this.loadFromUriLock = paramReentrantLock;
    this.memoryCacheKey = paramString2;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.ImageLoadingInfo
 * JD-Core Version:    0.6.2
 */