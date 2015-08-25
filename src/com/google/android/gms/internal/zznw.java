package com.google.android.gms.internal;

import java.util.Arrays;

public final class zznw
{
  public static final Object zzaNS = new Object();

  public static boolean equals(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    if ((paramArrayOfFloat1 == null) || (paramArrayOfFloat1.length == 0))
      return (paramArrayOfFloat2 == null) || (paramArrayOfFloat2.length == 0);
    return Arrays.equals(paramArrayOfFloat1, paramArrayOfFloat2);
  }

  public static boolean equals(int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    if ((paramArrayOfInt1 == null) || (paramArrayOfInt1.length == 0))
      return (paramArrayOfInt2 == null) || (paramArrayOfInt2.length == 0);
    return Arrays.equals(paramArrayOfInt1, paramArrayOfInt2);
  }

  public static boolean equals(long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    if ((paramArrayOfLong1 == null) || (paramArrayOfLong1.length == 0))
      return (paramArrayOfLong2 == null) || (paramArrayOfLong2.length == 0);
    return Arrays.equals(paramArrayOfLong1, paramArrayOfLong2);
  }

  public static boolean equals(Object[] paramArrayOfObject1, Object[] paramArrayOfObject2)
  {
    int i;
    if (paramArrayOfObject1 == null)
    {
      i = 0;
      if (paramArrayOfObject2 != null)
        break label43;
    }
    int k;
    int m;
    label43: for (int j = 0; ; j = paramArrayOfObject2.length)
    {
      k = 0;
      for (m = 0; (m < i) && (paramArrayOfObject1[m] == null); m++);
      i = paramArrayOfObject1.length;
      break;
    }
    while (true)
    {
      int n;
      if ((n < j) && (paramArrayOfObject2[n] == null))
      {
        n++;
      }
      else
      {
        int i1;
        int i2;
        label86: boolean bool1;
        if (m >= i)
        {
          i1 = 1;
          if (n < j)
            break label108;
          i2 = 1;
          if ((i1 == 0) || (i2 == 0))
            break label114;
          bool1 = true;
        }
        label108: label114: boolean bool2;
        do
        {
          do
          {
            return bool1;
            i1 = 0;
            break;
            i2 = 0;
            break label86;
            bool1 = false;
          }
          while (i1 != i2);
          bool2 = paramArrayOfObject1[m].equals(paramArrayOfObject2[n]);
          bool1 = false;
        }
        while (!bool2);
        int i3 = m + 1;
        k = n + 1;
        m = i3;
        break;
        n = k;
      }
    }
  }

  public static int hashCode(float[] paramArrayOfFloat)
  {
    if ((paramArrayOfFloat == null) || (paramArrayOfFloat.length == 0))
      return 0;
    return Arrays.hashCode(paramArrayOfFloat);
  }

  public static int hashCode(int[] paramArrayOfInt)
  {
    if ((paramArrayOfInt == null) || (paramArrayOfInt.length == 0))
      return 0;
    return Arrays.hashCode(paramArrayOfInt);
  }

  public static int hashCode(long[] paramArrayOfLong)
  {
    if ((paramArrayOfLong == null) || (paramArrayOfLong.length == 0))
      return 0;
    return Arrays.hashCode(paramArrayOfLong);
  }

  public static int hashCode(Object[] paramArrayOfObject)
  {
    int i = 0;
    if (paramArrayOfObject == null);
    for (int j = 0; ; j = paramArrayOfObject.length)
      for (int k = 0; k < j; k++)
      {
        Object localObject = paramArrayOfObject[k];
        if (localObject != null)
          i = i * 31 + localObject.hashCode();
      }
    return i;
  }

  public static int zza(byte[][] paramArrayOfByte)
  {
    int i = 0;
    if (paramArrayOfByte == null);
    for (int j = 0; ; j = paramArrayOfByte.length)
      for (int k = 0; k < j; k++)
      {
        byte[] arrayOfByte = paramArrayOfByte[k];
        if (arrayOfByte != null)
          i = i * 31 + Arrays.hashCode(arrayOfByte);
      }
    return i;
  }

  public static void zza(zzns paramzzns1, zzns paramzzns2)
  {
    if (paramzzns1.zzaNI != null)
      paramzzns2.zzaNI = paramzzns1.zzaNI.zzzS();
  }

  public static boolean zza(byte[][] paramArrayOfByte1, byte[][] paramArrayOfByte2)
  {
    int i;
    if (paramArrayOfByte1 == null)
    {
      i = 0;
      if (paramArrayOfByte2 != null)
        break label43;
    }
    int k;
    int m;
    label43: for (int j = 0; ; j = paramArrayOfByte2.length)
    {
      k = 0;
      for (m = 0; (m < i) && (paramArrayOfByte1[m] == null); m++);
      i = paramArrayOfByte1.length;
      break;
    }
    while (true)
    {
      int n;
      if ((n < j) && (paramArrayOfByte2[n] == null))
      {
        n++;
      }
      else
      {
        int i1;
        int i2;
        label86: boolean bool1;
        if (m >= i)
        {
          i1 = 1;
          if (n < j)
            break label108;
          i2 = 1;
          if ((i1 == 0) || (i2 == 0))
            break label114;
          bool1 = true;
        }
        label108: label114: boolean bool2;
        do
        {
          do
          {
            return bool1;
            i1 = 0;
            break;
            i2 = 0;
            break label86;
            bool1 = false;
          }
          while (i1 != i2);
          bool2 = Arrays.equals(paramArrayOfByte1[m], paramArrayOfByte2[n]);
          bool1 = false;
        }
        while (!bool2);
        int i3 = m + 1;
        k = n + 1;
        m = i3;
        break;
        n = k;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznw
 * JD-Core Version:    0.6.2
 */