package com.crashlytics.android;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.ScrollView;
import android.widget.TextView;
import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.internal.CrashEventDataProvider;
import com.crashlytics.android.internal.models.SessionEventData;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import io.fabric.sdk.android.Logger;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.common.Crash.FatalException;
import io.fabric.sdk.android.services.common.Crash.LoggedException;
import io.fabric.sdk.android.services.common.ExecutorUtils;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityCallable;
import io.fabric.sdk.android.services.concurrency.Task;
import io.fabric.sdk.android.services.network.HttpMethod;
import io.fabric.sdk.android.services.network.HttpRequest;
import io.fabric.sdk.android.services.network.HttpRequestFactory;
import io.fabric.sdk.android.services.persistence.FileStoreImpl;
import io.fabric.sdk.android.services.persistence.PreferenceStore;
import io.fabric.sdk.android.services.persistence.PreferenceStoreImpl;
import io.fabric.sdk.android.services.settings.AppSettingsData;
import io.fabric.sdk.android.services.settings.FeaturesSettingsData;
import io.fabric.sdk.android.services.settings.PromptSettingsData;
import io.fabric.sdk.android.services.settings.SessionSettingsData;
import io.fabric.sdk.android.services.settings.Settings;
import io.fabric.sdk.android.services.settings.Settings.SettingsAccess;
import io.fabric.sdk.android.services.settings.SettingsData;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.net.ssl.HttpsURLConnection;

@DependsOn({"Lcom/crashlytics/android/internal/CrashEventDataProvider;"})
public class Crashlytics extends Kit<Void>
  implements KitGroup
{
  static final float CLS_DEFAULT_PROCESS_DELAY = 1.0F;
  static final String COLLECT_CUSTOM_KEYS = "com.crashlytics.CollectCustomKeys";
  static final String COLLECT_CUSTOM_LOGS = "com.crashlytics.CollectCustomLogs";
  static final String CRASHLYTICS_API_ENDPOINT = "com.crashlytics.ApiEndpoint";
  static final String CRASHLYTICS_REQUIRE_BUILD_ID = "com.crashlytics.RequireBuildId";
  static final boolean CRASHLYTICS_REQUIRE_BUILD_ID_DEFAULT = true;
  static final int DEFAULT_MAIN_HANDLER_TIMEOUT_SEC = 4;
  static final int MAX_ATTRIBUTES = 64;
  static final int MAX_ATTRIBUTE_SIZE = 1024;
  private static final String PREF_ALWAYS_SEND_REPORTS_KEY = "always_send_reports_opt_in";
  private static final boolean SHOULD_PROMPT_BEFORE_SENDING_REPORTS_DEFAULT = false;
  public static final String TAG = "Fabric";
  private final ConcurrentHashMap<String, String> attributes = new ConcurrentHashMap();
  private String buildId;
  private final ExecutorService crashHandlerExecutor;
  private float delay;
  private boolean disabled;
  private CrashEventDataProvider externalCrashEventDataProvider;
  private CrashlyticsUncaughtExceptionHandler handler;
  private HttpRequestFactory httpRequestFactory;
  private String installerPackageName;
  private final Collection<Kit<Boolean>> kits;
  private CrashlyticsListener listener;
  private String packageName;
  private final PinningInfoProvider pinningInfo;
  private final long startTime = System.currentTimeMillis();
  private String userEmail = null;
  private String userId = null;
  private String userName = null;
  private String versionCode;
  private String versionName;

  public Crashlytics()
  {
    this(1.0F, null, null, false);
  }

  Crashlytics(float paramFloat, CrashlyticsListener paramCrashlyticsListener, PinningInfoProvider paramPinningInfoProvider, boolean paramBoolean)
  {
    this(paramFloat, paramCrashlyticsListener, paramPinningInfoProvider, paramBoolean, ExecutorUtils.buildSingleThreadExecutorService("Crashlytics Exception Handler"));
  }

  Crashlytics(float paramFloat, CrashlyticsListener paramCrashlyticsListener, PinningInfoProvider paramPinningInfoProvider, boolean paramBoolean, ExecutorService paramExecutorService)
  {
    this.delay = paramFloat;
    this.listener = paramCrashlyticsListener;
    this.pinningInfo = paramPinningInfoProvider;
    this.disabled = paramBoolean;
    this.crashHandlerExecutor = paramExecutorService;
    Kit[] arrayOfKit = new Kit[2];
    arrayOfKit[0] = new Answers();
    arrayOfKit[1] = new Beta();
    this.kits = Collections.unmodifiableCollection(Arrays.asList(arrayOfKit));
  }

  private int dipsToPixels(float paramFloat, int paramInt)
  {
    return (int)(paramFloat * paramInt);
  }

  private static void doLog(int paramInt, String paramString1, String paramString2)
  {
    if (isDisabled());
    Crashlytics localCrashlytics;
    do
    {
      return;
      localCrashlytics = getInstance();
    }
    while (!ensureFabricWithCalled("prior to logging messages.", localCrashlytics));
    long l = System.currentTimeMillis() - localCrashlytics.startTime;
    localCrashlytics.handler.writeToLog(l, formatLogMessage(paramInt, paramString1, paramString2));
  }

  private static boolean ensureFabricWithCalled(String paramString, Crashlytics paramCrashlytics)
  {
    if ((paramCrashlytics == null) || (paramCrashlytics.handler == null))
    {
      Fabric.getLogger().e("Fabric", "Crashlytics must be initialized by calling Fabric.with(Context) " + paramString, null);
      return false;
    }
    return true;
  }

  private void finishInitSynchronously()
  {
    PriorityCallable local2 = new PriorityCallable()
    {
      public Void call()
        throws Exception
      {
        return Crashlytics.this.doInBackground();
      }

      public Priority getPriority()
      {
        return Priority.IMMEDIATE;
      }
    };
    Iterator localIterator = getDependencies().iterator();
    while (localIterator.hasNext())
      local2.addDependency((Task)localIterator.next());
    Future localFuture = getFabric().getExecutorService().submit(local2);
    Fabric.getLogger().d("Fabric", "Crashlytics detected incomplete initialization on previous app launch. Will initialize synchronously.");
    try
    {
      localFuture.get(4L, TimeUnit.SECONDS);
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      Fabric.getLogger().e("Fabric", "Crashlytics was interrupted during initialization.", localInterruptedException);
      return;
    }
    catch (ExecutionException localExecutionException)
    {
      Fabric.getLogger().e("Fabric", "Problem encountered during Crashlytics initialization.", localExecutionException);
      return;
    }
    catch (TimeoutException localTimeoutException)
    {
      Fabric.getLogger().e("Fabric", "Crashlytics timed out during initialization.", localTimeoutException);
    }
  }

  private static String formatLogMessage(int paramInt, String paramString1, String paramString2)
  {
    return CommonUtils.logPriorityToString(paramInt) + "/" + paramString1 + " " + paramString2;
  }

  public static Crashlytics getInstance()
  {
    try
    {
      Crashlytics localCrashlytics = (Crashlytics)Fabric.getKit(Crashlytics.class);
      return localCrashlytics;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Fabric.getLogger().e("Fabric", "Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()", null);
      throw localIllegalStateException;
    }
  }

  public static PinningInfoProvider getPinningInfoProvider()
  {
    if (!isDisabled())
      return getInstance().pinningInfo;
    return null;
  }

  private boolean getSendDecisionFromUser(final Activity paramActivity, final PromptSettingsData paramPromptSettingsData)
  {
    final DialogStringResolver localDialogStringResolver = new DialogStringResolver(paramActivity, paramPromptSettingsData);
    final OptInLatch localOptInLatch = new OptInLatch(null);
    paramActivity.runOnUiThread(new Runnable()
    {
      public void run()
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramActivity);
        DialogInterface.OnClickListener local1 = new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            Crashlytics.5.this.val$latch.setOptIn(true);
            paramAnonymous2DialogInterface.dismiss();
          }
        };
        float f = paramActivity.getResources().getDisplayMetrics().density;
        int i = Crashlytics.this.dipsToPixels(f, 5);
        TextView localTextView = new TextView(paramActivity);
        localTextView.setAutoLinkMask(15);
        localTextView.setText(localDialogStringResolver.getMessage());
        localTextView.setTextAppearance(paramActivity, 16973892);
        localTextView.setPadding(i, i, i, i);
        localTextView.setFocusable(false);
        ScrollView localScrollView = new ScrollView(paramActivity);
        localScrollView.setPadding(Crashlytics.this.dipsToPixels(f, 14), Crashlytics.this.dipsToPixels(f, 2), Crashlytics.this.dipsToPixels(f, 10), Crashlytics.this.dipsToPixels(f, 12));
        localScrollView.addView(localTextView);
        localBuilder.setView(localScrollView).setTitle(localDialogStringResolver.getTitle()).setCancelable(false).setNeutralButton(localDialogStringResolver.getSendButtonTitle(), local1);
        if (paramPromptSettingsData.showCancelButton)
        {
          DialogInterface.OnClickListener local2 = new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              Crashlytics.5.this.val$latch.setOptIn(false);
              paramAnonymous2DialogInterface.dismiss();
            }
          };
          localBuilder.setNegativeButton(localDialogStringResolver.getCancelButtonTitle(), local2);
        }
        if (paramPromptSettingsData.showAlwaysSendButton)
        {
          DialogInterface.OnClickListener local3 = new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              Crashlytics.this.setShouldSendUserReportsWithoutPrompting(true);
              Crashlytics.5.this.val$latch.setOptIn(true);
              paramAnonymous2DialogInterface.dismiss();
            }
          };
          localBuilder.setPositiveButton(localDialogStringResolver.getAlwaysSendButtonTitle(), local3);
        }
        localBuilder.show();
      }
    });
    Fabric.getLogger().d("Fabric", "Waiting for user opt-in.");
    localOptInLatch.await();
    return localOptInLatch.getOptIn();
  }

  private static boolean isDisabled()
  {
    Crashlytics localCrashlytics = getInstance();
    return (localCrashlytics == null) || (localCrashlytics.disabled);
  }

  private boolean isRequiringBuildId(Context paramContext)
  {
    return CommonUtils.getBooleanResourceValue(paramContext, "com.crashlytics.RequireBuildId", true);
  }

  public static void log(int paramInt, String paramString1, String paramString2)
  {
    doLog(paramInt, paramString1, paramString2);
    Fabric.getLogger().log(paramInt, "" + paramString1, "" + paramString2, true);
  }

  public static void log(String paramString)
  {
    doLog(3, "Fabric", paramString);
  }

  public static void logException(Throwable paramThrowable)
  {
    if (isDisabled());
    Crashlytics localCrashlytics;
    do
    {
      return;
      localCrashlytics = getInstance();
    }
    while (!ensureFabricWithCalled("prior to logging exceptions.", localCrashlytics));
    if (paramThrowable == null)
    {
      Fabric.getLogger().log(5, "Fabric", "Crashlytics is ignoring a request to log a null exception.");
      return;
    }
    localCrashlytics.handler.writeNonFatalException(Thread.currentThread(), paramThrowable);
  }

  static void recordFatalExceptionEvent(String paramString)
  {
    Answers localAnswers = (Answers)Fabric.getKit(Answers.class);
    if (localAnswers != null)
      localAnswers.onException(new Crash.FatalException(paramString));
  }

  static void recordLoggedExceptionEvent(String paramString)
  {
    Answers localAnswers = (Answers)Fabric.getKit(Answers.class);
    if (localAnswers != null)
      localAnswers.onException(new Crash.LoggedException(paramString));
  }

  private static String sanitizeAttribute(String paramString)
  {
    if (paramString != null)
    {
      paramString = paramString.trim();
      if (paramString.length() > 1024)
        paramString = paramString.substring(0, 1024);
    }
    return paramString;
  }

  public static void setBool(String paramString, boolean paramBoolean)
  {
    setString(paramString, Boolean.toString(paramBoolean));
  }

  public static void setDouble(String paramString, double paramDouble)
  {
    setString(paramString, Double.toString(paramDouble));
  }

  public static void setFloat(String paramString, float paramFloat)
  {
    setString(paramString, Float.toString(paramFloat));
  }

  public static void setInt(String paramString, int paramInt)
  {
    setString(paramString, Integer.toString(paramInt));
  }

  public static void setLong(String paramString, long paramLong)
  {
    setString(paramString, Long.toString(paramLong));
  }

  @Deprecated
  public static void setPinningInfoProvider(PinningInfoProvider paramPinningInfoProvider)
  {
    Fabric.getLogger().w("Fabric", "Use of Crashlytics.setPinningInfoProvider is deprecated");
  }

  public static void setString(String paramString1, String paramString2)
  {
    if (isDisabled())
      return;
    if (paramString1 == null)
    {
      if ((getInstance().getContext() != null) && (CommonUtils.isAppDebuggable(getInstance().getContext())))
        throw new IllegalArgumentException("Custom attribute key must not be null.");
      Fabric.getLogger().e("Fabric", "Attempting to set custom attribute with null key, ignoring.", null);
      return;
    }
    String str1 = sanitizeAttribute(paramString1);
    if ((getInstance().attributes.size() < 64) || (getInstance().attributes.containsKey(str1)))
    {
      if (paramString2 == null);
      for (String str2 = ""; ; str2 = sanitizeAttribute(paramString2))
      {
        getInstance().attributes.put(str1, str2);
        return;
      }
    }
    Fabric.getLogger().d("Fabric", "Exceeded maximum number of custom attributes (64)");
  }

  public static void setUserEmail(String paramString)
  {
    if (isDisabled())
      return;
    getInstance().userEmail = sanitizeAttribute(paramString);
  }

  public static void setUserIdentifier(String paramString)
  {
    if (isDisabled())
      return;
    getInstance().userId = sanitizeAttribute(paramString);
  }

  public static void setUserName(String paramString)
  {
    if (isDisabled())
      return;
    getInstance().userName = sanitizeAttribute(paramString);
  }

  boolean canSendWithUserApproval()
  {
    return ((Boolean)Settings.getInstance().withSettings(new Settings.SettingsAccess()
    {
      public Boolean usingSettings(SettingsData paramAnonymousSettingsData)
      {
        boolean bool = true;
        Activity localActivity = Crashlytics.this.getFabric().getCurrentActivity();
        if ((localActivity != null) && (!localActivity.isFinishing()) && (Crashlytics.this.shouldPromptUserBeforeSendingCrashReports()))
          bool = Crashlytics.this.getSendDecisionFromUser(localActivity, paramAnonymousSettingsData.promptData);
        return Boolean.valueOf(bool);
      }
    }
    , Boolean.valueOf(true))).booleanValue();
  }

  public void crash()
  {
    new CrashTest().indexOutOfBounds();
  }

  protected Void doInBackground()
  {
    this.handler.markInitializationStarted();
    this.handler.cleanInvalidTempFiles();
    int i = 1;
    try
    {
      SettingsData localSettingsData = Settings.getInstance().awaitSettingsData();
      if (localSettingsData == null)
      {
        Fabric.getLogger().w("Fabric", "Received null settings, skipping initialization!");
        return null;
      }
      if (localSettingsData.featuresData.collectReports)
      {
        i = 0;
        this.handler.finalizeSessions();
        CreateReportSpiCall localCreateReportSpiCall = getCreateReportSpiCall(localSettingsData);
        i = 0;
        if (localCreateReportSpiCall != null)
          new ReportUploader(localCreateReportSpiCall).uploadReports(this.delay);
      }
      else if (i == 0);
    }
    catch (Exception localException1)
    {
    }
    finally
    {
      this.handler.markInitializationComplete();
    }
  }

  Map<String, String> getAttributes()
  {
    return Collections.unmodifiableMap(this.attributes);
  }

  String getBuildId()
  {
    return this.buildId;
  }

  BuildIdValidator getBuildIdValidator(String paramString, boolean paramBoolean)
  {
    return new BuildIdValidator(paramString, paramBoolean);
  }

  CreateReportSpiCall getCreateReportSpiCall(SettingsData paramSettingsData)
  {
    if (paramSettingsData != null)
      return new DefaultCreateReportSpiCall(this, getOverridenSpiEndpoint(), paramSettingsData.appData.reportsUrl, this.httpRequestFactory);
    return null;
  }

  @Deprecated
  public boolean getDebugMode()
  {
    Fabric.getLogger().w("Fabric", "Use of Crashlytics.getDebugMode is deprecated.");
    getFabric();
    return Fabric.isDebuggable();
  }

  SessionEventData getExternalCrashEventData()
  {
    CrashEventDataProvider localCrashEventDataProvider = this.externalCrashEventDataProvider;
    SessionEventData localSessionEventData = null;
    if (localCrashEventDataProvider != null)
      localSessionEventData = this.externalCrashEventDataProvider.getCrashEventData();
    return localSessionEventData;
  }

  CrashlyticsUncaughtExceptionHandler getHandler()
  {
    return this.handler;
  }

  public String getIdentifier()
  {
    return "com.crashlytics.sdk.android:crashlytics";
  }

  String getInstallerPackageName()
  {
    return this.installerPackageName;
  }

  public Collection<? extends Kit> getKits()
  {
    return this.kits;
  }

  String getOverridenSpiEndpoint()
  {
    return CommonUtils.getStringsFileValue(getInstance().getContext(), "com.crashlytics.ApiEndpoint");
  }

  String getPackageName()
  {
    return this.packageName;
  }

  File getSdkDirectory()
  {
    return new FileStoreImpl(this).getFilesDir();
  }

  SessionSettingsData getSessionSettingsData()
  {
    SettingsData localSettingsData = Settings.getInstance().awaitSettingsData();
    if (localSettingsData == null)
      return null;
    return localSettingsData.sessionData;
  }

  String getUserEmail()
  {
    if (getIdManager().canCollectUserIds())
      return this.userEmail;
    return null;
  }

  String getUserIdentifier()
  {
    if (getIdManager().canCollectUserIds())
      return this.userId;
    return null;
  }

  String getUserName()
  {
    if (getIdManager().canCollectUserIds())
      return this.userName;
    return null;
  }

  public String getVersion()
  {
    return "2.2.1.32";
  }

  String getVersionCode()
  {
    return this.versionCode;
  }

  String getVersionName()
  {
    return this.versionName;
  }

  boolean internalVerifyPinning(URL paramURL)
  {
    PinningInfoProvider localPinningInfoProvider = getPinningInfoProvider();
    boolean bool = false;
    if (localPinningInfoProvider != null)
    {
      HttpRequest localHttpRequest = this.httpRequestFactory.buildHttpRequest(HttpMethod.GET, paramURL.toString());
      ((HttpsURLConnection)localHttpRequest.getConnection()).setInstanceFollowRedirects(false);
      localHttpRequest.code();
      bool = true;
    }
    return bool;
  }

  protected boolean onPreExecute()
  {
    return onPreExecute(super.getContext());
  }

  // ERROR //
  boolean onPreExecute(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 118	com/crashlytics/android/Crashlytics:disabled	Z
    //   4: ifeq +5 -> 9
    //   7: iconst_0
    //   8: ireturn
    //   9: aload_1
    //   10: invokestatic 597	io/fabric/sdk/android/Fabric:isDebuggable	()Z
    //   13: invokestatic 705	io/fabric/sdk/android/services/common/ApiKey:getApiKey	(Landroid/content/Context;Z)Ljava/lang/String;
    //   16: astore_2
    //   17: aload_2
    //   18: ifnonnull +5 -> 23
    //   21: iconst_0
    //   22: ireturn
    //   23: aload_0
    //   24: new 707	io/fabric/sdk/android/services/network/DefaultHttpRequestFactory
    //   27: dup
    //   28: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   31: invokespecial 710	io/fabric/sdk/android/services/network/DefaultHttpRequestFactory:<init>	(Lio/fabric/sdk/android/Logger;)V
    //   34: putfield 588	com/crashlytics/android/Crashlytics:httpRequestFactory	Lio/fabric/sdk/android/services/network/HttpRequestFactory;
    //   37: aload_0
    //   38: getfield 116	com/crashlytics/android/Crashlytics:pinningInfo	Lcom/crashlytics/android/PinningInfoProvider;
    //   41: ifnonnull +284 -> 325
    //   44: aload_0
    //   45: getfield 588	com/crashlytics/android/Crashlytics:httpRequestFactory	Lio/fabric/sdk/android/services/network/HttpRequestFactory;
    //   48: aconst_null
    //   49: invokeinterface 713 2 0
    //   54: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   57: ldc 44
    //   59: new 190	java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   66: ldc_w 715
    //   69: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: aload_0
    //   73: invokevirtual 717	com/crashlytics/android/Crashlytics:getVersion	()Ljava/lang/String;
    //   76: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   79: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   82: invokeinterface 720 3 0
    //   87: aload_0
    //   88: aload_1
    //   89: invokevirtual 724	android/content/Context:getPackageName	()Ljava/lang/String;
    //   92: putfield 622	com/crashlytics/android/Crashlytics:packageName	Ljava/lang/String;
    //   95: aload_0
    //   96: aload_0
    //   97: invokevirtual 643	com/crashlytics/android/Crashlytics:getIdManager	()Lio/fabric/sdk/android/services/common/IdManager;
    //   100: invokevirtual 726	io/fabric/sdk/android/services/common/IdManager:getInstallerPackageName	()Ljava/lang/String;
    //   103: putfield 614	com/crashlytics/android/Crashlytics:installerPackageName	Ljava/lang/String;
    //   106: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   109: ldc 44
    //   111: new 190	java/lang/StringBuilder
    //   114: dup
    //   115: invokespecial 191	java/lang/StringBuilder:<init>	()V
    //   118: ldc_w 728
    //   121: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   124: aload_0
    //   125: getfield 614	com/crashlytics/android/Crashlytics:installerPackageName	Ljava/lang/String;
    //   128: invokevirtual 197	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   131: invokevirtual 201	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   134: invokeinterface 266 3 0
    //   139: aload_1
    //   140: invokevirtual 732	android/content/Context:getPackageManager	()Landroid/content/pm/PackageManager;
    //   143: aload_0
    //   144: getfield 622	com/crashlytics/android/Crashlytics:packageName	Ljava/lang/String;
    //   147: iconst_0
    //   148: invokevirtual 738	android/content/pm/PackageManager:getPackageInfo	(Ljava/lang/String;I)Landroid/content/pm/PackageInfo;
    //   151: astore 9
    //   153: aload_0
    //   154: aload 9
    //   156: getfield 742	android/content/pm/PackageInfo:versionCode	I
    //   159: invokestatic 429	java/lang/Integer:toString	(I)Ljava/lang/String;
    //   162: putfield 656	com/crashlytics/android/Crashlytics:versionCode	Ljava/lang/String;
    //   165: aload 9
    //   167: getfield 743	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   170: ifnonnull +187 -> 357
    //   173: ldc_w 745
    //   176: astore 10
    //   178: aload_0
    //   179: aload 10
    //   181: putfield 659	com/crashlytics/android/Crashlytics:versionName	Ljava/lang/String;
    //   184: aload_0
    //   185: aload_1
    //   186: invokestatic 749	io/fabric/sdk/android/services/common/CommonUtils:resolveBuildId	(Landroid/content/Context;)Ljava/lang/String;
    //   189: putfield 566	com/crashlytics/android/Crashlytics:buildId	Ljava/lang/String;
    //   192: aload_0
    //   193: invokevirtual 643	com/crashlytics/android/Crashlytics:getIdManager	()Lio/fabric/sdk/android/services/common/IdManager;
    //   196: invokevirtual 752	io/fabric/sdk/android/services/common/IdManager:getBluetoothMacAddress	()Ljava/lang/String;
    //   199: pop
    //   200: aload_0
    //   201: aload_0
    //   202: getfield 566	com/crashlytics/android/Crashlytics:buildId	Ljava/lang/String;
    //   205: aload_0
    //   206: aload_1
    //   207: invokespecial 754	com/crashlytics/android/Crashlytics:isRequiringBuildId	(Landroid/content/Context;)Z
    //   210: invokevirtual 756	com/crashlytics/android/Crashlytics:getBuildIdValidator	(Ljava/lang/String;Z)Lcom/crashlytics/android/BuildIdValidator;
    //   213: aload_2
    //   214: aload_0
    //   215: getfield 622	com/crashlytics/android/Crashlytics:packageName	Ljava/lang/String;
    //   218: invokevirtual 759	com/crashlytics/android/BuildIdValidator:validate	(Ljava/lang/String;Ljava/lang/String;)V
    //   221: iconst_0
    //   222: istore 7
    //   224: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   227: ldc 44
    //   229: ldc_w 761
    //   232: invokeinterface 266 3 0
    //   237: aload_0
    //   238: new 178	com/crashlytics/android/CrashlyticsUncaughtExceptionHandler
    //   241: dup
    //   242: invokestatic 765	java/lang/Thread:getDefaultUncaughtExceptionHandler	()Ljava/lang/Thread$UncaughtExceptionHandler;
    //   245: aload_0
    //   246: getfield 114	com/crashlytics/android/Crashlytics:listener	Lcom/crashlytics/android/CrashlyticsListener;
    //   249: aload_0
    //   250: getfield 120	com/crashlytics/android/Crashlytics:crashHandlerExecutor	Ljava/util/concurrent/ExecutorService;
    //   253: aload_0
    //   254: getfield 566	com/crashlytics/android/Crashlytics:buildId	Ljava/lang/String;
    //   257: aload_0
    //   258: invokevirtual 643	com/crashlytics/android/Crashlytics:getIdManager	()Lio/fabric/sdk/android/services/common/IdManager;
    //   261: aload_0
    //   262: invokespecial 768	com/crashlytics/android/CrashlyticsUncaughtExceptionHandler:<init>	(Ljava/lang/Thread$UncaughtExceptionHandler;Lcom/crashlytics/android/CrashlyticsListener;Ljava/util/concurrent/ExecutorService;Ljava/lang/String;Lio/fabric/sdk/android/services/common/IdManager;Lcom/crashlytics/android/Crashlytics;)V
    //   265: putfield 172	com/crashlytics/android/Crashlytics:handler	Lcom/crashlytics/android/CrashlyticsUncaughtExceptionHandler;
    //   268: aload_0
    //   269: getfield 172	com/crashlytics/android/Crashlytics:handler	Lcom/crashlytics/android/CrashlyticsUncaughtExceptionHandler;
    //   272: invokevirtual 771	com/crashlytics/android/CrashlyticsUncaughtExceptionHandler:didPreviousInitializationComplete	()Z
    //   275: istore 7
    //   277: aload_0
    //   278: getfield 172	com/crashlytics/android/Crashlytics:handler	Lcom/crashlytics/android/CrashlyticsUncaughtExceptionHandler;
    //   281: invokevirtual 774	com/crashlytics/android/CrashlyticsUncaughtExceptionHandler:ensureOpenSessionExists	()V
    //   284: aload_0
    //   285: getfield 172	com/crashlytics/android/Crashlytics:handler	Lcom/crashlytics/android/CrashlyticsUncaughtExceptionHandler;
    //   288: invokestatic 778	java/lang/Thread:setDefaultUncaughtExceptionHandler	(Ljava/lang/Thread$UncaughtExceptionHandler;)V
    //   291: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   294: ldc 44
    //   296: ldc_w 780
    //   299: invokeinterface 266 3 0
    //   304: iload 7
    //   306: ifeq +118 -> 424
    //   309: aload_0
    //   310: invokevirtual 448	com/crashlytics/android/Crashlytics:getContext	()Landroid/content/Context;
    //   313: invokestatic 783	io/fabric/sdk/android/services/common/CommonUtils:canTryConnection	(Landroid/content/Context;)Z
    //   316: ifeq +108 -> 424
    //   319: aload_0
    //   320: invokespecial 785	com/crashlytics/android/Crashlytics:finishInitSynchronously	()V
    //   323: iconst_0
    //   324: ireturn
    //   325: aload_0
    //   326: getfield 588	com/crashlytics/android/Crashlytics:httpRequestFactory	Lio/fabric/sdk/android/services/network/HttpRequestFactory;
    //   329: new 787	com/crashlytics/android/Crashlytics$1
    //   332: dup
    //   333: aload_0
    //   334: invokespecial 788	com/crashlytics/android/Crashlytics$1:<init>	(Lcom/crashlytics/android/Crashlytics;)V
    //   337: invokeinterface 713 2 0
    //   342: goto -288 -> 54
    //   345: astore 4
    //   347: new 790	io/fabric/sdk/android/services/concurrency/UnmetDependencyException
    //   350: dup
    //   351: aload 4
    //   353: invokespecial 792	io/fabric/sdk/android/services/concurrency/UnmetDependencyException:<init>	(Ljava/lang/Throwable;)V
    //   356: athrow
    //   357: aload 9
    //   359: getfield 743	android/content/pm/PackageInfo:versionName	Ljava/lang/String;
    //   362: astore 10
    //   364: goto -186 -> 178
    //   367: astore 5
    //   369: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   372: ldc 44
    //   374: ldc_w 794
    //   377: aload 5
    //   379: invokeinterface 207 4 0
    //   384: goto -192 -> 192
    //   387: astore_3
    //   388: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   391: ldc 44
    //   393: ldc_w 796
    //   396: aload_3
    //   397: invokeinterface 207 4 0
    //   402: iconst_0
    //   403: ireturn
    //   404: astore 8
    //   406: invokestatic 188	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   409: ldc 44
    //   411: ldc_w 798
    //   414: aload 8
    //   416: invokeinterface 207 4 0
    //   421: goto -117 -> 304
    //   424: iconst_1
    //   425: ireturn
    //
    // Exception table:
    //   from	to	target	type
    //   23	54	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   54	87	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   87	173	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   178	192	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   192	221	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   224	304	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   309	323	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   325	342	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   357	364	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   369	384	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   406	421	345	com/crashlytics/android/CrashlyticsMissingDependencyException
    //   87	173	367	java/lang/Exception
    //   178	192	367	java/lang/Exception
    //   357	364	367	java/lang/Exception
    //   23	54	387	java/lang/Exception
    //   54	87	387	java/lang/Exception
    //   192	221	387	java/lang/Exception
    //   309	323	387	java/lang/Exception
    //   325	342	387	java/lang/Exception
    //   369	384	387	java/lang/Exception
    //   406	421	387	java/lang/Exception
    //   224	304	404	java/lang/Exception
  }

  @Deprecated
  public void setDebugMode(boolean paramBoolean)
  {
    Fabric.getLogger().w("Fabric", "Use of Crashlytics.setDebugMode is deprecated.");
  }

  void setExternalCrashEventDataProvider(CrashEventDataProvider paramCrashEventDataProvider)
  {
    this.externalCrashEventDataProvider = paramCrashEventDataProvider;
  }

  @Deprecated
  public void setListener(CrashlyticsListener paramCrashlyticsListener)
  {
    try
    {
      Fabric.getLogger().w("Fabric", "Use of Crashlytics.setListener is deprecated.");
      if (paramCrashlyticsListener == null)
        throw new IllegalArgumentException("listener must not be null.");
    }
    finally
    {
    }
    this.listener = paramCrashlyticsListener;
  }

  @SuppressLint({"CommitPrefEdits"})
  void setShouldSendUserReportsWithoutPrompting(boolean paramBoolean)
  {
    PreferenceStoreImpl localPreferenceStoreImpl = new PreferenceStoreImpl(this);
    localPreferenceStoreImpl.save(localPreferenceStoreImpl.edit().putBoolean("always_send_reports_opt_in", paramBoolean));
  }

  boolean shouldPromptUserBeforeSendingCrashReports()
  {
    return ((Boolean)Settings.getInstance().withSettings(new Settings.SettingsAccess()
    {
      public Boolean usingSettings(SettingsData paramAnonymousSettingsData)
      {
        if (paramAnonymousSettingsData.featuresData.promptEnabled)
        {
          boolean bool1 = Crashlytics.this.shouldSendReportsWithoutPrompting();
          boolean bool2 = false;
          if (!bool1)
            bool2 = true;
          return Boolean.valueOf(bool2);
        }
        return Boolean.valueOf(false);
      }
    }
    , Boolean.valueOf(false))).booleanValue();
  }

  boolean shouldSendReportsWithoutPrompting()
  {
    return new PreferenceStoreImpl(this).get().getBoolean("always_send_reports_opt_in", false);
  }

  public boolean verifyPinning(URL paramURL)
  {
    try
    {
      boolean bool = internalVerifyPinning(paramURL);
      return bool;
    }
    catch (Exception localException)
    {
      Fabric.getLogger().e("Fabric", "Could not verify SSL pinning", localException);
    }
    return false;
  }

  public static class Builder
  {
    private float delay = -1.0F;
    private boolean disabled = false;
    private CrashlyticsListener listener;
    private PinningInfoProvider pinningInfoProvider;

    public Crashlytics build()
    {
      if (this.delay < 0.0F)
        this.delay = 1.0F;
      return new Crashlytics(this.delay, this.listener, this.pinningInfoProvider, this.disabled);
    }

    public Builder delay(float paramFloat)
    {
      if (paramFloat <= 0.0F)
        throw new IllegalArgumentException("delay must be greater than 0");
      if (this.delay > 0.0F)
        throw new IllegalStateException("delay already set.");
      this.delay = paramFloat;
      return this;
    }

    public Builder disabled(boolean paramBoolean)
    {
      this.disabled = paramBoolean;
      return this;
    }

    public Builder listener(CrashlyticsListener paramCrashlyticsListener)
    {
      if (paramCrashlyticsListener == null)
        throw new IllegalArgumentException("listener must not be null.");
      if (this.listener != null)
        throw new IllegalStateException("listener already set.");
      this.listener = paramCrashlyticsListener;
      return this;
    }

    @Deprecated
    public Builder pinningInfo(PinningInfoProvider paramPinningInfoProvider)
    {
      if (paramPinningInfoProvider == null)
        throw new IllegalArgumentException("pinningInfoProvider must not be null.");
      if (this.pinningInfoProvider != null)
        throw new IllegalStateException("pinningInfoProvider already set.");
      this.pinningInfoProvider = paramPinningInfoProvider;
      return this;
    }
  }

  private class OptInLatch
  {
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean send = false;

    private OptInLatch()
    {
    }

    void await()
    {
      try
      {
        this.latch.await();
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
      }
    }

    boolean getOptIn()
    {
      return this.send;
    }

    void setOptIn(boolean paramBoolean)
    {
      this.send = paramBoolean;
      this.latch.countDown();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.crashlytics.android.Crashlytics
 * JD-Core Version:    0.6.2
 */