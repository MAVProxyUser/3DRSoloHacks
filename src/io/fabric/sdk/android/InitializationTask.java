package io.fabric.sdk.android;

import io.fabric.sdk.android.services.common.TimingMetric;
import io.fabric.sdk.android.services.concurrency.Priority;
import io.fabric.sdk.android.services.concurrency.PriorityAsyncTask;

class InitializationTask<Result> extends PriorityAsyncTask<Void, Void, Result>
{
  private static final String TIMING_METRIC_TAG = "KitInitialization";
  final Kit<Result> kit;

  public InitializationTask(Kit<Result> paramKit)
  {
    this.kit = paramKit;
  }

  private TimingMetric createAndStartTimingMetric(String paramString)
  {
    TimingMetric localTimingMetric = new TimingMetric(this.kit.getIdentifier() + "." + paramString, "KitInitialization");
    localTimingMetric.startMeasuring();
    return localTimingMetric;
  }

  protected Result doInBackground(Void[] paramArrayOfVoid)
  {
    TimingMetric localTimingMetric = createAndStartTimingMetric("doInBackground");
    boolean bool = isCancelled();
    Object localObject = null;
    if (!bool)
      localObject = this.kit.doInBackground();
    localTimingMetric.stopMeasuring();
    return localObject;
  }

  public Priority getPriority()
  {
    return Priority.HIGH;
  }

  protected void onCancelled(Result paramResult)
  {
    this.kit.onCancelled(paramResult);
    InitializationException localInitializationException = new InitializationException(this.kit.getIdentifier() + " Initialization was cancelled");
    this.kit.initializationCallback.failure(localInitializationException);
  }

  protected void onPostExecute(Result paramResult)
  {
    this.kit.onPostExecute(paramResult);
    this.kit.initializationCallback.success(paramResult);
  }

  // ERROR //
  protected void onPreExecute()
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 109	io/fabric/sdk/android/services/concurrency/PriorityAsyncTask:onPreExecute	()V
    //   4: aload_0
    //   5: ldc 110
    //   7: invokespecial 57	io/fabric/sdk/android/InitializationTask:createAndStartTimingMetric	(Ljava/lang/String;)Lio/fabric/sdk/android/services/common/TimingMetric;
    //   10: astore_1
    //   11: aload_0
    //   12: getfield 19	io/fabric/sdk/android/InitializationTask:kit	Lio/fabric/sdk/android/Kit;
    //   15: invokevirtual 112	io/fabric/sdk/android/Kit:onPreExecute	()Z
    //   18: istore 7
    //   20: aload_1
    //   21: invokevirtual 67	io/fabric/sdk/android/services/common/TimingMetric:stopMeasuring	()V
    //   24: iload 7
    //   26: ifne +9 -> 35
    //   29: aload_0
    //   30: iconst_1
    //   31: invokevirtual 116	io/fabric/sdk/android/InitializationTask:cancel	(Z)Z
    //   34: pop
    //   35: return
    //   36: astore 6
    //   38: aload 6
    //   40: athrow
    //   41: astore 4
    //   43: aload_1
    //   44: invokevirtual 67	io/fabric/sdk/android/services/common/TimingMetric:stopMeasuring	()V
    //   47: iconst_0
    //   48: ifne +9 -> 57
    //   51: aload_0
    //   52: iconst_1
    //   53: invokevirtual 116	io/fabric/sdk/android/InitializationTask:cancel	(Z)Z
    //   56: pop
    //   57: aload 4
    //   59: athrow
    //   60: astore_2
    //   61: invokestatic 122	io/fabric/sdk/android/Fabric:getLogger	()Lio/fabric/sdk/android/Logger;
    //   64: ldc 124
    //   66: ldc 126
    //   68: aload_2
    //   69: invokeinterface 132 4 0
    //   74: aload_1
    //   75: invokevirtual 67	io/fabric/sdk/android/services/common/TimingMetric:stopMeasuring	()V
    //   78: iconst_0
    //   79: ifne -44 -> 35
    //   82: aload_0
    //   83: iconst_1
    //   84: invokevirtual 116	io/fabric/sdk/android/InitializationTask:cancel	(Z)Z
    //   87: pop
    //   88: return
    //
    // Exception table:
    //   from	to	target	type
    //   11	20	36	io/fabric/sdk/android/services/concurrency/UnmetDependencyException
    //   11	20	41	finally
    //   38	41	41	finally
    //   61	74	41	finally
    //   11	20	60	java/lang/Exception
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.InitializationTask
 * JD-Core Version:    0.6.2
 */