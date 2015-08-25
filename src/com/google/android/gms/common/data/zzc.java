package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.internal.zzv;

public abstract class zzc
{
  protected final DataHolder zzPy;
  protected int zzRw;
  private int zzRx;

  public zzc(DataHolder paramDataHolder, int paramInt)
  {
    this.zzPy = ((DataHolder)zzv.zzr(paramDataHolder));
    zzaB(paramInt);
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof zzc;
    boolean bool2 = false;
    if (bool1)
    {
      zzc localzzc = (zzc)paramObject;
      boolean bool3 = zzu.equal(Integer.valueOf(localzzc.zzRw), Integer.valueOf(this.zzRw));
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = zzu.equal(Integer.valueOf(localzzc.zzRx), Integer.valueOf(this.zzRx));
        bool2 = false;
        if (bool4)
        {
          DataHolder localDataHolder1 = localzzc.zzPy;
          DataHolder localDataHolder2 = this.zzPy;
          bool2 = false;
          if (localDataHolder1 == localDataHolder2)
            bool2 = true;
        }
      }
    }
    return bool2;
  }

  protected boolean getBoolean(String paramString)
  {
    return this.zzPy.zze(paramString, this.zzRw, this.zzRx);
  }

  protected byte[] getByteArray(String paramString)
  {
    return this.zzPy.zzg(paramString, this.zzRw, this.zzRx);
  }

  protected float getFloat(String paramString)
  {
    return this.zzPy.zzf(paramString, this.zzRw, this.zzRx);
  }

  protected int getInteger(String paramString)
  {
    return this.zzPy.zzc(paramString, this.zzRw, this.zzRx);
  }

  protected long getLong(String paramString)
  {
    return this.zzPy.zzb(paramString, this.zzRw, this.zzRx);
  }

  protected String getString(String paramString)
  {
    return this.zzPy.zzd(paramString, this.zzRw, this.zzRx);
  }

  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(this.zzRw);
    arrayOfObject[1] = Integer.valueOf(this.zzRx);
    arrayOfObject[2] = this.zzPy;
    return zzu.hashCode(arrayOfObject);
  }

  public boolean isDataValid()
  {
    return !this.zzPy.isClosed();
  }

  protected void zza(String paramString, CharArrayBuffer paramCharArrayBuffer)
  {
    this.zzPy.zza(paramString, this.zzRw, this.zzRx, paramCharArrayBuffer);
  }

  protected void zzaB(int paramInt)
  {
    if ((paramInt >= 0) && (paramInt < this.zzPy.getCount()));
    for (boolean bool = true; ; bool = false)
    {
      zzv.zzP(bool);
      this.zzRw = paramInt;
      this.zzRx = this.zzPy.zzaD(this.zzRw);
      return;
    }
  }

  public boolean zzbF(String paramString)
  {
    return this.zzPy.zzbF(paramString);
  }

  protected Uri zzbG(String paramString)
  {
    return this.zzPy.zzh(paramString, this.zzRw, this.zzRx);
  }

  protected boolean zzbH(String paramString)
  {
    return this.zzPy.zzi(paramString, this.zzRw, this.zzRx);
  }

  protected int zzlp()
  {
    return this.zzRw;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zzc
 * JD-Core Version:    0.6.2
 */