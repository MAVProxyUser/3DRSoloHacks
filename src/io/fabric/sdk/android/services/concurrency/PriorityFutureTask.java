package io.fabric.sdk.android.services.concurrency;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class PriorityFutureTask<V> extends FutureTask<V>
  implements Dependency<Task>, PriorityProvider, Task, DelegateProvider
{
  final Object delegate = checkAndInitDelegate(paramCallable);

  public PriorityFutureTask(Runnable paramRunnable, V paramV)
  {
    super(paramRunnable, paramV);
  }

  public PriorityFutureTask(Callable<V> paramCallable)
  {
    super(paramCallable);
  }

  public void addDependency(Task paramTask)
  {
    ((Dependency)getDelegate()).addDependency(paramTask);
  }

  public boolean areDependenciesMet()
  {
    return ((Dependency)getDelegate()).areDependenciesMet();
  }

  protected <T extends Dependency<Task>,  extends PriorityProvider,  extends Task> T checkAndInitDelegate(Object paramObject)
  {
    if (PriorityTask.isProperDelegate(paramObject))
      return (Dependency)paramObject;
    return new PriorityTask();
  }

  public int compareTo(Object paramObject)
  {
    return ((PriorityProvider)getDelegate()).compareTo(paramObject);
  }

  public <T extends Dependency<Task>,  extends PriorityProvider,  extends Task> T getDelegate()
  {
    return (Dependency)this.delegate;
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
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.PriorityFutureTask
 * JD-Core Version:    0.6.2
 */