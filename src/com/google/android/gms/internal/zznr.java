package com.google.android.gms.internal;

import java.io.IOException;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;

public final class zznr
{
  private final ByteBuffer zzaNH;

  private zznr(ByteBuffer paramByteBuffer)
  {
    this.zzaNH = paramByteBuffer;
  }

  private zznr(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this(ByteBuffer.wrap(paramArrayOfByte, paramInt1, paramInt2));
  }

  public static int zzA(int paramInt1, int paramInt2)
  {
    return zzjx(paramInt1) + zzjv(paramInt2);
  }

  public static int zzX(long paramLong)
  {
    return zzaa(paramLong);
  }

  public static int zzY(long paramLong)
  {
    return zzaa(zzac(paramLong));
  }

  private static int zza(CharSequence paramCharSequence)
  {
    int i = paramCharSequence.length();
    for (int j = 0; (j < i) && (paramCharSequence.charAt(j) < ''); j++);
    while (true)
    {
      int k;
      int m;
      if (k < i)
      {
        int n = paramCharSequence.charAt(k);
        if (n < 2048)
        {
          int i1 = m + (127 - n >>> 31);
          k++;
          m = i1;
        }
        else
        {
          m += zza(paramCharSequence, k);
        }
      }
      else
      {
        if (m < i)
          throw new IllegalArgumentException("UTF-8 length does not fit in int: " + (4294967296L + m));
        return m;
        k = j;
        m = i;
      }
    }
  }

  private static int zza(CharSequence paramCharSequence, int paramInt)
  {
    int i = paramCharSequence.length();
    int j = 0;
    int k = paramInt;
    if (k < i)
    {
      int m = paramCharSequence.charAt(k);
      if (m < 2048)
        j += (127 - m >>> 31);
      while (true)
      {
        k++;
        break;
        j += 2;
        if ((55296 <= m) && (m <= 57343))
        {
          if (Character.codePointAt(paramCharSequence, k) < 65536)
            throw new IllegalArgumentException("Unpaired surrogate at index " + k);
          k++;
        }
      }
    }
    return j;
  }

  private static int zza(CharSequence paramCharSequence, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    int i = paramCharSequence.length();
    int j = 0;
    int k = paramInt1 + paramInt2;
    while ((j < i) && (j + paramInt1 < k))
    {
      int i9 = paramCharSequence.charAt(j);
      if (i9 >= 128)
        break;
      paramArrayOfByte[(paramInt1 + j)] = ((byte)i9);
      j++;
    }
    if (j == i)
      return paramInt1 + i;
    int m = paramInt1 + j;
    if (j < i)
    {
      int n = paramCharSequence.charAt(j);
      int i5;
      if ((n < 128) && (m < k))
      {
        i5 = m + 1;
        paramArrayOfByte[m] = ((byte)n);
      }
      while (true)
      {
        j++;
        m = i5;
        break;
        if ((n < 2048) && (m <= k - 2))
        {
          int i8 = m + 1;
          paramArrayOfByte[m] = ((byte)(0x3C0 | n >>> 6));
          i5 = i8 + 1;
          paramArrayOfByte[i8] = ((byte)(0x80 | n & 0x3F));
        }
        else if (((n < 55296) || (57343 < n)) && (m <= k - 3))
        {
          int i6 = m + 1;
          paramArrayOfByte[m] = ((byte)(0x1E0 | n >>> 12));
          int i7 = i6 + 1;
          paramArrayOfByte[i6] = ((byte)(0x80 | 0x3F & n >>> 6));
          i5 = i7 + 1;
          paramArrayOfByte[i7] = ((byte)(0x80 | n & 0x3F));
        }
        else
        {
          if (m > k - 4)
            break label460;
          char c;
          if (j + 1 != paramCharSequence.length())
          {
            j++;
            c = paramCharSequence.charAt(j);
            if (Character.isSurrogatePair(n, c));
          }
          else
          {
            throw new IllegalArgumentException("Unpaired surrogate at index " + (j - 1));
          }
          int i1 = Character.toCodePoint(n, c);
          int i2 = m + 1;
          paramArrayOfByte[m] = ((byte)(0xF0 | i1 >>> 18));
          int i3 = i2 + 1;
          paramArrayOfByte[i2] = ((byte)(0x80 | 0x3F & i1 >>> 12));
          int i4 = i3 + 1;
          paramArrayOfByte[i3] = ((byte)(0x80 | 0x3F & i1 >>> 6));
          i5 = i4 + 1;
          paramArrayOfByte[i4] = ((byte)(0x80 | i1 & 0x3F));
        }
      }
      label460: throw new ArrayIndexOutOfBoundsException("Failed writing " + n + " at index " + m);
    }
    return m;
  }

  private static void zza(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer.isReadOnly())
      throw new ReadOnlyBufferException();
    if (paramByteBuffer.hasArray())
      try
      {
        paramByteBuffer.position(zza(paramCharSequence, paramByteBuffer.array(), paramByteBuffer.arrayOffset() + paramByteBuffer.position(), paramByteBuffer.remaining()) - paramByteBuffer.arrayOffset());
        return;
      }
      catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
      {
        BufferOverflowException localBufferOverflowException = new BufferOverflowException();
        localBufferOverflowException.initCause(localArrayIndexOutOfBoundsException);
        throw localBufferOverflowException;
      }
    zzb(paramCharSequence, paramByteBuffer);
  }

  public static int zzaa(long paramLong)
  {
    if ((0xFFFFFF80 & paramLong) == 0L)
      return 1;
    if ((0xFFFFC000 & paramLong) == 0L)
      return 2;
    if ((0xFFE00000 & paramLong) == 0L)
      return 3;
    if ((0xF0000000 & paramLong) == 0L)
      return 4;
    if ((0x0 & paramLong) == 0L)
      return 5;
    if ((0x0 & paramLong) == 0L)
      return 6;
    if ((0x0 & paramLong) == 0L)
      return 7;
    if ((0x0 & paramLong) == 0L)
      return 8;
    if ((0x0 & paramLong) == 0L)
      return 9;
    return 10;
  }

  public static long zzac(long paramLong)
  {
    return paramLong << 1 ^ paramLong >> 63;
  }

  public static int zzan(boolean paramBoolean)
  {
    return 1;
  }

  public static int zzb(int paramInt, double paramDouble)
  {
    return zzjx(paramInt) + zzg(paramDouble);
  }

  public static int zzb(int paramInt, zzny paramzzny)
  {
    return 2 * zzjx(paramInt) + zzd(paramzzny);
  }

  public static int zzb(int paramInt, byte[] paramArrayOfByte)
  {
    return zzjx(paramInt) + zzy(paramArrayOfByte);
  }

  public static zznr zzb(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    return new zznr(paramArrayOfByte, paramInt1, paramInt2);
  }

  private static void zzb(CharSequence paramCharSequence, ByteBuffer paramByteBuffer)
  {
    int i = paramCharSequence.length();
    int j = 0;
    if (j < i)
    {
      int k = paramCharSequence.charAt(j);
      if (k < 128)
        paramByteBuffer.put((byte)k);
      while (true)
      {
        j++;
        break;
        if (k < 2048)
        {
          paramByteBuffer.put((byte)(0x3C0 | k >>> 6));
          paramByteBuffer.put((byte)(0x80 | k & 0x3F));
        }
        else if ((k < 55296) || (57343 < k))
        {
          paramByteBuffer.put((byte)(0x1E0 | k >>> 12));
          paramByteBuffer.put((byte)(0x80 | 0x3F & k >>> 6));
          paramByteBuffer.put((byte)(0x80 | k & 0x3F));
        }
        else
        {
          char c;
          if (j + 1 != paramCharSequence.length())
          {
            j++;
            c = paramCharSequence.charAt(j);
            if (Character.isSurrogatePair(k, c));
          }
          else
          {
            throw new IllegalArgumentException("Unpaired surrogate at index " + (j - 1));
          }
          int m = Character.toCodePoint(k, c);
          paramByteBuffer.put((byte)(0xF0 | m >>> 18));
          paramByteBuffer.put((byte)(0x80 | 0x3F & m >>> 12));
          paramByteBuffer.put((byte)(0x80 | 0x3F & m >>> 6));
          paramByteBuffer.put((byte)(0x80 | m & 0x3F));
        }
      }
    }
  }

  public static int zzc(int paramInt, float paramFloat)
  {
    return zzjx(paramInt) + zzj(paramFloat);
  }

  public static int zzc(int paramInt, zzny paramzzny)
  {
    return zzjx(paramInt) + zze(paramzzny);
  }

  public static int zzc(int paramInt, boolean paramBoolean)
  {
    return zzjx(paramInt) + zzan(paramBoolean);
  }

  public static int zzd(int paramInt, long paramLong)
  {
    return zzjx(paramInt) + zzX(paramLong);
  }

  public static int zzd(zzny paramzzny)
  {
    return paramzzny.zzAc();
  }

  public static int zze(int paramInt, long paramLong)
  {
    return zzjx(paramInt) + zzY(paramLong);
  }

  public static int zze(zzny paramzzny)
  {
    int i = paramzzny.zzAc();
    return i + zzjz(i);
  }

  public static int zzeB(String paramString)
  {
    int i = zza(paramString);
    return i + zzjz(i);
  }

  public static int zzg(double paramDouble)
  {
    return 8;
  }

  public static int zzj(float paramFloat)
  {
    return 4;
  }

  public static int zzj(int paramInt, String paramString)
  {
    return zzjx(paramInt) + zzeB(paramString);
  }

  public static int zzjB(int paramInt)
  {
    return paramInt << 1 ^ paramInt >> 31;
  }

  public static int zzju(int paramInt)
  {
    if (paramInt >= 0)
      return zzjz(paramInt);
    return 10;
  }

  public static int zzjv(int paramInt)
  {
    return zzjz(zzjB(paramInt));
  }

  public static int zzjx(int paramInt)
  {
    return zzjz(zzob.zzC(paramInt, 0));
  }

  public static int zzjz(int paramInt)
  {
    if ((paramInt & 0xFFFFFF80) == 0)
      return 1;
    if ((paramInt & 0xFFFFC000) == 0)
      return 2;
    if ((0xFFE00000 & paramInt) == 0)
      return 3;
    if ((0xF0000000 & paramInt) == 0)
      return 4;
    return 5;
  }

  public static zznr zzw(byte[] paramArrayOfByte)
  {
    return zzb(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public static int zzy(byte[] paramArrayOfByte)
  {
    return zzjz(paramArrayOfByte.length) + paramArrayOfByte.length;
  }

  public static int zzz(int paramInt1, int paramInt2)
  {
    return zzjx(paramInt1) + zzju(paramInt2);
  }

  public void zzB(int paramInt1, int paramInt2)
    throws IOException
  {
    zzjy(zzob.zzC(paramInt1, paramInt2));
  }

  public void zzV(long paramLong)
    throws IOException
  {
    zzZ(paramLong);
  }

  public void zzW(long paramLong)
    throws IOException
  {
    zzZ(zzac(paramLong));
  }

  public void zzZ(long paramLong)
    throws IOException
  {
    while (true)
    {
      if ((0xFFFFFF80 & paramLong) == 0L)
      {
        zzjw((int)paramLong);
        return;
      }
      zzjw(0x80 | 0x7F & (int)paramLong);
      paramLong >>>= 7;
    }
  }

  public void zza(int paramInt, double paramDouble)
    throws IOException
  {
    zzB(paramInt, 1);
    zzf(paramDouble);
  }

  public void zza(int paramInt, zzny paramzzny)
    throws IOException
  {
    zzB(paramInt, 2);
    zzc(paramzzny);
  }

  public void zza(int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    zzB(paramInt, 2);
    zzx(paramArrayOfByte);
  }

  public void zzab(long paramLong)
    throws IOException
  {
    zzjw(0xFF & (int)paramLong);
    zzjw(0xFF & (int)(paramLong >> 8));
    zzjw(0xFF & (int)(paramLong >> 16));
    zzjw(0xFF & (int)(paramLong >> 24));
    zzjw(0xFF & (int)(paramLong >> 32));
    zzjw(0xFF & (int)(paramLong >> 40));
    zzjw(0xFF & (int)(paramLong >> 48));
    zzjw(0xFF & (int)(paramLong >> 56));
  }

  public void zzam(boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean);
    for (int i = 1; ; i = 0)
    {
      zzjw(i);
      return;
    }
  }

  public void zzb(byte paramByte)
    throws IOException
  {
    if (!this.zzaNH.hasRemaining())
      throw new zza(this.zzaNH.position(), this.zzaNH.limit());
    this.zzaNH.put(paramByte);
  }

  public void zzb(int paramInt, float paramFloat)
    throws IOException
  {
    zzB(paramInt, 5);
    zzi(paramFloat);
  }

  public void zzb(int paramInt, long paramLong)
    throws IOException
  {
    zzB(paramInt, 0);
    zzV(paramLong);
  }

  public void zzb(int paramInt, String paramString)
    throws IOException
  {
    zzB(paramInt, 2);
    zzeA(paramString);
  }

  public void zzb(int paramInt, boolean paramBoolean)
    throws IOException
  {
    zzB(paramInt, 0);
    zzam(paramBoolean);
  }

  public void zzb(zzny paramzzny)
    throws IOException
  {
    paramzzny.zza(this);
  }

  public void zzc(int paramInt, long paramLong)
    throws IOException
  {
    zzB(paramInt, 0);
    zzW(paramLong);
  }

  public void zzc(zzny paramzzny)
    throws IOException
  {
    zzjy(paramzzny.zzAb());
    paramzzny.zza(this);
  }

  public void zzc(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.zzaNH.remaining() >= paramInt2)
    {
      this.zzaNH.put(paramArrayOfByte, paramInt1, paramInt2);
      return;
    }
    throw new zza(this.zzaNH.position(), this.zzaNH.limit());
  }

  public void zzeA(String paramString)
    throws IOException
  {
    try
    {
      int i = zzjz(paramString.length());
      if (i == zzjz(3 * paramString.length()))
      {
        int j = this.zzaNH.position();
        this.zzaNH.position(j + i);
        zza(paramString, this.zzaNH);
        int k = this.zzaNH.position();
        this.zzaNH.position(j);
        zzjy(k - j - i);
        this.zzaNH.position(k);
        return;
      }
      zzjy(zza(paramString));
      zza(paramString, this.zzaNH);
      return;
    }
    catch (BufferOverflowException localBufferOverflowException)
    {
    }
    throw new zza(this.zzaNH.position(), this.zzaNH.limit());
  }

  public void zzf(double paramDouble)
    throws IOException
  {
    zzab(Double.doubleToLongBits(paramDouble));
  }

  public void zzi(float paramFloat)
    throws IOException
  {
    zzjA(Float.floatToIntBits(paramFloat));
  }

  public void zzjA(int paramInt)
    throws IOException
  {
    zzjw(paramInt & 0xFF);
    zzjw(0xFF & paramInt >> 8);
    zzjw(0xFF & paramInt >> 16);
    zzjw(0xFF & paramInt >> 24);
  }

  public void zzjs(int paramInt)
    throws IOException
  {
    if (paramInt >= 0)
    {
      zzjy(paramInt);
      return;
    }
    zzZ(paramInt);
  }

  public void zzjt(int paramInt)
    throws IOException
  {
    zzjy(zzjB(paramInt));
  }

  public void zzjw(int paramInt)
    throws IOException
  {
    zzb((byte)paramInt);
  }

  public void zzjy(int paramInt)
    throws IOException
  {
    while (true)
    {
      if ((paramInt & 0xFFFFFF80) == 0)
      {
        zzjw(paramInt);
        return;
      }
      zzjw(0x80 | paramInt & 0x7F);
      paramInt >>>= 7;
    }
  }

  public void zzx(int paramInt1, int paramInt2)
    throws IOException
  {
    zzB(paramInt1, 0);
    zzjs(paramInt2);
  }

  public void zzx(byte[] paramArrayOfByte)
    throws IOException
  {
    zzjy(paramArrayOfByte.length);
    zzz(paramArrayOfByte);
  }

  public void zzy(int paramInt1, int paramInt2)
    throws IOException
  {
    zzB(paramInt1, 0);
    zzjt(paramInt2);
  }

  public void zzz(byte[] paramArrayOfByte)
    throws IOException
  {
    zzc(paramArrayOfByte, 0, paramArrayOfByte.length);
  }

  public int zzzN()
  {
    return this.zzaNH.remaining();
  }

  public void zzzO()
  {
    if (zzzN() != 0)
      throw new IllegalStateException("Did not write as much data as expected.");
  }

  public static class zza extends IOException
  {
    zza(int paramInt1, int paramInt2)
    {
      super();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznr
 * JD-Core Version:    0.6.2
 */