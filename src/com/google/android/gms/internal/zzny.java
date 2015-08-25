package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzny
{
  protected volatile int zzaNT = -1;

  public static final <T extends zzny> T zza(T paramT, byte[] paramArrayOfByte)
    throws zznx
  {
    return zzb(paramT, paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static final void zza(zzny paramzzny, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    try
    {
      zznr localzznr = zznr.zzb(paramArrayOfByte, paramInt1, paramInt2);
      paramzzny.zza(localzznr);
      localzznr.zzzO();
      return;
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", localIOException);
    }
  }

  public static final <T extends zzny> T zzb(T paramT, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws zznx
  {
    try
    {
      zznq localzznq = zznq.zza(paramArrayOfByte, paramInt1, paramInt2);
      paramT.zzb(localzznq);
      localzznq.zzjk(0);
      return paramT;
    }
    catch (zznx localzznx)
    {
      throw localzznx;
    }
    catch (IOException localIOException)
    {
    }
    throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
  }

  public static final byte[] zzf(zzny paramzzny)
  {
    byte[] arrayOfByte = new byte[paramzzny.zzAc()];
    zza(paramzzny, arrayOfByte, 0, arrayOfByte.length);
    return arrayOfByte;
  }

  public String toString()
  {
    return zznz.zzg(this);
  }

  public int zzAb()
  {
    if (this.zzaNT < 0)
      zzAc();
    return this.zzaNT;
  }

  public int zzAc()
  {
    int i = zzc();
    this.zzaNT = i;
    return i;
  }

  public void zza(zznr paramzznr)
    throws IOException
  {
  }

  public abstract zzny zzb(zznq paramzznq)
    throws IOException;

  protected int zzc()
  {
    return 0;
  }

  public zzny zzzR()
    throws CloneNotSupportedException
  {
    return (zzny)super.clone();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzny
 * JD-Core Version:    0.6.2
 */