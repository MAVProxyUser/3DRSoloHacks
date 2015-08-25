package com.google.android.gms.common.stats;

import com.google.android.gms.internal.zzhc;

public final class zzc
{
  public static zzhc<Boolean> zzVj = zzhc.zzg("gms:common:stats:debug", false);

  public static final class zza
  {
    public static zzhc<Integer> zzVk = zzhc.zza("gms:common:stats:connections:level", Integer.valueOf(zzd.zzVq));
    public static zzhc<String> zzVl = zzhc.zzr("gms:common:stats:connections:ignored_calling_processes", "");
    public static zzhc<String> zzVm = zzhc.zzr("gms:common:stats:connections:ignored_calling_services", "");
    public static zzhc<String> zzVn = zzhc.zzr("gms:common:stats:connections:ignored_target_processes", "");
    public static zzhc<String> zzVo = zzhc.zzr("gms:common:stats:connections:ignored_target_services", "com.google.android.gms.auth.GetToken");
    public static zzhc<Long> zzVp = zzhc.zza("gms:common:stats:connections:time_out_duration", Long.valueOf(600000L));
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zzc
 * JD-Core Version:    0.6.2
 */