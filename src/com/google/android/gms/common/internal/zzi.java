package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.Api.zza;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.zza;
import com.google.android.gms.common.api.Scope;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public abstract class zzi<T extends IInterface>
  implements Api.zza, zzj.zza
{
  public static final String[] zzTx = { "service_esmobile", "service_googleme" };
  private final Context mContext;
  final Handler mHandler;
  private final Account zzJc;
  private final Set<Scope> zzPP;
  private final Looper zzPx;
  private final zze zzQg;
  private final zzj zzQs;
  private final zzk zzTo;
  private zzq zzTp;
  private boolean zzTq = false;
  private GoogleApiClient.zza zzTr;
  private T zzTs;
  private final ArrayList<zzi<T>.zzc<?>> zzTt = new ArrayList();
  private zzi<T>.zze zzTu;
  private int zzTv = 1;
  private final int zzTw;
  private final Object zzoe = new Object();

  @Deprecated
  protected zzi(Context paramContext, Looper paramLooper, int paramInt, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this.mContext = ((Context)zzv.zzr(paramContext));
    this.zzPx = ((Looper)zzv.zzb(paramLooper, "Looper must not be null"));
    this.zzTo = zzk.zzU(paramContext);
    this.zzQs = new zzj(paramLooper, this);
    this.mHandler = new zzb(paramLooper);
    this.zzTw = paramInt;
    this.zzJc = null;
    this.zzPP = Collections.emptySet();
    this.zzQg = new GoogleApiClient.Builder(paramContext).zzkK();
    registerConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)zzv.zzr(paramConnectionCallbacks));
    registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener)zzv.zzr(paramOnConnectionFailedListener));
  }

  protected zzi(Context paramContext, Looper paramLooper, int paramInt, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, zze paramzze)
  {
    this(paramContext, paramLooper, zzk.zzU(paramContext), paramInt, paramzze, paramConnectionCallbacks, paramOnConnectionFailedListener);
  }

  protected zzi(Context paramContext, Looper paramLooper, zzk paramzzk, int paramInt, zze paramzze)
  {
    this.mContext = ((Context)zzv.zzb(paramContext, "Context must not be null"));
    this.zzPx = ((Looper)zzv.zzb(paramLooper, "Looper must not be null"));
    this.zzTo = ((zzk)zzv.zzb(paramzzk, "Supervisor must not be null"));
    this.zzQs = new zzj(paramLooper, this);
    this.mHandler = new zzb(paramLooper);
    this.zzTw = paramInt;
    this.zzQg = ((zze)zzv.zzr(paramzze));
    this.zzJc = paramzze.getAccount();
    this.zzPP = zzb(paramzze.zzlH());
  }

  protected zzi(Context paramContext, Looper paramLooper, zzk paramzzk, int paramInt, zze paramzze, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this(paramContext, paramLooper, paramzzk, paramInt, paramzze);
    registerConnectionCallbacks((GoogleApiClient.ConnectionCallbacks)zzv.zzr(paramConnectionCallbacks));
    registerConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener)zzv.zzr(paramOnConnectionFailedListener));
  }

  private void zza(int paramInt, T paramT)
  {
    boolean bool1 = true;
    boolean bool2;
    boolean bool3;
    if (paramInt == 3)
    {
      bool2 = bool1;
      if (paramT == null)
        break label104;
      bool3 = bool1;
      label17: if (bool2 != bool3)
        break label110;
    }
    while (true)
    {
      zzv.zzQ(bool1);
      while (true)
      {
        synchronized (this.zzoe)
        {
          this.zzTv = paramInt;
          this.zzTs = paramT;
          switch (paramInt)
          {
          default:
            return;
          case 2:
            zzlS();
          case 1:
          }
        }
        zzlT();
      }
      bool2 = false;
      break;
      label104: bool3 = false;
      break label17;
      label110: bool1 = false;
    }
  }

  private boolean zza(int paramInt1, int paramInt2, T paramT)
  {
    synchronized (this.zzoe)
    {
      if (this.zzTv != paramInt1)
        return false;
      zza(paramInt2, paramT);
      return true;
    }
  }

  private Set<Scope> zzb(Set<Scope> paramSet)
  {
    Set localSet = zza(paramSet);
    if (localSet == null)
      return localSet;
    Iterator localIterator = localSet.iterator();
    while (localIterator.hasNext())
      if (!paramSet.contains((Scope)localIterator.next()))
        throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
    return localSet;
  }

  private void zzlS()
  {
    if (this.zzTu != null)
    {
      Log.e("GmsClient", "Calling connect() while still connected, missing disconnect() for " + zzeq());
      this.zzTo.zzb(zzeq(), this.zzTu, zzlR());
    }
    this.zzTu = new zze();
    if (!this.zzTo.zza(zzeq(), this.zzTu, zzlR()))
    {
      Log.e("GmsClient", "unable to connect to service: " + zzeq());
      this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(9)));
    }
  }

  private void zzlT()
  {
    if (this.zzTu != null)
    {
      this.zzTo.zzb(zzeq(), this.zzTu, zzlR());
      this.zzTu = null;
    }
  }

  public void connect()
  {
    this.zzQs.zzmg();
    int i = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this.mContext);
    if (i != 0)
    {
      zza(1, null);
      this.mHandler.sendMessage(this.mHandler.obtainMessage(3, Integer.valueOf(i)));
      return;
    }
    zza(2, null);
  }

  public void disconnect()
  {
    this.zzQs.zzmf();
    synchronized (this.zzTt)
    {
      int i = this.zzTt.size();
      for (int j = 0; j < i; j++)
        ((zzc)this.zzTt.get(j)).zzmd();
      this.zzTt.clear();
      zza(1, null);
      return;
    }
  }

  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    paramPrintWriter.append(paramString).println("GmsClient:");
    String str = paramString + "  ";
    paramPrintWriter.append(str).append("mStartServiceAction=").println(zzeq());
    IInterface localIInterface;
    while (true)
    {
      synchronized (this.zzoe)
      {
        int i = this.zzTv;
        localIInterface = this.zzTs;
        paramPrintWriter.append(str).append("mConnectState=");
        switch (i)
        {
        default:
          paramPrintWriter.print("UNKNOWN");
          paramPrintWriter.append(" mService=");
          if (localIInterface != null)
            break label196;
          paramPrintWriter.println("null");
          return;
        case 2:
        case 3:
        case 4:
        case 1:
        }
      }
      paramPrintWriter.print("CONNECTING");
      continue;
      paramPrintWriter.print("CONNECTED");
      continue;
      paramPrintWriter.print("DISCONNECTING");
      continue;
      paramPrintWriter.print("DISCONNECTED");
    }
    label196: paramPrintWriter.append(zzer()).append("@").println(Integer.toHexString(System.identityHashCode(localIInterface.asBinder())));
  }

  public final Context getContext()
  {
    return this.mContext;
  }

  public final Looper getLooper()
  {
    return this.zzPx;
  }

  public boolean isConnected()
  {
    while (true)
    {
      synchronized (this.zzoe)
      {
        if (this.zzTv == 3)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }

  public boolean isConnecting()
  {
    while (true)
    {
      synchronized (this.zzoe)
      {
        if (this.zzTv == 2)
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }

  public void registerConnectionCallbacks(GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    this.zzQs.registerConnectionCallbacks(paramConnectionCallbacks);
  }

  public void registerConnectionFailedListener(GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    this.zzQs.registerConnectionFailedListener(paramOnConnectionFailedListener);
  }

  protected abstract T zzD(IBinder paramIBinder);

  protected Set<Scope> zza(Set<Scope> paramSet)
  {
    return paramSet;
  }

  protected void zza(int paramInt, IBinder paramIBinder, Bundle paramBundle)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(1, new zzf(paramInt, paramIBinder, paramBundle)));
  }

  public void zza(GoogleApiClient.zza paramzza)
  {
    this.zzTr = ((GoogleApiClient.zza)zzv.zzb(paramzza, "Must provide a non-null ConnectionStatusReportCallbacks"));
    this.zzTq = true;
  }

  @Deprecated
  public final void zza(zzi<T>.zzc<?> paramzzi)
  {
    synchronized (this.zzTt)
    {
      this.zzTt.add(paramzzi);
      this.mHandler.sendMessage(this.mHandler.obtainMessage(2, paramzzi));
      return;
    }
  }

  public void zza(zzo paramzzo)
  {
    Bundle localBundle = zzlY();
    ValidateAccountRequest localValidateAccountRequest = new ValidateAccountRequest(paramzzo, (Scope[])this.zzPP.toArray(new Scope[this.zzPP.size()]), this.mContext.getPackageName(), localBundle);
    try
    {
      this.zzTp.zza(new zzd(this), localValidateAccountRequest);
      return;
    }
    catch (DeadObjectException localDeadObjectException)
    {
      Log.w("GmsClient", "service died");
      zzaO(1);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("GmsClient", "Remote exception occurred", localRemoteException);
    }
  }

  public void zza(zzo paramzzo, Set<Scope> paramSet)
  {
    try
    {
      Bundle localBundle = zzka();
      GetServiceRequest localGetServiceRequest = new GetServiceRequest(this.zzTw).zzbL(this.mContext.getPackageName()).zzf(localBundle);
      if (paramSet != null)
        localGetServiceRequest.zza((Scope[])paramSet.toArray(new Scope[paramSet.size()]));
      if (zzjM())
        localGetServiceRequest.zzb(zzlE()).zzc(paramzzo);
      while (true)
      {
        this.zzTp.zza(new zzd(this), localGetServiceRequest);
        return;
        if (zzlZ())
          localGetServiceRequest.zzb(this.zzJc);
      }
    }
    catch (DeadObjectException localDeadObjectException)
    {
      Log.w("GmsClient", "service died");
      zzaO(1);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("GmsClient", "Remote exception occurred", localRemoteException);
    }
  }

  public void zzaO(int paramInt)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(4, Integer.valueOf(paramInt)));
  }

  protected void zzb(int paramInt, Bundle paramBundle)
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(5, new zzh(paramInt, paramBundle)));
  }

  protected abstract String zzeq();

  protected abstract String zzer();

  public boolean zzjM()
  {
    return false;
  }

  public Bundle zzjZ()
  {
    return null;
  }

  protected Bundle zzka()
  {
    return new Bundle();
  }

  public final Account zzlE()
  {
    if (this.zzJc != null)
      return this.zzJc;
    return new Account("<<default account>>", "com.google");
  }

  protected String zzlR()
  {
    return this.zzQg.zzlK();
  }

  protected final zze zzlU()
  {
    return this.zzQg;
  }

  protected void zzlV()
  {
    this.mHandler.sendMessage(this.mHandler.obtainMessage(6, new zzg()));
  }

  protected final void zzlW()
  {
    if (!isConnected())
      throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
  }

  public final T zzlX()
    throws DeadObjectException
  {
    synchronized (this.zzoe)
    {
      if (this.zzTv == 4)
        throw new DeadObjectException();
    }
    zzlW();
    if (this.zzTs != null);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "Client is connected but service is null");
      IInterface localIInterface = this.zzTs;
      return localIInterface;
    }
  }

  protected Bundle zzlY()
  {
    return null;
  }

  public boolean zzlZ()
  {
    return false;
  }

  private abstract class zza extends zzi<T>.zzc<Boolean>
  {
    public final int statusCode;
    public final Bundle zzTy;

    protected zza(int paramBundle, Bundle arg3)
    {
      super(Boolean.valueOf(true));
      this.statusCode = paramBundle;
      Object localObject;
      this.zzTy = localObject;
    }

    protected void zzc(Boolean paramBoolean)
    {
      if (paramBoolean == null)
        zzi.zza(zzi.this, 1, null);
      do
      {
        return;
        switch (this.statusCode)
        {
        default:
          zzi.zza(zzi.this, 1, null);
          Bundle localBundle = this.zzTy;
          PendingIntent localPendingIntent = null;
          if (localBundle != null)
            localPendingIntent = (PendingIntent)this.zzTy.getParcelable("pendingIntent");
          zzi(new ConnectionResult(this.statusCode, localPendingIntent));
          return;
        case 0:
        case 10:
        }
      }
      while (zzma());
      zzi.zza(zzi.this, 1, null);
      zzi(new ConnectionResult(8, null));
      return;
      zzi.zza(zzi.this, 1, null);
      throw new IllegalStateException("A fatal developer error has occurred. Check the logs for further information.");
    }

    protected abstract void zzi(ConnectionResult paramConnectionResult);

    protected abstract boolean zzma();

    protected void zzmb()
    {
    }
  }

  final class zzb extends Handler
  {
    public zzb(Looper arg2)
    {
      super();
    }

    public void handleMessage(Message paramMessage)
    {
      if (((paramMessage.what == 1) || (paramMessage.what == 5) || (paramMessage.what == 6)) && (!zzi.this.isConnecting()))
      {
        zzi.zzc localzzc2 = (zzi.zzc)paramMessage.obj;
        localzzc2.zzmb();
        localzzc2.unregister();
        return;
      }
      if (paramMessage.what == 3)
      {
        ConnectionResult localConnectionResult = new ConnectionResult(((Integer)paramMessage.obj).intValue(), null);
        if (zzi.zza(zzi.this))
        {
          zzi.zzb(zzi.this).zza(localConnectionResult);
          return;
        }
        zzi.zzc(zzi.this).zzj(localConnectionResult);
        return;
      }
      if (paramMessage.what == 4)
      {
        zzi.zza(zzi.this, 4, null);
        zzi.zzc(zzi.this).zzaP(((Integer)paramMessage.obj).intValue());
        zzi.zza(zzi.this, 4, 1, null);
        return;
      }
      if ((paramMessage.what == 2) && (!zzi.this.isConnected()))
      {
        zzi.zzc localzzc1 = (zzi.zzc)paramMessage.obj;
        localzzc1.zzmb();
        localzzc1.unregister();
        return;
      }
      if ((paramMessage.what == 2) || (paramMessage.what == 1) || (paramMessage.what == 5) || (paramMessage.what == 6))
      {
        ((zzi.zzc)paramMessage.obj).zzmc();
        return;
      }
      Log.wtf("GmsClient", "Don't know how to handle this message.");
    }
  }

  protected abstract class zzc<TListener>
  {
    private TListener mListener;
    private boolean zzTA;

    public zzc()
    {
      Object localObject;
      this.mListener = localObject;
      this.zzTA = false;
    }

    public void unregister()
    {
      zzmd();
      synchronized (zzi.zzd(zzi.this))
      {
        zzi.zzd(zzi.this).remove(this);
        return;
      }
    }

    protected abstract void zzmb();

    // ERROR //
    public void zzmc()
    {
      // Byte code:
      //   0: aload_0
      //   1: monitorenter
      //   2: aload_0
      //   3: getfield 21	com/google/android/gms/common/internal/zzi$zzc:mListener	Ljava/lang/Object;
      //   6: astore_2
      //   7: aload_0
      //   8: getfield 23	com/google/android/gms/common/internal/zzi$zzc:zzTA	Z
      //   11: ifeq +33 -> 44
      //   14: ldc 45
      //   16: new 47	java/lang/StringBuilder
      //   19: dup
      //   20: invokespecial 48	java/lang/StringBuilder:<init>	()V
      //   23: ldc 50
      //   25: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   28: aload_0
      //   29: invokevirtual 57	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   32: ldc 59
      //   34: invokevirtual 54	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   37: invokevirtual 63	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   40: invokestatic 69	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
      //   43: pop
      //   44: aload_0
      //   45: monitorexit
      //   46: aload_2
      //   47: ifnull +36 -> 83
      //   50: aload_0
      //   51: aload_2
      //   52: invokevirtual 73	com/google/android/gms/common/internal/zzi$zzc:zzo	(Ljava/lang/Object;)V
      //   55: aload_0
      //   56: monitorenter
      //   57: aload_0
      //   58: iconst_1
      //   59: putfield 23	com/google/android/gms/common/internal/zzi$zzc:zzTA	Z
      //   62: aload_0
      //   63: monitorexit
      //   64: aload_0
      //   65: invokevirtual 75	com/google/android/gms/common/internal/zzi$zzc:unregister	()V
      //   68: return
      //   69: astore_1
      //   70: aload_0
      //   71: monitorexit
      //   72: aload_1
      //   73: athrow
      //   74: astore 4
      //   76: aload_0
      //   77: invokevirtual 77	com/google/android/gms/common/internal/zzi$zzc:zzmb	()V
      //   80: aload 4
      //   82: athrow
      //   83: aload_0
      //   84: invokevirtual 77	com/google/android/gms/common/internal/zzi$zzc:zzmb	()V
      //   87: goto -32 -> 55
      //   90: astore_3
      //   91: aload_0
      //   92: monitorexit
      //   93: aload_3
      //   94: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   2	44	69	finally
      //   44	46	69	finally
      //   70	72	69	finally
      //   50	55	74	java/lang/RuntimeException
      //   57	64	90	finally
      //   91	93	90	finally
    }

    public void zzmd()
    {
      try
      {
        this.mListener = null;
        return;
      }
      finally
      {
      }
    }

    protected abstract void zzo(TListener paramTListener);
  }

  public static final class zzd extends zzp.zza
  {
    private zzi zzTB;

    public zzd(zzi paramzzi)
    {
      this.zzTB = paramzzi;
    }

    private void zzme()
    {
      this.zzTB = null;
    }

    public void zzb(int paramInt, IBinder paramIBinder, Bundle paramBundle)
    {
      zzv.zzb(this.zzTB, "onPostInitComplete can be called only once per call to getRemoteService");
      this.zzTB.zza(paramInt, paramIBinder, paramBundle);
      zzme();
    }

    public void zzc(int paramInt, Bundle paramBundle)
    {
      zzv.zzb(this.zzTB, "onAccountValidationComplete can be called only once per call to validateAccount");
      this.zzTB.zzb(paramInt, paramBundle);
      zzme();
    }
  }

  public final class zze
    implements ServiceConnection
  {
    public zze()
    {
    }

    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      zzv.zzb(paramIBinder, "Expecting a valid IBinder");
      zzi.zza(zzi.this, zzq.zza.zzT(paramIBinder));
      zzi.this.zzlV();
    }

    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      zzi.this.mHandler.sendMessage(zzi.this.mHandler.obtainMessage(4, Integer.valueOf(1)));
    }
  }

  protected final class zzf extends zzi<T>.zza
  {
    public final IBinder zzTC;

    public zzf(int paramIBinder, IBinder paramBundle, Bundle arg4)
    {
      super(paramIBinder, localBundle);
      this.zzTC = paramBundle;
    }

    protected void zzi(ConnectionResult paramConnectionResult)
    {
      zzi.zzc(zzi.this).zzj(paramConnectionResult);
    }

    protected boolean zzma()
    {
      IInterface localIInterface;
      do
      {
        try
        {
          String str = this.zzTC.getInterfaceDescriptor();
          if (!zzi.this.zzer().equals(str))
          {
            Log.e("GmsClient", "service descriptor mismatch: " + zzi.this.zzer() + " vs. " + str);
            return false;
          }
        }
        catch (RemoteException localRemoteException)
        {
          Log.w("GmsClient", "service probably died");
          return false;
        }
        localIInterface = zzi.this.zzD(this.zzTC);
      }
      while ((localIInterface == null) || (!zzi.zza(zzi.this, 2, 3, localIInterface)));
      zzi.zzc(zzi.this).zzmh();
      GooglePlayServicesUtil.zzQ(zzi.zzf(zzi.this));
      return true;
    }
  }

  protected final class zzg extends zzi<T>.zza
  {
    public zzg()
    {
      super(0, null);
    }

    protected void zzi(ConnectionResult paramConnectionResult)
    {
      if (zzi.zza(zzi.this))
      {
        zzi.zzb(zzi.this).zza(paramConnectionResult);
        return;
      }
      zzi.zzc(zzi.this).zzj(paramConnectionResult);
    }

    protected boolean zzma()
    {
      if (zzi.zza(zzi.this))
      {
        if (zzi.zzb(zzi.this) != null);
        for (boolean bool = true; ; bool = false)
        {
          zzv.zza(bool, "mConnectionProgressReportCallbacks should not be null if mReportProgress");
          zzi.zzb(zzi.this).zza(ConnectionResult.zzOI);
          return true;
        }
      }
      zzi.this.zza(null, zzi.zze(zzi.this));
      return true;
    }
  }

  protected final class zzh extends zzi<T>.zza
  {
    public zzh(int paramBundle, Bundle arg3)
    {
      super(paramBundle, localBundle);
    }

    protected void zzi(ConnectionResult paramConnectionResult)
    {
      if (zzi.zza(zzi.this))
      {
        zzi.zzb(zzi.this).zzb(paramConnectionResult);
        return;
      }
      zzi.zzc(zzi.this).zzj(paramConnectionResult);
    }

    protected boolean zzma()
    {
      if ((zzi.zza(zzi.this)) && (zzi.zzb(zzi.this) != null));
      for (boolean bool = true; ; bool = false)
      {
        zzv.zza(bool, "PostValidationCallback should not happen when mReportProgress is false ormConnectionProgressReportCallbacks is null");
        zzi.zzb(zzi.this).zzb(ConnectionResult.zzOI);
        return true;
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.internal.zzi
 * JD-Core Version:    0.6.2
 */