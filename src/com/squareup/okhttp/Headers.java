package com.squareup.okhttp;

import com.squareup.okhttp.internal.http.HttpDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

public final class Headers
{
  private final String[] namesAndValues;

  private Headers(Builder paramBuilder)
  {
    this.namesAndValues = ((String[])paramBuilder.namesAndValues.toArray(new String[paramBuilder.namesAndValues.size()]));
  }

  private Headers(String[] paramArrayOfString)
  {
    this.namesAndValues = paramArrayOfString;
  }

  private static String get(String[] paramArrayOfString, String paramString)
  {
    for (int i = -2 + paramArrayOfString.length; i >= 0; i -= 2)
      if (paramString.equalsIgnoreCase(paramArrayOfString[i]))
        return paramArrayOfString[(i + 1)];
    return null;
  }

  public static Headers of(Map<String, String> paramMap)
  {
    if (paramMap == null)
      throw new IllegalArgumentException("Expected map with header names and values");
    String[] arrayOfString = new String[2 * paramMap.size()];
    int i = 0;
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if ((localEntry.getKey() == null) || (localEntry.getValue() == null))
        throw new IllegalArgumentException("Headers cannot be null");
      String str1 = ((String)localEntry.getKey()).trim();
      String str2 = ((String)localEntry.getValue()).trim();
      if ((str1.length() == 0) || (str1.indexOf(0) != -1) || (str2.indexOf(0) != -1))
        throw new IllegalArgumentException("Unexpected header: " + str1 + ": " + str2);
      arrayOfString[i] = str1;
      arrayOfString[(i + 1)] = str2;
      i += 2;
    }
    return new Headers(arrayOfString);
  }

  public static Headers of(String[] paramArrayOfString)
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length % 2 != 0))
      throw new IllegalArgumentException("Expected alternating header names and values");
    String[] arrayOfString = (String[])paramArrayOfString.clone();
    for (int i = 0; i < arrayOfString.length; i++)
    {
      if (arrayOfString[i] == null)
        throw new IllegalArgumentException("Headers cannot be null");
      arrayOfString[i] = arrayOfString[i].trim();
    }
    for (int j = 0; j < arrayOfString.length; j += 2)
    {
      String str1 = arrayOfString[j];
      String str2 = arrayOfString[(j + 1)];
      if ((str1.length() == 0) || (str1.indexOf(0) != -1) || (str2.indexOf(0) != -1))
        throw new IllegalArgumentException("Unexpected header: " + str1 + ": " + str2);
    }
    return new Headers(arrayOfString);
  }

  public String get(String paramString)
  {
    return get(this.namesAndValues, paramString);
  }

  public Date getDate(String paramString)
  {
    String str = get(paramString);
    if (str != null)
      return HttpDate.parse(str);
    return null;
  }

  public String name(int paramInt)
  {
    int i = paramInt * 2;
    if ((i < 0) || (i >= this.namesAndValues.length))
      return null;
    return this.namesAndValues[i];
  }

  public Set<String> names()
  {
    TreeSet localTreeSet = new TreeSet(String.CASE_INSENSITIVE_ORDER);
    int i = 0;
    int j = size();
    while (i < j)
    {
      localTreeSet.add(name(i));
      i++;
    }
    return Collections.unmodifiableSet(localTreeSet);
  }

  public Builder newBuilder()
  {
    Builder localBuilder = new Builder();
    Collections.addAll(localBuilder.namesAndValues, this.namesAndValues);
    return localBuilder;
  }

  public int size()
  {
    return this.namesAndValues.length / 2;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    int i = 0;
    int j = size();
    while (i < j)
    {
      localStringBuilder.append(name(i)).append(": ").append(value(i)).append("\n");
      i++;
    }
    return localStringBuilder.toString();
  }

  public String value(int paramInt)
  {
    int i = 1 + paramInt * 2;
    if ((i < 0) || (i >= this.namesAndValues.length))
      return null;
    return this.namesAndValues[i];
  }

  public List<String> values(String paramString)
  {
    ArrayList localArrayList = null;
    int i = 0;
    int j = size();
    while (i < j)
    {
      if (paramString.equalsIgnoreCase(name(i)))
      {
        if (localArrayList == null)
          localArrayList = new ArrayList(2);
        localArrayList.add(value(i));
      }
      i++;
    }
    if (localArrayList != null)
      return Collections.unmodifiableList(localArrayList);
    return Collections.emptyList();
  }

  public static final class Builder
  {
    private final List<String> namesAndValues = new ArrayList(20);

    private Builder addLenient(String paramString1, String paramString2)
    {
      this.namesAndValues.add(paramString1);
      this.namesAndValues.add(paramString2.trim());
      return this;
    }

    public Builder add(String paramString)
    {
      int i = paramString.indexOf(":");
      if (i == -1)
        throw new IllegalArgumentException("Unexpected header: " + paramString);
      return add(paramString.substring(0, i).trim(), paramString.substring(i + 1));
    }

    public Builder add(String paramString1, String paramString2)
    {
      if (paramString1 == null)
        throw new IllegalArgumentException("name == null");
      if (paramString2 == null)
        throw new IllegalArgumentException("value == null");
      if ((paramString1.length() == 0) || (paramString1.indexOf(0) != -1) || (paramString2.indexOf(0) != -1))
        throw new IllegalArgumentException("Unexpected header: " + paramString1 + ": " + paramString2);
      return addLenient(paramString1, paramString2);
    }

    Builder addLenient(String paramString)
    {
      int i = paramString.indexOf(":", 1);
      if (i != -1)
        return addLenient(paramString.substring(0, i), paramString.substring(i + 1));
      if (paramString.startsWith(":"))
        return addLenient("", paramString.substring(1));
      return addLenient("", paramString);
    }

    public Headers build()
    {
      return new Headers(this, null);
    }

    public String get(String paramString)
    {
      for (int i = -2 + this.namesAndValues.size(); i >= 0; i -= 2)
        if (paramString.equalsIgnoreCase((String)this.namesAndValues.get(i)))
          return (String)this.namesAndValues.get(i + 1);
      return null;
    }

    public Builder removeAll(String paramString)
    {
      for (int i = 0; i < this.namesAndValues.size(); i += 2)
        if (paramString.equalsIgnoreCase((String)this.namesAndValues.get(i)))
        {
          this.namesAndValues.remove(i);
          this.namesAndValues.remove(i);
          i -= 2;
        }
      return this;
    }

    public Builder set(String paramString1, String paramString2)
    {
      removeAll(paramString1);
      add(paramString1, paramString2);
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.Headers
 * JD-Core Version:    0.6.2
 */