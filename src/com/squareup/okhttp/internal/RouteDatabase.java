package com.squareup.okhttp.internal;

import com.squareup.okhttp.Route;
import java.util.LinkedHashSet;
import java.util.Set;

public final class RouteDatabase
{
  private final Set<Route> failedRoutes = new LinkedHashSet();

  public void connected(Route paramRoute)
  {
    try
    {
      this.failedRoutes.remove(paramRoute);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void failed(Route paramRoute)
  {
    try
    {
      this.failedRoutes.add(paramRoute);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public int failedRoutesCount()
  {
    try
    {
      int i = this.failedRoutes.size();
      return i;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public boolean shouldPostpone(Route paramRoute)
  {
    try
    {
      boolean bool = this.failedRoutes.contains(paramRoute);
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.squareup.okhttp.internal.RouteDatabase
 * JD-Core Version:    0.6.2
 */