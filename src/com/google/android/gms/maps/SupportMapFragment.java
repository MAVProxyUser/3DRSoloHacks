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
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzm.zza;
import com.google.android.gms.maps.internal.zzx;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SupportMapFragment extends Fragment
{
  private GoogleMap zzatY;
  private final zzb zzauK = new zzb(this);

  public static SupportMapFragment newInstance()
  {
    return new SupportMapFragment();
  }

  public static SupportMapFragment newInstance(GoogleMapOptions paramGoogleMapOptions)
  {
    SupportMapFragment localSupportMapFragment = new SupportMapFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("MapOptions", paramGoogleMapOptions);
    localSupportMapFragment.setArguments(localBundle);
    return localSupportMapFragment;
  }

  @Deprecated
  public final GoogleMap getMap()
  {
    IMapFragmentDelegate localIMapFragmentDelegate = zztC();
    if (localIMapFragmentDelegate == null);
    while (true)
    {
      return null;
      try
      {
        IGoogleMapDelegate localIGoogleMapDelegate = localIMapFragmentDelegate.getMap();
        if (localIGoogleMapDelegate == null)
          continue;
        if ((this.zzatY == null) || (this.zzatY.zztr().asBinder() != localIGoogleMapDelegate.asBinder()))
          this.zzatY = new GoogleMap(localIGoogleMapDelegate);
        return this.zzatY;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
  }

  public void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback)
  {
    zzv.zzbI("getMapAsync must be called on the main thread.");
    this.zzauK.getMapAsync(paramOnMapReadyCallback);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(SupportMapFragment.class.getClassLoader());
    super.onActivityCreated(paramBundle);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    zzb.zza(this.zzauK, paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.zzauK.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = this.zzauK.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    localView.setClickable(true);
    return localView;
  }

  public void onDestroy()
  {
    this.zzauK.onDestroy();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    this.zzauK.onDestroyView();
    super.onDestroyView();
  }

  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    super.onInflate(paramActivity, paramAttributeSet, paramBundle);
    zzb.zza(this.zzauK, paramActivity);
    GoogleMapOptions localGoogleMapOptions = GoogleMapOptions.createFromAttributes(paramActivity, paramAttributeSet);
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("MapOptions", localGoogleMapOptions);
    this.zzauK.onInflate(paramActivity, localBundle, paramBundle);
  }

  public void onLowMemory()
  {
    this.zzauK.onLowMemory();
    super.onLowMemory();
  }

  public void onPause()
  {
    this.zzauK.onPause();
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    this.zzauK.onResume();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(SupportMapFragment.class.getClassLoader());
    super.onSaveInstanceState(paramBundle);
    this.zzauK.onSaveInstanceState(paramBundle);
  }

  public void setArguments(Bundle paramBundle)
  {
    super.setArguments(paramBundle);
  }

  protected IMapFragmentDelegate zztC()
  {
    this.zzauK.zztD();
    if (this.zzauK.zzou() == null)
      return null;
    return ((zza)this.zzauK.zzou()).zztC();
  }

  static class zza
    implements MapLifecycleDelegate
  {
    private final Fragment zzTb;
    private final IMapFragmentDelegate zzatZ;

    public zza(Fragment paramFragment, IMapFragmentDelegate paramIMapFragmentDelegate)
    {
      this.zzatZ = ((IMapFragmentDelegate)zzv.zzr(paramIMapFragmentDelegate));
      this.zzTb = ((Fragment)zzv.zzr(paramFragment));
    }

    public void getMapAsync(final OnMapReadyCallback paramOnMapReadyCallback)
    {
      try
      {
        this.zzatZ.getMapAsync(new zzm.zza()
        {
          public void zza(IGoogleMapDelegate paramAnonymousIGoogleMapDelegate)
            throws RemoteException
          {
            paramOnMapReadyCallback.onMapReady(new GoogleMap(paramAnonymousIGoogleMapDelegate));
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
        if ((localBundle != null) && (localBundle.containsKey("MapOptions")))
          zzx.zza(paramBundle, "MapOptions", localBundle.getParcelable("MapOptions"));
        this.zzatZ.onCreate(paramBundle);
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
        zzd localzzd = this.zzatZ.onCreateView(zze.zzt(paramLayoutInflater), zze.zzt(paramViewGroup), paramBundle);
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
        this.zzatZ.onDestroy();
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
        this.zzatZ.onDestroyView();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      GoogleMapOptions localGoogleMapOptions = (GoogleMapOptions)paramBundle1.getParcelable("MapOptions");
      try
      {
        this.zzatZ.onInflate(zze.zzt(paramActivity), localGoogleMapOptions, paramBundle2);
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
        this.zzatZ.onLowMemory();
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
        this.zzatZ.onPause();
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
        this.zzatZ.onResume();
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
        this.zzatZ.onSaveInstanceState(paramBundle);
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

    public IMapFragmentDelegate zztC()
    {
      return this.zzatZ;
    }
  }

  static class zzb extends zza<SupportMapFragment.zza>
  {
    private final Fragment zzTb;
    protected zzf<SupportMapFragment.zza> zzauc;
    private final List<OnMapReadyCallback> zzaud = new ArrayList();
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

    public void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback)
    {
      if (zzou() != null)
      {
        ((SupportMapFragment.zza)zzou()).getMapAsync(paramOnMapReadyCallback);
        return;
      }
      this.zzaud.add(paramOnMapReadyCallback);
    }

    protected void zza(zzf<SupportMapFragment.zza> paramzzf)
    {
      this.zzauc = paramzzf;
      zztD();
    }

    public void zztD()
    {
      if ((this.zzpf != null) && (this.zzauc != null) && (zzou() == null))
        try
        {
          MapsInitializer.initialize(this.zzpf);
          IMapFragmentDelegate localIMapFragmentDelegate = zzy.zzah(this.zzpf).zzk(zze.zzt(this.zzpf));
          if (localIMapFragmentDelegate == null)
            return;
          this.zzauc.zza(new SupportMapFragment.zza(this.zzTb, localIMapFragmentDelegate));
          Iterator localIterator = this.zzaud.iterator();
          while (localIterator.hasNext())
          {
            OnMapReadyCallback localOnMapReadyCallback = (OnMapReadyCallback)localIterator.next();
            ((SupportMapFragment.zza)zzou()).getMapAsync(localOnMapReadyCallback);
          }
        }
        catch (RemoteException localRemoteException)
        {
          throw new RuntimeRemoteException(localRemoteException);
          this.zzaud.clear();
          return;
        }
        catch (GooglePlayServicesNotAvailableException localGooglePlayServicesNotAvailableException)
        {
        }
    }
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.SupportMapFragment
 * JD-Core Version:    0.6.2
 */