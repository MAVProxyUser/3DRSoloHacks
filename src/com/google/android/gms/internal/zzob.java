package com.google.android.gms.internal;

import java.io.IOException;

public final class zzob
{
  public static final int[] zzaNV = new int[0];
  public static final long[] zzaNW = new long[0];
  public static final float[] zzaNX = new float[0];
  public static final double[] zzaNY = new double[0];
  public static final boolean[] zzaNZ = new boolean[0];
  public static final String[] zzaOa = new String[0];
  public static final byte[][] zzaOb = new byte[0][];
  public static final byte[] zzaOc = new byte[0];

  static int zzC(int paramInt1, int paramInt2)
  {
    return paramInt2 | paramInt1 << 3;
  }

  public static final int zzb(zznq paramzznq, int paramInt)
    throws IOException
  {
    int i = 1;
    int j = paramzznq.getPosition();
    paramzznq.zzjl(paramInt);
    while (paramzznq.zzzy() == paramInt)
    {
      paramzznq.zzjl(paramInt);
      i++;
    }
    paramzznq.zzjp(j);
    return i;
  }

  static int zzjF(int paramInt)
  {
    return paramInt & 0x7;
  }

  public static int zzjG(int paramInt)
  {
    return paramInt >>> 3;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzob
 * JD-Core Version:    0.6.2
 */