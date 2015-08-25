package com.o3dr.solo.android.util.unit.providers.area;

import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.UnitIdentifier;
import org.beyene.sius.unit.composition.area.AreaUnit;
import org.beyene.sius.unit.composition.area.SquareMeter;
import org.beyene.sius.unit.impl.FactoryArea;

public abstract class AreaUnitProvider
{
  public SquareMeter boxBaseValue(double paramDouble)
  {
    return FactoryArea.squareMeter(paramDouble);
  }

  public AreaUnit boxBaseValueToTarget(double paramDouble)
  {
    return fromBaseToTarget(boxBaseValue(paramDouble));
  }

  public abstract AreaUnit fromBaseToTarget(SquareMeter paramSquareMeter);

  public SquareMeter fromTargetToBase(AreaUnit paramAreaUnit)
  {
    if ((paramAreaUnit instanceof SquareMeter))
      return (SquareMeter)paramAreaUnit;
    return (SquareMeter)Operation.convert(paramAreaUnit, UnitIdentifier.SQUARE_METER);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     com.o3dr.solo.android.util.unit.providers.area.AreaUnitProvider
 * JD-Core Version:    0.6.2
 */