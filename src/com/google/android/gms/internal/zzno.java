package com.google.android.gms.internal;

import java.io.IOException;

public final class zzno extends zzns<zzno>
{
  public String[] zzaNs;
  public int[] zzaNt;
  public byte[][] zzaNu;

  public zzno()
  {
    zzzx();
  }

  public static zzno zzt(byte[] paramArrayOfByte)
    throws zznx
  {
    return (zzno)zzny.zza(new zzno(), paramArrayOfByte);
  }

  public boolean equals(Object paramObject)
  {
    boolean bool2;
    if (paramObject == this)
      bool2 = true;
    zzno localzzno;
    boolean bool5;
    do
    {
      boolean bool4;
      do
      {
        boolean bool3;
        do
        {
          boolean bool1;
          do
          {
            return bool2;
            bool1 = paramObject instanceof zzno;
            bool2 = false;
          }
          while (!bool1);
          localzzno = (zzno)paramObject;
          bool3 = zznw.equals(this.zzaNs, localzzno.zzaNs);
          bool2 = false;
        }
        while (!bool3);
        bool4 = zznw.equals(this.zzaNt, localzzno.zzaNt);
        bool2 = false;
      }
      while (!bool4);
      bool5 = zznw.zza(this.zzaNu, localzzno.zzaNu);
      bool2 = false;
    }
    while (!bool5);
    return zza(localzzno);
  }

  public int hashCode()
  {
    return 31 * (31 * (31 * (527 + zznw.hashCode(this.zzaNs)) + zznw.hashCode(this.zzaNt)) + zznw.zza(this.zzaNu)) + zzzP();
  }

  public void zza(zznr paramzznr)
    throws IOException
  {
    if ((this.zzaNs != null) && (this.zzaNs.length > 0))
      for (int m = 0; m < this.zzaNs.length; m++)
      {
        String str = this.zzaNs[m];
        if (str != null)
          paramzznr.zzb(1, str);
      }
    if ((this.zzaNt != null) && (this.zzaNt.length > 0))
      for (int k = 0; k < this.zzaNt.length; k++)
        paramzznr.zzx(2, this.zzaNt[k]);
    if (this.zzaNu != null)
    {
      int i = this.zzaNu.length;
      int j = 0;
      if (i > 0)
        while (j < this.zzaNu.length)
        {
          byte[] arrayOfByte = this.zzaNu[j];
          if (arrayOfByte != null)
            paramzznr.zza(3, arrayOfByte);
          j++;
        }
    }
    super.zza(paramzznr);
  }

  protected int zzc()
  {
    int i = 0;
    int j = super.zzc();
    int i4;
    int i5;
    if ((this.zzaNs != null) && (this.zzaNs.length > 0))
    {
      int i3 = 0;
      i4 = 0;
      i5 = 0;
      while (i3 < this.zzaNs.length)
      {
        String str = this.zzaNs[i3];
        if (str != null)
        {
          i5++;
          i4 += zznr.zzeB(str);
        }
        i3++;
      }
    }
    for (int k = j + i4 + i5 * 1; ; k = j)
    {
      if ((this.zzaNt != null) && (this.zzaNt.length > 0))
      {
        int i1 = 0;
        int i2 = 0;
        while (i1 < this.zzaNt.length)
        {
          i2 += zznr.zzju(this.zzaNt[i1]);
          i1++;
        }
        k = k + i2 + 1 * this.zzaNt.length;
      }
      if ((this.zzaNu != null) && (this.zzaNu.length > 0))
      {
        int m = 0;
        int n = 0;
        while (i < this.zzaNu.length)
        {
          byte[] arrayOfByte = this.zzaNu[i];
          if (arrayOfByte != null)
          {
            n++;
            m += zznr.zzy(arrayOfByte);
          }
          i++;
        }
        k = k + m + n * 1;
      }
      return k;
    }
  }

  public zzno zzz(zznq paramzznq)
    throws IOException
  {
    while (true)
    {
      int i = paramzznq.zzzy();
      switch (i)
      {
      default:
        if (zza(paramzznq, i))
          continue;
      case 0:
        return this;
      case 10:
        int i5 = zzob.zzb(paramzznq, 10);
        if (this.zzaNs == null);
        String[] arrayOfString;
        for (int i6 = 0; ; i6 = this.zzaNs.length)
        {
          arrayOfString = new String[i5 + i6];
          if (i6 != 0)
            System.arraycopy(this.zzaNs, 0, arrayOfString, 0, i6);
          while (i6 < -1 + arrayOfString.length)
          {
            arrayOfString[i6] = paramzznq.readString();
            paramzznq.zzzy();
            i6++;
          }
        }
        arrayOfString[i6] = paramzznq.readString();
        this.zzaNs = arrayOfString;
        break;
      case 16:
        int i3 = zzob.zzb(paramzznq, 16);
        if (this.zzaNt == null);
        int[] arrayOfInt2;
        for (int i4 = 0; ; i4 = this.zzaNt.length)
        {
          arrayOfInt2 = new int[i3 + i4];
          if (i4 != 0)
            System.arraycopy(this.zzaNt, 0, arrayOfInt2, 0, i4);
          while (i4 < -1 + arrayOfInt2.length)
          {
            arrayOfInt2[i4] = paramzznq.zzzB();
            paramzznq.zzzy();
            i4++;
          }
        }
        arrayOfInt2[i4] = paramzznq.zzzB();
        this.zzaNt = arrayOfInt2;
        break;
      case 18:
        int m = paramzznq.zzjn(paramzznq.zzzF());
        int n = paramzznq.getPosition();
        for (int i1 = 0; paramzznq.zzzK() > 0; i1++)
          paramzznq.zzzB();
        paramzznq.zzjp(n);
        if (this.zzaNt == null);
        int[] arrayOfInt1;
        for (int i2 = 0; ; i2 = this.zzaNt.length)
        {
          arrayOfInt1 = new int[i1 + i2];
          if (i2 != 0)
            System.arraycopy(this.zzaNt, 0, arrayOfInt1, 0, i2);
          while (i2 < arrayOfInt1.length)
          {
            arrayOfInt1[i2] = paramzznq.zzzB();
            i2++;
          }
        }
        this.zzaNt = arrayOfInt1;
        paramzznq.zzjo(m);
        break;
      case 26:
      }
      int j = zzob.zzb(paramzznq, 26);
      if (this.zzaNu == null);
      byte[][] arrayOfByte;
      for (int k = 0; ; k = this.zzaNu.length)
      {
        arrayOfByte = new byte[j + k][];
        if (k != 0)
          System.arraycopy(this.zzaNu, 0, arrayOfByte, 0, k);
        while (k < -1 + arrayOfByte.length)
        {
          arrayOfByte[k] = paramzznq.readBytes();
          paramzznq.zzzy();
          k++;
        }
      }
      arrayOfByte[k] = paramzznq.readBytes();
      this.zzaNu = arrayOfByte;
    }
  }

  public zzno zzzx()
  {
    this.zzaNs = zzob.zzaOa;
    this.zzaNt = zzob.zzaNV;
    this.zzaNu = zzob.zzaOb;
    this.zzaNI = null;
    this.zzaNT = -1;
    return this;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzno
 * JD-Core Version:    0.6.2
 */