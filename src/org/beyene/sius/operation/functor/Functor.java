package org.beyene.sius.operation.functor;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;

public abstract interface Functor<D extends Dimension<D>, B extends Unit<D, B, B>, TARGET_UNIT extends Unit<D, B, TARGET_UNIT>, F extends Functor<D, B, TARGET_UNIT, F>>
{
  public abstract TARGET_UNIT apply();

  public abstract F op(Unit<D, B, ?> paramUnit);

  public abstract String toString();
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.operation.functor.Functor
 * JD-Core Version:    0.6.2
 */