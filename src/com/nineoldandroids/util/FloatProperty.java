package com.nineoldandroids.util;

public abstract class FloatProperty<T> extends Property<T, Float>
{
  public FloatProperty(String paramString)
  {
    super(Float.class, paramString);
  }

  public final void set(T paramT, Float paramFloat)
  {
    setValue(paramT, paramFloat.floatValue());
  }

  public abstract void setValue(T paramT, float paramFloat);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.util.FloatProperty
 * JD-Core Version:    0.6.2
 */