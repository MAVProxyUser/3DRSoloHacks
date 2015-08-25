package io.fabric.sdk.android.services.concurrency;

import java.util.concurrent.Callable;

public abstract class PriorityCallable<V> extends PriorityTask
  implements Callable<V>
{
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.PriorityCallable
 * JD-Core Version:    0.6.2
 */