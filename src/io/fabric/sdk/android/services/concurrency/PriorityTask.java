package io.fabric.sdk.android.services.concurrency;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class PriorityTask
  implements Dependency<Task>, PriorityProvider, Task
{
  private final List<Task> dependencies = new ArrayList();
  private final AtomicBoolean hasRun = new AtomicBoolean(false);
  private final AtomicReference<Throwable> throwable = new AtomicReference(null);

  public static boolean isProperDelegate(Object paramObject)
  {
    try
    {
      Dependency localDependency = (Dependency)paramObject;
      Task localTask = (Task)paramObject;
      PriorityProvider localPriorityProvider = (PriorityProvider)paramObject;
      boolean bool = false;
      if (localDependency != null)
      {
        bool = false;
        if (localTask != null)
        {
          bool = false;
          if (localPriorityProvider != null)
            bool = true;
        }
      }
      return bool;
    }
    catch (ClassCastException localClassCastException)
    {
    }
    return false;
  }

  public void addDependency(Task paramTask)
  {
    try
    {
      this.dependencies.add(paramTask);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean areDependenciesMet()
  {
    Iterator localIterator = getDependencies().iterator();
    while (localIterator.hasNext())
      if (!((Task)localIterator.next()).isFinished())
        return false;
    return true;
  }

  public int compareTo(Object paramObject)
  {
    return Priority.compareTo(this, paramObject);
  }

  public Collection<Task> getDependencies()
  {
    try
    {
      Collection localCollection = Collections.unmodifiableCollection(this.dependencies);
      return localCollection;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public Throwable getError()
  {
    return (Throwable)this.throwable.get();
  }

  public Priority getPriority()
  {
    return Priority.NORMAL;
  }

  public boolean isFinished()
  {
    return this.hasRun.get();
  }

  public void setError(Throwable paramThrowable)
  {
    this.throwable.set(paramThrowable);
  }

  public void setFinished(boolean paramBoolean)
  {
    try
    {
      this.hasRun.set(paramBoolean);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.PriorityTask
 * JD-Core Version:    0.6.2
 */