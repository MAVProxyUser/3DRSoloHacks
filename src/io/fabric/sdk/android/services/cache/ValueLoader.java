package io.fabric.sdk.android.services.cache;

import android.content.Context;

public abstract interface ValueLoader<T>
{
  public abstract T load(Context paramContext)
    throws Exception;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.cache.ValueLoader
 * JD-Core Version:    0.6.2
 */