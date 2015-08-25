package com.google.android.gms.common;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Looper;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class zza
  implements ServiceConnection
{
  boolean zzOG = false;
  private final BlockingQueue<IBinder> zzOH = new LinkedBlockingQueue();

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    this.zzOH.add(paramIBinder);
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
  }

  public IBinder zzku()
    throws InterruptedException
  {
    if (Looper.myLooper() == Looper.getMainLooper())
      throw new IllegalStateException("BlockingServiceConnection.getService() called on main thread");
    if (this.zzOG)
      throw new IllegalStateException();
    this.zzOG = true;
    return (IBinder)this.zzOH.take();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.zza
 * JD-Core Version:    0.6.2
 */