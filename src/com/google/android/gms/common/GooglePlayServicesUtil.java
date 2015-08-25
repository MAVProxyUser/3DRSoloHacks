package com.google.android.gms.common;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.AppOpsManager;
import android.app.Dialog;
import android.app.Notification;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageInstaller.SessionInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import com.google.android.gms.R.drawable;
import com.google.android.gms.R.string;
import com.google.android.gms.common.internal.zzd;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzg;
import com.google.android.gms.common.internal.zzm;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzhs;
import com.google.android.gms.internal.zzic;
import java.util.Iterator;
import java.util.List;

public final class GooglePlayServicesUtil
{
  public static final String GMS_ERROR_DIALOG = "GooglePlayServicesErrorDialog";

  @Deprecated
  public static final String GOOGLE_PLAY_SERVICES_PACKAGE = "com.google.android.gms";

  @Deprecated
  public static final int GOOGLE_PLAY_SERVICES_VERSION_CODE = 7327000;
  public static final String GOOGLE_PLAY_STORE_PACKAGE = "com.android.vending";
  public static boolean zzOV = false;
  public static boolean zzOW = false;
  private static int zzOX = -1;
  private static String zzOY = null;
  private static Integer zzOZ = null;
  private static final Object zznu = new Object();

  @Deprecated
  public static Dialog getErrorDialog(int paramInt1, Activity paramActivity, int paramInt2)
  {
    return getErrorDialog(paramInt1, paramActivity, paramInt2, null);
  }

  @Deprecated
  public static Dialog getErrorDialog(int paramInt1, Activity paramActivity, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return zza(paramInt1, paramActivity, null, paramInt2, paramOnCancelListener);
  }

  protected static int getErrorNotificationId(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return 39789;
    case 1:
    case 3:
    case 18:
    }
    return 10436;
  }

  @Deprecated
  public static PendingIntent getErrorPendingIntent(int paramInt1, Context paramContext, int paramInt2)
  {
    Intent localIntent = zzar(paramInt1);
    if (localIntent == null)
      return null;
    return PendingIntent.getActivity(paramContext, paramInt2, localIntent, 268435456);
  }

  @Deprecated
  public static String getErrorString(int paramInt)
  {
    switch (paramInt)
    {
    case 12:
    case 13:
    case 14:
    case 15:
    case 17:
    default:
      return "UNKNOWN_ERROR_CODE";
    case 0:
      return "SUCCESS";
    case 1:
      return "SERVICE_MISSING";
    case 2:
      return "SERVICE_VERSION_UPDATE_REQUIRED";
    case 3:
      return "SERVICE_DISABLED";
    case 4:
      return "SIGN_IN_REQUIRED";
    case 5:
      return "INVALID_ACCOUNT";
    case 6:
      return "RESOLUTION_REQUIRED";
    case 7:
      return "NETWORK_ERROR";
    case 8:
      return "INTERNAL_ERROR";
    case 9:
      return "SERVICE_INVALID";
    case 10:
      return "DEVELOPER_ERROR";
    case 11:
      return "LICENSE_CHECK_FAILED";
    case 16:
      return "API_UNAVAILABLE";
    case 18:
    }
    return "SERVICE_UPDATING";
  }

  // ERROR //
  @Deprecated
  public static String getOpenSourceSoftwareLicenseInfo(Context paramContext)
  {
    // Byte code:
    //   0: new 109	android/net/Uri$Builder
    //   3: dup
    //   4: invokespecial 110	android/net/Uri$Builder:<init>	()V
    //   7: ldc 112
    //   9: invokevirtual 116	android/net/Uri$Builder:scheme	(Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   12: ldc 11
    //   14: invokevirtual 119	android/net/Uri$Builder:authority	(Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   17: ldc 121
    //   19: invokevirtual 124	android/net/Uri$Builder:appendPath	(Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   22: ldc 126
    //   24: invokevirtual 124	android/net/Uri$Builder:appendPath	(Ljava/lang/String;)Landroid/net/Uri$Builder;
    //   27: invokevirtual 130	android/net/Uri$Builder:build	()Landroid/net/Uri;
    //   30: astore_1
    //   31: aload_0
    //   32: invokevirtual 136	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   35: aload_1
    //   36: invokevirtual 142	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   39: astore 4
    //   41: new 144	java/util/Scanner
    //   44: dup
    //   45: aload 4
    //   47: invokespecial 147	java/util/Scanner:<init>	(Ljava/io/InputStream;)V
    //   50: ldc 149
    //   52: invokevirtual 153	java/util/Scanner:useDelimiter	(Ljava/lang/String;)Ljava/util/Scanner;
    //   55: invokevirtual 157	java/util/Scanner:next	()Ljava/lang/String;
    //   58: astore 7
    //   60: aload 7
    //   62: astore_3
    //   63: aload 4
    //   65: ifnull +43 -> 108
    //   68: aload 4
    //   70: invokevirtual 162	java/io/InputStream:close	()V
    //   73: aload_3
    //   74: areturn
    //   75: astore 6
    //   77: aload 4
    //   79: ifnull +31 -> 110
    //   82: aload 4
    //   84: invokevirtual 162	java/io/InputStream:close	()V
    //   87: goto +23 -> 110
    //   90: astore 5
    //   92: aload 4
    //   94: ifnull +8 -> 102
    //   97: aload 4
    //   99: invokevirtual 162	java/io/InputStream:close	()V
    //   102: aload 5
    //   104: athrow
    //   105: astore_2
    //   106: aconst_null
    //   107: astore_3
    //   108: aload_3
    //   109: areturn
    //   110: aconst_null
    //   111: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   41	60	75	java/util/NoSuchElementException
    //   41	60	90	finally
    //   31	41	105	java/lang/Exception
    //   68	73	105	java/lang/Exception
    //   82	87	105	java/lang/Exception
    //   97	102	105	java/lang/Exception
    //   102	105	105	java/lang/Exception
  }

  public static Context getRemoteContext(Context paramContext)
  {
    try
    {
      Context localContext = paramContext.createPackageContext("com.google.android.gms", 3);
      return localContext;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return null;
  }

  public static Resources getRemoteResource(Context paramContext)
  {
    try
    {
      Resources localResources = paramContext.getPackageManager().getResourcesForApplication("com.google.android.gms");
      return localResources;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return null;
  }

  @Deprecated
  public static int isGooglePlayServicesAvailable(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    if (!zzd.zzSV);
    PackageInfo localPackageInfo;
    zzc localzzc;
    try
    {
      paramContext.getResources().getString(R.string.common_google_play_services_unknown_issue);
      if ((!zzd.zzSV) && (!"com.google.android.gms".equals(paramContext.getPackageName())))
        zzO(paramContext);
    }
    catch (Throwable localThrowable)
    {
      try
      {
        localPackageInfo = localPackageManager.getPackageInfo("com.google.android.gms", 64);
        localzzc = zzc.zzkA();
        if ((zzhs.zzbj(localPackageInfo.versionCode)) || (zzhs.zzV(paramContext)))
        {
          if (localzzc.zza(localPackageInfo, zzb.zzbd.zzOU) != null)
            break label251;
          Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
          return 9;
          localThrowable = localThrowable;
          Log.e("GooglePlayServicesUtil", "The Google Play services resources were not found. Check your project configuration to ensure that the resources are included.");
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException1)
      {
        Log.w("GooglePlayServicesUtil", "Google Play services is missing.");
        return 1;
      }
    }
    try
    {
      localzza = localzzc.zza(localPackageManager.getPackageInfo("com.android.vending", 64), zzb.zzbd.zzOU);
      if (localzza == null)
      {
        Log.w("GooglePlayServicesUtil", "Google Play Store signature invalid.");
        return 9;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException3)
    {
      zzb.zza localzza;
      if (zzh(paramContext, "com.android.vending"))
      {
        Log.w("GooglePlayServicesUtil", "Google Play Store is updating.");
        if (localzzc.zza(localPackageInfo, zzb.zzbd.zzOU) == null)
        {
          Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
          return 9;
          if (localzzc.zza(localPackageInfo, new zzb.zza[] { localzza }) == null)
          {
            Log.w("GooglePlayServicesUtil", "Google Play services signature invalid.");
            return 9;
          }
        }
      }
      else
      {
        Log.w("GooglePlayServicesUtil", "Google Play Store is neither installed nor updating.");
        return 9;
      }
    }
    label251: int i = zzhs.zzbh(7327000);
    if (zzhs.zzbh(localPackageInfo.versionCode) < i)
    {
      Log.w("GooglePlayServicesUtil", "Google Play services out of date.  Requires 7327000 but found " + localPackageInfo.versionCode);
      return 2;
    }
    Object localObject = localPackageInfo.applicationInfo;
    if (localObject == null);
    try
    {
      ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo("com.google.android.gms", 0);
      localObject = localApplicationInfo;
      if (!((ApplicationInfo)localObject).enabled)
        return 3;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException2)
    {
      Log.wtf("GooglePlayServicesUtil", "Google Play services missing when getting application info.");
      localNameNotFoundException2.printStackTrace();
      return 1;
    }
    return 0;
  }

  @Deprecated
  public static boolean isUserRecoverableError(int paramInt)
  {
    switch (paramInt)
    {
    case 4:
    case 5:
    case 6:
    case 7:
    case 8:
    default:
      return false;
    case 1:
    case 2:
    case 3:
    case 9:
    }
    return true;
  }

  @Deprecated
  public static boolean showErrorDialogFragment(int paramInt1, Activity paramActivity, int paramInt2)
  {
    return showErrorDialogFragment(paramInt1, paramActivity, paramInt2, null);
  }

  @Deprecated
  public static boolean showErrorDialogFragment(int paramInt1, Activity paramActivity, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    return showErrorDialogFragment(paramInt1, paramActivity, null, paramInt2, paramOnCancelListener);
  }

  public static boolean showErrorDialogFragment(int paramInt1, Activity paramActivity, Fragment paramFragment, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    Dialog localDialog = zza(paramInt1, paramActivity, paramFragment, paramInt2, paramOnCancelListener);
    if (localDialog == null)
      return false;
    try
    {
      bool = paramActivity instanceof FragmentActivity;
      if (bool)
      {
        android.support.v4.app.FragmentManager localFragmentManager1 = ((FragmentActivity)paramActivity).getSupportFragmentManager();
        SupportErrorDialogFragment.newInstance(localDialog, paramOnCancelListener).show(localFragmentManager1, "GooglePlayServicesErrorDialog");
      }
      while (true)
      {
        return true;
        if (!zzic.zzne())
          break;
        android.app.FragmentManager localFragmentManager = paramActivity.getFragmentManager();
        ErrorDialogFragment.newInstance(localDialog, paramOnCancelListener).show(localFragmentManager, "GooglePlayServicesErrorDialog");
      }
      throw new RuntimeException("This Activity does not support Fragments.");
    }
    catch (NoClassDefFoundError localNoClassDefFoundError)
    {
      while (true)
        boolean bool = false;
    }
  }

  @Deprecated
  public static void showErrorNotification(int paramInt, Context paramContext)
  {
    if ((zzhs.zzV(paramContext)) && (paramInt == 2))
      paramInt = 42;
    if ((zze(paramContext, paramInt)) || (zzf(paramContext, paramInt)))
    {
      zzP(paramContext);
      return;
    }
    zza(paramInt, paramContext);
  }

  @Deprecated
  public static void zzM(Context paramContext)
    throws GooglePlayServicesRepairableException, GooglePlayServicesNotAvailableException
  {
    int i = isGooglePlayServicesAvailable(paramContext);
    if (i != 0)
    {
      Intent localIntent = zzar(i);
      Log.e("GooglePlayServicesUtil", "GooglePlayServices not available due to error " + i);
      if (localIntent == null)
        throw new GooglePlayServicesNotAvailableException(i);
      throw new GooglePlayServicesRepairableException(i, "Google Play Services not available", localIntent);
    }
  }

  private static void zzO(Context paramContext)
  {
    Integer localInteger;
    while (true)
    {
      synchronized (zznu)
      {
        if (zzOY == null)
        {
          zzOY = paramContext.getPackageName();
          try
          {
            Bundle localBundle = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128).metaData;
            if (localBundle != null)
            {
              zzOZ = Integer.valueOf(localBundle.getInt("com.google.android.gms.version"));
              localInteger = zzOZ;
              if (localInteger != null)
                break;
              throw new IllegalStateException("A required meta-data tag in your app's AndroidManifest.xml does not exist.  You must have the following declaration within the <application> element:     <meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />");
            }
            zzOZ = null;
            continue;
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException)
          {
            Log.wtf("GooglePlayServicesUtil", "This should never happen.", localNameNotFoundException);
            continue;
          }
        }
      }
      if (!zzOY.equals(paramContext.getPackageName()))
        throw new IllegalArgumentException("isGooglePlayServicesAvailable should only be called with Context from your application's package. A previous call used package '" + zzOY + "' and this call used package '" + paramContext.getPackageName() + "'.");
    }
    if (localInteger.intValue() != 7327000)
      throw new IllegalStateException("The meta-data tag in your app's AndroidManifest.xml does not have the right value.  Expected 7327000 but found " + localInteger + ".  You must have the" + " following declaration within the <application> element: " + "    <meta-data android:name=\"" + "com.google.android.gms.version" + "\" android:value=\"@integer/google_play_services_version\" />");
  }

  private static void zzP(Context paramContext)
  {
    zza localzza = new zza(paramContext);
    localzza.sendMessageDelayed(localzza.obtainMessage(1), 120000L);
  }

  @Deprecated
  public static void zzQ(Context paramContext)
  {
    try
    {
      ((NotificationManager)paramContext.getSystemService("notification")).cancel(10436);
      return;
    }
    catch (SecurityException localSecurityException)
    {
    }
  }

  private static String zzR(Context paramContext)
  {
    String str = paramContext.getApplicationInfo().name;
    PackageManager localPackageManager;
    if (TextUtils.isEmpty(str))
    {
      str = paramContext.getPackageName();
      localPackageManager = paramContext.getApplicationContext().getPackageManager();
    }
    try
    {
      ApplicationInfo localApplicationInfo2 = localPackageManager.getApplicationInfo(paramContext.getPackageName(), 0);
      localApplicationInfo1 = localApplicationInfo2;
      if (localApplicationInfo1 != null)
        str = localPackageManager.getApplicationLabel(localApplicationInfo1).toString();
      return str;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        ApplicationInfo localApplicationInfo1 = null;
    }
  }

  private static Dialog zza(int paramInt1, Activity paramActivity, Fragment paramFragment, int paramInt2, DialogInterface.OnCancelListener paramOnCancelListener)
  {
    if (paramInt1 == 0)
      return null;
    if ((zzhs.zzV(paramActivity)) && (paramInt1 == 2))
      paramInt1 = 42;
    boolean bool1 = zzic.zznh();
    AlertDialog.Builder localBuilder = null;
    if (bool1)
    {
      TypedValue localTypedValue = new TypedValue();
      paramActivity.getTheme().resolveAttribute(16843529, localTypedValue, true);
      boolean bool2 = "Theme.Dialog.Alert".equals(paramActivity.getResources().getResourceEntryName(localTypedValue.resourceId));
      localBuilder = null;
      if (bool2)
        localBuilder = new AlertDialog.Builder(paramActivity, 5);
    }
    if (localBuilder == null)
      localBuilder = new AlertDialog.Builder(paramActivity);
    localBuilder.setMessage(zzf.zzh(paramActivity, paramInt1));
    if (paramOnCancelListener != null)
      localBuilder.setOnCancelListener(paramOnCancelListener);
    Intent localIntent = zzar(paramInt1);
    if (paramFragment == null);
    for (zzg localzzg = new zzg(paramActivity, localIntent, paramInt2); ; localzzg = new zzg(paramFragment, localIntent, paramInt2))
    {
      String str1 = zzf.zzi(paramActivity, paramInt1);
      if (str1 != null)
        localBuilder.setPositiveButton(str1, localzzg);
      String str2 = zzf.zzg(paramActivity, paramInt1);
      if (str2 != null)
        localBuilder.setTitle(str2);
      return localBuilder.create();
    }
  }

  private static void zza(int paramInt, Context paramContext)
  {
    zza(paramInt, paramContext, null);
  }

  private static void zza(int paramInt, Context paramContext, String paramString)
  {
    Resources localResources = paramContext.getResources();
    String str1 = zzf.zzj(paramContext, paramInt);
    int i = R.string.common_google_play_services_error_notification_requested_by_msg;
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = zzR(paramContext);
    String str2 = localResources.getString(i, arrayOfObject);
    PendingIntent localPendingIntent = getErrorPendingIntent(paramInt, paramContext, 0);
    Object localObject;
    NotificationManager localNotificationManager;
    if (zzhs.zzV(paramContext))
    {
      zzv.zzP(zzic.zzni());
      localObject = new Notification.Builder(paramContext).setSmallIcon(R.drawable.common_ic_googleplayservices).setPriority(2).setAutoCancel(true).setStyle(new Notification.BigTextStyle().bigText(str1 + " " + str2)).addAction(R.drawable.common_full_open_on_phone, localResources.getString(R.string.common_open_on_phone), localPendingIntent).build();
      localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      if (paramString != null)
        localNotificationManager.notify(paramString, getErrorNotificationId(paramInt), (Notification)localObject);
    }
    else
    {
      Notification localNotification = new Notification(17301642, localResources.getString(R.string.common_google_play_services_notification_ticker), System.currentTimeMillis());
      localNotification.flags = (0x10 | localNotification.flags);
      if (Build.VERSION.SDK_INT >= 21)
        localNotification.flags = (0x100 | localNotification.flags);
      while (true)
      {
        localNotification.setLatestEventInfo(paramContext, str1, str2, localPendingIntent);
        localObject = localNotification;
        break;
        if (Build.VERSION.SDK_INT >= 19)
          localNotification.extras.putBoolean("android.support.localOnly", true);
      }
    }
    localNotificationManager.notify(getErrorNotificationId(paramInt), (Notification)localObject);
  }

  public static boolean zza(Context paramContext, int paramInt, String paramString)
  {
    AppOpsManager localAppOpsManager;
    if (zzic.zznk())
      localAppOpsManager = (AppOpsManager)paramContext.getSystemService("appops");
    try
    {
      localAppOpsManager.checkPackage(paramInt, paramString);
      boolean bool = true;
      String[] arrayOfString;
      do
      {
        do
        {
          return bool;
          arrayOfString = paramContext.getPackageManager().getPackagesForUid(paramInt);
          bool = false;
        }
        while (paramString == null);
        bool = false;
      }
      while (arrayOfString == null);
      for (int i = 0; ; i++)
      {
        int j = arrayOfString.length;
        bool = false;
        if (i >= j)
          break;
        if (paramString.equals(arrayOfString[i]))
          return true;
      }
    }
    catch (SecurityException localSecurityException)
    {
    }
    return false;
  }

  @Deprecated
  public static Intent zzar(int paramInt)
  {
    switch (paramInt)
    {
    default:
      return null;
    case 1:
    case 2:
    case 18:
      return zzm.zzbQ("com.google.android.gms");
    case 42:
      return zzm.zzmk();
    case 3:
    }
    return zzm.zzbO("com.google.android.gms");
  }

  public static boolean zzb(PackageManager paramPackageManager)
  {
    synchronized (zznu)
    {
      int i = zzOX;
      if (i == -1);
      try
      {
        PackageInfo localPackageInfo = paramPackageManager.getPackageInfo("com.google.android.gms", 64);
        zzc localzzc = zzc.zzkA();
        zzb.zza[] arrayOfzza = new zzb.zza[1];
        arrayOfzza[0] = zzb.zzON[1];
        if (localzzc.zza(localPackageInfo, arrayOfzza) != null);
        for (zzOX = 1; zzOX != 0; zzOX = 0)
          return true;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        while (true)
          zzOX = 0;
      }
    }
    return false;
  }

  @Deprecated
  public static boolean zzb(PackageManager paramPackageManager, String paramString)
  {
    return zzc.zzkA().zzb(paramPackageManager, paramString);
  }

  public static boolean zzc(PackageManager paramPackageManager)
  {
    return (zzb(paramPackageManager)) || (!zzkz());
  }

  public static boolean zzd(Context paramContext, int paramInt)
  {
    return (zza(paramContext, paramInt, "com.google.android.gms")) && (zzb(paramContext.getPackageManager(), "com.google.android.gms"));
  }

  @Deprecated
  public static boolean zze(Context paramContext, int paramInt)
  {
    if (paramInt == 18)
      return true;
    if (paramInt == 1)
      return zzh(paramContext, "com.google.android.gms");
    return false;
  }

  @Deprecated
  public static boolean zzf(Context paramContext, int paramInt)
  {
    if (paramInt == 9)
      return zzh(paramContext, "com.android.vending");
    return false;
  }

  public static boolean zzh(Context paramContext, String paramString)
  {
    if (zzic.zznm())
    {
      Iterator localIterator = paramContext.getPackageManager().getPackageInstaller().getAllSessions().iterator();
      do
        if (!localIterator.hasNext())
          break;
      while (!paramString.equals(((PackageInstaller.SessionInfo)localIterator.next()).getAppPackageName()));
      return true;
    }
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      boolean bool = localPackageManager.getApplicationInfo(paramString, 8192).enabled;
      if (bool)
        return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    return false;
  }

  public static boolean zzkz()
  {
    if (zzOV)
      return zzOW;
    return "user".equals(Build.TYPE);
  }

  private static class zza extends Handler
  {
    private final Context zzoh;

    zza(Context paramContext)
    {
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        Log.w("GooglePlayServicesUtil", "Don't know how to handle this message: " + paramMessage.what);
      case 1:
      }
      int i;
      do
      {
        return;
        i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.zzoh);
      }
      while (!GooglePlayServicesUtil.isUserRecoverableError(i));
      GooglePlayServicesUtil.zzb(i, this.zzoh);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.GooglePlayServicesUtil
 * JD-Core Version:    0.6.2
 */