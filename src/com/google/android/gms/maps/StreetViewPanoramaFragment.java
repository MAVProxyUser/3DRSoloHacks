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
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaFragmentDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class StreetViewPanoramaFragment extends Fragment
{
  private final StreetViewPanoramaFragment.zzb zzaus = new StreetViewPanoramaFragment.zzb(this);
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
    StreetViewPanoramaFragment.zzb.zza(this.zzaus, paramActivity);
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
    StreetViewPanoramaFragment.zzb.zza(this.zzaus, paramActivity);
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
    return ((StreetViewPanoramaFragment.zza)this.zzaus.zzou()).zztG();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.StreetViewPanoramaFragment
 * JD-Core Version:    0.6.2
 */