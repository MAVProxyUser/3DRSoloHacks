package com.nineoldandroids.util;

public abstract class IntProperty<T> extends Property<T, Integer>
{
  public IntProperty(String paramString)
  {
    super(Integer.class, paramString);
  }

  public final void set(T paramT, Integer paramInteger)
  {
    set(paramT, Integer.valueOf(paramInteger.intValue()));
  }

  public abstract void setValue(T paramT, int paramInt);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.util.IntProperty
 * JD-Core Version:    0.6.2
 */