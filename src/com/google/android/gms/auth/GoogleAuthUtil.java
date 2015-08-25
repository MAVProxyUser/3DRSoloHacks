package com.google.android.gms.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzha;
import java.io.IOException;
import java.net.URISyntaxException;

public final class GoogleAuthUtil
{
  public static final int CHANGE_TYPE_ACCOUNT_ADDED = 1;
  public static final int CHANGE_TYPE_ACCOUNT_REMOVED = 2;
  public static final int CHANGE_TYPE_ACCOUNT_RENAMED_FROM = 3;
  public static final int CHANGE_TYPE_ACCOUNT_RENAMED_TO = 4;
  public static final String GOOGLE_ACCOUNT_TYPE = "com.google";
  public static final String KEY_ANDROID_PACKAGE_NAME;
  public static final String KEY_CALLER_UID;
  public static final String KEY_REQUEST_ACTIONS = "request_visible_actions";

  @Deprecated
  public static final String KEY_REQUEST_VISIBLE_ACTIVITIES = "request_visible_actions";
  public static final String KEY_SUPPRESS_PROGRESS_SCREEN = "suppressProgressScreen";
  private static final ComponentName zzKA;
  private static final ComponentName zzKB;

  static
  {
    String str1;
    if (Build.VERSION.SDK_INT >= 11)
    {
      str1 = "callerUid";
      KEY_CALLER_UID = str1;
      if (Build.VERSION.SDK_INT < 14)
        break label65;
    }
    label65: for (String str2 = "androidPackageName"; ; str2 = "androidPackageName")
    {
      KEY_ANDROID_PACKAGE_NAME = str2;
      zzKA = new ComponentName("com.google.android.gms", "com.google.android.gms.auth.GetToken");
      zzKB = new ComponentName("com.google.android.gms", "com.google.android.gms.recovery.RecoveryService");
      return;
      str1 = "callerUid";
      break;
    }
  }

  // ERROR //
  public static void clearToken(Context paramContext, String paramString)
    throws GooglePlayServicesAvailabilityException, GoogleAuthException, IOException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 81	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   4: astore_2
    //   5: ldc 83
    //   7: invokestatic 89	com/google/android/gms/common/internal/zzv:zzbJ	(Ljava/lang/String;)V
    //   10: aload_2
    //   11: invokestatic 93	com/google/android/gms/auth/GoogleAuthUtil:zzM	(Landroid/content/Context;)V
    //   14: new 95	android/os/Bundle
    //   17: dup
    //   18: invokespecial 96	android/os/Bundle:<init>	()V
    //   21: astore_3
    //   22: aload_0
    //   23: invokevirtual 100	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   26: getfield 105	android/content/pm/ApplicationInfo:packageName	Ljava/lang/String;
    //   29: astore 4
    //   31: aload_3
    //   32: ldc 107
    //   34: aload 4
    //   36: invokevirtual 110	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   39: aload_3
    //   40: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   43: invokevirtual 114	android/os/Bundle:containsKey	(Ljava/lang/String;)Z
    //   46: ifne +12 -> 58
    //   49: aload_3
    //   50: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   53: aload 4
    //   55: invokevirtual 110	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   58: new 116	com/google/android/gms/common/zza
    //   61: dup
    //   62: invokespecial 117	com/google/android/gms/common/zza:<init>	()V
    //   65: astore 5
    //   67: aload_2
    //   68: invokestatic 123	com/google/android/gms/common/internal/zzk:zzU	(Landroid/content/Context;)Lcom/google/android/gms/common/internal/zzk;
    //   71: astore 6
    //   73: aload 6
    //   75: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   78: aload 5
    //   80: ldc 125
    //   82: invokevirtual 129	com/google/android/gms/common/internal/zzk:zza	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)Z
    //   85: ifeq +114 -> 199
    //   88: aload 5
    //   90: invokevirtual 133	com/google/android/gms/common/zza:zzku	()Landroid/os/IBinder;
    //   93: invokestatic 138	com/google/android/gms/internal/zzr$zza:zza	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/zzr;
    //   96: aload_1
    //   97: aload_3
    //   98: invokeinterface 143 3 0
    //   103: astore 11
    //   105: aload 11
    //   107: getstatic 148	com/google/android/gms/internal/zzha:zzLz	Ljava/lang/String;
    //   110: invokevirtual 152	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   113: astore 12
    //   115: aload 11
    //   117: ldc 154
    //   119: invokevirtual 157	android/os/Bundle:getBoolean	(Ljava/lang/String;)Z
    //   122: ifne +52 -> 174
    //   125: new 69	com/google/android/gms/auth/GoogleAuthException
    //   128: dup
    //   129: aload 12
    //   131: invokespecial 159	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   134: athrow
    //   135: astore 9
    //   137: ldc 125
    //   139: ldc 161
    //   141: aload 9
    //   143: invokestatic 167	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   146: pop
    //   147: new 71	java/io/IOException
    //   150: dup
    //   151: ldc 169
    //   153: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   156: athrow
    //   157: astore 8
    //   159: aload 6
    //   161: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   164: aload 5
    //   166: ldc 125
    //   168: invokevirtual 174	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   171: aload 8
    //   173: athrow
    //   174: aload 6
    //   176: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   179: aload 5
    //   181: ldc 125
    //   183: invokevirtual 174	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   186: return
    //   187: astore 7
    //   189: new 69	com/google/android/gms/auth/GoogleAuthException
    //   192: dup
    //   193: ldc 176
    //   195: invokespecial 159	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   198: athrow
    //   199: new 71	java/io/IOException
    //   202: dup
    //   203: ldc 178
    //   205: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   208: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   88	135	135	android/os/RemoteException
    //   88	135	157	finally
    //   137	157	157	finally
    //   189	199	157	finally
    //   88	135	187	java/lang/InterruptedException
  }

  // ERROR //
  public static java.util.List<AccountChangeEvent> getAccountChangeEvents(Context paramContext, int paramInt, String paramString)
    throws GoogleAuthException, IOException
  {
    // Byte code:
    //   0: aload_2
    //   1: ldc 182
    //   3: invokestatic 186	com/google/android/gms/common/internal/zzv:zzh	(Ljava/lang/String;Ljava/lang/Object;)Ljava/lang/String;
    //   6: pop
    //   7: ldc 83
    //   9: invokestatic 89	com/google/android/gms/common/internal/zzv:zzbJ	(Ljava/lang/String;)V
    //   12: aload_0
    //   13: invokevirtual 81	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   16: astore 4
    //   18: aload 4
    //   20: invokestatic 93	com/google/android/gms/auth/GoogleAuthUtil:zzM	(Landroid/content/Context;)V
    //   23: new 116	com/google/android/gms/common/zza
    //   26: dup
    //   27: invokespecial 117	com/google/android/gms/common/zza:<init>	()V
    //   30: astore 5
    //   32: aload 4
    //   34: invokestatic 123	com/google/android/gms/common/internal/zzk:zzU	(Landroid/content/Context;)Lcom/google/android/gms/common/internal/zzk;
    //   37: astore 6
    //   39: aload 6
    //   41: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   44: aload 5
    //   46: ldc 125
    //   48: invokevirtual 129	com/google/android/gms/common/internal/zzk:zza	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)Z
    //   51: ifeq +102 -> 153
    //   54: aload 5
    //   56: invokevirtual 133	com/google/android/gms/common/zza:zzku	()Landroid/os/IBinder;
    //   59: invokestatic 138	com/google/android/gms/internal/zzr$zza:zza	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/zzr;
    //   62: new 188	com/google/android/gms/auth/AccountChangeEventsRequest
    //   65: dup
    //   66: invokespecial 189	com/google/android/gms/auth/AccountChangeEventsRequest:<init>	()V
    //   69: aload_2
    //   70: invokevirtual 193	com/google/android/gms/auth/AccountChangeEventsRequest:setAccountName	(Ljava/lang/String;)Lcom/google/android/gms/auth/AccountChangeEventsRequest;
    //   73: iload_1
    //   74: invokevirtual 197	com/google/android/gms/auth/AccountChangeEventsRequest:setEventIndex	(I)Lcom/google/android/gms/auth/AccountChangeEventsRequest;
    //   77: invokeinterface 200 2 0
    //   82: invokevirtual 206	com/google/android/gms/auth/AccountChangeEventsResponse:getEvents	()Ljava/util/List;
    //   85: astore 11
    //   87: aload 6
    //   89: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   92: aload 5
    //   94: ldc 125
    //   96: invokevirtual 174	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   99: aload 11
    //   101: areturn
    //   102: astore 9
    //   104: ldc 125
    //   106: ldc 161
    //   108: aload 9
    //   110: invokestatic 167	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   113: pop
    //   114: new 71	java/io/IOException
    //   117: dup
    //   118: ldc 169
    //   120: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   123: athrow
    //   124: astore 8
    //   126: aload 6
    //   128: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   131: aload 5
    //   133: ldc 125
    //   135: invokevirtual 174	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   138: aload 8
    //   140: athrow
    //   141: astore 7
    //   143: new 69	com/google/android/gms/auth/GoogleAuthException
    //   146: dup
    //   147: ldc 176
    //   149: invokespecial 159	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   152: athrow
    //   153: new 71	java/io/IOException
    //   156: dup
    //   157: ldc 178
    //   159: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   162: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   54	87	102	android/os/RemoteException
    //   54	87	124	finally
    //   104	124	124	finally
    //   143	153	124	finally
    //   54	87	141	java/lang/InterruptedException
  }

  public static String getAccountId(Context paramContext, String paramString)
    throws GoogleAuthException, IOException
  {
    zzv.zzh(paramString, "accountName must be provided");
    zzv.zzbJ("Calling this from your main thread can lead to deadlock");
    zzM(paramContext.getApplicationContext());
    return getToken(paramContext, paramString, "^^_account_id_^^", new Bundle());
  }

  public static String getToken(Context paramContext, Account paramAccount, String paramString)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return getToken(paramContext, paramAccount, paramString, new Bundle());
  }

  public static String getToken(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return zza(paramContext, paramAccount, paramString, paramBundle, null);
  }

  @Deprecated
  public static String getToken(Context paramContext, String paramString1, String paramString2)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return getToken(paramContext, new Account(paramString1, "com.google"), paramString2);
  }

  @Deprecated
  public static String getToken(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return getToken(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle);
  }

  public static String getTokenWithNotification(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    if (paramBundle == null)
      paramBundle = new Bundle();
    paramBundle.putBoolean("handle_notification", true);
    return zza(paramContext, paramAccount, paramString, paramBundle);
  }

  public static String getTokenWithNotification(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle, Intent paramIntent)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    zzh(paramIntent);
    if (paramBundle == null)
      paramBundle = new Bundle();
    paramBundle.putParcelable("callback_intent", paramIntent);
    paramBundle.putBoolean("handle_notification", true);
    return zza(paramContext, paramAccount, paramString, paramBundle);
  }

  public static String getTokenWithNotification(Context paramContext, Account paramAccount, String paramString1, Bundle paramBundle1, String paramString2, Bundle paramBundle2)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    if (TextUtils.isEmpty(paramString2))
      throw new IllegalArgumentException("Authority cannot be empty or null.");
    if (paramBundle1 == null)
      paramBundle1 = new Bundle();
    if (paramBundle2 == null)
      paramBundle2 = new Bundle();
    ContentResolver.validateSyncExtrasBundle(paramBundle2);
    paramBundle1.putString("authority", paramString2);
    paramBundle1.putBundle("sync_extras", paramBundle2);
    paramBundle1.putBoolean("handle_notification", true);
    return zza(paramContext, paramAccount, paramString1, paramBundle1);
  }

  @Deprecated
  public static String getTokenWithNotification(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    return getTokenWithNotification(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle);
  }

  @Deprecated
  public static String getTokenWithNotification(Context paramContext, String paramString1, String paramString2, Bundle paramBundle, Intent paramIntent)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    return getTokenWithNotification(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle, paramIntent);
  }

  @Deprecated
  public static String getTokenWithNotification(Context paramContext, String paramString1, String paramString2, Bundle paramBundle1, String paramString3, Bundle paramBundle2)
    throws IOException, UserRecoverableNotifiedException, GoogleAuthException
  {
    return getTokenWithNotification(paramContext, new Account(paramString1, "com.google"), paramString2, paramBundle1, paramString3, paramBundle2);
  }

  @Deprecated
  public static void invalidateToken(Context paramContext, String paramString)
  {
    AccountManager.get(paramContext).invalidateAuthToken("com.google", paramString);
  }

  private static void zzM(Context paramContext)
    throws GoogleAuthException
  {
    try
    {
      GooglePlayServicesUtil.zzM(paramContext);
      return;
    }
    catch (GooglePlayServicesRepairableException localGooglePlayServicesRepairableException)
    {
      throw new GooglePlayServicesAvailabilityException(localGooglePlayServicesRepairableException.getConnectionStatusCode(), localGooglePlayServicesRepairableException.getMessage(), localGooglePlayServicesRepairableException.getIntent());
    }
    catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
    {
      throw new GoogleAuthException(localGooglePlayServicesNotAvailableException.getMessage());
    }
  }

  private static String zza(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle)
    throws IOException, GoogleAuthException
  {
    if (paramBundle == null)
      paramBundle = new Bundle();
    try
    {
      String str = getToken(paramContext, paramAccount, paramString, paramBundle);
      return str;
    }
    catch (GooglePlayServicesAvailabilityException localGooglePlayServicesAvailabilityException)
    {
      GooglePlayServicesUtil.showErrorNotification(localGooglePlayServicesAvailabilityException.getConnectionStatusCode(), paramContext);
      throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
    }
    catch (UserRecoverableAuthException localUserRecoverableAuthException)
    {
    }
    throw new UserRecoverableNotifiedException("User intervention required. Notification has been pushed.");
  }

  public static String zza(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle, Boolean paramBoolean)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    return zzb(paramContext, paramAccount, paramString, paramBundle, paramBoolean).getString("authtoken");
  }

  // ERROR //
  public static Bundle zzb(Context paramContext, Account paramAccount, String paramString, Bundle paramBundle, Boolean paramBoolean)
    throws IOException, UserRecoverableAuthException, GoogleAuthException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokevirtual 81	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   4: astore 5
    //   6: ldc 83
    //   8: invokestatic 89	com/google/android/gms/common/internal/zzv:zzbJ	(Ljava/lang/String;)V
    //   11: aload 5
    //   13: invokestatic 93	com/google/android/gms/auth/GoogleAuthUtil:zzM	(Landroid/content/Context;)V
    //   16: aload_3
    //   17: ifnonnull +186 -> 203
    //   20: new 95	android/os/Bundle
    //   23: dup
    //   24: invokespecial 96	android/os/Bundle:<init>	()V
    //   27: astore 6
    //   29: aload_0
    //   30: invokevirtual 100	android/content/Context:getApplicationInfo	()Landroid/content/pm/ApplicationInfo;
    //   33: getfield 105	android/content/pm/ApplicationInfo:packageName	Ljava/lang/String;
    //   36: astore 7
    //   38: aload 6
    //   40: ldc 107
    //   42: aload 7
    //   44: invokevirtual 110	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   47: aload 6
    //   49: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   52: invokevirtual 152	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   55: invokestatic 257	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   58: ifeq +13 -> 71
    //   61: aload 6
    //   63: getstatic 45	com/google/android/gms/auth/GoogleAuthUtil:KEY_ANDROID_PACKAGE_NAME	Ljava/lang/String;
    //   66: aload 7
    //   68: invokevirtual 110	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   71: aload 4
    //   73: ifnull +16 -> 89
    //   76: aload 6
    //   78: ldc_w 332
    //   81: aload 4
    //   83: invokevirtual 338	java/lang/Boolean:booleanValue	()Z
    //   86: invokevirtual 238	android/os/Bundle:putBoolean	(Ljava/lang/String;Z)V
    //   89: new 116	com/google/android/gms/common/zza
    //   92: dup
    //   93: invokespecial 117	com/google/android/gms/common/zza:<init>	()V
    //   96: astore 8
    //   98: aload 5
    //   100: invokestatic 123	com/google/android/gms/common/internal/zzk:zzU	(Landroid/content/Context;)Lcom/google/android/gms/common/internal/zzk;
    //   103: astore 9
    //   105: aload 9
    //   107: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   110: aload 8
    //   112: ldc 125
    //   114: invokevirtual 129	com/google/android/gms/common/internal/zzk:zza	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)Z
    //   117: ifeq +215 -> 332
    //   120: aload 8
    //   122: invokevirtual 133	com/google/android/gms/common/zza:zzku	()Landroid/os/IBinder;
    //   125: invokestatic 138	com/google/android/gms/internal/zzr$zza:zza	(Landroid/os/IBinder;)Lcom/google/android/gms/internal/zzr;
    //   128: aload_1
    //   129: aload_2
    //   130: aload 6
    //   132: invokeinterface 341 4 0
    //   137: astore 14
    //   139: aload 14
    //   141: ifnonnull +75 -> 216
    //   144: ldc 125
    //   146: ldc_w 343
    //   149: invokestatic 347	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   152: pop
    //   153: new 69	com/google/android/gms/auth/GoogleAuthException
    //   156: dup
    //   157: ldc_w 349
    //   160: invokespecial 159	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   163: athrow
    //   164: astore 12
    //   166: ldc 125
    //   168: ldc 161
    //   170: aload 12
    //   172: invokestatic 167	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   175: pop
    //   176: new 71	java/io/IOException
    //   179: dup
    //   180: ldc 169
    //   182: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   185: athrow
    //   186: astore 11
    //   188: aload 9
    //   190: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   193: aload 8
    //   195: ldc 125
    //   197: invokevirtual 174	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   200: aload 11
    //   202: athrow
    //   203: new 95	android/os/Bundle
    //   206: dup
    //   207: aload_3
    //   208: invokespecial 351	android/os/Bundle:<init>	(Landroid/os/Bundle;)V
    //   211: astore 6
    //   213: goto -184 -> 29
    //   216: aload 14
    //   218: ldc_w 330
    //   221: invokevirtual 152	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   224: invokestatic 257	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   227: istore 16
    //   229: iload 16
    //   231: ifne +18 -> 249
    //   234: aload 9
    //   236: getstatic 57	com/google/android/gms/auth/GoogleAuthUtil:zzKA	Landroid/content/ComponentName;
    //   239: aload 8
    //   241: ldc 125
    //   243: invokevirtual 174	com/google/android/gms/common/internal/zzk:zzb	(Landroid/content/ComponentName;Landroid/content/ServiceConnection;Ljava/lang/String;)V
    //   246: aload 14
    //   248: areturn
    //   249: aload 14
    //   251: ldc_w 353
    //   254: invokevirtual 152	android/os/Bundle:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   257: astore 17
    //   259: aload 14
    //   261: ldc_w 355
    //   264: invokevirtual 359	android/os/Bundle:getParcelable	(Ljava/lang/String;)Landroid/os/Parcelable;
    //   267: checkcast 361	android/content/Intent
    //   270: astore 18
    //   272: aload 17
    //   274: invokestatic 364	com/google/android/gms/auth/GoogleAuthUtil:zzbh	(Ljava/lang/String;)Z
    //   277: ifeq +27 -> 304
    //   280: new 217	com/google/android/gms/auth/UserRecoverableAuthException
    //   283: dup
    //   284: aload 17
    //   286: aload 18
    //   288: invokespecial 367	com/google/android/gms/auth/UserRecoverableAuthException:<init>	(Ljava/lang/String;Landroid/content/Intent;)V
    //   291: athrow
    //   292: astore 10
    //   294: new 69	com/google/android/gms/auth/GoogleAuthException
    //   297: dup
    //   298: ldc 176
    //   300: invokespecial 159	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   303: athrow
    //   304: aload 17
    //   306: invokestatic 370	com/google/android/gms/auth/GoogleAuthUtil:zzbg	(Ljava/lang/String;)Z
    //   309: ifeq +13 -> 322
    //   312: new 71	java/io/IOException
    //   315: dup
    //   316: aload 17
    //   318: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   321: athrow
    //   322: new 69	com/google/android/gms/auth/GoogleAuthException
    //   325: dup
    //   326: aload 17
    //   328: invokespecial 159	com/google/android/gms/auth/GoogleAuthException:<init>	(Ljava/lang/String;)V
    //   331: athrow
    //   332: new 71	java/io/IOException
    //   335: dup
    //   336: ldc 178
    //   338: invokespecial 170	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   341: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   120	139	164	android/os/RemoteException
    //   144	164	164	android/os/RemoteException
    //   216	229	164	android/os/RemoteException
    //   249	292	164	android/os/RemoteException
    //   304	322	164	android/os/RemoteException
    //   322	332	164	android/os/RemoteException
    //   120	139	186	finally
    //   144	164	186	finally
    //   166	186	186	finally
    //   216	229	186	finally
    //   249	292	186	finally
    //   294	304	186	finally
    //   304	322	186	finally
    //   322	332	186	finally
    //   120	139	292	java/lang/InterruptedException
    //   144	164	292	java/lang/InterruptedException
    //   216	229	292	java/lang/InterruptedException
    //   249	292	292	java/lang/InterruptedException
    //   304	322	292	java/lang/InterruptedException
    //   322	332	292	java/lang/InterruptedException
  }

  private static boolean zzbg(String paramString)
  {
    return ("NetworkError".equals(paramString)) || ("ServiceUnavailable".equals(paramString)) || ("Timeout".equals(paramString));
  }

  private static boolean zzbh(String paramString)
  {
    return ("BadAuthentication".equals(paramString)) || ("CaptchaRequired".equals(paramString)) || ("DeviceManagementRequiredOrSyncDisabled".equals(paramString)) || ("NeedPermission".equals(paramString)) || ("NeedsBrowser".equals(paramString)) || ("UserCancel".equals(paramString)) || ("AppDownloadRequired".equals(paramString)) || (zzha.zzKX.zzjQ().equals(paramString)) || (zzha.zzKY.zzjQ().equals(paramString)) || (zzha.zzKZ.zzjQ().equals(paramString)) || (zzha.zzLa.zzjQ().equals(paramString)) || (zzha.zzLb.zzjQ().equals(paramString)) || (zzha.zzLc.zzjQ().equals(paramString)) || (zzha.zzKV.zzjQ().equals(paramString));
  }

  private static void zzh(Intent paramIntent)
  {
    if (paramIntent == null)
      throw new IllegalArgumentException("Callback cannot be null.");
    String str = paramIntent.toUri(1);
    try
    {
      Intent.parseUri(str, 1);
      return;
    }
    catch (URISyntaxException localURISyntaxException)
    {
    }
    throw new IllegalArgumentException("Parameter callback contains invalid data. It must be serializable using toUri() and parseUri().");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.auth.GoogleAuthUtil
 * JD-Core Version:    0.6.2
 */