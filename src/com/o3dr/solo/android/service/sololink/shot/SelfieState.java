package com.o3dr.solo.android.service.sololink.shot;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageLocation;
import com.o3dr.solo.android.service.sololink.tlv.SoloShotOptions;
import com.o3dr.solo.android.util.AppPreferences;

public class SelfieState extends ShotState<SoloShotOptions>
{
  public static final String ACTION_SELFIE_ENDED = "com.o3dr.solo.android.action.SELFIE_ENDED";
  public static final String ACTION_SELFIE_STARTED = "com.o3dr.solo.android.action.SELFIE_STARTED";
  public static final String ACTION_SELFIE_STATUS = "com.o3dr.solo.android.action.SELFIE_STATUS";
  public static final String ACTION_SELFIE_WAYPOINT_SET = "com.o3dr.solo.android.action.SELFIE_WAYPOINT_SET";
  public static final int DEFAULT_SELFIE_ALTITUDE_UP = 25;
  public static final int DEFAULT_SELFIE_DISTANCE_OUT = 50;
  public static final String EXTRA_SELFIE_STATUS = "extra_selfie_status";
  public static final String EXTRA_SELFIE_WAYPOINT_INDEX = "extra_selfie_waypoint_index";
  public static final String EXTRA_SELFIE_WAYPOINT_LOCATION = "extra_selfie_waypoint_location";
  public static final int MAX_SELFIE_ALTITUDE_UP = 50;
  public static final int MAX_SELFIE_DISTANCE_OUT = 100;
  public static final int MIN_SELFIE_ALTITUDE_UP = 1;
  public static final int MIN_SELFIE_DISTANCE_OUT = 20;
  public static final int ROI_ALTITUDE = 1;
  public static final int ROI_DISTANCE_FROM_START = -8;
  private static final String TAG = SelfieState.class.getSimpleName();
  private int currentState = 0;
  private SoloMessageLocation farWaypoint;
  private final LocalBroadcastManager lbm;
  private SoloMessageLocation nearWaypoint;
  private SoloMessageLocation roiWaypoint;
  private boolean sentSelfieWaypoints = false;
  private final SoloLinkManager soloLinkMgr;
  private final LatLongAlt[] waypointsLoc = new LatLongAlt[3];

  SelfieState(AppPreferences paramAppPreferences, LocalBroadcastManager paramLocalBroadcastManager, SoloLinkManager paramSoloLinkManager)
  {
    super(paramAppPreferences);
    this.lbm = paramLocalBroadcastManager;
    this.soloLinkMgr = paramSoloLinkManager;
  }

  private boolean areWaypointsSet()
  {
    return (this.nearWaypoint != null) && (this.farWaypoint != null) && (this.roiWaypoint != null);
  }

  private void sendSelfieWaypoints()
  {
    if ((!areWaypointsSet()) || (this.sentSelfieWaypoints))
      return;
    this.soloLinkMgr.sendTLVPacket(this.nearWaypoint);
    this.soloLinkMgr.sendTLVPacket(this.farWaypoint);
    this.soloLinkMgr.sendTLVPacket(this.roiWaypoint);
    this.sentSelfieWaypoints = true;
  }

  public int getState()
  {
    return this.currentState;
  }

  public LatLongAlt[] getWaypoints()
  {
    return this.waypointsLoc;
  }

  public boolean isActive()
  {
    return (super.isActive()) && (this.sentSelfieWaypoints);
  }

  public void onOptionsUpdated(SoloShotOptions paramSoloShotOptions)
  {
    if ((this.currentState == 0) || (!areWaypointsSet()))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    this.shotOptions = paramSoloShotOptions;
    Log.d(TAG, "Updating selfie settings.");
    float f = paramSoloShotOptions.getCruiseSpeed();
    if (f > 0.0F)
      this.currentState = 3;
    while (true)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SELFIE_STATUS").putExtra("extra_selfie_status", this.currentState));
      return;
      if (f < 0.0F)
        this.currentState = 4;
      else
        this.currentState = 2;
    }
  }

  public void reset()
  {
    if (this.currentState != 0);
    for (int i = 1; ; i = 0)
    {
      this.currentState = 0;
      this.sentSelfieWaypoints = false;
      this.shotOptions = null;
      this.roiWaypoint = null;
      this.farWaypoint = null;
      this.nearWaypoint = null;
      for (int j = 0; j < this.waypointsLoc.length; j++)
        this.waypointsLoc[j] = null;
    }
    if (i != 0)
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SELFIE_ENDED"));
    Log.d(TAG, "Resetting selfie shot.");
  }

  public void setActiveState(int paramInt)
  {
    if (this.shotOptions == null)
      return;
    if (!this.sentSelfieWaypoints)
      sendSelfieWaypoints();
    int i = this.appPrefs.getSelfieCruiseSpeed();
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
    if ((this.currentState == 0) || (!areWaypointsSet()))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    this.soloLinkMgr.sendTLVPacket(paramSoloShotOptions);
    onOptionsUpdated(paramSoloShotOptions);
  }

  public void setSelfieWaypoints(LatLongAlt paramLatLongAlt1, LatLongAlt paramLatLongAlt2, LatLongAlt paramLatLongAlt3)
  {
    if (this.sentSelfieWaypoints)
      return;
    this.nearWaypoint = new SoloMessageLocation(paramLatLongAlt1);
    this.waypointsLoc[0] = paramLatLongAlt1;
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SELFIE_WAYPOINT_SET").putExtra("extra_selfie_waypoint_index", 0).putExtra("extra_selfie_waypoint_location", paramLatLongAlt1));
    this.farWaypoint = new SoloMessageLocation(paramLatLongAlt2);
    this.waypointsLoc[1] = paramLatLongAlt2;
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SELFIE_WAYPOINT_SET").putExtra("extra_selfie_waypoint_index", 1).putExtra("extra_selfie_waypoint_location", paramLatLongAlt2));
    this.roiWaypoint = new SoloMessageLocation(paramLatLongAlt3);
    this.waypointsLoc[2] = paramLatLongAlt3;
    onOptionsUpdated(new SoloShotOptions());
  }

  public void start()
  {
    this.currentState = 1;
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SELFIE_STARTED"));
    Log.d(TAG, "Starting selfie shot.");
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.shot.SelfieState
 * JD-Core Version:    0.6.2
 */