package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class FieldMappingDictionary
  implements SafeParcelable
{
  public static final zzc CREATOR = new zzc();
  private final HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zzUI;
  private final ArrayList<Entry> zzUJ;
  private final String zzUK;
  private final int zzzH;

  FieldMappingDictionary(int paramInt, ArrayList<Entry> paramArrayList, String paramString)
  {
    this.zzzH = paramInt;
    this.zzUJ = null;
    this.zzUI = zzc(paramArrayList);
    this.zzUK = ((String)zzv.zzr(paramString));
    zzmL();
  }

  public FieldMappingDictionary(Class<? extends FastJsonResponse> paramClass)
  {
    this.zzzH = 1;
    this.zzUJ = null;
    this.zzUI = new HashMap();
    this.zzUK = paramClass.getCanonicalName();
  }

  private static HashMap<String, Map<String, FastJsonResponse.Field<?, ?>>> zzc(ArrayList<Entry> paramArrayList)
  {
    HashMap localHashMap = new HashMap();
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++)
    {
      Entry localEntry = (Entry)paramArrayList.get(j);
      localHashMap.put(localEntry.className, localEntry.zzmP());
    }
    return localHashMap;
  }

  public int describeContents()
  {
    return 0;
  }

  int getVersionCode()
  {
    return this.zzzH;
  }

  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    Iterator localIterator1 = this.zzUI.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      localStringBuilder.append(str1).append(":\n");
      Map localMap = (Map)this.zzUI.get(str1);
      Iterator localIterator2 = localMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        localStringBuilder.append("  ").append(str2).append(": ");
        localStringBuilder.append(localMap.get(str2));
      }
    }
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zzc.zza(this, paramParcel, paramInt);
  }

  public void zza(Class<? extends FastJsonResponse> paramClass, Map<String, FastJsonResponse.Field<?, ?>> paramMap)
  {
    this.zzUI.put(paramClass.getCanonicalName(), paramMap);
  }

  public boolean zzb(Class<? extends FastJsonResponse> paramClass)
  {
    return this.zzUI.containsKey(paramClass.getCanonicalName());
  }

  public Map<String, FastJsonResponse.Field<?, ?>> zzbX(String paramString)
  {
    return (Map)this.zzUI.get(paramString);
  }

  public void zzmL()
  {
    Iterator localIterator1 = this.zzUI.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str = (String)localIterator1.next();
      Map localMap = (Map)this.zzUI.get(str);
      Iterator localIterator2 = localMap.keySet().iterator();
      while (localIterator2.hasNext())
        ((FastJsonResponse.Field)localMap.get((String)localIterator2.next())).zza(this);
    }
  }

  public void zzmM()
  {
    Iterator localIterator1 = this.zzUI.keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      Map localMap = (Map)this.zzUI.get(str1);
      HashMap localHashMap = new HashMap();
      Iterator localIterator2 = localMap.keySet().iterator();
      while (localIterator2.hasNext())
      {
        String str2 = (String)localIterator2.next();
        localHashMap.put(str2, ((FastJsonResponse.Field)localMap.get(str2)).zzmB());
      }
      this.zzUI.put(str1, localHashMap);
    }
  }

  ArrayList<Entry> zzmN()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.zzUI.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localArrayList.add(new Entry(str, (Map)this.zzUI.get(str)));
    }
    return localArrayList;
  }

  public String zzmO()
  {
    return this.zzUK;
  }

  public static class Entry
    implements SafeParcelable
  {
    public static final zzd CREATOR = new zzd();
    final String className;
    final int versionCode;
    final ArrayList<FieldMappingDictionary.FieldMapPair> zzUL;

    Entry(int paramInt, String paramString, ArrayList<FieldMappingDictionary.FieldMapPair> paramArrayList)
    {
      this.versionCode = paramInt;
      this.className = paramString;
      this.zzUL = paramArrayList;
    }

    Entry(String paramString, Map<String, FastJsonResponse.Field<?, ?>> paramMap)
    {
      this.versionCode = 1;
      this.className = paramString;
      this.zzUL = zzA(paramMap);
    }

    private static ArrayList<FieldMappingDictionary.FieldMapPair> zzA(Map<String, FastJsonResponse.Field<?, ?>> paramMap)
    {
      if (paramMap == null)
        return null;
      ArrayList localArrayList = new ArrayList();
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localArrayList.add(new FieldMappingDictionary.FieldMapPair(str, (FastJsonResponse.Field)paramMap.get(str)));
      }
      return localArrayList;
    }

    public int describeContents()
    {
      return 0;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzd.zza(this, paramParcel, paramInt);
    }

    HashMap<String, FastJsonResponse.Field<?, ?>> zzmP()
    {
      HashMap localHashMap = new HashMap();
      int i = this.zzUL.size();
      for (int j = 0; j < i; j++)
      {
        FieldMappingDictionary.FieldMapPair localFieldMapPair = (FieldMappingDictionary.FieldMapPair)this.zzUL.get(j);
        localHashMap.put(localFieldMapPair.zzgk, localFieldMapPair.zzUM);
      }
      return localHashMap;
    }
  }

  public static class FieldMapPair
    implements SafeParcelable
  {
    public static final zzb CREATOR = new zzb();
    final int versionCode;
    final FastJsonResponse.Field<?, ?> zzUM;
    final String zzgk;

    FieldMapPair(int paramInt, String paramString, FastJsonResponse.Field<?, ?> paramField)
    {
      this.versionCode = paramInt;
      this.zzgk = paramString;
      this.zzUM = paramField;
    }

    FieldMapPair(String paramString, FastJsonResponse.Field<?, ?> paramField)
    {
      this.versionCode = 1;
      this.zzgk = paramString;
      this.zzUM = paramField;
    }

    public int describeContents()
    {
      return 0;
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zzb.zza(this, paramParcel, paramInt);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.response.FieldMappingDictionary
 * JD-Core Version:    0.6.2
 */