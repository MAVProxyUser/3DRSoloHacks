package com.o3dr.solo.android.util.unit.providers.speed;

import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.speed.MeterPerSecond;
import org.beyene.sius.unit.composition.speed.SpeedUnit;
import org.beyene.sius.unit.impl.FactorySpeed;

public abstract class SpeedUnitProvider
{
  public MeterPerSecond boxBaseValue(double paramDouble)
  {
    return FactorySpeed.mps(paramDouble);
  }

  public SpeedUnit boxBaseValueToTarget(double paramDouble)
  {
    return fromBaseToTarget(boxBaseValue(paramDouble));
  }

  public abstract SpeedUnit fromBaseToTarget(MeterPerSecond paramMeterPerSecond);

  public MeterPerSecond fromTargetToBase(SpeedUnit paramSpeedUnit)
  {
    if ((paramSpeedUnit instanceof MeterPerSecond))
      return (MeterPerSecond)paramSpeedUnit;
    return (MeterPerSecond)Operation.convert(paramSpeedUnit, UnitIdentifier.METER_PER_SECOND);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.providers.speed.SpeedUnitProvider
 * JD-Core Version:    0.6.2
 */