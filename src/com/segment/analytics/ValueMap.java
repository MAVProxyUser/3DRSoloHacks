package com.segment.analytics;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.segment.analytics.internal.Utils;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public class ValueMap
  implements Map<String, Object>
{
  private final Map<String, Object> delegate;

  public ValueMap()
  {
    this.delegate = new LinkedHashMap();
  }

  public ValueMap(Map<String, Object> paramMap)
  {
    if (paramMap == null)
      throw new IllegalArgumentException("Map must not be null.");
    this.delegate = paramMap;
  }

  private <T extends ValueMap> T coerceToValueMap(Object paramObject, Class<T> paramClass)
  {
    if (paramObject == null)
      return null;
    if (paramClass.isAssignableFrom(paramObject.getClass()))
      return (ValueMap)paramObject;
    if ((paramObject instanceof Map))
      return createValueMap((Map)paramObject, paramClass);
    return null;
  }

  static <T extends ValueMap> T createValueMap(Map paramMap, Class<T> paramClass)
  {
    try
    {
      Constructor localConstructor = paramClass.getDeclaredConstructor(new Class[] { Map.class });
      localConstructor.setAccessible(true);
      ValueMap localValueMap = (ValueMap)localConstructor.newInstance(new Object[] { paramMap });
      return localValueMap;
    }
    catch (Exception localException)
    {
      throw new AssertionError("Could not create instance of " + paramClass.getCanonicalName() + ".\n" + localException);
    }
  }

  public void clear()
  {
    this.delegate.clear();
  }

  public boolean containsKey(Object paramObject)
  {
    return this.delegate.containsKey(paramObject);
  }

  public boolean containsValue(Object paramObject)
  {
    return this.delegate.containsValue(paramObject);
  }

  public Set<Map.Entry<String, Object>> entrySet()
  {
    return this.delegate.entrySet();
  }

  public boolean equals(Object paramObject)
  {
    return (paramObject == this) || (this.delegate.equals(paramObject));
  }

  public Object get(Object paramObject)
  {
    return this.delegate.get(paramObject);
  }

  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    Object localObject = get(paramString);
    if ((localObject instanceof Boolean))
      paramBoolean = ((Boolean)localObject).booleanValue();
    while (!(localObject instanceof String))
      return paramBoolean;
    return Boolean.valueOf((String)localObject).booleanValue();
  }

  public char getChar(String paramString, char paramChar)
  {
    Object localObject = get(paramString);
    if ((localObject instanceof Character))
      paramChar = ((Character)localObject).charValue();
    while ((localObject == null) || (!(localObject instanceof String)) || (((String)localObject).length() != 1))
      return paramChar;
    return ((String)localObject).charAt(0);
  }

  public double getDouble(String paramString, double paramDouble)
  {
    Object localObject = get(paramString);
    if ((localObject instanceof Double))
      paramDouble = ((Double)localObject).doubleValue();
    do
    {
      return paramDouble;
      if ((localObject instanceof Number))
        return ((Number)localObject).doubleValue();
    }
    while (!(localObject instanceof String));
    try
    {
      double d = Double.valueOf((String)localObject).doubleValue();
      return d;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return paramDouble;
  }

  public <T extends Enum<T>> T getEnum(Class<T> paramClass, String paramString)
  {
    if (paramClass == null)
      throw new IllegalArgumentException("enumType may not be null");
    Object localObject = get(paramString);
    if (paramClass.isInstance(localObject))
      return (Enum)localObject;
    if ((localObject instanceof String))
      return Enum.valueOf(paramClass, (String)localObject);
    return null;
  }

  public int getInt(String paramString, int paramInt)
  {
    Object localObject = get(paramString);
    if ((localObject instanceof Integer))
      paramInt = ((Integer)localObject).intValue();
    do
    {
      return paramInt;
      if ((localObject instanceof Number))
        return ((Number)localObject).intValue();
    }
    while (!(localObject instanceof String));
    try
    {
      int i = Integer.valueOf((String)localObject).intValue();
      return i;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return paramInt;
  }

  public <T extends ValueMap> List<T> getList(Object paramObject, Class<T> paramClass)
  {
    Object localObject = get(paramObject);
    ArrayList localArrayList;
    if ((localObject instanceof List))
    {
      List localList = (List)localObject;
      try
      {
        localArrayList = new ArrayList();
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
        {
          ValueMap localValueMap = coerceToValueMap(localIterator.next(), paramClass);
          if (localValueMap != null)
            localArrayList.add(localValueMap);
        }
      }
      catch (Exception localException)
      {
      }
    }
    else
    {
      localArrayList = null;
    }
    return localArrayList;
  }

  public long getLong(String paramString, long paramLong)
  {
    Object localObject = get(paramString);
    if ((localObject instanceof Long))
      paramLong = ((Long)localObject).longValue();
    do
    {
      return paramLong;
      if ((localObject instanceof Number))
        return ((Number)localObject).longValue();
    }
    while (!(localObject instanceof String));
    try
    {
      long l = Long.valueOf((String)localObject).longValue();
      return l;
    }
    catch (NumberFormatException localNumberFormatException)
    {
    }
    return paramLong;
  }

  public String getString(String paramString)
  {
    Object localObject = get(paramString);
    if ((localObject instanceof String))
      return (String)localObject;
    if (localObject != null)
      return String.valueOf(localObject);
    return null;
  }

  public ValueMap getValueMap(Object paramObject)
  {
    Object localObject = get(paramObject);
    if ((localObject instanceof ValueMap))
      return (ValueMap)localObject;
    if ((localObject instanceof Map))
      return new ValueMap((Map)localObject);
    return null;
  }

  public <T extends ValueMap> T getValueMap(String paramString, Class<T> paramClass)
  {
    return coerceToValueMap(get(paramString), paramClass);
  }

  public int hashCode()
  {
    return this.delegate.hashCode();
  }

  public boolean isEmpty()
  {
    return this.delegate.isEmpty();
  }

  public Set<String> keySet()
  {
    return this.delegate.keySet();
  }

  public Object put(String paramString, Object paramObject)
  {
    return this.delegate.put(paramString, paramObject);
  }

  public void putAll(Map<? extends String, ?> paramMap)
  {
    this.delegate.putAll(paramMap);
  }

  public ValueMap putValue(String paramString, Object paramObject)
  {
    this.delegate.put(paramString, paramObject);
    return this;
  }

  public Object remove(Object paramObject)
  {
    return this.delegate.remove(paramObject);
  }

  public int size()
  {
    return this.delegate.size();
  }

  public JSONObject toJsonObject()
  {
    return new JSONObject(this.delegate);
  }

  public String toString()
  {
    return this.delegate.toString();
  }

  public Map<String, String> toStringMap()
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHashMap.put(localEntry.getKey(), String.valueOf(localEntry.getValue()));
    }
    return localHashMap;
  }

  public Collection<Object> values()
  {
    return this.delegate.values();
  }

  static class Cache<T extends ValueMap>
  {
    private final Cartographer cartographer;
    private final Class<T> clazz;
    private final String key;
    private final SharedPreferences preferences;
    private T value;

    Cache(Context paramContext, Cartographer paramCartographer, String paramString, Class<T> paramClass)
    {
      this.cartographer = paramCartographer;
      this.preferences = Utils.getSegmentSharedPreferences(paramContext);
      this.key = paramString;
      this.clazz = paramClass;
    }

    T create(Map<String, Object> paramMap)
    {
      return ValueMap.createValueMap(paramMap, this.clazz);
    }

    void delete()
    {
      this.preferences.edit().remove(this.key).apply();
    }

    T get()
    {
      String str;
      if (this.value == null)
      {
        str = this.preferences.getString(this.key, null);
        if (Utils.isNullOrEmpty(str))
          return null;
      }
      try
      {
        this.value = create(this.cartographer.fromJson(str));
        return this.value;
      }
      catch (IOException localIOException)
      {
      }
      return null;
    }

    boolean isSet()
    {
      return this.preferences.contains(this.key);
    }

    void set(T paramT)
    {
      this.value = paramT;
      try
      {
        String str = this.cartographer.toJson(paramT);
        this.preferences.edit().putString(this.key, str).apply();
        return;
      }
      catch (IOException localIOException)
      {
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.ValueMap
 * JD-Core Version:    0.6.2
 */