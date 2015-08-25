package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract interface ValueCache<T>
{
  public abstract T get(Context paramContext, ValueLoader<T> paramValueLoader)
    throws Exception;

  public abstract void invalidate(Context paramContext);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.cache.ValueCache
 * JD-Core Version:    0.6.2
 */