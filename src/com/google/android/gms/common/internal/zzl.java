package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.IBinder;
import android.os.Message;
import com.google.android.gms.common.stats.zzb;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

final class zzl extends zzk
  implements Handler.Callback
{
  private final Handler mHandler;
  private final HashMap<zza, zzb> zzTM = new HashMap();
  private final zzb zzTN;
  private final long zzTO;
  private final Context zzoh;

  zzl(Context paramContext)
  {
    this.zzoh = paramContext.getApplicationContext();
    this.mHandler = new Handler(paramContext.getMainLooper(), this);
    this.zzTN = zzb.zznb();
    this.zzTO = 5000L;
  }

  private boolean zza(zza paramzza, ServiceConnection paramServiceConnection, String paramString)
  {
    zzv.zzb(paramServiceConnection, "ServiceConnection must not be null");
    while (true)
    {
      zzb localzzb;
      synchronized (this.zzTM)
      {
        localzzb = (zzb)this.zzTM.get(paramzza);
        if (localzzb == null)
        {
          localzzb = new zzb(paramzza);
          localzzb.zza(paramServiceConnection, paramString);
          localzzb.zzbM(paramString);
          this.zzTM.put(paramzza, localzzb);
          boolean bool = localzzb.isBound();
          return bool;
        }
        this.mHandler.removeMessages(0, localzzb);
        if (localzzb.zza(paramServiceConnection))
          throw new IllegalStateException("Trying to bind a GmsServiceConnection that was already connected before.  config=" + paramzza);
      }
      localzzb.zza(paramServiceConnection, paramString);
      switch (localzzb.getState())
      {
      case 1:
        paramServiceConnection.onServiceConnected(localzzb.getComponentName(), localzzb.getBinder());
        break;
      case 2:
        localzzb.zzbM(paramString);
      }
    }
  }

  private void zzb(zza paramzza, ServiceConnection paramServiceConnection, String paramString)
  {
    zzv.zzb(paramServiceConnection, "ServiceConnection must not be null");
    zzb localzzb;
    synchronized (this.zzTM)
    {
      localzzb = (zzb)this.zzTM.get(paramzza);
      if (localzzb == null)
        throw new IllegalStateException("Nonexistent connection status for service config: " + paramzza);
    }
    if (!localzzb.zza(paramServiceConnection))
      throw new IllegalStateException("Trying to unbind a GmsServiceConnection  that was not bound before.  config=" + paramzza);
    localzzb.zzb(paramServiceConnection, paramString);
    if (localzzb.zzmj())
    {
      Message localMessage = this.mHandler.obtainMessage(0, localzzb);
      this.mHandler.sendMessageDelayed(localMessage, this.zzTO);
    }
  }

  public boolean handleMessage(Message paramMessage)
  {
    switch (paramMessage.what)
    {
    default:
      return false;
    case 0:
    }
    zzb localzzb = (zzb)paramMessage.obj;
    synchronized (this.zzTM)
    {
      if (localzzb.zzmj())
      {
        if (localzzb.isBound())
          localzzb.zzbN("GmsClientSupervisor");
        this.zzTM.remove(zzb.zza(localzzb));
      }
      return true;
    }
  }

  public boolean zza(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString)
  {
    return zza(new zza(paramComponentName), paramServiceConnection, paramString);
  }

  public boolean zza(String paramString1, ServiceConnection paramServiceConnection, String paramString2)
  {
    return zza(new zza(paramString1), paramServiceConnection, paramString2);
  }

  public void zzb(ComponentName paramComponentName, ServiceConnection paramServiceConnection, String paramString)
  {
    zzb(new zza(paramComponentName), paramServiceConnection, paramString);
  }

  public void zzb(String paramString1, ServiceConnection paramServiceConnection, String paramString2)
  {
    zzb(new zza(paramString1), paramServiceConnection, paramString2);
  }

  private static final class zza
  {
    private final ComponentName zzTP;
    private final String zzso;

    public zza(ComponentName paramComponentName)
    {
      this.zzso = null;
      this.zzTP = ((ComponentName)zzv.zzr(paramComponentName));
    }

    public zza(String paramString)
    {
      this.zzso = zzv.zzbS(paramString);
      this.zzTP = null;
    }

    public boolean equals(Object paramObject)
    {
      if (this == paramObject);
      zza localzza;
      do
      {
        return true;
        if (!(paramObject instanceof zza))
          return false;
        localzza = (zza)paramObject;
      }
      while ((zzu.equal(this.zzso, localzza.zzso)) && (zzu.equal(this.zzTP, localzza.zzTP)));
      return false;
    }

    public int hashCode()
    {
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = this.zzso;
      arrayOfObject[1] = this.zzTP;
      return zzu.hashCode(arrayOfObject);
    }

    public String toString()
    {
      if (this.zzso == null)
        return this.zzTP.flattenToString();
      return this.zzso;
    }

    public Intent zzmi()
    {
      if (this.zzso != null)
        return new Intent(this.zzso).setPackage("com.google.android.gms");
      return new Intent().setComponent(this.zzTP);
    }
  }

  private final class zzb
  {
    private int mState;
    private IBinder zzSU;
    private ComponentName zzTP;
    private final zza zzTQ;
    private final Set<ServiceConnection> zzTR;
    private boolean zzTS;
    private final zzl.zza zzTT;

    public zzb(zzl.zza arg2)
    {
      Object localObject;
      this.zzTT = localObject;
      this.zzTQ = new zza();
      this.zzTR = new HashSet();
      this.mState = 2;
    }

    public IBinder getBinder()
    {
      return this.zzSU;
    }

    public ComponentName getComponentName()
    {
      return this.zzTP;
    }

    public int getState()
    {
      return this.mState;
    }

    public boolean isBound()
    {
      return this.zzTS;
    }

    public void zza(ServiceConnection paramServiceConnection, String paramString)
    {
      zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), paramServiceConnection, paramString, this.zzTT.zzmi());
      this.zzTR.add(paramServiceConnection);
    }

    public boolean zza(ServiceConnection paramServiceConnection)
    {
      return this.zzTR.contains(paramServiceConnection);
    }

    public void zzb(ServiceConnection paramServiceConnection, String paramString)
    {
      zzl.zzc(zzl.this).zzb(zzl.zzb(zzl.this), paramServiceConnection);
      this.zzTR.remove(paramServiceConnection);
    }

    public void zzbM(String paramString)
    {
      this.zzTS = zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), paramString, this.zzTT.zzmi(), this.zzTQ, 129);
      if (this.zzTS)
      {
        this.mState = 3;
        return;
      }
      zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), this.zzTQ);
    }

    public void zzbN(String paramString)
    {
      zzl.zzc(zzl.this).zza(zzl.zzb(zzl.this), this.zzTQ);
      this.zzTS = false;
      this.mState = 2;
    }

    public boolean zzmj()
    {
      return this.zzTR.isEmpty();
    }

    public class zza
      implements ServiceConnection
    {
      public zza()
      {
      }

      public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
      {
        synchronized (zzl.zza(zzl.this))
        {
          zzl.zzb.zza(zzl.zzb.this, paramIBinder);
          zzl.zzb.zza(zzl.zzb.this, paramComponentName);
          Iterator localIterator = zzl.zzb.zzb(zzl.zzb.this).iterator();
          if (localIterator.hasNext())
            ((ServiceConnection)localIterator.next()).onServiceConnected(paramComponentName, paramIBinder);
        }
        zzl.zzb.zza(zzl.zzb.this, 1);
      }

      public void onServiceDisconnected(ComponentName paramComponentName)
      {
        synchronized (zzl.zza(zzl.this))
        {
          zzl.zzb.zza(zzl.zzb.this, null);
          zzl.zzb.zza(zzl.zzb.this, paramComponentName);
          Iterator localIterator = zzl.zzb.zzb(zzl.zzb.this).iterator();
          if (localIterator.hasNext())
            ((ServiceConnection)localIterator.next()).onServiceDisconnected(paramComponentName);
        }
        zzl.zzb.zza(zzl.zzb.this, 2);
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzl
 * JD-Core Version:    0.6.2
 */