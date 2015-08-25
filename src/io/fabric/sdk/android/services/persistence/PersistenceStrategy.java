package io.fabric.sdk.android.services.persistence;

public abstract interface PersistenceStrategy<T>
{
  public abstract void clear();

  public abstract T restore();

  public abstract void save(T paramT);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.persistence.PersistenceStrategy
 * JD-Core Version:    0.6.2
 */