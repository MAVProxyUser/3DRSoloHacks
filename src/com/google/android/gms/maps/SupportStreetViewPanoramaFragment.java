package com.google.android.gms.maps;

import android.app.Activity;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzv.zza;
import com.google.android.gms.maps.internal.zzx;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SupportStreetViewPanoramaFragment extends Fragment
{
  private final zzb zzauM = new zzb(this);
  private StreetViewPanorama zzaut;

  public static SupportStreetViewPanoramaFragment newInstance()
  {
    return new SupportStreetViewPanoramaFragment();
  }

  public static SupportStreetViewPanoramaFragment newInstance(StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
  {
    SupportStreetViewPanoramaFragment localSupportStreetViewPanoramaFragment = new SupportStreetViewPanoramaFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("StreetViewPanoramaOptions", paramStreetViewPanoramaOptions);
    localSupportStreetViewPanoramaFragment.setArguments(localBundle);
    return localSupportStreetViewPanoramaFragment;
  }

  @Deprecated
  public final StreetViewPanorama getStreetViewPanorama()
  {
    IStreetViewPanoramaFragmentDelegate localIStreetViewPanoramaFragmentDelegate = zztG();
    if (localIStreetViewPanoramaFragmentDelegate == null);
    while (true)
    {
      return null;
      try
      {
        IStreetViewPanoramaDelegate localIStreetViewPanoramaDelegate = localIStreetViewPanoramaFragmentDelegate.getStreetViewPanorama();
        if (localIStreetViewPanoramaDelegate == null)
          continue;
        if ((this.zzaut == null) || (this.zzaut.zztF().asBinder() != localIStreetViewPanoramaDelegate.asBinder()))
          this.zzaut = new StreetViewPanorama(localIStreetViewPanoramaDelegate);
        return this.zzaut;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
  }

  public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
  {
    zzv.zzbI("getStreetViewPanoramaAsync() must be called on the main thread");
    this.zzauM.getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
    super.onActivityCreated(paramBundle);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    zzb.zza(this.zzauM, paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.zzauM.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return this.zzauM.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }

  public void onDestroy()
  {
    this.zzauM.onDestroy();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    this.zzauM.onDestroyView();
    super.onDestroyView();
  }

  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    super.onInflate(paramActivity, paramAttributeSet, paramBundle);
    zzb.zza(this.zzauM, paramActivity);
    Bundle localBundle = new Bundle();
    this.zzauM.onInflate(paramActivity, localBundle, paramBundle);
  }

  public void onLowMemory()
  {
    this.zzauM.onLowMemory();
    super.onLowMemory();
  }

  public void onPause()
  {
    this.zzauM.onPause();
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    this.zzauM.onResume();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(SupportStreetViewPanoramaFragment.class.getClassLoader());
    super.onSaveInstanceState(paramBundle);
    this.zzauM.onSaveInstanceState(paramBundle);
  }

  public void setArguments(Bundle paramBundle)
  {
    super.setArguments(paramBundle);
  }

  protected IStreetViewPanoramaFragmentDelegate zztG()
  {
    this.zzauM.zztD();
    if (this.zzauM.zzou() == null)
      return null;
    return ((zza)this.zzauM.zzou()).zztG();
  }

  static class zza
    implements StreetViewLifecycleDelegate
  {
    private final Fragment zzTb;
    private final IStreetViewPanoramaFragmentDelegate zzauu;

    public zza(Fragment paramFragment, IStreetViewPanoramaFragmentDelegate paramIStreetViewPanoramaFragmentDelegate)
    {
      this.zzauu = ((IStreetViewPanoramaFragmentDelegate)zzv.zzr(paramIStreetViewPanoramaFragmentDelegate));
      this.zzTb = ((Fragment)zzv.zzr(paramFragment));
    }

    public void getStreetViewPanoramaAsync(final OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
    {
      try
      {
        this.zzauu.getStreetViewPanoramaAsync(new zzv.zza()
        {
          public void zza(IStreetViewPanoramaDelegate paramAnonymousIStreetViewPanoramaDelegate)
            throws RemoteException
          {
            paramOnStreetViewPanoramaReadyCallback.onStreetViewPanoramaReady(new StreetViewPanorama(paramAnonymousIStreetViewPanoramaDelegate));
          }
        });
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onCreate(Bundle paramBundle)
    {
      if (paramBundle == null);
      try
      {
        paramBundle = new Bundle();
        Bundle localBundle = this.zzTb.getArguments();
        if ((localBundle != null) && (localBundle.containsKey("StreetViewPanoramaOptions")))
          zzx.zza(paramBundle, "StreetViewPanoramaOptions", localBundle.getParcelable("StreetViewPanoramaOptions"));
        this.zzauu.onCreate(paramBundle);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      try
      {
        zzd localzzd = this.zzauu.onCreateView(zze.zzt(paramLayoutInflater), zze.zzt(paramViewGroup), paramBundle);
        return (View)zze.zzg(localzzd);
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onDestroy()
    {
      try
      {
        this.zzauu.onDestroy();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onDestroyView()
    {
      try
      {
        this.zzauu.onDestroyView();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      try
      {
        this.zzauu.onInflate(zze.zzt(paramActivity), null, paramBundle2);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onLowMemory()
    {
      try
      {
        this.zzauu.onLowMemory();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onPause()
    {
      try
      {
        this.zzauu.onPause();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onResume()
    {
      try
      {
        this.zzauu.onResume();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onSaveInstanceState(Bundle paramBundle)
    {
      try
      {
        this.zzauu.onSaveInstanceState(paramBundle);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onStart()
    {
    }

    public void onStop()
    {
    }

    public IStreetViewPanoramaFragmentDelegate zztG()
    {
      return this.zzauu;
    }
  }

  static class zzb extends zza<SupportStreetViewPanoramaFragment.zza>
  {
    private final Fragment zzTb;
    protected zzf<SupportStreetViewPanoramaFragment.zza> zzauc;
    private final List<OnStreetViewPanoramaReadyCallback> zzaux = new ArrayList();
    private Activity zzpf;

    zzb(Fragment paramFragment)
    {
      this.zzTb = paramFragment;
    }

    private void setActivity(Activity paramActivity)
    {
      this.zzpf = paramActivity;
      zztD();
    }

    public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
    {
      if (zzou() != null)
      {
        ((SupportStreetViewPanoramaFragment.zza)zzou()).getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
        return;
      }
      this.zzaux.add(paramOnStreetViewPanoramaReadyCallback);
    }

    protected void zza(zzf<SupportStreetViewPanoramaFragment.zza> paramzzf)
    {
      this.zzauc = paramzzf;
      zztD();
    }

    public void zztD()
    {
      if ((this.zzpf != null) && (this.zzauc != null) && (zzou() == null));
      try
      {
        MapsInitializer.initialize(this.zzpf);
        IStreetViewPanoramaFragmentDelegate localIStreetViewPanoramaFragmentDelegate = zzy.zzah(this.zzpf).zzl(zze.zzt(this.zzpf));
        this.zzauc.zza(new SupportStreetViewPanoramaFragment.zza(this.zzTb, localIStreetViewPanoramaFragmentDelegate));
        Iterator localIterator = this.zzaux.iterator();
        while (localIterator.hasNext())
        {
          OnStreetViewPanoramaReadyCallback localOnStreetViewPanoramaReadyCallback = (OnStreetViewPanoramaReadyCallback)localIterator.next();
          ((SupportStreetViewPanoramaFragment.zza)zzou()).getStreetViewPanoramaAsync(localOnStreetViewPanoramaReadyCallback);
        }
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
        this.zzaux.clear();
        return;
      }
      catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
      {
      }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.SupportStreetViewPanoramaFragment
 * JD-Core Version:    0.6.2
 */