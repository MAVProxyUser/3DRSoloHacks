package io.fabric.sdk.android.services.persistence;

public abstract interface SerializationStrategy<T>
{
  public abstract T deserialize(String paramString);

  public abstract String serialize(T paramT);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.persistence.SerializationStrategy
 * JD-Core Version:    0.6.2
 */