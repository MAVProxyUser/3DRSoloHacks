package org.beyene.sius.operation;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitFactory;
import org.beyene.sius.unit.UnitId;

final class Converter
{
  public static <D extends Dimension<D>, B extends Unit<D, B, B>, TARGET_UNIT extends Unit<D, B, TARGET_UNIT>> TARGET_UNIT convert(Unit<D, B, ?> paramUnit, UnitId<D, B, TARGET_UNIT> paramUnitId)
  {
    Unit localUnit = UnitFactory.valueOf(0.0D, paramUnitId);
    if (localUnit.getIdentifier().equals(paramUnit.getIdentifier()))
      return localUnit.valueOf(paramUnit.getValue());
    return localUnit.fromBase(paramUnit.toBase());
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.operation.Converter
 * JD-Core Version:    0.6.2
 */