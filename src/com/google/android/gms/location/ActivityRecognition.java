package com.google.android.gms.location;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzb;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.location.internal.zza;
import com.google.android.gms.location.internal.zzj;

public class ActivityRecognition
{
  public static final Api<Api.ApiOptions.NoOptions> API = new Api("ActivityRecognition.API", zzKi, zzKh, new Scope[0]);
  public static ActivityRecognitionApi ActivityRecognitionApi = new zza();
  public static final String CLIENT_NAME = "activity_recognition";
  private static final Api.zzc<zzj> zzKh = new Api.zzc();
  private static final Api.zzb<zzj, Api.ApiOptions.NoOptions> zzKi = new ActivityRecognition.1();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.location.ActivityRecognition
 * JD-Core Version:    0.6.2
 */