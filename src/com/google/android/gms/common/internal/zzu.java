package com.google.android.gms.common.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class zzu
{
  public static boolean equal(Object paramObject1, Object paramObject2)
  {
    return (paramObject1 == paramObject2) || ((paramObject1 != null) && (paramObject1.equals(paramObject2)));
  }

  public static int hashCode(Object[] paramArrayOfObject)
  {
    return Arrays.hashCode(paramArrayOfObject);
  }

  public static zza zzq(Object paramObject)
  {
    return new zza(paramObject, null);
  }

  public static final class zza
  {
    private final Object zzCG;
    private final List<String> zzTZ;

    private zza(Object paramObject)
    {
      this.zzCG = zzv.zzr(paramObject);
      this.zzTZ = new ArrayList();
    }

    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder(100).append(this.zzCG.getClass().getSimpleName()).append('{');
      int i = this.zzTZ.size();
      for (int j = 0; j < i; j++)
      {
        localStringBuilder.append((String)this.zzTZ.get(j));
        if (j < i - 1)
          localStringBuilder.append(", ");
      }
      return '}';
    }

    public zza zzg(String paramString, Object paramObject)
    {
      this.zzTZ.add((String)zzv.zzr(paramString) + "=" + String.valueOf(paramObject));
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzu
 * JD-Core Version:    0.6.2
 */