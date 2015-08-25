package com.google.android.gms.common.api;

import com.google.android.gms.common.data.DataHolder;

public abstract class zzb<L>
  implements zzi.zzb<L>
{
  private final DataHolder zzPy;

  protected zzb(DataHolder paramDataHolder)
  {
    this.zzPy = paramDataHolder;
  }

  protected abstract void zza(L paramL, DataHolder paramDataHolder);

  public final void zzk(L paramL)
  {
    zza(paramL, this.zzPy);
  }

  public void zzkJ()
  {
    if (this.zzPy != null)
      this.zzPy.close();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzb
 * JD-Core Version:    0.6.2
 */