package com.o3dr.solo.android.service;

import android.content.Context;
import android.media.SoundPool;
import android.util.Log;
import android.util.SparseIntArray;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class SoundManager
{
  public static final int ALERT_MESSAGE = 2131034112;
  public static final int ALERT_NEUTRAL = 2131034113;
  public static final int ARM = 2131034114;
  public static final int BATT_LOW_ARTOO = 2131034115;
  public static final int BATT_LOW_SOLO = 2131034116;
  public static final int CABLE_CAM = 2131034129;
  public static final int DISARM = 2131034118;
  public static final int DROP_PIN = 2131034119;
  public static final int FAILSAFE = 2131034120;
  public static final int GPS_LOST = 2131034121;
  public static final int NO_SOUND = -1;
  public static final int RECORD_START = 2131034124;
  public static final int RECORD_START_SIMPLE = 2131034125;
  public static final int RECORD_STOP = 2131034126;
  public static final int RECORD_STOP_SIMPLE = 2131034127;
  public static final int RTH = 2131034128;
  private static final String TAG = SoundManager.class.getSimpleName();
  public static final int UPDATE_SUCCESS = 2131034130;
  private final Context context;
  private final SparseIntArray loadedSoundsIds = new SparseIntArray(16);
  private final SoundPool soundPool;

  SoundManager(Context paramContext)
  {
    this.context = paramContext;
    this.soundPool = new SoundPool(1, 3, 0);
  }

  private void loadSound(Context paramContext, int paramInt)
  {
    this.loadedSoundsIds.put(paramInt, this.soundPool.load(paramContext, paramInt, 1));
  }

  public boolean play(int paramInt)
  {
    if (paramInt == -1);
    int i;
    do
    {
      return true;
      i = this.loadedSoundsIds.get(paramInt, -1);
      if (i == -1)
      {
        Log.e(TAG, "Unable to retrieve sound id for resource " + paramInt);
        return false;
      }
    }
    while (this.soundPool.play(i, 1.0F, 1.0F, 1, 0, 1.0F) != 0);
    return false;
  }

  void start()
  {
    loadSound(this.context, 2131034112);
    loadSound(this.context, 2131034113);
    loadSound(this.context, 2131034114);
    loadSound(this.context, 2131034115);
    loadSound(this.context, 2131034116);
    loadSound(this.context, 2131034118);
    loadSound(this.context, 2131034119);
    loadSound(this.context, 2131034120);
    loadSound(this.context, 2131034121);
    loadSound(this.context, 2131034124);
    loadSound(this.context, 2131034125);
    loadSound(this.context, 2131034126);
    loadSound(this.context, 2131034127);
    loadSound(this.context, 2131034128);
    loadSound(this.context, 2131034129);
    loadSound(this.context, 2131034130);
  }

  void stop()
  {
    int i = this.loadedSoundsIds.size();
    for (int j = 0; j < i; j++)
      this.soundPool.unload(this.loadedSoundsIds.valueAt(j));
  }

  @Retention(RetentionPolicy.SOURCE)
  public static @interface SoundType
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.service.SoundManager
 * JD-Core Version:    0.6.2
 */