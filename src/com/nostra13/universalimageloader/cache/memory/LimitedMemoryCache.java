package com.nostra13.universalimageloader.cache.memory;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.utils.L;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class LimitedMemoryCache extends BaseMemoryCache
{
  private static final int MAX_NORMAL_CACHE_SIZE = 16777216;
  private static final int MAX_NORMAL_CACHE_SIZE_IN_MB = 16;
  private final AtomicInteger cacheSize;
  private final List<Bitmap> hardCache = Collections.synchronizedList(new LinkedList());
  private final int sizeLimit;

  public LimitedMemoryCache(int paramInt)
  {
    this.sizeLimit = paramInt;
    this.cacheSize = new AtomicInteger();
    if (paramInt > 16777216)
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Integer.valueOf(16);
      L.w("You set too large memory cache size (more than %1$d Mb)", arrayOfObject);
    }
  }

  public void clear()
  {
    this.hardCache.clear();
    this.cacheSize.set(0);
    super.clear();
  }

  protected abstract int getSize(Bitmap paramBitmap);

  protected int getSizeLimit()
  {
    return this.sizeLimit;
  }

  public boolean put(String paramString, Bitmap paramBitmap)
  {
    int i = getSize(paramBitmap);
    int j = getSizeLimit();
    int k = this.cacheSize.get();
    boolean bool = false;
    if (i < j)
    {
      while (k + i > j)
      {
        Bitmap localBitmap = removeNext();
        if (this.hardCache.remove(localBitmap))
          k = this.cacheSize.addAndGet(-getSize(localBitmap));
      }
      this.hardCache.add(paramBitmap);
      this.cacheSize.addAndGet(i);
      bool = true;
    }
    super.put(paramString, paramBitmap);
    return bool;
  }

  public Bitmap remove(String paramString)
  {
    Bitmap localBitmap = super.get(paramString);
    if ((localBitmap != null) && (this.hardCache.remove(localBitmap)))
      this.cacheSize.addAndGet(-getSize(localBitmap));
    return super.remove(paramString);
  }

  protected abstract Bitmap removeNext();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.memory.LimitedMemoryCache
 * JD-Core Version:    0.6.2
 */