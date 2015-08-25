package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.google.android.gms.R.string;
import com.google.android.gms.internal.zzhw;

public final class zzf
{
  public static final String zzg(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    switch (paramInt)
    {
    default:
      Log.e("GooglePlayServicesUtil", "Unexpected error code " + paramInt);
    case 4:
    case 6:
      return null;
    case 1:
      return localResources.getString(R.string.common_google_play_services_install_title);
    case 3:
      return localResources.getString(R.string.common_google_play_services_enable_title);
    case 2:
    case 18:
      return localResources.getString(R.string.common_google_play_services_update_title);
    case 42:
      return localResources.getString(R.string.common_android_wear_update_title);
    case 9:
      Log.e("GooglePlayServicesUtil", "Google Play services is invalid. Cannot recover.");
      return localResources.getString(R.string.common_google_play_services_unsupported_title);
    case 7:
      Log.e("GooglePlayServicesUtil", "Network error occurred. Please retry request later.");
      return localResources.getString(R.string.common_google_play_services_network_error_title);
    case 8:
      Log.e("GooglePlayServicesUtil", "Internal error occurred. Please see logs for detailed information");
      return null;
    case 10:
      Log.e("GooglePlayServicesUtil", "Developer error occurred. Please see logs for detailed information");
      return null;
    case 5:
      Log.e("GooglePlayServicesUtil", "An invalid account was specified when connecting. Please provide a valid account.");
      return localResources.getString(R.string.common_google_play_services_invalid_account_title);
    case 11:
      Log.e("GooglePlayServicesUtil", "The application is not licensed to the user.");
      return null;
    case 16:
      Log.e("GooglePlayServicesUtil", "One of the API components you attempted to connect to is not available.");
      return null;
    case 17:
    }
    Log.e("GooglePlayServicesUtil", "The specified account could not be signed in.");
    return localResources.getString(R.string.common_google_play_services_sign_in_failed_title);
  }

  public static String zzh(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    switch (paramInt)
    {
    default:
      return localResources.getString(R.string.common_google_play_services_unknown_issue);
    case 1:
      if (zzhw.zzb(paramContext.getResources()))
        return localResources.getString(R.string.common_google_play_services_install_text_tablet);
      return localResources.getString(R.string.common_google_play_services_install_text_phone);
    case 3:
      return localResources.getString(R.string.common_google_play_services_enable_text);
    case 2:
    case 18:
      return localResources.getString(R.string.common_google_play_services_update_text);
    case 42:
      return localResources.getString(R.string.common_android_wear_update_text);
    case 9:
      return localResources.getString(R.string.common_google_play_services_unsupported_text);
    case 7:
      return localResources.getString(R.string.common_google_play_services_network_error_text);
    case 5:
      return localResources.getString(R.string.common_google_play_services_invalid_account_text);
    case 16:
      return localResources.getString(R.string.commono_google_play_services_api_unavailable_text);
    case 17:
    }
    return localResources.getString(R.string.common_google_play_services_sign_in_failed_text);
  }

  public static String zzi(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    switch (paramInt)
    {
    default:
      return localResources.getString(17039370);
    case 1:
      return localResources.getString(R.string.common_google_play_services_install_button);
    case 3:
      return localResources.getString(R.string.common_google_play_services_enable_button);
    case 2:
    case 18:
    case 42:
    }
    return localResources.getString(R.string.common_google_play_services_update_button);
  }

  public static String zzj(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    switch (paramInt)
    {
    default:
      return localResources.getString(R.string.common_google_play_services_unknown_issue);
    case 1:
      return localResources.getString(R.string.common_google_play_services_notification_needs_installation_title);
    case 2:
    case 18:
      return localResources.getString(R.string.common_google_play_services_notification_needs_update_title);
    case 42:
      return localResources.getString(R.string.common_android_wear_notification_needs_update_text);
    case 3:
      return localResources.getString(R.string.common_google_play_services_needs_enabling_title);
    case 9:
      return localResources.getString(R.string.common_google_play_services_unsupported_text);
    case 7:
      return localResources.getString(R.string.common_google_play_services_network_error_text);
    case 5:
      return localResources.getString(R.string.common_google_play_services_invalid_account_text);
    case 16:
      return localResources.getString(R.string.commono_google_play_services_api_unavailable_text);
    case 17:
    }
    return localResources.getString(R.string.common_google_play_services_sign_in_failed_text);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzf
 * JD-Core Version:    0.6.2
 */