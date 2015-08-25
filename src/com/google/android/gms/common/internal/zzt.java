package com.google.android.gms.common.internal;

import java.util.Iterator;

public class zzt
{
  private final String separator;

  private zzt(String paramString)
  {
    this.separator = paramString;
  }

  public static zzt zzbR(String paramString)
  {
    return new zzt(paramString);
  }

  public final String zza(Iterable<?> paramIterable)
  {
    return zza(new StringBuilder(), paramIterable).toString();
  }

  public final StringBuilder zza(StringBuilder paramStringBuilder, Iterable<?> paramIterable)
  {
    Iterator localIterator = paramIterable.iterator();
    if (localIterator.hasNext())
    {
      paramStringBuilder.append(zzp(localIterator.next()));
      while (localIterator.hasNext())
      {
        paramStringBuilder.append(this.separator);
        paramStringBuilder.append(zzp(localIterator.next()));
      }
    }
    return paramStringBuilder;
  }

  CharSequence zzp(Object paramObject)
  {
    if ((paramObject instanceof CharSequence))
      return (CharSequence)paramObject;
    return paramObject.toString();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzt
 * JD-Core Version:    0.6.2
 */