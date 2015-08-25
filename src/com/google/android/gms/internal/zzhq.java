package com.google.android.gms.internal;

import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;

public final class zzhq
{
  public static <T> int zza(T[] paramArrayOfT, T paramT)
  {
    int i = 0;
    int j;
    if (paramArrayOfT != null)
      j = paramArrayOfT.length;
    while (i < j)
      if (zzu.equal(paramArrayOfT[i], paramT))
      {
        return i;
        j = 0;
        i = 0;
      }
      else
      {
        i++;
      }
    return -1;
  }

  public static void zza(StringBuilder paramStringBuilder, double[] paramArrayOfDouble)
  {
    int i = paramArrayOfDouble.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      paramStringBuilder.append(Double.toString(paramArrayOfDouble[j]));
    }
  }

  public static void zza(StringBuilder paramStringBuilder, float[] paramArrayOfFloat)
  {
    int i = paramArrayOfFloat.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      paramStringBuilder.append(Float.toString(paramArrayOfFloat[j]));
    }
  }

  public static void zza(StringBuilder paramStringBuilder, int[] paramArrayOfInt)
  {
    int i = paramArrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      paramStringBuilder.append(Integer.toString(paramArrayOfInt[j]));
    }
  }

  public static void zza(StringBuilder paramStringBuilder, long[] paramArrayOfLong)
  {
    int i = paramArrayOfLong.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      paramStringBuilder.append(Long.toString(paramArrayOfLong[j]));
    }
  }

  public static <T> void zza(StringBuilder paramStringBuilder, T[] paramArrayOfT)
  {
    int i = paramArrayOfT.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      paramStringBuilder.append(paramArrayOfT[j].toString());
    }
  }

  public static void zza(StringBuilder paramStringBuilder, String[] paramArrayOfString)
  {
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      paramStringBuilder.append("\"").append(paramArrayOfString[j]).append("\"");
    }
  }

  public static void zza(StringBuilder paramStringBuilder, boolean[] paramArrayOfBoolean)
  {
    int i = paramArrayOfBoolean.length;
    for (int j = 0; j < i; j++)
    {
      if (j != 0)
        paramStringBuilder.append(",");
      paramStringBuilder.append(Boolean.toString(paramArrayOfBoolean[j]));
    }
  }

  public static Integer[] zza(int[] paramArrayOfInt)
  {
    Integer[] arrayOfInteger;
    if (paramArrayOfInt == null)
      arrayOfInteger = null;
    while (true)
    {
      return arrayOfInteger;
      int i = paramArrayOfInt.length;
      arrayOfInteger = new Integer[i];
      for (int j = 0; j < i; j++)
        arrayOfInteger[j] = Integer.valueOf(paramArrayOfInt[j]);
    }
  }

  public static <T> ArrayList<T> zzb(T[] paramArrayOfT)
  {
    int i = paramArrayOfT.length;
    ArrayList localArrayList = new ArrayList(i);
    for (int j = 0; j < i; j++)
      localArrayList.add(paramArrayOfT[j]);
    return localArrayList;
  }

  public static <T> boolean zzb(T[] paramArrayOfT, T paramT)
  {
    return zza(paramArrayOfT, paramT) >= 0;
  }

  public static <T> ArrayList<T> zznc()
  {
    return new ArrayList();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhq
 * JD-Core Version:    0.6.2
 */