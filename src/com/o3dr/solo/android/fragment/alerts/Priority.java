package com.o3dr.solo.android.fragment.alerts;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Priority
{
  public static final int ERROR = 3;
  public static final int INFO = 1;
  public static final int VERBOSE = 0;
  public static final int WARNING = 2;

  @Retention(RetentionPolicy.SOURCE)
  public static @interface AlertPriority
  {
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.fragment.alerts.Priority
 * JD-Core Version:    0.6.2
 */