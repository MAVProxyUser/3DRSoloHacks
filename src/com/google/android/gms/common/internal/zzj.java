package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzj
  implements Handler.Callback
{
  private final Handler mHandler;
  private final zza zzTD;
  private final ArrayList<GoogleApiClient.ConnectionCallbacks> zzTE = new ArrayList();
  final ArrayList<GoogleApiClient.ConnectionCallbacks> zzTF = new ArrayList();
  private final ArrayList<GoogleApiClient.OnConnectionFailedListener> zzTG = new ArrayList();
  private volatile boolean zzTH = false;
  private final AtomicInteger zzTI = new AtomicInteger(0);
  private boolean zzTJ = false;
  private final Object zzoe = new Object();

  public zzj(Looper paramLooper, zza paramzza)
  {
    this.zzTD = paramzza;
    this.mHandler = new Handler(paramLooper, this);
  }

  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what == 1)
    {
      GoogleApiClient.ConnectionCallbacks localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)paramMessage.obj;
      synchronized (this.zzoe)
      {
        if ((this.zzTH) && (this.zzTD.isConnected()) && (this.zzTE.contains(localConnectionCallbacks)))
          localConnectionCallbacks.onConnected(this.zzTD.zzjZ());
        return true;
      }
    }
    Log.wtf("GmsClientEvents", "Don't know how to handle this message.");
    return false;
  }

  public boolean isConnectionCallbacksRegistered(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzv.zzr(paramConnectionCallbacks);
    synchronized (this.zzoe)
    {
      boolean bool = this.zzTE.contains(paramConnectionCallbacks);
      return bool;
    }
  }

  public boolean isConnectionFailedListenerRegistered(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzv.zzr(paramOnConnectionFailedListener);
    synchronized (this.zzoe)
    {
      boolean bool = this.zzTG.contains(paramOnConnectionFailedListener);
      return bool;
    }
  }

  public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzv.zzr(paramConnectionCallbacks);
    synchronized (this.zzoe)
    {
      if (this.zzTE.contains(paramConnectionCallbacks))
      {
        Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + paramConnectionCallbacks + " is already registered");
        if (this.zzTD.isConnected())
          this.mHandler.sendMessage(this.mHandler.obtainMessage(1, paramConnectionCallbacks));
        return;
      }
      this.zzTE.add(paramConnectionCallbacks);
    }
  }

  public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzv.zzr(paramOnConnectionFailedListener);
    synchronized (this.zzoe)
    {
      if (this.zzTG.contains(paramOnConnectionFailedListener))
      {
        Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " is already registered");
        return;
      }
      this.zzTG.add(paramOnConnectionFailedListener);
    }
  }

  public void unregisterConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    zzv.zzr(paramConnectionCallbacks);
    synchronized (this.zzoe)
    {
      if (!this.zzTE.remove(paramConnectionCallbacks))
        Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + paramConnectionCallbacks + " not found");
      while (!this.zzTJ)
        return;
      this.zzTF.add(paramConnectionCallbacks);
    }
  }

  public void unregisterConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzv.zzr(paramOnConnectionFailedListener);
    synchronized (this.zzoe)
    {
      if (!this.zzTG.remove(paramOnConnectionFailedListener))
        Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " not found");
      return;
    }
  }

  public void zzaP(int paramInt)
  {
    this.mHandler.removeMessages(1);
    synchronized (this.zzoe)
    {
      this.zzTJ = true;
      ArrayList localArrayList = new ArrayList(this.zzTE);
      int i = this.zzTI.get();
      Iterator localIterator = localArrayList.iterator();
      GoogleApiClient.ConnectionCallbacks localConnectionCallbacks;
      do
        if (localIterator.hasNext())
        {
          localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)localIterator.next();
          if ((this.zzTH) && (this.zzTI.get() == i));
        }
        else
        {
          this.zzTF.clear();
          this.zzTJ = false;
          return;
        }
      while (!this.zzTE.contains(localConnectionCallbacks));
      localConnectionCallbacks.onConnectionSuspended(paramInt);
    }
  }

  public void zzg(Bundle paramBundle)
  {
    boolean bool1 = true;
    while (true)
    {
      synchronized (this.zzoe)
      {
        if (!this.zzTJ)
        {
          bool2 = bool1;
          zzv.zzP(bool2);
          this.mHandler.removeMessages(1);
          this.zzTJ = true;
          if (this.zzTF.size() != 0)
            break label184;
          zzv.zzP(bool1);
          ArrayList localArrayList = new ArrayList(this.zzTE);
          int i = this.zzTI.get();
          Iterator localIterator = localArrayList.iterator();
          GoogleApiClient.ConnectionCallbacks localConnectionCallbacks;
          if (localIterator.hasNext())
          {
            localConnectionCallbacks = (GoogleApiClient.ConnectionCallbacks)localIterator.next();
            if ((this.zzTH) && (this.zzTD.isConnected()) && (this.zzTI.get() == i));
          }
          else
          {
            this.zzTF.clear();
            this.zzTJ = false;
            return;
          }
          if (this.zzTF.contains(localConnectionCallbacks))
            continue;
          localConnectionCallbacks.onConnected(paramBundle);
        }
      }
      boolean bool2 = false;
      continue;
      label184: bool1 = false;
    }
  }

  public void zzj(ConnectionResult paramConnectionResult)
  {
    this.mHandler.removeMessages(1);
    synchronized (this.zzoe)
    {
      ArrayList localArrayList = new ArrayList(this.zzTG);
      int i = this.zzTI.get();
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = (GoogleApiClient.OnConnectionFailedListener)localIterator.next();
        if ((!this.zzTH) || (this.zzTI.get() != i))
          return;
        if (this.zzTG.contains(localOnConnectionFailedListener))
          localOnConnectionFailedListener.onConnectionFailed(paramConnectionResult);
      }
    }
  }

  public void zzmf()
  {
    this.zzTH = false;
    this.zzTI.incrementAndGet();
  }

  public void zzmg()
  {
    this.zzTH = true;
  }

  protected void zzmh()
  {
    synchronized (this.zzoe)
    {
      zzg(this.zzTD.zzjZ());
      return;
    }
  }

  public static abstract interface zza
  {
    public abstract boolean isConnected();

    public abstract Bundle zzjZ();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzj
 * JD-Core Version:    0.6.2
 */