package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

public class zzg
  implements DialogInterface.OnClickListener
{
  private final Intent mIntent;
  private final Fragment zzTb;
  private final int zzTc;
  private final Activity zzpf;

  public zzg(Activity paramActivity, Intent paramIntent, int paramInt)
  {
    this.zzpf = paramActivity;
    this.zzTb = null;
    this.mIntent = paramIntent;
    this.zzTc = paramInt;
  }

  public zzg(Fragment paramFragment, Intent paramIntent, int paramInt)
  {
    this.zzpf = null;
    this.zzTb = paramFragment;
    this.mIntent = paramIntent;
    this.zzTc = paramInt;
  }

  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    try
    {
      if ((this.mIntent != null) && (this.zzTb != null))
        this.zzTb.startActivityForResult(this.mIntent, this.zzTc);
      while (true)
      {
        paramDialogInterface.dismiss();
        return;
        if (this.mIntent != null)
          this.zzpf.startActivityForResult(this.mIntent, this.zzTc);
      }
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      Log.e("SettingsRedirect", "Can't redirect to app settings for Google Play services");
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzg
 * JD-Core Version:    0.6.2
 */