package com.o3dr.solo.android.util.unit.systems;

import com.o3dr.solo.android.util.unit.providers.area.AreaUnitProvider;
import com.o3dr.solo.android.util.unit.providers.area.MetricAreaUnitProvider;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;
import com.o3dr.solo.android.util.unit.providers.length.MetricLengthUnitProvider;
import com.o3dr.solo.android.util.unit.providers.speed.MetricSpeedUnitProvider;
import com.o3dr.solo.android.util.unit.providers.speed.SpeedUnitProvider;

public class MetricUnitSystem
  implements UnitSystem
{
  private static final AreaUnitProvider areaUnitProvider = new MetricAreaUnitProvider();
  private static final LengthUnitProvider lengthUnitProvider = new MetricLengthUnitProvider();
  private static final SpeedUnitProvider speedUnitProvider = new MetricSpeedUnitProvider();

  public AreaUnitProvider getAreaUnitProvider()
  {
    return areaUnitProvider;
  }

  public LengthUnitProvider getLengthUnitProvider()
  {
    return lengthUnitProvider;
  }

  public SpeedUnitProvider getSpeedUnitProvider()
  {
    return speedUnitProvider;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.systems.MetricUnitSystem
 * JD-Core Version:    0.6.2
 */