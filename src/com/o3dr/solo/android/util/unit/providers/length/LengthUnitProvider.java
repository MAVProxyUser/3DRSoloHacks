package com.o3dr.solo.android.util.unit.providers.length;

import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.impl.FactoryLength;
import org.beyene.sius.unit.length.LengthUnit;
import org.beyene.sius.unit.length.Meter;

public abstract class LengthUnitProvider
{
  public Meter boxBaseValue(double paramDouble)
  {
    return FactoryLength.meter(paramDouble);
  }

  public LengthUnit boxBaseValueToTarget(double paramDouble)
  {
    return fromBaseToTarget(boxBaseValue(paramDouble));
  }

  public abstract LengthUnit fromBaseToTarget(Meter paramMeter);

  public Meter fromTargetToBase(LengthUnit paramLengthUnit)
  {
    if ((paramLengthUnit instanceof Meter))
      return (Meter)paramLengthUnit;
    return (Meter)Operation.convert(paramLengthUnit, UnitIdentifier.METER);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.providers.length.LengthUnitProvider
 * JD-Core Version:    0.6.2
 */