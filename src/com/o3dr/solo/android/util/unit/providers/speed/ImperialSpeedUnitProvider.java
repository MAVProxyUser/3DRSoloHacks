package com.o3dr.solo.android.util.unit.providers.speed;

import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.speed.MeterPerSecond;
import org.beyene.sius.unit.composition.speed.SpeedUnit;

public class ImperialSpeedUnitProvider extends SpeedUnitProvider
{
  public SpeedUnit fromBaseToTarget(MeterPerSecond paramMeterPerSecond)
  {
    return (SpeedUnit)Operation.convert(paramMeterPerSecond, UnitIdentifier.MILES_PER_HOUR);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.providers.speed.ImperialSpeedUnitProvider
 * JD-Core Version:    0.6.2
 */