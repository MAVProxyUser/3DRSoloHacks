package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.Iterator;

public abstract class AbstractDataBuffer<T>
  implements DataBuffer<T>
{
  protected final DataHolder zzPy;

  protected AbstractDataBuffer(DataHolder paramDataHolder)
  {
    this.zzPy = paramDataHolder;
    if (this.zzPy != null)
      this.zzPy.zzm(this);
  }

  @Deprecated
  public final void close()
  {
    release();
  }

  public abstract T get(int paramInt);

  public int getCount()
  {
    if (this.zzPy == null)
      return 0;
    return this.zzPy.getCount();
  }

  @Deprecated
  public boolean isClosed()
  {
    return (this.zzPy == null) || (this.zzPy.isClosed());
  }

  public Iterator<T> iterator()
  {
    return new zzb(this);
  }

  public void release()
  {
    if (this.zzPy != null)
      this.zzPy.close();
  }

  public Iterator<T> singleRefIterator()
  {
    return new zzg(this);
  }

  public Bundle zzlm()
  {
    return this.zzPy.zzlm();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.AbstractDataBuffer
 * JD-Core Version:    0.6.2
 */