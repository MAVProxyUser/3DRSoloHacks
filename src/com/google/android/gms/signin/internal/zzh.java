package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.ServerAuthCodeCallbacks.CheckResult;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.AuthAccountRequest;
import com.google.android.gms.common.internal.BinderWrapper;
import com.google.android.gms.common.internal.ResolveAccountRequest;
import com.google.android.gms.common.internal.ResolveAccountResponse;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzo;
import com.google.android.gms.common.internal.zzr;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class zzh extends zzi<zzf>
  implements zzmd
{
  private final com.google.android.gms.common.internal.zze zzQg;
  private final zzme zzSY;
  private Integer zzSZ;
  private final ExecutorService zzaBN;

  public zzh(Context paramContext, Looper paramLooper, com.google.android.gms.common.internal.zze paramzze, zzme paramzzme, GoogleApiClient.ConnectionCallbacks paramConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener, ExecutorService paramExecutorService)
  {
    super(paramContext, paramLooper, 44, paramConnectionCallbacks, paramOnConnectionFailedListener, paramzze);
    this.zzQg = paramzze;
    this.zzSY = paramzzme;
    this.zzSZ = paramzze.zzlN();
    this.zzaBN = paramExecutorService;
  }

  public static Bundle zza(zzme paramzzme, Integer paramInteger, ExecutorService paramExecutorService)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("com.google.android.gms.signin.internal.offlineAccessRequested", paramzzme.zzwf());
    localBundle.putBoolean("com.google.android.gms.signin.internal.idTokenRequested", paramzzme.zzwg());
    localBundle.putString("com.google.android.gms.signin.internal.serverClientId", paramzzme.zzvx());
    if (paramzzme.zzwh() != null)
      localBundle.putParcelable("com.google.android.gms.signin.internal.signInCallbacks", new BinderWrapper(new zza(paramzzme, paramExecutorService).asBinder()));
    if (paramInteger != null)
      localBundle.putInt("com.google.android.gms.common.internal.ClientSettings.sessionId", paramInteger.intValue());
    return localBundle;
  }

  public void zza(zzo paramzzo, Set<Scope> paramSet, zze paramzze)
  {
    zzv.zzb(paramzze, "Expecting a valid ISignInCallbacks");
    try
    {
      ((zzf)zzlX()).zza(new AuthAccountRequest(paramzzo, paramSet), paramzze);
      return;
    }
    catch (RemoteException localRemoteException1)
    {
      Log.w("SignInClientImpl", "Remote service probably died when authAccount is called");
      try
      {
        paramzze.zza(new ConnectionResult(8, null), new AuthAccountResult());
        return;
      }
      catch (RemoteException localRemoteException2)
      {
        Log.wtf("SignInClientImpl", "ISignInCallbacks#onAuthAccount should be executed from the same process, unexpected RemoteException.");
      }
    }
  }

  public void zza(zzo paramzzo, boolean paramBoolean)
  {
    try
    {
      ((zzf)zzlX()).zza(paramzzo, this.zzSZ.intValue(), paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("SignInClientImpl", "Remote service probably died when saveDefaultAccount is called");
    }
  }

  public void zza(zzr paramzzr)
  {
    zzv.zzb(paramzzr, "Expecting a valid IResolveAccountCallbacks");
    try
    {
      Account localAccount = this.zzQg.zzlE();
      ((zzf)zzlX()).zza(new ResolveAccountRequest(localAccount, this.zzSZ.intValue()), paramzzr);
      return;
    }
    catch (RemoteException localRemoteException1)
    {
      Log.w("SignInClientImpl", "Remote service probably died when resolveAccount is called");
      try
      {
        paramzzr.zzb(new ResolveAccountResponse(8));
        return;
      }
      catch (RemoteException localRemoteException2)
      {
        Log.wtf("SignInClientImpl", "IResolveAccountCallbacks#onAccountResolutionComplete should be executed from the same process, unexpected RemoteException.");
      }
    }
  }

  protected zzf zzcI(IBinder paramIBinder)
  {
    return zzf.zza.zzcH(paramIBinder);
  }

  protected String zzeq()
  {
    return "com.google.android.gms.signin.service.START";
  }

  protected String zzer()
  {
    return "com.google.android.gms.signin.internal.ISignInService";
  }

  protected Bundle zzka()
  {
    Bundle localBundle = zza(this.zzSY, this.zzQg.zzlN(), this.zzaBN);
    String str = this.zzQg.zzlJ();
    if (!getContext().getPackageName().equals(str))
      localBundle.putString("com.google.android.gms.signin.internal.realClientPackageName", this.zzQg.zzlJ());
    return localBundle;
  }

  public void zzwe()
  {
    try
    {
      ((zzf)zzlX()).zzhB(this.zzSZ.intValue());
      return;
    }
    catch (RemoteException localRemoteException)
    {
      Log.w("SignInClientImpl", "Remote service probably died when clearAccountFromSessionStore is called");
    }
  }

  private static class zza extends zzd.zza
  {
    private final zzme zzSY;
    private final ExecutorService zzaBN;

    public zza(zzme paramzzme, ExecutorService paramExecutorService)
    {
      this.zzSY = paramzzme;
      this.zzaBN = paramExecutorService;
    }

    private GoogleApiClient.ServerAuthCodeCallbacks zzwh()
      throws RemoteException
    {
      return this.zzSY.zzwh();
    }

    public void zza(final String paramString1, final String paramString2, final zzf paramzzf)
      throws RemoteException
    {
      this.zzaBN.submit(new Runnable()
      {
        public void run()
        {
          try
          {
            boolean bool = zzh.zza.zza(zzh.zza.this).onUploadServerAuthCode(paramString1, paramString2);
            paramzzf.zzag(bool);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            Log.e("SignInClientImpl", "RemoteException thrown when processing uploadServerAuthCode callback", localRemoteException);
          }
        }
      });
    }

    public void zza(final String paramString, final List<Scope> paramList, final zzf paramzzf)
      throws RemoteException
    {
      this.zzaBN.submit(new Runnable()
      {
        public void run()
        {
          try
          {
            GoogleApiClient.ServerAuthCodeCallbacks localServerAuthCodeCallbacks = zzh.zza.zza(zzh.zza.this);
            Set localSet = Collections.unmodifiableSet(new HashSet(paramList));
            GoogleApiClient.ServerAuthCodeCallbacks.CheckResult localCheckResult = localServerAuthCodeCallbacks.onCheckServerAuthorization(paramString, localSet);
            CheckServerAuthResult localCheckServerAuthResult = new CheckServerAuthResult(localCheckResult.zzkN(), localCheckResult.zzkO());
            paramzzf.zza(localCheckServerAuthResult);
            return;
          }
          catch (RemoteException localRemoteException)
          {
            Log.e("SignInClientImpl", "RemoteException thrown when processing checkServerAuthorization callback", localRemoteException);
          }
        }
      });
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.signin.internal.zzh
 * JD-Core Version:    0.6.2
 */