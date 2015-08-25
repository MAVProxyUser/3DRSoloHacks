package com.google.android.gms.common.internal;

import android.content.Context;
import android.os.IBinder;
import android.view.View;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzg;
import com.google.android.gms.dynamic.zzg.zza;

public final class zzy extends zzg<zzs>
{
  private static final zzy zzUc = new zzy();

  private zzy()
  {
    super("com.google.android.gms.common.ui.SignInButtonCreatorImpl");
  }

  public static View zzb(Context paramContext, int paramInt1, int paramInt2)
    throws zzg.zza
  {
    return zzUc.zzc(paramContext, paramInt1, paramInt2);
  }

  private View zzc(Context paramContext, int paramInt1, int paramInt2)
    throws zzg.zza
  {
    try
    {
      zzd localzzd = zze.zzt(paramContext);
      View localView = (View)zze.zzg(((zzs)zzX(paramContext)).zza(localzzd, paramInt1, paramInt2));
      return localView;
    }
    catch (Exception localException)
    {
      throw new zzg.zza("Could not get button with size " + paramInt1 + " and color " + paramInt2, localException);
    }
  }

  public zzs zzW(IBinder paramIBinder)
  {
    return zzs.zza.zzV(paramIBinder);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzy
 * JD-Core Version:    0.6.2
 */