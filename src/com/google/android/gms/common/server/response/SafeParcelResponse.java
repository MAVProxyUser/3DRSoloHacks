package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zza.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzhq;
import com.google.android.gms.internal.zzhr;
import com.google.android.gms.internal.zzhz;
import com.google.android.gms.internal.zzia;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SafeParcelResponse extends FastJsonResponse
  implements SafeParcelable
{
  public static final zze CREATOR = new zze();
  private final String mClassName;
  private final FieldMappingDictionary zzUG;
  private final Parcel zzUN;
  private final int zzUO;
  private int zzUP;
  private int zzUQ;
  private final int zzzH;

  SafeParcelResponse(int paramInt, Parcel paramParcel, FieldMappingDictionary paramFieldMappingDictionary)
  {
    this.zzzH = paramInt;
    this.zzUN = ((Parcel)zzv.zzr(paramParcel));
    this.zzUO = 2;
    this.zzUG = paramFieldMappingDictionary;
    if (this.zzUG == null);
    for (this.mClassName = null; ; this.mClassName = this.zzUG.zzmO())
    {
      this.zzUP = 2;
      return;
    }
  }

  private SafeParcelResponse(SafeParcelable paramSafeParcelable, FieldMappingDictionary paramFieldMappingDictionary, String paramString)
  {
    this.zzzH = 1;
    this.zzUN = Parcel.obtain();
    paramSafeParcelable.writeToParcel(this.zzUN, 0);
    this.zzUO = 1;
    this.zzUG = ((FieldMappingDictionary)zzv.zzr(paramFieldMappingDictionary));
    this.mClassName = ((String)zzv.zzr(paramString));
    this.zzUP = 2;
  }

  private static HashMap<Integer, Map.Entry<String, FastJsonResponse.Field<?, ?>>> zzB(Map<String, FastJsonResponse.Field<?, ?>> paramMap)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      localHashMap.put(Integer.valueOf(((FastJsonResponse.Field)localEntry.getValue()).zzmF()), localEntry);
    }
    return localHashMap;
  }

  public static <T extends FastJsonResponse,  extends SafeParcelable> SafeParcelResponse zza(T paramT)
  {
    String str = paramT.getClass().getCanonicalName();
    FieldMappingDictionary localFieldMappingDictionary = zzb(paramT);
    return new SafeParcelResponse((SafeParcelable)paramT, localFieldMappingDictionary, str);
  }

  private static void zza(FieldMappingDictionary paramFieldMappingDictionary, FastJsonResponse paramFastJsonResponse)
  {
    Class localClass1 = paramFastJsonResponse.getClass();
    if (!paramFieldMappingDictionary.zzb(localClass1))
    {
      Map localMap = paramFastJsonResponse.zzmy();
      paramFieldMappingDictionary.zza(localClass1, localMap);
      Iterator localIterator = localMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        FastJsonResponse.Field localField = (FastJsonResponse.Field)localMap.get((String)localIterator.next());
        Class localClass2 = localField.zzmG();
        if (localClass2 != null)
          try
          {
            zza(paramFieldMappingDictionary, (FastJsonResponse)localClass2.newInstance());
          }
          catch (InstantiationException localInstantiationException)
          {
            throw new IllegalStateException("Could not instantiate an object of type " + localField.zzmG().getCanonicalName(), localInstantiationException);
          }
          catch (IllegalAccessException localIllegalAccessException)
          {
            throw new IllegalStateException("Could not access object of type " + localField.zzmG().getCanonicalName(), localIllegalAccessException);
          }
      }
    }
  }

  private void zza(StringBuilder paramStringBuilder, int paramInt, Object paramObject)
  {
    switch (paramInt)
    {
    default:
      throw new IllegalArgumentException("Unknown type = " + paramInt);
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    case 5:
    case 6:
      paramStringBuilder.append(paramObject);
      return;
    case 7:
      paramStringBuilder.append("\"").append(zzhz.zzbY(paramObject.toString())).append("\"");
      return;
    case 8:
      paramStringBuilder.append("\"").append(zzhr.zzg((byte[])paramObject)).append("\"");
      return;
    case 9:
      paramStringBuilder.append("\"").append(zzhr.zzh((byte[])paramObject));
      paramStringBuilder.append("\"");
      return;
    case 10:
      zzia.zza(paramStringBuilder, (HashMap)paramObject);
      return;
    case 11:
    }
    throw new IllegalArgumentException("Method does not accept concrete type.");
  }

  private void zza(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, Parcel paramParcel, int paramInt)
  {
    switch (paramField.zzmx())
    {
    default:
      throw new IllegalArgumentException("Unknown field out type = " + paramField.zzmx());
    case 0:
      zzb(paramStringBuilder, paramField, zza(paramField, Integer.valueOf(zza.zzg(paramParcel, paramInt))));
      return;
    case 1:
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzk(paramParcel, paramInt)));
      return;
    case 2:
      zzb(paramStringBuilder, paramField, zza(paramField, Long.valueOf(zza.zzi(paramParcel, paramInt))));
      return;
    case 3:
      zzb(paramStringBuilder, paramField, zza(paramField, Float.valueOf(zza.zzl(paramParcel, paramInt))));
      return;
    case 4:
      zzb(paramStringBuilder, paramField, zza(paramField, Double.valueOf(zza.zzm(paramParcel, paramInt))));
      return;
    case 5:
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzn(paramParcel, paramInt)));
      return;
    case 6:
      zzb(paramStringBuilder, paramField, zza(paramField, Boolean.valueOf(zza.zzc(paramParcel, paramInt))));
      return;
    case 7:
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzo(paramParcel, paramInt)));
      return;
    case 8:
    case 9:
      zzb(paramStringBuilder, paramField, zza(paramField, zza.zzr(paramParcel, paramInt)));
      return;
    case 10:
      zzb(paramStringBuilder, paramField, zza(paramField, zzh(zza.zzq(paramParcel, paramInt))));
      return;
    case 11:
    }
    throw new IllegalArgumentException("Method does not accept concrete type.");
  }

  private void zza(StringBuilder paramStringBuilder, String paramString, FastJsonResponse.Field<?, ?> paramField, Parcel paramParcel, int paramInt)
  {
    paramStringBuilder.append("\"").append(paramString).append("\":");
    if (paramField.zzmI())
    {
      zza(paramStringBuilder, paramField, paramParcel, paramInt);
      return;
    }
    zzb(paramStringBuilder, paramField, paramParcel, paramInt);
  }

  private void zza(StringBuilder paramStringBuilder, Map<String, FastJsonResponse.Field<?, ?>> paramMap, Parcel paramParcel)
  {
    HashMap localHashMap = zzB(paramMap);
    paramStringBuilder.append('{');
    int i = zza.zzL(paramParcel);
    int j = 0;
    while (paramParcel.dataPosition() < i)
    {
      int k = zza.zzK(paramParcel);
      Map.Entry localEntry = (Map.Entry)localHashMap.get(Integer.valueOf(zza.zzaV(k)));
      if (localEntry != null)
      {
        if (j != 0)
          paramStringBuilder.append(",");
        zza(paramStringBuilder, (String)localEntry.getKey(), (FastJsonResponse.Field)localEntry.getValue(), paramParcel, k);
        j = 1;
      }
    }
    if (paramParcel.dataPosition() != i)
      throw new zza.zza("Overread allowed size end=" + i, paramParcel);
    paramStringBuilder.append('}');
  }

  private static FieldMappingDictionary zzb(FastJsonResponse paramFastJsonResponse)
  {
    FieldMappingDictionary localFieldMappingDictionary = new FieldMappingDictionary(paramFastJsonResponse.getClass());
    zza(localFieldMappingDictionary, paramFastJsonResponse);
    localFieldMappingDictionary.zzmM();
    localFieldMappingDictionary.zzmL();
    return localFieldMappingDictionary;
  }

  private void zzb(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, Parcel paramParcel, int paramInt)
  {
    if (paramField.zzmD())
    {
      paramStringBuilder.append("[");
      switch (paramField.zzmx())
      {
      default:
        throw new IllegalStateException("Unknown field type out.");
      case 0:
        zzhq.zza(paramStringBuilder, zza.zzu(paramParcel, paramInt));
      case 1:
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      }
      while (true)
      {
        paramStringBuilder.append("]");
        return;
        zzhq.zza(paramStringBuilder, zza.zzw(paramParcel, paramInt));
        continue;
        zzhq.zza(paramStringBuilder, zza.zzv(paramParcel, paramInt));
        continue;
        zzhq.zza(paramStringBuilder, zza.zzx(paramParcel, paramInt));
        continue;
        zzhq.zza(paramStringBuilder, zza.zzy(paramParcel, paramInt));
        continue;
        zzhq.zza(paramStringBuilder, zza.zzz(paramParcel, paramInt));
        continue;
        zzhq.zza(paramStringBuilder, zza.zzt(paramParcel, paramInt));
        continue;
        zzhq.zza(paramStringBuilder, zza.zzA(paramParcel, paramInt));
        continue;
        throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
        Parcel[] arrayOfParcel = zza.zzE(paramParcel, paramInt);
        int j = arrayOfParcel.length;
        for (int k = 0; k < j; k++)
        {
          if (k > 0)
            paramStringBuilder.append(",");
          arrayOfParcel[k].setDataPosition(0);
          zza(paramStringBuilder, paramField.zzmK(), arrayOfParcel[k]);
        }
      }
    }
    switch (paramField.zzmx())
    {
    default:
      throw new IllegalStateException("Unknown field type out");
    case 0:
      paramStringBuilder.append(zza.zzg(paramParcel, paramInt));
      return;
    case 1:
      paramStringBuilder.append(zza.zzk(paramParcel, paramInt));
      return;
    case 2:
      paramStringBuilder.append(zza.zzi(paramParcel, paramInt));
      return;
    case 3:
      paramStringBuilder.append(zza.zzl(paramParcel, paramInt));
      return;
    case 4:
      paramStringBuilder.append(zza.zzm(paramParcel, paramInt));
      return;
    case 5:
      paramStringBuilder.append(zza.zzn(paramParcel, paramInt));
      return;
    case 6:
      paramStringBuilder.append(zza.zzc(paramParcel, paramInt));
      return;
    case 7:
      String str2 = zza.zzo(paramParcel, paramInt);
      paramStringBuilder.append("\"").append(zzhz.zzbY(str2)).append("\"");
      return;
    case 8:
      byte[] arrayOfByte2 = zza.zzr(paramParcel, paramInt);
      paramStringBuilder.append("\"").append(zzhr.zzg(arrayOfByte2)).append("\"");
      return;
    case 9:
      byte[] arrayOfByte1 = zza.zzr(paramParcel, paramInt);
      paramStringBuilder.append("\"").append(zzhr.zzh(arrayOfByte1));
      paramStringBuilder.append("\"");
      return;
    case 10:
      Bundle localBundle = zza.zzq(paramParcel, paramInt);
      Set localSet = localBundle.keySet();
      localSet.size();
      paramStringBuilder.append("{");
      Iterator localIterator = localSet.iterator();
      for (int i = 1; localIterator.hasNext(); i = 0)
      {
        String str1 = (String)localIterator.next();
        if (i == 0)
          paramStringBuilder.append(",");
        paramStringBuilder.append("\"").append(str1).append("\"");
        paramStringBuilder.append(":");
        paramStringBuilder.append("\"").append(zzhz.zzbY(localBundle.getString(str1))).append("\"");
      }
      paramStringBuilder.append("}");
      return;
    case 11:
    }
    Parcel localParcel = zza.zzD(paramParcel, paramInt);
    localParcel.setDataPosition(0);
    zza(paramStringBuilder, paramField.zzmK(), localParcel);
  }

  private void zzb(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, Object paramObject)
  {
    if (paramField.zzmC())
    {
      zzb(paramStringBuilder, paramField, (ArrayList)paramObject);
      return;
    }
    zza(paramStringBuilder, paramField.zzmw(), paramObject);
  }

  private void zzb(StringBuilder paramStringBuilder, FastJsonResponse.Field<?, ?> paramField, ArrayList<?> paramArrayList)
  {
    paramStringBuilder.append("[");
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      zza(paramStringBuilder, paramField.zzmw(), paramArrayList.get(j));
    }
    paramStringBuilder.append("]");
  }

  public static HashMap<String, String> zzh(Bundle paramBundle)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, paramBundle.getString(str));
    }
    return localHashMap;
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
    zzv.zzb(this.zzUG, "Cannot convert to JSON on client side.");
    Parcel localParcel = zzmQ();
    localParcel.setDataPosition(0);
    StringBuilder localStringBuilder = new StringBuilder(100);
    zza(localStringBuilder, this.zzUG.zzbX(this.mClassName), localParcel);
    return localStringBuilder.toString();
  }

  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    zze.zza(this, paramParcel, paramInt);
  }

  protected Object zzbT(String paramString)
  {
    throw new UnsupportedOperationException("Converting to JSON does not require this method.");
  }

  protected boolean zzbU(String paramString)
  {
    throw new UnsupportedOperationException("Converting to JSON does not require this method.");
  }

  public Parcel zzmQ()
  {
    switch (this.zzUP)
    {
    default:
    case 0:
    case 1:
    }
    while (true)
    {
      return this.zzUN;
      this.zzUQ = zzb.zzM(this.zzUN);
      zzb.zzH(this.zzUN, this.zzUQ);
      this.zzUP = 2;
      continue;
      zzb.zzH(this.zzUN, this.zzUQ);
      this.zzUP = 2;
    }
  }

  FieldMappingDictionary zzmR()
  {
    switch (this.zzUO)
    {
    default:
      throw new IllegalStateException("Invalid creation type: " + this.zzUO);
    case 0:
      return null;
    case 1:
      return this.zzUG;
    case 2:
    }
    return this.zzUG;
  }

  public Map<String, FastJsonResponse.Field<?, ?>> zzmy()
  {
    if (this.zzUG == null)
      return null;
    return this.zzUG.zzbX(this.mClassName);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.server.response.SafeParcelResponse
 * JD-Core Version:    0.6.2
 */