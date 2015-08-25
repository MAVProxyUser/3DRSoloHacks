package com.o3dr.solo.android.service.sololink.shot;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.o3dr.android.client.Drone;
import com.o3dr.services.android.lib.coordinate.LatLong;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.services.android.lib.drone.property.Altitude;
import com.o3dr.services.android.lib.drone.property.Attitude;
import com.o3dr.services.android.lib.drone.property.Gps;
import com.o3dr.services.android.lib.util.MathUtils;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageLocation;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageRecordPosition;
import com.o3dr.solo.android.service.sololink.tlv.SoloShotOptions;
import com.o3dr.solo.android.util.AppPreferences;

public class OrbitState extends ShotState<SoloShotOptions>
{
  public static final String ACTION_ORBIT_ENDED = "com.o3dr.solo.android.action.ORBIT_ENDED";
  public static final String ACTION_ORBIT_ROI_SET = "com.o3dr.solo.android.action_ORBIT_ROI_SET";
  public static final String ACTION_ORBIT_STARTED = "com.o3dr.solo.android.action.ORBIT_STARTED";
  public static final String ACTION_ORBIT_STATUS = "com.o3dr.solo.android.action.ORBIT_STATUS";
  public static final String EXTRA_ORBIT_ROI_LOCATION = "extra_orbit_roi_location";
  public static final String EXTRA_ORBIT_STATUS = "extra_orbit_status";
  private static final float ORBIT_ROI_DEFAULT_DISTANCE = 20.0F;
  private static final float SHALLOW_ANGLE_THRESHOLD = -60.0F;
  private static final String TAG = OrbitState.class.getSimpleName();
  private int currentState = 0;
  private final LocalBroadcastManager lbm;
  private SoloMessageLocation orbitRoi;
  private final SoloMessageRecordPosition scratchRecordPosition = new SoloMessageRecordPosition();
  private final SoloLinkManager soloLinkMgr;
  private final LatLongAlt[] waypointsLoc = new LatLongAlt[1];

  public OrbitState(AppPreferences paramAppPreferences, LocalBroadcastManager paramLocalBroadcastManager, SoloLinkManager paramSoloLinkManager)
  {
    super(paramAppPreferences);
    this.lbm = paramLocalBroadcastManager;
    this.soloLinkMgr = paramSoloLinkManager;
  }

  private static LatLongAlt calculateCurrentROI(LatLongAlt paramLatLongAlt, double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble2 == 0.0D)
      return new LatLongAlt(MathUtils.newCoordFromBearingAndDistance(paramLatLongAlt, paramDouble1, paramDouble3), 0.0D);
    double d = 90.0D - paramDouble2;
    return new LatLongAlt(MathUtils.newCoordFromBearingAndDistance(paramLatLongAlt, paramDouble1, paramLatLongAlt.getAltitude() * Math.tan(Math.toRadians(d))), 0.0D);
  }

  public static LatLongAlt estimateOrbitRoi(Drone paramDrone)
  {
    if (paramDrone == null);
    Gps localGps;
    do
    {
      return null;
      localGps = (Gps)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.GPS");
    }
    while ((localGps == null) || (!localGps.isValid()));
    ((Altitude)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.ALTITUDE"));
    Attitude localAttitude = (Attitude)paramDrone.getAttribute("com.o3dr.services.android.lib.attribute.ATTITUDE");
    return new LatLongAlt(MathUtils.newCoordFromBearingAndDistance(localGps.getPosition(), localAttitude.getYaw(), 20.0D), 0.0D);
  }

  private boolean isROISet()
  {
    return this.orbitRoi != null;
  }

  public int getState()
  {
    return this.currentState;
  }

  public LatLongAlt[] getWaypoints()
  {
    return this.waypointsLoc;
  }

  public void onOptionsUpdated(SoloShotOptions paramSoloShotOptions)
  {
    if ((this.currentState == 0) || (!isROISet()))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    this.shotOptions = paramSoloShotOptions;
    Log.d(TAG, "Updating orbit settings.");
    float f = paramSoloShotOptions.getCruiseSpeed();
    if (f > 0.0F)
      this.currentState = 3;
    while (true)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ORBIT_STATUS").putExtra("extra_orbit_status", this.currentState));
      return;
      if (f < 0.0F)
        this.currentState = 4;
      else
        this.currentState = 2;
    }
  }

  public void onOrbitROIUpdated(LatLongAlt paramLatLongAlt)
  {
    onOrbitROIUpdated(new SoloMessageLocation(paramLatLongAlt));
  }

  public void onOrbitROIUpdated(SoloMessageLocation paramSoloMessageLocation)
  {
    Log.d(TAG, "Received orbit roi: " + paramSoloMessageLocation.getCoordinate().toString());
    this.orbitRoi = paramSoloMessageLocation;
    this.waypointsLoc[0] = this.orbitRoi.getCoordinate();
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action_ORBIT_ROI_SET").putExtra("extra_orbit_roi_location", this.orbitRoi.getCoordinate()));
    onOptionsUpdated(new SoloShotOptions());
  }

  public void recordLocation()
  {
    this.soloLinkMgr.sendTLVPacket(this.scratchRecordPosition);
  }

  public void recordLocation(LatLong paramLatLong)
  {
    recordLocation(new LatLongAlt(paramLatLong.getLatitude(), paramLatLong.getLongitude(), 0.0D));
  }

  public void recordLocation(LatLongAlt paramLatLongAlt)
  {
    SoloMessageLocation localSoloMessageLocation = new SoloMessageLocation(paramLatLongAlt);
    this.soloLinkMgr.sendTLVPacket(localSoloMessageLocation);
    onOrbitROIUpdated(localSoloMessageLocation);
  }

  public void reset()
  {
    if (this.currentState != 0);
    for (int i = 1; ; i = 0)
    {
      this.currentState = 0;
      this.shotOptions = null;
      this.orbitRoi = null;
      this.waypointsLoc[0] = null;
      if (i != 0)
        this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ORBIT_ENDED"));
      Log.d(TAG, "Resetting orbit shot.");
      return;
    }
  }

  public void setActiveState(int paramInt)
  {
    if (this.shotOptions == null)
      return;
    int i = this.appPrefs.getOrbitCruiseSpeed();
    switch (paramInt)
    {
    default:
      this.shotOptions.setCruiseSpeed(0.0F);
    case 4:
    case 3:
    }
    while (true)
    {
      setOptions(this.shotOptions);
      return;
      i = -i;
      this.shotOptions.setCruiseSpeed(i);
    }
  }

  public void setOptions(SoloShotOptions paramSoloShotOptions)
  {
    if ((this.currentState == 0) || (!isROISet()))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    this.soloLinkMgr.sendTLVPacket(paramSoloShotOptions);
    onOptionsUpdated(paramSoloShotOptions);
  }

  public void start()
  {
    this.currentState = 1;
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.ORBIT_STARTED"));
    Log.d(TAG, "Starting orbit shot.");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.shot.OrbitState
 * JD-Core Version:    0.6.2
 */