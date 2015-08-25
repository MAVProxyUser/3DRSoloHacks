package com.o3dr.solo.android.util.unit.providers.length;

import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.LengthUnit;
import org.beyene.sius.unit.length.Meter;

public class ImperialLengthUnitProvider extends LengthUnitProvider
{
  public LengthUnit fromBaseToTarget(Meter paramMeter)
  {
    if (Math.abs(paramMeter.getValue()) >= 1609.3440000000001D)
      return (LengthUnit)Operation.convert(paramMeter, UnitIdentifier.MILE);
    return (LengthUnit)Operation.convert(paramMeter, UnitIdentifier.FOOT);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.providers.length.ImperialLengthUnitProvider
 * JD-Core Version:    0.6.2
 */