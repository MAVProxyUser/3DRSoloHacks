package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzv;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class zzm extends Fragment
  implements DialogInterface.OnCancelListener, LoaderManager.LoaderCallbacks<ConnectionResult>
{
  private boolean zzRa;
  private int zzRb = -1;
  private ConnectionResult zzRc;
  private final Handler zzRd = new Handler(Looper.getMainLooper());
  private final SparseArray<zzb> zzRe = new SparseArray();

  private void zza(int paramInt, ConnectionResult paramConnectionResult)
  {
    Log.w("GmsSupportLoaderLifecycleFragment", "Unresolved error while connecting client. Stopping auto-manage.");
    zzb localzzb = (zzb)this.zzRe.get(paramInt);
    if (localzzb != null)
    {
      zzax(paramInt);
      GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = localzzb.zzRh;
      if (localOnConnectionFailedListener != null)
        localOnConnectionFailedListener.onConnectionFailed(paramConnectionResult);
    }
    zzlg();
  }

  public static zzm zzb(FragmentActivity paramFragmentActivity)
  {
    zzv.zzbI("Must be called from main thread of process");
    FragmentManager localFragmentManager = paramFragmentActivity.getSupportFragmentManager();
    try
    {
      zzm localzzm = (zzm)localFragmentManager.findFragmentByTag("GmsSupportLoaderLifecycleFragment");
      if ((localzzm == null) || (localzzm.isRemoving()))
      {
        localzzm = new zzm();
        localFragmentManager.beginTransaction().add(localzzm, "GmsSupportLoaderLifecycleFragment").commit();
        localFragmentManager.executePendingTransactions();
      }
      return localzzm;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IllegalStateException("Fragment with tag GmsSupportLoaderLifecycleFragment is not a SupportLoaderLifecycleFragment", localClassCastException);
    }
  }

  private void zzb(int paramInt, ConnectionResult paramConnectionResult)
  {
    if (!this.zzRa)
    {
      this.zzRa = true;
      this.zzRb = paramInt;
      this.zzRc = paramConnectionResult;
      this.zzRd.post(new zzc(paramInt, paramConnectionResult));
    }
  }

  private void zzlg()
  {
    int i = 0;
    this.zzRa = false;
    this.zzRb = -1;
    this.zzRc = null;
    LoaderManager localLoaderManager = getLoaderManager();
    while (i < this.zzRe.size())
    {
      int j = this.zzRe.keyAt(i);
      zza localzza = zzaz(j);
      if ((localzza != null) && (localzza.zzli()))
      {
        localLoaderManager.destroyLoader(j);
        localLoaderManager.initLoader(j, null, this);
      }
      i++;
    }
  }

  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    int i = 1;
    switch (paramInt1)
    {
    default:
      i = 0;
    case 2:
    case 1:
    }
    while (true)
    {
      if (i == 0)
        break label62;
      zzlg();
      return;
      if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) != 0)
        break;
      continue;
      if (paramInt2 != -1)
        break;
    }
    label62: zza(this.zzRb, this.zzRc);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    int i = 0;
    if (i < this.zzRe.size())
    {
      int j = this.zzRe.keyAt(i);
      zza localzza = zzaz(j);
      if ((localzza != null) && (((zzb)this.zzRe.valueAt(i)).zzRg != localzza.zzRg))
        getLoaderManager().restartLoader(j, null, this);
      while (true)
      {
        i++;
        break;
        getLoaderManager().initLoader(j, null, this);
      }
    }
  }

  public void onCancel(DialogInterface paramDialogInterface)
  {
    zza(this.zzRb, new ConnectionResult(13, null));
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.zzRa = paramBundle.getBoolean("resolving_error", false);
      this.zzRb = paramBundle.getInt("failed_client_id", -1);
      if (this.zzRb >= 0)
        this.zzRc = new ConnectionResult(paramBundle.getInt("failed_status"), (PendingIntent)paramBundle.getParcelable("failed_resolution"));
    }
  }

  public Loader<ConnectionResult> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new zza(getActivity(), ((zzb)this.zzRe.get(paramInt)).zzRg);
  }

  public void onLoaderReset(Loader<ConnectionResult> paramLoader)
  {
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putBoolean("resolving_error", this.zzRa);
    if (this.zzRb >= 0)
    {
      paramBundle.putInt("failed_client_id", this.zzRb);
      paramBundle.putInt("failed_status", this.zzRc.getErrorCode());
      paramBundle.putParcelable("failed_resolution", this.zzRc.getResolution());
    }
  }

  public void onStart()
  {
    super.onStart();
    if (!this.zzRa)
      for (int i = 0; i < this.zzRe.size(); i++)
        getLoaderManager().initLoader(this.zzRe.keyAt(i), null, this);
  }

  public void zza(int paramInt, GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzv.zzb(paramGoogleApiClient, "GoogleApiClient instance cannot be null");
    if (this.zzRe.indexOfKey(paramInt) < 0);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "Already managing a GoogleApiClient with id " + paramInt);
      zzb localzzb = new zzb(paramGoogleApiClient, paramOnConnectionFailedListener, null);
      this.zzRe.put(paramInt, localzzb);
      if (getActivity() != null)
      {
        LoaderManager.enableDebugLogging(false);
        getLoaderManager().initLoader(paramInt, null, this);
      }
      return;
    }
  }

  public void zza(Loader<ConnectionResult> paramLoader, ConnectionResult paramConnectionResult)
  {
    if (!paramConnectionResult.isSuccess())
      zzb(paramLoader.getId(), paramConnectionResult);
  }

  public void zzax(int paramInt)
  {
    this.zzRe.remove(paramInt);
    getLoaderManager().destroyLoader(paramInt);
  }

  public GoogleApiClient zzay(int paramInt)
  {
    if (getActivity() != null)
    {
      zza localzza = zzaz(paramInt);
      if (localzza != null)
        return localzza.zzRg;
    }
    return null;
  }

  zza zzaz(int paramInt)
  {
    try
    {
      zza localzza = (zza)getLoaderManager().getLoader(paramInt);
      return localzza;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IllegalStateException("Unknown loader in SupportLoaderLifecycleFragment", localClassCastException);
    }
  }

  static class zza extends Loader<ConnectionResult>
    implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
  {
    public final GoogleApiClient zzRg;
    private boolean zzRl;
    private ConnectionResult zzRm;

    public zza(Context paramContext, GoogleApiClient paramGoogleApiClient)
    {
      super();
      this.zzRg = paramGoogleApiClient;
    }

    private void zzh(ConnectionResult paramConnectionResult)
    {
      this.zzRm = paramConnectionResult;
      if ((isStarted()) && (!isAbandoned()))
        deliverResult(paramConnectionResult);
    }

    public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
      this.zzRg.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }

    public void onConnected(Bundle paramBundle)
    {
      this.zzRl = false;
      zzh(ConnectionResult.zzOI);
    }

    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      this.zzRl = true;
      zzh(paramConnectionResult);
    }

    public void onConnectionSuspended(int paramInt)
    {
    }

    protected void onReset()
    {
      this.zzRm = null;
      this.zzRl = false;
      this.zzRg.unregisterConnectionCallbacks(this);
      this.zzRg.unregisterConnectionFailedListener(this);
      this.zzRg.disconnect();
    }

    protected void onStartLoading()
    {
      super.onStartLoading();
      this.zzRg.registerConnectionCallbacks(this);
      this.zzRg.registerConnectionFailedListener(this);
      if (this.zzRm != null)
        deliverResult(this.zzRm);
      if ((!this.zzRg.isConnected()) && (!this.zzRg.isConnecting()) && (!this.zzRl))
        this.zzRg.connect();
    }

    protected void onStopLoading()
    {
      this.zzRg.disconnect();
    }

    public boolean zzli()
    {
      return this.zzRl;
    }
  }

  private static class zzb
  {
    public final GoogleApiClient zzRg;
    public final GoogleApiClient.OnConnectionFailedListener zzRh;

    private zzb(GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
    {
      this.zzRg = paramGoogleApiClient;
      this.zzRh = paramOnConnectionFailedListener;
    }
  }

  private class zzc
    implements Runnable
  {
    private final int zzRj;
    private final ConnectionResult zzRk;

    public zzc(int paramConnectionResult, ConnectionResult arg3)
    {
      this.zzRj = paramConnectionResult;
      Object localObject;
      this.zzRk = localObject;
    }

    public void run()
    {
      if (this.zzRk.hasResolution())
        try
        {
          int i = 1 + (1 + zzm.this.getActivity().getSupportFragmentManager().getFragments().indexOf(zzm.this) << 16);
          this.zzRk.startResolutionForResult(zzm.this.getActivity(), i);
          return;
        }
        catch (IntentSender.SendIntentException localSendIntentException)
        {
          zzm.zza(zzm.this);
          return;
        }
      if (GooglePlayServicesUtil.isUserRecoverableError(this.zzRk.getErrorCode()))
      {
        GooglePlayServicesUtil.showErrorDialogFragment(this.zzRk.getErrorCode(), zzm.this.getActivity(), zzm.this, 2, zzm.this);
        return;
      }
      zzm.zza(zzm.this, this.zzRj, this.zzRk);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzm
 * JD-Core Version:    0.6.2
 */