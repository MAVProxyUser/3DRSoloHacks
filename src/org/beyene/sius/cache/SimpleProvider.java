package org.beyene.sius.cache;

import org.beyene.sius.dimension.Dimension;
import org.beyene.sius.unit.Unit;
import org.beyene.sius.unit.UnitId;

final class SimpleProvider
  implements Provider
{
  public <D extends Dimension<D>, B extends Unit<D, B, B>, U extends Unit<D, B, U>> Cache<D, B, U> newInstance(UnitId<D, B, U> paramUnitId, int paramInt)
  {
    return new SimpleCache(paramInt);
  }
}

/* Location:           /Users/kfinisterre/Desktop/Solo/3DRSoloHacks/unpacked_apk/classes_dex2jar.jar
 * Qualified Name:     org.beyene.sius.cache.SimpleProvider
 * JD-Core Version:    0.6.2
 */