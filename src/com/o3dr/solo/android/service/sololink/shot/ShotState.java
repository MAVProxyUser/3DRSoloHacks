package com.o3dr.solo.android.service.sololink.shot;

import com.o3dr.services.android.lib.coordinate.LatLongAlt;
import com.o3dr.solo.android.service.sololink.tlv.SoloShotOptions;
import com.o3dr.solo.android.util.AppPreferences;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class ShotState<Option extends SoloShotOptions>
{
  public static final int STATE_ACTIVE_MOVING_BACKWARD = 4;
  public static final int STATE_ACTIVE_MOVING_FORWARD = 3;
  public static final int STATE_ACTIVE_PAUSED = 2;
  public static final int STATE_IDLE = 0;
  public static final int STATE_INITIALIZING = 1;
  protected final AppPreferences appPrefs;
  protected Option shotOptions;

  protected ShotState(AppPreferences paramAppPreferences)
  {
    this.appPrefs = paramAppPreferences;
  }

  public Option getOptions()
  {
    return this.shotOptions;
  }

  public abstract int getState();

  public abstract LatLongAlt[] getWaypoints();

  public boolean isActive()
  {
    int i = getState();
    return (i == 2) || (i == 3) || (i == 4);
  }

  public boolean isStarted()
  {
    return getState() != 0;
  }

  public abstract void onOptionsUpdated(Option paramOption);

  public abstract void reset();

  public void setAbsoluteCruiseSpeed(int paramInt)
  {
    switch (getState())
    {
    default:
      return;
    case 4:
      paramInt = -paramInt;
    case 3:
    }
    this.shotOptions.setCruiseSpeed(paramInt);
    setOptions(this.shotOptions);
  }

  public abstract void setActiveState(int paramInt);

  public abstract void setOptions(Option paramOption);

  public abstract void start();

  @Retention(RetentionPolicy.SOURCE)
  public static @interface StateMode
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.sololink.shot.ShotState
 * JD-Core Version:    0.6.2
 */