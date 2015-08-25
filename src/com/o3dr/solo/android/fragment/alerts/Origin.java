package com.o3dr.solo.android.fragment.alerts;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Origin
{
  public static final int APP = 3;
  public static final int AUTOPILOT = 0;
  public static final int CONTROLLER = 2;
  public static final int SOLO = 1;
  private static final String TAG = Origin.class.getSimpleName();

  @Retention(RetentionPolicy.SOURCE)
  public static @interface AlertOrigin
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.alerts.Origin
 * JD-Core Version:    0.6.2
 */