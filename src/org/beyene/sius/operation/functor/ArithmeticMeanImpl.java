package org.beyene.sius.operation.functor;

import java.util.Iterator;
import java.util.List;
import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.operation.Operation;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitFactory;
import org.beyene.sius.unit.UnitId;

final class ArithmeticMeanImpl<D extends Dimension<D>, B extends Unit<D, B, B>, TARGET_UNIT extends Unit<D, B, TARGET_UNIT>> extends AbstractFunctor<D, B, TARGET_UNIT, ArithmeticMean<D, B, TARGET_UNIT>>
  implements ArithmeticMean<D, B, TARGET_UNIT>
{
  private TARGET_UNIT cachedResult;

  public ArithmeticMeanImpl(UnitId<D, B, TARGET_UNIT> paramUnitId)
  {
    super(paramUnitId, ArithmeticMean.class.getSimpleName());
  }

  protected ArithmeticMean<D, B, TARGET_UNIT> _this()
  {
    return this;
  }

  public TARGET_UNIT apply()
  {
    if (this.operands.isEmpty())
      return UnitFactory.valueOf(0.0D, this.targetId);
    if (this.cachedResult != null)
      return this.cachedResult;
    double d = 0.0D;
    Iterator localIterator = this.operands.iterator();
    while (localIterator.hasNext())
      d += Operation.convert((Unit)localIterator.next(), this.targetId).getValue();
    this.cachedResult = UnitFactory.valueOf(d / this.operands.size(), this.targetId);
    return this.cachedResult;
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.operation.functor.ArithmeticMeanImpl
 * JD-Core Version:    0.6.2
 */