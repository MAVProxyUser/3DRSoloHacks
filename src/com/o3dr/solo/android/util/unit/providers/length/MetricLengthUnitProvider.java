package com.o3dr.solo.android.util.unit.providers.length;

import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.length.LengthUnit;
import org.beyene.sius.unit.length.Meter;

public class MetricLengthUnitProvider extends LengthUnitProvider
{
  public LengthUnit fromBaseToTarget(Meter paramMeter)
  {
    if (Math.abs(paramMeter.getValue()) >= 1000.0D)
      return (LengthUnit)Operation.convert(paramMeter, UnitIdentifier.KILOMETER);
    return paramMeter;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.providers.length.MetricLengthUnitProvider
 * JD-Core Version:    0.6.2
 */