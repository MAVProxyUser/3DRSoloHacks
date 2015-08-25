package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.CameraUpdateParcelable;
import com.google.android.gms.maps.model.internal.GroundOverlayOptionsParcelable;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.MarkerOptionsParcelable;
import com.google.android.gms.maps.model.internal.zzi;

public abstract interface IGoogleMapDelegate extends IInterface
{
  public abstract com.google.android.gms.maps.model.internal.zze addCircle(CircleOptions paramCircleOptions)
    throws RemoteException;

  public abstract com.google.android.gms.maps.model.internal.zzf addGroundOverlay(GroundOverlayOptions paramGroundOverlayOptions)
    throws RemoteException;

  public abstract com.google.android.gms.maps.model.internal.zzf addGroundOverlay2(GroundOverlayOptions paramGroundOverlayOptions, GroundOverlayOptionsParcelable paramGroundOverlayOptionsParcelable)
    throws RemoteException;

  public abstract zzi addMarker(MarkerOptions paramMarkerOptions)
    throws RemoteException;

  public abstract zzi addMarker2(MarkerOptions paramMarkerOptions, MarkerOptionsParcelable paramMarkerOptionsParcelable)
    throws RemoteException;

  public abstract com.google.android.gms.maps.model.internal.zzj addPolygon(PolygonOptions paramPolygonOptions)
    throws RemoteException;

  public abstract IPolylineDelegate addPolyline(PolylineOptions paramPolylineOptions)
    throws RemoteException;

  public abstract com.google.android.gms.maps.model.internal.zzk addTileOverlay(TileOverlayOptions paramTileOverlayOptions)
    throws RemoteException;

  public abstract void animateCamera(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract void animateCamera2(CameraUpdateParcelable paramCameraUpdateParcelable)
    throws RemoteException;

  public abstract void animateCameraWithCallback(com.google.android.gms.dynamic.zzd paramzzd, zzb paramzzb)
    throws RemoteException;

  public abstract void animateCameraWithCallback2(CameraUpdateParcelable paramCameraUpdateParcelable, zzb paramzzb)
    throws RemoteException;

  public abstract void animateCameraWithDurationAndCallback(com.google.android.gms.dynamic.zzd paramzzd, int paramInt, zzb paramzzb)
    throws RemoteException;

  public abstract void animateCameraWithDurationAndCallback2(CameraUpdateParcelable paramCameraUpdateParcelable, int paramInt, zzb paramzzb)
    throws RemoteException;

  public abstract void clear()
    throws RemoteException;

  public abstract CameraPosition getCameraPosition()
    throws RemoteException;

  public abstract com.google.android.gms.maps.model.internal.zzg getFocusedBuilding()
    throws RemoteException;

  public abstract void getMapAsync(zzm paramzzm)
    throws RemoteException;

  public abstract int getMapType()
    throws RemoteException;

  public abstract float getMaxZoomLevel()
    throws RemoteException;

  public abstract float getMinZoomLevel()
    throws RemoteException;

  public abstract Location getMyLocation()
    throws RemoteException;

  public abstract IProjectionDelegate getProjection()
    throws RemoteException;

  public abstract IUiSettingsDelegate getUiSettings()
    throws RemoteException;

  public abstract boolean isBuildingsEnabled()
    throws RemoteException;

  public abstract boolean isIndoorEnabled()
    throws RemoteException;

  public abstract boolean isMyLocationEnabled()
    throws RemoteException;

  public abstract boolean isTrafficEnabled()
    throws RemoteException;

  public abstract void moveCamera(com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract void moveCamera2(CameraUpdateParcelable paramCameraUpdateParcelable)
    throws RemoteException;

  public abstract void onCreate(Bundle paramBundle)
    throws RemoteException;

  public abstract void onDestroy()
    throws RemoteException;

  public abstract void onLowMemory()
    throws RemoteException;

  public abstract void onPause()
    throws RemoteException;

  public abstract void onResume()
    throws RemoteException;

  public abstract void onSaveInstanceState(Bundle paramBundle)
    throws RemoteException;

  public abstract void setBuildingsEnabled(boolean paramBoolean)
    throws RemoteException;

  public abstract void setContentDescription(String paramString)
    throws RemoteException;

  public abstract boolean setIndoorEnabled(boolean paramBoolean)
    throws RemoteException;

  public abstract void setInfoWindowAdapter(zzd paramzzd)
    throws RemoteException;

  public abstract void setInfoWindowRenderer(zze paramzze)
    throws RemoteException;

  public abstract void setLocationSource(ILocationSourceDelegate paramILocationSourceDelegate)
    throws RemoteException;

  public abstract void setMapType(int paramInt)
    throws RemoteException;

  public abstract void setMyLocationEnabled(boolean paramBoolean)
    throws RemoteException;

  public abstract void setOnCameraChangeListener(zzf paramzzf)
    throws RemoteException;

  public abstract void setOnIndoorStateChangeListener(zzg paramzzg)
    throws RemoteException;

  public abstract void setOnInfoWindowClickListener(zzh paramzzh)
    throws RemoteException;

  public abstract void setOnMapClickListener(zzj paramzzj)
    throws RemoteException;

  public abstract void setOnMapLoadedCallback(zzk paramzzk)
    throws RemoteException;

  public abstract void setOnMapLongClickListener(zzl paramzzl)
    throws RemoteException;

  public abstract void setOnMarkerClickListener(zzn paramzzn)
    throws RemoteException;

  public abstract void setOnMarkerDragListener(zzo paramzzo)
    throws RemoteException;

  public abstract void setOnMyLocationButtonClickListener(zzp paramzzp)
    throws RemoteException;

  public abstract void setOnMyLocationChangeListener(zzq paramzzq)
    throws RemoteException;

  public abstract void setPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    throws RemoteException;

  public abstract void setTrafficEnabled(boolean paramBoolean)
    throws RemoteException;

  public abstract void snapshot(zzw paramzzw, com.google.android.gms.dynamic.zzd paramzzd)
    throws RemoteException;

  public abstract void stopAnimation()
    throws RemoteException;

  public abstract boolean useViewLifecycleWhenInFragment()
    throws RemoteException;
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.maps.internal.IGoogleMapDelegate
 * JD-Core Version:    0.6.2
 */