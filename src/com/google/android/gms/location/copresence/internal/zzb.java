package com.google.android.gms.location.copresence.internal;

import android.content.Context;
import com.google.android.gms.location.internal.zzg;
import com.google.android.gms.location.internal.zzn;

public class zzb
{
  private final Context mContext;
  private final String zzKw;
  private final String zzPC;
  private final CopresenceApiOptions zzaqA;
  private zzc zzaqB;
  private final zzn<zzg> zzaqz;

  private zzb(Context paramContext, String paramString1, String paramString2, zzn<zzg> paramzzn, CopresenceApiOptions paramCopresenceApiOptions)
  {
    this.mContext = paramContext;
    this.zzKw = paramString1;
    this.zzaqz = paramzzn;
    this.zzaqB = null;
    this.zzPC = paramString2;
    this.zzaqA = paramCopresenceApiOptions;
  }

  public static zzb zza(Context paramContext, String paramString1, String paramString2, zzn<zzg> paramzzn, CopresenceApiOptions paramCopresenceApiOptions)
  {
    return new zzb(paramContext, paramString1, paramString2, paramzzn, paramCopresenceApiOptions);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.copresence.internal.zzb
 * JD-Core Version:    0.6.2
 */