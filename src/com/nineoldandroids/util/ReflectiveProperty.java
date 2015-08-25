package com.nineoldandroids.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class ReflectiveProperty<T, V> extends Property<T, V>
{
  private static final String PREFIX_GET = "get";
  private static final String PREFIX_IS = "is";
  private static final String PREFIX_SET = "set";
  private Field mField;
  private Method mGetter;
  private Method mSetter;

  public ReflectiveProperty(Class<T> paramClass, Class<V> paramClass1, String paramString)
  {
    super(paramClass1, paramString);
    char c = Character.toUpperCase(paramString.charAt(0));
    String str1 = paramString.substring(1);
    String str2 = c + str1;
    String str3 = "get" + str2;
    try
    {
      this.mGetter = paramClass.getMethod(str3, (Class[])null);
      localClass2 = this.mGetter.getReturnType();
      if (!typesMatch(paramClass1, localClass2))
        throw new NoSuchPropertyException("Underlying type (" + localClass2 + ") " + "does not match Property type (" + paramClass1 + ")");
    }
    catch (NoSuchMethodException localNoSuchMethodException1)
    {
      Class localClass2;
      try
      {
        this.mGetter = paramClass.getDeclaredMethod(str3, (Class[])null);
        this.mGetter.setAccessible(true);
      }
      catch (NoSuchMethodException localNoSuchMethodException2)
      {
        while (true)
        {
          String str4 = "is" + str2;
          try
          {
            this.mGetter = paramClass.getMethod(str4, (Class[])null);
          }
          catch (NoSuchMethodException localNoSuchMethodException3)
          {
            try
            {
              this.mGetter = paramClass.getDeclaredMethod(str4, (Class[])null);
              this.mGetter.setAccessible(true);
            }
            catch (NoSuchMethodException localNoSuchMethodException4)
            {
              try
              {
                this.mField = paramClass.getField(paramString);
                Class localClass1 = this.mField.getType();
                if (typesMatch(paramClass1, localClass1))
                  break label397;
                throw new NoSuchPropertyException("Underlying type (" + localClass1 + ") " + "does not match Property type (" + paramClass1 + ")");
              }
              catch (NoSuchFieldException localNoSuchFieldException)
              {
                throw new NoSuchPropertyException("No accessor method or field found for property with name " + paramString);
              }
            }
          }
        }
      }
      String str5 = "set" + str2;
      try
      {
        this.mSetter = paramClass.getDeclaredMethod(str5, new Class[] { localClass2 });
        this.mSetter.setAccessible(true);
        label397: return;
      }
      catch (NoSuchMethodException localNoSuchMethodException5)
      {
      }
    }
  }

  private boolean typesMatch(Class<V> paramClass, Class paramClass1)
  {
    if (paramClass1 != paramClass)
    {
      boolean bool1 = paramClass1.isPrimitive();
      boolean bool2 = false;
      if (bool1)
        if (((paramClass1 != Float.TYPE) || (paramClass != Float.class)) && ((paramClass1 != Integer.TYPE) || (paramClass != Integer.class)) && ((paramClass1 != Boolean.TYPE) || (paramClass != Boolean.class)) && ((paramClass1 != Long.TYPE) || (paramClass != Long.class)) && ((paramClass1 != Double.TYPE) || (paramClass != Double.class)) && ((paramClass1 != Short.TYPE) || (paramClass != Short.class)) && ((paramClass1 != Byte.TYPE) || (paramClass != Byte.class)))
        {
          Class localClass = Character.TYPE;
          bool2 = false;
          if (paramClass1 == localClass)
          {
            bool2 = false;
            if (paramClass != Character.class);
          }
        }
        else
        {
          bool2 = true;
        }
      return bool2;
    }
    return true;
  }

  public V get(T paramT)
  {
    if (this.mGetter != null)
      try
      {
        Object localObject2 = this.mGetter.invoke(paramT, (Object[])null);
        return localObject2;
      }
      catch (IllegalAccessException localIllegalAccessException2)
      {
        throw new AssertionError();
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException(localInvocationTargetException.getCause());
      }
    if (this.mField != null)
      try
      {
        Object localObject1 = this.mField.get(paramT);
        return localObject1;
      }
      catch (IllegalAccessException localIllegalAccessException1)
      {
        throw new AssertionError();
      }
    throw new AssertionError();
  }

  public boolean isReadOnly()
  {
    return (this.mSetter == null) && (this.mField == null);
  }

  public void set(T paramT, V paramV)
  {
    if (this.mSetter != null)
      try
      {
        this.mSetter.invoke(paramT, new Object[] { paramV });
        return;
      }
      catch (IllegalAccessException localIllegalAccessException2)
      {
        throw new AssertionError();
      }
      catch (InvocationTargetException localInvocationTargetException)
      {
        throw new RuntimeException(localInvocationTargetException.getCause());
      }
    if (this.mField != null)
      try
      {
        this.mField.set(paramT, paramV);
        return;
      }
      catch (IllegalAccessException localIllegalAccessException1)
      {
        throw new AssertionError();
      }
    throw new UnsupportedOperationException("Property " + getName() + " is read-only");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nineoldandroids.util.ReflectiveProperty
 * JD-Core Version:    0.6.2
 */