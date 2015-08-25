package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

final class zzoa
{
  final int tag;
  final byte[] zzaNU;

  zzoa(int paramInt, byte[] paramArrayOfByte)
  {
    this.tag = paramInt;
    this.zzaNU = paramArrayOfByte;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == this);
    zzoa localzzoa;
    do
    {
      return true;
      if (!(paramObject instanceof zzoa))
        return false;
      localzzoa = (zzoa)paramObject;
    }
    while ((this.tag == localzzoa.tag) && (Arrays.equals(this.zzaNU, localzzoa.zzaNU)));
    return false;
  }

  public int hashCode()
  {
    return 31 * (527 + this.tag) + Arrays.hashCode(this.zzaNU);
  }

  void zza(zznr paramzznr)
    throws IOException
  {
    paramzznr.zzjy(this.tag);
    paramzznr.zzz(this.zzaNU);
  }

  int zzc()
  {
    return 0 + zznr.zzjz(this.tag) + this.zzaNU.length;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzoa
 * JD-Core Version:    0.6.2
 */