package com.google.android.gms.common.server.response;

import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzhr;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzia;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class FastJsonResponse
{
  private void zza(StringBuilder paramStringBuilder, FastJsonResponse.Field paramField, Object paramObject)
  {
    if (paramField.zzmw() == 11)
    {
      paramStringBuilder.append(((FastJsonResponse)paramField.zzmG().cast(paramObject)).toString());
      return;
    }
    if (paramField.zzmw() == 7)
    {
      paramStringBuilder.append("\"");
      paramStringBuilder.append(zzhz.zzbY((String)paramObject));
      paramStringBuilder.append("\"");
      return;
    }
    paramStringBuilder.append(paramObject);
  }

  private void zza(StringBuilder paramStringBuilder, FastJsonResponse.Field paramField, ArrayList<Object> paramArrayList)
  {
    paramStringBuilder.append("[");
    int i = 0;
    int j = paramArrayList.size();
    while (i < j)
    {
      if (i > 0)
        paramStringBuilder.append(",");
      Object localObject = paramArrayList.get(i);
      if (localObject != null)
        zza(paramStringBuilder, paramField, localObject);
      i++;
    }
    paramStringBuilder.append("]");
  }

  public String toString()
  {
    Map localMap = zzmy();
    StringBuilder localStringBuilder = new StringBuilder(100);
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      FastJsonResponse.Field localField = (FastJsonResponse.Field)localMap.get(str);
      if (zza(localField))
      {
        Object localObject = zza(localField, zzb(localField));
        if (localStringBuilder.length() == 0)
          localStringBuilder.append("{");
        while (true)
        {
          localStringBuilder.append("\"").append(str).append("\":");
          if (localObject != null)
            break label139;
          localStringBuilder.append("null");
          break;
          localStringBuilder.append(",");
        }
        label139: switch (localField.zzmx())
        {
        default:
          if (localField.zzmC())
            zza(localStringBuilder, localField, (ArrayList)localObject);
          break;
        case 8:
          localStringBuilder.append("\"").append(zzhr.zzg((byte[])localObject)).append("\"");
          break;
        case 9:
          localStringBuilder.append("\"").append(zzhr.zzh((byte[])localObject)).append("\"");
          break;
        case 10:
          zzia.zza(localStringBuilder, (HashMap)localObject);
          continue;
          zza(localStringBuilder, localField, localObject);
        }
      }
    }
    if (localStringBuilder.length() > 0)
      localStringBuilder.append("}");
    while (true)
    {
      return localStringBuilder.toString();
      localStringBuilder.append("{}");
    }
  }

  protected <O, I> I zza(FastJsonResponse.Field<I, O> paramField, Object paramObject)
  {
    if (FastJsonResponse.Field.zzc(paramField) != null)
      paramObject = paramField.convertBack(paramObject);
    return paramObject;
  }

  protected boolean zza(FastJsonResponse.Field paramField)
  {
    if (paramField.zzmx() == 11)
    {
      if (paramField.zzmD())
        return zzbW(paramField.zzmE());
      return zzbV(paramField.zzmE());
    }
    return zzbU(paramField.zzmE());
  }

  protected Object zzb(FastJsonResponse.Field paramField)
  {
    String str1 = paramField.zzmE();
    if (paramField.zzmG() != null)
    {
      boolean bool;
      if (zzbT(paramField.zzmE()) == null)
      {
        bool = true;
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = paramField.zzmE();
        zzv.zza(bool, "Concrete field shouldn't be value object: %s", arrayOfObject);
        if (!paramField.zzmD())
          break label79;
      }
      label79: for (HashMap localHashMap = zzmA(); ; localHashMap = zzmz())
      {
        if (localHashMap == null)
          break label88;
        return localHashMap.get(str1);
        bool = false;
        break;
      }
      try
      {
        label88: String str2 = "get" + Character.toUpperCase(str1.charAt(0)) + str1.substring(1);
        Object localObject = getClass().getMethod(str2, new Class[0]).invoke(this, new Object[0]);
        return localObject;
      }
      catch (Exception localException)
      {
        throw new RuntimeException(localException);
      }
    }
    return zzbT(paramField.zzmE());
  }

  protected abstract Object zzbT(String paramString);

  protected abstract boolean zzbU(String paramString);

  protected boolean zzbV(String paramString)
  {
    throw new UnsupportedOperationException("Concrete types not supported");
  }

  protected boolean zzbW(String paramString)
  {
    throw new UnsupportedOperationException("Concrete type arrays not supported");
  }

  public HashMap<String, Object> zzmA()
  {
    return null;
  }

  public abstract Map<String, FastJsonResponse.Field<?, ?>> zzmy();

  public HashMap<String, Object> zzmz()
  {
    return null;
  }

  public static abstract interface zza<I, O>
  {
    public abstract I convertBack(O paramO);

    public abstract int zzmw();

    public abstract int zzmx();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.response.FastJsonResponse
 * JD-Core Version:    0.6.2
 */