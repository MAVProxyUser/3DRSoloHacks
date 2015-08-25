package com.google.android.gms.internal;

import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.zzv;
import java.util.Set;

public final class zzif
{
  public static String[] zzb(Scope[] paramArrayOfScope)
  {
    zzv.zzb(paramArrayOfScope, "scopes can't be null.");
    String[] arrayOfString = new String[paramArrayOfScope.length];
    for (int i = 0; i < paramArrayOfScope.length; i++)
      arrayOfString[i] = paramArrayOfScope[i].zzle();
    return arrayOfString;
  }

  public static String[] zzc(Set<Scope> paramSet)
  {
    return zzb((Scope[])paramSet.toArray(new Scope[paramSet.size()]));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzif
 * JD-Core Version:    0.6.2
 */