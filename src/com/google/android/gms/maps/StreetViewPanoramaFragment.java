package com.google.android.gms.maps;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.RemoteException;
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

public class StreetViewPanoramaFragment extends Fragment
{
  private final zzb zzaus = new zzb(this);
  private StreetViewPanorama zzaut;

  public static StreetViewPanoramaFragment newInstance()
  {
    return new StreetViewPanoramaFragment();
  }

  public static StreetViewPanoramaFragment newInstance(StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
  {
    StreetViewPanoramaFragment localStreetViewPanoramaFragment = new StreetViewPanoramaFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("StreetViewPanoramaOptions", paramStreetViewPanoramaOptions);
    localStreetViewPanoramaFragment.setArguments(localBundle);
    return localStreetViewPanoramaFragment;
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
    this.zzaus.getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(StreetViewPanoramaFragment.class.getClassLoader());
    super.onActivityCreated(paramBundle);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    zzb.zza(this.zzaus, paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.zzaus.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return this.zzaus.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
  }

  public void onDestroy()
  {
    this.zzaus.onDestroy();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    this.zzaus.onDestroyView();
    super.onDestroyView();
  }

  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    super.onInflate(paramActivity, paramAttributeSet, paramBundle);
    zzb.zza(this.zzaus, paramActivity);
    Bundle localBundle = new Bundle();
    this.zzaus.onInflate(paramActivity, localBundle, paramBundle);
  }

  public void onLowMemory()
  {
    this.zzaus.onLowMemory();
    super.onLowMemory();
  }

  public void onPause()
  {
    this.zzaus.onPause();
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    this.zzaus.onResume();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(StreetViewPanoramaFragment.class.getClassLoader());
    super.onSaveInstanceState(paramBundle);
    this.zzaus.onSaveInstanceState(paramBundle);
  }

  public void setArguments(Bundle paramBundle)
  {
    super.setArguments(paramBundle);
  }

  protected IStreetViewPanoramaFragmentDelegate zztG()
  {
    this.zzaus.zztD();
    if (this.zzaus.zzou() == null)
      return null;
    return ((zza)this.zzaus.zzou()).zztG();
  }

  static class zza
    implements StreetViewLifecycleDelegate
  {
    private final Fragment zzacp;
    private final IStreetViewPanoramaFragmentDelegate zzauu;

    public zza(Fragment paramFragment, IStreetViewPanoramaFragmentDelegate paramIStreetViewPanoramaFragmentDelegate)
    {
      this.zzauu = ((IStreetViewPanoramaFragmentDelegate)zzv.zzr(paramIStreetViewPanoramaFragmentDelegate));
      this.zzacp = ((Fragment)zzv.zzr(paramFragment));
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
        Bundle localBundle = this.zzacp.getArguments();
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

  static class zzb extends zza<StreetViewPanoramaFragment.zza>
  {
    private final Fragment zzacp;
    protected zzf<StreetViewPanoramaFragment.zza> zzauc;
    private final List<OnStreetViewPanoramaReadyCallback> zzaux = new ArrayList();
    private Activity zzpf;

    zzb(Fragment paramFragment)
    {
      this.zzacp = paramFragment;
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
        ((StreetViewPanoramaFragment.zza)zzou()).getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
        return;
      }
      this.zzaux.add(paramOnStreetViewPanoramaReadyCallback);
    }

    protected void zza(zzf<StreetViewPanoramaFragment.zza> paramzzf)
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
        this.zzauc.zza(new StreetViewPanoramaFragment.zza(this.zzacp, localIStreetViewPanoramaFragmentDelegate));
        Iterator localIterator = this.zzaux.iterator();
        while (localIterator.hasNext())
        {
          OnStreetViewPanoramaReadyCallback localOnStreetViewPanoramaReadyCallback = (OnStreetViewPanoramaReadyCallback)localIterator.next();
          ((StreetViewPanoramaFragment.zza)zzou()).getStreetViewPanoramaAsync(localOnStreetViewPanoramaReadyCallback);
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
 * Qualified Name:     com.google.android.gms.maps.StreetViewPanoramaFragment
 * JD-Core Version:    0.6.2
 */