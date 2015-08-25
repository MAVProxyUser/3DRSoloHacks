package com.nostra13.universalimageloader.cache.memory;

import android.graphics.Bitmap;
import java.lang.ref.Reference;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public abstract class BaseMemoryCache
  implements MemoryCache
{
  private final Map<String, Reference<Bitmap>> softMap = Collections.synchronizedMap(new HashMap());

  public void clear()
  {
    this.softMap.clear();
  }

  protected abstract Reference<Bitmap> createReference(Bitmap paramBitmap);

  public Bitmap get(String paramString)
  {
    Reference localReference = (Reference)this.softMap.get(paramString);
    Bitmap localBitmap = null;
    if (localReference != null)
      localBitmap = (Bitmap)localReference.get();
    return localBitmap;
  }

  public Collection<String> keys()
  {
    synchronized (this.softMap)
    {
      HashSet localHashSet = new HashSet(this.softMap.keySet());
      return localHashSet;
    }
  }

  public boolean put(String paramString, Bitmap paramBitmap)
  {
    this.softMap.put(paramString, createReference(paramBitmap));
    return true;
  }

  public Bitmap remove(String paramString)
  {
    Reference localReference = (Reference)this.softMap.remove(paramString);
    if (localReference == null)
      return null;
    return (Bitmap)localReference.get();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.cache.memory.BaseMemoryCache
 * JD-Core Version:    0.6.2
 */