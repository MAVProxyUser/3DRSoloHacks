package com.nineoldandroids.util;

public abstract class Property<T, V>
{
  private final String mName;
  private final Class<V> mType;

  public Property(Class<V> paramClass, String paramString)
  {
    this.mName = paramString;
    this.mType = paramClass;
  }

  public static <T, V> Property<T, V> of(Class<T> paramClass, Class<V> paramClass1, String paramString)
  {
    return new ReflectiveProperty(paramClass, paramClass1, paramString);
  }

  public abstract V get(T paramT);

  public String getName()
  {
    return this.mName;
  }

  public Class<V> getType()
  {
    return this.mType;
  }

  public boolean isReadOnly()
  {
    return false;
  }

  public void set(T paramT, V paramV)
  {
    throw new UnsupportedOperationException("Property " + getName() + " is read-only");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.util.Property
 * JD-Core Version:    0.6.2
 */