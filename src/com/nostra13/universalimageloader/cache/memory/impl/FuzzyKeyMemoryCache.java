package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.MemoryCache;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class FuzzyKeyMemoryCache
  implements MemoryCache
{
  private final MemoryCache cache;
  private final Comparator<String> keyComparator;

  public FuzzyKeyMemoryCache(MemoryCache paramMemoryCache, Comparator<String> paramComparator)
  {
    this.cache = paramMemoryCache;
    this.keyComparator = paramComparator;
  }

  public void clear()
  {
    this.cache.clear();
  }

  public Bitmap get(String paramString)
  {
    return (Bitmap)this.cache.get(paramString);
  }

  public Collection<String> keys()
  {
    return this.cache.keys();
  }

  public boolean put(String paramString, Bitmap paramBitmap)
  {
    synchronized (this.cache)
    {
      Iterator localIterator = this.cache.keys().iterator();
      String str;
      do
      {
        boolean bool = localIterator.hasNext();
        localObject2 = null;
        if (!bool)
          break;
        str = (String)localIterator.next();
      }
      while (this.keyComparator.compare(paramString, str) != 0);
      Object localObject2 = str;
      if (localObject2 != null)
        this.cache.remove(localObject2);
      return this.cache.put(paramString, paramBitmap);
    }
  }

  public Bitmap remove(String paramString)
  {
    return (Bitmap)this.cache.remove(paramString);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.memory.impl.FuzzyKeyMemoryCache
 * JD-Core Version:    0.6.2
 */