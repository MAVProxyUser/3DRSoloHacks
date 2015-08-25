package com.google.android.gms.internal;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class zznt<M extends zzns<M>, T>
{
  public final int tag;
  protected final int type;
  protected final Class<T> zzaNJ;
  protected final boolean zzaNK;

  private zznt(int paramInt1, Class<T> paramClass, int paramInt2, boolean paramBoolean)
  {
    this.type = paramInt1;
    this.zzaNJ = paramClass;
    this.tag = paramInt2;
    this.zzaNK = paramBoolean;
  }

  private T zzA(List<zzoa> paramList)
  {
    if (paramList.isEmpty())
      return null;
    zzoa localzzoa = (zzoa)paramList.get(-1 + paramList.size());
    return this.zzaNJ.cast(zzA(zznq.zzv(localzzoa.zzaNU)));
  }

  @Deprecated
  public static <M extends zzns<M>, T extends zzny> zznt<M, T> zza(int paramInt1, Class<T> paramClass, int paramInt2)
  {
    return new zznt(paramInt1, paramClass, paramInt2, false);
  }

  private T zzz(List<zzoa> paramList)
  {
    int i = 0;
    ArrayList localArrayList = new ArrayList();
    for (int j = 0; j < paramList.size(); j++)
    {
      zzoa localzzoa = (zzoa)paramList.get(j);
      if (localzzoa.zzaNU.length != 0)
        zza(localzzoa, localArrayList);
    }
    int k = localArrayList.size();
    Object localObject;
    if (k == 0)
      localObject = null;
    while (true)
    {
      return localObject;
      localObject = this.zzaNJ.cast(Array.newInstance(this.zzaNJ.getComponentType(), k));
      while (i < k)
      {
        Array.set(localObject, i, localArrayList.get(i));
        i++;
      }
    }
  }

  protected Object zzA(zznq paramzznq)
  {
    Class localClass;
    if (this.zzaNK)
      localClass = this.zzaNJ.getComponentType();
    try
    {
      switch (this.type)
      {
      default:
        throw new IllegalArgumentException("Unknown type " + this.type);
      case 10:
      case 11:
      }
    }
    catch (InstantiationException localInstantiationException)
    {
      while (true)
      {
        throw new IllegalArgumentException("Error creating instance of class " + localClass, localInstantiationException);
        localClass = this.zzaNJ;
      }
      zzny localzzny2 = (zzny)localClass.newInstance();
      paramzznq.zza(localzzny2, zzob.zzjG(this.tag));
      return localzzny2;
      zzny localzzny1 = (zzny)localClass.newInstance();
      paramzznq.zza(localzzny1);
      return localzzny1;
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      throw new IllegalArgumentException("Error creating instance of class " + localClass, localIllegalAccessException);
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("Error reading extension field", localIOException);
    }
  }

  int zzM(Object paramObject)
  {
    if (this.zzaNK)
      return zzN(paramObject);
    return zzO(paramObject);
  }

  protected int zzN(Object paramObject)
  {
    int i = 0;
    int j = Array.getLength(paramObject);
    for (int k = 0; k < j; k++)
      if (Array.get(paramObject, k) != null)
        i += zzO(Array.get(paramObject, k));
    return i;
  }

  protected int zzO(Object paramObject)
  {
    int i = zzob.zzjG(this.tag);
    switch (this.type)
    {
    default:
      throw new IllegalArgumentException("Unknown type " + this.type);
    case 10:
      return zznr.zzb(i, (zzny)paramObject);
    case 11:
    }
    return zznr.zzc(i, (zzny)paramObject);
  }

  protected void zza(zzoa paramzzoa, List<Object> paramList)
  {
    paramList.add(zzA(zznq.zzv(paramzzoa.zzaNU)));
  }

  void zza(Object paramObject, zznr paramzznr)
    throws IOException
  {
    if (this.zzaNK)
    {
      zzc(paramObject, paramzznr);
      return;
    }
    zzb(paramObject, paramzznr);
  }

  protected void zzb(Object paramObject, zznr paramzznr)
  {
    try
    {
      paramzznr.zzjy(this.tag);
      switch (this.type)
      {
      default:
        throw new IllegalArgumentException("Unknown type " + this.type);
      case 10:
      case 11:
      }
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException(localIOException);
    }
    zzny localzzny = (zzny)paramObject;
    int i = zzob.zzjG(this.tag);
    paramzznr.zzb(localzzny);
    paramzznr.zzB(i, 4);
    return;
    paramzznr.zzc((zzny)paramObject);
  }

  protected void zzc(Object paramObject, zznr paramzznr)
  {
    int i = Array.getLength(paramObject);
    for (int j = 0; j < i; j++)
    {
      Object localObject = Array.get(paramObject, j);
      if (localObject != null)
        zzb(localObject, paramzznr);
    }
  }

  final T zzy(List<zzoa> paramList)
  {
    if (paramList == null)
      return null;
    if (this.zzaNK)
      return zzz(paramList);
    return zzA(paramList);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zznt
 * JD-Core Version:    0.6.2
 */