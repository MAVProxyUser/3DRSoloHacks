package io.fabric.sdk.android.services.events;

import java.io.IOException;

public abstract interface EventTransform<T>
{
  public abstract byte[] toBytes(T paramT)
    throws IOException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.EventTransform
 * JD-Core Version:    0.6.2
 */