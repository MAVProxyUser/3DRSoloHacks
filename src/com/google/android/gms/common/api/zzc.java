package com.google.android.gms.common.api;

import com.google.android.gms.common.data.DataHolder;

public abstract class zzc
  implements Releasable, Result
{
  protected final Status zzKr;
  protected final DataHolder zzPy;

  protected zzc(DataHolder paramDataHolder)
  {
    this(paramDataHolder, new Status(paramDataHolder.getStatusCode()));
  }

  protected zzc(DataHolder paramDataHolder, Status paramStatus)
  {
    this.zzKr = paramStatus;
    this.zzPy = paramDataHolder;
  }

  public Status getStatus()
  {
    return this.zzKr;
  }

  public void release()
  {
    if (this.zzPy != null)
      this.zzPy.close();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzc
 * JD-Core Version:    0.6.2
 */