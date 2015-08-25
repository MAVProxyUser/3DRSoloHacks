package com.google.android.gms.maps;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.dynamic.zzf;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.internal.MapLifecycleDelegate;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzm.zza;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapView extends FrameLayout
{
  private GoogleMap zzatY;
  private final zzb zzaue;

  public MapView(Context paramContext)
  {
    super(paramContext);
    this.zzaue = new zzb(this, paramContext, null);
    init();
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.zzaue = new zzb(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
    init();
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.zzaue = new zzb(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
    init();
  }

  public MapView(Context paramContext, GoogleMapOptions paramGoogleMapOptions)
  {
    super(paramContext);
    this.zzaue = new zzb(this, paramContext, paramGoogleMapOptions);
    init();
  }

  private void init()
  {
    setClickable(true);
  }

  @Deprecated
  public final GoogleMap getMap()
  {
    if (this.zzatY != null)
      return this.zzatY;
    this.zzaue.zztD();
    if (this.zzaue.zzou() == null)
      return null;
    try
    {
      this.zzatY = new GoogleMap(((zza)this.zzaue.zzou()).zztE().getMap());
      return this.zzatY;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback)
  {
    zzv.zzbI("getMapAsync() must be called on the main thread");
    this.zzaue.getMapAsync(paramOnMapReadyCallback);
  }

  public final void onCreate(Bundle paramBundle)
  {
    this.zzaue.onCreate(paramBundle);
    if (this.zzaue.zzou() == null)
      zza.zzb(this);
  }

  public final void onDestroy()
  {
    this.zzaue.onDestroy();
  }

  public final void onLowMemory()
  {
    this.zzaue.onLowMemory();
  }

  public final void onPause()
  {
    this.zzaue.onPause();
  }

  public final void onResume()
  {
    this.zzaue.onResume();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    this.zzaue.onSaveInstanceState(paramBundle);
  }

  static class zza
    implements MapLifecycleDelegate
  {
    private final ViewGroup zzauf;
    private final IMapViewDelegate zzaug;
    private View zzauh;

    public zza(ViewGroup paramViewGroup, IMapViewDelegate paramIMapViewDelegate)
    {
      this.zzaug = ((IMapViewDelegate)zzv.zzr(paramIMapViewDelegate));
      this.zzauf = ((ViewGroup)zzv.zzr(paramViewGroup));
    }

    public void getMapAsync(final OnMapReadyCallback paramOnMapReadyCallback)
    {
      try
      {
        this.zzaug.getMapAsync(new zzm.zza()
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
      try
      {
        this.zzaug.onCreate(paramBundle);
        this.zzauh = ((View)zze.zzg(this.zzaug.getView()));
        this.zzauf.removeAllViews();
        this.zzauf.addView(this.zzauh);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      throw new UnsupportedOperationException("onCreateView not allowed on MapViewDelegate");
    }

    public void onDestroy()
    {
      try
      {
        this.zzaug.onDestroy();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onDestroyView()
    {
      throw new UnsupportedOperationException("onDestroyView not allowed on MapViewDelegate");
    }

    public void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      throw new UnsupportedOperationException("onInflate not allowed on MapViewDelegate");
    }

    public void onLowMemory()
    {
      try
      {
        this.zzaug.onLowMemory();
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
        this.zzaug.onPause();
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
        this.zzaug.onResume();
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
        this.zzaug.onSaveInstanceState(paramBundle);
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

    public IMapViewDelegate zztE()
    {
      return this.zzaug;
    }
  }

  static class zzb extends zza<MapView.zza>
  {
    private final Context mContext;
    protected zzf<MapView.zza> zzauc;
    private final List<OnMapReadyCallback> zzaud = new ArrayList();
    private final ViewGroup zzauj;
    private final GoogleMapOptions zzauk;

    zzb(ViewGroup paramViewGroup, Context paramContext, GoogleMapOptions paramGoogleMapOptions)
    {
      this.zzauj = paramViewGroup;
      this.mContext = paramContext;
      this.zzauk = paramGoogleMapOptions;
    }

    public void getMapAsync(OnMapReadyCallback paramOnMapReadyCallback)
    {
      if (zzou() != null)
      {
        ((MapView.zza)zzou()).getMapAsync(paramOnMapReadyCallback);
        return;
      }
      this.zzaud.add(paramOnMapReadyCallback);
    }

    protected void zza(zzf<MapView.zza> paramzzf)
    {
      this.zzauc = paramzzf;
      zztD();
    }

    public void zztD()
    {
      if ((this.zzauc != null) && (zzou() == null))
        try
        {
          MapsInitializer.initialize(this.mContext);
          IMapViewDelegate localIMapViewDelegate = zzy.zzah(this.mContext).zza(zze.zzt(this.mContext), this.zzauk);
          if (localIMapViewDelegate == null)
            return;
          this.zzauc.zza(new MapView.zza(this.zzauj, localIMapViewDelegate));
          Iterator localIterator = this.zzaud.iterator();
          while (localIterator.hasNext())
          {
            OnMapReadyCallback localOnMapReadyCallback = (OnMapReadyCallback)localIterator.next();
            ((MapView.zza)zzou()).getMapAsync(localOnMapReadyCallback);
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
 * Qualified Name:     com.google.android.gms.maps.MapView
 * JD-Core Version:    0.6.2
 */