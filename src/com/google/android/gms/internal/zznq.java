package com.google.android.gms.internal;

import java.io.IOException;

public final class zznq
{
  private final byte[] buffer;
  private int zzaNA;
  private int zzaNB;
  private int zzaNC;
  private int zzaND = 2147483647;
  private int zzaNE;
  private int zzaNF = 64;
  private int zzaNG = 67108864;
  private int zzaNy;
  private int zzaNz;

  private zznq(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.buffer = paramArrayOfByte;
    this.zzaNy = paramInt1;
    this.zzaNz = (paramInt1 + paramInt2);
    this.zzaNB = paramInt1;
  }

  public static long zzU(long paramLong)
  {
    return paramLong >>> 1 ^ -(1L & paramLong);
  }

  public static zznq zza(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new zznq(paramArrayOfByte, paramInt1, paramInt2);
  }

  public static int zzjm(int paramInt)
  {
    return paramInt >>> 1 ^ -(paramInt & 0x1);
  }

  public static zznq zzv(byte[] paramArrayOfByte)
  {
    return zza(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  private void zzzJ()
  {
    this.zzaNz += this.zzaNA;
    int i = this.zzaNz;
    if (i > this.zzaND)
    {
      this.zzaNA = (i - this.zzaND);
      this.zzaNz -= this.zzaNA;
      return;
    }
    this.zzaNA = 0;
  }

  public int getPosition()
  {
    return this.zzaNB - this.zzaNy;
  }

  public byte[] readBytes()
    throws IOException
  {
    int i = zzzF();
    if ((i <= this.zzaNz - this.zzaNB) && (i > 0))
    {
      byte[] arrayOfByte = new byte[i];
      System.arraycopy(this.buffer, this.zzaNB, arrayOfByte, 0, i);
      this.zzaNB = (i + this.zzaNB);
      return arrayOfByte;
    }
    return zzjq(i);
  }

  public double readDouble()
    throws IOException
  {
    return Double.longBitsToDouble(zzzI());
  }

  public float readFloat()
    throws IOException
  {
    return Float.intBitsToFloat(zzzH());
  }

  public String readString()
    throws IOException
  {
    int i = zzzF();
    if ((i <= this.zzaNz - this.zzaNB) && (i > 0))
    {
      String str = new String(this.buffer, this.zzaNB, i, "UTF-8");
      this.zzaNB = (i + this.zzaNB);
      return str;
    }
    return new String(zzjq(i), "UTF-8");
  }

  public void zza(zzny paramzzny)
    throws IOException
  {
    int i = zzzF();
    if (this.zzaNE >= this.zzaNF)
      throw zznx.zzAa();
    int j = zzjn(i);
    this.zzaNE = (1 + this.zzaNE);
    paramzzny.zzb(this);
    zzjk(0);
    this.zzaNE = (-1 + this.zzaNE);
    zzjo(j);
  }

  public void zza(zzny paramzzny, int paramInt)
    throws IOException
  {
    if (this.zzaNE >= this.zzaNF)
      throw zznx.zzAa();
    this.zzaNE = (1 + this.zzaNE);
    paramzzny.zzb(this);
    zzjk(zzob.zzC(paramInt, 4));
    this.zzaNE = (-1 + this.zzaNE);
  }

  public void zzjk(int paramInt)
    throws zznx
  {
    if (this.zzaNC != paramInt)
      throw zznx.zzzY();
  }

  public boolean zzjl(int paramInt)
    throws IOException
  {
    switch (zzob.zzjF(paramInt))
    {
    default:
      throw zznx.zzzZ();
    case 0:
      zzzB();
      return true;
    case 1:
      zzzI();
      return true;
    case 2:
      zzjr(zzzF());
      return true;
    case 3:
      zzzz();
      zzjk(zzob.zzC(zzob.zzjG(paramInt), 4));
      return true;
    case 4:
      return false;
    case 5:
    }
    zzzH();
    return true;
  }

  public int zzjn(int paramInt)
    throws zznx
  {
    if (paramInt < 0)
      throw zznx.zzzV();
    int i = paramInt + this.zzaNB;
    int j = this.zzaND;
    if (i > j)
      throw zznx.zzzU();
    this.zzaND = i;
    zzzJ();
    return j;
  }

  public void zzjo(int paramInt)
  {
    this.zzaND = paramInt;
    zzzJ();
  }

  public void zzjp(int paramInt)
  {
    if (paramInt > this.zzaNB - this.zzaNy)
      throw new IllegalArgumentException("Position " + paramInt + " is beyond current " + (this.zzaNB - this.zzaNy));
    if (paramInt < 0)
      throw new IllegalArgumentException("Bad position " + paramInt);
    this.zzaNB = (paramInt + this.zzaNy);
  }

  public byte[] zzjq(int paramInt)
    throws IOException
  {
    if (paramInt < 0)
      throw zznx.zzzV();
    if (paramInt + this.zzaNB > this.zzaND)
    {
      zzjr(this.zzaND - this.zzaNB);
      throw zznx.zzzU();
    }
    if (paramInt <= this.zzaNz - this.zzaNB)
    {
      byte[] arrayOfByte = new byte[paramInt];
      System.arraycopy(this.buffer, this.zzaNB, arrayOfByte, 0, paramInt);
      this.zzaNB = (paramInt + this.zzaNB);
      return arrayOfByte;
    }
    throw zznx.zzzU();
  }

  public void zzjr(int paramInt)
    throws IOException
  {
    if (paramInt < 0)
      throw zznx.zzzV();
    if (paramInt + this.zzaNB > this.zzaND)
    {
      zzjr(this.zzaND - this.zzaNB);
      throw zznx.zzzU();
    }
    if (paramInt <= this.zzaNz - this.zzaNB)
    {
      this.zzaNB = (paramInt + this.zzaNB);
      return;
    }
    throw zznx.zzzU();
  }

  public byte[] zzw(int paramInt1, int paramInt2)
  {
    if (paramInt2 == 0)
      return zzob.zzaOc;
    byte[] arrayOfByte = new byte[paramInt2];
    int i = paramInt1 + this.zzaNy;
    System.arraycopy(this.buffer, i, arrayOfByte, 0, paramInt2);
    return arrayOfByte;
  }

  public long zzzA()
    throws IOException
  {
    return zzzG();
  }

  public int zzzB()
    throws IOException
  {
    return zzzF();
  }

  public boolean zzzC()
    throws IOException
  {
    return zzzF() != 0;
  }

  public int zzzD()
    throws IOException
  {
    return zzjm(zzzF());
  }

  public long zzzE()
    throws IOException
  {
    return zzU(zzzG());
  }

  public int zzzF()
    throws IOException
  {
    int i = zzzM();
    if (i >= 0);
    int i4;
    do
    {
      return i;
      int j = i & 0x7F;
      int k = zzzM();
      if (k >= 0)
        return j | k << 7;
      int m = j | (k & 0x7F) << 7;
      int n = zzzM();
      if (n >= 0)
        return m | n << 14;
      int i1 = m | (n & 0x7F) << 14;
      int i2 = zzzM();
      if (i2 >= 0)
        return i1 | i2 << 21;
      int i3 = i1 | (i2 & 0x7F) << 21;
      i4 = zzzM();
      i = i3 | i4 << 28;
    }
    while (i4 >= 0);
    for (int i5 = 0; ; i5++)
    {
      if (i5 >= 5)
        break label151;
      if (zzzM() >= 0)
        break;
    }
    label151: throw zznx.zzzW();
  }

  public long zzzG()
    throws IOException
  {
    int i = 0;
    long l = 0L;
    while (i < 64)
    {
      int j = zzzM();
      l |= (j & 0x7F) << i;
      if ((j & 0x80) == 0)
        return l;
      i += 7;
    }
    throw zznx.zzzW();
  }

  public int zzzH()
    throws IOException
  {
    int i = zzzM();
    int j = zzzM();
    int k = zzzM();
    int m = zzzM();
    return i & 0xFF | (j & 0xFF) << 8 | (k & 0xFF) << 16 | (m & 0xFF) << 24;
  }

  public long zzzI()
    throws IOException
  {
    int i = zzzM();
    int j = zzzM();
    int k = zzzM();
    int m = zzzM();
    int n = zzzM();
    int i1 = zzzM();
    int i2 = zzzM();
    int i3 = zzzM();
    return 0xFF & i | (0xFF & j) << 8 | (0xFF & k) << 16 | (0xFF & m) << 24 | (0xFF & n) << 32 | (0xFF & i1) << 40 | (0xFF & i2) << 48 | (0xFF & i3) << 56;
  }

  public int zzzK()
  {
    if (this.zzaND == 2147483647)
      return -1;
    int i = this.zzaNB;
    return this.zzaND - i;
  }

  public boolean zzzL()
  {
    return this.zzaNB == this.zzaNz;
  }

  public byte zzzM()
    throws IOException
  {
    if (this.zzaNB == this.zzaNz)
      throw zznx.zzzU();
    byte[] arrayOfByte = this.buffer;
    int i = this.zzaNB;
    this.zzaNB = (i + 1);
    return arrayOfByte[i];
  }

  public int zzzy()
    throws IOException
  {
    if (zzzL())
    {
      this.zzaNC = 0;
      return 0;
    }
    this.zzaNC = zzzF();
    if (this.zzaNC == 0)
      throw zznx.zzzX();
    return this.zzaNC;
  }

  public void zzzz()
    throws IOException
  {
    int i;
    do
      i = zzzy();
    while ((i != 0) && (zzjl(i)));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznq
 * JD-Core Version:    0.6.2
 */