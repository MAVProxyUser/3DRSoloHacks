package com.google.android.gms.internal;

class zznu
  implements Cloneable
{
  private static final zznv zzaNL = new zznv();
  private int mSize;
  private boolean zzaNM = false;
  private int[] zzaNN;
  private zznv[] zzaNO;

  public zznu()
  {
    this(10);
  }

  public zznu(int paramInt)
  {
    int i = idealIntArraySize(paramInt);
    this.zzaNN = new int[i];
    this.zzaNO = new zznv[i];
    this.mSize = 0;
  }

  private void gc()
  {
    int i = this.mSize;
    int[] arrayOfInt = this.zzaNN;
    zznv[] arrayOfzznv = this.zzaNO;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      zznv localzznv = arrayOfzznv[j];
      if (localzznv != zzaNL)
      {
        if (j != k)
        {
          arrayOfInt[k] = arrayOfInt[j];
          arrayOfzznv[k] = localzznv;
          arrayOfzznv[j] = null;
        }
        k++;
      }
      j++;
    }
    this.zzaNM = false;
    this.mSize = k;
  }

  private int idealByteArraySize(int paramInt)
  {
    for (int i = 4; ; i++)
      if (i < 32)
      {
        if (paramInt <= -12 + (1 << i))
          paramInt = -12 + (1 << i);
      }
      else
        return paramInt;
  }

  private int idealIntArraySize(int paramInt)
  {
    return idealByteArraySize(paramInt * 4) / 4;
  }

  private boolean zza(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      if (paramArrayOfInt1[i] != paramArrayOfInt2[i])
        return false;
    return true;
  }

  private boolean zza(zznv[] paramArrayOfzznv1, zznv[] paramArrayOfzznv2, int paramInt)
  {
    for (int i = 0; i < paramInt; i++)
      if (!paramArrayOfzznv1[i].equals(paramArrayOfzznv2[i]))
        return false;
    return true;
  }

  private int zzjE(int paramInt)
  {
    int i = 0;
    int j = -1 + this.mSize;
    while (i <= j)
    {
      int k = i + j >>> 1;
      int m = this.zzaNN[k];
      if (m < paramInt)
        i = k + 1;
      else if (m > paramInt)
        j = k - 1;
      else
        return k;
    }
    return i ^ 0xFFFFFFFF;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this);
    zznu localzznu;
    do
    {
      return true;
      if (!(paramObject instanceof zznu))
        return false;
      localzznu = (zznu)paramObject;
      if (size() != localzznu.size())
        return false;
    }
    while ((zza(this.zzaNN, localzznu.zzaNN, this.mSize)) && (zza(this.zzaNO, localzznu.zzaNO, this.mSize)));
    return false;
  }

  public int hashCode()
  {
    if (this.zzaNM)
      gc();
    int i = 17;
    for (int j = 0; j < this.mSize; j++)
      i = 31 * (i * 31 + this.zzaNN[j]) + this.zzaNO[j].hashCode();
    return i;
  }

  public boolean isEmpty()
  {
    return size() == 0;
  }

  public int size()
  {
    if (this.zzaNM)
      gc();
    return this.mSize;
  }

  public void zza(int paramInt, zznv paramzznv)
  {
    int i = zzjE(paramInt);
    if (i >= 0)
    {
      this.zzaNO[i] = paramzznv;
      return;
    }
    int j = i ^ 0xFFFFFFFF;
    if ((j < this.mSize) && (this.zzaNO[j] == zzaNL))
    {
      this.zzaNN[j] = paramInt;
      this.zzaNO[j] = paramzznv;
      return;
    }
    if ((this.zzaNM) && (this.mSize >= this.zzaNN.length))
    {
      gc();
      j = 0xFFFFFFFF ^ zzjE(paramInt);
    }
    if (this.mSize >= this.zzaNN.length)
    {
      int k = idealIntArraySize(1 + this.mSize);
      int[] arrayOfInt = new int[k];
      zznv[] arrayOfzznv = new zznv[k];
      System.arraycopy(this.zzaNN, 0, arrayOfInt, 0, this.zzaNN.length);
      System.arraycopy(this.zzaNO, 0, arrayOfzznv, 0, this.zzaNO.length);
      this.zzaNN = arrayOfInt;
      this.zzaNO = arrayOfzznv;
    }
    if (this.mSize - j != 0)
    {
      System.arraycopy(this.zzaNN, j, this.zzaNN, j + 1, this.mSize - j);
      System.arraycopy(this.zzaNO, j, this.zzaNO, j + 1, this.mSize - j);
    }
    this.zzaNN[j] = paramInt;
    this.zzaNO[j] = paramzznv;
    this.mSize = (1 + this.mSize);
  }

  public zznv zzjC(int paramInt)
  {
    int i = zzjE(paramInt);
    if ((i < 0) || (this.zzaNO[i] == zzaNL))
      return null;
    return this.zzaNO[i];
  }

  public zznv zzjD(int paramInt)
  {
    if (this.zzaNM)
      gc();
    return this.zzaNO[paramInt];
  }

  public final zznu zzzS()
  {
    int i = 0;
    int j = size();
    zznu localzznu = new zznu(j);
    System.arraycopy(this.zzaNN, 0, localzznu.zzaNN, 0, j);
    while (i < j)
    {
      if (this.zzaNO[i] != null)
        localzznu.zzaNO[i] = this.zzaNO[i].zzzT();
      i++;
    }
    localzznu.mSize = j;
    return localzznu;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznu
 * JD-Core Version:    0.6.2
 */