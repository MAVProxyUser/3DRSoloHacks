package com.google.android.gms.internal;

import java.io.IOException;

public abstract class zzns<M extends zzns<M>> extends zzny
{
  protected zznu zzaNI;

  public final <T> T zza(zznt<M, T> paramzznt)
  {
    if (this.zzaNI == null);
    zznv localzznv;
    do
    {
      return null;
      localzznv = this.zzaNI.zzjC(zzob.zzjG(paramzznt.tag));
    }
    while (localzznv == null);
    return localzznv.zzb(paramzznt);
  }

  public void zza(zznr paramzznr)
    throws IOException
  {
    if (this.zzaNI == null);
    while (true)
    {
      return;
      for (int i = 0; i < this.zzaNI.size(); i++)
        this.zzaNI.zzjD(i).zza(paramzznr);
    }
  }

  protected final boolean zza(zznq paramzznq, int paramInt)
    throws IOException
  {
    int i = paramzznq.getPosition();
    if (!paramzznq.zzjl(paramInt))
      return false;
    int j = zzob.zzjG(paramInt);
    zzoa localzzoa = new zzoa(paramInt, paramzznq.zzw(i, paramzznq.getPosition() - i));
    zznv localzznv = null;
    if (this.zzaNI == null)
      this.zzaNI = new zznu();
    while (true)
    {
      if (localzznv == null)
      {
        localzznv = new zznv();
        this.zzaNI.zza(j, localzznv);
      }
      localzznv.zza(localzzoa);
      return true;
      localzznv = this.zzaNI.zzjC(j);
    }
  }

  protected final boolean zza(M paramM)
  {
    if ((this.zzaNI == null) || (this.zzaNI.isEmpty()))
      return (paramM.zzaNI == null) || (paramM.zzaNI.isEmpty());
    return this.zzaNI.equals(paramM.zzaNI);
  }

  protected int zzc()
  {
    int i = 0;
    if (this.zzaNI != null)
    {
      j = 0;
      while (i < this.zzaNI.size())
      {
        j += this.zzaNI.zzjD(i).zzc();
        i++;
      }
    }
    int j = 0;
    return j;
  }

  protected final int zzzP()
  {
    if ((this.zzaNI == null) || (this.zzaNI.isEmpty()))
      return 0;
    return this.zzaNI.hashCode();
  }

  public M zzzQ()
    throws CloneNotSupportedException
  {
    zzns localzzns = (zzns)super.zzzR();
    zznw.zza(this, localzzns);
    return localzzns;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzns
 * JD-Core Version:    0.6.2
 */