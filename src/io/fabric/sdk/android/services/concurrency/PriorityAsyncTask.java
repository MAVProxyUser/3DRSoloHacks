package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public abstract class PriorityAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result>
  implements Dependency<Task>, PriorityProvider, Task, DelegateProvider
{
  private final PriorityTask priorityTask = new PriorityTask();

  public void addDependency(Task paramTask)
  {
    if (getStatus() != AsyncTask.Status.PENDING)
      throw new IllegalStateException("Must not add Dependency after task is running");
    ((Dependency)getDelegate()).addDependency(paramTask);
  }

  public boolean areDependenciesMet()
  {
    return ((Dependency)getDelegate()).areDependenciesMet();
  }

  public int compareTo(Object paramObject)
  {
    return Priority.compareTo(this, paramObject);
  }

  public final void executeOnExecutor(ExecutorService paramExecutorService, Params[] paramArrayOfParams)
  {
    super.executeOnExecutor(new ProxyExecutor(paramExecutorService, this), paramArrayOfParams);
  }

  public <T extends Dependency<Task>,  extends PriorityProvider,  extends Task> T getDelegate()
  {
    return this.priorityTask;
  }

  public Collection<Task> getDependencies()
  {
    return ((Dependency)getDelegate()).getDependencies();
  }

  public Throwable getError()
  {
    return ((Task)getDelegate()).getError();
  }

  public Priority getPriority()
  {
    return ((PriorityProvider)getDelegate()).getPriority();
  }

  public boolean isFinished()
  {
    return ((Task)getDelegate()).isFinished();
  }

  public void setError(Throwable paramThrowable)
  {
    ((Task)getDelegate()).setError(paramThrowable);
  }

  public void setFinished(boolean paramBoolean)
  {
    ((Task)getDelegate()).setFinished(paramBoolean);
  }

  private static class ProxyExecutor<Result>
    implements Executor
  {
    private final Executor executor;
    private final PriorityAsyncTask task;

    public ProxyExecutor(Executor paramExecutor, PriorityAsyncTask paramPriorityAsyncTask)
    {
      this.executor = paramExecutor;
      this.task = paramPriorityAsyncTask;
    }

    public void execute(Runnable paramRunnable)
    {
      this.executor.execute(new PriorityFutureTask(paramRunnable, null)
      {
        public <T extends Dependency<Task>,  extends PriorityProvider,  extends Task> T getDelegate()
        {
          return PriorityAsyncTask.this;
        }
      });
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.PriorityAsyncTask
 * JD-Core Version:    0.6.2
 */