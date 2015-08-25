package com.o3dr.solo.android.service.sololink.shot;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import com.o3dr.solo.android.service.sololink.SoloLinkManager;
import com.o3dr.solo.android.service.sololink.tlv.SoloCableCamOptions;
import com.o3dr.solo.android.service.sololink.tlv.SoloCableCamWaypoint;
import com.o3dr.solo.android.service.sololink.tlv.SoloFollowOptions;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageLocation;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShot;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShotGetter;
import com.o3dr.solo.android.service.sololink.tlv.SoloMessageShotSetter;
import com.o3dr.solo.android.service.sololink.tlv.SoloShotError;
import com.o3dr.solo.android.service.sololink.tlv.SoloShotOptions;
import com.o3dr.solo.android.service.sololink.tlv.TLVPacket;
import com.o3dr.solo.android.util.AppPreferences;

public class ShotManager
{
  public static final String ACTION_SHOT_ERROR = "com.o3dr.solo.android.action.SHOT_ERROR";
  public static final String ACTION_SHOT_TYPE_UPDATE = "com.o3dr.solo.android.action.SHOT_TYPE_UPDATE";
  public static final String EXTRA_SHOT_ERROR_TYPE = "extra_shot_error_type";
  public static final String EXTRA_SHOT_TYPE = "extra_shot_type";
  private static final String TAG = ShotManager.class.getSimpleName();
  private final CableCamState cableCamState;
  private final SoloMessageShotGetter currentShotMessage = new SoloMessageShotGetter(-1);
  private final FollowShotState followState;
  private final LocalBroadcastManager lbm;
  private final OrbitState orbitState;
  private final SelfieState selfieState;
  private final SoloMessageShotSetter shotSetter = new SoloMessageShotSetter(-1);
  private final SoloLinkManager soloLinkMgr;

  public ShotManager(Context paramContext, SoloLinkManager paramSoloLinkManager)
  {
    this.lbm = LocalBroadcastManager.getInstance(paramContext);
    this.soloLinkMgr = paramSoloLinkManager;
    AppPreferences localAppPreferences = new AppPreferences(paramContext);
    this.selfieState = new SelfieState(localAppPreferences, this.lbm, paramSoloLinkManager);
    this.cableCamState = new CableCamState(localAppPreferences, this.lbm, paramSoloLinkManager);
    this.orbitState = new OrbitState(localAppPreferences, this.lbm, paramSoloLinkManager);
    this.followState = new FollowShotState(localAppPreferences, this.lbm, paramSoloLinkManager);
  }

  private void parseShotType(int paramInt)
  {
    if (paramInt == getCurrentShot())
      return;
    switch (paramInt)
    {
    case 3:
    case 4:
    default:
      resetShot();
      return;
    case 2:
      this.cableCamState.reset();
      this.cableCamState.start();
      return;
    case 0:
      this.selfieState.reset();
      this.selfieState.start();
      return;
    case 1:
      this.orbitState.reset();
      this.orbitState.start();
      return;
    case 5:
    }
    this.followState.reset();
    this.followState.start();
  }

  private void resetShot()
  {
    this.cableCamState.reset();
    this.selfieState.reset();
    this.orbitState.reset();
    this.followState.reset();
  }

  public void forceResetShot()
  {
    resetShot();
    setCurrentShot(-1);
    this.currentShotMessage.setShotType(-1);
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE").putExtra("extra_shot_type", -1));
  }

  public int getCurrentShot()
  {
    return this.currentShotMessage.getShotType();
  }

  public ShotState getCurrentShotState()
  {
    switch (getCurrentShot())
    {
    case 3:
    case 4:
    default:
      return null;
    case 2:
      return this.cableCamState;
    case 0:
      return this.selfieState;
    case 1:
      return this.orbitState;
    case 5:
    }
    return this.followState;
  }

  public boolean handleMessage(TLVPacket paramTLVPacket)
  {
    if (paramTLVPacket == null)
      return false;
    switch (paramTLVPacket.getMessageType())
    {
    default:
      return false;
    case 0:
      int i = ((SoloMessageShot)paramTLVPacket).getShotType();
      parseShotType(i);
      this.currentShotMessage.setShotType(i);
      this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SHOT_TYPE_UPDATE").putExtra("extra_shot_type", i));
      Log.d(TAG, "Received current shot type: " + i);
      return true;
    case 1001:
      Log.d(TAG, "Received cable cam shot waypoint.");
      this.cableCamState.setWaypoint((SoloCableCamWaypoint)paramTLVPacket);
      return true;
    case 4:
      Log.d(TAG, "Received cable cam shot options.");
      this.cableCamState.onOptionsUpdated((SoloCableCamOptions)paramTLVPacket);
      return true;
    case 19:
      this.followState.onOptionsUpdated((SoloFollowOptions)paramTLVPacket);
      return true;
    case 20:
      Log.d(TAG, "Received shot options.");
      if (this.selfieState.isStarted())
        this.selfieState.onOptionsUpdated((SoloShotOptions)paramTLVPacket);
      while (true)
      {
        return true;
        if (this.orbitState.isStarted())
          this.orbitState.onOptionsUpdated((SoloShotOptions)paramTLVPacket);
      }
    case 2:
      Log.d(TAG, "Received shot message location.");
      if (this.orbitState.isStarted())
        this.orbitState.onOrbitROIUpdated((SoloMessageLocation)paramTLVPacket);
      return true;
    case 21:
    }
    SoloShotError localSoloShotError = (SoloShotError)paramTLVPacket;
    this.lbm.sendBroadcast(new Intent("com.o3dr.solo.android.action.SHOT_ERROR").putExtra("extra_shot_error_type", localSoloShotError.getErrorType()));
    return true;
  }

  public void setCurrentShot(int paramInt)
  {
    int i = getCurrentShot();
    if (paramInt == i)
      return;
    if ((paramInt != -1) && (i != -1))
    {
      this.shotSetter.setShotType(-1);
      this.soloLinkMgr.sendTLVPacket(this.shotSetter);
    }
    this.shotSetter.setShotType(paramInt);
    this.soloLinkMgr.sendTLVPacket(this.shotSetter);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.shot.ShotManager
 * JD-Core Version:    0.6.2
 */