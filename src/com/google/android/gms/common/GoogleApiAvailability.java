package com.google.android.gms.common;

import android.app.Activity;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;

public class GoogleApiAvailability
{
  public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";
  public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 7327000;
  private static final GoogleApiAvailability zzOL = new GoogleApiAvailability();

  public static GoogleApiAvailability getInstance()
  {
    return zzOL;
  }

  public Dialog getErrorDialog(Activity paramActivity, int paramInt1, int paramInt2)
  {
    return GooglePlayServicesUtil.getErrorDialog(paramInt1, paramActivity, paramInt2);
  }

  public Dialog getErrorDialog(Activity paramActivity, int paramInt1, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return GooglePlayServicesUtil.getErrorDialog(paramInt1, paramActivity, paramInt2, paramOnCancelListener);
  }

  public PendingIntent getErrorResolutionPendingIntent(Context paramContext, int paramInt1, int paramInt2)
  {
    return GooglePlayServicesUtil.getErrorPendingIntent(paramInt1, paramContext, paramInt2);
  }

  public final String getErrorString(int paramInt)
  {
    return GooglePlayServicesUtil.getErrorString(paramInt);
  }

  public String getOpenSourceSoftwareLicenseInfo(Context paramContext)
  {
    return GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(paramContext);
  }

  public int isGooglePlayServicesAvailable(Context paramContext)
  {
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(paramContext);
    if (GooglePlayServicesUtil.zze(paramContext, i))
      i = 18;
    return i;
  }

  public final boolean isUserResolvableError(int paramInt)
  {
    return GooglePlayServicesUtil.isUserRecoverableError(paramInt);
  }

  public boolean showErrorDialogFragment(Activity paramActivity, int paramInt1, int paramInt2)
  {
    return GooglePlayServicesUtil.showErrorDialogFragment(paramInt1, paramActivity, paramInt2);
  }

  public boolean showErrorDialogFragment(Activity paramActivity, int paramInt1, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return GooglePlayServicesUtil.showErrorDialogFragment(paramInt1, paramActivity, paramInt2, paramOnCancelListener);
  }

  public void showErrorNotification(Context paramContext, int paramInt)
  {
    GooglePlayServicesUtil.showErrorNotification(paramInt, paramContext);
  }

  public void zzN(Context paramContext)
    throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
  {
    GooglePlayServicesUtil.zzM(paramContext);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.GoogleApiAvailability
 * JD-Core Version:    0.6.2
 */