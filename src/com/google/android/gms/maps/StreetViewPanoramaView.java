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
import com.google.android.gms.maps.internal.IStreetViewPanoramaDelegate;
import com.google.android.gms.maps.internal.IStreetViewPanoramaViewDelegate;
import com.google.android.gms.maps.internal.StreetViewLifecycleDelegate;
import com.google.android.gms.maps.internal.zzc;
import com.google.android.gms.maps.internal.zzv.zza;
import com.google.android.gms.maps.internal.zzy;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class StreetViewPanoramaView extends FrameLayout
{
  private final zzb zzauF;
  private StreetViewPanorama zzaut;

  public StreetViewPanoramaView(Context paramContext)
  {
    super(paramContext);
    this.zzauF = new zzb(this, paramContext, null);
  }

  public StreetViewPanoramaView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.zzauF = new zzb(this, paramContext, null);
  }

  public StreetViewPanoramaView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.zzauF = new zzb(this, paramContext, null);
  }

  public StreetViewPanoramaView(Context paramContext, StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
  {
    super(paramContext);
    this.zzauF = new zzb(this, paramContext, paramStreetViewPanoramaOptions);
  }

  @Deprecated
  public final StreetViewPanorama getStreetViewPanorama()
  {
    if (this.zzaut != null)
      return this.zzaut;
    this.zzauF.zztD();
    if (this.zzauF.zzou() == null)
      return null;
    try
    {
      this.zzaut = new StreetViewPanorama(((zza)this.zzauF.zzou()).zztK().getStreetViewPanorama());
      return this.zzaut;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
  {
    zzv.zzbI("getStreetViewPanoramaAsync() must be called on the main thread");
    this.zzauF.getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
  }

  public final void onCreate(Bundle paramBundle)
  {
    this.zzauF.onCreate(paramBundle);
    if (this.zzauF.zzou() == null)
      zza.zzb(this);
  }

  public final void onDestroy()
  {
    this.zzauF.onDestroy();
  }

  public final void onLowMemory()
  {
    this.zzauF.onLowMemory();
  }

  public final void onPause()
  {
    this.zzauF.onPause();
  }

  public final void onResume()
  {
    this.zzauF.onResume();
  }

  public final void onSaveInstanceState(Bundle paramBundle)
  {
    this.zzauF.onSaveInstanceState(paramBundle);
  }

  static class zza
    implements StreetViewLifecycleDelegate
  {
    private final IStreetViewPanoramaViewDelegate zzauG;
    private View zzauH;
    private final ViewGroup zzauf;

    public zza(ViewGroup paramViewGroup, IStreetViewPanoramaViewDelegate paramIStreetViewPanoramaViewDelegate)
    {
      this.zzauG = ((IStreetViewPanoramaViewDelegate)zzv.zzr(paramIStreetViewPanoramaViewDelegate));
      this.zzauf = ((ViewGroup)zzv.zzr(paramViewGroup));
    }

    public void getStreetViewPanoramaAsync(final OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
    {
      try
      {
        this.zzauG.getStreetViewPanoramaAsync(new zzv.zza()
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
      try
      {
        this.zzauG.onCreate(paramBundle);
        this.zzauH = ((View)zze.zzg(this.zzauG.getView()));
        this.zzauf.removeAllViews();
        this.zzauf.addView(this.zzauH);
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
    {
      throw new UnsupportedOperationException("onCreateView not allowed on StreetViewPanoramaViewDelegate");
    }

    public void onDestroy()
    {
      try
      {
        this.zzauG.onDestroy();
        return;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }

    public void onDestroyView()
    {
      throw new UnsupportedOperationException("onDestroyView not allowed on StreetViewPanoramaViewDelegate");
    }

    public void onInflate(Activity paramActivity, Bundle paramBundle1, Bundle paramBundle2)
    {
      throw new UnsupportedOperationException("onInflate not allowed on StreetViewPanoramaViewDelegate");
    }

    public void onLowMemory()
    {
      try
      {
        this.zzauG.onLowMemory();
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
        this.zzauG.onPause();
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
        this.zzauG.onResume();
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
        this.zzauG.onSaveInstanceState(paramBundle);
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

    public IStreetViewPanoramaViewDelegate zztK()
    {
      return this.zzauG;
    }
  }

  static class zzb extends zza<StreetViewPanoramaView.zza>
  {
    private final Context mContext;
    private final StreetViewPanoramaOptions zzauJ;
    protected zzf<StreetViewPanoramaView.zza> zzauc;
    private final ViewGroup zzauj;
    private final List<OnStreetViewPanoramaReadyCallback> zzaux = new ArrayList();

    zzb(ViewGroup paramViewGroup, Context paramContext, StreetViewPanoramaOptions paramStreetViewPanoramaOptions)
    {
      this.zzauj = paramViewGroup;
      this.mContext = paramContext;
      this.zzauJ = paramStreetViewPanoramaOptions;
    }

    public void getStreetViewPanoramaAsync(OnStreetViewPanoramaReadyCallback paramOnStreetViewPanoramaReadyCallback)
    {
      if (zzou() != null)
      {
        ((StreetViewPanoramaView.zza)zzou()).getStreetViewPanoramaAsync(paramOnStreetViewPanoramaReadyCallback);
        return;
      }
      this.zzaux.add(paramOnStreetViewPanoramaReadyCallback);
    }

    protected void zza(zzf<StreetViewPanoramaView.zza> paramzzf)
    {
      this.zzauc = paramzzf;
      zztD();
    }

    public void zztD()
    {
      if ((this.zzauc != null) && (zzou() == null));
      try
      {
        IStreetViewPanoramaViewDelegate localIStreetViewPanoramaViewDelegate = zzy.zzah(this.mContext).zza(zze.zzt(this.mContext), this.zzauJ);
        this.zzauc.zza(new StreetViewPanoramaView.zza(this.zzauj, localIStreetViewPanoramaViewDelegate));
        Iterator localIterator = this.zzaux.iterator();
        while (localIterator.hasNext())
        {
          OnStreetViewPanoramaReadyCallback localOnStreetViewPanoramaReadyCallback = (OnStreetViewPanoramaReadyCallback)localIterator.next();
          ((StreetViewPanoramaView.zza)zzou()).getStreetViewPanoramaAsync(localOnStreetViewPanoramaReadyCallback);
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
 * Qualified Name:     com.google.android.gms.maps.StreetViewPanoramaView
 * JD-Core Version:    0.6.2
 */