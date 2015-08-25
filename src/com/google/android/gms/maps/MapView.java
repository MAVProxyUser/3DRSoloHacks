package com.google.android.gms.maps;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.dynamic.zza;
import com.google.android.gms.maps.internal.IMapViewDelegate;
import com.google.android.gms.maps.model.RuntimeRemoteException;

public class MapView extends FrameLayout
{
  private GoogleMap zzatY;
  private final MapView.zzb zzaue;

  public MapView(Context paramContext)
  {
    super(paramContext);
    this.zzaue = new MapView.zzb(this, paramContext, null);
    init();
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.zzaue = new MapView.zzb(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
    init();
  }

  public MapView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    this.zzaue = new MapView.zzb(this, paramContext, GoogleMapOptions.createFromAttributes(paramContext, paramAttributeSet));
    init();
  }

  public MapView(Context paramContext, GoogleMapOptions paramGoogleMapOptions)
  {
    super(paramContext);
    this.zzaue = new MapView.zzb(this, paramContext, paramGoogleMapOptions);
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
      this.zzatY = new GoogleMap(((MapView.zza)this.zzaue.zzou()).zztE().getMap());
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
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.MapView
 * JD-Core Version:    0.6.2
 */