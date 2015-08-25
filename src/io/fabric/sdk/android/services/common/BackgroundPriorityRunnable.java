package io.fabric.sdk.android.services.common;

import android.os.Process;

public abstract class BackgroundPriorityRunnable
  implements Runnable
{
  protected abstract void onRun();

  public final void run()
  {
    Process.setThreadPriority(10);
    onRun();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.common.BackgroundPriorityRunnable
 * JD-Core Version:    0.6.2
 */