package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.view.View;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzme;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class zze
{
  private final Account zzJc;
  private final int zzPA;
  private final View zzPB;
  private final String zzPC;
  private final String zzPD;
  private final Set<Scope> zzPz;
  private final Set<Scope> zzSW;
  private final Map<Api<?>, zza> zzSX;
  private final zzme zzSY;
  private Integer zzSZ;

  public zze(Account paramAccount, Collection<Scope> paramCollection, Map<Api<?>, zza> paramMap, int paramInt, View paramView, String paramString1, String paramString2, zzme paramzzme)
  {
    this.zzJc = paramAccount;
    if (paramCollection == null);
    HashSet localHashSet;
    for (Set localSet = Collections.EMPTY_SET; ; localSet = Collections.unmodifiableSet(new HashSet(paramCollection)))
    {
      this.zzPz = localSet;
      if (paramMap == null)
        paramMap = Collections.EMPTY_MAP;
      this.zzSX = paramMap;
      this.zzPB = paramView;
      this.zzPA = paramInt;
      this.zzPC = paramString1;
      this.zzPD = paramString2;
      this.zzSY = paramzzme;
      localHashSet = new HashSet(this.zzPz);
      Iterator localIterator = this.zzSX.values().iterator();
      while (localIterator.hasNext())
        localHashSet.addAll(((zza)localIterator.next()).zzPP);
    }
    this.zzSW = Collections.unmodifiableSet(localHashSet);
  }

  public Account getAccount()
  {
    return this.zzJc;
  }

  @Deprecated
  public String getAccountName()
  {
    if (this.zzJc != null)
      return this.zzJc.name;
    return null;
  }

  public void zza(Integer paramInteger)
  {
    this.zzSZ = paramInteger;
  }

  public Set<Scope> zzb(Api<?> paramApi)
  {
    zza localzza = (zza)this.zzSX.get(paramApi);
    if ((localzza == null) || (localzza.zzPP.isEmpty()))
      return this.zzPz;
    HashSet localHashSet = new HashSet(this.zzPz);
    localHashSet.addAll(localzza.zzPP);
    return localHashSet;
  }

  @Deprecated
  public String zzlD()
  {
    return zzlE().name;
  }

  public Account zzlE()
  {
    if (this.zzJc != null)
      return this.zzJc;
    return new Account("<<default account>>", "com.google");
  }

  public int zzlF()
  {
    return this.zzPA;
  }

  public Set<Scope> zzlG()
  {
    return this.zzPz;
  }

  public Set<Scope> zzlH()
  {
    return this.zzSW;
  }

  public Map<Api<?>, zza> zzlI()
  {
    return this.zzSX;
  }

  public String zzlJ()
  {
    return this.zzPC;
  }

  public String zzlK()
  {
    return this.zzPD;
  }

  public View zzlL()
  {
    return this.zzPB;
  }

  public zzme zzlM()
  {
    return this.zzSY;
  }

  public Integer zzlN()
  {
    return this.zzSZ;
  }

  public static final class zza
  {
    public final Set<Scope> zzPP;
    public final boolean zzTa;

    public zza(Set<Scope> paramSet, boolean paramBoolean)
    {
      zzv.zzr(paramSet);
      this.zzPP = Collections.unmodifiableSet(paramSet);
      this.zzTa = paramBoolean;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zze
 * JD-Core Version:    0.6.2
 */