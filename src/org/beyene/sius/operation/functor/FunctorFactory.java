package org.beyene.sius.operation.functor;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;

public final class FunctorFactory
{
  public static <D extends Dimension<D>, B extends Unit<D, B, B>, TARGET_UNIT extends Unit<D, B, TARGET_UNIT>> ArithmeticMean<D, B, TARGET_UNIT> mean(UnitId<D, B, TARGET_UNIT> paramUnitId)
  {
    return new ArithmeticMeanImpl(paramUnitId);
  }

  public static <D extends Dimension<D>, B extends Unit<D, B, B>, TARGET_UNIT extends Unit<D, B, TARGET_UNIT>> Adder<D, B, TARGET_UNIT> sum(UnitId<D, B, TARGET_UNIT> paramUnitId)
  {
    return new AdderImpl(paramUnitId);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.operation.functor.FunctorFactory
 * JD-Core Version:    0.6.2
 */