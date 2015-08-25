package com.o3dr.solo.android.util.unit.providers.area;

import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.area.AreaUnit;
import org.beyene.sius.unit.composition.area.Constants;
import org.beyene.sius.unit.composition.area.SquareMeter;

public class MetricAreaUnitProvider extends AreaUnitProvider
{
  public AreaUnit fromBaseToTarget(SquareMeter paramSquareMeter)
  {
    double d = Math.abs(paramSquareMeter.getValue());
    if (d >= Constants.SQM_PER_SQKM)
      return (AreaUnit)Operation.convert(paramSquareMeter, UnitIdentifier.SQUARE_KILOMETER);
    if (d >= 0.1D)
      return paramSquareMeter;
    return (AreaUnit)Operation.convert(paramSquareMeter, UnitIdentifier.SQUARE_MILLIMETER);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.providers.area.MetricAreaUnitProvider
 * JD-Core Version:    0.6.2
 */