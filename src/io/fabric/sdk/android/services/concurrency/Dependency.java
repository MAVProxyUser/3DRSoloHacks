package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;

public abstract interface Dependency<T>
{
  public abstract void addDependency(T paramT);

  public abstract boolean areDependenciesMet();

  public abstract Collection<T> getDependencies();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.Dependency
 * JD-Core Version:    0.6.2
 */