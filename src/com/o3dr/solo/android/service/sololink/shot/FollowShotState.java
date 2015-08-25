package com.o3dr.solo.android.service.sololink.shot;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloFollowOptions;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageLocation;
import com.o3dr.solo.android.util.AppPreferences;

public class FollowShotState extends ShotState<SoloFollowOptions>
{
  public static final String ACTION_FOLLOW_ENDED = "com.o3dr.solo.android.action.FOLLOW_ENDED";
  public static final String ACTION_FOLLOW_LOOK_AT = "com.o3dr.solo.android.action.FOLLOW_LOOK_AT";
  public static final String ACTION_FOLLOW_STARTED = "com.o3dr.solo.android.action.FOLLOW_STARTED";
  public static final String ACTION_FOLLOW_STATUS = "com.o3dr.solo.android.action.FOLLOW_STATUS";
  public static final String ACTION_FOLLOW_TARGET_SET = "com.o3dr.solo.android.action.FOLLOW_TARGET_SET";
  public static final String EXTRA_FOLLOW_STATUS = "extra_orbit_status";
  public static final String EXTRA_FOLLOW_TARGET_LOCATION = "extra_follow_target_location";
  public static final String EXTRA_LOOK_AT_ENABLED = "extra_look_at_enabled";
  private static final String TAG = FollowShotState.class.getSimpleName();
  private int currentState = 0;
  private SoloMessageLocation followTarget;
  private boolean isLookAtEnabled;
  private final LocalBroadcastManager lbm;
  private final SoloLinkManager soloLinkMgr;
  private final LatLongAlt[] waypointsLoc = new LatLongAlt[1];

  public FollowShotState(AppPreferences paramAppPreferences, LocalBroadcastManager paramLocalBroadcastManager, SoloLinkManager paramSoloLinkManager)
  {
    super(paramAppPreferences);
    this.lbm = paramLocalBroadcastManager;
    this.soloLinkMgr = paramSoloLinkManager;
  }

  public int getState()
  {
    return this.currentState;
  }

  public LatLongAlt[] getWaypoints()
  {
    return this.waypointsLoc;
  }

  public boolean isLookAtEnabled()
  {
    return this.isLookAtEnabled;
  }

  public void onOptionsUpdated(SoloFollowOptions paramSoloFollowOptions)
  {
    if ((this.currentState == 0) || (this.followTarget == null))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    this.shotOptions = paramSoloFollowOptions;
    Log.d(TAG, "Updating follow settings.");
    float f = paramSoloFollowOptions.getCruiseSpeed();
    if (f > 0.0F)
      this.currentState = 3;
    while (true)
    {
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.FOLLOW_STATUS").putExtra("extra_orbit_status", this.currentState));
      boolean bool = paramSoloFollowOptions.isLookAt();
      if (this.isLookAtEnabled == bool)
        break;
      this.isLookAtEnabled = bool;
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.FOLLOW_LOOK_AT").putExtra("extra_look_at_enabled", bool));
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
      this.shotOptions = null;
      this.followTarget = null;
      this.waypointsLoc[0] = null;
      if (i != 0)
        this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.FOLLOW_ENDED"));
      Log.d(TAG, "Resetting follow shot.");
      this.soloLinkMgr.disableFollowDataConnection();
      return;
    }
  }

  public void setActiveState(int paramInt)
  {
    if (this.shotOptions == null)
      return;
    int i = this.appPrefs.getFollowCruiseSpeed();
    switch (paramInt)
    {
    default:
      ((SoloFollowOptions)this.shotOptions).setCruiseSpeed(0.0F);
    case 4:
    case 3:
    }
    while (true)
    {
      setOptions((SoloFollowOptions)this.shotOptions);
      return;
      i = -i;
      ((SoloFollowOptions)this.shotOptions).setCruiseSpeed(i);
    }
  }

  public void setFollowTarget(LatLongAlt paramLatLongAlt)
  {
    setFollowTarget(new SoloMessageLocation(paramLatLongAlt));
  }

  public void setFollowTarget(SoloMessageLocation paramSoloMessageLocation)
  {
    try
    {
      this.soloLinkMgr.sendTLVPacket(paramSoloMessageLocation, true);
      this.followTarget = paramSoloMessageLocation;
      this.waypointsLoc[0] = paramSoloMessageLocation.getCoordinate();
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.FOLLOW_TARGET_SET").putExtra("extra_follow_target_location", this.followTarget.getCoordinate()));
      if (this.shotOptions == null)
        onOptionsUpdated(new SoloFollowOptions());
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      Log.e(TAG, "Error while setting new follow target location.", localIllegalStateException);
    }
  }

  public void setOptions(SoloFollowOptions paramSoloFollowOptions)
  {
    if ((this.currentState == 0) || (this.followTarget == null))
    {
      Log.w(TAG, "Invalid state for options initialization.");
      return;
    }
    this.soloLinkMgr.sendTLVPacket(paramSoloFollowOptions);
    onOptionsUpdated(paramSoloFollowOptions);
  }

  public void start()
  {
    this.currentState = 1;
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.FOLLOW_STARTED"));
    Log.d(TAG, "Starting follow shot.");
    this.soloLinkMgr.enableFollowDataConnection();
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.shot.FollowShotState
 * JD-Core Version:    0.6.2
 */