package com.google.android.gms.common.data;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Iterator;

public final class DataBufferUtils
{
  public static <T, E extends Freezable<T>> ArrayList<T> freezeAndClose(DataBuffer<E> paramDataBuffer)
  {
    ArrayList localArrayList = new ArrayList(paramDataBuffer.getCount());
    try
    {
      Iterator localIterator = paramDataBuffer.iterator();
      while (localIterator.hasNext())
        localArrayList.add(((Freezable)localIterator.next()).freeze());
    }
    finally
    {
      paramDataBuffer.close();
    }
    paramDataBuffer.close();
    return localArrayList;
  }

  public static boolean hasData(DataBuffer<?> paramDataBuffer)
  {
    return (paramDataBuffer != null) && (paramDataBuffer.getCount() > 0);
  }

  public static boolean hasNextPage(DataBuffer<?> paramDataBuffer)
  {
    Bundle localBundle = paramDataBuffer.zzlm();
    return (localBundle != null) && (localBundle.getString("next_page_token") != null);
  }

  public static boolean hasPrevPage(DataBuffer<?> paramDataBuffer)
  {
    Bundle localBundle = paramDataBuffer.zzlm();
    return (localBundle != null) && (localBundle.getString("prev_page_token") != null);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.data.DataBufferUtils
 * JD-Core Version:    0.6.2
 */