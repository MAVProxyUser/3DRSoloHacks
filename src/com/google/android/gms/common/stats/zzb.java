package com.google.android.gms.common.stats;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Debug;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.internal.zzhc;
import com.google.android.gms.internal.zzid;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class zzb
{
  private static final Object zzTK = new Object();
  private static zzb zzVc;
  private static final ComponentName zzVh = new ComponentName("com.google.android.gms", "com.google.android.gms.common.stats.GmsCoreStatsService");
  private final List<String> zzVd;
  private final List<String> zzVe;
  private final List<String> zzVf;
  private final List<String> zzVg;
  private zze zzVi;

  private zzb()
  {
    if (getLogLevel() == zzd.zzVq)
    {
      this.zzVd = Collections.EMPTY_LIST;
      this.zzVe = Collections.EMPTY_LIST;
      this.zzVf = Collections.EMPTY_LIST;
      this.zzVg = Collections.EMPTY_LIST;
      return;
    }
    String str1 = (String)zzc.zza.zzVl.get();
    List localList1;
    String str2;
    List localList2;
    label85: String str3;
    List localList3;
    label112: String str4;
    if (str1 == null)
    {
      localList1 = Collections.EMPTY_LIST;
      this.zzVd = localList1;
      str2 = (String)zzc.zza.zzVm.get();
      if (str2 != null)
        break label185;
      localList2 = Collections.EMPTY_LIST;
      this.zzVe = localList2;
      str3 = (String)zzc.zza.zzVn.get();
      if (str3 != null)
        break label199;
      localList3 = Collections.EMPTY_LIST;
      this.zzVf = localList3;
      str4 = (String)zzc.zza.zzVo.get();
      if (str4 != null)
        break label214;
    }
    label185: label199: label214: for (List localList4 = Collections.EMPTY_LIST; ; localList4 = Arrays.asList(str4.split(",")))
    {
      this.zzVg = localList4;
      this.zzVi = new zze(1024, ((Long)zzc.zza.zzVp.get()).longValue());
      return;
      localList1 = Arrays.asList(str1.split(","));
      break;
      localList2 = Arrays.asList(str2.split(","));
      break label85;
      localList3 = Arrays.asList(str3.split(","));
      break label112;
    }
  }

  private int getLogLevel()
  {
    try
    {
      if ((com.google.android.gms.common.internal.zzd.zzSV) && (zzhc.isInitialized()) && (zzhc.zzlj() == Process.myUid()))
        return ((Integer)zzc.zza.zzVk.get()).intValue();
      int i = zzd.zzVq;
      return i;
    }
    catch (SecurityException localSecurityException)
    {
    }
    return zzd.zzVq;
  }

  private void zza(Context paramContext, ServiceConnection paramServiceConnection, String paramString1, Intent paramIntent, String paramString2)
  {
    if (!com.google.android.gms.common.internal.zzd.zzSV);
    long l1;
    do
    {
      return;
      l1 = zzb(paramServiceConnection);
    }
    while (!zza(paramContext, paramString1, paramIntent, l1, paramString2));
    long l2 = System.currentTimeMillis();
    int i = getLogLevel() & zzd.zzVu;
    String str = null;
    if (i != 0)
      str = zzid.zzj(3, 5);
    long l3 = 0L;
    if ((getLogLevel() & zzd.zzVw) != 0)
      l3 = Debug.getNativeHeapAllocatedSize();
    if ((paramString2.equals("UNBIND")) || (paramString2.equals("DISCONNECT")));
    ServiceInfo localServiceInfo;
    for (ConnectionEvent localConnectionEvent = new ConnectionEvent(l2, paramString2, null, null, null, null, str, l1, SystemClock.elapsedRealtime(), l3); ; localConnectionEvent = new ConnectionEvent(l2, paramString2, zzid.zzW(paramContext), paramString1, localServiceInfo.processName, localServiceInfo.name, str, l1, SystemClock.elapsedRealtime(), l3))
    {
      paramContext.startService(new Intent().setComponent(zzVh).putExtra("com.google.android.gms.common.stats.EXTRA_LOG_EVENT", localConnectionEvent));
      return;
      localServiceInfo = zzb(paramContext, paramIntent);
    }
  }

  private boolean zza(Context paramContext, Intent paramIntent)
  {
    return false;
  }

  private boolean zza(Context paramContext, String paramString1, Intent paramIntent, long paramLong, String paramString2)
  {
    int i = getLogLevel();
    if ((i == zzd.zzVq) || (this.zzVi == null));
    String str1;
    String str2;
    String str3;
    do
    {
      do
      {
        return false;
        if ((paramString2 != "DISCONNECT") && (paramString2 != "UNBIND"))
          break;
      }
      while (!this.zzVi.zzA(paramLong));
      return true;
      ServiceInfo localServiceInfo = zzb(paramContext, paramIntent);
      if (localServiceInfo == null)
      {
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = paramString1;
        arrayOfObject[1] = paramIntent.toUri(0);
        Log.w("ConnectionTracker", String.format("Client %s made an invalid request %s", arrayOfObject));
        return false;
      }
      str1 = zzid.zzW(paramContext);
      str2 = localServiceInfo.processName;
      str3 = localServiceInfo.name;
    }
    while ((this.zzVd.contains(str1)) || (this.zzVe.contains(paramString1)) || (this.zzVf.contains(str2)) || (this.zzVg.contains(str3)) || ((str2.equals(str1)) && ((i & zzd.zzVv) != 0)));
    this.zzVi.zza(Long.valueOf(paramLong));
    return true;
  }

  private long zzb(ServiceConnection paramServiceConnection)
  {
    return Process.myPid() << 32 | System.identityHashCode(paramServiceConnection);
  }

  private static ServiceInfo zzb(Context paramContext, Intent paramIntent)
  {
    List localList = paramContext.getPackageManager().queryIntentServices(paramIntent, 128);
    if ((localList == null) || (localList.size() == 0))
    {
      Object[] arrayOfObject1 = new Object[2];
      arrayOfObject1[0] = paramIntent.toUri(0);
      arrayOfObject1[1] = zzid.zzj(3, 20);
      Log.w("ConnectionTracker", String.format("There are no handler of this intent: %s\n Stack trace: %s", arrayOfObject1));
      return null;
    }
    if (localList.size() > 1)
    {
      Object[] arrayOfObject2 = new Object[2];
      arrayOfObject2[0] = paramIntent.toUri(0);
      arrayOfObject2[1] = zzid.zzj(3, 20);
      Log.w("ConnectionTracker", String.format("Multiple handlers found for this intent: %s\n Stack trace: %s", arrayOfObject2));
      Iterator localIterator = localList.iterator();
      if (localIterator.hasNext())
      {
        Log.w("ConnectionTracker", ((ResolveInfo)localIterator.next()).serviceInfo.name);
        return null;
      }
    }
    return ((ResolveInfo)localList.get(0)).serviceInfo;
  }

  public static zzb zznb()
  {
    synchronized (zzTK)
    {
      if (zzVc == null)
        zzVc = new zzb();
      return zzVc;
    }
  }

  public void zza(Context paramContext, ServiceConnection paramServiceConnection)
  {
    zza(paramContext, paramServiceConnection, null, null, "UNBIND");
    paramContext.unbindService(paramServiceConnection);
  }

  public void zza(Context paramContext, ServiceConnection paramServiceConnection, String paramString, Intent paramIntent)
  {
    zza(paramContext, paramServiceConnection, paramString, paramIntent, "CONNECT");
  }

  public boolean zza(Context paramContext, Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    return zza(paramContext, paramContext.getClass().getName(), paramIntent, paramServiceConnection, paramInt);
  }

  public boolean zza(Context paramContext, String paramString, Intent paramIntent, ServiceConnection paramServiceConnection, int paramInt)
  {
    if (zza(paramContext, paramIntent))
    {
      Log.w("ConnectionTracker", "Attempted to bind to a service in a STOPPED package.");
      return false;
    }
    zza(paramContext, paramServiceConnection, paramString, paramIntent, "BIND");
    return paramContext.bindService(paramIntent, paramServiceConnection, paramInt);
  }

  public void zzb(Context paramContext, ServiceConnection paramServiceConnection)
  {
    zza(paramContext, paramServiceConnection, null, null, "DISCONNECT");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.stats.zzb
 * JD-Core Version:    0.6.2
 */