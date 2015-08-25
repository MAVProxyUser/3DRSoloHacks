package com.segment.analytics;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Pair;
import com.segment.analytics.internal.AbstractIntegration;
import com.segment.analytics.internal.Utils;
import com.segment.analytics.internal.model.payloads.AliasPayload;
import com.segment.analytics.internal.model.payloads.BasePayload;
import com.segment.analytics.internal.model.payloads.GroupPayload;
import com.segment.analytics.internal.model.payloads.IdentifyPayload;
import com.segment.analytics.internal.model.payloads.ScreenPayload;
import com.segment.analytics.internal.model.payloads.TrackPayload;
import java.lang.reflect.Constructor;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class IntegrationManager
  implements Application.ActivityLifecycleCallbacks
{
  private static final String INTEGRATION_MANAGER_THREAD_NAME = "SegmentAnalytics-IntegrationManager";
  private static final long SETTINGS_REFRESH_INTERVAL = 86400000L;
  private static final long SETTINGS_RETRY_INTERVAL = 60000L;
  final Analytics analytics;
  final Application application;
  final Map<String, Boolean> bundledIntegrations = new ConcurrentHashMap();
  Map<String, Analytics.Callback> callbacks;
  final Cartographer cartographer;
  final Client client;
  volatile boolean initialized;
  final Handler integrationManagerHandler;
  final HandlerThread integrationManagerThread;
  final List<AbstractIntegration> integrations = new ArrayList();
  final Analytics.LogLevel logLevel;
  final ExecutorService networkExecutor;
  Queue<IntegrationOperation> operationQueue;
  final ProjectSettings.Cache projectSettingsCache;
  final SegmentDispatcher segmentDispatcher;
  final Stats stats;

  IntegrationManager(Analytics paramAnalytics, Client paramClient, ExecutorService paramExecutorService, Cartographer paramCartographer, Stats paramStats, ProjectSettings.Cache paramCache, String paramString, long paramLong, int paramInt)
  {
    this.analytics = paramAnalytics;
    this.application = paramAnalytics.getApplication();
    this.client = paramClient;
    this.networkExecutor = paramExecutorService;
    this.cartographer = paramCartographer;
    this.stats = paramStats;
    this.projectSettingsCache = paramCache;
    this.logLevel = paramAnalytics.getLogLevel();
    this.application.registerActivityLifecycleCallbacks(this);
    this.integrationManagerThread = new HandlerThread("SegmentAnalytics-IntegrationManager", 10);
    this.integrationManagerThread.start();
    this.integrationManagerHandler = new IntegrationManagerHandler(this.integrationManagerThread.getLooper(), this);
    loadIntegrations();
    this.segmentDispatcher = SegmentDispatcher.create(this.application, paramClient, paramCartographer, paramExecutorService, paramStats, Collections.unmodifiableMap(this.bundledIntegrations), paramString, paramLong, paramInt, this.logLevel);
    this.integrations.add(this.segmentDispatcher);
    if ((paramCache.isSet()) && (paramCache.get() != null))
    {
      dispatchInitializeIntegrations((ProjectSettings)paramCache.get());
      if (86400000L + ((ProjectSettings)paramCache.get()).timestamp() < System.currentTimeMillis())
        dispatchFetchSettings();
      return;
    }
    dispatchFetchSettings();
  }

  static IntegrationManager create(Analytics paramAnalytics, Cartographer paramCartographer, Client paramClient, ExecutorService paramExecutorService, Stats paramStats, String paramString, long paramLong, int paramInt)
  {
    try
    {
      IntegrationManager localIntegrationManager = new IntegrationManager(paramAnalytics, paramClient, paramExecutorService, paramCartographer, paramStats, new ProjectSettings.Cache(paramAnalytics.getApplication(), paramCartographer, paramString), paramString, paramLong, paramInt);
      return localIntegrationManager;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  private void dispatchEnqueue(IntegrationOperation paramIntegrationOperation)
  {
    this.integrationManagerHandler.sendMessage(this.integrationManagerHandler.obtainMessage(3, paramIntegrationOperation));
  }

  private void dispatchRetryFetchSettings()
  {
    this.integrationManagerHandler.sendMessageDelayed(this.integrationManagerHandler.obtainMessage(1), 60000L);
  }

  private void initializeIntegrations(ValueMap paramValueMap)
  {
    Iterator localIterator = this.integrations.iterator();
    while (localIterator.hasNext())
    {
      AbstractIntegration localAbstractIntegration = (AbstractIntegration)localIterator.next();
      String str = localAbstractIntegration.key();
      ValueMap localValueMap = paramValueMap.getValueMap(str);
      boolean bool1 = Utils.isNullOrEmpty(localValueMap);
      int i = 0;
      if (!bool1)
        if (this.logLevel.log())
          Utils.debug("Initializing integration %s with settings %s.", new Object[] { str, localValueMap });
      try
      {
        localAbstractIntegration.initialize(this.analytics, localValueMap);
        i = 1;
        if (i != 0)
        {
          if (Utils.isNullOrEmpty(this.callbacks))
            continue;
          Analytics.Callback localCallback = (Analytics.Callback)this.callbacks.get(str);
          if (localCallback == null)
            continue;
          localCallback.onReady(localAbstractIntegration.getUnderlyingInstance());
        }
      }
      catch (Exception localException)
      {
        while (true)
        {
          boolean bool2 = this.logLevel.log();
          i = 0;
          if (bool2)
          {
            Utils.error(localException, "Could not initialize integration %s.", new Object[] { str });
            i = 0;
          }
        }
        localIterator.remove();
        this.bundledIntegrations.remove(str);
      }
    }
  }

  private void loadIntegration(Class<AbstractIntegration> paramClass)
  {
    try
    {
      Constructor localConstructor = paramClass.getDeclaredConstructor(new Class[0]);
      localConstructor.setAccessible(true);
      AbstractIntegration localAbstractIntegration = (AbstractIntegration)localConstructor.newInstance(new Object[0]);
      this.integrations.add(localAbstractIntegration);
      this.bundledIntegrations.put(localAbstractIntegration.key(), Boolean.valueOf(false));
      return;
    }
    catch (Exception localException)
    {
      throw new AssertionError("Could not create instance of " + paramClass.getCanonicalName() + ".\n" + localException);
    }
  }

  private void loadIntegrations()
  {
    loadIntegration("com.segment.analytics.internal.integrations.AmplitudeIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.AppsFlyerIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.ApptimizeIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.BugsnagIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.CountlyIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.CrittercismIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.FlurryIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.GoogleAnalyticsIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.KahunaIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.LeanplumIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.LocalyticsIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.MixpanelIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.QuantcastIntegration");
    loadIntegration("com.segment.analytics.internal.integrations.TapstreamIntegration");
  }

  private void replay()
  {
    if (!Utils.isNullOrEmpty(this.operationQueue))
    {
      Iterator localIterator = this.operationQueue.iterator();
      while (localIterator.hasNext())
      {
        run((IntegrationOperation)localIterator.next());
        localIterator.remove();
      }
    }
    this.operationQueue = null;
  }

  void dispatchEnqueue(BasePayload paramBasePayload)
  {
    this.integrationManagerHandler.sendMessage(this.integrationManagerHandler.obtainMessage(4, paramBasePayload));
  }

  void dispatchFetchSettings()
  {
    this.integrationManagerHandler.sendMessage(this.integrationManagerHandler.obtainMessage(1));
  }

  void dispatchFlush()
  {
    dispatchEnqueue(IntegrationOperation.flush());
  }

  void dispatchInitializeIntegrations(ProjectSettings paramProjectSettings)
  {
    this.integrationManagerHandler.sendMessageAtFrontOfQueue(this.integrationManagerHandler.obtainMessage(2, paramProjectSettings));
  }

  void dispatchRegisterCallback(String paramString, Analytics.Callback paramCallback)
  {
    this.integrationManagerHandler.sendMessage(this.integrationManagerHandler.obtainMessage(5, new Pair(paramString, paramCallback)));
  }

  public void dispatchReset()
  {
    dispatchEnqueue(IntegrationOperation.reset());
  }

  void loadIntegration(String paramString)
  {
    try
    {
      loadIntegration(Class.forName(paramString));
      return;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      while (!this.logLevel.log());
      Utils.debug("Integration for class %s not bundled.", new Object[] { paramString });
    }
  }

  public void onActivityCreated(Activity paramActivity, Bundle paramBundle)
  {
    dispatchEnqueue(IntegrationOperation.onActivityCreated(paramActivity, paramBundle));
  }

  public void onActivityDestroyed(Activity paramActivity)
  {
    dispatchEnqueue(IntegrationOperation.onActivityDestroyed(paramActivity));
  }

  public void onActivityPaused(Activity paramActivity)
  {
    dispatchEnqueue(IntegrationOperation.onActivityPaused(paramActivity));
  }

  public void onActivityResumed(Activity paramActivity)
  {
    dispatchEnqueue(IntegrationOperation.onActivityResumed(paramActivity));
  }

  public void onActivitySaveInstanceState(Activity paramActivity, Bundle paramBundle)
  {
    dispatchEnqueue(IntegrationOperation.onActivitySaveInstanceState(paramActivity, paramBundle));
  }

  public void onActivityStarted(Activity paramActivity)
  {
    dispatchEnqueue(IntegrationOperation.onActivityStarted(paramActivity));
  }

  public void onActivityStopped(Activity paramActivity)
  {
    dispatchEnqueue(IntegrationOperation.onActivityStopped(paramActivity));
  }

  void performEnqueue(IntegrationOperation paramIntegrationOperation)
  {
    if (this.initialized)
    {
      run(paramIntegrationOperation);
      return;
    }
    if (this.logLevel.log())
      Utils.debug("Enqueuing action %s.", new Object[] { paramIntegrationOperation });
    if (this.operationQueue == null)
      this.operationQueue = new ArrayDeque();
    this.operationQueue.add(paramIntegrationOperation);
  }

  void performEnqueue(BasePayload paramBasePayload)
  {
    IntegrationOperation localIntegrationOperation;
    switch (2.$SwitchMap$com$segment$analytics$internal$model$payloads$BasePayload$Type[paramBasePayload.type().ordinal()])
    {
    default:
      throw new AssertionError("unknown type " + paramBasePayload.type());
    case 1:
      localIntegrationOperation = IntegrationOperation.identify((IdentifyPayload)paramBasePayload);
    case 2:
    case 3:
    case 4:
    case 5:
    }
    while (true)
    {
      performEnqueue(localIntegrationOperation);
      return;
      localIntegrationOperation = IntegrationOperation.alias((AliasPayload)paramBasePayload);
      continue;
      localIntegrationOperation = IntegrationOperation.group((GroupPayload)paramBasePayload);
      continue;
      localIntegrationOperation = IntegrationOperation.track((TrackPayload)paramBasePayload);
      continue;
      localIntegrationOperation = IntegrationOperation.screen((ScreenPayload)paramBasePayload);
    }
  }

  void performFetchSettings()
  {
    if (!Utils.isConnected(this.application))
      dispatchRetryFetchSettings();
    while (true)
    {
      return;
      try
      {
        ProjectSettings localProjectSettings = (ProjectSettings)this.networkExecutor.submit(new Callable()
        {
          public ProjectSettings call()
            throws Exception
          {
            Client.Connection localConnection = null;
            try
            {
              localConnection = IntegrationManager.this.client.fetchSettings();
              ProjectSettings localProjectSettings = ProjectSettings.create(IntegrationManager.this.cartographer.fromJson(Utils.buffer(localConnection.is)));
              return localProjectSettings;
            }
            finally
            {
              Utils.closeQuietly(localConnection);
            }
          }
        }).get();
        this.projectSettingsCache.set(localProjectSettings);
        dispatchInitializeIntegrations(localProjectSettings);
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        if (this.logLevel.log())
        {
          Utils.error(localInterruptedException, "Thread interrupted while fetching settings.", new Object[0]);
          return;
        }
      }
      catch (ExecutionException localExecutionException)
      {
        if (this.logLevel.log())
        {
          Object[] arrayOfObject = new Object[1];
          arrayOfObject[0] = Long.valueOf(60000L);
          Utils.error(localExecutionException, "Unable to fetch settings. Retrying in %s ms.", arrayOfObject);
        }
        dispatchRetryFetchSettings();
      }
    }
  }

  void performInitializeIntegrations(ProjectSettings paramProjectSettings)
  {
    if (this.initialized)
      return;
    ValueMap localValueMap = paramProjectSettings.integrations();
    if (Utils.isNullOrEmpty(localValueMap))
    {
      if (this.logLevel.log())
        Utils.error(null, "No integrations enabled in %s. Make sure you have the correct writeKey.", new Object[] { paramProjectSettings });
      this.bundledIntegrations.clear();
      this.integrations.clear();
    }
    while (true)
    {
      if (this.callbacks != null)
      {
        this.callbacks.clear();
        this.callbacks = null;
      }
      replay();
      this.initialized = true;
      return;
      if (this.logLevel.log())
        Utils.debug("Initializing integrations with %s.", new Object[] { localValueMap });
      initializeIntegrations(localValueMap);
    }
  }

  void performRegisterCallback(String paramString, Analytics.Callback paramCallback)
  {
    Iterator localIterator;
    if ((this.initialized) && (paramCallback != null))
      localIterator = this.integrations.iterator();
    while (localIterator.hasNext())
    {
      AbstractIntegration localAbstractIntegration = (AbstractIntegration)localIterator.next();
      if (paramString.equals(localAbstractIntegration.key()))
      {
        paramCallback.onReady(localAbstractIntegration.getUnderlyingInstance());
        continue;
        if (paramCallback != null)
          break label93;
        if (this.callbacks != null)
          this.callbacks.remove(paramString);
      }
    }
    return;
    label93: if (this.callbacks == null)
      this.callbacks = new HashMap();
    this.callbacks.put(paramString, paramCallback);
  }

  void run(IntegrationOperation paramIntegrationOperation)
  {
    if (this.logLevel.log())
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramIntegrationOperation;
      arrayOfObject2[1] = Integer.valueOf(this.integrations.size());
      Utils.debug("Running %s on %s integrations.", arrayOfObject2);
    }
    for (int i = 0; i < this.integrations.size(); i++)
    {
      AbstractIntegration localAbstractIntegration = (AbstractIntegration)this.integrations.get(i);
      long l1 = System.nanoTime();
      paramIntegrationOperation.run(localAbstractIntegration, (ProjectSettings)this.projectSettingsCache.get());
      long l2 = System.nanoTime();
      long l3 = TimeUnit.NANOSECONDS.toMillis(l2 - l1);
      if (this.logLevel.log())
      {
        Object[] arrayOfObject1 = new Object[3];
        arrayOfObject1[0] = Long.valueOf(l3);
        arrayOfObject1[1] = paramIntegrationOperation;
        arrayOfObject1[2] = localAbstractIntegration.key();
        Utils.debug("Took %s ms to run action %s on %s.", arrayOfObject1);
      }
      this.stats.dispatchIntegrationOperation(localAbstractIntegration.key(), l3);
    }
  }

  void shutdown()
  {
    this.application.unregisterActivityLifecycleCallbacks(this);
    this.integrationManagerThread.quit();
    this.segmentDispatcher.shutdown();
    if (this.operationQueue != null)
    {
      this.operationQueue.clear();
      this.operationQueue = null;
    }
  }

  static abstract interface Factory
  {
    public abstract IntegrationManager create(Analytics paramAnalytics);
  }

  static class IntegrationManagerHandler extends Handler
  {
    private static final int REQUEST_ENQUEUE_OPERATION = 3;
    private static final int REQUEST_ENQUEUE_PAYLOAD = 4;
    static final int REQUEST_FETCH_SETTINGS = 1;
    static final int REQUEST_INITIALIZE_INTEGRATIONS = 2;
    private static final int REQUEST_REGISTER_CALLBACK = 5;
    private final IntegrationManager integrationManager;

    IntegrationManagerHandler(Looper paramLooper, IntegrationManager paramIntegrationManager)
    {
      super();
      this.integrationManager = paramIntegrationManager;
    }

    public void handleMessage(Message paramMessage)
    {
      switch (paramMessage.what)
      {
      default:
        throw new AssertionError("Unknown Integration Manager handler message: " + paramMessage);
      case 1:
        this.integrationManager.performFetchSettings();
        return;
      case 2:
        this.integrationManager.performInitializeIntegrations((ProjectSettings)paramMessage.obj);
        return;
      case 3:
        this.integrationManager.performEnqueue((IntegrationOperation)paramMessage.obj);
        return;
      case 4:
        this.integrationManager.performEnqueue((BasePayload)paramMessage.obj);
        return;
      case 5:
      }
      Pair localPair = (Pair)paramMessage.obj;
      this.integrationManager.performRegisterCallback((String)localPair.first, (Analytics.Callback)localPair.second);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.segment.analytics.IntegrationManager
 * JD-Core Version:    0.6.2
 */