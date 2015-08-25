package com.google.android.gms.common.data;

import com.google.android.gms.common.internal.zzv;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class zzb<T>
  implements Iterator<T>
{
  protected final DataBuffer<T> zzRt;
  protected int zzRu;

  public zzb(DataBuffer<T> paramDataBuffer)
  {
    this.zzRt = ((DataBuffer)zzv.zzr(paramDataBuffer));
    this.zzRu = -1;
  }

  public boolean hasNext()
  {
    return this.zzRu < -1 + this.zzRt.getCount();
  }

  public T next()
  {
    if (!hasNext())
      throw new NoSuchElementException("Cannot advance the iterator beyond " + this.zzRu);
    DataBuffer localDataBuffer = this.zzRt;
    int i = 1 + this.zzRu;
    this.zzRu = i;
    return localDataBuffer.get(i);
  }

  public void remove()
  {
    throw new UnsupportedOperationException("Cannot remove elements from a DataBufferIterator");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.zzb
 * JD-Core Version:    0.6.2
 */