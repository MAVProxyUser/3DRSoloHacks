package com.o3dr.solo.android.util.unit.systems;

import com.o3dr.solo.android.util.unit.providers.area.AreaUnitProvider;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;
import com.o3dr.solo.android.util.unit.providers.speed.SpeedUnitProvider;

public abstract interface UnitSystem
{
  public static final int AUTO = 0;
  public static final int IMPERIAL = 2;
  public static final int METRIC = 1;

  public abstract AreaUnitProvider getAreaUnitProvider();

  public abstract LengthUnitProvider getLengthUnitProvider();

  public abstract SpeedUnitProvider getSpeedUnitProvider();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.systems.UnitSystem
 * JD-Core Version:    0.6.2
 */