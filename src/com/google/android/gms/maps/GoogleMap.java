package com.google.android.gms.maps;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.maps.internal.IGoogleMapDelegate;
import com.google.android.gms.maps.internal.zzb;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RuntimeRemoteException;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.zzf;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzi;
import com.google.android.gms.maps.model.internal.zzk;

public final class GoogleMap
{
  public static final int MAP_TYPE_HYBRID = 4;
  public static final int MAP_TYPE_NONE = 0;
  public static final int MAP_TYPE_NORMAL = 1;
  public static final int MAP_TYPE_SATELLITE = 2;
  public static final int MAP_TYPE_TERRAIN = 3;
  private final IGoogleMapDelegate zzats;
  private UiSettings zzatt;

  protected GoogleMap(IGoogleMapDelegate paramIGoogleMapDelegate)
  {
    this.zzats = ((IGoogleMapDelegate)zzv.zzr(paramIGoogleMapDelegate));
  }

  public final Circle addCircle(CircleOptions paramCircleOptions)
  {
    try
    {
      Circle localCircle = new Circle(this.zzats.addCircle(paramCircleOptions));
      return localCircle;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final GroundOverlay addGroundOverlay(GroundOverlayOptions paramGroundOverlayOptions)
  {
    try
    {
      zzf localzzf = this.zzats.addGroundOverlay(paramGroundOverlayOptions);
      if (localzzf != null)
      {
        GroundOverlay localGroundOverlay = new GroundOverlay(localzzf);
        return localGroundOverlay;
      }
      return null;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final Marker addMarker(MarkerOptions paramMarkerOptions)
  {
    try
    {
      zzi localzzi = this.zzats.addMarker(paramMarkerOptions);
      if (localzzi != null)
      {
        Marker localMarker = new Marker(localzzi);
        return localMarker;
      }
      return null;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final Polygon addPolygon(PolygonOptions paramPolygonOptions)
  {
    try
    {
      Polygon localPolygon = new Polygon(this.zzats.addPolygon(paramPolygonOptions));
      return localPolygon;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final Polyline addPolyline(PolylineOptions paramPolylineOptions)
  {
    try
    {
      Polyline localPolyline = new Polyline(this.zzats.addPolyline(paramPolylineOptions));
      return localPolyline;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final TileOverlay addTileOverlay(TileOverlayOptions paramTileOverlayOptions)
  {
    try
    {
      zzk localzzk = this.zzats.addTileOverlay(paramTileOverlayOptions);
      if (localzzk != null)
      {
        TileOverlay localTileOverlay = new TileOverlay(localzzk);
        return localTileOverlay;
      }
      return null;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void animateCamera(CameraUpdate paramCameraUpdate)
  {
    try
    {
      this.zzats.animateCamera(paramCameraUpdate.zztp());
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void animateCamera(CameraUpdate paramCameraUpdate, int paramInt, CancelableCallback paramCancelableCallback)
  {
    try
    {
      IGoogleMapDelegate localIGoogleMapDelegate = this.zzats;
      zzd localzzd = paramCameraUpdate.zztp();
      if (paramCancelableCallback == null);
      for (Object localObject = null; ; localObject = new GoogleMap.zza(paramCancelableCallback))
      {
        localIGoogleMapDelegate.animateCameraWithDurationAndCallback(localzzd, paramInt, (zzb)localObject);
        return;
      }
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void animateCamera(CameraUpdate paramCameraUpdate, CancelableCallback paramCancelableCallback)
  {
    try
    {
      IGoogleMapDelegate localIGoogleMapDelegate = this.zzats;
      zzd localzzd = paramCameraUpdate.zztp();
      if (paramCancelableCallback == null);
      for (Object localObject = null; ; localObject = new GoogleMap.zza(paramCancelableCallback))
      {
        localIGoogleMapDelegate.animateCameraWithCallback(localzzd, (zzb)localObject);
        return;
      }
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void clear()
  {
    try
    {
      this.zzats.clear();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final CameraPosition getCameraPosition()
  {
    try
    {
      CameraPosition localCameraPosition = this.zzats.getCameraPosition();
      return localCameraPosition;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public IndoorBuilding getFocusedBuilding()
  {
    try
    {
      zzg localzzg = this.zzats.getFocusedBuilding();
      if (localzzg != null)
      {
        IndoorBuilding localIndoorBuilding = new IndoorBuilding(localzzg);
        return localIndoorBuilding;
      }
      return null;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final int getMapType()
  {
    try
    {
      int i = this.zzats.getMapType();
      return i;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final float getMaxZoomLevel()
  {
    try
    {
      float f = this.zzats.getMaxZoomLevel();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final float getMinZoomLevel()
  {
    try
    {
      float f = this.zzats.getMinZoomLevel();
      return f;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  @Deprecated
  public final Location getMyLocation()
  {
    try
    {
      Location localLocation = this.zzats.getMyLocation();
      return localLocation;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final Projection getProjection()
  {
    try
    {
      Projection localProjection = new Projection(this.zzats.getProjection());
      return localProjection;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final UiSettings getUiSettings()
  {
    try
    {
      if (this.zzatt == null)
        this.zzatt = new UiSettings(this.zzats.getUiSettings());
      UiSettings localUiSettings = this.zzatt;
      return localUiSettings;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final boolean isBuildingsEnabled()
  {
    try
    {
      boolean bool = this.zzats.isBuildingsEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final boolean isIndoorEnabled()
  {
    try
    {
      boolean bool = this.zzats.isIndoorEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final boolean isMyLocationEnabled()
  {
    try
    {
      boolean bool = this.zzats.isMyLocationEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final boolean isTrafficEnabled()
  {
    try
    {
      boolean bool = this.zzats.isTrafficEnabled();
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void moveCamera(CameraUpdate paramCameraUpdate)
  {
    try
    {
      this.zzats.moveCamera(paramCameraUpdate.zztp());
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setBuildingsEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzats.setBuildingsEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setContentDescription(String paramString)
  {
    try
    {
      this.zzats.setContentDescription(paramString);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final boolean setIndoorEnabled(boolean paramBoolean)
  {
    try
    {
      boolean bool = this.zzats.setIndoorEnabled(paramBoolean);
      return bool;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setInfoWindowAdapter(InfoWindowAdapter paramInfoWindowAdapter)
  {
    if (paramInfoWindowAdapter == null);
    try
    {
      this.zzats.setInfoWindowAdapter(null);
      return;
      this.zzats.setInfoWindowAdapter(new GoogleMap.13(this, paramInfoWindowAdapter));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setLocationSource(LocationSource paramLocationSource)
  {
    if (paramLocationSource == null);
    try
    {
      this.zzats.setLocationSource(null);
      return;
      this.zzats.setLocationSource(new GoogleMap.6(this, paramLocationSource));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setMapType(int paramInt)
  {
    try
    {
      this.zzats.setMapType(paramInt);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setMyLocationEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzats.setMyLocationEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnCameraChangeListener(OnCameraChangeListener paramOnCameraChangeListener)
  {
    if (paramOnCameraChangeListener == null);
    try
    {
      this.zzats.setOnCameraChangeListener(null);
      return;
      this.zzats.setOnCameraChangeListener(new GoogleMap.7(this, paramOnCameraChangeListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnIndoorStateChangeListener(OnIndoorStateChangeListener paramOnIndoorStateChangeListener)
  {
    if (paramOnIndoorStateChangeListener == null);
    try
    {
      this.zzats.setOnIndoorStateChangeListener(null);
      return;
      this.zzats.setOnIndoorStateChangeListener(new GoogleMap.1(this, paramOnIndoorStateChangeListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnInfoWindowClickListener(OnInfoWindowClickListener paramOnInfoWindowClickListener)
  {
    if (paramOnInfoWindowClickListener == null);
    try
    {
      this.zzats.setOnInfoWindowClickListener(null);
      return;
      this.zzats.setOnInfoWindowClickListener(new GoogleMap.12(this, paramOnInfoWindowClickListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnMapClickListener(OnMapClickListener paramOnMapClickListener)
  {
    if (paramOnMapClickListener == null);
    try
    {
      this.zzats.setOnMapClickListener(null);
      return;
      this.zzats.setOnMapClickListener(new GoogleMap.8(this, paramOnMapClickListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public void setOnMapLoadedCallback(OnMapLoadedCallback paramOnMapLoadedCallback)
  {
    if (paramOnMapLoadedCallback == null);
    try
    {
      this.zzats.setOnMapLoadedCallback(null);
      return;
      this.zzats.setOnMapLoadedCallback(new GoogleMap.4(this, paramOnMapLoadedCallback));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnMapLongClickListener(OnMapLongClickListener paramOnMapLongClickListener)
  {
    if (paramOnMapLongClickListener == null);
    try
    {
      this.zzats.setOnMapLongClickListener(null);
      return;
      this.zzats.setOnMapLongClickListener(new GoogleMap.9(this, paramOnMapLongClickListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnMarkerClickListener(OnMarkerClickListener paramOnMarkerClickListener)
  {
    if (paramOnMarkerClickListener == null);
    try
    {
      this.zzats.setOnMarkerClickListener(null);
      return;
      this.zzats.setOnMarkerClickListener(new GoogleMap.10(this, paramOnMarkerClickListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnMarkerDragListener(OnMarkerDragListener paramOnMarkerDragListener)
  {
    if (paramOnMarkerDragListener == null);
    try
    {
      this.zzats.setOnMarkerDragListener(null);
      return;
      this.zzats.setOnMarkerDragListener(new GoogleMap.11(this, paramOnMarkerDragListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setOnMyLocationButtonClickListener(OnMyLocationButtonClickListener paramOnMyLocationButtonClickListener)
  {
    if (paramOnMyLocationButtonClickListener == null);
    try
    {
      this.zzats.setOnMyLocationButtonClickListener(null);
      return;
      this.zzats.setOnMyLocationButtonClickListener(new GoogleMap.3(this, paramOnMyLocationButtonClickListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  @Deprecated
  public final void setOnMyLocationChangeListener(OnMyLocationChangeListener paramOnMyLocationChangeListener)
  {
    if (paramOnMyLocationChangeListener == null);
    try
    {
      this.zzats.setOnMyLocationChangeListener(null);
      return;
      this.zzats.setOnMyLocationChangeListener(new GoogleMap.2(this, paramOnMyLocationChangeListener));
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    try
    {
      this.zzats.setPadding(paramInt1, paramInt2, paramInt3, paramInt4);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void setTrafficEnabled(boolean paramBoolean)
  {
    try
    {
      this.zzats.setTrafficEnabled(paramBoolean);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  public final void snapshot(SnapshotReadyCallback paramSnapshotReadyCallback)
  {
    snapshot(paramSnapshotReadyCallback, null);
  }

  public final void snapshot(SnapshotReadyCallback paramSnapshotReadyCallback, Bitmap paramBitmap)
  {
    zzd localzzd;
    if (paramBitmap != null)
      localzzd = zze.zzt(paramBitmap);
    while (true)
    {
      zze localzze = (zze)localzzd;
      try
      {
        this.zzats.snapshot(new GoogleMap.5(this, paramSnapshotReadyCallback), localzze);
        return;
        localzzd = null;
      }
      catch (RemoteException localRemoteException)
      {
        throw new RuntimeRemoteException(localRemoteException);
      }
    }
  }

  public final void stopAnimation()
  {
    try
    {
      this.zzats.stopAnimation();
      return;
    }
    catch (RemoteException localRemoteException)
    {
      throw new RuntimeRemoteException(localRemoteException);
    }
  }

  IGoogleMapDelegate zztr()
  {
    return this.zzats;
  }

  public static abstract interface CancelableCallback
  {
    public abstract void onCancel();

    public abstract void onFinish();
  }

  public static abstract interface InfoWindowAdapter
  {
    public abstract View getInfoContents(Marker paramMarker);

    public abstract View getInfoWindow(Marker paramMarker);
  }

  public static abstract interface OnCameraChangeListener
  {
    public abstract void onCameraChange(CameraPosition paramCameraPosition);
  }

  public static abstract interface OnIndoorStateChangeListener
  {
    public abstract void onIndoorBuildingFocused();

    public abstract void onIndoorLevelActivated(IndoorBuilding paramIndoorBuilding);
  }

  public static abstract interface OnInfoWindowClickListener
  {
    public abstract void onInfoWindowClick(Marker paramMarker);
  }

  public static abstract interface OnMapClickListener
  {
    public abstract void onMapClick(LatLng paramLatLng);
  }

  public static abstract interface OnMapLoadedCallback
  {
    public abstract void onMapLoaded();
  }

  public static abstract interface OnMapLongClickListener
  {
    public abstract void onMapLongClick(LatLng paramLatLng);
  }

  public static abstract interface OnMarkerClickListener
  {
    public abstract boolean onMarkerClick(Marker paramMarker);
  }

  public static abstract interface OnMarkerDragListener
  {
    public abstract void onMarkerDrag(Marker paramMarker);

    public abstract void onMarkerDragEnd(Marker paramMarker);

    public abstract void onMarkerDragStart(Marker paramMarker);
  }

  public static abstract interface OnMyLocationButtonClickListener
  {
    public abstract boolean onMyLocationButtonClick();
  }

  @Deprecated
  public static abstract interface OnMyLocationChangeListener
  {
    public abstract void onMyLocationChange(Location paramLocation);
  }

  public static abstract interface SnapshotReadyCallback
  {
    public abstract void onSnapshotReady(Bitmap paramBitmap);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.GoogleMap
 * JD-Core Version:    0.6.2
 */