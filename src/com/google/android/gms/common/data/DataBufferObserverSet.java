package com.google.android.gms.common.data;

import java.util.HashSet;
import java.util.Iterator;

public final class DataBufferObserverSet
  implements DataBufferObserver, DataBufferObserver.Observable
{
  private HashSet<DataBufferObserver> zzRv = new HashSet();

  public void addObserver(DataBufferObserver paramDataBufferObserver)
  {
    this.zzRv.add(paramDataBufferObserver);
  }

  public void clear()
  {
    this.zzRv.clear();
  }

  public boolean hasObservers()
  {
    return !this.zzRv.isEmpty();
  }

  public void onDataChanged()
  {
    Iterator localIterator = this.zzRv.iterator();
    while (localIterator.hasNext())
      ((DataBufferObserver.Observable)localIterator.next()).onDataChanged();
  }

  public void onDataRangeChanged(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.zzRv.iterator();
    while (localIterator.hasNext())
      ((DataBufferObserver.Observable)localIterator.next()).onDataRangeChanged(paramInt1, paramInt2);
  }

  public void onDataRangeInserted(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.zzRv.iterator();
    while (localIterator.hasNext())
      ((DataBufferObserver.Observable)localIterator.next()).onDataRangeInserted(paramInt1, paramInt2);
  }

  public void onDataRangeMoved(int paramInt1, int paramInt2, int paramInt3)
  {
    Iterator localIterator = this.zzRv.iterator();
    while (localIterator.hasNext())
      ((DataBufferObserver.Observable)localIterator.next()).onDataRangeMoved(paramInt1, paramInt2, paramInt3);
  }

  public void onDataRangeRemoved(int paramInt1, int paramInt2)
  {
    Iterator localIterator = this.zzRv.iterator();
    while (localIterator.hasNext())
      ((DataBufferObserver.Observable)localIterator.next()).onDataRangeRemoved(paramInt1, paramInt2);
  }

  public void removeObserver(DataBufferObserver paramDataBufferObserver)
  {
    this.zzRv.remove(paramDataBufferObserver);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.DataBufferObserverSet
 * JD-Core Version:    0.6.2
 */