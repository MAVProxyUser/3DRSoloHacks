package com.squareup.okhttp;

import com.squareup.okhttp.internal.Platform;
import com.squareup.okhttp.internal.Util;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class ConnectionPool
{
  private static final long DEFAULT_KEEP_ALIVE_DURATION_MS = 300000L;
  private static final ConnectionPool systemDefault = new ConnectionPool(5, l);
  private final LinkedList<Connection> connections = new LinkedList();
  private final Runnable connectionsCleanupRunnable = new Runnable()
  {
    public void run()
    {
      ConnectionPool.this.runCleanupUntilPoolIsEmpty();
    }
  };
  private Executor executor = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp ConnectionPool", true));
  private final long keepAliveDurationNs;
  private final int maxIdleConnections;

  static
  {
    String str1 = System.getProperty("http.keepAlive");
    String str2 = System.getProperty("http.keepAliveDuration");
    String str3 = System.getProperty("http.maxConnections");
    if (str2 != null);
    for (long l = Long.parseLong(str2); (str1 != null) && (!Boolean.parseBoolean(str1)); l = 300000L)
    {
      systemDefault = new ConnectionPool(0, l);
      return;
    }
    if (str3 != null)
    {
      systemDefault = new ConnectionPool(Integer.parseInt(str3), l);
      return;
    }
  }

  public ConnectionPool(int paramInt, long paramLong)
  {
    this.maxIdleConnections = paramInt;
    this.keepAliveDurationNs = (1000L * (paramLong * 1000L));
  }

  private void addConnection(Connection paramConnection)
  {
    boolean bool = this.connections.isEmpty();
    this.connections.addFirst(paramConnection);
    if (bool)
    {
      this.executor.execute(this.connectionsCleanupRunnable);
      return;
    }
    notifyAll();
  }

  public static ConnectionPool getDefault()
  {
    return systemDefault;
  }

  private void runCleanupUntilPoolIsEmpty()
  {
    while (performCleanup());
  }

  public void evictAll()
  {
    try
    {
      ArrayList localArrayList = new ArrayList(this.connections);
      this.connections.clear();
      notifyAll();
      int i = 0;
      int j = localArrayList.size();
      while (i < j)
      {
        Util.closeQuietly(((Connection)localArrayList.get(i)).getSocket());
        i++;
      }
    }
    finally
    {
    }
  }

  public Connection get(Address paramAddress)
  {
    try
    {
      ListIterator localListIterator = this.connections.listIterator(this.connections.size());
      while (true)
      {
        boolean bool1 = localListIterator.hasPrevious();
        Object localObject2 = null;
        Connection localConnection;
        if (bool1)
        {
          localConnection = (Connection)localListIterator.previous();
          if ((localConnection.getRoute().getAddress().equals(paramAddress)) && (localConnection.isAlive()) && (System.nanoTime() - localConnection.getIdleStartTimeNs() < this.keepAliveDurationNs))
          {
            localListIterator.remove();
            boolean bool2 = localConnection.isSpdy();
            if (bool2);
          }
        }
        else
        {
          try
          {
            Platform.get().tagSocket(localConnection.getSocket());
            localObject2 = localConnection;
            if ((localObject2 != null) && (localObject2.isSpdy()))
              this.connections.addFirst(localObject2);
            return localObject2;
          }
          catch (SocketException localSocketException)
          {
            Util.closeQuietly(localConnection.getSocket());
            Platform.get().logW("Unable to tagSocket(): " + localSocketException);
          }
        }
      }
    }
    finally
    {
    }
  }

  public int getConnectionCount()
  {
    try
    {
      int i = this.connections.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  List<Connection> getConnections()
  {
    try
    {
      ArrayList localArrayList = new ArrayList(this.connections);
      return localArrayList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int getHttpConnectionCount()
  {
    try
    {
      int i = this.connections.size();
      int j = getMultiplexedConnectionCount();
      int k = i - j;
      return k;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int getMultiplexedConnectionCount()
  {
    int i = 0;
    try
    {
      Iterator localIterator = this.connections.iterator();
      while (localIterator.hasNext())
      {
        boolean bool = ((Connection)localIterator.next()).isSpdy();
        if (bool)
          i++;
      }
      return i;
    }
    finally
    {
    }
  }

  @Deprecated
  public int getSpdyConnectionCount()
  {
    try
    {
      int i = getMultiplexedConnectionCount();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  boolean performCleanup()
  {
    ArrayList localArrayList;
    int i;
    long l2;
    while (true)
    {
      Connection localConnection2;
      long l4;
      try
      {
        if (this.connections.isEmpty())
          return false;
        localArrayList = new ArrayList();
        i = 0;
        long l1 = System.nanoTime();
        l2 = this.keepAliveDurationNs;
        ListIterator localListIterator1 = this.connections.listIterator(this.connections.size());
        if (!localListIterator1.hasPrevious())
          break;
        localConnection2 = (Connection)localListIterator1.previous();
        l4 = localConnection2.getIdleStartTimeNs() + this.keepAliveDurationNs - l1;
        if ((l4 <= 0L) || (!localConnection2.isAlive()))
        {
          localListIterator1.remove();
          localArrayList.add(localConnection2);
          continue;
        }
      }
      finally
      {
      }
      if (localConnection2.isIdle())
      {
        i++;
        l2 = Math.min(l2, l4);
      }
    }
    ListIterator localListIterator2 = this.connections.listIterator(this.connections.size());
    while ((localListIterator2.hasPrevious()) && (i > this.maxIdleConnections))
    {
      Connection localConnection1 = (Connection)localListIterator2.previous();
      if (localConnection1.isIdle())
      {
        localArrayList.add(localConnection1);
        localListIterator2.remove();
        i--;
      }
    }
    boolean bool = localArrayList.isEmpty();
    if (bool);
    try
    {
      long l3 = l2 / 1000000L;
      wait(l3, (int)(l2 - 1000000L * l3));
      return true;
      label269: int j = 0;
      int k = localArrayList.size();
      while (j < k)
      {
        Util.closeQuietly(((Connection)localArrayList.get(j)).getSocket());
        j++;
      }
      return true;
    }
    catch (InterruptedException localInterruptedException)
    {
      break label269;
    }
  }

  void recycle(Connection paramConnection)
  {
    if (paramConnection.isSpdy());
    while (!paramConnection.clearOwner())
      return;
    if (!paramConnection.isAlive())
    {
      Util.closeQuietly(paramConnection.getSocket());
      return;
    }
    try
    {
      Platform.get().untagSocket(paramConnection.getSocket());
      try
      {
        addConnection(paramConnection);
        paramConnection.incrementRecycleCount();
        paramConnection.resetIdleStartTime();
        return;
      }
      finally
      {
      }
    }
    catch (SocketException localSocketException)
    {
      Platform.get().logW("Unable to untagSocket(): " + localSocketException);
      Util.closeQuietly(paramConnection.getSocket());
    }
  }

  void replaceCleanupExecutorForTests(Executor paramExecutor)
  {
    this.executor = paramExecutor;
  }

  void share(Connection paramConnection)
  {
    if (!paramConnection.isSpdy())
      throw new IllegalArgumentException();
    if (!paramConnection.isAlive())
      return;
    try
    {
      addConnection(paramConnection);
      return;
    }
    finally
    {
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.ConnectionPool
 * JD-Core Version:    0.6.2
 */