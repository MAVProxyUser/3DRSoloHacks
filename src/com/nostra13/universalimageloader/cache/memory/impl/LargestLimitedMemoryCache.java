package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.LimitedMemoryCache;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LargestLimitedMemoryCache extends LimitedMemoryCache
{
  private final Map<Bitmap, Integer> valueSizes = Collections.synchronizedMap(new HashMap());

  public LargestLimitedMemoryCache(int paramInt)
  {
    super(paramInt);
  }

  public void clear()
  {
    this.valueSizes.clear();
    super.clear();
  }

  protected Reference<Bitmap> createReference(Bitmap paramBitmap)
  {
    return new WeakReference(paramBitmap);
  }

  protected int getSize(Bitmap paramBitmap)
  {
    return paramBitmap.getRowBytes() * paramBitmap.getHeight();
  }

  public boolean put(String paramString, Bitmap paramBitmap)
  {
    if (super.put(paramString, paramBitmap))
    {
      this.valueSizes.put(paramBitmap, Integer.valueOf(getSize(paramBitmap)));
      return true;
    }
    return false;
  }

  public Bitmap remove(String paramString)
  {
    Bitmap localBitmap = super.get(paramString);
    if (localBitmap != null)
      this.valueSizes.remove(localBitmap);
    return super.remove(paramString);
  }

  protected Bitmap removeNext()
  {
    Object localObject1 = null;
    Bitmap localBitmap = null;
    Set localSet = this.valueSizes.entrySet();
    synchronized (this.valueSizes)
    {
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        if (localBitmap == null)
        {
          localBitmap = (Bitmap)localEntry.getKey();
          localObject1 = (Integer)localEntry.getValue();
        }
        else
        {
          Integer localInteger = (Integer)localEntry.getValue();
          if (localInteger.intValue() > ((Integer)localObject1).intValue())
          {
            localObject1 = localInteger;
            localBitmap = (Bitmap)localEntry.getKey();
          }
        }
      }
      this.valueSizes.remove(localBitmap);
      return localBitmap;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.memory.impl.LargestLimitedMemoryCache
 * JD-Core Version:    0.6.2
 */