package com.google.android.gms.maps;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapFragmentDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapFragment extends Fragment
{
  private final MapFragment.zzb zzatX = new MapFragment.zzb(this);
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
    MapFragment.zzb.zza(this.zzatX, paramActivity);
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
    MapFragment.zzb.zza(this.zzatX, paramActivity);
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
    return ((MapFragment.zza)this.zzatX.zzou()).zztC();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.MapFragment
 * JD-Core Version:    0.6.2
 */