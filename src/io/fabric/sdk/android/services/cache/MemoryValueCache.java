package io.fabric.sdk.android.services.cache;

import android.content.Context;

public class MemoryValueCache<T> extends AbstractValueCache<T>
{
  private T value;

  public MemoryValueCache()
  {
    this(null);
  }

  public MemoryValueCache(ValueCache<T> paramValueCache)
  {
    super(paramValueCache);
  }

  protected void cacheValue(Context paramContext, T paramT)
  {
    this.value = paramT;
  }

  protected void doInvalidate(Context paramContext)
  {
    this.value = null;
  }

  protected T getCached(Context paramContext)
  {
    return this.value;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.cache.MemoryValueCache
 * JD-Core Version:    0.6.2
 */