package com.google.android.gms.common.data;

import java.util.ArrayList;
import java.util.Iterator;

public final class FreezableUtils
{
  public static <T, E extends Freezable<T>> ArrayList<T> freeze(ArrayList<E> paramArrayList)
  {
    ArrayList localArrayList = new ArrayList(paramArrayList.size());
    int i = paramArrayList.size();
    for (int j = 0; j < i; j++)
      localArrayList.add(((Freezable)paramArrayList.get(j)).freeze());
    return localArrayList;
  }

  public static <T, E extends Freezable<T>> ArrayList<T> freeze(E[] paramArrayOfE)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfE.length);
    for (int i = 0; i < paramArrayOfE.length; i++)
      localArrayList.add(paramArrayOfE[i].freeze());
    return localArrayList;
  }

  public static <T, E extends Freezable<T>> ArrayList<T> freezeIterable(Iterable<E> paramIterable)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramIterable.iterator();
    while (localIterator.hasNext())
      localArrayList.add(((Freezable)localIterator.next()).freeze());
    return localArrayList;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.FreezableUtils
 * JD-Core Version:    0.6.2
 */