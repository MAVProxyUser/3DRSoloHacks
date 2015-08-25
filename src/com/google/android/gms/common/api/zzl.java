package com.google.android.gms.common.api;

import android.app.PendingIntent;
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
import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzv;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.List;

public class zzl extends Fragment
  implements DialogInterface.OnCancelListener
{
  private boolean mStarted;
  private boolean zzRa;
  private int zzRb = -1;
  private ConnectionResult zzRc;
  private final Handler zzRd = new Handler(Looper.getMainLooper());
  private final SparseArray<zza> zzRe = new SparseArray();

  public static zzl zza(FragmentActivity paramFragmentActivity)
  {
    zzv.zzbI("Must be called from main thread of process");
    FragmentManager localFragmentManager = paramFragmentActivity.getSupportFragmentManager();
    try
    {
      zzl localzzl = (zzl)localFragmentManager.findFragmentByTag("GmsSupportLifecycleFragment");
      if ((localzzl == null) || (localzzl.isRemoving()))
      {
        localzzl = new zzl();
        localFragmentManager.beginTransaction().add(localzzl, "GmsSupportLifecycleFragment").commit();
        localFragmentManager.executePendingTransactions();
      }
      return localzzl;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IllegalStateException("Fragment with tag GmsSupportLifecycleFragment is not a SupportLifecycleFragment", localClassCastException);
    }
  }

  private void zza(int paramInt, ConnectionResult paramConnectionResult)
  {
    Log.w("GmsSupportLifecycleFragment", "Unresolved error while connecting client. Stopping auto-manage.");
    zza localzza = (zza)this.zzRe.get(paramInt);
    if (localzza != null)
    {
      zzax(paramInt);
      GoogleApiClient.OnConnectionFailedListener localOnConnectionFailedListener = localzza.zzRh;
      if (localOnConnectionFailedListener != null)
        localOnConnectionFailedListener.onConnectionFailed(paramConnectionResult);
    }
    zzlg();
  }

  private void zzlg()
  {
    this.zzRa = false;
    this.zzRb = -1;
    this.zzRc = null;
    for (int i = 0; i < this.zzRe.size(); i++)
      ((zza)this.zzRe.valueAt(i)).zzRg.connect();
  }

  public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
  {
    super.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    for (int i = 0; i < this.zzRe.size(); i++)
      ((zza)this.zzRe.valueAt(i)).dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
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
    this.mStarted = true;
    if (!this.zzRa)
      for (int i = 0; i < this.zzRe.size(); i++)
        ((zza)this.zzRe.valueAt(i)).zzRg.connect();
  }

  public void onStop()
  {
    super.onStop();
    this.mStarted = false;
    for (int i = 0; i < this.zzRe.size(); i++)
      ((zza)this.zzRe.valueAt(i)).zzRg.disconnect();
  }

  public void zza(int paramInt, GoogleApiClient paramGoogleApiClient, GoogleApiClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    zzv.zzb(paramGoogleApiClient, "GoogleApiClient instance cannot be null");
    if (this.zzRe.indexOfKey(paramInt) < 0);
    for (boolean bool = true; ; bool = false)
    {
      zzv.zza(bool, "Already managing a GoogleApiClient with id " + paramInt);
      zza localzza = new zza(paramInt, paramGoogleApiClient, paramOnConnectionFailedListener);
      this.zzRe.put(paramInt, localzza);
      if ((this.mStarted) && (!this.zzRa))
        paramGoogleApiClient.connect();
      return;
    }
  }

  public void zzax(int paramInt)
  {
    zza localzza = (zza)this.zzRe.get(paramInt);
    this.zzRe.remove(paramInt);
    if (localzza != null)
      localzza.zzlh();
  }

  private class zza
    implements GoogleApiClient.OnConnectionFailedListener
  {
    public final int zzRf;
    public final GoogleApiClient zzRg;
    public final GoogleApiClient.OnConnectionFailedListener zzRh;

    public zza(int paramGoogleApiClient, GoogleApiClient paramOnConnectionFailedListener, GoogleApiClient.OnConnectionFailedListener arg4)
    {
      this.zzRf = paramGoogleApiClient;
      this.zzRg = paramOnConnectionFailedListener;
      Object localObject;
      this.zzRh = localObject;
      paramOnConnectionFailedListener.registerConnectionFailedListener(this);
    }

    public void dump(String paramString, FileDescriptor paramFileDescriptor, PrintWriter paramPrintWriter, String[] paramArrayOfString)
    {
      paramPrintWriter.append("#").print(this.zzRf);
      paramPrintWriter.append(" ");
      this.zzRg.dump(paramString, paramFileDescriptor, paramPrintWriter, paramArrayOfString);
    }

    public void onConnectionFailed(ConnectionResult paramConnectionResult)
    {
      zzl.zzd(zzl.this).post(new zzl.zzb(zzl.this, this.zzRf, paramConnectionResult));
    }

    public void zzlh()
    {
      this.zzRg.unregisterConnectionFailedListener(this);
      this.zzRg.disconnect();
    }
  }

  private class zzb
    implements Runnable
  {
    private final int zzRj;
    private final ConnectionResult zzRk;

    public zzb(int paramConnectionResult, ConnectionResult arg3)
    {
      this.zzRj = paramConnectionResult;
      Object localObject;
      this.zzRk = localObject;
    }

    public void run()
    {
      if ((!zzl.zza(zzl.this)) || (zzl.zzb(zzl.this)))
        return;
      zzl.zza(zzl.this, true);
      zzl.zza(zzl.this, this.zzRj);
      zzl.zza(zzl.this, this.zzRk);
      if (this.zzRk.hasResolution())
        try
        {
          int i = 1 + (1 + zzl.this.getActivity().getSupportFragmentManager().getFragments().indexOf(zzl.this) << 16);
          this.zzRk.startResolutionForResult(zzl.this.getActivity(), i);
          return;
        }
        catch (IntentSender.SendIntentException localSendIntentException)
        {
          zzl.zzc(zzl.this);
          return;
        }
      if (GooglePlayServicesUtil.isUserRecoverableError(this.zzRk.getErrorCode()))
      {
        GooglePlayServicesUtil.showErrorDialogFragment(this.zzRk.getErrorCode(), zzl.this.getActivity(), zzl.this, 2, zzl.this);
        return;
      }
      zzl.zza(zzl.this, this.zzRj, this.zzRk);
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.api.zzl
 * JD-Core Version:    0.6.2
 */