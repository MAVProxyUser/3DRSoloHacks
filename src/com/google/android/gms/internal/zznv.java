package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

class zznv
  implements Cloneable
{
  private zznt<?, ?> zzaNP;
  private Object zzaNQ;
  private List<zzoa> zzaNR = new ArrayList();

  private byte[] toByteArray()
    throws IOException
  {
    byte[] arrayOfByte = new byte[zzc()];
    zza(zznr.zzw(arrayOfByte));
    return arrayOfByte;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool2;
    if (paramObject == this)
      bool2 = true;
    zznv localzznv;
    zznt localzznt1;
    zznt localzznt2;
    do
    {
      boolean bool1;
      do
      {
        return bool2;
        bool1 = paramObject instanceof zznv;
        bool2 = false;
      }
      while (!bool1);
      localzznv = (zznv)paramObject;
      if ((this.zzaNQ == null) || (localzznv.zzaNQ == null))
        break;
      localzznt1 = this.zzaNP;
      localzznt2 = localzznv.zzaNP;
      bool2 = false;
    }
    while (localzznt1 != localzznt2);
    if (!this.zzaNP.zzaNJ.isArray())
      return this.zzaNQ.equals(localzznv.zzaNQ);
    if ((this.zzaNQ instanceof byte[]))
      return Arrays.equals((byte[])this.zzaNQ, (byte[])localzznv.zzaNQ);
    if ((this.zzaNQ instanceof int[]))
      return Arrays.equals((int[])this.zzaNQ, (int[])localzznv.zzaNQ);
    if ((this.zzaNQ instanceof long[]))
      return Arrays.equals((long[])this.zzaNQ, (long[])localzznv.zzaNQ);
    if ((this.zzaNQ instanceof float[]))
      return Arrays.equals((float[])this.zzaNQ, (float[])localzznv.zzaNQ);
    if ((this.zzaNQ instanceof double[]))
      return Arrays.equals((double[])this.zzaNQ, (double[])localzznv.zzaNQ);
    if ((this.zzaNQ instanceof boolean[]))
      return Arrays.equals((boolean[])this.zzaNQ, (boolean[])localzznv.zzaNQ);
    return Arrays.deepEquals((Object[])this.zzaNQ, (Object[])localzznv.zzaNQ);
    if ((this.zzaNR != null) && (localzznv.zzaNR != null))
      return this.zzaNR.equals(localzznv.zzaNR);
    try
    {
      boolean bool3 = Arrays.equals(toByteArray(), localzznv.toByteArray());
      return bool3;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
  }

  public int hashCode()
  {
    try
    {
      int i = Arrays.hashCode(toByteArray());
      return i + 527;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
  }

  void zza(zznr paramzznr)
    throws IOException
  {
    if (this.zzaNQ != null)
      this.zzaNP.zza(this.zzaNQ, paramzznr);
    while (true)
    {
      return;
      Iterator localIterator = this.zzaNR.iterator();
      while (localIterator.hasNext())
        ((zzoa)localIterator.next()).zza(paramzznr);
    }
  }

  void zza(zzoa paramzzoa)
  {
    this.zzaNR.add(paramzzoa);
  }

  <T> T zzb(zznt<?, T> paramzznt)
  {
    if (this.zzaNQ != null)
    {
      if (this.zzaNP != paramzznt)
        throw new IllegalStateException("Tried to getExtension with a differernt Extension.");
    }
    else
    {
      this.zzaNP = paramzznt;
      this.zzaNQ = paramzznt.zzy(this.zzaNR);
      this.zzaNR = null;
    }
    return this.zzaNQ;
  }

  int zzc()
  {
    int i;
    if (this.zzaNQ != null)
      i = this.zzaNP.zzM(this.zzaNQ);
    while (true)
    {
      return i;
      Iterator localIterator = this.zzaNR.iterator();
      i = 0;
      while (localIterator.hasNext())
        i += ((zzoa)localIterator.next()).zzc();
    }
  }

  public final zznv zzzT()
  {
    int i = 0;
    zznv localzznv = new zznv();
    try
    {
      localzznv.zzaNP = this.zzaNP;
      if (this.zzaNR == null)
        localzznv.zzaNR = null;
      while (this.zzaNQ == null)
      {
        return localzznv;
        localzznv.zzaNR.addAll(this.zzaNR);
      }
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      throw new AssertionError(localCloneNotSupportedException);
    }
    if ((this.zzaNQ instanceof zzny))
    {
      localzznv.zzaNQ = ((zzny)this.zzaNQ).zzzR();
      return localzznv;
    }
    if ((this.zzaNQ instanceof byte[]))
    {
      localzznv.zzaNQ = ((byte[])this.zzaNQ).clone();
      return localzznv;
    }
    if ((this.zzaNQ instanceof byte[][]))
    {
      byte[][] arrayOfByte = (byte[][])this.zzaNQ;
      byte[][] arrayOfByte1 = new byte[arrayOfByte.length][];
      localzznv.zzaNQ = arrayOfByte1;
      for (int j = 0; j < arrayOfByte.length; j++)
        arrayOfByte1[j] = ((byte[])arrayOfByte[j].clone());
    }
    if ((this.zzaNQ instanceof boolean[]))
    {
      localzznv.zzaNQ = ((boolean[])this.zzaNQ).clone();
      return localzznv;
    }
    if ((this.zzaNQ instanceof int[]))
    {
      localzznv.zzaNQ = ((int[])this.zzaNQ).clone();
      return localzznv;
    }
    if ((this.zzaNQ instanceof long[]))
    {
      localzznv.zzaNQ = ((long[])this.zzaNQ).clone();
      return localzznv;
    }
    if ((this.zzaNQ instanceof float[]))
    {
      localzznv.zzaNQ = ((float[])this.zzaNQ).clone();
      return localzznv;
    }
    if ((this.zzaNQ instanceof double[]))
    {
      localzznv.zzaNQ = ((double[])this.zzaNQ).clone();
      return localzznv;
    }
    if ((this.zzaNQ instanceof zzny[]))
    {
      zzny[] arrayOfzzny1 = (zzny[])this.zzaNQ;
      zzny[] arrayOfzzny2 = new zzny[arrayOfzzny1.length];
      localzznv.zzaNQ = arrayOfzzny2;
      while (i < arrayOfzzny1.length)
      {
        arrayOfzzny2[i] = arrayOfzzny1[i].zzzR();
        i++;
      }
    }
    return localzznv;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznv
 * JD-Core Version:    0.6.2
 */