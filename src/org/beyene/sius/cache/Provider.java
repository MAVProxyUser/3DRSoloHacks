package org.beyene.sius.cache;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;

abstract interface Provider
{
  public abstract <D extends Dimension<D>, B extends Unit<D, B, B>, U extends Unit<D, B, U>> Cache<D, B, U> newInstance(UnitId<D, B, U> paramUnitId, int paramInt);
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.cache.Provider
 * JD-Core Version:    0.6.2
 */