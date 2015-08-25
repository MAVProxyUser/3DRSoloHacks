package com.nostra13.universalimageloader.cache.disc.impl;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import com.nostra13.universalimageloader.cache.disc.DiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.FileNameGenerator;
import com.nostra13.universalimageloader.core.DefaultConfigurationFactory;
import com.nostra13.universalimageloader.utils.IoUtils;
import com.nostra13.universalimageloader.utils.IoUtils.CopyListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseDiscCache
  implements DiskCache
{
  public static final int DEFAULT_BUFFER_SIZE = 32768;
  public static final Bitmap.CompressFormat DEFAULT_COMPRESS_FORMAT = Bitmap.CompressFormat.PNG;
  public static final int DEFAULT_COMPRESS_QUALITY = 100;
  private static final String ERROR_ARG_NULL = " argument must be not null";
  private static final String TEMP_IMAGE_POSTFIX = ".tmp";
  protected int bufferSize = 32768;
  protected final File cacheDir;
  protected Bitmap.CompressFormat compressFormat = DEFAULT_COMPRESS_FORMAT;
  protected int compressQuality = 100;
  protected final FileNameGenerator fileNameGenerator;
  protected final File reserveCacheDir;

  public BaseDiscCache(File paramFile)
  {
    this(paramFile, null);
  }

  public BaseDiscCache(File paramFile1, File paramFile2)
  {
    this(paramFile1, paramFile2, DefaultConfigurationFactory.createFileNameGenerator());
  }

  public BaseDiscCache(File paramFile1, File paramFile2, FileNameGenerator paramFileNameGenerator)
  {
    if (paramFile1 == null)
      throw new IllegalArgumentException("cacheDir argument must be not null");
    if (paramFileNameGenerator == null)
      throw new IllegalArgumentException("fileNameGenerator argument must be not null");
    this.cacheDir = paramFile1;
    this.reserveCacheDir = paramFile2;
    this.fileNameGenerator = paramFileNameGenerator;
  }

  public void clear()
  {
    File[] arrayOfFile = this.cacheDir.listFiles();
    if (arrayOfFile != null)
    {
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
        arrayOfFile[j].delete();
    }
  }

  public void close()
  {
  }

  public File get(String paramString)
  {
    return getFile(paramString);
  }

  public File getDirectory()
  {
    return this.cacheDir;
  }

  protected File getFile(String paramString)
  {
    String str = this.fileNameGenerator.generate(paramString);
    File localFile = this.cacheDir;
    if ((!this.cacheDir.exists()) && (!this.cacheDir.mkdirs()) && (this.reserveCacheDir != null) && ((this.reserveCacheDir.exists()) || (this.reserveCacheDir.mkdirs())))
      localFile = this.reserveCacheDir;
    return new File(localFile, str);
  }

  public boolean remove(String paramString)
  {
    return getFile(paramString).delete();
  }

  public boolean save(String paramString, Bitmap paramBitmap)
    throws IOException
  {
    File localFile1 = getFile(paramString);
    File localFile2 = new File(localFile1.getAbsolutePath() + ".tmp");
    BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile2), this.bufferSize);
    try
    {
      boolean bool1 = paramBitmap.compress(this.compressFormat, this.compressQuality, localBufferedOutputStream);
      boolean bool2 = bool1;
      IoUtils.closeSilently(localBufferedOutputStream);
      if ((bool2) && (!localFile2.renameTo(localFile1)))
        bool2 = false;
      if (!bool2)
        localFile2.delete();
      paramBitmap.recycle();
      return bool2;
    }
    finally
    {
      IoUtils.closeSilently(localBufferedOutputStream);
      if (((0 == 0) || (localFile2.renameTo(localFile1))) || (0 == 0))
        localFile2.delete();
    }
  }

  public boolean save(String paramString, InputStream paramInputStream, IoUtils.CopyListener paramCopyListener)
    throws IOException
  {
    File localFile1 = getFile(paramString);
    File localFile2 = new File(localFile1.getAbsolutePath() + ".tmp");
    boolean bool1 = false;
    try
    {
      BufferedOutputStream localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile2), this.bufferSize);
      try
      {
        boolean bool2 = IoUtils.copyStream(paramInputStream, localBufferedOutputStream, paramCopyListener, this.bufferSize);
        bool1 = bool2;
        IoUtils.closeSilently(localBufferedOutputStream);
        IoUtils.closeSilently(paramInputStream);
        if ((bool1) && (!localFile2.renameTo(localFile1)))
          bool1 = false;
        if (!bool1)
          localFile2.delete();
        return bool1;
      }
      finally
      {
        IoUtils.closeSilently(localBufferedOutputStream);
      }
    }
    finally
    {
      IoUtils.closeSilently(paramInputStream);
      if ((bool1) && (!localFile2.renameTo(localFile1)))
        bool1 = false;
      if (!bool1)
        localFile2.delete();
    }
  }

  public void setBufferSize(int paramInt)
  {
    this.bufferSize = paramInt;
  }

  public void setCompressFormat(Bitmap.CompressFormat paramCompressFormat)
  {
    this.compressFormat = paramCompressFormat;
  }

  public void setCompressQuality(int paramInt)
  {
    this.compressQuality = paramInt;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.disc.impl.BaseDiscCache
 * JD-Core Version:    0.6.2
 */