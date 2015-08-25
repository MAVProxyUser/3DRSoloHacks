package io.fabric.sdk.android.services.events;

import java.io.IOException;

public abstract interface FileRollOverManager
{
  public abstract void cancelTimeBasedFileRollOver();

  public abstract boolean rollFileOver()
    throws IOException;

  public abstract void scheduleTimeBasedRollOverIfNeeded();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.events.FileRollOverManager
 * JD-Core Version:    0.6.2
 */