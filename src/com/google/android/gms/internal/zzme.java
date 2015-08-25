package com.google.android.gms.internal;

import com.google.android.gms.common.api.Api.ApiOptions.Optional;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks;
import com.google.android.gms.common.internal.zzv;

public final class zzme
  implements Api.ApiOptions.Optional
{
  public static final zzme zzaBD = new zza().zzwi();
  private final boolean zzaBE;
  private final boolean zzaBF;
  private final String zzaBG;
  private final GoogleApiClient.ServerAuthCodeCallbacks zzaBH;

  private zzme(boolean paramBoolean1, boolean paramBoolean2, String paramString, GoogleApiClient.ServerAuthCodeCallbacks paramServerAuthCodeCallbacks)
  {
    this.zzaBE = paramBoolean1;
    this.zzaBF = paramBoolean2;
    this.zzaBG = paramString;
    this.zzaBH = paramServerAuthCodeCallbacks;
  }

  public String zzvx()
  {
    return this.zzaBG;
  }

  public boolean zzwf()
  {
    return this.zzaBE;
  }

  public boolean zzwg()
  {
    return this.zzaBF;
  }

  public GoogleApiClient.ServerAuthCodeCallbacks zzwh()
  {
    return this.zzaBH;
  }

  public static final class zza
  {
    private boolean zzaBI;
    private boolean zzaBJ;
    private GoogleApiClient.ServerAuthCodeCallbacks zzaBK;
    private String zzayN;

    private String zzds(String paramString)
    {
      zzv.zzr(paramString);
      if ((this.zzayN == null) || (this.zzayN.equals(paramString)));
      for (boolean bool = true; ; bool = false)
      {
        zzv.zzb(bool, "two different server client ids provided");
        return paramString;
      }
    }

    public zza zza(String paramString, GoogleApiClient.ServerAuthCodeCallbacks paramServerAuthCodeCallbacks)
    {
      this.zzaBI = true;
      this.zzaBJ = true;
      this.zzayN = zzds(paramString);
      this.zzaBK = ((GoogleApiClient.ServerAuthCodeCallbacks)zzv.zzr(paramServerAuthCodeCallbacks));
      return this;
    }

    public zzme zzwi()
    {
      return new zzme(this.zzaBI, this.zzaBJ, this.zzayN, this.zzaBK, null);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.zzme
 * JD-Core Version:    0.6.2
 */