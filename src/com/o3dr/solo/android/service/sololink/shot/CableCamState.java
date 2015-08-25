package com.o3dr.solo.android.service.sololink.shot;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloCableCamOptions;
import com.o3dr.solo.android.service.sololink.tlv.SoloCableCamWaypoint;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageRecordPosition;
import com.o3dr.solo.android.util.AppPreferences;

public class CableCamState extends ShotState<SoloCableCamOptions>
{
  public static final String ACTION_CABLE_CAM_ENDED = "com.o3dr.solo.android.action.CABLE_CAM_ENDED";
  public static final String ACTION_CABLE_CAM_STARTED = "com.o3dr.solo.android.action.CABLE_CAM_STARTED";
  public static final String ACTION_CABLE_CAM_STATUS = "com.o3dr.solo.android.action.CABLE_CAM_STATUS";
  public static final String ACTION_CABLE_CAM_WAYPOINT_SET = "com.o3dr.solo.android.action.CABLE_CAM_WAYPOINT_SET";
  public static final String EXTRA_CABLE_CAM_STATUS = "extra_cable_cam_status";
  public static final String EXTRA_CABLE_CAM_WAYPOINT_INDEX = "extra_cable_cam_waypoint_index";
  public static final String EXTRA_CABLE_CAM_WAYPOINT_LOCATION = "extra_cable_cam_waypoint_location";
  private static final String TAG = CableCamState.class.getSimpleName();
  private int currentState = 0;
  private SoloCableCamWaypoint endWaypoint;
  private final LocalBroadcastManager lbm;
  private final SoloMessageRecordPosition scratchRecordPosition = new SoloMessageRecordPosition();
  private final SoloLinkManager soloLinkMgr;
  private SoloCableCamWaypoint startWaypoint;
  private final LatLongAlt[] waypointsLoc = new LatLongAlt[2];

  CableCamState(AppPreferences paramAppPreferences, LocalBroadcastManager paramLocalBroadcastManager, SoloLinkManager paramSoloLinkManager)
  {
    super(paramAppPreferences);
    this.lbm = paramLocalBroadcastManager;
    this.soloLinkMgr = paramSoloLinkManager;
  }

  private boolean areWaypointSet()
  {
    return (this.startWaypoint != null) && (this.endWaypoint != null);
  }

  public int getState()
  {
    return this.currentState;
  }

  public LatLongAlt[] getWaypoints()
  {
    return this.waypointsLoc;
  }

  public void onOptionsUpdated(SoloCableCamOptions paramSoloCableCamOptions)
  {
    if ((this.currentState == 0) || (!areWaypointSet()))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    if (paramSoloCableCamOptions.isCamInterpolationOn() != this.appPrefs.isCableCamInterpolationOn())
    {
      paramSoloCableCamOptions.setCamInterpolation(this.appPrefs.isCableCamInterpolationOn());
      setOptions(paramSoloCableCamOptions);
      return;
    }
    this.shotOptions = paramSoloCableCamOptions;
    Log.d(TAG, "Updating cable cam settings.");
    float f = paramSoloCableCamOptions.getCruiseSpeed();
    if (f > 0.0F)
      this.currentState = 3;
    while (true)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.CABLE_CAM_STATUS").putExtra("extra_cable_cam_status", this.currentState));
      return;
      if (f < 0.0F)
        this.currentState = 4;
      else
        this.currentState = 2;
    }
  }

  public void recordLocation()
  {
    this.soloLinkMgr.sendTLVPacket(this.scratchRecordPosition);
  }

  public void reset()
  {
    if (this.currentState != 0);
    for (int i = 1; ; i = 0)
    {
      this.currentState = 0;
      this.shotOptions = null;
      this.startWaypoint = null;
      this.endWaypoint = null;
      for (int j = 0; j < this.waypointsLoc.length; j++)
        this.waypointsLoc[j] = null;
    }
    if (i != 0)
    {
      Log.d(TAG, "Ending cable cam.");
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.CABLE_CAM_ENDED"));
    }
  }

  public void setActiveState(int paramInt)
  {
    if (this.shotOptions == null)
      return;
    int i = this.appPrefs.getCableCamCruiseSpeed();
    switch (paramInt)
    {
    default:
      ((SoloCableCamOptions)this.shotOptions).setCruiseSpeed(0.0F);
    case 4:
    case 3:
    }
    while (true)
    {
      setOptions((SoloCableCamOptions)this.shotOptions);
      return;
      i = -i;
      ((SoloCableCamOptions)this.shotOptions).setCruiseSpeed(i);
    }
  }

  public void setOptions(SoloCableCamOptions paramSoloCableCamOptions)
  {
    if ((this.currentState == 0) || (!areWaypointSet()))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    this.soloLinkMgr.sendTLVPacket(paramSoloCableCamOptions);
    onOptionsUpdated(paramSoloCableCamOptions);
  }

  void setWaypoint(SoloCableCamWaypoint paramSoloCableCamWaypoint)
  {
    if (this.currentState != 1)
    {
      Log.w(TAG, "Invalid state for waypoint initialization.");
      return;
    }
    int i = -1;
    if (this.startWaypoint == null)
    {
      this.startWaypoint = paramSoloCableCamWaypoint;
      this.waypointsLoc[0] = paramSoloCableCamWaypoint.getCoordinate();
      i = 0;
    }
    while (i > -1)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.CABLE_CAM_WAYPOINT_SET").putExtra("extra_cable_cam_waypoint_index", i).putExtra("extra_cable_cam_waypoint_location", paramSoloCableCamWaypoint.getCoordinate()));
      return;
      if (this.endWaypoint == null)
      {
        this.endWaypoint = paramSoloCableCamWaypoint;
        this.waypointsLoc[1] = paramSoloCableCamWaypoint.getCoordinate();
        i = 1;
      }
    }
    Log.w(TAG, "Both waypoints are already set.");
  }

  public void start()
  {
    this.currentState = 1;
    Log.d(TAG, "Initializing cable cam.");
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.CABLE_CAM_STARTED"));
  }

  public void updateCamInterpolation(boolean paramBoolean)
  {
    if ((this.shotOptions == null) || (((SoloCableCamOptions)this.shotOptions).isCamInterpolationOn() == paramBoolean))
      return;
    ((SoloCableCamOptions)this.shotOptions).setCamInterpolation(paramBoolean);
    setOptions((SoloCableCamOptions)this.shotOptions);
  }

  public void updateYawDirection(boolean paramBoolean)
  {
    if ((this.shotOptions == null) || (((SoloCableCamOptions)this.shotOptions).isYawDirectionClockWise() == paramBoolean))
      return;
    ((SoloCableCamOptions)this.shotOptions).setYawDirection(paramBoolean);
    setOptions((SoloCableCamOptions)this.shotOptions);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.shot.CableCamState
 * JD-Core Version:    0.6.2
 */