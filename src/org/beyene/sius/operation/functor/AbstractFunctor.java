package org.beyene.sius.operation.functor;

import java.util.LinkedList;
import java.util.List;
import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;

abstract class AbstractFunctor<D extends Dimension<D>, B extends Unit<D, B, B>, TARGET_UNIT extends Unit<D, B, TARGET_UNIT>, F extends Functor<D, B, TARGET_UNIT, F>>
  implements Functor<D, B, TARGET_UNIT, F>
{
  protected TARGET_UNIT cachedResult;
  private final String functionName;
  protected final List<Unit<D, B, ?>> operands = new LinkedList();
  protected final UnitId<D, B, TARGET_UNIT> targetId;

  public AbstractFunctor(UnitId<D, B, TARGET_UNIT> paramUnitId, String paramString)
  {
    this.targetId = paramUnitId;
    this.functionName = paramString;
  }

  private void resetCache()
  {
    this.cachedResult = null;
  }

  protected abstract F _this();

  public abstract TARGET_UNIT apply();

  public F op(Unit<D, B, ?> paramUnit)
  {
    resetCache();
    this.operands.add(paramUnit);
    return _this();
  }

  public String toString()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.functionName;
    arrayOfObject[1] = this.targetId;
    arrayOfObject[2] = this.operands;
    return String.format("%s [targetId=%s, operands=%s]", arrayOfObject);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.operation.functor.AbstractFunctor
 * JD-Core Version:    0.6.2
 */