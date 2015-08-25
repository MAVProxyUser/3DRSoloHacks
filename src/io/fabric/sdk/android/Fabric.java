package io.fabric.sdk.android;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import io.fabric.sdk.android.services.common.IdManager;
import io.fabric.sdk.android.services.concurrency.DependsOn;
import io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor;
import io.fabric.sdk.android.services.concurrency.UnmetDependencyException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fabric
{
  static final boolean DEFAULT_DEBUGGABLE = false;
  static final Logger DEFAULT_LOGGER = new DefaultLogger();
  static final String ROOT_DIR = ".Fabric";
  public static final String TAG = "Fabric";
  static volatile Fabric singleton;
  private WeakReference<Activity> activity;
  private ActivityLifecycleManager activityLifecycleManager;
  private final Context context;
  final boolean debuggable;
  private final ExecutorService executorService;
  private final IdManager idManager;
  private final InitializationCallback<Fabric> initializationCallback;
  private AtomicBoolean initialized;
  private final InitializationCallback<?> kitInitializationCallback;
  private final Map<Class<? extends Kit>, Kit> kits;
  final Logger logger;
  private final Handler mainHandler;
  private Onboarding onboarding;

  Fabric(Context paramContext, Map<Class<? extends Kit>, Kit> paramMap, PriorityThreadPoolExecutor paramPriorityThreadPoolExecutor, Handler paramHandler, Logger paramLogger, boolean paramBoolean, InitializationCallback paramInitializationCallback, IdManager paramIdManager)
  {
    this.context = paramContext;
    this.kits = paramMap;
    this.executorService = paramPriorityThreadPoolExecutor;
    this.mainHandler = paramHandler;
    this.logger = paramLogger;
    this.debuggable = paramBoolean;
    this.initializationCallback = paramInitializationCallback;
    this.initialized = new AtomicBoolean(false);
    this.kitInitializationCallback = createKitInitializationCallback(paramMap.size());
    this.idManager = paramIdManager;
  }

  private static void addToKitMap(Map<Class<? extends Kit>, Kit> paramMap, Collection<? extends Kit> paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext())
    {
      Kit localKit = (Kit)localIterator.next();
      paramMap.put(localKit.getClass(), localKit);
      if ((localKit instanceof KitGroup))
        addToKitMap(paramMap, ((KitGroup)localKit).getKits());
    }
  }

  private Activity extractActivity(Context paramContext)
  {
    if ((paramContext instanceof Activity))
      return (Activity)paramContext;
    return null;
  }

  public static <T extends Kit> T getKit(Class<T> paramClass)
  {
    return (Kit)singleton().kits.get(paramClass);
  }

  private static Map<Class<? extends Kit>, Kit> getKitMap(Collection<? extends Kit> paramCollection)
  {
    HashMap localHashMap = new HashMap(paramCollection.size());
    addToKitMap(localHashMap, paramCollection);
    return localHashMap;
  }

  public static Logger getLogger()
  {
    if (singleton == null)
      return DEFAULT_LOGGER;
    return singleton.logger;
  }

  private void init()
  {
    setCurrentActivity(extractActivity(this.context));
    this.activityLifecycleManager = new ActivityLifecycleManager(this.context);
    this.activityLifecycleManager.registerCallbacks(new ActivityLifecycleManager.Callbacks()
    {
      public void onActivityCreated(Activity paramAnonymousActivity, Bundle paramAnonymousBundle)
      {
        Fabric.this.setCurrentActivity(paramAnonymousActivity);
      }

      public void onActivityResumed(Activity paramAnonymousActivity)
      {
        Fabric.this.setCurrentActivity(paramAnonymousActivity);
      }

      public void onActivityStarted(Activity paramAnonymousActivity)
      {
        Fabric.this.setCurrentActivity(paramAnonymousActivity);
      }
    });
    initializeKits(this.context);
  }

  public static boolean isDebuggable()
  {
    if (singleton == null)
      return false;
    return singleton.debuggable;
  }

  public static boolean isInitialized()
  {
    return (singleton != null) && (singleton.initialized.get());
  }

  private static void setFabric(Fabric paramFabric)
  {
    singleton = paramFabric;
    paramFabric.init();
  }

  static Fabric singleton()
  {
    if (singleton == null)
      throw new IllegalStateException("Must Initialize Fabric before using singleton()");
    return singleton;
  }

  public static Fabric with(Context paramContext, Kit[] paramArrayOfKit)
  {
    if (singleton == null);
    try
    {
      if (singleton == null)
        setFabric(new Builder(paramContext).kits(paramArrayOfKit).build());
      return singleton;
    }
    finally
    {
    }
  }

  public static Fabric with(Fabric paramFabric)
  {
    if (singleton == null);
    try
    {
      if (singleton == null)
        setFabric(paramFabric);
      return singleton;
    }
    finally
    {
    }
  }

  void addAnnotatedDependencies(Map<Class<? extends Kit>, Kit> paramMap, Kit paramKit)
  {
    DependsOn localDependsOn = (DependsOn)paramKit.getClass().getAnnotation(DependsOn.class);
    if (localDependsOn != null)
      for (Class localClass : localDependsOn.value())
      {
        if (localClass.isInterface())
        {
          Iterator localIterator = paramMap.values().iterator();
          while (localIterator.hasNext())
          {
            Kit localKit = (Kit)localIterator.next();
            if (localClass.isAssignableFrom(localKit.getClass()))
              paramKit.initializationTask.addDependency(localKit.initializationTask);
          }
        }
        if ((Kit)paramMap.get(localClass) == null)
          throw new UnmetDependencyException("Referenced Kit was null, does the kit exist?");
        paramKit.initializationTask.addDependency(((Kit)paramMap.get(localClass)).initializationTask);
      }
  }

  InitializationCallback<?> createKitInitializationCallback(final int paramInt)
  {
    return new InitializationCallback()
    {
      final CountDownLatch kitInitializedLatch = new CountDownLatch(paramInt);

      public void failure(Exception paramAnonymousException)
      {
        Fabric.this.initializationCallback.failure(paramAnonymousException);
      }

      public void success(Object paramAnonymousObject)
      {
        this.kitInitializedLatch.countDown();
        if (this.kitInitializedLatch.getCount() == 0L)
        {
          Fabric.this.initialized.set(true);
          Fabric.this.initializationCallback.success(Fabric.this);
        }
      }
    };
  }

  public ActivityLifecycleManager getActivityLifecycleManager()
  {
    return this.activityLifecycleManager;
  }

  public String getAppIdentifier()
  {
    return this.idManager.getAppIdentifier();
  }

  public String getAppInstallIdentifier()
  {
    return this.idManager.getAppInstallIdentifier();
  }

  public Activity getCurrentActivity()
  {
    if (this.activity != null)
      return (Activity)this.activity.get();
    return null;
  }

  public ExecutorService getExecutorService()
  {
    return this.executorService;
  }

  public String getIdentifier()
  {
    return "io.fabric.sdk.android:fabric";
  }

  public Collection<Kit> getKits()
  {
    return this.kits.values();
  }

  public Handler getMainHandler()
  {
    return this.mainHandler;
  }

  public String getVersion()
  {
    return "1.1.1.32";
  }

  void initializeKits(Context paramContext)
  {
    Collection localCollection = getKits();
    this.onboarding = new Onboarding(localCollection);
    ArrayList localArrayList = new ArrayList(localCollection);
    Collections.sort(localArrayList);
    this.onboarding.injectParameters(paramContext, this, InitializationCallback.EMPTY, this.idManager);
    Iterator localIterator1 = localArrayList.iterator();
    while (localIterator1.hasNext())
      ((Kit)localIterator1.next()).injectParameters(paramContext, this, this.kitInitializationCallback, this.idManager);
    this.onboarding.initialize();
    if (getLogger().isLoggable("Fabric", 3));
    for (StringBuilder localStringBuilder = new StringBuilder("Initializing ").append(getIdentifier()).append(" [Version: ").append(getVersion()).append("], with the following kits:\n"); ; localStringBuilder = null)
    {
      Iterator localIterator2 = localArrayList.iterator();
      while (localIterator2.hasNext())
      {
        Kit localKit = (Kit)localIterator2.next();
        localKit.initializationTask.addDependency(this.onboarding.initializationTask);
        addAnnotatedDependencies(this.kits, localKit);
        localKit.initialize();
        if (localStringBuilder != null)
          localStringBuilder.append(localKit.getIdentifier()).append(" [Version: ").append(localKit.getVersion()).append("]\n");
      }
    }
    if (localStringBuilder != null)
      getLogger().d("Fabric", localStringBuilder.toString());
  }

  public Fabric setCurrentActivity(Activity paramActivity)
  {
    this.activity = new WeakReference(paramActivity);
    return this;
  }

  public static class Builder
  {
    private String appIdentifier;
    private String appInstallIdentifier;
    private final Context context;
    private boolean debuggable;
    private Handler handler;
    private InitializationCallback<Fabric> initializationCallback;
    private Kit[] kits;
    private Logger logger;
    private PriorityThreadPoolExecutor threadPoolExecutor;

    public Builder(Context paramContext)
    {
      if (paramContext == null)
        throw new IllegalArgumentException("Context must not be null.");
      this.context = paramContext.getApplicationContext();
    }

    public Builder appIdentifier(String paramString)
    {
      if (paramString == null)
        throw new IllegalArgumentException("appIdentifier must not be null.");
      if (this.appIdentifier != null)
        throw new IllegalStateException("appIdentifier already set.");
      this.appIdentifier = paramString;
      return this;
    }

    public Builder appInstallIdentifier(String paramString)
    {
      if (paramString == null)
        throw new IllegalArgumentException("appInstallIdentifier must not be null.");
      if (this.appInstallIdentifier != null)
        throw new IllegalStateException("appInstallIdentifier already set.");
      this.appInstallIdentifier = paramString;
      return this;
    }

    public Fabric build()
    {
      if (this.kits == null)
        throw new IllegalStateException("Kits must not be null.");
      if (this.threadPoolExecutor == null)
        this.threadPoolExecutor = PriorityThreadPoolExecutor.create();
      if (this.handler == null)
        this.handler = new Handler(Looper.getMainLooper());
      if (this.logger == null)
        if (!this.debuggable)
          break label181;
      label181: for (this.logger = new DefaultLogger(3); ; this.logger = new DefaultLogger())
      {
        if (this.appIdentifier == null)
          this.appIdentifier = this.context.getPackageName();
        if (this.initializationCallback == null)
          this.initializationCallback = InitializationCallback.EMPTY;
        Map localMap = Fabric.getKitMap(Arrays.asList(this.kits));
        IdManager localIdManager = new IdManager(this.context, this.appIdentifier, this.appInstallIdentifier, localMap.values());
        return new Fabric(this.context, localMap, this.threadPoolExecutor, this.handler, this.logger, this.debuggable, this.initializationCallback, localIdManager);
      }
    }

    public Builder debuggable(boolean paramBoolean)
    {
      this.debuggable = paramBoolean;
      return this;
    }

    @Deprecated
    public Builder executorService(ExecutorService paramExecutorService)
    {
      return this;
    }

    @Deprecated
    public Builder handler(Handler paramHandler)
    {
      return this;
    }

    public Builder initializationCallback(InitializationCallback<Fabric> paramInitializationCallback)
    {
      if (paramInitializationCallback == null)
        throw new IllegalArgumentException("initializationCallback must not be null.");
      if (this.initializationCallback != null)
        throw new IllegalStateException("initializationCallback already set.");
      this.initializationCallback = paramInitializationCallback;
      return this;
    }

    public Builder kits(Kit[] paramArrayOfKit)
    {
      if (paramArrayOfKit == null)
        throw new IllegalArgumentException("Kits must not be null.");
      if (paramArrayOfKit.length == 0)
        throw new IllegalArgumentException("Kits must not be empty.");
      if (this.kits != null)
        throw new IllegalStateException("Kits already set.");
      this.kits = paramArrayOfKit;
      return this;
    }

    public Builder logger(Logger paramLogger)
    {
      if (paramLogger == null)
        throw new IllegalArgumentException("Logger must not be null.");
      if (this.logger != null)
        throw new IllegalStateException("Logger already set.");
      this.logger = paramLogger;
      return this;
    }

    public Builder threadPoolExecutor(PriorityThreadPoolExecutor paramPriorityThreadPoolExecutor)
    {
      if (paramPriorityThreadPoolExecutor == null)
        throw new IllegalArgumentException("PriorityThreadPoolExecutor must not be null.");
      if (this.threadPoolExecutor != null)
        throw new IllegalStateException("PriorityThreadPoolExecutor already set.");
      this.threadPoolExecutor = paramPriorityThreadPoolExecutor;
      return this;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.Fabric
 * JD-Core Version:    0.6.2
 */