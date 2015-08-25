package com.o3dr.solo.android.fragment.shots;

public abstract interface ShotControlListener
{
  public abstract void onShotEnded(int paramInt);

  public abstract void onShotPaused(int paramInt);

  public abstract void onShotResumed(int paramInt);

  public abstract void onShotSetupCompleted(int paramInt);

  public abstract void onShotStarted(int paramInt);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.shots.ShotControlListener
 * JD-Core Version:    0.6.2
 */