package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import com.google.android.gms.common.internal.zzu;

public final class zzhg extends zzhp<zza, Drawable>
{
  public zzhg()
  {
    super(10);
  }

  public static final class zza
  {
    public final int zzSP;
    public final int zzSQ;

    public zza(int paramInt1, int paramInt2)
    {
      this.zzSP = paramInt1;
      this.zzSQ = paramInt2;
    }

    public boolean equals(Object paramObject)
    {
      boolean bool = true;
      if (!(paramObject instanceof zza))
        bool = false;
      zza localzza;
      do
      {
        do
          return bool;
        while (this == paramObject);
        localzza = (zza)paramObject;
      }
      while ((localzza.zzSP == this.zzSP) && (localzza.zzSQ == this.zzSQ));
      return false;
    }

    public int hashCode()
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(this.zzSP);
      arrayOfObject[1] = Integer.valueOf(this.zzSQ);
      return zzu.hashCode(arrayOfObject);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzhg
 * JD-Core Version:    0.6.2
 */