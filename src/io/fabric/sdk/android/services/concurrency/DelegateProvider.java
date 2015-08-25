package io.fabric.sdk.android.services.concurrency;

public abstract interface DelegateProvider
{
  public abstract <T extends Dependency<Task>,  extends PriorityProvider,  extends Task> T getDelegate();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.DelegateProvider
 * JD-Core Version:    0.6.2
 */