package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract class AbstractValueCache<T>
  implements ValueCache<T>
{
  private final ValueCache<T> childCache;

  public AbstractValueCache()
  {
    this(null);
  }

  public AbstractValueCache(ValueCache<T> paramValueCache)
  {
    this.childCache = paramValueCache;
  }

  private void cache(Context paramContext, T paramT)
  {
    if (paramT == null)
      throw new NullPointerException();
    cacheValue(paramContext, paramT);
  }

  protected abstract void cacheValue(Context paramContext, T paramT);

  protected abstract void doInvalidate(Context paramContext);

  public final T get(Context paramContext, ValueLoader<T> paramValueLoader)
    throws Exception
  {
    try
    {
      Object localObject2 = getCached(paramContext);
      if (localObject2 == null)
        if (this.childCache == null)
          break label46;
      label46: Object localObject3;
      for (localObject2 = this.childCache.get(paramContext, paramValueLoader); ; localObject2 = localObject3)
      {
        cache(paramContext, localObject2);
        return localObject2;
        localObject3 = paramValueLoader.load(paramContext);
      }
    }
    finally
    {
    }
  }

  protected abstract T getCached(Context paramContext);

  public final void invalidate(Context paramContext)
  {
    try
    {
      doInvalidate(paramContext);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.cache.AbstractValueCache
 * JD-Core Version:    0.6.2
 */