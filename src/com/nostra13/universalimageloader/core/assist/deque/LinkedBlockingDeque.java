package com.nostra13.universalimageloader.core.assist.deque;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LinkedBlockingDeque<E> extends AbstractQueue<E>
  implements BlockingDeque<E>, Serializable
{
  private static final long serialVersionUID = -387911632671998426L;
  private final int capacity;
  private transient int count;
  transient Node<E> first;
  transient Node<E> last;
  final ReentrantLock lock = new ReentrantLock();
  private final Condition notEmpty = this.lock.newCondition();
  private final Condition notFull = this.lock.newCondition();

  public LinkedBlockingDeque()
  {
    this(2147483647);
  }

  public LinkedBlockingDeque(int paramInt)
  {
    if (paramInt <= 0)
      throw new IllegalArgumentException();
    this.capacity = paramInt;
  }

  public LinkedBlockingDeque(Collection<? extends E> paramCollection)
  {
    this(2147483647);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    while (true)
    {
      Object localObject2;
      try
      {
        Iterator localIterator = paramCollection.iterator();
        if (!localIterator.hasNext())
          break;
        localObject2 = localIterator.next();
        if (localObject2 == null)
          throw new NullPointerException();
      }
      finally
      {
        localReentrantLock.unlock();
      }
      if (!linkLast(new Node(localObject2)))
        throw new IllegalStateException("Deque full");
    }
    localReentrantLock.unlock();
  }

  private boolean linkFirst(Node<E> paramNode)
  {
    if (this.count >= this.capacity)
      return false;
    Node localNode = this.first;
    paramNode.next = localNode;
    this.first = paramNode;
    if (this.last == null)
      this.last = paramNode;
    while (true)
    {
      this.count = (1 + this.count);
      this.notEmpty.signal();
      return true;
      localNode.prev = paramNode;
    }
  }

  private boolean linkLast(Node<E> paramNode)
  {
    if (this.count >= this.capacity)
      return false;
    Node localNode = this.last;
    paramNode.prev = localNode;
    this.last = paramNode;
    if (this.first == null)
      this.first = paramNode;
    while (true)
    {
      this.count = (1 + this.count);
      this.notEmpty.signal();
      return true;
      localNode.next = paramNode;
    }
  }

  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    this.count = 0;
    this.first = null;
    this.last = null;
    while (true)
    {
      Object localObject = paramObjectInputStream.readObject();
      if (localObject == null)
        return;
      add(localObject);
    }
  }

  private E unlinkFirst()
  {
    Node localNode1 = this.first;
    if (localNode1 == null)
      return null;
    Node localNode2 = localNode1.next;
    Object localObject = localNode1.item;
    localNode1.item = null;
    localNode1.next = localNode1;
    this.first = localNode2;
    if (localNode2 == null)
      this.last = null;
    while (true)
    {
      this.count = (-1 + this.count);
      this.notFull.signal();
      return localObject;
      localNode2.prev = null;
    }
  }

  private E unlinkLast()
  {
    Node localNode1 = this.last;
    if (localNode1 == null)
      return null;
    Node localNode2 = localNode1.prev;
    Object localObject = localNode1.item;
    localNode1.item = null;
    localNode1.prev = localNode1;
    this.last = localNode2;
    if (localNode2 == null)
      this.first = null;
    while (true)
    {
      this.count = (-1 + this.count);
      this.notFull.signal();
      return localObject;
      localNode2.next = null;
    }
  }

  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      paramObjectOutputStream.defaultWriteObject();
      for (Node localNode = this.first; localNode != null; localNode = localNode.next)
        paramObjectOutputStream.writeObject(localNode.item);
      paramObjectOutputStream.writeObject(null);
      return;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public boolean add(E paramE)
  {
    addLast(paramE);
    return true;
  }

  public void addFirst(E paramE)
  {
    if (!offerFirst(paramE))
      throw new IllegalStateException("Deque full");
  }

  public void addLast(E paramE)
  {
    if (!offerLast(paramE))
      throw new IllegalStateException("Deque full");
  }

  public void clear()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      Node localNode;
      for (Object localObject2 = this.first; localObject2 != null; localObject2 = localNode)
      {
        ((Node)localObject2).item = null;
        localNode = ((Node)localObject2).next;
        ((Node)localObject2).prev = null;
        ((Node)localObject2).next = null;
      }
      this.last = null;
      this.first = null;
      this.count = 0;
      this.notFull.signalAll();
      return;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public boolean contains(Object paramObject)
  {
    if (paramObject == null)
      return false;
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      for (Node localNode = this.first; localNode != null; localNode = localNode.next)
      {
        boolean bool = paramObject.equals(localNode.item);
        if (bool)
          return true;
      }
      return false;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public Iterator<E> descendingIterator()
  {
    return new DescendingItr(null);
  }

  public int drainTo(Collection<? super E> paramCollection)
  {
    return drainTo(paramCollection, 2147483647);
  }

  public int drainTo(Collection<? super E> paramCollection, int paramInt)
  {
    if (paramCollection == null)
      throw new NullPointerException();
    if (paramCollection == this)
      throw new IllegalArgumentException();
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      int i = Math.min(paramInt, this.count);
      for (int j = 0; j < i; j++)
      {
        paramCollection.add(this.first.item);
        unlinkFirst();
      }
      return i;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E element()
  {
    return getFirst();
  }

  public E getFirst()
  {
    Object localObject = peekFirst();
    if (localObject == null)
      throw new NoSuchElementException();
    return localObject;
  }

  public E getLast()
  {
    Object localObject = peekLast();
    if (localObject == null)
      throw new NoSuchElementException();
    return localObject;
  }

  public Iterator<E> iterator()
  {
    return new Itr(null);
  }

  public boolean offer(E paramE)
  {
    return offerLast(paramE);
  }

  public boolean offer(E paramE, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    return offerLast(paramE, paramLong, paramTimeUnit);
  }

  public boolean offerFirst(E paramE)
  {
    if (paramE == null)
      throw new NullPointerException();
    Node localNode = new Node(paramE);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      boolean bool = linkFirst(localNode);
      return bool;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public boolean offerFirst(E paramE, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (paramE == null)
      throw new NullPointerException();
    Node localNode = new Node(paramE);
    long l1 = paramTimeUnit.toNanos(paramLong);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lockInterruptibly();
    try
    {
      while (true)
      {
        boolean bool = linkFirst(localNode);
        if (bool)
          break;
        if (l1 <= 0L)
          return false;
        long l2 = this.notFull.awaitNanos(l1);
        l1 = l2;
      }
      return true;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public boolean offerLast(E paramE)
  {
    if (paramE == null)
      throw new NullPointerException();
    Node localNode = new Node(paramE);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      boolean bool = linkLast(localNode);
      return bool;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public boolean offerLast(E paramE, long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    if (paramE == null)
      throw new NullPointerException();
    Node localNode = new Node(paramE);
    long l1 = paramTimeUnit.toNanos(paramLong);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lockInterruptibly();
    try
    {
      while (true)
      {
        boolean bool = linkLast(localNode);
        if (bool)
          break;
        if (l1 <= 0L)
          return false;
        long l2 = this.notFull.awaitNanos(l1);
        l1 = l2;
      }
      return true;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E peek()
  {
    return peekFirst();
  }

  public E peekFirst()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      Node localNode = this.first;
      if (localNode == null);
      for (Object localObject2 = null; ; localObject2 = this.first.item)
        return localObject2;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E peekLast()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      Node localNode = this.last;
      if (localNode == null);
      for (Object localObject2 = null; ; localObject2 = this.last.item)
        return localObject2;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E poll()
  {
    return pollFirst();
  }

  public E poll(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    return pollFirst(paramLong, paramTimeUnit);
  }

  public E pollFirst()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      Object localObject2 = unlinkFirst();
      return localObject2;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E pollFirst(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    long l1 = paramTimeUnit.toNanos(paramLong);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lockInterruptibly();
    try
    {
      Object localObject2;
      while (true)
      {
        localObject2 = unlinkFirst();
        if (localObject2 != null)
          break;
        if (l1 <= 0L)
          return null;
        long l2 = this.notEmpty.awaitNanos(l1);
        l1 = l2;
      }
      return localObject2;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E pollLast()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      Object localObject2 = unlinkLast();
      return localObject2;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E pollLast(long paramLong, TimeUnit paramTimeUnit)
    throws InterruptedException
  {
    long l1 = paramTimeUnit.toNanos(paramLong);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lockInterruptibly();
    try
    {
      Object localObject2;
      while (true)
      {
        localObject2 = unlinkLast();
        if (localObject2 != null)
          break;
        if (l1 <= 0L)
          return null;
        long l2 = this.notEmpty.awaitNanos(l1);
        l1 = l2;
      }
      return localObject2;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E pop()
  {
    return removeFirst();
  }

  public void push(E paramE)
  {
    addFirst(paramE);
  }

  public void put(E paramE)
    throws InterruptedException
  {
    putLast(paramE);
  }

  public void putFirst(E paramE)
    throws InterruptedException
  {
    if (paramE == null)
      throw new NullPointerException();
    Node localNode = new Node(paramE);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      if (!linkFirst(localNode))
        this.notFull.await();
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public void putLast(E paramE)
    throws InterruptedException
  {
    if (paramE == null)
      throw new NullPointerException();
    Node localNode = new Node(paramE);
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      if (!linkLast(localNode))
        this.notFull.await();
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public int remainingCapacity()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      int i = this.capacity;
      int j = this.count;
      int k = i - j;
      return k;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E remove()
  {
    return removeFirst();
  }

  public boolean remove(Object paramObject)
  {
    return removeFirstOccurrence(paramObject);
  }

  public E removeFirst()
  {
    Object localObject = pollFirst();
    if (localObject == null)
      throw new NoSuchElementException();
    return localObject;
  }

  public boolean removeFirstOccurrence(Object paramObject)
  {
    if (paramObject == null)
      return false;
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      for (Node localNode = this.first; localNode != null; localNode = localNode.next)
        if (paramObject.equals(localNode.item))
        {
          unlink(localNode);
          return true;
        }
      return false;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E removeLast()
  {
    Object localObject = pollLast();
    if (localObject == null)
      throw new NoSuchElementException();
    return localObject;
  }

  public boolean removeLastOccurrence(Object paramObject)
  {
    if (paramObject == null)
      return false;
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      for (Node localNode = this.last; localNode != null; localNode = localNode.prev)
        if (paramObject.equals(localNode.item))
        {
          unlink(localNode);
          return true;
        }
      return false;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public int size()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      int i = this.count;
      return i;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public E take()
    throws InterruptedException
  {
    return takeFirst();
  }

  public E takeFirst()
    throws InterruptedException
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    Object localObject2;
    try
    {
      while (true)
      {
        localObject2 = unlinkFirst();
        if (localObject2 != null)
          break;
        this.notEmpty.await();
      }
    }
    finally
    {
      localReentrantLock.unlock();
    }
    localReentrantLock.unlock();
    return localObject2;
  }

  public E takeLast()
    throws InterruptedException
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    Object localObject2;
    try
    {
      while (true)
      {
        localObject2 = unlinkLast();
        if (localObject2 != null)
          break;
        this.notEmpty.await();
      }
    }
    finally
    {
      localReentrantLock.unlock();
    }
    localReentrantLock.unlock();
    return localObject2;
  }

  public Object[] toArray()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      Object[] arrayOfObject = new Object[this.count];
      Node localNode = this.first;
      int j;
      for (int i = 0; localNode != null; i = j)
      {
        j = i + 1;
        arrayOfObject[i] = localNode.item;
        localNode = localNode.next;
      }
      return arrayOfObject;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public <T> T[] toArray(T[] paramArrayOfT)
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      if (paramArrayOfT.length < this.count)
        paramArrayOfT = (Object[])Array.newInstance(paramArrayOfT.getClass().getComponentType(), this.count);
      Node localNode = this.first;
      int j;
      for (int i = 0; localNode != null; i = j)
      {
        j = i + 1;
        paramArrayOfT[i] = localNode.item;
        localNode = localNode.next;
      }
      if (paramArrayOfT.length > i)
        paramArrayOfT[i] = null;
      return paramArrayOfT;
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  public String toString()
  {
    ReentrantLock localReentrantLock = this.lock;
    localReentrantLock.lock();
    try
    {
      Node localNode = this.first;
      if (localNode == null)
        return "[]";
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append('[');
      while (true)
      {
        Object localObject2 = localNode.item;
        if (localObject2 == this)
          localObject2 = "(this Collection)";
        localStringBuilder.append(localObject2);
        localNode = localNode.next;
        if (localNode == null)
        {
          String str = ']';
          return str;
        }
        localStringBuilder.append(',').append(' ');
      }
    }
    finally
    {
      localReentrantLock.unlock();
    }
  }

  void unlink(Node<E> paramNode)
  {
    Node localNode1 = paramNode.prev;
    Node localNode2 = paramNode.next;
    if (localNode1 == null)
    {
      unlinkFirst();
      return;
    }
    if (localNode2 == null)
    {
      unlinkLast();
      return;
    }
    localNode1.next = localNode2;
    localNode2.prev = localNode1;
    paramNode.item = null;
    this.count = (-1 + this.count);
    this.notFull.signal();
  }

  private abstract class AbstractItr
    implements Iterator<E>
  {
    private LinkedBlockingDeque.Node<E> lastRet;
    LinkedBlockingDeque.Node<E> next;
    E nextItem;

    AbstractItr()
    {
      ReentrantLock localReentrantLock = LinkedBlockingDeque.this.lock;
      localReentrantLock.lock();
      try
      {
        this.next = firstNode();
        if (this.next == null);
        for (Object localObject2 = null; ; localObject2 = this.next.item)
        {
          this.nextItem = localObject2;
          return;
        }
      }
      finally
      {
        localReentrantLock.unlock();
      }
    }

    private LinkedBlockingDeque.Node<E> succ(LinkedBlockingDeque.Node<E> paramNode)
    {
      while (true)
      {
        LinkedBlockingDeque.Node localNode = nextNode(paramNode);
        if (localNode == null)
          localNode = null;
        while (localNode.item != null)
          return localNode;
        if (localNode == paramNode)
          return firstNode();
        paramNode = localNode;
      }
    }

    void advance()
    {
      ReentrantLock localReentrantLock = LinkedBlockingDeque.this.lock;
      localReentrantLock.lock();
      try
      {
        this.next = succ(this.next);
        if (this.next == null);
        for (Object localObject2 = null; ; localObject2 = this.next.item)
        {
          this.nextItem = localObject2;
          return;
        }
      }
      finally
      {
        localReentrantLock.unlock();
      }
    }

    abstract LinkedBlockingDeque.Node<E> firstNode();

    public boolean hasNext()
    {
      return this.next != null;
    }

    public E next()
    {
      if (this.next == null)
        throw new NoSuchElementException();
      this.lastRet = this.next;
      Object localObject = this.nextItem;
      advance();
      return localObject;
    }

    abstract LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> paramNode);

    public void remove()
    {
      LinkedBlockingDeque.Node localNode = this.lastRet;
      if (localNode == null)
        throw new IllegalStateException();
      this.lastRet = null;
      ReentrantLock localReentrantLock = LinkedBlockingDeque.this.lock;
      localReentrantLock.lock();
      try
      {
        if (localNode.item != null)
          LinkedBlockingDeque.this.unlink(localNode);
        return;
      }
      finally
      {
        localReentrantLock.unlock();
      }
    }
  }

  private class DescendingItr extends LinkedBlockingDeque.AbstractItr
  {
    private DescendingItr()
    {
      super();
    }

    LinkedBlockingDeque.Node<E> firstNode()
    {
      return LinkedBlockingDeque.this.last;
    }

    LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> paramNode)
    {
      return paramNode.prev;
    }
  }

  private class Itr extends LinkedBlockingDeque.AbstractItr
  {
    private Itr()
    {
      super();
    }

    LinkedBlockingDeque.Node<E> firstNode()
    {
      return LinkedBlockingDeque.this.first;
    }

    LinkedBlockingDeque.Node<E> nextNode(LinkedBlockingDeque.Node<E> paramNode)
    {
      return paramNode.next;
    }
  }

  static final class Node<E>
  {
    E item;
    Node<E> next;
    Node<E> prev;

    Node(E paramE)
    {
      this.item = paramE;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.nostra13.universalimageloader.core.assist.deque.LinkedBlockingDeque
 * JD-Core Version:    0.6.2
 */