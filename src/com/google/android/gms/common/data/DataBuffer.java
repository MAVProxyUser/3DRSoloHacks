package com.google.android.gms.common.data;

import android.os.Bundle;
import com.google.android.gms.common.api.Releasable;
import java.util.Iterator;

public abstract interface DataBuffer<T> extends Releasable, Iterable<T>
{
  @Deprecated
  public abstract void close();

  public abstract T get(int paramInt);

  public abstract int getCount();

  @Deprecated
  public abstract boolean isClosed();

  public abstract Iterator<T> iterator();

  public abstract void release();

  public abstract Iterator<T> singleRefIterator();

  public abstract Bundle zzlm();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.DataBuffer
 * JD-Core Version:    0.6.2
 */