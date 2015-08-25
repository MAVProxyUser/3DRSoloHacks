package com.nostra13.universalimageloader.cache.disc.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.utils.IoUtils.CopyListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LimitedAgeDiscCache extends BaseDiscCache
{
  private final Map<File, Long> loadingDates = Collections.synchronizedMap(new HashMap());
  private final long maxFileAge;

  public LimitedAgeDiscCache(File paramFile, long paramLong)
  {
    this(paramFile, null, DefaultConfigurationFactory.createFileNameGenerator(), paramLong);
  }

  public LimitedAgeDiscCache(File paramFile1, File paramFile2, long paramLong)
  {
    this(paramFile1, paramFile2, DefaultConfigurationFactory.createFileNameGenerator(), paramLong);
  }

  public LimitedAgeDiscCache(File paramFile1, File paramFile2, FileNameGenerator paramFileNameGenerator, long paramLong)
  {
    super(paramFile1, paramFile2, paramFileNameGenerator);
    this.maxFileAge = (1000L * paramLong);
  }

  private void rememberUsage(String paramString)
  {
    File localFile = getFile(paramString);
    long l = System.currentTimeMillis();
    localFile.setLastModified(l);
    this.loadingDates.put(localFile, Long.valueOf(l));
  }

  public void clear()
  {
    super.clear();
    this.loadingDates.clear();
  }

  public File get(String paramString)
  {
    File localFile = super.get(paramString);
    Long localLong;
    int i;
    if ((localFile != null) && (localFile.exists()))
    {
      localLong = (Long)this.loadingDates.get(localFile);
      if (localLong != null)
        break label80;
      i = 0;
      localLong = Long.valueOf(localFile.lastModified());
      if (System.currentTimeMillis() - localLong.longValue() <= this.maxFileAge)
        break label86;
      localFile.delete();
      this.loadingDates.remove(localFile);
    }
    label80: label86: 
    while (i != 0)
    {
      return localFile;
      i = 1;
      break;
    }
    this.loadingDates.put(localFile, localLong);
    return localFile;
  }

  public boolean remove(String paramString)
  {
    this.loadingDates.remove(getFile(paramString));
    return super.remove(paramString);
  }

  public boolean save(String paramString, Bitmap paramBitmap)
    throws IOException
  {
    boolean bool = super.save(paramString, paramBitmap);
    rememberUsage(paramString);
    return bool;
  }

  public boolean save(String paramString, InputStream paramInputStream, IoUtils.CopyListener paramCopyListener)
    throws IOException
  {
    boolean bool = super.save(paramString, paramInputStream, paramCopyListener);
    rememberUsage(paramString);
    return bool;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiscCache
 * JD-Core Version:    0.6.2
 */