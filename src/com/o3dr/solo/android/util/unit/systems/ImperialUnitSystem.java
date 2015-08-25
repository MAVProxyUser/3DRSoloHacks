package com.o3dr.solo.android.util.unit.systems;

import com.o3dr.solo.android.util.unit.providers.area.AreaUnitProvider;
import com.o3dr.solo.android.util.unit.providers.area.ImperialAreaUnitProvider;
import com.o3dr.solo.android.util.unit.providers.length.ImperialLengthUnitProvider;
import com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider;
import com.o3dr.solo.android.util.unit.providers.speed.ImperialSpeedUnitProvider;
import com.o3dr.solo.android.util.unit.providers.speed.SpeedUnitProvider;

public class ImperialUnitSystem
  implements UnitSystem
{
  private static final AreaUnitProvider areaUnitProvider = new ImperialAreaUnitProvider();
  private static final LengthUnitProvider lengthUnitProvider = new ImperialLengthUnitProvider();
  private static final SpeedUnitProvider speedUnitProvider = new ImperialSpeedUnitProvider();

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
 * Qualified Name:     com.o3dr.solo.android.util.unit.systems.ImperialUnitSystem
 * JD-Core Version:    0.6.2
 */