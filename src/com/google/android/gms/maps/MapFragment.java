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

public class MapFragment extends Fragment
{
  private final zzb zzatX = new zzb(this);
  private GoogleMap zzatY;

  public static MapFragment newInstance()
  {
    return new MapFragment();
  }

  public static MapFragment newInstance(GoogleMapOptions paramGoogleMapOptions)
  {
    MapFragment localMapFragment = new MapFragment();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("MapOptions", paramGoogleMapOptions);
    localMapFragment.setArguments(localBundle);
    return localMapFragment;
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
    this.zzatX.getMapAsync(paramOnMapReadyCallback);
  }

  public void onActivityCreated(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(MapFragment.class.getClassLoader());
    super.onActivityCreated(paramBundle);
  }

  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    zzb.zza(this.zzatX, paramActivity);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.zzatX.onCreate(paramBundle);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = this.zzatX.onCreateView(paramLayoutInflater, paramViewGroup, paramBundle);
    localView.setClickable(true);
    return localView;
  }

  public void onDestroy()
  {
    this.zzatX.onDestroy();
    super.onDestroy();
  }

  public void onDestroyView()
  {
    this.zzatX.onDestroyView();
    super.onDestroyView();
  }

  public void onInflate(Activity paramActivity, AttributeSet paramAttributeSet, Bundle paramBundle)
  {
    super.onInflate(paramActivity, paramAttributeSet, paramBundle);
    zzb.zza(this.zzatX, paramActivity);
    GoogleMapOptions localGoogleMapOptions = GoogleMapOptions.createFromAttributes(paramActivity, paramAttributeSet);
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("MapOptions", localGoogleMapOptions);
    this.zzatX.onInflate(paramActivity, localBundle, paramBundle);
  }

  public void onLowMemory()
  {
    this.zzatX.onLowMemory();
    super.onLowMemory();
  }

  public void onPause()
  {
    this.zzatX.onPause();
    super.onPause();
  }

  public void onResume()
  {
    super.onResume();
    this.zzatX.onResume();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    if (paramBundle != null)
      paramBundle.setClassLoader(MapFragment.class.getClassLoader());
    super.onSaveInstanceState(paramBundle);
    this.zzatX.onSaveInstanceState(paramBundle);
  }

  public void setArguments(Bundle paramBundle)
  {
    super.setArguments(paramBundle);
  }

  protected IMapFragmentDelegate zztC()
  {
    this.zzatX.zztD();
    if (this.zzatX.zzou() == null)
      return null;
    return ((zza)this.zzatX.zzou()).zztC();
  }

  static class zza
    implements MapLifecycleDelegate
  {
    private final Fragment zzacp;
    private final IMapFragmentDelegate zzatZ;

    public zza(Fragment paramFragment, IMapFragmentDelegate paramIMapFragmentDelegate)
    {
      this.zzatZ = ((IMapFragmentDelegate)zzv.zzr(paramIMapFragmentDelegate));
      this.zzacp = ((Fragment)zzv.zzr(paramFragment));
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
        Bundle localBundle = this.zzacp.getArguments();
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

  static class zzb extends zza<MapFragment.zza>
  {
    private final Fragment zzacp;
    protected zzf<MapFragment.zza> zzauc;
    private final List<OnMapReadyCallback> zzaud = new ArrayList();
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

    public void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback)
    {
      if (zzou() != null)
      {
        ((MapFragment.zza)zzou()).getMapAsync(paramOnMapReadyCallback);
        return;
      }
      this.zzaud.add(paramOnMapReadyCallback);
    }

    protected void zza(zzf<MapFragment.zza> paramzzf)
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
          this.zzauc.zza(new MapFragment.zza(this.zzacp, localIMapFragmentDelegate));
          Iterator localIterator = this.zzaud.iterator();
          while (localIterator.hasNext())
          {
            OnMapReadyCallback localOnMapReadyCallback = (OnMapReadyCallback)localIterator.next();
            ((MapFragment.zza)zzou()).getMapAsync(localOnMapReadyCallback);
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
 * Qualified Name:     com.google.android.gms.maps.MapFragment
 * JD-Core Version:    0.6.2
 */