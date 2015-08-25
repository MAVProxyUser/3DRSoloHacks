package io.fabric.sdk.android.services.concurrency;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class DependencyPriorityBlockingQueue<E extends Dependency,  extends Task,  extends PriorityProvider> extends PriorityBlockingQueue<E>
{
  static final int PEEK = 1;
  static final int POLL = 2;
  static final int POLL_WITH_TIMEOUT = 3;
  static final int TAKE;
  final Queue<E> blockedQueue = new LinkedList();
  private final ReentrantLock lock = new ReentrantLock();

  boolean canProcess(E paramE)
  {
    return paramE.areDependenciesMet();
  }

  public void clear()
  {
    try
    {
      this.lock.lock();
      this.blockedQueue.clear();
      super.clear();
      return;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  <T> T[] concatenate(T[] paramArrayOfT1, T[] paramArrayOfT2)
  {
    int i = paramArrayOfT1.length;
    int j = paramArrayOfT2.length;
    Object[] arrayOfObject = (Object[])Array.newInstance(paramArrayOfT1.getClass().getComponentType(), i + j);
    System.arraycopy(paramArrayOfT1, 0, arrayOfObject, 0, i);
    System.arraycopy(paramArrayOfT2, 0, arrayOfObject, i, j);
    return arrayOfObject;
  }

  public boolean contains(Object paramObject)
  {
    try
    {
      this.lock.lock();
      if (!super.contains(paramObject))
      {
        boolean bool2 = this.blockedQueue.contains(paramObject);
        if (!bool2);
      }
      else
      {
        bool1 = true;
        return bool1;
      }
      boolean bool1 = false;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public int drainTo(Collection<? super E> paramCollection)
  {
    int i;
    try
    {
      this.lock.lock();
      i = super.drainTo(paramCollection) + this.blockedQueue.size();
      while (!this.blockedQueue.isEmpty())
        paramCollection.add(this.blockedQueue.poll());
    }
    finally
    {
      this.lock.unlock();
    }
    this.lock.unlock();
    return i;
  }

  public int drainTo(Collection<? super E> paramCollection, int paramInt)
  {
    try
    {
      this.lock.lock();
      for (int i = super.drainTo(paramCollection, paramInt); (!this.blockedQueue.isEmpty()) && (i <= paramInt); i++)
        paramCollection.add(this.blockedQueue.poll());
      return i;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  E get(int paramInt, Long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    while (true)
    {
      Dependency localDependency = performOperation(paramInt, paramLong, paramTimeUnit);
      if ((localDependency == null) || (canProcess(localDependency)))
        return localDependency;
      offerBlockedResult(paramInt, localDependency);
    }
  }

  boolean offerBlockedResult(int paramInt, E paramE)
  {
    try
    {
      this.lock.lock();
      if (paramInt == 1)
        super.remove(paramE);
      boolean bool = this.blockedQueue.offer(paramE);
      return bool;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public E peek()
  {
    try
    {
      Dependency localDependency = get(1, null, null);
      return localDependency;
    }
    catch (InterruptedException localInterruptedException)
    {
    }
    return null;
  }

  E performOperation(int paramInt, Long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    switch (paramInt)
    {
    default:
      return null;
    case 0:
      return (Dependency)super.take();
    case 1:
      return (Dependency)super.peek();
    case 2:
      return (Dependency)super.poll();
    case 3:
    }
    return (Dependency)super.poll(paramLong.longValue(), paramTimeUnit);
  }

  public E poll()
  {
    try
    {
      Dependency localDependency = get(2, null, null);
      return localDependency;
    }
    catch (InterruptedException localInterruptedException)
    {
    }
    return null;
  }

  public E poll(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    return get(3, Long.valueOf(paramLong), paramTimeUnit);
  }

  public void recycleBlockedQueue()
  {
    try
    {
      this.lock.lock();
      Iterator localIterator = this.blockedQueue.iterator();
      while (localIterator.hasNext())
      {
        Dependency localDependency = (Dependency)localIterator.next();
        if (canProcess(localDependency))
        {
          super.offer(localDependency);
          localIterator.remove();
        }
      }
    }
    finally
    {
      this.lock.unlock();
    }
    this.lock.unlock();
  }

  public boolean remove(Object paramObject)
  {
    try
    {
      this.lock.lock();
      if (!super.remove(paramObject))
      {
        boolean bool2 = this.blockedQueue.remove(paramObject);
        if (!bool2);
      }
      else
      {
        bool1 = true;
        return bool1;
      }
      boolean bool1 = false;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public boolean removeAll(Collection<?> paramCollection)
  {
    try
    {
      this.lock.lock();
      boolean bool1 = super.removeAll(paramCollection);
      boolean bool2 = this.blockedQueue.removeAll(paramCollection);
      boolean bool3 = bool1 | bool2;
      return bool3;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public int size()
  {
    try
    {
      this.lock.lock();
      int i = this.blockedQueue.size();
      int j = super.size();
      int k = i + j;
      return k;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public E take()
    throws InterruptedException
  {
    return get(0, null, null);
  }

  public Object[] toArray()
  {
    try
    {
      this.lock.lock();
      Object[] arrayOfObject = concatenate(super.toArray(), this.blockedQueue.toArray());
      return arrayOfObject;
    }
    finally
    {
      this.lock.unlock();
    }
  }

  public <T> T[] toArray(T[] paramArrayOfT)
  {
    try
    {
      this.lock.lock();
      Object[] arrayOfObject = concatenate(super.toArray(paramArrayOfT), this.blockedQueue.toArray(paramArrayOfT));
      return arrayOfObject;
    }
    finally
    {
      this.lock.unlock();
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     io.fabric.sdk.android.services.concurrency.DependencyPriorityBlockingQueue
 * JD-Core Version:    0.6.2
 */