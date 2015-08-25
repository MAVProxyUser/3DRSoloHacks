package com.nostra13.universalimageloader.utils;

import com.nostra13.universalimageloader.cache.disc.DiskCache;
import java.io.File;

public final class DiskCacheUtils
{
  public static File findInCache(String paramString, DiskCache paramDiskCache)
  {
    File localFile = paramDiskCache.get(paramString);
    if ((localFile != null) && (localFile.exists()))
      return localFile;
    return null;
  }

  public static boolean removeFromCache(String paramString, DiskCache paramDiskCache)
  {
    File localFile = paramDiskCache.get(paramString);
    return (localFile != null) && (localFile.exists()) && (localFile.delete());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.utils.DiskCacheUtils
 * JD-Core Version:    0.6.2
 */