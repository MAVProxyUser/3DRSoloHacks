package com.google.android.gms.common.server.response;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.server.converter.ConverterWrapper;
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
  private void zza(StringBuilder paramStringBuilder, Field paramField, Object paramObject)
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

  private void zza(StringBuilder paramStringBuilder, Field paramField, ArrayList<Object> paramArrayList)
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
      Field localField = (Field)localMap.get(str);
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

  protected <O, I> I zza(Field<I, O> paramField, Object paramObject)
  {
    if (Field.zzc(paramField) != null)
      paramObject = paramField.convertBack(paramObject);
    return paramObject;
  }

  protected boolean zza(Field paramField)
  {
    if (paramField.zzmx() == 11)
    {
      if (paramField.zzmD())
        return zzbW(paramField.zzmE());
      return zzbV(paramField.zzmE());
    }
    return zzbU(paramField.zzmE());
  }

  protected Object zzb(Field paramField)
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

  public abstract Map<String, Field<?, ?>> zzmy();

  public HashMap<String, Object> zzmz()
  {
    return null;
  }

  public static class Field<I, O>
    implements SafeParcelable
  {
    public static final zza CREATOR = new zza();
    protected final int zzUA;
    protected final boolean zzUB;
    protected final String zzUC;
    protected final int zzUD;
    protected final Class<? extends FastJsonResponse> zzUE;
    protected final String zzUF;
    private FieldMappingDictionary zzUG;
    private FastJsonResponse.zza<I, O> zzUH;
    protected final int zzUy;
    protected final boolean zzUz;
    private final int zzzH;

    Field(int paramInt1, int paramInt2, boolean paramBoolean1, int paramInt3, boolean paramBoolean2, String paramString1, int paramInt4, String paramString2, ConverterWrapper paramConverterWrapper)
    {
      this.zzzH = paramInt1;
      this.zzUy = paramInt2;
      this.zzUz = paramBoolean1;
      this.zzUA = paramInt3;
      this.zzUB = paramBoolean2;
      this.zzUC = paramString1;
      this.zzUD = paramInt4;
      if (paramString2 == null)
        this.zzUE = null;
      for (this.zzUF = null; paramConverterWrapper == null; this.zzUF = paramString2)
      {
        this.zzUH = null;
        return;
        this.zzUE = SafeParcelResponse.class;
      }
      this.zzUH = paramConverterWrapper.zzmu();
    }

    protected Field(int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, String paramString, int paramInt3, Class<? extends FastJsonResponse> paramClass, FastJsonResponse.zza<I, O> paramzza)
    {
      this.zzzH = 1;
      this.zzUy = paramInt1;
      this.zzUz = paramBoolean1;
      this.zzUA = paramInt2;
      this.zzUB = paramBoolean2;
      this.zzUC = paramString;
      this.zzUD = paramInt3;
      this.zzUE = paramClass;
      if (paramClass == null);
      for (this.zzUF = null; ; this.zzUF = paramClass.getCanonicalName())
      {
        this.zzUH = paramzza;
        return;
      }
    }

    public static Field zza(String paramString, int paramInt, FastJsonResponse.zza<?, ?> paramzza, boolean paramBoolean)
    {
      return new Field(paramzza.zzmw(), paramBoolean, paramzza.zzmx(), false, paramString, paramInt, null, paramzza);
    }

    public static <T extends FastJsonResponse> Field<T, T> zza(String paramString, int paramInt, Class<T> paramClass)
    {
      return new Field(11, false, 11, false, paramString, paramInt, paramClass, null);
    }

    public static <T extends FastJsonResponse> Field<ArrayList<T>, ArrayList<T>> zzb(String paramString, int paramInt, Class<T> paramClass)
    {
      return new Field(11, true, 11, true, paramString, paramInt, paramClass, null);
    }

    public static Field<Integer, Integer> zzh(String paramString, int paramInt)
    {
      return new Field(0, false, 0, false, paramString, paramInt, null, null);
    }

    public static Field<Double, Double> zzi(String paramString, int paramInt)
    {
      return new Field(4, false, 4, false, paramString, paramInt, null, null);
    }

    public static Field<Boolean, Boolean> zzj(String paramString, int paramInt)
    {
      return new Field(6, false, 6, false, paramString, paramInt, null, null);
    }

    public static Field<String, String> zzk(String paramString, int paramInt)
    {
      return new Field(7, false, 7, false, paramString, paramInt, null, null);
    }

    public static Field<ArrayList<String>, ArrayList<String>> zzl(String paramString, int paramInt)
    {
      return new Field(7, true, 7, true, paramString, paramInt, null, null);
    }

    public I convertBack(O paramO)
    {
      return this.zzUH.convertBack(paramO);
    }

    public int describeContents()
    {
      return 0;
    }

    public int getVersionCode()
    {
      return this.zzzH;
    }

    public String toString()
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("Field\n");
      localStringBuilder1.append("            versionCode=").append(this.zzzH).append('\n');
      localStringBuilder1.append("                 typeIn=").append(this.zzUy).append('\n');
      localStringBuilder1.append("            typeInArray=").append(this.zzUz).append('\n');
      localStringBuilder1.append("                typeOut=").append(this.zzUA).append('\n');
      localStringBuilder1.append("           typeOutArray=").append(this.zzUB).append('\n');
      localStringBuilder1.append("        outputFieldName=").append(this.zzUC).append('\n');
      localStringBuilder1.append("      safeParcelFieldId=").append(this.zzUD).append('\n');
      localStringBuilder1.append("       concreteTypeName=").append(zzmH()).append('\n');
      if (zzmG() != null)
        localStringBuilder1.append("     concreteType.class=").append(zzmG().getCanonicalName()).append('\n');
      StringBuilder localStringBuilder2 = localStringBuilder1.append("          converterName=");
      if (this.zzUH == null);
      for (String str = "null"; ; str = this.zzUH.getClass().getCanonicalName())
      {
        localStringBuilder2.append(str).append('\n');
        return localStringBuilder1.toString();
      }
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      zza.zza(this, paramParcel, paramInt);
    }

    public void zza(FieldMappingDictionary paramFieldMappingDictionary)
    {
      this.zzUG = paramFieldMappingDictionary;
    }

    public Field<I, O> zzmB()
    {
      return new Field(this.zzzH, this.zzUy, this.zzUz, this.zzUA, this.zzUB, this.zzUC, this.zzUD, this.zzUF, zzmJ());
    }

    public boolean zzmC()
    {
      return this.zzUz;
    }

    public boolean zzmD()
    {
      return this.zzUB;
    }

    public String zzmE()
    {
      return this.zzUC;
    }

    public int zzmF()
    {
      return this.zzUD;
    }

    public Class<? extends FastJsonResponse> zzmG()
    {
      return this.zzUE;
    }

    String zzmH()
    {
      if (this.zzUF == null)
        return null;
      return this.zzUF;
    }

    public boolean zzmI()
    {
      return this.zzUH != null;
    }

    ConverterWrapper zzmJ()
    {
      if (this.zzUH == null)
        return null;
      return ConverterWrapper.zza(this.zzUH);
    }

    public Map<String, Field<?, ?>> zzmK()
    {
      zzv.zzr(this.zzUF);
      zzv.zzr(this.zzUG);
      return this.zzUG.zzbX(this.zzUF);
    }

    public int zzmw()
    {
      return this.zzUy;
    }

    public int zzmx()
    {
      return this.zzUA;
    }
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