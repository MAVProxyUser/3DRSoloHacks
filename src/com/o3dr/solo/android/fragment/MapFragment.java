package com.o3dr.solo.android.fragment;

import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.VisibleRegion;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.property.Attitude;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.drone.property.Home;
import com.o3dr.services.android.lib.util.MathUtils;
import com.o3dr.solo.android.appstate.SoloApp;
import com.o3dr.solo.android.fragment.alerts.AlertType;
import com.o3dr.solo.android.fragment.alerts.AlertsListener;
import com.o3dr.solo.android.fragment.base.BaseFragment;
import com.o3dr.solo.android.fragment.shots.ShotControlListener;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.shot.FollowShotState;
import com.o3dr.solo.android.service.sololink.shot.OrbitState;
import com.o3dr.solo.android.service.sololink.shot.ShotManager;
import com.o3dr.solo.android.service.sololink.shot.ShotState;
import com.o3dr.solo.android.util.NetworkUtils;
import com.o3dr.solo.android.util.geo.GeoTools;
import com.o3dr.solo.android.util.maps.mapbox.MapboxTileProvider;
import com.o3dr.solo.android.util.maps.mapbox.OfflineTileProvider;
import com.o3dr.solo.android.util.maps.mapbox.offline.MapDownloader;
import com.o3dr.solo.android.util.maps.mapbox.offline.MapDownloader.MBXOfflineMapDownloaderState;
import com.o3dr.solo.android.util.maps.mapbox.offline.MapDownloaderListener;
import com.sothree.slidinguppanel.SlidingPanelLayout.PanelSlideListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class MapFragment extends BaseFragment
  implements SlidingPanelLayout.PanelSlideListener, AlertsListener, ShotControlListener, MapDownloaderListener
{
  private static final long CONNECTIVITY_CHECK_DELAY = 3500L;
  public static final int DEFAULT_BEARING = 0;
  public static final float DEFAULT_LATITUDE = 37.857552F;
  public static final float DEFAULT_LONGITUDE = -122.29277F;
  public static final int DEFAULT_TILT = 0;
  public static final float DEFAULT_ZOOM_LEVEL = 19.0F;
  public static final int MAP_CACHE_MAX_ZOOM_LEVEL = 19;
  public static final int MAP_CACHE_MIN_ZOOM_LEVEL = 16;
  private static final int OFFLINE_TILE_PROVIDER_Z_INDEX = -2;
  private static final int ONLINE_TILE_PROVIDER_Z_INDEX = -1;
  public static final String PREF_BEA = "pref_map_bea";
  public static final String PREF_LAT = "pref_map_lat";
  public static final String PREF_LNG = "pref_map_lng";
  public static final String PREF_TILT = "pref_map_tilt";
  public static final String PREF_ZOOM = "pref_map_zoom";
  private static final float ROTATION_ANIM_THRESHOLD = 15.0F;
  private static final String TAG = MapFragment.class.getSimpleName();
  private static final IntentFilter eventFilter = new IntentFilter();
  private int actionBarToolbarHeight;
  private int bottomPadding;
  private final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      int i = 1;
      Drone localDrone = MapFragment.this.getDrone();
      if (localDrone == null);
      label203: int k;
      do
      {
        int m;
        do
        {
          ShotState localShotState;
          do
          {
            do
            {
              return;
              String str = paramAnonymousIntent.getAction();
              int j;
              MapFragment localMapFragment;
              switch (str.hashCode())
              {
              default:
                j = -1;
                switch (j)
                {
                default:
                  return;
                case 0:
                  localMapFragment = MapFragment.this;
                  if (MapFragment.this.droneMarker != null);
                  break;
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                case 18:
                case 19:
                }
                break;
              case -258149067:
              case -966973459:
              case 1256617868:
              case -1096304662:
              case -1002472721:
              case -528266846:
              case -286121488:
              case 494888137:
              case -1578907242:
              case 1781897487:
              case 1383094019:
              case 441792541:
              case -42951236:
              case 1841369708:
              case -818937780:
              case 191628613:
              case -260677709:
              case -302519047:
              case -508972501:
              case -1172645946:
              }
              while (true)
              {
                localMapFragment.updateDroneOnMap(localDrone, i);
                return;
                if (!str.equals("com.o3dr.services.android.lib.attribute.event.ATTITUDE_UPDATED"))
                  break;
                j = 0;
                break label203;
                if (!str.equals("com.o3dr.services.android.lib.attribute.event.GPS_POSITION"))
                  break;
                j = i;
                break label203;
                if (!str.equals("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED"))
                  break;
                j = 2;
                break label203;
                if (!str.equals("com.o3dr.services.android.lib.attribute.event.HOME_UPDATED"))
                  break;
                j = 3;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.CABLE_CAM_WAYPOINT_SET"))
                  break;
                j = 4;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.SELFIE_WAYPOINT_SET"))
                  break;
                j = 5;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action_ORBIT_ROI_SET"))
                  break;
                j = 6;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.FOLLOW_TARGET_SET"))
                  break;
                j = 7;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.CABLE_CAM_STARTED"))
                  break;
                j = 8;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.CABLE_CAM_ENDED"))
                  break;
                j = 9;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.SELFIE_STARTED"))
                  break;
                j = 10;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.SELFIE_SETTINGS_UPDATED"))
                  break;
                j = 11;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.SELFIE_ENDED"))
                  break;
                j = 12;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.ORBIT_ENDED"))
                  break;
                j = 13;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.FOLLOW_STARTED"))
                  break;
                j = 14;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.FOLLOW_ENDED"))
                  break;
                j = 15;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.ORBIT_STARTED"))
                  break;
                j = 16;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED"))
                  break;
                j = 17;
                break label203;
                if (!str.equals("com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED"))
                  break;
                j = 18;
                break label203;
                if (!str.equals("android.net.conn.CONNECTIVITY_CHANGE"))
                  break;
                j = 19;
                break label203;
                i = 0;
              }
              MapFragment.this.updateDroneOnMap(localDrone, i);
              localShotState = MapFragment.this.getSoloLinkManager().getShotManager().getCurrentShotState();
              if (!(localShotState instanceof OrbitState))
                break;
              if (localShotState.isActive())
              {
                MapFragment.this.updateOrbitMarker(localShotState.getWaypoints()[0]);
                return;
              }
            }
            while (!localShotState.isStarted());
            MapFragment.this.updateOrbitMarker(OrbitState.estimateOrbitRoi(MapFragment.this.getDrone()));
            return;
          }
          while ((!(localShotState instanceof FollowShotState)) || (!localShotState.isActive()));
          MapFragment.this.updateOrbitMarker(localShotState.getWaypoints()[0]);
          return;
          MapFragment.this.updateDroneOnMap(localDrone, i);
          return;
          MapFragment.this.updateHomeLocation(localDrone);
          return;
          m = paramAnonymousIntent.getIntExtra("extra_cable_cam_waypoint_index", -1);
        }
        while (m <= -1);
        LatLongAlt localLatLongAlt4 = (LatLongAlt)paramAnonymousIntent.getParcelableExtra("extra_cable_cam_waypoint_location");
        MapFragment.this.updateShotMarker(m, localLatLongAlt4);
        return;
        k = paramAnonymousIntent.getIntExtra("extra_selfie_waypoint_index", -1);
      }
      while (k <= -1);
      LatLongAlt localLatLongAlt3 = (LatLongAlt)paramAnonymousIntent.getParcelableExtra("extra_selfie_waypoint_location");
      MapFragment.this.updateShotMarker(k, localLatLongAlt3);
      return;
      LatLongAlt localLatLongAlt2 = (LatLongAlt)paramAnonymousIntent.getParcelableExtra("extra_orbit_roi_location");
      MapFragment.this.updateOrbitMarker(localLatLongAlt2);
      return;
      LatLongAlt localLatLongAlt1 = (LatLongAlt)paramAnonymousIntent.getParcelableExtra("extra_follow_target_location");
      MapFragment.this.updateOrbitMarker(localLatLongAlt1);
      return;
      MapFragment.this.resetShotMarkers();
      return;
      MapFragment.this.resetShotMarkers();
      MapFragment.this.updateOrbitMarker(OrbitState.estimateOrbitRoi(MapFragment.this.getDrone()));
      return;
      MapFragment.this.toggleOfflineMapProvider(MapFragment.access$400(MapFragment.this));
      return;
      Log.v(MapFragment.TAG, "Connectivity update.");
      MapFragment.this.handler.removeCallbacks(MapFragment.this.connectivityChecker);
      MapFragment.this.handler.postDelayed(MapFragment.this.connectivityChecker, 3500L);
    }
  };
  private final Runnable connectivityChecker = new Runnable()
  {
    public void run()
    {
      MapFragment.this.checkNetworkConnectivity();
    }
  };
  private View downloadBar;
  private View downloadInstructionsContainer;
  private FloatingActionButton downloadMapButton;
  private ProgressBar downloadProgress;
  private Marker droneMarker;
  private final TypeEvaluator<Float> droneRotationEvaluator = new TypeEvaluator()
  {
    private final FloatEvaluator floatEvaluator = new FloatEvaluator();

    public Float evaluate(float paramAnonymousFloat, Float paramAnonymousFloat1, Float paramAnonymousFloat2)
    {
      float f1 = Math.abs(paramAnonymousFloat1.floatValue() - paramAnonymousFloat2.floatValue());
      float f2 = 360.0F - f1;
      if (f1 <= f2)
        return this.floatEvaluator.evaluate(paramAnonymousFloat, paramAnonymousFloat1, paramAnonymousFloat2);
      if (paramAnonymousFloat1.floatValue() < paramAnonymousFloat2.floatValue());
      for (float f3 = f2 * (-1.0F * paramAnonymousFloat); ; f3 = paramAnonymousFloat * f2)
        return Float.valueOf((f3 + (360.0F + paramAnonymousFloat1.floatValue())) % 360.0F);
    }
  };
  private final Handler handler = new Handler();
  private Marker homeMarker;
  private final AtomicBoolean isDownloadProcessStarted = new AtomicBoolean(false);
  private int leftPadding;
  private MapDownloader mapDownloader;
  private MapView mapView;
  private View mapViewBorder;
  private TileOverlay offlineTileOverlay;
  private TileOverlay onlineTileOverlay;
  private Circle orbitCircle;
  private int rightPadding;
  private Polyline shotLine;
  private final SparseArray<Marker> shotWaypointMarkers = new SparseArray(3);
  private int topPadding;
  private final OnMapReadyCallback updateMapPadding = new OnMapReadyCallback()
  {
    public void onMapReady(GoogleMap paramAnonymousGoogleMap)
    {
      paramAnonymousGoogleMap.setPadding(MapFragment.this.leftPadding, MapFragment.this.actionBarToolbarHeight + MapFragment.this.topPadding, MapFragment.this.rightPadding, MapFragment.this.bottomPadding);
    }
  };

  static
  {
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.ATTITUDE_UPDATED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.GPS_POSITION");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.STATE_CONNECTED");
    eventFilter.addAction("com.o3dr.services.android.lib.attribute.event.HOME_UPDATED");
    eventFilter.addAction("com.o3dr.solo.android.action.CABLE_CAM_WAYPOINT_SET");
    eventFilter.addAction("com.o3dr.solo.android.action.CABLE_CAM_ENDED");
    eventFilter.addAction("com.o3dr.solo.android.action.CABLE_CAM_STARTED");
    eventFilter.addAction("com.o3dr.solo.android.action.SELFIE_STARTED");
    eventFilter.addAction("com.o3dr.solo.android.action.SELFIE_ENDED");
    eventFilter.addAction("com.o3dr.solo.android.action.SELFIE_WAYPOINT_SET");
    eventFilter.addAction("com.o3dr.solo.android.action.SELFIE_SETTINGS_UPDATED");
    eventFilter.addAction("com.o3dr.solo.android.action.ORBIT_STARTED");
    eventFilter.addAction("com.o3dr.solo.android.action.ORBIT_ENDED");
    eventFilter.addAction("com.o3dr.solo.android.action_ORBIT_ROI_SET");
    eventFilter.addAction("com.o3dr.solo.android.action.FOLLOW_STARTED");
    eventFilter.addAction("com.o3dr.solo.android.action.FOLLOW_ENDED");
    eventFilter.addAction("com.o3dr.solo.android.action.FOLLOW_TARGET_SET");
    eventFilter.addAction("com.o3dr.solo.android.action.ARTOO_LINK_CONNECTED");
    eventFilter.addAction("com.o3dr.solo.android.action.ARTOO_LINK_DISCONNECTED");
    eventFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
  }

  private void cancelMapDownloadProcess()
  {
    if (this.isDownloadProcessStarted.compareAndSet(true, false))
      this.mapDownloader.cancelDownload();
    this.downloadBar.setVisibility(8);
  }

  private void checkNetworkConnectivity()
  {
    Context localContext = getContext();
    if (localContext == null)
      return;
    if ((isArtooLinkConnected()) || (!NetworkUtils.isNetworkAvailable(localContext)));
    for (boolean bool = true; ; bool = false)
    {
      toggleOfflineMapProvider(bool);
      return;
    }
  }

  private void completeMapDownloadProcess()
  {
    if (this.isDownloadProcessStarted.compareAndSet(true, false))
      Toast.makeText(getContext(), "Map area saved!", 1).show();
    this.downloadBar.setVisibility(8);
  }

  private void disableOfflineMapTileProvider()
  {
    if (this.offlineTileOverlay != null)
    {
      this.offlineTileOverlay.remove();
      this.offlineTileOverlay = null;
      Log.i(TAG, "Removing offline map overlay.");
    }
  }

  private void enableOfflineMapTileProvider(GoogleMap paramGoogleMap)
  {
    Context localContext = getContext();
    if ((localContext == null) || (paramGoogleMap == null) || (this.offlineTileOverlay != null))
      return;
    this.offlineTileOverlay = paramGoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(new OfflineTileProvider(localContext, getString(2131099972), getString(2131099971))).zIndex(-2.0F));
    Log.i(TAG, "Adding offline map overlay");
  }

  private void saveCameraPosition()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((this.mapView == null) || (localFragmentActivity == null))
      return;
    final SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(localFragmentActivity.getApplicationContext());
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        CameraPosition localCameraPosition = paramAnonymousGoogleMap.getCameraPosition();
        localSharedPreferences.edit().putFloat("pref_map_lat", (float)localCameraPosition.target.latitude).putFloat("pref_map_lng", (float)localCameraPosition.target.longitude).putFloat("pref_map_bea", localCameraPosition.bearing).putFloat("pref_map_tilt", localCameraPosition.tilt).putFloat("pref_map_zoom", localCameraPosition.zoom).apply();
      }
    });
  }

  private void setupMapboxTileProvider(GoogleMap paramGoogleMap)
  {
    if ((getContext() == null) || (paramGoogleMap == null) || (this.onlineTileOverlay != null))
      return;
    paramGoogleMap.setMapType(1);
    MapboxTileProvider localMapboxTileProvider = new MapboxTileProvider(getString(2131099972), getString(2131099971));
    this.onlineTileOverlay = paramGoogleMap.addTileOverlay(new TileOverlayOptions().tileProvider(localMapboxTileProvider).zIndex(-1.0F));
  }

  private void startMapDownloadProcess()
  {
    startMapDownloadProcess(false);
  }

  private void startMapDownloadProcess(boolean paramBoolean)
  {
    if (this.isDownloadProcessStarted.compareAndSet(false, true))
    {
      if (paramBoolean)
        break label42;
      this.downloadProgress.setVisibility(8);
      this.downloadInstructionsContainer.setVisibility(0);
    }
    while (true)
    {
      this.downloadBar.setVisibility(0);
      return;
      label42: this.downloadProgress.setVisibility(0);
      this.downloadProgress.setIndeterminate(true);
      this.downloadInstructionsContainer.setVisibility(8);
    }
  }

  private void toggleOfflineMapProvider(GoogleMap paramGoogleMap)
  {
    if (this.offlineTileOverlay == null)
    {
      enableOfflineMapTileProvider(paramGoogleMap);
      return;
    }
    disableOfflineMapTileProvider();
  }

  private void toggleOfflineMapProvider(final boolean paramBoolean)
  {
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        if (paramBoolean)
        {
          MapFragment.this.enableOfflineMapTileProvider(paramAnonymousGoogleMap);
          return;
        }
        MapFragment.this.disableOfflineMapTileProvider();
      }
    });
  }

  private void triggerMapDownload()
  {
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        int i = (int)paramAnonymousGoogleMap.getCameraPosition().zoom;
        if ((i < 16) || (i > 19))
          return;
        MapFragment.this.downloadInstructionsContainer.setVisibility(8);
        MapFragment.this.downloadProgress.setVisibility(0);
        MapFragment.this.downloadProgress.setIndeterminate(true);
        VisibleRegion localVisibleRegion = paramAnonymousGoogleMap.getProjection().getVisibleRegion();
        MapFragment.this.mapDownloader.beginDownloadingMapID(MapFragment.this.getString(2131099972), MapFragment.this.getString(2131099971), localVisibleRegion, 0, 19, true, true);
      }
    });
  }

  private void updateOrbitMarker(final LatLongAlt paramLatLongAlt)
  {
    if (paramLatLongAlt == null);
    final Gps localGps;
    do
    {
      Drone localDrone;
      do
      {
        return;
        localDrone = getDrone();
      }
      while ((localDrone == null) || (!isDroneConnected()));
      localGps = (Gps)localDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    }
    while ((localGps == null) || (!localGps.isValid()));
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        LatLng localLatLng1 = GeoTools.LatLongToLatLng(paramLatLongAlt);
        LatLng localLatLng2 = GeoTools.LatLongToLatLng(localGps.getPosition());
        Marker localMarker = (Marker)MapFragment.this.shotWaypointMarkers.get(0);
        if (localMarker == null)
        {
          MarkerOptions localMarkerOptions = new MarkerOptions().position(localLatLng1).draggable(false).flat(true).visible(true).anchor(0.5F, 0.5F).icon(BitmapDescriptorFactory.fromResource(2130837631));
          MapFragment.this.shotWaypointMarkers.put(0, paramAnonymousGoogleMap.addMarker(localMarkerOptions));
          if (MapFragment.this.shotLine != null)
            break label262;
          PolylineOptions localPolylineOptions = new PolylineOptions().color(MapFragment.this.getResources().getColor(2131427387)).add(localLatLng1).add(localLatLng2).width(6.0F).geodesic(true).zIndex(1.0F);
          MapFragment.access$2402(MapFragment.this, paramAnonymousGoogleMap.addPolyline(localPolylineOptions));
        }
        double d;
        while (true)
        {
          d = MathUtils.getDistance2D(paramLatLongAlt, localGps.getPosition());
          if (MapFragment.this.orbitCircle != null)
            break label305;
          CircleOptions localCircleOptions = new CircleOptions().zIndex(1.0F).center(localLatLng1).visible(true).strokeWidth(6.0F).strokeColor(MapFragment.this.getResources().getColor(2131427387)).radius(d);
          MapFragment.access$2502(MapFragment.this, paramAnonymousGoogleMap.addCircle(localCircleOptions));
          return;
          localMarker.setPosition(localLatLng1);
          break;
          label262: ArrayList localArrayList = new ArrayList(2);
          localArrayList.add(localLatLng1);
          localArrayList.add(localLatLng2);
          MapFragment.this.shotLine.setPoints(localArrayList);
        }
        label305: MapFragment.this.orbitCircle.setCenter(localLatLng1);
        MapFragment.this.orbitCircle.setRadius(d);
      }
    });
  }

  private void updateShotMarker(final int paramInt, final LatLongAlt paramLatLongAlt)
  {
    if (paramLatLongAlt == null)
      return;
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        LatLng localLatLng = GeoTools.LatLongToLatLng(paramLatLongAlt);
        if (MapFragment.this.shotLine == null)
        {
          PolylineOptions localPolylineOptions = new PolylineOptions().color(MapFragment.this.getResources().getColor(2131427386)).add(localLatLng).width(6.0F).geodesic(true).zIndex(1.0F);
          MapFragment.access$2402(MapFragment.this, paramAnonymousGoogleMap.addPolyline(localPolylineOptions));
        }
        Marker localMarker;
        while (true)
        {
          localMarker = (Marker)MapFragment.this.shotWaypointMarkers.get(paramInt);
          if (localMarker != null)
            break;
          MarkerOptions localMarkerOptions = new MarkerOptions().position(localLatLng).draggable(false).flat(true).visible(true).anchor(0.5F, 0.5F).icon(BitmapDescriptorFactory.fromResource(2130837631));
          MapFragment.this.shotWaypointMarkers.put(paramInt, paramAnonymousGoogleMap.addMarker(localMarkerOptions));
          return;
          List localList = MapFragment.this.shotLine.getPoints();
          localList.add(localLatLng);
          MapFragment.this.shotLine.setPoints(localList);
        }
        localMarker.setPosition(localLatLng);
      }
    });
  }

  public void completionOfOfflineDatabaseMap()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null)
      localFragmentActivity.runOnUiThread(new Runnable()
      {
        public void run()
        {
          MapFragment.this.completeMapDownloadProcess();
        }
      });
  }

  public void httpStatusError(Throwable paramThrowable)
  {
  }

  public void initialCountOfFiles(final int paramInt)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null)
      localFragmentActivity.runOnUiThread(new Runnable()
      {
        public void run()
        {
          MapFragment.this.downloadProgress.setIndeterminate(false);
          MapFragment.this.downloadProgress.setMax(paramInt);
          MapFragment.this.downloadProgress.setProgress(0);
        }
      });
  }

  public void loadCameraPosition()
  {
    FragmentActivity localFragmentActivity = getActivity();
    if ((this.mapView == null) || (localFragmentActivity == null))
      return;
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(localFragmentActivity.getApplicationContext());
    final CameraPosition.Builder localBuilder = new CameraPosition.Builder();
    localBuilder.bearing(localSharedPreferences.getFloat("pref_map_bea", 0.0F));
    localBuilder.tilt(localSharedPreferences.getFloat("pref_map_tilt", 0.0F));
    localBuilder.zoom(localSharedPreferences.getFloat("pref_map_zoom", 19.0F));
    localBuilder.target(new LatLng(localSharedPreferences.getFloat("pref_map_lat", 37.857552F), localSharedPreferences.getFloat("pref_map_lng", -122.29277F)));
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        paramAnonymousGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(localBuilder.build()));
      }
    });
  }

  public void networkConnectivityError(Throwable paramThrowable)
  {
  }

  public void onAlertHidden(AlertType paramAlertType)
  {
    this.downloadMapButton.setEnabled(true);
    if (this.isDownloadProcessStarted.get())
      this.downloadBar.setVisibility(0);
  }

  public void onAlertShown(AlertType paramAlertType)
  {
    this.downloadMapButton.setEnabled(false);
    if (this.isDownloadProcessStarted.get())
      this.downloadBar.setVisibility(4);
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance(true);
  }

  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    return paramLayoutInflater.inflate(2130903092, paramViewGroup, false);
  }

  public void onDestroyView()
  {
    super.onDestroyView();
    this.mapView.onDestroy();
  }

  protected void onDroneAttached(Drone paramDrone)
  {
    super.onDroneAttached(paramDrone);
    if (this.droneMarker == null);
    for (boolean bool = true; ; bool = false)
    {
      updateDroneOnMap(paramDrone, bool);
      checkNetworkConnectivity();
      return;
    }
  }

  public void onLowMemory()
  {
    super.onLowMemory();
    this.mapView.onLowMemory();
  }

  public void onPanelAnchored(View paramView)
  {
    if (this.mapViewBorder != null)
      this.mapViewBorder.setVisibility(0);
  }

  public void onPanelCollapsed(View paramView)
  {
    if (this.mapViewBorder != null)
      this.mapViewBorder.setVisibility(0);
  }

  public void onPanelExpanded(View paramView)
  {
    if (this.mapViewBorder != null)
      this.mapViewBorder.setVisibility(8);
  }

  public void onPanelHidden(View paramView)
  {
    if (this.mapViewBorder != null)
      this.mapViewBorder.setVisibility(0);
  }

  public void onPanelSlide(View paramView, float paramFloat)
  {
  }

  public void onPause()
  {
    super.onPause();
    saveCameraPosition();
    this.mapView.onPause();
  }

  public void onResume()
  {
    super.onResume();
    this.mapView.onResume();
    loadCameraPosition();
  }

  public void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    this.mapView.onSaveInstanceState(paramBundle);
  }

  public void onShotEnded(int paramInt)
  {
    this.downloadMapButton.setEnabled(true);
  }

  public void onShotPaused(int paramInt)
  {
  }

  public void onShotResumed(int paramInt)
  {
  }

  public void onShotSetupCompleted(int paramInt)
  {
  }

  public void onShotStarted(int paramInt)
  {
    switch (paramInt)
    {
    default:
      this.downloadMapButton.setEnabled(false);
      if (this.isDownloadProcessStarted.get())
        cancelMapDownloadProcess();
      return;
    case -1:
    }
    this.downloadMapButton.setEnabled(true);
  }

  public void onStart()
  {
    if (this.mapDownloader.getState() == MapDownloader.MBXOfflineMapDownloaderState.MBXOfflineMapDownloaderStateRunning)
      startMapDownloadProcess(true);
    while (true)
    {
      this.mapDownloader.addMapDownloaderListener(this);
      getBroadcastManager().registerReceiver(this.broadcastReceiver, eventFilter);
      getActivity().registerReceiver(this.broadcastReceiver, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
      super.onStart();
      return;
      cancelMapDownloadProcess();
    }
  }

  public void onStop()
  {
    super.onStop();
    this.mapDownloader.removeMapDownloaderListener(this);
    getBroadcastManager().unregisterReceiver(this.broadcastReceiver);
    getActivity().unregisterReceiver(this.broadcastReceiver);
  }

  public void onViewCreated(View paramView, Bundle paramBundle)
  {
    super.onViewCreated(paramView, paramBundle);
    this.actionBarToolbarHeight = ((int)getResources().getDimension(2131165264));
    this.mapViewBorder = paramView.findViewById(2131493104);
    this.downloadMapButton = ((FloatingActionButton)paramView.findViewById(2131493106));
    this.downloadMapButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MapFragment.this.startMapDownloadProcess();
      }
    });
    this.downloadBar = paramView.findViewById(2131493107);
    this.downloadInstructionsContainer = paramView.findViewById(2131493110);
    final View localView1 = paramView.findViewById(2131493111);
    localView1.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MapFragment.this.triggerMapDownload();
      }
    });
    this.downloadProgress = ((ProgressBar)paramView.findViewById(2131493113));
    paramView.findViewById(2131493109).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        MapFragment.this.cancelMapDownloadProcess();
      }
    });
    final View localView2 = paramView.findViewById(2131493112);
    final View localView3 = paramView.findViewById(2131493108);
    MapsInitializer.initialize(getActivity().getApplicationContext());
    this.mapView = ((MapView)paramView.findViewById(2131493105));
    this.mapView.onCreate(paramBundle);
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        paramAnonymousGoogleMap.clear();
        paramAnonymousGoogleMap.setMyLocationEnabled(false);
        paramAnonymousGoogleMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener()
        {
          public void onCameraChange(CameraPosition paramAnonymous2CameraPosition)
          {
            float f = paramAnonymous2CameraPosition.zoom;
            Log.d(MapFragment.TAG, "Zoom level: " + paramAnonymous2CameraPosition.zoom);
            if (f < 16.0F)
            {
              MapFragment.8.this.val$downloadWarning.setVisibility(0);
              MapFragment.8.this.val$downloadInstructions.setVisibility(8);
              MapFragment.8.this.val$downloadBarIcon.setBackgroundResource(2131427397);
              return;
            }
            MapFragment.8.this.val$downloadWarning.setVisibility(8);
            MapFragment.8.this.val$downloadInstructions.setVisibility(0);
            MapFragment.8.this.val$downloadBarIcon.setBackgroundResource(2131427399);
          }
        });
        UiSettings localUiSettings = paramAnonymousGoogleMap.getUiSettings();
        localUiSettings.setMyLocationButtonEnabled(false);
        localUiSettings.setCompassEnabled(false);
        localUiSettings.setTiltGesturesEnabled(false);
        localUiSettings.setZoomControlsEnabled(false);
        MapFragment.this.setupMapboxTileProvider(paramAnonymousGoogleMap);
        paramAnonymousGoogleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
        {
          public void onMapLongClick(LatLng paramAnonymous2LatLng)
          {
            Log.d(MapFragment.TAG, "Received map long click.");
            ShotState localShotState = MapFragment.this.getSoloLinkManager().getShotManager().getCurrentShotState();
            if ((localShotState instanceof OrbitState))
              ((OrbitState)localShotState).recordLocation(GeoTools.LatLngToLatLong(paramAnonymous2LatLng));
          }
        });
      }
    });
    this.mapDownloader = getApplication().getMapDownloader();
  }

  public void progressUpdate(final int paramInt1, final int paramInt2)
  {
    FragmentActivity localFragmentActivity = getActivity();
    if (localFragmentActivity != null)
      localFragmentActivity.runOnUiThread(new Runnable()
      {
        public void run()
        {
          MapFragment.this.downloadProgress.setIndeterminate(false);
          MapFragment.this.downloadProgress.setMax(paramInt2);
          MapFragment.this.downloadProgress.setProgress(paramInt1);
        }
      });
  }

  public void resetShotMarkers()
  {
    int i = this.shotWaypointMarkers.size();
    for (int j = 0; j < i; j++)
      ((Marker)this.shotWaypointMarkers.valueAt(j)).remove();
    this.shotWaypointMarkers.clear();
    if (this.shotLine != null)
    {
      this.shotLine.remove();
      this.shotLine = null;
    }
    if (this.orbitCircle != null)
    {
      this.orbitCircle.remove();
      this.orbitCircle = null;
    }
  }

  public void setMapPadding(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.leftPadding = paramInt1;
    this.topPadding = paramInt2;
    this.rightPadding = paramInt3;
    this.bottomPadding = paramInt4;
    if (this.mapView != null)
      this.mapView.getMapAsync(this.updateMapPadding);
  }

  public void sqlLiteError(Throwable paramThrowable)
  {
  }

  public void stateChanged(MapDownloader.MBXOfflineMapDownloaderState paramMBXOfflineMapDownloaderState)
  {
  }

  public void updateDroneOnMap(Drone paramDrone, final boolean paramBoolean)
  {
    if ((this.mapView == null) || (paramDrone == null) || (!isDroneConnected()));
    Gps localGps;
    do
    {
      return;
      localGps = (Gps)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    }
    while ((localGps == null) || (!localGps.isValid()));
    final LatLng localLatLng = GeoTools.LatLongToLatLng(localGps.getPosition());
    Attitude localAttitude = (Attitude)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.ATTITUDE");
    if (localAttitude == null);
    for (final float f = 0.0F; ; f = (360.0F + (float)localAttitude.getYaw()) % 360.0F)
    {
      this.mapView.getMapAsync(new OnMapReadyCallback()
      {
        public void onMapReady(GoogleMap paramAnonymousGoogleMap)
        {
          if (MapFragment.this.droneMarker == null)
          {
            MarkerOptions localMarkerOptions = new MarkerOptions().position(localLatLng).rotation(f).draggable(false).flat(true).visible(true).anchor(0.5F, 0.5F).icon(BitmapDescriptorFactory.fromResource(2130837871));
            MapFragment.access$102(MapFragment.this, paramAnonymousGoogleMap.addMarker(localMarkerOptions));
            if (paramBoolean)
              paramAnonymousGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localLatLng, 19.0F));
          }
          do
          {
            return;
            MapFragment.this.droneMarker.setPosition(localLatLng);
            if (Math.abs(MapFragment.this.droneMarker.getRotation() - f) > 15.0F)
            {
              Marker localMarker = MapFragment.this.droneMarker;
              float[] arrayOfFloat = new float[1];
              arrayOfFloat[0] = f;
              ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(localMarker, "rotation", arrayOfFloat);
              localObjectAnimator.setAutoCancel(true);
              localObjectAnimator.setEvaluator(MapFragment.this.droneRotationEvaluator);
              localObjectAnimator.setDuration(500L);
              localObjectAnimator.start();
            }
          }
          while (!paramBoolean);
          paramAnonymousGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(localLatLng, 19.0F));
        }
      });
      return;
    }
  }

  public void updateHomeLocation(Drone paramDrone)
  {
    if ((this.mapView == null) || (paramDrone == null) || (!isDroneConnected()));
    final Home localHome;
    do
    {
      return;
      localHome = (Home)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.HOME");
    }
    while (!localHome.isValid());
    this.mapView.getMapAsync(new OnMapReadyCallback()
    {
      public void onMapReady(GoogleMap paramAnonymousGoogleMap)
      {
        LatLng localLatLng = GeoTools.LatLongToLatLng(localHome.getCoordinate());
        if (MapFragment.this.homeMarker == null)
        {
          MarkerOptions localMarkerOptions = new MarkerOptions().position(localLatLng).draggable(false).flat(true).visible(true).anchor(0.5F, 0.5F).icon(BitmapDescriptorFactory.fromResource(2130837786));
          MapFragment.access$2602(MapFragment.this, paramAnonymousGoogleMap.addMarker(localMarkerOptions));
          return;
        }
        MapFragment.this.homeMarker.setPosition(localLatLng);
      }
    });
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.MapFragment
 * JD-Core Version:    0.6.2
 */