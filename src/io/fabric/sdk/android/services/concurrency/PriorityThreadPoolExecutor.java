package io.fabric.sdk.android.services.concurrency;

import java.util.concurrent.Callable;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class PriorityThreadPoolExecutor extends ThreadPoolExecutor
{
  private static final int CORE_POOL_SIZE = 0;
  private static final int CPU_COUNT = 0;
  private static final long KEEP_ALIVE = 1L;
  private static final int MAXIMUM_POOL_SIZE = 1 + 2 * CPU_COUNT;

  <T extends Runnable,  extends Dependency,  extends Task,  extends PriorityProvider> PriorityThreadPoolExecutor(int paramInt1, int paramInt2, long paramLong, TimeUnit paramTimeUnit, DependencyPriorityBlockingQueue<T> paramDependencyPriorityBlockingQueue, ThreadFactory paramThreadFactory)
  {
    super(paramInt1, paramInt2, paramLong, paramTimeUnit, paramDependencyPriorityBlockingQueue, paramThreadFactory);
    prestartAllCoreThreads();
  }

  public static PriorityThreadPoolExecutor create()
  {
    return create(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE);
  }

  public static PriorityThreadPoolExecutor create(int paramInt)
  {
    return create(paramInt, paramInt);
  }

  public static <T extends Runnable,  extends Dependency,  extends Task,  extends PriorityProvider> PriorityThreadPoolExecutor create(int paramInt1, int paramInt2)
  {
    return new PriorityThreadPoolExecutor(paramInt1, paramInt2, 1L, TimeUnit.SECONDS, new DependencyPriorityBlockingQueue(), new PriorityThreadFactory(10));
  }

  protected void afterExecute(Runnable paramRunnable, Throwable paramThrowable)
  {
    Task localTask = (Task)paramRunnable;
    localTask.setFinished(true);
    localTask.setError(paramThrowable);
    getQueue().recycleBlockedQueue();
    super.afterExecute(paramRunnable, paramThrowable);
  }

  public void execute(Runnable paramRunnable)
  {
    if (PriorityTask.isProperDelegate(paramRunnable))
    {
      super.execute(paramRunnable);
      return;
    }
    super.execute(newTaskFor(paramRunnable, null));
  }

  public DependencyPriorityBlockingQueue getQueue()
  {
    return (DependencyPriorityBlockingQueue)super.getQueue();
  }

  protected <T> RunnableFuture<T> newTaskFor(Runnable paramRunnable, T paramT)
  {
    return new PriorityFutureTask(paramRunnable, paramT);
  }

  protected <T> RunnableFuture<T> newTaskFor(Callable<T> paramCallable)
  {
    return new PriorityFutureTask(paramCallable);
  }

  protected static final class PriorityThreadFactory
    implements ThreadFactory
  {
    private final int threadPriority;

    public PriorityThreadFactory(int paramInt)
    {
      this.threadPriority = paramInt;
    }

    public Thread newThread(Runnable paramRunnable)
    {
      Thread localThread = new Thread(paramRunnable);
      localThread.setPriority(this.threadPriority);
      localThread.setName("Queue");
      return localThread;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.PriorityThreadPoolExecutor
 * JD-Core Version:    0.6.2
 */